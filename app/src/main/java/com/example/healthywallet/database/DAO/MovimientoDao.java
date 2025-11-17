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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertar(Movimiento movimiento);

    @Update
    int actualizar(Movimiento movimiento);

    @Delete
    int eliminar(Movimiento movimiento);

    @Query("SELECT * FROM movimientos ORDER BY fecha DESC")
    List<Movimiento> obtenerTodos();

    @Query("SELECT * FROM movimientos WHERE tipo = :tipo ORDER BY fecha DESC")
    List<Movimiento> obtenerPorTipo(String tipo);

    @Query("SELECT SUM(cantidad) FROM movimientos WHERE tipo = :tipo")
    Double obtenerSumaPorTipo(String tipo);

    @Query("SELECT * FROM movimientos WHERE id = :id LIMIT 1")
    Movimiento obtenerPorId(int id);
}
