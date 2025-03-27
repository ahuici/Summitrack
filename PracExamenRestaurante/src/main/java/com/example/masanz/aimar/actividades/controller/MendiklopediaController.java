package com.example.masanz.aimar.actividades.controller;

import com.example.masanz.aimar.actividades.model.entity.Monte;
import com.example.masanz.aimar.actividades.model.entity.MonteAPI;
import com.example.masanz.aimar.actividades.model.service.MendiklopediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MendiklopediaController {

    @Autowired
    private MendiklopediaService mendiklopediaService;

    @GetMapping("/mendiklopedia")
    public String getAPI(Model model){
        // Llamamos al servicio para obtener la lista de montes desde la API
        List<MonteAPI> montes = mendiklopediaService.getMontes();

        // Añadimos los montes al modelo
        model.addAttribute("montes", montes);

        // Retornamos la vista (puede ser un archivo .html o .jsp)
        return "API/montes";  // El nombre de la vista donde quieres mostrar los montes
    }

}
