package com.example.healthywallet.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movimientos")
public class Movimiento {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String descripcion;
    private double cantidad;
    private String tipo; // "INGRESO" o "GASTO"
    private String fecha;
    private String categoria;

    // Constructor
    public Movimiento(String descripcion, double cantidad, String tipo, String fecha, String categoria) {
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.tipo = tipo;
        this.fecha = fecha;
        this.categoria = categoria;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getCantidad() { return cantidad; }
    public void setCantidad(double cantidad) { this.cantidad = cantidad; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}