package com.example.masanz.aimar.actividades.model.DAO;

import com.example.masanz.aimar.actividades.model.entity.Calendario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICalendarioDAO extends JpaRepository<Calendario, Integer>{

}