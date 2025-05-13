package com.example.masanz.aimar.actividades.controller;

import com.example.masanz.aimar.actividades.model.entity.MapaDTO;
import com.example.masanz.aimar.actividades.model.entity.TiempoDTO;
import com.example.masanz.aimar.actividades.model.service.MonteService;
import com.example.masanz.aimar.actividades.model.entity.Monte;
import com.example.masanz.aimar.actividades.model.service.AscensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class MonteController {

    @Autowired
    private MonteService monteService;

    @Autowired
    private AscensionService ascensionService;

    @GetMapping("/monte/eliminar")
    public String eliminarMonte(@RequestParam(name ="id") Integer id, Model model) {
        Monte monte = monteService.findByID(id);
        monteService.delete(monte);

        return "redirect:/ver/monte";
    }

    @GetMapping("/monte/ascension")
    public String verAscension(@RequestParam(name ="id") Integer id, Model model) {
        model.addAttribute("ascensiones", ascensionService.getAllById(id));
        model.addAttribute("volver", "/monte/monte?id=" + id + "&volver=/ver/monte");

        return "monte/allAscensiones";
    }

    @GetMapping("/monte/favoritos")
    public String addFavorito(Model model,
                              @RequestParam(name = "id", required = false) Integer id){

        Monte monte = monteService.findByID(id);

        if (!monte.isFavorito()) monte.setFavorito(Boolean.TRUE);
        else monte.setFavorito(Boolean.FALSE);

        monteService.save(monte);
        model.addAttribute("montes", monteService.getAll());

        return "redirect:/ver/monte";
    }

    @GetMapping("/monte/monte")
    public String verMonteIndividual(Model model,
                                     @RequestParam(name = "id") Integer id,
                                     @RequestParam(name = "volver") String volver){
        Monte monte = monteService.findByID(id);
        List<TiempoDTO> tiempo = monteService.getTiempoMonte(monte);
        tiempo.sort(Comparator.comparingInt(TiempoDTO::getMes).thenComparingInt(TiempoDTO::getDia));

        model.addAttribute("monte",monte);
        model.addAttribute("tiempo",tiempo);
        model.addAttribute("volver",volver);

        return "monte/individual";
    }

    @GetMapping("/monte/monte/sacarUbicacion")
    public String sacarUbicacion(Model model,
                                     @RequestParam(name = "id") Integer id){
        Monte monte = monteService.findByID(id);
        model.addAttribute("monte",monte);
        model.addAttribute("id",id);
        model.addAttribute("volver","/monte/monte?id=" + id + "&volver=/ver/monte");

        return "monte/sacarUbicacion";
    }

    @PostMapping("/guardar-ubicacion")
    public String obtenerUbicacion(@RequestBody Map<String, Object> ubicacion) {
        double latitud = (double) ubicacion.get("latitud");
        double longitud = (double) ubicacion.get("longitud");

        return "";
    }

    @GetMapping("/monte/monte/comoLlegar")
    public String verComoLlegar(Model model,
                                @RequestParam(name = "id") Integer id,
                                @RequestParam(name = "latitud") double latitud,
                                @RequestParam(name = "longitud") double longitud){
        Monte monte = monteService.findByID(id);
        model.addAttribute("inicio",new MapaDTO(monte.getId(), monte.getNombre(), monte.getLatitud(), monte.getLongitud()));
        model.addAttribute("final",new MapaDTO(0,"Inicio",latitud, longitud));
        model.addAttribute("id",id);
        model.addAttribute("volver","/ver/monte");

        return "monte/comoLlegar";
    }
}
