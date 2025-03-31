package com.example.masanz.aimar.actividades.controller;

import com.example.masanz.aimar.actividades.model.entity.Persona;
import com.example.masanz.aimar.actividades.model.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @GetMapping("/personas")
    public String getAll(Model model){
        model.addAttribute("personas",personaService.getAll());

        return "persona/all";
    }

    @GetMapping("/personas/add")
    public String addPersonaGet(@RequestParam(name ="id", required = false) Integer id, Model model){
        Persona persona  = new Persona();
        if (id != null) {
            persona = personaService.findByID(id);
            model.addAttribute("editar", true);
        }
        model.addAttribute("persona", persona);
        return "persona/add";
    }

    @PostMapping("/personas/add")
    public String addPersonaPost(@ModelAttribute Persona persona, Model model){
        personaService.save(persona);
        return "redirect:/personas";
    }

    @GetMapping("/personas/eliminar")
    public String eliminarPersona(@RequestParam(name ="id") Integer id, Model model) {
        Persona persona = personaService.findByID(id);

        personaService.delete(persona);
        return "redirect:/personas";
    }
}
