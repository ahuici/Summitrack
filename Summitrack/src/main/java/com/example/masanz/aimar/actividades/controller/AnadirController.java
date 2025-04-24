package com.example.masanz.aimar.actividades.controller;

import com.example.masanz.aimar.actividades.model.entity.Calendario;
import com.example.masanz.aimar.actividades.model.entity.Monte;
import com.example.masanz.aimar.actividades.model.entity.Persona;
import com.example.masanz.aimar.actividades.model.entity.Ruta;
import com.example.masanz.aimar.actividades.model.service.CalendarioService;
import com.example.masanz.aimar.actividades.model.service.MonteService;
import com.example.masanz.aimar.actividades.model.service.PersonaService;
import com.example.masanz.aimar.actividades.model.service.RutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/anadir")
public class AnadirController {
    @Autowired
    private MonteService monteService;

    @Autowired
    private RutaService rutaService;

    @Autowired
    private PersonaService personaService;

    @Autowired
    private CalendarioService calendarioService;

    @Value("${upload.directory}")
    private String uploadDirectory;

    /* OPCIONES*/
    @GetMapping({"","/"})
    public String getOpciones(Model model){
        model.addAttribute("opcion","anadir");
        model.addAttribute("titulo","Añadir");
        return "utils/opciones";
    }

    /* AÑADIR MONTE*/
    @GetMapping({"/monte","/monte/"})
    public String anadirMonteGet(@RequestParam(name ="id", required = false) Integer id, Model model){
        Monte monte  = new Monte();
        if (id != null) {
            monte = monteService.findByID(id);
            model.addAttribute("editar", true);
        }
        model.addAttribute("monte", monte);
        return "monte/add";
    }

    @PostMapping({"/monte","/monte/"})
    public String anadirMontePost(@ModelAttribute Monte monte, Model model){
        monteService.save(monte);
        return "redirect:/monte";
    }

    /*AÑADIR RUTA*/
    @GetMapping({"/ruta","/ruta/"})
    public String anadirRutaGet(@RequestParam(name ="id", required = false) Integer id,Model model){
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

    @PostMapping({"/ruta","/ruta/"})
    public String anadirRutaPost(@ModelAttribute Ruta ruta,
                              @RequestPart(name = "fotoAgregar", required = false) MultipartFile fotoAgregar,
                              @RequestParam("personas") List<Integer> personas) {
        try {
            if (fotoAgregar != null && !fotoAgregar.isEmpty()) {
                String filename = fotoAgregar.getOriginalFilename();
                Path filePath = Paths.get(uploadDirectory, filename);

                File dir = new File(uploadDirectory);
                if (!dir.exists()) {dir.mkdirs();}

                File destFile = new File(filePath.toString());
                fotoAgregar.transferTo(destFile); // Guarda el archivo en el disco
                ruta.setFoto(filename);


                rutaService.save(ruta, personas);
                return "redirect:/ruta";  // Redirige a la lista de rutas
            } else {
                System.out.println("IllegalArgumentException: No se ha cargado una foto válida.");
            }
        } catch (IOException e) {
            System.out.println("ERROR AnadirController (anadirRutaPost): " + e.getMessage());
            return "error";
        }
        return "";
    }

    /* AÑADIR PERSONAS*/
    @GetMapping({"/persona","/persona/"})
    public String anadirPersonaGet(@RequestParam(name ="id", required = false) Integer id, Model model){
        Persona persona  = new Persona();
        if (id != null) {
            persona = personaService.findByID(id);
            model.addAttribute("editar", true);
        }
        model.addAttribute("persona", persona);
        return "persona/add";
    }

    @PostMapping({"/persona", "/persona/"})
    public String anadirPersonaPost(@ModelAttribute Persona persona, Model model){
        System.out.println("Genero: " + persona.getGenero());
        personaService.save(persona);
        return "redirect:/personas";
    }

    /* ANADIR CALENDARIO*/
    @GetMapping({"/calendario", "/calendario/"})
    public String anadirCalendarioGet(@RequestParam(name ="id", required = false) Integer id,Model model){
        Calendario calendario  = new Calendario();
        if (id != null) {
            calendario = calendarioService.findByID(id);
            model.addAttribute("editar", true);
        }
        model.addAttribute("calendario", calendario);
        model.addAttribute("montes", monteService.getAll());
        return "calendario/add";
    }

    @PostMapping({"/calendario", "/calendario/"})
    public String anadirCalendarioPost(@ModelAttribute Calendario calendario, Model model){
        calendarioService.save(calendario);
        return "redirect:/calendario";
    }

}
