--liquibase formatted sql

--changeset jroa:01
--comment: Se crea esquema para las tablas de seguridad 
CREATE SCHEMA seg;

--changeset jroa:02
--comment: Se crean las tablas de seguridad
CREATE TABLE Pregunta(
	preId bigint IDENTITY(1,1) NOT NULL,
	prePregunta varchar (50) NOT NULL,
	preEstado varchar (10) NOT NULL, 
	CONSTRAINT PK_Pregunta_id PRIMARY KEY (preId)
);

CREATE TABLE ReferenciaToken(
	retId bigint IDENTITY(1,1) NOT NULL,
	retToken varchar (50) NULL,
	retTipoIdentificacion varchar (20), 
    retNumeroIdentificacion  varchar (16),
    retDigitoVerificacion smallint ,
    retFechaExpiracion DATETIME2,
	CONSTRAINT PK_ReferenciaToken_retId PRIMARY KEY (retId)
);

ALTER TABLE Pregunta ADD CONSTRAINT CK_Pregunta_preEstado check (preEstado in ('ACTIVO','INACTIVO'));

ALTER TABLE ReferenciaToken ADD CONSTRAINT CK_ReferenciaToken_retTipoIdentificacion check (retTipoIdentificacion in ('REGISTRO_CIVIL','TARJETA_IDENTIDAD','CEDULA_CIUDADANIA','CEDULA_EXTRANJERIA','PASAPORTE','CARNE_DIPLOMATICO','NIT','SALVOCONDUCTO','PERM_ESP_PERMANENCIA','PERM_PROT_TEMPORAL'));


--changeset jroa:03
--comment: Se insertan las preguntas de seguridad
INSERT INTO Pregunta (prePregunta, preEstado) VALUES ('¿Cuál es su color favorito?','ACTIVO');
INSERT INTO Pregunta (prePregunta, preEstado) VALUES ('¿Cuál es el nombre de su mamá?','ACTIVO');
INSERT INTO Pregunta (prePregunta, preEstado) VALUES ('¿Cuál es el nombre de su mejor amigo?','ACTIVO');
INSERT INTO Pregunta (prePregunta, preEstado) VALUES ('¿Cuál es el nombre de su abuela?','ACTIVO');
INSERT INTO Pregunta (prePregunta, preEstado) VALUES ('¿Cuál es su súper heroe favorito?','ACTIVO');
INSERT INTO Pregunta (prePregunta, preEstado) VALUES ('¿Cuál es el nombre de su mascota?','ACTIVO');
