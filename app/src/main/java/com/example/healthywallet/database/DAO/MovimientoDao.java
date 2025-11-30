package com.example.healthywallet.database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Update;

import com.example.healthywallet.database.entities.Movimiento;

import java.util.List;

@Dao
public interface MovimientoDao {

    @Insert
    long insertar(Movimiento movimiento);

    @Update
    int actualizar(Movimiento movimiento);

    @Delete
    int eliminar(Movimiento movimiento);

    @Query("SELECT * FROM movimientos WHERE userId = :userId ORDER BY fecha DESC")
    List<Movimiento> obtenerTodos(int userId);

    @Query("SELECT * FROM movimientos WHERE id = :id AND userId = :userId LIMIT 1")
    Movimiento obtenerPorId(int id, int userId);

    @Query("SELECT * FROM movimientos WHERE tipo = :tipo AND userId = :userId ORDER BY fecha DESC")
    List<Movimiento> obtenerPorTipo(String tipo, int userId);

    @Query("SELECT SUM(cantidad) FROM movimientos WHERE tipo = :tipo AND userId = :userId")
    Double obtenerSumaPorTipo(String tipo, int userId);
}
