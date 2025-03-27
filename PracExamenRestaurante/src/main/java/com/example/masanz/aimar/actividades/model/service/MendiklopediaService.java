package com.example.masanz.aimar.actividades.model.service;

import com.example.masanz.aimar.actividades.model.entity.Monte;
import com.example.masanz.aimar.actividades.model.entity.MonteAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Service
public class MendiklopediaService {

    @Autowired
    private RestTemplate restTemplate;

    public List<MonteAPI> getMontes() {
        String apiUrl = "http://localhost:5000/montes"; // La URL de la API C#

        // Realizamos la solicitud GET y la convertimos en una lista de objetos Monte
        ResponseEntity<List<MonteAPI>> response = restTemplate.exchange(
                apiUrl,
                org.springframework.http.HttpMethod.GET,
                null,
                new org.springframework.core.ParameterizedTypeReference<List<MonteAPI>>() {}
        );

        return response.getBody();  // Devuelve la lista de montes
    }

    public List<MonteAPI> getMontesNavarra() {
        String apiUrl = "http://localhost:5000/montes"; // La URL de la API C#

        // Realizamos la solicitud GET y la convertimos en una lista de objetos Monte
        ResponseEntity<List<MonteAPI>> response = restTemplate.exchange(
                apiUrl,
                org.springframework.http.HttpMethod.GET,
                null,
                new org.springframework.core.ParameterizedTypeReference<List<MonteAPI>>() {}
        );

        List<MonteAPI> navarra = new ArrayList<>();
        for (MonteAPI monte : response.getBody()){
            if (monte.getProvincia().equals("Navarra")) navarra.add(monte);
        }
        return navarra;
    }

    public List<MonteAPI> getMontesTresmiles() {
        String apiUrl = "http://localhost:5000/montes"; // La URL de la API C#

        // Realizamos la solicitud GET y la convertimos en una lista de objetos Monte
        ResponseEntity<List<MonteAPI>> response = restTemplate.exchange(
                apiUrl,
                org.springframework.http.HttpMethod.GET,
                null,
                new org.springframework.core.ParameterizedTypeReference<List<MonteAPI>>() {}
        );

        List<MonteAPI> tresmiles = new ArrayList<>();
        for (MonteAPI monte : response.getBody()){
            if (monte.getProvincia().equals("Huesca") || monte.getProvincia().equals("Lleida") && monte.getAltura()>= 3000) tresmiles.add(monte);
        }
        return tresmiles;
    }
}
