package com.example.healthywallet.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthywallet.R;
import com.example.healthywallet.database.entities.Formacion;

import java.util.List;

public class AdaptadorVideos extends RecyclerView.Adapter<AdaptadorVideos.VideoViewHolder> {

    /** Callback cuando un video se marca o desmarca */
    public interface OnCheckListener {
        void onChecked(Formacion video, boolean nuevoEstado);
    }

    private List<Formacion> lista;
    private final OnCheckListener listener;

    public AdaptadorVideos(List<Formacion> lista, OnCheckListener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video, parent, false);

        return new VideoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Formacion video = lista.get(position);

        holder.txtTitulo.setText(video.getTitulo());

        // Evitar conflictos cuando el RecyclerView recicla las vistas
        holder.chkCompletado.setOnCheckedChangeListener(null);
        holder.chkCompletado.setChecked(video.isCompletado());

        holder.chkCompletado.setOnCheckedChangeListener((button, marcado) -> {
            listener.onChecked(video, marcado);
        });

        // Permite pulsar en toda la tarjeta para marcar/desmarcar
        holder.itemView.setOnClickListener(v -> {
            boolean nuevoEstado = !holder.chkCompletado.isChecked();
            holder.chkCompletado.setChecked(nuevoEstado);
            listener.onChecked(video, nuevoEstado);
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void actualizar(List<Formacion> nuevaLista) {
        this.lista = nuevaLista;
        notifyDataSetChanged();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitulo;
        CheckBox chkCompletado;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitulo = itemView.findViewById(R.id.txtTituloVideo);
            chkCompletado = itemView.findViewById(R.id.checkCompletado);

            // Ripple cuando pulsas el item
            itemView.setForeground(
                    itemView.getContext().getDrawable(
                            androidx.appcompat.R.drawable.abc_list_selector_holo_light
                    )
            );
        }
    }
}
