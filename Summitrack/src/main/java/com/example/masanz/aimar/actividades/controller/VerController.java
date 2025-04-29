package com.example.masanz.aimar.actividades.controller;

import com.example.masanz.aimar.actividades.model.entity.Calendario;
import com.example.masanz.aimar.actividades.model.service.CalendarioService;
import com.example.masanz.aimar.actividades.model.service.MonteService;
import com.example.masanz.aimar.actividades.model.service.PersonaService;
import com.example.masanz.aimar.actividades.model.service.AscensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/ver")
public class VerController {

    @Autowired
    private MonteService monteService;

    @Autowired
    private AscensionService ascensionService;

    @Autowired
    private PersonaService personaService;

    @Autowired
    private CalendarioService calendarioService;

    /* OPCIONES*/
    @GetMapping({"","/"})
    public String getOpciones(Model model){
        model.addAttribute("opcion","ver");
        model.addAttribute("titulo","Ver CRUD");

        return "utils/opciones";
    }


    @GetMapping({"/monte","/monte/"})
    public String verMonte(Model model){
        model.addAttribute("montes", monteService.getAll());

        return "monte/all";
    }

    @GetMapping({"/ascension","/ascension/"})
    public String verAscension(Model model){
        model.addAttribute("ascensiones", ascensionService.getAll());

        return "ascension/all";
    }

    @GetMapping({"/persona","/persona/"})
    public String verPersona(Model model){
        model.addAttribute("personas",personaService.getAll());

        return "persona/all";
    }

    @GetMapping({"/calendario","/calendario/"})
    public String verCalendario(Model model){
        Map<String, List<Calendario>> calendariosPorMes = calendarioService.getCalendariosAgrupadosPorMes();
        model.addAttribute("calendariosPorMes", calendariosPorMes);

        return "calendario/all";
    }
}
