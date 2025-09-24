--liquibase formatted sql


--changeset  jzambrano:01
--comment:Insert dato tipo parametro
INSERT INTO Parametro (prmNombre,prmValor) VALUES ('BPMS_PROCESS_AFIL_DEP_WEB_DEPLOYMENTID','com.asopagos.coreaas.bpm.afiliacion_dependiente_web:afiliacion_dependiente_web:0.0.2-SNAPSHOT');
