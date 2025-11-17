package com.example.healthywallet.database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.healthywallet.database.entities.Categoria;

import java.util.List;

@Dao
public interface CategoriaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertar(Categoria categoria);

    @Query("SELECT * FROM categorias ORDER BY nombre ASC")
    List<Categoria> obtenerTodas();

    @Query("DELETE FROM categorias WHERE id = :id")
    int eliminarPorId(int id);
}
