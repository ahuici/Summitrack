package com.example.masanz.aimar.actividades.model.service;

import com.example.masanz.aimar.actividades.model.DAO.ICompletaDAO;
import com.example.masanz.aimar.actividades.model.DAO.IMonteDAO;
import com.example.masanz.aimar.actividades.model.DAO.IPersonaDAO;
import com.example.masanz.aimar.actividades.model.DAO.IRutaDAO;
import com.example.masanz.aimar.actividades.model.entity.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TopService {

    @Autowired
    private IMonteDAO monteDAO;

    @Autowired
    private IPersonaDAO personaDAO;

    @Autowired
    private IRutaDAO rutaDAO;

    @Autowired
    private ICompletaDAO completaDAO;

    public List<Monte> getAll(){
        return monteDAO.findAll();
    }

    public Monte findByID(Integer id){
        return monteDAO.findById(id).orElse(null);
    }

    public void save(Monte monte){ monteDAO.save(monte);}

    public boolean existe(Monte monte){
        return monteDAO.existsById(monte.getId());
    }

    public void delete(Monte monte){
        monteDAO.delete(monte);
    }

    public List<DistanciaDTO> getTopDistancia(){
        Map<Persona, Integer> personasDistancia = getPersonas();
        List<Completa> completas = completaDAO.findAll();

        for (Completa completa : completas){
            Persona persona = completa.getPersona();
            Integer distanciaActual = personasDistancia.get(persona);
            personasDistancia.put(persona, distanciaActual + completa.getRuta().getDistancia());
        }

        List<DistanciaDTO> top = new ArrayList<>();
        for (Map.Entry<Persona, Integer> entry : personasDistancia.entrySet()) {
            Persona persona = entry.getKey();
            Integer distancia = entry.getValue();
            DistanciaDTO distanciaDTO = new DistanciaDTO(persona, distancia);
            top.add(distanciaDTO);
        }
        top.sort(Comparator.comparing(DistanciaDTO::getDistancia).reversed());
        return top;
    }

    public List<DesnivelDTO> getTopDesnivel(){
        Map<Persona, Integer> personasDesnivel = getPersonas();
        List<Completa> completas = completaDAO.findAll();

        for (Completa completa : completas){
            Persona persona = completa.getPersona();
            Integer desnivelActual = personasDesnivel.get(persona);
            personasDesnivel.put(persona, desnivelActual + completa.getRuta().getDesnivel());
        }

        List<DesnivelDTO> top = new ArrayList<>();
        for (Map.Entry<Persona, Integer> entry : personasDesnivel.entrySet()) {
            Persona persona = entry.getKey();
            Integer desnivel = entry.getValue();
            DesnivelDTO desnivelDTO = new DesnivelDTO(persona, desnivel);
            top.add(desnivelDTO);
        }
        top.sort(Comparator.comparing(DesnivelDTO::getDesnivel).reversed());
        return top;
    }

    public List<CimasDTO> getTopCimas(){
        Map<Persona, Integer> personasCimas = getPersonas();
        List<Completa> completas = completaDAO.findAll();
        List<Persona> personas = personaDAO.findAll();

        for (Persona persona : personas){
            Integer cantCimas = 0;
            for (Completa completa : completas){
                if (completa.getPersona().equals(persona)) cantCimas++;
            }
            personasCimas.put(persona,cantCimas);
        }

        List<CimasDTO> top = new ArrayList<>();
        for (Map.Entry<Persona, Integer> entry : personasCimas.entrySet()) {
            Persona persona = entry.getKey();
            Integer cimas = entry.getValue();
            CimasDTO cimasDTO = new CimasDTO(persona, cimas);
            top.add(cimasDTO);
        }
        top.sort(Comparator.comparing(CimasDTO::getCimas).reversed());
        return top;
    }

    public Map<Persona, Integer> getPersonas(){
        List<Persona> personas = personaDAO.findAll();
        Map<Persona, Integer> asociado = new HashMap<>();
        for (Persona persona : personas){
            asociado.put(persona,0);
        }
        return asociado;
    }
}

