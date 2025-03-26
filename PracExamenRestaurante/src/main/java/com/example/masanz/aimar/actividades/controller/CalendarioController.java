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
        model.addAttribute("calendarios", calendarioService.getAll());
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
