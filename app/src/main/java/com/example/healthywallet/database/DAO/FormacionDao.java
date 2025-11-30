package com.example.healthywallet.database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.healthywallet.database.entities.Formacion;

import java.util.List;

@Dao
public interface FormacionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertar(Formacion formacion);


    @Query("SELECT * FROM formacion WHERE userId = :userId ORDER BY nivel ASC")
    List<Formacion> obtenerTodas(int userId);


    @Query("DELETE FROM formacion WHERE id = :id AND userId = :userId")
    int eliminarPorId(int id, int userId);
}
