--liquibase formatted sql

--changeset hhernandez:01
--comment: Se crea tabla TasasInteresMora_aud
CREATE TABLE dbo.TasasInteresMora_aud(
	timId bigint IDENTITY(1,1) NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	timFechaInicioTasa date NOT NULL,
	timFechaFinTasa date NOT NULL,
	timNumeroPeriodoTasa smallint NOT NULL,
	timPorcentajeTasa numeric(4, 4) NOT NULL,
	timNormativa varchar(100) NOT NULL,
	timTipoInteres varchar(20) NOT NULL,
 );
ALTER TABLE dbo.TasasInteresMora_aud ADD CONSTRAINT FK_TasasInteresMora_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--changeset hhernandez:02
--comment: Se elimina la tabla PilaEjecucionProgramada_aud y se crea tabla EjecucionProgramada_aud
--Eliminacion de la tabla dbo.PilaEjecucionProgramada 
ALTER TABLE dbo.PilaEjecucionProgramada_aud DROP CONSTRAINT FK_PilaEjecucionProgramada_aud_REV;
DROP TABLE dbo.PilaEjecucionProgramada_aud;

--Creacion de la tabla dbo.EjecucionProgramada_aud    
CREATE TABLE dbo.EjecucionProgramada_aud(
	ejpId bigint IDENTITY(1,1) NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	ejpFechaDefinicion datetime NULL,
	ejpUsuario varchar(255) NULL,
	ejpFrecuencia varchar(50) NULL,
	ejpHoraInicio varchar(5) NULL,
	ejpFechaInicioVigencia datetime NULL,
	ejpFechaFinVigencia datetime NULL,
	ejpCajaCompensacion int NULL,
);
ALTER TABLE dbo.EjecucionProgramada_aud ADD CONSTRAINT FK_EjecucionProgramada_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);