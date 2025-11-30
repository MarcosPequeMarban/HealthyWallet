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

    @Query("SELECT * FROM metas WHERE userId = :userId ORDER BY fechaObjetivo ASC")
    List<Meta> obtenerTodas(int userId);

    @Query("SELECT * FROM metas WHERE id = :id AND userId = :userId LIMIT 1")
    Meta obtenerPorId(int id, int userId);

    @Query("UPDATE metas SET cantidadActual = :cantidad WHERE id = :id AND userId = :userId")
    int actualizarCantidad(double cantidad, int id, int userId);
}
