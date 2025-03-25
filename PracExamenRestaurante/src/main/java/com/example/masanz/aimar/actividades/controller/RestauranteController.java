//package com.example.masanz.aimar.actividades.controller;
//
//import com.example.masanz.aimar.actividades.model.entity.Restaurante;
//import com.example.masanz.aimar.actividades.model.service.RestauranteService;
//import com.example.masanz.aimar.actividades.model.service.VendeService;
//import com.example.masanz.aimar.actividades.model.service.PlatoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//public class RestauranteController {
//
//    @Autowired
//    private RestauranteService restauranteService;
//
//    @Autowired
//    private PlatoService platoService;
//
//    @Autowired
//    private VendeService vendeService;
//
//    @GetMapping("/restaurante")
//    public String getAll(Model model){
//        model.addAttribute("restaurantes", restauranteService.getAll());
//        return "restaurante/all";
//    }
//
//    @GetMapping("/restaurante/add")
//    public String addRestauranteGet(@RequestParam(name ="id", required = false) Integer id,Model model){
//        Restaurante restaurante = new Restaurante();
//        if (id != null) {
//            restaurante = restauranteService.findByID(id);
//            model.addAttribute("editar", true);
//        }
//        model.addAttribute("restaurante", restaurante);
//        return "restaurante/add";
//    }
//
//    @PostMapping("/restaurante/add")
//    public String addRestaurantePost(@ModelAttribute Restaurante restaurante, Model model){
//        restauranteService.save(restaurante);
//        return "redirect:/restaurante";
//    }
//
//    @GetMapping("/restaurante/eliminar")
//    public String eliminarRestaurante(@RequestParam(name ="id") Integer id, Model model) {
//        Restaurante restaurante = restauranteService.findByID(id);
//        restauranteService.delete(restaurante);
//        return "redirect:/restaurante";
//    }
//
//}
