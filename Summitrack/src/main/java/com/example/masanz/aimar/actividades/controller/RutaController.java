package com.example.masanz.aimar.actividades.controller;

import com.example.masanz.aimar.actividades.model.entity.Ruta;
import com.example.masanz.aimar.actividades.model.service.CompletaService;
import com.example.masanz.aimar.actividades.model.service.MonteService;
import com.example.masanz.aimar.actividades.model.service.PersonaService;
import com.example.masanz.aimar.actividades.model.service.RutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @Value("${upload.directory}")
    private String uploadDirectory;

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
        model.addAttribute("personas", personaService.getAll());
        return "ruta/add";
    }

    @PostMapping("/ruta/add")
    public String addRutaPost(@ModelAttribute Ruta ruta,
                              @RequestPart(name = "fotoAgregar", required = false) MultipartFile fotoAgregar) {
        System.out.println("Entrando en el metodo");
        try {
            if (fotoAgregar != null && !fotoAgregar.isEmpty()) {
                String filename = fotoAgregar.getOriginalFilename();
                Path filePath = Paths.get(uploadDirectory, filename);

                File dir = new File(uploadDirectory);
                if (!dir.exists()) {dir.mkdirs();}

                File destFile = new File(filePath.toString());
                fotoAgregar.transferTo(destFile); // Guarda el archivo en el disco

                ruta.setFoto(filename);
                rutaService.save(ruta);  // Guarda la ruta con la foto
                return "redirect:/ruta";  // Redirige a la lista de rutas
            } else {throw new IllegalArgumentException("No se ha cargado una foto válida.");}
        } catch (IOException e) {
            e.printStackTrace();
            return "error";  // Redirige a una página de error si hay un fallo
        }
    }

    @GetMapping("/ruta/eliminar")
    public String eliminarRuta(@RequestParam(name ="id") Integer id, Model model) {
        Ruta ruta = rutaService.findByID(id);
        rutaService.delete(ruta);
        return "redirect:/ruta";
    }


    @GetMapping("/ruta/verMas")
    public String verMasRuta(@RequestParam(name ="id") Integer id, Model model) {
        Ruta ruta = rutaService.findByID(id);
        model.addAttribute("ruta", ruta);
        return "ruta/verMas";
    }

}
