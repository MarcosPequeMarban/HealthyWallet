package com.example.healthywallet.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "metas")
public class Meta {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "nombre")
    private String nombre;

    @ColumnInfo(name = "cantidadObjetivo")
    private double cantidadObjetivo;

    @ColumnInfo(name = "cantidadActual")
    private double cantidadActual;

    @ColumnInfo(name = "fechaObjetivo")
    private String fechaObjetivo;

    public Meta(String nombre, double cantidadObjetivo, double cantidadActual, String fechaObjetivo) {
        this.nombre = nombre;
        this.cantidadObjetivo = cantidadObjetivo;
        this.cantidadActual = cantidadActual;
        this.fechaObjetivo = fechaObjetivo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getCantidadObjetivo() { return cantidadObjetivo; }
    public void setCantidadObjetivo(double cantidadObjetivo) { this.cantidadObjetivo = cantidadObjetivo; }

    public double getCantidadActual() { return cantidadActual; }
    public void setCantidadActual(double cantidadActual) { this.cantidadActual = cantidadActual; }

    public String getFechaObjetivo() { return fechaObjetivo; }
    public void setFechaObjetivo(String fechaObjetivo) { this.fechaObjetivo = fechaObjetivo; }
}
