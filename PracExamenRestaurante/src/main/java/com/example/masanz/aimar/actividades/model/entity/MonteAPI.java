package com.example.masanz.aimar.actividades.model.entity;

public class MonteAPI {
    private String id;
    private String nombre;
    private String provincia;
    private int altura;

    public MonteAPI(String id, String nombre, String provincia, int altura) {
        this.id = id;
        this.nombre = nombre;
        this.provincia = provincia;
        this.altura = altura;
    }

    public MonteAPI() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }
}
