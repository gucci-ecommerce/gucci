package com.gucci.Dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.github.kwasow.bottomnavigationcircles.BottomNavigationCircles;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gucci.R;

public class HomeActivity extends AppCompatActivity {
    public static String full_name = "";
    public static String phone = "";
    public static String email = "";
    public static NavHostFragment navHostFragment;
    public static NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(bottomNavigationView, navController);
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            full_name = extras.getString("full_name");
            phone = extras.getString("phone");
            email = extras.getString("email");
        }
    }
}