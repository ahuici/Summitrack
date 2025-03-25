package com.example.masanz.aimar.actividades.model.DAO;

import com.example.masanz.aimar.actividades.model.entity.Completa;
import com.example.masanz.aimar.actividades.model.entity.CompletaID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICompletaDAO extends JpaRepository<Completa, CompletaID> {
}
