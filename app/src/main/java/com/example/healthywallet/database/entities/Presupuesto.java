package com.example.healthywallet.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "presupuestos",
        foreignKeys = @ForeignKey(
                entity = Usuario.class,
                parentColumns = "id",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE
        )
)
public class Presupuesto {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "categoria")
    private String categoria;

    @ColumnInfo(name = "limite")
    private double limite;

    @ColumnInfo(name = "gastoActual")
    private double gastoActual;

    @ColumnInfo(name = "userId", index = true)
    private int userId; // ← NUEVO: identifica al dueño del presupuesto

    // Constructor principal
    public Presupuesto(String categoria, double limite, double gastoActual, int userId) {
        this.categoria = categoria;
        this.limite = limite;
        this.gastoActual = gastoActual;
        this.userId = userId;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public double getLimite() { return limite; }
    public void setLimite(double limite) { this.limite = limite; }

    public double getGastoActual() { return gastoActual; }
    public void setGastoActual(double gastoActual) { this.gastoActual = gastoActual; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}
