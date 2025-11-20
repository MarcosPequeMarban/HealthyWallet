package com.example.healthywallet.ui.educacion;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.healthywallet.R;

public class PantallaEducacion extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.pantalla_educacion, container, false);

        // =========== ASIGNAR CARDS ===========
        CardView cardVideo1 = vista.findViewById(R.id.cardVideo1);
        CardView cardVideo2 = vista.findViewById(R.id.cardVideo2);

        CardView cardLibro1 = vista.findViewById(R.id.cardLibro1);

        CardView cardTool1 = vista.findViewById(R.id.cardTool1);
        CardView cardTool2 = vista.findViewById(R.id.cardTool2);
        CardView cardTool3 = vista.findViewById(R.id.cardTool3);

        // =========== EVENTOS ===========

        abrir(cardVideo1, "https://youtu.be/ujY0DkyL--w?si=bOuKABNajrNIy-iT");
        abrir(cardVideo2, "https://youtu.be/bkYK-akFam4?si=zZ1Oug3rfNqxzNex");

        abrir(cardLibro1, "https://www.amazon.es/s?k=padre+rico+padre+pobre&__mk_es_ES=%C3%85M%C3%85%C5%BD%C3%95%C3%91&crid=3CQQ3JP79SI2&sprefix=padre+rico+padre+pobre+%2Caps%2C73&ref=nb_sb_noss_2");

        abrir(cardTool1, "https://es.investing.com/");
        abrir(cardTool2, "https://www.tradingview.com/");
        abrir(cardTool3, "http://www.moneychimp.com/");

        return vista;
    }

    private void abrir(CardView card, String url) {
        card.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(i);
        });
    }
}
