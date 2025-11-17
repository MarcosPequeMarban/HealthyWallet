package com.example.healthywallet.controller;

import android.content.Context;
import android.util.Log;

import com.example.healthywallet.database.GestorBaseDatos;
import com.example.healthywallet.database.DAO.MetaDao;
import com.example.healthywallet.database.entities.Meta;

import java.util.List;

public class MetaControlador {

    private final MetaDao metaDao;

    public MetaControlador(Context context) {
        GestorBaseDatos db = GestorBaseDatos.obtenerInstancia(context);
        this.metaDao = db.metaDao();
    }

    public long insertarMeta(Meta meta) {
        try {
            return metaDao.insertar(meta);
        } catch (Exception e) {
            Log.e("MetasControlador", "Error insertando meta", e);
            return -1;
        }
    }

    public int actualizarMeta(Meta meta) {
        try {
            return metaDao.actualizar(meta);
        } catch (Exception e) {
            Log.e("MetasControlador", "Error actualizando meta", e);
            return 0;
        }
    }

    public Meta obtenerMeta(int id) {
        try {
            Meta meta = metaDao.obtenerPorId(id);

            if (meta == null) {
                Log.e("MetasControlador", "Meta no encontrada (id=" + id + ")");
            }

            return meta;
        } catch (Exception e) {
            Log.e("MetasControlador", "Error obteniendo meta", e);
            return null;
        }
    }

    public List<Meta> obtenerTodas() {
        try {
            return metaDao.obtenerTodas();
        } catch (Exception e) {
            Log.e("MetasControlador", "Error obteniendo lista de metas", e);
            return null;
        }
    }

    public int eliminarMeta(Meta meta) {
        try {
            return metaDao.eliminar(meta);
        } catch (Exception e) {
            Log.e("MetasControlador", "Error eliminando meta", e);
            return 0;
        }
    }

    public int actualizarCantidad(int id, double nuevaCantidad) {
        try {
            return metaDao.actualizarCantidad(nuevaCantidad, id);
        } catch (Exception e) {
            Log.e("MetasControlador", "Error actualizando cantidad", e);
            return 0;
        }
    }
}
