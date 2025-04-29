package com.example.masanz.aimar.actividades.controller;

import com.example.masanz.aimar.actividades.model.entity.Ascension;
import com.example.masanz.aimar.actividades.model.service.CompletaService;
import com.example.masanz.aimar.actividades.model.service.MonteService;
import com.example.masanz.aimar.actividades.model.service.PersonaService;
import com.example.masanz.aimar.actividades.model.service.AscensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AscensionController {

    @Autowired
    private AscensionService ascensionService;

    @Autowired
    private MonteService monteService;

    @Autowired
    private CompletaService completaService;

    @Autowired
    private PersonaService personaService;

    @Value("${upload.directory}")
    private String uploadDirectory;

    @GetMapping("/ascension/eliminar")
    public String eliminarAscension(@RequestParam(name ="id") Integer id, Model model) {
        Ascension ascension = ascensionService.findByID(id);
        ascensionService.delete(ascension);
        return "redirect:/ver/ascension";
    }


    @GetMapping("/ascension/verMas")
    public String verMasAscension(@RequestParam(name ="id") Integer id, Model model) {
        Ascension ascension = ascensionService.findByID(id);
        model.addAttribute("ascension", ascension);
        return "ascension/verMas";
    }

    @GetMapping("/uploads/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path directory = Paths.get(uploadDirectory);
            Path file = directory.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().body(resource);
            } else {
                throw new FileNotFoundException("No se pudo encontrar el archivo: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error al recuperar el archivo", e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
