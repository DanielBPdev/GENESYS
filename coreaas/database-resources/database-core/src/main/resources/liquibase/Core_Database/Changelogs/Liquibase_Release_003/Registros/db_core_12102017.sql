--liquibase formatted sql

--changeset jroa:01
--comment: Insercion de registro en la tabla parametro
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('BPMS_PROCESS_GESTION_PREVENTIVA_CARTERA_DEPLOYMENTID','com.asopagos.coreaas.bpm.gestion_preventiva_cartera:gestion_preventiva_cartera:0.0.2-SNAPSHOT',0,'BPM_PROCESS','Identificador de versión de proceso BPM para Gestión Preventiva de Cartera');
