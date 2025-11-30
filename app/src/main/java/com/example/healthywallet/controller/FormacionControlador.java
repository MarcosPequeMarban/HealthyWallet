package com.example.healthywallet.controller;

import android.content.Context;

import com.example.healthywallet.database.GestorBaseDatos;
import com.example.healthywallet.database.DAO.FormacionDao;
import com.example.healthywallet.database.entities.Formacion;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class FormacionControlador {

    // --------------------------
    //      CALLBACKS
    // --------------------------

    public interface CallbackListaFormacion {
        void onResult(List<Formacion> lista);
    }

    public interface CallbackInt {
        void onResult(int valor);
    }

    public interface FormacionCallback {
        void onResult(Formacion formacion);
    }

    // --------------------------
    //       CAMPOS
    // --------------------------

    private final FormacionDao dao;
    private final ExecutorService executor;

    public FormacionControlador(Context context) {
        GestorBaseDatos db = GestorBaseDatos.obtenerInstancia(context);
        dao = db.formacionDao();
        executor = GestorBaseDatos.databaseExecutor;
    }

    // --------------------------
    //     OPERACIONES CRUD
    // --------------------------

    public void insertar(Formacion formacion, CallbackInt callback) {
        executor.execute(() -> callback.onResult((int) dao.insertar(formacion)));
    }

    public void obtenerTodas(CallbackListaFormacion callback) {
        executor.execute(() -> callback.onResult(dao.obtenerTodas()));
    }

    public void eliminarPorId(int id, CallbackInt callback) {
        executor.execute(() -> callback.onResult(dao.eliminarPorId(id)));
    }


}
