package com.example.healthywallet.ui.movimientos;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.healthywallet.R;
import com.example.healthywallet.controller.MovimientosControlador;
import com.example.healthywallet.database.entities.Movimiento;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class PantallaAgregarMovimiento extends Fragment {

    private EditText inputCantidad, inputFecha, inputDescripcion;
    private Spinner spinnerTipo, spinnerCategoria;
    private FloatingActionButton btnGuardar;

    private MovimientosControlador controlador;

    private final String[] categoriasGasto = {
            "Comida", "Transporte", "Educación", "Ocio", "Hogar", "Salud", "Otros"
    };

    private final String[] categoriasIngreso = {
            "Trabajo", "Beca", "Regalo", "Venta", "Premio", "Extra", "Otros ingresos"
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.pantalla_agregar_movimiento, container, false);

        controlador = new MovimientosControlador(requireContext());

        // Referencias UI
        inputCantidad = vista.findViewById(R.id.inputCantidad);
        inputFecha = vista.findViewById(R.id.inputFecha);
        inputDescripcion = vista.findViewById(R.id.inputDescripcion);
        spinnerTipo = vista.findViewById(R.id.spinnerTipo);
        spinnerCategoria = vista.findViewById(R.id.spinnerCategoria);
        btnGuardar = vista.findViewById(R.id.btnGuardarMovimiento);

        configurarSpinners();
        configurarCambioCategorias();
        configurarSelectorFecha();
        configurarBotonGuardar();

        return vista;
    }

    private void configurarSpinners() {

        String[] tipos = {"Ingreso", "Gasto"};

        ArrayAdapter<String> adapterTipo = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                tipos
        );

        spinnerTipo.setAdapter(adapterTipo);

        // Por defecto → categorías de gasto
        ArrayAdapter<String> adapterCat = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                categoriasGasto
        );
        spinnerCategoria.setAdapter(adapterCat);
    }

    private void configurarCambioCategorias() {

        spinnerTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String tipo = spinnerTipo.getSelectedItem().toString();

                ArrayAdapter<String> adapter;

                if (tipo.equals("Ingreso")) {
                    adapter = new ArrayAdapter<>(
                            requireContext(),
                            android.R.layout.simple_spinner_dropdown_item,
                            categoriasIngreso
                    );
                } else {
                    adapter = new ArrayAdapter<>(
                            requireContext(),
                            android.R.layout.simple_spinner_dropdown_item,
                            categoriasGasto
                    );
                }

                spinnerCategoria.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void configurarSelectorFecha() {
        inputFecha.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();
            int año = calendar.get(Calendar.YEAR);
            int mes = calendar.get(Calendar.MONTH);
            int dia = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    requireContext(),
                    (DatePicker view, int year, int month, int dayOfMonth) -> {
                        String fecha = String.format("%02d/%02d/%04d",
                                dayOfMonth, month + 1, year);
                        inputFecha.setText(fecha);
                    },
                    año, mes, dia
            );

            dialog.show();
        });
    }

    private void configurarBotonGuardar() {

        btnGuardar.setOnClickListener(v -> {

            String cantidadStr = inputCantidad.getText().toString().trim();
            String fecha = inputFecha.getText().toString().trim();
            String descripcion = inputDescripcion.getText().toString().trim();

            if (cantidadStr.isEmpty() || fecha.isEmpty()) {
                Toast.makeText(requireContext(),
                        "Rellena cantidad y fecha",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            double cantidad;
            try {
                cantidad = Double.parseDouble(cantidadStr);
            } catch (Exception e) {
                Toast.makeText(requireContext(),
                        "Formato de cantidad inválido",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            String tipo = spinnerTipo.getSelectedItem().toString();
            String categoria = spinnerCategoria.getSelectedItem().toString();

            // Obtener ID del usuario logueado
            SharedPreferences prefs = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE);
            int userId = prefs.getInt("usuarioId", -1);

            if (userId == -1) {
                Toast.makeText(requireContext(),
                        "Error: usuario no encontrado",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            Movimiento mov = new Movimiento(
                    tipo,
                    categoria,
                    cantidad,
                    fecha,
                    descripcion,
                    userId
            );

            controlador.insertar(mov, id -> {

                requireActivity().runOnUiThread(() -> {

                    if (id > 0) {
                        Toast.makeText(requireContext(),
                                "Movimiento guardado",
                                Toast.LENGTH_SHORT).show();

                        Navigation.findNavController(v)
                                .navigate(R.id.nav_movimientos);

                    } else {
                        Toast.makeText(requireContext(),
                                "Error al guardar",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            });

        });
    }
}
