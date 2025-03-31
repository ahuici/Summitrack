package com.example.masanz.aimar.actividades.controller;

import com.example.masanz.aimar.actividades.model.entity.Calendario;
import com.example.masanz.aimar.actividades.model.entity.Monte;
import com.example.masanz.aimar.actividades.model.entity.MonteAPI;
import com.example.masanz.aimar.actividades.model.service.MendiklopediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

@Controller
public class MendiklopediaController {

    @Autowired
    private MendiklopediaService mendiklopediaService;

    @GetMapping("/mendiklopedia")
    public String getAPI(Model model){
        return "API/opciones";  // El nombre de la vista donde quieres mostrar los montes
    }

    @GetMapping("/mendiklopedia/navarra")
    public String getAPINavarra(Model model,
                                @RequestParam(name = "campo", required = false) String campo,
                                @RequestParam(name = "orden", required = false) String orden){

        List<MonteAPI> montes = ordenar(campo, orden, mendiklopediaService.getMontesNavarra());

        if (montes == null){
            model.addAttribute("error", "ERROR: Parece ser que el servidor no responde. Intentelo mas tarde. " + LocalTime.now().getHour() + " : " + LocalTime.now().getMinute() + " : " + LocalTime.now().getSecond());
            return "API/opciones";
        }
        // Añadimos los montes al modelo
        model.addAttribute("montes", montes);
        model.addAttribute("url", "navarra");
        model.addAttribute("nombreOpcion", "Montes de Navarra");

        return "API/montes";
    }

    @GetMapping("/mendiklopedia/tresmiles")
    public String getAPITresmiles(Model model,
                                  @RequestParam(name = "campo", required = false) String campo,
                                  @RequestParam(name = "orden", required = false) String orden){

        List<MonteAPI> montes = ordenar(campo, orden, mendiklopediaService.getMontesTresmiles());

        if (montes == null){
            model.addAttribute("error", "ERROR: Parece ser que el servidor no responde. Intentelo mas tarde. " + LocalTime.now().getHour() + " : " + LocalTime.now().getMinute() + " : " + LocalTime.now().getSecond());
            return "API/opciones";
        }
        model.addAttribute("montes", montes);
        model.addAttribute("url", "tresmiles");
        model.addAttribute("nombreOpcion", "Tresmiles del Pirineo");

        return "API/montes";
    }

    @GetMapping("/mendiklopedia/españa")
    public String getAPIEspaña(Model model,
                               @RequestParam(name = "campo", required = false) String campo,
                               @RequestParam(name = "orden", required = false) String orden){
        List<MonteAPI> montes = ordenar(campo, orden, mendiklopediaService.getMontes());

        if (montes == null){
            model.addAttribute("error", "ERROR: Parece ser que el servidor no responde. Intentelo mas tarde. " + LocalTime.now().getHour() + " : " + LocalTime.now().getMinute() + " : " + LocalTime.now().getSecond());
            return "API/opciones";
        }

        model.addAttribute("montes", montes);
        model.addAttribute("url", "españa");
        model.addAttribute("nombreOpcion", "Montes de España");

        return "API/montes";
    }

    private List<MonteAPI> ordenar(String campo, String orden, List<MonteAPI> montes){
        if (campo != null && orden != null){
            if (campo.equals("nombre") && orden.equals("asc")) montes.sort(Comparator.comparing(MonteAPI::getNombre));
            else if (campo.equals("nombre")) montes.sort(Comparator.comparing(MonteAPI::getNombre).reversed());
            else if (campo.equals("altura") && orden.equals("asc")) montes.sort(Comparator.comparing(MonteAPI::getAltura));
            else montes.sort(Comparator.comparing(MonteAPI::getAltura).reversed());
        }
        return montes;
    }

}
