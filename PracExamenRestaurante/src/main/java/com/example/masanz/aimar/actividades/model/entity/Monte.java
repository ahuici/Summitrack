package com.example.masanz.aimar.actividades.model.entity;
import jakarta.persistence.*;
import org.hibernate.annotations.IdGeneratorType;

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
    private String apuntes;

    @Column
    private Integer altura;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "monte", orphanRemoval = true)
    private List<Ruta> asciende;



    public Monte() {
    }

    public Monte(Integer id, String nombre, String ubicacion, Integer altura, String apuntes) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.altura = altura;
        this.apuntes = apuntes;
        this.asciende = new ArrayList<>();
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

    public List<Ruta> getAsciende() {
        return asciende;
    }

    public void setAsciende(List<Ruta> asciendes) {
        this.asciende = asciendes;
    }

    public void addAsciende(Ruta ruta){
        this.asciende.add(ruta);
    }

    public String getApuntes() {
        return apuntes;
    }

    public void setApuntes(String apuntes) {
        this.apuntes = apuntes;
    }
}

