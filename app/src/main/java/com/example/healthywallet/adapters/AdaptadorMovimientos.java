package com.example.healthywallet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthywallet.R;
import com.example.healthywallet.database.entities.Movimiento;

import java.util.List;

public class AdaptadorMovimientos extends RecyclerView.Adapter<AdaptadorMovimientos.ViewHolderMovimiento> {

    private final Context context;
    private final List<Movimiento> lista;

    public AdaptadorMovimientos(Context context, List<Movimiento> lista) {
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolderMovimiento onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(context).inflate(R.layout.item_movimiento, parent, false);
        return new ViewHolderMovimiento(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMovimiento holder, int position) {
        Movimiento mov = lista.get(position);

        holder.txtTipo.setText(mov.getTipo());
        holder.txtCategoria.setText(mov.getCategoria());
        holder.txtCantidad.setText(String.valueOf(mov.getCantidad()) + " €");
        holder.txtFecha.setText(mov.getFecha());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class ViewHolderMovimiento extends RecyclerView.ViewHolder {

        TextView txtTipo, txtCategoria, txtCantidad, txtFecha;

        public ViewHolderMovimiento(@NonNull View itemView) {
            super(itemView);

            txtTipo = itemView.findViewById(R.id.txtTipoMovimiento);
            txtCategoria = itemView.findViewById(R.id.txtCategoriaMovimiento);
            txtCantidad = itemView.findViewById(R.id.txtCantidadMovimiento);
            txtFecha = itemView.findViewById(R.id.txtFechaMovimiento);
        }
    }
}
