-- =============================================
-- Author:		Jose Arley Correa
-- Create date: 2018/10/23
-- Description:	SP que lista los datos del reporte normativo Ubicacion y contacto Aportantes 
-- =============================================
CREATE PROCEDURE USP_GET_UbicacionContactoAportantes
	@dFechaInicial DATE,
	@dFechaFin DATE,
	@bCount BIT
with execute as owner
AS

BEGIN
	--print 'Inicia USP_GET_UbicacionContactoAportantes'	
	SET NOCOUNT ON;
	DECLARE @sql NVARCHAR(4000);

	CREATE TABLE #IdUbicacionMod(ubiId BIGINT, fechaMod DATE, s1 VARCHAR(500))
	
	SET @sql = '
			SELECT ubi.ubiId, MAX(dateadd(second, revTimeStamp / 1000, ''19700101'') AT TIME ZONE ''UTC'' AT TIME ZONE ''SA Pacific Standard Time'') AS fechaMod
			FROM Ubicacion_aud ubi
			JOIN Revision rev ON (ubi.REV = rev.revId)
			WHERE dateadd(second, revTimeStamp / 1000, ''19700101'') AT TIME ZONE ''UTC'' AT TIME ZONE ''SA Pacific Standard Time'' <= @dFechaFin
			GROUP BY ubi.ubiId'	
	
	INSERT INTO #IdUbicacionMod (ubiId, fechaMod, s1)
	EXEC sp_execute_remote CoreAudReferenceData,
	@sql,
	N'@dFechaFin DATE',
	@dFechaFin = @dFechaFin
	
IF @bCount = 0
	BEGIN
		SELECT cns.cnsValor AS CodAdministradora,
			prm.prmValor AS NombreAdministradora,
			CASE WHEN per.perRazonSocial IS NULL 
				THEN RTrim(Coalesce(per.perPrimerNombre + ' ','') 
							+ Coalesce(per.perSegundoNombre + ' ', '')
							+ Coalesce(per.perPrimerApellido + ' ', '')
							+ Coalesce(per.perSegundoApellido, ''))
				ELSE per.perRazonSocial END AS NombreRazonSocialAportante,
			CASE per.perTipoIdentificacion
				WHEN 'REGISTRO_CIVIL'		THEN 'RC'
				WHEN 'TARJETA_IDENTIDAD'	THEN 'TI'
				WHEN 'CEDULA_CIUDADANIA'	THEN 'CC'
				WHEN 'CEDULA_EXTRANJERIA'	THEN 'CE'
				WHEN 'PASAPORTE'			THEN 'PA'
				WHEN 'CARNE_DIPLOMATICO'	THEN 'CD'
				WHEN 'NIT'					THEN 'NI'
				WHEN 'SALVOCONDUCTO'		THEN 'SC'
				WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE' END AS TipoDocumento,
			per.perNumeroIdentificacion AS NumeroDocumento,
			per.perDigitoVerificacion AS NumeroDigitoVerificacion,
			RTRIM(ubi.ubiDireccionFisica) AS Direccion1,
			dep.depCodigo AS CodDepartamento1,
			mun.munCodigo AS CodMunicipio1,
			dep.depNombre AS NombreDepartamento1,
			mun.munNombre AS NombreMunicipio1,
			RTRIM(ubiE.ubiDireccionFisica) AS Direccion2,
			depE.depCodigo AS CodDepartamento2,
			munE.munCodigo AS CodMunicipio2,
			depE.depNombre AS NombreDepartamento2,
			munE.munNombre AS NombreMunicipio2,
			ubi.ubiTelefonoFijo AS telefono1,
			ubi.ubiIndicativoTelFijo AS indTelefono1,
			ubiE.ubiTelefonoFijo AS telefono2,
			ubiE.ubiIndicativoTelFijo AS indTelefono2,
			ubi.ubiTelefonoCelular AS celular1,
			ubiE.ubiTelefonoCelular AS celular2,
			ubi.ubiEmail AS email1,
			ubiE.ubiEmail AS email2,
			ubiMod.fechaMod AS ultimaFechaActualizacion
		FROM ( SELECT apg.apgPersona FROM AporteGeneral apg WHERE apg.apgPersona IS NOT NULL
				UNION SELECT emp.empPersona FROM AporteGeneral apg JOIN Empresa emp ON (apg.apgEmpresa = emp.empId)) AS personaAporte
		JOIN Persona per ON (personaAporte.apgPersona = per.perId)
		JOIN Ubicacion ubi ON (per.perUbicacionPrincipal = ubi.ubiId)
		JOIN Municipio mun ON (ubi.ubiMunicipio = mun.munId)
		JOIN Departamento dep ON (mun.munDepartamento = dep.depId)
		JOIN Constante cns ON (cnsNombre = 'CAJA_COMPENSACION_CODIGO')
		JOIN Parametro prm ON (prmNombre = 'NOMBRE_CCF')
		JOIN #IdUbicacionMod ubiMod ON (ubiMod.ubiId = ubi.ubiId)
		LEFT JOIN Empresa emp ON (personaAporte.apgPersona = emp.empPersona)
		LEFT JOIN UbicacionEmpresa ube ON (ube.ubeEmpresa = emp.empId AND ube.ubeTipoUbicacion = 'ENVIO_CORRESPONDENCIA')
		LEFT JOIN Ubicacion ubiE ON (ube.ubeUbicacion = ubiE.ubiId)
		LEFT JOIN Municipio munE ON (ubiE.ubiMunicipio = munE.munId)
		LEFT JOIN Departamento depE ON (munE.munDepartamento = depE.depId)
	END
ELSE
	BEGIN
		SELECT COUNT(1) 
		FROM ( SELECT apg.apgPersona FROM AporteGeneral apg WHERE apg.apgPersona IS NOT NULL
				UNION SELECT emp.empPersona FROM AporteGeneral apg JOIN Empresa emp ON (apg.apgEmpresa = emp.empId)) AS personaAporte
		JOIN Persona per ON (personaAporte.apgPersona = per.perId)
		JOIN Ubicacion ubi ON (per.perUbicacionPrincipal = ubi.ubiId)
		JOIN Municipio mun ON (ubi.ubiMunicipio = mun.munId)
		JOIN Departamento dep ON (mun.munDepartamento = dep.depId)
		JOIN Constante cns ON (cnsNombre = 'CAJA_COMPENSACION_CODIGO')
		JOIN Parametro prm ON (prmNombre = 'NOMBRE_CCF')
		JOIN #IdUbicacionMod ubiMod ON (ubiMod.ubiId = ubi.ubiId)
		LEFT JOIN Empresa emp ON (personaAporte.apgPersona = emp.empPersona)
		LEFT JOIN UbicacionEmpresa ube ON (ube.ubeEmpresa = emp.empId AND ube.ubeTipoUbicacion = 'ENVIO_CORRESPONDENCIA')
		LEFT JOIN Ubicacion ubiE ON (ube.ubeUbicacion = ubiE.ubiId)
		LEFT JOIN Municipio munE ON (ubiE.ubiMunicipio = munE.munId)
		LEFT JOIN Departamento depE ON (munE.munDepartamento = depE.depId)
	END
--print 'Finaliza USP_GET_UbicacionContactoAportantes'
END;
