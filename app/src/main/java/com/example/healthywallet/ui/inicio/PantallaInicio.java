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

    private TextView txtBalanceCantidadInicio;
    private TextView txtResumenMovimientosInicio;
    private TextView txtResumenPresupuestosInicio;
    private TextView txtResumenMetasInicio;
    private TextView txtResumenEducacionInicio;

    private CardView cardMovimientos, cardPresupuestos, cardMetas, cardEducacion;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.pantalla_inicio, container, false);

        // IDs reales
        txtBalanceCantidadInicio = vista.findViewById(R.id.txtBalanceCantidadInicio);
        txtResumenMovimientosInicio = vista.findViewById(R.id.txtResumenMovimientosInicio);
        txtResumenPresupuestosInicio = vista.findViewById(R.id.txtResumenPresupuestosInicio);
        txtResumenMetasInicio = vista.findViewById(R.id.txtResumenMetasInicio);
        txtResumenEducacionInicio = vista.findViewById(R.id.txtResumenEducacionInicio);

        cardMovimientos = vista.findViewById(R.id.cardMovimientos);
        cardPresupuestos = vista.findViewById(R.id.cardPresupuestos);
        cardMetas = vista.findViewById(R.id.cardMetas);
        cardEducacion = vista.findViewById(R.id.cardEducacion);

        // Navegación real
        cardMovimientos.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.nav_movimientos));
        cardPresupuestos.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.nav_presupuestos));
        cardMetas.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.nav_metas));
        cardEducacion.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.nav_educacion));

        cargarDatos();

        return vista;
    }

    private void cargarDatos() {

        // CONTROLADORES
        MovimientosControlador movimientosControlador = new MovimientosControlador(getContext());
        PresupuestoControlador presupuestoControlador = new PresupuestoControlador(getContext());
        MetaControlador metaControlador = new MetaControlador(getContext());
        FormacionControlador formacionControlador = new FormacionControlador(getContext());

        // ======================
        //       MOVIMIENTOS
        // ======================
        double ingresos = movimientosControlador.obtenerSumaPorTipo("Ingreso");
        double gastos = movimientosControlador.obtenerSumaPorTipo("Gasto");
        double balance = ingresos - gastos;

        txtBalanceCantidadInicio.setText(String.format("%.2f €", balance));
        txtResumenMovimientosInicio.setText(
                "+ " + String.format("%.2f", ingresos) + " €  |  - " + String.format("%.2f", gastos) + " €"
        );

        // ======================
        //      PRESUPUESTOS
        // ======================
        List<Presupuesto> presupuestos = presupuestoControlador.obtenerTodos();
        double totalGasto = 0;
        double totalLimite = 0;

        if (presupuestos != null) {
            for (Presupuesto p : presupuestos) {
                totalGasto += p.getGastoActual();
                totalLimite += p.getLimite();
            }
        }

        txtResumenPresupuestosInicio.setText(
                String.format("%.2f€ gastados / %.2f€", totalGasto, totalLimite)
        );

        // ======================
        //         METAS
        // ======================
// ======================
//          METAS
// ======================
        List<Meta> metas = metaControlador.obtenerTodas();
        int activas = 0;
        int completadas = 0;

        if (metas != null) {
            for (Meta m : metas) {
                if (m.getCantidadActual() >= m.getCantidadObjetivo()) {
                    completadas++;
                } else {
                    activas++;
                }
            }
        }

        txtResumenMetasInicio.setText(
                activas + " activas  |  " + completadas + " completadas"
        );


        // ======================
        //         EDUCACION
        // ======================
        List<Formacion> modulos = formacionControlador.obtenerTodas();
        int completados = 0;

        if (modulos != null) {
            for (Formacion f : modulos) {
                if (f.getCompletado() == 1) completados++;
            }
        }

        txtResumenEducacionInicio.setText(
                completados + " módulos completados"
        );
    }
}
