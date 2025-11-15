package com.example.healthywallet.ui.inicio;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.example.healthywallet.R;
import com.example.healthywallet.database.GestorBaseDatos;
import com.example.healthywallet.database.entities.Movimiento;

import java.util.List;

public class PantallaInicio extends Fragment {

    private TextView txtSaldoTotal, txtGastoMes, txtPresupuestoRestante;
    private Button btnAgregar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.pantalla_inicio, container, false);

        txtSaldoTotal = vista.findViewById(R.id.txtSaldoTotal);
        txtGastoMes = vista.findViewById(R.id.txtGastoMes);
        txtPresupuestoRestante = vista.findViewById(R.id.txtPresupuestoRestante);
        btnAgregar = vista.findViewById(R.id.btnAgregarMovimiento);

        cargarDatos();

        btnAgregar.setOnClickListener(view -> {
            Navigation.findNavController(view)
                    .navigate(R.id.fragmentoAgregarMovimiento);
        });

        return vista;
    }

    private void cargarDatos() {
        GestorBaseDatos db = GestorBaseDatos.obtenerInstancia(getContext());
        List<Movimiento> movimientos = db.movimientoDao().obtenerTodos();

        double ingresos = 0;
        double gastos = 0;

        for (Movimiento m : movimientos) {
            if (m.getTipo().equalsIgnoreCase("INGRESO")) {
                ingresos += m.getCantidad();
            } else {
                gastos += m.getCantidad();
            }
        }

        double saldo = ingresos - gastos;
        double presupuestoRestante = 500 - gastos; // Valor temporal hasta que programemos presupuestos reales

        txtSaldoTotal.setText("Saldo total: " + saldo + "€");
        txtGastoMes.setText("Gasto del mes: " + gastos + "€");
        txtPresupuestoRestante.setText("Presupuesto restante: " + presupuestoRestante + "€");
    }
}
