package com.app.nailappointment.firebase.repository;

import androidx.annotation.Nullable;

import com.app.nailappointment.room.viewmodel.UserRoomViewModel;
import com.app.nailappointment.utils.CollectionPaths;
import com.app.nailappointment.utils.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FirebaseUserRepo {

    private final CollectionReference mUsers;

    public FirebaseUserRepo() {
        this.mUsers = FirebaseFirestore.getInstance()
                .collection(CollectionPaths.USERS.getName());
    }

    public void findById(String id, UserRoomViewModel userRoomViewModel) {
        mUsers.whereEqualTo("id", id)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null) {
                    List<User> users = value.toObjects(User.class);
                    if (!Objects.isNull(users) || !users.isEmpty()) {
                        userRoomViewModel.createUser(users.get(0));
                    }
                }
            }
        });
    }

    public void addUser(User user, UserRoomViewModel userRoomViewModel) {
        mUsers.document(user.getId())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        userRoomViewModel.createUser(user);
                    }
                });
    }

    public void updateUser(User user, UserRoomViewModel userRoomViewModel) {
        Map<String, Object> userFieldsToUpdate = new HashMap<>();
        userFieldsToUpdate.put("email", user.getEmail());
        userFieldsToUpdate.put("username", user.getUsername());

        mUsers.document(user.getId())
                .update(userFieldsToUpdate)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        userRoomViewModel.updateUser(user);
                    }
                });
    }
}
