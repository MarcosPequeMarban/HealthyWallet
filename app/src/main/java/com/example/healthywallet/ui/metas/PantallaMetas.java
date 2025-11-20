package com.example.healthywallet.ui.metas;

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
import com.example.healthywallet.adapters.AdaptadorMetas;
import com.example.healthywallet.controller.MetaControlador;
import com.example.healthywallet.database.entities.Meta;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PantallaMetas extends Fragment {

    private RecyclerView recycler;
    private AdaptadorMetas adaptador;
    private FloatingActionButton fabAgregar;

    private TextView txtResumenTitulo;
    private TextView txtResumenCantidad;

    private MetaControlador controlador;
    private final List<Meta> listaMetas = new ArrayList<>();

    public PantallaMetas() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.pantalla_metas, container, false);

        // Controlador
        controlador = new MetaControlador(requireContext());

        // UI
        recycler = vista.findViewById(R.id.recyclerMetas);
        fabAgregar = vista.findViewById(R.id.fabAgregarMeta);

        txtResumenTitulo = vista.findViewById(R.id.txtResumenTitulo);
        txtResumenCantidad = vista.findViewById(R.id.txtResumenCantidad);

        // Recycler configurado
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adaptador = new AdaptadorMetas(getContext(), listaMetas);
        recycler.setAdapter(adaptador);

        // FAB → Ir a agregar meta
        fabAgregar.setOnClickListener(v ->
                Navigation.findNavController(v)
                        .navigate(R.id.nav_metas)
        );

        cargarMetas();

        return vista;
    }

    @Override
    public void onResume() {
        super.onResume();
        cargarMetas();
    }

    private void cargarMetas() {
        controlador.obtenerTodas(metas -> {

            requireActivity().runOnUiThread(() -> {

                listaMetas.clear();
                if (metas != null) listaMetas.addAll(metas);
                adaptador.notifyDataSetChanged();

                actualizarResumen();
            });
        });
    }

    private void actualizarResumen() {

        double totalObjetivo = 0;
        double totalActual = 0;

        for (Meta m : listaMetas) {
            totalObjetivo += m.getCantidadObjetivo();
            totalActual += m.getCantidadActual();
        }

        txtResumenCantidad.setText(
                String.format("%.2f € ahorrados de %.2f €", totalActual, totalObjetivo)
        );
    }
}
