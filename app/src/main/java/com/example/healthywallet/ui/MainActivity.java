package com.example.healthywallet.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.healthywallet.R;
import com.example.healthywallet.database.GestorBaseDatos;
import com.example.healthywallet.database.entities.Movimiento;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ✅ 1. Obtenemos una instancia de la base de datos
        GestorBaseDatos db = GestorBaseDatos.obtenerInstancia(this);

        // ✅ 2. Creamos un movimiento de prueba
        Movimiento m = new Movimiento(
                "Compra supermercado",
                45.60,
                "GASTO",
                "2025-11-12",
                "Alimentación"
        );

        // ✅ 3. Insertamos el movimiento en la base de datos
        db.movimientoDao().insertar(m);

        // ✅ 4. Obtenemos todos los movimientos guardados
        List<Movimiento> lista = db.movimientoDao().obtenerTodos();

        // ✅ 5. Mostramos el resultado en el Logcat
        Log.d("HealthyWallet", "Número de movimientos guardados: " + lista.size());

        // ✅ 6. Mostramos también el detalle de cada uno
        for (Movimiento mov : lista) {
            Log.d("HealthyWallet", "Movimiento: " + mov.getDescripcion()
                    + " | Tipo: " + mov.getTipo()
                    + " | Cantidad: " + mov.getCantidad());
        }
    }
}
