package com.example.masanz.aimar.actividades;

import com.example.masanz.aimar.actividades.model.service.CalendarioService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ActividadesApplication {

	@Autowired
	private CalendarioService calendarioService;

	public static void main(String[] args) {
		SpringApplication.run(ActividadesApplication.class, args);
	}

	// Método que se ejecutará una vez que la aplicación esté completamente inicializada
	@PostConstruct
	public void eliminarAscensionesPasadas() {
		calendarioService.eliminarCalendariosPasadas(); // Elimina las ascensiones pasadas
	}
}
