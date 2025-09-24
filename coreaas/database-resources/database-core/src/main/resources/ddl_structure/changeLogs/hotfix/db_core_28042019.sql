--liquibase formatted sql

--changeset dsuesca:01
--comment: 
CREATE TABLE HistoricoBeneficiario (
	hbeId bigint IDENTITY(1,1) NOT NULL,
	hbeParentesco varchar(25) NULL,	
	hbeTipoBeneficiario varchar(30) NOT NULL,
	hbePrimerNombreAfiliado varchar(50) NULL,
	hbeSegundoNombreAfiliado varchar(50) NULL,
	hbePrimerApellidoAfiliado varchar(50) NULL,
	hbeSegundoApellidoAfiliado varchar(50) NULL,
	hbeRazonSocialAfiliado varchar(250) NULL,
	hbeTipoIdentificacionAfiliado varchar(20) NULL,
	hbeNumeroIdentificacionAfiliado varchar(16) NULL,	
	hbeFechaAfiliacion date NULL,
	hbeEstadoBeneficiarioAfiliado varchar(20) NULL,
	hbeTipoIdentificacionBeneficiario varchar(20) NULL,
	hbeNumeroIdentificacionBeneficiario varchar(16) NULL,
	CONSTRAINT PK_HistoricoBeneficiario_hbeId PRIMARY KEY (hbeId)
);
