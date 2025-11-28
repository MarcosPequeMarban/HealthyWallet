package com.example.healthywallet.ui.metas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class PantallaDetalleMeta extends Fragment {

    private TextView txtTitulo, txtCantidades, txtPorcentaje, txtFecha;
    private ProgressBar barra;
    private Button btnAgregarAhorro, btnEliminar;

    private MetaControlador controlador;
    private Meta metaActual;
    private int metaId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.pantalla_detalle_meta, container, false);

        controlador = new MetaControlador(requireContext());

        txtTitulo = vista.findViewById(R.id.txtTituloMeta);
        txtCantidades = vista.findViewById(R.id.txtCantidadesDetalle);
        txtPorcentaje = vista.findViewById(R.id.txtPorcentajeDetalle);
        txtFecha = vista.findViewById(R.id.txtFechaDetalle);
        barra = vista.findViewById(R.id.barraDetalleMeta);
        btnAgregarAhorro = vista.findViewById(R.id.btnAgregarAhorro);
        btnEliminar = vista.findViewById(R.id.btnEliminarMeta);

        // Recuperar ID de la meta
        metaId = getArguments().getInt("metaId", -1);

        if (metaId == -1) {
            Toast.makeText(requireContext(), "Error al cargar meta", Toast.LENGTH_SHORT).show();
            return vista;
        }

        cargarMeta();

        btnAgregarAhorro.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("metaId", metaId);

            Navigation.findNavController(v)
                    .navigate(R.id.pantallaAgregarAhorro, bundle);
        });

        btnEliminar.setOnClickListener(v -> eliminarMeta());

        return vista;
    }

    private void cargarMeta() {
        controlador.obtenerPorId(metaId, meta -> requireActivity().runOnUiThread(() -> {
            metaActual = meta;

            if (metaActual == null) return;

            txtTitulo.setText(metaActual.getNombre());
            txtCantidades.setText(metaActual.getCantidadActual() + " € / " +
                    metaActual.getCantidadObjetivo() + " €");
            txtFecha.setText("Fecha objetivo: " + metaActual.getFechaObjetivo());

            double porcentaje = (metaActual.getCantidadActual() /
                    metaActual.getCantidadObjetivo()) * 100;

            if (porcentaje > 100) porcentaje = 100;

            txtPorcentaje.setText(String.format("%.0f%%", porcentaje));
            barra.setProgress((int) porcentaje);
        }));
    }

    private void eliminarMeta() {

        if (metaActual == null) {
            Toast.makeText(requireContext(), "Error: meta no cargada", Toast.LENGTH_SHORT).show();
            return;
        }

        controlador.eliminar(metaActual, filas -> requireActivity().runOnUiThread(() -> {

            boolean ok = filas > 0;

            if (ok) {
                Toast.makeText(requireContext(), "Meta eliminada", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(requireView()).navigateUp();
            } else {
                Toast.makeText(requireContext(), "Error al eliminar", Toast.LENGTH_SHORT).show();
            }

        }));
    }
}
