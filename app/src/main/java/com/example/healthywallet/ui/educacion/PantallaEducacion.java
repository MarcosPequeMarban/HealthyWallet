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
        libros.add(new String[]{"Padre Rico Padre Pobre", "Robert Kiyosaki", "https://amzn.to/3EMYYabc"});
        libros.add(new String[]{"El Hombre Más Rico de Babilonia", "George S. Clason", "https://amzn.to/3ABxYlq"});
        libros.add(new String[]{"Piense y Hágase Rico", "Napoleon Hill", "https://amzn.to/4acFG8d"});

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
