--liquibase formatted sql

--changeset squintero:01
--comment: Insert tabla Constante
INSERT Constante(cnsNombre,cnsValor,cnsDescripcion) VALUES ('BPMS_PROCESS_GESTION_PREVENTIVA_CARTERA_ACTUALIZACION_DEPLOYMENTID','com.asopagos.coreaas.bpm.gestion_preventiva_cartera_actualizacion:gestion_preventiva_cartera_actualizacion:0.0.2-SNAPSHOT','Identificador de versión del proceso BPM para la actualziación de datos en la gestión preventiva de cartera');
