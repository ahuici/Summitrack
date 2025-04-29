package com.example.masanz.aimar.actividades.model.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class CompletaID {

    private Integer idAscension;
    private Integer idPersona;

    public CompletaID(Integer idAscension, Integer idPersona) {
        this.idAscension = idAscension;
        this.idPersona = idPersona;
    }

    public CompletaID() {
    }

    public Integer getIdAscension() {
        return idAscension;
    }

    public void setIdAscension(Integer idAscension) {
        this.idAscension = idAscension;
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    @Override
    public String toString() {
        return "VendeID{" +
                "idAscension =" + idAscension +
                ", idPersona=" + idPersona +
                '}';
    }
}
