package com.example.healthywallet.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;

import com.example.healthywallet.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtiene el NavHostFragment
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.contenedorFragments);

        if (navHostFragment == null) {
            throw new IllegalStateException("El contenedor de navegación no está disponible en el layout");
        }

        NavController navController = navHostFragment.getNavController();

        // Bottom Navigation
        bottomNav = findViewById(R.id.bottomNavigation);
        NavigationUI.setupWithNavController(bottomNav, navController);

        // ESCONDER O MOSTRAR NAV SEGÚN LA PANTALLA
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            int id = destination.getId();

            if (id == R.id.pantallaLogin || id == R.id.pantallaRegistroUsuario) {
                ocultarBottomNav();
            } else {
                mostrarBottomNav();
            }
        });
    }

    private void ocultarBottomNav() {
        if (bottomNav != null) bottomNav.setVisibility(View.GONE);
    }

    private void mostrarBottomNav() {
        if (bottomNav != null) bottomNav.setVisibility(View.VISIBLE);
    }
}
