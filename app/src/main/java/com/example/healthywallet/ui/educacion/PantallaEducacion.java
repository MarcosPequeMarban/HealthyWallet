package com.example.healthywallet.ui.educacion;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.healthywallet.R;
import com.example.healthywallet.controller.FormacionControlador;
import com.example.healthywallet.database.entities.Formacion;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PantallaEducacion extends Fragment {

    private FormacionControlador controlador;

    // ==== VISTAS ====
    private TextView txtProgresoGlobal;
    private LinearProgressIndicator barraProgresoGlobal;

    private LinearLayout contPrincipiante, contIntermedio, contExperto;

    private LinearProgressIndicator barraPrincipiante, barraIntermedio, barraExperto;

    private TextView txtLibroTitulo, txtLibroAutor;
    private Button btnAbrirLibro;

    public PantallaEducacion() {
        super(R.layout.pantalla_educacion);
    }

    @Override
    public void onViewCreated(View vista, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(vista, savedInstanceState);

        controlador = new FormacionControlador(requireContext());
        controlador.inicializarDatosSiNecesario();

        // ==== REFERENCIAS ====
        txtProgresoGlobal = vista.findViewById(R.id.txtProgresoGlobal);
        barraProgresoGlobal = vista.findViewById(R.id.progresoGlobalBarra);

        contPrincipiante = vista.findViewById(R.id.contenedorVideosPrincipiante);
        contIntermedio = vista.findViewById(R.id.contenedorVideosIntermedio);
        contExperto = vista.findViewById(R.id.contenedorVideosExperto);

        barraPrincipiante = vista.findViewById(R.id.barraNivelPrincipiante);
        barraIntermedio = vista.findViewById(R.id.barraNivelIntermedio);
        barraExperto = vista.findViewById(R.id.barraNivelExperto);

        txtLibroTitulo = vista.findViewById(R.id.txtLibroTitulo);
        txtLibroAutor = vista.findViewById(R.id.txtLibroAutor);
        btnAbrirLibro = vista.findViewById(R.id.btnAbrirLibro);

        // === HERRAMIENTAS ===
        MaterialCardView toolCalc = vista.findViewById(R.id.toolCalculadoraInteres);
        MaterialCardView toolSim = vista.findViewById(R.id.toolSimuladorInversion);
        MaterialCardView toolDic = vista.findViewById(R.id.toolDiccionarioFinanciero);

        toolCalc.setOnClickListener(v -> abrirWeb("http://www.moneychimp.com/international/es/calculator/compound_interest_calculator.htm"));
        toolSim.setOnClickListener(v -> abrirWeb("https://es.investing.com/"));
        toolDic.setOnClickListener(v -> abrirWeb("https://es.tradingview.com/"));

        // CARGA
        cargarVideos();
        cargarLibroSemana();
    }

    // =====================================================================================
    //   ABRIR LINKS
    // =====================================================================================
    private void abrirWeb(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    // =====================================================================================
    //   CARGAR VIDEOS DESDE BASE DE DATOS
    // =====================================================================================
    private void cargarVideos() {

        controlador.obtenerTodas(lista -> {
            if (lista == null) return;

            requireActivity().runOnUiThread(() -> {
                contPrincipiante.removeAllViews();
                contIntermedio.removeAllViews();
                contExperto.removeAllViews();
            });

            List<Formacion> principiantes = new ArrayList<>();
            List<Formacion> intermedios = new ArrayList<>();
            List<Formacion> expertos = new ArrayList<>();

            for (Formacion v : lista) {
                switch (v.getNivel().toLowerCase()) {
                    case "principiante":
                        principiantes.add(v);
                        break;
                    case "intermedio":
                        intermedios.add(v);
                        break;
                    case "experto":
                        expertos.add(v);
                        break;
                }
            }

            cargarVideosEnContenedor(principiantes, contPrincipiante, barraPrincipiante);
            cargarVideosEnContenedor(intermedios, contIntermedio, barraIntermedio);
            cargarVideosEnContenedor(expertos, contExperto, barraExperto);

            calcularProgresoGlobal();
        });
    }

    // =====================================================================================
    //   AGREGAR VIDEOS A SU CONTENEDOR + ACTUALIZAR BARRA
    // =====================================================================================
    private void cargarVideosEnContenedor(List<Formacion> lista, LinearLayout contenedor, LinearProgressIndicator barra) {

        final int total = lista.size();
        final int[] completados = {0};

        requireActivity().runOnUiThread(() -> {

            LayoutInflater infl = LayoutInflater.from(requireContext());

            for (Formacion v : lista) {

                if (v.isCompletado()) completados[0]++;

                View item = infl.inflate(R.layout.item_video, contenedor, false);

                TextView titulo = item.findViewById(R.id.txtTituloVideo);
                CheckBox check = item.findViewById(R.id.checkCompletado);

                titulo.setText(v.getTitulo());
                check.setChecked(v.isCompletado());

                // ✔ CLICK EN EL ITEM PARA ABRIR EL VIDEO
                item.setOnClickListener(vw -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(v.getUrl()));
                    startActivity(intent);
                });

                // ✔ CLICK EN EL CHECKBOX PARA COMPLETAR
                check.setOnCheckedChangeListener((btn, marcado) -> {
                    v.setCompletado(marcado);
                    controlador.actualizar(v, ok -> cargarVideos());
                });

                contenedor.addView(item);
            }

            int porcentaje = total == 0 ? 0 : (completados[0] * 100 / total);
            barra.setProgress(porcentaje);
        });
    }


    // =====================================================================================
    //   CALCULAR PROGRESO TOTAL (%)
    // =====================================================================================
    private void calcularProgresoGlobal() {

        controlador.calcularProgresoGlobal(porcentaje -> {
            requireActivity().runOnUiThread(() -> {
                barraProgresoGlobal.setProgress(porcentaje);
                txtProgresoGlobal.setText(porcentaje + "% completado");
            });
        });
    }

    // =====================================================================================
    //   LIBRO DE LA SEMANA
    // =====================================================================================
    private void cargarLibroSemana() {

        List<String[]> libros = new ArrayList<>();
        libros.add(new String[]{"Padre Rico Padre Pobre", "Robert Kiyosaki", "https://www.amazon.es/Padre-padre-Pobre-edici%C3%B3n-actualizada/dp/8466373004/ref=sr_1_1?dib=eyJ2IjoiMSJ9.X73yUfrT8lmywIVH8vGwZ_SXnHqOWEPvcsqtFm38fme59Q2NSqTHab1YvZSFO8WlqbyYdjJNVjKjRFcAnTGTFJfT9fgm9tDs6z3LneONmsHeGKQ9FrDCmLcVEATDWyaq9yX4ZygsmX5gVL7q-ZchoYKH1m2oUI9_EcWBD5KKe684dx71bLnd5PO111diydEFxGACx0qKVcr9WXJmxJxzVYjWs9cgH5Wc7lO8h_S2VJRaV9r79yJ2rvJj8fYDlEQ1VBusjgfJ3A4wxzyGjTE28oNK3XI6737JzNTvIqPqeWo.RNiwyZTujMqJ68reXXxzYNR6JBNO4sQpPOcJ7m6-suE&dib_tag=se&keywords=Padre+Rico+Padre+Pobre&qid=1764771563&sr=8-1"});
        libros.add(new String[]{"El Hombre Más Rico de Babilonia", "George S. Clason", "https://www.amazon.es/hombre-m%C3%A1s-rico-Babilonia-ficci%C3%B3n/dp/8413149444/ref=sr_1_2?__mk_es_ES=%C3%85M%C3%85%C5%BD%C3%95%C3%91&crid=X4F6Q55IEW42&dib=eyJ2IjoiMSJ9.QOVHWy3S0uFBJSPpeFysqEUFTf1iBHbWPbgoK4W-pejOB9kwRx9XorO1Lj4yYifuaoY6uGeESUJTHtWsiXN351xa_Goeq9A7cGPjzoa0EyxAWRI-1CyLKm85b8SZywfGrle6Qczd0cjKz6jIWMf7HXndN3MnYH8kE0op0UkGhLh3r2gJ6lhMQgU4tP2hhAz_nqDne4VjE2WK73v3LO5Fvd3asF3IUHd8Mh1Al8gVBgl6vx3sUorTWgswO3VzRC2xS1VwYBjI0xqTiEbUKl1BRrv-jMZygvLLOuVR2zHTrg4.Tv4kGXzZKrvzRtA9rdlQgj9cD2V0nRe-Vdza4Ei4oZI&dib_tag=se&keywords=El+Hombre+M%C3%A1s+Rico+de+Babilonia&qid=1764771587&sprefix=el+hombre+m%C3%A1s+rico+de+babilonia%2Caps%2C66&sr=8-2"});
        libros.add(new String[]{"Piense y Hágase Rico", "Napoleon Hill", "https://www.amazon.es/Piense-y-h%C3%A1gase-rico-EXITO/dp/8497778219/ref=sr_1_1?__mk_es_ES=%C3%85M%C3%85%C5%BD%C3%95%C3%91&crid=HFSCFM9WJCZG&dib=eyJ2IjoiMSJ9.vFSEkAszDLCg232Bagf7Y4enmAG1NZP95ODIaQ7MKWGok58FB5bV_LlX2LvTUPFkhzvWz4luysA66HZfB6unOfJBcrUsqZsuJ4sDLKMrZHKaUdvHtN8UhACCGfpLvG0PW3sNp-x8TTvwXkbeZC2DromHOqQXjE4A0s9NCZ738KCMJKEEXWXW1l7LvrhaA1Wiuv___roxBtZCp00ewyaxquJ0afGEwfbno_yPgL32zwXE4mlUerm2_TBfoA0Eapw9X35GhR9-jQOblcTWpwDbJo-NaYgfs8fb-cMZpxbqb2k.fbL_SCB4Fn1GIyjpXBNrcDYAmngleWPg7Wrh088a9dI&dib_tag=se&keywords=Piense+y+H%C3%A1gase+Rico&qid=1764771607&sprefix=piense+y+h%C3%A1gase+rico%2Caps%2C60&sr=8-1"});

        int semana = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
        int indice = semana % libros.size();

        String[] libro = libros.get(indice);

        requireActivity().runOnUiThread(() -> {
            txtLibroTitulo.setText(libro[0]);
            txtLibroAutor.setText(libro[1]);

            btnAbrirLibro.setOnClickListener(v -> abrirWeb(libro[2]));
        });
    }
}
