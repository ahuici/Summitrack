package com.example.masanz.aimar.actividades.model.service;

import com.example.masanz.aimar.actividades.model.entity.Monte;
import com.example.masanz.aimar.actividades.model.entity.MonteAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Service
public class MendiklopediaService {

    @Autowired
    private RestTemplate restTemplate;

    public List<MonteAPI> getAll() {
        List<MonteAPI> allMontes = conectAPI("montes");
        return conectAPI("montes");  // Devuelve la lista de montes
    }

    public List<MonteAPI> getMontesNavarra() {
        List<MonteAPI> navarra = new ArrayList<>();
        List<MonteAPI> api = conectAPI("montes");
        if (api == null) return null;

        for (MonteAPI monte : api){
            if (monte.getProvincia().equals("Navarra")) navarra.add(monte);
        }
        return navarra;
    }

    public List<MonteAPI> getMontesTresmiles() {
        List<MonteAPI> tresmiles = new ArrayList<>();
        List<MonteAPI> api = conectAPI("montes");
        if (api == null) return null;

        for (MonteAPI monte : api){
            if (monte.getProvincia().equals("Huesca") || monte.getProvincia().equals("Lleida") && monte.getAltura()>= 3000) tresmiles.add(monte);
        }
        return tresmiles;
    }

//    public List<MonteAPI> getFavoritos(){
//        List<MonteAPI> favoritos = new ArrayList<>();
//        for (MonteAPI monte : getAll()){
//            if (monte.isFavorite()) favoritos.add(monte);
//        }
//        return favoritos;
//    }

    private List<MonteAPI> conectAPI(String url){

        try {
            String apiUrl = "http://localhost:5000/" + url; // La URL de la API C#

            // Realizamos la solicitud GET y la convertimos en una lista de objetos Monte
            ResponseEntity<List<MonteAPI>> response = restTemplate.exchange(
                    apiUrl,
                    org.springframework.http.HttpMethod.GET,
                    null,
                    new org.springframework.core.ParameterizedTypeReference<List<MonteAPI>>() {}
            );

            return response.getBody();
        } catch (Exception e) {
            return null;
        }
    }

}
