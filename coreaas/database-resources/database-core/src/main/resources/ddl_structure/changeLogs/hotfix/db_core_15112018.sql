--liquibase formatted sql

--changeset fvasquez:01
--comment: Ambio de nomnbre campo dtaCartera
EXEC sp_rename 'DatoTemporalCartera.dtaCartera', 'dtaCarteraAgrupadora', 'COLUMN'; 
ALTER TABLE DatoTemporalCartera ADD CONSTRAINT FK_DatoTemporalCartera_dtaCarteraAgrupadora FOREIGN KEY (dtaCarteraAgrupadora) REFERENCES CarteraAgrupadora(cagId);

--changeset fvasquez:02
--comment: 
ALTER TABLE DatoTemporalCartera DROP CONSTRAINT FK_DatoTemporalCartera_Cartera;

--changeset jocorrea:03
--comment: 
ALTER TABLE PostulacionFovis ALTER COLUMN pofInfoAsignacion VARCHAR(MAX);
ALTER TABLE aud.PostulacionFovis_aud ALTER COLUMN pofInfoAsignacion VARCHAR(MAX);

--changeset jocorrea:04
--comment: 
INSERT ValidacionProceso (vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa) VALUES
('323-045-2', 'VALIDACION_INGRESOS_TOTALES_PERMITIDOS', 'POSTULACION_FOVIS_PRESENCIAL', 'ACTIVO', '1', 'JEFE_HOGAR', 0);

--changeset jvelandia:04
--comment: 
INSERT DestinatarioComunicado(dcoProceso,dcoEtiquetaPlantilla)VALUES('NOVEDADES_EMPRESAS_PRESENCIAL','DSTMTO_NVD_RET_APRT');