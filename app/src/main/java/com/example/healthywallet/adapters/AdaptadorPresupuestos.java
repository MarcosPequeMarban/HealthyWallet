package com.example.healthywallet.adapters;

import android.content.Context;
import android.graphics.Color;
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

    // ==========================================================
    //     LISTENER DE MANTENER PULSADO (NUEVO)
    // ==========================================================
    public interface OnPresupuestoLongClickListener {
        void onLongClick(Presupuesto presupuesto);
    }

    private OnPresupuestoLongClickListener longClickListener;

    public void setOnPresupuestoLongClickListener(OnPresupuestoLongClickListener listener) {
        this.longClickListener = listener;
    }
    // ==========================================================

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

        double limite = p.getLimite();
        double gasto = p.getGastoActual();

        // 🔥 COLOR DINÁMICO SEGÚN EL NIVEL DE GASTO
        if (gasto > limite) {
            holder.itemView.setBackgroundColor(Color.parseColor("#FF8A80"));
        }
        else if (gasto >= limite * 0.80) {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFF176"));
        }
        else {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        // ==========================================================
        //     AÑADIMOS LA PULSACIÓN LARGA (NUEVO)
        // ==========================================================
        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.onLongClick(p);
            }
            return true; // consumimos el evento
        });
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
