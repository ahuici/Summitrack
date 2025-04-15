/* INSERTAR DATOS DEMO EN LA BASE DE DATOS DE SUMMITRACK, TFG AIMAR HUICI*/
USE summitrack;

/*LIMPIAR DATOS RESTANTES*/
delete from completa where 1 = 1;
delete from calendario where 1 = 1;
delete from ruta where 1 = 1;
delete from monte where 1 = 1;
delete from persona where 1 = 1;

/* MONTES SCRIPT */
INSERT INTO monte (id, nombre, altura, ubicacion, is_favorito, latitud, longitud) VALUES
    (1000, 'Balaitús', 3144, 'Huesca', true, 42.838923, -0.290149),
    (1001, 'Posets', 3371, 'Huesca', false, 42.654481, 0.4352),
    (1002, 'Beriain', 1493, 'Navarra', true, 42.88835792445149, -1.980419181148572),
    (1003, 'Mesa de los tres reyes', 2444,  'Navarra', false, 42.91849196296539, -0.7232656132765253),
    (1004, 'Aneto', 3404, 'Huesca', true, 42.631089575915475, 0.6566890306862706),
    (1005, 'Midi d Ossau', 2807, 'Laruns', false, 42.84367510107132, -0.4381398184311536),
    (1006, 'Taillon', 3150, 'Huesca', false, 42.693902050133275, -0.05205623943728622),
    (1007, 'Tebarray', 2886, 'Huesca', false, 42.79377382574507, -0.27059830084952324),
    (1008, 'Gran Facha', 3003, 'Huesca', true, 42.808459823646416, -0.23770177194996325),
    (1009, 'Castillo de Acher', 2384, 'Huesca', false, 42.82310133037144, -0.6683649899718592),
    (1010, 'Infierno Central', 3083, 'Huesca', true, 42.78330540569626, -0.2636752906949856),
    (1011, 'Infierno Oriental', 3078, 'Huesca', false, 42.781518982598605, -0.2593512056550617),
    (1012, 'Ezkaba', 895, 'Navarra', true, 42.854856968299, -1.6635532960050363),
    (1013, 'Cilidro Marbore', 3328, 'Huesca', false, 42.6848369458257, 0.02323034375590083),
    (1014, 'Monte Perdido', 3355, 'Huesca', false, 42.675769046720056, 0.03367480501661623),
    (1015, 'Garmo Negro', 3058, 'Huesca', true, 42.77170331363778, -0.2641879060623257);


/* PERSONAS SCRIPT*/
INSERT INTO persona (id, nombre, apellidos, edad, genero, salir_top) values
	(2000, 'Aimar', 'Huici', 19, 'Hombre', true),
	(2001, 'Josetxo', 'Nidea', 18, 'Hombre', true),
	(2002, 'Hodei', 'Cia', 18, 'Hombre', true),
	(2003, 'Oskia', 'Senosiain ', 20,'Mujer', true),
	(2004, 'Ibai', 'Legarra ', 20,'Hombre', true),
	(2005, 'Sergio', 'Ajona ', 21,'Mujer', true),
	(2006, 'Daniel', 'Medina ', 22,'Hombre', true);
    

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


    
