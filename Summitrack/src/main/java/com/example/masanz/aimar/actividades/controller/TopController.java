package com.example.masanz.aimar.actividades.controller;

import com.example.masanz.aimar.actividades.model.service.TopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TopController {

    @Autowired
    private TopService topService;

    @GetMapping("/top")
    public String getAll(Model model){
        model.addAttribute("distanciasLocal", topService.getTopDistanciaLocal());
        model.addAttribute("desnivelesLocal", topService.getTopDesnivelLocal());
        model.addAttribute("cimasLocal", topService.getTopCimasLocal());


        model.addAttribute("distanciasGlobal", topService.getTopDistanciaGlobal());
        model.addAttribute("desnivelesGlobal", topService.getTopDesnivelGlobal());
        model.addAttribute("cimasGlobal", topService.getTopCimasGlobal());
        return "top/top";
    }
}
