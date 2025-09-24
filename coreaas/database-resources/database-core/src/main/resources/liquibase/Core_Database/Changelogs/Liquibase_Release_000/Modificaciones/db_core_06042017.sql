--liquibase formatted sql

--changeset clmarin:02
--comment: Creacion tabla PersonaDetalle
CREATE TABLE PersonaDetalle(
	pedId bigint IDENTITY(1,1) NOT NULL,
	pedPersona bigint NOT NULL,
	pedFechaNacimiento date NULL,
	pedFechaExpedicionDocumento date NULL,
	pedGenero varchar(10) NULL,
	pedOcupacionProfesion int NULL,
	pedNivelEducativo varchar(100) NULL,
	pedGradoAcademico smallint NULL,
	pedCabezaHogar bit NULL,
	pedAutorizaUsoDatosPersonales bit NULL,
	pedResideSectorRural bit NULL,
	pedMedioPago smallint NULL,
	pedEstadoCivil varchar(20) NULL,
	pedHabitaCasaPropia bit NULL,
	pedFallecido bit NULL
	CONSTRAINT PK_Persona_pedId PRIMARY KEY (pedId)
);

--changeset clmarin:03
--comment: Creacion de constraints tabla 
ALTER TABLE PersonaDetalle  WITH CHECK ADD CONSTRAINT FK_PersonaDetalle_pedPersona FOREIGN KEY(pedPersona) REFERENCES Persona (perId);
ALTER TABLE PersonaDetalle  WITH CHECK ADD CONSTRAINT FK_PersonaDetalle_pedMedioPago FOREIGN KEY(pedMedioPago) REFERENCES ParametrizacionMedioDePago (pmpId);
ALTER TABLE PersonaDetalle  WITH CHECK ADD CONSTRAINT FK_PersonaDetalle_pedOcupacionProfesion FOREIGN KEY(pedOcupacionProfesion) REFERENCES OcupacionProfesion (ocuId);
ALTER TABLE PersonaDetalle  WITH CHECK ADD CONSTRAINT FK_PersonaDetalle_pedGradoAcademico FOREIGN KEY(pedGradoAcademico) REFERENCES GradoAcademico (graId);