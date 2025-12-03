package com.example.healthywallet.database.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.healthywallet.database.entities.Usuario;

import java.util.List;

@Dao
public interface UsuarioDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    long insertar(Usuario usuario);

    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    Usuario obtenerPorEmail(String email);

    // Mantengo login antiguo (solo para compatibilidad)
    @Query("SELECT * FROM usuarios WHERE email = :email AND password = :password LIMIT 1")
    Usuario login(String email, String password);

    // actualizar password ya hasheada
    @Query("UPDATE usuarios SET password = :password WHERE email = :email")
    void actualizarPassword(String email, String password);

    @Query("SELECT * FROM usuarios")
    List<Usuario> obtenerTodos();

    @Delete
    int borrar(Usuario usuario);
}
