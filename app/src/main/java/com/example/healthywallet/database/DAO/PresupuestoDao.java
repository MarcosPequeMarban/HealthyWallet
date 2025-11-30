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

    @Query("SELECT * FROM presupuestos WHERE userId = :userId ORDER BY categoria ASC")
    List<Presupuesto> obtenerTodos(int userId);

    @Query("SELECT * FROM presupuestos WHERE id = :id AND userId = :userId LIMIT 1")
    Presupuesto obtenerPorId(int id, int userId);

    @Query("UPDATE presupuestos SET gastoActual = :nuevoGasto WHERE id = :id AND userId = :userId")
    int actualizarGasto(int id, int userId, double nuevoGasto);
}
