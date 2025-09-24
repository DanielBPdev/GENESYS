--liquibase formatted sql

--changeset dsuesca:01
--comment: Se crea tabla control de cambios subsidios
CREATE TABLE CuentaCCF (
	cccId bigint IDENTITY(1,1) NOT NULL,	
	cccTipoCuenta varchar(30) NOT NULL,
	cccNumeroCuenta varchar(16) NOT NULL,	
	CONSTRAINT PK_CuentaCCF_cccId PRIMARY KEY (cccId)	
);
