package com.example.masanz.aimar.actividades.model.service;

import com.example.masanz.aimar.actividades.model.DAO.IPersonaDAO;
import com.example.masanz.aimar.actividades.model.entity.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PersonaService {

    @Autowired
    private IPersonaDAO personaDAO;

    public List<Persona> getAll(){
        return personaDAO.findAll();
    }

    public Persona findByID(Integer id){
        return personaDAO.findById(id).orElse(null);
    }

    public String save(Persona persona){
        if (persona.getNombre().length() < 2) return "El nombre debe tener al menos 2 letras";
        else if (persona.getNombre().matches("\\d+")) return "El nombre debe estar compuesto por letras";
        else if (persona.getApellidos().length() < 5) return "Los apellidos debe tener al menos 5 letras";
        else if (persona.getApellidos().matches("\\d+")) return "Los apellidos debe estar compuesto por letras";
        else if (persona.getEdad() < 1) return "La edad minima es de 1 año";

        personaDAO.save(persona);
        return null;
    }

    public boolean existe(Persona persona){
        return personaDAO.existsById(persona.getId());
    }

    public void delete(Persona persona){
        personaDAO.delete(persona);
    }

    public List<Persona> IDconverter(List<Integer> personasID) {
        List<Persona> personas = new ArrayList<>();
        for (Persona persona : getAll()){
            if (personasID.contains(persona.getId())) personas.add(persona);
        }
        return personas;
    }
}

