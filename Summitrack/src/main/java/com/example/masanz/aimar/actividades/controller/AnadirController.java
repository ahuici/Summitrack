package com.example.masanz.aimar.actividades.controller;

import com.example.masanz.aimar.actividades.model.entity.Ascension;
import com.example.masanz.aimar.actividades.model.entity.Calendario;
import com.example.masanz.aimar.actividades.model.entity.Monte;
import com.example.masanz.aimar.actividades.model.entity.Persona;
import com.example.masanz.aimar.actividades.model.service.CalendarioService;
import com.example.masanz.aimar.actividades.model.service.MonteService;
import com.example.masanz.aimar.actividades.model.service.PersonaService;
import com.example.masanz.aimar.actividades.model.service.AscensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/anadir")
public class AnadirController {
    @Autowired
    private MonteService monteService;

    @Autowired
    private AscensionService ascensionService;

    @Autowired
    private PersonaService personaService;

    @Autowired
    private CalendarioService calendarioService;

    /* OPCIONES*/
    @GetMapping({"","/"})
    public String getOpciones(Model model){
        model.addAttribute("opcion","anadir");
        model.addAttribute("titulo","Añadir CRUD");

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
        String respuesta = monteService.save(monte);
        if (respuesta != null){
            model.addAttribute("msg",respuesta);
            if (monte.getId() != null) model.addAttribute("volver","/anadir/monte?id?=" + monte.getId());
            else model.addAttribute("volver","/anadir/monte?");

            return "error";
        }
        return "redirect:/ver/monte";
    }

    /*AÑADIR ASCENSION*/
    @GetMapping({"/ascension","/ascension/"})
    public String anadirAscensionGet(@RequestParam(name ="id", required = false) Integer id, Model model){
        Ascension ascension = new Ascension();
        if (id != null) {
            ascension = ascensionService.findByID(id);
            model.addAttribute("editar", true);
        }
        model.addAttribute("ascension", ascension);
        model.addAttribute("montes", monteService.getAll());
        model.addAttribute("personas", personaService.getAll());

        return "ascension/add";
    }

    @PostMapping({"/ascension","/ascension/"})
    public String anadirAscensionPost(@ModelAttribute Ascension ascension,
                                      @RequestPart(name = "fotoAgregar", required = false) MultipartFile fotoAgregar,
                                      @RequestParam("personas") List<Integer> personas,
                                      Model model)
    {
        try {
            if (fotoAgregar != null && !fotoAgregar.isEmpty()) {
                String respuesta = ascensionService.save(ascension, personas, fotoAgregar);
                if (respuesta != null){
                    model.addAttribute("msg",respuesta);
                    if (ascension.getId() != null) model.addAttribute("volver","/anadir/ascension?id=" + ascension.getId());
                    else model.addAttribute("volver","/anadir/ascension");

                    return "error";
                }
                return "redirect:/ver/ascension";
            } else System.out.println("IllegalArgumentException: No se ha cargado una foto válida.");

        } catch (IOException e) {
            System.out.println("ERROR AnadirController (anadirAscensionPost): " + e.getMessage());
            return "error";
        }

        return "redirect:/ver/ascension";
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
        String respuesta = personaService.save(persona);
        if (respuesta != null){
            model.addAttribute("msg",respuesta);
            if (persona.getId() != null) model.addAttribute("volver","/anadir/persona?id=" + persona.getId());
            else model.addAttribute("volver","/anadir/persona");

            return "error";
        }

        return "redirect:/ver/persona";
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
        String respuesta = calendarioService.save(calendario);
        if (respuesta != null){
            model.addAttribute("msg",respuesta);
            if (calendario.getId() != null) model.addAttribute("volver","/anadir/calendario?id=" + calendario.getId());
            else model.addAttribute("volver","/anadir/calendario");

            return "error";
        }

        return "redirect:/ver/calendario";
    }

}
