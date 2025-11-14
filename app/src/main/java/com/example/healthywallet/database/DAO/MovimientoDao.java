package com.example.healthywallet.database.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.healthywallet.database.entities.Movimiento;

import java.util.List;

@Dao
public interface MovimientoDao {

    // INSERTAR un nuevo movimiento
    @Insert
    void insertar(Movimiento movimiento);

    // ACTUALIZAR un movimiento existente
    @Update
    void actualizar(Movimiento movimiento);

    // ELIMINAR un movimiento
    @Delete
    void eliminar(Movimiento movimiento);

    // OBTENER TODOS los movimientos (ordenados por fecha descendente)
    @Query("SELECT * FROM movimientos ORDER BY fecha DESC")
    List<Movimiento> obtenerTodos();

    // OBTENER movimientos por tipo (por ejemplo, solo 'INGRESO' o 'GASTO')
    @Query("SELECT * FROM movimientos WHERE tipo = :tipo")
    List<Movimiento> obtenerPorTipo(String tipo);

    // OBTENER la suma total de ingresos o gastos
    @Query("SELECT SUM(cantidad) FROM movimientos WHERE tipo = :tipo")
    double obtenerSumaPorTipo(String tipo);
}

