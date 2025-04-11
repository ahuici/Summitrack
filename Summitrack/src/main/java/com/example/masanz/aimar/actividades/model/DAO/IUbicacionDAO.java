package com.example.masanz.aimar.actividades.model.DAO;

import com.example.masanz.aimar.actividades.model.entity.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUbicacionDAO extends JpaRepository<Ubicacion, Integer> {
}
