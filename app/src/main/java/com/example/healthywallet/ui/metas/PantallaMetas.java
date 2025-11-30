package com.example.healthywallet.ui.metas;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.healthywallet.adapters.AdaptadorMetas;
import com.example.healthywallet.controller.MetaControlador;
import com.example.healthywallet.database.entities.Meta;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PantallaMetas extends Fragment {

    private RecyclerView recycler;
    private AdaptadorMetas adaptador;
    private FloatingActionButton fabAgregarMeta;

    private MetaControlador controlador;
    private int userId;

    private final List<Meta> lista = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.pantalla_metas, container, false);

        controlador = new MetaControlador(requireContext());

        SharedPreferences prefs = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        userId = prefs.getInt("usuarioId", -1);

        recycler = vista.findViewById(R.id.recyclerMetas);
        fabAgregarMeta = vista.findViewById(R.id.fabAgregarMeta);

        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adaptador = new AdaptadorMetas(requireContext(), lista);
        recycler.setAdapter(adaptador);

        fabAgregarMeta.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_metas_to_agregarMeta)
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
                lista.clear();
                if (metas != null) lista.addAll(metas);
                adaptador.notifyDataSetChanged();
            });
        });
    }
}
