package com.example.healthywallet.controller;

import android.content.Context;

import com.example.healthywallet.database.GestorBaseDatos;
import com.example.healthywallet.database.DAO.PresupuestoDao;
import com.example.healthywallet.database.entities.Presupuesto;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class PresupuestoControlador {

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

    private final PresupuestoDao dao;
    private final ExecutorService executor;

    public PresupuestoControlador(Context context) {
        GestorBaseDatos db = GestorBaseDatos.obtenerInstancia(context);
        dao = db.presupuestoDao();
        executor = GestorBaseDatos.databaseExecutor;
    }

    public void insertar(Presupuesto p, CallbackLong callback) {
        executor.execute(() -> callback.onResult(dao.insertar(p)));
    }

    public void actualizar(Presupuesto p, CallbackInt callback) {
        executor.execute(() -> callback.onResult(dao.actualizar(p)));
    }

    public void obtenerPorId(int id, CallbackPresupuesto callback) {
        executor.execute(() -> callback.onResult(dao.obtenerPorId(id)));
    }

    public void obtenerTodos(CallbackListaPresupuesto callback) {
        executor.execute(() -> callback.onResult(dao.obtenerTodos()));
    }

    public void actualizarGasto(int id, double gasto, CallbackInt callback) {
        executor.execute(() -> callback.onResult(dao.actualizarGasto(id, gasto)));
    }

    public void eliminar(Presupuesto p, CallbackInt callback) {
        executor.execute(() -> callback.onResult(dao.eliminar(p)));
    }
}
