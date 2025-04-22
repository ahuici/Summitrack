package com.example.masanz.aimar.actividades.model.entity;

public class TiempoDTO {
    private Integer dia;
    private Integer mes;
    private Integer hora;
    private double temperatura;
    private double viento;
    private Integer probLluvia;
    private double nieveAcumulada; //Nieve ya acumulada de antes
    private double precipitacionNieve; //Nieve nueva que va ha caer

    public TiempoDTO() {
    }

    public TiempoDTO(Integer dia, Integer mes, Integer hora, double temperatura, double viento, Integer probLluvia, double nieveAcumulada, double precipitacionNieve) {
        this.dia = dia;
        this.mes = mes;
        this.hora = hora;
        this.temperatura = temperatura;
        this.viento = viento;
        this.probLluvia = probLluvia;
        this.nieveAcumulada = nieveAcumulada;
        this.precipitacionNieve = precipitacionNieve;
    }

    public Integer getDia() {
        return dia;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getHora() {
        return hora;
    }

    public void setHora(Integer hora) {
        this.hora = hora;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getViento() {
        return viento;
    }

    public void setViento(double viento) {
        this.viento = viento;
    }

    public Integer getProbLluvia() {
        return probLluvia;
    }

    public void setProbLluvia(Integer probLluvia) {
        this.probLluvia = probLluvia;
    }

    public double getNieveAcumulada() {
        return nieveAcumulada;
    }

    public void setNieveAcumulada(double nieveAcumulada) {
        this.nieveAcumulada = nieveAcumulada;
    }

    public double getPrecipitacionNieve() {
        return precipitacionNieve;
    }

    public void setPrecipitacionNieve(double precipitacionNieve) {
        this.precipitacionNieve = precipitacionNieve;
    }
}
