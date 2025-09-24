--liquibase formatted sql

--changeset jusanchez:01
--comment: Creacion de la tabla ConsolaEstadoCargueMasivo

CREATE TABLE ConsolaEstadoCargueMasivo(
	cecId bigint IDENTITY(1,1) NOT NULL,
	cecCcf varchar(5) NULL,
	cecTipoProcesoMasivo varchar(24) NOT NULL,
	cecUsuario varchar(20) NULL,
	cecFechaInicio date NULL,
	cecFechaFin date NULL,
	cecNumRegistroObjetivo bigint NOT NULL,
	cecNumRegistroProcesado bigint NOT NULL, 
	cecNumRegistroConErrores bigint NULL,
	cecNumRegistroValidos bigint NULL,
	cecEstadoCargueMasivo varchar(15) NOT NULL,
	cecCargueId bigint NULL,
	cecFileLoadedId bigint NULL,
	cecIdentificacionECM varchar(255) NOT NULL,
    CONSTRAINT PK_ConsolaEstadoCargueMasivo_cecId PRIMARY KEY (cecId) 
);
	 
 