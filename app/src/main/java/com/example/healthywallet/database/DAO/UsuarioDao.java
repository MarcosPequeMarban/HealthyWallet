package com.example.healthywallet.database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.healthywallet.database.entities.Usuario;

@Dao
public interface UsuarioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertar(Usuario usuario);

    @Query("SELECT * FROM usuarios LIMIT 1")
    Usuario obtenerUnico();

    @Query("DELETE FROM usuarios")
    int eliminarTodos();
}
