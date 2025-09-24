/****** Object:  StoredProcedure [dbo].[reporteAvisoIncumplimiento]    Script Date: 15/03/2021 2:41:42 p. m. ******/

CREATE PROCEDURE [dbo].[reporteAvisoIncumplimiento](
	@FECHA_INICIO DATE,
	@FECHA_FINAL DATE
)

AS
BEGIN
-- SET ANSI_WARNINGS OFF
SET NOCOUNT ON

	SELECT  
		SUBSTRING(per.perRazonSocial,1,200) AS [Nombre o razón social del aportante],
		CASE per.perTipoIdentificacion
						WHEN 'NIT' THEN 'NI'
						WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
						WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
						WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
						WHEN 'REGISTRO_CIVIL' THEN 'TI'
						WHEN 'PASAPORTE' THEN 'PA' END AS [Tipo de documento del aportante],
		per.perNumeroIdentificacion AS [Número de documento del aportante],
		CASE per.perTipoIdentificacion
			WHEN 'NIT' THEN per.perDigitoVerificacion 
			ELSE NULL
		END AS [Número de digito de verificación],
		'3' AS [Mecanismo de envio de la comunicación],---FISICO
		'' AS [Descripción del otro mecanismo de envio de la comunicación],
		CONVERT(VARCHAR(10),bca.bcaFecha,120) AS [Fecha de envio de la comunicación]
	
	FROM Cartera car
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
	WHERE per.perTipoIdentificacion IN ('NIT','CEDULA_CIUDADANIA','CEDULA_EXTRANJERIA','TARJETA_IDENTIDAD','REGISTRO_CIVIL','PASAPORTE')
		/*AND car.carDeudaPresunta>0 
		AND car.carEstadoCartera='MOROSO' 
		AND car.carEstadoOperacion='VIGENTE'*/
		AND DATEDIFF(DAY,car.carFechaCreacion,@FECHA_INICIO) <= 30
		AND bca.bcaResultado = 'EXITOSO'

UNION
                            
		SELECT  
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
            CASE per.perTipoIdentificacion
				WHEN 'NIT' THEN per.perDigitoVerificacion 
				ELSE NULL
			END digitoVerificacion,
            '2' metodoEnvio,---ELECTRONICO
            '' otroMetodo,
            CONVERT(VARCHAR(10),bca.bcaFecha,120) fecha
        FROM Cartera car 
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
        WHERE per.perTipoIdentificacion IN ('NIT','CEDULA_CIUDADANIA','CEDULA_EXTRANJERIA','TARJETA_IDENTIDAD','REGISTRO_CIVIL','PASAPORTE')
        /*AND car.carDeudaPresunta>0 
		AND car.carEstadoCartera = 'MOROSO'
		AND car.carEstadoOperacion ='VIGENTE' */
        AND DATEDIFF(DAY,car.carFechaCreacion,@FECHA_INICIO) <= 30
		AND bca.bcaResultado = 'EXITOSO'

END
