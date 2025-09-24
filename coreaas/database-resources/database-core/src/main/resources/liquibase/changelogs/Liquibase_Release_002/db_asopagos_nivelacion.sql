--liquibase formatted sql

--changeset Heinsohn_Sprint002:04 stripComments:false 
CREATE TABLE OcupacionProfesion(
	ocuId int IDENTITY(1,1) NOT NULL,
	ocuNombre varchar(255) NULL,
    CONSTRAINT PK_OcupacionProfesion_ocuId PRIMARY KEY (ocuId) 
) ;

--changeset Heinsohn_Sprint002:05 stripComments:false 
/*
 * <b>Descripción:</b> Entidad que representa una persona afiliada a la caja de 
 * compensación
 * <b>Historia de Usuario: </b>121-104
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */

 
CREATE TABLE Afiliado(
	afiId bigint IDENTITY(1,1) NOT NULL,
	afiEstadoAfiliadoCaja varchar(20) NOT NULL,
	afiPersona bigint NULL,
     CONSTRAINT PK_Afiliado_afiId PRIMARY KEY (afiId) 
);
GO

ALTER TABLE Afiliado  ADD  CONSTRAINT FK_Afliado_afiId FOREIGN KEY(afiPersona)
REFERENCES Persona (perId)
GO

--changeset Heinsohn_Sprint002:06 stripComments:false 
/*
 * <b>Descripción:</b> Entidad que representa los grupos familiares de un 
 * afiliado
 * <b>Historia de Usuario: </b>121-107
 *
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */

CREATE TABLE GrupoFamiliar(
	grfId bigint IDENTITY(1,1) NOT NULL,
	grfParametrizacionMedioPago smallint NULL,
	grfNumero smallint NOT NULL,
	grfObservaciones varchar(500) NULL,
	grfAdministradorSubsidio bigint NULL,
	grfAfiliado bigint NOT NULL,
	grfUbicacion bigint NULL,
     CONSTRAINT PK_GrupoFamiliar_grfId PRIMARY KEY (grfId) 
);
GO

ALTER TABLE GrupoFamiliar ADD  CONSTRAINT FK_GrupoFamiliar_grfAdministradorSubsidio FOREIGN KEY(grfAdministradorSubsidio) REFERENCES Persona (perId)
GO

ALTER TABLE GrupoFamiliar  ADD  CONSTRAINT FK_GrupoFamiliar_grfAfiliado FOREIGN KEY(grfAfiliado) REFERENCES Afiliado (afiId)
GO

ALTER TABLE GrupoFamiliar  ADD  CONSTRAINT FK_GrupoFamiliar_grfUbicacion FOREIGN KEY(grfUbicacion) REFERENCES Ubicacion (ubiId)
GO

ALTER TABLE GrupoFamiliar  ADD  CONSTRAINT FK_GrupoFamiliar_grfParametrizacionMedioPago FOREIGN KEY(grfParametrizacionMedioPago) REFERENCES ParametrizacionMedioDePago (pmpId)
GO

--changeset Heinsohn_Sprint002:07 stripComments:false 

ALTER TABLE Persona drop column perNombres;
ALTER TABLE Persona drop column perApellidos;
ALTER TABLE Persona add perPrimerNombre varchar(50);
ALTER TABLE Persona add perSegundoNombre varchar(50);
ALTER TABLE Persona add perPrimerApellido varchar(50);
ALTER TABLE Persona add perSegundoApellido varchar(50);
ALTER TABLE Persona add perFechaNacimiento date;
ALTER TABLE Persona add perFechaExpedicionDocumento date;
ALTER TABLE Persona add perGenero varchar (10);
ALTER TABLE Persona add perOcupacionProfesion int;
ALTER TABLE Persona add perNivelEducativo varchar(100);
ALTER TABLE Persona add perCabezaHogar bit;
ALTER TABLE Persona add habitaCasaPropia bit;
ALTER TABLE Persona add perAutorizaEnvioEmail bit;
ALTER TABLE Persona add perAutorizaUsoDatosPersonales bit;
ALTER TABLE Persona add perResideSectorRural bit;
ALTER TABLE Persona add perMedioPago smallint;
ALTER TABLE Persona add perEstadoCivil varchar(20);
ALTER TABLE Persona add perHabitaCasaPropia bit;
ALTER TABLE Persona drop column perPrecargado;


ALTER TABLE Persona ADD CONSTRAINT FK_Persona_perOcupacionProfesion FOREIGN KEY(perOcupacionProfesion) REFERENCES OcupacionProfesion(ocuId) ;
ALTER TABLE Persona ADD CONSTRAINT FK_Persona_perMedioPago FOREIGN KEY(perMedioPago) REFERENCES ParametrizacionMedioDePago(pmpId) ;

--changeset Heinsohn_Sprint002:08 stripComments:false  

CREATE TABLE EntidadPagadora(
	epaId bigint IDENTITY(1,1) NOT NULL,
	epaAportante bit NULL,
	epaCanalComunicacion varchar(20) NULL,
	epaEmailComunicacion varchar(255) NULL,
	epaEstadoEntidadPagadora varchar(20) NULL,
	epaMedioComunicacion varchar(50) NULL,
	epaNombreContacto varchar(255) NULL,
	epaEmpleador bigint NOT NULL,
    CONSTRAINT EntidadPagadora_epaId PRIMARY KEY (epaId) 
);
GO

ALTER TABLE EntidadPagadora  ADD  CONSTRAINT FK_EntidadPagadora_epaEmpleador FOREIGN KEY(epaEmpleador) REFERENCES Empleador (empId)
GO

--changeset Heinsohn_Sprint002:09 stripComments:false  


CREATE TABLE AFP(
	afpId bigint IDENTITY(1,1) NOT NULL,
	afpNombre varchar(255) NULL,
    CONSTRAINT PK_AFP_afpId PRIMARY KEY (afpId) 
);
GO

--changeset Heinsohn_Sprint002:10 stripComments:false 
 /*
 * <b>Descripción:</b> Entidad que representa los posibles roles de afiliación
 * que puede tener un afiliado
 * <b>Historia de Usuario: </b>121-104
 *
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
	 
 CREATE TABLE RolAfiliado(
	roaId bigint IDENTITY(1,1) NOT NULL,
	roaCargo varchar(200) NULL,
	roaClaseIndependiente varchar(50) NULL,
	roaClaseTrabajador varchar(50) NULL,
	roaEstadoAfiliado varchar(20) NOT NULL,
	roaEstadoEnEntidadPagadora varchar(20) NULL,
	roaFechaIngreso date NULL,
	roaFechaRetiro date NULL,
	roaHorasLaboradasMes smallint NULL,
	roaIdentificadorAnteEntidadPagadora varchar(255) NULL,
	roaPorcentajePagoAportes numeric(19, 2) NULL,
	roaTipoAfiliado varchar(30) NOT NULL,
	roaTipoContrato varchar(10) NULL,
	roaTipoSalario varchar(10) NULL,
	roaValorSalarioMesadaIngresos numeric(19, 2) NULL,
	roaAfiliado bigint NOT NULL,
	roaEmpleador bigint NULL,
	roaPagadorAportes bigint NULL,
	roaPagadorPension bigint NULL,
	roaSucursalEmpleador bigint NULL,
    CONSTRAINT PK_RolAfiliado_roaId PRIMARY KEY (roaId) 
);

GO

ALTER TABLE RolAfiliado  ADD  CONSTRAINT FK_RolAfiliado_roaAfiliado FOREIGN KEY(roaAfiliado)
REFERENCES Afiliado (afiId)
GO


ALTER TABLE RolAfiliado  ADD  CONSTRAINT FK_RolAfiliado_roaEmpleador FOREIGN KEY(roaEmpleador)
REFERENCES Empleador (empId)
GO


ALTER TABLE RolAfiliado  ADD  CONSTRAINT FK_RolAfiliado_roaPagadorAportes FOREIGN KEY(roaPagadorAportes)
REFERENCES EntidadPagadora (epaId)
GO


ALTER TABLE RolAfiliado  ADD  CONSTRAINT FK_RolAfiliado_roaPagadorPension FOREIGN KEY(roaPagadorPension)
REFERENCES AFP (afpId)
GO


ALTER TABLE RolAfiliado  ADD  CONSTRAINT FK_RolAfiliado_roaSucursalEmpleador FOREIGN KEY(roaSucursalEmpleador)
REFERENCES SucursalEmpleador (sueId)
GO

--changeset Heinsohn_Sprint002:11 stripComments:false  


/*
	* <b>Descripción:</b> Entidad que representa una persona afiliada a la caja de
 * compensación
 * <b>Historia de Usuario: </b>121-104
 *
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */


CREATE TABLE Beneficiario(
	benId bigint IDENTITY(1,1) NOT NULL,
	benCertificadoEscolaridad bit NULL,
	benComentariosInvalidez varchar(500) NULL,
	benEstadoBeneficiarioAfiliado varchar(20) NULL,
	benEstadoBeneficiarioCaja varchar(20) NULL,
	benEstudianteTrabajoDesarrolloHumano bit NULL,
	benFechaAfiliacion date NULL,
	benFechaRecepcionCertificadoEscolar date NULL,
	benFechaReporteInvalidez date NULL,
	benFechaVencimientoCertificadoEscolar date NULL,
	benInvalidez bit NULL,
	benLabora bit NULL,
	benTipoBeneficiario varchar(30) NOT NULL,
	benGrupoFamiliar bigint NULL,
	benPersona bigint NOT NULL,
	benRolAfiliado bigint NOT NULL,
     CONSTRAINT PK_Beneficiario_benId PRIMARY KEY (benId) 
);
GO

ALTER TABLE Beneficiario  ADD  CONSTRAINT FK_Beneficiario_benPersona FOREIGN KEY(benPersona)
REFERENCES Persona (perId)
GO

ALTER TABLE Beneficiario  ADD  CONSTRAINT FK_Beneficiario_benGrupoFamiliar FOREIGN KEY(benGrupoFamiliar)
REFERENCES GrupoFamiliar (grfId)
GO

ALTER TABLE Beneficiario  ADD  CONSTRAINT FK_Beneficiario_benRolAfiliado FOREIGN KEY(benRolAfiliado)
REFERENCES RolAfiliado (roaId)
GO


--changeset Heinsohn_Sprint002:12 stripComments:false  

CREATE TABLE ItemChequeo(
	ichId bigint IDENTITY(1,1) NOT NULL,
	ichSolicitud bigint NULL,
	ichRequisito bigint NULL,
	ichPersona bigint NULL,
	ichIdentificadorDocumento varchar(255) NULL,
	ichVersionDocumento smallint NULL,
	ichEstadoRequisito varchar(20) NULL,
	ichPrecargado bit NULL,
	ichCumpleRequisito bit NULL,
	ichFormatoEntregaDocumento varchar(20) NULL,
	ichComentarios varchar(255) NULL,
	ichCumpleRequisitoBack bit NULL,
	ichComentariosBack varchar(255) NULL,
     CONSTRAINT PK_ItemChequeo_ichId PRIMARY KEY (ichId) 
);


ALTER TABLE   ItemChequeo  ADD  CONSTRAINT FK_ItemChequeo_ichSolicitud FOREIGN KEY(ichSolicitud) REFERENCES  solicitud (solId);
GO
ALTER TABLE   ItemChequeo  ADD  CONSTRAINT FK_ItemChequeo_ichRequisito FOREIGN KEY(ichRequisito) REFERENCES  Requisito (reqId);
GO
ALTER TABLE   ItemChequeo  ADD  CONSTRAINT FK_ItemChequeo_ichPersona FOREIGN KEY(ichPersona) REFERENCES  Persona (perId);
GO


--changeset Heinsohn_Sprint002:13 stripComments:false  

ALTER TABLE SolicitudAfiliaciEmpleador DROP CONSTRAINT FK_SolicitudAfiliaciEmpleador_saePaqueteEnvioPack;
ALTER TABLE SolicitudAfiliaciEmpleador DROP COLUMN saePaqueteEnvioPack;
DROP TABLE PaqueteEnvioBack;


--changeset Heinsohn_Sprint002:14 stripComments:false  
ALTER TABLE SolicitudAfiliaciEmpleador DROP COLUMN saeEstadoDocumentacion;
ALTER TABLE SolicitudAfiliaciEmpleador DROP COLUMN saeMetodoEnvio;
ALTER TABLE SolicitudAfiliaciEmpleador DROP COLUMN saeDestinatario;
ALTER TABLE SolicitudAfiliaciEmpleador DROP COLUMN saeFechaCreacion;
ALTER TABLE SolicitudAfiliaciEmpleador DROP COLUMN saeSedeDestinatario;


--changeset Heinsohn_Sprint002:15 stripComments:false  

CREATE TABLE SolicitudAfiliacionPersona(
	sapId bigint IDENTITY(1,1) NOT NULL,
	sapEstadoSolicitud varchar(20) NULL,
	sapRolAfiliado bigint NULL,
	sapSolicitudGlobal bigint NULL,
     CONSTRAINT PK_SolicitudAfiliacionPersona_sapId PRIMARY KEY (sapId) 
);

ALTER TABLE SolicitudAfiliacionPersona ADD CONSTRAINT FK_SolicitudAfiliacionPersona_sapSolicitudGlobal FOREIGN KEY(sapSolicitudGlobal)
REFERENCES Solicitud (solId)
GO

ALTER TABLE SolicitudAfiliacionPersona ADD CONSTRAINT FK_SolicitudAfiliacionPersona_sapRolAfiliado FOREIGN KEY(sapRolAfiliado)
REFERENCES RolAfiliado (roaId)
GO

--changeset Heinsohn_Sprint002:16 stripComments:false  

CREATE TABLE SolicitudAsociacionPersonaEntidadPagadora(
	soaId bigint IDENTITY(1,1) NOT NULL,
	soaConsecutivo bigint NULL,
	soaEstado varchar(50) NULL,
	soaFechaGestion datetime2(7) NULL,
	soaIdentificadorArchivoCarta varchar(255) NULL,
	soaIdentificadorArchivoPlano varchar(255) NULL,
	soaTipoGestion varchar(50) NULL,
	soaRolAfiliado bigint NOT NULL,
	soaSolicitudGlobal bigint NOT NULL,
    CONSTRAINT PK_SolicitudAsociacionPersonaEntidadPagadora_soaId PRIMARY KEY (soaId) 
);
GO


ALTER TABLE SolicitudAsociacionPersonaEntidadPagadora ADD  CONSTRAINT FK_SolicitudAsociacionPersonaEntidadPagadora_soaSolicitudGlobal FOREIGN KEY(soaSolicitudGlobal) REFERENCES Solicitud (solId)
GO

ALTER TABLE SolicitudAsociacionPersonaEntidadPagadora ADD  CONSTRAINT FK_SolicitudAsociacionPersonaEntidadPagadora_soaRolAfiliado FOREIGN KEY(soaRolAfiliado)REFERENCES RolAfiliado (roaId)
GO

--changeset Heinsohn_Sprint002:17 stripComments:false  
ALTER TABLE Comunicado ADD comSolicitud BIGINT;
ALTER TABLE Comunicado ADD CONSTRAINT FK_Comunicado_comSolicitud FOREIGN KEY (comSolicitud) REFERENCES Solicitud (solId)
GO

--changeset Heinsohn_Sprint002:18 stripComments:false  

CREATE TABLE Novedad(
	novId bigint IDENTITY(1,1) NOT NULL,
	novCausal varchar(100) NULL,
    CONSTRAINT PK_Novedad_novId PRIMARY KEY (novId) 
);

CREATE TABLE Tarjeta(
	tarId bigint IDENTITY(1,1) NOT NULL,
	tarEstadoTarjeta varchar(20) NULL,
	tarNumeroTarjeta varchar(20) NULL,
	afiPersona bigint NULL,
    CONSTRAINT PK_Tarjeta_tarId PRIMARY KEY (tarId) 
);

ALTER TABLE Tarjeta ADD  CONSTRAINT FK_Tarjeta_afiPersona FOREIGN KEY(afiPersona)
REFERENCES Afiliado (afiId)
GO

--changeset Heinsohn_Sprint002:19 stripComments:false  


ALTER TABLE Solicitud ADD solEstadoDocumentacion VARCHAR (50);
ALTER TABLE Solicitud ADD solMetodoEnvio VARCHAR (20);
ALTER TABLE Solicitud ADD solClasificacion VARCHAR (20);
ALTER TABLE Solicitud ADD solTipoRadicacion VARCHAR (20);
ALTER TABLE Solicitud ADD solFechaCreacion DATE;
ALTER TABLE Solicitud ADD solDestinatario VARCHAR(255);
ALTER TABLE Solicitud ADD solSedeDestinatario VARCHAR(2);

--changeset Heinsohn_Sprint002:20 stripComments:false  

EXEC sp_RENAME 'ProductoNoConforme.pncSolicitudAfiliacion', 'pncSolicitud', 'COLUMN';
ALTER TABLE ProductoNoConforme DROP CONSTRAINT FK_ProductoNoConforme_pncSolicitudAfiliacion;
ALTER TABLE ProductoNoConforme  ADD  CONSTRAINT FK_ProductoNoConforme_pncSolicitud FOREIGN KEY(pncSolicitud) REFERENCES  solicitud (solId);
ALTER TABLE ProductoNoConforme ADD pncBeneficiario bigint;
ALTER TABLE ProductoNoConforme  ADD  CONSTRAINT FK_ProductoNoConforme_pncBeneficiario FOREIGN KEY(pncBeneficiario) REFERENCES  Beneficiario (benId);

--changeset Heinsohn_Sprint002:21 stripComments:false  
ALTER TABLE Requisito add reqClasificacion varchar(20);

--changeset Heinsohn_Sprint002:22 stripComments:false  

CREATE TABLE DocumentoEntidadPagadora(
	dpgId bigint IDENTITY(1,1) NOT NULL,
	dpgEntidadPagadora bigint NULL,
	dpgIdentificadorDocumento varchar(255) NULL,
	dpgTipoDocumento varchar(50) NULL,
    CONSTRAINT PK_DocumentoEntidadPagadora_dpgId PRIMARY KEY (dpgId) 
);

ALTER TABLE DocumentoEntidadPagadora  ADD  CONSTRAINT FK_DocumentoEntidadPagadora_dpgEntidadPagadora FOREIGN KEY(dpgEntidadPagadora) REFERENCES  EntidadPagadora (epaId);

--changeset Heinsohn_Sprint002:23 stripComments:false 
SET ANSI_PADDING ON    
CREATE UNIQUE  INDEX IDX_Solicitud_solNumeroRadicacion
ON Solicitud (solNumeroRadicacion) WHERE solNumeroRadicacion IS NOT NULL;
SET ANSI_PADDING OFF

--changeset Heinsohn_Sprint002:25 stripComments:false
SET ANSI_PADDING ON 
CREATE UNIQUE NONCLUSTERED INDEX IDX_Persona_perRazonSocial
ON persona (perRazonSocial)
WHERE perRazonSocial IS NOT NULL;
SET ANSI_PADDING OFF

--changeset Heinsohn_Sprint002:26 stripComments:false  
alter table GrupoFamiliar add grfRelacionGrupoFamiliar smallint;