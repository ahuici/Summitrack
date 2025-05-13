package com.example.masanz.aimar.actividades.controller;

import com.example.masanz.aimar.actividades.model.service.CalendarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CalendarioController {

    @Autowired
    private CalendarioService calendarioService;

    @GetMapping("/calendario/eliminar")
    public String eliminarCalendario(@RequestParam(name ="id") Integer id, Model model) {
        calendarioService.delete(calendarioService.findByID(id));

        return "redirect:/ver/calendario";
    }
}
