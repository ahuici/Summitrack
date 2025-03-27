package com.example.masanz.aimar.actividades.model.service;

import com.example.masanz.aimar.actividades.model.DAO.ICalendarioDAO;
import com.example.masanz.aimar.actividades.model.DAO.IMonteDAO;
import com.example.masanz.aimar.actividades.model.DAO.IPersonaDAO;
import com.example.masanz.aimar.actividades.model.DAO.IRutaDAO;
import com.example.masanz.aimar.actividades.model.entity.Calendario;
import com.example.masanz.aimar.actividades.model.entity.Monte;
import com.example.masanz.aimar.actividades.model.entity.Ruta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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

    public void eliminarCalendariosPasadas() {
        System.out.println("Gos");
        List<Calendario> calendarios = getAll();
        List<Calendario> eliminadas = new ArrayList<>();

        for (Calendario calendario : calendarios){
            System.out.println("Rutas " + calendario.getFecha().isBefore(LocalDate.now()));
            if (calendario.getFecha().isBefore(LocalDate.now())) eliminadas.add(calendario);
        }

        for (Calendario calendario : eliminadas){
            System.out.println("Ruta eliminada pasada: " + calendario.getId());
            delete(calendario);
        }
    }
}

