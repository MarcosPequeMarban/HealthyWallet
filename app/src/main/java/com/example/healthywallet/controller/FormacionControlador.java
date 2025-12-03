package com.example.healthywallet.controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.healthywallet.database.GestorBaseDatos;
import com.example.healthywallet.database.DAO.FormacionDao;
import com.example.healthywallet.database.entities.Formacion;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class FormacionControlador {

    // ==== Callbacks ====
    public interface CallbackListaFormacion { void onResult(List<Formacion> lista); }
    public interface CallbackInt { void onResult(int valor); }
    public interface CallbackSimple { void onFinish(); }
    public interface CallbackFormacion { void onResult(Formacion formacion); }

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

    // ===============================
    // INSERTAR VIDEO
    // ===============================
    public void insertar(Formacion formacion, CallbackInt callback) {
        formacion.setUserId(userId);
        executor.execute(() -> callback.onResult((int) dao.insertar(formacion)));
    }

    // ===============================
    // OBTENER TODOS LOS VIDEOS
    // ===============================
    public void obtenerTodas(CallbackListaFormacion callback) {
        executor.execute(() -> callback.onResult(dao.obtenerTodas(userId)));
    }

    // ===============================
    // ELIMINAR VIDEO
    // ===============================
    public void eliminarPorId(int id, CallbackInt callback) {
        executor.execute(() -> callback.onResult(dao.eliminarPorId(id, userId)));
    }

    // ===============================
    // OBTENER VIDEOS POR NIVEL
    // ===============================
    public void obtenerPorNivel(String nivel, CallbackListaFormacion callback) {
        executor.execute(() -> callback.onResult(
                dao.obtenerPorNivel(nivel, userId)
        ));
    }

    // ===============================
    // MARCAR COMPLETADO / NO COMPLETADO
    // ===============================
    public void marcarCompletado(Formacion video, boolean nuevoEstado, CallbackSimple callback) {
        executor.execute(() -> {
            video.setCompletado(nuevoEstado);

            if (nuevoEstado)
                video.setFechaCompletado(System.currentTimeMillis());
            else
                video.setFechaCompletado(0);

            dao.actualizar(video);
            callback.onFinish();
        });
    }

    // ===============================
    // CONTAR TOTAL VIDEOS POR NIVEL
    // ===============================
    public void contarVideosNivel(String nivel, CallbackInt callback) {
        executor.execute(() -> callback.onResult(
                dao.contarPorNivel(nivel, userId)
        ));
    }

    // ===============================
    // CONTAR COMPLETADOS POR NIVEL
    // ===============================
    public void contarCompletadosNivel(String nivel, CallbackInt callback) {
        executor.execute(() -> callback.onResult(
                dao.contarCompletadosNivel(nivel, userId)
        ));
    }

    // ===============================
    // CONTAR TODOS GLOBAL
    // ===============================
    public void contarTodosGlobal(CallbackInt callback) {
        executor.execute(() ->
                callback.onResult(dao.contarTodos(userId))
        );
    }

    // ===============================
    // CONTAR COMPLETADOS GLOBAL
    // ===============================
    public void contarCompletadosGlobal(CallbackInt callback) {
        executor.execute(() ->
                callback.onResult(dao.contarTodosCompletados(userId))
        );
    }

    // ===============================
    // CALCULAR % PROGRESO GLOBAL
    // ===============================
    public void calcularProgresoGlobal(CallbackInt callback) {
        executor.execute(() -> {

            int total = dao.contarTodos(userId);
            int completados = dao.contarTodosCompletados(userId);

            int porcentaje = (total == 0) ? 0 : (completados * 100 / total);

            callback.onResult(porcentaje);
        });
    }

    // ===============================
    // LIBRO DE LA SEMANA
    // ===============================
    public void obtenerLibroSemana(CallbackFormacion callback) {
        executor.execute(() -> {
            List<Formacion> libros = dao.obtenerPorNivel("LIBRO", userId);

            if (libros.isEmpty()) {
                callback.onResult(null);
                return;
            }

            int week = (int) (System.currentTimeMillis() / (7L * 24 * 60 * 60 * 1000));
            int index = week % libros.size();

            callback.onResult(libros.get(index));
        });
    }

    // ===============================
    // ACTUALIZAR VIDEO
    // ===============================
    public void actualizar(Formacion f, CallbackInt callback) {
        executor.execute(() -> callback.onResult(dao.actualizar(f)));
    }

    // ===============================
    // INSERTAR VIDEOS SI NO EXISTEN
    // ===============================
    public void inicializarDatosSiNecesario() {
        executor.execute(() -> {

            int total = dao.contarTodos(userId);

            if (total > 0) return;

            // PRINCIPIANTE
            dao.insertar(new Formacion(
                    "Introducción a la inversión",
                    "https://youtu.be/ujY0DkyL--w?si=rHrEaVOZDL-fqa61",
                    "PRINCIPIANTE",
                    userId
            ));

            dao.insertar(new Formacion(
                    "Cómo funcionan los fondos indexados",
                    "https://youtu.be/6Uw-27uRoO4?si=VtSpx85TXxHNtW6L",
                    "PRINCIPIANTE",
                    userId
            ));

            // INTERMEDIO
            dao.insertar(new Formacion(
                    "Análisis técnico básico",
                    "https://youtu.be/bL8sNc0n984?si=Q6lH0YWyPPut2UI0",
                    "INTERMEDIO",
                    userId
            ));

            dao.insertar(new Formacion(
                    "Cómo empezar en las inversiones",
                    "https://youtu.be/VYuoxeZ33hw?si=zTY_9RWkbpU4sPjS",
                    "INTERMEDIO",
                    userId
            ));

            // EXPERTO
            dao.insertar(new Formacion(
                    "Podcast experto financiero",
                    "https://youtu.be/SKEsbnJHTkc?si=qPKL_LbBTmJpKQCa",
                    "EXPERTO",
                    userId
            ));

            dao.insertar(new Formacion(
                    "Clase de renta fija con gestor experto",
                    "https://youtu.be/EE0eJM_BTqU?si=qNMcUCfUGcXOcDQ3",
                    "EXPERTO",
                    userId
            ));
        });
    }
}
