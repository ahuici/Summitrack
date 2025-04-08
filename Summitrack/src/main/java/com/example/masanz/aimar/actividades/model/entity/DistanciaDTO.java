package com.example.masanz.aimar.actividades.model.entity;

public class DistanciaDTO {
    private String nombre;
    private Integer distancia;

    public DistanciaDTO(Persona persona, Integer distancia) {
        this.nombre = persona.getNombre();
        this.distancia = distancia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getDistancia() {
        return distancia;
    }

    public void setDistancia(Integer distancia) {
        this.distancia = distancia;
    }
}
