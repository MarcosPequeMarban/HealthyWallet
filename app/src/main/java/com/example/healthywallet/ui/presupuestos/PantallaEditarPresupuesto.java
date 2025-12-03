package com.example.healthywallet.ui.presupuestos;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.healthywallet.R;
import com.example.healthywallet.controller.PresupuestoControlador;
import com.example.healthywallet.database.entities.Presupuesto;

public class PantallaEditarPresupuesto extends Fragment {

    private EditText edtNombre, edtLimite;
    private Button btnGuardar;

    private PresupuestoControlador controlador;
    private Presupuesto presupuestoActual;
    private int presupuestoId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.pantalla_editar_presupuesto, container, false);

        edtNombre = vista.findViewById(R.id.edtNombreEditarPresupuesto);
        edtLimite = vista.findViewById(R.id.edtLimiteEditarPresupuesto);
        btnGuardar = vista.findViewById(R.id.btnGuardarCambiosPresupuesto);

        controlador = new PresupuestoControlador(requireContext());

        // --- Recibir ID del presupuesto ---
        Bundle args = getArguments();
        if (args == null || !args.containsKey("presupuestoId")) {
            Toast.makeText(requireContext(), "Error: presupuesto no encontrado", Toast.LENGTH_SHORT).show();
            return vista;
        }

        presupuestoId = args.getInt("presupuestoId");

        cargarPresupuesto(); // carga inicial

        btnGuardar.setOnClickListener(v -> guardarCambios());

        return vista;
    }

    private void cargarPresupuesto() {

        controlador.obtenerPorId(presupuestoId, presupuesto ->
                requireActivity().runOnUiThread(() -> {

                    if (presupuesto == null) {
                        Toast.makeText(requireContext(), "No se pudo cargar el presupuesto", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    presupuestoActual = presupuesto;

                    // Mostrar datos en los campos
                    edtNombre.setText(presupuesto.getCategoria());
                    edtLimite.setText(String.valueOf(presupuesto.getLimite()));
                })
        );
    }

    private void guardarCambios() {

        String nuevoNombre = edtNombre.getText().toString().trim();
        String nuevoLimiteStr = edtLimite.getText().toString().trim();

        // Validación
        if (TextUtils.isEmpty(nuevoNombre)) {
            edtNombre.setError("Introduce un nombre");
            return;
        }

        if (TextUtils.isEmpty(nuevoLimiteStr)) {
            edtLimite.setError("Introduce un límite");
            return;
        }

        double nuevoLimite;

        try {
            nuevoLimite = Double.parseDouble(nuevoLimiteStr);
        } catch (NumberFormatException e) {
            edtLimite.setError("Cantidad no válida");
            return;
        }

        // Actualizar objeto
        presupuestoActual.setCategoria(nuevoNombre);
        presupuestoActual.setLimite(nuevoLimite);

        // Guardar en BD
        controlador.actualizar(presupuestoActual, filas ->
                requireActivity().runOnUiThread(() -> {

                    if (filas > 0) {
                        Toast.makeText(requireContext(), "Presupuesto actualizado", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(requireView()).navigateUp();
                    } else {
                        Toast.makeText(requireContext(), "Error al actualizar", Toast.LENGTH_SHORT).show();
                    }

                })
        );
    }
}
