package com.example.masanz.aimar.actividades.model.DAO;

import com.example.masanz.aimar.actividades.model.entity.Ascension;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAscensionDAO extends JpaRepository<Ascension,Integer> {
}
