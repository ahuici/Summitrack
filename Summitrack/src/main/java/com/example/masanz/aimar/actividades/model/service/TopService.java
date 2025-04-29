package com.example.masanz.aimar.actividades.model.service;

import com.example.masanz.aimar.actividades.model.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TopService {

    @Autowired
    private PersonaService personaService;

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
            if (personasDistancia.keySet().contains(persona)){
                Integer distanciaActual = personasDistancia.get(persona);
                personasDistancia.put(persona, distanciaActual + completa.getAscension().getDistancia());
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

    public List<DesnivelDTO> getTopDesnivelLocal(){
        Map<Persona, Integer> personasDesnivel = getPersonas();
        List<Completa> completas = completaService.getAll();

        for (Completa completa : completas){
            Persona persona = completa.getPersona();
            if (personasDesnivel.keySet().contains(persona)) {
                Integer desnivelActual = personasDesnivel.get(persona);
                personasDesnivel.put(persona, desnivelActual + completa.getAscension().getDesnivel());
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

    public List<CimasDTO> getTopCimasLocal(){
        Map<Persona, Integer> personasCimas = getPersonas();
        List<Completa> completas = completaService.getAll();
        List<Persona> personas = personaService.getAll();

        for (Persona persona : personas){
            if (personasCimas.keySet().contains(persona)){
                Integer cantCimas = 0;
                for (Completa completa : completas){
                    if (completa.getPersona().equals(persona)) cantCimas++;
                }
                personasCimas.put(persona,cantCimas);
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

    /* TOP GLOBAL*/

    public List<DistanciaDTO> getTopDistanciaGlobal(){
        List<AscensionDTO> ascensiones = firebase.getAllAscensiones();
        Map<Persona, Integer> personasDistancia = getPersonas();

        for (AscensionDTO ascensionDTO : ascensiones){
            for (Integer personaID : ascensionDTO.getPersonas()){
                Persona persona = personaService.findByID(personaID);
                if (personasDistancia.keySet().contains(persona)) {
                    Integer distanciaActual = personasDistancia.get(persona);
                    personasDistancia.put(persona, distanciaActual + ascensionDTO.getDistancia());
                }
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
        List<AscensionDTO> ascensiones = firebase.getAllAscensiones();
        Map<Persona, Integer> personasDesnivel = getPersonas();

        for (AscensionDTO ascensionDTO : ascensiones){
            for (Integer personaID : ascensionDTO.getPersonas()){
                Persona persona = personaService.findByID(personaID);
                if (personasDesnivel.keySet().contains(persona)) {
                    Integer desnivelActual = personasDesnivel.get(persona);
                    personasDesnivel.put(persona, desnivelActual + ascensionDTO.getDesnivel());
                }
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
        List<AscensionDTO> ascensiones = firebase.getAllAscensiones();
        Map<Persona, Integer> personasCimas = getPersonas();

        for (AscensionDTO ascensionDTO : ascensiones){
            for (Integer personaID : ascensionDTO.getPersonas()){
                Persona persona = personaService.findByID(personaID);
                if (personasCimas.keySet().contains(persona)) {
                    Integer totalCimas = personasCimas.get(persona);
                    personasCimas.put(persona, totalCimas + 1);
                }
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
            if (persona.isSalirTop()) asociado.put(persona,0);
        }
        return asociado;
    }
}

