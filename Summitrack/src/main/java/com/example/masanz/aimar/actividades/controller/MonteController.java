package com.example.masanz.aimar.actividades.controller;

import com.example.masanz.aimar.actividades.model.entity.TiempoDTO;
import com.example.masanz.aimar.actividades.model.service.MonteService;
import com.example.masanz.aimar.actividades.model.entity.Monte;
import com.example.masanz.aimar.actividades.model.service.RutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MonteController {

    @Autowired
    private MonteService monteService;

    @Autowired
    private RutaService rutaService;

    @GetMapping("/monte")
    public String getAll(Model model){
        model.addAttribute("montes", monteService.getAll());
        return "monte/all";
    }

    @GetMapping("/monte/add")
    public String addMonteGet(@RequestParam(name ="id", required = false) Integer id,Model model){
        Monte monte  = new Monte();
        if (id != null) {
            monte = monteService.findByID(id);
            model.addAttribute("editar", true);
        }
        model.addAttribute("monte", monte);
        return "monte/add";
    }

    @PostMapping("/monte/add")
    public String addMontePost(@ModelAttribute Monte monte, Model model){
        monteService.save(monte);
        return "redirect:/monte";
    }

    @GetMapping("/monte/eliminar")
    public String eliminarMonte(@RequestParam(name ="id") Integer id, Model model) {
        Monte monte = monteService.findByID(id);

        monteService.delete(monte);
        return "redirect:/monte";
    }

    @GetMapping("/monte/rutas")
    public String verRuta(@RequestParam(name ="id") Integer id, Model model) {
        model.addAttribute("rutas", rutaService.getAllById(id));
        return "monte/allRutas";
    }

    @GetMapping("/monte/favoritos")
    public String addFavorito(Model model,
                              @RequestParam(name = "id", required = false) Integer id){

        Monte monte = monteService.findByID(id);

        if (!monte.isFavorito()) monte.setFavorito(Boolean.TRUE);
        else monte.setFavorito(Boolean.FALSE);

        monteService.save(monte);
        model.addAttribute("montes", monteService.getAll());
        return "redirect:/monte";
    }

    @GetMapping("/monte/tiempo")
    public String verTiempo(Model model, @RequestParam(name = "id") Integer id){
        Monte monte = monteService.findByID(id);
        List<TiempoDTO> tiempos = monteService.getTiempoMonte(monte);

        model.addAttribute("monte", monte);
        Map<String, List<TiempoDTO>> tiemposAgrupados = tiempos.stream()
                .collect(Collectors.groupingBy(t -> t.getDia() + "/" + t.getMes()));
        model.addAttribute("tiemposAgrupadosPorDia", tiemposAgrupados);

        return "monte/tiempo";
    }

    @GetMapping("/monte/monte")
    public String verMonteIndividual(Model model,
                                     @RequestParam(name = "id") Integer id,
                                     @RequestParam(name = "mapa") boolean mapa){
        Monte monte = monteService.findByID(id);
        List<TiempoDTO> tiempo = monteService.getTiempoMonte(monte);
        model.addAttribute("monte",monte);
        model.addAttribute("tiempo",tiempo);
        model.addAttribute("mapa",mapa);

        return "monte/individual";
    }

    @GetMapping("/monte/monte/comoLlegar")
    public String verComoLlegar(Model model,
                                     @RequestParam(name = "id") Integer id){
        Monte monte = monteService.findByID(id);
        model.addAttribute("monte",monte);

        return "monte/comoLlegar";
    }
    @PostMapping("/guardar-ubicacion")
    public String obtenerUbicacion(@RequestBody Map<String, Object> ubicacion) {
        double latitud = (double) ubicacion.get("latitud");
        double longitud = (double) ubicacion.get("longitud");

        // Lógica para procesar la ubicación si es necesario
        System.out.println("{\"latitud\": " + latitud + ", \"longitud\": " + longitud + "}");

        return "";
    }
}
