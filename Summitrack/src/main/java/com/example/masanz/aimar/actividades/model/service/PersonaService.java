package com.example.masanz.aimar.actividades.model.service;

import com.example.masanz.aimar.actividades.model.DAO.IMonteDAO;
import com.example.masanz.aimar.actividades.model.DAO.IPersonaDAO;
import com.example.masanz.aimar.actividades.model.DAO.IRutaDAO;
import com.example.masanz.aimar.actividades.model.entity.Monte;
import com.example.masanz.aimar.actividades.model.entity.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonaService {

    @Autowired
    private IPersonaDAO personaDAO;

    @Autowired
    private IRutaDAO rutaDAO;

    public List<Persona> getAll(){
        return personaDAO.findAll();
    }

    public Persona findByID(Integer id){
        return personaDAO.findById(id).orElse(null);
    }

    public void save(Persona persona){ personaDAO.save(persona);}

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

