package com.example.healthywallet.controller;

import android.content.Context;

import com.example.healthywallet.database.DAO.FormacionDao;
import com.example.healthywallet.database.GestorBaseDatos;
import com.example.healthywallet.database.entities.Formacion;

import java.util.List;

public class FormacionControlador {

    private final FormacionDao formacionDao;

    public FormacionControlador(Context context) {
        GestorBaseDatos db = GestorBaseDatos.obtenerInstancia(context);
        formacionDao = db.formacionDao();
    }

    public void insertar(Formacion formacion) {
        formacionDao.insertar(formacion);
    }

    public List<Formacion> obtenerTodas() {
        return formacionDao.obtenerTodas();
    }
}

