package com.example.masanz.aimar.actividades.model.entity;

import java.util.List;

public class AscensionDTO {
    private String id;
    private Integer idMySQL;
    private Integer desnivel;
    private Integer distancia;
    private List<Integer> personas;

    public AscensionDTO(String id, Integer idMySQL, Integer desnivel, Integer distancia, List<Integer> personas) {
        this.id = id;
        this.idMySQL = idMySQL;
        this.desnivel = desnivel;
        this.distancia = distancia;
        this.personas = personas;
    }

    public AscensionDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIdMySQL() {
        return idMySQL;
    }

    public void setIdMySQL(Integer idMySQL) {
        this.idMySQL = idMySQL;
    }

    public Integer getDesnivel() {
        return desnivel;
    }

    public void setDesnivel(Integer desnivel) {
        this.desnivel = desnivel;
    }

    public Integer getDistancia() {
        return distancia;
    }

    public void setDistancia(Integer distancia) {
        this.distancia = distancia;
    }

    public List<Integer> getPersonas() {
        return personas;
    }

    public void setPersonas(List<Integer> personas) {
        this.personas = personas;
    }
}
