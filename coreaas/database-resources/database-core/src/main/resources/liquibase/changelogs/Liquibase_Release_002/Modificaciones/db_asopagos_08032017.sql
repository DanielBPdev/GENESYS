--liquibase formatted sql

--changeset flopez:01
--comment: Creacion de las tablas SolicitudNovedadEmpleador y SolicitudNovedadAfiliado
DROP TABLE SolicitudNovedadEmpleador;

DROP TABLE SolicitudNovedadAfiliado;

CREATE TABLE SolicitudNovedadEmpleador
(
   sneId BIGINT NOT NULL,
   sneIdEmpleador BIGINT,
   sneIdSolicitudNovedad BIGINT,
   CONSTRAINT PK_SolicitudNovedadEmpleador_sneId PRIMARY KEY (sneId) 
 )
 CREATE SEQUENCE SEC_consecutivoSne START WITH 1 INCREMENT BY 1 ;
 
 ALTER TABLE SolicitudNovedadEmpleador ADD CONSTRAINT FK_SolicitudNovedadEmpleador_sneIdEmpleador FOREIGN KEY (sneIdEmpleador) REFERENCES Empleador
 ALTER TABLE SolicitudNovedadEmpleador ADD CONSTRAINT FK_SolicitudNovedadEmpleador_sneIdSolicitudNovedad FOREIGN KEY (sneIdSolicitudNovedad) REFERENCES SolicitudNovedad
 
 
  CREATE TABLE SolicitudNovedadAfiliado
(
   snaId BIGINT NOT NULL,
   snaIdAfiliado BIGINT,
   snaIdSolicitudNovedad BIGINT,
   CONSTRAINT PK_SolicitudNovedadAfiliado_snaId PRIMARY KEY (snaId) 
 )
 CREATE SEQUENCE SEC_consecutivoSna START WITH 1 INCREMENT BY 1 ;
 
 ALTER TABLE SolicitudNovedadAfiliado ADD CONSTRAINT FK_SolicitudNovedadAfiliado_snaIdAfiliado FOREIGN KEY (snaIdAfiliado) REFERENCES Afiliado
 ALTER TABLE SolicitudNovedadAfiliado ADD CONSTRAINT FK_SolicitudNovedadAfiliado_snaIdSolicitudNovedad FOREIGN KEY (snaIdSolicitudNovedad) REFERENCES SolicitudNovedad
 
--changeset jsanchez:02
--comment: Creacion de la tabla ListaEspecialRevision con IDENTITY
DELETE FROM ListaEspecialRevision;
 
DROP TABLE ListaEspecialRevision;

CREATE TABLE ListaEspecialRevision
(
   	lerId BIGINT IDENTITY(1,1) NOT NULL,
   	lerTipoIdentificacion varchar(20) NOT NULL,
	lerNumeroIdentificacion varchar(16) NOT NULL,
	lerDigitoVerificacion tinyint NULL,
	lerCajaCompensacion int NULL,
	lerNombreEmpleador varchar(200) NULL,
	lerFechaInicioInclusion date NULL,
	lerFechaFinInclusion date NULL,
	lerRazonInclusion varchar(20) NULL,
	lerEstado varchar(20) NULL,
	lerComentario varchar(255) NULL,
   CONSTRAINT PK_ListaEspecialRevision_lerId PRIMARY KEY (lerId) 
 )
 
 ALTER TABLE ListaEspecialRevision ADD CONSTRAINT FK_ListaEspecialRevision_lerCajaCompensacion FOREIGN KEY (lerCajaCompensacion) REFERENCES CajaCompensacion
 