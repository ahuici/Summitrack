package com.example.masanz.aimar.actividades.model.entity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Calendario")
public class Calendario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String titulo;

    @Column
    private String descripcion;

    @Column
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id")
    private Monte monte;

    public Calendario(Integer id, String titulo, String descripcion, String personas, LocalDate fecha, Monte monte) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.monte = monte;

    }

    public Calendario() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

//    public String getPersonas() {
//        return personas;
//    }
//
//    public void setPersonas(String personas) {
//        this.personas = personas;
//    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Monte getMonte() {
        return monte;
    }

    public void setMonte(Monte monte) {
        this.monte = monte;
    }
}

