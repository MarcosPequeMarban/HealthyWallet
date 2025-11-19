package com.example.healthywallet.ui.metas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthywallet.R;
import com.example.healthywallet.adapters.AdaptadorMetas;
import com.example.healthywallet.controller.MetaControlador;

public class PantallaMetas extends Fragment {

    private RecyclerView recyclerView;
    private AdaptadorMetas adaptador;
    private MetaControlador controlador;

    public PantallaMetas() {
        // Constructor vacío obligatorio
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflamos el layout
        View vista = inflater.inflate(R.layout.pantalla_metas, container, false);

        controlador = new MetaControlador(getContext());

        recyclerView = vista.findViewById(R.id.recyclerMetas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Aquí cargarías tus metas cuando implementemos la BD
        // adaptador = new AdaptadorMetas(listaMetas);
        // recyclerView.setAdapter(adaptador);

        return vista;
    }
}
