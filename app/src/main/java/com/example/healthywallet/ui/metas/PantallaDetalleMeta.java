package com.example.healthywallet.ui.metas;

import android.content.Context;
import android.content.SharedPreferences;
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
import androidx.appcompat.app.AlertDialog;
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
    private int userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.pantalla_detalle_meta, container, false);

        controlador = new MetaControlador(requireContext());

        // Obtener usuario logueado
        SharedPreferences prefs = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        userId = prefs.getInt("usuarioId", -1);

        txtTitulo = vista.findViewById(R.id.txtTituloMeta);
        txtCantidades = vista.findViewById(R.id.txtCantidadesDetalle);
        txtPorcentaje = vista.findViewById(R.id.txtPorcentajeDetalle);
        txtFecha = vista.findViewById(R.id.txtFechaDetalle);
        barra = vista.findViewById(R.id.barraDetalleMeta);
        btnAgregarAhorro = vista.findViewById(R.id.btnAgregarAhorro);
        btnEliminar = vista.findViewById(R.id.btnEliminarMeta);

        // Obtener argumento
        Bundle args = getArguments();
        if (args == null || !args.containsKey("metaId")) {
            Toast.makeText(requireContext(), "Error al cargar meta", Toast.LENGTH_SHORT).show();
            return vista;
        }

        metaId = args.getInt("metaId");

        cargarMeta();

        btnAgregarAhorro.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("metaId", metaId);
            Navigation.findNavController(v).navigate(R.id.pantallaAgregarAhorro, bundle);
        });

        // CONFIRMAR antes de eliminar
        btnEliminar.setOnClickListener(v -> confirmarEliminar());

        return vista;
    }

    private void cargarMeta() {
        controlador.obtenerPorId(metaId, meta -> requireActivity().runOnUiThread(() -> {

            if (meta == null) {
                Toast.makeText(requireContext(), "Meta no encontrada", Toast.LENGTH_SHORT).show();
                return;
            }

            metaActual = meta;

            txtTitulo.setText(metaActual.getNombre());
            txtCantidades.setText(
                    String.format("%.2f € / %.2f €",
                            metaActual.getCantidadActual(),
                            metaActual.getCantidadObjetivo())
            );

            txtFecha.setText("Fecha objetivo: " + metaActual.getFechaObjetivo());

            double porcentaje = 0;
            if (metaActual.getCantidadObjetivo() > 0)
                porcentaje = (metaActual.getCantidadActual() / metaActual.getCantidadObjetivo()) * 100;

            if (porcentaje > 100) porcentaje = 100;

            txtPorcentaje.setText(String.format("%.0f%%", porcentaje));
            barra.setProgress((int) porcentaje);

            // =============================
            //   POPUP DE META COMPLETADA
            // =============================
            if (porcentaje >= 100) {

                new AlertDialog.Builder(requireContext())
                        .setTitle("🎉 ¡Meta completada!")
                        .setMessage("Has alcanzado tu objetivo de ahorro.")
                        .setPositiveButton("Aceptar", null)
                        .show();
            }

        }));
    }

    // ======================================
    //     CONFIRMACIÓN DE ELIMINAR META
    // ======================================
    private void confirmarEliminar() {

        if (metaActual == null) {
            Toast.makeText(requireContext(), "Meta no cargada", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(requireContext())
                .setTitle("Eliminar meta")
                .setMessage("¿Estás seguro de que quieres eliminar esta meta?")
                .setPositiveButton("Sí", (dialog, which) -> eliminarMeta())
                .setNegativeButton("No", null)
                .show();
    }

    private void eliminarMeta() {

        controlador.eliminar(metaActual, filas -> requireActivity().runOnUiThread(() -> {

            if (filas > 0) {
                Toast.makeText(requireContext(), "Meta eliminada", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(requireView()).navigateUp();
            } else {
                Toast.makeText(requireContext(), "Error al eliminar la meta", Toast.LENGTH_SHORT).show();
            }

        }));
    }
}
