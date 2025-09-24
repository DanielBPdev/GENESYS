--liquibase formatted sql

--changeset halzate:01 
--comment: Actualización plantilla comunicado
DELETE FROM VariableComunicado WHERE vcoPlantillaComunicado = (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Rechazo de solicitud de afiliación de persona por inconsistencia en validación');
DELETE FROM VariableComunicado WHERE vcoPlantillaComunicado = (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación para requerir subsanación de solicitud de afiliación de persona');

DELETE FROM PlantillaComunicado WHERE pcoNombre = 'Rechazo de solicitud de afiliación de persona por inconsistencia en validación';
DELETE FROM PlantillaComunicado WHERE pcoNombre = 'Notificación para requerir subsanación de solicitud de afiliación de persona';

DELETE FROM VariableComunicado WHERE vcoClave = '${documentacionPendienteParaElTramite}';
