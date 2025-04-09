package com.example.masanz.aimar.actividades.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class  Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String nombre;

    @Column
    private String apellidos;

    @Column
    private String genero;

    @Column
    private int edad;

    @Column
    private boolean salirTop;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persona", orphanRemoval = true)
    private List<Completa> completa;

    public Persona() {
    }

    public Persona(Integer id, String nombre, String apellidos, String genero, int edad, boolean salirTop) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.genero = genero;
        this.edad = edad;
        this.salirTop = salirTop;
        this.completa = new ArrayList<>();
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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public boolean isSalirTop() {
        return salirTop;
    }

    public void setSalirTop(boolean salirTop) {
        this.salirTop = salirTop;
    }

    public List<Completa> getCompleta() {
        return completa;
    }

    public void setCompleta(List<Completa> completa) {
        this.completa = completa;
    }

}
