--liquibase formatted sql

--changeset abaquero:01
--comment: Actualizacion tabla ValidatorParamValue
UPDATE ValidatorParamValue SET value='S' WHERE id=2111091;


--changeset mosorio:02
--comment: Actualizacion VariableComunicado
UPDATE VariableComunicado SET vcoOrden='4' WHERE vcoClave='${fechaYHoraDeSolicitud}' AND vcoPlantillaComunicado in (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta ='HU_PROCESO_317_507');
UPDATE VariableComunicado SET vcoOrden='5' WHERE vcoClave='${nooperacion}' AND vcoPlantillaComunicado in (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta ='HU_PROCESO_317_507');
UPDATE VariableComunicado SET vcoOrden='6' WHERE vcoClave='${estadoDeLaSolicitud}' AND vcoPlantillaComunicado in (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta ='HU_PROCESO_317_507');

--changeset jvelandia:03
--comment: Actualizacion plantillaComunicado
UPDATE plantillaComunicado set pcoAsunto='RECHAZO DE SOLICITUD DE AFILIACIÓN DE INDEPENDIENTE PRODUCTO NO CONFORME' WHERE pcoAsunto = 'RECHAZO DE SOLICITUD DE AFILIACIÓN DE INDEOENDIENTE PRODUCTO NO CONFORME'