package com.example.healthywallet.database.entities;

public class CategoriaGasto {

    private String categoria;
    private double total;

    public CategoriaGasto(String categoria, double total) {
        this.categoria = categoria;
        this.total = total;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getTotal() {
        return total;
    }
}
