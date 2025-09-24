--liquibase formatted sql

--changeset jcamargo:01 context:security,security-pruebas failonerror:false
--comment:Cambio de nombre de la fecha de expiración de ReferenciaToken
 EXEC sp_rename 'ReferenciaToken.retFechaNacimiento', 'retFechaExpiracion';
 
--changeset jcamargo:02
--comment:Cambio de nombre de la fecha de expiración de ReferenciaToken
 create table AreaCajaCompensacion(
	arcId bigint NOT NULL IDENTITY,
	arcNombre varchar(30) ,
	CONSTRAINT PK_AreaCajaCompensacion_arcId PRIMARY KEY (arcId)
);

INSERT INTO AreaCajaCompensacion (arcNombre) VALUES ('Subsidio');
INSERT INTO AreaCajaCompensacion (arcNombre) VALUES ('Afiliación');
INSERT INTO AreaCajaCompensacion (arcNombre) VALUES ('Aporte');

--changeset sbrinez:03
--comment:Refactor y creacion EscalamientoSolicitud
create table EscalamientoSolicitud(
	esoId bigint NOT NULL IDENTITY,
	esoSolicitud bigint NOT NULL ,
	esoAsunto varchar(100) NOT NULL,
	esoDescripcion  varchar (255) NOT NULL,
	esoDestinatario varchar(255),
	esoResultadoAnalista varchar(30),
	esoComentarioAnalista varchar (255),
	esoIdentificadorDocumentoSoporteAnalista varchar(255),
	esoUsuarioCreacion varchar(255),
	esoFechaCreacion datetime2,
	CONSTRAINT PK_EscalamientoSolicitud_esoId PRIMARY KEY (esoId)
);
ALTER TABLE EscalamientoSolicitud ADD CONSTRAINT FK_EscalamientoSolicitud_esoId FOREIGN KEY (esoSolicitud) REFERENCES Solicitud;

--changeset sbrinez:05
--comment:Eliminacion tabla EscalaSoliciAfiliEmpleador
DROP TABLE EscalaSoliciAfiliEmpleador;