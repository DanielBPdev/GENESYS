--liquibase formatted sql

--changeset Heinsohn:01
--comment: Creacion de las tablas de la base de datos asopagos
--Creacion de la tabla AFP
CREATE TABLE AFP(
	afpId smallint IDENTITY(1,1) NOT NULL,
	afpNombre varchar(150) NOT NULL,
	afpCodigoPila varchar(10) NULL,
 CONSTRAINT PK_AFP_afpId PRIMARY KEY (afpId)
);
ALTER TABLE AFP ADD CONSTRAINT UK_AFP_afpCodigoPila UNIQUE (afpCodigoPila);

--Creacion de la tabla ARL    
CREATE TABLE ARL(
	arlId smallint IDENTITY(1,1) NOT NULL,
	arlNombre varchar(25) NOT NULL,
 CONSTRAINT PK_ARL_arlId PRIMARY KEY (arlId)
);

--Creacion de la tabla Departamento    
CREATE TABLE Departamento(
	depId smallint IDENTITY(1,1) NOT NULL,
	depCodigo varchar(2) NOT NULL,
	depIndicativoTelefoniaFija varchar(2) NOT NULL,
	depNombre varchar(100) NOT NULL,
	depExcepcionAplicaFOVIS bit NULL,
 CONSTRAINT PK_Departamento_depId PRIMARY KEY (depId)
);

--Creacion de la tabla Municipio    
CREATE TABLE Municipio(
	munId smallint IDENTITY(1,1) NOT NULL,
	munCodigo varchar(6) NOT NULL,
	munNombre varchar(50) NOT NULL,
	munDepartamento smallint NOT NULL,
 CONSTRAINT PK_Municipio_munId PRIMARY KEY (munId)
);
ALTER TABLE Municipio ADD CONSTRAINT FK_Municipio_munDepartamento FOREIGN KEY(munDepartamento) REFERENCES Departamento (depId);

--Creacion de la tabla CodigoCIIU    
CREATE TABLE CodigoCIIU(
	ciiId smallint IDENTITY(1,1) NOT NULL,
	ciiCodigo varchar(4) NOT NULL,
	ciiDescripcion varchar(255) NOT NULL,
	ciiCodigoSeccion varchar(1) NULL,
	ciiDescripcionSeccion varchar(200) NULL,
	ciiCodigoDivision varchar(2) NULL,
	ciiDescripcionDivision varchar(250) NULL,
	ciiCodigoGrupo varchar(3) NULL,
	ciiDescripcionGrupo varchar(200) NULL,
 CONSTRAINT PK_CodigoCIIU_ciiId PRIMARY KEY (ciiId)
);

--Creacion de la tabla DiasFestivos    
CREATE TABLE DiasFestivos(
	pifId bigint IDENTITY(1,1) NOT NULL,
	pifConcepto varchar(100) NOT NULL,
	pifFecha date NOT NULL,
 CONSTRAINT PK_PilaDiasFestivos_pifId PRIMARY KEY(pifId)
);

--Creacion de la tabla GradoAcademico    
CREATE TABLE GradoAcademico(
	graId smallint IDENTITY(1,1) NOT NULL,
	graNombre varchar(20) NOT NULL,
	graNivelEducativo varchar(43) NOT NULL,
 CONSTRAINT PK_GradoAcademico_graId PRIMARY KEY (graId)
);

--Creacion de la tabla OcupacionProfesion    
CREATE TABLE OcupacionProfesion(
	ocuId int IDENTITY(1,1) NOT NULL,
	ocuNombre varchar(100) NOT NULL,
 CONSTRAINT PK_OcupacionProfesion_ocuId PRIMARY KEY (ocuId)
);

--Creacion de la tabla CajaCompensacion    
CREATE TABLE CajaCompensacion(
	ccfId int IDENTITY(1,1) NOT NULL,
	ccfHabilitado bit NOT NULL,
	ccfMetodoGeneracionEtiquetas varchar(20) NOT NULL,
	ccfNombre varchar(100) NOT NULL,
	ccfSocioAsopagos bit NOT NULL,
	ccfDepartamento smallint NOT NULL,
	ccfCodigo varchar(5) NOT NULL,
	ccfCodigoRedeban int NULL,
 CONSTRAINT PK_CajaCompensacion_ccfId PRIMARY KEY (ccfId)
 );
 ALTER TABLE CajaCompensacion ADD CONSTRAINT FK_CajaCompensacion_ccfDepartamento FOREIGN KEY(ccfDepartamento) REFERENCES Departamento (depId);

--Creacion de la tabla Requisito    
CREATE TABLE Requisito(
	reqId bigint IDENTITY(1,1) NOT NULL,
	reqDescripcion varchar(200) NOT NULL,
	reqEstado varchar(20) NOT NULL,
 CONSTRAINT PK_Requisito_reqId PRIMARY KEY (reqId)
);

--Creacion de la tabla ElementoDireccion    
CREATE TABLE ElementoDireccion(
	eldId bigint IDENTITY(1,1) NOT NULL,
	eldNombre varchar(20) NOT NULL,
 CONSTRAINT PK_ElementoDireccion_eldId PRIMARY KEY (eldId)
);

--Creacion de la tabla TipoVia    
CREATE TABLE TipoVia(
	tviId bigint IDENTITY(1,1) NOT NULL,
	tviNombreVia varchar(20) NOT NULL,
 CONSTRAINT PK_TipoVia_tviId PRIMARY KEY (tviId)
);

--Creacion de la tabla Beneficio    
CREATE TABLE Beneficio(
	befId bigint IDENTITY(1,1) NOT NULL,
	befTipoBeneficio varchar(16) NOT NULL,
	befVigenciaFiscal bit NOT NULL,
	befFechaVigenciaInicio date NULL,
	befFechaVigenciaFin date NULL,
 CONSTRAINT PK_Beneficio_befId PRIMARY KEY (befId)
);
 
--Creacion de la tabla Banco    
CREATE TABLE Banco(
	banId bigint IDENTITY(1,1) NOT NULL,
	banCodigoPILA varchar(4) NOT NULL,
	banNombre varchar(255) NOT NULL,
	banMedioPago bit NOT NULL,
 CONSTRAINT PK_Banco_banId PRIMARY KEY (banId)
);

--Creacion de la tabla OperadorInformacion    
CREATE TABLE OperadorInformacion(
	oinId bigint IDENTITY(1,1) NOT NULL,
	oinCodigo varchar(2) NOT NULL,
	oinNombre varchar(75) NOT NULL,
	oinOperadorActivo bit NOT NULL,
 CONSTRAINT PK_OperadorInformacion_oinId PRIMARY KEY (oinId )
);

--Creacion de la tabla TipoTenencia    
CREATE TABLE TipoTenencia(
	tenId bigint IDENTITY(1,1) NOT NULL,
	tenCodigo smallint NOT NULL,
	tenNombre varchar(255) NOT NULL,
	tenActivo bit NOT NULL,
 CONSTRAINT PK_TipoTenencia_tenId PRIMARY KEY (tenId)
 );
 
--Creacion de la tabla TipoInfraestructura    
CREATE TABLE TipoInfraestructura(
	tifId bigint IDENTITY(1,1) NOT NULL,
	tifCodigo varchar(3) NOT NULL,
	tifNombre varchar(255) NOT NULL,
	tifMedidaCapacidad varchar(40) NOT NULL,
	tifActivo bit NOT NULL,
 CONSTRAINT PK_TipoInfraestructura_tifId PRIMARY KEY (tifId)
);

--Creacion de la tabla Infraestructura    
CREATE TABLE Infraestructura(
	infId bigint IDENTITY(1,1) NOT NULL,
	infCodigoParaSSF varchar(13) NOT NULL,
	infCodigoParaCCF varchar(12) NOT NULL,
	infConsecutivoInfraestructura smallint NOT NULL,
	infNombre varchar(255) NOT NULL,
	infTipoInfraestructura bigint NOT NULL,
	infZona varchar(255) NOT NULL,
	infDireccion varchar(300) NOT NULL,
	infAreaGeografica varchar(6) NOT NULL,
	infMunicipio bigint NOT NULL,
	infTipoTenencia bigint NOT NULL,
	infLatitud numeric(9, 6) NOT NULL,
	infLongitud numeric(9, 6) NOT NULL,
	infActivo bit NOT NULL,
	infCapacidadEstimada numeric(7, 2) NULL,
 CONSTRAINT PK_Infraestructura_infId PRIMARY KEY (infId)
);
ALTER TABLE Infraestructura ADD CONSTRAINT FK_Infraestructura_infTipoInfraestructura FOREIGN KEY(infTipoInfraestructura) REFERENCES TipoInfraestructura (tifId);
ALTER TABLE Infraestructura ADD CONSTRAINT FK_Infraestructura_infTipoTenencia FOREIGN KEY(infTipoTenencia) REFERENCES TipoTenencia (tenId);
