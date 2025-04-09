package com.example.masanz.aimar.actividades.model.service;

import com.example.masanz.aimar.actividades.model.DAO.ICompletaDAO;
import com.example.masanz.aimar.actividades.model.entity.*;
import com.google.cloud.firestore.DocumentReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class CompletaService {

    @Autowired
    private ICompletaDAO completaDAO;

    @Autowired
    private FirebaseService firebase;

    public void save(Completa completa){
        completaDAO.save(completa);
//        try {
//            //TODO GUARDAR LA DISTANCIA EN FIREBASE
//            DocumentReference docRef = firebase.getFirestore().collection("summitrack").document();
//            docRef.set(new CompletaDTO(completa.getRuta().getId(), completa.getPersona().getId())).get();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        } catch (ExecutionException e) {
//            throw new RuntimeException(e);
//        }
    }

    public List<Completa> getAll(){
        return  completaDAO.findAll();
    }

    public boolean existe(Completa completa){
        List<Completa> todos = getAll();
        for(Completa completaTodos : todos) {
            if (completaTodos.getCompletaID().equals(completa.getCompletaID())){
                return true;
            }
        }
        return false;
    }

    public Completa findByID(CompletaID id){
        return completaDAO.getReferenceById(id);
    }

    public void delete(Completa completa){
        completaDAO.delete(completa);
//        firebase.getFirestore().collection("grupos").document(String.valueOf(completa.get())).delete();
    }
}
