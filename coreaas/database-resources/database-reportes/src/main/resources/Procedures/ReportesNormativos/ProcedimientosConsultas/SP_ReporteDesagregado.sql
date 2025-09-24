/****** Object:  StoredProcedure [dbo].[reporteDesagregado]    
Script Date: 24/03/2021 1:06:47 p. m. 
update : 20220805
----exec reporteDesagregado '2024-03-31'
----VALIDAR HISTORIA 20231127
******/
CREATE OR ALTER     PROCEDURE [dbo].[reporteDesagregado](  @FECHA_PROCESAMIENTO DATE )
AS
BEGIN
-- DECLARE @FECHA_PROCESAMIENTO DATE = '2025-05-31'
IF OBJECT_ID('tempdb.dbo.#DesagregadoCartera', 'U') IS NOT NULL
	DROP TABLE #DesagregadoCartera;
IF OBJECT_ID('tempdb.dbo.#DesagregadoSuspendidos', 'U') IS NOT NULL
	DROP TABLE #DesagregadoSuspendidos;

DROP TABLE IF EXISTS #tmpDatosBitacora
SELECT CASE WHEN bc.bcaId = max(bc.bcaId) OVER (PARTITION BY p.perId) THEN bc.bcaFecha ELSE NULL END as [bcaFecha],
bc.bcaResultado,bc.bcaActividad,bc.bcaMedio,bc.bcaId,bc.bcaPersona,bcaNumeroOperacion
into #tmpDatosBitacora
FROM Persona p
inner join BitacoraCartera bc on p.perId = bc.bcaPersona
--WHERE  p.perNumeroIdentificacion in ('1000395291','1000412506','1000482','1000914205','1001243965')

drop table if exists #tmpdatosBitacora2
SELECT distinct dbc.*,c.carTipoAccionCobro 
into #tmpdatosBitacora2
FROM #tmpDatosBitacora dbc
inner join CarteraAgrupadora ca on ca.cagNumeroOperacion = dbc.bcaNumeroOperacion
inner join Cartera c on c.carId = ca.cagCartera
where dbc.bcaFecha IS NOT NULL

DROP TABLE IF EXISTS #BitacoraFinal
SELECT dbc2.bcaFecha, dbc2.bcaId, dbc2.bcaPersona,dbc2.bcaNumeroOperacion,--dbc2.bcaActividad,dbc2.carTipoAccionCobro,dbc2.bcaResultado,
CASE 
	WHEN dbc2.bcaActividad IN ('B1','B2' ) and dbc2.bcaResultado = 'EXITOSO' THEN '1'
	WHEN dbc2.bcaActividad IN ('B1','B2' ) and dbc2.bcaResultado != 'EXITOSO' THEN CASE WHEN dbc2.carTipoAccionCobro IN ('B1', 'BC1', 'B2', 'BC2') THEN '1' END
	WHEN dbc2.bcaActividad IN ('C1', 'C2', 'GENERAR_LIQUIDACION' ) and dbc2.bcaResultado = 'EXITOSO' THEN '2'
	WHEN dbc2.bcaActividad IN ('C1', 'C2', 'GENERAR_LIQUIDACION' ) and dbc2.bcaResultado != 'EXITOSO' THEN CASE WHEN dbc2.carTipoAccionCobro IN ('C1', 'CD1', 'C2', 'CD2') THEN '2' END
	WHEN dbc2.bcaActividad IN ('FIRMEZA_TITULO_EJECUTIVO' ) THEN '3'
	WHEN dbc2.bcaActividad IN ('F2','2G','D1','E1') and dbc2.bcaResultado = 'EXITOSO' THEN '4'
	WHEN dbc2.bcaActividad IN ('F2','2G','D1','E1') and dbc2.bcaResultado != 'EXITOSO' THEN CASE WHEN dbc2.carTipoAccionCobro IN ('D1', 'DE1', 'E1', 'EF1', 'F2', 'FE2', 'G2', 'GH2') THEN '4' END
	WHEN dbc2.bcaActividad IN ('ENVIO_JURIDICO') THEN '5'
	WHEN dbc2.bcaActividad IN ('A2' , 'A1') and dbc2.bcaResultado = 'EXITOSO' THEN '6'
	WHEN dbc2.bcaActividad IN ('A2' , 'A1') and dbc2.bcaResultado != 'EXITOSO' THEN CASE WHEN dbc2.carTipoAccionCobro IN ('A01', 'A1', 'AB1', 'A02', 'A2' , 'AB2') THEN '6'  END	
	ELSE '6' end [bcaActividad]
into #BitacoraFinal
FROM #tmpdatosBitacora2 dbc2

DROP TABLE IF EXISTS #tmpDeudaReal
SELECT cast( sum(cd.cadDeudaReal) as numeric(19,0)) [cadDeudaReal], cadcartera 
INTO #tmpDeudaReal
FROM CarteraDependiente cd where cd.cadDeudaReal = 0 group by cd.cadCartera


--#################################################################################################
-------------------------DesagregadoCartera - LC1 Y C6
--#################################################################################################
DROP TABLE IF EXISTS #DesagregadoCartera
Select DISTINCT
	-------------------------------------------------------------------------------------------------
	-- Periodo del reporte
	   FORMAT(GETDATE(), 'yyyy-MM') [Periodo del reporte],
	-------------------------------------------------------------------------------------------------
	-- codAdministradora
	  (SELECT cnsValor FROM Constante WHERE cnsNombre='CAJA_COMPENSACION_CODIGO') AS [Cód. Administradora],
	-- nombreRazonSocial
	  P.perrazonsocial AS [Nombre o razón social del aportante],
	-- tipoDocumento
	  CASE P.perTipoIdentificacion
			WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			WHEN 'PASAPORTE' THEN 'PA'
			WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
			WHEN 'REGISTRO_CIVIL' THEN 'RC'
			WHEN 'NIT' THEN 'NI'
			WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
			WHEN 'PERM_PROT_TEMPORAL' THEN 'PT' ELSE ''
		END AS [Tipo de documento del aportante],
	-- perNumeroIdentificacion
	  P.perNumeroIdentificacion AS [Número de documento del aportante],
	-- Nombre del Cotizante 
	   CONCAT(Cotizante.perPrimerNombre, ' ', Cotizante.perSegundoNombre, ' ', Cotizante.perPrimerApellido, ' ', Cotizante.perSegundoApellido) AS [Nombre del Cotizante],
	--Tipo documento cotizante 
		 CASE Cotizante.perTipoIdentificacion
			WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			WHEN 'PASAPORTE' THEN 'PA'
			WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
			WHEN 'REGISTRO_CIVIL' THEN 'RC'
			WHEN 'NIT' THEN 'NI'
			WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
			WHEN 'PERM_PROT_TEMPORAL' THEN 'PT' ELSE ''
		END AS [Tipo documento cotizante],
	--Número de documento del cotizante 
	Cotizante.perNumeroIdentificacion AS [Número de documento del cotizante],
	--Periodo de mora 
	FORMAT(c.carPeriodoDeuda, 'yyyy-MM') AS [Periodo de mora],
	--Valor de la cartera 
	CASE WHEN cast(carDep.cadDeudaReal as numeric(19,0)) = 0 THEN cast(carDep.cadDeudaPresunta as numeric(19,0)) ELSE cast(carDep.cadDeudaReal as numeric(19,0)) END 
	AS [Valor de la cartera],
	-- Última accion de cobro  ### ajustar la logica para traer la data de la ultima acción de cobro ### FORMAT(X.bcaFecha, 'dd/MM/yyyy')
	bc.bcaActividad AS  [Última acción de cobro],
	-- FECHA ULTIMA ACCION DE COBRO ### ajustar la logica para traer la data de la ultima acción de cobro ###
	CASE WHEN bc.bcaFecha IS NULL THEN FORMAT(cast(c.carFechaAsignacionAccion as date), 'dd/MM/yyyy')  else FORMAT(bc.bcaFecha, 'dd/MM/yyyy')  end AS [Fecha última acción de cobro],
	-- clasificaciondelestadodelaportante
  
	 ISNULL ( ( 
		SELECT TOP 1
			CASE 
				WHEN bc.bcaActividad IN ('ACIVACION_LIQUIDACION')
				THEN '3'
				WHEN bc.bcaActividad IN ('ACTIVACION_REESTRUCTURACION')
				THEN '2'
				ELSE '1'
			END 
			
		FROM 
			persona per
			inner join  Cartera c on c.carPersona = per.perId
			INNER JOIN BitacoraCartera bc WITH(NOLOCK) 
					ON bc.bcaPersona = c.carPersona 
					AND bc.bcaFecha <= @FECHA_PROCESAMIENTO
					AND bc.bcaActividad IN ('ACIVACION_LIQUIDACION', 'ACTIVACION_REESTRUCTURACION')
		WHERE per.perNumeroIdentificacion = P.perNumeroIdentificacion
		GROUP BY 
			per.perId,
			bc.bcaActividad,
			bc.bcaFecha
		ORDER BY bc.bcaFecha DESC

	  ), '1') AS [Clasificacion del estado del aportante],
	-- Aportantes expulsados
		CASE WHEN c.carTipoLineaCobro = 'LC1' THEN '2'
			 WHEN c.carTipoLineaCobro = 'C6' THEN '1' END 
		AS [Aportantes expulsados]
INTO #DesagregadoCartera
from Cartera c 
INNER JOIN Persona p ON c.carpersona = p.perid 
INNER JOIN Empresa emp ON emp.empPersona = p.perId
INNER JOIN Empleador emple ON emple.empEmpresa = emp.empId
INNER JOIN CarteraDependiente carDep ON carDep.cadCartera = c.carId AND carDep.cadDeudaPresunta > 0
INNER JOIN Persona Cotizante ON Cotizante.perId = carDep.cadPersona
INNER JOIN carteraagrupadora ca WITH(NOLOCK) ON ca.cagCartera = c.carid
INNER JOIN #BitacoraFinal bc WITH(NOLOCK) ON bc.bcaPersona = c.carPersona 
LEFT JOIN Conveniopago cp WITH(NOLOCK) ON cp.copPersona = c.carpersona	
WHERE 
c.carEstadoOperacion = 'VIGENTE' 
AND (DATEDIFF(DAY,c.carPeriodoDeuda,CAST(@FECHA_PROCESAMIENTO AS DATE))) >= 30 
AND c.carFechaCreacion <= @FECHA_PROCESAMIENTO
AND c.carDeudaPresunta > 0 
AND c.cartipolineacobro IN ('LC1', 'C6')
AND emple.empEstadoEmpleador NOT IN ('INACTIVO')
--AND p.perNumeroIdentificacion in ('1000412506','1000482','1001243965')


--#################################################################################################
-------------------------SUSPENDIDOS - C6
--#################################################################################################
DROP TABLE IF EXISTS #DesagregadoSuspendidos
Select DISTINCT
	-------------------------------------------------------------------------------------------------
	-- Periodo del reporte
	   FORMAT(GETDATE(), 'yyyy-MM') [Periodo del reporte],
	-------------------------------------------------------------------------------------------------
	-- codAdministradora
	  (SELECT cnsValor FROM Constante WHERE cnsNombre='CAJA_COMPENSACION_CODIGO') AS [Cód. Administradora],
	-- nombreRazonSocial
	  P.perrazonsocial AS [Nombre o razón social del aportante],
	-- tipoDocumento
	  CASE P.perTipoIdentificacion
			WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			WHEN 'PASAPORTE' THEN 'PA'
			WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
			WHEN 'REGISTRO_CIVIL' THEN 'RC'
			WHEN 'NIT' THEN 'NI'
			WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
			WHEN 'PERM_PROT_TEMPORAL' THEN 'PT' ELSE ''
		END AS [Tipo de documento del aportante],
	-- perNumeroIdentificacion
	  P.perNumeroIdentificacion AS [Número de documento del aportante],
	-- Nombre del Cotizante 
	   CONCAT(Cotizante.perPrimerNombre, ' ', Cotizante.perSegundoNombre, ' ', Cotizante.perPrimerApellido, ' ', Cotizante.perSegundoApellido) AS [Nombre del Cotizante],
	--Tipo documento cotizante 
		 CASE Cotizante.perTipoIdentificacion
			WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			WHEN 'PASAPORTE' THEN 'PA'
			WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
			WHEN 'REGISTRO_CIVIL' THEN 'RC'
			WHEN 'NIT' THEN 'NI'
			WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
			WHEN 'PERM_PROT_TEMPORAL' THEN 'PT' ELSE ''
		END AS [Tipo documento cotizante],
	--Número de documento del cotizante 
	Cotizante.perNumeroIdentificacion AS [Número de documento del cotizante],
	--Periodo de mora 
	FORMAT(c.carPeriodoDeuda, 'yyyy-MM') AS [Periodo de mora],
	--Valor de la cartera 
	CASE WHEN cast(carDep.cadDeudaReal as numeric(19,0)) = 0 THEN cast(carDep.cadDeudaPresunta as numeric(19,0)) ELSE cast(carDep.cadDeudaReal as numeric(19,0)) END 
	AS [Valor de la cartera],
	-- Última accion de cobro  ### ajustar la logica para traer la data de la ultima acción de cobro ###
	bc.bcaActividad AS  [Última acción de cobro],
	-- FECHA ULTIMA ACCION DE COBRO ### ajustar la logica para traer la data de la ultima acción de cobro ###
	CASE WHEN bc.bcaFecha IS NULL THEN FORMAT(cast(c.carFechaAsignacionAccion as date), 'dd/MM/yyyy')  else FORMAT(bc.bcaFecha, 'dd/MM/yyyy')  end AS [Fecha última acción de cobro],
	-- clasificaciondelestadodelaportante
  
	 ISNULL ( ( 
		SELECT TOP 1
			CASE 
				WHEN bc.bcaActividad IN ('ACIVACION_LIQUIDACION')
				THEN '3'
				WHEN bc.bcaActividad IN ('ACTIVACION_REESTRUCTURACION')
				THEN '2'
				ELSE '1'
			END 
			
		FROM 
			persona per
			inner join  Cartera c on c.carPersona = per.perId
			INNER JOIN BitacoraCartera bc WITH(NOLOCK) 
					ON bc.bcaPersona = c.carPersona 
					AND bc.bcaFecha <= @FECHA_PROCESAMIENTO
					AND bc.bcaActividad IN ('ACIVACION_LIQUIDACION', 'ACTIVACION_REESTRUCTURACION')
		WHERE per.perNumeroIdentificacion = P.perNumeroIdentificacion
		GROUP BY 
			per.perId,
			bc.bcaActividad,
			bc.bcaFecha
		ORDER BY bc.bcaFecha DESC

	  ), '1') AS [Clasificacion del estado del aportante],
	-- Aportantes expulsados
		CASE WHEN c.carTipoLineaCobro = 'LC1' THEN '2'
			 WHEN c.carTipoLineaCobro = 'C6' THEN '1' END 
		AS [Aportantes expulsados]
INTO #DesagregadoSuspendidos
FROM Cartera c 
INNER JOIN Persona p ON c.carpersona = p.perid 
INNER JOIN Empresa emp ON emp.empPersona = p.perId
INNER JOIN Empleador emple ON emple.empEmpresa = emp.empId
INNER JOIN CarteraDependiente carDep ON carDep.cadCartera = c.carId AND carDep.cadDeudaPresunta > 0
INNER JOIN Persona Cotizante ON Cotizante.perId = carDep.cadPersona
INNER JOIN carteraagrupadora ca WITH(NOLOCK) ON ca.cagCartera = c.carid
LEFT  JOIN #BitacoraFinal bc WITH(NOLOCK) ------SOLO POR EL GLPI80138 20240429 SE CAMBIA INNER POR LEFT 
ON bc.bcaPersona = c.carPersona --AND bc.bcaResultado = 'EXITOSO'   
LEFT JOIN Conveniopago cp WITH(NOLOCK) ON cp.copPersona = c.carpersona
WHERE c.carDeudaPresunta > 0 AND c.cartipolineacobro IN ('LC1', 'C6') AND c.carEstadoOperacion = 'VIGENTE'
AND emple.empEstadoEmpleador = 'INACTIVO'
AND emple.empMotivoDesafiliacion = 'EXPULSION_POR_MOROSIDAD'
AND DATEDIFF(DAY,c.carFechaCreacion,CAST(@FECHA_PROCESAMIENTO AS DATE)) >= 30	

SELECT * FROM #DesagregadoSuspendidos d where d.[Última acción de cobro] IS NOT NULL
UNION
SELECT * FROM #DesagregadoCartera d where d.[Última acción de cobro] IS NOT NULL


END



/**
Script antes del cambio solicitado en el GLPI 93620
*/


--	SELECT 
--	-------------------------------------------------------------------------------------------------
--	-- Periodo del reporte
--	   FORMAT(GETDATE(), 'yyyy-MM') [Periodo del reporte],
--	-------------------------------------------------------------------------------------------------
--	-- codAdministradora
--	  (SELECT cnsValor FROM Constante WHERE cnsNombre='CAJA_COMPENSACION_CODIGO') AS [Cód. Administradora],

--	---------------------------------------------------------------------------------------------------
--	-- nombreRazonSocial
--	  P.perrazonsocial AS [Nombre o razón social del aportante],
--	---------------------------------------------------------------------------------------------------
--	-- tipoDocumento
--	  CASE P.perTipoIdentificacion
--			WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
--			WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
--			WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
--			WHEN 'PASAPORTE' THEN 'PA'
--			WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
--			WHEN 'REGISTRO_CIVIL' THEN 'RC'
--			WHEN 'NIT' THEN 'NI'
--			WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
--			WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
--		END AS [Tipo de documento del aportante],
--	---------------------------------------------------------------------------------------------------
--	-- perNumeroIdentificacion
--	  P.perNumeroIdentificacion AS [Número de documento del aportante],

--	---------------------------------------------------------------------------------------------------
--	-- Nombre del Cotizante 
--	   CONCAT(Cotizante.perPrimerNombre, ' ', Cotizante.perSegundoNombre, ' ', Cotizante.perPrimerApellido, ' ', Cotizante.perSegundoApellido) AS [Nombre del Cotizante],

--	---------------------------------------------------------------------------------------------------
--	--Tipo documento cotizante 
--		 CASE Cotizante.perTipoIdentificacion
--			WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
--			WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
--			WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
--			WHEN 'PASAPORTE' THEN 'PA'
--			WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
--			WHEN 'REGISTRO_CIVIL' THEN 'RC'
--			WHEN 'NIT' THEN 'NI'
--			WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
--			WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
--		END AS [Tipo documento cotizante],
--	---------------------------------------------------------------------------------------------------
--	--Número de documento del cotizante 
--	Cotizante.perNumeroIdentificacion AS [Número de documento del cotizante],

--	---------------------------------------------------------------------------------------------------
--	--Periodo de mora 
--	FORMAT(c.carPeriodoDeuda, 'yyyy-MM') AS [Periodo de mora],

--	---------------------------------------------------------------------------------------------------
--	--Valor de la cartera 
--	CAST(carDep.cadDeudaPresunta AS BIGINT) AS [Valor de la cartera],

--	---------------------------------------------------------------------------------------------------
--	--Última accion de cobro 
--	  (
--		SELECT 
--		  ISNULL( 
--			MAX(
--			  CASE

--				WHEN bb.bcaActividad IN ('B1','B2' ) 
--				THEN '1'---aviso de incumplimiento

--				WHEN bb.bcaActividad IN('C1','C2', 'D2', 'E2', 'REGISTRO_NOTIFICACION_PERSONAL') -- 
--				THEN '3'

--				WHEN bb.bcaActividad IN ('D1', 'E1', 'F1', 'F2', 'G2', 'H2') 
--				THEN '4'

--				--Cobros judiciales es un proceso interno de cada caja por esto no aplica
--				--WHEN bb.bcaActividad IN ('F1','H2') 
--				--THEN '5' 
--				--else bb.bcaActividad
--				ELSE '6'

--			  END
--			)  ,6
--		  )
--		FROM Bitacoracartera bb
--		INNER JOIN   (

--		  SELECT 
--			MAX(X.bcaFecha) AS fecha, 
--			X.bcanumerooperacion 
--		  FROM bitacoracartera X 
--		  WHERE 
--			X.bcaResultado IN ('EXITOSO')
--			AND X.bcaFecha <= @FECHA_PROCESAMIENTO
		
--		  GROUP BY x.bcanumerooperacion
--		) Y ON bb.bcaFecha = y.fecha 
--			AND y.bcaNumeroOperacion = bb.bcaNumeroOperacion

--		WHERE 
--			bc.bcaNumeroOperacion = bb.bcaNumeroOperacion AND
--			bb.bcaActividad in ('B1','B2','C1','C2','D2','E2','REGISTRO_NOTIFICACION_PERSONAL','D1','E1','F1','F2','G2','H2')


--	  ) AS  [Última acción de cobro],

--	---------------------------------------------------------------------------------------------------
--	-- FECHA ULTIMA ACCION DE COBRO
--	  (
--		SELECT 
--			ISNULL(
--				MAX( --FORMAT(X.bcaFecha, 'dd/MM/yyyy')
--					CASE
--						WHEN X.bcaActividad IN ('A2', 'B1','B2', 'C1','C2', 'D2', 'E2', 'REGISTRO_NOTIFICACION_PERSONAL', 'D1', 'E1', 'F1', 'F2', 'G2', 'H2' ) 
--						THEN FORMAT(X.bcaFecha, 'dd/MM/yyyy')
--						--WHEN X.bcaActividad = 'A2'
--						--THEN NULL
--						ELSE NULL
--					  END
--				)
--			,FORMAT(c.carFechaCreacion, 'dd/MM/yyyy')) 
--		FROM bitacoracartera x 
--		WHERE 
--		  bc.bcanumerooperacion = x.bcaNumeroOperacion 
--		  --bc.bcaPersona = c.carPersona 
--		  AND  X.bcaResultado IN ('EXITOSO','PUBLICADO') 
--		  AND X.bcaFecha <= @FECHA_PROCESAMIENTO
--		 -- AND x.bcaActividad in ('A2', 'B1','B2', 'C1','C2', 'D2', 'E2', 'REGISTRO_NOTIFICACION_PERSONAL', 'D1', 'E1', 'F1', 'F2', 'G2', 'H2')
--	  ) AS [Fecha última acción de cobro],

--	---------------------------------------------------------------------------------------------------
--	-- clasificaciondelestadodelaportante
  
--	 ISNULL ( ( 
--		SELECT TOP 1
--			CASE 
--				WHEN bc.bcaActividad IN ('ACIVACION_LIQUIDACION')
--				THEN '3'
--				WHEN bc.bcaActividad IN ('ACTIVACION_REESTRUCTURACION')
--				THEN '2'
--				ELSE '1'
--			END 
			
--		FROM 
--			persona per
--			inner join  Cartera c on c.carPersona = per.perId
--			INNER JOIN BitacoraCartera bc WITH(NOLOCK) 
--					ON bc.bcaPersona = c.carPersona 
--					AND bc.bcaFecha <= @FECHA_PROCESAMIENTO
--					AND bc.bcaActividad IN ('ACIVACION_LIQUIDACION', 'ACTIVACION_REESTRUCTURACION')
--		WHERE per.perNumeroIdentificacion = P.perNumeroIdentificacion
--		GROUP BY 
--			per.perId,
--			bc.bcaActividad,
--			bc.bcaFecha
--		ORDER BY bc.bcaFecha DESC

--	  ), '1') AS [Clasificacion del estado del aportante],
	
	 
--	 ---------------------------------------------------------------------------------------------------
--	-- Aportantes expulsados
--		'2' AS [Aportantes expulsados] -- 2

--	--#################################################################################################
--	-- TABLA TEMPORAL PARA ALMACENAR EL DESAGREGADO
--	INTO #DesagregadoCartera -- crea una tabla temporal
--	--#################################################################################################

--	from 
  
--	  Cartera c 

--	  INNER JOIN Persona p ON c.carpersona = p.perid 
--	  INNER JOIN Empresa emp ON emp.empPersona = p.perId
--	  INNER JOIN Empleador emple ON emple.empEmpresa = emp.empId

--	  INNER JOIN CarteraDependiente carDep 
--		ON carDep.cadCartera = c.carId
--		AND carDep.cadDeudaPresunta > 0

--	  INNER JOIN Persona Cotizante ON Cotizante.perId = carDep.cadPersona
  
--	  INNER JOIN carteraagrupadora ca WITH(NOLOCK) ON ca.cagCartera = c.carid
--	  INNER JOIN BitacoraCartera bc WITH(NOLOCK) 
--		      ON bc.bcaPersona = c.carPersona 
--		     AND bc.bcaResultado = 'EXITOSO' 
  
--	  LEFT JOIN Conveniopago cp WITH(NOLOCK) ON cp.copPersona = c.carpersona
		 
--	  WHERE 
--		c.carEstadoOperacion = 'VIGENTE' 
--		AND (DATEDIFF(DAY,c.carPeriodoDeuda,CAST(@FECHA_PROCESAMIENTO AS DATE))) >= 30 
--		AND c.carFechaCreacion <= @FECHA_PROCESAMIENTO
--		AND c.carDeudaPresunta > 0 
--		AND c.cartipolineacobro IN ('LC1', 'C6')
--		AND emple.empEstadoEmpleador NOT IN ('INACTIVO')
		
--	/*order by 
--		p.perRazonSocial*/

--	  GROUP BY 
--	   P.perrazonsocial,
--	   P.perTipoIdentificacion,
--	   P.perNumeroIdentificacion,
--	   Cotizante.perPrimerNombre,
--	   Cotizante.perSegundoNombre, 
--	   Cotizante.perPrimerApellido, 
--	   Cotizante.perSegundoApellido,
--	   Cotizante.perTipoIdentificacion, 
--	   Cotizante.perNumeroIdentificacion,
--	   c.carPeriodoDeuda,
--	   carDep.cadDeudaPresunta,
--	   bc.bcaNumeroOperacion,
--	   c.carFechaCreacion

--	--#################################################################################################
--	-------------------------SUSPENDIDOS - C6
--	--#################################################################################################
--	SELECT 
--	-------------------------------------------------------------------------------------------------
--	-- Periodo del reporte
--	   FORMAT(GETDATE(), 'yyyy-MM') [Periodo del reporte],

--	---------------------------------------------------------------------------------------------------
--	-- codAdministradora
--		(SELECT cnsValor FROM Constante WHERE cnsNombre='CAJA_COMPENSACION_CODIGO') AS [Cód. Administradora],


--	---------------------------------------------------------------------------------------------------
--	-- nombreRazonSocial
--		P.perrazonsocial AS [Nombre o razón social del aportante],         
  
--	---------------------------------------------------------------------------------------------------
--	-- tipoDocumento
--		CASE P.perTipoIdentificacion
--			WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
--			WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
--			WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
--			WHEN 'PASAPORTE' THEN 'PA'
--			WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
--			WHEN 'REGISTRO_CIVIL' THEN 'RC'
--			WHEN 'NIT' THEN 'NI'
--			WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
--			WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
--		END AS [Tipo de documento del aportante],
--	---------------------------------------------------------------------------------------------------
--	-- perNumeroIdentificacion
--		P.perNumeroIdentificacion AS [Número de documento del aportante],

--	---------------------------------------------------------------------------------------------------
--	--Nombre del Cotizante 
--		CONCAT(Cotizante.perPrimerNombre, ' ', Cotizante.perSegundoNombre, ' ', Cotizante.perPrimerApellido, ' ', Cotizante.perSegundoApellido) as [Nombre del Cotizante],
--	---------------------------------------------------------------------------------------------------
--	--Tipo documento cotizante
--		CASE Cotizante.perTipoIdentificacion
--			WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
--			WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
--			WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
--			WHEN 'PASAPORTE' THEN 'PA'
--			WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
--			WHEN 'REGISTRO_CIVIL' THEN 'RC'
--			WHEN 'NIT' THEN 'NI'
--			WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
--			WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
--		END AS [Tipo documento cotizante],
--	---------------------------------------------------------------------------------------------------
--	--Número de documento del cotizante 
--		Cotizante.perNumeroIdentificacion AS [Número de documento del cotizante],

--	---------------------------------------------------------------------------------------------------
--	--Periodo de mora 
--		FORMAT(c.carPeriodoDeuda, 'yyyy-MM') AS [Periodo de mora],

--	---------------------------------------------------------------------------------------------------
--	--Valor de la cartera 
--		CAST(carDep.cadDeudaPresunta AS BIGINT) AS [Valor de la cartera],

--	---------------------------------------------------------------------------------------------------
--	-- ultimaAccion

--	  (
--		SELECT 
--		  ISNULL( 
--			MAX(
--			  CASE

--				WHEN bb.bcaActividad IN ('B1','B2' ) 
--				THEN '1'---aviso de incumplimiento

--				WHEN bb.bcaActividad IN('C1','C2', 'D2', 'E2') -- 
--				THEN '3'

--				WHEN bb.bcaActividad IN ('D1', 'E1', 'F1', 'F2', 'G2', 'H2') 
--				THEN '4'

--				ELSE '6'

--			  END
--			)  ,6
--		  )
--		FROM Bitacoracartera bb
--		INNER JOIN   (

--		  SELECT 
--			MAX(X.bcaFecha) AS fecha, 
--			X.bcanumerooperacion 
--		  FROM bitacoracartera X 
--		  WHERE 
--			X.bcaResultado IN ('EXITOSO')
--			AND X.bcaFecha <= @FECHA_PROCESAMIENTO
--		  GROUP BY x.bcanumerooperacion
--		) Y
--		ON bb.bcaFecha = y.fecha 
--		AND y.bcaNumeroOperacion = bb.bcaNumeroOperacion
--		WHERE bc.bcaNumeroOperacion = bb.bcaNumeroOperacion


--	  ) AS  [Última acción de cobro],
--	---------------------------------------------------------------------------------------------------
--	-- FECHA ULTIMA ACCION DE COBRO

--	  (
--		SELECT TOP 1
--			CASE 
--				WHEN X.bcaActividad IN ('B1','B2','C1','C2', 'D2', 'E2','D1', 'E1', 'F1', 'F2', 'G2', 'H2') 
--				THEN ISNULL( MAX( FORMAT(X.bcaFecha, 'dd/MM/yyyy') ) ,FORMAT(c.carFechaCreacion, 'dd/MM/yyyy') ) 
--				ELSE NULL
--			END 
--		FROM bitacoracartera x 
--		WHERE 
--		  x.bcaNumeroOperacion = bc.bcanumerooperacion 
--		  AND  X.bcaResultado IN ('EXITOSO','PUBLICADO') 
--		  AND X.bcaFecha <= @FECHA_PROCESAMIENTO
--		GROUP BY X.bcaActividad, X.bcaFecha
--		ORDER BY X.bcaFecha DESC
--	  ) AS [Fecha última acción de cobro],

--	  ---------------------------------------------------------------------------------------------------
--	-- clasificaciondelestadodelaportante
  
--	'5' AS [Clasificacion del estado del aportante],
  
--	  '1' AS [Aportantes expulsados] -- 1

--	INTO #DesagregadoSuspendidos

--	from 
  
--	  Cartera c 

--	  INNER JOIN Persona p ON c.carpersona = p.perid 
--	  INNER JOIN Empresa emp ON emp.empPersona = p.perId
--	  INNER JOIN Empleador emple ON emple.empEmpresa = emp.empId

--	  INNER JOIN CarteraDependiente carDep 
--		ON carDep.cadCartera = c.carId
--		AND carDep.cadDeudaPresunta > 0

--	  INNER JOIN Persona Cotizante ON Cotizante.perId = carDep.cadPersona
  
--	  INNER JOIN carteraagrupadora ca WITH(NOLOCK) ON ca.cagCartera = c.carid
--	  LEFT JOIN BitacoraCartera bc WITH(NOLOCK) ------SOLO POR EL GLPI80138 20240429 SE CAMBIA INNER POR LEFT 
--		ON bc.bcaPersona = c.carPersona 
--		AND bc.bcaResultado = 'EXITOSO' 
  
--	  LEFT JOIN Conveniopago cp WITH(NOLOCK) ON cp.copPersona = c.carpersona

--	WHERE 
--		c.carDeudaPresunta > 0 
--		AND c.cartipolineacobro IN ('LC1', 'C6')
--		AND c.carEstadoOperacion = 'VIGENTE'
--		AND emple.empEstadoEmpleador = 'INACTIVO'
--		--AND emple.empFechaRetiro BETWEEN '2016-11-18' AND @FECHA_PROCESAMIENTO  20240429 SOLO POR EL GLPI80138
--		AND emple.empMotivoDesafiliacion = 'EXPULSION_POR_MOROSIDAD'
--		AND DATEDIFF(DAY,c.carFechaCreacion,CAST(@FECHA_PROCESAMIENTO AS DATE)) >= 30	
----		select DATEADD(YEAR,-5, GETDATE())
--	 GROUP BY 
--		C.carTipoAccionCobro,
--		P.perRazonSocial,
--		p.perTipoIdentificacion,
--		p.perNumeroIdentificacion,
--		p.perDigitoVerificacion,
--		c.carId,
--		c.carTipoLineaCobro,
--		c.carPersona,
--		bc.bcaNumeroOperacion,
--		bc.bcaActividad,
--		cp.copId,
--		c.carFechaCreacion,
--		Cotizante.perPrimerNombre, 
--		Cotizante.perSegundoNombre, 
--		Cotizante.perPrimerApellido,
--		Cotizante.perSegundoApellido,
--		Cotizante.perTipoIdentificacion,
--		Cotizante.perNumeroIdentificacion,
--		c.carPeriodoDeuda,
--		carDep.cadDeudaPresunta


--	--AGRUPAMIENTO
--	SELECT 
--		[Periodo del reporte],
--		[Cód. Administradora],
--		[Nombre o razón social del aportante],
--		[Tipo de documento del aportante],
--		[Número de documento del aportante],
--		[Nombre del Cotizante],
--		[Tipo documento cotizante],
--		[Número de documento del cotizante],
--		[Periodo de mora],
--		[Valor de la cartera],
--		MIN([Última acción de cobro]) [Última acción de cobro],
--		CASE 
--			WHEN MIN([Última acción de cobro]) = 6
--			THEN NULL
--			ELSE MIN([Fecha última acción de cobro]) 
--		END [Fecha última acción de cobro],
--		[Clasificacion del estado del aportante],
--		[Aportantes expulsados]
--	FROM  #DesagregadoSuspendidos
--	GROUP BY
--		[Periodo del reporte],
--		[Cód. Administradora],
--		[Nombre o razón social del aportante],
--		[Tipo de documento del aportante],
--		[Número de documento del aportante],
--		[Nombre del Cotizante],
--		[Tipo documento cotizante],
--		[Número de documento del cotizante],
--		[Periodo de mora],
--		[Valor de la cartera],
		
--		[Clasificacion del estado del aportante],
--		[Aportantes expulsados]

--		UNION

--	SELECT 
--		[Periodo del reporte],
--		[Cód. Administradora],
--		[Nombre o razón social del aportante],
--		[Tipo de documento del aportante],
--		[Número de documento del aportante],
--		[Nombre del Cotizante],
--		[Tipo documento cotizante],
--		[Número de documento del cotizante],
--		[Periodo de mora],
--		[Valor de la cartera],
--		MIN([Última acción de cobro]) [Última acción de cobro],
--		CASE 
--			WHEN MIN([Última acción de cobro]) = 6
--			THEN NULL
--			ELSE MIN([Fecha última acción de cobro]) 
--		END [Fecha última acción de cobro],
--		[Clasificacion del estado del aportante],
--		[Aportantes expulsados]
--	FROM  #DesagregadoCartera 
--	GROUP BY
--		[Periodo del reporte],
--		[Cód. Administradora],
--		[Nombre o razón social del aportante],
--		[Tipo de documento del aportante],
--		[Número de documento del aportante],
--		[Nombre del Cotizante],
--		[Tipo documento cotizante],
--		[Número de documento del cotizante],
--		[Periodo de mora],
--		[Valor de la cartera],
--		--[Última acción de cobro],
--		[Clasificacion del estado del aportante],
--		[Aportantes expulsados]