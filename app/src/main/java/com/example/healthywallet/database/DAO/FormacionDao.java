package com.example.healthywallet.database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.healthywallet.database.entities.Formacion;

import java.util.List;

@Dao
public interface FormacionDao {
    @Insert
    void insertar(Formacion formacion);

    @Query("SELECT * FROM formacion")
    List<Formacion> obtenerTodas();
}
