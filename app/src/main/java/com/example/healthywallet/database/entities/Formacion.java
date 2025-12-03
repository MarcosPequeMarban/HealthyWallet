package com.example.healthywallet.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "formacion")
public class Formacion {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String titulo;
    private String url;
    private String nivel; // PRINCIPIANTE / INTERMEDIO / EXPERTO

    private boolean completado;     // ← IMPORTANTE
    private long fechaCompletado;   // ← IMPORTANTE

    private int userId; // MULTICUENTA

    public Formacion(String titulo, String url, String nivel, int userId) {
        this.titulo = titulo;
        this.url = url;
        this.nivel = nivel;
        this.userId = userId;
        this.completado = false;
        this.fechaCompletado = 0;
    }

    // ==== GETTERS ====
    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getUrl() { return url; }
    public String getNivel() { return nivel; }
    public boolean isCompletado() { return completado; }
    public long getFechaCompletado() { return fechaCompletado; }
    public int getUserId() { return userId; }

    // ==== SETTERS ====
    public void setId(int id) { this.id = id; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setUrl(String url) { this.url = url; }
    public void setNivel(String nivel) { this.nivel = nivel; }
    public void setCompletado(boolean completado) { this.completado = completado; }
    public void setFechaCompletado(long fechaCompletado) { this.fechaCompletado = fechaCompletado; }
    public void setUserId(int userId) { this.userId = userId; }
}
