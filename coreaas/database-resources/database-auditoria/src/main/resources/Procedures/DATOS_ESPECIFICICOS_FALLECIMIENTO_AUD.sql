CREATE PROCEDURE [dbo].[DATOS_ESPECIFICICOS_FALLECIMIENTO_AUD]

(
@revInicio BIGINT,
@RevisionTimeStampFin BIGINT
)

AS
BEGIN

TRUNCATE TABLE fall.MedioDePago;
TRUNCATE TABLE fall.GrupoFamiliar;
TRUNCATE TABLE fall.Empresa;
TRUNCATE TABLE fall.SucursalEmpresa;
TRUNCATE TABLE fall.Empleador;
TRUNCATE TABLE fall.RolAfiliado;
TRUNCATE TABLE fall.AdministradorSubsidio;
TRUNCATE TABLE fall.AdminSubsidioGrupo;
TRUNCATE TABLE fall.Solicitud;
TRUNCATE TABLE fall.ParametrizacionNovedad;
TRUNCATE TABLE fall.SolicitudNovedad;
TRUNCATE TABLE fall.SolicitudNovedadPersona;
TRUNCATE TABLE fall.NovedadDetalle;
TRUNCATE TABLE fall.AporteGeneral;
TRUNCATE TABLE fall.AporteDetallado;
TRUNCATE TABLE fall.SocioEmpleador;
TRUNCATE TABLE fall.PersonaDetalle;
TRUNCATE TABLE fall.BeneficiarioDetalle;
TRUNCATE TABLE fall.Beneficiario;
TRUNCATE TABLE fall.BeneficioEmpleador;
TRUNCATE TABLE fall.CondicionInvalidez;
TRUNCATE TABLE fall.SolicitudNovedadEmpleador;
TRUNCATE TABLE fall.CertificadoEscolarBeneficiario;
TRUNCATE TABLE fall.CargueBloqueoCuotaMonetaria;
TRUNCATE TABLE fall.BloqueoBeneficiarioCuotaMonetaria;
TRUNCATE TABLE fall.ItemChequeo;
TRUNCATE TABLE fall.SolicitudAfiliacionPersona;
TRUNCATE TABLE fall.Persona;
TRUNCATE TABLE fall.Afiliado;
TRUNCATE TABLE fall.RevisionFallecimientoBuffer;
TRUNCATE TABLE fall.revisionGroup;

--1. Se inserta el buffer de core a core_aud para buscar las revisiones correspondientes a los datos identificados y teniendo en cuenta el rango de revisiones del proceso Staging

		INSERT INTO fall.MedioDePago (mdpId, s1)
		EXEC sp_execute_remote [CoreReferenceData], N'SELECT  mdpId FROM fall.MedioDePago;'

		INSERT INTO fall.GrupoFamiliar (grfId, s1)
		EXEC sp_execute_remote [CoreReferenceData], N'SELECT  grfId FROM fall.GrupoFamiliar;'

		INSERT INTO fall.Empresa (empId, s1)
		EXEC sp_execute_remote [CoreReferenceData], N'SELECT  empId FROM fall.Empresa;'

		INSERT INTO fall.SucursalEmpresa (sueId, s1)
		EXEC sp_execute_remote [CoreReferenceData], N'SELECT  sueId FROM fall.SucursalEmpresa;'

		INSERT INTO fall.Empleador (empId, s1)
		EXEC sp_execute_remote [CoreReferenceData], N'SELECT  empId FROM fall.Empleador;'

		INSERT INTO fall.RolAfiliado (roaId, s1)
		EXEC sp_execute_remote [CoreReferenceData], N'SELECT  roaId FROM fall.RolAfiliado;'

		INSERT INTO fall.AdministradorSubsidio (asuId, s1)
		EXEC sp_execute_remote [CoreReferenceData], N'SELECT  asuId FROM fall.AdministradorSubsidio;'

		INSERT INTO fall.AdminSubsidioGrupo (asgId, s1)
		EXEC sp_execute_remote [CoreReferenceData], N'SELECT  asgId FROM fall.AdminSubsidioGrupo;'

		INSERT INTO fall.Solicitud (solId, s1)
		EXEC sp_execute_remote [CoreReferenceData], N'SELECT  solId FROM fall.Solicitud;'

		INSERT INTO fall.ParametrizacionNovedad (novId, s1)
		EXEC sp_execute_remote [CoreReferenceData], N'SELECT  novId FROM fall.ParametrizacionNovedad;'

		INSERT INTO fall.SolicitudNovedad (snoId, s1)
		EXEC sp_execute_remote [CoreReferenceData], N'SELECT  snoId FROM fall.SolicitudNovedad;'

		INSERT INTO fall.SolicitudNovedadPersona (snpId, s1)
		EXEC sp_execute_remote [CoreReferenceData], N'SELECT  snpId FROM fall.SolicitudNovedadPersona;'

		INSERT INTO fall.NovedadDetalle (nopId, s1)
		EXEC sp_execute_remote [CoreReferenceData], N'SELECT  nopId FROM fall.NovedadDetalle;'

		INSERT INTO fall.AporteGeneral (apgId, s1)
		EXEC sp_execute_remote [CoreReferenceData], N'SELECT  apgId FROM fall.AporteGeneral;'

		INSERT INTO fall.AporteDetallado (apdId, s1)
		EXEC sp_execute_remote [CoreReferenceData], N'SELECT  apdId FROM fall.AporteDetallado;'

		INSERT INTO fall.SocioEmpleador (semId, s1)
		EXEC sp_execute_remote [CoreReferenceData], N'SELECT  semId FROM fall.SocioEmpleador;'

		INSERT INTO fall.PersonaDetalle (pedId, s1)
		EXEC sp_execute_remote [CoreReferenceData], N'SELECT  pedId FROM fall.PersonaDetalle;'

		INSERT INTO fall.BeneficiarioDetalle (bedId, s1)
		EXEC sp_execute_remote [CoreReferenceData], N'SELECT  bedId FROM fall.BeneficiarioDetalle;'

		INSERT INTO fall.Beneficiario (benId, s1)
		EXEC sp_execute_remote [CoreReferenceData], N'SELECT  benId FROM fall.Beneficiario;'

		INSERT INTO fall.BeneficioEmpleador (bemId, s1)
		EXEC sp_execute_remote [CoreReferenceData], N'SELECT  bemId FROM fall.BeneficioEmpleador;'

		INSERT INTO fall.CondicionInvalidez (coiId, s1)
		EXEC sp_execute_remote [CoreReferenceData], N'SELECT  coiId FROM fall.CondicionInvalidez;'

		INSERT INTO fall.SolicitudNovedadEmpleador (sneId, s1)
		EXEC sp_execute_remote [CoreReferenceData], N'SELECT  sneId FROM fall.SolicitudNovedadEmpleador;'

		INSERT INTO fall.CertificadoEscolarBeneficiario (cebId, s1)
		EXEC sp_execute_remote [CoreReferenceData], N'SELECT  cebId FROM fall.CertificadoEscolarBeneficiario;'

		INSERT INTO fall.CargueBloqueoCuotaMonetaria (cabId, s1)
		EXEC sp_execute_remote [CoreReferenceData], N'SELECT  cabId FROM fall.CargueBloqueoCuotaMonetaria;'

		INSERT INTO fall.BloqueoBeneficiarioCuotaMonetaria (bbcId, s1)
		EXEC sp_execute_remote [CoreReferenceData], N'SELECT  bbcId FROM fall.BloqueoBeneficiarioCuotaMonetaria;'

		INSERT INTO fall.ItemChequeo (ichId, s1)
		EXEC sp_execute_remote [CoreReferenceData], N'SELECT  ichId FROM fall.ItemChequeo;'

		INSERT INTO fall.SolicitudAfiliacionPersona (sapId, s1)
		EXEC sp_execute_remote [CoreReferenceData], N'SELECT  sapId FROM fall.SolicitudAfiliacionPersona;'

		INSERT INTO fall.Persona (perId, s1)
		EXEC sp_execute_remote [CoreReferenceData], N'SELECT  perId FROM fall.Persona;'

		INSERT INTO fall.Afiliado (afiId, s1)
		EXEC sp_execute_remote [CoreReferenceData], N'SELECT  afiId FROM fall.Afiliado;'

--2. Se utiliza cursor para identificar cada revision de cada tabla

----DECLARE @revInicio BIGINT = 46039112 , @RevisionTimeStampFin BIGINT = 46040112;
DECLARE @table VARCHAR (50),@column VARCHAR (5), @sql VARCHAR (1000);

CREATE TABLE #RevisionFallecimientoBuffer (tableName VARCHAR (50), identificador VARCHAR (6), REV BIGINT, id BIGINT);

DECLARE @FechaFallecimiento DATETIME = SYSDATETIME();

DECLARE revision_fallecimiento CURSOR
FOR
		SELECT a.name, c.name
		FROM 
		sys.tables a
		INNER JOIN sys.schemas b ON a.schema_id = b.schema_id
		INNER JOIN sys.columns c ON c.object_id = a.object_id
		WHERE b.name = 'fall' AND c.name <> 's1' AND a.name NOT IN ('RevisionFallecimientoBuffer', 'revisionGroup', 'HistoricoRevisionFallecimiento')
		ORDER BY a.object_id;

OPEN revision_fallecimiento

FETCH NEXT FROM revision_fallecimiento
INTO @table, @column

WHILE @@FETCH_STATUS = 0
BEGIN

		--Consulta SELECT
		SET @sql = CONCAT ('SELECT ''', @table,''', ', '''',
		@column, ''', ',' b.REV, a.', @column,' FROM fall.', @table , ' AS a INNER JOIN ', @table , '_aud AS b ON a.', @column , ' = b.', @column ,
		' INNER JOIN revision c ON c.revId = b.REV WHERE c.revId > ', @revInicio , ' AND revTimeStamp < ', @RevisionTimeStampFin , ';');

		--Consulta Insert
		SET @sql = CONCAT ('INSERT INTO #RevisionFallecimientoBuffer (tableName, identificador, REV, id)', @sql);
		EXEC (@sql);

		FETCH NEXT FROM revision_fallecimiento
		INTO @table, @column
		END

CLOSE revision_fallecimiento;
DEALLOCATE revision_fallecimiento;


INSERT INTO fall.RevisionFallecimientoBuffer (tableName, identificador, REV, id)
SELECT a.tableName, a.identificador, a.REV, a.id 
FROM #RevisionFallecimientoBuffer a
LEFT JOIN fall.HistoricoRevisionFallecimiento b
	ON a.REV = b.REV AND a.tableName = b.tableName AND a.id = b.id
	WHERE b.REV IS NULL
	ORDER BY a.REV;

INSERT INTO fall.HistoricoRevisionFallecimiento (tableName, identificador, REV, id, fechaConsulta)
	SELECT a.tableName, a.identificador, a.REV, a.id, @FechaFallecimiento 
	FROM fall.RevisionFallecimientoBuffer  a;

INSERT INTO fall.revisionGroup (REV)
SELECT REV FROM fall.RevisionFallecimientoBuffer GROUP BY REV ORDER BY REV;

DROP TABLE #RevisionFallecimientoBuffer;

END