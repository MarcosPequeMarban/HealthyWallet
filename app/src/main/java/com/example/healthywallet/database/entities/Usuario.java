package com.example.healthywallet.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "usuarios",
        indices = { @Index(value = "email", unique = true) }
)
public class Usuario {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "nombre")
    private String nombre;

    @NonNull
    @ColumnInfo(name = "email")
    private String email;

    @NonNull
    @ColumnInfo(name = "password")
    private String password;

    // Constructor vacío requerido por Room
    public Usuario() {
        this.nombre = "";
        this.email = "";
        this.password = "";
    }

    @Ignore
    public Usuario(@NonNull String nombre, @NonNull String email, @NonNull String password) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    // ==== GETTERS ==== ==== SETTERS ====
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @NonNull
    public String getNombre() { return nombre; }
    public void setNombre(@NonNull String nombre) { this.nombre = nombre; }

    @NonNull
    public String getEmail() { return email; }
    public void setEmail(@NonNull String email) { this.email = email; }

    @NonNull
    public String getPassword() { return password; }
    public void setPassword(@NonNull String password) { this.password = password; }
}
