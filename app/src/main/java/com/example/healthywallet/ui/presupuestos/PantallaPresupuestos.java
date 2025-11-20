package com.example.healthywallet.ui.presupuestos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthywallet.R;
import com.example.healthywallet.adapters.AdaptadorPresupuestos;
import com.example.healthywallet.controller.PresupuestoControlador;
import com.example.healthywallet.database.entities.Presupuesto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PantallaPresupuestos extends Fragment {

    private RecyclerView recycler;
    private AdaptadorPresupuestos adaptador;
    private FloatingActionButton fabAgregar;
    private TextView txtResumen;
    private ProgressBar barraProgreso;

    private final List<Presupuesto> lista = new ArrayList<>();

    private PresupuestoControlador controlador;

    public PantallaPresupuestos() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.pantalla_presupuestos, container, false);

        // Controlador
        controlador = new PresupuestoControlador(requireContext());

        // UI
        recycler = vista.findViewById(R.id.recyclerPresupuestos);
        fabAgregar = vista.findViewById(R.id.fabAgregarPresupuesto);
        txtResumen = vista.findViewById(R.id.txtResumenPresupuesto);
        barraProgreso = vista.findViewById(R.id.progresoGeneralPresupuesto);

        // Recycler
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adaptador = new AdaptadorPresupuestos(getContext(), lista);
        recycler.setAdapter(adaptador);

        // FAB → Agregar presupuesto
        fabAgregar.setOnClickListener(v ->
                Navigation.findNavController(v)
                        .navigate(R.id.nav_presupuestos)
        );

        cargarPresupuestos();
        return vista;
    }

    @Override
    public void onResume() {
        super.onResume();
        cargarPresupuestos();
    }

    private void cargarPresupuestos() {

        controlador.obtenerTodos(presupuestos -> {

            requireActivity().runOnUiThread(() -> {

                lista.clear();
                if (presupuestos != null) lista.addAll(presupuestos);

                adaptador.notifyDataSetChanged();

                actualizarResumen();
            });
        });
    }

    private void actualizarResumen() {

        double totalLimite = 0;
        double totalGasto = 0;

        for (Presupuesto p : lista) {
            totalLimite += p.getLimite();
            totalGasto += p.getGastoActual();
        }

        txtResumen.setText(String.format("%.2f € gastados de %.2f €", totalGasto, totalLimite));

        // Configurar barra
        barraProgreso.setMax((int) totalLimite);
        barraProgreso.setProgress((int) totalGasto);
    }
}
