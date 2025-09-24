--liquibase formatted sql

--changeset clmarin:01
--comment: Actualizacion de registros en la tabla VariableComunicado con respecto a las Variables
--NTF_GST_INF_PAG_APT
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'NTF_GST_INF_PAG_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'NTF_GST_INF_PAG_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${razonSocial/Nombre}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${razonSocial/Nombre}') WHERE pcoEtiqueta = 'NTF_GST_INF_PAG_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoSolicitante}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoSolicitante}') WHERE pcoEtiqueta = 'NTF_GST_INF_PAG_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaGestion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaGestion}') WHERE pcoEtiqueta = 'NTF_GST_INF_PAG_APT';

--changeset clmarin:02
--comment: Actualizacion de registros en la tabla VariableComunicado con respecto a las Constantes
--NTF_GST_INF_PAG_APT
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'NTF_GST_INF_PAG_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'NTF_GST_INF_PAG_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta = 'NTF_GST_INF_PAG_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'NTF_GST_INF_PAG_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'NTF_GST_INF_PAG_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'NTF_GST_INF_PAG_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${webCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${webCcf}') WHERE pcoEtiqueta = 'NTF_GST_INF_PAG_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta = 'NTF_GST_INF_PAG_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'NTF_GST_INF_PAG_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'NTF_GST_INF_PAG_APT';

--changeset atoro:03
--comment: Modificacion del tamaño del campo reaEstadoSolicitud para la tabla RegistroEstadoAporte
ALTER TABLE RegistroEstadoAporte ALTER COLUMN reaEstadoSolicitud varchar (25);

--changeset fvasquez:04
--comment: Creacion de la tabla SolicitudCorreccionAporte y Correccion
CREATE TABLE SolicitudCorreccionAporte(
	scaId bigint IDENTITY(1,1) NOT NULL, 
	scaEstadoSolicitud varchar(25) NULL, 
	scaTipoSolicitante varchar(25) NULL, 
	scaObservacionSupervisor varchar(255) NULL,
	scaResultadoSupervisor varchar(10) NULL,
	scaSolicitudGlobal bigint NULL, 
	scaPersona bigint NULL,
	scaAporteGeneral bigint NULL,
CONSTRAINT PK_SolicitudCorreccionAporte_scaId PRIMARY KEY (scaId)
);
ALTER TABLE SolicitudCorreccionAporte ADD CONSTRAINT FK_SolicitudCorreccionAporte_scaSolicitudGlobal FOREIGN KEY (scaSolicitudGlobal) REFERENCES Solicitud (solId);
ALTER TABLE SolicitudCorreccionAporte ADD CONSTRAINT FK_SolicitudCorreccionAporte_scaPersona FOREIGN KEY (scaPersona) REFERENCES Persona (perId);
ALTER TABLE SolicitudCorreccionAporte ADD CONSTRAINT FK_SolicitudCorreccionAporte_scaAporteGeneral FOREIGN KEY (scaAporteGeneral) REFERENCES AporteGeneral (apgId);

CREATE TABLE Correccion(
	corId bigint IDENTITY(1,1) NOT NULL,
	corAporteDetallado bigint NULL,
	corAporteGeneral bigint NULL,
	corSolicitudCorreccionAporte bigint NULL,
CONSTRAINT PK_Correccion_scaId PRIMARY KEY (corId)
);
ALTER TABLE Correccion ADD CONSTRAINT FK_Correccion_corAporteDetallado FOREIGN KEY (corAporteDetallado) REFERENCES AporteDetallado (apdId);
ALTER TABLE Correccion ADD CONSTRAINT FK_Correccion_corAporteGeneral FOREIGN KEY (corAporteGeneral) REFERENCES AporteGeneral (apgId);
ALTER TABLE Correccion ADD CONSTRAINT FK_Correccion_corSolicitudCorreccionAporte FOREIGN KEY (corSolicitudCorreccionAporte) REFERENCES SolicitudCorreccionAporte (scaId);