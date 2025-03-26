package com.example.masanz.aimar.actividades.controller;

import com.example.masanz.aimar.actividades.model.entity.Ruta;
import com.example.masanz.aimar.actividades.model.service.CompletaService;
import com.example.masanz.aimar.actividades.model.service.MonteService;
import com.example.masanz.aimar.actividades.model.service.PersonaService;
import com.example.masanz.aimar.actividades.model.service.RutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
public class RutaController {

    @Autowired
    private RutaService rutaService;

    @Autowired
    private MonteService monteService;

    @Autowired
    private CompletaService completaService;

    @Autowired
    private PersonaService personaService;

    @GetMapping("/ruta")
    public String getAll(Model model){
        model.addAttribute("rutas", rutaService.getAll());
        return "ruta/all";
    }

    @GetMapping("/ruta/add")
    public String addRutaGet(@RequestParam(name ="id", required = false) Integer id,Model model){
        Ruta ruta = new Ruta();
        if (id != null) {
            ruta = rutaService.findByID(id);
            model.addAttribute("editar", true);
        }
        model.addAttribute("ruta", ruta);
        model.addAttribute("montes", monteService.getAll());
        return "ruta/add";
    }

    @PostMapping("/ruta/add")
    public String addRutaPost(@ModelAttribute Ruta ruta) {
        rutaService.save(ruta);
        return "redirect:/ruta";
    }

    @GetMapping("/ruta/eliminar")
    public String eliminarRuta(@RequestParam(name ="id") Integer id, Model model) {
        Ruta ruta = rutaService.findByID(id);
        rutaService.delete(ruta);
        return "redirect:/ruta";
    }



//    @GetMapping("/ruta/foto")
//    public ResponseEntity<byte[]> getFoto(@RequestParam("id") Integer id) {
//        Ruta ruta = rutaService.findByID(id);
//
//        if (ruta == null || ruta.getFoto() == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.IMAGE_JPEG)
//                .body(ruta.getFoto());
//    }


    @GetMapping("/ruta/verMas")
    public String verMasRuta(@RequestParam(name ="id") Integer id, Model model) {
        Ruta ruta = rutaService.findByID(id);
        model.addAttribute("ruta", ruta);
        return "ruta/verMas";
    }

}
