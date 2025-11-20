package com.example.healthywallet.controller;

import android.content.Context;

import com.example.healthywallet.database.GestorBaseDatos;
import com.example.healthywallet.database.DAO.MetaDao;
import com.example.healthywallet.database.entities.Meta;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class MetaControlador {

    public interface CallbackListaMeta {
        void onResult(List<Meta> lista);
    }

    public interface CallbackMeta {
        void onResult(Meta meta);
    }

    public interface CallbackInt {
        void onResult(int valor);
    }

    private final MetaDao dao;
    private final ExecutorService executor;

    public MetaControlador(Context context) {
        GestorBaseDatos db = GestorBaseDatos.obtenerInstancia(context);
        dao = db.metaDao();
        executor = GestorBaseDatos.databaseExecutor;
    }

    public void insertar(Meta meta, CallbackInt callback) {
        executor.execute(() -> callback.onResult((int) dao.insertar(meta)));
    }

    public void actualizar(Meta meta, CallbackInt callback) {
        executor.execute(() -> callback.onResult(dao.actualizar(meta)));
    }

    public void obtenerPorId(int id, CallbackMeta callback) {
        executor.execute(() -> callback.onResult(dao.obtenerPorId(id)));
    }

    public void obtenerTodas(CallbackListaMeta callback) {
        executor.execute(() -> callback.onResult(dao.obtenerTodas()));
    }

    public void actualizarCantidad(int id, double nuevaCantidad, CallbackInt callback) {
        executor.execute(() -> callback.onResult(dao.actualizarCantidad(nuevaCantidad, id)));
    }

    public void eliminar(Meta meta, CallbackInt callback) {
        executor.execute(() -> callback.onResult(dao.eliminar(meta)));
    }
}
