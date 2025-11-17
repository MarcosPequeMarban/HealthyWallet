package com.example.healthywallet.database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.healthywallet.database.entities.Presupuesto;

import java.util.List;

@Dao
public interface PresupuestoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertar(Presupuesto presupuesto);

    @Update
    int actualizar(Presupuesto presupuesto);

    @Delete
    int eliminar(Presupuesto presupuesto);

    @Query("SELECT * FROM presupuestos ORDER BY categoria ASC")
    List<Presupuesto> obtenerTodos();

    @Query("SELECT * FROM presupuestos WHERE id = :id LIMIT 1")
    Presupuesto obtenerPorId(int id);

    @Query("UPDATE presupuestos SET gastoActual = :nuevoGasto WHERE id = :id")
    int actualizarGasto(int id, double nuevoGasto);
}
