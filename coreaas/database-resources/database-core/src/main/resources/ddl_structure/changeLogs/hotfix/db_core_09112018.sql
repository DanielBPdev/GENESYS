--liquibase formatted sql

--changeset clmarin:01
--comment: Actualizacon PlantillaComunicado
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Encabezado_11 Seguimiento Solicitud de Afiliación del Empleador','Cuerpo','Encabezado','Mensaje','Encabezado_11 Seguimiento Solicitud de Afiliación del Empleador','Pie','HU_PROCESO_112_124_EXT') ;

INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacion}','1','Tipo identificación','Tipo identificación','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_112_124_EXT') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdentificacion}','2','Número Identificación','Número Identificación','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_112_124_EXT') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${dv}','2,1','Dv','Cuando el valor del campo "Tipo identificación" es igual a "NIT"','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_112_124_EXT') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${razonSocial/Nombre}','3','Razón social / Nombre','Nombre completo compuesto por los campos Primer nombre, Segundo nombre, Primer apellido, Segundo apellido.Presentados en pantalla concatenados. Si es un empresa "Razón social" de ser persona','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_112_124_EXT') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoDeTransaccion}','4','Tipo de transacción','Tipo de transacción','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_112_124_EXT') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroDeSolicitud}','6','Número de solicitud','Campo corresponde al número asignado automáticamente ','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_112_124_EXT') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaYHoraDeSolicitud}','7','Fecha y hora de solicitud','Corresponde al campo "Fecha y hora de radicación"','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_112_124_EXT') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${estadoDeLaSolicitud}','8','Estado de la Solicitud','Estado de la Solicitud','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_112_124_EXT') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${canalDeRecepcion}','5','Canal de recepción','Canal de recepción','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_112_124_EXT') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${resultado}','9','Resultado','Resultado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_112_124_EXT') ) ;

UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdentificacion}') WHERE pcoEtiqueta = 'HU_PROCESO_112_124_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdentificacion}') WHERE pcoEtiqueta = 'HU_PROCESO_112_124_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${dv}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${dv}') WHERE pcoEtiqueta = 'HU_PROCESO_112_124_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${razonSocial/Nombre}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${razonSocial/Nombre}') WHERE pcoEtiqueta = 'HU_PROCESO_112_124_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoDeTransaccion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoDeTransaccion}') WHERE pcoEtiqueta = 'HU_PROCESO_112_124_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroDeSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroDeSolicitud}') WHERE pcoEtiqueta = 'HU_PROCESO_112_124_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaYHoraDeSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaYHoraDeSolicitud}') WHERE pcoEtiqueta = 'HU_PROCESO_112_124_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${estadoDeLaSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${estadoDeLaSolicitud}') WHERE pcoEtiqueta = 'HU_PROCESO_112_124_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${canalDeRecepcion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${canalDeRecepcion}') WHERE pcoEtiqueta = 'HU_PROCESO_112_124_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${resultado}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${resultado}') WHERE pcoEtiqueta = 'HU_PROCESO_112_124_EXT';

--changeset jocorrea:02
--comment: Se elimina sp por nombre errado
IF ( Object_id( 'USP_GET_HistoricoAsignacionFOVIS' ) IS NOT NULL ) 
	DROP PROCEDURE [dbo].[USP_GET_HistoricoAsignacionFOVIS];

--changeset jocorrea:03
--comment: Actualizacion tabla PlantillaComunicado
UPDATE PlantillaComunicado set pcoNombre='Creación de usuario exitosa' WHERE  pcoEtiqueta='NTF_CRCN_USR_EXT';

--changeset jocorrea:04
--comment: Creacion campos
ALTER TABLE PostulacionFovis ADD pofInfoAsignacion NVARCHAR(MAX);
ALTER TABLE aud.PostulacionFovis_aud ADD pofInfoAsignacion NVARCHAR(MAX);

--changeset jocorrea:05
--comment: Creacion campos
ALTER TABLE EjecucionProcesoAsincrono ADD epsTotalProceso smallint;
ALTER TABLE EjecucionProcesoAsincrono ADD epsAvanceProceso smallint;
ALTER TABLE aud.EjecucionProcesoAsincrono_aud ADD epsTotalProceso smallint;
ALTER TABLE aud.EjecucionProcesoAsincrono_aud ADD epsAvanceProceso smallint;