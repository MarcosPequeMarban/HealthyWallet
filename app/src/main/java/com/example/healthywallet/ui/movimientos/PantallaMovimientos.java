package com.example.healthywallet.ui.movimientos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
