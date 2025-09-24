--liquibase formatted sql

--changeset dasanchez:02
--comment: Se crea la tabla Banco_aud y se adicionan campos en las tablas RequisitoCajaClasificacion_aud y AporteGeneral_aud
CREATE TABLE Banco_aud(
	banId bigint NOT NULL IDENTITY,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	banCodigoPILA varchar(4) NOT NULL,
	banNombre varchar(255) NOT NULL,
	banMedioPago bit,
); 
ALTER TABLE Banco_aud ADD CONSTRAINT FK_Banco_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

ALTER TABLE RequisitoCajaClasificacion_aud ADD rtsTipoRequisito VARCHAR(30) NULL;
ALTER TABLE AporteGeneral_aud ADD apgTipoSolicitante VARCHAR (13) NULL;