package com.example.masanz.aimar.actividades.model.service;

import com.example.masanz.aimar.actividades.model.DAO.IMonteDAO;
import com.example.masanz.aimar.actividades.model.DAO.IRutaDAO;
import com.example.masanz.aimar.actividades.model.entity.Monte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MonteService {

    @Autowired
    private IMonteDAO monteDAO;

    @Autowired
    private IRutaDAO rutaDAO;

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

}

