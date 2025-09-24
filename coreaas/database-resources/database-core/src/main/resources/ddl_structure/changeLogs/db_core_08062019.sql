--liquibase formatted sql

--changeset dsuesca:01
--comment:
CREATE TABLE DatosFichaControl(
	dfcId bigint IDENTITY(1,1) not null,
	dfcNombreReporte varchar(100) not null UNIQUE,
	dfcNombreResponsableEnvio varchar(255),
	dfcCargoResponsableEnvio varchar(200),
	dfcCorreoElectronicoUno varchar(255),
	dfcCorreoElectronicoDos varchar(255),
	dfcTelefonoResponsable varchar(10),
	dfcIndicativoResponsable varchar(3),
	CONSTRAINT PK_DatosFichaControl_dfcId PRIMARY KEY (dfcId),
)

CREATE TABLE aud.DatosFichaControl_aud(
	dfcId bigint IDENTITY(1,1) not null,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	dfcNombreReporte varchar(100) not null UNIQUE,
	dfcNombreResponsableEnvio varchar(255),
	dfcCargoResponsableEnvio varchar(200),
	dfcCorreoElectronicoUno varchar(255),
	dfcCorreoElectronicoDos varchar(255),
	dfcTelefonoResponsable varchar(10),
	dfcIndicativoResponsable varchar(3),
	CONSTRAINT FK_DatosFichaControl_aud_REV FOREIGN KEY (REV) REFERENCES aud.Revision(revId)
)