package com.app.nailappointment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.app.nailappointment.firebase.viewmodel.FirebaseAuthenticationViewmodel;
import com.app.nailappointment.view.activites.DateActivity;
import com.app.nailappointment.view.activites.LoginActivity;
import com.app.nailappointment.view.activites.MyAppointmentsActivity;
import com.app.nailappointment.view.activites.SettingsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;

    private FirebaseAuthenticationViewmodel fireAuthViewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fireAuthViewmodel = new ViewModelProvider(this)
                .get(FirebaseAuthenticationViewmodel.class);


        fab = findViewById(R.id.floatingActionButton);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_menu_home);
        bottomNavigationSetup(bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (R.id.bottom_menu_home == id) {
                return true;
            }
            if (R.id.bottom_menu_login == id) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                return true;
            }
            if (R.id.bottom_menu_book == id) {
                startActivity(new Intent(getApplicationContext(), DateActivity.class));
                return true;
            }
            if (R.id.bottom_menu_settings == id) {
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                return true;
            }
            if (R.id.bottom_menu_logout == id) {
                fireAuthViewmodel.logoutUser();
                Toast.makeText(this, "You have signed out", Toast.LENGTH_LONG)
                        .show();
                return true;
            }
            return false;
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MyAppointmentsActivity.class));
            }
        });
    }

    private void bottomNavigationSetup(BottomNavigationView bottomNavigation) {
        Menu menu = bottomNavigation.getMenu();

        fireAuthViewmodel.getIsUserLoggedIn().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoggedIn) {
                menu.clear();
                if (isLoggedIn) {
                    bottomNavigation.inflateMenu(R.menu.bottom_login_menu);
                    fab.setVisibility(View.VISIBLE);
                } else {
                    bottomNavigation.inflateMenu(R.menu.bottom_logout_menu);
                    fab.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}