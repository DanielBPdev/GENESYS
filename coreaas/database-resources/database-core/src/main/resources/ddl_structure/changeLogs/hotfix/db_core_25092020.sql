--liquibase formatted sql

--changeset tuestrella:01
--comment: Actualizar nombre variable de comunicado

UPDATE vco SET vco.vcoClave = '${direccionEnvioCorrespondencia}', vco.vcoDescripcion = 'Dirección de Envio de Correspondencia', vco.vcoNombre = 'Dirección Envio Correspondencia' FROM variableComunicado vco JOIN PlantillaComunicado pco ON vco.vcoPlantillaComunicado = pco.pcoId WHERE vco.vcoClave = '${direccionRepresentanteLegal}' AND pco.pcoEtiqueta ='NTF_NO_REC_APO';