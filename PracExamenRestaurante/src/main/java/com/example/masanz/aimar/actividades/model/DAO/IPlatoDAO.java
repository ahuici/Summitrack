package com.example.masanz.aimar.actividades.model.DAO;

import com.example.masanz.aimar.actividades.model.entity.Plato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPlatoDAO extends JpaRepository<Plato, Integer> {
}
