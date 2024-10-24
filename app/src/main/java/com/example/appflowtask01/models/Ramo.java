package com.example.appflowtask01.models;

import com.google.firebase.firestore.PropertyName;

public class Ramo {
    private String nombreProfesor;
    private String nombreRamo;
    private String seccion;

    public Ramo() {
        // Constructor vacío requerido por Firestore
    }

    @PropertyName("Nombre Profesor")
    public String getNombreProfesor() {
        return nombreProfesor;
    }

    @PropertyName("Nombre Profesor")
    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = nombreProfesor;
    }

    @PropertyName("Nombre Ramo")
    public String getNombreRamo() {
        return nombreRamo;
    }

    @PropertyName("Nombre Ramo")
    public void setNombreRamo(String nombreRamo) {
        this.nombreRamo = nombreRamo;
    }

    @PropertyName("Seccion")
    public String getSeccion() {
        return seccion;
    }

    @PropertyName("Seccion")
    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }
}
