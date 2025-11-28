package com.example.healthywallet.ui.metas;

import android.app.AlertDialog;
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

        controlador = new MetaControlador(requireContext());

        recycler = vista.findViewById(R.id.recyclerMetas);
        fabAgregar = vista.findViewById(R.id.fabAgregarMeta);
        txtResumenCantidad = vista.findViewById(R.id.txtResumenCantidad);

        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adaptador = new AdaptadorMetas(getContext(), listaMetas);
        recycler.setAdapter(adaptador);

        // CLICK: abrir detalle de meta
        adaptador.setOnMetaClickListener(meta -> {
            Bundle b = new Bundle();
            b.putInt("metaId", meta.getId());
            Navigation.findNavController(requireView())
                    .navigate(R.id.pantallaDetalleMeta, b);
        });

        // META COMPLETADA → popup
        adaptador.setOnMetaCompletadaListener(meta -> mostrarPopupMetaCompleta(meta));

        // FAB → agregar meta
        fabAgregar.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.pantallaAgregarMeta));

        cargarMetas();

        return vista;
    }

    @Override
    public void onResume() {
        super.onResume();
        cargarMetas();
    }

    private void cargarMetas() {
        controlador.obtenerTodas(metas -> requireActivity().runOnUiThread(() -> {

            listaMetas.clear();
            if (metas != null) listaMetas.addAll(metas);

            adaptador.notifyDataSetChanged();
            actualizarResumen();
        }));
    }

    private void actualizarResumen() {

        double totalActual = 0;
        double totalObjetivo = 0;

        for (Meta m : listaMetas) {
            totalActual += m.getCantidadActual();
            totalObjetivo += m.getCantidadObjetivo();
        }

        txtResumenCantidad.setText(
                String.format("%.2f € / %.2f €", totalActual, totalObjetivo)
        );
    }

    private void mostrarPopupMetaCompleta(Meta meta) {

        new AlertDialog.Builder(requireContext())
                .setTitle("🎉 ¡Meta completada!")
                .setMessage("Enhorabuena, has conseguido tu objetivo \""
                        + meta.getNombre() + "\".\n\n¡Sigue así crack! 💪🔥")
                .setPositiveButton("Aceptar", (d, w) -> {

                    // Opcional: eliminar meta automáticamente
                    controlador.eliminar(meta, filas -> cargarMetas());
                })
                .setCancelable(true)
                .show();
    }
}
