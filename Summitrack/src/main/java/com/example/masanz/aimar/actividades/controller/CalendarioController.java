package com.example.masanz.aimar.actividades.controller;

import com.example.masanz.aimar.actividades.model.entity.Calendario;
import com.example.masanz.aimar.actividades.model.service.CalendarioService;
import com.example.masanz.aimar.actividades.model.service.MonteService;
import com.example.masanz.aimar.actividades.model.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.*;

@Controller
public class CalendarioController {

    @Autowired
    private MonteService monteService;

    @Autowired
    private PersonaService personaService;

    @Autowired
    private CalendarioService calendarioService;

    @GetMapping("/calendario")
    public String getAll(Model model){
        /*Ordenar por fecha*/
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

    @GetMapping("/calendario/add")
    public String addCalendarioGet(@RequestParam(name ="id", required = false) Integer id,Model model){
        Calendario calendario  = new Calendario();
        if (id != null) {
            calendario = calendarioService.findByID(id);
            model.addAttribute("editar", true);
        }
        model.addAttribute("calendario", calendario);
        model.addAttribute("montes", monteService.getAll());
        return "calendario/add";
    }

    @PostMapping("/calendario/add")
    public String addCalendarioPost(@ModelAttribute Calendario calendario, Model model){
        calendarioService.save(calendario);
        return "redirect:/calendario";
    }

    @GetMapping("/calendario/eliminar")
    public String eliminarCalendario(@RequestParam(name ="id") Integer id, Model model) {
        Calendario calendario = calendarioService.findByID(id);

        calendarioService.delete(calendario);
        return "redirect:/calendario";
    }
}
