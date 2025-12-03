package com.example.healthywallet.database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.healthywallet.database.entities.VideoEducacion;

import java.util.List;

@Dao
public interface VideoEducacionDao {

    @Insert
    void insertar(VideoEducacion video);

    @Insert
    void insertarTodos(List<VideoEducacion> lista);

    @Update
    void actualizar(VideoEducacion video);

    @Query("SELECT * FROM video_educacion WHERE userId = :userId")
    List<VideoEducacion> obtenerTodos(int userId);

    @Query("SELECT * FROM video_educacion WHERE nivel = :nivel AND userId = :userId")
    List<VideoEducacion> obtenerPorNivel(String nivel, int userId);
}
