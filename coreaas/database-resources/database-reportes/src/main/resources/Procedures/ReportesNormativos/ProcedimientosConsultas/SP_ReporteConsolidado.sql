/****** Object:  StoredProcedure [dbo].[reporteConsolidado]    Script Date: 5/03/2021 7:42:14 p. m. ******/

CREATE PROCEDURE [dbo].[reporteConsolidado](
	@FECHA_PROCESAMIENTO DATE
)

AS
BEGIN
-- SET ANSI_WARNINGS OFF
SET NOCOUNT ON

	/*DECLARE 
		@tDesagregado table (
			codAdministradora VARCHAR(100), 
			nombreAdministradora VARCHAR(100), 
			nombreRazonSocial VARCHAR(100),
			tipoDocumento VARCHAR(100), 
			perNumeroIdentificacion BIGINT,
			digitoVerificacion INT,
			tipoDeuda INT,
			origenCartera INT,
			Deuda BIGINT, 
			anioCartera BIGINT, 
			numeroPeriodos BIGINT, 
			ultimaAccion BIGINT,
			fechaUltimaAccion DATETIME,
			estadoAportante VARCHAR(100),
			clasificaciondelestadodelaportante VARCHAR(100),
			convenioCobro INT
		)

	INSERT INTO @tDesagregado 
			EXEC dbo.reporteDesagregado @FECHA_PROCESAMIENTO*/
	------------------------------------------------------------------------------------------------------------
	--se toman los desagregados
	------------------------------------------------------------------------------------------------------------
	IF OBJECT_ID('tempdb.dbo.#DesagregadoCartera', 'U') IS NOT NULL
	  DROP TABLE #DesagregadoCartera;

	SELECT  
		-------------------------------------------------------------------------------------------------
	-- codAdministradora
	  (SELECT cnsValor FROM Constante WHERE cnsNombre='CAJA_COMPENSACION_CODIGO') AS [Cod Administradora],
	---------------------------------------------------------------------------------------------------
	-- nombreAdministradora
	  (SELECT prmValor FROM PARAMETRO WHERE prmNombre='NOMBRE_CCF') AS [Nombre adminsitradora],
	---------------------------------------------------------------------------------------------------
	-- nombreRazonSocial
	  P.perrazonsocial AS [Nombre o razon social del aportante],                                    
	---------------------------------------------------------------------------------------------------
	-- tipoDocumento
	  CASE P.perTipoIdentificacion
			WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			WHEN 'PASAPORTE' THEN 'PA'
			WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
			WHEN 'REGISTRO_CIVIL' THEN 'RC'
			WHEN 'NIT' THEN 'NI'
		END AS [Tipo de documento],
	---------------------------------------------------------------------------------------------------
	-- perNumeroIdentificacion
	  P.perNumeroIdentificacion AS [Numero de documento del aportante],
	---------------------------------------------------------------------------------------------------
	-- digitoVerificacion
		CASE p.perTipoIdentificacion
			WHEN 'NIT' THEN P.perDigitoVerificacion
			ELSE NULL
		END AS [Numero de digito de verificacion],

	---------------------------------------------------------------------------------------------------
	-- tipoDeuda
	--CONVERT(BIGINT,ISNULL((CASE WHEN (cd.caddeudareal) IS NULL THEN '2' ELSE '1' END ),'1')) AS tipoDeuda,
	---------------------------------------------------------------------------------------------------
	---tipoDeuda
	(
		--SELECT 
			  CASE 
				WHEN  BC.bcaActividad IN('C1', 'D1', 'E1', 'F1', 'E2', 'D2', 'F2', 'G2', 'H2')
				THEN 1 
				ELSE 2 
			  END
		--FROM Carteradependiente cd  WITH(NOLOCK)
		--WHERE cd.cadcartera = c.carid 
		--AND TRIM(cadEstadoOperacion) = 'VIGENTE' 

	) AS [Tipo de cartera aportante],
	---------------------------------------------------------------------------------------------------
	-- origenCartera
	  CONVERT(BIGINT,
		CASE 
			WHEN c.cartipolineacobro IN ( 'LC1', 'C6') 
			THEN 1 
			ELSE 2 
		END
	  ) AS [Origen de cartera aportante],
	---------------------------------------------------------------------------------------------------
	-- Deuda
	
		CONVERT(BIGINT, 
			SUM(c.carDeudaPresunta)  
		) AS [Valor de la cartera aportante],

	---------------------------------------------------------------------------------------------------
	-- anioCartera
	  CONVERT(
		BIGINT,
		CASE 
			WHEN CEILING(DATEDIFF(DAY,MIN(c.carFechaCreacion), @FECHA_PROCESAMIENTO)/365.) > 5 
			THEN 6 
			ELSE CEILING(DATEDIFF(DAY, MIN(c.carFechaCreacion), @FECHA_PROCESAMIENTO)/365.) 
		END ) AS [Anio de la cartera aportante], 

		--c.carFechaCreacion as carFechaCreacion,

	---------------------------------------------------------------------------------------------------
	-- numeroPeriodos
  
		( CASE 

		  WHEN (
			SELECT 
				(DATEDIFF(DAY, cc.carFechaCreacion, @FECHA_PROCESAMIENTO) / 30) --COUNT(cc.carId) 
			FROM cartera cc 
			WHERE 
				cc.carId = c.carId
			AND cc.carDeudaPresunta > 0
			--AND cc.carFechaCreacion <= @FECHA_PROCESAMIENTO
		
			)  < 6 

		  THEN 1 
      
		  WHEN (
			SELECT 
				(DATEDIFF(DAY, cc.carFechaCreacion, @FECHA_PROCESAMIENTO) / 30) --COUNT(cc.carId) 
			FROM cartera cc 
			WHERE 
				cc.carId = c.carId 
				and cc.carDeudaPresunta > 0
			/*and cc.carId in ('7651',
	'7703',
	'7882',
	'14517')*/
			--AND cc.carFechaCreacion <= @FECHA_PROCESAMIENTO
			) BETWEEN 6 and 11
		  THEN 2 
                  
		  WHEN (
			SELECT (DATEDIFF(DAY, cc.carFechaCreacion, @FECHA_PROCESAMIENTO) / 30) --COUNT(cc.carId) 
			FROM cartera cc 
			WHERE cc.carId = c.carId 
			AND cc.carDeudaPresunta > 0
			--AND cc.carFechaCreacion <= @FECHA_PROCESAMIENTO
			)  between 12 and 35
		  THEN 3 

		WHEN (
			SELECT (DATEDIFF(DAY, cc.carFechaCreacion, @FECHA_PROCESAMIENTO) / 30) --COUNT(cc.carId) 
			FROM cartera cc 
			WHERE cc.carId = c.carId 
			AND cc.carDeudaPresunta > 0
			--AND cc.carFechaCreacion <= @FECHA_PROCESAMIENTO
			)  >= 36
		 THEN 4 

		  ELSE (
			SELECT (DATEDIFF(DAY, cc.carFechaCreacion, @FECHA_PROCESAMIENTO) / 30) --COUNT(cc.carId) 
			FROM cartera cc 
			WHERE cc.carId = c.carId 
			AND cc.carDeudaPresunta > 0)
		END
	  ) AS [Numero de periodos sin pago], 
	---------------------------------------------------------------------------------------------------
	-- ultimaAccion

	  (
		SELECT 
		  ISNULL( 
			MAX(
			  CASE

				WHEN bb.bcaActividad IN ('B1','B2' ) 
				THEN '1'---aviso de incumplimiento

				WHEN bb.bcaActividad IN('C1','C2', 'D2', 'E2') -- 
				THEN '3'

				WHEN bb.bcaActividad IN ('D1', 'E1', 'F1', 'F2', 'G2', 'H2') 
				THEN '4'

				--Cobros judiciales es un proceso interno de cada caja por esto no aplica
				--WHEN bb.bcaActividad IN ('F1','H2') 
				--THEN '5' 

				ELSE '6'

			  END
			)  ,6
		  )
		FROM Bitacoracartera bb
		INNER JOIN   (

		  SELECT 
			MAX(X.bcaFecha) AS fecha, 
			X.bcanumerooperacion 
		  FROM bitacoracartera X 
		  WHERE 
			X.bcaResultado IN ('EXITOSO')
			AND X.bcaFecha <= @FECHA_PROCESAMIENTO
		  GROUP BY x.bcanumerooperacion
		) Y
		ON bb.bcaFecha = y.fecha 
		AND y.bcaNumeroOperacion = bb.bcaNumeroOperacion
		WHERE bc.bcaNumeroOperacion = bb.bcaNumeroOperacion


	  ) AS  [Ultima accion de cobro],
	---------------------------------------------------------------------------------------------------
	-- FECHA ULTIMA ACCION DE COBRO

	  (
		SELECT 
		ISNULL(MAX(
			CASE
				WHEN X.bcaActividad IN ('B1','B2', 'C1','C2','D2', 'E2', 'D1', 'E1', 'F1','F2', 'G2', 'H2', 'F1','H2' ) 
				THEN X.bcaFecha
				ELSE NULL

			  END),NULL) 
		FROM bitacoracartera x 
		WHERE 
		  x.bcaNumeroOperacion = bc.bcanumerooperacion 
		  AND  X.bcaResultado IN ('EXITOSO','PUBLICADO') 
		  AND X.bcaFecha <= @FECHA_PROCESAMIENTO
	  ) AS [Fecha de ultima accion de cobro],

	  ---------------------------------------------------------------------------------------------------
	-- estadoAportante

		(
			SELECT 
			CASE 
				WHEN empEstadoEmpleador = 'ACTIVO' 
				THEN 'A' 
				ELSE 'I' 
			END 
			FROM VW_EstadoAfiliacionEmpleadorCaja W WITH(NOLOCK)
			WHERE 
				W.perTipoIdentificacion = P.perTipoIdentificacion
				AND W.perNumeroIdentificacion = P.perNumeroIdentificacion
		)  AS [Estado del aportante],

	---------------------------------------------------------------------------------------------------
	-- clasificaciondelestadodelaportante
  
	  ( 
		SELECT 
		  CASE 
			WHEN  empMotivoDesafiliacion in ('EXPULSION_POR_MOROSIDAD' , 'EXPULSION_POR_INFORMACION_INCORRECTA')
			THEN 'E'   

			WHEN empMotivoDesafiliacion = 'CESE_EN_PROCESO_LIQUIDACION_LIQUIDADO_FALLECIDO' 
			THEN 'L'
          
			WHEN empMotivoDesafiliacion = 'OTRO' 
			THEN 'ND'

			ELSE 'A' 
		  END
		FROM Empleador EM 
		INNER JOIN empresa e ON em.empempresa = e.empid 
		INNER JOIN persona pe ON pe.perid = e.emppersona 
		WHERE 
		  pe.perTipoIdentificacion = P.perTipoIdentificacion
		  AND pe.perNumeroIdentificacion = P.perNumeroIdentificacion
	  ) AS [Clasificacion del estado del aportante],
	---------------------------------------------------------------------------------------------------
	-- convenioCobro
	  CONVERT(
		BIGINT,
		CASE 
		  WHEN cp.copId IS NULL 
		  THEN '2' 
		  ELSE '1' 
		END
	  ) AS [Convenio de cobro]

  

	--#################################################################################################
	-- TABLA TEMPORAL PARA ALMACENAR EL DESAGREGADO
	  INTO #DesagregadoCartera -- crea una tabla temporal
	--#################################################################################################

	  from 
  
	  Cartera c 

	  INNER JOIN Persona p 
		ON c.carpersona = p.perid 
			--AND c.carDeudaPresunta > 0 
			--AND c.cartipolineacobro IN('LC1', 'LC4', 'LC5', 'C6', 'C7', 'C8')
			--AND DATEDIFF(DAY,c.carFechaCreacion,CAST(@FECHA_PROCESAMIENTO AS DATE)) > 30
			--AND c.carEstadoOperacion = 'VIGENTE'

	  INNER JOIN carteraagrupadora ca WITH(NOLOCK) ON ca.cagCartera = c.carid

	  /*select top 10 * from BitacoraCartera 
	  inner join Persona on perId = bcaPersona
	  where perNumero*/

	  LEFT JOIN BitacoraCartera bc WITH(NOLOCK) 
		on bc.bcaPersona = c.carPersona AND
			bc.bcaResultado = 'EXITOSO' 

		--ON bc.bcaNumeroOperacion = ca.cagNumeroOperacion 
		--	AND bcaResultado = 'EXITOSO' --AND bcaFecha > 
  
	  LEFT JOIN Conveniopago cp WITH(NOLOCK) ON cp.copPersona = c.carpersona

	  WHERE 
		c.carEstadoOperacion = 'VIGENTE' 
		AND (DATEDIFF(DAY,c.carFechaCreacion,CAST(@FECHA_PROCESAMIENTO AS DATE))) > 30 
		AND c.carDeudaPresunta > 0 
		AND c.cartipolineacobro IN('LC1', 'C6')
		AND c.carEstadoOperacion = 'VIGENTE'
	  --WHERE P.perNumeroIdentificacion = '10106999'
	  --select top 10 * from Conveniopago left join persona on perid = copPersona
	  --WHERE P.perNumeroIdentificacion = '901143157'

	  GROUP BY 
		C.carTipoAccionCobro,
		P.perRazonSocial,
		p.perTipoIdentificacion,
		p.perNumeroIdentificacion,
		p.perDigitoVerificacion,
		c.carId,
		c.carTipoLineaCobro,
		c.carPersona,
		bc.bcaNumeroOperacion,
		bc.bcaActividad,
		cp.copId,
		c.carFechaCreacion
	--########################################################################################################--
	--se toman los desagregados
	--########################################################################################################--

	SELECT --DISTINCT 
		----------CODIGO CAJA
		desagregado.[Cod Administradora] as [Cod Administradora],

		----------NOMBRE CAJA
		desagregado.[Nombre adminsitradora]  as [Nombre Administradora],

		----------TIPO DE CARTERA
		desagregado.[Tipo de cartera aportante] AS [Tipo de Cartera Aportante],

		----------ORIGEN DE LA CARTERA
		desagregado.[Origen de cartera aportante] AS [Origen de Cartera],
		
		----------AÑO DE LA CARTERA
		desagregado.[Anio de la cartera aportante] AS [Año de la Cartera],

		----------NUMERO DE PERIODOS SIN PAGO
		desagregado.[Numero de periodos sin pago] AS [Número de Periodos sin Pago], 

		----------SUMATORIA VALOR DE LA CARTERA
		SUM(desagregado.[Valor de la cartera aportante]) AS [Valor de la Cartera Aportante]

	FROM #DesagregadoCartera AS desagregado

	GROUP BY 
		desagregado.[Cod Administradora],
		desagregado.[Nombre adminsitradora],
		desagregado.[Tipo de cartera aportante],
		desagregado.[Origen de cartera aportante],
		desagregado.[Anio de la cartera aportante],
		desagregado.[Numero de periodos sin pago]

END
