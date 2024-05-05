//package com.app.nailappointment.view;
//
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.navigation.NavController;
//import androidx.navigation.Navigation;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.app.nailappointment.MainActivity;
//import com.app.nailappointment.R;
//import com.app.nailappointment.db.UserEntity;
//import com.app.nailappointment.utils.BundleArguments;
//import com.app.nailappointment.utils.CustomError;
//import com.app.nailappointment.utils.Styler;
//import com.app.nailappointment.utils.Validator;
//import com.app.nailappointment.utils.viewmodel.AuthenticationViewModel;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.FirebaseDatabase;
//
//public class LoginFragment extends Fragment {
//
//    private Button registerNavigationButton;
//
//    private Button loginButton;
//
//    private EditText emailEditText;
//
//    private EditText passwordEditText;
//
//    private NavController navController;
//
//    private AuthenticationViewModel authViewModel;
//
//    public LoginFragment() {
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        authViewModel = new ViewModelProvider(requireActivity()).get(AuthenticationViewModel.class);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_login, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        registerNavigationButton = view.findViewById(R.id.registerNavigationButton);
//        loginButton = view.findViewById(R.id.loginButton);
//        emailEditText = view.findViewById(R.id.emailEditText);
//        passwordEditText = view.findViewById(R.id.passwordEditText);
//
//        navController = Navigation.findNavController(view);
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        Styler.underlineButton(registerNavigationButton);
//        registerNavigationButton.setOnClickListener(this::onNavigateToRegister);
//        loginButton.setOnClickListener(this::onLogin);
//    }
//
//    private void onNavigateToRegister(View view) {
//        navController.navigate(R.id.registerFragment);
//    }
//
//    private void onLogin(View view) {
//        String email = String.valueOf(emailEditText.getText()).trim();
//        String password = String.valueOf(passwordEditText.getText()).trim();
//
//        CustomError emailError = Validator.isEmailValid(email);
//        CustomError passwordError = Validator.isPasswordValid(password);
//
//        boolean canLoginUser = true;
//
//        if (emailError.getHasError()) {
//            emailEditText.setError(emailError.getMessage());
//            canLoginUser = false;
//        }
//        if (passwordError.getHasError()) {
//            passwordEditText.setError(passwordError.getMessage());
//            canLoginUser = false;
//        }
//
//        if (!canLoginUser) return;
//
////        authViewModel.login(new UserEntity(email, password));
//
//        navController.navigate(R.id.homeFragment);
//    }
//}