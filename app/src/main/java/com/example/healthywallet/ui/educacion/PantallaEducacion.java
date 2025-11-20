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

        abrir(cardVideo1, "https://youtu.be/WN6C4V9Ezg8");
        abrir(cardVideo2, "https://youtu.be/G1nK2JQyb-8");

        abrir(cardLibro1, "https://amzn.to/3EMYabc");

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
