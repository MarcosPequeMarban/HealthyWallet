package com.example.healthywallet.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "movimientos",
        foreignKeys = @ForeignKey(
                entity = Usuario.class,
                parentColumns = "id",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE
        )
)
public class Movimiento {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "tipo")
    private String tipo;

    @ColumnInfo(name = "categoria")
    private String categoria;

    @ColumnInfo(name = "cantidad")
    private double cantidad;

    @ColumnInfo(name = "fecha")
    private String fecha;

    @ColumnInfo(name = "descripcion")
    private String descripcion;

    @ColumnInfo(name = "userId", index = true)
    private int userId;  // ← NUEVO: para asociar este movimiento al usuario

    public Movimiento(String tipo, String categoria, double cantidad, String fecha, String descripcion, int userId) {
        this.tipo = tipo;
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.userId = userId;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public double getCantidad() { return cantidad; }
    public void setCantidad(double cantidad) { this.cantidad = cantidad; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}
