--liquibase formatted sql

--changeset rarboleda:01
--comment:Insercion de registros en la tabla Constante
INSERT Constante (cnsNombre, cnsValor, cnsDescripcion) VALUES('BPMS_PROCESS_SUBSIDIO_MONETARIO_ESPECIFICO_DEPLOYMENTID','com.asopagos.coreaas.bpm.subsidio_monetario_especifico:subsidio_monetario_especifico:0.0.2-SNAPSHOT','Identificador de versión de proceso BPM para gestión de subsidio monetario especifico');

--changeset jroa:02
--comment:Se modifican campos en la tabla InicioTarea
ALTER TABLE InicioTarea ALTER COLUMN itaProceso BIGINT; 
ALTER TABLE InicioTarea ALTER COLUMN itaTarea BIGINT; 

--changeset flopez:03
--comment:Insercion de registros en las tablas DestinatarioComunicado y PrioridadDestinatario
INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('POSTULACION_FOVIS_WEB','NTF_RAD_POS_FOVIS_WEB');
INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('POSTULACION_FOVIS_WEB','NTF_VAL_NEXT_RAD_POS_FOVIS_WEB');
INSERT PrioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad) VALUES ((select dcoId from DestinatarioComunicado where dcoEtiquetaPlantilla = 'NTF_RAD_POS_FOVIS_WEB'),(select gprId from GrupoPrioridad where gprNombre = 'RESPONSABLE_JEFE_HOGAR'), 1);
INSERT PrioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad) VALUES ((select dcoId from DestinatarioComunicado where dcoEtiquetaPlantilla = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB'),(select gprId from GrupoPrioridad where gprNombre = 'RESPONSABLE_JEFE_HOGAR'), 1);
