package com.example.masanz.aimar.actividades.model.entity;

public class CorreoDTO {

    private String asunto;

    private String cuerpo;

    private String nombre;

    private String remitente;

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public CorreoDTO(String asunto, String cuerpo, String nombre, String remitente) {
        this.asunto = asunto;
        this.cuerpo = cuerpo;
        this.nombre = nombre;
        this.remitente = remitente;
    }

    public CorreoDTO() {
    }
}
