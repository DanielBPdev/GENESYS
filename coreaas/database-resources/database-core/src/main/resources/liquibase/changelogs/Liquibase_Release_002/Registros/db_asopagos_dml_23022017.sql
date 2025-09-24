--liquibase formatted sql

--changeset ogiral:01 
--comment: Insercion de parametro
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('TIEMPO_REINTEGRO','2592000000');

--changeset atoro:02 
--comment: Se agrega procesos BPM de novedades
insert into Parametro (prmNombre,prmValor) values ('BPMS_PROCESS_NOVE_EMP_PRES_DEPLOYMENTID','com.asopagos.coreaas.bpm.novedades_empleador_presencial:novedades_empleador_presencial:0.0.2-SNAPSHOT');
insert into Parametro (prmNombre,prmValor) values ('BPMS_PROCESS_NOVE_EMP_WEB_DEPLOYMENTID','com.asopagos.coreaas.bpm.novedades_empleador_web:novedades_empleador_web:0.0.2-SNAPSHOT');
insert into Parametro (prmNombre,prmValor) values ('BPMS_PROCESS_NOVE_PER_PRES_DEPLOYMENTID','com.asopagos.coreaas.bpm.novedades_persona_presencial:novedades_persona_presencial:0.0.2-SNAPSHOT');
insert into Parametro (prmNombre,prmValor) values ('BPMS_PROCESS_NOVE_DEP_WEB_DEPLOYMENTID','com.asopagos.coreaas.bpm.novedades_dependiente_web:novedades_dependiente_web:0.0.2-SNAPSHOT');
insert into Parametro (prmNombre,prmValor) values ('BPMS_PROCESS_NOVE_PER_WEB_DEPLOYMENTID','com.asopagos.coreaas.bpm.novedades_persona_web:novedades_persona_web:0.0.2-SNAPSHOT');