package com.example.healthywallet.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "formacion")
public class Formacion {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String titulo;
    private String contenido;

    public Formacion(String titulo, String contenido) {
        this.titulo = titulo;
        this.contenido = contenido;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
}
