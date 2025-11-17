package com.example.healthywallet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthywallet.R;
import com.example.healthywallet.database.entities.Meta;

import java.util.List;

public class AdaptadorMetas extends RecyclerView.Adapter<AdaptadorMetas.ViewHolderMeta> {

    private final Context context;
    private final List<Meta> lista;

    public AdaptadorMetas(Context context, List<Meta> lista) {
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolderMeta onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(context).inflate(R.layout.item_meta, parent, false);
        return new ViewHolderMeta(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMeta holder, int position) {
        Meta meta = lista.get(position);

        holder.txtNombre.setText(meta.getNombre());
        holder.txtCantidad.setText(meta.getCantidadActual() + " / " + meta.getCantidadObjetivo());
        holder.txtFecha.setText(meta.getFechaObjetivo());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class ViewHolderMeta extends RecyclerView.ViewHolder {

        TextView txtNombre, txtCantidad, txtFecha;

        public ViewHolderMeta(@NonNull View itemView) {
            super(itemView);

            txtNombre = itemView.findViewById(R.id.txtNombreMeta);
            txtCantidad = itemView.findViewById(R.id.txtCantidadMeta);
            txtFecha = itemView.findViewById(R.id.txtFechaMeta);
        }
    }
}
