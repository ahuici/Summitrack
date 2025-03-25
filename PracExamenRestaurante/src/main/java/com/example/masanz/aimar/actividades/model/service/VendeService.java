package com.example.masanz.aimar.actividades.model.service;

import com.example.masanz.aimar.actividades.model.DAO.IVendeDAO;
import com.example.masanz.aimar.actividades.model.entity.Vende;
import com.example.masanz.aimar.actividades.model.entity.VendeID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendeService {

    @Autowired
    private IVendeDAO vendeDAO;

    public void save(Vende vende){
        vendeDAO.save(vende);
    }

    public List<Vende> getAll(){
        return  vendeDAO.findAll();
    }

    public boolean existe(Vende vende){
        List<Vende> todos = getAll();
        for(Vende vendeTodos : todos) {
            if (vendeTodos.getVendeID().equals(vende.getVendeID())){
                return true;
            }
        }
        return false;
    }

    public Vende findByID(VendeID id){
        return vendeDAO.getReferenceById(id);
    }

    public void delete(Vende vende){
        vendeDAO.delete(vende);
    }
}
