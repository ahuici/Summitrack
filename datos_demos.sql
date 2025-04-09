/* INSERTAR DATOS DEMO EN LA BASE DE DATOS DE SUMMITRACK, TFG AIMAR HUICI*/
USE summitrack;

/*LIMPIAR DATOS RESTANTES*/
delete from completa where 1 = 1;
delete from calendario where 1 = 1;
delete from ruta where 1 = 1;
delete from monte where 1 = 1;
delete from persona where 1 = 1;

/* MONTES SCRIPT */
INSERT INTO monte (id, nombre, altura, ubicacion, is_favorito) VALUES
    (1000, 'Balaitús', 3144, 'Huesca', true),
    (1001, 'Posets', 3371, 'Huesca', false),
    (1002, 'Torre Costerillou', 3049, 'Huesca', false),
    (1003, 'Aguja d\'Ussel', 3022, 'Huesca', false),
    (1004, 'Aguja Cadier', 3019, 'Huesca', false),
    (1005, 'Frondella N.', 3063, 'Huesca', false),
    (1006, 'Frondella', 3066, 'Huesca', false),
    (1007, 'Frondella Central', 3049, 'Huesca', false),
    (1008, 'Gran Facha', 3003, 'Huesca', true),
    (1009, 'Infierno Occidental', 3073, 'Huesca', false),
    (1010, 'Infierno Central', 3083, 'Huesca', true),
    (1011, 'Infierno Oriental', 3078, 'Huesca', false),
    (1012, 'Aguja de Arnales', 3034, 'Huesca', false),
    (1013, 'Arnales', 3004, 'Huesca', false),
    (1014, 'Aguja Pondiellos', 3014, 'Huesca', false),
    (1015, 'Garmo Negro', 3058, 'Huesca', true);

/* PERSONAS SCRIPT*/
INSERT INTO persona (id, nombre, apellidos, edad, genero) values
	(2000, 'Aimar', 'Huici', 19, 'Hombre'),
	(2001, 'Josetxo', 'Nidea', 18, 'Hombre'),
	(2002, 'Hodei', 'Cia', 18, 'Hombre'),
	(2003, 'Oskia', 'Senosiain ', 20,'Mujer'),
	(2004, 'Ibai', 'Legarra ', 20,'Hombre'),
	(2005, 'Sergio', 'Ajona ', 21,'Mujer'),
	(2006, 'Daniel', 'Medina ', 22,'Hombre');
    

/* RUTA SCRIPT */
INSERT INTO ruta (id, apuntes, desnivel, dificultad, distancia, fecha, foto, peligrosidad, tiempo, tipo, monte_id) values
	(2000, 'Muy bonito', 1500, 3, 21, "2021-07-10", null, 4, 4, "Cima", 1000),
    (2001, 'Muy feo, no vuelvo mas', 651, 1, 9, "2024-01-04", null, 1, 2, "Cima", 1001),
	(2002, 'Muy bonito, se ven todo muy bien', 1231, 5, 15, "2019-07-21", null, 1, 6, "Subcima", 1002),
	(2003, 'Dificil para hacer en familia', 2011, 4, 11, "2024-09-30", null, 2, 2, "Cima secundaria", 1003),
	(2004, 'Estaba lloviendo, no se veia nada. Tendremos que volver', 835, 1, 13, "2023-12-01", null, 4, 1, "Cima", 1004),
	(2005, 'Vistas muy feas, no merece la pena', 455, 5, 5, "2025-03-05", null, 5, 4, "Cima principal", 1005);
    
    
/* CALENDARIO SCRIPT */
INSERT INTO calendario (id, descripcion, fecha, titulo, monte_id) values
	(2000, 'Tengo mucha ganas de subir, lleva pendiente mucho tiempo', "2025-07-10", "Subir Balaitus", 1000),
	(2001, 'La ultima vez no pudimos ver las vistas', "2026-01-19", "Repetir vistas", 1004),
	(2002, 'Dicen que las vistas son muy bonitas', "2025-09-29", "Vistas bonitas", 1002),
	(2003, 'Buenos atardeceres', "2025-12-23", "Atardeceres", 1003),
	(2004, '', "2025-06-01", "Entrenamiento series", 1006),
	(2005, 'Vamos a ir en cuadrilla', "2025-10-14", "Quedada cuadrilla", 1005);
    
/* COMPLETA SCRIPT */
INSERT INTO completa (id_persona, id_ruta, persona_id,ruta_id) values
	(2000, 2000, 2000, 2000),
	(2001, 2001, 2001, 2001),
	(2002, 2002, 2002, 2002),
	(2003, 2003, 2003, 2003),
	(2004, 2004, 2004, 2004),
	(2005, 2005, 2005, 2005);


    
