package com.example.healthywallet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthywallet.R;
import com.example.healthywallet.database.entities.Presupuesto;

import java.util.List;

public class AdaptadorPresupuestos extends RecyclerView.Adapter<AdaptadorPresupuestos.ViewHolderPresupuesto> {

    private final Context context;
    private final List<Presupuesto> lista;

    public AdaptadorPresupuestos(Context context, List<Presupuesto> lista) {
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolderPresupuesto onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(context).inflate(R.layout.item_presupuesto, parent, false);
        return new ViewHolderPresupuesto(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPresupuesto holder, int position) {
        Presupuesto p = lista.get(position);

        holder.txtCategoria.setText(p.getCategoria());
        holder.txtLimite.setText("Límite: " + p.getLimite() + " €");
        holder.txtGastoActual.setText("Gastado: " + p.getGastoActual() + " €");
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class ViewHolderPresupuesto extends RecyclerView.ViewHolder {

        TextView txtCategoria, txtLimite, txtGastoActual;

        public ViewHolderPresupuesto(@NonNull View itemView) {
            super(itemView);

            txtCategoria = itemView.findViewById(R.id.txtCategoriaPresupuesto);
            txtLimite = itemView.findViewById(R.id.txtLimitePresupuesto);
            txtGastoActual = itemView.findViewById(R.id.txtGastoActualPresupuesto);
        }
    }
}
