--liquibase formatted sql

--changeset clmarin:01
--comment: Actualizacion tabla PlantillaComunicado
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${contenido}</p>') WHERE pcoEtiqueta = 'NTF_APR_COR_APT';

--changeset abaquero:01
--comment: Creación del esquema "cartera" y tablas temporales para manejo de traza histórica en Cartera y CarteraDependiente
CREATE SCHEMA [cartera]

CREATE TABLE cartera.CarteraTemp (
	carId bigint,
	carDeudaPresunta numeric(19,5),
	carEstadoCartera varchar(6),
	carEstadoOperacion varchar(10),
	carFechaCreacion datetime,
	carPersona bigint,
	carMetodo varchar(8),
	carPeriodoDeuda date,
	carRiesgoIncobrabilidad varchar(48),
	carTipoAccionCobro varchar(4),
	carTipoDeuda varchar(11),
	carTipoLineaCobro varchar(3),
	carTipoSolicitante varchar(13),
	carFechaAsignacionAccion datetime,
	carUsuarioTraspaso varchar(255), 
	carIdTemp bigint identity
)

CREATE TABLE cartera.CarteraDependienteTemp (
	cadId bigint,
	cadDeudaPresunta numeric(19,5),
	cadEstadoOperacion varchar(10),
	cadCartera bigint,
	cadPersona bigint,
	cadDeudaReal numeric(19,5),
	cadAgregadoManual bigint, 
	cadEliminarRegistro bit, 
	cadIdTemp bigint identity
)

--changeset clmarin:02
--comment: Actualizacion tabla PlantillaComunicado
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${contenido}</p>') WHERE pcoEtiqueta = 'NTF_APR_DVL_APT';