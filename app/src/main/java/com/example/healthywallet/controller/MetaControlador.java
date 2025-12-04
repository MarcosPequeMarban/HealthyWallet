package com.example.healthywallet.controller;

import android.content.Context;
import android.content.SharedPreferences;

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
    private final int userId;

    // === CONSTRUCTOR ===
    public MetaControlador(Context context) {
        GestorBaseDatos db = GestorBaseDatos.obtenerInstancia(context);
        dao = db.metaDao();
        executor = GestorBaseDatos.databaseExecutor;

        SharedPreferences prefs = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        userId = prefs.getInt("usuarioId", -1);
    }

    // === CRUD ===

    public void insertar(Meta meta, CallbackLong callback) {
        meta.setUserId(userId);
        executor.execute(() -> {
            long id = dao.insertar(meta);
            callback.onResult(id);
        });
    }


    public void actualizar(Meta meta, CallbackInt callback) {
        executor.execute(() -> {
            int filas = dao.actualizar(meta);
            callback.onResult(filas);
        });
    }


    public void obtenerPorId(int id, CallbackMeta callback) {
        executor.execute(() -> {
            Meta meta = dao.obtenerPorId(id, userId);
            callback.onResult(meta);
        });
    }

    public void obtenerTodas(CallbackListaMeta callback) {
        executor.execute(() -> {
            List<Meta> lista = dao.obtenerTodas(userId);
            callback.onResult(lista);
        });
    }



    public void eliminar(Meta meta, CallbackInt callback) {
        executor.execute(() -> {
            int filas = dao.eliminar(meta);
            callback.onResult(filas);
        });
    }
}
