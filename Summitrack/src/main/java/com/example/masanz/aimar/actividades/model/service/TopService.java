package com.example.masanz.aimar.actividades.model.service;

import com.example.masanz.aimar.actividades.model.DAO.ICompletaDAO;
import com.example.masanz.aimar.actividades.model.DAO.IMonteDAO;
import com.example.masanz.aimar.actividades.model.DAO.IPersonaDAO;
import com.example.masanz.aimar.actividades.model.DAO.IRutaDAO;
import com.example.masanz.aimar.actividades.model.entity.*;
import com.google.cloud.firestore.DocumentReference;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.aspectj.weaver.patterns.PerObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TopService {

    @Autowired
    private IMonteDAO monteDAO;

    @Autowired
    private PersonaService personaService;

    @Autowired
    private RutaService rutaService;

    @Autowired
    private CompletaService completaService;

    @Autowired
    private FirebaseService firebase;

    /* TOP LOCAL*/

    public List<DistanciaDTO> getTopDistanciaLocal(){
        Map<Persona, Integer> personasDistancia = getPersonas();
        List<Completa> completas = completaService.getAll();

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

    public List<DesnivelDTO> getTopDesnivelLocal(){
        Map<Persona, Integer> personasDesnivel = getPersonas();
        List<Completa> completas = completaService.getAll();

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

    public List<CimasDTO> getTopCimasLocal(){
        Map<Persona, Integer> personasCimas = getPersonas();
        List<Completa> completas = completaService.getAll();
        List<Persona> personas = personaService.getAll();

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

    /* TOP GLOBAL*/

    public List<DistanciaDTO> getTopDistanciaGlobal(){
        List<RutaDTO> rutas = firebase.getAllRuta();
        Map<Persona, Integer> personasDistancia = getPersonas();

        for (RutaDTO rutaDTO : rutas){
            for (Integer personaID : rutaDTO.getPersonas()){
                Persona persona = personaService.findByID(personaID);
                Integer distanciaActual = personasDistancia.get(persona);
                personasDistancia.put(persona, distanciaActual + rutaDTO.getDistancia());
            }
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

    public List<DesnivelDTO> getTopDesnivelGlobal(){
        List<RutaDTO> rutas = firebase.getAllRuta();
        Map<Persona, Integer> personasDesnivel = getPersonas();

        for (RutaDTO rutaDTO : rutas){
            for (Integer personaID : rutaDTO.getPersonas()){
                Persona persona = personaService.findByID(personaID);
                Integer desnivelActual = personasDesnivel.get(persona);
                personasDesnivel.put(persona, desnivelActual + rutaDTO.getDesnivel());
            }
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

    public List<CimasDTO> getTopCimasGlobal(){
        List<RutaDTO> rutas = firebase.getAllRuta();
        Map<Persona, Integer> personasCimas = getPersonas();

        for (RutaDTO rutaDTO : rutas){
            for (Integer personaID : rutaDTO.getPersonas()){
                Persona persona = personaService.findByID(personaID);
                Integer totalCimas = personasCimas.get(persona);
                personasCimas.put(persona, totalCimas + 1);
            }
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
        List<Persona> personas = personaService.getAll();
        Map<Persona, Integer> asociado = new HashMap<>();
        for (Persona persona : personas){
            asociado.put(persona,0);
        }
        return asociado;
    }
}

