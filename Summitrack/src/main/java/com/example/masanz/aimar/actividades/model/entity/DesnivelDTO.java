package com.example.masanz.aimar.actividades.model.entity;

public class DesnivelDTO {

    private String nombre;
    private Integer desnivel;

    public DesnivelDTO(Persona persona, Integer desnivel) {
        this.nombre = persona.getNombre();
        this.desnivel = desnivel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getDesnivel() {
        return desnivel;
    }

    public void setDesnivel(Integer desnivel) {
        this.desnivel = desnivel;
    }
}
