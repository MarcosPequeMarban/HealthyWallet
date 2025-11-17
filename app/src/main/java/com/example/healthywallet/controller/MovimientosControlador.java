package com.example.healthywallet.controller;

import android.content.Context;
import android.util.Log;

import com.example.healthywallet.database.GestorBaseDatos;
import com.example.healthywallet.database.DAO.MovimientoDao;
import com.example.healthywallet.database.entities.Movimiento;

import java.util.List;

public class MovimientosControlador {

    private final MovimientoDao movimientoDao;

    public MovimientosControlador(Context context) {
        GestorBaseDatos db = GestorBaseDatos.obtenerInstancia(context);
        this.movimientoDao = db.movimientoDao();
    }

    public long insertarMovimiento(Movimiento mov) {
        try {
            return movimientoDao.insertar(mov);
        } catch (Exception e) {
            Log.e("MovimientosControlador", "Error insertando movimiento", e);
            return -1;
        }
    }

    public int actualizarMovimiento(Movimiento mov) {
        try {
            return movimientoDao.actualizar(mov);
        } catch (Exception e) {
            Log.e("MovimientosControlador", "Error actualizando movimiento", e);
            return 0;
        }
    }

    public Movimiento obtenerMovimiento(int id) {
        try {
            Movimiento mov = movimientoDao.obtenerPorId(id);
            if (mov == null) Log.e("MovimientosControlador", "Movimiento no encontrado (id=" + id + ")");
            return mov;
        } catch (Exception e) {
            Log.e("MovimientosControlador", "Error obteniendo movimiento", e);
            return null;
        }
    }

    public List<Movimiento> obtenerTodos() {
        try {
            return movimientoDao.obtenerTodos();
        } catch (Exception e) {
            Log.e("MovimientosControlador", "Error obteniendo movimientos", e);
            return null;
        }
    }

    public List<Movimiento> obtenerPorTipo(String tipo) {
        try {
            return movimientoDao.obtenerPorTipo(tipo);
        } catch (Exception e) {
            Log.e("MovimientosControlador", "Error filtrando movimientos por tipo", e);
            return null;
        }
    }

    public Double obtenerSumaPorTipo(String tipo) {
        try {
            Double suma = movimientoDao.obtenerSumaPorTipo(tipo);
            return suma == null ? 0.0 : suma;
        } catch (Exception e) {
            Log.e("MovimientosControlador", "Error obteniendo suma", e);
            return 0.0;
        }
    }

    public int eliminarMovimiento(Movimiento mov) {
        try {
            return movimientoDao.eliminar(mov);
        } catch (Exception e) {
            Log.e("MovimientosControlador", "Error eliminando movimiento", e);
            return 0;
        }
    }
}
