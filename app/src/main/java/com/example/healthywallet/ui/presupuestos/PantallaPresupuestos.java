package com.example.healthywallet.ui.presupuestos;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.healthywallet.controller.MovimientosControlador;
import com.example.healthywallet.controller.PresupuestoControlador;
import com.example.healthywallet.database.entities.Movimiento;
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
    private MovimientosControlador movControlador;

    private int userId;

    public PantallaPresupuestos() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.pantalla_presupuestos, container, false);

        controlador = new PresupuestoControlador(requireContext());
        movControlador = new MovimientosControlador(requireContext());

        SharedPreferences prefs = requireContext()
                .getSharedPreferences("session", Context.MODE_PRIVATE);
        userId = prefs.getInt("usuarioId", -1);

        recycler = vista.findViewById(R.id.recyclerPresupuestos);
        fabAgregar = vista.findViewById(R.id.fabAgregarPresupuesto);
        txtResumen = vista.findViewById(R.id.txtResumenPresupuesto);
        barraProgreso = vista.findViewById(R.id.progresoGeneralPresupuesto);

        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adaptador = new AdaptadorPresupuestos(getContext(), lista);
        recycler.setAdapter(adaptador);

        fabAgregar.setOnClickListener(v ->
                Navigation.findNavController(v)
                        .navigate(R.id.action_presupuestos_to_agregarPresupuesto)
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

        controlador.obtenerTodos(listaPresupuestos -> {

            requireActivity().runOnUiThread(() -> {

                lista.clear();

                if (listaPresupuestos != null)
                    lista.addAll(listaPresupuestos);

                recalcularGastosPresupuestos();
            });
        });
    }

    private void recalcularGastosPresupuestos() {

        movControlador.obtenerTodos(movimientos -> {

            requireActivity().runOnUiThread(() -> {

                for (Presupuesto p : lista) {

                    double suma = 0;

                    for (Movimiento m : movimientos) {

                        if (m.getUserId() == p.getUserId() &&         // ← FILTRO IMPORTANTE
                                m.getCategoria().equalsIgnoreCase(p.getCategoria()) &&
                                m.getTipo().equalsIgnoreCase("Gasto")) {

                            suma += m.getCantidad();
                        }
                    }

                    p.setGastoActual(suma);
                    controlador.actualizar(p, filas -> {});
                }

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

        barraProgreso.setMax((int) totalLimite);
        barraProgreso.setProgress((int) totalGasto);
    }
}
