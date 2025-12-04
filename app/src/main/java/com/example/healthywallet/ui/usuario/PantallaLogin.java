package com.example.healthywallet.ui.usuario;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.healthywallet.R;
import com.example.healthywallet.controller.UsuarioControlador;
import com.example.healthywallet.database.entities.Usuario;

public class PantallaLogin extends Fragment {

    private EditText etEmail, etPass;
    private Button btnLogin;
    private TextView tvIrRegistro;
    private UsuarioControlador controlador;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pantalla_login, container, false);

        etEmail = v.findViewById(R.id.etEmailLogin);
        etPass = v.findViewById(R.id.etPasswordLogin);
        btnLogin = v.findViewById(R.id.btnLogin);
        tvIrRegistro = v.findViewById(R.id.tvIrRegistro);

        controlador = new UsuarioControlador(requireContext());

        // SIEMPRE MOSTRAR LOGIN

        btnLogin.setOnClickListener(view -> login());

        tvIrRegistro.setOnClickListener(view ->
                NavHostFragment.findNavController(PantallaLogin.this)
                        .navigate(R.id.action_pantallaLogin_to_pantallaRegistroUsuario)
        );

        return v;
    }

    private void login() {
        String email = etEmail.getText().toString().trim();
        String pass = etPass.getText().toString().trim();

        // VALIDACIONES
        if (email.isEmpty() || pass.isEmpty()) {
            mostrar("Rellena todos los campos");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mostrar("El correo no es válido");
            return;
        }

        if (pass.length() < 6) {
            mostrar("La contraseña debe tener mínimo 6 caracteres");
            return;
        }

        // Loading visual
        mostrarCargando(true);

        controlador.login(email, pass, usuario -> {

            requireActivity().runOnUiThread(() -> {
                mostrarCargando(false);

                if (usuario != null) {
                    guardarSesion(usuario);
                    irAInicio();
                } else {
                    mostrar("Credenciales incorrectas");
                }
            });

        });
    }

    private void guardarSesion(Usuario usuario) {
        SharedPreferences prefs = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        prefs.edit()
                .putBoolean("logueado", true)
                .putInt("usuarioId", usuario.getId())
                .apply();
    }

    private void irAInicio() {
        NavHostFragment.findNavController(this)
                .navigate(R.id.nav_inicio);
    }

    private void mostrar(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void mostrarCargando(boolean cargando) {
        if (cargando) {
            btnLogin.setEnabled(false);
            btnLogin.setText("Cargando...");
        } else {
            btnLogin.setEnabled(true);
            btnLogin.setText("ENTRAR");
        }
    }
}
