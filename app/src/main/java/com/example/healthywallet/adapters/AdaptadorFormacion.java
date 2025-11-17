package com.example.healthywallet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthywallet.R;
import com.example.healthywallet.database.entities.Formacion;

import java.util.List;

public class AdaptadorFormacion extends RecyclerView.Adapter<AdaptadorFormacion.ViewHolderFormacion> {

    private final Context context;
    private final List<Formacion> lista;

    public AdaptadorFormacion(Context context, List<Formacion> lista) {
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolderFormacion onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(context).inflate(R.layout.item_formacion, parent, false);
        return new ViewHolderFormacion(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFormacion holder, int position) {
        Formacion f = lista.get(position);

        holder.txtTitulo.setText(f.getTitulo());
        holder.txtNivel.setText(f.getNivel());
        holder.txtDescripcion.setText(f.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class ViewHolderFormacion extends RecyclerView.ViewHolder {

        TextView txtTitulo, txtNivel, txtDescripcion;

        public ViewHolderFormacion(@NonNull View itemView) {
            super(itemView);

            txtTitulo = itemView.findViewById(R.id.txtTituloFormacion);
            txtNivel = itemView.findViewById(R.id.txtNivelFormacion);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcionFormacion);
        }
    }
}
