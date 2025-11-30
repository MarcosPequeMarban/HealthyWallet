package com.example.healthywallet.ui.usuario;

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

public class PantallaRegistroUsuario extends Fragment {

    private EditText etNombre, etEmail, etPass, etPass2;
    private Button btnRegistrar;
    private TextView tvIrLogin;
    private UsuarioControlador controlador;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pantalla_registro, container, false);

        etNombre = v.findViewById(R.id.etNombreRegistro);
        etEmail = v.findViewById(R.id.etEmailRegistro);
        etPass = v.findViewById(R.id.etPassRegistro);
        etPass2 = v.findViewById(R.id.etPassConfirmarRegistro);
        btnRegistrar = v.findViewById(R.id.btnRegistrar);
        tvIrLogin = v.findViewById(R.id.tvIrLogin);

        controlador = new UsuarioControlador(requireContext());

        btnRegistrar.setOnClickListener(view -> registrarUsuario());

        tvIrLogin.setOnClickListener(view ->
                NavHostFragment.findNavController(PantallaRegistroUsuario.this)
                        .navigate(R.id.action_pantallaRegistroUsuario_to_pantallaLogin)
        );

        return v;
    }

    private void registrarUsuario() {
        String nombre = etNombre.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String pass = etPass.getText().toString().trim();
        String pass2 = etPass2.getText().toString().trim();

        // VALIDACIONES
        if (nombre.isEmpty() || email.isEmpty() || pass.isEmpty() || pass2.isEmpty()) {
            mostrar("Rellena todos los campos");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mostrar("Correo no válido");
            return;
        }

        if (pass.length() < 6) {
            mostrar("La contraseña debe tener al menos 6 caracteres");
            return;
        }

        if (!pass.equals(pass2)) {
            mostrar("Las contraseñas no coinciden");
            return;
        }

        Usuario nuevo = new Usuario();
        nuevo.setNombre(nombre);
        nuevo.setEmail(email);
        nuevo.setPassword(pass);

        btnRegistrar.setEnabled(false);
        btnRegistrar.setText("Creando cuenta...");

        controlador.registrar(nuevo, resultado -> {
            requireActivity().runOnUiThread(() -> {

                btnRegistrar.setEnabled(true);
                btnRegistrar.setText("REGISTRARSE");

                if (!resultado) {
                    mostrar("El correo ya está registrado");
                    return;
                }

                mostrar("Cuenta creada correctamente. Inicia sesión.");

                // IR A LOGIN
                NavHostFragment.findNavController(PantallaRegistroUsuario.this)
                        .navigate(R.id.action_pantallaRegistroUsuario_to_pantallaLogin);
            });
        });
    }

    private void mostrar(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
