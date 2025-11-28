package com.example.healthywallet.ui.metas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.healthywallet.R;
import com.example.healthywallet.controller.MetaControlador;
import com.example.healthywallet.database.entities.Meta;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PantallaAgregarAhorro extends Fragment {

    private TextView txtNombreMeta, txtResumen;
    private ProgressBar barra;
    private EditText inputCantidad;
    private FloatingActionButton btnGuardar;

    private MetaControlador controlador;
    private Meta metaActual;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.pantalla_agregar_ahorro, container, false);

        controlador = new MetaControlador(requireContext());

        txtNombreMeta = vista.findViewById(R.id.txtNombreMetaAhorro);
        txtResumen = vista.findViewById(R.id.txtResumenAhorro);
        barra = vista.findViewById(R.id.barraMetaAhorro);
        inputCantidad = vista.findViewById(R.id.inputCantidadAhorro);
        btnGuardar = vista.findViewById(R.id.btnGuardarAhorro);

        int metaId = getArguments().getInt("metaId", -1);
        if (metaId == -1) {
            Toast.makeText(requireContext(), "Error al cargar meta", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(vista).navigateUp();
            return vista;
        }

        cargarMeta(metaId);
        configurarBotonGuardar(vista);

        return vista;
    }

    private void cargarMeta(int id) {
        controlador.obtenerPorId(id, meta -> {

            requireActivity().runOnUiThread(() -> {

                if (meta == null) {
                    Toast.makeText(requireContext(), "Meta no encontrada", Toast.LENGTH_SHORT).show();
                    return;
                }

                metaActual = meta;

                txtNombreMeta.setText(meta.getNombre());

                txtResumen.setText(
                        String.format("%.2f € / %.2f €",
                                meta.getCantidadActual(), meta.getCantidadObjetivo())
                );

                double porcentaje = (meta.getCantidadActual() / meta.getCantidadObjetivo()) * 100;
                barra.setProgress((int) porcentaje);
            });
        });
    }

    private void configurarBotonGuardar(View vista) {

        btnGuardar.setOnClickListener(v -> {

            String cantidadStr = inputCantidad.getText().toString().trim();

            if (cantidadStr.isEmpty()) {
                Toast.makeText(requireContext(), "Introduce una cantidad", Toast.LENGTH_SHORT).show();
                return;
            }

            double cantidad;
            try {
                cantidad = Double.parseDouble(cantidadStr);
            } catch (Exception e) {
                Toast.makeText(requireContext(), "Formato inválido", Toast.LENGTH_SHORT).show();
                return;
            }

            if (cantidad <= 0) {
                Toast.makeText(requireContext(), "La cantidad debe ser mayor que 0", Toast.LENGTH_SHORT).show();
                return;
            }

            // sumamos al progreso actual
            double nuevoTotal = metaActual.getCantidadActual() + cantidad;
            metaActual.setCantidadActual(nuevoTotal);

            controlador.actualizar(metaActual, filas -> {
                requireActivity().runOnUiThread(() -> {

                    if (filas > 0) {
                        Toast.makeText(requireContext(), "Ahorro añadido", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(vista).navigateUp();
                    } else {
                        Toast.makeText(requireContext(), "Error al guardar", Toast.LENGTH_SHORT).show();
                    }

                });
            });
        });

    }
}
