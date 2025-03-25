package com.example.masanz.aimar.actividades.model.DAO;

import com.example.masanz.aimar.actividades.model.entity.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRestauranteDAO extends JpaRepository<Restaurante,Integer> {
}
