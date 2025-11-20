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

import java.util.List;

public class PantallaMovimientos extends Fragment {

    private RecyclerView recycler;
    private FloatingActionButton fabAgregar;
    private MovimientosControlador controlador;

    public PantallaMovimientos() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.pantalla_movimientos, container, false);

        controlador = new MovimientosControlador(requireContext());

        recycler = vista.findViewById(R.id.recyclerMovimientos);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));

        fabAgregar = vista.findViewById(R.id.fabAgregarMovimiento);

        fabAgregar.setOnClickListener(view ->
                Navigation.findNavController(view)
                        .navigate(R.id.fragmentoAgregarMovimiento)
        );

        cargarMovimientos();

        return vista;
    }

    private void cargarMovimientos() {
        List<Movimiento> lista = controlador.obtenerTodos();

        AdaptadorMovimientos adaptador = new AdaptadorMovimientos(requireContext(), lista);
        recycler.setAdapter(adaptador);
    }
}
