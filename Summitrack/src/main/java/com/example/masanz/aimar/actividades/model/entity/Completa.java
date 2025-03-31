package com.example.masanz.aimar.actividades.model.entity;


import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Comment;

@Entity
@Table
public class Completa {
    @Id
    private CompletaID completaID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id")
    private Ruta ruta;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id")
    private Persona persona;

    public Completa() {
    }

    public Completa(Ruta ruta, Persona persona) {
        this.completaID = new CompletaID(ruta.getId(), persona.getId());
        this.ruta = ruta;
        this.persona = persona;
    }

    public CompletaID getCompletaID() {
        return completaID;
    }

    public void setCompletaID(CompletaID completaID) {
        this.completaID = completaID;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}
