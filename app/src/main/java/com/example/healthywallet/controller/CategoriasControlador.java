package com.example.healthywallet.controller;

import android.content.Context;
import android.util.Log;

import com.example.healthywallet.database.GestorBaseDatos;
import com.example.healthywallet.database.DAO.CategoriaDao;
import com.example.healthywallet.database.entities.Categoria;

import java.util.List;

public class CategoriasControlador {

    private final CategoriaDao categoriaDao;

    public CategoriasControlador(Context context) {
        GestorBaseDatos db = GestorBaseDatos.obtenerInstancia(context);
        this.categoriaDao = db.categoriaDao();
    }

    public long insertarCategoria(Categoria categoria) {
        try {
            return categoriaDao.insertar(categoria);
        } catch (Exception e) {
            Log.e("CategoriaControlador", "Error insertando categoría", e);
            return -1;
        }
    }

    public List<Categoria> obtenerCategorias() {
        try {
            return categoriaDao.obtenerTodas();
        } catch (Exception e) {
            Log.e("CategoriaControlador", "Error obteniendo categorías", e);
            return null;
        }
    }

    public int eliminarCategoria(int id) {
        try {
            return categoriaDao.eliminarPorId(id);
        } catch (Exception e) {
            Log.e("CategoriaControlador", "Error eliminando categoría", e);
            return 0;
        }
    }
}
