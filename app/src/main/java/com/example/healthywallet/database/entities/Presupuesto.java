package com.example.healthywallet.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "presupuestos")
public class Presupuesto {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nombre;
    private double limite;
    private double gastado;

    public Presupuesto(String nombre, double limite, double gastado) {
        this.nombre = nombre;
        this.limite = limite;
        this.gastado = gastado;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getLimite() { return limite; }
    public void setLimite(double limite) { this.limite = limite; }

    public double getGastado() { return gastado; }
    public void setGastado(double gastado) { this.gastado = gastado; }
}
