package com.app.nailappointment.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.nailappointment.R;
import com.app.nailappointment.dto.UserDTO;
import com.app.nailappointment.utils.CustomError;
import com.app.nailappointment.utils.Validator;
import com.app.nailappointment.viewmodel.AuthenticationViewModel;
import com.app.nailappointment.viewmodel.SettingsViewModel;
import com.app.nailappointment.viewmodel.UserViewModel;

public class SettingsFragment extends Fragment {

    private AuthenticationViewModel authViewModel;

    private UserViewModel userViewModel;

    private SettingsViewModel settingsViewModel;

    private Button editButton;

    private Button cancelButton;

    private EditText emailEditText;

    private EditText usernameEditText;

    private Button contactButton;

    public SettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        this.authViewModel = new ViewModelProvider(requireActivity())
//                .get(AuthenticationViewModel.class);
//        this.userViewModel = new ViewModelProvider(requireActivity())
//                .get(UserViewModel.class);
        this.userViewModel = new ViewModelProvider(requireActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getActivity().getApplication())).get(UserViewModel.class);
        this.settingsViewModel = new ViewModelProvider(requireActivity())
                .get(SettingsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        settingsViewModel.setIsEditModeOn(false);
        editButton = view.findViewById(R.id.editButton);
        emailEditText = view.findViewById(R.id.emailEditText);
        usernameEditText = view.findViewById(R.id.usernameEditText);
        cancelButton = view.findViewById(R.id.cancelButton);
        contactButton = view.findViewById(R.id.contactButton);
        userViewModel.findById(userViewModel.getUserId().getValue());
    }

    @Override
    public void onStart() {
        super.onStart();
        editButton.setOnClickListener(this::onSave);
        cancelButton.setOnClickListener(this::onCancel);
        contactButton.setOnClickListener(this::onContact);

        editModeChangeListener();
        userChangeListener();
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
        UserDTO user = userViewModel.getUser().getValue();
        emailEditText.setText(user.getEmail());
        usernameEditText.setText(user.getUsername());
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
                UserDTO currentUser = userViewModel.getUser().getValue();

                assert currentUser != null;

                if (!currentUser.getEmail().equals(changedEmail)) {
                    currentUser.setEmail(changedEmail);
                    authViewModel.updateEmail(changedEmail);
                }

                currentUser.setUsername(changedUsername);
                userViewModel.updateUser(currentUser);
            }
        }

        settingsViewModel.setIsEditModeOn(!settingsViewModel.getIsEditModeOn().getValue());
    }

    private void editModeChangeListener() {
        settingsViewModel
                .getIsEditModeOn()
                .observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean isEditModeOn) {
                        if (isEditModeOn) {
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

    private void userChangeListener() {
        userViewModel
                .getUser()
                .observe(this, new Observer<UserDTO>() {
                    @Override
                    public void onChanged(UserDTO userDTO) {
                        if (userDTO != null) {
                            emailEditText.setText(userDTO.getEmail());
                            usernameEditText.setText(userDTO.getUsername());
                        }
                    }
                });
    }
}