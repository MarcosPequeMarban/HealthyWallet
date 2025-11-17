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

    @Query("SELECT * FROM formacion ORDER BY nivel ASC")
    List<Formacion> obtenerTodas();

    @Query("DELETE FROM formacion WHERE id = :id")
    int eliminarPorId(int id);
}
