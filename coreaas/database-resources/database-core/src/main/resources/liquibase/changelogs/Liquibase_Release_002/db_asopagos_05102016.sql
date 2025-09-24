--liquibase formatted sql

--changeset jcamargo:01 stripComments:false  
/*05/10/2016-jcamargo-HU-110*/
CREATE TABLE InformacionContacto
(
   incId bigint NOT NULL,
   incCargo varchar(30),
   incCorreoEnviado tinyint,
   incCuentaActiva tinyint,
   incEmail varchar(50),
   incFechaRegistro datetime,
   incIdSolicitud bigint,
   incNumeroIdentificacion varchar(10),
   incPrimerNombre varchar(50),
   incSegundoNombre varchar(50),
   incPrimerApellido varchar(50),
   incSegundoApellido varchar(50),
   incTelefonoFijo varchar(7),
   incTipoIdentificacion varchar(20),
   incToken varchar(50),
   CONSTRAINT PK_InformacionContacto PRIMARY KEY (incId)
);

ALTER TABLE InformacionContacto ADD CONSTRAINT FK_InformacionContacto_incIdSolicitud FOREIGN KEY(incIdSolicitud) REFERENCES SolicitudAfiliaciEmpleador (saeId);
GO

--changeset jcamargo:03 stripComments:false  
/*05/10/2016-jcamargo*/
ALTER TABLE HistoriaResultadoValidacion ALTER COLUMN hrvDetalle VARCHAR(500) ;

--changeset jcamargo:04 stripComments:false 
/*06/10/2016-jcamargo-HU-*/

alter table InformacionContacto alter column incToken varchar(max);

--changeset jocampo:05 stripComments:false 
/*06/10/2016-jocampo-HU-*/
alter table Comunicado alter column comMensajeEnvio varchar(max);




