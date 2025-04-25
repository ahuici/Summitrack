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

    @GetMapping("/calendario/eliminar")
    public String eliminarCalendario(@RequestParam(name ="id") Integer id, Model model) {
        Calendario calendario = calendarioService.findByID(id);

        calendarioService.delete(calendario);
        return "redirect:/ver/calendario";
    }
}
