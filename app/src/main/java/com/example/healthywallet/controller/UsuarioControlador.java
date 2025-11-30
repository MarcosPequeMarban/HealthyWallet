package com.example.healthywallet.controller;

import android.content.Context;

import com.example.healthywallet.database.DAO.UsuarioDao;
import com.example.healthywallet.database.GestorBaseDatos;
import com.example.healthywallet.database.entities.Usuario;

import java.util.concurrent.ExecutorService;

public class UsuarioControlador {

    private final UsuarioDao usuarioDao;
    private final ExecutorService executor;

    public interface Callback<T> {
        void onComplete(T resultado);
    }

    public UsuarioControlador(Context context) {
        GestorBaseDatos db = GestorBaseDatos.obtenerInstancia(context);
        usuarioDao = db.usuarioDao();
        executor = GestorBaseDatos.databaseExecutor;
    }

    /** Registro seguro: comprueba si el email ya existe y luego inserta */
    public void registrar(Usuario usuario, Callback<Boolean> callback) {
        executor.execute(() -> {
            try {
                Usuario existe = usuarioDao.obtenerPorEmail(usuario.getEmail());
                if (existe != null) {
                    callback.onComplete(false);
                    return;
                }

                usuarioDao.insertar(usuario);
                callback.onComplete(true);

            } catch (Exception e) {
                callback.onComplete(false);
            }
        });
    }

    /** Login: devuelve Usuario o null */
    public void login(String email, String password, Callback<Usuario> callback) {
        executor.execute(() -> {
            Usuario user = null;
            try {
                user = usuarioDao.login(email, password);
            } catch (Exception ignored) {}

            callback.onComplete(user);
        });
    }
}
