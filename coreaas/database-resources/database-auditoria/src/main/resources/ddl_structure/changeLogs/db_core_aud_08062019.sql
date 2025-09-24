--liquibase formatted sql

--changeset jocorrea:01
--comment:

CREATE TABLE DatosFichaControl_aud(
	dfcId bigint not null,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	dfcNombreReporte varchar(100) not null,
	dfcNombreResponsableEnvio varchar(255),
	dfcCargoResponsableEnvio varchar(200),
	dfcCorreoElectronicoUno varchar(255),
	dfcCorreoElectronicoDos varchar(255),
	dfcTelefonoResponsable varchar(10),
	dfcIndicativoResponsable varchar(3)
)