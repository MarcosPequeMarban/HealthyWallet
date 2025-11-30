package com.example.healthywallet.ui.metas;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.healthywallet.R;
import com.example.healthywallet.controller.MetaControlador;
import com.example.healthywallet.database.entities.Meta;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class PantallaAgregarMeta extends Fragment {

    private EditText inputNombreMeta, inputCantidadObjetivo, inputCantidadActual, inputFechaObjetivo;
    private FloatingActionButton btnGuardar;
    private MetaControlador controlador;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.pantalla_agregar_meta, container, false);

        controlador = new MetaControlador(requireContext());

        // Referencias UI
        inputNombreMeta = vista.findViewById(R.id.inputNombreMeta);
        inputCantidadObjetivo = vista.findViewById(R.id.inputCantidadObjetivo);
        inputCantidadActual = vista.findViewById(R.id.inputCantidadActual);
        inputFechaObjetivo = vista.findViewById(R.id.inputFechaObjetivo);
        btnGuardar = vista.findViewById(R.id.btnGuardarMeta);

        configurarFecha();
        configurarBotonGuardar();

        return vista;
    }

    private void configurarFecha() {
        inputFechaObjetivo.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    requireContext(),
                    (DatePicker view, int y, int m, int d) ->
                            inputFechaObjetivo.setText(d + "/" + (m + 1) + "/" + y),
                    year, month, day
            );

            dialog.show();
        });
    }

    private void configurarBotonGuardar() {

        btnGuardar.setOnClickListener(v -> {

            String nombre = inputNombreMeta.getText().toString().trim();
            String objStr = inputCantidadObjetivo.getText().toString().trim();
            String actualStr = inputCantidadActual.getText().toString().trim();
            String fecha = inputFechaObjetivo.getText().toString().trim();

            if (nombre.isEmpty() || objStr.isEmpty() || fecha.isEmpty()) {
                Toast.makeText(requireContext(), "Completa todos los campos obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            double objetivo, actual;

            try {
                objetivo = Double.parseDouble(objStr);
                actual = actualStr.isEmpty() ? 0 : Double.parseDouble(actualStr);
            } catch (Exception e) {
                Toast.makeText(requireContext(), "Formato de cantidad inválido", Toast.LENGTH_SHORT).show();
                return;
            }

            if (objetivo <= 0) {
                Toast.makeText(requireContext(), "La meta debe tener una cantidad mayor a 0", Toast.LENGTH_SHORT).show();
                return;
            }

            Meta meta = new Meta(nombre, objetivo, actual, fecha, 0);

            controlador.insertar(meta, id -> {
                requireActivity().runOnUiThread(() -> {
                    if (id > 0) {
                        Toast.makeText(requireContext(), "Meta creada", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(v).navigateUp();
                    } else {
                        Toast.makeText(requireContext(), "Error al guardar", Toast.LENGTH_SHORT).show();
                    }
                });
            });

        });
    }
}
