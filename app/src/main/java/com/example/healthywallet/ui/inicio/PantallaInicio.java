package com.example.healthywallet.ui.inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.healthywallet.R;
import com.example.healthywallet.controller.FormacionControlador;
import com.example.healthywallet.controller.MetaControlador;
import com.example.healthywallet.controller.MovimientosControlador;
import com.example.healthywallet.controller.PresupuestoControlador;
import com.example.healthywallet.database.entities.Formacion;
import com.example.healthywallet.database.entities.Meta;
import com.example.healthywallet.database.entities.Presupuesto;

import java.util.List;

public class PantallaInicio extends Fragment {

    // UI
    private TextView txtBalanceCantidadInicio;
    private TextView txtResumenMovimientosInicio;
    private TextView txtResumenPresupuestosInicio;
    private TextView txtResumenMetasInicio;
    private TextView txtResumenEducacionInicio;

    private CardView cardBalanceGeneral;  // NUEVA TARJETA
    private CardView cardMovimientos, cardPresupuestos, cardMetas, cardEducacion;

    // Controllers
    private MovimientosControlador movimientosControlador;
    private PresupuestoControlador presupuestoControlador;
    private MetaControlador metaControlador;
    private FormacionControlador formacionControlador;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {

        View vista = inflater.inflate(R.layout.pantalla_inicio, container, false);

        // Controladores
        movimientosControlador = new MovimientosControlador(getContext());
        presupuestoControlador = new PresupuestoControlador(getContext());
        metaControlador = new MetaControlador(getContext());
        formacionControlador = new FormacionControlador(getContext());

        // UI
        txtBalanceCantidadInicio = vista.findViewById(R.id.txtBalanceCantidadInicio);
        txtResumenMovimientosInicio = vista.findViewById(R.id.txtResumenMovimientosInicio);
        txtResumenPresupuestosInicio = vista.findViewById(R.id.txtResumenPresupuestosInicio);
        txtResumenMetasInicio = vista.findViewById(R.id.txtResumenMetasInicio);
        txtResumenEducacionInicio = vista.findViewById(R.id.txtResumenEducacionInicio);

        cardBalanceGeneral = vista.findViewById(R.id.cardBalanceGeneral); 
        cardMovimientos = vista.findViewById(R.id.cardMovimientos);
        cardPresupuestos = vista.findViewById(R.id.cardPresupuestos);
        cardMetas = vista.findViewById(R.id.cardMetas);
        cardEducacion = vista.findViewById(R.id.cardEducacion);

        // Navegación
        cardBalanceGeneral.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.pantallaBalanceGeneral)
        );

        cardMovimientos.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.nav_movimientos));

        cardPresupuestos.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.nav_presupuestos));

        cardMetas.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.nav_metas));

        cardEducacion.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.nav_educacion));

        cargarDatos();

        return vista;
    }


    private void cargarDatos() {

        // -------- MOVIMIENTOS --------
        movimientosControlador.obtenerSumaPorTipo("Ingreso", ingresos ->
                movimientosControlador.obtenerSumaPorTipo("Gasto", gastos -> {

                    double balance = ingresos - gastos;

                    requireActivity().runOnUiThread(() -> {
                        txtBalanceCantidadInicio.setText(String.format("%.2f €", balance));
                        txtResumenMovimientosInicio.setText(
                                String.format("+%.2f ingresos | -%.2f gastos", ingresos, gastos)
                        );
                    });

                })
        );

        // -------- PRESUPUESTOS --------
        presupuestoControlador.obtenerTodos(presupuestos -> {

            double totalLimite = 0, totalGasto = 0;

            if (presupuestos != null) {
                for (Presupuesto p : presupuestos) {
                    totalLimite += p.getLimite();
                    totalGasto += p.getGastoActual();
                }
            }

            double finalTotalLimite = totalLimite;
            double finalTotalGasto = totalGasto;

            requireActivity().runOnUiThread(() ->
                    txtResumenPresupuestosInicio.setText(
                            String.format("%.2f € gastados / %.2f €", finalTotalGasto, finalTotalLimite)
                    )
            );
        });

        // -------- METAS --------
        metaControlador.obtenerTodas(metas -> {

            int activas = 0;
            int completadas = 0;

            if (metas != null) {
                for (Meta m : metas) {
                    if (m.getCantidadActual() >= m.getCantidadObjetivo())
                        completadas++;
                    else
                        activas++;
                }
            }

            int finalActivas = activas;
            int finalCompletadas = completadas;

            requireActivity().runOnUiThread(() ->
                    txtResumenMetasInicio.setText(
                            String.format("%d activas | %d completadas", finalActivas, finalCompletadas)
                    ));
        });

        // -------- EDUCACIÓN --------
        formacionControlador.obtenerTodas(modulos -> {

            int total = (modulos != null ? modulos.size() : 0);

            requireActivity().runOnUiThread(() ->
                    txtResumenEducacionInicio.setText(
                            String.format("%d módulos registrados", total)
                    )
            );
        });
    }
}
