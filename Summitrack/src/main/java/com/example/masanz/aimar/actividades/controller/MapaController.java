package com.example.masanz.aimar.actividades.controller;

import com.example.masanz.aimar.actividades.model.entity.Monte;
import com.example.masanz.aimar.actividades.model.service.MonteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MapaController {

    @Autowired
    private MonteService monteService;

    @GetMapping("/mapa")
    public String mostrarMapa(Model model) {
        model.addAttribute("montes", monteService.pasarMonteAMonteDTO());

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
        if (checkbox) monte.setUbicacion(monteService.sacarNombrePorCordenadas(lat, lon));
        model.addAttribute("monte", monte);

        return "mapa/mapaAdd";
    }

    @PostMapping("/mapa/guardar")
    public String addMontePost(@ModelAttribute Monte monte, Model model){
        String respuesta = monteService.save(monte);
        if (respuesta != null){
            model.addAttribute("msg",respuesta);
            model.addAttribute("volver", "javascript:window.close()");

            return "error";
        }
        model.addAttribute("isExitoso", true);

        return "mapa/cerrar";
    }

    @GetMapping("/mapa/salir")
    public String salirAgregar(Model model){
        model.addAttribute("isExitoso", false);
        return "mapa/cerrar";
    }

    @GetMapping("/mapa/crearRuta")
    public String crearRuta(Model model){
        model.addAttribute("montes", monteService.pasarMonteAMonteDTO());

        return "mapa/crearRuta";
    }
}
