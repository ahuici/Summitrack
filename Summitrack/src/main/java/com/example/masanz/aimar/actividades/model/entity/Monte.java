package com.example.masanz.aimar.actividades.model.entity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Monte")
public class Monte {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String nombre;

    @Column
    private String ubicacion;

    @Column
    private Integer altura;

    @Column
    private Double latitud;

    @Column
    private Double longitud;

    @Column
    private Boolean isFavorito = Boolean.FALSE;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "monte", orphanRemoval = true)
    private List<Ascension> asciende;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "monte", orphanRemoval = true)
    private List<Calendario> futurasAscensiones;



    public Monte() {
    }

    public Monte(Integer id, String nombre, String ubicacion, Integer altura, Double latitud, Double longitud) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.altura = altura;
        this.latitud = latitud;
        this.longitud = longitud;
        this.asciende = new ArrayList<>();
        this.futurasAscensiones = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Integer getAltura() {
        return altura;
    }

    public void setAltura(Integer altura) {
        this.altura = altura;
    }

    public List<Ascension> getAsciende() {
        return asciende;
    }

    public Boolean isFavorito() {
        return isFavorito;
    }

    public void setFavorito(Boolean favorito) {
        isFavorito = favorito;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Boolean getFavorito() {
        return isFavorito;
    }

    public void setAsciende(List<Ascension> asciendes) {
        this.asciende = asciendes;
    }

    public void addAsciende(Ascension ascension){
        this.asciende.add(ascension);
    }

    public List<Calendario> getFuturasAscensiones() {
        return futurasAscensiones;
    }

    public void setFuturasAscensiones(List<Calendario> futurasAscensiones) {
        this.futurasAscensiones = futurasAscensiones;
    }
}

