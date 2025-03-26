package com.example.masanz.aimar.actividades.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.IdGeneratorType;
import org.hibernate.grammars.hql.HqlParser;
import org.springframework.boot.autoconfigure.ssl.SslProperties;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Ruta")
public class Ruta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private int peligrosidad;

//    @Lob
//    @Column(nullable = true)
//    private byte[] foto;

    @Column
    private int desnivel;

    @Column
    private int distancia;

    @Column
    private int dificultad;

    @Column
    private String tipo;

    @Column
    private int tiempo;

    @Column
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id")
    private Monte monte;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "ruta", orphanRemoval = true)
    private List<Completa> completa;

    public Ruta() {
    }

    public Ruta(Integer id, int peligrosidad,  int desnivel, int distancia, int dificultad, String tipo, int tiempo, LocalDate fecha, Monte monte) {
        this.id = id;
        this.peligrosidad = peligrosidad;
//        this.foto = foto;
        this.desnivel = desnivel;
        this.distancia = distancia;
        this.dificultad = dificultad;
        this.tipo = tipo;
        this.tiempo = tiempo;
        this.fecha = fecha;
        this.monte = monte;
        this.completa = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPeligrosidad() {
        return peligrosidad;
    }

    public void setPeligrosidad(int peligrosidad) {
        this.peligrosidad = peligrosidad;
    }

//    public byte[] getFoto() {
//        return foto;
//    }
//
//    public void setFoto(byte[] foto) {
//        this.foto = foto;
//    }

    public int getDesnivel() {
        return desnivel;
    }

    public void setDesnivel(int desnivel) {
        this.desnivel = desnivel;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public int getDificultad() {
        return dificultad;
    }

    public void setDificultad(int dificultad) {
        this.dificultad = dificultad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

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

    public List<Completa> getCompleta() {
        return completa;
    }

    public void setCompleta(List<Completa> completa) {
        this.completa = completa;
    }

    public void addCompleta(Completa completa){
        this.completa.add(completa);
    }
}

