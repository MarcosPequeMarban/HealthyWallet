package com.example.healthywallet.controller;

import android.content.Context;

import com.example.healthywallet.database.GestorBaseDatos;
import com.example.healthywallet.database.DAO.UsuarioDao;
import com.example.healthywallet.database.entities.Usuario;

import java.util.concurrent.ExecutorService;

public class UsuarioControlador {

    public interface CallbackUsuario {
        void onResult(Usuario usuario);
    }

    public interface CallbackInt {
        void onResult(int valor);
    }

    private final UsuarioDao dao;
    private final ExecutorService executor;

    public UsuarioControlador(Context context) {
        GestorBaseDatos db = GestorBaseDatos.obtenerInstancia(context);
        dao = db.usuarioDao();
        executor = GestorBaseDatos.databaseExecutor;
    }

    public void guardarUsuario(Usuario usuario, CallbackInt callback) {
        executor.execute(() -> callback.onResult((int) dao.insertar(usuario)));
    }

    public void obtenerUsuario(CallbackUsuario callback) {
        executor.execute(() -> callback.onResult(dao.obtenerUnico()));
    }

    public void eliminarUsuarios(CallbackInt callback) {
        executor.execute(() -> callback.onResult(dao.eliminarTodos()));
    }
}
