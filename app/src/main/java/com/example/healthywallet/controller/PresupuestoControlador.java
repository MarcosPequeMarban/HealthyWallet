package com.example.healthywallet.controller;

import android.content.Context;
import android.util.Log;

import com.example.healthywallet.database.GestorBaseDatos;
import com.example.healthywallet.database.DAO.PresupuestoDao;
import com.example.healthywallet.database.entities.Presupuesto;

import java.util.List;

public class PresupuestoControlador {

    private final PresupuestoDao presupuestoDao;

    public PresupuestoControlador(Context context) {
        GestorBaseDatos db = GestorBaseDatos.obtenerInstancia(context);
        this.presupuestoDao = db.presupuestoDao();
    }

    public long insertarPresupuesto(Presupuesto presupuesto) {
        try {
            return presupuestoDao.insertar(presupuesto);
        } catch (Exception e) {
            Log.e("PresupuestoControlador", "Error insertando presupuesto", e);
            return -1;
        }
    }

    public int actualizarPresupuesto(Presupuesto presupuesto) {
        try {
            return presupuestoDao.actualizar(presupuesto);
        } catch (Exception e) {
            Log.e("PresupuestoControlador", "Error actualizando presupuesto", e);
            return 0;
        }
    }

    public Presupuesto obtenerPresupuesto(int id) {
        try {
            Presupuesto p = presupuestoDao.obtenerPorId(id);
            if (p == null) Log.e("PresupuestoControlador", "Presupuesto no encontrado (id=" + id + ")");
            return p;
        } catch (Exception e) {
            Log.e("PresupuestoControlador", "Error obteniendo presupuesto", e);
            return null;
        }
    }

    public List<Presupuesto> obtenerTodos() {
        try {
            return presupuestoDao.obtenerTodos();
        } catch (Exception e) {
            Log.e("PresupuestoControlador", "Error obteniendo presupuestos", e);
            return null;
        }
    }

    public int actualizarGasto(int id, double nuevoGasto) {
        try {
            return presupuestoDao.actualizarGasto(id, nuevoGasto);
        } catch (Exception e) {
            Log.e("PresupuestoControlador", "Error actualizando gasto", e);
            return 0;
        }
    }

    public int eliminarPresupuesto(Presupuesto presupuesto) {
        try {
            return presupuestoDao.eliminar(presupuesto);
        } catch (Exception e) {
            Log.e("PresupuestoControlador", "Error eliminando presupuesto", e);
            return 0;
        }
    }
}
