package com.app.nailappointment.repository;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.app.nailappointment.db.UserEntity;
import com.app.nailappointment.dto.UserDTO;
import com.app.nailappointment.dto.mapper.UserMapper;
import com.app.nailappointment.utils.CollectionPaths;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

public class AuthenticationRepository {

    private final Application application;

    private final UserRepository userRepository;

    private final FirebaseAuth mAuth;

    private final MutableLiveData<Boolean> isUserLoggedInMutableLiveData;

    public AuthenticationRepository(Application application) {
        this.application = application;
        this.userRepository = new UserRepository(application);
        this.mAuth = FirebaseAuth.getInstance();

        isUserLoggedInMutableLiveData = new MutableLiveData<>();

        setIsUserLoggedInMutableLiveData(mAuth.getCurrentUser() != null);
    }

    public MutableLiveData<Boolean> getIsUserLoggedInMutableLiveData() {
        return isUserLoggedInMutableLiveData;
    }

    private void setIsUserLoggedInMutableLiveData(boolean bool) {
        isUserLoggedInMutableLiveData.setValue(bool);
    }

    public void register(UserEntity userEntity) {
        mAuth.createUserWithEmailAndPassword(userEntity.getEmail(), userEntity.getPassword())
                .addOnSuccessListener(authResult -> {
                    userRepository.addUser(userEntity, authResult.getUser().getUid());
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(application, e.getMessage(), Toast.LENGTH_LONG)
                                .show();
                    }
                });
    }

    public void login(UserEntity userEntity) {
        mAuth.signInWithEmailAndPassword(userEntity.getEmail(), userEntity.getPassword())
                .addOnSuccessListener(authResult -> {
                    userRepository.findById(authResult.getUser().getUid());
                    setIsUserLoggedInMutableLiveData(true);
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(application, e.getMessage(), Toast.LENGTH_LONG)
                                .show();
                    }
                });
    }

    public void logout() {
        setIsUserLoggedInMutableLiveData(false);
        mAuth.signOut();
    }

    public void updateEmail(String email) {
        mAuth.getCurrentUser().updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(application, "Email update was successful", Toast.LENGTH_LONG)
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
