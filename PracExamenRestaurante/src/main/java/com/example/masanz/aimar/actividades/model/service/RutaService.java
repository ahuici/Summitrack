package com.example.masanz.aimar.actividades.model.service;

import com.example.masanz.aimar.actividades.model.DAO.IMonteDAO;
import com.example.masanz.aimar.actividades.model.DAO.IPersonaDAO;
import com.example.masanz.aimar.actividades.model.DAO.IRutaDAO;
import com.example.masanz.aimar.actividades.model.entity.Monte;
import com.example.masanz.aimar.actividades.model.entity.Ruta;
import org.apache.tomcat.util.net.jsse.JSSEUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RutaService {

    @Autowired
    private IPersonaDAO personaDAO;

    @Autowired
    private IRutaDAO rutaDAO;

    public List<Ruta> getAll(){
        return rutaDAO.findAll();
    }

    public Ruta findByID(Integer id){
        return rutaDAO.findById(id).orElse(null);
    }

    public void save(Ruta ruta){ rutaDAO.save(ruta);}

    public boolean existe(Ruta ruta){
        return rutaDAO.existsById(ruta.getId());
    }

    public void delete(Ruta ruta){
        rutaDAO.delete(ruta);
    }

    public List<Ruta> getAllById(int id){
        List<Ruta> rutas = new ArrayList<>();
        for (Ruta ruta : getAll()){
            if(ruta.getMonte().getId() == id) rutas.add(ruta);
        }
        return rutas;
    }
}

