--/****** Object:  StoredProcedure [dbo].[USP_GET_CierreCartera]    Script Date: 2025-03-10 910:52 AM ******/
--/****** Object:  StoredProcedure [dbo].[USP_GET_CierreCartera]    Script Date: 14/03/2024 8:28:13 a. m. ******/
--/****** Object:  StoredProcedure [dbo].[USP_GET_CierreCartera]    Script Date: 2023-09-11 9:19:48 AM ******/
--/****** Object:  StoredProcedure [dbo].[USP_GET_CierreCartera]    Script Date: 2023-08-02 8:59:41 AM ******/
--/****** Object:  StoredProcedure [dbo].[USP_GET_CierreCartera]    Script Date: 2023-07-31 8:47:17 AM ******/
--/****** Object:  StoredProcedure [dbo].[USP_GET_CierreCartera]    Script Date: 2023-07-27 8:55:03 AM ******/
--/****** Object:  StoredProcedure [dbo].[USP_GET_CierreCartera]    Script Date: 2023-07-26 9:52:59 AM ******/
--/****** Object:  StoredProcedure [dbo].[USP_GET_CierreCartera]    Script Date: 2023-07-25 11:18:57 AM ******/
--/****** Object:  StoredProcedure [dbo].[USP_GET_CierreCartera]    Script Date: 2023-07-18 1:07:00 PM ******/
--/****** Object:  StoredProcedure [dbo].[USP_GET_CierreCartera]    Script Date: 2023-07-17 2:24:53 PM ******/
--/****** Object:  StoredProcedure [dbo].[USP_GET_CierreCartera]    Script Date: 2023-07-13 9:19:38 AM ******/
--/****** Object:  StoredProcedure [dbo].[USP_GET_CierreCartera]    Script Date: 2023-06-26 9:13:00 AM ******/
--/****** Object:  StoredProcedure [dbo].[USP_GET_CierreCartera]    Script Date: 13/06/2023 12:06:48 p. m. ******/
--/****** Object:  StoredProcedure [dbo].[USP_GET_CierreCartera]    Script Date: 29/05/2023 9:28:59 a. m. ******/
--/****** Object:  StoredProcedure [dbo].[USP_GET_CierreCartera]    Script Date: 19/05/2023 9:15:38 a. m. ******/
--/****** Object:  StoredProcedure [dbo].[USP_GET_CierreCartera]    Script Date: 18/05/2023 1:34:02 p. m. ******/
--/****** Object:  StoredProcedure [dbo].[USP_GET_CierreCartera]    Script Date: 18/05/2023 12:55:34 p. m. ******/
-----creado por olga vega 20230518

CREATE OR ALTER   PROCEDURE [dbo].[USP_GET_CierreCartera]
(
	@FechaInicial DATE,
	@FechaFinal DATE,
	@of  varchar(1) = null
)
---EXECUTE [USP_GET_CierreCartera] '2023-01-01','2024-03-12', 0
---EXECUTE [USP_GET_CierreCartera] '2023-06-01','2023-06-30', NULL
---EXECUTE [USP_GET_CierreCartera] '2023-07-01','2023-07-31', NULL
---reporte 32
AS


SET ANSI_NULLS ON
SET  NOCOUNT ON 
SET QUOTED_IDENTIFIER ON

					  DECLARE @SQL NVARCHAR(MAX)
					  SELECT @SQL= 
					--  N'SELECT DISTINCT CASE WHEN  c.REV  = MAX( c.REV)  
					--	      OVER (PARTITION BY carpersona, carPeriodoDeuda  ) 
					--	      THEN carpersona  ELSE NULL END as  carPersona,
					--		  carid ,  
					--		  c.carFechaCreacion  , 
					--		  c.carPeriodoDeuda  ,
					--		  cd.cadDeudaPresunta  ,
					--		  cd.cadDeudaReal  ,
					--		  c.carMetodo , c.carEstadoCartera,c.carTipoLineaCobro, 
					--		  c.carFechaAsignacionAccion, c.carDeudaParcial,c.cardeudapresunta, c.carTipoAccionCobro
					--     FROM  Cartera_aud c WITH(NOLOCK)  		
				 --  INNER JOIN Revision r ON r.revid = c.rev
				 --  INNER JOIN (  SELECT SUM(cadDeudaPresunta) AS cadDeudaPresunta, SUM(cadDeudaReal ) AS cadDeudaReal, 
				 --                       cadCartera
					--			   FROM carteradependiente_aud WITH(NOLOCK)
					--			  WHERE cadEstadoOperacion =''VIGENTE'' 
					--		   GROUP BY cadCartera) cd 
					--       ON c.carid = cd.cadCartera     					
					--	WHERE c.carEstadoCartera = ''MOROSO''  	
					--	  AND c.carTipoAccionCobro NOT IN (''A01'',''A02'')
				
					--AND (DATEADD(SECOND, r.revTimeStamp / 1000, ''19700101'') AT TIME ZONE ''UTC'' AT TIME ZONE ''SA Pacific Standard Time'')<= @FechaFinal
					-- 	ORDER BY carid '


						N'
						
						drop table if exists #DataCartera_aud
						;with xcar as (
						select car.carId, max(revId) as rev
						from dbo.Cartera_aud as car with (nolock)
						inner join dbo.revision as r with (nolock) on car.REV = r.revId
						group by car.carId)
						select distinct car.carId,car.carPersona,car.carFechaCreacion,car.carPeriodoDeuda,car.carMetodo,car.carEstadoCartera,car.carTipoLineaCobro,car.carFechaAsignacionAccion,car.carDeudaParcial
						,car.cardeudapresunta,car.carTipoAccionCobro,car.rev
						into #DataCartera_aud
						from dbo.Cartera_aud as car with (nolock)
						inner join dbo.revision as r with (nolock) on car.REV = r.revId
						inner join xcar on car.carId = xcar.carId and xcar.rev = r.revId
						order by car.carId


						drop table if exists #DataCarteraDependiente_aud
						;with xCad as (
						select cad.cadId, max(revId) as rev
						from dbo.carteradependiente_aud as cad with (nolock)
						inner join dbo.revision as r with (nolock) on cad.REV = r.revId
						inner join #DataCartera_aud car on cad.cadCartera = car.carId
						group by cad.cadId)
						select cad.cadId, cad.cadDeudaPresunta,cadPersona,cad.cadCartera,cad.cadDeudaReal,cad.REV
						into #DataCarteraDependiente_aud
						from dbo.carteradependiente_aud as cad with (nolock)
						inner join dbo.revision as r with (nolock) on cad.REV = r.revId
						inner join xCad on cad.cadId = xCad.cadId and xCad.rev = r.revId
						order by cad.cadId



						select 
						cat_aud.carPersona
						,cat_aud.carid
						,cat_aud.carFechaCreacion
						,cat_aud.carPeriodoDeuda
						,CASE WHEN sum(cast(cadep_aud.cadDeudaPresunta as numeric(19,0))) IS NULL THEN sum(cast(cat_aud.cardeudapresunta as numeric(19,0))) ELSE sum(cast(cadep_aud.cadDeudaPresunta as  numeric(19,0))) END AS ''cadDeudaPresunta''
						,CASE WHEN sum(cast(cadep_aud.cadDeudaReal as numeric(19,0))) IS NULL THEN 0 ELSE sum(cast(cadep_aud.cadDeudaReal as numeric(19,0))) END AS ''cadDeudaReal''
						,cat_aud.carMetodo
						,cat_aud.carEstadoCartera
						,cat_aud.carTipoLineaCobro
						,cat_aud.carFechaAsignacionAccion
						,cat_aud.carDeudaParcial
						,cast(cat_aud.cardeudapresunta as numeric(19,0)) as[cardeudapresunta]
						,cat_aud.carTipoAccionCobro
						from  #DataCartera_aud cat_aud 
						left join  #DataCarteraDependiente_aud cadep_aud on (cat_aud.carId = cadep_aud.cadCartera) --and (cat_aud.rev = cadep_aud.rev)
						where   cat_aud.carTipoLineaCobro IN (''LC1'',''C6'') AND carEstadoCartera = ''MOROSO'' --and cat_aud.carId = 8783
						--and ( cat_aud.carDeudaPresunta - (cadep_aud.cadDeudaPresunta + cadep_aud.cadDeudaReal)) != 0
						group by cat_aud.carPersona
						,cat_aud.carid
						,cat_aud.carFechaCreacion
						,cat_aud.carPeriodoDeuda
						,cat_aud.carMetodo
						,cat_aud.carEstadoCartera
						,cat_aud.carTipoLineaCobro
						,cat_aud.carFechaAsignacionAccion
						,cat_aud.carDeudaParcial
						,cat_aud.cardeudapresunta
						,cat_aud.carTipoAccionCobro								
						
						
						'

						IF OBJECT_ID('tempdb.dbo.#AudCartera', 'U') IS NOT NULL
							DROP TABLE #AudCartera; 

						CREATE TABLE #AudCartera ( carpersona bigint, 
								 
													  carid BIGINT,  
													  carFechaCreacion DATETIME , 
													  carPeriodoDeuda  DATE,
													  cadDeudaPresunta NUMERIC ,
													  cadDeudaReal NUMERIC ,
													  carMetodo VARCHAR(20),
													  carEstadoCartera varchar(6),
													  carTipoLineaCobro varchar(3),													 
													  carFechaAsignacionAccion date,carDeudaParcial varchar(32),
													  cardeudapresunta numeric,carTipoAccionCobro varchar(4),
													  shard VARCHAR(500)
												    )
 	
						INSERT INTO #AudCartera 
						(
												 carpersona ,
													  carid ,  
													  carFechaCreacion  , 
													  carPeriodoDeuda  ,
													  cadDeudaPresunta  ,
													  cadDeudaReal  ,
													  carMetodo,carEstadoCartera,carTipoLineaCobro,													   
													  carFechaAsignacionAccion,carDeudaParcial,
													  cardeudapresunta,carTipoAccionCobro,shard
												  ) 
	
						

					EXEC sp_execute_remote N'CoreAudReferenceData',
					@SQL,
					N'@FechaInicial DATE, @fechaFinal DATE',
					@FechaInicial = @FechaInicial, 
					@fechaFinal = @fechaFinal

 


			   DECLARE @oficial  VARCHAR(1) = 0

			   SELECT @oficial =   
			   (SELECT ISNULL(x.srOficial,0) FROM (
			   SELECT CASE WHEN srFechaGeneracion  = MAX(srFechaGeneracion)  
						   OVER (PARTITION BY srParamReportesNormativosId ) 
						   THEN srParamReportesNormativosId ELSE NULL END AS srParamReportesNormativosId ,
								srOficial, srFechaInicio, srFechaFin
				 		 	FROM SolicitudesReportesNormativos 
					  INNER JOIN ParametrizacionReportesNormativos prn
							  ON prnId = srParamReportesNormativosId
						   WHERE srParamReportesNormativosId = 38 
							-- AND srEstado = 'PROCESANDO'
							 AND srFechaInicio = @FechaInicial 
							 AND srFechaFin = @FechaFinal
							) X WHERE srParamReportesNormativosId IS NOT NULL)



 IF @oficial = 0
 --DECLARE 	@FechaInicial DATE= '2023-06-01',
	--@FechaFinal DATE = '2023-06-30'
 BEGIN 
 	            SELECT  DISTINCT--@FechaFinal 
				       LEFT(@FechaFinal ,7) AS  [Periodo reporte],
					   
				        (
							SELECT cnsValor 
							  FROM Constante 
							 WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO'
						)  AS [Cód. Administradora],
						
						CASE  pe.perTipoIdentificacion 
							WHEN 'CEDULA_CIUDADANIA' THEN 'CC' 
							WHEN 'TARJETA_IDENTIDAD' THEN 'TI' 
							WHEN 'REGISTRO_CIVIL' THEN 'RC'
							WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
							WHEN 'PASAPORTE' THEN 'PA'
							WHEN 'NIT' THEN 'NI' 
							WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
							WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE' 
							WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
							WHEN 'SALVOCONDUCTO' THEN 'SC'
						END  AS [Tipo de documento del aportante],
						pe.perNumeroIdentificacion AS [Número de documento del aportante],
						pe.perRazonSocial AS [Nombre o razón social del aportante],
						CASE WHEN  pe.perTipoIdentificacion = 'NIT' THEN isnull(pe.perDigitoVerificacion,'') ELSE NULL END   AS [Digito de verificación],
						left(c.carPeriodoDeuda,7) AS [Periodo mora],
						--replace(c.cardeudapresunta,'.00000','') AS [Valor de la cartera] ,
						CASE WHEN c.cadDeudaReal  > 0 THEN c.cadDeudaReal ELSE  CONVERT(NUMERIC(15,0),c.cardeudapresunta) END  AS [Valor de la cartera] ,
						--ISNULL( bc.bcaactividad,'') AS [Última acción de cobro],
						CASE WHEN c.carTipoAccionCobro = 'AB1'   THEN  'B1'
							 WHEN c.carTipoAccionCobro = 'BC1'	 THEN  'C1'
							 WHEN c.carTipoAccionCobro = 'CD1'	 THEN  'D1'
							 WHEN c.carTipoAccionCobro = 'DE1'	 THEN  'E1'
							 WHEN c.carTipoAccionCobro = 'EF1'	 THEN  'F1'
							 WHEN c.carTipoAccionCobro = 'F1'	 THEN  'F1'
							 WHEN c.carTipoAccionCobro = 'AB2'	 THEN  'B2'
							 WHEN c.carTipoAccionCobro = 'BC2'	 THEN  'C2'
							 WHEN c.carTipoAccionCobro = 'CD2'	 THEN  'D2'
							 WHEN c.carTipoAccionCobro = 'DE2'	 THEN  'E2'
							 WHEN c.carTipoAccionCobro = 'EF2'	 THEN  'F2'
							 WHEN c.carTipoAccionCobro = 'FG2'	 THEN  'G2'
							 WHEN c.carTipoAccionCobro = 'GH2'	 THEN  'H2'
							 WHEN c.carTipoAccionCobro = 'H2'	 THEN  'H2'
							 ELSE c.carTipoAccionCobro END AS [Última acción de cobro],
					 	--ISNULL(CONVERT(VARCHAR,CONVERT(varchar,bc.bcafecha,103),121),'') AS [Fecha última acción de cobro],
						ISNULL(CONVERT(VARCHAR,CONVERT(varchar,bc.bcafecha,103),121),CONVERT(VARCHAR,CONVERT(varchar,c.carFechaAsignacionAccion,103),121)) AS [Fecha última acción de cobro],
						--Clasificación del estado del aportante

					 ISNULL ( ( 
						SELECT TOP 1
							CASE 
								WHEN bc.bcaActividad IN ('ACTIVACION_LIQUIDACION')
								THEN '3'
								WHEN bc.bcaActividad IN ('ACTIVACION_REESTRUCTURACION')
								THEN '2'
								ELSE '1'
							END 	
						 FROM   BitacoraCartera bc WITH(NOLOCK) 				      
						WHERE  bc.bcaFecha <=  @FechaFinal 
						  AND bc.bcaActividad IN ('ACTIVACION_LIQUIDACION', 'ACTIVACION_REESTRUCTURACION')
						GROUP BY bc.bcaActividad,bc.bcaFecha
						ORDER BY bc.bcaFecha DESC
					  ), '1') AS [Clasificación del estado del aportante],

					  CASE WHEN em.empMotivoDesafiliacion IN('EXPULSION_POR_MOROSIDAD','CERO_TRABAJADORES_NOVEDAD_INTERNA','MULTIAFILIACION') AND em.empEstadoEmpleador = 'INACTIVO'
					       THEN 1
					       WHEN em.empMotivoDesafiliacion IS NULL AND em.empEstadoEmpleador <> 'INACTIVO'
					       THEN 2
					       ELSE 3 END AS [Aportantes expulsados],
						CASE WHEN  c.cadDeudaReal  >0 THEN 0 ELSE 
						--replace(cd.cadDeudaPresunta,'.00000','')  END AS [Valor Deuda Presunta],
						CONVERT(NUMERIC(15,0),c.cadDeudaPresunta)  END AS [Valor Deuda Presunta],
					  --  replace(cd.cadDeudaReal,'.00000','') AS [Valor Deuda Real],
						CONVERT(NUMERIC(15,0),c.cadDeudaReal)   AS [Valor Deuda Real],
				        CASE WHEN c.cadDeudaReal >0 THEN '' ELSE 'X' END AS [Marcación "X" si tiene Deuda presunta],
						CASE WHEN c.cadDeudaReal >0 THEN 'X' ELSE '' END  AS [Marcación "X" si tiene Deuda Real],
						ISNULL(CONVERT(VARCHAR,CONVERT(varchar,getdate(),103),121),'') AS [Fecha generación de reporte],---???
						--ca.cagNumeroOperacion AS 'Número de operación',
						REPLACE( (copDeudaPresuntaRegistrada    +  copDeudaRealRegistrada   ) ,'.00000','') AS [Valor Convenio Pago],
						ISNULL(CONVERT(VARCHAR,CONVERT(varchar,copFechaRegistro,103),121),'')    AS [Fecha de Convenio de pago],
						ISNULL(CONVERT(VARCHAR,CONVERT(varchar,ppcFechaPago,103),121),'')   AS [Fecha fin Convenio de pago],
						ISNULL(CONVERT(VARCHAR,CONVERT(varchar,c.carFechaCreacion,103),121),'') AS [Fecha de ingreso a cartera],
						c.carDeudaParcial   AS [Deuda parcial o total]
					--	c.carMetodo AS	'Tipo de método' 
				  FROM #AudCartera c WITH(NOLOCK)
						INNER JOIN carteraagrupadora ca WITH(NOLOCK) ON ca.cagCartera = carid 			 
						INNER JOIN Persona pe WITH(NOLOCK) ON c.carpersona = pe.perid 
						INNER JOIN Empresa e WITH(NOLOCK) ON pe.perid = e.empPersona
						INNER JOIN Empleador em WITH(NOLOCK) ON e.empid = em.empEmpresa
						 LEFT JOIN ( SELECT SUM(cadDeudaPresunta) AS cadDeudaPresunta, SUM(cadDeudaReal) AS cadDeudaReal, cadCartera
									   FROM carteradependiente WITH(NOLOCK)
									  WHERE cadEstadoOperacion ='VIGENTE'
								   GROUP BY cadCartera) cd 
								ON c.carid = cd.cadCartera
						LEFT JOIN  ( SELECT DISTINCT  CASE WHEN bcaid  = MAX(bcaid)  
										OVER (PARTITION BY bcaPersona  ) 
										THEN bcaPersona ELSE NULL END AS bcaPersona  , 
											bcaActividad, bcaFecha
										FROM BitacoraCartera AS bc  
										WHERE bc.bcaactividad IN ('A1','A2','FIRMEZA_TITULO_EJECUTIVO','GENERAR_LIQUIDACION',
										-- LC1 y C6 MÉTODO 1
										'B1','C1','D1','E1','F1',
										-- LC1 y C6 MÉTODO 2
										'B2','C2','D2','E2','F2','G2','H2',
										-- LC1 MÉTODO 1 transitoria
										'AB1','BC1','CD1','DE1','EF1', 
										-- LC1 MÉTODO 2 transitoria
										'AB2','BC2','CD2','DE2','EF2','FG2','GH2','H2')) bc ON bc.bcaPersona = c.carPersona AND bcaPersona IS NOT NULL
		
						 LEFT JOIN ConvenioPago  WITH(NOLOCK) 
						 LEFT JOIN PagoPeriodoConvenio  WITH(NOLOCK)  ON ppcConvenioPago = copId
								ON copPersona = c.carPersona 
							   AND ppcPeriodo = c.carPeriodoDeuda
							   AND copFechaRegistro BETWEEN @FechaInicial AND @FechaFinal
							   AND copEstadoConvenioPago = 'ACTIVO'
			     WHERE c.carEstadoCartera = 'MOROSO'  
				   AND c.carid NOT IN (SELECT carid FROM cartera WHERE carEstadoCartera = 'AL_DIA')
				   AND c.carTipoLineaCobro IN ('LC1','C6')
				   AND c.carTipoAccionCobro NOT IN ('A1', 'AB1', 'A2', 'AB2','A01','A02')
				   AND cd.cadDeudaPresunta > 0
				 
				   AND (CONVERT(DATE,c.carFechaAsignacionAccion)<=  @FechaFinal 
				   OR CONVERT(DATE,c.carFechaAsignacionAccion) IS NULL  
   				 OR copFechaRegistro BETWEEN @fechainicial AND @FechaFinal )
				   
	  
END
	ELSE 
BEGIN 	  
		 	 INSERT INTO rno.HistoricoCierreCartera (
					 [FechaProceso]
					,[Periodo reporte]
					,[Cód. Administradora]
					,[Tipo de documento del aportante]
					,[Número de documento del aportante]
					,[Nombre o razón social del aportante]
					,[Digito de verificación]
					,[Periodo mora]
					,[Valor de la cartera]
					,[Última acción de cobro]
					,[Fecha última acción de cobro]
					,[Clasificacion del estado del aportante]
					,[Aportantes expulsados]
					,[Valor Deuda Presunta]
					,[Valor Deuda Real]
					,[Marcación "X" si tiene Deuda presunta]
					,[Marcación "X" si tiene Deuda Real]
					,[Fecha generación de reporte]
					,[Valor Convenio Pago]
					,[Fecha de Convenio de pago]
					,[Fecha fin Convenio de pago]
					,[Fecha de ingreso a cartera]
					,[Deuda parcial o total]
					,[FechaInicial]
					,[FechaFinal]
					)

 						SELECT  DISTINCT getdate(),
						LEFT(@FechaFinal ,7) AS  [Periodo reporte],					   
						(
							SELECT cnsValor 
							  FROM Constante 
							 WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO'
						)  AS [Cód. Administradora],
						
						CASE  pe.perTipoIdentificacion 
							WHEN 'CEDULA_CIUDADANIA' THEN 'CC' 
							WHEN 'TARJETA_IDENTIDAD' THEN 'TI' 
							WHEN 'REGISTRO_CIVIL' THEN 'RC'
							WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
							WHEN 'PASAPORTE' THEN 'PA'
							WHEN 'NIT' THEN 'NI' 
							WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
							WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE' 
							WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
							WHEN 'SALVOCONDUCTO' THEN 'SC'
						END  AS [Tipo de documento del aportante],
						pe.perNumeroIdentificacion AS [Número de documento del aportante],
						pe.perRazonSocial AS [Nombre o razón social del aportante],
						CASE WHEN  pe.perTipoIdentificacion = 'NIT' THEN isnull(pe.perDigitoVerificacion,0) ELSE NULL END   AS [Digito de verificación],
						left(c.carPeriodoDeuda,7) AS [Periodo mora],
						--replace(c.cardeudapresunta,'.00000','') AS [Valor de la cartera] ,
						CASE WHEN c.cadDeudaReal  > 0 THEN c.cadDeudaReal ELSE  CONVERT(NUMERIC(15,0),c.cardeudapresunta) END  AS [Valor de la cartera] ,
						--ISNULL( bc.bcaactividad,'') AS [Última acción de cobro],
						CASE WHEN c.carTipoAccionCobro = 'AB1'   THEN  'B1'
							 WHEN c.carTipoAccionCobro = 'BC1'	 THEN  'C1'
							 WHEN c.carTipoAccionCobro = 'CD1'	 THEN  'D1'
							 WHEN c.carTipoAccionCobro = 'DE1'	 THEN  'E1'
							 WHEN c.carTipoAccionCobro = 'EF1'	 THEN  'F1'
							 WHEN c.carTipoAccionCobro = 'F1'	 THEN  'F1'
							 WHEN c.carTipoAccionCobro = 'AB2'	 THEN  'B2'
							 WHEN c.carTipoAccionCobro = 'BC2'	 THEN  'C2'
							 WHEN c.carTipoAccionCobro = 'CD2'	 THEN  'D2'
							 WHEN c.carTipoAccionCobro = 'DE2'	 THEN  'E2'
							 WHEN c.carTipoAccionCobro = 'EF2'	 THEN  'F2'
							 WHEN c.carTipoAccionCobro = 'FG2'	 THEN  'G2'
							 WHEN c.carTipoAccionCobro = 'GH2'	 THEN  'H2'
							 WHEN c.carTipoAccionCobro = 'H2'	 THEN  'H2'
							 ELSE c.carTipoAccionCobro END AS [Última acción de cobro],
						ISNULL(CONVERT(VARCHAR,CONVERT(varchar,bc.bcafecha,103),121),CONVERT(VARCHAR,CONVERT(varchar,c.carFechaAsignacionAccion,103),121)) AS [Fecha última acción de cobro],
						--cast(bc.bcafecha as date) AS [Fecha última acción de cobro],
					 	--ISNULL(CONVERT(VARCHAR,CONVERT(varchar,bc.bcafecha,103),121),'') AS [Fecha última acción de cobro],
						--Clasificación del estado del aportante

					 ISNULL ( ( 
						SELECT TOP 1
							CASE 
								WHEN bc.bcaActividad IN ('ACTIVACION_LIQUIDACION')
								THEN '3'
								WHEN bc.bcaActividad IN ('ACTIVACION_REESTRUCTURACION')
								THEN '2'
								ELSE '1'
							END 	
						 FROM   BitacoraCartera bc WITH(NOLOCK) 				      
						WHERE  bc.bcaFecha <=  @FechaFinal 
						  AND bc.bcaActividad IN ('ACTIVACION_LIQUIDACION', 'ACTIVACION_REESTRUCTURACION')
						GROUP BY bc.bcaActividad,bc.bcaFecha
						ORDER BY bc.bcaFecha DESC
					  ), '1') AS [Clasificación del estado del aportante],

					  CASE WHEN em.empMotivoDesafiliacion IN('EXPULSION_POR_MOROSIDAD','CERO_TRABAJADORES_NOVEDAD_INTERNA','MULTIAFILIACION') AND em.empEstadoEmpleador = 'INACTIVO'
					       THEN 1
					       WHEN em.empMotivoDesafiliacion IS NULL AND em.empEstadoEmpleador <> 'INACTIVO'
					       THEN 2
					       ELSE 3 END AS [Aportantes expulsados],
						CASE WHEN  c.cadDeudaReal  >0 THEN 0 ELSE 
						--replace(cd.cadDeudaPresunta,'.00000','')  END AS [Valor Deuda Presunta],
						CONVERT(NUMERIC(15,0),c.cadDeudaPresunta)  END AS [Valor Deuda Presunta],
					  --  replace(cd.cadDeudaReal,'.00000','') AS [Valor Deuda Real],
						CONVERT(NUMERIC(15,0),c.cadDeudaReal)   AS [Valor Deuda Real],
				        CASE WHEN c.cadDeudaReal >0 THEN '' ELSE 'X' END AS [Marcación "X" si tiene Deuda presunta],
					  CASE WHEN c.cadDeudaReal >0 THEN 'X' ELSE '' END  AS [Marcación "X" si tiene Deuda Real],
					  cast (getdate() as date) AS [Fecha generación de reporte],---???
						----ca.cagNumeroOperacion AS 'Número de operación',
					 REPLACE( (copDeudaPresuntaRegistrada    +  copDeudaRealRegistrada   ) ,'.00000','') AS [Valor Convenio Pago],
					CAST(copFechaRegistro as date)    AS [Fecha de Convenio de pago],
					CAST(ppcFechaPago as date)   AS [Fecha fin Convenio de pago],
					cast(c.carFechaCreacion as date) AS [Fecha de ingreso a cartera],
					cast(isnull(c.carDeudaParcial,0) as numeric(19,2))   AS [Deuda parcial o total]
					,@FechaInicial AS FechaInicial, @FechaFinal AS FechaFinal 
					--	c.carMetodo AS	'Tipo de método' 
				  FROM #AudCartera c WITH(NOLOCK)
						INNER JOIN carteraagrupadora ca WITH(NOLOCK) ON ca.cagCartera = carid 			 
						INNER JOIN Persona pe WITH(NOLOCK) ON c.carpersona = pe.perid 
						INNER JOIN Empresa e WITH(NOLOCK) ON pe.perid = e.empPersona
						INNER JOIN Empleador em WITH(NOLOCK) ON e.empid = em.empEmpresa
						 LEFT JOIN ( SELECT SUM(cadDeudaPresunta) AS cadDeudaPresunta, SUM(cadDeudaReal) AS cadDeudaReal, cadCartera
									   FROM carteradependiente WITH(NOLOCK)
									  WHERE cadEstadoOperacion ='VIGENTE'
								   GROUP BY cadCartera) cd 
								ON c.carid = cd.cadCartera
						LEFT JOIN  ( SELECT DISTINCT  CASE WHEN bcaid  = MAX(bcaid)  
										OVER (PARTITION BY bcaPersona  ) 
										THEN bcaPersona ELSE NULL END AS bcaPersona  , 
											bcaActividad, bcaFecha
										FROM BitacoraCartera AS bc  
										WHERE bc.bcaactividad IN (
										'A1','A2','FIRMEZA_TITULO_EJECUTIVO','GENERAR_LIQUIDACION',
										-- LC1 y C6 MÉTODO 1
										'B1','C1','D1','E1','F1',
										-- LC1 y C6 MÉTODO 2
										'B2','C2','D2','E2','F2','G2','H2',
										-- LC1 MÉTODO 1 transitoria
										'AB1','BC1','CD1','DE1','EF1', 
										-- LC1 MÉTODO 2 transitoria
										'AB2','BC2','CD2','DE2','EF2','FG2','GH2','H2')
										) bc 
						 ON bc.bcaPersona = c.carPersona
						 AND bcaPersona IS NOT NULL
		
						 LEFT JOIN ConvenioPago  WITH(NOLOCK) 
						 LEFT JOIN PagoPeriodoConvenio  WITH(NOLOCK)  ON ppcConvenioPago = copId
								ON copPersona = c.carPersona 
							   AND ppcPeriodo = c.carPeriodoDeuda
							   AND copFechaRegistro BETWEEN @FechaInicial AND @FechaFinal
							   AND copEstadoConvenioPago = 'ACTIVO'
			     WHERE c.carEstadoCartera = 'MOROSO'  
				   AND c.carid NOT IN (SELECT carid FROM cartera WHERE carEstadoCartera = 'AL_DIA')
				   AND c.carTipoLineaCobro IN ('LC1','C6')
				   AND c.carTipoAccionCobro NOT IN ('A1', 'AB1', 'A2', 'AB2','A01','A02')
				   AND cd.cadDeudaPresunta>0
				 
				   AND (CONVERT(DATE,c.carFechaAsignacionAccion)<=  @FechaFinal 
				   OR CONVERT(DATE,c.carFechaAsignacionAccion) IS NULL  
   				 OR copFechaRegistro BETWEEN @fechainicial AND @FechaFinal )
				   
			  
			 	 
				 SELECT DISTINCT
							left ([Periodo reporte],7) as [Periodo reporte],
							[Cód. Administradora],
							[Tipo de documento del aportante],
							[Número de documento del aportante],
							[Nombre o razón social del aportante],
							[Digito de verificación],
							left([Periodo mora],7) as [Periodo mora],
							replace(convert(varchar,[Valor de la cartera]),'.00000','')  as [Valor de la cartera],
							[Última acción de cobro],
							replace ([Fecha última acción de cobro],'1900-01-01','') as [Fecha última acción de cobro],
							[Clasificacion del estado del aportante],
							[Aportantes expulsados],
							replace([Valor Deuda Presunta],'.00000','')  as [Valor Deuda Presunta],
							replace([Valor Deuda Real],'.00000','')  as [Valor Deuda Real],
							[Marcación "X" si tiene Deuda presunta],
							[Marcación "X" si tiene Deuda Real],
							[Fecha generación de reporte],
							REPLACE( [Valor Convenio Pago] ,'.00000','') AS [Valor Convenio Pago],
							[Fecha de Convenio de pago] as [Fecha de Convenio de pago],
							[Fecha fin Convenio de pago],
							convert(varchar,[Fecha de ingreso a cartera],23) as [Fecha de ingreso a cartera] ,
							[Deuda parcial o total]							
					 FROM    rno.HistoricoCierreCartera
					WHERE [FechaInicial] = @FechaInicial
					  AND [FechaFinal]= @FechaFinal
 
END