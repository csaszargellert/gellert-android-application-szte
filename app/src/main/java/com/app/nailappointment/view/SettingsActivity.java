package com.app.nailappointment.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.app.nailappointment.R;
import com.app.nailappointment.firebase.viewmodel.FirebaseAuthenticationViewmodel;
import com.app.nailappointment.firebase.viewmodel.FirebaseUserViewmodel;
import com.app.nailappointment.room.viewmodel.UserRoomViewModel;
import com.app.nailappointment.utils.CustomError;
import com.app.nailappointment.utils.NavBarActivities;
import com.app.nailappointment.utils.NavigationHelper;
import com.app.nailappointment.utils.Validator;
import com.app.nailappointment.utils.model.User;
import com.app.nailappointment.utils.viewmodel.SettingsViewModel;

public class SettingsActivity extends AppCompatActivity {

    private FirebaseUserViewmodel firebaseUserViewmodel;

    private FirebaseAuthenticationViewmodel firebaseAuthViewModel;

    private UserRoomViewModel userRoomViewModel;

    private SettingsViewModel settingsViewModel;

    private Button editButton;

    private Button cancelButton;

    private EditText emailEditText;

    private EditText usernameEditText;

    private Button contactButton;

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        firebaseAuthViewModel = new ViewModelProvider(this)
                .get(FirebaseAuthenticationViewmodel.class);
        userRoomViewModel = new ViewModelProvider(this)
                .get(UserRoomViewModel.class);
        firebaseUserViewmodel = new ViewModelProvider(this)
                .get(FirebaseUserViewmodel.class);
        settingsViewModel = new ViewModelProvider(this)
                .get(SettingsViewModel.class);

        settingsViewModel.setIsEditModeOn(false);
        editButton = findViewById(R.id.editButton);
        emailEditText = findViewById(R.id.emailEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        cancelButton = findViewById(R.id.cancelButton);
        contactButton = findViewById(R.id.contactButton);

        NavigationHelper navHelper = new NavigationHelper(this, firebaseAuthViewModel, NavBarActivities.SETTINGS);
        navHelper.setupNavigationListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        userDataListener();
        editingModeListener();
        contactButton.setOnClickListener(this::onContact);
        editButton.setOnClickListener(this::onSave);
        cancelButton.setOnClickListener(this::onCancel);
    }

    private void onContact(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"nail.application@test.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Support Nail Application");
        startActivity(Intent.createChooser(emailIntent, "Contact with email..."));
    }

    private void onCancel(View view) {
        settingsViewModel.setIsEditModeOn(false);
    }

    private void onSave(View view) {
        if (settingsViewModel.getIsEditModeOn().getValue()) {
            String changedEmail = String.valueOf(emailEditText.getText()).trim();
            String changedUsername = String.valueOf(usernameEditText.getText()).trim();

            CustomError changedEmailError = Validator.isEmailValid(changedEmail);
            CustomError changedUsernameError = Validator.isUsernameValid(changedUsername);

            boolean canSaveModifiedData = true;

            if (changedEmailError.getHasError()) {
                emailEditText.setError(changedEmailError.getMessage());
                canSaveModifiedData = false;
            }
            if (changedUsernameError.getHasError()) {
                usernameEditText.setError(changedUsernameError.getMessage());
                canSaveModifiedData = false;
            }

            if (canSaveModifiedData) {
                if (!currentUser.getUsername().equals(changedUsername)) {
                    currentUser.setUsername(changedUsername);
                }

                if (!currentUser.getEmail().equals(changedEmail)) {
                    currentUser.setEmail(changedEmail);
                    firebaseAuthViewModel.updateUser(currentUser, firebaseUserViewmodel, userRoomViewModel);
                } else {
                    firebaseUserViewmodel.updateUser(currentUser, userRoomViewModel);
                }
            }
        }

        settingsViewModel.setIsEditModeOn(!settingsViewModel.getIsEditModeOn().getValue());
    }

    private void editingModeListener() {
        settingsViewModel.getIsEditModeOn()
                .observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean isEditing) {
                        if (isEditing) {
                            editButton.setText(R.string.save);
                            emailEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                            usernameEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                            cancelButton.setVisibility(View.VISIBLE);
                        } else {
                            editButton.setText(R.string.edit);
                            emailEditText.setInputType(InputType.TYPE_NULL);
                            usernameEditText.setInputType(InputType.TYPE_NULL);
                            cancelButton.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void userDataListener() {
        userRoomViewModel
                .getCurrentUser(firebaseAuthViewModel.getUserId().getValue())
                .observe(this, new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        if (user == null) {
                            firebaseUserViewmodel.findUserById(
                                    firebaseAuthViewModel.getUserId().getValue(),
                                    userRoomViewModel);
                        } else {
                            currentUser = user;
                            usernameEditText.setText(user.getUsername());
                            emailEditText.setText(user.getEmail());
                        }
                    }
                });
    }
}