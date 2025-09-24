-- =============================================
-- Author:		Diego Suesca
-- Create date: 2019/03/14
-- Description:	Inserta datos para reporte 
-- =============================================
CREATE PROCEDURE USP_GET_HistoricoAportantesProcesoEnUnidad
(
	@fechaInicio DATE,
	@fechaFin DATE,
	@historico BIT = NULL
)
AS
BEGIN TRY
	SET NOCOUNT ON;

	DECLARE @sql NVARCHAR(MAX),
			@minCar bigint,
			@maxCar bigint

	SELECT @minCar = MIN(car.carId),
		   @maxCar = MAX(car.carId)
	FROM Cartera car
 	WHERE @fechaFin > DATEADD(DAY, 30, car.carFechaCreacion)
 	  AND car.carEstadoOperacion = 'NO_VIGENTE'

 	CREATE TABLE #fechasNoVigentes(carId BIGINT,fechaNoVigente DATE, s1 VARCHAR(500))

 	SET @sql = 'SELECT car.carId, dateadd(second, revTimeStamp / 1000, ''19700101'') AT TIME ZONE ''UTC'' AT TIME ZONE ''SA Pacific Standard Time''
				FROM Cartera_aud car
				INNER JOIN Revision rev on rev.revId=car.REV
				INNER JOIN (
					SELECT MIN(REV) minRev,carId
					FROM Cartera_aud
					WHERE carEstadoOperacion = ''NO_VIGENTE''
					  AND carId BETWEEN @minCar AND @maxCar
					GROUP BY carId) minRev ON minRev.carId = car.carId'

	INSERT INTO #fechasNoVigentes (carId,fechaNoVigente, s1)
	EXEC sp_execute_remote CoreAudReferenceData,
	@sql,
	N'@minCar bigint, @maxCar bigint',
	@minCar = @minCar,@maxCar = @maxCar

	IF @historico = 1
	BEGIN
		INSERT rno.HistoricoAportantesProcesoEnUnidad(
			hauFechaHistorico,  
		  hauTipoDocumento,
		  hauNumeroDocumento,
		  hauRazonSocial,
		  hauPeriodoInicio,
		  hauPeriodoFin,
		  hauAdelantadoAccion,
		  hauTipoAccion,
		  hauCodigoAdmin,
		  hauNombreAdmin,
		  hauValorDeuda,
		  hauFechaInicialReporte,
		  hauFechaFinalReporte)
		SELECT @fechaFin,
		CASE per.perTipoIdentificacion
			WHEN 'NIT' THEN 'NI'
			WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			WHEN 'REGISTRO_CIVIL' THEN 'RC'
			WHEN 'PASAPORTE' THEN 'PA'
			WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
			WHEN 'SALVOCONDUCTO' THEN 'SC' 
			WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
			ELSE ''
		END tipoDocumento,
		per.perNumeroIdentificacion,
		per.perRazonSocial,
		car.carFechaAsignacionAccion fechaAccion,
		fnv.fechaNoVigente fechaFin,
		CASE WHEN car.carFechaAsignacionAccion IS NULL THEN 2 ELSE 1 END AS seHaAdelantadoAccion,
		CASE car.carEstadoOperacion
			WHEN 'NO_VIGENTE' THEN '5'
			WHEN 'VIGENTE' THEN
				CASE car.carTipoAccionCobro
					WHEN 'D1' THEN '1'
					WHEN 'E1' THEN '1'
					WHEN 'F2' THEN '1'
					WHEN 'G2' THEN '1'
					WHEN 'C1' THEN '2'
					WHEN 'C2' THEN '2'
					WHEN 'F1' THEN '3'
					WHEN 'H2' THEN '3'
					WHEN 'LC4C' THEN '3'
					WHEN 'LC5C' THEN '3'
					ELSE '6'
				END
			END tipoDeAccion,
		(SELECT cnsValor FROM Constante WHERE cnsNombre='CAJA_COMPENSACION_CODIGO') codAdministradora,
		(SELECT prmValor FROM PARAMETRO WHERE prmNombre='NOMBRE_CCF') nombreAdministradora,
		0,
		@fechaInicio,
		@fechaFin
		FROM Persona per
		INNER JOIN Cartera car ON car.carPersona = per.perId
		LEFT JOIN #fechasNoVigentes fnv
									ON fnv.carId = car.carId
		WHERE @fechaFin > DATEADD(DAY, 30, car.carFechaCreacion)
		  AND car.carFechaAsignacionAccion IS NOT NULL
	END
	ELSE
	BEGIN
			SELECT @fechaFin,
		CASE per.perTipoIdentificacion
			WHEN 'NIT' THEN 'NI'
			WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			WHEN 'REGISTRO_CIVIL' THEN 'RC'
			WHEN 'PASAPORTE' THEN 'PA'
			WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
			WHEN 'SALVOCONDUCTO' THEN 'SC' 
			WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
			ELSE ''
		END tipoDocumento,
		per.perNumeroIdentificacion,
		per.perRazonSocial,
		car.carFechaAsignacionAccion fechaAccion,
		fnv.fechaNoVigente fechaFin,
		CASE WHEN car.carFechaAsignacionAccion IS NULL THEN 2 ELSE 1 END AS seHaAdelantadoAccion,
		CASE car.carEstadoOperacion
			WHEN 'NO_VIGENTE' THEN '5'
			WHEN 'VIGENTE' THEN
				CASE car.carTipoAccionCobro
					WHEN 'D1' THEN '1'
					WHEN 'E1' THEN '1'
					WHEN 'F2' THEN '1'
					WHEN 'G2' THEN '1'
					WHEN 'C1' THEN '2'
					WHEN 'C2' THEN '2'
					WHEN 'F1' THEN '3'
					WHEN 'H2' THEN '3'
					WHEN 'LC4C' THEN '3'
					WHEN 'LC5C' THEN '3'
					ELSE '6'
				END
			END tipoDeAccion,
		(SELECT cnsValor FROM Constante WHERE cnsNombre='CAJA_COMPENSACION_CODIGO') codAdministradora,
		(SELECT prmValor FROM PARAMETRO WHERE prmNombre='NOMBRE_CCF') nombreAdministradora,
		0,
		@fechaInicio,
		@fechaFin
		FROM Persona per
		INNER JOIN Cartera car ON car.carPersona = per.perId
		LEFT JOIN #fechasNoVigentes fnv
									ON fnv.carId = car.carId
		WHERE @fechaFin > DATEADD(DAY, 30, car.carFechaCreacion)
		  AND car.carFechaAsignacionAccion IS NOT NULL
	END
END TRY
BEGIN CATCH
	THROW;
END CATCH
;
