--liquibase formatted sql

--changeset sbrinez:01
--comment: Creacion tabla PropietarioArchivo
CREATE TABLE PropietarioArchivo(
	praId bigint IDENTITY(1,1) NOT NULL,
	praTipoIdentificacion varchar(20) NOT NULL,
	praNumeroIdentificacion varchar(16) NOT NULL,
	praTipoPropietario varchar(30) NULL,
 CONSTRAINT PK_PropietarioArchivo_praId PRIMARY KEY CLUSTERED (praId)  
);

--changeset sbrinez:03
--comment: Creacion tabla ArchivoAlmacenado
CREATE TABLE ArchivoAlmacenado(
	araId bigint IDENTITY(1,1) NOT NULL,
	araSolicitud bigint NULL,
	araRequisito bigint NULL,
	araPropietarioArchivo bigint NULL,
 CONSTRAINT PK_ArchivoAlmacenado_araId PRIMARY KEY CLUSTERED (araId)  
);

--changeset sbrinez:04
--comment: Relaciones tabla ArchivoAlmacenado
ALTER TABLE ArchivoAlmacenado ADD CONSTRAINT FK_ArchivoAlmacenado_araSolicitud FOREIGN KEY (araSolicitud) REFERENCES Solicitud (solId); 
ALTER TABLE ArchivoAlmacenado ADD CONSTRAINT FK_ArchivoAlmacenado_araRequisito FOREIGN KEY (araRequisito) REFERENCES Requisito (reqId); 
ALTER TABLE ArchivoAlmacenado ADD CONSTRAINT FK_ArchivoAlmacenado_araPropietarioArchivo FOREIGN KEY (araPropietarioArchivo) REFERENCES PropietarioArchivo (praId); 

--changeset sbrinez:05
--comment: Creacion tabla VersionArchivo
CREATE TABLE VersionArchivo(
	veaId bigint IDENTITY(1,1) NOT NULL,
	veaArchivoAlmacenado bigint NOT NULL,
	veaIdentificador varchar(255) NOT NULL,
	veaVersion varchar(20) NULL,
	veaFecha date NOT NULL,
	veaMetadata varchar(300) NULL,
	
 CONSTRAINT PK_VersionArchivo_veaId PRIMARY KEY CLUSTERED (veaId)  
);

--changeset sbrinez:06
--comment: Relacion tabla VersionArchivo
ALTER TABLE VersionArchivo ADD CONSTRAINT FK_VersionArchivo_veaArchivoAlmacenado FOREIGN KEY (veaArchivoAlmacenado) REFERENCES ArchivoAlmacenado (araId); 

--changeset sbrinez:07
--comment: Creacion parametro SISTEMA_ALMACENAMIENTO_ARCHIVOS 
INSERT INTO Parametro
(prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion)
VALUES('SISTEMA_ALMACENAMIENTO_ARCHIVOS', 'ALFRESCO', 0, 'ALMACENAMIENTO_ARCHIVOS', 'Sistema de almacenamiento de archivos a usar por la solución');