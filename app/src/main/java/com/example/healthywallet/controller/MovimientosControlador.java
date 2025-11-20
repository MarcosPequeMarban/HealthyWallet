package com.example.healthywallet.controller;

import android.content.Context;
import android.util.Log;

import com.example.healthywallet.database.GestorBaseDatos;
import com.example.healthywallet.database.DAO.MovimientoDao;
import com.example.healthywallet.database.entities.Movimiento;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class MovimientosControlador {

    public interface CallbackMovimiento {
        void onResult(Movimiento mov);
    }

    public interface CallbackListaMovimiento {
        void onResult(List<Movimiento> lista);
    }

    public interface CallbackDouble {
        void onResult(double valor);
    }

    public interface CallbackInt {
        void onResult(int valor);
    }

    private final MovimientoDao dao;
    private final ExecutorService executor;

    public MovimientosControlador(Context context) {
        GestorBaseDatos db = GestorBaseDatos.obtenerInstancia(context);
        dao = db.movimientoDao();
        executor = GestorBaseDatos.databaseExecutor;
    }

    // Insertar
    public void insertar(Movimiento mov, CallbackInt callback) {
        executor.execute(() -> {
            int result = (int) dao.insertar(mov);
            callback.onResult(result);
        });
    }

    // Actualizar
    public void actualizar(Movimiento mov, CallbackInt callback) {
        executor.execute(() -> {
            int result = dao.actualizar(mov);
            callback.onResult(result);
        });
    }

    // Obtener por ID
    public void obtenerPorId(int id, CallbackMovimiento callback) {
        executor.execute(() -> {
            Movimiento mov = dao.obtenerPorId(id);
            callback.onResult(mov);
        });
    }

    // Obtener todos
    public void obtenerTodos(CallbackListaMovimiento callback) {
        executor.execute(() -> callback.onResult(dao.obtenerTodos()));
    }

    // Obtener por tipo
    public void obtenerPorTipo(String tipo, CallbackListaMovimiento callback) {
        executor.execute(() -> callback.onResult(dao.obtenerPorTipo(tipo)));
    }

    // Obtener suma por tipo
    public void obtenerSumaPorTipo(String tipo, CallbackDouble callback) {
        executor.execute(() -> {
            Double valor = dao.obtenerSumaPorTipo(tipo);
            callback.onResult(valor != null ? valor : 0.0);
        });
    }

}
