package com.example.healthywallet.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "video_educacion")
public class VideoEducacion {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String nivel;          // principiante / medio / avanzado
    public String titulo;
    public String url;

    public int completado = 0;    // 0 = no, 1 = sí
    public long fechaCompletado = 0;

    public int userId;

    // ==== GETTERS ==== ==== SETTERS ====
    public void setCompletado(boolean valor) {
        this.completado = valor ? 1 : 0;
    }

    public void setFechaCompletado(long fecha) {
        this.fechaCompletado = fecha;
    }
}
