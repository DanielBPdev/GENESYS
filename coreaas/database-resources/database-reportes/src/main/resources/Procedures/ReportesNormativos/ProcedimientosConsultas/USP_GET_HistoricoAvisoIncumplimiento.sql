-- =============================================
-- Author:		Diego Suesca
-- Create date: 2019/03/14
-- Description:	Inserta datos para reporte HistoricoDesagregadoCarteraAportante
-- =============================================
CREATE PROCEDURE USP_GET_HistoricoAvisoIncumplimiento
(
	@fechaInicio DATE,
	@fechaFin DATE,
	@historico BIT = NULL
)
AS
BEGIN TRY
	SET NOCOUNT ON;
	IF @historico = 1
	BEGIN
		INSERT rno.HistoricoAvisoIncumplimiento(haiFechaHistorico,
												  haiRazonSocial,
												  haiTipoDocumento ,
												  haiNumeroDocumento,
												  haiDigitoVerificacion,
												  haiMetodoEnvio,
												  haiVacio,
												  haiFecha)
		SELECT @fechaFin,
		SUBSTRING(per.perRazonSocial,1,200) AS razonSocial,
		CASE per.perTipoIdentificacion
			WHEN 'NIT' THEN 'NI'
			WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			WHEN 'REGISTRO_CIVIL' THEN 'TI'
			WHEN 'PASAPORTE' THEN 'PA' END perTipoIdentificacion,
		per.perNumeroIdentificacion AS numeroDocumento,
		per.perDigitoVerificacion AS digitoVerificacion,
		'3' metodoEnvio,---FISICO
		'' otroMetodo,
		CONVERT(VARCHAR(10),bca.bcaFecha,120) fecha
		FROM Cartera car
		INNER JOIN Persona per ON car.carPersona = per.perId
		INNER JOIN CarteraAgrupadora cag ON car.carId = cag.cagCartera
		INNER JOIN BitacoraCartera bca ON bca.bcaNumeroOperacion = cag.cagNumeroOperacion AND bca.bcaActividad in ('B1','B2') 
			AND bca.bcaFecha BETWEEN @fechaInicio AND @fechaFin	AND bca.bcaMedio='DOCUMENTO_FISICO'
			AND bca.bcaId = (SELECT MAX(bcaX.bcaId) FROM BitacoraCartera bcaX 
				WHERE bcaX.bcaNumeroOperacion=bca.bcaNumeroOperacion AND bcaX.bcaActividad=bca.bcaActividad)
		WHERE per.perTipoIdentificacion IN ('NIT','CEDULA_CIUDADANIA','CEDULA_EXTRANJERIA','TARJETA_IDENTIDAD','REGISTRO_CIVIL','PASAPORTE')
		AND car.carDeudaPresunta>0 AND car.carEstadoCartera='MOROSO' AND car.carEstadoOperacion='VIGENTE'
		AND DATEDIFF(DAY,car.carFechaCreacion, bca.bcaFecha) <= 30
		UNION ALL
		SELECT @fechaFin,
			   SUBSTRING(per.perRazonSocial,1,200) AS razonSocial,
			   CASE per.perTipoIdentificacion
					WHEN 'NIT' THEN 'NI'
					WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
					WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
					WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
					WHEN 'REGISTRO_CIVIL' THEN 'TI'
					WHEN 'PASAPORTE' THEN 'PA'
				END perTipoIdentificacion,
			   per.perNumeroIdentificacion AS numeroDocumento,
			   per.perDigitoVerificacion AS digitoVerificacion,
			   '2' metodoEnvio,---ELECTRONICO
			   '' otroMetodo,
			    CONVERT(VARCHAR(10),bca.bcaFecha,120) fecha
		FROM Cartera car 
		INNER JOIN Persona per ON car.carPersona = per.perId
		INNER JOIN CarteraAgrupadora cag ON car.carId = cag.cagCartera
		INNER JOIN BitacoraCartera bca ON bca.bcaNumeroOperacion = cag.cagNumeroOperacion AND bca.bcaActividad in ('B1','B2') 
			AND bca.bcaFecha BETWEEN @fechaInicio AND @fechaFin	AND bca.bcaMedio='ELECTRONICO'
			AND bca.bcaId = (SELECT MAX(bcaX.bcaId) FROM BitacoraCartera bcaX 
				WHERE bcaX.bcaNumeroOperacion=bca.bcaNumeroOperacion AND bcaX.bcaActividad=bca.bcaActividad)
		WHERE per.perTipoIdentificacion IN ('NIT','CEDULA_CIUDADANIA','CEDULA_EXTRANJERIA','TARJETA_IDENTIDAD','REGISTRO_CIVIL','PASAPORTE')
		AND car.carDeudaPresunta>0 AND car.carEstadoCartera='MOROSO' AND car.carEstadoOperacion='VIGENTE'
		AND DATEDIFF(DAY,car.carFechaCreacion, bca.bcaFecha) <= 30
	END
END TRY
BEGIN CATCH
	THROW;
END CATCH
;
