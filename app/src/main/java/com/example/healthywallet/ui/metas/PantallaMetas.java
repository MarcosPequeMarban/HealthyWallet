package com.example.healthywallet.ui.metas;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthywallet.R;
import com.example.healthywallet.adapters.AdaptadorMetas;
import com.example.healthywallet.controller.MetaControlador;
import com.example.healthywallet.database.entities.Meta;

import java.util.List;

public class PantallaMetas extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdaptadorMetas adaptador;
    private MetaControlador controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_metas);

        controlador = new MetaControlador(this);

        recyclerView = findViewById(R.id.recyclerMetas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


}
