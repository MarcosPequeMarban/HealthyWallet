package com.example.healthywallet.controller;

import android.content.Context;

import com.example.healthywallet.database.DAO.MovimientoDao;
import com.example.healthywallet.database.GestorBaseDatos;
import com.example.healthywallet.database.entities.Movimiento;

import java.util.List;

public class MovimientosControlador {

    private final MovimientoDao movimientoDao;

    public MovimientosControlador(Context context) {
        GestorBaseDatos db = GestorBaseDatos.obtenerInstancia(context);
        movimientoDao = db.movimientoDao();
    }

    // ---------------------------------------------------------
    // INSERTAR
    // ---------------------------------------------------------
    public void insertarMovimiento(Movimiento movimiento) {
        movimientoDao.insertar(movimiento);
    }

    // ---------------------------------------------------------
    // OBTENER TODOS
    // ---------------------------------------------------------
    public List<Movimiento> obtenerTodos() {
        return movimientoDao.obtenerTodos();
    }

    // ---------------------------------------------------------
    // OBTENER POR TIPO (GASTO / INGRESO)
    // ---------------------------------------------------------
    public List<Movimiento> obtenerPorTipo(String tipo) {
        return movimientoDao.obtenerPorTipo(tipo);
    }

    // ---------------------------------------------------------
    // ACTUALIZAR
    // ---------------------------------------------------------
    public void actualizarMovimiento(Movimiento movimiento) {
        movimientoDao.actualizar(movimiento);
    }

    // ---------------------------------------------------------
    // ELIMINAR
    // ---------------------------------------------------------
    public void eliminarMovimiento(Movimiento movimiento) {
        movimientoDao.eliminar(movimiento);
    }

    // ---------------------------------------------------------
    // SUMA TOTAL POR TIPO (SAFE NULL)
    // ---------------------------------------------------------
    public double obtenerTotalPorTipo(String tipo) {

        Double total = movimientoDao.obtenerSumaPorTipo(tipo);

        if (total == null) {
            return 0.0;
        }
        return total;
    }

    // ---------------------------------------------------------
    // BALANCE (INGRESOS – GASTOS)
    // ---------------------------------------------------------
    public double obtenerBalanceGeneral() {
        double ingresos = obtenerTotalPorTipo("INGRESO");
        double gastos = obtenerTotalPorTipo("GASTO");
        return ingresos - gastos;
    }
}
