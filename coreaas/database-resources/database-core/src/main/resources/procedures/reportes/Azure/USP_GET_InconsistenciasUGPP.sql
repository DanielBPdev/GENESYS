-- =============================================
-- Author:		Alfonso Baquero E.
-- Create date: 2019/01/12
-- Description:	SP que lista los datos del Reporte de Inconsistencias UGPP
-- =============================================
CREATE PROCEDURE USP_GET_InconsistenciasUGPP
	@dFechaInicial DATE,
	@dFechaFin DATE,
	@bCount BIT
WITH EXECUTE AS OWNER
AS 

BEGIN
	SET NOCOUNT ON;
	DECLARE @sql NVARCHAR(4000);

	DECLARE @tCartera AS TABLE (
		perAportante BIGINT, perCotizante BIGINT, estado VARCHAR(10), fechaInicio DATETIME, fecRev DATETIME, deuda NUMERIC(13, 5), carId BIGINT, shard VARCHAR(500))

	SET @sql = '
		SELECT T.perAportante, T.perCotizante, T.estado, T.fechaInicio, T.fecRev, 
			CASE WHEN T.cadId IS NULL 
				THEN (SELECT car.carDeudaPresunta FROM dbo.Cartera_aud car WHERE car.carId = T.carId and car.REV = T.rev)
				ELSE (SELECT cad.cadDeudaPresunta FROM dbo.CarteraDependiente_aud cad WHERE cad.cadId = T.cadId and cad.REV = T.rev)
			END deuda, T.carId
		FROM (
			SELECT car.carPersona perAportante, cad.cadPersona perCotizante, car.carEstadoOperacion estado, car.carFechaCreacion fechaInicio, 
				MAX(DATEADD(SECOND, revTimeStamp / 1000, ''19700101'') AT TIME ZONE ''UTC'' AT TIME ZONE ''SA Pacific Standard Time'') fecRev, 
				car.carId, cad.cadId, MAX(car.REV) rev
			FROM dbo.Cartera_aud car
			INNER JOIN dbo.Revision rev ON rev.revId = car.REV
			LEFT JOIN dbo.CarteraDependiente_aud cad ON car.carId = cad.cadCartera AND car.REV = cad.REV
			WHERE 1 = 1
			AND car.carTipoLineaCobro IN (''LC2'', ''LC3'', ''LC4'', ''LC5'')
			AND (DATEADD(SECOND, revTimeStamp / 1000, ''19700101'') AT TIME ZONE ''UTC'' AT TIME ZONE ''SA Pacific Standard Time'') BETWEEN @dFechaInicial AND @dFechaFin
			GROUP BY car.carPersona, cad.cadPersona, car.carEstadoOperacion, car.carFechaCreacion, car.carId, cad.cadId
		) AS T'

	INSERT INTO @tCartera (perAportante, perCotizante, estado, fechaInicio, fecRev, deuda, carId, shard)
	EXEC sp_execute_remote CoreAudReferenceData,
	@sql,
	N'@dFechaInicial DATE, @dFechaFin DATE',
	@dFechaInicial = @dFechaInicial, @dFechaFin = @dFechaFin

	IF @bCount = 1 
	BEGIN
		SELECT COUNT(1)
		FROM @tCartera
	END
	ELSE
	BEGIN
		SELECT 'CCF' tipoAdmin, 
			(SELECT cnsValor FROM dbo.Constante WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO') codAdmin, 
			(SELECT ccfNombre FROM dbo.CajaCompensacion WHERE ccfCodigo = (SELECT cnsValor FROM dbo.Constante WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO')) nomAdmin,
			CASE 
				WHEN per.perTipoIdentificacion = 'REGISTRO_CIVIL' THEN 'RC' 
				WHEN per.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI' 
				WHEN per.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC' 
				WHEN per.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE' 
				WHEN per.perTipoIdentificacion = 'PASAPORTE' THEN 'PA' 
				WHEN per.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD' 
				WHEN per.perTipoIdentificacion = 'NIT' THEN 'NI' 
				WHEN per.perTipoIdentificacion = 'SALVOCONDUCTO' THEN 'SC' 
				WHEN per.perTipoIdentificacion = 'PERM_ESP_PERMANENCIA' THEN 'PE' 
				ELSE SUBSTRING(per.perTipoIdentificacion, 1, 2)
			END tipoIdAportante, 
			per.perNumeroIdentificacion numIdAportante, 
			CASE WHEN ISNULL(per.perPrimerNombre, '') = '' THEN per.perRazonSocial
			ELSE (
				per.perPrimerNombre + 
				CASE WHEN per.perSegundoNombre IS NULL THEN ' ' ELSE ' '+per.perSegundoNombre+' ' END +
				per.perPrimerApellido + 
				CASE WHEN per.perSegundoApellido IS NULL THEN '' ELSE ' '+per.perSegundoApellido END
			) END razonSocial, 
			dep.depCodigo codigoDep, 
			mun.munCodigo cod, 
			ubi.ubiDireccionFisica direccion, 
			CASE WHEN ptr.perTipoIdentificacion IS NOT NULL 
				THEN CASE 
					WHEN ptr.perTipoIdentificacion = 'REGISTRO_CIVIL' THEN 'RC' 
					WHEN ptr.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI' 
					WHEN ptr.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC' 
					WHEN ptr.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE' 
					WHEN ptr.perTipoIdentificacion = 'PASAPORTE' THEN 'PA' 
					WHEN ptr.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD' 
					WHEN ptr.perTipoIdentificacion = 'NIT' THEN 'NI' 
					WHEN ptr.perTipoIdentificacion = 'SALVOCONDUCTO' THEN 'SC' 
					WHEN ptr.perTipoIdentificacion = 'PERM_ESP_PERMANENCIA' THEN 'PE' 
					ELSE SUBSTRING(per.perTipoIdentificacion, 1, 2)
				END 
				ELSE CASE 
					WHEN per.perTipoIdentificacion = 'REGISTRO_CIVIL' THEN 'RC' 
					WHEN per.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI' 
					WHEN per.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC' 
					WHEN per.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE' 
					WHEN per.perTipoIdentificacion = 'PASAPORTE' THEN 'PA' 
					WHEN per.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD' 
					WHEN per.perTipoIdentificacion = 'NIT' THEN 'NI' 
					WHEN per.perTipoIdentificacion = 'SALVOCONDUCTO' THEN 'SC' 
					WHEN per.perTipoIdentificacion = 'PERM_ESP_PERMANENCIA' THEN 'PE' 
					ELSE SUBSTRING(per.perTipoIdentificacion, 1, 2)
				END 
			END tipoIdCotizante, 
			CASE WHEN ptr.perNumeroIdentificacion IS NOT NULL THEN ptr.perNumeroIdentificacion ELSE per.perNumeroIdentificacion END numIdCotizante, 
			'Inexactitud' concepto, 
			DATEPART(YEAR, car.fechaInicio) anioInicio, 
			DATEPART(MONTH, car.fechaInicio) mesInicio, 
			CASE WHEN car.estado = 'VIGENTE' THEN NULL ELSE DATEPART(YEAR, car.fecRev) END anioFin,
			CASE WHEN car.estado = 'VIGENTE' THEN NULL ELSE DATEPART(MONTH, car.fecRev) END mesFin, 
			CONVERT(NUMERIC(9, 0), ISNULL(car.deuda, 0)) deuda, 
			A.accion ultimaAccion, 
			A.fecha fechaAccion, 
			NULL observaciones 
		FROM dbo.Persona per
		INNER JOIN @tCartera car ON car.perAportante = per.perId
		LEFT JOIN dbo.Ubicacion ubi ON per.perUbicacionPrincipal = ubi.ubiId
		LEFT JOIN dbo.Municipio mun ON ubi.ubiMunicipio = mun.munId
		LEFT JOIN dbo.Departamento dep ON mun.munDepartamento = dep.depId 
		LEFT JOIN dbo.Persona ptr ON car.perCotizante = ptr.perId
		LEFT JOIN (
			SELECT accion,
				MAX(fecha) fecha, carId
			FROM (
				SELECT 
					CASE
						WHEN acrActividadCartera IN ('FISCALIZACION_TELEFONICA', 'GESTION_TELEFONICA', 'LLAMADO') THEN 'LLAMADA'
						WHEN acrActividadCartera IN ('SOLICITAR_DOCUMENTACION') THEN 'OFICIO'
						ELSE NULL
					END accion, 
					CONVERT(DATE, acrFecha, 121) fecha, acrCartera carId
				FROM ActividadCartera
				WHERE acrCartera IS NOT NULL 
					AND acrFecha BETWEEN @dFechaInicial AND @dFechaFin
				UNION ALL
				SELECT 
					CASE
						WHEN agrVisitaAgenda IN ('FISCALIZACION_TELEFONICA', 'GESTION_TELEFONICA', 'LLAMADO') THEN 'LLAMADA'
						WHEN agrVisitaAgenda IN ('VISITA', 'VISITA_A_CAJA') THEN 'VISITA'
						ELSE NULL
					END accion, agrFecha fecha, agrCartera carId
				FROM AgendaCartera
				WHERE agrCartera IS NOT NULL 
					AND agrFecha BETWEEN @dFechaInicial AND @dFechaFin
			) AS T
			WHERE accion IS NOT NULL
			GROUP BY accion, carId
		) AS A ON A.carId = car.carId
	END
END