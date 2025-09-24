--liquibase formatted sql

--changeset jocampo:01
--comment:Se crea tabla BeneficiarioDetalle y se adiciona campo en Beneficiario
CREATE TABLE BeneficiarioDetalle(
	bedId BIGINT NOT NULL IDENTITY(1,1),
	bedSalarioMensual NUMERIC(19,5) NULL,
	bedLabora BIT NULL,
	bedPersonaDetalle BIGINT NOT NULL,
	bedCertificadoEscolaridad BIT NULL,
	bedFechaRecepcionCertificadoEscolar DATE NULL,
	bedFechaVencimientoCertificadoEscolar DATE NULL,
CONSTRAINT PK_BeneficiarioDetalle_bedId PRIMARY KEY (bedId)
);
ALTER TABLE BeneficiarioDetalle ADD CONSTRAINT FK_BeneficiarioDetalle_bedPersonaDetalle FOREIGN KEY (bedPersonaDetalle) REFERENCES PersonaDetalle(pedId);

ALTER TABLE Beneficiario ADD benBeneficiarioDetalle BIGINT NULL;
ALTER TABLE Beneficiario ADD CONSTRAINT FK_Beneficiario_benBeneficiarioDetalle FOREIGN KEY (benBeneficiarioDetalle) REFERENCES BeneficiarioDetalle(bedId);

--changeset jocampo:02
--comment:Se adiciona campo en la tabla PersonaDetalle
ALTER TABLE PersonaDetalle ADD pedEstudianteTrabajoDesarrolloHumano BIT NULL;