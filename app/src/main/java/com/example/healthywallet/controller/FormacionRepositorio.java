package com.example.healthywallet.controller;

import java.util.Random;

public class FormacionRepositorio {

    private static final String[] LIBROS = {
            "El inversor inteligente – Benjamin Graham",
            "Los secretos de la mente millonaria – T. Harv Eker",
            "Padre Rico, Padre Pobre – Robert Kiyosaki",
            "El pequeño libro que aún vence al mercado – Joel Greenblatt",
            "Hábitos Atómicos – James Clear",
            "Piense y hágase rico – Napoleon Hill",
            "La psicología del dinero – Morgan Housel",
            "El hombre más rico de Babilonia – George S. Clason",
            "Cómo ganar amigos e influir sobre las personas – Dale Carnegie",
            "De cero a uno – Peter Thiel"
    };

    private static final String[] VIDEOS = {
            "Cómo funcionan los ETFs explicado fácil",
            "La regla 50/30/20 para organizar tu dinero",
            "Principios básicos de inversión para principiantes",
            "El interés compuesto explicado en 5 minutos",
            "Cómo superar la ansiedad financiera",
            "7 hábitos de personas económicamente exitosas",
            "Qué es la libertad financiera y cómo alcanzarla",
            "Cómo evitar compras impulsivas",
            "Guía de inversión a largo plazo",
            "Ahorra más con estos trucos psicológicos"
    };

    public static String libroAleatorio() {
        return LIBROS[new Random().nextInt(LIBROS.length)];
    }

    public static String videoAleatorio() {
        return VIDEOS[new Random().nextInt(VIDEOS.length)];
    }
}
