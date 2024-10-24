package com.example.appflowtask01.models;

import com.google.firebase.firestore.PropertyName;

public class Evaluacion {

    private String descripcion;
    private String fechaEntrega;
    private String nombreTarea;
    private String ramoSeleccionado;

    // Constructor vacío necesario para Firestore
    public Evaluacion() {}

    public Evaluacion(String descripcion, String fechaEntrega, String nombreTarea, String ramoSeleccionado) {
        this.descripcion = descripcion;
        this.fechaEntrega = fechaEntrega;
        this.nombreTarea = nombreTarea;
        this.ramoSeleccionado = ramoSeleccionado;
    }

    @PropertyName("descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    @PropertyName("descripcion")
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @PropertyName("fechaEntrega")
    public String getFechaEntrega() {
        return fechaEntrega;
    }

    @PropertyName("fechaEntrega")
    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    @PropertyName("nombreTarea")
    public String getNombreTarea() {
        return nombreTarea;
    }

    @PropertyName("nombreTarea")
    public void setNombreTarea(String nombreTarea) {
        this.nombreTarea = nombreTarea;
    }

    @PropertyName("ramoSeleccionado")
    public String getRamoSeleccionado() {
        return ramoSeleccionado;
    }

    @PropertyName("ramoSeleccionado")
    public void setRamoSeleccionado(String ramoSeleccionado) {
        this.ramoSeleccionado = ramoSeleccionado;
    }
}

