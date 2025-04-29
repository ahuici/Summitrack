package com.example.masanz.aimar.actividades.model.service;

import com.example.masanz.aimar.actividades.model.entity.Monte;
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

    public List<Monte> getAll() {
        List<Monte> allMontes = conectAPI("montes");
        return conectAPI("montes");  // Devuelve la lista de montes
    }

    public List<Monte> getMontesNavarra() {
        List<Monte> navarra = new ArrayList<>();
        List<Monte> api = conectAPI("montes");
        if (api == null) return null;

        for (Monte monte : api){
            if (monte.getUbicacion().equals("Navarra")) navarra.add(monte);
        }
        return navarra;
    }

    public List<Monte> getMontesTresmiles() {
        List<Monte> tresmiles = new ArrayList<>();
        List<Monte> api = conectAPI("montes");
        if (api == null) return null;

        for (Monte monte : api){
            if (monte.getUbicacion().equals("Huesca") || monte.getUbicacion().equals("Lleida") && monte.getAltura()>= 3000) tresmiles.add(monte);
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

    private List<Monte> conectAPI(String url){

        try {
            String apiUrl = "http://localhost:5000/" + url; // La URL de la API C#

            // Realizamos la solicitud GET y la convertimos en una lista de objetos Monte
            ResponseEntity<List<Monte>> response = restTemplate.exchange(
                    apiUrl,
                    org.springframework.http.HttpMethod.GET,
                    null,
                    new org.springframework.core.ParameterizedTypeReference<List<Monte>>() {}
            );

            return response.getBody();
        } catch (Exception e) {
            return null;
        }
    }

}
