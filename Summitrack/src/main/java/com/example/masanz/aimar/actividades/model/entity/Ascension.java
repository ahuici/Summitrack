package com.example.masanz.aimar.actividades.model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Ascension")
public class Ascension {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private int peligrosidad;

    @Column(nullable = true)
    private String foto;

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

    @Column
    private String apuntes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id")
    private Monte monte;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "ascension", orphanRemoval = true)
    private List<Completa> completa;

    public Ascension() {
    }

    public Ascension(Integer id, int peligrosidad, String foto, int desnivel, int distancia, int dificultad, String tipo, int tiempo, LocalDate fecha, Monte monte, String apuntes) {
        this.id = id;
        this.peligrosidad = peligrosidad;
        this.foto = foto;
        this.desnivel = desnivel;
        this.distancia = distancia;
        this.dificultad = dificultad;
        this.tipo = tipo;
        this.tiempo = tiempo;
        this.fecha = fecha;
        this.monte = monte;
        this.apuntes = apuntes;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

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

    public String getApuntes() {
        return apuntes;
    }

    public void setApuntes(String apuntes) {
        this.apuntes = apuntes;
    }
}

