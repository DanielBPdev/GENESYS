--liquibase formatted sql

--changeset mmonroy:01
--comment:Se actualiza registro de la tabla ValidacionProceso
UPDATE ValidacionProceso SET vapBloque = '323-045-1' WHERE vapBloque =  '322-045-1' AND vapProceso = 'POSTULACION_FOVIS_PRESENCIAL'

--changeset jocorrea:02
--comment: Se crea la tabla EjecucionProcesoAsincrono y se adicionan campos en la tabla Cruce y CruceDetalle
CREATE TABLE EjecucionProcesoAsincrono(
	epsId BIGINT NOT NULL IDENTITY(1,1),
	epsFechaInicio DATETIME NOT NULL,
	epsFechaFin DATETIME NULL,
	epsRevisado BIT NOT NULL,
	epsTipoProceso VARCHAR(20) NULL,
CONSTRAINT PK_EjecucionProcesoAsincrono_epsId PRIMARY KEY (epsId)
);

ALTER TABLE Cruce ADD cruEjecucionProcesoAsincrono BIGINT NULL;
ALTER TABLE Cruce ADD CONSTRAINT FK_Cruce_cruEjecucionProcesoAsincrono FOREIGN KEY (cruEjecucionProcesoAsincrono) REFERENCES EjecucionProcesoAsincrono (epsId);

ALTER TABLE CruceDetalle ADD crdResultadoValidacion VARCHAR(255) NULL;
ALTER TABLE CruceDetalle ADD crdClasificacion VARCHAR(30) NULL;

ALTER TABLE SolicitudGestionCruce ADD sgcTipoCruce VARCHAR(8) NULL;

--changeset ecastano:03
--comment: Se crean las tablas SolicitudAsignacion, ActaAsignacionFovis y se adicionan campo en la tabla PostulacionFOVIS
CREATE TABLE SolicitudAsignacion(
	safId BIGINT IDENTITY(1,1) NOT NULL,
	safSolicitudGlobal BIGINT NOT NULL,
	safFechaAceptacion datetime NULL,
	safUsuario VARCHAR (50) NULL,
	safValorSFVAsignado NUMERIC(19,5) NULL,	
	safEstadoSolicitudAsignacion VARCHAR(50) NULL,
	safComentarios VARCHAR(500) NULL,	
	safCicloAsignacion BIGINT NOT NULL,
 CONSTRAINT PK_SolicitudAsignacion_safId PRIMARY KEY (safId)
 );
ALTER TABLE SolicitudAsignacion ADD CONSTRAINT FK_SolicitudAsignacion_safSolicitudGlobal FOREIGN KEY(safSolicitudGlobal) REFERENCES Solicitud (solId);
ALTER TABLE SolicitudAsignacion ADD CONSTRAINT FK_SolicitudAsignacion_safCicloAsignacion FOREIGN KEY(safCicloAsignacion) REFERENCES CicloAsignacion (ciaId);

CREATE TABLE ActaAsignacionFovis(
	aafId BIGINT IDENTITY(1,1) NOT NULL,
	aafSolicitudAsignacion BIGINT NOT NULL,
	aafIdentificadorDocumentoActa VARCHAR (255) NULL,
	aafIdentificadorDocumentoConsolidado VARCHAR (255) NULL,
	aafNumeroResolucion BIGINT NULL,
	aafNumeroOficio BIGINT NULL,
	aafAnoResolucion VARCHAR(4) NULL,
	aafFechaResolucion DATETIME NULL,
	aafFechaOficio DATETIME NULL,
	aafNombreResponsable1 VARCHAR(50) NULL,
	aafCargoResponsable1 VARCHAR(50) NULL,
	aafNombreResponsable2 VARCHAR(50) NULL,
	aafCargoResponsable2 VARCHAR(50) NULL,	
	aafNombreResponsable3 VARCHAR(50) NULL,	
	aafCargoResponsable3 VARCHAR(50) NULL,	
	aafFechaConfirmacion DATETIME NULL,
	aafFechaCorte DATETIME NULL,
	aafInicioVigencia DATETIME NULL,
	aafFinVigencia DATETIME NULL,	
 CONSTRAINT PK_ActaAsignacionFovis_aafId PRIMARY KEY (aafId) 
);
ALTER TABLE ActaAsignacionFovis ADD CONSTRAINT FK_ActaAsignacionFovis_aafSolicitudAsignacion FOREIGN KEY(aafSolicitudAsignacion) REFERENCES SolicitudAsignacion (safId);

ALTER TABLE PostulacionFOVIS ADD pofSolicitudAsignacion BIGINT NULL;
ALTER TABLE PostulacionFOVIS ADD pofResultadoAsignacion VARCHAR(50) NULL;
ALTER TABLE PostulacionFOVIS ADD pofValorAsignadoSFV NUMERIC(19,5) NULL;
ALTER TABLE PostulacionFOVIS ADD pofIdentificardorDocumentoActaAsignacion VARCHAR(255) NULL;
ALTER TABLE PostulacionFOVIS ADD pofPuntaje NUMERIC(5,2) NULL;
ALTER TABLE PostulacionFOVIS ADD CONSTRAINT FK_PostulacionFOVIS_pofSolicitudAsignacion FOREIGN KEY(pofSolicitudAsignacion) REFERENCES SolicitudAsignacion(safId);

--changeset mamonroy:04
--comment:Se actualizan registros en la tabla ValidacionProceso
UPDATE ValidacionProceso SET vapProceso = 'POSTULACION_FOVIS_WEB' where vapBloque = '322-039-1' AND vapProceso = 'POSTULACION_FOVIS_PRESENCIAL';
UPDATE ValidacionProceso SET vapProceso = 'POSTULACION_FOVIS_WEB' where vapBloque = '322-039-2' AND vapProceso = 'POSTULACION_FOVIS_PRESENCIAL';
UPDATE ValidacionProceso SET vapProceso = 'POSTULACION_FOVIS_WEB' where vapBloque = '322-041-1' AND vapProceso = 'POSTULACION_FOVIS_PRESENCIAL';
UPDATE ValidacionProceso SET vapProceso = 'POSTULACION_FOVIS_WEB' where vapBloque = '322-041-2' AND vapProceso = 'POSTULACION_FOVIS_PRESENCIAL';
UPDATE ValidacionProceso SET vapProceso = 'POSTULACION_FOVIS_WEB' where vapBloque = '322-044-1' AND vapProceso = 'POSTULACION_FOVIS_PRESENCIAL';

--changeset flopez:05
--comment:Se actualizan registros en la tabla SolicitudPostulacion
UPDATE SolicitudPostulacion SET spoEstadoSolicitud = 'POSTULACION_RADICADA' WHERE spoEstadoSolicitud = 'RADICADA';
UPDATE SolicitudPostulacion SET spoEstadoSolicitud = 'POSTULACION_RECHAZADA' WHERE spoEstadoSolicitud = 'RECHAZADA';
UPDATE SolicitudPostulacion SET spoEstadoSolicitud = 'POSTULACION_CERRADA' WHERE spoEstadoSolicitud = 'CERRADA';

--changeset jocorrea:06
--comment:Se crean tablas relacionadas con CargueArchivoCruceFovis
CREATE TABLE CargueArchivoCruceFovisCedula(
	cfcId BIGINT NOT NULL IDENTITY(1,1),
	cfcCargueArchivoCruceFovis BIGINT NOT NULL,
	cfcNroCedula VARCHAR(16)  NULL,
CONSTRAINT PK_CargueArchivoCruceFovisCedula_cfcId PRIMARY KEY (cfcId)
);
ALTER TABLE CargueArchivoCruceFovisCedula ADD CONSTRAINT FK_CargueArchivoCruceFovisCedula_cfcCargueArchivoCruceFovis FOREIGN KEY (cfcCargueArchivoCruceFovis) REFERENCES CargueArchivoCruceFovis(cacId);

CREATE TABLE CargueArchivoCruceFovisAfiliado(
	cfaId BIGINT NOT NULL IDENTITY(1,1),
	cfaCargueArchivoCruceFovis BIGINT NOT NULL,
	cfaNitEntidad VARCHAR(16) NULL,
	cfaNombreEntidad VARCHAR(30) NULL,
	cfaIdentificacion VARCHAR(16) NULL,
	cfaApellidos VARCHAR(100) NULL,
	cfaNombres VARCHAR(100) NULL,
CONSTRAINT PK_CargueArchivoCruceFovisAfiliado_cfaId PRIMARY KEY (cfaId)
);
ALTER TABLE CargueArchivoCruceFovisAfiliado ADD CONSTRAINT FK_CargueArchivoCruceFovisAfiliado_cfaCargueArchivoCruceFovis FOREIGN KEY (cfaCargueArchivoCruceFovis) REFERENCES CargueArchivoCruceFovis(cacId);

CREATE TABLE CargueArchivoCruceFovisBeneficiario(
	cfbId BIGINT NOT NULL IDENTITY(1,1),
	cfbCargueArchivoCruceFovis BIGINT NOT NULL,
	cfbNitEntidad VARCHAR(16) NULL,
	cfbNombreEntidad VARCHAR(30) NULL,
	cfbIdentificacion VARCHAR(16) NULL,
	cfbApellidos VARCHAR(100) NULL,
	cfbNombres VARCHAR(100) NULL,
CONSTRAINT PK_CargueArchivoCruceFovisBeneficiario_cfbId PRIMARY KEY (cfbId)
);
ALTER TABLE CargueArchivoCruceFovisBeneficiario ADD CONSTRAINT FK_CargueArchivoCruceFovisBeneficiario_cfbCargueArchivoCruceFovis FOREIGN KEY (cfbCargueArchivoCruceFovis) REFERENCES CargueArchivoCruceFovis(cacId);

CREATE TABLE CargueArchivoCruceFovisCatAnt(
	cfnId BIGINT NOT NULL IDENTITY(1,1),
	cfnCargueArchivoCruceFovis BIGINT NOT NULL,
	cfnNitEntidad VARCHAR(16) NULL,
	cfnNombreEntidad VARCHAR(30) NULL,
	cfnIdentificacion VARCHAR(16) NULL,
	cfnApellidos VARCHAR(100) NULL,
	cfnNombres VARCHAR(100) NULL,
	cfnCedulaCatastral VARCHAR(50) NULL,
	cfnDireccionInmueble VARCHAR(300) NULL,
	cfnMatriculaInmobiliaria VARCHAR(50) NULL,
	cfnDepartamento VARCHAR(100) NULL,
	cfnMunicipio VARCHAR(100) NULL,
CONSTRAINT PK_CargueArchivoCruceFovisCatAnt_cfnId PRIMARY KEY (cfnId)
);
ALTER TABLE CargueArchivoCruceFovisCatAnt ADD CONSTRAINT FK_CargueArchivoCruceFovisCatAnt_cfnCargueArchivoCruceFovis FOREIGN KEY (cfnCargueArchivoCruceFovis) REFERENCES CargueArchivoCruceFovis(cacId);

CREATE TABLE CargueArchivoCruceFovisCatBog(
	cfoId BIGINT NOT NULL IDENTITY(1,1),
	cfoCargueArchivoCruceFovis BIGINT NOT NULL,
	cfoNitEntidad VARCHAR(16) NULL,
	cfoNombreEntidad VARCHAR(30) NULL,
	cfoIdentificacion VARCHAR(16) NULL,
	cfoApellidos VARCHAR(100) NULL,
	cfoNombres VARCHAR(100) NULL,
	cfoCedulaCatastral VARCHAR(50) NULL,
	cfoDireccion VARCHAR(300) NULL,
	cfoMatriculaInmobiliaria VARCHAR(50) NULL,
	cfoDepartamento VARCHAR(100) NULL,
	cfoMunicipio VARCHAR(100) NULL,
CONSTRAINT PK_CargueArchivoCruceFovisBeneficiario_cfoId PRIMARY KEY (cfoId)
);
ALTER TABLE CargueArchivoCruceFovisCatBog ADD CONSTRAINT FK_CargueArchivoCruceFovisCatBog_cfoCargueArchivoCruceFovis FOREIGN KEY (cfoCargueArchivoCruceFovis) REFERENCES CargueArchivoCruceFovis(cacId);

CREATE TABLE CargueArchivoCruceFovisCatCali(
	cflId BIGINT NOT NULL IDENTITY(1,1),
	cflCargueArchivoCruceFovis BIGINT NOT NULL,
	cflNitEntidad VARCHAR(16) NULL,
	cflNombreEntidad VARCHAR(30) NULL,
	cflIdentificacion VARCHAR(16) NULL,
	cflApellidosNombres VARCHAR(200) NULL,
	cflMatriculaInmobiliaria VARCHAR(50) NULL,
	cflDepartamento VARCHAR(100) NULL,
	cflMunicipio VARCHAR(100) NULL,
CONSTRAINT PK_CargueArchivoCruceFovisCatCali_cflId PRIMARY KEY (cflId)
);
ALTER TABLE CargueArchivoCruceFovisCatCali ADD CONSTRAINT FK_CargueArchivoCruceFovisCatCali_cflCargueArchivoCruceFovis FOREIGN KEY (cflCargueArchivoCruceFovis) REFERENCES CargueArchivoCruceFovis(cacId);

CREATE TABLE CargueArchivoCruceFovisCatMed(
	cfmId BIGINT NOT NULL IDENTITY(1,1),
	cfmCargueArchivoCruceFovis BIGINT NOT NULL,
	cfmNombreEntidad VARCHAR(30) NULL,
	cfmIdentificacion VARCHAR(16) NULL,
	cfmApellidosNombres VARCHAR(200) NULL,
	cfmDireccion VARCHAR(300) NULL,
	cfmDepartamento VARCHAR(100) NULL,
	cfmMunicipio VARCHAR(100) NULL,
	cfmMatriculaInmobiliaria VARCHAR(50) NULL,
	cfmCedulaCatastral VARCHAR(50) NULL,
CONSTRAINT PK_CargueArchivoCruceFovisCatMed_cfmId PRIMARY KEY (cfmId)
);
ALTER TABLE CargueArchivoCruceFovisCatMed ADD CONSTRAINT FK_CargueArchivoCruceFovisCatMed_cfmCargueArchivoCruceFovis FOREIGN KEY (cfmCargueArchivoCruceFovis) REFERENCES CargueArchivoCruceFovis(cacId);

CREATE TABLE CargueArchivoCruceFovisIGAC(
	cfgId BIGINT NOT NULL IDENTITY(1,1),
	cfgCargueArchivoCruceFovis BIGINT NOT NULL,
	cfgNitEntidad VARCHAR(16) NULL,
	cfgNombreEntidad VARCHAR(30) NULL,
	cfgIdentificacion VARCHAR(16) NULL,
	cfgApellidosNombres VARCHAR(200) NULL,
	cfgCedulaCatastral VARCHAR(50) NULL,
	cfgDireccion VARCHAR(300) NULL,
	cfgMatriculaInmobiliaria VARCHAR(50) NULL,
	cfgDepartamento VARCHAR(100) NULL,
	cfgMunicipio VARCHAR(100) NULL,
CONSTRAINT PK_CargueArchivoCruceFovisIGAC_cfgId PRIMARY KEY (cfgId)
);
ALTER TABLE CargueArchivoCruceFovisIGAC ADD CONSTRAINT FK_CargueArchivoCruceFovisIGAC_cfgCargueArchivoCruceFovis FOREIGN KEY (cfgCargueArchivoCruceFovis) REFERENCES CargueArchivoCruceFovis(cacId);

CREATE TABLE CargueArchivoCruceFovisNuevoHogar(
	cfhId BIGINT NOT NULL IDENTITY(1,1),
	cfhCargueArchivoCruceFovis BIGINT NOT NULL,
	cfhIdentificacion VARCHAR(16) NULL,
	cfhApellidosNombres VARCHAR(200) NULL,
	cfhFechaSolicitud DATE NULL,
	cfhEntidadOrtogante VARCHAR(30) NULL,
	cfhCajaCompensacion VARCHAR(30) NULL,
	cfhAsignadoPosteriorReporte VARCHAR(30) NULL,
CONSTRAINT PK_CargueArchivoCruceFovisIGAC_cfhId PRIMARY KEY (cfhId)
);
ALTER TABLE CargueArchivoCruceFovisNuevoHogar ADD CONSTRAINT FK_CargueArchivoCruceFovisNuevoHogar_cfhCargueArchivoCruceFovis FOREIGN KEY (cfhCargueArchivoCruceFovis) REFERENCES CargueArchivoCruceFovis(cacId);

CREATE TABLE CargueArchivoCruceFovisFechasCorte(
	cffId BIGINT NOT NULL IDENTITY(1,1),
	cffCargueArchivoCruceFovis BIGINT NOT NULL,
	cffNitEntidad VARCHAR(16) NULL,
	cffNombreEntidad VARCHAR(30) NULL,
	cffTipoInformacion VARCHAR(20) NULL,
	cffFechaCorte DATE NULL,
	cffFechaActualizacion DATE  NULL,
CONSTRAINT PK_CargueArchivoCruceFovisFechasCorte_cffId PRIMARY KEY (cffId)
);
ALTER TABLE CargueArchivoCruceFovisFechasCorte ADD CONSTRAINT FK_CargueArchivoCruceFovisFechasCorte_cffCargueArchivoCruceFovis FOREIGN KEY (cffCargueArchivoCruceFovis) REFERENCES CargueArchivoCruceFovis(cacId);

CREATE TABLE CargueArchivoCruceFovisSisben(
	cfsId BIGINT NOT NULL IDENTITY(1,1),
	cfsCargueArchivoCruceFovis BIGINT NOT NULL,
	cfsIdentificacion VARCHAR(16) NULL,
	cfsApellidosNombres VARCHAR(200) NULL,
	cfsPuntaje VARCHAR(10) NULL,
	cfsSexo VARCHAR(10) NULL,
	cfsZona VARCHAR(30) NULL,
	cfsParantesco VARCHAR(30) NULL,
CONSTRAINT PK_CargueArchivoCruceFovisSisben_cfsId PRIMARY KEY (cfsId)
);
ALTER TABLE CargueArchivoCruceFovisSisben ADD CONSTRAINT FK_CargueArchivoCruceFovisSisben_cfsCargueArchivoCruceFovis FOREIGN KEY (cfsCargueArchivoCruceFovis) REFERENCES CargueArchivoCruceFovis(cacId);

CREATE TABLE CargueArchivoCruceFovisUnidos(
	cfuId BIGINT NOT NULL IDENTITY(1,1),
	cfuCargueArchivoCruceFovis BIGINT NOT NULL,
	cfuIdentificacion VARCHAR(16) NULL,
	cfuApellidosNombres VARCHAR(200) NULL,
	cfuFolio VARCHAR(30) NULL,
	cfuSexo VARCHAR(10) NULL,
	cfuParantesco VARCHAR(30) NULL,
	cfuDepartamento VARCHAR(100) NULL,
	cfuMunicipio VARCHAR(50) NULL,
CONSTRAINT PK_CargueArchivoCruceFovisUnidos_cfuId PRIMARY KEY (cfuId)
);
ALTER TABLE CargueArchivoCruceFovisUnidos ADD CONSTRAINT FK_CargueArchivoCruceFovisUnidos_cfuCargueArchivoCruceFovis FOREIGN KEY (cfuCargueArchivoCruceFovis) REFERENCES CargueArchivoCruceFovis(cacId);

CREATE TABLE CargueArchivoCruceFovisReunidos(
	cfrId BIGINT NOT NULL IDENTITY(1,1),
	cfrCargueArchivoCruceFovis BIGINT NOT NULL,
	cfrDocumento VARCHAR(16) NULL,
	cfrTipoDocumento VARCHAR(30) NULL,
	cfrApellidosNombres VARCHAR(200) NULL,
	cfrMunicipio VARCHAR(10) NULL,
	cfrDepartamento VARCHAR(100) NULL,
CONSTRAINT PK_CargueArchivoCruceFovisReunidos_cfrId PRIMARY KEY (cfrId)
);
ALTER TABLE CargueArchivoCruceFovisReunidos ADD CONSTRAINT FK_CargueArchivoCruceFovisReunidos_cfrCargueArchivoCruceFovis FOREIGN KEY (cfrCargueArchivoCruceFovis) REFERENCES CargueArchivoCruceFovis(cacId);

--changeset fvasquez:07
--comment:Se adiciona campo en la tabla CondicionEspecialPersona
ALTER TABLE CondicionEspecialPersona ADD cepNombreCondicion VARCHAR(28) NULL;

--changeset fvasquez:08
--comment:Se actualizan registros en la tabla CondicionEspecialPersona
UPDATE CondicionEspecialPersona SET CondicionEspecialPersona.cepNombreCondicion = 'CONDICION_INVALIDEZ' FROM CondicionEspecialPersona cep, CondicionEspecial coe WHERE cep.cepCondicionEspEcial=coe.coeID AND coe.CoeNombre = 'Condición invalidez';
UPDATE CondicionEspecialPersona SET CondicionEspecialPersona.cepNombreCondicion = 'DAMNIFICADO_DESASTRE_NATURAL' FROM CondicionEspecialPersona cep, CondicionEspecial coe WHERE cep.cepCondicionEspecial=coe.coeID AND coe.CoeNombre = 'Damnificado desastre natural';
UPDATE CondicionEspecialPersona SET CondicionEspecialPersona.cepNombreCondicion = 'INDIGENA' FROM CondicionEspecialPersona cep, CondicionEspecial coe WHERE cep.cepCondicionEspecial=coe.coeID AND coe.CoeNombre = 'Indígena';
UPDATE CondicionEspecialPersona SET CondicionEspecialPersona.cepNombreCondicion = 'MADRE_COMUNITARIA_ICBF' FROM CondicionEspecialPersona cep, CondicionEspecial coe WHERE cep.cepCondicionEspecial=coe.coeID AND coe.CoeNombre = 'Madre comunitaria ICBF';
UPDATE CondicionEspecialPersona SET CondicionEspecialPersona.cepNombreCondicion = 'MIEMBRO_HOGAR_AFROCOLOMBIANO' FROM CondicionEspecialPersona cep, CondicionEspecial coe WHERE cep.cepCondicionEspecial=coe.coeID AND coe.CoeNombre = 'Miembro de Hogar afrocolombiano';
UPDATE CondicionEspecialPersona SET CondicionEspecialPersona.cepNombreCondicion = 'MUJER_HOMBRE_CABEZA_FAMILIA' FROM CondicionEspecialPersona cep, CondicionEspecial coe WHERE cep.cepCondicionEspecial=coe.coeID AND coe.CoeNombre = 'Mujer/Hombre Cabeza de Familia';

--changeset fvasquez:09
--comment:Se elimina campo en la tabla CondicionEspecialPersona y se elimina la tabla CondicionEspecial
ALTER TABLE CondicionEspecialPersona ALTER COLUMN cepNombreCondicion VARCHAR(28) NOT NULL;
ALTER TABLE CondicionEspecialPersona DROP CONSTRAINT FK_CondicionEspecialPersona_cepCondicionEspecial;
ALTER TABLE CondicionEspecialPersona DROP COLUMN cepCondicionEspecial;
DROP TABLE CondicionEspecial;

--changeset ecastano:10
--comment:Se adiciona campo en la tabla CicloAsignacion
ALTER TABLE CicloAsignacion ADD ciaValorDisponible NUMERIC(19,5) NULL;

--changeset jocorrea:11
--comment:Se adiciona campo en la tabla Cruce y se modifican campos en la tabla CruceDetalle
ALTER TABLE Cruce ADD cruFechaRegistro DATETIME NOT NULL;
ALTER TABLE CruceDetalle ALTER COLUMN crdCausalCruce VARCHAR (30) NULL;
ALTER TABLE CruceDetalle ALTER COLUMN crdTipo VARCHAR (15) NULL;

--changeset jocorrea:12
--comment:Se modifica campo en la tabla CruceDetalle
ALTER TABLE CruceDetalle ALTER COLUMN crdSexo VARCHAR(20) NULL;

--changeset flopez:13
--comment:Se modifican campos en las tablas RecursoComplementario y AhorroPrevio
ALTER TABLE RecursoComplementario ALTER COLUMN recNombre VARCHAR(26) NULL;
ALTER TABLE AhorroPrevio ALTER COLUMN ahpNombreAhorro VARCHAR(65) NULL;

--changeset mamonroy:14
--comment:Insercion de registros de la tabla ValidacionProceso
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-1','VALIDACION_EXISTENCIA_PERSONA','POSTULACION_FOVIS_WEB','ACTIVO',1,'JEFE_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-1','VALIDACION_AFILIADO_ACTIVO','POSTULACION_FOVIS_WEB','ACTIVO',1,'JEFE_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-1','VALIDACION_PERSONA_FALLECIDA','POSTULACION_FOVIS_WEB','ACTIVO',1,'JEFE_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-1','VALIDACION_JEFE_HOGAR_DEPENDIENTE_AL_DIA_APORTES','POSTULACION_FOVIS_WEB','ACTIVO',1,'JEFE_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-1','VALIDACION_PERSONA_HOGAR_HA_SIDO_BENEFICIARIA','POSTULACION_FOVIS_WEB','ACTIVO',1,'JEFE_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-1','VALIDACION_PERSONA_MENOR_16','POSTULACION_FOVIS_WEB','ACTIVO',1,'JEFE_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-1','VALIDACION_JEFE_HOGAR_DEPENDIENTE_BENEFICIO_1429','POSTULACION_FOVIS_WEB','ACTIVO',1,'JEFE_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-1','VALIDACION_PERSONA_ACTIVA_SUBSIDIO_OTRO_GRUPO_FAMILIAR','POSTULACION_FOVIS_WEB','ACTIVO',1,'JEFE_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-1','VALIDACION_FECHA_NACIMIENTO_MAYOR_FECHA_POSTULACION','POSTULACION_FOVIS_WEB','ACTIVO',1,'JEFE_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-1','VALIDACION_NO_HABILITADO_SUBSIDIO','POSTULACION_FOVIS_WEB','ACTIVO',1,'JEFE_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-1','VALIDACION_PERSONA_NO_TIPO_NUMERO_DOCUMENTO','POSTULACION_FOVIS_WEB','ACTIVO',1,'JEFE_HOGAR',0);

--changeset mamonroy:15
--comment:Se actualizan registros en la tabla RequisitoCajaClasificacion
UPDATE RequisitoCajaClasificacion SET rtsTextoAyuda = NULL WHERE rtsTextoAyuda = 'N/A';

--changeset flopez:16
--comment:Se adiciona campo en la tabla CondicionEspecialPersona
ALTER TABLE CondicionEspecialPersona ADD cepActiva BIT NULL;

--changeset mamonroy:17
--comment:Insercion de registros en la tabla PlantillaComunicado
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Notificación de intento de postulación','Cuerpo','Encabezado','Mensaje','Notificación de intento de postulación','Pie','NTF_INT_POS_FOVIS');
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Notificación de radicación  solicitud postulación FOVIS presencial','Cuerpo','Encabezado','Mensaje','Notificación de radicación  solicitud postulación FOVIS presencial','Pie','NTF_RAD_POS_FOVIS_PRE');
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Rechazo de solicitud de postulación FOVIS por escalamiento','Cuerpo','Encabezado','Mensaje','Rechazo de solicitud de postulación FOVIS por escalamiento','Pie','RCHZ_SOL_POS_FOVIS_ESCA');
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Notificación de radicación de solicitud de legalización y desembolso','Cuerpo','Encabezado','Mensaje','Notificación de radicación de solicitud de legalización y desembolso','Pie','NTF_RAD_SOL_LEG_DES');
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Notificación de intento de legalizacion y desembolso','Cuerpo','Encabezado','Mensaje','Notificación de intento de legalizacion y desembolso','Pie','NTF_INT_LEG_DES');
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Rechazo de solicitud de postulación FOVIS','Cuerpo','Encabezado','Mensaje','Rechazo de solicitud de postulación FOVIS','Pie','RCHZ_SOL_POS_FOVIS');
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Rechazo de solicitud de legalización y desembolso FOVIS por subsanación no exitosa','Cuerpo','Encabezado','Mensaje','Rechazo de solicitud de legalización y desembolso FOVIS por subsanación no exitosa','Pie','RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT');
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Desembolso FOVIS autorizado','Cuerpo','Encabezado','Mensaje','Desembolso FOVIS autorizado','Pie','DES_FOVIS_AUTO');
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Notificación de desembolso FOVIS exitoso','Cuerpo','Encabezado','Mensaje','Notificación de desembolso FOVIS exitoso','Pie','NTF_DES_FOVIS_EXT');
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Desembolso FOVIS no autorizado','Cuerpo','Encabezado','Mensaje','Desembolso FOVIS no autorizado','Pie','DES_FOVIS_NAUTO');
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Notificación de subsanación de postulación FOVIS exitosa','Cuerpo','Encabezado','Mensaje','Notificación de subsanación de postulación FOVIS exitosa','Pie','NTF_SBC_POS_FOVIS_EXT');
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Notificaciónde radicación de solicitud de postulación  FOVIS web','Cuerpo','Encabezado','Mensaje','Notificaciónde radicación de solicitud de postulación  FOVIS web','Pie','NTF_RAD_POS_FOVIS_WEB');
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Rechazo de solicitud de legalización y desembolso FOVIS','Cuerpo','Encabezado','Mensaje','Rechazo de solicitud de legalización y desembolso FOVIS','Pie','RCHZ_SOL_LEG_DES_FOVIS');
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Rechazo de solicitud de legalización y desembolso FOVIS por escalamiento','Cuerpo','Encabezado','Mensaje','Rechazo de solicitud de legalización y desembolso FOVIS por escalamiento','Pie','RCHZ_SOL_LEG_DES_FOVIS_ESCA');
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Notificación de validaciones no exitosas para radicar postulación FOVIS web','Cuerpo','Encabezado','Mensaje','Notificación de validaciones no exitosas para radicar postulación FOVIS web','Pie','NTF_VAL_NEXT_RAD_POS_FOVIS_WEB');
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Notificación de radicación de solicitud de novedad FOVIS','Cuerpo','Encabezado','Mensaje','Notificación de radicación de solicitud de novedad FOVIS','Pie','NTF_RAD_SOL_NVD_FOVIS');
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Notificación de resultados de solicitud de novedad FOVIS','Cuerpo','Encabezado','Mensaje','Notificación de resultados de solicitud de novedad FOVIS','Pie','NTF_SOL_NVD_FOVIS');
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Rechazo de solicitud de novedad FOVIS por escalamiento','Cuerpo','Encabezado','Mensaje','Rechazo de solicitud de novedad FOVIS por escalamiento','Pie','RCHZ_SOL_NVD_FOVIS_ESCA');
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Rechazo de solicitud de novedad FOVIS por producto no conforme no subsanable','Cuerpo','Encabezado','Mensaje','Rechazo de solicitud de novedad FOVIS por producto no conforme no subsanable','Pie','RCHZ_SOL_NVD_FOVIS_PROD_NSUB');

--changeset mamonroy:18
--comment:Insercion de registros en la tabla VariableComunicado - Variables
--NTF_INT_POS_FOVIS
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadSolicitud}','0','Ciudad solicitud','Ciudad de la sede donde se realiza la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_POS_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','0','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_POS_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionResidencia}','0','Dirección residencia','Dirección del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_POS_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${municipio}','0','Municipio','Municipio del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_POS_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamento}','0','Departamento','Departamento del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_POS_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefono}','0','Teléfono','Telefono del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_POS_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaRadicacionSolicitud}','0','Fecha radicacion solicitud','Fecha en que se radica la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_POS_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroSolicitud}','0','Numero solicitud','Numerro de identificacion  de la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_POS_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${modalidad}','0','Modalidad','Modalidad  de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_POS_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cicloAsignacion}','0','Ciclo asignación','Ciclo de asignación de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_POS_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacion}','0','Tipo Identificación','Tipo identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_POS_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdentificacion}','0','Número Identificación','No. identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_POS_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombresYApellidosDelJefeDelHogar}','0','Nombres y Apellidos del jefe del hogar','Nombre completo Jefe de hogar (Concatenado)','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_POS_FOVIS'));
--NTF_RAD_POS_FOVIS_PRE
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadSolicitud}','0','Ciudad solicitud','Ciudad de la sede donde se realiza la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','0','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionResidencia}','0','Dirección residencia','Dirección del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${municipio}','0','Municipio','Municipio del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamento}','0','Departamento','Departamento del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefono}','0','Teléfono','Telefono del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaRadicacionSolicitud}','0','Fecha radicacion solicitud','Fecha en que se radica la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroSolicitud}','0','Numero solicitud','Numerro de identificacion  de la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${modalidad}','0','Modalidad','Modalidad  de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cicloAsignacion}','0','Ciclo asignación','Ciclo de asignación de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacion}','0','Tipo Identificación','Tipo identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdentificacion}','0','Número Identificación','No. identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombresYApellidosDelJefeDelHogar}','0','Nombres y Apellidos del jefe del hogar','Nombre completo Jefe de hogar (Concatenado)','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE'));
--RCHZ_SOL_POS_FOVIS_ESCA
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadSolicitud}','0','Ciudad solicitud','Ciudad de la sede donde se realiza la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','0','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionResidencia}','0','Dirección residencia','Dirección del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${municipio}','0','Municipio','Municipio del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamento}','0','Departamento','Departamento del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefono}','0','Teléfono','Telefono del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaRadicacionSolicitud}','0','Fecha radicacion solicitud','Fecha en que se radica la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroSolicitud}','0','Numero solicitud','Numerro de identificacion  de la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${modalidad}','0','Modalidad','Modalidad  de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cicloAsignacion}','0','Ciclo asignación','Ciclo de asignación de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacion}','0','Tipo Identificación','Tipo identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdentificacion}','0','Número Identificación','No. identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombresYApellidosDelJefeDelHogar}','0','Nombres y Apellidos del jefe del hogar','Nombre completo Jefe de hogar (Concatenado)','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA'));
--NTF_RAD_SOL_LEG_DES
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadSolicitud}','0','Ciudad solicitud','Ciudad de la sede donde se realiza la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','0','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionResidencia}','0','Dirección residencia','Dirección del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${municipio}','0','Municipio','Municipio del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamento}','0','Departamento','Departamento del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefono}','0','Teléfono','Telefono del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaRadicacionSolicitud}','0','Fecha radicacion solicitud','Fecha en que se radica la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroSolicitud}','0','Numero solicitud','Numerro de identificacion  de la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${modalidad}','0','Modalidad','Modalidad  de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cicloAsignacion}','0','Ciclo asignación','Ciclo de asignación de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacion}','0','Tipo Identificación','Tipo identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdentificacion}','0','Número Identificación','No. identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombresYApellidosDelJefeDelHogar}','0','Nombres y Apellidos del jefe del hogar','Nombre completo Jefe de hogar (Concatenado)','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES'));
--NTF_INT_LEG_DES
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadSolicitud}','0','Ciudad solicitud','Ciudad de la sede donde se realiza la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_LEG_DES'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','0','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_LEG_DES'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionResidencia}','0','Dirección residencia','Dirección del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_LEG_DES'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${municipio}','0','Municipio','Municipio del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_LEG_DES'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamento}','0','Departamento','Departamento del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_LEG_DES'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefono}','0','Teléfono','Telefono del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_LEG_DES'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaRadicacionSolicitud}','0','Fecha radicacion solicitud','Fecha en que se radica la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_LEG_DES'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroSolicitud}','0','Numero solicitud','Numerro de identificacion  de la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_LEG_DES'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${modalidad}','0','Modalidad','Modalidad  de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_LEG_DES'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cicloAsignacion}','0','Ciclo asignación','Ciclo de asignación de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_LEG_DES'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacion}','0','Tipo Identificación','Tipo identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_LEG_DES'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdentificacion}','0','Número Identificación','No. identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_LEG_DES'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombresYApellidosDelJefeDelHogar}','0','Nombres y Apellidos del jefe del hogar','Nombre completo Jefe de hogar (Concatenado)','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_LEG_DES'));
--RCHZ_SOL_POS_FOVIS
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadSolicitud}','0','Ciudad solicitud','Ciudad de la sede donde se realiza la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','0','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionResidencia}','0','Dirección residencia','Dirección del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${municipio}','0','Municipio','Municipio del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamento}','0','Departamento','Departamento del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefono}','0','Teléfono','Telefono del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaRadicacionSolicitud}','0','Fecha radicacion solicitud','Fecha en que se radica la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroSolicitud}','0','Numero solicitud','Numerro de identificacion  de la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${modalidad}','0','Modalidad','Modalidad  de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cicloAsignacion}','0','Ciclo asignación','Ciclo de asignación de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacion}','0','Tipo Identificación','Tipo identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdentificacion}','0','Número Identificación','No. identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombresYApellidosDelJefeDelHogar}','0','Nombres y Apellidos del jefe del hogar','Nombre completo Jefe de hogar (Concatenado)','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS'));
--RCHZ_SOL_LEG_DES_FOVIS
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadSolicitud}','0','Ciudad solicitud','Ciudad de la sede donde se realiza la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','0','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionResidencia}','0','Dirección residencia','Dirección del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${municipio}','0','Municipio','Municipio del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamento}','0','Departamento','Departamento del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefono}','0','Teléfono','Telefono del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaRadicacionSolicitud}','0','Fecha radicacion solicitud','Fecha en que se radica la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroSolicitud}','0','Numero solicitud','Numerro de identificacion  de la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${modalidad}','0','Modalidad','Modalidad  de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cicloAsignacion}','0','Ciclo asignación','Ciclo de asignación de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacion}','0','Tipo Identificación','Tipo identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdentificacion}','0','Número Identificación','No. identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombresYApellidosDelJefeDelHogar}','0','Nombres y Apellidos del jefe del hogar','Nombre completo Jefe de hogar (Concatenado)','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS'));
--DES_FOVIS_AUTO
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadSolicitud}','0','Ciudad solicitud','Ciudad de la sede donde se realiza la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DES_FOVIS_AUTO'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','0','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DES_FOVIS_AUTO'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionResidencia}','0','Dirección residencia','Dirección del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DES_FOVIS_AUTO'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${municipio}','0','Municipio','Municipio del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DES_FOVIS_AUTO'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamento}','0','Departamento','Departamento del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DES_FOVIS_AUTO'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefono}','0','Teléfono','Telefono del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DES_FOVIS_AUTO'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaRadicacionSolicitud}','0','Fecha radicacion solicitud','Fecha en que se radica la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DES_FOVIS_AUTO'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroSolicitud}','0','Numero solicitud','Numerro de identificacion  de la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DES_FOVIS_AUTO'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${modalidad}','0','Modalidad','Modalidad  de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DES_FOVIS_AUTO'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cicloAsignacion}','0','Ciclo asignación','Ciclo de asignación de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DES_FOVIS_AUTO'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacion}','0','Tipo Identificación','Tipo identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DES_FOVIS_AUTO'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdentificacion}','0','Número Identificación','No. identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DES_FOVIS_AUTO'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombresYApellidosDelJefeDelHogar}','0','Nombres y Apellidos del jefe del hogar','Nombre completo Jefe de hogar (Concatenado)','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DES_FOVIS_AUTO'));
--NTF_DES_FOVIS_EXT
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadSolicitud}','0','Ciudad solicitud','Ciudad de la sede donde se realiza la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_DES_FOVIS_EXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','0','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_DES_FOVIS_EXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionResidencia}','0','Dirección residencia','Dirección del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_DES_FOVIS_EXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${municipio}','0','Municipio','Municipio del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_DES_FOVIS_EXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamento}','0','Departamento','Departamento del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_DES_FOVIS_EXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefono}','0','Teléfono','Telefono del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_DES_FOVIS_EXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaRadicacionSolicitud}','0','Fecha radicacion solicitud','Fecha en que se radica la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_DES_FOVIS_EXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroSolicitud}','0','Numero solicitud','Numerro de identificacion  de la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_DES_FOVIS_EXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${modalidad}','0','Modalidad','Modalidad  de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_DES_FOVIS_EXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cicloAsignacion}','0','Ciclo asignación','Ciclo de asignación de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_DES_FOVIS_EXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacion}','0','Tipo Identificación','Tipo identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_DES_FOVIS_EXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdentificacion}','0','Número Identificación','No. identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_DES_FOVIS_EXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombresYApellidosDelJefeDelHogar}','0','Nombres y Apellidos del jefe del hogar','Nombre completo Jefe de hogar (Concatenado)','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_DES_FOVIS_EXT'));
--DES_FOVIS_NAUTO
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadSolicitud}','0','Ciudad solicitud','Ciudad de la sede donde se realiza la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DES_FOVIS_NAUTO'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','0','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DES_FOVIS_NAUTO'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionResidencia}','0','Dirección residencia','Dirección del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DES_FOVIS_NAUTO'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${municipio}','0','Municipio','Municipio del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DES_FOVIS_NAUTO'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamento}','0','Departamento','Departamento del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DES_FOVIS_NAUTO'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefono}','0','Teléfono','Telefono del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DES_FOVIS_NAUTO'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaRadicacionSolicitud}','0','Fecha radicacion solicitud','Fecha en que se radica la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DES_FOVIS_NAUTO'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroSolicitud}','0','Numero solicitud','Numerro de identificacion  de la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DES_FOVIS_NAUTO'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${modalidad}','0','Modalidad','Modalidad  de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DES_FOVIS_NAUTO'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cicloAsignacion}','0','Ciclo asignación','Ciclo de asignación de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DES_FOVIS_NAUTO'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacion}','0','Tipo Identificación','Tipo identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DES_FOVIS_NAUTO'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdentificacion}','0','Número Identificación','No. identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DES_FOVIS_NAUTO'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombresYApellidosDelJefeDelHogar}','0','Nombres y Apellidos del jefe del hogar','Nombre completo Jefe de hogar (Concatenado)','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DES_FOVIS_NAUTO'));
--NTF_SBC_POS_FOVIS_EXT
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadSolicitud}','0','Ciudad solicitud','Ciudad de la sede donde se realiza la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','0','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionResidencia}','0','Dirección residencia','Dirección del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${municipio}','0','Municipio','Municipio del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamento}','0','Departamento','Departamento del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefono}','0','Teléfono','Telefono del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaRadicacionSolicitud}','0','Fecha radicacion solicitud','Fecha en que se radica la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroSolicitud}','0','Numero solicitud','Numerro de identificacion  de la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${modalidad}','0','Modalidad','Modalidad  de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cicloAsignacion}','0','Ciclo asignación','Ciclo de asignación de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacion}','0','Tipo Identificación','Tipo identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdentificacion}','0','Número Identificación','No. identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombresYApellidosDelJefeDelHogar}','0','Nombres y Apellidos del jefe del hogar','Nombre completo Jefe de hogar (Concatenado)','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT'));
--NTF_RAD_POS_FOVIS_WEB
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadSolicitud}','0','Ciudad solicitud','Ciudad de la sede donde se realiza la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','0','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionResidencia}','0','Dirección residencia','Dirección del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${municipio}','0','Municipio','Municipio del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamento}','0','Departamento','Departamento del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefono}','0','Teléfono','Telefono del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaRadicacionSolicitud}','0','Fecha radicacion solicitud','Fecha en que se radica la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroSolicitud}','0','Numero solicitud','Numerro de identificacion  de la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${modalidad}','0','Modalidad','Modalidad  de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cicloAsignacion}','0','Ciclo asignación','Ciclo de asignación de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacion}','0','Tipo Identificación','Tipo identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdentificacion}','0','Número Identificación','No. identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombresYApellidosDelJefeDelHogar}','0','Nombres y Apellidos del jefe del hogar','Nombre completo Jefe de hogar (Concatenado)','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB'));
--NTF_VAL_NEXT_RAD_POS_FOVIS_WEB
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadSolicitud}','0','Ciudad solicitud','Ciudad de la sede donde se realiza la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','0','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionResidencia}','0','Dirección residencia','Dirección del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${municipio}','0','Municipio','Municipio del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamento}','0','Departamento','Departamento del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefono}','0','Teléfono','Telefono del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaRadicacionSolicitud}','0','Fecha radicacion solicitud','Fecha en que se radica la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroSolicitud}','0','Numero solicitud','Numerro de identificacion  de la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${modalidad}','0','Modalidad','Modalidad  de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cicloAsignacion}','0','Ciclo asignación','Ciclo de asignación de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacion}','0','Tipo Identificación','Tipo identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdentificacion}','0','Número Identificación','No. identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombresYApellidosDelJefeDelHogar}','0','Nombres y Apellidos del jefe del hogar','Nombre completo Jefe de hogar (Concatenado)','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB'));
--RCHZ_SOL_LEG_DES_FOVIS_ESCA
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadSolicitud}','0','Ciudad solicitud','Ciudad de la sede donde se realiza la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','0','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionResidencia}','0','Dirección residencia','Dirección del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${municipio}','0','Municipio','Municipio del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamento}','0','Departamento','Departamento del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefono}','0','Teléfono','Telefono del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaRadicacionSolicitud}','0','Fecha radicacion solicitud','Fecha en que se radica la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroSolicitud}','0','Numero solicitud','Numerro de identificacion  de la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${modalidad}','0','Modalidad','Modalidad  de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cicloAsignacion}','0','Ciclo asignación','Ciclo de asignación de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacion}','0','Tipo Identificación','Tipo identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdentificacion}','0','Número Identificación','No. identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombresYApellidosDelJefeDelHogar}','0','Nombres y Apellidos del jefe del hogar','Nombre completo Jefe de hogar (Concatenado)','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA'));
--RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadSolicitud}','0','Ciudad solicitud','Ciudad de la sede donde se realiza la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','0','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionResidencia}','0','Dirección residencia','Dirección del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${municipio}','0','Municipio','Municipio del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamento}','0','Departamento','Departamento del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefono}','0','Teléfono','Telefono del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaRadicacionSolicitud}','0','Fecha radicacion solicitud','Fecha en que se radica la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroSolicitud}','0','Numero solicitud','Numerro de identificacion  de la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${modalidad}','0','Modalidad','Modalidad  de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cicloAsignacion}','0','Ciclo asignación','Ciclo de asignación de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacion}','0','Tipo Identificación','Tipo identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdentificacion}','0','Número Identificación','No. identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombresYApellidosDelJefeDelHogar}','0','Nombres y Apellidos del jefe del hogar','Nombre completo Jefe de hogar (Concatenado)','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT'));
--NTF_RAD_SOL_NVD_FOVIS
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadSolicitud}','0','Ciudad solicitud','Ciudad de la sede donde se realiza la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','0','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionResidencia}','0','Dirección residencia','Dirección del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${municipio}','0','Municipio','Municipio del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamento}','0','Departamento','Departamento del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefono}','0','Teléfono','Telefono del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaRadicacionSolicitud}','0','Fecha radicacion solicitud','Fecha en que se radica la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroSolicitud}','0','Numero solicitud','Numerro de identificacion  de la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${modalidad}','0','Modalidad','Modalidad  de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cicloAsignacion}','0','Ciclo asignación','Ciclo de asignación de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacion}','0','Tipo Identificación','Tipo identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdentificacion}','0','Número Identificación','No. identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombresYApellidosDelJefeDelHogar}','0','Nombres y Apellidos del jefe del hogar','Nombre completo Jefe de hogar (Concatenado)','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS'));
--NTF_SOL_NVD_FOVIS
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadSolicitud}','0','Ciudad solicitud','Ciudad de la sede donde se realiza la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_SOL_NVD_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','0','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_SOL_NVD_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionResidencia}','0','Dirección residencia','Dirección del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_SOL_NVD_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${municipio}','0','Municipio','Municipio del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_SOL_NVD_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamento}','0','Departamento','Departamento del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_SOL_NVD_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefono}','0','Teléfono','Telefono del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_SOL_NVD_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaRadicacionSolicitud}','0','Fecha radicacion solicitud','Fecha en que se radica la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_SOL_NVD_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroSolicitud}','0','Numero solicitud','Numerro de identificacion  de la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_SOL_NVD_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${modalidad}','0','Modalidad','Modalidad  de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_SOL_NVD_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cicloAsignacion}','0','Ciclo asignación','Ciclo de asignación de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_SOL_NVD_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacion}','0','Tipo Identificación','Tipo identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_SOL_NVD_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdentificacion}','0','Número Identificación','No. identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_SOL_NVD_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombresYApellidosDelJefeDelHogar}','0','Nombres y Apellidos del jefe del hogar','Nombre completo Jefe de hogar (Concatenado)','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_SOL_NVD_FOVIS'));
--RCHZ_SOL_NVD_FOVIS_ESCA
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadSolicitud}','0','Ciudad solicitud','Ciudad de la sede donde se realiza la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','0','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionResidencia}','0','Dirección residencia','Dirección del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${municipio}','0','Municipio','Municipio del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamento}','0','Departamento','Departamento del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefono}','0','Teléfono','Telefono del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaRadicacionSolicitud}','0','Fecha radicacion solicitud','Fecha en que se radica la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroSolicitud}','0','Numero solicitud','Numerro de identificacion  de la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${modalidad}','0','Modalidad','Modalidad  de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cicloAsignacion}','0','Ciclo asignación','Ciclo de asignación de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacion}','0','Tipo Identificación','Tipo identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdentificacion}','0','Número Identificación','No. identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${estadoDeLaSolicitud}','0','Estado de la Solicitud','Estado de la Solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoDeTransaccion}','0','Tipo de transacción','Tipo de transacción','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombresYApellidosDelJefeDelHogar}','0','Nombres y Apellidos del jefe del hogar','Nombre completo Jefe de hogar (Concatenado)','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA'));
--RCHZ_SOL_NVD_FOVIS_PROD_NSUB
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadSolicitud}','0','Ciudad solicitud','Ciudad de la sede donde se realiza la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','0','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionResidencia}','0','Dirección residencia','Dirección del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${municipio}','0','Municipio','Municipio del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamento}','0','Departamento','Departamento del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefono}','0','Teléfono','Telefono del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaRadicacionSolicitud}','0','Fecha radicacion solicitud','Fecha en que se radica la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroSolicitud}','0','Numero solicitud','Numerro de identificacion  de la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${modalidad}','0','Modalidad','Modalidad  de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cicloAsignacion}','0','Ciclo asignación','Ciclo de asignación de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacion}','0','Tipo Identificación','Tipo identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdentificacion}','0','Número Identificación','No. identificación Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${estadoDeLaSolicitud}','0','Estado de la Solicitud','Estado de la Solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoDeTransaccion}','0','Tipo de transacción','Tipo de transacción','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombresYApellidosDelJefeDelHogar}','0','Nombres y Apellidos del jefe del hogar','Nombre completo Jefe de hogar (Concatenado)','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB'));

--changeset mamonroy:19
--comment:Insercion de registros en la tabla VariableComunicado - Constantes
--NTF_INT_POS_FOVIS
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','Responsable de novedades de la caja de Compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_POS_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Nombre de la caja de Compensación','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_POS_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_POS_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Telefono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_POS_FOVIS'));
--NTF_RAD_POS_FOVIS_PRE
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','Responsable de novedades de la caja de Compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Nombre de la caja de Compensación','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Telefono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE'));
--RCHZ_SOL_POS_FOVIS_ESCA
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','Responsable de novedades de la caja de Compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Nombre de la caja de Compensación','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Telefono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA'));
--NTF_RAD_SOL_LEG_DES
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','Responsable de novedades de la caja de Compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Nombre de la caja de Compensación','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Telefono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES'));
--NTF_INT_LEG_DES
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','Responsable de novedades de la caja de Compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_LEG_DES'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Nombre de la caja de Compensación','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_LEG_DES'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_LEG_DES'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Telefono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_LEG_DES'));
--RCHZ_SOL_POS_FOVIS
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','Responsable de novedades de la caja de Compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Nombre de la caja de Compensación','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Telefono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS'));
--RCHZ_SOL_LEG_DES_FOVIS
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','Responsable de novedades de la caja de Compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Nombre de la caja de Compensación','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Telefono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS'));
--DES_FOVIS_AUTO
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','Responsable de novedades de la caja de Compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DES_FOVIS_AUTO'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Nombre de la caja de Compensación','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DES_FOVIS_AUTO'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DES_FOVIS_AUTO'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Telefono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DES_FOVIS_AUTO'));
--NTF_DES_FOVIS_EXT
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','Responsable de novedades de la caja de Compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_DES_FOVIS_EXT'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Nombre de la caja de Compensación','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_DES_FOVIS_EXT'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_DES_FOVIS_EXT'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Telefono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_DES_FOVIS_EXT'));
--DES_FOVIS_NAUTO
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','Responsable de novedades de la caja de Compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DES_FOVIS_NAUTO'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Nombre de la caja de Compensación','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DES_FOVIS_NAUTO'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DES_FOVIS_NAUTO'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Telefono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DES_FOVIS_NAUTO'));
--NTF_SBC_POS_FOVIS_EXT
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','Responsable de novedades de la caja de Compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Nombre de la caja de Compensación','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Telefono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT'));
--NTF_RAD_POS_FOVIS_WEB
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','Responsable de novedades de la caja de Compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Nombre de la caja de Compensación','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Telefono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB'));
--NTF_VAL_NEXT_RAD_POS_FOVIS_WEB
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','Responsable de novedades de la caja de Compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Nombre de la caja de Compensación','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Telefono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB'));
--RCHZ_SOL_LEG_DES_FOVIS_ESCA
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','Responsable de novedades de la caja de Compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Nombre de la caja de Compensación','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Telefono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA'));
--RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','Responsable de novedades de la caja de Compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Nombre de la caja de Compensación','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Telefono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT'));
--NTF_RAD_SOL_NVD_FOVIS
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','Responsable de novedades de la caja de Compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Nombre de la caja de Compensación','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Telefono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS'));
--NTF_SOL_NVD_FOVIS
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','Responsable de novedades de la caja de Compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_SOL_NVD_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Nombre de la caja de Compensación','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_SOL_NVD_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_SOL_NVD_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Telefono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_SOL_NVD_FOVIS'));
--RCHZ_SOL_NVD_FOVIS_ESCA
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','Responsable de novedades de la caja de Compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Nombre de la caja de Compensación','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Telefono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA'));
--RCHZ_SOL_NVD_FOVIS_PROD_NSUB
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','Responsable de novedades de la caja de Compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Nombre de la caja de Compensación','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Telefono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB'));

--changeset mamonroy:20
--comment:Actualizacion de registros en la tabla VariableComunicado - Variables
--NTF_INT_POS_FOVIS
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'NTF_INT_POS_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'NTF_INT_POS_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionResidencia}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionResidencia}') WHERE pcoEtiqueta = 'NTF_INT_POS_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${municipio}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${municipio}') WHERE pcoEtiqueta = 'NTF_INT_POS_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamento}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamento}') WHERE pcoEtiqueta = 'NTF_INT_POS_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefono}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefono}') WHERE pcoEtiqueta = 'NTF_INT_POS_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaRadicacionSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaRadicacionSolicitud}') WHERE pcoEtiqueta = 'NTF_INT_POS_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroSolicitud}') WHERE pcoEtiqueta = 'NTF_INT_POS_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${modalidad}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${modalidad}') WHERE pcoEtiqueta = 'NTF_INT_POS_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cicloAsignacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cicloAsignacion}') WHERE pcoEtiqueta = 'NTF_INT_POS_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdentificacion}') WHERE pcoEtiqueta = 'NTF_INT_POS_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdentificacion}') WHERE pcoEtiqueta = 'NTF_INT_POS_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombresYApellidosDelJefeDelHogar}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombresYApellidosDelJefeDelHogar}') WHERE pcoEtiqueta = 'NTF_INT_POS_FOVIS';
--NTF_RAD_POS_FOVIS_PRE
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionResidencia}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionResidencia}') WHERE pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${municipio}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${municipio}') WHERE pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamento}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamento}') WHERE pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefono}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefono}') WHERE pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaRadicacionSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaRadicacionSolicitud}') WHERE pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroSolicitud}') WHERE pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${modalidad}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${modalidad}') WHERE pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cicloAsignacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cicloAsignacion}') WHERE pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdentificacion}') WHERE pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdentificacion}') WHERE pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombresYApellidosDelJefeDelHogar}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombresYApellidosDelJefeDelHogar}') WHERE pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE';
--RCHZ_SOL_POS_FOVIS_ESCA
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionResidencia}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionResidencia}') WHERE pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${municipio}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${municipio}') WHERE pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamento}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamento}') WHERE pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefono}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefono}') WHERE pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaRadicacionSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaRadicacionSolicitud}') WHERE pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroSolicitud}') WHERE pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${modalidad}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${modalidad}') WHERE pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cicloAsignacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cicloAsignacion}') WHERE pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdentificacion}') WHERE pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdentificacion}') WHERE pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombresYApellidosDelJefeDelHogar}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombresYApellidosDelJefeDelHogar}') WHERE pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA';
--NTF_RAD_SOL_LEG_DES
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionResidencia}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionResidencia}') WHERE pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${municipio}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${municipio}') WHERE pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamento}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamento}') WHERE pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefono}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefono}') WHERE pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaRadicacionSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaRadicacionSolicitud}') WHERE pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroSolicitud}') WHERE pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${modalidad}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${modalidad}') WHERE pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cicloAsignacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cicloAsignacion}') WHERE pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdentificacion}') WHERE pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdentificacion}') WHERE pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombresYApellidosDelJefeDelHogar}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombresYApellidosDelJefeDelHogar}') WHERE pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES';
--NTF_INT_LEG_DES
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'NTF_INT_LEG_DES';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'NTF_INT_LEG_DES';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionResidencia}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionResidencia}') WHERE pcoEtiqueta = 'NTF_INT_LEG_DES';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${municipio}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${municipio}') WHERE pcoEtiqueta = 'NTF_INT_LEG_DES';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamento}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamento}') WHERE pcoEtiqueta = 'NTF_INT_LEG_DES';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefono}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefono}') WHERE pcoEtiqueta = 'NTF_INT_LEG_DES';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaRadicacionSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaRadicacionSolicitud}') WHERE pcoEtiqueta = 'NTF_INT_LEG_DES';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroSolicitud}') WHERE pcoEtiqueta = 'NTF_INT_LEG_DES';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${modalidad}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${modalidad}') WHERE pcoEtiqueta = 'NTF_INT_LEG_DES';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cicloAsignacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cicloAsignacion}') WHERE pcoEtiqueta = 'NTF_INT_LEG_DES';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdentificacion}') WHERE pcoEtiqueta = 'NTF_INT_LEG_DES';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdentificacion}') WHERE pcoEtiqueta = 'NTF_INT_LEG_DES';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombresYApellidosDelJefeDelHogar}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombresYApellidosDelJefeDelHogar}') WHERE pcoEtiqueta = 'NTF_INT_LEG_DES';
--RCHZ_SOL_POS_FOVIS
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionResidencia}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionResidencia}') WHERE pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${municipio}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${municipio}') WHERE pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamento}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamento}') WHERE pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefono}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefono}') WHERE pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaRadicacionSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaRadicacionSolicitud}') WHERE pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroSolicitud}') WHERE pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${modalidad}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${modalidad}') WHERE pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cicloAsignacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cicloAsignacion}') WHERE pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdentificacion}') WHERE pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdentificacion}') WHERE pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombresYApellidosDelJefeDelHogar}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombresYApellidosDelJefeDelHogar}') WHERE pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS';
--RCHZ_SOL_LEG_DES_FOVIS
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionResidencia}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionResidencia}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${municipio}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${municipio}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamento}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamento}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefono}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefono}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaRadicacionSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaRadicacionSolicitud}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroSolicitud}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${modalidad}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${modalidad}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cicloAsignacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cicloAsignacion}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdentificacion}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdentificacion}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombresYApellidosDelJefeDelHogar}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombresYApellidosDelJefeDelHogar}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS';
--DES_FOVIS_AUTO
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'DES_FOVIS_AUTO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'DES_FOVIS_AUTO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionResidencia}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionResidencia}') WHERE pcoEtiqueta = 'DES_FOVIS_AUTO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${municipio}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${municipio}') WHERE pcoEtiqueta = 'DES_FOVIS_AUTO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamento}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamento}') WHERE pcoEtiqueta = 'DES_FOVIS_AUTO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefono}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefono}') WHERE pcoEtiqueta = 'DES_FOVIS_AUTO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaRadicacionSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaRadicacionSolicitud}') WHERE pcoEtiqueta = 'DES_FOVIS_AUTO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroSolicitud}') WHERE pcoEtiqueta = 'DES_FOVIS_AUTO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${modalidad}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${modalidad}') WHERE pcoEtiqueta = 'DES_FOVIS_AUTO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cicloAsignacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cicloAsignacion}') WHERE pcoEtiqueta = 'DES_FOVIS_AUTO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdentificacion}') WHERE pcoEtiqueta = 'DES_FOVIS_AUTO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdentificacion}') WHERE pcoEtiqueta = 'DES_FOVIS_AUTO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombresYApellidosDelJefeDelHogar}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombresYApellidosDelJefeDelHogar}') WHERE pcoEtiqueta = 'DES_FOVIS_AUTO';
--NTF_DES_FOVIS_EXT
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'NTF_DES_FOVIS_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'NTF_DES_FOVIS_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionResidencia}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionResidencia}') WHERE pcoEtiqueta = 'NTF_DES_FOVIS_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${municipio}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${municipio}') WHERE pcoEtiqueta = 'NTF_DES_FOVIS_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamento}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamento}') WHERE pcoEtiqueta = 'NTF_DES_FOVIS_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefono}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefono}') WHERE pcoEtiqueta = 'NTF_DES_FOVIS_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaRadicacionSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaRadicacionSolicitud}') WHERE pcoEtiqueta = 'NTF_DES_FOVIS_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroSolicitud}') WHERE pcoEtiqueta = 'NTF_DES_FOVIS_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${modalidad}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${modalidad}') WHERE pcoEtiqueta = 'NTF_DES_FOVIS_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cicloAsignacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cicloAsignacion}') WHERE pcoEtiqueta = 'NTF_DES_FOVIS_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdentificacion}') WHERE pcoEtiqueta = 'NTF_DES_FOVIS_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdentificacion}') WHERE pcoEtiqueta = 'NTF_DES_FOVIS_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombresYApellidosDelJefeDelHogar}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombresYApellidosDelJefeDelHogar}') WHERE pcoEtiqueta = 'NTF_DES_FOVIS_EXT';
--DES_FOVIS_NAUTO
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'DES_FOVIS_NAUTO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'DES_FOVIS_NAUTO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionResidencia}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionResidencia}') WHERE pcoEtiqueta = 'DES_FOVIS_NAUTO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${municipio}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${municipio}') WHERE pcoEtiqueta = 'DES_FOVIS_NAUTO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamento}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamento}') WHERE pcoEtiqueta = 'DES_FOVIS_NAUTO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefono}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefono}') WHERE pcoEtiqueta = 'DES_FOVIS_NAUTO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaRadicacionSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaRadicacionSolicitud}') WHERE pcoEtiqueta = 'DES_FOVIS_NAUTO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroSolicitud}') WHERE pcoEtiqueta = 'DES_FOVIS_NAUTO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${modalidad}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${modalidad}') WHERE pcoEtiqueta = 'DES_FOVIS_NAUTO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cicloAsignacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cicloAsignacion}') WHERE pcoEtiqueta = 'DES_FOVIS_NAUTO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdentificacion}') WHERE pcoEtiqueta = 'DES_FOVIS_NAUTO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdentificacion}') WHERE pcoEtiqueta = 'DES_FOVIS_NAUTO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombresYApellidosDelJefeDelHogar}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombresYApellidosDelJefeDelHogar}') WHERE pcoEtiqueta = 'DES_FOVIS_NAUTO';
--NTF_SBC_POS_FOVIS_EXT
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionResidencia}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionResidencia}') WHERE pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${municipio}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${municipio}') WHERE pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamento}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamento}') WHERE pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefono}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefono}') WHERE pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaRadicacionSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaRadicacionSolicitud}') WHERE pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroSolicitud}') WHERE pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${modalidad}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${modalidad}') WHERE pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cicloAsignacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cicloAsignacion}') WHERE pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdentificacion}') WHERE pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdentificacion}') WHERE pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombresYApellidosDelJefeDelHogar}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombresYApellidosDelJefeDelHogar}') WHERE pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT';
--NTF_RAD_POS_FOVIS_WEB
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionResidencia}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionResidencia}') WHERE pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${municipio}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${municipio}') WHERE pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamento}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamento}') WHERE pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefono}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefono}') WHERE pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaRadicacionSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaRadicacionSolicitud}') WHERE pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroSolicitud}') WHERE pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${modalidad}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${modalidad}') WHERE pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cicloAsignacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cicloAsignacion}') WHERE pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdentificacion}') WHERE pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdentificacion}') WHERE pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombresYApellidosDelJefeDelHogar}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombresYApellidosDelJefeDelHogar}') WHERE pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB';
--NTF_VAL_NEXT_RAD_POS_FOVIS_WEB
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionResidencia}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionResidencia}') WHERE pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${municipio}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${municipio}') WHERE pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamento}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamento}') WHERE pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefono}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefono}') WHERE pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaRadicacionSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaRadicacionSolicitud}') WHERE pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroSolicitud}') WHERE pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${modalidad}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${modalidad}') WHERE pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cicloAsignacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cicloAsignacion}') WHERE pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdentificacion}') WHERE pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdentificacion}') WHERE pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombresYApellidosDelJefeDelHogar}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombresYApellidosDelJefeDelHogar}') WHERE pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB';
--RCHZ_SOL_LEG_DES_FOVIS_ESCA
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionResidencia}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionResidencia}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${municipio}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${municipio}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamento}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamento}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefono}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefono}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaRadicacionSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaRadicacionSolicitud}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroSolicitud}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${modalidad}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${modalidad}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cicloAsignacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cicloAsignacion}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdentificacion}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdentificacion}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombresYApellidosDelJefeDelHogar}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombresYApellidosDelJefeDelHogar}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA';
--RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionResidencia}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionResidencia}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${municipio}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${municipio}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamento}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamento}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefono}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefono}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaRadicacionSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaRadicacionSolicitud}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroSolicitud}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${modalidad}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${modalidad}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cicloAsignacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cicloAsignacion}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdentificacion}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdentificacion}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombresYApellidosDelJefeDelHogar}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombresYApellidosDelJefeDelHogar}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT';
--NTF_RAD_SOL_NVD_FOVIS
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionResidencia}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionResidencia}') WHERE pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${municipio}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${municipio}') WHERE pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamento}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamento}') WHERE pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefono}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefono}') WHERE pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaRadicacionSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaRadicacionSolicitud}') WHERE pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroSolicitud}') WHERE pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${modalidad}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${modalidad}') WHERE pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cicloAsignacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cicloAsignacion}') WHERE pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdentificacion}') WHERE pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdentificacion}') WHERE pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombresYApellidosDelJefeDelHogar}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombresYApellidosDelJefeDelHogar}') WHERE pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS';
--NTF_SOL_NVD_FOVIS
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'NTF_SOL_NVD_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'NTF_SOL_NVD_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionResidencia}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionResidencia}') WHERE pcoEtiqueta = 'NTF_SOL_NVD_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${municipio}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${municipio}') WHERE pcoEtiqueta = 'NTF_SOL_NVD_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamento}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamento}') WHERE pcoEtiqueta = 'NTF_SOL_NVD_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefono}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefono}') WHERE pcoEtiqueta = 'NTF_SOL_NVD_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaRadicacionSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaRadicacionSolicitud}') WHERE pcoEtiqueta = 'NTF_SOL_NVD_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroSolicitud}') WHERE pcoEtiqueta = 'NTF_SOL_NVD_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${modalidad}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${modalidad}') WHERE pcoEtiqueta = 'NTF_SOL_NVD_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cicloAsignacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cicloAsignacion}') WHERE pcoEtiqueta = 'NTF_SOL_NVD_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdentificacion}') WHERE pcoEtiqueta = 'NTF_SOL_NVD_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdentificacion}') WHERE pcoEtiqueta = 'NTF_SOL_NVD_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombresYApellidosDelJefeDelHogar}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombresYApellidosDelJefeDelHogar}') WHERE pcoEtiqueta = 'NTF_SOL_NVD_FOVIS';
--RCHZ_SOL_NVD_FOVIS_ESCA
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionResidencia}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionResidencia}') WHERE pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${municipio}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${municipio}') WHERE pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamento}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamento}') WHERE pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefono}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefono}') WHERE pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaRadicacionSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaRadicacionSolicitud}') WHERE pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroSolicitud}') WHERE pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${modalidad}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${modalidad}') WHERE pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cicloAsignacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cicloAsignacion}') WHERE pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdentificacion}') WHERE pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdentificacion}') WHERE pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${estadoDeLaSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${estadoDeLaSolicitud}') WHERE pcoEtiqueta = 'HU_PROCESO_212-486';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoDeTransaccion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoDeTransaccion}') WHERE pcoEtiqueta = 'HU_PROCESO_325_096';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombresYApellidosDelJefeDelHogar}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombresYApellidosDelJefeDelHogar}') WHERE pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA';
--RCHZ_SOL_NVD_FOVIS_PROD_NSUB
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionResidencia}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionResidencia}') WHERE pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${municipio}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${municipio}') WHERE pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamento}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamento}') WHERE pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefono}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefono}') WHERE pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaRadicacionSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaRadicacionSolicitud}') WHERE pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroSolicitud}') WHERE pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${modalidad}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${modalidad}') WHERE pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cicloAsignacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cicloAsignacion}') WHERE pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdentificacion}') WHERE pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdentificacion}') WHERE pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${estadoDeLaSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${estadoDeLaSolicitud}') WHERE pcoEtiqueta = 'HU_PROCESO_212-486';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoDeTransaccion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoDeTransaccion}') WHERE pcoEtiqueta = 'HU_PROCESO_325_096';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombresYApellidosDelJefeDelHogar}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombresYApellidosDelJefeDelHogar}') WHERE pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB';

--changeset mamonroy:21
--comment:Actualizacion de registros en la tabla VariableComunicado - Constantes
--NTF_INT_POS_FOVIS
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'NTF_INT_POS_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'NTF_INT_POS_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'NTF_INT_POS_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'NTF_INT_POS_FOVIS';
--NTF_RAD_POS_FOVIS_PRE
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE';
--RCHZ_SOL_POS_FOVIS_ESCA
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS_ESCA';
--NTF_RAD_SOL_LEG_DES
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'NTF_RAD_SOL_LEG_DES';
--NTF_INT_LEG_DES
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'NTF_INT_LEG_DES';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'NTF_INT_LEG_DES';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'NTF_INT_LEG_DES';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'NTF_INT_LEG_DES';
--RCHZ_SOL_POS_FOVIS
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'RCHZ_SOL_POS_FOVIS';
--RCHZ_SOL_LEG_DES_FOVIS
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS';
--DES_FOVIS_AUTO
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'DES_FOVIS_AUTO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'DES_FOVIS_AUTO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'DES_FOVIS_AUTO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'DES_FOVIS_AUTO';
--NTF_DES_FOVIS_EXT
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'NTF_DES_FOVIS_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'NTF_DES_FOVIS_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'NTF_DES_FOVIS_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'NTF_DES_FOVIS_EXT';
--DES_FOVIS_NAUTO
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'DES_FOVIS_NAUTO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'DES_FOVIS_NAUTO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'DES_FOVIS_NAUTO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'DES_FOVIS_NAUTO';
--NTF_SBC_POS_FOVIS_EXT
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'NTF_SBC_POS_FOVIS_EXT';
--NTF_RAD_POS_FOVIS_WEB
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'NTF_RAD_POS_FOVIS_WEB';
--NTF_VAL_NEXT_RAD_POS_FOVIS_WEB
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'NTF_VAL_NEXT_RAD_POS_FOVIS_WEB';
--RCHZ_SOL_LEG_DES_FOVIS_ESCA
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_ESCA';
--RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'RCHZ_SOL_LEG_DES_FOVIS_SBC_NEXT';
--NTF_RAD_SOL_NVD_FOVIS
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'NTF_RAD_SOL_NVD_FOVIS';
--NTF_SOL_NVD_FOVIS
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'NTF_SOL_NVD_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'NTF_SOL_NVD_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'NTF_SOL_NVD_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'NTF_SOL_NVD_FOVIS';
--RCHZ_SOL_NVD_FOVIS_ESCA
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_ESCA';
--RCHZ_SOL_NVD_FOVIS_PROD_NSUB
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'RCHZ_SOL_NVD_FOVIS_PROD_NSUB';

--changeset ecastano:22
--comment:Insercion de registros en la tabla PlantillaComunicado
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Acta de asignación FOVIS','Cuerpo','Encabezado','Mensaje','Acta de asignación FOVIS','Pie','ACT_ASIG_FOVIS');
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Carta asignación FOVIS','Cuerpo','Encabezado','Mensaje','Carta asignación FOVIS','Pie','CRT_ASIG_FOVIS');

--changeset ecastano:23
--comment:Insercion de registros en la tabla VariableComunicado - Variables
--ACT_ASIG_FOVIS
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','0','Fecha del sistema','Fecha actual del sistema (con el mes en letras)','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroActaAsignacion}','0','Numero acta asignacion','Numero consecutivo acta asignacion','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaResolucion}','0','Fecha Resolucion','dd/Mes/aaaa fecha resolución  del acta asignacion','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroResolucionSsf}','0','Numero resolucion SSF','Numero de resolucion SSF del acta asignacion','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${anoResolucionSsf}','0','Ano resolucion SSF','Año resolucion SSF del acta de asignacion','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroOficio}','0','Numero Oficio','Numero de Oficio del acta de asignacion','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaOficio}','0','Fecha oficio ','dd/Mes/aaaa fecha resolución del acta de asignacion','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cicloAsignacion}','0','ciclo asignacion ','ciclo de asignación  del acta','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroSolicitud}','0','Numero solicitud','Numerro de identificacion  de la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${modalidad}','0','Modalidad','Modalidad  de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cicloAsignacion}','0','Ciclo asignación','Ciclo de asignación de la postulación','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaApertura}','0','fecha apertura ','Fecha de inicio para adelantar los procesos de postulación de los afiliados a esta Corporación.','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaCierre}','0','fecha  cierre ','Fecha fin para adelantar los procesos de postulación de los afiliados a esta Corporación.','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cantidadPostControlInternoLetras}','0','Cantidad post control Interno letras','Cantidad de postulaciones en estado Pendiente envío a control interno','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cantidadPostControlInternoNumero}','0','Cantidad post control Interno numero','Cantidad de postulaciones en estado Pendiente envío a control interno numero','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cantidadPostCruceRatificadoLetras}','0','Cantidad post cruce ratificado letras','Cantidad de postulaciones en estado cruce ratificado','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cantidadPostCruceRatificadoNumero}','0','Cantidad post cruce ratificado numero','Cantidad de postulaciones en estado cruce ratificado','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${familiasNovedadCruceInformacionLetras}','0','Familias novedad cruce informacion letras','Cantidad de familias con novedades de cruce de información','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${familiasNovedadCruceInformacionNumeros}','0','Familias novedad cruce informacion numeros','Cantidad de familias con novedades de cruce de información','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${valorDisponibleLetras}','0','Valor disponible letras','Valor disponible (Expresado en letras)','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${valorDisponibleNumero}','0','Valor disponible numero','Valor disponible (Expresado en números con separador de miles)','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroHogaresAsignados}','0','Numero hogares asignados','Numero hogares asignados (Expresado en letras)','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroHogaresAsignadosLetras}','0','Numero hogares asignados letras','Numero hogares asignados (Expresado en numeros)','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${valorTotalSfvAsignarLetras}','0','Valor total sfv asignar letras','Valor total sfv a asignar (expresado en letras)','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${valorTotalSfvAsignar}','0','Valor total sfv asignar','Valor total sfv a asignar (expresado en numeros)','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tabla1}','0','tabla1','Tabla con las familias que presentaron novedades de cruce de información','REPORTE_VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tabla2}','0','tabla2','Tabla con los hogares de acuerdo con el puntaje obtenido','REPORTE_VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tabla3}','0','tabla3','Tabla resumen subsidios asignados por modalidad','REPORTE_VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombresYApellidosResponsable1}','0','Nombres y apellidos responsable 1','Nombres y apellidos responsable 1','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsable1}','0','Cargo responsable 1','Cargo responsable 1','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombresYApellidosResponsable2}','0','Nombres y apellidos responsable 2','Nombres y apellidos responsable 2','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsable2}','0','Cargo responsable 2','Cargo responsable 2','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombresYApellidosResponsable3}','0','Nombres y apellidos responsable 3','Nombres y apellidos responsable 3','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsable3}','0','Cargo responsable 3','Cargo responsable 3','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
--CRT_ASIG_FOVIS
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaResolucion}','0','Fecha Resolucion','Fecha resolución (con el mes en letras)','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaInicioVigencia}','0','Fecha inicio vigencia','Fecha inicio vigencia de los subsidios asignados','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionResidencia}','0','Dirección residencia','Dirección del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${municipio}','0','Municipio','Municipio del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamento}','0','Departamento','Departamento del Jefe de hogar','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaRadicacionSolicitud}','0','Fecha radicacion solicitud','Fecha en que se radica la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tablaHogar}','0','Tabla Hogar','tabla con los integrantes del hogar asignado','REPORTE_VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroActaAsignacion}','0','Numero acta asignacion','Numero consecutivo acta asignacion','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${valorSfv}','0','Valor SFV','Valor SFV (con separador de miles)','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${municipioAsociadoProyecto}','0','Municipio asociado proyecto','Municipio (asociado a la dirección del proyecto de vivienda)','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamentoAsociadoProyecto}','0','Departamento asociado proyecto','Departamento (asociado a la dirección del proyecto de vivienda)]','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${modalidad}','0','Modalidad','Modalidad','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ahorroProgramadoValor}','0','Ahorro programado Valor','Ahorro programado - Valor','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ahorroProgramadoContractualValor}','0','Ahorro programado contractual Valor','Ahorro programado contractual con evaluación crediticia favorable previa (FNA) - Valor','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${aportesPeriodicosEntidadValor}','0','Aportes periódicos Entidad Valor','Aportes periódicos ([Entidad]) - Valor','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cesantiasInmovilizadasValor}','0','Cesantías inmovilizadas Valor','Cesantías inmovilizadas - Valor','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cuotaInicialValor}','0','Cuota inicial Valor','Cuota inicial - Valor','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${valorLoteTerrenoPropio}','0','Valor lote terreno propio','Valor lote o terreno propio - Valor','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${valorLoteOpvOngNoReembolsable}','0','Valor lote OPV ONG no reembolsable','Valor lote OPV, ONG no reembolsable - Valor','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${valorLotePorSubsidio}','0','Valor lote por subsidio','Valor lote por subsidio municipal o departamental - Valor','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${valorAhorroOtrasModalidades}','0','Valor Ahorro otras modalidades','Ahorro en otras modalidades - Valor','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${valorAportesEnteTerritorial}','0','Valor Aportes ente territorial','Aportes ente territorial - Valor','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${valorAportesSolidarios}','0','Valor Aportes solidarios','Aportes solidarios - Valor','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${valorCesantiasNoInmovilizadas}','0','Valor Cesantías no inmovilizadas','Cesantías no inmovilizadas - Valor','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${valorCreditoAprobado}','0','Valor Crédito aprobado','Crédito aprobado - Valor','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${valorDonacionOtrasEntidades}','0','Valor Donación otras entidades','Donación otras entidades - Valor','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${valorEvaluacionCrediticia}','0','Valor Evaluación crediticia','Evaluación crediticia - Valor','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${valorOtrosRecursos}','0','Valor Otros recursos','Otros recursos [Especificar] - Valor','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${valorOtrosRecursosEspecifico}','0','valor Otros Recursos Especifico','Otros recursos [Especificar] - Valor especificado de otro recuerso','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${valorAvanceObra}','0','Valor avance obra','Valor avance obra - Valor','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'));

--changeset ecastano:24
--comment:Insercion de registros en la tabla VariableComunicado - Constantes
--ACT_ASIG_FOVIS
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','Logotipo de la Caja de Compensación','LOGO_DE_LA_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadCcf}','Ciudad CCF','Ciudad de la caja de Compensación','CIUDAD_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACT_ASIG_FOVIS'));
--CRT_ASIG_FOVIS
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','Logotipo de la Caja de Compensación','LOGO_DE_LA_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadCcf}','Ciudad CCF','Ciudad de la caja de Compensación','CIUDAD_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','Director Administrativo','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'));

--changeset ecastano:25
--comment:Actualizacion de registros en la tabla VariableComunicado - Variables
--ACT_ASIG_FOVIS
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroActaAsignacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroActaAsignacion}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaResolucion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaResolucion}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroResolucionSsf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroResolucionSsf}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${anoResolucionSsf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${anoResolucionSsf}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroOficio}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroOficio}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaOficio}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaOficio}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cicloAsignacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cicloAsignacion}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroSolicitud}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${modalidad}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${modalidad}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cicloAsignacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cicloAsignacion}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaApertura}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaApertura}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaCierre}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaCierre}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cantidadPostControlInternoLetras}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cantidadPostControlInternoLetras}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cantidadPostControlInternoNumero}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cantidadPostControlInternoNumero}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cantidadPostCruceRatificadoLetras}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cantidadPostCruceRatificadoLetras}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cantidadPostCruceRatificadoNumero}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cantidadPostCruceRatificadoNumero}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${familiasNovedadCruceInformacionLetras}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${familiasNovedadCruceInformacionLetras}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${familiasNovedadCruceInformacionNumeros}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${familiasNovedadCruceInformacionNumeros}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${valorDisponibleLetras}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${valorDisponibleLetras}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${valorDisponibleNumero}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${valorDisponibleNumero}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroHogaresAsignados}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroHogaresAsignados}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroHogaresAsignadosLetras}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroHogaresAsignadosLetras}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${valorTotalSfvAsignarLetras}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${valorTotalSfvAsignarLetras}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${valorTotalSfvAsignar}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${valorTotalSfvAsignar}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tabla1}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tabla1}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tabla2}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tabla2}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tabla3}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tabla3}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombresYApellidosResponsable1}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombresYApellidosResponsable1}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsable1}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsable1}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombresYApellidosResponsable2}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombresYApellidosResponsable2}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsable2}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsable2}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombresYApellidosResponsable3}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombresYApellidosResponsable3}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsable3}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsable3}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
--CRT_ASIG_FOVIS
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaResolucion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaResolucion}') WHERE pcoEtiqueta = 'CRT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaInicioVigencia}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaInicioVigencia}') WHERE pcoEtiqueta = 'CRT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionResidencia}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionResidencia}') WHERE pcoEtiqueta = 'CRT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${municipio}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${municipio}') WHERE pcoEtiqueta = 'CRT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamento}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamento}') WHERE pcoEtiqueta = 'CRT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaRadicacionSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaRadicacionSolicitud}') WHERE pcoEtiqueta = 'CRT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tablaHogar}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tablaHogar}') WHERE pcoEtiqueta = 'CRT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroActaAsignacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroActaAsignacion}') WHERE pcoEtiqueta = 'CRT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${valorSfv}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${valorSfv}') WHERE pcoEtiqueta = 'CRT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${municipioAsociadoProyecto}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${municipioAsociadoProyecto}') WHERE pcoEtiqueta = 'CRT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamentoAsociadoProyecto}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamentoAsociadoProyecto}') WHERE pcoEtiqueta = 'CRT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${modalidad}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${modalidad}') WHERE pcoEtiqueta = 'CRT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ahorroProgramadoValor}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ahorroProgramadoValor}') WHERE pcoEtiqueta = 'CRT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ahorroProgramadoContractualValor}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ahorroProgramadoContractualValor}') WHERE pcoEtiqueta = 'CRT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${aportesPeriodicosEntidadValor}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${aportesPeriodicosEntidadValor}') WHERE pcoEtiqueta = 'CRT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cesantiasInmovilizadasValor}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cesantiasInmovilizadasValor}') WHERE pcoEtiqueta = 'CRT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cuotaInicialValor}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cuotaInicialValor}') WHERE pcoEtiqueta = 'CRT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${valorLoteTerrenoPropio}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${valorLoteTerrenoPropio}') WHERE pcoEtiqueta = 'CRT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${valorLoteOpvOngNoReembolsable}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${valorLoteOpvOngNoReembolsable}') WHERE pcoEtiqueta = 'CRT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${valorLotePorSubsidio}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${valorLotePorSubsidio}') WHERE pcoEtiqueta = 'CRT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${valorAhorroOtrasModalidades}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${valorAhorroOtrasModalidades}') WHERE pcoEtiqueta = 'CRT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${valorAportesEnteTerritorial}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${valorAportesEnteTerritorial}') WHERE pcoEtiqueta = 'CRT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${valorAportesSolidarios}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${valorAportesSolidarios}') WHERE pcoEtiqueta = 'CRT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${valorCesantiasNoInmovilizadas}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${valorCesantiasNoInmovilizadas}') WHERE pcoEtiqueta = 'CRT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${valorCreditoAprobado}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${valorCreditoAprobado}') WHERE pcoEtiqueta = 'CRT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${valorDonacionOtrasEntidades}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${valorDonacionOtrasEntidades}') WHERE pcoEtiqueta = 'CRT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${valorEvaluacionCrediticia}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${valorEvaluacionCrediticia}') WHERE pcoEtiqueta = 'CRT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${valorOtrosRecursos}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${valorOtrosRecursos}') WHERE pcoEtiqueta = 'CRT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${valorOtrosRecursosEspecifico}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${valorOtrosRecursosEspecifico}') WHERE pcoEtiqueta = 'CRT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${valorAvanceObra}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${valorAvanceObra}') WHERE pcoEtiqueta = 'CRT_ASIG_FOVIS';

--changeset ecastano:26
--comment:Actualizacion de registros en la tabla VariableComunicado - Constantes
--ACT_ASIG_FOVIS
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS';
--CRT_ASIG_FOVIS
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'CRT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'CRT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'CRT_ASIG_FOVIS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'CRT_ASIG_FOVIS';

--changeset jocorrea:27
--comment:Eliminacion de tablas relacionadas con el CargueArchivoCruces
ALTER TABLE CargueArchivoCruceFovisCedula DROP CONSTRAINT FK_CargueArchivoCruceFovisCedula_cfcCargueArchivoCruceFovis;
DROP TABLE CargueArchivoCruceFovisCedula;
ALTER TABLE CargueArchivoCruceFovisAfiliado DROP CONSTRAINT FK_CargueArchivoCruceFovisAfiliado_cfaCargueArchivoCruceFovis;
DROP TABLE CargueArchivoCruceFovisAfiliado;
ALTER TABLE CargueArchivoCruceFovisBeneficiario DROP CONSTRAINT FK_CargueArchivoCruceFovisBeneficiario_cfbCargueArchivoCruceFovis;
DROP TABLE CargueArchivoCruceFovisBeneficiario;
ALTER TABLE CargueArchivoCruceFovisCatAnt DROP CONSTRAINT FK_CargueArchivoCruceFovisCatAnt_cfnCargueArchivoCruceFovis;
DROP TABLE CargueArchivoCruceFovisCatAnt;
ALTER TABLE CargueArchivoCruceFovisCatBog DROP CONSTRAINT FK_CargueArchivoCruceFovisCatBog_cfoCargueArchivoCruceFovis;
DROP TABLE CargueArchivoCruceFovisCatBog;
ALTER TABLE CargueArchivoCruceFovisCatCali DROP CONSTRAINT FK_CargueArchivoCruceFovisCatCali_cflCargueArchivoCruceFovis;
DROP TABLE CargueArchivoCruceFovisCatCali;
ALTER TABLE CargueArchivoCruceFovisCatMed DROP CONSTRAINT FK_CargueArchivoCruceFovisCatMed_cfmCargueArchivoCruceFovis;
DROP TABLE CargueArchivoCruceFovisCatMed;
ALTER TABLE CargueArchivoCruceFovisIGAC DROP CONSTRAINT FK_CargueArchivoCruceFovisIGAC_cfgCargueArchivoCruceFovis;
DROP TABLE CargueArchivoCruceFovisIGAC;
ALTER TABLE CargueArchivoCruceFovisFechasCorte DROP CONSTRAINT FK_CargueArchivoCruceFovisFechasCorte_cffCargueArchivoCruceFovis;
DROP TABLE CargueArchivoCruceFovisFechasCorte;
ALTER TABLE CargueArchivoCruceFovisSisben DROP CONSTRAINT FK_CargueArchivoCruceFovisSisben_cfsCargueArchivoCruceFovis;
DROP TABLE CargueArchivoCruceFovisSisben;
ALTER TABLE CargueArchivoCruceFovisUnidos DROP CONSTRAINT FK_CargueArchivoCruceFovisUnidos_cfuCargueArchivoCruceFovis;
DROP TABLE CargueArchivoCruceFovisUnidos;
ALTER TABLE CargueArchivoCruceFovisReunidos DROP CONSTRAINT FK_CargueArchivoCruceFovisReunidos_cfrCargueArchivoCruceFovis;
DROP TABLE CargueArchivoCruceFovisReunidos;

--changeset jocorrea:28
--comment:Se elimina tabla relacionada con el CargueArchivoCruces
ALTER TABLE CargueArchivoCruceFovisNuevoHogar DROP CONSTRAINT FK_CargueArchivoCruceFovisNuevoHogar_cfhCargueArchivoCruceFovis;
DROP TABLE CargueArchivoCruceFovisNuevoHogar;

--changeset jocorrea:29
--comment:Creacion de tablas y secuencias relacionadas con el CargueArchivoCruce
CREATE TABLE CargueArchivoCruceFovisCedula(
	cfcId BIGINT NOT NULL,
	cfcCargueArchivoCruceFovis BIGINT NOT NULL,
	cfcNroCedula VARCHAR(16)  NULL,
CONSTRAINT PK_CargueArchivoCruceFovisCedula_cfcId PRIMARY KEY (cfcId)
);
CREATE SEQUENCE SEC_CargueArchivoCruceFovisCedula START WITH 1 INCREMENT BY 1;
ALTER TABLE CargueArchivoCruceFovisCedula ADD CONSTRAINT FK_CargueArchivoCruceFovisCedula_cfcCargueArchivoCruceFovis FOREIGN KEY (cfcCargueArchivoCruceFovis) REFERENCES CargueArchivoCruceFovis(cacId);

CREATE TABLE CargueArchivoCruceFovisAfiliado(
	cfaId BIGINT NOT NULL,
	cfaCargueArchivoCruceFovis BIGINT NOT NULL,
	cfaNitEntidad VARCHAR(16) NULL,
	cfaNombreEntidad VARCHAR(30) NULL,
	cfaIdentificacion VARCHAR(16) NULL,
	cfaApellidos VARCHAR(100) NULL,
	cfaNombres VARCHAR(100) NULL,
CONSTRAINT PK_CargueArchivoCruceFovisAfiliado_cfaId PRIMARY KEY (cfaId)
);
CREATE SEQUENCE SEC_CargueArchivoCruceFovisAfiliado START WITH 1 INCREMENT BY 1;
ALTER TABLE CargueArchivoCruceFovisAfiliado ADD CONSTRAINT FK_CargueArchivoCruceFovisAfiliado_cfaCargueArchivoCruceFovis FOREIGN KEY (cfaCargueArchivoCruceFovis) REFERENCES CargueArchivoCruceFovis(cacId);

CREATE TABLE CargueArchivoCruceFovisBeneficiario(
	cfbId BIGINT NOT NULL,
	cfbCargueArchivoCruceFovis BIGINT NOT NULL,
	cfbNitEntidad VARCHAR(16) NULL,
	cfbNombreEntidad VARCHAR(30) NULL,
	cfbIdentificacion VARCHAR(16) NULL,
	cfbApellidos VARCHAR(100) NULL,
	cfbNombres VARCHAR(100) NULL,
CONSTRAINT PK_CargueArchivoCruceFovisBeneficiario_cfbId PRIMARY KEY (cfbId)
);
CREATE SEQUENCE SEC_CargueArchivoCruceFovisBeneficiario START WITH 1 INCREMENT BY 1;
ALTER TABLE CargueArchivoCruceFovisBeneficiario ADD CONSTRAINT FK_CargueArchivoCruceFovisBeneficiario_cfbCargueArchivoCruceFovis FOREIGN KEY (cfbCargueArchivoCruceFovis) REFERENCES CargueArchivoCruceFovis(cacId);

CREATE TABLE CargueArchivoCruceFovisCatAnt(
	cfnId BIGINT NOT NULL,
	cfnCargueArchivoCruceFovis BIGINT NOT NULL,
	cfnNitEntidad VARCHAR(16) NULL,
	cfnNombreEntidad VARCHAR(30) NULL,
	cfnIdentificacion VARCHAR(16) NULL,
	cfnApellidos VARCHAR(100) NULL,
	cfnNombres VARCHAR(100) NULL,
	cfnCedulaCatastral VARCHAR(50) NULL,
	cfnDireccionInmueble VARCHAR(300) NULL,
	cfnMatriculaInmobiliaria VARCHAR(50) NULL,
	cfnDepartamento VARCHAR(100) NULL,
	cfnMunicipio VARCHAR(100) NULL,
CONSTRAINT PK_CargueArchivoCruceFovisCatAnt_cfnId PRIMARY KEY (cfnId)
);
CREATE SEQUENCE SEC_CargueArchivoCruceFovisCatAnt START WITH 1 INCREMENT BY 1;
ALTER TABLE CargueArchivoCruceFovisCatAnt ADD CONSTRAINT FK_CargueArchivoCruceFovisCatAnt_cfnCargueArchivoCruceFovis FOREIGN KEY (cfnCargueArchivoCruceFovis) REFERENCES CargueArchivoCruceFovis(cacId);

CREATE TABLE CargueArchivoCruceFovisCatBog(
	cfoId BIGINT NOT NULL,
	cfoCargueArchivoCruceFovis BIGINT NOT NULL,
	cfoNitEntidad VARCHAR(16) NULL,
	cfoNombreEntidad VARCHAR(30) NULL,
	cfoIdentificacion VARCHAR(16) NULL,
	cfoApellidos VARCHAR(100) NULL,
	cfoNombres VARCHAR(100) NULL,
	cfoCedulaCatastral VARCHAR(50) NULL,
	cfoDireccion VARCHAR(300) NULL,
	cfoMatriculaInmobiliaria VARCHAR(50) NULL,
	cfoDepartamento VARCHAR(100) NULL,
	cfoMunicipio VARCHAR(100) NULL,
CONSTRAINT PK_CargueArchivoCruceFovisBeneficiario_cfoId PRIMARY KEY (cfoId)
);
CREATE SEQUENCE SEC_CargueArchivoCruceFovisCatBog START WITH 1 INCREMENT BY 1;
ALTER TABLE CargueArchivoCruceFovisCatBog ADD CONSTRAINT FK_CargueArchivoCruceFovisCatBog_cfoCargueArchivoCruceFovis FOREIGN KEY (cfoCargueArchivoCruceFovis) REFERENCES CargueArchivoCruceFovis(cacId);

CREATE TABLE CargueArchivoCruceFovisCatCali(
	cflId BIGINT NOT NULL,
	cflCargueArchivoCruceFovis BIGINT NOT NULL,
	cflNitEntidad VARCHAR(16) NULL,
	cflNombreEntidad VARCHAR(30) NULL,
	cflIdentificacion VARCHAR(16) NULL,
	cflApellidosNombres VARCHAR(200) NULL,
	cflMatriculaInmobiliaria VARCHAR(50) NULL,
	cflDepartamento VARCHAR(100) NULL,
	cflMunicipio VARCHAR(100) NULL,
CONSTRAINT PK_CargueArchivoCruceFovisCatCali_cflId PRIMARY KEY (cflId)
);
CREATE SEQUENCE SEC_CargueArchivoCruceFovisCatCali START WITH 1 INCREMENT BY 1;
ALTER TABLE CargueArchivoCruceFovisCatCali ADD CONSTRAINT FK_CargueArchivoCruceFovisCatCali_cflCargueArchivoCruceFovis FOREIGN KEY (cflCargueArchivoCruceFovis) REFERENCES CargueArchivoCruceFovis(cacId);

CREATE TABLE CargueArchivoCruceFovisCatMed(
	cfmId BIGINT NOT NULL,
	cfmCargueArchivoCruceFovis BIGINT NOT NULL,
	cfmNombreEntidad VARCHAR(30) NULL,
	cfmIdentificacion VARCHAR(16) NULL,
	cfmApellidosNombres VARCHAR(200) NULL,
	cfmDireccion VARCHAR(300) NULL,
	cfmDepartamento VARCHAR(100) NULL,
	cfmMunicipio VARCHAR(100) NULL,
	cfmMatriculaInmobiliaria VARCHAR(50) NULL,
	cfmCedulaCatastral VARCHAR(50) NULL,
CONSTRAINT PK_CargueArchivoCruceFovisCatMed_cfmId PRIMARY KEY (cfmId)
);
CREATE SEQUENCE SEC_CargueArchivoCruceFovisCatMed START WITH 1 INCREMENT BY 1;
ALTER TABLE CargueArchivoCruceFovisCatMed ADD CONSTRAINT FK_CargueArchivoCruceFovisCatMed_cfmCargueArchivoCruceFovis FOREIGN KEY (cfmCargueArchivoCruceFovis) REFERENCES CargueArchivoCruceFovis(cacId);

CREATE TABLE CargueArchivoCruceFovisIGAC(
	cfgId BIGINT NOT NULL,
	cfgCargueArchivoCruceFovis BIGINT NOT NULL,
	cfgNitEntidad VARCHAR(16) NULL,
	cfgNombreEntidad VARCHAR(30) NULL,
	cfgIdentificacion VARCHAR(16) NULL,
	cfgApellidosNombres VARCHAR(200) NULL,
	cfgCedulaCatastral VARCHAR(50) NULL,
	cfgDireccion VARCHAR(300) NULL,
	cfgMatriculaInmobiliaria VARCHAR(50) NULL,
	cfgDepartamento VARCHAR(100) NULL,
	cfgMunicipio VARCHAR(100) NULL,
CONSTRAINT PK_CargueArchivoCruceFovisIGAC_cfgId PRIMARY KEY (cfgId)
);
CREATE SEQUENCE SEC_CargueArchivoCruceFovisIGAC START WITH 1 INCREMENT BY 1;
ALTER TABLE CargueArchivoCruceFovisIGAC ADD CONSTRAINT FK_CargueArchivoCruceFovisIGAC_cfgCargueArchivoCruceFovis FOREIGN KEY (cfgCargueArchivoCruceFovis) REFERENCES CargueArchivoCruceFovis(cacId);

CREATE TABLE CargueArchivoCruceFovisNuevoHogar(
	cfhId BIGINT NOT NULL,
	cfhCargueArchivoCruceFovis BIGINT NOT NULL,
	cfhIdentificacion VARCHAR(16) NULL,
	cfhApellidosNombres VARCHAR(200) NULL,
	cfhFechaSolicitud DATE NULL,
	cfhEntidadOrtogante VARCHAR(30) NULL,
	cfhCajaCompensacion VARCHAR(30) NULL,
	cfhAsignadoPosteriorReporte VARCHAR(30) NULL,
CONSTRAINT PK_CargueArchivoCruceFovisIGAC_cfhId PRIMARY KEY (cfhId)
);
CREATE SEQUENCE SEC_CargueArchivoCruceFovisNuevoHogar START WITH 1 INCREMENT BY 1;
ALTER TABLE CargueArchivoCruceFovisNuevoHogar ADD CONSTRAINT FK_CargueArchivoCruceFovisNuevoHogar_cfhCargueArchivoCruceFovis FOREIGN KEY (cfhCargueArchivoCruceFovis) REFERENCES CargueArchivoCruceFovis(cacId);

CREATE TABLE CargueArchivoCruceFovisFechasCorte(
	cffId BIGINT NOT NULL,
	cffCargueArchivoCruceFovis BIGINT NOT NULL,
	cffNitEntidad VARCHAR(16) NULL,
	cffNombreEntidad VARCHAR(30) NULL,
	cffTipoInformacion VARCHAR(20) NULL,
	cffFechaCorte DATE NULL,
	cffFechaActualizacion DATE  NULL,
CONSTRAINT PK_CargueArchivoCruceFovisFechasCorte_cffId PRIMARY KEY (cffId)
);
CREATE SEQUENCE SEC_CargueArchivoCruceFovisFechasCorte START WITH 1 INCREMENT BY 1;
ALTER TABLE CargueArchivoCruceFovisFechasCorte ADD CONSTRAINT FK_CargueArchivoCruceFovisFechasCorte_cffCargueArchivoCruceFovis FOREIGN KEY (cffCargueArchivoCruceFovis) REFERENCES CargueArchivoCruceFovis(cacId);

CREATE TABLE CargueArchivoCruceFovisSisben(
	cfsId BIGINT NOT NULL,
	cfsCargueArchivoCruceFovis BIGINT NOT NULL,
	cfsIdentificacion VARCHAR(16) NULL,
	cfsApellidosNombres VARCHAR(200) NULL,
	cfsPuntaje VARCHAR(10) NULL,
	cfsSexo VARCHAR(20) NULL,
	cfsZona VARCHAR(30) NULL,
	cfsParantesco VARCHAR(30) NULL,
CONSTRAINT PK_CargueArchivoCruceFovisSisben_cfsId PRIMARY KEY (cfsId)
);
CREATE SEQUENCE SEC_CargueArchivoCruceFovisSisben START WITH 1 INCREMENT BY 1;
ALTER TABLE CargueArchivoCruceFovisSisben ADD CONSTRAINT FK_CargueArchivoCruceFovisSisben_cfsCargueArchivoCruceFovis FOREIGN KEY (cfsCargueArchivoCruceFovis) REFERENCES CargueArchivoCruceFovis(cacId);

CREATE TABLE CargueArchivoCruceFovisUnidos(
	cfuId BIGINT NOT NULL,
	cfuCargueArchivoCruceFovis BIGINT NOT NULL,
	cfuIdentificacion VARCHAR(16) NULL,
	cfuApellidosNombres VARCHAR(200) NULL,
	cfuFolio VARCHAR(30) NULL,
	cfuSexo VARCHAR(20) NULL,
	cfuParantesco VARCHAR(30) NULL,
	cfuDepartamento VARCHAR(100) NULL,
	cfuMunicipio VARCHAR(50) NULL,
CONSTRAINT PK_CargueArchivoCruceFovisUnidos_cfuId PRIMARY KEY (cfuId)
);
CREATE SEQUENCE SEC_CargueArchivoCruceFovisUnidos START WITH 1 INCREMENT BY 1;
ALTER TABLE CargueArchivoCruceFovisUnidos ADD CONSTRAINT FK_CargueArchivoCruceFovisUnidos_cfuCargueArchivoCruceFovis FOREIGN KEY (cfuCargueArchivoCruceFovis) REFERENCES CargueArchivoCruceFovis(cacId);

CREATE TABLE CargueArchivoCruceFovisReunidos(
	cfrId BIGINT NOT NULL,
	cfrCargueArchivoCruceFovis BIGINT NOT NULL,
	cfrDocumento VARCHAR(16) NULL,
	cfrTipoDocumento VARCHAR(30) NULL,
	cfrApellidosNombres VARCHAR(200) NULL,
	cfrMunicipio VARCHAR(10) NULL,
	cfrDepartamento VARCHAR(100) NULL,
CONSTRAINT PK_CargueArchivoCruceFovisReunidos_cfrId PRIMARY KEY (cfrId)
);
CREATE SEQUENCE SEC_CargueArchivoCruceFovisReunidos START WITH 1 INCREMENT BY 1;
ALTER TABLE CargueArchivoCruceFovisReunidos ADD CONSTRAINT FK_CargueArchivoCruceFovisReunidos_cfrCargueArchivoCruceFovis FOREIGN KEY (cfrCargueArchivoCruceFovis) REFERENCES CargueArchivoCruceFovis(cacId);

--changeset jocorrea:30
--comment:Se modifica llave primaria de la tabla 
ALTER TABLE CargueArchivoCruceFovisNuevoHogar DROP CONSTRAINT PK_CargueArchivoCruceFovisIGAC_cfhId;
ALTER TABLE CargueArchivoCruceFovisNuevoHogar ADD CONSTRAINT PK_CargueArchivoCruceFovisNuevoHogar_cfhId PRIMARY KEY (cfhId);

--changeset fvasquez:31
--comment:Se adiciona campo a la tabla ActaAsignacionFovis
ALTER TABLE ActaAsignacionFovis ADD aafFechaActaAsignacionFovis DATETIME NULL;

--changeset ecastaño:32
--comment:Se adiciona campo a la tabla ActaAsignacionFovis
ALTER TABLE PostulacionFOVIS ADD pofFechaCalificacion DATETIME NULL;
ALTER TABLE PostulacionFOVIS ADD pofPrioridadAsignacion VARCHAR(11) NULL;

--changeset flopez:33
--comment:Insercion de registros en la tabla ValidacionProceso
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-1','VALIDACION_PERSONA_SOLICITUD_EN_PROCESO','POSTULACION_FOVIS_PRESENCIAL','ACTIVO',1,'JEFE_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-1','VALIDACION_PERSONA_SOLICITUD_EN_PROCESO','POSTULACION_FOVIS_WEB','ACTIVO',1,'JEFE_HOGAR',0);

--changeset jocorrea:34
--comment:Se eliminan y crean tablas de Cruce, CruceDetalle y SolicitudGestionCruce
ALTER TABLE CruceDetalle DROP CONSTRAINT FK_CruceDetalle_crdCruce;
DROP TABLE CruceDetalle;

ALTER TABLE Cruce DROP CONSTRAINT FK_Cruce_cruCargueArchivoCruceFovis;
ALTER TABLE Cruce DROP CONSTRAINT FK_Cruce_cruEjecucionProcesoAsincrono;
ALTER TABLE Cruce DROP CONSTRAINT FK_Cruce_cruPersona;
DROP TABLE Cruce;

ALTER TABLE SolicitudGestionCruce DROP CONSTRAINT FK_SolicitudGestionCruce_sgcSolicitudPostulacion;
DROP TABLE SolicitudGestionCruce;

CREATE TABLE Cruce(
	cruId BIGINT NOT NULL,
	cruCargueArchivoCruceFovis BIGINT NULL,
	cruNumeroPostulacion VARCHAR(12) NULL,
	cruPersona BIGINT NULL,
	cruEstadoCruce VARCHAR(22) NOT NULL,
	cruSolicitudGestionCruce BIGINT NULL,
	cruResultadoCodigoIdentificadorECM VARCHAR(255) NULL,
	cruObservacionResultado VARCHAR(500) NULL,
	cruEjecucionProcesoAsincrono BIGINT NULL,
	cruFechaRegistro DATETIME NOT NULL,
CONSTRAINT PK_Cruce_cruId PRIMARY KEY (cruId)
);
CREATE SEQUENCE SEC_Cruce START WITH 1 INCREMENT BY 1;
ALTER TABLE Cruce ADD CONSTRAINT FK_Cruce_cruCargueArchivoCruceFovis FOREIGN KEY(cruCargueArchivoCruceFovis) REFERENCES CargueArchivoCruceFovis (cacId);
ALTER TABLE Cruce ADD CONSTRAINT FK_Cruce_cruEjecucionProcesoAsincrono FOREIGN KEY(cruEjecucionProcesoAsincrono) REFERENCES EjecucionProcesoAsincrono (epsId);
ALTER TABLE Cruce ADD CONSTRAINT FK_Cruce_cruPersona FOREIGN KEY(cruPersona) REFERENCES Persona (perId);
 
CREATE TABLE CruceDetalle(
	crdId BIGINT NOT NULL,
	crdCruce BIGINT NOT NULL,
	crdCausalCruce VARCHAR(30) NULL,
	crdNitEntidad VARCHAR(16) NULL,
	crdNombreEntidad VARCHAR(100) NULL,
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
	crdSexo VARCHAR(20) NULL,
	crdZona VARCHAR(30) NULL,
	crdParentesco VARCHAR(30) NULL,
	crdFolio VARCHAR(30) NULL,
	crdTipodocumento VARCHAR(30) NULL,
	crdFechaSolicitud DATE NULL,
	crdEntidadOtorgante VARCHAR(30) NULL,
	crdCajaCompensacion VARCHAR(30) NULL,
	crdAsignadoPosterior VARCHAR(30) NULL,
	crdTipo VARCHAR(15) NULL,
	crdResultadoValidacion VARCHAR(255) NULL,
	crdClasificacion VARCHAR(30) NULL,
CONSTRAINT PK_CruceDetalle_crdId PRIMARY KEY	(crdId)
); 
CREATE SEQUENCE SEC_CruceDetalle START WITH 1 INCREMENT BY 1;
ALTER TABLE CruceDetalle ADD CONSTRAINT FK_CruceDetalle_crdCruce FOREIGN KEY(crdCruce) REFERENCES Cruce (cruId);

CREATE TABLE SolicitudGestionCruce(
	sgcId BIGINT NOT NULL,
	sgcSolicitudPostulacion BIGINT NOT NULL,
	sgcEstadoCruceHogar VARCHAR(53) NULL,
	sgcTipoCruce VARCHAR(8) NULL,
CONSTRAINT PK_SolicitudGestionCruce_sgcId PRIMARY KEY (sgcId)
);
CREATE SEQUENCE SEC_SolicitudGestionCruce START WITH 1 INCREMENT BY 1;
ALTER TABLE SolicitudGestionCruce ADD CONSTRAINT FK_SolicitudGestionCruce_sgcSolicitudPostulacion FOREIGN KEY(sgcSolicitudPostulacion) REFERENCES SolicitudPostulacion (spoId);

--changeset jocorrea:35
--comment:Se actualizan campos de las tablas CargueArchivoCruce
ALTER TABLE CargueArchivoCruceFovisAfiliado ALTER COLUMN cfaNombreEntidad VARCHAR(100) NULL;
ALTER TABLE CargueArchivoCruceFovisBeneficiario ALTER COLUMN cfbNombreEntidad VARCHAR(100) NULL;
ALTER TABLE CargueArchivoCruceFovisCatAnt ALTER COLUMN cfnNombreEntidad VARCHAR(100) NULL;
ALTER TABLE CargueArchivoCruceFovisCatBog ALTER COLUMN cfoNombreEntidad VARCHAR(100) NULL;
ALTER TABLE CargueArchivoCruceFovisCatCali ALTER COLUMN cflNombreEntidad VARCHAR(100) NULL;
ALTER TABLE CargueArchivoCruceFovisCatMed ALTER COLUMN cfmNombreEntidad VARCHAR(100) NULL;
ALTER TABLE CargueArchivoCruceFovisFechasCorte ALTER COLUMN cffNombreEntidad VARCHAR(100) NULL;
ALTER TABLE CargueArchivoCruceFovisIGAC ALTER COLUMN cfgNombreEntidad VARCHAR(100) NULL;

--changeset jocorrea:36
--comment:Se actualizan campos de las tablas CargueArchivoCruceFovis
ALTER TABLE CargueArchivoCruceFovis DROP COLUMN cacInfoArchivoJsonPayload;

--changeset mamonroy:37
--comment:Insercion de registros en las tablas GrupoPrioridad, DestinatarioGrupo, DestinatarioComunicado y PrioridadDestinatario
INSERT GrupoPrioridad (gprNombre) VALUES ('GRUPO 21');
INSERT DestinatarioGrupo (dgrGrupoPrioridad,dgrRolContacto) VALUES ((SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='GRUPO 21'),'RESPONSABLE_JEFE_HOGAR');
INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('POSTULACION_FOVIS_PRESENCIAL','NTF_INT_POS_FOVIS');
INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('POSTULACION_FOVIS_PRESENCIAL','NTF_RAD_POS_FOVIS_PRE');
INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('POSTULACION_FOVIS_PRESENCIAL','RCHZ_SOL_POS_FOVIS_ESCA');
INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('POSTULACION_FOVIS_PRESENCIAL','RCHZ_SOL_POS_FOVIS');
INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('POSTULACION_FOVIS_PRESENCIAL','NTF_SBC_POS_FOVIS_EXT');
INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('POSTULACION_FOVIS_WEB','NTF_VAL_NEXT_RAD_POS_FOVIS_WEB');
INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('POSTULACION_FOVIS_WEB','NTF_RAD_POS_FOVIS_WEB');
INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('POSTULACION_FOVIS_WEB','RCHZ_SOL_LEG_DES_FOVIS_ESCA');
INSERT PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES ((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='POSTULACION_FOVIS_PRESENCIAL' AND des.dcoEtiquetaPlantilla='NTF_INT_POS_FOVIS'),(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='GRUPO 21'),1);
INSERT PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES ((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='POSTULACION_FOVIS_PRESENCIAL' AND des.dcoEtiquetaPlantilla='NTF_RAD_POS_FOVIS_PRE'),(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='GRUPO 21'),1);
INSERT PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES ((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='POSTULACION_FOVIS_PRESENCIAL' AND des.dcoEtiquetaPlantilla='RCHZ_SOL_POS_FOVIS_ESCA'),(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='GRUPO 21'),1);
INSERT PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES ((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='POSTULACION_FOVIS_PRESENCIAL' AND des.dcoEtiquetaPlantilla='RCHZ_SOL_POS_FOVIS'),(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='GRUPO 21'),1);
INSERT PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES ((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='POSTULACION_FOVIS_PRESENCIAL' AND des.dcoEtiquetaPlantilla='NTF_SBC_POS_FOVIS_EXT'),(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='GRUPO 21'),1);
INSERT PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES ((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='POSTULACION_FOVIS_WEB' AND des.dcoEtiquetaPlantilla='NTF_VAL_NEXT_RAD_POS_FOVIS_WEB'),(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='GRUPO 21'),1);
INSERT PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES ((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='POSTULACION_FOVIS_WEB' AND des.dcoEtiquetaPlantilla='NTF_RAD_POS_FOVIS_WEB'),(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='GRUPO 21'),1);
INSERT PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES ((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='POSTULACION_FOVIS_WEB' AND des.dcoEtiquetaPlantilla='RCHZ_SOL_LEG_DES_FOVIS_ESCA'),(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='GRUPO 21'),1);

--changeset mamonroy:38
--comment:Insercion de registros en las tablas ValidacionProceso
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-3','VALIDACION_PADRE_DISTINTO_FORMULARIO','POSTULACION_FOVIS_PRESENCIAL','ACTIVO',1,'PADRE_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-3','VALIDACION_MADRE_DISTINTA_FORMULARIO','POSTULACION_FOVIS_PRESENCIAL','ACTIVO',1,'MADRE_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('322-039-1','VALIDACION_PADRE_DISTINTO_FORMULARIO','POSTULACION_FOVIS_WEB','ACTIVO',1,'PADRE_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('322-039-1','VALIDACION_MADRE_DISTINTA_FORMULARIO','POSTULACION_FOVIS_WEB','ACTIVO',1,'MADRE_HOGAR',0);

--changeset ecastano:39
--comment:Se adiciona campo en la tabla SolicitudAsignacion
ALTER TABLE SolicitudAsignacion ADD safComentarioControlInterno VARCHAR(500) NULL;

--changeset ecastano:40
--comment:Se modifica campo en la tabla ActaAsignacionFOVIS
ALTER TABLE ActaAsignacionFOVIS ALTER COLUMN aafNumeroResolucion VARCHAR(20);
ALTER TABLE ActaAsignacionFOVIS ALTER COLUMN aafNumeroOficio VARCHAR(20);

--changeset flopez:41
--comment:Insercion de registros en la tabla ValidacionProceso
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_PRESENCIAL','ACTIVO',1,'JEFE_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_PRESENCIAL','ACTIVO',1,'CONYUGE_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_PRESENCIAL','ACTIVO',1,'ABUELO_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_PRESENCIAL','ACTIVO',1,'BISABUELO_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_PRESENCIAL','ACTIVO',1,'BISNIETO_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_PRESENCIAL','ACTIVO',1,'CUNIADO_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_PRESENCIAL','ACTIVO',1,'HIJO_BIOLOGICO_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_PRESENCIAL','ACTIVO',1,'HIJASTRO_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_PRESENCIAL','ACTIVO',1,'HIJO_ADOPTIVO_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_PRESENCIAL','ACTIVO',1,'HERMANO_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_PRESENCIAL','ACTIVO',1,'MADRE_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_PRESENCIAL','ACTIVO',1,'PADRE_MADRE_ADOPTANTE_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_PRESENCIAL','ACTIVO',1,'PADRE_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_PRESENCIAL','ACTIVO',1,'NIETO_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_PRESENCIAL','ACTIVO',1,'SOBRINO_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_PRESENCIAL','ACTIVO',1,'SUEGRO_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_PRESENCIAL','ACTIVO',1,'TIO_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_PRESENCIAL','ACTIVO',1,'YERNO_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_WEB','ACTIVO',1,'JEFE_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_WEB','ACTIVO',1,'CONYUGE_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_WEB','ACTIVO',1,'ABUELO_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_WEB','ACTIVO',1,'BISABUELO_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_WEB','ACTIVO',1,'BISNIETO_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_WEB','ACTIVO',1,'CUNIADO_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_WEB','ACTIVO',1,'HIJO_BIOLOGICO_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_WEB','ACTIVO',1,'HIJASTRO_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_WEB','ACTIVO',1,'HIJO_ADOPTIVO_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_WEB','ACTIVO',1,'HERMANO_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_WEB','ACTIVO',1,'MADRE_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_WEB','ACTIVO',1,'PADRE_MADRE_ADOPTANTE_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_WEB','ACTIVO',1,'PADRE_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_WEB','ACTIVO',1,'NIETO_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_WEB','ACTIVO',1,'SOBRINO_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_WEB','ACTIVO',1,'SUEGRO_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_WEB','ACTIVO',1,'TIO_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_CONDICION_INVALIDEZ_SIN_REGISTRAR_FOVIS_REGISTRADA_BD','POSTULACION_FOVIS_WEB','ACTIVO',1,'YERNO_HOGAR',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('321-020-5','VALIDACION_JEFE_HOGAR_CABEZA_FAMILIA_CONYUGE_ACTIVO','POSTULACION_FOVIS_WEB','ACTIVO',1,'JEFE_HOGAR',0);

--changeset jocorrea:42
--comment:Se adiciona campo en la tabla SolicitudGestionCruce
ALTER TABLE SolicitudGestionCruce ADD sgcEstado VARCHAR (32) NULL;

--changeset mamonroy:43
--comment:Se actualizan registros en la tabla RequisitoCajaClasificacion
UPDATE RequisitoCajaClasificacion SET rtsTextoAyuda = 'Documento emitido por la entidad competente, en el que se acredita que el hogar o alguno de sus miembros tiene alguna de las siguientes condiciones particulares:<br />-Desplazado inscrito en acción social<br />-Hogar objeto de reubicación zona de alto riesgo no mitigable.<br />-Reubicado archipiélago de San Andrés, Providencia y Santa Catalina.<br />-Víctima de atentado terrorista' WHERE rtsTextoAyuda like 'Documento emitido por la entidad competente, en el que se acredita que el hogar o alguno de sus miembros tiene alguna de las siguientes condiciones particulares:<br />-Desplazado inscrito en acción social<br />-Hogar objeto de reubicación zona de alto riesgo no mitigable.<br />-Reubicado archipiélago de San Andrés, providencia y santa catalina.<br />-Víctima de atentado terrorista' AND rtsRequisito = 97 AND rtsClasificacion = 'HOGAR';
UPDATE RequisitoCajaClasificacion SET rtsTextoAyuda = 'Se revisa:<br />- Monto: Debe coincidir con la información suministrada en el formulario<br />- Debe ser expedida por la entidad financiera respectiva, con indicación del monto del préstamo al cual podrá acceder<br />- La fecha de expedición debe ser menor a 30 días<br />Nota: Se exceptúan para efectos del requisito del ahorro, los hogares con ingresos hasta de dos (2) salarios mínimos legales mensuales vigentes que tengan garantizada la totalidad de la financiación de la vivienda; igualmente las familias reubicadas en el continente como solución del problema de población del departamento de San Andrés, Providencia y Santa Catalina.' WHERE rtsTextoAyuda like 'Se revisa:<br />- Monto: Debe coincidir con la información suministrada en el formulario<br />- Debe ser expedida por la entidad financiera respectiva, con indicación del monto del préstamo al cual podrÃ¡ acceder<br />- La fecha de expedición debe ser menor a 30 días<br />Nota: Se exceptúan para efectos del requisito del ahorro, los hogares con ingresos hasta de dos (2) salarios mínimos legales mensuales vigentes que tengan garantizada la totalidad de la financiación de la vivienda; igualmente las familias reubicadas en el continente como solución del problema de población del departamento de San Andrés, Providencia y Santa Catalina.' AND rtsRequisito = 111 AND rtsClasificacion = 'HOGAR';
UPDATE RequisitoCajaClasificacion SET rtsTextoAyuda = 'Declaración juramentada con fecha de expedición no mayor a 30 días en la que se indica que la persona manifiesta de manera libre, espontánea y de acuerdo con la verdad que es cabeza de familia en concordancia con las siguientes descripciones:<br />- Lo estipulado por la Ley 82 de 1993: quien siendo soltera o casada, ejerce la jefatura de hogar y tiene bajo su cargo, afectiva, económica o socialmente, en forma permanente, hijos menores propios u otras personas incapaces o incapacitadas para trabajar, ya sea por ausencia permanente o incapacidad física, sensorial, psíquica o moral del cónyuge o compañero permanente o deficiencia sustancial de ayuda de los demás miembros del núcleo familiar.<br />- Quien tiene a cargo exclusivo la responsabilidad económica del hogar, cuando los demás miembros tienen incapacidad para trabajar debidamente comprobada' WHERE rtsTextoAyuda like 'Declaración juramentada con fecha de expedición no mayor a 30 días en la que se indica que la persona manifiesta de manera libre, espontánea y de acuerdo con la verdad que es cabeza de familia en concordancia con las siguientes descripciones:<br />- Lo estipulado por la Ley 82 de 1993: quien siendo soltera o casada, ejerce la jefatura de hogar y tiene bajo su cargo, afectiva, económica o socialmente, en forma permanente, hijos menores propios u otras personas incapaces o incapacitadas para trabajar, ya sea por ausencia permanente o incapacidad física, sensorial, síquica o moral del cónyuge o compañero permanente o deficiencia sustancial de ayuda de los demás miembros del núcleo familiar.<br />- Quien tiene a cargo exclusivo la responsabilidad económica del hogar, cuando los demás miembros tienen incapacidad para trabajar debidamente comprobada' AND rtsRequisito = 133 AND rtsClasificacion = 'JEFE_HOGAR';

--changeset jocorrea:44
--comment:Se eliminan y adiciona campo en la tabla CargueArchivoCruceFovisCatBog
ALTER TABLE CargueArchivoCruceFovisCatBog DROP COLUMN cfoApellidos;
ALTER TABLE CargueArchivoCruceFovisCatBog DROP COLUMN cfoNombres;
ALTER TABLE CargueArchivoCruceFovisCatBog ADD cfoApellidosNombres VARCHAR (200) NULL;

--changeset mamonroy:45
--comment:Se actualizan registros en la tabla RequisitoCajaClasificacion
UPDATE RequisitoCajaClasificacion SET rtsTextoAyuda = 'Documento emitido por la entidad competente, en el que se acredita que el hogar o alguno de sus miembros tiene alguna de las siguientes condiciones particulares:<br />-Desplazado inscrito en acción social<br />-Hogar objeto de reubicación zona de alto riesgo no mitigable.<br />-Reubicado archipiélago de San Andrés, Providencia y Santa Catalina.<br />-Víctima de atentado terrorista' WHERE rtsTextoAyuda = 'Documento emitido por la entidad competente, en el que se acredita que el hogar o alguno de sus miembros tiene alguna de las siguientes condiciones particulares:<br />-Desplazado inscrito en acción social<br />-Hogar objeto de reubicación zona de alto riesgo no mitigable.<br />-Reubicado archipiélago de San Andrés, providencia y santa catalina.<br />-Víctima de atentado terrorista';
UPDATE RequisitoCajaClasificacion SET rtsTextoAyuda = 'Se revisa:<br />- Monto: Debe coincidir con la información suministrada en el formulario<br />- Debe ser expedida por la entidad financiera respectiva, con indicación del monto del préstamo al cual podrá acceder<br />- La fecha de expedición debe ser menor a 30 días<br />Nota: Se exceptúan para efectos del requisito del ahorro, los hogares con ingresos hasta de dos (2) salarios mínimos legales mensuales vigentes que tengan garantizada la totalidad de la financiación de la vivienda; igualmente las familias reubicadas en el continente como solución del problema de población del departamento de San Andrés, Providencia y Santa Catalina.' WHERE rtsTextoAyuda = 'Se revisa:<br />- Monto: Debe coincidir con la información suministrada en el formulario<br />- Debe ser expedida por la entidad financiera respectiva, con indicación del monto del préstamo al cual podrÃ¡ acceder<br />- La fecha de expedición debe ser menor a 30 días<br />Nota: Se exceptúan para efectos del requisito del ahorro, los hogares con ingresos hasta de dos (2) salarios mínimos legales mensuales vigentes que tengan garantizada la totalidad de la financiación de la vivienda; igualmente las familias reubicadas en el continente como solución del problema de población del departamento de San Andrés, Providencia y Santa Catalina.'; 
UPDATE RequisitoCajaClasificacion SET rtsTextoAyuda = 'Declaración juramentada con fecha de expedición no mayor a 30 días en la que se indica que la persona manifiesta de manera libre, espontánea y de acuerdo con la verdad que es cabeza de familia en concordancia con las siguientes descripciones:<br />- Lo estipulado por la Ley 82 de 1993: quien siendo soltera o casada, ejerce la jefatura de hogar y tiene bajo su cargo, afectiva, económica o socialmente, en forma permanente, hijos menores propios u otras personas incapaces o incapacitadas para trabajar, ya sea por ausencia permanente o incapacidad física, sensorial, psíquica o moral del cónyuge o compañero permanente o deficiencia sustancial de ayuda de los demás miembros del núcleo familiar.<br />- Quien tiene a cargo exclusivo la responsabilidad económica del hogar, cuando los demás miembros tienen incapacidad para trabajar debidamente comprobada' WHERE rtsTextoAyuda = 'Declaración juramentada con fecha de expedición no mayor a 30 días en la que se indica que la persona manifiesta de manera libre, espontánea y de acuerdo con la verdad que es cabeza de familia en concordancia con las siguientes descripciones:<br />- Lo estipulado por la Ley 82 de 1993: quien siendo soltera o casada, ejerce la jefatura de hogar y tiene bajo su cargo, afectiva, económica o socialmente, en forma permanente, hijos menores propios u otras personas incapaces o incapacitadas para trabajar, ya sea por ausencia permanente o incapacidad física, sensorial, síquica o moral del cónyuge o compañero permanente o deficiencia sustancial de ayuda de los demás miembros del núcleo familiar.<br />- Quien tiene a cargo exclusivo la responsabilidad económica del hogar, cuando los demás miembros tienen incapacidad para trabajar debidamente comprobada';

--changeset alquintero:46
--comment: Se adiciona campo en la tabla SolicitudPostulacion
ALTER TABLE SolicitudPostulacion ADD spoObservacionesWeb VARCHAR(500) NULL;

--changeset alquintero:47
--comment:Se modifica campo en la tabla ParametrizacionModalidad
ALTER TABLE ParametrizacionModalidad ALTER COLUMN pmoTopeSMLMV NUMERIC (4,1) NULL;

--changeset alquintero:48
--comment:Se adiciona campo en la tabla PostulacionFOVIS
ALTER TABLE PostulacionFOVIS ADD pofValorCalculadoSFV NUMERIC (19,5) NULL;

--changeset flopez:49
--comment:Insercion de registros en la tabla ParametrizacionMetodoAsignacion
INSERT ParametrizacionMetodoAsignacion (pmaSedeCajaCompensacion,pmaProceso,pmaMetodoAsignacion,pmaUsuario,pmaGrupo) VALUES ((select cnsValor from Constante where cnsNombre = 'CAJA_COMPENSACION_ID'),'ASIGNACION_FOVIS','PREDEFINIDO','coordinadorinternofovis@heinsohn.com.co','CooConInFov');

--changeset flopez:50
--comment:Se adiciona campo en la tabla CargueArchivoCruceFovisSisben y CargueArchivoCruceFovisUnidos
ALTER TABLE CargueArchivoCruceFovisSisben ALTER COLUMN cfsParantesco VARCHAR(100) NULL;
ALTER TABLE CargueArchivoCruceFovisUnidos ALTER COLUMN cfuParantesco VARCHAR(100) NULL;