package com.example.masanz.aimar.actividades.model.entity;

import jakarta.persistence.*;

@Entity
public class Ubicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Double latitud;

    @Column
    private Double longitud;

    @Column
    private String nombre;


    public Ubicacion(Integer id, Double latitud, Double longitud, String nombre) {
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.nombre = nombre;
    }

    public Ubicacion() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
