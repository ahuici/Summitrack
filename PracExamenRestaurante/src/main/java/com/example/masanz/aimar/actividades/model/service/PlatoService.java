package com.example.masanz.aimar.actividades.model.service;

import com.example.masanz.aimar.actividades.model.DAO.IRestauranteDAO;
import com.example.masanz.aimar.actividades.model.DAO.IPlatoDAO;
import com.example.masanz.aimar.actividades.model.entity.Plato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatoService {

    @Autowired
    private IPlatoDAO platoDAO;

    @Autowired
    private IRestauranteDAO restauranteDAO;

    public List<Plato> getAll(){
        return platoDAO.findAll();
    }

    public Plato findByID(Integer id){
        return platoDAO.findById(id).orElse(null);
    }

    public void save(Plato plato){
        platoDAO.save(plato);
    }

    public boolean existe(Plato plato){
       return platoDAO.existsById(plato.getId());
    }

    public void delete(Plato plato){
        platoDAO.delete(plato);
    }

}
