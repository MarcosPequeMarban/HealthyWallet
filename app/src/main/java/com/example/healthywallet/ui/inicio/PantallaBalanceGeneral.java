package com.example.healthywallet.ui.inicio;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.healthywallet.R;
import com.example.healthywallet.controller.MetaControlador;
import com.example.healthywallet.controller.MovimientosControlador;
import com.example.healthywallet.controller.PresupuestoControlador;
import com.example.healthywallet.database.entities.CategoriaGasto;
import com.example.healthywallet.database.entities.Meta;
import com.example.healthywallet.database.entities.Presupuesto;


public class PantallaBalanceGeneral extends Fragment {

    // --- UI ---
    private TextView txtBalanceMes, txtIngresosGastosMes;

    private ProgressBar barraAhorroMes, barraPresupuestoMes, barraMetas;

    private TextView txtPctAhorro, txtPctPresupuesto, txtPctMetas;

    private TextView txtCategoriaMayorGasto, txtGastoSuperfluo, txtRecomendacion;

    private LinearLayout layoutCategorias;

    // --- Controladores ---
    private MovimientosControlador movC;
    private PresupuestoControlador preC;
    private MetaControlador metaC;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.pantalla_balance_general, container, false);

        // Referencias UI
        txtBalanceMes = v.findViewById(R.id.txtBalanceMes);
        txtIngresosGastosMes = v.findViewById(R.id.txtIngresosGastosMes);

        barraAhorroMes = v.findViewById(R.id.barraAhorroMes);
        barraPresupuestoMes = v.findViewById(R.id.barraPresupuestoMes);
        barraMetas = v.findViewById(R.id.barraMetas);

        txtPctAhorro = v.findViewById(R.id.txtPctAhorro);
        txtPctPresupuesto = v.findViewById(R.id.txtPctPresupuesto);
        txtPctMetas = v.findViewById(R.id.txtPctMetas);

        txtCategoriaMayorGasto = v.findViewById(R.id.txtCategoriaMayorGasto);
        txtGastoSuperfluo = v.findViewById(R.id.txtGastoSuperfluo);
        txtRecomendacion = v.findViewById(R.id.txtRecomendacion);

        layoutCategorias = v.findViewById(R.id.layoutCategorias);

        // Controladores
        movC = new MovimientosControlador(requireContext());
        preC = new PresupuestoControlador(requireContext());
        metaC = new MetaControlador(requireContext());

        // Cargar datos
        cargarBalanceMes();
        cargarPresupuestos();
        cargarMetas();
        cargarEstadisticasGasto();
        cargarCategorias();

        return v;
    }


    // ---------------------------------------------------------
    // Cargar balance general del mes
    // ---------------------------------------------------------
    private void cargarBalanceMes() {

        movC.obtenerSumaPorTipo("Ingreso", ingresos ->
                movC.obtenerSumaPorTipo("Gasto", gastos -> {

                    double balance = ingresos - gastos;
                    int pct = ingresos == 0 ? 0 : (int) ((balance / ingresos) * 100);

                    if (!isAdded()) return;

                    requireActivity().runOnUiThread(() -> {
                        txtBalanceMes.setText(String.format("%.2f €", balance));
                        txtIngresosGastosMes.setText(
                                String.format("+%.2f ingresos | -%.2f gastos", ingresos, gastos)
                        );

                        barraAhorroMes.setProgress(pct);
                        txtPctAhorro.setText(pct + "% — " + generarMensaje(pct));
                    });

                })
        );
    }


    // ---------------------------------------------------------
    // Cargar Presupuestos
    // ---------------------------------------------------------
    private void cargarPresupuestos() {

        preC.obtenerTodos(lista -> {

            double gasto = 0;
            double limite = 0;

            for (Presupuesto p : lista) {
                gasto += p.getGastoActual();
                limite += p.getLimite();
            }

            int pct = limite == 0 ? 0 : (int) ((gasto / limite) * 100);

            if (!isAdded()) return;

            requireActivity().runOnUiThread(() -> {
                barraPresupuestoMes.setProgress(pct);
                txtPctPresupuesto.setText(pct + "% — " + generarMensaje(100 - pct));
            });
        });
    }


    // ---------------------------------------------------------
    // Cargar metas
    // ---------------------------------------------------------
    private void cargarMetas() {

        metaC.obtenerTodas(lista -> {

            double progreso = 0;
            double total = 0;

            for (Meta m : lista) {
                progreso += m.getCantidadActual();
                total += m.getCantidadObjetivo();
            }

            int pct = total == 0 ? 0 : (int) ((progreso / total) * 100);

            if (!isAdded()) return;

            requireActivity().runOnUiThread(() -> {
                barraMetas.setProgress(pct);
                txtPctMetas.setText(pct + "% — " + generarMensaje(pct));
            });
        });
    }


    // ---------------------------------------------------------
    // Estadísticas: categoría mayor gasto + superfluo
    // ---------------------------------------------------------
    private void cargarEstadisticasGasto() {

        movC.obtenerGastosPorCategoria(resultado -> {

            if (!isAdded()) return;

            requireActivity().runOnUiThread(() -> {

                if (resultado == null || resultado.isEmpty()) {
                    txtCategoriaMayorGasto.setText("Categoría con más gasto: ---");
                    txtGastoSuperfluo.setText("Gasto superfluo: ---");
                    txtRecomendacion.setText("Recomendación: ---");
                    return;
                }

                double total = 0;
                String maxCat = "";
                double maxVal = 0;

                for (CategoriaGasto item : resultado) {
                    total += item.getTotal();
                    if (item.getTotal() > maxVal) {
                        maxVal = item.getTotal();
                        maxCat = item.getCategoria();
                    }
                }

                int pct = total == 0 ? 0 : (int) ((maxVal / total) * 100);

                txtCategoriaMayorGasto.setText("Categoría con más gasto: " + maxCat);
                txtGastoSuperfluo.setText("Gasto superfluo: " + pct + "%");

                String rec =
                        pct >= 50 ? "Revisa tus gastos en " + maxCat + " y busca alternativas."
                                : pct >= 25 ? "Puedes optimizar tus gastos en " + maxCat + "."
                                : "Tu gasto está bien equilibrado.";

                txtRecomendacion.setText(rec);
            });
        });
    }



    // ---------------------------------------------------------
    // Cargar categorías en tarjetas
    // ---------------------------------------------------------
    private void cargarCategorias() {

        movC.obtenerGastosPorCategoria(resultado -> {

            if (!isAdded()) return;

            requireActivity().runOnUiThread(() -> {

                layoutCategorias.removeAllViews();

                if (resultado == null || resultado.isEmpty()) {

                    TextView txt = new TextView(getContext());
                    txt.setText("No hay gastos registrados este mes.");
                    txt.setTextColor(Color.GRAY);
                    layoutCategorias.addView(txt);
                    return;
                }

                for (CategoriaGasto item : resultado) {

                    String categoria = item.getCategoria();
                    double total = item.getTotal();

                    // Crear tarjeta
                    CardView card = new CardView(getContext());
                    card.setCardElevation(6);
                    card.setRadius(14);
                    card.setUseCompatPadding(true);

                    LinearLayout layout = new LinearLayout(getContext());
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setPadding(20, 20, 20, 20);

                    TextView txtCat = new TextView(getContext());
                    txtCat.setText("• " + categoria);
                    txtCat.setTextSize(16f);
                    txtCat.setTextColor(Color.BLACK);

                    TextView txtTotal = new TextView(getContext());
                    txtTotal.setText(String.format("%.2f €", total));
                    txtTotal.setTextSize(14f);
                    txtTotal.setTextColor(Color.DKGRAY);

                    layout.addView(txtCat);
                    layout.addView(txtTotal);

                    card.addView(layout);
                    layoutCategorias.addView(card);
                }
            });
        });
    }



    // ---------------------------------------------------------
    // Mensajes motivadores
    // ---------------------------------------------------------
    private String generarMensaje(int pct) {
        if (pct >= 80) return "¡Excelente progreso! 🎉";
        if (pct >= 50) return "Vas por buen camino 👍";
        if (pct >= 20) return "Puedes mejorar 💡";
        return "Sin progreso significativo aún ⚠️";
    }
}
