package com.app.nailappointment.view.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.app.nailappointment.R;
import com.app.nailappointment.firebase.viewmodel.FirebaseAuthenticationViewmodel;
import com.app.nailappointment.firebase.viewmodel.FirebaseUserViewmodel;
import com.app.nailappointment.room.viewmodel.UserRoomViewModel;
import com.app.nailappointment.utils.CustomError;
import com.app.nailappointment.utils.StylerHelper;
import com.app.nailappointment.utils.Validator;
import com.app.nailappointment.utils.model.User;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuthenticationViewmodel firebaseAuthViewmodel;

    private FirebaseUserViewmodel firebaseUserViewmodel;

    private UserRoomViewModel userRoomViewModel;

    private Button loginNavigationButton;

    private Button registerButton;

    private EditText emailEditText;

    private EditText usernameEditText;

    private EditText passwordEditText;

    private EditText confirmPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuthViewmodel = new ViewModelProvider(this)
                .get(FirebaseAuthenticationViewmodel.class);
        firebaseUserViewmodel = new ViewModelProvider(this)
                .get(FirebaseUserViewmodel.class);
        userRoomViewModel = new ViewModelProvider(this)
                .get(UserRoomViewModel.class);

        loginNavigationButton = findViewById(R.id.loginNavigationButton);
        registerButton = findViewById(R.id.registerButton);
        emailEditText = findViewById(R.id.emailEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
    }

    @Override
    protected void onStart() {
        super.onStart();
        StylerHelper.underlineButton(loginNavigationButton);
        registerButton.setOnClickListener(this::onRegister);
        loginNavigationButton.setOnClickListener(this::onNavigateToLogin);
    }

    private void onNavigateToLogin(View view) {
        finish();
    }

    private void onRegister(View view) {
        String email = String.valueOf(emailEditText.getText()).trim();
        String username = String.valueOf(usernameEditText.getText()).trim();
        String password = String.valueOf(passwordEditText.getText()).trim();
        String confirmPassword = String.valueOf(confirmPasswordEditText.getText()).trim();

        CustomError emailError = Validator.isEmailValid(email);
        CustomError usernameError = Validator.isUsernameValid(username);
        CustomError passwordError = Validator.isPasswordValid(password);
        CustomError confirmPasswordError = Validator.doPasswordsMatch(password, confirmPassword);

        boolean canSaveUser = true;

        // Validate User Input
        if (emailError.getHasError()) {
            emailEditText.setError(emailError.getMessage());
            canSaveUser = false;
        }
        if (usernameError.getHasError()) {
            usernameEditText.setError(usernameError.getMessage());
            canSaveUser = false;
        }
        if (passwordError.getHasError()) {
            passwordEditText.setError(passwordError.getMessage());
            canSaveUser = false;
        }
        if (confirmPasswordError.getHasError()) {
            confirmPasswordEditText.setError(confirmPasswordError.getMessage());
            canSaveUser = false;
        }
        if (!canSaveUser) return;

        User user = new User(username, email, password);

        firebaseAuthViewmodel.registerUser(user, firebaseUserViewmodel, userRoomViewModel);
        startActivity(new Intent(this, LoginActivity.class));
        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT)
                .show();
    }
}