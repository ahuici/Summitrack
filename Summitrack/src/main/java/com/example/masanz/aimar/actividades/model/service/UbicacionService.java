package com.example.masanz.aimar.actividades.model.service;

import com.example.masanz.aimar.actividades.model.DAO.IMonteDAO;
import com.example.masanz.aimar.actividades.model.DAO.IRutaDAO;
import com.example.masanz.aimar.actividades.model.DAO.IUbicacionDAO;
import com.example.masanz.aimar.actividades.model.entity.Monte;
import com.example.masanz.aimar.actividades.model.entity.Ubicacion;
import org.hibernate.annotations.SecondaryRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UbicacionService {
    @Autowired
    private IUbicacionDAO ubicacionDAO;

    public List<Ubicacion> getAll(){
        return ubicacionDAO.findAll();
    }

    public Ubicacion findByID(Integer id){
        return ubicacionDAO.findById(id).orElse(null);
    }

    public void save(Ubicacion ubicacion){ ubicacionDAO.save(ubicacion);}

    public boolean existe(Ubicacion ubicacion){
        return ubicacionDAO.existsById(ubicacion.getId());
    }

    public void delete(Ubicacion ubicacion){
        ubicacionDAO.delete(ubicacion);
    }
}
