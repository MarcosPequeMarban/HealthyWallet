package com.example.healthywallet.ui.movimientos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthywallet.R;
import com.example.healthywallet.adapters.AdaptadorMovimientos;
import com.example.healthywallet.controller.MovimientosControlador;
import com.example.healthywallet.database.entities.Movimiento;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;

public class PantallaMovimientos extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton fabAgregarMovimiento;
    private TextView txtBalanceMensual;

    private MovimientosControlador movimientosControlador;
    private AdaptadorMovimientos adaptador;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.pantalla_movimientos, container, false);

        movimientosControlador = new MovimientosControlador(requireContext());

        recyclerView = vista.findViewById(R.id.recyclerMovimientos);
        fabAgregarMovimiento = vista.findViewById(R.id.fabAgregarMovimiento);
        txtBalanceMensual = vista.findViewById(R.id.txtBalanceCantidad);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fabAgregarMovimiento.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.fragmentoAgregarMovimiento)
        );

        cargarLista();
        return vista;
    }

    @Override
    public void onResume() {
        super.onResume();
        cargarLista();
    }

    private void cargarLista() {

        movimientosControlador.obtenerTodos(lista -> {

            requireActivity().runOnUiThread(() -> {

                List<Movimiento> movimientos =
                        (lista != null) ? lista : Collections.emptyList();

                if (adaptador == null) {
                    adaptador = new AdaptadorMovimientos(requireContext(), movimientos);
                    recyclerView.setAdapter(adaptador);

                    adaptador.setOnMovimientoLongClickListener(mov -> {

                        new AlertDialog.Builder(requireContext())
                                .setTitle("Eliminar movimiento")
                                .setMessage("¿Seguro que deseas eliminar este movimiento?")
                                .setPositiveButton("Eliminar", (dialog, which) -> {

                                    movimientosControlador.eliminar(mov, filas ->
                                            requireActivity().runOnUiThread(() -> {

                                                if (filas > 0) {
                                                    Toast.makeText(requireContext(),
                                                            "Movimiento eliminado", Toast.LENGTH_SHORT).show();
                                                    cargarLista();
                                                } else {
                                                    Toast.makeText(requireContext(),
                                                            "Error al eliminar el movimiento", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                    );

                                })
                                .setNegativeButton("Cancelar", null)
                                .show();
                    });


                } else {
                    adaptador.actualizarLista(movimientos);
                }

                actualizarBalance(movimientos);
            });
        });
    }

    private void actualizarBalance(List<Movimiento> lista) {

        double total = 0;

        for (Movimiento mov : lista) {
            if ("Ingreso".equals(mov.getTipo())) {
                total += mov.getCantidad();
            } else {
                total -= mov.getCantidad();
            }
        }

        txtBalanceMensual.setText(String.format("%.2f €", total));
    }
}
