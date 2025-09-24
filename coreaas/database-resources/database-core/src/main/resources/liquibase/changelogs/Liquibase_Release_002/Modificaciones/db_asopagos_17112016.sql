--liquibase formatted sql

--changeset  jzambrano:01
--comment: Creación de la tabla TipoVia,ElementoDireccion
CREATE TABLE TipoVia(
	tviId bigint NOT NULL IDENTITY,
	tviNombreVia varchar(20),
	CONSTRAINT PK_TipoVia_tviId PRIMARY KEY (tviId)
);

CREATE TABLE ElementoDireccion(
	eldId bigint NOT NULL IDENTITY,
	eldNombre varchar(20),
	CONSTRAINT PK_ElementoDireccion_eldId PRIMARY KEY (eldId)
);

--changeset  jzambrano:02
--comment: Creación del campo ubiDescripcionIndicacion en Ubicacion
ALTER TABLE Ubicacion ADD ubiDescripcionIndicacion VARCHAR (100);