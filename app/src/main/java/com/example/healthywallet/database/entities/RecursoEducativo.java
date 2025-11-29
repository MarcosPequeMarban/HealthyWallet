package com.example.healthywallet.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recursos_educativos")
public class RecursoEducativo {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String tipo; // "video", "libro", "herramienta"
    private String titulo;
    private String url;

    private boolean completado;
    private long fechaCompletado;

    private long fechaRecomendado; // Para renovación cada 14 días

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public boolean isCompletado() { return completado; }
    public void setCompletado(boolean completado) { this.completado = completado; }

    public long getFechaCompletado() { return fechaCompletado; }
    public void setFechaCompletado(long fechaCompletado) { this.fechaCompletado = fechaCompletado; }

    public long getFechaRecomendado() { return fechaRecomendado; }
    public void setFechaRecomendado(long fechaRecomendado) { this.fechaRecomendado = fechaRecomendado; }
}
