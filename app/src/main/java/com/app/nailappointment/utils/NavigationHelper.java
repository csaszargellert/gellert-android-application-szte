package com.app.nailappointment.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.app.nailappointment.MainActivity;
import com.app.nailappointment.R;
import com.app.nailappointment.firebase.viewmodel.FirebaseAuthenticationViewmodel;
import com.app.nailappointment.view.activites.DateActivity;
import com.app.nailappointment.view.activites.LoginActivity;
import com.app.nailappointment.view.appointments.MyAppointmentsActivity;
import com.app.nailappointment.view.activites.SettingsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NavigationHelper {

    private FloatingActionButton fab;

    private Activity mActivity;

    private FirebaseAuthenticationViewmodel fireAuthViewmodel;

    private NavBarActivities navBarActivity;

    private String currentActivityName;

    public NavigationHelper(Activity _activity, FirebaseAuthenticationViewmodel fireAuthViewmodel, NavBarActivities navBarActivity) {
        this.mActivity = _activity;
        this.fireAuthViewmodel = fireAuthViewmodel;
        this.navBarActivity = navBarActivity;
        this.currentActivityName = mActivity.getLocalClassName();
    }

    public void setupNavigationListener() {
        fab = mActivity.findViewById(R.id.floatingActionButton);
        BottomNavigationView bottomNavigationView = mActivity.findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(navBarActivity.getItemId());
        bottomNavigationSetup(bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (R.id.bottom_menu_home == id) {
                if (navBarActivity.getActivityName()
                        .contains(NavBarActivities.MAIN.getActivityName())
                ) {
                    return true;
                }

                mActivity.startActivity(
                        new Intent(mActivity.getApplicationContext(), MainActivity.class)
                );
                return true;
            }
            if (R.id.bottom_menu_login == id) {
                if (navBarActivity.getActivityName()
                        .contains(NavBarActivities.LOGIN.getActivityName())
                ) {
                    return true;
                }

                mActivity.startActivity(
                        new Intent(mActivity.getApplicationContext(), LoginActivity.class)
                );
                return true;
            }
            if (R.id.bottom_menu_date == id) {
                if (navBarActivity.getActivityName()
                        .contains(NavBarActivities.DATE.getActivityName())
                ) {
                    return true;
                }

                mActivity.startActivity(
                        new Intent(mActivity.getApplicationContext(), DateActivity.class)
                );
                return true;
            }
            if (R.id.bottom_menu_settings == id) {
                if (navBarActivity.getActivityName()
                        .contains(NavBarActivities.SETTINGS.getActivityName())
                ) {
                    return true;
                }

                mActivity.startActivity(
                        new Intent(mActivity.getApplicationContext(), SettingsActivity.class)
                );
                return true;
            }
            if (R.id.bottom_menu_logout == id) {
                fireAuthViewmodel.logoutUser();
                Toast.makeText(mActivity, "You have signed out", Toast.LENGTH_LONG)
                        .show();
                mActivity.startActivity(
                        new Intent(mActivity.getApplicationContext(), MainActivity.class)
                );
                return true;
            }

            return false;
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.startActivity(
                        new Intent(mActivity.getApplicationContext(), MyAppointmentsActivity.class)
                );
            }
        });
    }

    private void bottomNavigationSetup(BottomNavigationView bottomNavigation) {
        Menu menu = bottomNavigation.getMenu();

        fireAuthViewmodel.getIsUserLoggedIn()
                .observe((LifecycleOwner) mActivity, new Observer<Boolean>() {
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
