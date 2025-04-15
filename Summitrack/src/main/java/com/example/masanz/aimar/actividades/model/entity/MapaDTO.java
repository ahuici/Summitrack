package com.example.masanz.aimar.actividades.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MapaDTO {
    @JsonProperty("nombre")
    private String nombre;

    @JsonProperty("latitud")
    private double latitud;

    @JsonProperty("longitud")
    private double longitud;

    public MapaDTO(String nombre, double latitud, double longitud) {
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public MapaDTO() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
