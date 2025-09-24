--liquibase formatted sql

--changeset atoro:01
--comment: Se adiciona columna daeTipoDocumentoAdjunto para la tabla DocumentoAdministracionEstadoSolicitud, se crean las tablas InformacionFaltanteAportante y Trazabilidad
ALTER TABLE dbo.DocumentoAdministracionEstadoSolicitud ADD daeTipoDocumentoAdjunto varchar (19);

CREATE TABLE dbo.InformacionFaltanteAportante (
	ifaId bigint NOT NULL IDENTITY(1,1),
	ifaSolicitud bigint NOT NULL,
	ifaTipoGestion varchar(22) NULL,
	ifaResponsableInformacion varchar(18) NULL,
	ifaTipoDocumentoGestion varchar (10) NULL,
	ifaMedioComunicacion varchar (19) NULL,
	ifaObservaciones varchar (500) NULL
	CONSTRAINT PK_InformacionFaltanteAportante_ifaId PRIMARY KEY (ifaId)
); 
ALTER TABLE InformacionFaltanteAportante WITH CHECK ADD CONSTRAINT FK_InformacionFaltanteAportante_ifaSolicitud FOREIGN KEY(ifaSolicitud) REFERENCES Solicitud (solId);

CREATE TABLE dbo.Trazabilidad (
	traId bigint NOT NULL IDENTITY(1,1),
	traSolicitud bigint NOT NULL,
	traActividad varchar(29) NULL,
	traEstadoSolicitud varchar(18) NULL,
	traFecha datetime NULL,
	traComunicado bigint NULL,
	traUsuario varchar (20) NULL
	CONSTRAINT PK_Trazabilidad_traId PRIMARY KEY (traId)
);
ALTER TABLE Trazabilidad WITH CHECK ADD CONSTRAINT FK_Trazabilidad_traSolicitud FOREIGN KEY (traSolicitud) REFERENCES Solicitud (solId);
ALTER TABLE Trazabilidad WITH CHECK ADD CONSTRAINT FK_Trazabilidad_traComunicado FOREIGN KEY (traComunicado) REFERENCES Comunicado (comId);

--changeset arocha:02
--comment: Se adiciona schema pila y se crean las tablas Aportante y Cotizante 	
CREATE SCHEMA pila;

CREATE TABLE pila.Aportante(
	appTipoIdentificacion varchar(20) NOT NULL,
	appNumeroIdentificacion varchar(16) NOT NULL,     
	appTransaccion bigint NOT NULL
);

CREATE TABLE pila.Cotizante(  
	copTipoIdentificacion varchar (20) NOT NULL,
    copNumeroIdentificacion varchar (16) NOT NULL,
    copTipoIdentificacionAportante varchar (20) NOT NULL,
    copNumeroIdentificacionAportante varchar(16) NOT NULL,
    copPeriodoAporte varchar(7) NOT NULL,
    copTransaccion bigint NOT NULL
);

