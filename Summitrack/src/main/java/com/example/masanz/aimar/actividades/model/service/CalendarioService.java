package com.example.masanz.aimar.actividades.model.service;

import com.example.masanz.aimar.actividades.model.DAO.ICalendarioDAO;
import com.example.masanz.aimar.actividades.model.entity.Calendario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CalendarioService {

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
        List<Calendario> calendarios = getAll();
        List<Calendario> eliminadas = new ArrayList<>();

        for (Calendario calendario : calendarios){
            if (calendario.getFecha().isBefore(LocalDate.now())) eliminadas.add(calendario);
        }

        for (Calendario calendario : eliminadas){
            delete(calendario);
        }
    }

    public Map<String, List<Calendario>> getCalendariosAgrupadosPorMes() {
        List<Calendario> calendarios = getAll();
        calendarios.sort(Comparator.comparing(Calendario::getFecha));
        return calendarios.stream()
                .collect(Collectors.groupingBy(
                        c -> c.getFecha().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + c.getFecha().getYear(),
                        LinkedHashMap::new,
                        Collectors.toList()
                ));
    }
}

