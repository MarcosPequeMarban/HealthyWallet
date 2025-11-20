package com.example.healthywallet.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.healthywallet.database.DAO.CategoriaDao;
import com.example.healthywallet.database.DAO.FormacionDao;
import com.example.healthywallet.database.DAO.MetaDao;
import com.example.healthywallet.database.DAO.MovimientoDao;
import com.example.healthywallet.database.DAO.PresupuestoDao;

import com.example.healthywallet.database.entities.Categoria;
import com.example.healthywallet.database.entities.Formacion;
import com.example.healthywallet.database.entities.Meta;
import com.example.healthywallet.database.entities.Movimiento;
import com.example.healthywallet.database.entities.Presupuesto;

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
        version = 2,
        exportSchema = true
)
public abstract class GestorBaseDatos extends RoomDatabase {

    // --- DAO ---
    public abstract MovimientoDao movimientoDao();
    public abstract CategoriaDao categoriaDao();
    public abstract PresupuestoDao presupuestoDao();
    public abstract MetaDao metaDao();
    public abstract FormacionDao formacionDao();

    // -------------------------------
    //   SINGLETON + THREAD POOL
    // -------------------------------
    private static volatile GestorBaseDatos INSTANCIA;

    private static final int NUM_HILOS = 4;
    public static final ExecutorService databaseExecutor =
            Executors.newFixedThreadPool(NUM_HILOS);


    // -------------------------------
    //        MIGRACIONES
    // -------------------------------
    // ⭐ Migración vacía 1→2 (necesaria para evitar borrado de datos)
    static final Migration MIGRACION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // No hay cambios aún, pero es obligatorio definir la migración
        }
    };


    // -------------------------------
    //         OBTENER INSTANCIA
    // -------------------------------
    public static GestorBaseDatos obtenerInstancia(Context context) {
        if (INSTANCIA == null) {
            synchronized (GestorBaseDatos.class) {
                if (INSTANCIA == null) {
                    INSTANCIA = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    GestorBaseDatos.class,
                                    "HealthyWalletDB"
                            )
                            .addMigrations(MIGRACION_1_2)
                            .build();
                }
            }
        }
        return INSTANCIA;
    }
}
