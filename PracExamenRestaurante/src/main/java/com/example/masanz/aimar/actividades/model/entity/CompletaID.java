package com.example.masanz.aimar.actividades.model.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Embeddable
public class CompletaID {

    private Integer idRuta;
    private Integer idPersona;

    public CompletaID(Integer idRuta, Integer idPersona) {
        this.idRuta = idRuta;
        this.idPersona = idPersona;
    }

    public CompletaID() {
    }

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

    @Override
    public String toString() {
        return "VendeID{" +
                "idRuta =" + idRuta +
                ", idPersona=" + idPersona +
                '}';
    }
}
