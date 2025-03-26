package com.example.masanz.aimar.actividades.model.service;

import com.example.masanz.aimar.actividades.model.DAO.ICalendarioDAO;
import com.example.masanz.aimar.actividades.model.DAO.IMonteDAO;
import com.example.masanz.aimar.actividades.model.DAO.IPersonaDAO;
import com.example.masanz.aimar.actividades.model.DAO.IRutaDAO;
import com.example.masanz.aimar.actividades.model.entity.Calendario;
import com.example.masanz.aimar.actividades.model.entity.Monte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CalendarioService {

    @Autowired
    private IMonteDAO monteDAO;

    @Autowired
    private IPersonaDAO personaDAO;

    @Autowired
    private ICalendarioDAO calendarioDAO;

    public List<Calendario> getAll(){
        return calendarioDAO.findAll();
    }

    public Calendario findByID(Integer id){
        return calendarioDAO.findById(id).orElse(null);
    }

    public void save(Calendario calendario){ calendarioDAO.save(calendario);}

    public boolean existe(Calendario calendario){
        return calendarioDAO.existsById(calendario.getId());
    }

    public void delete(Calendario calendario){
        calendarioDAO.delete(calendario);
    }

}

