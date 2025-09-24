--liquibase formatted sql

--changeset flopez:01
--comment:Se agregan tablas y campos a tablas relacionados con el proceso FOVIS

--Creacion de la tabla ParametrizacionModalidad_aud
CREATE TABLE ParametrizacionModalidad_aud(
	pmoId BIGINT NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	pmoNombre VARCHAR(50) NULL,
	pmoEstado BIT NULL,
	pmoTopeSMLMV NUMERIC (2,1) NULL,
	pmoTopeAvaluoCatastral NUMERIC (4,1),
);
ALTER TABLE ParametrizacionModalidad_aud ADD CONSTRAINT FK_ParametrizacionModalidad_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla CicloAsignacion_aud
CREATE TABLE CicloAsignacion_aud(
	ciaId BIGINT NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	ciaNombre VARCHAR (50) NULL,
	ciaFechaInicio DATE NULL,
	ciaFechaFin DATE NULL,
	ciaCicloPredecesor BIGINT NOT NULL,
	ciaEstadoCicloAsignacion VARCHAR (30) NULL,
	ciaCicloActivo BIT NULL,
);
ALTER TABLE CicloAsignacion_aud ADD CONSTRAINT FK_CicloAsignacion_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla CicloModalidad_aud
CREATE TABLE CicloModalidad_aud(
	cmoId BIGINT NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	cmoCicloAsignacion BIGINT NOT NULL,
	cmoParametrizacionModalidad BIGINT NOT NULL,
);
ALTER TABLE CicloModalidad_aud ADD CONSTRAINT FK_CicloModalidad_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla CondicionEspecial_aud
CREATE TABLE CondicionEspecial_aud(
	coeId BIGINT NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	coeNombre VARCHAR (32) NOT NULL,
);
ALTER TABLE CondicionEspecial_aud ADD CONSTRAINT FK_CondicionEspecial_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla FormaPagoModalidad_aud
CREATE TABLE FormaPagoModalidad_aud(
	fpmId BIGINT NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	fpmParametrizacionModalidad BIGINT NOT NULL,
	fpmFormaPago VARCHAR (34) NOT NULL,
);
ALTER TABLE FormaPagoModalidad_aud ADD CONSTRAINT FK_FormaPagoModalidad_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla RangoTopeValorSFV_aud
CREATE TABLE RangoTopeValorSFV_aud(
	rtvId BIGINT NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	rtvParametrizacionModalidad BIGINT NOT NULL,
	rtvNombre VARCHAR (50) NOT NULL,
	rtvOperadorValorMinimo VARCHAR (13) NOT NULL,
	rtvValorMinimo NUMERIC (3,1) NOT NULL,
	rtvOperadorValorMaximo VARCHAR (13) NOT NULL,
	rtvValorMaximo NUMERIC (3,1) NOT NULL,
	rtvTopeSMLMV NUMERIC (4,1) NOT NULL,
);
ALTER TABLE RangoTopeValorSFV_aud ADD CONSTRAINT FK_RangoTopeValorSFV_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla ParametrizacionFOVIS_aud
CREATE TABLE ParametrizacionFOVIS_aud(
	pafId BIGINT NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	pafNombre VARCHAR(50) NULL,
	pafValor BIT NULL,
	pafValorNumerico NUMERIC (4,1) NULL,
);
ALTER TABLE ParametrizacionFOVIS_aud ADD CONSTRAINT FK_ParametrizacionFOVIS_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla JefeHogar_aud
CREATE TABLE JefeHogar_aud(
	jehId BIGINT NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	jehAfiliado BIGINT NOT NULL,
);
ALTER TABLE JefeHogar_aud ADD CONSTRAINT FK_JefeHogar_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla IntegranteHogar_aud
CREATE TABLE IntegranteHogar_aud(
	inhId BIGINT NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	inhJefeHogar BIGINT NOT NULL,
	inhRelacionGrupoFamiliar SMALLINT NOT NULL,
	inhPersona BIGINT NOT NULL,
	inhIntegranteReemplazaJefeHogar BIT NULL,
);
ALTER TABLE IntegranteHogar_aud ADD CONSTRAINT FK_IntegranteHogar_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla Oferente_aud
CREATE TABLE Oferente_aud(
	ofeId BIGINT NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	ofePersona BIGINT NOT NULL,
	ofeEmpresa BIGINT NULL,
	ofeMedioDePago BIGINT NULL,
	ofeEstado VARCHAR(30) NULL,
);
ALTER TABLE Oferente_aud ADD CONSTRAINT FK_Oferente_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla ProyectoSolucionVivienda_aud
CREATE TABLE ProyectoSolucionVivienda_aud(
	psvId BIGINT NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	psvNombreProyecto VARCHAR (250) NULL,
	psvMatriculaInmobiliariaInmueble VARCHAR (50) NULL,
	psvLoteUrbanizado BIT NULL,
	psvFechaRegistroEscritura DATE NULL,
	psvNumeroEscritura VARCHAR (20) NULL,
	psvFechaEscritura DATE NULL,
	psvValorProyectoVivienda NUMERIC (19,5) NULL,
	psvAvaluoCatastral BIGINT NULL,
	psvObservaciones VARCHAR (500) NULL,
	psvOferente BIGINT NULL,
	psvParametrizacionModalidad BIGINT NOT NULL,
	psvUbicacionProyecto BIGINT NULL,
	psvMedioDePago BIGINT NULL,
	psvUbicacionIgualProyecto BIT NULL,
	psvUbicacionVivienda BIGINT NULL,
	psvNumeroDocumentoElegibilidad VARCHAR (50) NULL,
	psvCodigoProyectoElegibilidad VARCHAR (50) NULL,
	psvNombreEntidadElegibilidad VARCHAR (50) NULL,
	psvFechaElegibilidad DATE NULL,
	psvNumeroViviendaElegibilidad INT NULL,
	psvTipoInmuebleElegibilidad VARCHAR (50) NULL,
	psvComentariosElegibilidad VARCHAR (500) NULL,
);
ALTER TABLE ProyectoSolucionVivienda_aud ADD CONSTRAINT FK_ProyectoSolucionVivienda_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla Licencia_aud
CREATE TABLE Licencia_aud(
	licId BIGINT NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	licEntidadExpide VARCHAR (21) NULL,
	licNumeroLicencia VARCHAR (50) NULL,
	licMatriculaInmobiliaria VARCHAR (50) NULL,
	licProyectoSolucionVivienda BIGINT NOT NULL,
);
ALTER TABLE Licencia_aud ADD CONSTRAINT FK_Licencia_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla LicenciaDetalle_aud
CREATE TABLE LicenciaDetalle_aud(
	lidId BIGINT NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	lidNumeroResolucion VARCHAR (50) NULL,
	lidFechaInicio DATE NULL,
	lidFechaFin DATE NULL,
	lidLicencia BIGINT NOT NULL,
);
ALTER TABLE LicenciaDetalle_aud ADD CONSTRAINT FK_LicenciaDetalle_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla PostulacionFOVIS_aud
CREATE TABLE PostulacionFOVIS_aud(
	pofId BIGINT NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	pofCicloAsignacion BIGINT NULL,
	pofParametrizacionModalidad BIGINT NULL,
	pofJefeHogar BIGINT NULL,
	pofEstadoHogar VARCHAR (20) NULL,
	pofCondicionHogar VARCHAR (44) NULL, 
	pofHogarPerdioSubsidioNoPago BIT NULL,
	pofCantidadFolios SMALLINT NULL,
	pofValorSFVSolicitado NUMERIC (19,5) NULL,
);
ALTER TABLE PostulacionFOVIS_aud ADD CONSTRAINT FK_PostulacionFOVIS_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla SolicitudPostulacion_aud
CREATE TABLE SolicitudPostulacion_aud(
	spoId BIGINT NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	spoSolicitudGlobal BIGINT NOT NULL,
	spoPostulacionFOVIS BIGINT NOT NULL,
	spoEstadoSolicitud VARCHAR (42) NULL,
);
ALTER TABLE SolicitudPostulacion_aud ADD CONSTRAINT FK_SolicitudPostulacion_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla AhorroPrevio_aud
CREATE TABLE AhorroPrevio_aud(
	ahpId BIGINT NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	ahpNombreAhorro VARCHAR (78) NULL,
	ahpEntidad VARCHAR (50) NULL,
	ahpFechaInicial DATE NULL,
	ahpValor NUMERIC (19,5) NULL,
	ahpFechaInmovilizacion DATE NULL,
	ahpFechaAdquisicion DATE NULL,
	ahpPostulacionFOVIS BIGINT NOT NULL,
);
ALTER TABLE AhorroPrevio_aud ADD CONSTRAINT FK_AhorroPrevio_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla RecursoComplementario_aud
CREATE TABLE RecursoComplementario_aud(
	recId BIGINT NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	recNombre VARCHAR (30) NULL,
	recEntidad VARCHAR (50) NULL,
	recFecha DATE NULL,
	recOtroRecurso VARCHAR (255) NULL,
	recValor NUMERIC (19,5) NULL,
	recPostulacionFOVIS BIGINT NOT NULL,
);
ALTER TABLE RecursoComplementario_aud ADD CONSTRAINT FK_RecursoComplementario_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla IntentoPostulacion_aud
CREATE TABLE IntentoPostulacion_aud(
	ipoId BIGINT NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	ipoCausaIntentoFallido VARCHAR (50) NULL,
	ipoFechaCreacion DATETIME NULL,
	ipoFechaInicioProceso DATETIME NULL,
	ipoSedeCajaCompensacion VARCHAR (2) NULL,
	ipoTipoTransaccion VARCHAR (100) NULL,
	ipoUsuarioCreacion VARCHAR (255) NULL,
	ipoSolicitud BIGINT NULL,
);
ALTER TABLE IntentoPostulacion_aud ADD CONSTRAINT FK_IntentoPostulacion_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla IntentoPostulacionRequisito_aud
CREATE TABLE IntentoPostulacionRequisito_aud(
	iprId BIGINT NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	iprIntentoPostulacion BIGINT  NULL,
	iprRequisito BIGINT NULL,
);
ALTER TABLE IntentoPostulacionRequisito_aud ADD CONSTRAINT FK_IntentoPostulacionRequisito_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Adicion de campo a la tabla PersonaDetalle_aud
ALTER TABLE PersonaDetalle_aud ADD pedBeneficiarioSubsidio bit NULL;
ALTER TABLE PersonaDetalle_aud ADD pedEstadoFovisHogar VARCHAR (10) NULL;

--Adicion de campo a la tabla MediosPagoCCF_aud
ALTER TABLE MediosPagoCCF_aud ADD mpcAplicaFOVIS BIT NULL;

--changeset fvasquez:02
--comment: Se agregan campos a la tabla PostulacionFOVIS
ALTER TABLE PostulacionFOVIS_aud ADD pofProyectoSolucionVivienda BIGINT NULL;

--changeset flopez:03
--comment: Se agregan campos a la tabla IntegranteHogar
ALTER TABLE IntegranteHogar_aud DROP COLUMN inhRelacionGrupoFamiliar;
ALTER TABLE IntegranteHogar_aud ADD inhTipoIntegrante VARCHAR(32) NOT NULL;

--changeset fvasquez:04
--comment: Se agregan campos a la tabla IntegranteHogar_aud y jefeHogar_aud y se elimina campo en la tabla PersonaDetalle_aud
ALTER TABLE IntegranteHogar_aud ADD inhEstadoHogar VARCHAR(10) NULL;
ALTER TABLE JefeHogar_aud ADD jehEstadoHogar VARCHAR(10) NULL;
ALTER TABLE PersonaDetalle_aud DROP COLUMN pedEstadoFovisHogar;

--changeset fvasquez:05
--comment: Se adiciona campo en la tabla PersonaDetalle_aud
ALTER TABLE PersonaDetalle_aud ADD pedEstadoFovisHogar VARCHAR(10) NULL;

--changeset fvasquez:06
--comment: Se elimina campo de la tabla PersonaDetalle_aud
ALTER TABLE PersonaDetalle_aud DROP COLUMN pedEstadoFovisHogar;

--changeset edelgado:07
--comment: Se adiciona campo en la tabla Departamento_aud
ALTER TABLE Departamento_aud ADD depExcepcionAplicaFOVIS BIT NULL;

--changeset alquintero:08
--comment:Se adicionan campos en las tablas SolicitudPostulacion, ProductoNoConforme y EscalamientoSolicitud
ALTER TABLE SolicitudPostulacion_aud ADD spoObservaciones VARCHAR(500);
ALTER TABLE ProductoNoConforme_aud ADD pncClasificacionTipoProducto VARCHAR(15);
ALTER TABLE EscalamientoSolicitud_aud ADD esoTipoAnalistaFOVIS VARCHAR(22);

--changeset alquintero:09
--comment:Se eliminan y adicionan campos en las tablas CicloModalidad,PostulacionFovis,ProyectoSolucionVivienda,FormaPagoModalidad y RangoTopeValorSVF

--Eliminacion de columnas de las tablas CicloModalidad,PostulacionFovis,ProyectoSolucionVivienda,FormaPagoModalidad y RangoTopeValorSVF
ALTER TABLE CicloModalidad_aud DROP COLUMN cmoParametrizacionModalidad;
ALTER TABLE PostulacionFovis_aud DROP COLUMN pofParametrizacionModalidad;
ALTER TABLE ProyectoSolucionVivienda_aud DROP COLUMN psvParametrizacionModalidad;
ALTER TABLE FormaPagoModalidad_aud DROP COLUMN fpmParametrizacionModalidad;
ALTER TABLE RangoTopeValorSFV_aud DROP COLUMN rtvParametrizacionModalidad;

--Adicion de columnas de las tablas CicloModalidad,PostulacionFovis,ProyectoSolucionVivienda,FormaPagoModalidad y RangoTopeValorSVF
ALTER TABLE CicloModalidad_aud ADD cmoModalidad VARCHAR(50) NOT NULL;
ALTER TABLE PostulacionFovis_aud ADD pofModalidad VARCHAR(50) NULL;
ALTER TABLE ProyectoSolucionVivienda_aud ADD psvModalidad VARCHAR(50) NOT NULL;
ALTER TABLE FormaPagoModalidad_aud ADD fpmModalidad VARCHAR(50) NOT NULL;
ALTER TABLE RangoTopeValorSFV_aud ADD rtvModalidad VARCHAR(50) NOT NULL;

--changeset flopez:10
--comment: Se adiciona campo en la tabla CondicionEspecial_aud y se crea la tabla CondicionEspecialPersona
ALTER TABLE CondicionEspecial_aud ADD coeCabezaFamilia BIT NOT NULL;

CREATE TABLE CondicionEspecialPersona_aud(
	cepId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	cepCondicionEspecial BIGINT NOT NULL,
	cepPersona BIGINT NOT NULL,
CONSTRAINT PK_CondicionEspecialPersona_cepId PRIMARY KEY (cepId)			
);
ALTER TABLE CondicionEspecialPersona_aud ADD CONSTRAINT FK_CondicionEspecialPersona_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--changeset anvalbuena:11
--comment:Se adiciona campo en la tabla ResultadoEjecucionProgramada_aud
ALTER TABLE ResultadoEjecucionProgramada_aud ADD repTipoResultadoProceso VARCHAR(10);

--changeset alquintero:12
--comment: Se crean las tablas MedioCheque_aud y MedioConsignacion_aud
CREATE TABLE MedioCheque_aud(
	mdpId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	mecTipoIdentificacionTitular VARCHAR(20) NULL,
	mecNumeroIdentificacionTitular VARCHAR(16) NULL,
	mecDigitoVerificacionTitular SMALLINT NULL,
	mecNombreTitularCuenta VARCHAR(200) NULL,
);
ALTER TABLE MedioCheque_aud ADD CONSTRAINT FK_MedioCheque_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE MedioConsignacion_aud(
	mdpId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	mcoBanco BIGINT NOT NULL,
	mcoTipoCuenta VARCHAR(30) NOT NULL,
	mcoNumeroCuenta VARCHAR(30) NOT NULL,
	mcoTipoIdentificacionTitular VARCHAR(20) NULL,
	mcoNumeroIdentificacionTitular VARCHAR(16) NULL,
	mcoDigitoVerificacionTitular SMALLINT NULL,
	mcoNombreTitularCuenta VARCHAR(200) NULL,
);
ALTER TABLE MedioConsignacion_aud ADD CONSTRAINT FK_MedioConsignacion_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--changeset flopez:13
--comment:Se adiciona campo en la tabla IntegranteHogar_aud
ALTER TABLE IntegranteHogar_aud ADD inhIntegranteValido BIT NULL;

--changeset alquintero:14
--comment:Se adiciona campos en la tabla ParametrizacionFOVIS__aud 
ALTER TABLE ParametrizacionFOVIS_aud ADD pafPlazoVencimiento VARCHAR(15) NULL;
ALTER TABLE ParametrizacionFOVIS_aud ADD pafPlazoAdicional VARCHAR(15) NULL;
ALTER TABLE ParametrizacionFOVIS_aud ADD pafValorAdicional NUMERIC(4,1) NULL;
ALTER TABLE ParametrizacionFOVIS_aud ADD pafValorString VARCHAR(30) NULL;

--changeset alquintero:15
--comment:Se agrega campo en la tabla CicloAsignacion_aud
ALTER TABLE CicloAsignacion_aud ALTER COLUMN ciaCicloPredecesor BIGINT NULL;

--changeset alquintero:16
--comment: Se eliminan registros de la tabla ParametrizacionFOVIS
ALTER TABLE ParametrizacionFOVIS_aud ALTER COLUMN pafNombre VARCHAR(50) NOT NULL;

--changeset alquintero:17
--comment: Se modifica campo en la tabla ParametrizacionModalidad_aud
ALTER TABLE ParametrizacionModalidad_aud ALTER COLUMN pmoTopeSMLMV NUMERIC(3,1) NULL;

--changeset flopez:18
--comment: Se modifica campo en la tabla SolicitudPostulacion
ALTER TABLE SolicitudPostulacion_aud ALTER COLUMN spoPostulacionFOVIS BIGINT NULL;

--changeset alquintero:19
--comment: Se modifica campo en la tabla MedioDePago_aud
 ALTER TABLE MedioDePago_aud ALTER COLUMN mdpTipo VARCHAR(100) NOT NULL;

--changeset flopez:20
--comment: Se modifica campo en la tabla DocumentoAdministracionEstadoSolicitud_aud
 ALTER TABLE DocumentoAdministracionEstadoSolicitud_aud ALTER COLUMN daeTipoDocumentoAdjunto VARCHAR(22);
 
--changeset fvasquez:21
--comment: Se adicionan campos en la tabla IntentoPostulacion
ALTER TABLE IntentoPostulacion_aud ADD ipoProceso VARCHAR(32) NULL;
ALTER TABLE IntentoPostulacion_aud ADD ipoTipoSolicitante VARCHAR(5) NULL;
ALTER TABLE IntentoPostulacion_aud ADD ipoModalidad VARCHAR(33) NULL;

--changeset jocorrea:22
--comment: Creacion de las tablas CargueArchivoCruceFovis_aud, Cruce_aud y CruceDetalle_aud
--Creacion tabla CargueArchivoCruceFovis_aud
CREATE TABLE CargueArchivoCruceFovis_aud(
	cacId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	cacCodigoIdentificadorECM VARCHAR(255) NOT NULL,
	cacNombreArchivo VARCHAR(50) NOT NULL,
	cacFechaCargue DATETIME NOT NULL,
	cacInfoArchivoJsonPayload TEXT NULL,		
);
ALTER TABLE CargueArchivoCruceFovis_aud ADD CONSTRAINT FK_CargueArchivoCruceFovis_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion tabla Cruce_aud
CREATE TABLE Cruce_aud(
	cruId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	cruCargueArchivoCruceFovis BIGINT NOT NULL,
	cruNumeroPostulacion BIGINT NULL,
	cruPersona BIGINT NULL,			
);
ALTER TABLE Cruce_aud ADD CONSTRAINT FK_Cruce_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Creacion de la tabla CruceDetalle_aud
CREATE TABLE CruceDetalle_aud(
	crdId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	crdCruce BIGINT NOT NULL,
	crdEstadoCruce VARCHAR(22) NOT NULL,
	crdCausalCruce VARCHAR(30) NOT NULL,
	crdNitEntidad VARCHAR (16) NULL,
	crdNombreEntidad VARCHAR(30) NULL,
	crdNumeroIdentificacion VARCHAR(16) NULL,
	crdApellidos VARCHAR(100) NULL,
	crdNombres VARCHAR(100) NULL,
	crdCedulaCatastral VARCHAR(50) NULL,
	crdDireccionInmueble VARCHAR(300) NULL,
	crdMatriculaInmobiliaria VARCHAR(50) NULL,
	crdDepartamento VARCHAR(100) NULL,
	crdMunicipio VARCHAR(50) NULL,
	crdFechaActualizacionMinisterio DATE NULL,
	crdFechaCorteEntidad DATE NULL,
	crdApellidosNombres VARCHAR(200) NULL,
	crdPuntaje VARCHAR(10) NULL,
	crdSexo VARCHAR(10) NULL,
	crdZona VARCHAR(30) NULL,
	crdParentesco VARCHAR(30) NULL,
	crdFolio VARCHAR(30) NULL,
	crdTipodocumento VARCHAR(30) NULL,
	crdFechaSolicitud DATE NULL,
	crdEntidadOtorgante VARCHAR(30) NULL,
	crdCajaCompensacion VARCHAR(30) NULL,
	crdAsignadoPosterior VARCHAR(30) NULL,
	crdTipo VARCHAR(15) NOT NULL,		
);
ALTER TABLE CruceDetalle_aud ADD CONSTRAINT FK_CruceDetalle_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--changeset fvasquez:23
--comment: Se adiciona campo en la tabla ProyectoSolucionVivienda
ALTER TABLE ProyectoSolucionVivienda_aud ADD psvPoseedorOcupanteVivienda VARCHAR(50) NULL;

--changeset jocorrea:24
--comment: Se elimina campo en la tabla CruceDetalle_aud, se crea tabla SolicitudGestionCruce_aud, se modifican y adiciona campos en la tabla Cruce_aud
ALTER TABLE CruceDetalle_aud DROP COLUMN crdEstadoCruce;

CREATE TABLE SolicitudGestionCruce_aud(
	sgcId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	sgcSolicitudPostulacion BIGINT NOT NULL,
	sgcEstadoCruceHogar VARCHAR(53) NULL,
);
ALTER TABLE SolicitudGestionCruce_aud ADD CONSTRAINT FK_SolicitudGestionCruce_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

ALTER TABLE Cruce_aud ALTER COLUMN cruCargueArchivoCruceFovis BIGINT NULL;
ALTER TABLE Cruce_aud ADD cruEstadoCruce VARCHAR(22) NOT NULL;
ALTER TABLE Cruce_aud ADD cruSolicitudGestionCruce BIGINT NULL;

--changeset flopez:25
--comment: Se agrega campo en la tabla IntegranteHogar_aud
ALTER TABLE IntegranteHogar_aud ADD inhSalarioMensual NUMERIC (19,5) NULL;

--changeset jocorrea:26
--comment: Se adicionan campos a la tabla Cruce
ALTER TABLE Cruce_aud ADD cruResultadoCodigoIdentificadorECM VARCHAR(255) NULL;
ALTER TABLE Cruce_aud ADD cruObservacionResultado VARCHAR(500) NULL;

--changeset jocorrea:27
--comment: Se modifica tipo y tamaño para campo en la tabla Cruce_aud
ALTER TABLE Cruce_aud ALTER COLUMN cruNumeroPostulacion VARCHAR(12) NULL;