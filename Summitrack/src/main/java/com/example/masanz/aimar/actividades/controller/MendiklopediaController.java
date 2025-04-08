package com.example.masanz.aimar.actividades.controller;

import com.example.masanz.aimar.actividades.model.entity.Calendario;
import com.example.masanz.aimar.actividades.model.entity.Monte;
import com.example.masanz.aimar.actividades.model.service.MendiklopediaService;
import com.example.masanz.aimar.actividades.model.service.MonteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

@Controller
public class MendiklopediaController {

    @Autowired
    private MendiklopediaService mendiklopediaService;

    @Autowired
    private MonteService monteService;


    @GetMapping("/mendiklopedia")
    public String getAPI(Model model){
        return "API/opciones";  // El nombre de la vista donde quieres mostrar los montes
    }

    @GetMapping("/mendiklopedia/navarra")
    public String getAPINavarra(Model model,
                                @RequestParam(name = "campo", required = false) String campo,
                                @RequestParam(name = "orden", required = false) String orden){

        List<Monte> montes = ordenar(campo, orden, mendiklopediaService.getMontesNavarra());

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

        List<Monte> montes = ordenar(campo, orden, mendiklopediaService.getMontesTresmiles());

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
        List<Monte> montes = ordenar(campo, orden, mendiklopediaService.getAll());

        if (montes == null){
            model.addAttribute("error", "ERROR: Parece ser que el servidor no responde. Intentelo mas tarde. " + LocalTime.now().getHour() + " : " + LocalTime.now().getMinute() + " : " + LocalTime.now().getSecond());
            return "API/opciones";
        }

        model.addAttribute("montes", montes);
        model.addAttribute("url", "españa");
        model.addAttribute("nombreOpcion", "Montes de España");

        return "API/montes";
    }

    @GetMapping("/mendiklopedia/favoritos")
    public String getFavoritos(Model model){
        model.addAttribute("montes", monteService.getFavoritos());
        return "API/favoritos";
    }

    @GetMapping("/mendiklopedia/favoritoDelete")
    public String deleteFavorito(Model model,
                              @RequestParam(name = "id", required = false) Integer id){

        Monte monte = monteService.findByID(id);
        monte.setFavorito(Boolean.FALSE);

        monteService.save(monte);
        model.addAttribute("montes", monteService.getAll());
        return "redirect:/mendiklopedia/favoritos";
    }


    private List<Monte> ordenar(String campo, String orden, List<Monte> montes){
        if (campo != null && orden != null){
            if (campo.equals("nombre") && orden.equals("asc")) montes.sort(Comparator.comparing(Monte::getNombre));
            else if (campo.equals("nombre")) montes.sort(Comparator.comparing(Monte::getNombre).reversed());
            else if (campo.equals("altura") && orden.equals("asc")) montes.sort(Comparator.comparing(Monte::getAltura));
            else montes.sort(Comparator.comparing(Monte::getAltura).reversed());
        }
        return montes;
    }

}
