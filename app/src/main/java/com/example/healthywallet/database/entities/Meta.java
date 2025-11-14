package com.example.healthywallet.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "metas")
public class Meta {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String titulo;
    private double objetivo;
    private double progreso;

    public Meta(String titulo, double objetivo, double progreso) {
        this.titulo = titulo;
        this.objetivo = objetivo;
        this.progreso = progreso;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public double getObjetivo() { return objetivo; }
    public void setObjetivo(double objetivo) { this.objetivo = objetivo; }

    public double getProgreso() { return progreso; }
    public void setProgreso(double progreso) { this.progreso = progreso; }
}
