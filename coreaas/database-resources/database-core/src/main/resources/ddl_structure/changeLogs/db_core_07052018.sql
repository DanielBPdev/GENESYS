--liquibase formatted sql

--changeset borozco:01
--comment: Inserts tablas PlantillaComunicado y VariableComunicado
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Encabezado_161 Resumen datos de la solicitud','Cuerpo','Encabezado','Mensaje','Encabezado_161 Resumen datos de la solicitud','Pie','HU_PROCESO_221_161_PADRE') ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroDeSolicitud}','1','Número de solicitud','Campo corresponde al número asignado automáticamente ','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_221_161_PADRE') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaYHoraDeSolicitud}','2','Fecha y hora de solicitud','Corresponde al campo "Fecha y hora de radicación"','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_221_161_PADRE') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${estadoDeLaSolicitud}','3','Estado de la Solicitud','Estado de la Solicitud','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_221_161_PADRE') ) ;

--changeset borozco:02
--comment: Creacion tabla SolicitudPreventivaAgrupadora
 CREATE TABLE SolicitudPreventivaAgrupadora (
 spaId bigint NOT NULL IDENTITY(1,1),
 spaEstadoSolicitudPreventivaAgrupadora varchar(255) NULL,
 spaSolicitudGlobal bigint NULL,
 CONSTRAINT PK_SolicitudPreventivaAgrupadora_spaId PRIMARY KEY (spaId)
)
ALTER TABLE SolicitudPreventiva ADD CONSTRAINT FK_SolicitudPreventiva_sprSolicitudPreventivaAgrupadora  FOREIGN KEY (sprSolicitudPreventivaAgrupadora ) REFERENCES SolicitudPreventivaAgrupadora (
spaId)

--changeset borozco:03
--comment: Se agregan campos a tabla SolicitudPreventiva
ALTER TABLE SolicitudPreventiva ADD sprCantidadVecesMoroso smallint
ALTER TABLE SolicitudPreventiva ADD sprEstadoActualCartera varchar(6)
ALTER TABLE SolicitudPreventiva ADD sprFechaLimitePago date  
ALTER TABLE SolicitudPreventiva ADD sprSolicitudPreventivaAgrupadora bigint
ALTER TABLE SolicitudPreventiva ADD sprTrabajadoresActivos smallint
ALTER TABLE SolicitudPreventiva ADD sprValorPromedioAportes numeric(19,2)

--changeset borozco:04
--comment: Agregacion foranea FK_ParametrizacionPreventiva_sprSolicitudPreventivaAgrupadora
ALTER TABLE SolicitudPreventiva ADD CONSTRAINT FK_ParametrizacionPreventiva_sprSolicitudPreventivaAgrupadora FOREIGN KEY (sprSolicitudPreventivaAgrupadora) REFERENCES SolicitudPreventivaAgrupadora(spaId)
