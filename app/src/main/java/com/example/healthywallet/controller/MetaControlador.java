package com.example.healthywallet.controller;

import android.content.Context;

import com.example.healthywallet.database.DAO.MetaDao;
import com.example.healthywallet.database.GestorBaseDatos;
import com.example.healthywallet.database.entities.Meta;

import java.util.List;

public class MetaControlador {

    private final MetaDao metaDao;

    public MetaControlador(Context context) {
        GestorBaseDatos db = GestorBaseDatos.obtenerInstancia(context);
        metaDao = db.metaDao();
    }

    public void insertar(Meta meta) {
        metaDao.insertar(meta);
    }

    public List<Meta> obtenerTodas() {
        return metaDao.obtenerTodas();
    }
}
