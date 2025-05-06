package com.example.masanz.aimar.actividades.model.entity;


import jakarta.persistence.*;

@Entity
@Table
public class  Completa {
    @Id
    private CompletaID completaID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id")
    private Ascension ascension;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id")
    private Persona persona;

    public Completa() {
    }

    public Completa(Ascension ascension, Persona persona) {
        this.completaID = new CompletaID(ascension.getId(), persona.getId());
        this.ascension = ascension;
        this.persona = persona;
    }

    public CompletaID getCompletaID() {
        return completaID;
    }

    public void setCompletaID(CompletaID completaID) {
        this.completaID = completaID;
    }

    public Ascension getAscension() {
        return ascension;
    }

    public void setAscension(Ascension ascension) {
        this.ascension = ascension;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}
