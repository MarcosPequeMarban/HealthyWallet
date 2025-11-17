package com.example.healthywallet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthywallet.R;
import com.example.healthywallet.database.entities.Categoria;

import java.util.List;

public class AdaptadorCategorias extends RecyclerView.Adapter<AdaptadorCategorias.ViewHolderCategoria> {

    private final Context context;
    private final List<Categoria> lista;

    public AdaptadorCategorias(Context context, List<Categoria> lista) {
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolderCategoria onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(context).inflate(R.layout.item_categoria, parent, false);
        return new ViewHolderCategoria(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCategoria holder, int position) {
        Categoria categoria = lista.get(position);

        holder.txtNombre.setText(categoria.getNombre());
        holder.txtDescripcion.setText(categoria.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class ViewHolderCategoria extends RecyclerView.ViewHolder {

        TextView txtNombre, txtDescripcion;

        public ViewHolderCategoria(@NonNull View itemView) {
            super(itemView);

            txtNombre = itemView.findViewById(R.id.txtNombreCategoria);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcionCategoria);
        }
    }
}
