--liquibase formatted sql
--changeset JUAN MONTAÑA
--comment:Crear Tablas para Staging Fallecimiento Core_aud

EXEC ('IF NOT EXISTS ( SELECT 1 FROM sys.schemas WHERE name = ''fall'' )
	EXEC (''CREATE SCHEMA fall'');

IF NOT EXISTS ( SELECT 1 FROM sys.Tables a INNER JOIN sys.schemas b ON a.schema_id = b.schema_id WHERE a.name = ''HistoricoRevisionFallecimiento'' AND b.name = ''fall'')
	CREATE TABLE fall.HistoricoRevisionFallecimiento (tableName VARCHAR (50), identificador VARCHAR (6),  REV BIGINT, id BIGINT, fechaConsulta DATETIME);

IF NOT EXISTS ( SELECT 1 FROM sys.Tables a INNER JOIN sys.schemas b ON a.schema_id = b.schema_id WHERE a.name = ''RevisionFallecimientoBuffer'' AND b.name = ''fall'')
	CREATE TABLE fall.RevisionFallecimientoBuffer (tableName VARCHAR (50), identificador VARCHAR (6), REV BIGINT, id BIGINT);

IF NOT EXISTS ( SELECT 1 FROM sys.Tables a INNER JOIN sys.schemas b ON a.schema_id = b.schema_id WHERE a.name = ''revisionGroup'' AND b.name = ''fall'')
	CREATE TABLE fall.revisionGroup (REV BIGINT);

IF NOT EXISTS ( SELECT 1 FROM sys.Tables a INNER JOIN sys.schemas b ON a.schema_id = b.schema_id WHERE a.name = ''MedioDePago'' AND b.name = ''fall'')
	CREATE TABLE fall.MedioDePago (mdpId BIGINT, s1 VARCHAR (500));

IF NOT EXISTS ( SELECT 1 FROM sys.Tables a INNER JOIN sys.schemas b ON a.schema_id = b.schema_id WHERE a.name = ''Persona'' AND b.name = ''fall'')
	CREATE TABLE fall.Persona (perId INT, s1 VARCHAR (500));

IF NOT EXISTS ( SELECT 1 FROM sys.Tables a INNER JOIN sys.schemas b ON a.schema_id = b.schema_id WHERE a.name = ''Afiliado'' AND b.name = ''fall'')
	CREATE TABLE fall.Afiliado (afiId INT, s1 VARCHAR (500));

IF NOT EXISTS ( SELECT 1 FROM sys.Tables a INNER JOIN sys.schemas b ON a.schema_id = b.schema_id WHERE a.name = ''GrupoFamiliar'' AND b.name = ''fall'')
	CREATE TABLE fall.GrupoFamiliar (grfId INT, s1 VARCHAR (500));

IF NOT EXISTS ( SELECT 1 FROM sys.Tables a INNER JOIN sys.schemas b ON a.schema_id = b.schema_id WHERE a.name = ''Empresa'' AND b.name = ''fall'')
	CREATE TABLE fall.Empresa (empId INT, s1 VARCHAR (500));

IF NOT EXISTS ( SELECT 1 FROM sys.Tables a INNER JOIN sys.schemas b ON a.schema_id = b.schema_id WHERE a.name = ''SucursalEmpresa'' AND b.name = ''fall'')
	CREATE TABLE fall.SucursalEmpresa (sueId INT, s1 VARCHAR (500));

IF NOT EXISTS ( SELECT 1 FROM sys.Tables a INNER JOIN sys.schemas b ON a.schema_id = b.schema_id WHERE a.name = ''Empleador'' AND b.name = ''fall'')
	CREATE TABLE fall.Empleador (empId INT, s1 VARCHAR (500));

IF NOT EXISTS ( SELECT 1 FROM sys.Tables a INNER JOIN sys.schemas b ON a.schema_id = b.schema_id WHERE a.name = ''RolAfiliado'' AND b.name = ''fall'')
	CREATE TABLE fall.RolAfiliado (roaId INT, s1 VARCHAR (500));

IF NOT EXISTS ( SELECT 1 FROM sys.Tables a INNER JOIN sys.schemas b ON a.schema_id = b.schema_id WHERE a.name = ''AdministradorSubsidio'' AND b.name = ''fall'')
	CREATE TABLE fall.AdministradorSubsidio (asuId INT, s1 VARCHAR (500));

IF NOT EXISTS ( SELECT 1 FROM sys.Tables a INNER JOIN sys.schemas b ON a.schema_id = b.schema_id WHERE a.name = ''AdminSubsidioGrupo'' AND b.name = ''fall'')
	CREATE TABLE fall.AdminSubsidioGrupo (asgId INT, s1 VARCHAR (500));

IF NOT EXISTS ( SELECT 1 FROM sys.Tables a INNER JOIN sys.schemas b ON a.schema_id = b.schema_id WHERE a.name = ''Solicitud'' AND b.name = ''fall'')
	CREATE TABLE fall.Solicitud (solId BIGINT, s1 VARCHAR (500));

IF NOT EXISTS ( SELECT 1 FROM sys.Tables a INNER JOIN sys.schemas b ON a.schema_id = b.schema_id WHERE a.name = ''ParametrizacionNovedad'' AND b.name = ''fall'')
	CREATE TABLE fall.ParametrizacionNovedad (novId BIGINT, s1 VARCHAR (500));

IF NOT EXISTS ( SELECT 1 FROM sys.Tables a INNER JOIN sys.schemas b ON a.schema_id = b.schema_id WHERE a.name = ''SolicitudNovedad'' AND b.name = ''fall'')
	CREATE TABLE fall.SolicitudNovedad (snoId BIGINT, s1 VARCHAR (500));

IF NOT EXISTS ( SELECT 1 FROM sys.Tables a INNER JOIN sys.schemas b ON a.schema_id = b.schema_id WHERE a.name = ''SolicitudNovedadPersona'' AND b.name = ''fall'')
	CREATE TABLE fall.SolicitudNovedadPersona (snpId BIGINT, s1 VARCHAR (500));

IF NOT EXISTS ( SELECT 1 FROM sys.Tables a INNER JOIN sys.schemas b ON a.schema_id = b.schema_id WHERE a.name = ''NovedadDetalle'' AND b.name = ''fall'')
	CREATE TABLE fall.NovedadDetalle (nopId BIGINT, s1 VARCHAR (500));

IF NOT EXISTS ( SELECT 1 FROM sys.Tables a INNER JOIN sys.schemas b ON a.schema_id = b.schema_id WHERE a.name = ''AporteGeneral'' AND b.name = ''fall'')
	CREATE TABLE fall.AporteGeneral (apgId BIGINT, s1 VARCHAR (500));

IF NOT EXISTS ( SELECT 1 FROM sys.Tables a INNER JOIN sys.schemas b ON a.schema_id = b.schema_id WHERE a.name = ''AporteDetallado'' AND b.name = ''fall'')
	CREATE TABLE fall.AporteDetallado (apdId BIGINT, s1 VARCHAR (500));

IF NOT EXISTS ( SELECT 1 FROM sys.Tables a INNER JOIN sys.schemas b ON a.schema_id = b.schema_id WHERE a.name = ''SocioEmpleador'' AND b.name = ''fall'')
	CREATE TABLE fall.SocioEmpleador (semId INT, s1 VARCHAR (500));

IF NOT EXISTS ( SELECT 1 FROM sys.Tables a INNER JOIN sys.schemas b ON a.schema_id = b.schema_id WHERE a.name = ''PersonaDetalle'' AND b.name = ''fall'')
	CREATE TABLE fall.PersonaDetalle (pedId INT, s1 VARCHAR (500));

IF NOT EXISTS ( SELECT 1 FROM sys.Tables a INNER JOIN sys.schemas b ON a.schema_id = b.schema_id WHERE a.name = ''BeneficiarioDetalle'' AND b.name = ''fall'')
	CREATE TABLE fall.BeneficiarioDetalle (bedId INT, s1 VARCHAR (500));

IF NOT EXISTS ( SELECT 1 FROM sys.Tables a INNER JOIN sys.schemas b ON a.schema_id = b.schema_id WHERE a.name = ''Beneficiario'' AND b.name = ''fall'')
	CREATE TABLE fall.Beneficiario (benId INT, s1 VARCHAR (500));

IF NOT EXISTS ( SELECT 1 FROM sys.Tables a INNER JOIN sys.schemas b ON a.schema_id = b.schema_id WHERE a.name = ''BeneficioEmpleador'' AND b.name = ''fall'')
	CREATE TABLE fall.BeneficioEmpleador (bemId INT, s1 VARCHAR (500));

IF NOT EXISTS ( SELECT 1 FROM sys.Tables a INNER JOIN sys.schemas b ON a.schema_id = b.schema_id WHERE a.name = ''CondicionInvalidez'' AND b.name = ''fall'')
	CREATE TABLE fall.CondicionInvalidez (coiId INT, s1 VARCHAR (500));

IF NOT EXISTS ( SELECT 1 FROM sys.Tables a INNER JOIN sys.schemas b ON a.schema_id = b.schema_id WHERE a.name = ''SolicitudNovedadEmpleador'' AND b.name = ''fall'')
	CREATE TABLE fall.SolicitudNovedadEmpleador (sneId BIGINT, s1 VARCHAR (500));

IF NOT EXISTS ( SELECT 1 FROM sys.Tables a INNER JOIN sys.schemas b ON a.schema_id = b.schema_id WHERE a.name = ''CertificadoEscolarBeneficiario'' AND b.name = ''fall'')
	CREATE TABLE fall.CertificadoEscolarBeneficiario (cebId BIGINT, s1 VARCHAR (500));

IF NOT EXISTS ( SELECT 1 FROM sys.Tables a INNER JOIN sys.schemas b ON a.schema_id = b.schema_id WHERE a.name = ''CargueBloqueoCuotaMonetaria'' AND b.name = ''fall'')
	CREATE TABLE fall.CargueBloqueoCuotaMonetaria (cabId INT, s1 VARCHAR (500));

IF NOT EXISTS ( SELECT 1 FROM sys.Tables a INNER JOIN sys.schemas b ON a.schema_id = b.schema_id WHERE a.name = ''BloqueoBeneficiarioCuotaMonetaria'' AND b.name = ''fall'')
	CREATE TABLE fall.BloqueoBeneficiarioCuotaMonetaria (bbcId INT, s1 VARCHAR (500));

IF NOT EXISTS ( SELECT 1 FROM sys.Tables a INNER JOIN sys.schemas b ON a.schema_id = b.schema_id WHERE a.name = ''ItemChequeo'' AND b.name = ''fall'')
	CREATE TABLE fall.ItemChequeo (ichId BIGINT, s1 VARCHAR (500));

IF NOT EXISTS ( SELECT 1 FROM sys.Tables a INNER JOIN sys.schemas b ON a.schema_id = b.schema_id WHERE a.name = ''SolicitudAfiliacionPersona'' AND b.name = ''fall'')
	CREATE TABLE fall.SolicitudAfiliacionPersona (sapId BIGINT, s1 VARCHAR (500));');