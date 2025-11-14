package com.example.healthywallet.controller;

import android.content.Context;

import com.example.healthywallet.database.DAO.PresupuestoDao;
import com.example.healthywallet.database.GestorBaseDatos;
import com.example.healthywallet.database.entities.Presupuesto;

import java.util.List;

public class PresupuestoControlador {

    private final PresupuestoDao presupuestoDao;

    public PresupuestoControlador(Context context) {
        GestorBaseDatos db = GestorBaseDatos.obtenerInstancia(context);
        presupuestoDao = db.presupuestoDao();
    }

    public void insertar(Presupuesto presupuesto) {
        presupuestoDao.insertar(presupuesto);
    }

    public List<Presupuesto> obtenerTodos() {
        return presupuestoDao.obtenerTodos();
    }
}
