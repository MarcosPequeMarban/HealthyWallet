package com.example.healthywallet.database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.healthywallet.database.entities.Categoria;

import java.util.List;

@Dao
public interface CategoriaDao {
    @Insert
    void insertar(Categoria categoria);

    @Query("SELECT * FROM categorias")
    List<Categoria> obtenerTodas();
}
