package com.example.healthywallet.ui.presupuestos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.healthywallet.R;
import com.example.healthywallet.controller.PresupuestoControlador;
import com.example.healthywallet.database.entities.Presupuesto;

public class PantallaAgregarPresupuesto extends Fragment {

    private Spinner spinnerCategoria;
    private EditText inputLimite;
    private Button btnGuardar;

    private PresupuestoControlador controlador;

    public PantallaAgregarPresupuesto() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.pantalla_agregar_presupuesto, container, false);

        controlador = new PresupuestoControlador(requireContext());

        spinnerCategoria = vista.findViewById(R.id.spinnerCategoriaPresupuesto);
        inputLimite = vista.findViewById(R.id.inputLimite);
        btnGuardar = vista.findViewById(R.id.btnGuardarPresupuesto);

        configurarSpinner();
        configurarBotonGuardar(vista);

        return vista;
    }

    private void configurarSpinner() {
        String[] categorias = {"Comida", "Transporte", "Educación", "Ocio", "Hogar", "Otros"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                categorias
        );

        spinnerCategoria.setAdapter(adapter);
    }

    private void configurarBotonGuardar(View vista) {
        btnGuardar.setOnClickListener(v -> {

            // Validación básica
            String limiteStr = inputLimite.getText().toString().trim();
            if (limiteStr.isEmpty()) {
                Toast.makeText(requireContext(), "Introduce un límite válido", Toast.LENGTH_SHORT).show();
                return;
            }

            double limite;
            try {
                limite = Double.parseDouble(limiteStr);
            } catch (Exception e) {
                Toast.makeText(requireContext(), "Formato de cantidad incorrecto", Toast.LENGTH_SHORT).show();
                return;
            }

            String categoria = spinnerCategoria.getSelectedItem().toString();

            // Obtener ID del usuario logueado
            SharedPreferences prefs = requireContext()
                    .getSharedPreferences("session", Context.MODE_PRIVATE);
            int userId = prefs.getInt("usuarioId", -1);

            if (userId == -1) {
                Toast.makeText(requireContext(),
                        "Error: usuario no identificado", Toast.LENGTH_SHORT).show();
                return;
            }

            // Crear presupuesto con userId
            Presupuesto nuevo = new Presupuesto(categoria, limite, 0, userId);

            controlador.insertar(nuevo, id -> {
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(requireContext(),
                            "Presupuesto creado", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(vista).popBackStack();
                });
            });
        });
    }
}
