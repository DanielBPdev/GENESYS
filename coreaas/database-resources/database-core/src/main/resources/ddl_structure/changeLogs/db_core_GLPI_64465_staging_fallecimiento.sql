--liquibase formatted sql
--changeset JUAN MONTAÑA
--comment:Crear Tablas para Staging Fallecimiento Core

EXEC('
if not exists (select * from INFORMATION_SCHEMA.SCHEMATA where SCHEMA_NAME = ''fall'')
	begin
		execute sp_executesql N''create schema fall''
	end

if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = ''MedioDePago'' and TABLE_SCHEMA = ''fall'')
	begin
		CREATE TABLE fall.MedioDePago (mdpId BIGINT, entidad VARCHAR (50));
	end


if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = ''Persona'' and TABLE_SCHEMA = ''fall'')
	begin
		CREATE TABLE fall.Persona (perId INT, entidad VARCHAR (50));
	end

if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = ''Afiliado'' and TABLE_SCHEMA = ''fall'')
	begin
		CREATE TABLE fall.Afiliado (afiId INT, entidad VARCHAR (50));
	end

if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = ''GrupoFamiliar'' and TABLE_SCHEMA = ''fall'')
	begin
		CREATE TABLE fall.GrupoFamiliar (grfId INT, entidad VARCHAR (50));
	end

if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = ''Empresa'' and TABLE_SCHEMA = ''fall'')
	begin
		CREATE TABLE fall.Empresa (empId INT, entidad VARCHAR (50));
	end

if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = ''SucursalEmpresa'' and TABLE_SCHEMA = ''fall'')
	begin
		CREATE TABLE fall.SucursalEmpresa (sueId INT, entidad VARCHAR (50));
	end

if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = ''Empleador'' and TABLE_SCHEMA = ''fall'')
	begin
		CREATE TABLE fall.Empleador (empId INT, entidad VARCHAR (50));
	end

if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = ''RolAfiliado'' and TABLE_SCHEMA = ''fall'')
	begin
		CREATE TABLE fall.RolAfiliado (roaId INT, entidad VARCHAR (50));
	end

if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = ''AdministradorSubsidio'' and TABLE_SCHEMA = ''fall'')
	begin
		CREATE TABLE fall.AdministradorSubsidio (asuId INT, entidad VARCHAR (50));
	end

if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = ''AdminSubsidioGrupo'' and TABLE_SCHEMA = ''fall'')
	begin
		CREATE TABLE fall.AdminSubsidioGrupo (asgId INT, entidad VARCHAR (50));
	end

if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = ''Solicitud'' and TABLE_SCHEMA = ''fall'')
	begin
		CREATE TABLE fall.Solicitud (solId BIGINT, entidad VARCHAR (50));
	end

if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = ''ParametrizacionNovedad'' and TABLE_SCHEMA = ''fall'')
	begin
		CREATE TABLE fall.ParametrizacionNovedad (novId BIGINT, entidad VARCHAR (50));
	end

if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = ''SolicitudNovedad'' and TABLE_SCHEMA = ''fall'')
	begin
		CREATE TABLE fall.SolicitudNovedad (snoId BIGINT, entidad VARCHAR (50));
	end

if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = ''SolicitudNovedadPersona'' and TABLE_SCHEMA = ''fall'')
	begin
		CREATE TABLE fall.SolicitudNovedadPersona (snpId BIGINT, entidad VARCHAR (50));
	end

if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = ''NovedadDetalle'' and TABLE_SCHEMA = ''fall'')
	begin
		CREATE TABLE fall.NovedadDetalle (nopId BIGINT, entidad VARCHAR (50));
	end

if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = ''AporteGeneral'' and TABLE_SCHEMA = ''fall'')
	begin
		CREATE TABLE fall.AporteGeneral (apgId BIGINT, entidad VARCHAR (50));
	end

if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = ''AporteDetallado'' and TABLE_SCHEMA = ''fall'')
	begin
		CREATE TABLE fall.AporteDetallado (apdId BIGINT, entidad VARCHAR (50));
	end

if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = ''SocioEmpleador'' and TABLE_SCHEMA = ''fall'')
	begin
	CREATE TABLE fall.SocioEmpleador (semId INT, entidad VARCHAR (50));
	end

if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = ''SocioEmpleador'' and TABLE_SCHEMA = ''fall'')
	begin
		CREATE TABLE fall.PersonaDetalle (pedId INT, entidad VARCHAR (50));
	end

if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = ''BeneficiarioDetalle'' and TABLE_SCHEMA = ''fall'')
	begin
		CREATE TABLE fall.BeneficiarioDetalle (bedId INT, entidad VARCHAR (50));
	end

if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = ''Beneficiario'' and TABLE_SCHEMA = ''fall'')
	begin
		CREATE TABLE fall.Beneficiario (benId INT, entidad VARCHAR (50));
	end

if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = ''BeneficioEmpleador'' and TABLE_SCHEMA = ''fall'')
	begin
		CREATE TABLE fall.BeneficioEmpleador (bemId INT, entidad VARCHAR (50));
	end

if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = ''CondicionInvalidez'' and TABLE_SCHEMA = ''fall'')
	begin
		CREATE TABLE fall.CondicionInvalidez (coiId INT, entidad VARCHAR (50));
	end

if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = ''SolicitudNovedadEmpleador'' and TABLE_SCHEMA = ''fall'')
	begin
		CREATE TABLE fall.SolicitudNovedadEmpleador (sneId BIGINT, entidad VARCHAR (50));
	end

if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = ''CertificadoEscolarBeneficiario'' and TABLE_SCHEMA = ''fall'')
	begin
		CREATE TABLE fall.CertificadoEscolarBeneficiario (cebId BIGINT, entidad VARCHAR (50));
	end

if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = ''CargueBloqueoCuotaMonetaria'' and TABLE_SCHEMA = ''fall'')
	begin
		CREATE TABLE fall.CargueBloqueoCuotaMonetaria (cabId INT, entidad VARCHAR (50));
	end

if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = ''BloqueoBeneficiarioCuotaMonetaria'' and TABLE_SCHEMA = ''fall'')
	begin
		CREATE TABLE fall.BloqueoBeneficiarioCuotaMonetaria (bbcId INT, entidad VARCHAR (50));
	end

if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = ''ItemChequeo'' and TABLE_SCHEMA = ''fall'')
	begin
		CREATE TABLE fall.ItemChequeo (ichId BIGINT, entidad VARCHAR (50));
	end

if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = ''SolicitudAfiliacionPersona'' and TABLE_SCHEMA = ''fall'')
	begin
		CREATE TABLE fall.SolicitudAfiliacionPersona (sapId BIGINT, entidad VARCHAR (50));
	end')