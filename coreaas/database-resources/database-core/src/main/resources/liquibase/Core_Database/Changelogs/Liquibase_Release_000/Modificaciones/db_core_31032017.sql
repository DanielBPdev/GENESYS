--liquibase formatted sql

--changeset atoro:01
--comment: Eliminación de registros en la tabla validacionProceso
DELETE FROM validacionProceso WHERE vapBloque ='NOVEDAD_ANULACION_AFILIACION';

--changeset clmarin:02
--comment: Eliminación de registros en la tabla variablecomunicado
DELETE FROM variablecomunicado WHERE vcoClave IN('${nombresYApellidosDelAfiliadoPrincipal}','${direccionResidencia}','${telefono}') AND vcoPlantillaComunicado = 2 AND vcoId IN(269,270,271);

--changeset atoro:03
--comment: Creacion de nuevos campos en las tablas GradoAcademico, Beneficiario, Persona y GrupoFamiliar
ALTER TABLE GradoAcademico ADD graNivelEducativo varchar (20) NULL
ALTER TABLE Beneficiario ADD benFechaRetiro date NULL
ALTER TABLE Beneficiario ADD benFechaInicioSociedadConyugal date NULL
ALTER TABLE Beneficiario ADD benFechaFinSociedadConyugal date NULL
ALTER TABLE Persona ADD perFechaFallecido date NULL
ALTER TABLE GrupoFamiliar ADD grfImnerbargable bit NULL

--changeset alopez:04
--comment: Actualizacion de parametro de KEYCLOAK_ENDPOINT y IDM_SERVER_URL
UPDATE parametro SET prmValor = 'http://50.23.54.143:8082/auth/realms/{realm}' WHERE prmNombre = 'KEYCLOAK_ENDPOINT'
UPDATE parametro SET prmValor = 'http://50.23.54.143:8082/auth/' WHERE prmNombre = 'IDM_SERVER_URL' 


