package com.example.healthywallet.controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.healthywallet.database.GestorBaseDatos;
import com.example.healthywallet.database.DAO.MovimientoDao;
import com.example.healthywallet.database.entities.Movimiento;
import com.example.healthywallet.database.entities.CategoriaGasto;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class MovimientosControlador {

    // === CALLBACKS ===
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

    public interface CallbackListaCategoriaGasto {
        void onResult(List<CategoriaGasto> lista);
    }

    // === CAMPOS ===
    private final MovimientoDao dao;
    private final ExecutorService executor;
    private final int userId;

    // === CONSTRUCTOR ===
    public MovimientosControlador(Context context) {
        GestorBaseDatos db = GestorBaseDatos.obtenerInstancia(context);
        dao = db.movimientoDao();
        executor = GestorBaseDatos.databaseExecutor;

        SharedPreferences prefs = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        userId = prefs.getInt("usuarioId", -1);
    }

    // === CRUD ===
    public void insertar(Movimiento mov, CallbackInt callback) {
        mov.setUserId(userId);
        executor.execute(() -> {
            int result = (int) dao.insertar(mov);
            callback.onResult(result);
        });
    }

    public void actualizar(Movimiento mov, CallbackInt callback) {
        mov.setUserId(userId);
        executor.execute(() -> {
            int result = dao.actualizar(mov);
            callback.onResult(result);
        });
    }

    public void obtenerPorId(int id, CallbackMovimiento callback) {
        executor.execute(() -> {
            Movimiento mov = dao.obtenerPorId(id, userId);
            callback.onResult(mov);
        });
    }

    public void obtenerTodos(CallbackListaMovimiento callback) {
        executor.execute(() -> {
            List<Movimiento> lista = dao.obtenerTodos(userId);
            callback.onResult(lista);
        });
    }



    public void obtenerSumaPorTipo(String tipo, CallbackDouble callback) {
        executor.execute(() -> {
            Double valor = dao.obtenerSumaPorTipo(tipo, userId);
            callback.onResult(valor != null ? valor : 0.0);
        });
    }


    public void obtenerGastosPorCategoria(CallbackListaCategoriaGasto callback) {
        executor.execute(() -> {
            List<CategoriaGasto> lista = dao.obtenerGastosPorCategoria(userId);
            callback.onResult(lista);
        });
    }

    public void eliminar(Movimiento mov, CallbackInt callback) {
        executor.execute(() -> {
            int filas = dao.eliminar(mov);
            callback.onResult(filas);
        });
    }
}
