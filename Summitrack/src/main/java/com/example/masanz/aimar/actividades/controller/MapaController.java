package com.example.masanz.aimar.actividades.controller;

import com.example.masanz.aimar.actividades.model.entity.Monte;
import com.example.masanz.aimar.actividades.model.entity.Ubicacion;
import com.example.masanz.aimar.actividades.model.service.MonteService;
import com.example.masanz.aimar.actividades.model.service.UbicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String guardarMonte(@RequestParam(required = false) Double lat, @RequestParam(required = false) Double lon, Model model) {
//        List<Ubicacion> ubicaciones = ubicacionService.getAll();
//        model.addAttribute("ubicaciones", ubicaciones);
//        model.addAttribute("lat", lat);
//        model.addAttribute("lon", lon); // Pasamos la latitud y longitud al modelo
        System.out.println("Monte en " + lat + " y en " + lon);

        Monte monte = new Monte();
//        monte.setLatitud(lat);
//        monte.setLongitud(lon);
        model.addAttribute("monte", monte);
        return "mapa/mapaAdd";
    }

    @PostMapping("/mapa/guardar")
    public String addMontePost(@ModelAttribute Monte monte, Model model){
        monteService.save(monte);

        return "mapa/cerrar";
    }
}
