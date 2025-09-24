--liquibase formatted sql

--changeset jvelandia:01
--comment: Se agrega tabla Certificado_aud
CREATE TABLE Certificado_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	cerId bigint NOT NULL,
	cerTipoCertificado varchar(40) NOT NULL,
	cerFechaGeneracion datetime2 NOT NULL,
    cerDirigidoA varchar(255) NOT NULL,
    cerPersona bigint NOT NULL,
	cerGeneradoComoEmpleador bit NOT NULL,
    cerTipoSolicitud varchar(100) NULL,
    cerComunicado bigint NULL,
    cerAnio smallint NULL,
    cerTipoAfiliado  varchar(30)  NULL,
    cerEmpleador bigint NULL
);

--changeset jvelandia:02
--comment: Se agregan constraints tabla Certificado_aud
ALTER TABLE Certificado_aud  WITH CHECK ADD  CONSTRAINT FK_Certificado_aud_REV FOREIGN KEY(REV)
REFERENCES Revision (revId);
ALTER TABLE Certificado_aud CHECK CONSTRAINT FK_Certificado_aud_REV;