package com.example.healthywallet.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.healthywallet.database.DAO.*;
import com.example.healthywallet.database.entities.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    // --- DAOs ---
    public abstract MovimientoDao movimientoDao();
    public abstract CategoriaDao categoriaDao();
    public abstract PresupuestoDao presupuestoDao();
    public abstract MetaDao metaDao();
    public abstract FormacionDao formacionDao();

    // --- Instancia Singleton ---
    private static volatile GestorBaseDatos instancia;

    // Executor para ejecutar consultas en segundo plano
    private static final ExecutorService databaseExecutor =
            Executors.newFixedThreadPool(4);

    public static ExecutorService getExecutor() {
        return databaseExecutor;
    }

    // --- Obtener instancia ---
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
                            .build();
                }
            }
        }
        return instancia;
    }
}
