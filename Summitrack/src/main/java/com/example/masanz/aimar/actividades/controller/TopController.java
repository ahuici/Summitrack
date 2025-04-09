package com.example.masanz.aimar.actividades.controller;

import com.example.masanz.aimar.actividades.model.entity.Calendario;
import com.example.masanz.aimar.actividades.model.service.CalendarioService;
import com.example.masanz.aimar.actividades.model.service.MonteService;
import com.example.masanz.aimar.actividades.model.service.PersonaService;
import com.example.masanz.aimar.actividades.model.service.TopService;
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
public class TopController {

    @Autowired
    private MonteService monteService;

    @Autowired
    private PersonaService personaService;

    @Autowired
    private CalendarioService calendarioService;

    @Autowired
    private TopService topService;

    @GetMapping("/top")
    public String getAll(Model model){
        model.addAttribute("distanciasLocal", topService.getTopDistanciaLocal());
        model.addAttribute("desnivelesLocal", topService.getTopDesnivelLocal());
        model.addAttribute("cimasLocal", topService.getTopCimasLocal());


        model.addAttribute("distanciasLocal", topService.getTopDistanciaGlobal());
        model.addAttribute("desnivelesLocal", topService.getTopDesnivelGlobal());
        model.addAttribute("cimasLocal", topService.getTopCimasGlobal());
        return "top/local";
    }
}
