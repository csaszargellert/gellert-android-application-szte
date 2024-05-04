package com.app.nailappointment.repository;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.nailappointment.db.UserEntity;
import com.app.nailappointment.dto.UserDTO;
import com.app.nailappointment.dto.mapper.UserMapper;
import com.app.nailappointment.utils.CollectionPaths;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Objects;

public class UserRepository {

    private final Application application;

    private final CollectionReference mUsers;

    private final MutableLiveData<UserDTO> userDTOMutableLiveData;

    private final MutableLiveData<String> userIdMLD;

    public UserRepository(Application application) {
        this.application = application;

        this.mUsers = FirebaseFirestore.getInstance()
                .collection(CollectionPaths.USERS.getName());
        this.userDTOMutableLiveData = new MutableLiveData<>();
        this.userIdMLD = new MutableLiveData<>();

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {
            setUserIdMLD(firebaseUser.getUid());
        } else {
            setUserIdMLD(null);
        }
        setUserDTOMutableLiveData(null);
    }

    public MutableLiveData<String> getUserIdMLD() {
        return userIdMLD;
    }

    public MutableLiveData<UserDTO> getUserDTOMutableLiveData() {
        return userDTOMutableLiveData;
    }

    private void setUserIdMLD(String id) {
        userIdMLD.setValue(id);
    }

    public void setUserDTOMutableLiveData(UserDTO user) {
        userDTOMutableLiveData.setValue(user);
    }

    public void findById(String id) {
        mUsers.whereEqualTo("id", id)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null) {
                    List<UserDTO> users = value.toObjects(UserDTO.class);
                    if (Objects.isNull(users) || users.isEmpty()) {
                        Toast.makeText(application, "User not found", Toast.LENGTH_LONG)
                                .show();
                    } else {
                        setUserDTOMutableLiveData(users.get(0));
                        setUserIdMLD(id);
                    }
                } else {
                    Toast.makeText(application, error.getMessage(), Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

//        mUsers.document(id)
//                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                if (error == null) {
//                    setUserDTOMutableLiveData(value.toObject(UserDTO.class));
//                    setUserIdMLD(id);
//                } else {
//                    Toast.makeText(application, error.getMessage(), Toast.LENGTH_LONG)
//                            .show();
//                }
//            }
//        });
    }

    public void addUser(UserEntity user, String id) {
        UserDTO userDTO = UserMapper.mapEntityToDTO(user, id);

        mUsers.document(id)
                .set(userDTO.toMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        setUserDTOMutableLiveData(null);
                        setUserIdMLD(null);
                        Toast.makeText(application, "Registration successful", Toast.LENGTH_LONG)
                                .show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(application, e.getMessage(), Toast.LENGTH_LONG)
                                .show();
                    }
                });
    }

    public void updateUser(UserDTO user) {
        mUsers.document(user.getId())
                .update(user.toMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        findById(user.getId());
                        Toast.makeText(application, "Update successful", Toast.LENGTH_LONG)
                                .show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(application, e.getMessage(), Toast.LENGTH_LONG)
                                .show();
                    }
                });
    }
}
