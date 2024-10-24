package com.example.appflowtask01.models;

import com.google.firebase.firestore.PropertyName;

public class Proyecto {

    private String titulo;
    private String descripcion;
    private String fechaEntrega;
    private String cantidadIntegrantes;
    private String ramo;

    // Constructor vacío necesario para Firestore
    public Proyecto() {}

    public Proyecto(String titulo, String descripcion, String fechaEntrega, String cantidadIntegrantes, String ramo) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaEntrega = fechaEntrega;
        this.cantidadIntegrantes = cantidadIntegrantes;
        this.ramo = ramo;
    }

    @PropertyName("Titulo")
    public String getTitulo() {
        return titulo;
    }

    @PropertyName("Titulo")
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @PropertyName("Descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    @PropertyName("Descripcion")
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @PropertyName("Fecha entrega")
    public String getFechaEntrega() {
        return fechaEntrega;
    }

    @PropertyName("Fecha entrega")
    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    @PropertyName("Cantidad de integrantes")
    public String getCantidadIntegrantes() {
        return cantidadIntegrantes;
    }

    @PropertyName("Cantidad de integrantes")
    public void setCantidadIntegrantes(String cantidadIntegrantes) {
        this.cantidadIntegrantes = cantidadIntegrantes;
    }

    @PropertyName("Ramo")
    public String getRamo() {
        return ramo;
    }

    @PropertyName("Ramo")
    public void setRamo(String ramo) {
        this.ramo = ramo;
    }
}
