package com.example.masanz.aimar.actividades.model.service;

import com.example.masanz.aimar.actividades.model.DAO.IRestauranteDAO;
import com.example.masanz.aimar.actividades.model.DAO.IVendeDAO;
import com.example.masanz.aimar.actividades.model.DAO.IPlatoDAO;
import com.example.masanz.aimar.actividades.model.entity.Restaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestauranteService {

    @Autowired
    private IPlatoDAO platoDAO;

    @Autowired
    private IRestauranteDAO restauranteDAO;

    @Autowired
    private IVendeDAO vendeDAO;

    public List<Restaurante> getAll(){
        return restauranteDAO.findAll();
    }

    public void save(Restaurante restaurante){
        restauranteDAO.save(restaurante);
    }

    public Restaurante findByID(Integer id){
        return restauranteDAO.findById(id).orElse(null);
    }

    public boolean existe(Restaurante restaurante){
        return restauranteDAO.existsById(restaurante.getId());
    }

    public void delete(Restaurante restaurante){
        restauranteDAO.delete(restaurante);
    }
}
