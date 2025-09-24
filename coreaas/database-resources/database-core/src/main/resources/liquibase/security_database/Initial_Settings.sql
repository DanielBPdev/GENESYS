--liquibase formatted sql

--changeset  jcamargo:02  context:security,security-pruebas
--comment:  Parametrización de la tabla pregunta 

INSERT INTO Pregunta (prePregunta, preEstado) VALUES ('¿Cuál es su color favorito?','ACTIVO');
INSERT INTO Pregunta (prePregunta, preEstado) VALUES ('¿Cuál es el nombre de su mamá?','ACTIVO');
INSERT INTO Pregunta (prePregunta, preEstado) VALUES ('¿Cuál es el nombre de su mejor amigo?','ACTIVO');
INSERT INTO Pregunta (prePregunta, preEstado) VALUES ('¿Cuál es el nombre de su abuela?','ACTIVO');
INSERT INTO Pregunta (prePregunta, preEstado) VALUES ('¿Cuál es su súper heroe favorito?','ACTIVO');
INSERT INTO Pregunta (prePregunta, preEstado) VALUES ('¿Cuál es el nombre de su mascota?','ACTIVO');