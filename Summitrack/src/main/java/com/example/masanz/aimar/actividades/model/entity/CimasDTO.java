package com.example.masanz.aimar.actividades.model.entity;

public class CimasDTO {

    private String nombre;
    private Integer cimas;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCimas() {
        return cimas;
    }

    public void setCimas(Integer cimas) {
        this.cimas = cimas;
    }

    public CimasDTO(Persona persona, Integer cimas) {
        this.nombre = persona.getNombre();
        this.cimas = cimas;
    }
}
