package com.example.healthywallet.controller;

import android.content.Context;

import com.example.healthywallet.database.DAO.CategoriaDao;
import com.example.healthywallet.database.GestorBaseDatos;
import com.example.healthywallet.database.entities.Categoria;

import java.util.List;

public class CategoriasControlador {

    private final CategoriaDao categoriaDao;

    public CategoriasControlador(Context context) {
        GestorBaseDatos db = GestorBaseDatos.obtenerInstancia(context);
        categoriaDao = db.categoriaDao();
    }

    public void insertar(Categoria categoria) {
        categoriaDao.insertar(categoria);
    }

    public List<Categoria> obtenerTodas() {
        return categoriaDao.obtenerTodas();
    }
}

