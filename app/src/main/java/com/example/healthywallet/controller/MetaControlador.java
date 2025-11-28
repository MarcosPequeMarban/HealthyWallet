package com.example.healthywallet.controller;

import android.content.Context;

import com.example.healthywallet.database.GestorBaseDatos;
import com.example.healthywallet.database.DAO.MetaDao;
import com.example.healthywallet.database.entities.Meta;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class MetaControlador {

    // === CALLBACKS ===
    public interface CallbackListaMeta {
        void onResult(List<Meta> lista);
    }

    public interface CallbackMeta {
        void onResult(Meta meta);
    }

    public interface CallbackLong {
        void onResult(long valor);
    }

    public interface CallbackInt {
        void onResult(int valor);
    }

    // === CAMPOS ===
    private final MetaDao dao;
    private final ExecutorService executor;

    // === CONSTRUCTOR ===
    public MetaControlador(Context context) {
        GestorBaseDatos db = GestorBaseDatos.obtenerInstancia(context);
        dao = db.metaDao();
        executor = GestorBaseDatos.databaseExecutor;
    }

    // === CRUD ===

    /** Insertar una meta y devolver el ID generado */
    public void insertar(Meta meta, CallbackLong callback) {
        executor.execute(() -> {
            long id = dao.insertar(meta);
            callback.onResult(id);
        });
    }

    /** Actualizar una meta */
    public void actualizar(Meta meta, CallbackInt callback) {
        executor.execute(() -> {
            int filas = dao.actualizar(meta);
            callback.onResult(filas);
        });
    }

    /** Obtener meta por ID */
    public void obtenerPorId(int id, CallbackMeta callback) {
        executor.execute(() -> {
            Meta meta = dao.obtenerPorId(id);
            callback.onResult(meta);
        });
    }

    /** Obtener todas las metas */
    public void obtenerTodas(CallbackListaMeta callback) {
        executor.execute(() -> {
            List<Meta> lista = dao.obtenerTodas();
            callback.onResult(lista);
        });
    }

    /** Actualizar solo la cantidad actual */
    public void actualizarCantidad(int id, double nuevaCantidad, CallbackInt callback) {
        executor.execute(() -> {
            int filas = dao.actualizarCantidad(nuevaCantidad, id);
            callback.onResult(filas);
        });
    }

    /** Eliminar una meta */
    public void eliminar(Meta meta, CallbackInt callback) {
        executor.execute(() -> {
            int filas = dao.eliminar(meta);
            callback.onResult(filas);
        });
    }
}
