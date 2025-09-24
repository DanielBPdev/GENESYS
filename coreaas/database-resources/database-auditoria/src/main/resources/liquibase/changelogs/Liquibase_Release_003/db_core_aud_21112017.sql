--liquibase formatted sql

--changeset jocampo:01
--comment:Se crea tabla BeneficiarioDetalle y se adiciona campo en Beneficiario
CREATE TABLE BeneficiarioDetalle_aud(
	bedId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	bedSalarioMensual NUMERIC(19,5) NULL,
	bedLabora BIT NULL,
	bedPersonaDetalle BIGINT NOT NULL,
	bedCertificadoEscolaridad BIT NULL,
	bedFechaRecepcionCertificadoEscolar DATE NULL,
	bedFechaVencimientoCertificadoEscolar DATE NULL,
);
ALTER TABLE BeneficiarioDetalle_aud ADD CONSTRAINT FK_BeneficiarioDetalle_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

ALTER TABLE Beneficiario_aud ADD benBeneficiarioDetalle BIGINT NULL;

--changeset jocampo:02
--comment:Se adiciona campo en la tabla PersonaDetalle_aud
ALTER TABLE PersonaDetalle_aud ADD pedEstudianteTrabajoDesarrolloHumano BIT NULL;