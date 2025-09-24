--liquibase formatted sql

--changeset squintero:01
--comment: Insercion de registro en la tabla parametro
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('BPMS_PROCESS_SUBSIDIO_MONETARIO_MASIVO_DEPLOYMENTID','com.asopagos.coreaas.bpm.subsidio_monetario_masivo:subsidio_monetario_masivo:0.0.2-SNAPSHOT',0,'BPM_PROCESS','Identificador de versión de proceso BPM para subsidio monetario masivo');