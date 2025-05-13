package com.example.masanz.aimar.actividades.model.service;

import com.example.masanz.aimar.actividades.model.DAO.IAscensionDAO;
import com.example.masanz.aimar.actividades.model.entity.*;
import com.google.cloud.firestore.DocumentReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class AscensionService {

    @Autowired
    @Lazy
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

    public String save(Ascension ascension, List<Integer> personasID, MultipartFile fotoAgregar) throws IOException {
        String filename = fotoAgregar.getOriginalFilename();
        Path filePath = Paths.get(uploadDirectory, filename);

        File dir = new File(uploadDirectory);
        if (!dir.exists()) {dir.mkdirs();}

        File destFile = new File(filePath.toString());
        fotoAgregar.transferTo(destFile);
        ascension.setFoto(filename);

        if (ascension.getDistancia() < 2) return "La distancia debe ser superior a 2km";
        else if (ascension.getDesnivel() < 0) return "El desnivel debe ser superior a 0m";
        else if (ascension.getFecha().isAfter(LocalDate.now())) return "La fecha debe ser anterior al dia de hoy";
        else if (ascension.getTiempo() < 0) return "El tiempo debe ser un numero positivo";
        else if (ascension.getTipo().length() < 5) return "El tipo debe tener al menos 5 caracteres";
        else if (ascension.getTipo().matches("\\d+")) return "El tipo debe estar compuesto por letras";
        else if (ascension.getDificultad() < 0 || ascension.getDificultad() > 10) return "La dificultad debe ser superior/igual a 0 y menor/igual de 10";
        else if (ascension.getPeligrosidad() < 0 || ascension.getPeligrosidad() > 10) return "La peligrosidad debe ser superior/igual a 0 y menor/igual de 10";
        else if (ascension.getFoto() == null) return "La imagen no puede estar vacia";
        else if (ascension.getMonte() == null) return "El monte no puede estar vacio";
        else if (personasID.isEmpty()) return "Debe haber al menos una persona que haya participado en la ascension";

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
        return null;
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

    /*
    * Devuelve una lista con las personas que han participado en el ascenso dado su ID
     */
    public List<Persona> getPersonasAscension(Integer id) {
        Ascension ascension = findByID(id);
        List<Persona> personas = new ArrayList<>();

        for (Completa completa : completaService.getAll()){
            if (completa.getAscension().equals(ascension)) personas.add(completa.getPersona());
        }

        return personas;
    }

    /*
    * Devuelve una lista con todas las ascensiones realizadas de una persona dado su ID
     */
    public List<Ascension> getAscensionesPersona(Persona persona){
        List<Ascension> ascensiones = new ArrayList<>();
        for (Ascension ascension : getAll()){
            for (Completa completa : ascension.getCompleta()){
                if (completa.getPersona().equals(persona)) ascensiones.add(ascension);
            }
        }
        return ascensiones;
    }
}

