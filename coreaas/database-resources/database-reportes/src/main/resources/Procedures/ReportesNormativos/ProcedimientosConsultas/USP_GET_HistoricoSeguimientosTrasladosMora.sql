-- =============================================
-- Author:		Diego Suesca
-- Create date: 2019/03/14
-- Description:	Inserta datos para reporte 
-- =============================================
CREATE PROCEDURE USP_GET_HistoricoSeguimientosTrasladosMora
(
	@fechaInicio DATE,
	@fechaFin DATE,
	@historico BIT = NULL
)
AS
BEGIN TRY
	SET NOCOUNT ON;

	DECLARE @sql NVARCHAR(MAX),
		@minCarVig bigint,
		@maxCarVig bigint,
		@minCarDia bigint,
		@maxCarDia bigint

	SELECT @minCarVig = MIN(car.carId),
		   @maxCarVig = MAX(car.carId)
	FROM Cartera car
 	WHERE car.carEstadoOperacion = 'NO_VIGENTE'


 	CREATE TABLE #fechasNoVigentes(carId BIGINT,fechaNoVigente DATE, s1 VARCHAR(500))

 	SET @sql = 'SELECT car.carId, dateadd(second, revTimeStamp / 1000, ''19700101'') AT TIME ZONE ''UTC'' AT TIME ZONE ''SA Pacific Standard Time''
				FROM Cartera_aud car
				INNER JOIN Revision rev on rev.revId=car.REV
				INNER JOIN (
					SELECT MIN(REV) minRev,carId
					FROM Cartera_aud
					WHERE carEstadoOperacion = ''NO_VIGENTE''
					  AND carId BETWEEN @minCarVig AND @maxCarVig
					GROUP BY carId) minRev ON minRev.carId = car.carId'

	INSERT INTO #fechasNoVigentes (carId,fechaNoVigente, s1)
	EXEC sp_execute_remote CoreAudReferenceData,
	@sql,
	N'@minCarVig bigint, @maxCarVig bigint',
	@minCarVig = @minCarVig,@maxCarVig = @maxCarVig

 	SELECT @minCarDia = MIN(car.carId),
		   @maxCarDia = MAX(car.carId)
	FROM Cartera car
 	WHERE car.carEstadoCartera  = 'AL_DIA'

	CREATE TABLE #fechasAlDia (carId BIGINT,fechaAlDia DATE, s1 VARCHAR(500))

	SET @sql = 'SELECT car.carId, dateadd(second, revTimeStamp / 1000, ''19700101'') AT TIME ZONE ''UTC'' AT TIME ZONE ''SA Pacific Standard Time''
			FROM Cartera_aud car
			INNER JOIN Revision rev on rev.revId=car.REV
			INNER JOIN (
				SELECT MIN(REV) minRev,carId
				FROM Cartera_aud
				WHERE carEstadoCartera = ''AL_DIA''
				  AND carId BETWEEN @minCarDia AND @maxCarDia
				GROUP BY carId) minRev ON minRev.carId = car.carId'

	INSERT INTO #fechasAlDia (carId,fechaAlDia, s1)
	EXEC sp_execute_remote CoreAudReferenceData,
	@sql,
	N'@minCarDia bigint, @maxCarDia bigint',
	@minCarDia = @minCarDia,@maxCarDia = @maxCarDia

	IF @historico = 1
	BEGIN
		INSERT rno.HistoricoSeguimientosTrasladosMora(
			hstFechaHistorico,
			hstFechaCorteReporte,
			hstNumeroAsignadoUnidad,
			hstFechaAsignadaUnidad,
			hstProcesoTraslado,
			hstTipoDocumento,
			hstNumeroDocumento,
			hstRazonSocial,
			hstTipoSubsistema,
			hstCodigoAdmin,
			hstNombreAdmin,
			hstNumComunicacionSalida,
			hstFechaSalidaUnidad,
			hstUltimaGestionAdra,
			hstFechaUltimaGestion,
			hstObservaciones,
			hstFechaInicialReporte,
			hstFechaFinalReporte)
		SELECT @fechaFin,
		CONVERT(VARCHAR(10),@fechaFin,105) fechaCorteReporte,
		'               ' numeroAsignadoUnidad,
		'          ' FechaAsignadaUnidad,
		'                                         ' procesoTraslado,
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
		'CCF' tipoSubsistema,
		(SELECT cnsValor FROM Constante WHERE cnsNombre='CAJA_COMPENSACION_CODIGO') codAdministradora,
		(SELECT prmValor FROM PARAMETRO WHERE prmNombre='NOMBRE_CCF') nombreAdministradora,
		'               ' numComunicacionSalida,
		'          ' fechaSalidaUnidad,
		CASE WHEN can.canId IS NOT NULL THEN '2'
			 WHEN car.carEstadoOperacion = 'NO_VIGENTE' THEN '4'
			 WHEN car.carEstadoCartera = 'AL_DIA' THEN '5'
			 WHEN sol.solId IS NOT NULL THEN '7'
			 ELSE '8' END
			ultimaGestionAdra,
		CASE WHEN can.canId IS NOT NULL THEN can.canFechaInicio
			 WHEN car.carEstadoOperacion = 'NO_VIGENTE' THEN fnv.fechaNoVigente
			 WHEN car.carEstadoCartera = 'AL_DIA' THEN fad.fechaAlDia
			 WHEN sol.solId IS NOT NULL THEN sol.solFechaCreacion end fechaUltimaGestion,
		'                                                                                                                                                                                                        ' observaciones,
		@fechaInicio,
		@fechaFin
		FROM Persona per
		INNER JOIN Cartera car ON car.carPersona = per.perId
		LEFT JOIN CicloAportante cap ON cap.capPersona = car.carPersona
		LEFT JOIN SolicitudFiscalizacion sfi ON sfi.sfiCicloAportante = cap.capId
		LEFT JOIN Solicitud sol ON sol.solId = sfi.sfiSolicitudGlobal
		LEFT JOIN CarteraNovedad can ON can.canPersona = car.carPersona
		LEFT JOIN #fechasNoVigentes fnv ON car.carId = fnv.carId
		LEFT JOIN #fechasAlDia fad ON car.carId = fad.carId
	END
	ELSE
	BEGIN
		SELECT @fechaFin,
		CONVERT(VARCHAR(10),@fechaFin,105) fechaCorteReporte,
		'               ' numeroAsignadoUnidad,
		'          ' FechaAsignadaUnidad,
		'                                         ' procesoTraslado,
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
		'CCF' tipoSubsistema,
		(SELECT cnsValor FROM Constante WHERE cnsNombre='CAJA_COMPENSACION_CODIGO') codAdministradora,
		(SELECT prmValor FROM PARAMETRO WHERE prmNombre='NOMBRE_CCF') nombreAdministradora,
		'               ' numComunicacionSalida,
		'          ' fechaSalidaUnidad,
		CASE WHEN can.canId IS NOT NULL THEN '2'
			 WHEN car.carEstadoOperacion = 'NO_VIGENTE' THEN '4'
			 WHEN car.carEstadoCartera = 'AL_DIA' THEN '5'
			 WHEN sol.solId IS NOT NULL THEN '7'
			 ELSE '8' END
			ultimaGestionAdra,
		CASE WHEN can.canId IS NOT NULL THEN can.canFechaInicio
			 WHEN car.carEstadoOperacion = 'NO_VIGENTE' THEN fnv.fechaNoVigente
			 WHEN car.carEstadoCartera = 'AL_DIA' THEN fad.fechaAlDia
			 WHEN sol.solId IS NOT NULL THEN sol.solFechaCreacion end fechaUltimaGestion,
		'                                                                                                                                                                                                        ' observaciones,
		@fechaInicio,
		@fechaFin
		FROM Persona per
		INNER JOIN Cartera car ON car.carPersona = per.perId
		LEFT JOIN CicloAportante cap ON cap.capPersona = car.carPersona
		LEFT JOIN SolicitudFiscalizacion sfi ON sfi.sfiCicloAportante = cap.capId
		LEFT JOIN Solicitud sol ON sol.solId = sfi.sfiSolicitudGlobal
		LEFT JOIN CarteraNovedad can ON can.canPersona = car.carPersona
		LEFT JOIN #fechasNoVigentes fnv ON car.carId = fnv.carId
		LEFT JOIN #fechasAlDia fad ON car.carId = fad.carId
	END
END TRY
BEGIN CATCH
	THROW;
END CATCH
;
