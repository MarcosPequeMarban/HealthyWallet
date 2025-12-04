package com.example.healthywallet.controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.healthywallet.database.GestorBaseDatos;
import com.example.healthywallet.database.DAO.PresupuestoDao;
import com.example.healthywallet.database.entities.Presupuesto;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class PresupuestoControlador {

    // === CALLBACKS ===
    public interface CallbackListaPresupuesto {
        void onResult(List<Presupuesto> lista);
    }

    public interface CallbackPresupuesto {
        void onResult(Presupuesto p);
    }

    public interface CallbackInt {
        void onResult(int valor);
    }

    public interface CallbackLong {
        void onResult(long valor);
    }

    // === CAMPOS ===
    private final PresupuestoDao dao;
    private final ExecutorService executor;
    private final int userId;

    // === CONSTRUCTOR ===
    public PresupuestoControlador(Context context) {
        GestorBaseDatos db = GestorBaseDatos.obtenerInstancia(context);
        dao = db.presupuestoDao();
        executor = GestorBaseDatos.databaseExecutor;

        SharedPreferences prefs = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        userId = prefs.getInt("usuarioId", -1);
    }
    // === CRUD ===
    public void insertar(Presupuesto p, CallbackLong callback) {
        p.setUserId(userId);
        executor.execute(() -> callback.onResult(dao.insertar(p)));
    }

    public void actualizar(Presupuesto p, CallbackInt callback) {
        executor.execute(() -> callback.onResult(dao.actualizar(p)));
    }

    public void obtenerPorId(int id, CallbackPresupuesto callback) {
        executor.execute(() -> callback.onResult(dao.obtenerPorId(id, userId)));
    }

    public void obtenerTodos(CallbackListaPresupuesto callback) {
        executor.execute(() -> callback.onResult(dao.obtenerTodos(userId)));
    }


    public void eliminar(Presupuesto p, CallbackInt callback) {
        executor.execute(() -> callback.onResult(dao.eliminar(p)));
    }
}
