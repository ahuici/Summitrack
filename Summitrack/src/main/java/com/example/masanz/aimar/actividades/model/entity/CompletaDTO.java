package com.example.masanz.aimar.actividades.model.entity;

public class CompletaDTO {
    private Integer idRuta;
    private Integer idPersona;

    public Integer getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(Integer idRuta) {
        this.idRuta = idRuta;
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public CompletaDTO(Integer idRuta, Integer idPersona) {
        this.idRuta = idRuta;
        this.idPersona = idPersona;
    }
}
