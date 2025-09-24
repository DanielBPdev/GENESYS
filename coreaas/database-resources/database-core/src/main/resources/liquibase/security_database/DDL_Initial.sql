--liquibase formatted sql

--changeset jcamargo:01 context:security,security-pruebas 
--comment: Creación de la tabla pregunta base de datos security
CREATE TABLE Pregunta(
	preId bigint IDENTITY(1,1) NOT NULL,
	prePregunta varchar (50) NOT NULL,
	preEstado varchar (10) NOT NULL, 
	CONSTRAINT PK_Pregunta_id PRIMARY KEY (preId)
);

--changeset jcamargo:02 context:security,security-pruebas
--comment: Creación de la tabla ReferenciaToken
CREATE TABLE ReferenciaToken(
	retId bigint IDENTITY(1,1) NOT NULL,
	retToken varchar (50) NULL,
	retTipoIdentificacion varchar (20), 
    retNumeroIdentificacion  varchar (16),
    retDigitoVerificacion smallint ,
    retFechaNacimiento DATE,
    retFechaExpiracion DATE,
	CONSTRAINT PK_ReferenciaToken_retId PRIMARY KEY (retId)
);