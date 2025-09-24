--liquibase formatted sql

--changeset flopez:01
--comment:Se modifica tamaño campo de la tabla SolicitudLegalizacionDesembolso
ALTER TABLE SolicitudLegalizacionDesembolso ALTER COLUMN sldEstadoSolicitud VARCHAR(48) NULL;

--changeset clmarin:02
--comment:Insercion de registros en las tablas DestinatarioComunicado y PrioridadDestinatario
INSERT DestinatarioComunicado(dcoProceso,dcoEtiquetaPlantilla) VALUES ('CONVENIO_PAGO','CRR_STC_CNV_PAG');
INSERT PrioridadDestinatario(prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES ((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='CONVENIO_PAGO' AND des.dcoEtiquetaPlantilla='CRR_STC_CNV_PAG'),(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='REPRESENTANTE_LEGAL'),1);
INSERT PrioridadDestinatario(prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES ((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='CONVENIO_PAGO' AND des.dcoEtiquetaPlantilla='CRR_STC_CNV_PAG'),(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='AFILIADO_PRINCIPAL'),2);

--changeset borozco:03
--comment:Creacion de la tabla ConvenioPagoDependiente
CREATE TABLE ConvenioPagoDependiente(
	cpdId bigint NOT NULL IDENTITY(1,1),
	cpdPagoPeriodoConvenio bigint NULL,
	cpdPersona bigint NULL,
CONSTRAINT PK_ConvenioPagoDependiente_cpdId PRIMARY KEY (cpdId)
);

ALTER TABLE ConvenioPagoDependiente ADD CONSTRAINT FK_ConvenioPagoDependiente_cpdPersona FOREIGN KEY (cpdPersona) REFERENCES Persona(perId);
ALTER TABLE ConvenioPagoDependiente ADD CONSTRAINT FK_ConvenioPagoDependiente_cpdPagoPeriodoConvenio FOREIGN KEY (cpdPagoPeriodoConvenio) REFERENCES PagoPeriodoConvenio(ppcId);

--changeset ecastano:04
--comment:Modificacion de tamaño de campo de la tabla Licencia
ALTER TABLE Licencia ALTER COLUMN licTipoLicencia VARCHAR(21) NULL;
