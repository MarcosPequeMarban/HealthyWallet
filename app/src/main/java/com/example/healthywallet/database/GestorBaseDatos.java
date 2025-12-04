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
import com.example.healthywallet.database.DAO.UsuarioDao;
import com.example.healthywallet.database.DAO.VideoEducacionDao;

import com.example.healthywallet.database.entities.Categoria;
import com.example.healthywallet.database.entities.Formacion;
import com.example.healthywallet.database.entities.Meta;
import com.example.healthywallet.database.entities.Movimiento;
import com.example.healthywallet.database.entities.Presupuesto;
import com.example.healthywallet.database.entities.Usuario;
import com.example.healthywallet.database.entities.VideoEducacion;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {
                Movimiento.class,
                Categoria.class,
                Presupuesto.class,
                Meta.class,
                Formacion.class,
                Usuario.class,
                VideoEducacion.class
        },
        version = 6
)
public abstract class GestorBaseDatos extends RoomDatabase {

    // ==== DAOs ====
    public abstract MovimientoDao movimientoDao();
    public abstract PresupuestoDao presupuestoDao();
    public abstract MetaDao metaDao();
    public abstract FormacionDao formacionDao();
    public abstract CategoriaDao categoriaDao();
    public abstract UsuarioDao usuarioDao();
    public abstract VideoEducacionDao videoEducacionDao();

    // ==== SINGLETON ====
    private static volatile GestorBaseDatos INSTANCIA;

    private static final int NUM_HILOS = 4;
    public static final ExecutorService databaseExecutor =
            Executors.newFixedThreadPool(NUM_HILOS);

    // ==== MIGRACIONES ====

    // Migración 1→2
    static final Migration MIGRACION_1_2 = new Migration(1, 2) {
        @Override public void migrate(@NonNull SupportSQLiteDatabase db) {}
    };

    // Migración 2→3
    static final Migration MIGRACION_2_3 = new Migration(2, 3) {
        @Override public void migrate(@NonNull SupportSQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS `recursos_educativos` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`tipo` TEXT NOT NULL, " +
                    "`titulo` TEXT NOT NULL, " +
                    "`url` TEXT NOT NULL, " +
                    "`completado` INTEGER NOT NULL, " +
                    "`fechaCompletado` INTEGER NOT NULL, " +
                    "`fechaRecomendado` INTEGER NOT NULL)");
        }
    };

    // Migración 3→4
    static final Migration MIGRACION_3_4 = new Migration(3, 4) {
        @Override public void migrate(@NonNull SupportSQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS usuarios (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "nombre TEXT NOT NULL, " +
                    "email TEXT NOT NULL, " +
                    "password TEXT NOT NULL)");
        }
    };

    // Migración 4→5
    static final Migration MIGRACION_4_5 = new Migration(4, 5) {
        @Override public void migrate(@NonNull SupportSQLiteDatabase db) {
            db.execSQL("ALTER TABLE presupuestos ADD COLUMN userId INTEGER NOT NULL DEFAULT -1");
            db.execSQL("ALTER TABLE movimientos ADD COLUMN userId INTEGER NOT NULL DEFAULT -1");
            db.execSQL("ALTER TABLE metas ADD COLUMN userId INTEGER NOT NULL DEFAULT -1");
            db.execSQL("ALTER TABLE formacion ADD COLUMN userId INTEGER NOT NULL DEFAULT -1");
        }
    };

    // ==== Migracion 5→6 ====
    static final Migration MIGRACION_5_6 = new Migration(5, 6) {
        @Override public void migrate(@NonNull SupportSQLiteDatabase db) {

            db.execSQL("" +
                    "CREATE TABLE IF NOT EXISTS `videos_educacion` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`titulo` TEXT NOT NULL, " +
                    "`url` TEXT NOT NULL, " +
                    "`nivel` TEXT NOT NULL, " +
                    "`completado` INTEGER NOT NULL, " +
                    "`fechaCompletado` INTEGER NOT NULL)");
        }
    };

    // ==== OBTENER INSTANCIA (SINGLETON) ====
    public static GestorBaseDatos obtenerInstancia(Context context) {
        if (INSTANCIA == null) {
            synchronized (GestorBaseDatos.class) {
                if (INSTANCIA == null) {
                    INSTANCIA = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    GestorBaseDatos.class,
                                    "HealthyWalletDB"
                            )
                            .addMigrations(
                                    MIGRACION_1_2,
                                    MIGRACION_2_3,
                                    MIGRACION_3_4,
                                    MIGRACION_4_5,
                                    MIGRACION_5_6
                            )
                            .build();
                }
            }
        }
        return INSTANCIA;
    }
}
