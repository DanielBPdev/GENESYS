--liquibase formatted sql

--changeset sbrinez:01
--comment: Cambio de tipo de dato para nombres de archivo
ALTER TABLE ArchivoConsumosAnibol ALTER COLUMN acnNombreArchivo VARCHAR(40);

--changeset jocorrea:02
--comment: Ajustes Plantillas Mantis
DELETE FROM VariableComunicado
WHERE vcoPlantillaComunicado = (SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'HU_PROCESO_322_041')
AND vcoNombre = 'Usuario que radicó'
AND vcoOrden = '8';

INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${usuarioQueRadico}','8','Usuario que radicó','Campo corresponde al Solicitante que asigna automáticamente','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_322_044') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${usuarioQueRadico}','8','Usuario que radicó','Campo corresponde al Solicitante que asigna automáticamente','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_322_041') ) ;

DELETE FROM VariableComunicado
WHERE vcoPlantillaComunicado = (SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'HU_PROCESO_324_074');

INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacion}','1','Tipo identificación','Tipo identificación','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_324_074') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdentificacion}','2','Número Identificación','Número Identificación','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_324_074') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoDeTransaccion}','5','Tipo de transacción','Tipo de transacción','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_324_074') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroDeSolicitud}','6','Número de solicitud','Campo corresponde al número asignado automáticamente ','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_324_074') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaYHoraDeSolicitud}','7','Fecha y hora de solicitud','Corresponde al campo "Fecha y hora de radicación"','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_324_074') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${metodoEnvioDocumentos}','11','Método envío documentos ','Método envío documentos','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_324_074') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaYHoraDeAsignacion}','9','Fecha y hora de asignación','Fecha y hora de asignación','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_324_074') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${estadoDeLaSolicitud}','12','Estado de la Solicitud','Estado de la Solicitud','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_324_074') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${jefeDeHogar}','3','Jefe de hogar','Jefe de hogar','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_324_074') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cicloDeAsignacion}','4','Ciclo de asignación','Ciclo de asignación','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_324_074') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${modalidad}','10','Modalidad','Modalidad','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_324_074') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${backControlInternoFovis}','8','Back control interno Fovis','usuario back','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_324_074') ) ;

--changeset fvasquez:03
--comment: 
ALTER TABLE BitacoraCartera add bcaNumeroOperacion varchar(12) NULL;
ALTER TABLE aud.BitacoraCartera_aud add bcaNumeroOperacion varchar(12) NULL;