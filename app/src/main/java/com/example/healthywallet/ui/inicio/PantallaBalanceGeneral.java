package com.example.healthywallet.ui.inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.healthywallet.R;
import com.example.healthywallet.controller.MovimientosControlador;
import com.example.healthywallet.database.entities.Movimiento;

import java.util.List;

public class PantallaBalanceGeneral extends Fragment {

    private TextView txtIngresosMes, txtGastosMes, txtTop1, txtTop2, txtTop3;
    private MovimientosControlador controlador;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.pantalla_balance_general, container, false);

        txtIngresosMes = v.findViewById(R.id.txtIngresosMes);
        txtGastosMes = v.findViewById(R.id.txtGastosMes);
        txtTop1 = v.findViewById(R.id.txtTop1);
        txtTop2 = v.findViewById(R.id.txtTop2);
        txtTop3 = v.findViewById(R.id.txtTop3);

        controlador = new MovimientosControlador(requireContext());

        cargarBalance();

        return v;
    }

    private void cargarBalance() {
        controlador.obtenerTodos(movs -> {

            requireActivity().runOnUiThread(() -> {

                double ingresos = 0;
                double gastos = 0;

                // contador por categorías
                java.util.Map<String, Double> categorias = new java.util.HashMap<>();

                for (Movimiento m : movs) {

                    if (m.getTipo().equals("Ingreso")) ingresos += m.getCantidad();
                    else gastos += m.getCantidad();

                    categorias.put(m.getCategoria(),
                            categorias.getOrDefault(m.getCategoria(), 0.0) + m.getCantidad());
                }

                txtIngresosMes.setText(String.format("%.2f €", ingresos));
                txtGastosMes.setText(String.format("%.2f €", gastos));

                // top categorías
                List<java.util.Map.Entry<String, Double>> lista = new java.util.ArrayList<>(categorias.entrySet());
                lista.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

                if (lista.size() > 0) txtTop1.setText(lista.get(0).getKey() + " — " + lista.get(0).getValue() + " €");
                if (lista.size() > 1) txtTop2.setText(lista.get(1).getKey() + " — " + lista.get(1).getValue() + " €");
                if (lista.size() > 2) txtTop3.setText(lista.get(2).getKey() + " — " + lista.get(2).getValue() + " €");
            });
        });
    }
}
