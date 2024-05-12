package com.app.nailappointment.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.nailappointment.MainActivity;
import com.app.nailappointment.R;
import com.app.nailappointment.firebase.viewmodel.FirebaseAuthenticationViewmodel;
import com.app.nailappointment.firebase.viewmodel.FirebaseUserViewmodel;
import com.app.nailappointment.room.viewmodel.UserRoomViewModel;
import com.app.nailappointment.utils.CustomError;
import com.app.nailappointment.utils.NavBarActivities;
import com.app.nailappointment.utils.NavigationHelper;
import com.app.nailappointment.utils.StylerHelper;
import com.app.nailappointment.utils.Validator;
import com.app.nailappointment.utils.model.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuthenticationViewmodel firebaseAuthViewmodel;

    private FirebaseUserViewmodel firebaseUserViewmodel;

    private UserRoomViewModel userRoomViewModel;

    private Button registerNavigationButton;

    private Button loginButton;

    private EditText emailEditText;

    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuthViewmodel = new ViewModelProvider(this)
                .get(FirebaseAuthenticationViewmodel.class);
        firebaseUserViewmodel = new ViewModelProvider(this)
                .get(FirebaseUserViewmodel.class);
        userRoomViewModel = new ViewModelProvider(this)
                .get(UserRoomViewModel.class);

        registerNavigationButton = findViewById(R.id.registerNavigationButton);
        loginButton = findViewById(R.id.loginButton);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        NavigationHelper navHelper = new NavigationHelper(this, firebaseAuthViewmodel, NavBarActivities.LOGIN);
        navHelper.setupNavigationListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        StylerHelper.underlineButton(registerNavigationButton);
        registerNavigationButton.setOnClickListener(this::onNavigateToRegister);
        loginButton.setOnClickListener(this::onLogin);
    }

    private void onNavigateToRegister(View view) {
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
    }

    private void onLogin(View view) {
        String email = String.valueOf(emailEditText.getText()).trim();
        String password = String.valueOf(passwordEditText.getText()).trim();

        CustomError emailError = Validator.isEmailValid(email);
        CustomError passwordError = Validator.isPasswordValid(password);

        boolean canLoginUser = true;

        if (emailError.getHasError()) {
            emailEditText.setError(emailError.getMessage());
            canLoginUser = false;
        }
        if (passwordError.getHasError()) {
            passwordEditText.setError(passwordError.getMessage());
            canLoginUser = false;
        }

        if (!canLoginUser) return;

        User user = new User(null, email, password);

        firebaseAuthViewmodel.loginUser(user, firebaseUserViewmodel, userRoomViewModel);

        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT)
                .show();
    }
}