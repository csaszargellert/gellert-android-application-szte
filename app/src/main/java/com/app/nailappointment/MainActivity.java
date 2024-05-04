package com.app.nailappointment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.app.nailappointment.viewmodel.AuthenticationViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private AuthenticationViewModel authViewModel;

    public NavController navController;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        authViewModel = new ViewModelProvider(this)
                .get(AuthenticationViewModel.class);
        fab = findViewById(R.id.floatingActionButton);

        NavHostFragment navhostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainer);
        assert navhostFragment != null;
        navController = navhostFragment.getNavController();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationSetup(bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.myAppointmentsFragment);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.bottom_menu_home) {
            navController.navigate(R.id.homeFragment);
            return true;
        }
        if (id == R.id.bottom_menu_login) {
            navController.navigate(R.id.loginFragment);
            return true;
        }
        if (id == R.id.bottom_menu_book) {
            navController.navigate(R.id.dateFragment);
            return true;
        }
        if (id == R.id.bottom_menu_settings) {
            navController.navigate(R.id.settingsFragment);
            return true;
        }
        if (id == R.id.bottom_menu_logout) {
            authViewModel.logout();
            navController.navigate(R.id.homeFragment);
            Toast.makeText(this, "You have signed out", Toast.LENGTH_LONG)
                    .show();
            return true;
        }

        return false;
    }

    private void bottomNavigationSetup(BottomNavigationView bottomNavigation) {
        Menu menu = bottomNavigation.getMenu();

        authViewModel
                .getIsUserLoggedIn()
                .observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean userLoggedIn) {
                menu.clear();
                if(userLoggedIn) {
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