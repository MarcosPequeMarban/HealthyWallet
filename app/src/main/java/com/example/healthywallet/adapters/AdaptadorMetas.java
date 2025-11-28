package com.example.healthywallet.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthywallet.R;
import com.example.healthywallet.database.entities.Meta;

import java.util.List;

public class AdaptadorMetas extends RecyclerView.Adapter<AdaptadorMetas.ViewHolderMeta> {

    private final Context context;
    private final List<Meta> lista;

    private OnMetaClickListener listenerNormal;
    private OnMetaCompletadaListener listenerCompletada;

    // ---------------------------------------------------------
    // LISTENER PARA ABRIR EL DETALLE
    // ---------------------------------------------------------
    public interface OnMetaClickListener {
        void onMetaClick(Meta meta);
    }

    public void setOnMetaClickListener(OnMetaClickListener l) {
        this.listenerNormal = l;
    }

    // ---------------------------------------------------------
    // LISTENER PARA META COMPLETADA (100%)
    // ---------------------------------------------------------
    public interface OnMetaCompletadaListener {
        void onMetaCompletada(Meta meta);
    }

    public void setOnMetaCompletadaListener(OnMetaCompletadaListener l) {
        this.listenerCompletada = l;
    }

    // ---------------------------------------------------------
    // CONSTRUCTOR
    // ---------------------------------------------------------
    public AdaptadorMetas(Context context, List<Meta> lista) {
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolderMeta onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_meta, parent, false);
        return new ViewHolderMeta(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMeta holder, int position) {

        Meta meta = lista.get(position);

        // NOMBRE
        holder.txtNombre.setText(meta.getNombre());

        // FECHA OBJETIVO
        holder.txtFecha.setText("Fecha objetivo: " + meta.getFechaObjetivo());

        // CANTIDADES
        holder.txtCantidades.setText(
                String.format("%.2f € / %.2f €", meta.getCantidadActual(), meta.getCantidadObjetivo())
        );

        // PORCENTAJE
        double porcentaje = (meta.getCantidadActual() / meta.getCantidadObjetivo()) * 100.0;
        if (porcentaje > 100) porcentaje = 100;

        holder.txtPorcentaje.setText(String.format("%.0f%%", porcentaje));
        holder.barra.setProgress((int) porcentaje);

        // ---------------------------------------------------------
        // COLORES SEGÚN PROGRESO
        // ---------------------------------------------------------
        if (porcentaje >= 100) {
            holder.barra.setProgressTintList(context.getColorStateList(R.color.meta_verde));
            holder.card.setCardBackgroundColor(Color.parseColor("#D1FFD8")); // verde triunfo
        }
        else if (porcentaje >= 80) {
            holder.barra.setProgressTintList(context.getColorStateList(R.color.meta_verde));
            holder.card.setCardBackgroundColor(Color.parseColor("#E8F5E9"));
        }
        else if (porcentaje >= 40) {
            holder.barra.setProgressTintList(context.getColorStateList(R.color.meta_amarillo));
            holder.card.setCardBackgroundColor(Color.parseColor("#FFFDE7"));
        }
        else {
            holder.barra.setProgressTintList(context.getColorStateList(R.color.meta_rojo));
            holder.card.setCardBackgroundColor(Color.parseColor("#FFEBEE"));
        }

        // ---------------------------------------------------------
        // CLICK (NORMAL o META COMPLETADA)
        // ---------------------------------------------------------
        final double porcentajeFinal = porcentaje;
        holder.itemView.setOnClickListener(v -> {

            if (porcentajeFinal >= 100) {
                if (listenerCompletada != null)
                    listenerCompletada.onMetaCompletada(meta);
            } else {
                if (listenerNormal != null)
                    listenerNormal.onMetaClick(meta);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    // ---------------------------------------------------------
    // VIEW HOLDER
    // ---------------------------------------------------------
    static class ViewHolderMeta extends RecyclerView.ViewHolder {

        CardView card;
        TextView txtNombre, txtCantidades, txtPorcentaje, txtFecha;
        ProgressBar barra;

        public ViewHolderMeta(@NonNull View itemView) {
            super(itemView);

            card = itemView.findViewById(R.id.cardViewMeta);
            txtNombre = itemView.findViewById(R.id.txtNombreMeta);
            txtCantidades = itemView.findViewById(R.id.txtCantidadesMeta);
            txtPorcentaje = itemView.findViewById(R.id.txtPorcentajeMeta);
            txtFecha = itemView.findViewById(R.id.txtFechaMeta);
            barra = itemView.findViewById(R.id.barraProgresoMeta);
        }
    }
}
