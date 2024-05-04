//package com.app.nailappointment.viewmodel;
//
//import android.app.Application;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.AndroidViewModel;
//import androidx.lifecycle.MutableLiveData;
//
//import com.app.nailappointment.dto.UserDTO;
//import com.app.nailappointment.firebase.repository.FirebaseUserRepo;
//
//public class UserViewModel extends AndroidViewModel {
//
//    private final FirebaseUserRepo repository;
//
//    private final MutableLiveData<UserDTO> user;
//
//    private final MutableLiveData<String> userId;
//
//    public UserViewModel(@NonNull Application application) {
//        super(application);
//
//        this.repository = new FirebaseUserRepo(application);
//        this.user = repository.getUserDTOMutableLiveData();
//        this.userId = repository.getUserIdMLD();
//    }
//
//    public MutableLiveData<String> getUserId() {
//        return userId;
//    }
//
//    public MutableLiveData<UserDTO> getUser() {
//        return user;
//    }
//
//    public void setUser(UserDTO currentUser) {
//        repository.setUserDTOMutableLiveData(currentUser);
//    }
//
//    public void updateUser(UserDTO user) {
//        repository.updateUser(user);
//    }
//
//    public void findById(String id) {
//        repository.findById(id);
//    }
//
//}
