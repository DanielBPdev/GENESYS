--liquibase formatted sql

--changeset abaquero:01
--comment: Se crean las tablas TipoInfraestructura_aud, TipoTenencia_aud, Infraestructura_aud, SitioPago_aud y se adiciona campo en la tabla MedioEfectivo
CREATE TABLE TipoInfraestructura_aud(
	tifId bigint NOT NULL IDENTITY,
	REV int NOT NULL,
	REVTYPE smallint NULL,	
	tifCodigo varchar(3) NOT NULL, 
	tifNombre varchar(255) NOT NULL, 
	tifMedidaCapacidad varchar(40) NOT NULL, 
	tifActivo bit NOT NULL, 
);
ALTER TABLE TipoInfraestructura_aud ADD CONSTRAINT FK_TipoInfraestructura_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE TipoTenencia_aud(
	tenId bigint NOT NULL IDENTITY, 
	REV int NOT NULL,
	REVTYPE smallint NULL,	
	tenCodigo smallint NOT NULL, 
	tenNombre varchar(255) NOT NULL, 
	tenActivo bit NOT NULL, 
);
ALTER TABLE TipoTenencia_aud ADD CONSTRAINT FK_TipoTenencia_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE Infraestructura_aud(
	infId bigint NOT NULL IDENTITY, 
	REV int NOT NULL,
	REVTYPE smallint NULL,	
	infCodigoParaSSF varchar(13) NOT NULL, 
	infCodigoParaCCF varchar(12) NOT NULL, 
	infConsecutivoInfraestructura smallint NOT NULL, 
	infNombre varchar(255) NOT NULL, 
	infTipoInfraestructura bigint NOT NULL, 
	infZona varchar(255) NOT NULL, 
	infDireccion varchar(300) NOT NULL, 
	infAreaGeografica varchar(6) NOT NULL, 
	infMunicipio bigint NOT NULL, 
	infTipoTenencia bigint NOT NULL, 
	infLatitud numeric(9,6) NOT NULL, 
	infLongitud numeric(9,6) NOT NULL, 
	infActivo bit NOT NULL, 
);
ALTER TABLE Infraestructura_aud ADD CONSTRAINT FK_Infraestructura_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE SitioPago_aud(
	sipId bigint NOT NULL IDENTITY,
	REV int NOT NULL,
	REVTYPE smallint NULL,	
	sipCodigo varchar(3) NOT NULL, 
	sipNombre varchar(255) NOT NULL, 
	sipInfraestructura bigint NOT NULL, 
	sipActivo bit NOT NULL, 
);
ALTER TABLE SitioPago_aud ADD CONSTRAINT FK_SitioPago_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

