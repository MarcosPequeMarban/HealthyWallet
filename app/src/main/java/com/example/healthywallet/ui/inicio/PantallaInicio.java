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

public class PantallaInicio extends Fragment {

    private TextView txtBalanceCantidadInicio;
    private TextView txtResumenMovimientosInicio;
    private TextView txtResumenPresupuestosInicio;
    private TextView txtResumenMetasInicio;
    private TextView txtResumenEducacionInicio;

    private CardView cardMovimientos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.pantalla_inicio, container, false);

        // --- Asignación de IDs reales ---
        txtBalanceCantidadInicio = vista.findViewById(R.id.txtBalanceCantidadInicio);
        txtResumenMovimientosInicio = vista.findViewById(R.id.txtResumenMovimientosInicio);
        txtResumenPresupuestosInicio = vista.findViewById(R.id.txtResumenPresupuestosInicio);
        txtResumenMetasInicio = vista.findViewById(R.id.txtResumenMetasInicio);
        txtResumenEducacionInicio = vista.findViewById(R.id.txtResumenEducacionInicio);

        cardMovimientos = vista.findViewById(R.id.cardMovimientos);

        // --- Click para ir a movimientos ---
        cardMovimientos.setOnClickListener(view -> {
            Navigation.findNavController(view)
                    .navigate(R.id.fragmentoAgregarMovimiento);
        });

        cargarDatos();

        return vista;
    }

    private void cargarDatos() {
        // Aquí luego cargaremos datos reales desde Room
        txtBalanceCantidadInicio.setText("0,00 €");
        txtResumenMovimientosInicio.setText("+0 ingresos | -0 gastos");
        txtResumenPresupuestosInicio.setText("0 € gastados / 0 €");
        txtResumenMetasInicio.setText("0 activas | 0 completadas");
        txtResumenEducacionInicio.setText("0 módulos completados");
    }
}
