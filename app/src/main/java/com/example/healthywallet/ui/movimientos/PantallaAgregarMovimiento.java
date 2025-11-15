package com.example.healthywallet.ui.movimientos;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.example.healthywallet.R;
import com.example.healthywallet.database.GestorBaseDatos;
import com.example.healthywallet.database.entities.Movimiento;

public class PantallaAgregarMovimiento extends Fragment {

    private EditText etDescripcion, etCantidad;
    private Spinner spTipo, spCategoria;
    private DatePicker datePicker;
    private Button btnGuardar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.pantalla_agregar_movimiento, container, false);

        etDescripcion = vista.findViewById(R.id.etDescripcion);
        etCantidad = vista.findViewById(R.id.etCantidad);
        spTipo = vista.findViewById(R.id.spTipo);
        spCategoria = vista.findViewById(R.id.spCategoria);
        datePicker = vista.findViewById(R.id.datePicker);
        btnGuardar = vista.findViewById(R.id.btnGuardar);

        configurarSpinners();
        configurarBoton();

        return vista;
    }

    private void configurarSpinners() {

        // Tipo
        String[] tipos = {"INGRESO", "GASTO"};
        ArrayAdapter<String> adapterTipos =
                new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, tipos);

        adapterTipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipo.setAdapter(adapterTipos);

        // Categorías (pueden venir de la BD en el futuro)
        String[] categorias = {"General", "Alimentación", "Transporte", "Ocio", "Casa"};
        ArrayAdapter<String> adapterCategorias =
                new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categorias);

        adapterCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(adapterCategorias);
    }

    private void configurarBoton() {
        btnGuardar.setOnClickListener(view -> {

            String descripcion = etDescripcion.getText().toString().trim();
            String cantidadStr = etCantidad.getText().toString().trim();
            String tipo = spTipo.getSelectedItem().toString();
            String categoria = spCategoria.getSelectedItem().toString();

            if (descripcion.isEmpty() || cantidadStr.isEmpty()) {
                Toast.makeText(getContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            double cantidad;
            try {
                cantidad = Double.parseDouble(cantidadStr);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Cantidad inválida", Toast.LENGTH_SHORT).show();
                return;
            }

            // Fecha en formato YYYY-MM-DD
            int dia = datePicker.getDayOfMonth();
            int mes = datePicker.getMonth() + 1;
            int anio = datePicker.getYear();
            String fecha = anio + "-" + (mes < 10 ? "0"+mes : mes) + "-" + (dia < 10 ? "0"+dia : dia);

            // Insertamos en BD
            GestorBaseDatos db = GestorBaseDatos.obtenerInstancia(getContext());
            Movimiento mov = new Movimiento(descripcion, cantidad, tipo, fecha, categoria);
            db.movimientoDao().insertar(mov);

            Toast.makeText(getContext(), "Movimiento guardado", Toast.LENGTH_SHORT).show();

            // Volver a la lista
            Navigation.findNavController(view)
                    .navigate(R.id.fragmentoMovimientos);
        });
    }
}
