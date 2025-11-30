package com.example.healthywallet.controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.healthywallet.database.GestorBaseDatos;
import com.example.healthywallet.database.DAO.FormacionDao;
import com.example.healthywallet.database.entities.Formacion;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class FormacionControlador {

    public interface CallbackListaFormacion {
        void onResult(List<Formacion> lista);
    }

    public interface CallbackInt {
        void onResult(int valor);
    }

    public interface FormacionCallback {
        void onResult(Formacion formacion);
    }

    private final FormacionDao dao;
    private final ExecutorService executor;
    private final int userId;

    public FormacionControlador(Context context) {
        GestorBaseDatos db = GestorBaseDatos.obtenerInstancia(context);
        dao = db.formacionDao();
        executor = GestorBaseDatos.databaseExecutor;

        SharedPreferences prefs = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        userId = prefs.getInt("usuarioId", -1);
    }

    public void insertar(Formacion formacion, CallbackInt callback) {
        formacion.setUserId(userId);
        executor.execute(() -> callback.onResult((int) dao.insertar(formacion)));
    }

    public void obtenerTodas(CallbackListaFormacion callback) {
        executor.execute(() -> callback.onResult(dao.obtenerTodas(userId)));
    }

    public void eliminarPorId(int id, CallbackInt callback) {
        executor.execute(() -> callback.onResult(dao.eliminarPorId(id, userId)));
    }
}
