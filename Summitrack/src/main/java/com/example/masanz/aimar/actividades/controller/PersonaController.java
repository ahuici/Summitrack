package com.example.masanz.aimar.actividades.controller;

import com.example.masanz.aimar.actividades.model.entity.Persona;
import com.example.masanz.aimar.actividades.model.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @GetMapping("/personas/eliminar")
    public String eliminarPersona(@RequestParam(name ="id") Integer id, Model model) {
        Persona persona = personaService.findByID(id);

        personaService.delete(persona);
        return "redirect:/ver/persona";
    }
}
