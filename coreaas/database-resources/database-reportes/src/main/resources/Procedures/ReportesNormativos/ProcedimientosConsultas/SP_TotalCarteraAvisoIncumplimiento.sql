CREATE PROCEDURE [dbo].[totalCarteraAvisoIncumplimiento](
	@FECHA_INICIO DATETIME,
	@FECHA_FINAL DATETIME
)

AS
BEGIN
--sSET ANSI_WARNINGS OFF
SET NOCOUNT ON

	SELECT  
		CONVERT(BIGINT, car.carDeudaPresunta) [valor]
	FROM 
		Cartera car
		INNER JOIN Persona per ON car.carPersona = per.perId
		INNER JOIN CarteraAgrupadora cag ON car.carId = cag.cagCartera
		INNER JOIN BitacoraCartera bca ON bca.bcaNumeroOperacion = cag.cagNumeroOperacion 
			AND bca.bcaActividad in ('B1','B2') 
			AND bca.bcaFecha BETWEEN @FECHA_INICIO AND @FECHA_FINAL    
			AND bca.bcaMedio='DOCUMENTO_FISICO'
			AND bca.bcaId = (
				SELECT MAX(bcaX.bcaId) 
				FROM BitacoraCartera bcaX 
				WHERE bcaX.bcaNumeroOperacion = bca.bcaNumeroOperacion AND bcaX.bcaActividad=bca.bcaActividad
			)
	WHERE 
		per.perTipoIdentificacion IN ('NIT','CEDULA_CIUDADANIA','CEDULA_EXTRANJERIA','TARJETA_IDENTIDAD','REGISTRO_CIVIL','PASAPORTE')
		AND DATEDIFF(DAY,car.carFechaCreacion,@FECHA_INICIO) <= 30
		AND bca.bcaResultado = 'EXITOSO'

UNION
                            
		SELECT  
			CONVERT(BIGINT, car.carDeudaPresunta) [valor]
        FROM 
			Cartera car 
			INNER JOIN Persona per ON car.carPersona = per.perId
			INNER JOIN CarteraAgrupadora cag ON car.carId = cag.cagCartera
			INNER JOIN BitacoraCartera bca ON bca.bcaNumeroOperacion = cag.cagNumeroOperacion 
				AND bca.bcaActividad IN ('B1','B2') 
				AND bca.bcaFecha BETWEEN @FECHA_INICIO AND @FECHA_FINAL AND bca.bcaMedio='ELECTRONICO'
				AND bca.bcaId = (
					SELECT MAX(bcaX.bcaId) 
					FROM BitacoraCartera bcaX 
					WHERE bcaX.bcaNumeroOperacion=bca.bcaNumeroOperacion AND bcaX.bcaActividad=bca.bcaActividad
				)
        WHERE 
			per.perTipoIdentificacion IN ('NIT','CEDULA_CIUDADANIA','CEDULA_EXTRANJERIA','TARJETA_IDENTIDAD','REGISTRO_CIVIL','PASAPORTE')
			AND DATEDIFF(DAY, car.carFechaCreacion, @FECHA_INICIO) <= 30
			AND bca.bcaResultado = 'EXITOSO'

end