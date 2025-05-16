package com.example.masanz.aimar.actividades.controller;

import com.example.masanz.aimar.actividades.model.entity.CorreoDTO;
import com.example.masanz.aimar.actividades.model.service.CorreoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactoController {

    @Autowired
    private CorreoService correoService;

    @GetMapping("/contacto")
    public String verContacto(Model model) {
        CorreoDTO correoDTO = new CorreoDTO();
        model.addAttribute("correo", correoDTO);

        return "contacto/contacto";
    }

    @PostMapping({"/contacto", "/contacto/"})
    public String enviarCorreo(@ModelAttribute CorreoDTO correoDTO, Model model){
        String respuesta = correoService.enviarCorreo(correoDTO);

        if (respuesta != null){
            model.addAttribute("msg",respuesta);
            model.addAttribute("volver","/contacto");
            return "error";
        }
        return "redirect:/contacto/hecho";
    }

    @GetMapping("/contacto/hecho")
    public String contactoHecho(Model model) {
        return "contacto/hecho";
    }
}
