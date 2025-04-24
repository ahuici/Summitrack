package com.example.masanz.aimar.actividades.controller;

import com.example.masanz.aimar.actividades.model.entity.Calendario;
import com.example.masanz.aimar.actividades.model.service.CalendarioService;
import com.example.masanz.aimar.actividades.model.service.MonteService;
import com.example.masanz.aimar.actividades.model.service.PersonaService;
import com.example.masanz.aimar.actividades.model.service.RutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/ver")
public class VerController {

    @Autowired
    private MonteService monteService;

    @Autowired
    private RutaService rutaService;

    @Autowired
    private PersonaService personaService;

    @Autowired
    private CalendarioService calendarioService;

    /* OPCIONES*/
    @GetMapping({"","/"})
    public String getOpciones(Model model){
        model.addAttribute("opcion","ver");
        model.addAttribute("titulo","Ver");
        return "utils/opciones";
    }


    @GetMapping({"/monte","/monte/"})
    public String verMonte(Model model){
        model.addAttribute("montes", monteService.getAll());
        return "monte/all";
    }

    @GetMapping({"/ruta","/ruta/"})
    public String verRuta(Model model){
        model.addAttribute("rutas", rutaService.getAll());
        return "ruta/all";
    }

    @GetMapping({"/persona","/persona/"})
    public String verPersona(Model model){
        model.addAttribute("personas",personaService.getAll());
        return "persona/all";
    }

    @GetMapping({"/calendario","/calendario/"})
    public String verCalendario(Model model){
        List<Calendario> calendarios = calendarioService.getAll();
        calendarios.sort(Comparator.comparing(Calendario::getFecha));

        /* Agrupar por mes */
        Map<String, List<Calendario>> calendariosPorMes = calendarios.stream()
                .collect(Collectors.groupingBy(
                        c -> c.getFecha().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + c.getFecha().getYear(), // Mes y año juntos
                        LinkedHashMap::new, // Mantiene el orden de inserción
                        Collectors.toList()
                ));

        model.addAttribute("calendariosPorMes", calendariosPorMes);
        return "calendario/all";
    }
}
