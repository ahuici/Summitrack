package com.example.masanz.aimar.actividades.model.service;

import com.example.masanz.aimar.actividades.model.DAO.IMonteDAO;
import com.example.masanz.aimar.actividades.model.DAO.IAscensionDAO;
import com.example.masanz.aimar.actividades.model.entity.Monte;
import com.example.masanz.aimar.actividades.model.entity.TiempoDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class MonteService {

    private final Integer saltoHora = 6; //Numero de horas de diferencia entre cada actualizacion del tiempo meteorologico

    @Autowired
    private IMonteDAO monteDAO;

    @Autowired
    private IAscensionDAO ascensionDAO;

    public List<Monte> getAll(){
        return monteDAO.findAll();
    }

    public Monte findByID(Integer id){
        return monteDAO.findById(id).orElse(null);
    }

    public void save(Monte monte){ monteDAO.save(monte);}

    public boolean existe(Monte monte){
        return monteDAO.existsById(monte.getId());
    }

    public void delete(Monte monte){
        monteDAO.delete(monte);
    }

    public List<Monte> getFavoritos(){
        List<Monte> favoritos = new ArrayList<>();
        for (Monte monte : getAll()){
            if (monte.isFavorito()) favoritos.add(monte);
        }
        return favoritos;
    }

    public List<TiempoDTO> getTiempoMonte(Monte monte){
        List<TiempoDTO> tiempo = new ArrayList<>();

        Double latitud = monte.getLatitud();
        Double longitud = monte.getLongitud();
        Integer altura = monte.getAltura();

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.open-meteo.com/v1/forecast?latitude=" + latitud +
                            "&longitude=" + longitud + "&elevation=" + altura
                            + "&hourly=temperature_2m" +
                            "&hourly=wind_speed_10m" +
                            "&hourly=precipitation_probability" +
                            "&hourly=snow_depth" +
                            "&hourly=snowfall"))
                    .header("User-Agent", "SummitrackApp/1.0 (aimarhuici@gmail.com)")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Sacar nombre del JSON
            ObjectMapper objetoJSON = new ObjectMapper();
            JsonNode nodoRoot = objetoJSON.readTree( response.body());
            JsonNode horario = nodoRoot.path("hourly");

            List<String> horas = new ArrayList<>();
            List<Double> temperaturas = new ArrayList<>();
            List<Double> viento = new ArrayList<>();
            List<Integer> probLluvia = new ArrayList<>();
            List<Double> nieveAcumulada = new ArrayList<>();
            List<Double> nuevaNieve = new ArrayList<>();

            for (JsonNode node : horario.path("time")) {
                horas.add(node.asText());
            }

            for (JsonNode node : horario.path("temperature_2m")) {
                temperaturas.add(node.asDouble());
            }

            for (JsonNode node : horario.path("wind_speed_10m")) {
                viento.add(node.asDouble());
            }

            for (JsonNode node : horario.path("precipitation_probability")) {
                probLluvia.add(node.asInt());
            }

            for (JsonNode node : horario.path("snowfall")) {
                nuevaNieve.add(node.asDouble());
            }

            for (JsonNode node : horario.path("snow_depth")) {
                nieveAcumulada.add(node.asDouble());
            }

            for (int i = 0; i < horas.size(); i += saltoHora){
                DateTimeFormatter formatoEntrada = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                DateTimeFormatter formatoSalida = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                LocalDateTime fechaHora = LocalDateTime.parse(horas.get(i), formatoEntrada);

                tiempo.add(new TiempoDTO(fechaHora.getDayOfMonth(),fechaHora.getMonth().getValue(), fechaHora.getHour(),
                        temperaturas.get(i), viento.get(i), probLluvia.get(i),nieveAcumulada.get(i), nuevaNieve.get(i)));
            }
            tiempo.sort(Comparator.comparing(TiempoDTO::getDia));
            return tiempo;
        } catch (Exception e) {
            System.out.println("ERROR: getTiempoDeMonte (MonteController) --> "); e.printStackTrace();
        }

        return null;
    }

}

