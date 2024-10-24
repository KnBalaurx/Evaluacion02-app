package com.example.appflowtask01;

public class EstudioRutina {
    private String nombre;
    private int tiempoEstudio;
    private int tiempoDescanso;
    private int intervaloDescanso;

    // Constructor vacío (necesario para Firestore)
    public EstudioRutina() {}

    // Constructor con todos los campos
    public EstudioRutina(String nombre, int tiempoEstudio, int tiempoDescanso, int intervaloDescanso) {
        this.nombre = nombre;
        this.tiempoEstudio = tiempoEstudio;
        this.tiempoDescanso = tiempoDescanso;
        this.intervaloDescanso = intervaloDescanso;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTiempoEstudio() {
        return tiempoEstudio;
    }

    public void setTiempoEstudio(int tiempoEstudio) {
        this.tiempoEstudio = tiempoEstudio;
    }

    public int getTiempoDescanso() {
        return tiempoDescanso;
    }

    public void setTiempoDescanso(int tiempoDescanso) {
        this.tiempoDescanso = tiempoDescanso;
    }

    public int getIntervaloDescanso() {
        return intervaloDescanso;
    }

    public void setIntervaloDescanso(int intervaloDescanso) {
        this.intervaloDescanso = intervaloDescanso;
    }
}

