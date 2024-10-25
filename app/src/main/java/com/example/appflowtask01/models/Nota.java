package com.example.appflowtask01.models;

public class Nota {
    private String id;
    private String titulo;
    private String descripcion;

    public Nota(String id, String titulo, String descripcion) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
