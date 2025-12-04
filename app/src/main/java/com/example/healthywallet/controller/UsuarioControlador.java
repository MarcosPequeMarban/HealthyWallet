package com.example.healthywallet.controller;

import android.content.Context;

import com.example.healthywallet.database.DAO.UsuarioDao;
import com.example.healthywallet.database.GestorBaseDatos;
import com.example.healthywallet.database.entities.Usuario;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.concurrent.ExecutorService;

public class UsuarioControlador {

    // === CAMPOS ===
    private final UsuarioDao usuarioDao;
    private final ExecutorService executor;

    // === CALLBACKS ===
    public interface Callback<T> {
        void onComplete(T resultado);
    }

    // === CONSTRUCTOR ===
    public UsuarioControlador(Context context) {
        GestorBaseDatos db = GestorBaseDatos.obtenerInstancia(context);
        usuarioDao = db.usuarioDao();
        executor = GestorBaseDatos.databaseExecutor;
    }

    // ===============================
    //       REGISTRO SEGURO
    // ===============================
    public void registrar(Usuario usuario, Callback<Boolean> callback) {
        executor.execute(() -> {
            try {
                Usuario existe = usuarioDao.obtenerPorEmail(usuario.getEmail());
                if (existe != null) {
                    callback.onComplete(false);
                    return;
                }

                // ✔ hash antes de guardar
                String hash = hashearPassword(usuario.getPassword());
                usuario.setPassword(hash);

                usuarioDao.insertar(usuario);
                callback.onComplete(true);

            } catch (Exception e) {
                callback.onComplete(false);
            }
        });
    }

    // ===============================
    //       LOGIN SEGURO
    // ===============================
    public void login(String email, String password, Callback<Usuario> callback) {
        executor.execute(() -> {

            Usuario user = null;

            try {
                Usuario existente = usuarioDao.obtenerPorEmail(email);

                if (existente != null) {

                    String hash = hashearPassword(password);

                    //ya estaba en hash
                    if (hash.equals(existente.getPassword())) {
                        user = existente;
                    }
                    //contraseña antigua (texto plano)
                    else if (password.equals(existente.getPassword())) {

                        // Actualizamos a hash automáticamente
                        usuarioDao.actualizarPassword(email, hash);
                        existente.setPassword(hash);

                        user = existente;
                    }
                }
            } catch (Exception ignored) {}

            callback.onComplete(user);
        });
    }

    // ===============================
    //       hashing SHA-256
    // ===============================
    private String hashearPassword(String password) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append("0");
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
