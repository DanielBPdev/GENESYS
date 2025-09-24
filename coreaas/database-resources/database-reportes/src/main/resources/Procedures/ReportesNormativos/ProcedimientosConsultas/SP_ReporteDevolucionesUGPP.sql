 
/****** Object:  StoredProcedure [dbo].[reporteDevolucionesUGPP]    Script Date: 2024-06-24 10:09:17 AM ******/
/****** Object:  StoredProcedure [dbo].[reporteDevolucionesUGPP]    Script Date: 18/01/2024 9:34:00 a. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteDevolucionesUGPP]    Script Date: 2023-11-28 8:01:46 AM ******/
/****** Object:  StoredProcedure [dbo].[reporteDevolucionesUGPP]    Script Date: 2023-11-27 9:55:00 AM ******/
/****** Object:  StoredProcedure [dbo].[reporteDevolucionesUGPP]    Script Date: 2023-11-17 9:44:04 AM ******/
/****** Object:  StoredProcedure [dbo].[reporteDevolucionesUGPP]    Script Date: 2023-11-17 7:40:02 AM ******/
/****** Object:  StoredProcedure [dbo].[reporteDevolucionesUGPP]    Script Date: 2023-08-30 9:34:29 AM ******/
/****** Object:  StoredProcedure [dbo].[reporteDevolucionesUGPP]    Script Date: 2023-08-29 8:56:47 AM ******/
/****** Object:  StoredProcedure [dbo].[reporteDevolucionesUGPP]    Script Date: 2023-08-28 9:36:09 AM ******/
/****** Object:  StoredProcedure [dbo].[reporteDevolucionesUGPP]    Script Date: 2023-08-24 9:00:45 AM ******/
/****** Object:  StoredProcedure [dbo].[reporteDevolucionesUGPP]    Script Date: 2023-07-18 8:49:21 AM ******/
/****** Object:  StoredProcedure [dbo].[reporteDevolucionesUGPP]    Script Date: 2023-07-13 8:57:56 AM ******/
/****** Object:  StoredProcedure [dbo].[reporteDevolucionesUGPP]    Script Date: 2023-07-10 11:40:01 AM ******/
/****** Object:  StoredProcedure [dbo].[reporteDevolucionesUGPP]    Script Date: 2023-07-10 10:13:09 AM ******/
/****** Object:  StoredProcedure [dbo].[reporteDevolucionesUGPP]    Script Date: 18/04/2023 4:33:05 p. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteDevolucionesUGPP]    Script Date: 19/08/2022 10:24:22 a. m. ******/
---EXEC reporteDevolucionesUGPP '2024-05-01', '2024-05-31'
create or ALTER     PROCEDURE [dbo].[reporteDevolucionesUGPP] (
	@FECHA_INICIO DATE,
	@FECHA_FINAL DATE
)

AS 
BEGIN
--SET ANSI_WARNINGS OFF
SET NOCOUNT ON
SET ANSI_NULLS ON
SET QUOTED_IDENTIFIER ON
 

SELECT  
	--------Tipo documento aportante----------
	CASE 
		WHEN aportante.perTipoIdentificacion IS NOT NULL
		THEN 
			CASE aportante.perTipoIdentificacion 
				WHEN 'CEDULA_CIUDADANIA' THEN 'CC' 
				WHEN 'TARJETA_IDENTIDAD' THEN 'TI' 
				WHEN 'REGISTRO_CIVIL' THEN 'RC'
				WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
				WHEN 'NUIP' THEN 'NU'
				WHEN 'PASAPORTE' THEN 'PA'
				WHEN 'NIT' THEN 'NI' 
				WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
				WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE' 
				WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
			END 
		
		ELSE

			CASE cotizante.perTipoIdentificacion 
				WHEN 'CEDULA_CIUDADANIA' THEN 'CC' 
				WHEN 'TARJETA_IDENTIDAD' THEN 'TI' 
				WHEN 'REGISTRO_CIVIL' THEN 'RC'
				WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
				WHEN 'NUIP' THEN 'NU'
				WHEN 'PASAPORTE' THEN 'PA'
				WHEN 'NIT' THEN 'NI' 
				WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
				WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE' 
				WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
			END 
	END AS [Tipo documento aportante],
			
	----------Documento aportante
	CASE 
		WHEN  Descent.prdNumeroIdentificacion IS NOT NULL THEN Descent.prdNumeroIdentificacion ---CAMBIO PARA DESCENTRALIZADAS
		WHEN  Descent.prdNumeroIdentificacion IS NULL THEN ISNULL(aportante.perNumeroIdentificacion,cotizante.perNumeroIdentificacion)
		ELSE cotizante.perNumeroIdentificacion
	END AS [Documento aportante],
	
	----------Razn social
	CASE 
		WHEN aportante.perRazonSocial IS NOT NULL
		THEN
			CASE aportante.perRazonSocial 
				WHEN 
					'NO REGISTRA NO REGISTRA NO REGISTRA NO REGISTRA'  
				THEN CONCAT ( 
					aportante.perPrimerNombre+' ', 
					aportante.perSegundoNombre+' ', 
					aportante.perPrimerApellido+' ',  
					aportante.perSegundoApellido
				)
				ELSE
					aportante.perRazonSocial
			END
		
		ELSE
			CONCAT ( 
				cotizante.perPrimerNombre+' ', 
				cotizante.perSegundoNombre+' ', 
				cotizante.perPrimerApellido+' ', 
				cotizante.perSegundoApellido
			)
	END	AS [Razon social],
	
	----------Id departamento
	CASE
		WHEN ISNULL(departamentoAportante.depCodigo, regen.regCodDepartamento)  IS NOT NULL 
		THEN ISNULL(departamentoAportante.depCodigo, RIGHT ('00' + regen.regCodDepartamento,2))
		ELSE ISNULL(departamentoAportanteCOT.depCodigo , RIGHT ('00' + regdet.redCodDepartamento,2))
	END AS [Id departamento],
	
	----------Id municipio
	REPLACE(CASE 
		WHEN ISNULL(municipioAportante.munCodigo, regen.regCodCiudad) IS NOT NULL
		THEN ISNULL(municipioAportante.munCodigo,  RIGHT ('00000' + CONCAT(regen.regCodDepartamento,regen.regCodCiudad),5))
		ELSE ISNULL(municipioAportanteCOT.munCodigo ,RIGHT ('00000' +  CONCAT(regdet.redCodDepartamento,regdet.redCodMunicipio),5 ))
	END,'00000','') AS [Id municipio],
	
	----------Tipo documento cotizante
	CASE cotizante.perTipoIdentificacion 
		WHEN 'CEDULA_CIUDADANIA' THEN 'CC' 
		WHEN 'TARJETA_IDENTIDAD' THEN 'TI' 
		WHEN 'REGISTRO_CIVIL' THEN 'RC'
		WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
		WHEN 'NUIP' THEN 'NU'
		WHEN 'PASAPORTE' THEN 'PA'
		WHEN 'NIT' THEN 'NI' 
		WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
		WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
		WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
	END AS [Tipo documento cotizante],
	
	----------Documento cotizante
	cotizante.perNumeroIdentificacion AS [Documento cotizante],
	
	----------Nombre cotizante
	CONCAT ( 
		cotizante.perPrimerNombre+' ', 
		cotizante.perSegundoNombre+' ', 
		cotizante.perPrimerApellido+' ', 
		cotizante.perSegundoApellido
	) AS [Nombre cotizante],
	
	----------Concepto devolucin
	
	--SELECT * FROM devolucionaporte WHERE 
	CASE 
		WHEN devolucion.dapMotivoPeticion in ( 'BASE_LIQUIDACION' ,'ERROR_REPORTE_IBC')
		THEN 1
		 WHEN devolucion.dapMotivoPeticion = 'ERROR_DIAS_TRABAJADOS' 
		THEN 2
		 WHEN devolucion.dapMotivoPeticion = 'ERROR_IDENTIFICACION_COTIZANTE' 
		THEN 3 
		WHEN devolucion.dapMotivoPeticion IN ('OTROS' ,'NOVEDADES')
		THEN 4
		WHEN devolucion.dapMotivoPeticion IN ( 'PAGO_MULTIPLE_PERIODO' ,'MAYOR_VALOR_PAGADO') --ADD MAYOR_VALOR_PAGADO POR GLPI 73551
		THEN 5
		WHEN devolucion.dapMotivoPeticion = 'APORTANTE_NO_AFILIADO_CAJA' 
	---	AND devolucion.dapCajaCompensacion IS NULL GLPI 73528
		THEN 6
		WHEN devolucion.dapMotivoPeticion IN ( 'COTIZANTE_AFILIADO_OTRA_CAJA'  )
		 --AND devolucion.dapCajaCompensacion IS NOT NULL cambio 20230824
		THEN 7
		 WHEN devolucion.dapMotivoPeticion = 'NO_AFILIACION_DEVOLUCION_DIRECTA_APORTANTE' 
		THEN 8-----ADD NO_AFILIACION_DEVOLUCION_DIRECTA_APORTANTE POR GLPI 73551
		WHEN devolucion.dapMotivoPeticion = 'DEVOLUCION_APORTES_PENSIONES_INDEPENDIENTES_CCF' 
		THEN 9 -----ADD NO_AFILIACION_DEVOLUCION_DIRECTA_APORTANTE POR GLPI 73551

		WHEN devolucion.dapMotivoPeticion in ( 'ERROR_PORCENTAJE_APORTES' ,'SIN_INFORMACION_PERIODO')
		THEN 10---POR GLPI 73528
		WHEN devolucion.dapMotivoPeticion in ( 'DEVOLUCIONES_AUTORIZADAS_UGPP')
		THEN 11
	END AS [Concepto devolucion],
    --dapMotivoPeticion,--prueba
	----------Fecha devolucin
	FORMAT(CONVERT (DATE, devolucion.dapFechaRecepcion, 103), 'yyyy-MM-dd') AS [Fecha devolucion],
	
	----------Fecha de pago
	FORMAT(CONVERT (DATE, devolucion.dapFechaRecepcion, 103), 'yyyy-MM-dd') AS [Fecha pago],
	--FORMAT(CONVERT (DATE, aporteGeneral.apgFechaProcesamiento, 103), 'yyyy-MM-dd') AS [Fecha pago],
	
	----------Periodo devolucion
	
	--devolucion.dapPeriodoReclamado AS [Periodo devolucion],
	--CASE 
	--	WHEN TRY_CAST( MAX(PeriodosDevolucion.value) AS BIGINT ) IS NULL
	--	THEN 'FECHA_INVALIDA'
	--	ELSE FORMAT(DATEADD(S, CONVERT(int,LEFT(CAST(TRIM(MAX(PeriodosDevolucion.value)) AS bigint) , 10)), '1970-01-01'), 'yyyy-MM')
	--END AS [Periodo Devolucion],
	 aporteGeneral.apgPeriodoAporte AS [Periodo Devolucion],
	 
	----------Nmero actos
	solicitudGeneral.solNumeroRadicacion AS [Numero acto],
	
	----------Fecha acto
	FORMAT(CONVERT (DATE, solicitudGeneral.solFechaRadicacion, 103), 'yyyy-MM-dd') AS [Fecha acto],
	
	----------Valor devuelto
 
	 (movAporte.moaValorAporte)	AS [Valor devuelto],
	
	----------Giro a otra Administradora
    CASE 
		WHEN devolucion.dapMotivoPeticion IN ( 'COTIZANTE_AFILIADO_OTRA_CAJA')
		  AND (SELECT ISNULL(ccfCodigo,'') FROM CajaCompensacion WHERE ccfId =  devolucion.dapCajaCompensacion ) <>''
		THEN 
			(SELECT ISNULL(ccfCodigo,'') FROM CajaCompensacion WHERE ccfId =  devolucion.dapCajaCompensacion )
		 
	END AS [Giro a otra Administradora] 
	 
	 --,apdRegistroDetallado
	 into #reportedev
FROM 
	Movimientoaporte AS movAporte WITH(NOLOCK)
	INNER JOIN aportegeneral2 AS aporteGeneral WITH(NOLOCK)  
	        ON aporteGeneral.apgid = movAporte.moaaportegeneral
	INNER JOIN AporteDetallado2 AS aporteDetalle WITH(NOLOCK) 
	        ON aporteDetalle.apdAporteGeneral = aporteGeneral.apgId 
		   AND aporteDetalle.apdId = movAporte.moaAporteDetallado 
	INNER JOIN(	 SELECT CASE WHEN regPeriodoAporte = MAX(regPeriodoAporte) 
					 OVER (PARTITION BY regTipoIdentificacionAportante,regNumeroIdentificacionAportante 
					 ORDER BY regPeriodoAporte desc) THEN regPeriodoAporte ELSE NULL END AS regPeriodoAporte,
					 regTipoIdentificacionAportante, regNumeroIdentificacionAportante, regId,
					 regCodCiudad, regCodDepartamento
			    FROM RegistroGeneral2 WITH(NOLOCK) 
	 
			) regen 
		   ON regId = aporteGeneral.apgRegistroGeneral  
	LEFT JOIN (SELECT CASE WHEN redId = MAX(redId) 
					   OVER (PARTITION BY redTipoIdentificacionCotizante,redNumeroIdentificacionCotizante 
					   ORDER BY redId desc) THEN redId ELSE NULL END AS redId,
					   redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante , redCodMunicipio, redCodDepartamento
				  FROM RegistroDetallado2 WITH(NOLOCK) 	 
			) regdet 
		   ON redId = aporteDetalle.apdRegistroDetallado
	LEFT JOIN empresa AS empresa  WITH(NOLOCK) 
	       ON empresa.empid = aporteGeneral.apgempresa
	LEFT JOIN persona AS aportante  WITH(NOLOCK) 
	       ON aportante.perid =  case when empresa.emppersona is null then aporteDetalle.apdPersona else empresa.emppersona end--cambio olga vega 20230713
		   --empresa.emppersona
	LEFT JOIN PreRegistroEmpresaDescentralizada AS Descent   WITH(NOLOCK) 
	       ON Descent.prdNumeroIdentificacionSerial = aportante.perNumeroIdentificacion
	
	LEFT JOIN UbicacionEmpresa AS ubicacionAportante  WITH(NOLOCK) 
	       ON ubicacionAportante.ubeEmpresa = empresa.empId 
		  AND ubicacionAportante.ubeTipoUbicacion = 'UBICACION_PRINCIPAL'
	LEFT JOIN Ubicacion AS ubicacion 
	       ON ubicacion.ubiId = ubicacionAportante.ubeUbicacion
	LEFT JOIN municipio AS municipioAportante 
	       ON municipioAportante.munId = ubicacion.ubiMunicipio
	LEFT JOIN departamento AS departamentoAportante 
	       ON departamentoAportante.depId = municipioAportante.munDepartamento
	--
	INNER JOIN persona AS cotizante 
	        ON cotizante.perId = aporteDetalle.apdPersona   

	LEFT JOIN Ubicacion AS ubicacionCOT 
	       ON ubicacionCOT.ubiId = cotizante.perUbicacionPrincipal
	LEFT JOIN municipio AS municipioAportanteCOT 
	       ON municipioAportanteCOT.munId = ubicacionCOT.ubiMunicipio
	LEFT JOIN departamento AS departamentoAportanteCOT ON departamentoAportanteCOT.depId = municipioAportanteCOT.munDepartamento
	
	 

	INNER JOIN devolucionaportedetalle AS devolucionDetalle ON devolucionDetalle.dadMovimientoAporte = movAporte.moaId
	INNER JOIN devolucionaporte AS devolucion 
	        ON devolucion.dapId = devolucionDetalle.dadDevolucionAporte  -----
			
	INNER JOIN solicituddevolucionaporte AS solicitudDevolucion ON solicitudDevolucion.sdaDevolucionAporte = devolucion.dapId
	INNER JOIN solicitud AS solicitudGeneral ON solicitudGeneral.solId = solicitudDevolucion.sdaSolicitudGlobal

	LEFT JOIN (
		SELECT 
			devAP.dapId, 
			value
		FROM devolucionaporte devAP 
		CROSS APPLY string_split(devAP.dapPeriodoReclamado, '|')
		--where dapId = '273322'
	) AS PeriodosDevolucion 
	ON PeriodosDevolucion.dapId = devolucion.dapId

WHERE
    -- aportante.perNumeroIdentificacion = '800027813' AND
	-- cotizante.perNumeroIdentificacion= '1088010914' and 
	movAporte.moaTipoMovimiento = 'DEVOLUCION_APORTES' AND 
	---devolucion.dapFechaRecepcion >= @FECHA_INICIO AND 
	--devolucion.dapFechaRecepcion <= @FECHA_FINAL AND 
	CONVERT(DATE,moaFechaActualizacionEstado) >= @FECHA_INICIO AND 
	CONVERT(DATE,moaFechaActualizacionEstado) <= @FECHA_FINAL AND 
	solicitudDevolucion.sdaestadosolicItud IN ('CERRADA','GESTIONAR_PAGO') AND 
	sdaEstadoSolicitud = 'CERRADA'
	and movAporte.moaValorAporte >0
GROUP BY
	aportante.perRazonSocial, 
	cotizante.perNumeroIdentificacion, 
	aportante.perNumeroIdentificacion, 
	cotizante.perTipoIdentificacion, 
	aportante.perTipoIdentificacion,
	aportante.perPrimerNombre, 
	aportante.perSegundoNombre,
	aportante.perPrimerApellido, 
	aportante.perSegundoApellido,
	aportante.perSegundoApellido,
	cotizante.perPrimerNombre, 
	cotizante.perSegundoNombre, 
	cotizante.perPrimerApellido, 
	cotizante.perSegundoApellido,
	departamentoAportanteCOT.depCodigo,
	departamentoAportante.depCodigo,
	devolucion.dapMotivoPeticion,
	cotizante.perNumeroIdentificacion,
	cotizante.perTipoIdentificacion,
	municipioAportanteCOT.munCodigo,
	municipioAportante.munCodigo,
	solicitudGeneral.solFechaRadicacion,
	solicitudGeneral.solNumeroRadicacion,
	devolucion.dapFechaRecepcion,
	devolucion.dapFechaRecepcion,
	--PeriodosDevolucion.value,
	aporteGeneral.apgPeriodoAporte,
	devolucion.dapMontoAportes,
	movAporte.moaValorAporte, movAporte.moaId,
	devolucion.dapDestinatarioDevolucion, 
	devolucion.dapCajaCompensacion, Descent.prdNumeroIdentificacion   ,  apgPeriodoAporte,
	regCodDepartamento, regCodCiudad, redCodDepartamento, redCodMunicipio--,apdRegistroDetallado

	--HAVING 	  CASE 
	--	WHEN TRY_CAST( MAX(PeriodosDevolucion.value) AS BIGINT ) IS NULL
	--	THEN 'FECHA_INVALIDA'
	--	ELSE FORMAT(DATEADD(S, CONVERT(int,LEFT(CAST(TRIM(MAX(PeriodosDevolucion.value)) AS bigint) , 10)), '1970-01-01'), 'yyyy-MM')
	--END = apgPeriodoAporte
 


		SELECT  [Tipo documento aportante],
		        [Documento aportante],
				[Razon social],	
				[Id departamento]	,[Id municipio]	,[Tipo documento cotizante]	,[Documento cotizante]	,
				[Nombre cotizante]	,[Concepto devolucion]	,[Fecha devolucion]	,[Fecha pago]	,[Periodo Devolucion],
				[Numero acto]	,[Fecha acto]	,REPLACE (SUM([Valor devuelto]), '.00000', '')   as [Valor devuelto],
				[Giro a otra Administradora]

		FROM #reportedev
		GROUP BY [Tipo documento aportante],
		        [Documento aportante],
				[Razon social],	
				[Id departamento]	,[Id municipio]	,[Tipo documento cotizante]	,[Documento cotizante]	,
				[Nombre cotizante]	,[Concepto devolucion]	,[Fecha devolucion]	,[Fecha pago]	,[Periodo Devolucion],
				[Numero acto]	,[Fecha acto]	,
				[Giro a otra Administradora]


END