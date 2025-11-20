package com.example.healthywallet.ui.movimientos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fabAgregarMovimiento.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.fragmentoAgregarMovimiento)
        );

        cargarLista();

        return vista;
    }

    private void cargarLista() {

        movimientosControlador.obtenerTodos(movimientos -> {

            requireActivity().runOnUiThread(() -> {

                List<Movimiento> listaSegura =
                        (movimientos != null) ? movimientos : Collections.emptyList();

                adaptador = new AdaptadorMovimientos(requireContext(), listaSegura);
                recyclerView.setAdapter(adaptador);
            });
        });
    }
}
