package com.example.masanz.aimar.actividades.model.service;

import com.example.masanz.aimar.actividades.model.DAO.ICompletaDAO;
import com.example.masanz.aimar.actividades.model.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompletaService {

    @Autowired
    private ICompletaDAO completaDAO;

    public void save(Completa completa){
        completaDAO.save(completa);
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
    }
}
