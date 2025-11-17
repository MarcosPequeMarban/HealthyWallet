package com.example.healthywallet.controller;

import android.content.Context;
import android.util.Log;

import com.example.healthywallet.database.GestorBaseDatos;
import com.example.healthywallet.database.DAO.FormacionDao;
import com.example.healthywallet.database.entities.Formacion;

import java.util.List;

public class FormacionControlador {

    private final FormacionDao formacionDao;

    public FormacionControlador(Context context) {
        GestorBaseDatos db = GestorBaseDatos.obtenerInstancia(context);
        this.formacionDao = db.formacionDao();
    }

    public long insertarFormacion(Formacion formacion) {
        try {
            return formacionDao.insertar(formacion);
        } catch (Exception e) {
            Log.e("FormacionControlador", "Error insertando formación", e);
            return -1;
        }
    }

    public List<Formacion> obtenerTodas() {
        try {
            return formacionDao.obtenerTodas();
        } catch (Exception e) {
            Log.e("FormacionControlador", "Error obteniendo formaciones", e);
            return null;
        }
    }

    public int eliminarFormacion(int id) {
        try {
            return formacionDao.eliminarPorId(id);
        } catch (Exception e) {
            Log.e("FormacionControlador", "Error eliminando formación", e);
            return 0;
        }
    }
}
