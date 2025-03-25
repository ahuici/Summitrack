package com.example.masanz.aimar.actividades.model.DAO;

import com.example.masanz.aimar.actividades.model.entity.Vende;
import com.example.masanz.aimar.actividades.model.entity.VendeID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVendeDAO extends JpaRepository<Vende, VendeID> {
}
