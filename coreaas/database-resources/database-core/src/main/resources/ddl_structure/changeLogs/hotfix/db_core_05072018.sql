--liquibase formatted sql

--changeset jocorrea:01
--comment: Ajustes plantillas
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${usuarioVerificador}','8','Usuario verificador','Campo que indica el nombre de usuario (back) que verificó la solicitud y la devolvió al front para gestionar el producto no conforme subsanable)','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_324_065') ) ;

UPDATE VariableComunicado SET vcoOrden = '9'
WHERE vcoPlantillaComunicado = (SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'HU_PROCESO_324_065')
AND vcoClave = '${backControlInternoFovis}'
AND vcoNombre = 'Back control interno Fovis';

UPDATE VariableComunicado SET vcoOrden = '10'
WHERE vcoPlantillaComunicado = (SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'HU_PROCESO_324_065')
AND vcoClave = '${fechaYHoraDeAsignacion}'
AND vcoNombre = 'Fecha y hora de asignación';

UPDATE VariableComunicado SET vcoOrden = '11'
WHERE vcoPlantillaComunicado = (SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'HU_PROCESO_324_065')
AND vcoClave = '${modalidad}'
AND vcoNombre = 'Modalidad';

UPDATE VariableComunicado SET vcoOrden = '12'
WHERE vcoPlantillaComunicado = (SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'HU_PROCESO_324_065')
AND vcoClave = '${metodoEnvioDocumentos}'
AND vcoNombre = 'Método envío documentos';

UPDATE VariableComunicado SET vcoOrden = '13'
WHERE vcoPlantillaComunicado = (SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'HU_PROCESO_324_065')
AND vcoClave = '${estadoDeLaSolicitud}'
AND vcoNombre = 'Estado de la Solicitud';

--changeset jocorrea:02
--comment: Eliminacion tabla VariableComunicado
DELETE FROM VariableComunicado
WHERE vcoPlantillaComunicado = (SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'HU_PROCESO_324_063')
AND vcoNombre = 'Usuario verificador'
AND vcoClave = '${usuarioVerificador}'
AND vcoOrden = '8';

