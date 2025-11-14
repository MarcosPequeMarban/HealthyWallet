package com.example.healthywallet.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.healthywallet.database.DAO.*;
import com.example.healthywallet.database.entities.*;

@Database(
        entities = {
                Movimiento.class,
                Categoria.class,
                Presupuesto.class,
                Meta.class,
                Formacion.class
        },
        version = 1,
        exportSchema = false
)
public abstract class GestorBaseDatos extends RoomDatabase {

    public abstract MovimientoDao movimientoDao();
    public abstract CategoriaDao categoriaDao();
    public abstract PresupuestoDao presupuestoDao();
    public abstract MetaDao metaDao();
    public abstract FormacionDao formacionDao();

    private static volatile GestorBaseDatos instancia;

    public static GestorBaseDatos obtenerInstancia(final Context context) {
        if (instancia == null) {
            synchronized (GestorBaseDatos.class) {
                if (instancia == null) {
                    instancia = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    GestorBaseDatos.class,
                                    "healthy_wallet_db"
                            )
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return instancia;
    }
}
