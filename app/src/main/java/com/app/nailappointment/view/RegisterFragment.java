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
//
//import com.app.nailappointment.MainActivity;
//import com.app.nailappointment.R;
//import com.app.nailappointment.db.UserEntity;
//import com.app.nailappointment.utils.CustomError;
//import com.app.nailappointment.utils.Styler;
//import com.app.nailappointment.utils.Validator;
//import com.app.nailappointment.viewmodel.AuthenticationViewModel;
//
//public class RegisterFragment extends Fragment {
//
//    private AuthenticationViewModel authViewModel;
//
//    private Button loginNavigationButton;
//
//    private Button registerButton;
//
//    private EditText emailEditText;
//
//    private EditText usernameEditText;
//
//    private EditText passwordEditText;
//
//    private EditText confirmPasswordEditText;
//
//    private NavController navController;
//
//    public RegisterFragment() {
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        authViewModel = new ViewModelProvider(requireActivity()).get(AuthenticationViewModel.class);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_register, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        loginNavigationButton = view.findViewById(R.id.loginNavigationButton);
//        registerButton = view.findViewById(R.id.registerButton);
//        emailEditText = view.findViewById(R.id.emailEditText);
//        usernameEditText = view.findViewById(R.id.usernameEditText);
//        passwordEditText = view.findViewById(R.id.passwordEditText);
//        confirmPasswordEditText = view.findViewById(R.id.confirmPasswordEditText);
//
//        navController = Navigation.findNavController(view);
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        Styler.underlineButton(loginNavigationButton);
//        registerButton.setOnClickListener(this::onRegister);
//        loginNavigationButton.setOnClickListener(this::onNavigateToLogin);
//    }
//
//    private void onNavigateToLogin(View view) {
//        navController.navigate(R.id.loginFragment);
//    }
//
//    private void onRegister(View view) {
//        String email = String.valueOf(emailEditText.getText()).trim();
//        String username = String.valueOf(usernameEditText.getText()).trim();
//        String password = String.valueOf(passwordEditText.getText()).trim();
//        String confirmPassword = String.valueOf(confirmPasswordEditText.getText()).trim();
//
//        CustomError emailError = Validator.isEmailValid(email);
//        CustomError usernameError = Validator.isUsernameValid(username);
//        CustomError passwordError = Validator.isPasswordValid(password);
//        CustomError confirmPasswordError = Validator.doPasswordsMatch(password, confirmPassword);
//
//        boolean canSaveUser = true;
//
//        // Validate User Input
//        if (emailError.getHasError()) {
//            emailEditText.setError(emailError.getMessage());
//            canSaveUser = false;
//        }
//        if (usernameError.getHasError()) {
//            usernameEditText.setError(usernameError.getMessage());
//            canSaveUser = false;
//        }
//        if (passwordError.getHasError()) {
//            passwordEditText.setError(passwordError.getMessage());
//            canSaveUser = false;
//        }
//        if (confirmPasswordError.getHasError()) {
//            confirmPasswordEditText.setError(confirmPasswordError.getMessage());
//            canSaveUser = false;
//        }
//        if (!canSaveUser) return;
//
//        // Save user to database
////        authViewModel.register(new UserEntity(username, email, password));
//        navController.navigate(R.id.loginFragment);
//    }
//
//}