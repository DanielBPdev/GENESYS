--liquibase formatted sql

--changeset jocorrea:01
--comment:
CREATE TABLE TipoDocumentoRequisito (
tdrId BIGINT IDENTITY(1,1) NOT NULL,
tdrRequisito BIGINT NOT NULL,
tdrTipoDocumento VARCHAR(26) NOT NULL,
CONSTRAINT PK_TipoDocumentoRequisito_tdrId PRIMARY KEY (tdrId)
);

CREATE TABLE aud.TipoDocumentoRequisito_aud (
tdrId BIGINT NOT NULL,
REV bigint NOT NULL,
REVTYPE smallint NULL,
tdrRequisito BIGINT NOT NULL,
tdrTipoDocumento VARCHAR(26) NOT NULL,
CONSTRAINT FK_TipoDocumentoRequisito_aud_REV FOREIGN KEY (REV) REFERENCES aud.Revision(revId)
);

--changeset jocorrea:02
--comment:
INSERT INTO TipoDocumentoRequisito (tdrRequisito, tdrTipoDocumento) VALUES ((SELECT reqId FROM Requisito WHERE reqDescripcion = 'Copia del documento de identidad' AND reqEstado = 'HABILITADO'), 'IDENTIFICACION_PERSONA');
INSERT INTO TipoDocumentoRequisito (tdrRequisito, tdrTipoDocumento) VALUES ((SELECT reqId FROM Requisito WHERE reqDescripcion = 'Copia del RUT' AND reqEstado = 'HABILITADO'), 'IDENTIFICACION_EMPLEADOR');
INSERT INTO TipoDocumentoRequisito (tdrRequisito, tdrTipoDocumento) VALUES ((SELECT reqId FROM Requisito WHERE reqDescripcion = 'Copia documento identidad  representante legal / administrador / empleador' AND reqEstado = 'HABILITADO'), 'IDENTIFICACION_REPRE_LEGAL');
INSERT INTO TipoDocumentoRequisito (tdrRequisito, tdrTipoDocumento) VALUES ((SELECT reqId FROM Requisito WHERE reqDescripcion = 'Documento acredita existencia/representación legal o Personería Jurídica' AND reqEstado = 'HABILITADO'), 'CERTIFICA_CREACION_EMPRESA');
INSERT INTO TipoDocumentoRequisito (tdrRequisito, tdrTipoDocumento) VALUES ((SELECT reqId FROM Requisito WHERE reqDescripcion = 'Formulario de solicitud de afiliación' AND reqEstado = 'HABILITADO'), 'FORMULARIO_AFILIACION');
