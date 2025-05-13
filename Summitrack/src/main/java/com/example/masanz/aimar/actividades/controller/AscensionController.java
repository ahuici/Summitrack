package com.example.masanz.aimar.actividades.controller;

import com.example.masanz.aimar.actividades.model.entity.Ascension;
import com.example.masanz.aimar.actividades.model.service.AscensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AscensionController {

    @Autowired
    private AscensionService ascensionService;

    @GetMapping("/ascension/eliminar")
    public String eliminarAscension(@RequestParam(name ="id") Integer id, Model model) {
        ascensionService.delete(ascensionService.findByID(id));

        return "redirect:/ver/ascension";
    }


    @GetMapping("/ascension/individual")
    public String individualAscension(@RequestParam(name ="id") Integer id, Model model) {
        model.addAttribute("ascension", ascensionService.findByID(id));
        model.addAttribute("personas", ascensionService.getPersonasAscension(id));

        return "ascension/individual";
    }

}
