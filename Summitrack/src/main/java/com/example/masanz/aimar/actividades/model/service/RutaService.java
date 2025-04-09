package com.example.masanz.aimar.actividades.model.service;

import com.example.masanz.aimar.actividades.model.DAO.IMonteDAO;
import com.example.masanz.aimar.actividades.model.DAO.IPersonaDAO;
import com.example.masanz.aimar.actividades.model.DAO.IRutaDAO;
import com.example.masanz.aimar.actividades.model.entity.*;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import org.apache.tomcat.util.net.jsse.JSSEUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class RutaService {

    @Autowired
    private PersonaService personaService;

    @Autowired
    private FirebaseService firebase;

    @Autowired
    private CompletaService completaService;

    @Autowired
    private IRutaDAO rutaDAO;

    public List<Ruta> getAll(){
        return rutaDAO.findAll();
    }

    public Ruta findByID(Integer id){
        return rutaDAO.findById(id).orElse(null);
    }

    public void save(Ruta ruta, List<Integer> personasID){
        rutaDAO.save(ruta);
        for (Integer idPersona : personasID){
            Completa completa = new Completa(ruta, personaService.findByID(Math.toIntExact(idPersona)));
            completaService.save(completa);
        }
        try {
            //TODO GUARDAR LA DISTANCIA EN FIREBASE
            DocumentReference docRef = firebase.getFirestore().collection("summitrack").document();
            RutaDTO rutaDTO = new RutaDTO(docRef.getId(),ruta.getId(),ruta.getDesnivel(), ruta.getDistancia(), personasID);
            docRef.set(rutaDTO).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existe(Ruta ruta){
        return rutaDAO.existsById(ruta.getId());
    }

    public void delete(Ruta ruta){
        rutaDAO.delete(ruta);

        for (RutaDTO rutaDTO : firebase.getAllRuta()){
            if (rutaDTO.getIdMySQL().equals(ruta.getId())) {
                firebase.getFirestore().collection("summitrack").document(String.valueOf(rutaDTO.getId())).delete();
            }
        }
    }

    public List<Ruta> getAllById(int id){
        List<Ruta> rutas = new ArrayList<>();
        for (Ruta ruta : getAll()){
            if(ruta.getMonte().getId() == id) rutas.add(ruta);
        }
        return rutas;
    }


    public List<Persona> getPersonasRuta(Integer id) {
        Ruta ruta = findByID(id);
        List<Persona> personas = new ArrayList<>();

        for (Completa completa : completaService.getAll()){
            if (completa.getRuta().equals(ruta)) personas.add(completa.getPersona());
        }

        return personas;
    }
}

