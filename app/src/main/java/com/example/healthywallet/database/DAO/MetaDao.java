package com.example.healthywallet.database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.healthywallet.database.entities.Meta;

import java.util.List;

@Dao
public interface MetaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertar(Meta meta);

    @Update
    int actualizar(Meta meta);

    @Delete
    int eliminar(Meta meta);

    @Query("SELECT * FROM metas ORDER BY fechaObjetivo ASC")
    List<Meta> obtenerTodas();

    @Query("SELECT * FROM metas WHERE id = :id LIMIT 1")
    Meta obtenerPorId(int id);

    @Query("UPDATE metas SET cantidadActual = :cantidad WHERE id = :id")
    int actualizarCantidad(double cantidad, int id);

}
