package com.example.masanz.aimar.actividades.controller;

import com.example.masanz.aimar.actividades.model.entity.Plato;
import com.example.masanz.aimar.actividades.model.service.RestauranteService;
import com.example.masanz.aimar.actividades.model.service.PlatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PlatoController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private PlatoService platoService;

    @GetMapping("/plato")
    public String getAll(Model model){
        model.addAttribute("platos", platoService.getAll());
        return "plato/all";
    }

    @GetMapping("/plato/add")
    public String addPlatoGet(@RequestParam(name ="id", required = false) Integer id,Model model){
        Plato plato  = new Plato();
        if (id != null) {
            plato = platoService.findByID(id);
            model.addAttribute("editar", true);
        }
        model.addAttribute("plato", plato);
        return "plato/add";
    }

    @PostMapping("/plato/add")
    public String addPlatoPost(@ModelAttribute Plato plato, Model model){
        platoService.save(plato);
        return "redirect:/plato";
    }

    @GetMapping("/plato/eliminar")
    public String eliminarPlato(@RequestParam(name ="id") Integer id, Model model) {
        Plato plato = platoService.findByID(id);

        platoService.delete(plato);
        return "redirect:/plato";
    }
}
