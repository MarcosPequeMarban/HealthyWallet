package com.example.healthywallet.database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.healthywallet.database.entities.Meta;

import java.util.List;

@Dao
public interface MetaDao {
    @Insert
    void insertar(Meta meta);

    @Query("SELECT * FROM metas")
    List<Meta> obtenerTodas();
}
