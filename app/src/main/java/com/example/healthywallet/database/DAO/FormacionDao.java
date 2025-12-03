package com.example.healthywallet.database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.healthywallet.database.entities.Formacion;

import java.util.List;

@Dao
public interface FormacionDao {

    // =========================
    // INSERTAR
    // =========================
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertar(Formacion formacion);

    // =========================
    // ACTUALIZAR
    // =========================
    @Update
    int actualizar(Formacion formacion);

    // =========================
    // OBTENER TODOS
    // =========================
    @Query("SELECT * FROM formacion WHERE userId = :userId ORDER BY nivel ASC")
    List<Formacion> obtenerTodas(int userId);

    // =========================
    // OBTENER POR NIVEL
    // =========================
    @Query("SELECT * FROM formacion WHERE nivel = :nivel AND userId = :userId ORDER BY id ASC")
    List<Formacion> obtenerPorNivel(String nivel, int userId);

    // =========================
    // CONTAR TOTAL POR NIVEL
    // =========================
    @Query("SELECT COUNT(*) FROM formacion WHERE nivel = :nivel AND userId = :userId")
    int contarPorNivel(String nivel, int userId);

    // =========================
    // CONTAR COMPLETADOS POR NIVEL
    // =========================
    @Query("SELECT COUNT(*) FROM formacion WHERE nivel = :nivel AND completado = 1 AND userId = :userId")
    int contarCompletadosNivel(String nivel, int userId);

    // =========================
    // CONTAR TODOS LOS VIDEOS
    // =========================
    @Query("SELECT COUNT(*) FROM formacion WHERE userId = :userId")
    int contarTodos(int userId);

    // =========================
    // CONTAR TODOS COMPLETADOS
    // =========================
    @Query("SELECT COUNT(*) FROM formacion WHERE completado = 1 AND userId = :userId")
    int contarTodosCompletados(int userId);

    // =========================
    // ELIMINAR POR ID
    // =========================
    @Query("DELETE FROM formacion WHERE id = :id AND userId = :userId")
    int eliminarPorId(int id, int userId);
}
