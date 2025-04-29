package com.example.masanz.aimar.actividades.model.service;

import com.example.masanz.aimar.actividades.model.DAO.IAscensionDAO;
import com.example.masanz.aimar.actividades.model.entity.*;
import com.google.cloud.firestore.DocumentReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class AscensionService {

    @Autowired
    private PersonaService personaService;

    @Autowired
    private FirebaseService firebase;

    @Autowired
    private CompletaService completaService;

    @Autowired
    private IAscensionDAO ascensionDAO;

    @Value("${upload.directory}")
    private String uploadDirectory;

    public List<Ascension> getAll(){
        return ascensionDAO.findAll();
    }

    public Ascension findByID(Integer id){
        return ascensionDAO.findById(id).orElse(null);
    }

    public void save(Ascension ascension, List<Integer> personasID, MultipartFile fotoAgregar) throws IOException {
        String filename = fotoAgregar.getOriginalFilename();
        Path filePath = Paths.get(uploadDirectory, filename);

        File dir = new File(uploadDirectory);
        if (!dir.exists()) {dir.mkdirs();}

        File destFile = new File(filePath.toString());
        fotoAgregar.transferTo(destFile);
        ascension.setFoto(filename);

        ascensionDAO.save(ascension);
        for (Integer idPersona : personasID){
            Completa completa = new Completa(ascension, personaService.findByID(Math.toIntExact(idPersona)));
            completaService.save(completa);
        }
        try {
            DocumentReference docRef = firebase.getFirestore().collection("summitrack").document();
            AscensionDTO ascensionDTO = new AscensionDTO(docRef.getId(), ascension.getId(), ascension.getDesnivel(), ascension.getDistancia(), personasID);
            docRef.set(ascensionDTO).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existe(Ascension ascension){
        return ascensionDAO.existsById(ascension.getId());
    }

    public void delete(Ascension ascension){
        ascensionDAO.delete(ascension);

        for (AscensionDTO ascensionDTO : firebase.getAllAscensiones()){
            if (ascensionDTO.getIdMySQL().equals(ascension.getId())) {
                firebase.getFirestore().collection("summitrack").document(String.valueOf(ascensionDTO.getId())).delete();
            }
        }
    }

    public List<Ascension> getAllById(int id){
        List<Ascension> ascensions = new ArrayList<>();
        for (Ascension ascension : getAll()){
            if(ascension.getMonte().getId() == id) ascensions.add(ascension);
        }
        return ascensions;
    }


    public List<Persona> getPersonasAscension(Integer id) {
        Ascension ascension = findByID(id);
        List<Persona> personas = new ArrayList<>();

        for (Completa completa : completaService.getAll()){
            if (completa.getAscension().equals(ascension)) personas.add(completa.getPersona());
        }

        return personas;
    }
}

