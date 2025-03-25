//package com.example.masanz.aimar.actividades.controller;
//
//import com.example.masanz.aimar.actividades.model.entity.Plato;
//import com.example.masanz.aimar.actividades.model.entity.Restaurante;
//import com.example.masanz.aimar.actividades.model.entity.Vende;
//import com.example.masanz.aimar.actividades.model.entity.VendeID;
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
//public class VendeController {
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
//    @GetMapping("/vende")
//    public String getAll(Model model){
//        model.addAttribute("vende", vendeService.getAll());
//        return "vende/all";
//    }
//
//    @GetMapping("/vende/add")
//    public String addVendeGet(@RequestParam(name ="idRestaurante", required = false) Integer idRestaurante, @RequestParam(name ="idPlato", required = false) Integer idPlato, Model model){
//
//        model.addAttribute("idRestaurante",0);
//        model.addAttribute("idPlato",0);
//        Vende vende = new Vende();
//
//        if (idRestaurante != null && idPlato != null) {
//            VendeID vendeID = new VendeID(restauranteService.findByID(idRestaurante).getId(), platoService.findByID(idPlato).getId());
//            vende = vendeService.findByID(vendeID);
//            model.addAttribute("editar", true);
//            model.addAttribute("idRestaurante",idRestaurante);
//            model.addAttribute("idPlato",idPlato);
//        }
//
//        model.addAttribute("vende", vende);
//        model.addAttribute("platos", platoService.getAll());
//        model.addAttribute("restaurantes", restauranteService.getAll());
//        return "vende/add";
//    }
//
//    @PostMapping("/vende/add")
//    public String addVendePost(@ModelAttribute Vende vende, @RequestParam(name = "idRestaurante") Integer idRestaurante, @RequestParam(name = "idPlato") Integer idPlato,  Model model){
//        Plato plato = platoService.findByID(idPlato);
//        Restaurante restaurante = restauranteService.findByID(idRestaurante);
//
//        Vende vendeLleno = new Vende(restaurante,plato);
//        vendeService.save(vendeLleno);
//        return "redirect:/vende";
//    }
//
//    @GetMapping("/vende/eliminar")
//    public String eliminarVende(@RequestParam(name ="idRestaurante", required = false) Integer idRestaurante, @RequestParam(name ="idPlato", required = false) Integer idPlato, Model model) {
//        Vende vendeID = new Vende(restauranteService.findByID(idRestaurante), platoService.findByID(idPlato));
//
//        Vende vende = vendeService.findByID(vendeID.getVendeID());
//        vendeService.delete(vende);
//        return "redirect:/vende";
//    }
//}
