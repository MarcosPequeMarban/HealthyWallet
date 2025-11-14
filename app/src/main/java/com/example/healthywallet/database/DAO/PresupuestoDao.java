package com.example.healthywallet.database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.healthywallet.database.entities.Presupuesto;

import java.util.List;

@Dao
public interface PresupuestoDao {
    @Insert
    void insertar(Presupuesto presupuesto);

    @Query("SELECT * FROM presupuestos")
    List<Presupuesto> obtenerTodos();
}
