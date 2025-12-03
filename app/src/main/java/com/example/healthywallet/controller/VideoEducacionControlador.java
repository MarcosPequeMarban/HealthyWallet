package com.example.healthywallet.controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.healthywallet.database.GestorBaseDatos;
import com.example.healthywallet.database.DAO.VideoEducacionDao;
import com.example.healthywallet.database.entities.VideoEducacion;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class VideoEducacionControlador {

    private final VideoEducacionDao dao;
    private final ExecutorService executor;
    private final int userId;

    public interface CallbackLista { void onResult(List<VideoEducacion> lista); }
    public interface CallbackSimple { void onResult(); }

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

    public void insertarTodos(List<VideoEducacion> lista, CallbackSimple callback) {
        for (VideoEducacion v : lista) v.userId = userId;

        executor.execute(() -> {
            dao.insertarTodos(lista);
            callback.onResult();
        });
    }

    public void obtenerTodos(CallbackLista callback) {
        executor.execute(() -> {
            List<VideoEducacion> lista = dao.obtenerTodos(userId);
            callback.onResult(lista);
        });
    }

    public void obtenerPorNivel(String nivel, CallbackLista callback) {
        executor.execute(() -> {
            List<VideoEducacion> lista = dao.obtenerPorNivel(nivel, userId);
            callback.onResult(lista);
        });
    }

    public void marcarComoCompletado(VideoEducacion video, CallbackSimple callback) {
        video.setCompletado(true);
        video.setFechaCompletado(System.currentTimeMillis());

        executor.execute(() -> {
            dao.actualizar(video);
            callback.onResult();
        });
    }
}
