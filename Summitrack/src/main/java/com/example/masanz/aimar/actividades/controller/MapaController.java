package com.example.masanz.aimar.actividades.controller;

import com.example.masanz.aimar.actividades.model.entity.Monte;
import com.example.masanz.aimar.actividades.model.entity.Ubicacion;
import com.example.masanz.aimar.actividades.model.service.MonteService;
import com.example.masanz.aimar.actividades.model.service.UbicacionService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Controller
public class MapaController {

    @Autowired
    private UbicacionService ubicacionService;

    @Autowired
    private MonteService monteService;

    @GetMapping("/mapa")
    public String mostrarMapa(Model model) {
        List<Ubicacion> ubicaciones = ubicacionService.getAll();
        model.addAttribute("ubicaciones", ubicaciones);
        return "mapa/mapa";
    }

    @GetMapping("/mapa/guardar")
    public String guardarMonte(@RequestParam(required = false) Double lat,
                               @RequestParam(required = false) Double lon,
                               @RequestParam(required = false) boolean checkbox,
                               Model model) {
        Monte monte = new Monte();
        monte.setLatitud(lat);
        monte.setLongitud(lon);
        if (!checkbox) monte.setUbicacion(sacarNombrePorCordenadas(lat, lon));
        model.addAttribute("monte", monte);
        return "mapa/mapaAdd";
    }

    @PostMapping("/mapa/guardar")
    public String addMontePost(@ModelAttribute Monte monte, Model model){
        monteService.save(monte);
        model.addAttribute("isExitoso", true);
        return "mapa/cerrar";
    }

    @GetMapping("/mapa/salir")
    public String salirAgregar(@ModelAttribute Monte monte, Model model){
        monteService.save(monte);
        model.addAttribute("isExitoso", false);
        return "mapa/cerrar";
    }

    private String sacarNombrePorCordenadas(Double latitud, Double longitud){
        String nombre = "Sin especificar";
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://nominatim.openstreetmap.org/reverse?lat=" + latitud + "&lon=" + longitud + "&format=json"))
                    .header("User-Agent", "SummitrackApp/1.0 (aimarhuici@gmail.com)") 
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Sacar nombre del JSON
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree( response.body());
            JsonNode addressNode = rootNode.path("name");
            nombre = addressNode.asText();
        } catch (Exception e) {
            System.out.println("ERROR: SacarNombrePorCordenadas (MapaController) --> "); e.printStackTrace();
        }
        return nombre;
    }
}
