package com.example.healthywallet.controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.healthywallet.database.GestorBaseDatos;
import com.example.healthywallet.database.DAO.VideoEducacionDao;
import com.example.healthywallet.database.entities.VideoEducacion;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class VideoEducacionControlador {

    // === CAMPOS ===
    private final VideoEducacionDao dao;
    private final ExecutorService executor;
    private final int userId;

    // === CALLBACKS ===
    public interface CallbackLista { void onResult(List<VideoEducacion> lista); }
    public interface CallbackSimple { void onResult(); }

    // === CONSTRUCTOR ===
    public VideoEducacionControlador(Context context) {
        dao = GestorBaseDatos.obtenerInstancia(context).videoEducacionDao();
        executor = GestorBaseDatos.databaseExecutor;

        SharedPreferences prefs = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        userId = prefs.getInt("usuarioId", -1);
    }

    public void insertar(VideoEducacion video, CallbackSimple callback) {
        video.userId = userId;
        executor.execute(() -> {
            dao.insertar(video);
            callback.onResult();
        });
    }


    public void obtenerTodos(CallbackLista callback) {
        executor.execute(() -> {
            List<VideoEducacion> lista = dao.obtenerTodos(userId);
            callback.onResult(lista);
        });
    }
    
}
