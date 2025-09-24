/****** Object:  StoredProcedure [dbo].[reporteEmpresasAportantes]    Script Date: 2/12/2024 9:35:37 a. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteEmpresasAportantes]    Script Date: 15/10/2024 3:14:52 p. m. ******/
--/****** Object:  StoredProcedure [dbo].[reporteEmpresasAportantes]    Script Date: 30/09/2024 2:49:44 p. m. ******/
--SET ANSI_NULLS ON
--GO
--SET QUOTED_IDENTIFIER ON
--GO
--/****** Object:  StoredProcedure [dbo].[reporteEmpresasAportantes]    Script Date: 14/06/2023 10:47:50 a. m. ******/
--/****** Object:  StoredProcedure [dbo].[reporteEmpresasAportantes]    Script Date: 09/06/2023 14:38:47 p. m. ******/
--/****** Object:  StoredProcedure [dbo].[reporteEmpresasAportantes]    Script Date: 24/05/2023 9:39:17 a. m. ******/
--/****** Object:  StoredProcedure [dbo].[reporteEmpresasAportantes]    Script Date: 23/05/2023 12:52:26 p. m. ******/
--/****** Object:  StoredProcedure [dbo].[reporteEmpresasAportantes]    Script Date: 23/05/2023 12:00:34 p. m. ******/
--/****** Object:  StoredProcedure [dbo].[reporteEmpresasAportantes]    Script Date: 04/05/2023 11:08:46 a. m. ******/
--/****** Object:  StoredProcedure [dbo].[reporteEmpresasAportantes]    Script Date: 17/04/2023 11:08:49 a. m. ******/
--/****** Object:  StoredProcedure [dbo].[reporteEmpresasAportantes]    Script Date: 23/03/2023 4:36:30 p. m. ******/
--/****** Object:  StoredProcedure [dbo].[reporteEmpresasAportantes]    Script Date: 14/03/2023 3:05:24 p. m. ******/
--/****** Object:  StoredProcedure [dbo].[reporteEmpresasAportantes]    Script Date: 11/10/2022 1:07:41 p. m. ******/
--/****** Object:  StoredProcedure [dbo].[reporteEmpresasAportantes]    Script Date: 09/08/2022 3:44:30 p. m. ******/
 
---- =============================================
---- Author:      Miguel Angel Perilla
---- Update Date: 06 Julio 2022
---- Description: Procedimiento almacenado para obtener el resultado que hace referencia al reporte 1.
---- Reporte 1
-----update : 20220809
-----exec reporteEmpresasAportantes '2023-06-01','2023-06-14'

---- =============================================
CREATE OR ALTER     PROCEDURE [dbo].[reporteEmpresasAportantes] (@FECHA_INICIAL DATE, @FECHA_FINAL DATE )

AS
BEGIN
--DECLARE
--@FECHA_INICIAL DATE = '2025-01-01'
--DECLARE @FECHA_FINAL DATE = '2025-01-31' 
SET NOCOUNT ON
SET ANSI_NULLS ON
SET QUOTED_IDENTIFIER ON

drop table if exists #temporalN
drop table if exists #tempAportesPensionadosIndependientes
drop table if exists #PlanillasN
DROP TABLE IF EXISTS #tempAportes
DROP TABLE IF EXISTS #TEMP_EMPRESAS_APORTANTES
drop table if exists #TEMP_EMPRESAS_APORTANTES
drop table if exists #ClasificacionRolAfiliadoIndependientePensionado

select
	regTipoIdentificacionAportante,
	regNumeroIdentificacionAportante,
	regNombreAportante,
	apdAporteGeneral,
	apgempresa,
	sum (isnull (ROUND (diferenciaAporteCot, 0), 0)) as totalAporteObligatorio,
	sum (isnull (ROUND (diferenciaMora, 0), 0)) as totalInteresesMora 
into #PlanillasN
from
	pila.RegistroGeneral reg
	inner join aportedetalladoregistrocontrolN pdn on planillaN = regnumplanilla
	inner join AporteGeneral apg on apdAporteGeneral = apgid
where
	pdn.regFechaPagoAporte between @FECHA_INICIAL
	and @FECHA_FINAL
group by
	regTipoIdentificacionAportante,
	regNumeroIdentificacionAportante,
	regNombreAportante,
	apdAporteGeneral,
	apgempresa

select
	regTipoIdentificacionAportante,
	regNumeroIdentificacionAportante,
	regNombreAportante,
	apdAporteGeneral,
	sum (isnull (ROUND (diferenciaAporteCot, 0), 0)) as totalAporteObligatorio,
	sum (isnull (ROUND (diferenciaMora, 0), 0)) as totalInteresesMora --regFechaRecaudo, 
	into #temporalN
from
	pila.RegistroGeneral reg
	inner join aportedetalladoregistrocontrolN pdn on planillaN = regnumplanilla
	inner join AporteGeneral apg on apdAporteGeneral = apgid --where pdn.regFechaPagoAporte between @FECHA_INICIAL and @FECHA_FINAL
group by
	regTipoIdentificacionAportante,
	regNumeroIdentificacionAportante,
	regNombreAportante,
	apdaportegeneral

SELECT * 
		INTO 
		#ClasificacionRolAfiliadoIndependientePensionado
		FROM (
		select roaid as identificadorRolAfiliado, solClasificacion, solid, sapId,
		CASE WHEN solResultadoProceso = 'APROBADA'
		and sapEstadoSolicitud = 'CERRADA' THEN 1 ELSE 0 END AS solicitudOk,
		ROW_NUMBER() over (PARTITION BY ROAID ORDER BY SOLID DESC) AS ID
		from

		RolAfiliado

		INNER JOIN SolicitudAfiliacionPersona ON sapRolAfiliado = roaid 
		INNER JOIN Solicitud ON solid = sapSolicitudGlobal
		WHERE 
		--roaestadoafiliado = 'ACTIVO' and 
		roatipoAFILIADO in ('TRABAJADOR_INDEPENDIENTE','PENSIONADO')
		) T WHERE T.ID=1 --AND solClasificacion NOT IN ('FIDELIDAD_25_ANIOS')




		SELECT
	T.perTipoIdentificacion as tipoId,
	T.perNumeroIdentificacion as numeroId,
	T.perrazonsocial nombre,
	T.apdAporteGeneral,
	apgEmpresa,
	T.totalAporteObligatorio -- isnull(N.totalAporteObligatorio, 0)
	as aporteOriginal,
	T.totalInteresesMora -- isnull(N.totalInteresesMora, 0)
	AS interesOriginal 
into #tempAportes
FROM
	(
		SELECT
			perTipoIdentificacion,
			perNumeroIdentificacion,
			perrazonsocial,
			apgid as apdAporteGeneral,
			apgEmpresa,
			sum (isnull (ROUND (moa.moaValorAporte, 0), 0)) as totalAporteObligatorio,
			sum (isnull (ROUND (moa.moaValorInteres, 0), 0)) as totalInteresesMora
		FROM
			persona PA
			INNER JOIN empresa e WITH (nolock) on e.emppersona = perid
			INNER JOIN empleador m WITH (nolock) on m.empempresa = e.empid
			LEFT JOIN AporteGeneral APGA WITH (nolock) on APGA.apgEmpresa = e.empid
			left join MovimientoAporte as moa on moa.moaAporteGeneral = APGA.apgId and moa.moaTipoMovimiento in ('RECAUDO_PILA_AUTOMATICO','RECAUDO_MANUAL','RECAUDO_MANUAL_APORTES')
			where 
			 APGA.apgFechaRecaudo BETWEEN @FECHA_INICIAL
			AND @FECHA_FINAL
		

		GROUP BY
			perTipoIdentificacion,
			perNumeroIdentificacion,
			perrazonsocial,
			apgid,
			apgEmpresa
	) T
	LEFT JOIN #temporalN N ON T.apdAporteGeneral=N.apdAporteGeneral 

	
SELECT
pertipoidentificacion, 
		pernumeroidentificacion, 
		perrazonsocial,munCodigo, 
		ubidireccionfisica, 
		afiId,
		roaEstadoAfiliado,
		roaTipoAfiliado, 
		roaMotivoDesafiliacion,
		solClasificacion,
	T.apgid,
	T.totalAporteObligatorio -- isnull(N.totalAporteObligatorio, 0) 
	as aporteOriginal,
	T.totalInteresesMora -- isnull(N.totalInteresesMora, 0)
	AS interesOriginal 
	into #tempAportesPensionadosIndependientes
FROM
	(
		SELECT
			perTipoIdentificacion,
			perNumeroIdentificacion,
			perrazonsocial,
			solClasificacion,
			apgid,

			solicitudOk,
			munCodigo, 
		ubidireccionfisica, 
		afiId,
		roaEstadoAfiliado,
		roaTipoAfiliado, 
		roaMotivoDesafiliacion,
			sum (isnull (ROUND (moa.moaValorAporte,0), 0)) as totalAporteObligatorio,
			sum (isnull (ROUND (moa.moaValorInteres, 0), 0)) as totalInteresesMora
		FROM
			persona 	 
		inner join afiliado on perid=afipersona
		inner join rolafiliado on roaafiliado=afiid
		-----DATOS DE LA SOLICITUD PARA LA_CLASIFICACION cambio 20230314 olga vega para eximir a los pensionados de 25 años
		
		
		inner join ubicacion on perubicacionPRINCIPAL=ubiid
		LEFT join municipio on munid=ubimunicipio
		LEFT join departamento on depid=mundepartamento
	
		left join aportegeneral on apgpersona=perid and case when apgtiposolicitante = 'INDEPENDIENTE' THEN 'TRABAJADOR_INDEPENDIENTE' WHEN apgtiposolicitante='PENSIONADO' THEN 'PENSIONADO' END =roatipoafiliado
			AND apgFechaRecaudo BETWEEN @FECHA_INICIAL AND @FECHA_FINAL
		LEFT JOIN AporteDetallado apgdet ON apgdet.apdAporteGeneral = apgId
		left join MovimientoAporte as moa on moa.moaAporteGeneral = apgId and apgdet.apdid=moa.moaAporteDetallado and moa.moaTipoMovimiento in ('RECAUDO_PILA_AUTOMATICO','RECAUDO_MANUAL','RECAUDO_MANUAL_APORTES')
		INNER JOIN #ClasificacionRolAfiliadoIndependientePensionado T ON T.identificadorRolAfiliado = roaId

			WHERE 
		roatipoAFILIADO in ('TRABAJADOR_INDEPENDIENTE','PENSIONADO')
		--AND		convert(date,roaFechaAfiliacion) <= @FECHA_FINAL 

	GROUP BY
		pertipoidentificacion, 
		pernumeroidentificacion, 
		perrazonsocial,munCodigo, 
		ubidireccionfisica, 
		afiId,
		roaEstadoAfiliado,
		roaTipoAfiliado, 
		roaMotivoDesafiliacion,
		apgid,
		solClasificacion,
		solicitudOk

	) T
	LEFT JOIN #temporalN N ON T.apgid=N.apdAporteGeneral 
	where  ((solClasificacion NOT IN ('FIDELIDAD_25_ANIOS'))
	or ( solClasificacion IN ('FIDELIDAD_25_ANIOS') and T.totalAporteObligatorio - isnull(N.totalAporteObligatorio, 0)>0)
	
	or ( solClasificacion IN ('FIDELIDAD_25_ANIOS') and T.totalInteresesMora - isnull(N.totalInteresesMora, 0)>0))
	or
	 (
	 (
	 (T.totalAporteObligatorio - isnull(N.totalAporteObligatorio, 0)>0 or T.totalInteresesMora - isnull(N.totalInteresesMora, 0)>0)
	 and T.solicitudOk <> 1) 
	 or T.solicitudOk = 1
	 ) 

	 
--/---------------------------------------**********---------------------------------------\--
--                          REPORTE DE EMPRESAS APORTANTES  -  N° 1.
--\---------------------------------------**********---------------------------------------/--

-----------------------------------------DEVOLUCIONES
	SELECT
		----------Tipo de Identificación----------
		CASE  PER.pertipoidentificacion 
			WHEN 'NIT' THEN '7' 
			WHEN 'CEDULA_CIUDADANIA' THEN '1' 
			WHEN 'CEDULA_EXTRANJERIA' THEN '4'
			WHEN 'TARJETA_IDENTIDAD' THEN '2' 
			WHEN 'REGISTRO_CIVIL' THEN '3'
			WHEN 'PASAPORTE' THEN '6'
			WHEN 'CARNE_DIPLOMATICO' THEN '8'
			WHEN 'PERM_ESP_PERMANENCIA' THEN '9' 	
			WHEN 'PERM_PROT_TEMPORAL' THEN '15'
			WHEN 'SALVOCONDUCTO' THEN 'Salvoconducto'
			ELSE PER.pertipoidentificacion

		END AS [TIP_IDENTIFICACION],--[TIPO DE IDENTIFICACION], 

		----------Número de identificación----------
		pernumeroidentificacion as [NUM_IDENTIFICACION],--[NUMERO IDENTIFICACION],

		----------Nombre----------
		SUBSTRING (perrazonsocial,1,200) AS [NOM_EMPRESA],--[NOMBRE], 

		----------Código Municipio----------
		RIGHT (munCodigo,5) AS [COD_MUNICIPIO_DANE],--[CÓDIGO MUNICIPIO], 

		----------Dirección----------
		LEFT (ub.ubidireccionfisica,200) AS [DIR_CORRESPONDECIA],--[DIRECCION],

		----------Estado de Vinculación----------
		CASE 
			WHEN 
				m.empEstadoEmpleador = 'ACTIVO' OR 
				MAX(MOVAPGA.apgFechaRecaudo) BETWEEN @FECHA_INICIAL AND @FECHA_FINAL 
			THEN '1'

			WHEN 
				m.empEstadoEmpleador = 'INACTIVO' AND 
				m.empMotivoDesafiliacion = 'OTRO'  
			THEN '4' 
		
			WHEN 
				m.empEstadoEmpleador = 'INACTIVO' AND 
				m.empMotivoDesafiliacion = 'CERO_TRABAJADORES_SOLICITUD_EMPLEADOR'  
			THEN '4'

			WHEN 
				m.empEstadoEmpleador = 'INACTIVO' AND 
				m.empMotivoDesafiliacion = 'CERO_TRABAJADORES_NOVEDAD_INTERNA'  
			THEN '4' 

			WHEN 
				m.empEstadoEmpleador = 'INACTIVO' AND 
				m.empMotivoDesafiliacion = 'ANULADO'  
			THEN '4' 

			WHEN 
				m.empEstadoEmpleador = 'INACTIVO' AND 
				m.empMotivoDesafiliacion = 'RETIRO_POR_TRASLADO_OTRA_CCF'  
			THEN '4' 

			WHEN 
				m.empEstadoEmpleador = 'INACTIVO' AND 
				m.empMotivoDesafiliacion = 'CESE_EN_PROCESO_LIQUIDACION_LIQUIDADO_FALLECIDO'  
			THEN '4'

			WHEN 
				m.empEstadoEmpleador = 'INACTIVO' AND 
				m.empMotivoDesafiliacion = 'FUSION_ADQUISICION'  
			THEN '4'

			WHEN 
				m.empEstadoEmpleador = 'INACTIVO' AND 
				m.empMotivoDesafiliacion = 'MULTIAFILIACION'  
			THEN '4'

			WHEN 
				m.empEstadoEmpleador = 'INACTIVO' AND 
				m.empMotivoDesafiliacion = 'EXPULSION_POR_USO_INDEBIDO_DE_SERVICIOS'  
			THEN '2' 

			WHEN 
				m.empEstadoEmpleador = 'INACTIVO' AND 
				m.empMotivoDesafiliacion = 'EXPULSION_POR_INFORMACION_INCORRECTA'  
			THEN '2'

			WHEN 
				m.empEstadoEmpleador = 'INACTIVO' AND 
				m.empMotivoDesafiliacion = 'EXPULSION_POR_MOROSIDAD'  
			THEN '2' 

			WHEN m.empEstadoEmpleador = 'INACTIVO' AND m.empMotivoDesafiliacion IS NULL THEN '4'
			--else PER.perId--'dev'

		END as [EST_VINCULACION],--[ESTADO DE VINCULACIÓN], 

		----------Tipo de Aportante----------
		MOVAPGA.tipoAportante AS [TIP_APORTANTE],--[TIPO APORTANTE], 

		----------Tipo de Sector----------
		CASE 
			WHEN e.empnaturalezajuridica = 'PRIVADA' THEN '2' 
			ELSE 
				CASE 
					WHEN E.empNaturalezaJuridica = 'PUBLICA' THEN '1' 
					ELSE 
						CASE 
							WHEN E.empNaturalezaJuridica = 'MIXTA' THEN '3' 
							ELSE '4'---E.empNaturalezaJuridica 
						END 
				END 
		END as [TIP_SECTOR],--[TIPO DE SECTOR],

		----------Actividad Economica Principal----------
		RIGHT('00000' + Ltrim(Rtrim(c.ciicodigo)),4)  as [ACT_ECONOMICA],--[ACTIVIDAD ECONOMICA PRINCIPAL],

		----------Situación de la Empresa frente a la Ley 1429 de 2010----------
	   CASE WHEN bemBeneficioActivo = 0 AND bembeneficio = '1' then '1'
	        WHEN bemBeneficioActivo = 0 AND bembeneficio = '2' then '2'
			WHEN bembeneficio IS NULL then '2' 
		    WHEN bemBeneficioActivo = 1 AND bemBeneficio = '1' THEN '1' 
			WHEN bemBeneficioActivo = 1 AND bembeneficio = '2' THEN '2'
			---PARA QUE EVALUE A TODOS LOS ACTIVO ACT Y LOS QUE NO Y LOS QUE ESTUVIERON 20230523
		END as [SIT_EMPRESA_LEY_1429],

		----------Progresividad en el pago de los parafiscales frente a la Ley 1429 de 2010----------
		CASE WHEN bemfechavinculacion IS NULL OR bembeneficio = '2' THEN '6' 
			 WHEN bemfechavinculacion <= '2014-12-31' AND bemBeneficio = 1 THEN '5' 
		     WHEN bemfechavinculacion > '2014-12-31'  AND bemBeneficio = 1  then '5' 
		ELSE '6'
		END AS [PRO_PAGO_LEY_1429],

		----------Situación de la Empresa frente a la Ley 590 de 2000----------
	---SELECT * FROM ben 
		   CASE WHEN bemBeneficioActivo = 0 AND bembeneficio = '2' then' 1'
		        WHEN bemBeneficioActivo = 0 AND bembeneficio = '1' then '2'
			WHEN bembeneficio IS NULL then '2' 
		    WHEN bemBeneficioActivo = 1 AND bemBeneficio = '1' THEN '2' 
			WHEN bemBeneficioActivo = 1 AND bembeneficio = '2' THEN '1'
	   
		end as [SIT_EMPRESA_LEY_590], 
	
		----------Progresividad en el pago de los parafiscales frente a la Ley 590 de 2000----------
		case 
			WHEN  bembeneficio = '2' 
			THEN   dbo.UFN_ObtenerProgreso590(bemfechavinculacion)  
			ELSE 
				'6'
			END  
		  as [PRO_PAGO_LEY_590],

		----------Aporte Total Mensual----------
		--REPLACE (SUM (ISNULL (MOVAPGA.dapMontoAportes,0)),'.00000','') 
		 CONVERT(NUMERIC(19,5),0) AS [APO_TOTAL_MENSUAL],--[APORTE TOTAL MENSUAL], 
		 
		----------Intereses Pagados por Mora----------
		REPLACE (0, '.00000', '') AS [INT_PAGADOS_MORA],--[INTERESES POR MORA], 
	
		----------Valor reintegros----------
		REPLACE ( SUM(ISNULL (MOVAPGA.valorAporte,0)), '.00000', '') AS [VAL_REINTEGROS]--[VALOR REINTEGROS] 



	  INTO #TEMP_EMPRESAS_APORTANTES --prueba 
	 --drop table #TEMP_EMPRESAS_APORTANTES
	 --select * from #TEMP_EMPRESAS_APORTANTES
	 FROM
 
		persona PER WITH (nolock)
		inner join empresa e WITH (nolock) on e.emppersona=perid
		left join empleador m WITH (nolock) on m.empempresa=e.empid
		inner join codigociiu c WITH (nolock) on c.ciiid = e.empcodigociiu
		inner join ubicacionempresa u WITH (nolock) on u.ubeempresa=e.empid
		inner join ubicacion ub WITH (nolock) on ub.ubiid=u.ubeubicacion
		LEFT join municipio WITH (nolock) on munid=ub.ubimunicipio
		LEFT join departamento WITH (nolock) on depid=mundepartamento 
		left JOIN beneficioempleador WITH (nolock) on bemempleador=m.empid AND  bemBeneficioActivo= 1 --debe descomentarearse debido a que esta trayendo ducplicados 20230523
		left join beneficio WITH (nolock) on befid=bembeneficio

		inner JOIN (
			
			-- DEVOLUCIONES - DEPENDIENTES
			------------------------------
			SELECT	
				aporteGeneral.apgEmpresa AS empresaId,

				CASE 
					WHEN aportante.perTipoIdentificacion IS NOT NULL
					THEN SUM(movAporte.moaValorAporte)
					ELSE SUM(devolucion.dapMontoAportes)
				END AS valorAporte,

				devolucion.dapMontoIntereses,
				sum(cast(devolucion.dapMontoAportes as numeric )) AS dapMontoAportes,
				devolucion.dapFechaRecepcion,			
				aporteGeneral.apgFechaRecaudo,
				'1' AS tipoAportante
			FROM
				MovimientoAporte movAporte 

				INNER JOIN aportegeneral AS aporteGeneral ON aporteGeneral.apgid = movAporte.moaaportegeneral 

				LEFT JOIN empresa AS empresa ON empresa.empid = aporteGeneral.apgempresa
				LEFT JOIN persona AS aportante ON aportante.perid = empresa.empPersona

				INNER JOIN devolucionaportedetalle AS devolucionDetalle ON devolucionDetalle.dadMovimientoAporte = movAporte.moaId
				INNER JOIN devolucionaporte AS devolucion ON devolucion.dapId = devolucionDetalle.dadDevolucionAporte
				
				INNER JOIN solicituddevolucionaporte AS solicitudDevolucion ON solicitudDevolucion.sdaDevolucionAporte = devolucion.dapId
				INNER JOIN solicitud AS solicitudGeneral ON solicitudGeneral.solId = solicitudDevolucion.sdaSolicitudGlobal
			
			WHERE 
				movAporte.moaTipoMovimiento = 'DEVOLUCION_APORTES' AND
				devolucion.dapFechaRecepcion >= @FECHA_INICIAL AND 
				devolucion.dapFechaRecepcion <= @FECHA_FINAL AND 
				solicitudDevolucion.sdaestadosolicItud IN ('CERRADA','GESTIONAR_PAGO') AND
				aporteGeneral.apgEstadoAportante NOT IN ('NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES')
			
			GROUP BY
				moaAporteGeneral,
				aporteGeneral.apgEmpresa,
				devolucion.dapMontoIntereses,
				devolucion.dapFechaRecepcion,			
				aporteGeneral.apgFechaRecaudo,
				aportante.perTipoIdentificacion

		
		
		-- DEVOLUCIONES - EMPRESAS TRAMITADORAS 
			UNION 
			---------------------------------------

			SELECT 
				aporteGeneral.apgEmpresa AS empresaId,
				SUM(movAporte.moaValorAporte) AS valorAporte,
				devolucion.dapMontoIntereses,
				sum(cast(devolucion.dapMontoAportes as bigint)) AS dapMontoAportes,
				devolucion.dapFechaRecepcion,			
				aporteGeneral.apgFechaRecaudo,
				CASE
					WHEN ROA.roaTipoAfiliado = 'PENSIONADO'
					THEN '2' 
					ELSE '3'
				END AS tipoAportante
			FROM
				MovimientoAporte movAporte 

				INNER JOIN aportegeneral AS aporteGeneral ON aporteGeneral.apgid = movAporte.moaaportegeneral 

				INNER JOIN empresa AS empresa ON empresa.empid = aporteGeneral.apgempresa
				INNER JOIN persona AS aportante ON aportante.perid = empresa.empPersona

				INNER JOIN rolAfiliado AS roa on roa.roaEmpleador = aportante.perId 
					AND roa.roaTipoAfiliado <> 'TRABAJADOR_DEPENDIENTE'
				INNER JOIN afiliado AS afiliado ON afiliado.afiId = roa.roaAfiliado
				
				INNER JOIN devolucionaportedetalle AS devolucionDetalle ON devolucionDetalle.dadMovimientoAporte = movAporte.moaId
				INNER JOIN devolucionaporte AS devolucion ON devolucion.dapId = devolucionDetalle.dadDevolucionAporte
				
				INNER JOIN solicituddevolucionaporte AS solicitudDevolucion ON solicitudDevolucion.sdaDevolucionAporte = devolucion.dapId
				INNER JOIN solicitud AS solicitudGeneral ON solicitudGeneral.solId = solicitudDevolucion.sdaSolicitudGlobal
			
			WHERE 
				movAporte.moaTipoMovimiento = 'DEVOLUCION_APORTES' AND
				devolucion.dapFechaRecepcion >= @FECHA_INICIAL AND 
				devolucion.dapFechaRecepcion <= @FECHA_FINAL AND 
				solicitudDevolucion.sdaestadosolicItud IN ('CERRADA','GESTIONAR_PAGO') AND
				aporteGeneral.apgEstadoAportante NOT IN ('NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES')
			GROUP BY
				moaAporteGeneral,
				aporteGeneral.apgEmpresa,
				devolucion.dapMontoIntereses,
				devolucion.dapFechaRecepcion,			
				aporteGeneral.apgFechaRecaudo,
				aportante.perTipoIdentificacion,
				ROA.roaTipoAfiliado
		

		) MOVAPGA ON MOVAPGA.empresaId = e.empid 
		
		
	WHERE  

		--m.empestadoEmpleador = 'ACTIVO' AND 
		u.ubeTipoUbicacion='UBICACION_PRINCIPAL' --AND 
		--M.empFechaCambioEstadoAfiliacion <= @FECHA_FINAL
		--and per.pernumeroidentificacion ='900810402'
	
	GROUP BY 
		PER.pertipoidentificacion, 
		PER.pernumeroidentificacion, 
		PER.perrazonsocial,munCodigo, 
		UB.ubidireccionfisica, 
		M.empestadoempleador,
		e.empnaturalezajuridica, 
		c.ciicodigo,
		bemBeneficio, 
		bemfechavinculacion,
		m.empMotivoDesafiliacion,
		MOVAPGA.tipoAportante, bemBeneficioActivo
		--, PER.perId
	
	 
	   UNION   -- prueba
	------------------------------------------------
	-- DEVOLUCIONES - PENSIONADOS E INDEPENDIENTES
	------------------------------------------------

		SELECT
		----------Tipo de Identificación----------
		CASE  PER.pertipoidentificacion 
			WHEN 'NIT' THEN '7' 
			WHEN 'CEDULA_CIUDADANIA' THEN '1' 
			WHEN 'CEDULA_EXTRANJERIA' THEN '4'
			WHEN 'TARJETA_IDENTIDAD' THEN '2' 
			WHEN 'REGISTRO_CIVIL' THEN '3'
			WHEN 'PASAPORTE' THEN '6'
			WHEN 'CARNE_DIPLOMATICO' THEN '8'
			WHEN 'PERM_ESP_PERMANENCIA' THEN '9'
			WHEN 'PERM_PROT_TEMPORAL' THEN '15'
			WHEN 'SALVOCONDUCTO' THEN 'Salvoconducto'
			ELSE PER.pertipoidentificacion

		END AS [TIP_IDENTIFICACION],--[TIPO DE IDENTIFICACION], 

		----------Número de identificación----------
		pernumeroidentificacion as [NUM_IDENTIFICACION],--[NUMERO IDENTIFICACION],

		----------Nombre----------
		SUBSTRING (perrazonsocial,1,200) AS [NOM_EMPRESA],--[NOMBRE], 

		----------Código Municipio----------
		RIGHT (munCodigo,5) AS [COD_MUNICIPIO_DANE],--[CÓDIGO MUNICIPIO], 

		----------Dirección----------
		LEFT (ubicacion.ubidireccionfisica,200) AS [DIR_CORRESPONDECIA],--[DIRECCION],

		----------Estado de Vinculación----------
		CASE 
			WHEN roa.roaEstadoAfiliado = 'ACTIVO' THEN '1'
			WHEN roa.roaEstadoAfiliado = 'INACTIVO' AND roa.roaMotivoDesafiliacion = 'SUSTITUCION_PATRONAL'  THEN '4' 
			WHEN roa.roaEstadoAfiliado = 'INACTIVO' AND roa.roaMotivoDesafiliacion = 'AFILIACION_ANULADA'  THEN '4' 
			WHEN roa.roaEstadoAfiliado = 'INACTIVO' AND roa.roaMotivoDesafiliacion = 'DESAFILIACION_EMPLEADOR'  THEN '4' 
			WHEN roa.roaEstadoAfiliado = 'INACTIVO' AND roa.roaMotivoDesafiliacion = 'OTROS'  THEN '4' 
			WHEN roa.roaEstadoAfiliado = 'INACTIVO' AND roa.roaMotivoDesafiliacion = 'RETIRO_POR_MORA_APORTES'  THEN '2' 
			WHEN roa.roaEstadoAfiliado = 'INACTIVO' AND roa.roaMotivoDesafiliacion = 'RETIRO_POR_CAMBIO_DE_CAJA_COMPENSACION'  THEN '4' 
			WHEN roa.roaEstadoAfiliado = 'INACTIVO' AND roa.roaMotivoDesafiliacion = 'ENTREGA_DE_INFORMACION_FRAUDULENTA_CCF'  THEN '2' 
			WHEN roa.roaEstadoAfiliado = 'INACTIVO' AND roa.roaMotivoDesafiliacion = 'MAL_USO_DE_SERVICIOS_CCF'  THEN '2' 
			WHEN roa.roaEstadoAfiliado = 'INACTIVO' AND roa.roaMotivoDesafiliacion = 'FALLECIMIENTO'  THEN '4' 
			WHEN roa.roaEstadoAfiliado = 'INACTIVO' AND roa.roaMotivoDesafiliacion = 'RETIRO_VOLUNTARIO'  THEN '4' 
			WHEN roa.roaEstadoAfiliado = 'INACTIVO' AND roa.roaMotivoDesafiliacion = 'MULTIAFILIACION'  THEN '4' 
			WHEN roa.roaEstadoAfiliado = 'INACTIVO' AND roa.roaMotivoDesafiliacion = 'PENSIONADO_SIN_CAPACIDAD' THEN '4'
			WHEN roa.roaEstadoAfiliado = 'INACTIVO' AND roa.roaMotivoDesafiliacion IS NULL THEN '4'
			--else 'dev pen'
		END as [EST_VINCULACION],--[ESTADO DE VINCULACIÓN], 

		----------Tipo de Aportante----------
		--MOVAPGA.tipoAportante 
		CASE
			WHEN ROA.roaTipoAfiliado = 'PENSIONADO'
			THEN '2' 
			ELSE '3'
		END AS [TIP_APORTANTE],--[TIPO APORTANTE], 

		----------Tipo de Sector----------
		'4' as [TIP_SECTOR],--[TIPO DE SECTOR],

		----------Actividad Economica Principal----------
		--RIGHT('00000' + Ltrim(Rtrim(c.ciicodigo)),4) 
		'0000' as [ACT_ECONOMICA],--[ACTIVIDAD ECONOMICA PRINCIPAL],

		--		----------Situación de la Empresa frente a la Ley 1429 de 2010----------

		--	---PARA QUE EVALUE A TODOS LOS ACTIVO ACT Y LOS QUE NO Y LOS QUE ESTUVIERON 20230523
		--END as [SIT_EMPRESA_LEY_1429],
		----------Situación de la Empresa frente a la Ley 1429 de 2010----------
		CASE  roa.roaTipoAfiliado
			WHEN 'TRABAJADOR_INDEPENDIENTE' THEN '2'
			WHEN 'PENSIONADO' THEN '2' 
		END AS [Situación de la Empresa frente a la Ley 1429 de 2010],

		----------Progresividad en el pago de los parafiscales frente a la Ley 1429 de 2010----------
		CASE  roa.roaTipoAfiliado
			WHEN 'TRABAJADOR_INDEPENDIENTE' THEN '6'
			WHEN 'PENSIONADO' THEN '6' 
		END AS [Progresividad en el pago de los parafiscales frente a la Ley 1429 de 2010],

		----------Situación de la Empresa frente a la Ley 590 de 2000----------
		CASE  roa.roaTipoAfiliado
			WHEN 'TRABAJADOR_INDEPENDIENTE' THEN '2'
			WHEN 'PENSIONADO' THEN '2' 
		END AS [Situación de la Empresa frente a la Ley 590 de 2000],

		----------Progresividad en el pago de los parafiscales frente a la Ley 590 de 2000----------
		CASE  roa.roaTipoAfiliado
			WHEN 'TRABAJADOR_INDEPENDIENTE' THEN '6'
			WHEN 'PENSIONADO' THEN '6' 
		END AS [Progresividad en el pago de los parafiscales frente a la Ley 590 de 2000],

		----------Aporte Total Mensual----------
		--REPLACE (SUM (ISNULL (MOVAPGA.dapMontoAportes,0)),'.00000','') 
		0 AS [APO_TOTAL_MENSUAL],--[APORTE TOTAL MENSUAL], 

		----------Intereses Pagados por Mora----------
		--REPLACE (SUM (ISNULL (MOVAPGA.dapMontoIntereses,0)), '.00000', '') 
		REPLACE (0, '.00000', '') AS [INT_PAGADOS_MORA],--[INTERESES POR MORA], 
	
		----------Valor reintegros----------
		REPLACE ( SUM(ISNULL (moa.moaValorAporte,0)), '.00000', '')  AS [VAL_REINTEGROS]--[VALOR REINTEGROS]  
	----------------------------------------------
FROM
 
		--persona PER
		afiliado a --on perid=afipersona
		inner join rolafiliado roa on roa.roaafiliado=afiid AND roaTipoAfiliado in ('PENSIONADO', 'TRABAJADOR_INDEPENDIENTE')
		-----DATOS DE LA SOLICITUD PARA LA CLASIFICACION cambio 20230314 olga vega para eximir a los pensionados de 25 años
		INNER JOIN SolicitudAfiliacionPersona ON sapRolAfiliado = roa.roaId 
		INNER JOIN Solicitud s ON solid = sapSolicitudGlobal

		inner join (
		select distinct 
			
			case 
				when count(*) over (partition by t.id) > 1 
				then 'ACTIVO' 
	            when t.est = 'ACTIVO' and t.fechaRetiro is null 
				then 'ACTIVO' 
	            else 'INACTIVO' 
			end as estadoAfiliado,

			case 
				when count(*) over (partition by t.id) > 1 
				then t.id 
	            when t.est = 'ACTIVO' and t.fechaRetiro is null 
				then t.id 
	            else t.id  end as idAfiliado,
				
			case 
				when count(*) over (partition by t.id) > 1 
				then null 
	            when t.est = 'ACTIVO' and t.fechaRetiro is null 
				then  null 
	            else t.fechaRetiro  
			end as fechaRetiro,
				
			case 
				when count(*) over (partition by t.id) > 1 
				then t.tipoAfi 
	            when t.est = 'ACTIVO' and t.fechaRetiro is null 
				then  t.tipoAfi 
	            else t.tipoAfi  
			end as tipoAfi,
			count(*) over (partition by t.id) as CantRegistros
		from ( 
			select 
				roaAfiliado as id, 
				roaEstadoAfiliado as est, 
				max(roaFechaRetiro) as fechaRetiro, 
				roaTipoAfiliado as tipoAfi
            from 
				rolAfiliado with (nolock)
			where 
				roaTipoAfiliado in ('TRABAJADOR_INDEPENDIENTE','PENSIONADO')
			group by 
				roaAfiliado, 
				roaEstadoAfiliado, 
				roaTipoAfiliado
            ) as t 
		) as ra on a.afiId = ra.idAfiliado 
		
		inner join Persona as per on per.perId = a.afiPersona

		inner join ubicacion on per.perubicacionPRINCIPAL=ubiid
		left join municipio on munid=ubimunicipio
		left join departamento on depid=mundepartamento

		inner join AporteGeneral as apg on per.perId = apg.apgPersona 
		inner join MovimientoAporte as moa on moa.moaAporteGeneral = apg.apgId
		
		INNER JOIN devolucionaportedetalle AS devolucionDetalle ON devolucionDetalle.dadMovimientoAporte = moa.moaId
		INNER JOIN devolucionaporte AS devolucion ON devolucion.dapId = devolucionDetalle.dadDevolucionAporte
				
		INNER JOIN solicituddevolucionaporte AS solicitudDevolucion ON solicitudDevolucion.sdaDevolucionAporte = devolucion.dapId
		INNER JOIN solicitud AS solicitudGeneral ON solicitudGeneral.solId = solicitudDevolucion.sdaSolicitudGlobal

		WHERE 
			moa.moaTipoMovimiento = 'DEVOLUCION_APORTES' 
			
			AND convert(date,devolucion.dapFechaRecepcion) >= @FECHA_INICIAL  
			AND convert(date,devolucion.dapFechaRecepcion) <= @FECHA_FINAL 
			AND solicitudDevolucion.sdaestadosolicItud IN ('CERRADA','GESTIONAR_PAGO')
			-- AND s.solClasificacion NOT IN ('FIDELIDAD_25_ANIOS')
			--	and per.pernumeroidentificacion ='900810402'
	GROUP BY 
			PER.pertipoidentificacion,
			PER.pernumeroidentificacion,
			PER.perrazonsocial,
			munCodigo,
			ubicacion.ubidireccionfisica,
			roa.roaEstadoAfiliado,
			roa.roaMotivoDesafiliacion,
			roa.roaTipoAfiliado
--------------------------------------APORTES APORTANTE
  UNION ALL --  prueba
--------------------------------------
SELECT
	----------Tipo de Identificación----------
	CASE
		pertipoidentificacion
		WHEN 'NIT' THEN '7'
		WHEN 'CEDULA_CIUDADANIA' THEN '1'
		WHEN 'CEDULA_EXTRANJERIA' THEN '4'
		WHEN 'TARJETA_IDENTIDAD' THEN '2'
		WHEN 'REGISTRO_CIVIL' THEN '3'
		WHEN 'PASAPORTE' THEN '6'
		WHEN 'CARNE_DIPLOMATICO' THEN '8'
		WHEN 'PERM_ESP_PERMANENCIA' THEN '9'
		WHEN 'PERM_PROT_TEMPORAL' THEN '15'
		WHEN 'SALVOCONDUCTO' THEN 'Salvoconducto'
		ELSE pertipoidentificacion
	END AS [Tipo de Identificación],
	----------Número de Identificación----------
	pernumeroidentificacion AS [Número de Identificación],
	----------Nombre----------
	SUBSTRING (perrazonsocial, 1, 200) AS [Nombre],
	----------Código municipio----------
	RIGHT (munCodigo, 5) AS [Código municipio],
	----------Dirección----------
	LEFT (ub.ubidireccionfisica, 200) AS [Dirección],
	----------Estado de vinculación----------
	CASE
		WHEN m.empEstadoEmpleador = 'ACTIVO' THEN '1'
		WHEN m.empEstadoEmpleador = 'INACTIVO'
		AND m.empMotivoDesafiliacion = 'OTRO' THEN '4'
		WHEN m.empEstadoEmpleador = 'INACTIVO'
		AND m.empMotivoDesafiliacion = 'CERO_TRABAJADORES_SOLICITUD_EMPLEADOR' THEN '4'
		WHEN m.empEstadoEmpleador = 'INACTIVO'
		AND m.empMotivoDesafiliacion = 'CERO_TRABAJADORES_NOVEDAD_INTERNA' THEN '4'
		WHEN m.empEstadoEmpleador = 'INACTIVO'
		AND m.empMotivoDesafiliacion = 'ANULADO' THEN '4'
		WHEN m.empEstadoEmpleador = 'INACTIVO'
		AND m.empMotivoDesafiliacion = 'RETIRO_POR_TRASLADO_OTRA_CCF' THEN '4'
		WHEN m.empEstadoEmpleador = 'INACTIVO'
		AND m.empMotivoDesafiliacion = 'CESE_EN_PROCESO_LIQUIDACION_LIQUIDADO_FALLECIDO' THEN '4'
		WHEN m.empEstadoEmpleador = 'INACTIVO'
		AND m.empMotivoDesafiliacion = 'FUSION_ADQUISICION' THEN '4'
		WHEN m.empEstadoEmpleador = 'INACTIVO'
		AND m.empMotivoDesafiliacion = 'MULTIAFILIACION' THEN '4'
		WHEN m.empEstadoEmpleador = 'INACTIVO'
		AND m.empMotivoDesafiliacion = 'EXPULSION_POR_USO_INDEBIDO_DE_SERVICIOS' THEN '2'
		WHEN m.empEstadoEmpleador = 'INACTIVO'
		AND m.empMotivoDesafiliacion = 'EXPULSION_POR_INFORMACION_INCORRECTA' THEN '2'
		WHEN m.empEstadoEmpleador = 'INACTIVO'
		AND m.empMotivoDesafiliacion = 'EXPULSION_POR_MOROSIDAD' THEN '2' --else 'vinc'
	END [Estado de vinculación],
	----------Tipo de Aportante----------
	'1' AS [Tipo de Aportante],
	----------Tipo de sector----------
	CASE
		WHEN e.empnaturalezajuridica = 'PRIVADA' THEN '2'
		ELSE CASE
			WHEN E.empNaturalezaJuridica = 'PUBLICA' THEN '1'
			ELSE CASE
				WHEN E.empNaturalezaJuridica = 'MIXTA' THEN '3'
				ELSE '4' --E.empNaturalezaJuridica 
			END
		END
	END AS [Tipo de sector],
	----------Actividad económica principal----------
	RIGHT('00000' + Ltrim(Rtrim(c.ciicodigo)), 4) AS [Actividad económica principal],
	----------Situación de la Empresa frente a la Ley 1429 de 2010----------
	CASE
		WHEN bemBeneficioActivo = 0
		AND bembeneficio = '1' then ' 1'
		WHEN bemBeneficioActivo = 0
		AND bembeneficio = '2' then ' 2'
		WHEN bembeneficio IS NULL then '2'
		WHEN bemBeneficioActivo = 1
		AND bemBeneficio = '1' THEN '1'
		WHEN bemBeneficioActivo = 1
		AND bembeneficio = '2' THEN '2'
	end as [Situación de la Empresa frente a la Ley 1429 de 2010],
	----------Progresividad en el pago de los parafiscales frente a la Ley 1429 de 2010----------
	case
		when bemfechavinculacion is null
		or bembeneficio = '2' then '6'
		else case
			when bemfechavinculacion <= '2014-12-31' then '5'
			else case
				when bemfechavinculacion > '2014-12-31' then '5'
			end
		end
	end as [Progresividad en el pago de los parafiscales frente a la Ley 1429 de 2010],
	----------Situación de la Empresa frente a la Ley 590 de 2000----------
	CASE
		WHEN bemBeneficioActivo = 0
		AND bembeneficio = '2' then '1'
		WHEN bemBeneficioActivo = 0
		AND bembeneficio = '1' then '2'
		WHEN bembeneficio IS NULL then '2'
		WHEN bemBeneficioActivo = 1
		AND bemBeneficio = '1' THEN '2'
		WHEN bemBeneficioActivo = 1
		AND bembeneficio = '2' THEN '1'
	end as [Situación de la Empresa frente a la Ley 590 de 2000],
	----------Progresividad en el pago de los parafiscales frente a la Ley 590 de 2000----------
	case
		WHEN bembeneficio = '2' THEN dbo.UFN_ObtenerProgreso590(bemfechavinculacion)
		ELSE '6'
	end as [Progresividad en el pago de los parafiscales frente a la Ley 590 de 2000],
	----------Aporte total mensual----------
	REPLACE (sum (isnull (APGA.aporteoriginal, 0))+(select isnull(sum(totalaporteobligatorio),0) from #PlanillasN pn where pn.regnumeroidentificacionaportante=pernumeroidentificacion  ), '.00000', '') AS [Aporte total mensual],
	----------Intereses pagados por mora----------
	REPLACE (
		sum (isnull (ROUND (APGA.interesOriginal, 0), 0))+(select isnull(sum(totalinteresesMora),0) from #PlanillasN pn where pn.regnumeroidentificacionaportante=pernumeroidentificacion  ),
		'.00000',
		''
	) AS [Intereses Pagados por Mora],
	--ROUND (sum (isnull (APGA.apgValorIntMora,0)),0) AS [Intereses pagados por mora], 
	----------Valor reintegros----------
	'0' AS [Valor reintegros]
	
FROM
	persona
	inner join empresa e WITH (nolock) on e.emppersona = perid
	INNER join empleador m WITH (nolock) on m.empempresa = e.empid
	inner join codigociiu c WITH (nolock) on c.ciiid = e.empcodigociiu
	inner join ubicacionempresa u WITH (nolock) on u.ubeempresa = e.empid
	inner join ubicacion ub WITH (nolock) on ub.ubiid = u.ubeubicacion
	LEFT join municipio WITH (nolock) on munid = ub.ubimunicipio
	LEFT join departamento WITH (nolock) on depid = mundepartamento
	left JOIN beneficioempleador WITH (nolock) on bemempleador = m.empid AND  bemBeneficioActivo= 1 -- debe descomentarearse para evitar suplicados
	left join beneficio WITH (nolock) on befid = bembeneficio --LEFT join AporteGeneral APGA WITH (nolock) on APGA.apgEmpresa=e.empid 
	--	AND APGA.apgFechaRecaudo BETWEEN @FECHA_INICIAL AND @FECHA_FINAL
	LEFT join #tempAportes APGA WITH (nolock) on APGA.apgEmpresa=e.empid 
	
	--AND APGA.apgValTotalApoObligatorio > 0
	--SE COMENTA LOGICA PUES DEBE TRAER TODOS LOS APORTANTES SIN IMPORTAR SI ESTUVIERON ACTIVOS O INACTIVOS
--	INNER JOIN (
--		SELECT
--			MAX(consolidado.CONTEO) CONTEO,
--			consolidado.roaEmpleador
--		FROM
--(
--				SELECT
--					COUNT(AFI.afiId) CONTEO,
--					ROA.roaEmpleador
--				FROM
--					Afiliado AFI
--					INNER JOIN RolAfiliado ROA ON ROA.roaAfiliado = AFI.afiId
--					AND (
--						ROA.roaEstadoAfiliado = 'ACTIVO'
--						OR ROA.roaFechaRetiro BETWEEN @FECHA_INICIAL
--						AND FORMAT(GETDATE(), 'MM-dd-yyyy')
--					)
--					INNER JOIN Empleador EMPLEN ON EMPLEN.empId = ROA.roaEmpleador
--					INNER JOIN Empresa EMPRES ON EMPRES.empId = EMPLEN.empEmpresa
--					INNER JOIN Persona PER ON PER.perId = EMPRES.empPersona --WHERE PER.perNumeroIdentificacion = '417729'
--				GROUP BY
--					ROA.roaEmpleador
--				UNION
--				SELECT
--					COUNT(AFI.afiId) CONTEO,
--					ROA.roaEmpleador
--				FROM
--					Afiliado AFI
--					INNER JOIN RolAfiliado ROA ON ROA.roaAfiliado = AFI.afiId
--					AND ROA.roaFechaRetiro BETWEEN @FECHA_INICIAL
--					AND FORMAT(GETDATE(), 'MM-dd-yyyy')
--					INNER JOIN Empleador EMPLEN ON EMPLEN.empId = ROA.roaEmpleador
--					INNER JOIN Empresa EMPRES ON EMPRES.empId = EMPLEN.empEmpresa
--					INNER JOIN Persona PER ON PER.perId = EMPRES.empPersona --WHERE PER.perNumeroIdentificacion = '417729'
--				GROUP BY
--					ROA.roaEmpleador
--			) consolidado
--		group by
--			consolidado.roaEmpleador
--	) AS AFIACTI ON AFIACTI.roaEmpleador = M.empId
where
		-- pernumeroidentificacion ='900810402' and
	u.ubeTipoUbicacion = 'UBICACION_PRINCIPAL'
--	AND convert(date, M.empFechaCambioEstadoAfiliacion) <= @FECHA_FINAL
group by
	pertipoidentificacion,
	pernumeroidentificacion,
	perrazonsocial,
	munCodigo,
	UB.ubidireccionfisica,
	M.empestadoempleador,
	e.empnaturalezajuridica,
	c.ciicodigo,
	bemBeneficio,
	bemfechavinculacion,
	m.empMotivoDesafiliacion,
	bemBeneficioActivo

		---***---
	----EMPRESAS INACTIVAS EMPLEADOS INACTIVOS PERO SUS AFILIADO CON MOV DE SUBSIDIO EN LAS FECHAS NO NECESARIAMENTE DE ESE PERIODO
	----***---***
	--UNION
	-----***---
	--	SELECT 
	--	----------Tipo de Identificación----------
	--	CASE perEmpresa.pertipoidentificacion 
	--		WHEN 'NIT' THEN '7' 
	--		WHEN 'CEDULA_CIUDADANIA' THEN '1' 
	--		WHEN 'CEDULA_EXTRANJERIA' THEN '4'
	--		WHEN 'TARJETA_IDENTIDAD' THEN '2' 
	--		WHEN 'REGISTRO_CIVIL' THEN '3'
	--		WHEN 'PASAPORTE' THEN '6'
	--		WHEN 'CARNE_DIPLOMATICO' THEN '8'
	--		WHEN 'PERM_ESP_PERMANENCIA' THEN '9' 
	--		WHEN 'PERM_PROT_TEMPORAL' THEN '9'
	--		ELSE pertipoidentificacion
	--	END AS [Tipo de Identificación], 

	--	----------Número de Identificación----------
	--	perEmpresa.perNumeroIdentificacion AS [Número de Identificación], 
	
	--	----------Nombre----------
	--	SUBSTRING (perEmpresa.perRazonSocial,1,200) AS [Nombre], 
	
	--	----------Código municipio----------
	--	RIGHT (munCodigo,5) AS [Código municipio] , 
	
	--	----------Dirección----------
	--	LEFT (ubiPrincipal.ubidireccionfisica,200) AS [Dirección], 

	--	----------Estado de vinculación----------
	--		CASE  
	--		WHEN empleador.empEstadoEmpleador = 'ACTIVO' THEN '1'

	--		WHEN 
	--			empleador.empEstadoEmpleador = 'INACTIVO' AND 
	--			empleador.empMotivoDesafiliacion = 'OTRO'  
	--		THEN '4' 

	--		WHEN 
	--			empleador.empEstadoEmpleador = 'INACTIVO' AND 
	--			empleador.empMotivoDesafiliacion = 'CERO_TRABAJADORES_SOLICITUD_EMPLEADOR'  
	--		THEN '4'

	--		WHEN 
	--			empleador.empEstadoEmpleador = 'INACTIVO' AND 
	--			empleador.empMotivoDesafiliacion = 'CERO_TRABAJADORES_NOVEDAD_INTERNA'  
	--		THEN '4' 

	--		WHEN 
	--			empleador.empEstadoEmpleador = 'INACTIVO' AND 
	--			empleador.empMotivoDesafiliacion = 'ANULADO'  
	--		THEN '4' 

	--		WHEN 
	--			empleador.empEstadoEmpleador = 'INACTIVO' AND 
	--			empleador.empMotivoDesafiliacion = 'RETIRO_POR_TRASLADO_OTRA_CCF'  
	--		THEN '4' 

	--		WHEN 
	--			empleador.empEstadoEmpleador = 'INACTIVO' AND 
	--			empleador.empMotivoDesafiliacion = 'CESE_EN_PROCESO_LIQUIDACION_LIQUIDADO_FALLECIDO'  
	--		THEN '4'

	--		WHEN 
	--			empleador.empEstadoEmpleador = 'INACTIVO' AND 
	--			empleador.empMotivoDesafiliacion = 'FUSION_ADQUISICION'  
	--		THEN '4'
			
	--		WHEN 
	--			empleador.empEstadoEmpleador = 'INACTIVO' AND 
	--			empleador.empMotivoDesafiliacion = 'MULTIAFILIACION'  
	--		THEN '4'

	--		WHEN 
	--			empleador.empEstadoEmpleador = 'INACTIVO' AND 
	--			empleador.empMotivoDesafiliacion = 'EXPULSION_POR_USO_INDEBIDO_DE_SERVICIOS'  
	--		THEN '2' 

	--		WHEN 
	--			empleador.empEstadoEmpleador = 'INACTIVO' AND 
	--			empleador.empMotivoDesafiliacion = 'EXPULSION_POR_INFORMACION_INCORRECTA'  
	--		THEN '2'

	--		WHEN 
	--			empleador.empEstadoEmpleador = 'INACTIVO' AND 
	--			empleador.empMotivoDesafiliacion = 'EXPULSION_POR_MOROSIDAD'  
	--		THEN '2' 
	--		--else 'vinc'
	--	END [Estado de vinculación],

	--	----------Tipo de Aportante----------
	--	'1' AS [Tipo de Aportante], 

	--	----------Tipo de sector----------
	--	CASE 
	--		WHEN empresa.empnaturalezajuridica = 'PRIVADA' THEN '2' 
	--		ELSE 
	--			CASE 
	--				WHEN empresa.empNaturalezaJuridica = 'PUBLICA' THEN '1' 
	--				ELSE
	--					CASE 
	--						WHEN empresa.empNaturalezaJuridica = 'MIXTA' THEN '3' 
	--						ELSE '4' --E.empNaturalezaJuridica 
	--					END 
	--			END 
	--	END AS [Tipo de sector],

	--	----------Actividad económica principal----------
	--	RIGHT('00000' + Ltrim(Rtrim(ciiu.ciicodigo)),4) AS [Actividad económica principal],

	--	----------Situación de la Empresa frente a la Ley 1429 de 2010----------
	--   CASE WHEN bemBeneficioActivo = 0 AND bembeneficio = '1' then' 1'
	--        WHEN bemBeneficioActivo = 0 AND bembeneficio = '2' then' 2'
	--		WHEN bembeneficio IS NULL then '2' 
	--	    WHEN bemBeneficioActivo = 1 AND bemBeneficio = '1' THEN '1' 
	--		WHEN bemBeneficioActivo = 1 AND bembeneficio = '2' THEN '2'
	--	end as [Situación de la Empresa frente a la Ley 1429 de 2010], 

	--	----------Progresividad en el pago de los parafiscales frente a la Ley 1429 de 2010----------
	--	case 
	--		when beneficioEmpl.bemfechavinculacion is null or bembeneficio = '2' then '6' 
	--		else 
	--			case 
	--				when beneficioEmpl.bemfechavinculacion <= '2014-12-31' then '5' 
	--				else 
	--					case 
	--						when beneficioEmpl.bemfechavinculacion > '2014-12-31' then '5' 
	--					end 
	--			end 
	--	end as [Progresividad en el pago de los parafiscales frente a la Ley 1429 de 2010],

	--	----------Situación de la Empresa frente a la Ley 590 de 2000----------
	--   CASE WHEN bemBeneficioActivo = 0 AND bemBeneficio = '2' then '1'
	--        when bemBeneficioActivo = 0 AND bemBeneficio = '1' THEN '2'
	--		WHEN bembeneficio IS NULL then '2' 
	--	    WHEN bemBeneficioActivo = 1 AND bemBeneficio = '1' THEN '2' 
	--		WHEN bemBeneficioActivo = 1 AND bembeneficio = '2' THEN '1'
		 
	--	end as [Situación de la Empresa frente a la Ley 590 de 2000],
	
	--	----------Progresividad en el pago de los parafiscales frente a la Ley 590 de 2000----------
	--	case 
	--		when bembeneficio = '2' 
	--		THEN dbo.UFN_ObtenerProgreso590(bemfechavinculacion) 
	--		else 
	--			'6'
	--		end 
	--	  as [Progresividad en el pago de los parafiscales frente a la Ley 590 de 2000],

	--	----------Aporte total mensual----------
	--	REPLACE (SUM (ISNULL (apoGeneral.aporteOriginal,0))+(select isnull(sum(totalaporteobligatorio),0) from #PlanillasN pn where pn.regnumeroidentificacionaportante=pernumeroidentificacion ),'.00000','') AS [Aporte total mensual], 

	--	----------Intereses pagados por mora----------
	--	REPLACE (SUM (ISNULL (ROUND (apoGeneral.interesOriginal, 0),0))+(select isnull(sum(totalInteresesMora),0) from #PlanillasN pn where pn.regnumeroidentificacionaportante=pernumeroidentificacion ), '.00000', '') AS [Intereses Pagados por Mora], 
	--	--ROUND (sum (isnull (APGA.apgValorIntMora,0)),0) AS [Intereses pagados por mora], 

	--	----------Valor reintegros----------
	--	'0' AS [Valor reintegros] 

	--FROM
	--	persona perEmpresa
	--	INNER JOIN empresa empresa ON empresa.emppersona=perEmpresa.perid
	--	INNER JOIN empleador empleador 
	--		ON empleador.empempresa=empresa.empid
	--	   AND empleador.empEstadoEmpleador = 'INACTIVO'
	
	--	INNER JOIN codigociiu ciiu WITH (NOLOCK) ON ciiu.ciiid = empresa.empcodigociiu
	--	INNER JOIN ubicacionempresa ubiEmpresa WITH (NOLOCK) ON ubiEmpresa.ubeempresa = empresa.empid
	--	INNER JOIN ubicacion ubiPrincipal WITH (NOLOCK) 
	--		ON ubiPrincipal.ubiid = ubiEmpresa.ubeubicacion
	--		AND ubiEmpresa.ubeTipoUbicacion = 'UBICACION_PRINCIPAL'
	--	LEFT JOIN municipio munEmpresa WITH (NOLOCK) ON munEmpresa.munid = ubiPrincipal.ubimunicipio
	--	LEFT JOIN departamento depEmpresa WITH (NOLOCK) ON depEmpresa.depid = munEmpresa.mundepartamento 
		
	--	INNER JOIN RolAfiliado roa 
	--		    ON roa.roaEmpleador = empleador.empId 
	--		   AND roa.roaEstadoAfiliado='INACTIVO'
	--	INNER JOIN DetalleSubsidioAsignado detsub WITH (NOLOCK) 
	--	        ON detsub.dsaAfiliadoPrincipal= roaAfiliado 
	--		   AND detsub.dsaEmpleador= empleador.empid 
	--	INNER JOIN SolicitudLiquidacionSubsidio sls
	--		   	ON sls.slsId= dsaSolicitudLiquidacionSubsidio
	--		   AND convert(date,sls.slsFechaDispersion) BETWEEN @FECHA_INICIAL AND @FECHA_FINAL
			
	--	LEFT JOIN #tempAportes apoGeneral WITH (NOLOCK) 
	--	       ON apoGeneral.apgEmpresa = empresa.empid 
			  
	--		  AND apoGeneral.aporteOriginal > 0
		
		 
	--	LEFT JOIN beneficioempleador beneficioEmpl WITH (NOLOCK) 
	--	       ON beneficioEmpl.bemempleador = empleador.empid  
	--	LEFT JOIN beneficio beneficio WITH (NOLOCK) ON beneficio.befid=bembeneficio

	----WHERE perEmpresa.perNumeroIdentificacion IN ( '900111343') --AND
 
	--	GROUP BY 
	--	empleador.empId,
	--	perEmpresa.pertipoidentificacion, 
	--	perEmpresa.pernumeroidentificacion, 
	--	perEmpresa.perrazonsocial,
	--	munCodigo, 
	--	ubiPrincipal.ubidireccionfisica, 
	--	empleador.empestadoempleador,
	--	empresa.empnaturalezajuridica, 
	--	ciiu.ciicodigo,
	--	bemBeneficio, 
	--	bemfechavinculacion,
	--	empleador.empMotivoDesafiliacion,bemBeneficioActivo


	------------------------------------------APORTANTES SIN AFILIADOS ACTIVOS INACTIVOS CON APORTES
-- DEBIDO A REDUNDANCIA EN EMPLEADORES, Y QUE LA LOGICA COMPLEJA NO ES NECESARIA, SE COMENTA ESTA SECCIÓN.
--  UNION --ALL --  prueba
--------------------------------------------
--	SELECT 
--	4 as origen,
--		----------Tipo de Identificación----------
--		CASE perEmpresa.pertipoidentificacion 
--			WHEN 'NIT' THEN '7' 
--			WHEN 'CEDULA_CIUDADANIA' THEN '1' 
--			WHEN 'CEDULA_EXTRANJERIA' THEN '4'
--			WHEN 'TARJETA_IDENTIDAD' THEN '2' 
--			WHEN 'REGISTRO_CIVIL' THEN '3'
--			WHEN 'PASAPORTE' THEN '6'
--			WHEN 'CARNE_DIPLOMATICO' THEN '8'
--			WHEN 'PERM_ESP_PERMANENCIA' THEN '9' 
--			WHEN 'PERM_PROT_TEMPORAL' THEN '9'
--			ELSE pertipoidentificacion
--		END AS [Tipo de Identificación], 

--		----------Número de Identificación----------
--		perEmpresa.perNumeroIdentificacion AS [Número de Identificación], 
	
--		----------Nombre----------
--		SUBSTRING (perEmpresa.perRazonSocial,1,200) AS [Nombre], 
	
--		----------Código municipio----------
--		RIGHT (munCodigo,5) AS [Código municipio] , 
	
--		----------Dirección----------
--		LEFT (ubiPrincipal.ubidireccionfisica,200) AS [Dirección], 

--		----------Estado de vinculación----------
--			CASE  
--			WHEN empleador.empEstadoEmpleador = 'ACTIVO' THEN '1'

--			WHEN 
--				empleador.empEstadoEmpleador = 'INACTIVO' AND 
--				empleador.empMotivoDesafiliacion = 'OTRO'  
--			THEN '4' 

--			WHEN 
--				empleador.empEstadoEmpleador = 'INACTIVO' AND 
--				empleador.empMotivoDesafiliacion = 'CERO_TRABAJADORES_SOLICITUD_EMPLEADOR'  
--			THEN '4'

--			WHEN 
--				empleador.empEstadoEmpleador = 'INACTIVO' AND 
--				empleador.empMotivoDesafiliacion = 'CERO_TRABAJADORES_NOVEDAD_INTERNA'  
--			THEN '4' 

--			WHEN 
--				empleador.empEstadoEmpleador = 'INACTIVO' AND 
--				empleador.empMotivoDesafiliacion = 'ANULADO'  
--			THEN '4' 

--			WHEN 
--				empleador.empEstadoEmpleador = 'INACTIVO' AND 
--				empleador.empMotivoDesafiliacion = 'RETIRO_POR_TRASLADO_OTRA_CCF'  
--			THEN '4' 

--			WHEN 
--				empleador.empEstadoEmpleador = 'INACTIVO' AND 
--				empleador.empMotivoDesafiliacion = 'CESE_EN_PROCESO_LIQUIDACION_LIQUIDADO_FALLECIDO'  
--			THEN '4'

--			WHEN 
--				empleador.empEstadoEmpleador = 'INACTIVO' AND 
--				empleador.empMotivoDesafiliacion = 'FUSION_ADQUISICION'  
--			THEN '4'
			
--			WHEN 
--				empleador.empEstadoEmpleador = 'INACTIVO' AND 
--				empleador.empMotivoDesafiliacion = 'MULTIAFILIACION'  
--			THEN '4'

--			WHEN 
--				empleador.empEstadoEmpleador = 'INACTIVO' AND 
--				empleador.empMotivoDesafiliacion = 'EXPULSION_POR_USO_INDEBIDO_DE_SERVICIOS'  
--			THEN '2' 

--			WHEN 
--				empleador.empEstadoEmpleador = 'INACTIVO' AND 
--				empleador.empMotivoDesafiliacion = 'EXPULSION_POR_INFORMACION_INCORRECTA'  
--			THEN '2'

--			WHEN 
--				empleador.empEstadoEmpleador = 'INACTIVO' AND 
--				empleador.empMotivoDesafiliacion = 'EXPULSION_POR_MOROSIDAD'  
--			THEN '2' 
--			--else 'vinc'
--		END [Estado de vinculación],

--		----------Tipo de Aportante----------
--		'1' AS [Tipo de Aportante], 

--		----------Tipo de sector----------
--		CASE 
--			WHEN empresa.empnaturalezajuridica = 'PRIVADA' THEN '2' 
--			ELSE 
--				CASE 
--					WHEN empresa.empNaturalezaJuridica = 'PUBLICA' THEN '1' 
--					ELSE
--						CASE 
--							WHEN empresa.empNaturalezaJuridica = 'MIXTA' THEN '3' 
--							ELSE '4' --E.empNaturalezaJuridica 
--						END 
--				END 
--		END AS [Tipo de sector],

--		----------Actividad económica principal----------
--		RIGHT('00000' + Ltrim(Rtrim(ciiu.ciicodigo)),4) AS [Actividad económica principal],

--		----------Situación de la Empresa frente a la Ley 1429 de 2010----------
--	   CASE WHEN bemBeneficioActivo = 0 AND bembeneficio = '1' then' 1'
--	        WHEN bemBeneficioActivo = 0 AND bembeneficio = '2' then' 2'
--			WHEN bembeneficio IS NULL then '2' 
--		    WHEN bemBeneficioActivo = 1 AND bemBeneficio = '1' THEN '1' 
--			WHEN bemBeneficioActivo = 1 AND bembeneficio = '2' THEN '2'
--		end as [Situación de la Empresa frente a la Ley 1429 de 2010], 

--		----------Progresividad en el pago de los parafiscales frente a la Ley 1429 de 2010----------
--		case 
--			when beneficioEmpl.bemfechavinculacion is null or bembeneficio = '2' then '6' 
--			else 
--				case 
--					when beneficioEmpl.bemfechavinculacion <= '2014-12-31' then '5' 
--					else 
--						case 
--							when beneficioEmpl.bemfechavinculacion > '2014-12-31' then '5' 
--						end 
--				end 
--		end as [Progresividad en el pago de los parafiscales frente a la Ley 1429 de 2010],

--		----------Situación de la Empresa frente a la Ley 590 de 2000----------
--	   CASE WHEN bemBeneficioActivo = 0 AND bemBeneficio = '2' then '1'
--	        when bemBeneficioActivo = 0 AND bemBeneficio = '1' THEN '2'
--			WHEN bembeneficio IS NULL then '2' 
--		    WHEN bemBeneficioActivo = 1 AND bemBeneficio = '1' THEN '2' 
--			WHEN bemBeneficioActivo = 1 AND bembeneficio = '2' THEN '1'
		 
--		end as [Situación de la Empresa frente a la Ley 590 de 2000],
	
--		----------Progresividad en el pago de los parafiscales frente a la Ley 590 de 2000----------
--		case 
--			when bembeneficio = '2' 
--			THEN dbo.UFN_ObtenerProgreso590(bemfechavinculacion) 
--			else 
--				'6'
--			end 
--		  as [Progresividad en el pago de los parafiscales frente a la Ley 590 de 2000],

--		----------Aporte total mensual----------
--		REPLACE (SUM (ISNULL (apoGeneral.aporteoriginal,0))+(select ISNULL(sum(totalaporteobligatorio),0) from #PlanillasN pn where pn.regnumeroidentificacionaportante=pernumeroidentificacion  ),'.00000','') AS [Aporte total mensual], 

--		----------Intereses pagados por mora----------
--		REPLACE (SUM (ISNULL (ROUND (apoGeneral.interesOriginal, 0),0))+(select ISNULL(sum(totalinteresesMora),0) from #PlanillasN pn where pn.regnumeroidentificacionaportante=pernumeroidentificacion  ), '.00000', '') AS [Intereses Pagados por Mora], 
--		--ROUND (sum (isnull (APGA.apgValorIntMora,0)),0) AS [Intereses pagados por mora], 

--		----------Valor reintegros----------
--		'0' AS [Valor reintegros] 

--	FROM
--		persona perEmpresa
--		INNER JOIN empresa empresa ON empresa.emppersona=perEmpresa.perid
--		INNER JOIN empleador empleador 
--			ON empleador.empempresa=empresa.empid
--			--AND empleador.empEstadoEmpleador = 'ACTIVO'
	
--		INNER JOIN codigociiu ciiu WITH (NOLOCK) ON ciiu.ciiid = empresa.empcodigociiu
--		INNER JOIN ubicacionempresa ubiEmpresa WITH (NOLOCK) ON ubiEmpresa.ubeempresa = empresa.empid
--		INNER JOIN ubicacion ubiPrincipal WITH (NOLOCK) 
--			ON ubiPrincipal.ubiid = ubiEmpresa.ubeubicacion
--			AND ubiEmpresa.ubeTipoUbicacion = 'UBICACION_PRINCIPAL'
--		LEFT JOIN municipio munEmpresa WITH (NOLOCK) ON munEmpresa.munid = ubiPrincipal.ubimunicipio
--		LEFT JOIN departamento depEmpresa WITH (NOLOCK) ON depEmpresa.depid = munEmpresa.mundepartamento 

--		LEFT JOIN RolAfiliado roa 
--			ON roa.roaEmpleador = empleador.empId 
--		--	AND roa.roaEstadoAfiliado='ACTIVO'
--		INNER join #tempAportes apoGeneral WITH (nolock) on apoGeneral.apgEmpresa=empresa.empid 		
--				AND apoGeneral.aporteoriginal > 0
		
--		--LEFT JOIN AporteDetallado apgdet ON apgdet.apdAporteGeneral = apgId

--		LEFT JOIN beneficioempleador beneficioEmpl WITH (NOLOCK) 
--		       ON beneficioEmpl.bemempleador = empleador.empid --AND  beneficioEmpl.bemBeneficioActivo= 1
--		LEFT JOIN beneficio beneficio WITH (NOLOCK) ON beneficio.befid=bembeneficio

--	WHERE 
--		-- perEmpresa.perNumeroIdentificacion IN ( '900111343') AND
--		convert(date,empleador.empFechaCambioEstadoAfiliacion) <= @FECHA_FINAL AND
--		roa.roaId IS NULL 
--	GROUP BY 
--		empleador.empId,
--		perEmpresa.pertipoidentificacion, 
--		perEmpresa.pernumeroidentificacion, 
--		perEmpresa.perrazonsocial,
--		munCodigo, 
--		ubiPrincipal.ubidireccionfisica, 
--		empleador.empestadoempleador,
--		empresa.empnaturalezajuridica, 
--		ciiu.ciicodigo,
--		bemBeneficio, 
--		bemfechavinculacion,
--		empleador.empMotivoDesafiliacion,bemBeneficioActivo

------------------------------------------INDEPENDIENTES ACTIVO
  UNION ALL  -- prueba
------------------------------------------
	
SELECT  
	
		----------Tipo de identificación----------
		CASE  pertipoidentificacion 

			WHEN 'NIT' THEN '7' 
			WHEN 'CEDULA_CIUDADANIA' THEN '1' 
			WHEN 'CEDULA_EXTRANJERIA' THEN '4'
			WHEN 'TARJETA_IDENTIDAD' THEN '2' 
			WHEN 'REGISTRO_CIVIL' THEN '3'
			WHEN 'PASAPORTE' THEN '6'
			WHEN 'CARNE_DIPLOMATICO' THEN '8'
			WHEN 'PERM_ESP_PERMANENCIA' THEN '9'
			WHEN 'PERM_PROT_TEMPORAL' THEN '15'
			WHEN 'SALVOCONDUCTO' THEN 'Salvoconducto'
			ELSE pertipoidentificacion

		END AS [TIPO DE IDENTIFICACION], 

		----------Número de identificación----------
		pernumeroidentificacion AS [Número de identificación], 

		----------Nombre----------
		SUBSTRING (perrazonsocial,1,200) as [Nombre], 

		----------Código municipio----------
		RIGHT (munCodigo,5) AS [Código municipio], 

		----------Dirección----------
		LEFT (ubidireccionfisica,200) AS [Dirección], 

		----------Estado de vinculación----------
		CASE 
			WHEN roaEstadoAfiliado = 'ACTIVO' THEN '1'
			WHEN roaEstadoAfiliado = 'INACTIVO' AND roaMotivoDesafiliacion = 'SUSTITUCION_PATRONAL'  THEN '4' 
			WHEN roaEstadoAfiliado = 'INACTIVO' AND roaMotivoDesafiliacion = 'AFILIACION_ANULADA'  THEN '4' 
			WHEN roaEstadoAfiliado = 'INACTIVO' AND roaMotivoDesafiliacion = 'DESAFILIACION_EMPLEADOR'  THEN '4' 
			WHEN roaEstadoAfiliado = 'INACTIVO' AND roaMotivoDesafiliacion = 'OTROS'  THEN '4' 
			WHEN roaEstadoAfiliado = 'INACTIVO' AND roaMotivoDesafiliacion = 'RETIRO_POR_MORA_APORTES'  THEN '2' 
			WHEN roaEstadoAfiliado = 'INACTIVO' AND roaMotivoDesafiliacion = 'RETIRO_POR_CAMBIO_DE_CAJA_COMPENSACION'  THEN '4' 
			WHEN roaEstadoAfiliado = 'INACTIVO' AND roaMotivoDesafiliacion = 'ENTREGA_DE_INFORMACION_FRAUDULENTA_CCF'  THEN '2' 
			WHEN roaEstadoAfiliado = 'INACTIVO' AND roaMotivoDesafiliacion = 'MAL_USO_DE_SERVICIOS_CCF'  THEN '2' 
			WHEN roaEstadoAfiliado = 'INACTIVO' AND roaMotivoDesafiliacion = 'FALLECIMIENTO'  THEN '4' 
			WHEN roaEstadoAfiliado = 'INACTIVO' AND roaMotivoDesafiliacion = 'RETIRO_VOLUNTARIO'  THEN '4' 
			WHEN roaEstadoAfiliado = 'INACTIVO' AND roaMotivoDesafiliacion = 'MULTIAFILIACION'  THEN '4' 
			WHEN  roaEstadoAfiliado = 'INACTIVO' AND  roaMotivoDesafiliacion = 'PENSIONADO_SIN_CAPACIDAD' THEN '4'
			WHEN roaEstadoAfiliado = 'INACTIVO' AND roaMotivoDesafiliacion IS NULL THEN '4'
		END [Estado de vinculación], 

		----------Tipo de Aportante----------
		CASE roaTipoAfiliado
			WHEN 'TRABAJADOR_INDEPENDIENTE' THEN '3'
			WHEN 'PENSIONADO' THEN '2' 
		END AS [Tipo de Aportante], 

		----------Tipo de sector----------
		CASE roaTipoAfiliado
			WHEN 'TRABAJADOR_INDEPENDIENTE' THEN '4'
			WHEN 'PENSIONADO' THEN '4' 
		END AS [Tipo de sector],

		----------Actividad económica principal----------
		CASE roaTipoAfiliado
			WHEN 'TRABAJADOR_INDEPENDIENTE' THEN '0000'
			WHEN 'PENSIONADO' THEN '0000' 
		END AS [Actividad económica principal],

		----------Situación de la Empresa frente a la Ley 1429 de 2010----------
		CASE  roaTipoAfiliado
			WHEN 'TRABAJADOR_INDEPENDIENTE' THEN '2'
			WHEN 'PENSIONADO' THEN '2' 
		END AS [Situación de la Empresa frente a la Ley 1429 de 2010],
 
		----------Progresividad en el pago de los parafiscales frente a la Ley 1429 de 2010----------
		CASE  roaTipoAfiliado
			WHEN 'TRABAJADOR_INDEPENDIENTE' THEN '6'
			WHEN 'PENSIONADO' THEN '6' 
		END AS [Progresividad en el pago de los parafiscales frente a la Ley 1429 de 2010],

		----------Situación de la Empresa frente a la Ley 590 de 2000----------
		CASE  roaTipoAfiliado
			WHEN 'TRABAJADOR_INDEPENDIENTE' THEN '2'
			WHEN 'PENSIONADO' THEN '2' 
		END AS [Situación de la Empresa frente a la Ley 590 de 2000],
 
		----------Progresividad en el pago de los parafiscales frente a la Ley 590 de 2000----------
		CASE  roaTipoAfiliado
			WHEN 'TRABAJADOR_INDEPENDIENTE' THEN '6'
			WHEN 'PENSIONADO' THEN '6' 
		END AS [Progresividad en el pago de los parafiscales frente a la Ley 590 de 2000],

		----------Aporte total mensual----------
		
		REPLACE (SUM (ISNULL (aporteoriginal,0))+(select isnull(sum(totalaporteobligatorio),0) from #PlanillasN pn where pn.regnumeroidentificacionaportante=pernumeroidentificacion )
		,'.00000','')
		AS [Aporte total mensual], 
		--REPLACE (sum (isnull (apgValTotalApoObligatorio,0)),'.00000','') AS [Aporte total mensual],

		----------Intereses pagados por mora----------
		REPLACE (sum (isnull (interesoriginal,0))+(select isnull(sum(totalinteresesMora),0) from #PlanillasN pn where pn.regnumeroidentificacionaportante=pernumeroidentificacion  )
		,'.00000','')
		AS [Intereses pagados por mora],

		----------Valor reintegros----------
		'0' AS [Valor reintegros] 
		from 
		#tempAportesPensionadosIndependientes t
		where (t.roaEstadoAfiliado = 'INACTIVO' and (t.aporteOriginal > 0 or t.interesOriginal > 0))
		or  (t.roaEstadoAfiliado = 'ACTIVO' )
	
			GROUP BY
		pertipoidentificacion, 
		pernumeroidentificacion, 
		perrazonsocial,munCodigo, 
		ubidireccionfisica, 
		afiId,
		roaEstadoAfiliado,
		roaTipoAfiliado, 
		roaMotivoDesafiliacion




		SELECT 
	[TIP_IDENTIFICACION],
	[NUM_IDENTIFICACION],
	[NOM_EMPRESA],
	[COD_MUNICIPIO_DANE],
	CASE
		WHEN [DIR_CORRESPONDECIA] = 'SIN DIRECCIÓN' THEN ''
		ELSE [DIR_CORRESPONDECIA]
	END AS [DIR_CORRESPONDECIA],
	[EST_VINCULACION],
	[TIP_APORTANTE],
	[TIP_SECTOR],
	[ACT_ECONOMICA],
	[SIT_EMPRESA_LEY_1429],
	[PRO_PAGO_LEY_1429],
	[SIT_EMPRESA_LEY_590],
	[PRO_PAGO_LEY_590],
	
	 REPLACE(MAX([APO_TOTAL_MENSUAL]), '.00000','') AS [APO_TOTAL_MENSUAL],
	MAX([INT_PAGADOS_MORA]) AS [INT_PAGADOS_MORA],
	MAX([VAL_REINTEGROS]) AS [VAL_REINTEGROS]
	/*SUM(CAST([APO_TOTAL_MENSUAL] AS BIGINT)) AS [APO_TOTAL_MENSUAL],
	SUM(CAST([INT_PAGADOS_MORA] AS BIGINT)) AS [INT_PAGADOS_MORA],
	SUM(CAST([VAL_REINTEGROS] AS BIGINT)) AS [VAL_REINTEGROS]*/

FROM #TEMP_EMPRESAS_APORTANTES
where 
[TIP_APORTANTE] <> '1' 
or 
(
([TIP_APORTANTE] ='1' and [EST_VINCULACION] ='1')
or 
([TIP_APORTANTE] ='1' and [EST_VINCULACION] <>'1' and  ([APO_TOTAL_MENSUAL] >0 or [INT_PAGADOS_MORA] >0) or [VAL_REINTEGROS] > 0 )
)
GROUP BY 
	[TIP_IDENTIFICACION],
	[NUM_IDENTIFICACION],
	[NOM_EMPRESA],
	[COD_MUNICIPIO_DANE],
	[DIR_CORRESPONDECIA],
	[EST_VINCULACION],
	[TIP_APORTANTE],
	[TIP_SECTOR],
	[ACT_ECONOMICA],
	[SIT_EMPRESA_LEY_1429],
	[PRO_PAGO_LEY_1429],
	[SIT_EMPRESA_LEY_590],
	[PRO_PAGO_LEY_590]


	end

	