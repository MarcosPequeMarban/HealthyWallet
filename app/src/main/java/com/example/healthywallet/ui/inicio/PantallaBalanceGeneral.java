package com.example.healthywallet.ui.inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.healthywallet.R;
import com.example.healthywallet.controller.MetaControlador;
import com.example.healthywallet.controller.MovimientosControlador;
import com.example.healthywallet.controller.PresupuestoControlador;
import com.example.healthywallet.database.entities.Meta;
import com.example.healthywallet.database.entities.Presupuesto;

import java.util.List;

public class PantallaBalanceGeneral extends Fragment {

    private TextView txtBalanceMes, txtIngresosGastosMes;
    private ProgressBar barraAhorroMes, barraPresupuestoMes, barraMetas;
    private LinearLayout layoutCategorias;

    private MovimientosControlador movC;
    private PresupuestoControlador preC;
    private MetaControlador metaC;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.pantalla_balance_general, container, false);

        // Referencias UI
        txtBalanceMes = v.findViewById(R.id.txtBalanceMes);
        txtIngresosGastosMes = v.findViewById(R.id.txtIngresosGastosMes);

        barraAhorroMes = v.findViewById(R.id.barraAhorroMes);
        barraPresupuestoMes = v.findViewById(R.id.barraPresupuestoMes);
        barraMetas = v.findViewById(R.id.barraMetas);

        layoutCategorias = v.findViewById(R.id.layoutCategorias);

        // Controladores
        movC = new MovimientosControlador(requireContext());
        preC = new PresupuestoControlador(requireContext());
        metaC = new MetaControlador(requireContext());

        cargarBalanceMes();
        cargarPresupuestos();
        cargarMetas();
        cargarCategorias();

        return v;
    }


    private void cargarBalanceMes() {

        movC.obtenerSumaPorTipo("Ingreso", ingresos ->
                movC.obtenerSumaPorTipo("Gasto", gastos -> {

                    double balance = ingresos - gastos;

                    if (!isAdded()) return;

                    requireActivity().runOnUiThread(() -> {
                        txtBalanceMes.setText(String.format("%.2f €", balance));
                        txtIngresosGastosMes.setText(
                                String.format("+%.2f ingresos | -%.2f gastos", ingresos, gastos)
                        );
                    });

                })
        );
    }


    private void cargarPresupuestos() {

        preC.obtenerTodos(lista -> {

            double gasto = 0;
            double limite = 0;

            if (lista != null) {
                for (Presupuesto p : lista) {
                    gasto += p.getGastoActual();
                    limite += p.getLimite();
                }
            }

            final int porcentaje = (int) ((limite == 0) ? 0 : (gasto / limite) * 100);

            if (!isAdded()) return;

            requireActivity().runOnUiThread(() ->
                    barraPresupuestoMes.setProgress(porcentaje)
            );
        });
    }


    private void cargarMetas() {

        metaC.obtenerTodas(lista -> {

            double progreso = 0;
            double total = 0;

            if (lista != null) {
                for (Meta m : lista) {
                    progreso += m.getCantidadActual();
                    total += m.getCantidadObjetivo();
                }
            }

            final int porcentaje = (int) ((total == 0) ? 0 : (progreso / total) * 100);

            if (!isAdded()) return;

            requireActivity().runOnUiThread(() ->
                    barraMetas.setProgress(porcentaje)
            );
        });
    }


    private void cargarCategorias() {

    }
}
