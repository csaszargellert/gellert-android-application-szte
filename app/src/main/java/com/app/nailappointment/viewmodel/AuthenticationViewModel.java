//package com.app.nailappointment.viewmodel;
//
//import android.app.Application;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.AndroidViewModel;
//import androidx.lifecycle.MutableLiveData;
//
//import com.app.nailappointment.db.UserEntity;
//import com.app.nailappointment.firebase.repository.FirebaseAuthenticationRepo;
//
//public class AuthenticationViewModel extends AndroidViewModel {
//
//    private final FirebaseAuthenticationRepo authRepository;
//
//    private final MutableLiveData<Boolean> isUserLoggedIn;
//
//    public AuthenticationViewModel(@NonNull Application application) {
//        super(application);
//        this.authRepository = new FirebaseAuthenticationRepo(application);
//
//        isUserLoggedIn = authRepository.getIsUserLoggedInMutableLiveData();
//    }
//
//    public MutableLiveData<Boolean> getIsUserLoggedIn() {
//        return isUserLoggedIn;
//    }
//
//    public void register(UserEntity userEntity) {
//        authRepository.register(userEntity);
//    }
//
//    public void login(UserEntity userEntity) {
////        authRepository.login(userEntity);
//    }
//
//    public void logout() {
//        authRepository.logout();
//    }
//
//    public void updateEmail(String email) {
//        authRepository.updateEmail(email);
//    }
//}
