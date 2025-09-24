/****** Object:  StoredProcedure [dbo].[ASP_ReporteNovedades]    Script Date: 16/07/2024 9:00:48 a. m. ******/
/****** Object:  StoredProcedure [dbo].[ASP_ReporteNovedades]    Script Date: 2024-07-11 7:55:01 AM ******/
/****** Object:  StoredProcedure [dbo].[ASP_ReporteNovedades]    Script Date: 2024-07-10 8:13:59 AM ******/
/****** Object:  StoredProcedure [dbo].[ASP_ReporteNovedades]    Script Date: 2023-09-19 9:59:18 AM ******/
/****** Object:  StoredProcedure [dbo].[ASP_ReporteNovedades]    Script Date: 2023-07-17 9:41:10 AM ******/
/*----Creado por : Olga Vega
Fecha de creación: 2022-01-05
Objetivo: Generar el archivo de para Corbanco de todos los empleados y sus las empresas principales que han sufrido alguna novedad de estado
o cambio de salario o de empresa o reingreso de todos los días
CORRECCION O DEVOLUCIONES
*/
CREATE OR ALTER PROCEDURE [dbo].[ASP_ReporteNovedades] AS 
BEGIN
      SET NOCOUNT ON ---agregado para evitar que conjuntos de resultados adicionales interfieran con las sentencias SELECT.CONFIGURAR SIN CUENTA EN;


	  ----ENCONTRAR LA FECHA DESDE LA CUAL DEBE REVISAR 
	  DECLARE  @fechainicio DATETIME
	  SELECT @fechainicio =  MAX(Fechaprocesamiento) FROM CtrlCorbanco 

-----HISTORICO PARA LOS DATOS ANTERIORES 

					DECLARE @aux_aud table (rev BIGINT ,roaid bigint,estado varchar(8),revTimeStamp bigint,roasucursalempleador bigint, roaempleador bigint ,roaafiliado bigint,s varchar(500))
					INSERT @aux_aud(rev,roaid , estado ,revTimeStamp,roasucursalempleador,roaempleador,roaafiliado, s)
					EXEC sp_execute_remote CoreAudReferenceData, N'  
																	  SELECT CASE WHEN x.rev = MAX(x.rev) OVER (PARTITION BY x.roaid) THEN x.rev ELSE NULL END AS revx,  
																			 CASE WHEN x.rev = MAX(x.rev) OVER (PARTITION BY x.roaid) THEN x.roaId ELSE NULL END AS roaidx, 
																			 x.roaestadoafiliado ,z.revTimeStamp,x.roasucursalempleador, x.roaempleador,x.roaafiliado
																		FROM rolafiliado_aud X WITH(NOLOCK)
																  INNER JOIN (SELECT MAX(r.rev) as rev , r.roaid ,re.revTimeStamp
																				 FROM RolAfiliado_aud r  
																			 INNER JOIN revision re on r.rev = re.revid
																			 GROUP BY roaid, revTimeStamp) z
																		   ON X.roaId = z.roaId
																	      AND x.REV < z.rev														   
																 '
   
	 	DELETE HistAfilAnt
		INSERT INTO HistAfilAnt
        SELECT rev ,roaid  ,estado, dateadd(second, revTimeStamp / 1000, '19700101') AT TIME ZONE 'UTC' AT TIME ZONE 'SA Pacific Standard Time' as fecha , roasucursalempleador as sucursalant, em.empEmpresa as empresaant, roaafiliado
---	into HistAfilAnt
		FROM @aux_aud  
	INNER JOIN empleador em 
		      ON em.empid = roaempleador
		--	 AND roaid <> 323060

----CREATE CLUSTERED INDEX IX_HistAfilAnt_REVROAID ON HistAfilAnt (REV,ROAID)
----alter table repnovedades add SucursalEmpleadorAnt varchar(10)
--CREATE CLUSTERED INDEX IX_HistRepNov_REVROAID ON RepNovedades (roaAfiliado,NumeroidentificacionAfiliado,NumeroidentificacionEmpresa,EstadoAfiliado)


-----PREPARAR LOS DATOS ENCONTRANDO LAS EMPRESAS PRINCIPALES 
-----LAS NOVEDADES EN UN LAPSO DE TIEMPO Y CIERTAS NOVEDADES 
-----AFILIACIONES EN CIERTO LAPSO DE TIEMPO 
 

 --CREATE TABLE EmpPpal (roaId BIGINT NOT NULL, roaEmpleador BIGINT NOT NULL)
 ----alter table EmpPpal add sueldo numeric
 ----alter table EmpPpal drop column sueldo
 ---CREATE UNIQUE INDEX INDX_empresappal_roaid_roaempleados ON EmpPpal (roaId , roaEmpleador)
TRUNCATE TABLE EmpPpal
INSERT INTO EmpPpal
SELECT roaId , roaEmpleador,0
					FROM (
							SELECT * FROM (
									SELECT *
									FROM	(
														SELECT CASE WHEN r.roaFechaAfiliacion = MIN(r.roaFechaAfiliacion) 
																OVER (PARTITION BY r.roaafiliado  
																ORDER BY r.roaFechaAfiliacion desc) THEN r.roaFechaAfiliacion ELSE NULL END AS  FechaAfiliacion,  
				   												r.roaId,r.roaFechaAfiliacion, r.roaEmpleador   ,
														 		ROW_NUMBER() OVER(PARTITION BY r.roaafiliado  ORDER BY r.roaFechaAfiliacion ASC) AS 'Orden'
														FROM RolAfiliado  r WITH(NOLOCK)
														WHERE r.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
															AND roaEstadoAfiliado = 'ACTIVO'
														) X 
												WHERE    X.Orden = 1

									UNION
									SELECT * FROM (
												SELECT CASE WHEN r.roaFechaRetiro = MAX(r.roaFechaRetiro) 
													OVER (PARTITION BY r.roaafiliado ) THEN CONVERT(DATE,r.roaFechaRetiro) ELSE NULL END AS  FechaRetiro,  
													r.roaId,r.roaFechaAfiliacion,	r.roaEmpleador  ,
												 	ROW_NUMBER() OVER(PARTITION BY r.roaafiliado  ORDER BY r.roaFechaRetiro desc) AS 'Orden'
												FROM RolAfiliado  r WITH(NOLOCK)
										LEFT JOIN RolAfiliado rr WITH(NOLOCK) ON r.roaAfiliado = rr.roaAfiliado AND rr.roaEstadoAfiliado = 'ACTIVO'
											WHERE r.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
												AND r.roaEstadoAfiliado = 'INACTIVO'
												---AND rr.roaAfiliado  IS NULL
												) Y WHERE  Y.Orden = 1

										) PPAL
						WHERE PPAL.FechaAfiliacion IS NOT NULL) AS principal
					 
 --ORDER BY ROAAFILIA ASC--281157

 ---**Adicionar solo actualización no borrado completo/////**----???? pensando cual el la mejor forma
 
   

 ---***ÚLTIMO SUELDO ULTIMO PERIODO ENCONTRADO si no hay aportes se coloca el del formulario, más adelante si tiene correccion se actualizara al de la corrección***---

				UPDATE EmpPpal 
				   SET EmpPpal.sueldo = CASE WHEN  isnull(Ultsal.apdSalarioBasico,0)  = 0 THEN  Ultsal.roaValorSalarioMesadaIngresos ELSE Ultsal.apdSalarioBasico END
				 ----select sueldo , Ultsal.apdSalarioBasico, roaValorSalarioMesadaIngresos
				  FROM EmpPpal
				 INNER JOIN 
							(		 SELECT CASE WHEN apgPeriodoAporte = MAX(apgPeriodoAporte) 
											OVER (PARTITION BY roaid , roaEmpleador) 
											THEN apgPeriodoAporte ELSE NULL END AS apgPeriodoAporte,
											roaid, roaEmpleador, apdSalarioBasico, roaValorSalarioMesadaIngresos
 									  FROM Afiliado WITH(NOLOCK)
								INNER JOIN RolAfiliado WITH(NOLOCK) ON roaAfiliado = afiid 
								INNER JOIN Empleador em WITH(NOLOCK) ON em.empid = roaEmpleador
								INNER JOIN empresa e WITH(NOLOCK) ON e.empId = em.empEmpresa
								 ---LEFT JOIN UltimoSueldoTrab  WITH(NOLOCK)  ON dsaEmpleador = roaEmpleador AND   afiPersona = apdpersona 
								INNER JOIN persona pt ON pt.perId = afipersona
								INNER JOIN AporteDetallado ON apdPersona = pt.perId
								INNER JOIN AporteGeneral ON apgId = apdAporteGeneral and apgEmpresa = e.empid 
							  ---	where  roaId  in (538769,524809)
								) AS Ultsal
					 ON EmpPpal.roaEmpleador= Ultsal.roaEmpleador and EmpPpal.roaId = Ultsal.roaId
				 	WHERE Ultsal.apgPeriodoAporte IS NOT NULL 
 
-- select * from EmpPpal where sueldo= 0   ---39866 trabajadores sin aportes 
 
				   UPDATE ep SET sueldo = ISNULL(roaValorSalarioMesadaIngresos,0)
					 FROM EmpPpal ep  WITH(NOLOCK)
			   INNER JOIN Rolafiliado r  WITH(NOLOCK)
					   ON ep.roaEmpleador= r.roaEmpleador and ep.roaid = r.roaId
					WHERE (sueldo = 0 OR sueldo is null)

 
  ---DROP TABLE AfiliacionesCorbanco
 --CREATE TABLE AfiliacionesCorbanco (FechaAfiliacion	DATETIME,perid	BIGINT, Soltipotransaccion VARCHAR(100),
	--								sapRolAfiliado	BIGINT,	roafechaafiliacion DATETIME,
	--								FechaRadicacion DATETIME ,	fechacreacion DATE ,TipoTransaccion VARCHAR(100),	Orden INT )

	
--	alter table AfiliacionesCorbanco add   roaEmpleador bigint ---solNumeroRadicacion nvarchar(20) 
TRUNCATE TABLE AfiliacionesCorbanco
 INSERT INTO AfiliacionesCorbanco

				SELECT * FROM (
									SELECT CASE WHEN r.roaFechaAfiliacion = MAX(r.roaFechaAfiliacion) 
								             OVER (PARTITION BY r.roaafiliado ) THEN r.roaFechaAfiliacion ELSE NULL END AS  FechaAfiliacion, 
											 perid, Soltipotransaccion, sapRolAfiliado , roafechaafiliacion ,
											 solFechaRadicacion as FechaRadicacion, solfechacreacion as fechacreacion , 
											 Soltipotransaccion as TipoTransaccion,
											  ROW_NUMBER() OVER(PARTITION BY r.roaafiliado  ORDER BY r.roaid desc) AS 'Orden', solNumeroRadicacion, r.roaEmpleador
 										FROM Solicitud  WITH(NOLOCK)
								  INNER JOIN SolicitudAfiliacionPersona  WITH(NOLOCK) ON sapSolicitudGlobal = solId  
								  INNER JOIN RolAfiliado r WITH(NOLOCK) ON roaid = sapRolAfiliado
								  INNER JOIN Afiliado  WITH(NOLOCK) ON roaAfiliado  = afiId
								  INNER JOIN persona  WITH(NOLOCK) ON afiPersona =  perId 	
								  INNER JOIN EmpPpal  WITH(NOLOCK) 
								          ON EmpPpal.roaEmpleador = r.roaEmpleador AND EmpPpal.roaId = r.roaId
								       WHERE solFechaRadicacion > =@fechainicio/*GETDATE()-1*/ AND  solFechaRadicacion <= GETDATE()
									     AND sapEstadoSolicitud = 'CERRADA'
									    
									 ) AFIL
						WHERE AFIL.roaFechaAfiliacion IS NOT NULL
						AND   AFIL.solNumeroRadicacion NOT IN (SELECT NumeroRadicacion FROM CtrlCorbanco)




					 
 ---DROP TABLE NovedadesCorbanco
 --CREATE TABLE NovedadesCorbanco (solFechaRadicacion DATETIME,	perid BIGINT ,	Soltipotransaccion VARCHAR(100),	
	--								snpRolAfiliado BIGINT,	fechaFIN DATETIME ,	fechaini DATETIME,	FechaRadicacion DATETIME ,	
	--								fechacreacion DATE ,	TipoTransaccion VARCHAR(100))

	---alter table NovedadesCorbanco add SalarioCorreccion numeric
	---alter table NovedadesCorbanco add solNumeroRadicacion nvarchar(20) 
		 TRUNCATE TABLE NovedadesCorbanco
		 INSERT INTO NovedadesCorbanco
		 SELECT * FROM (
						SELECT * FROM (
							SELECT CASE WHEN solFechaRadicacion = MAX(solFechaRadicacion) 
									OVER (PARTITION BY perid ) 
									THEN solFechaRadicacion ELSE NULL END AS FechaRadicacion,
									perid,  Soltipotransaccion as TipoTransaccion, snpRolAfiliado , nopFechaFin as fechaFIN, 
									nopFechaInicio as fechaini, 
									solfechacreacion as fechacreacion ,
									0 AS apdSalarioBasico, solNumeroRadicacion
 			 				  FROM Solicitud  WITH(NOLOCK)
						INNER JOIN solicitudnovedad  WITH(NOLOCK) ON solid = snoSolicitudGlobal
						INNER JOIN solicitudnovedadpersona  WITH(NOLOCK) ON snpSolicitudNovedad = snoid 
						 LEFT JOIN NovedadDetalle  WITH(NOLOCK) ON nopSolicitudNovedad = solId
						INNER JOIN persona  WITH(NOLOCK) ON perid = snppersona 											
							 WHERE Soltipotransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE',
														  'VARIACION_PERMANENTE_SALARIO_VSP_INDEPENDIENTE_PRESENCIAL',
														  'RETIRO_PENSIONADO_MENOR_1_5SM_2',
														  'VARIACION_PERMANENTE_SALARIO_VSP_DEPENDIENTE_PRESENCIAL',
													---	  'NOVEDAD_REINTEGRO',
														  'RETIRO_TRABAJADOR_INDEPENDIENTE',
														  'ACTUALIZAR_VALOR_TIPO_SALARIO_CONTRATO_TRABAJADOR_DEPENDIENTE_PRESENCIAL'
														  )
								AND solResultadoProceso = 'APROBADA'
								 ) X WHERE FechaRadicacion IS NOT NULL
										 
							UNION

							SELECT CASE WHEN solFechaRadicacion = MAX(solFechaRadicacion) 
									OVER (PARTITION BY perid ) 
									THEN solFechaRadicacion ELSE NULL END AS FechaRadicacion,perId,
										 Soltipotransaccion as TipoTransaccion, roaid  , NULL as FechafIN, NULL as Fechaini, 
										 solfechacreacion as fechacreacion , 
									     adc.apdSalarioBasico, solNumeroRadicacion
 							  FROM Solicitud  WITH(NOLOCK)
						INNER JOIN SolicitudCorreccionAporte  WITH(NOLOCK) ON solid = scaSolicitudGlobal
						INNER JOIN AporteGeneral ag  WITH(NOLOCK) ON ag.apgId = scaAporteGeneral  
						INNER JOIN Empleador em  WITH(NOLOCK) ON em.empEmpresa = ag.apgEmpresa
						INNER JOIN AporteDetallado apd  WITH(NOLOCK) ON apd.apdAporteGeneral =  apgid 
						INNER JOIN persona  WITH(NOLOCK) ON perid = apdPersona
						INNER JOIN Afiliado  WITH(NOLOCK) ON perid = afipersona 
						INNER JOIN RolAfiliado  WITH(NOLOCK) ON roaAfiliado = afiId and roaEmpleador = em.empid 
						INNER JOIN Correccion   WITH(NOLOCK) ON corAporteDetallado = apd.apdid
						INNER JOIN AporteGeneral agc  WITH(NOLOCK) ON agc.apgId = corAporteGeneral 
						INNER JOIN AporteDetallado adc  WITH(NOLOCK) ON adc.apdAporteGeneral =  agc.apgid 
								WHERE Soltipotransaccion IN ('CORRECCION_APORTES'  ---,
		 												--	'ACTUALIZAR_VALOR_TIPO_SALARIO_CONTRATO_TRABAJADOR_DEPENDIENTE_PRESENCIAL'
															)
													---	  	AND perNumeroIdentificacion = '1059812868'
								  AND scaResultadoSupervisor = 'APROBADA'  
								---  AND apdEstadoAporteAjuste <>'ANULADO'--- 'CORREGIDO' ---'VIGENTE'
															)NOVE
		WHERE NOVE.FechaRadicacion IS NOT NULL
		  AND NOVE.FechaRadicacion  >= @fechainicio/*GETDATE()-1*/ AND NOVE.FechaRadicacion  <= GETDATE()
		  AND  NOVE.solNumeroRadicacion NOT IN (SELECT NumeroRadicacion FROM CtrlCorbanco)

---**novedades por devolucion ---**---20220602

UNION 


						SELECT CASE WHEN solFechaRadicacion = MAX(solFechaRadicacion) 
									OVER (PARTITION BY perid ) 
									THEN solFechaRadicacion ELSE NULL END AS FechaRadicacion,perId,
										 Soltipotransaccion as TipoTransaccion, roaid  , NULL as FechafIN, NULL as Fechaini, 
										 solfechacreacion as fechacreacion , 
									     apdSalarioBasico, solNumeroRadicacion
 						       FROM Solicitud  WITH(NOLOCK)
						 INNER JOIN SolicitudDevolucionAporte  WITH(NOLOCK) ON solid = sdaSolicitudGlobal
						 INNER JOIN Empresa e WITH(NOLOCK) ON empPersona = sdaPersona
						 INNER JOIN Empleador em WITH(NOLOCK) ON em.empEmpresa = e.empid 
						 INNER JOIN DevolucionAporte WITH(NOLOCK) ON sdaDevolucionAporte = dapId
						 INNER JOIN DevolucionAporteDetalle WITH(NOLOCK) ON dadDevolucionAporte = dapId
						 INNER JOIN MovimientoAporte  WITH(NOLOCK) ON dadMovimientoAporte = moaid 
						 INNER JOIN AporteDetallado   WITH(NOLOCK)
						         ON apdid =  moaAporteDetallado AND moaAporteGeneral = apdAporteGeneral
						 INNER JOIN AporteGeneral 
						         ON apgId =  apdAporteGeneral AND moaAporteGeneral= apgId
			 		     INNER JOIN persona  WITH(NOLOCK) ON perid = apdPersona
				  	     INNER JOIN Afiliado  WITH(NOLOCK) ON perid = afipersona 
				 		 INNER JOIN RolAfiliado  WITH(NOLOCK) ON roaAfiliado = afiId  and roaEmpleador = em.empId 
								WHERE Soltipotransaccion IN ('DEVOLUCION_APORTES' )
								  AND solFechaRadicacion IS NOT NULL
								  AND solFechaRadicacion  >= @fechainicio/*GETDATE()-1*/  AND solFechaRadicacion  <= GETDATE()
								  AND solNumeroRadicacion NOT IN (SELECT NumeroRadicacion FROM CtrlCorbanco)




		   						----***NOVEDADES POR APORTES CAMBIOD E SALARIO****--

	---
	declare @perreg varchar(7) 
	set @perreg =  CONVERT(VARCHAR,YEAR(GETDATE()))+'-'+REPLICATE('0', 2-len(month(getdate()))) +convert(varchar,(month(getdate())-1))

INSERT INTO NovedadesCorbanco
SELECT  NovRec.*
 FROM (
					SELECT SueldoAct.apgFechaProcesamiento,SueldoAct.apdPersona, 
							'NOVEDAD_CAMBIO_SUELDO_POR_APORTE' AS tipotransaccion , SueldoAct.roaId,
							NULL as fechaFIN, NULL as fechaini, CONVERT(DATE,SueldoAct.apgFechaProcesamiento) AS fechacreacion, SueldoAct.apdSalarioBasico AS SalarioCorreccion,sueldoact.apgId
					FROM 

					(
								SELECT * 
								FROM (
										SELECT DISTINCT apgId ,apgPeriodoAporte, apdpersona ,roaEmpleador, roaId,apdSalarioBasico  , sueldo  ,apgFechaProcesamiento,
											ROW_NUMBER() OVER(PARTITION BY  apdpersona  ORDER BY x.apgPeriodoAporte desc) AS 'Orden'
										FROM (
											SELECT DISTINCT apgId ,apgPeriodoAporte, apdpersona ,epp.roaEmpleador, epp.roaId,apdSalarioBasico  , epp.sueldo,   apgFechaProcesamiento 
												FROM EmpPpal epp  WITH(NOLOCK)
										INNER JOIN RolAfiliado r  WITH(NOLOCK) ON r.roaId = epp.roaId AND r.roaEmpleador = epp.roaEmpleador
										INNER JOIN Afiliado a  WITH(NOLOCK) ON r.roaAfiliado = a.afiId
										INNER JOIN AporteDetallado ad  WITH(NOLOCK) ON a.afiPersona = ad.apdPersona
										INNER JOIN AporteGeneral ag  WITH(NOLOCK) ON ad.apdAporteGeneral = ag.apgid  
										INNER JOIN Empresa e  WITH(NOLOCK) ON  e.empId = ag.apgEmpresa
										INNER JOIN Empleador em  WITH(NOLOCK) ON em.empEmpresa = e.empId AND em.empid = r.roaEmpleador
												WHERE R.roaEstadoAfiliado = 'ACTIVO'
												  AND apgPeriodoAporte =@perreg
											) X
										) SueldoAct  WHERE SueldoAct.Orden = 1
		 
							)SueldoAct

	 
					INNER JOIN  
	
								(SELECT * 
									FROM (
												SELECT DISTINCT apgId ,apgPeriodoAporte, apdpersona ,roaEmpleador, roaId,apdSalarioBasico  , sueldo  ,apgFechaProcesamiento,
													ROW_NUMBER() OVER(PARTITION BY  apdpersona  ORDER BY x.apgPeriodoAporte desc) AS 'Orden'
												FROM (
													SELECT DISTINCT apgId ,apgPeriodoAporte, apdpersona ,epp.roaEmpleador, epp.roaId,apdSalarioBasico  , epp.sueldo  , apgFechaProcesamiento 
														FROM EmpPpal epp 
												INNER JOIN RolAfiliado r  WITH(NOLOCK) ON r.roaId = epp.roaId AND r.roaEmpleador = epp.roaEmpleador
												INNER JOIN Afiliado a  WITH(NOLOCK) ON r.roaAfiliado = a.afiId
												INNER JOIN AporteDetallado ad  WITH(NOLOCK) ON a.afiPersona = ad.apdPersona
												INNER JOIN AporteGeneral ag  WITH(NOLOCK) ON ad.apdAporteGeneral = ag.apgid  
												INNER JOIN Empresa e  WITH(NOLOCK) ON  e.empId = ag.apgEmpresa
												INNER JOIN Empleador em  WITH(NOLOCK) ON em.empEmpresa = e.empId AND em.empid = r.roaEmpleador
													WHERE R.roaEstadoAfiliado = 'ACTIVO'
														AND apgPeriodoAporte <= @perreg
										---			 WHERE ad.apdPersona = 729725 
													) X 
										)	X  WHERE X.Orden = 2 
									) SueldoAnt


											ON  SueldoAnt.roaEmpleador =  SueldoAct.roaempleador
											AND SueldoAnt.roaId = SueldoAct.roaid 
											AND SueldoAnt.apdSalarioBasico <> SueldoAct.apdSalarioBasico
									  WHERE SueldoAct.apgFechaProcesamiento  > GETDATE()-1
									    AND SueldoAct.apgFechaProcesamiento <= GETDATE()

) NovRec
LEFT JOIN NovedadesCorbanco nc 
       ON NovRec.apdPersona = nc.perid 
	  AND NovRec.roaId = nc.snpRolAfiliado 
WHERE nc.perid is null
AND   convert(nvarchar,apgId) NOT IN (SELECT NumeroRadicacion FROM CtrlCorbanco)


 	 

---- alter table RepNovedades add sueid bigint , sucursalprincipal varchar(1) 
 
--SELECT * FROM EmpPpal
--SELECT * FROM NovedadesCorbanco
--SELECT * FROM AfiliacionesCorbanco
---alter table RepNovedades add solNumeroRadicacion nvarchar(20) 
---INSERTAR LOS QUE TIENEN NOVEDADES 
 BEGIN TRY 

			 DELETE RepNovedades
		 	  INSERT  INTO  RepNovedades
  
					SELECT DISTINCT
									CASE WHEN ptr.pertipoidentificacion = 'CEDULA_CIUDADANIA'
										 THEN 'C'
										 WHEN ptr.pertipoidentificacion ='CEDULA_EXTRANJERIA'
										 THEN 'E'
										 WHEN ptr.pertipoidentificacion ='TARJETA_IDENTIDAD'
										 THEN 'T'
										 WHEN ptr.pertipoidentificacion ='PERM_ESP_PERMANENCIA'
										 THEN 'V'
										 WHEN ptr.pertipoidentificacion ='PASAPORTE'
										 THEN 'P'
										
										 ELSE 'M' END AS TipoidentificacionAfiliado, 
									ptr.pernumeroidentificacion as NumeroidentificacionAfiliado, 
									---CONCAT(ptr.perprimernombre,' ',  ptr.persegundonombre,' ', ptr.perPrimerApellido,' ', ptr.persegundoApellido) AS NombreAfiliado,
									CASE WHEN roaEstadoAfiliado = 'ACTIVO' THEN 'A' ELSE 'R' END as EstadoAfiliado,
									CASE WHEN pe.pertipoidentificacion = 'CEDULA_CIUDADANIA'
										 THEN 'C'
										 WHEN pe.pertipoidentificacion ='CEDULA_EXTRANJERIA'
										 THEN 'E'
										 WHEN pe.pertipoidentificacion ='TARJETA_IDENTIDAD'
										 THEN 'T'
										 WHEN pe.pertipoidentificacion ='PERM_ESP_PERMANENCIA'
										 THEN 'V'
										 WHEN pe.pertipoidentificacion ='PASAPORTE'
										 THEN 'P'
								
										 WHEN pe.pertipoidentificacion ='NIT'
										 THEN 'N'
										 ELSE 'M' END AS TipoidentificacionEmpresa, 
									--pe.pertipoidentificacion AS TipoIdentificacioEmpresa, 
									CONCAT( pe.pernumeroidentificacion , ISNULL(CONVERT(VARCHAR,pe.perDigitoVerificacion),'') ) AS NumeroidentificacionEmpresa,
									ISNULL(actualsuc.suecodigo ,sucdefault.sueCodigo) AS SucursalEmpleador, 
						
					 				CONVERT(DATE,FechaRadicacion)  AS FechaRadicacion, 
									CONVERT(TIME,FechaRadicacion) AS HoraRadicacion ,
									CASE WHEN tipotransaccion IN ( 'CORRECCION_APORTES' ,'DEVOLUCION_APORTES','NOVEDAD_CAMBIO_SUELDO_POR_APORTE')
									     THEN ---roaValorSalarioMesadaIngresos
										 NOVE.SalarioCorreccion 
										 ELSE principal.sueldo END
				 						---roaValorSalarioMesadaIngresos 
										 AS ValorSalarioMesadaIngresos , ra.roaAfiliado, NOVE.TipoTransaccion,
										 ISNULL(actualsuc.sueId ,sucdefault.sueId),
										 CASE WHEN ISNULL(actualsuc.sueSucursalPrincipal,sucdefault.sueSucursalPrincipal) = 1 THEN 'S' ELSE 'N' END AS SucursalPrincipal, nove.solNumeroRadicacion
							 
  ---INTO   RepNovedades select soltipotransaccion from NovedadesCorbanco group by soltipotransaccion
							   FROM persona pe  WITH(NOLOCK)
						 INNER JOIN Empresa e  WITH(NOLOCK) ON e.empPersona = pe.perId
						 INNER JOIN Empleador em  WITH(NOLOCK) ON em.empEmpresa = e.empId
						 INNER JOIN RolAfiliado ra WITH(NOLOCK) ON em.empid = ra.roaEmpleador
						 INNER JOIN Afiliado  WITH(NOLOCK) ON roaAfiliado = afiId
						 INNER JOIN persona ptr  WITH(NOLOCK) ON afipersona = ptr.perid 
						 inner JOIN NovedadesCorbanco NOVE WITH(NOLOCK)
								 ON NOVE.perid  = ptr.perId 
								AND NOVE.snpRolAfiliado = roaId
								AND NOVE.FechaRadicacion IS NOT NULL
						  LEFT JOIN HistAfilAnt  AS  antes 
								 ON antes.roaid = ra.roaId 
								AND antes.roaid IS NOT NULL
						 INNER JOIN EmpPpal AS principal
								 ON principal.roaId = ra.roaId 
								AND principal.roaEmpleador = EM.EMPID 
						  LEFT JOIN SucursalEmpresa  actualsuc
						         ON actualsuc.sueEmpresa = e.empId
								AND actualsuc.sueId = roaSucursalEmpleador
								-- ON actualsuc.sueId = roaSucursalEmpleador 
								--AND actualsuc.sueEmpresa = e.empid 
							--	AND actualsuc.sueSucursalPrincipal = 1----ADICION PARA SOLO PRINCIPAL 
 						  LEFT JOIN SucursalEmpresa  anteriorsuc
						        ON anteriorsuc.sueEmpresa = e.empId
								AND anteriorsuc.sueId = roaSucursalEmpleador
								-- ON anteriorsuc.sueId = antes.sucursalant
								--AND anteriorsuc.sueEmpresa = antes.empresaant  
							--	AND anteriorsuc.sueSucursalPrincipal = 1----ADICION PARA SOLO PRINCIPAL 
	 						  LEFT JOIN SucursalEmpresa  sucdefault
						        ON sucdefault.sueEmpresa = e.empId
								AND sucdefault.sueSucursalPrincipal = 1 AND sucdefault.sueEstadoSucursal= 'ACTIVO'
								 
							  WHERE NOVE.FechaRadicacion  >= @fechainicio/*GETDATE()-1*/
							    AND  NOVE.FechaRadicacion  <= GETDATE()
							    AND NOVE.solNumeroRadicacion NOT IN (SELECT NumeroRadicacion FROM CtrlCorbanco)
						 	 ---AND ptr.perNumeroIdentificacion = '1002855077'

 
	 -----INSERTAR LOS AFILIADOS NUEVOS

	   INSERT INTO RepNovedades
	 	SELECT DISTINCT
									CASE WHEN ptr.pertipoidentificacion = 'CEDULA_CIUDADANIA'
										 THEN 'C'
										 WHEN ptr.pertipoidentificacion ='CEDULA_EXTRANJERIA'
										 THEN 'E'
										 WHEN ptr.pertipoidentificacion ='TARJETA_IDENTIDAD'
										 THEN 'T'
										 WHEN ptr.pertipoidentificacion ='PERM_ESP_PERMANENCIA'
										 THEN 'V'
										 WHEN ptr.pertipoidentificacion ='PASAPORTE'
										 THEN 'P'
							
										 ELSE 'M' END AS TipoidentificacionAfiliado, 
									ptr.pernumeroidentificacion as NumeroidentificacionAfiliado, 
									---CONCAT(ptr.perprimernombre,' ',  ptr.persegundonombre,' ', ptr.perPrimerApellido,' ', ptr.persegundoApellido) AS NombreAfiliado,
									CASE WHEN roaEstadoAfiliado = 'ACTIVO' THEN 'A' ELSE 'R' END as EstadoAfiliado,
									CASE WHEN pe.pertipoidentificacion = 'CEDULA_CIUDADANIA'
										 THEN 'C'
										 WHEN pe.pertipoidentificacion ='CEDULA_EXTRANJERIA'
										 THEN 'E'
										 WHEN pe.pertipoidentificacion ='TARJETA_IDENTIDAD'
										 THEN 'T'
										 WHEN pe.pertipoidentificacion ='PERM_ESP_PERMANENCIA'
										 THEN 'V'
										 WHEN pe.pertipoidentificacion ='PASAPORTE'
										 THEN 'P'
				
										 WHEN pe.pertipoidentificacion ='NIT'
										 THEN 'N'
										 ELSE 'M' END AS TipoidentificacionEmpresa, 
									--pe.pertipoidentificacion AS TipoIdentificacioEmpresa, 
									CONCAT( pe.pernumeroidentificacion , ISNULL(CONVERT(VARCHAR,pe.perDigitoVerificacion),'') ) AS NumeroidentificacionEmpresa,
									ISNULL(actualsuc.suecodigo,sucdefault.suecodigo) AS SucursalEmpleador, 
						
					 				CONVERT(DATE,FechaRadicacion)  AS FechaRadicacion, 
									CONVERT(TIME,FechaRadicacion) AS HoraRadicacion ,
									isnull(sueldo,roaValorSalarioMesadaIngresos) AS ValorSalarioMesadaIngresos , ra.roaAfiliado, AFIL.solTipoTransaccion,
									isnull(actualsuc.sueId,sucdefault.sueid) as sueid ,
										 CASE WHEN isnull(actualsuc.sueSucursalPrincipal, sucdefault.sueSucursalPrincipal) = 1 THEN 'S' ELSE 'N' END AS SucursalPrincipal,AFIL.solNumeroRadicacion
							   FROM persona pe  WITH(NOLOCK)
						 INNER JOIN Empresa e  WITH(NOLOCK) ON e.empPersona = pe.perId
						 INNER JOIN Empleador em  WITH(NOLOCK) ON em.empEmpresa = e.empId
						 INNER JOIN RolAfiliado ra WITH(NOLOCK) ON em.empid = ra.roaEmpleador
						 INNER JOIN Afiliado  WITH(NOLOCK) ON roaAfiliado = afiId
						 INNER JOIN persona ptr  WITH(NOLOCK) ON afipersona = ptr.perid 
						 INNER JOIN AfiliacionesCorbanco AS  AFIL
								 ON AFIL.perid  = ptr.perId 
								AND AFIL.sapRolAfiliado = roaId
								AND AFIL.Orden = 1
						  LEFT JOIN HistAfilAnt  AS  antes 
								 ON antes.roaid = ra.roaId 
								AND antes.roaid IS NOT NULL
						 INNER JOIN EmpPpal AS principal
								ON principal.roaId = ra.roaId
						  LEFT JOIN SucursalEmpresa  actualsuc
						         ON actualsuc.sueEmpresa = e.empId
								AND actualsuc.sueId = roaSucursalEmpleador
 						  LEFT JOIN SucursalEmpresa  anteriorsuc
								 ON anteriorsuc.sueEmpresa = e.empId
								AND anteriorsuc.sueId = roaSucursalEmpleador
							 LEFT JOIN SucursalEmpresa  sucdefault
						        ON sucdefault.sueEmpresa = e.empId
								AND sucdefault.sueSucursalPrincipal = 1 AND sucdefault.sueEstadoSucursal= 'ACTIVO'
					---   WHERE  AFIL.FechaRadicacion  > GETDATE()-1 AND  AFIL.FechaRadicacion  <= GETDATE()
				--- 	 WHERE  ptr.perNumeroIdentificacion = '30397425'
				WHERE AFIL.FechaRadicacion  > @fechainicio/*GETDATE()-1*/
				 AND AFIL.FechaRadicacion  <= GETDATE()
				 AND AFIL.solNumeroRadicacion NOT IN (SELECT NumeroRadicacion FROM CtrlCorbanco)

 
  -----AFILIACIONES Y REINTEGROS ACTUAL
  --- DROP TABLE ARactual
 
 
  
  DELETE ARactual

  INSERT INTO ARactual
  SELECT   solid,  
			roaId  ,	 roaAfiliado,	 roaestadoafiliado ,  roaempleador, 
			perTipoIdentificacion, 
			CONCAT( pernumeroidentificacion , ISNULL(CONVERT(VARCHAR,perDigitoVerificacion),'') )
	AS		perNumeroIdentificacion, 
			perRazonSocial,  solNumeroRadicacion, solTipoTransaccion  
						--INTO ARactual 
  FROM (
  		 SELECT CASE WHEN x.solid = MAX(x.solid) OVER (PARTITION BY r.roaAfiliado, x.soltipotransaccion) THEN x.solid ELSE NULL END AS solid,  
						 r.roaId  ,	r.roaAfiliado,	r.roaestadoafiliado , r.roaempleador, 
						pe.perTipoIdentificacion, pe.perNumeroIdentificacion, PE.perDigitoVerificacion,
						pe.perRazonSocial, x.solNumeroRadicacion, x.solTipoTransaccion 
						
				FROM Solicitud  X WITH(NOLOCK)
		  INNER JOIN solicitudafiliacionpersona  WITH(NOLOCK) ON solid = sapSolicitudGlobal
		  INNER JOIN RolAfiliado  r WITH(NOLOCK) ON sapRolAfiliado = roaId
		  INNER JOIN Afiliado  WITH(NOLOCK) ON afiid = roaAfiliado
		  INNER JOIN persona   pa WITH(NOLOCK) ON pa.perId = afiPersona 	
		  INNER JOIN empleador  em WITH(NOLOCK) ON em.empId = roaEmpleador 
		  INNER JOIN empresa  e WITH(NOLOCK) ON e.empId = em.empEmpresa 	
		  INNER JOIN persona  pe  WITH(NOLOCK) ON pe.perId = e.empPersona 		
        --       where pa.perId = 170322
	 	--where pa.perNumeroIdentificacion = '1000000012'
		)X WHERE X.solid IS NOT NULL  
ORDER BY solid 
	
	  -----AFILIACIONES Y REINTEGROS ANTERIOR
	--- DROP TABLE ARanterior
	 DELETE ARanterior

  INSERT INTO ARanterior
	 SELECT *  -- INTO ARanterior
	  FROM ( 				  
				SELECT CASE WHEN x.solid = MAX(x.solid) OVER (PARTITION BY r.roaafiliado, x.soltipotransaccion) THEN x.solid ELSE NULL END AS solid,  
						r.roaId , r.roaAfiliado,
						r.roaestadoafiliado , r.roaempleador, 
						pe.perTipoIdentificacion,
						CONCAT( pe.pernumeroidentificacion , ISNULL(CONVERT(VARCHAR,pe.perDigitoVerificacion),'') )
						AS perNumeroIdentificacion, pe.perRazonSocial, 
						x.solNumeroRadicacion, x.solTipoTransaccion
				FROM Solicitud  X WITH(NOLOCK)
		  INNER JOIN solicitudafiliacionpersona  WITH(NOLOCK) ON solid = sapSolicitudGlobal
		  INNER JOIN RolAfiliado  r WITH(NOLOCK) ON sapRolAfiliado = roaId
		  INNER JOIN Afiliado  WITH(NOLOCK) ON afiid = roaAfiliado
		  INNER JOIN persona   pa WITH(NOLOCK) ON pa.perId = afiPersona 	
		  INNER JOIN empleador  em WITH(NOLOCK) ON em.empId = roaEmpleador 
		  INNER JOIN empresa  e WITH(NOLOCK) ON e.empId = em.empEmpresa 	
		  INNER JOIN persona  pe  WITH(NOLOCK) ON pe.perId = e.empPersona 		
		  INNER JOIN (SELECT MAX(x.solid) as solid , r.roaAfiliado ,
							ROW_NUMBER() OVER(PARTITION BY r.roaafiliado  ORDER BY r.roaid desc) AS 'Orden'
									FROM Solicitud  X WITH(NOLOCK)
								  INNER JOIN solicitudafiliacionpersona  WITH(NOLOCK) ON solid = sapSolicitudGlobal
								  INNER JOIN RolAfiliado  r WITH(NOLOCK) ON sapRolAfiliado = roaId
								  INNER JOIN Afiliado  WITH(NOLOCK) ON afiid = roaAfiliado	
							--	  where roaAfiliado = 332854
								GROUP BY r.roaAfiliado  , roaId
									
								) z
				   ON r.roaAfiliado = z.roaAfiliado AND z.Orden = 1
				  AND x.solid < z.solid	
		--		  where pa.perId = 170322
	---	where pa.perNumeroIdentificacion = '1007729149'
		)Z WHERE Z.solid IS NOT NULL
 



 ---**tabla de control*---
 ---delete CtrlCorbanco
--- CREATE TABLE CtrlCorbanco (numeroradicacion NVARCHAR(20), Fechaprocesamiento DATETIME)
INSERT INTO  CtrlCorbanco
SELECT DISTINCT solNumeroRadicacion, getdate() FROM RepNovedades 
WHERE solNumeroRadicacion NOT IN (SELECT numeroradicacion FROM CtrlCorbanco)

 --CREATE TABLE RepNovedades_Concat   (primary_key INT IDENTITY(1,1) NOT NULL,  
	--								   Dato NVARCHAR(MAX)) 
		   TRUNCATE TABLE RepNovedades_Concat;

	       INSERT INTO RepNovedades_Concat
			 SELECT CONCAT( 'DOCUMENTO',',',  'TIPO_DE_DOCUMENTO',',','ESTADO_ACTUAL',',',	'EMPRESA_ACTUAL_TIPO_DOCUMENTO',',', 'EMPRESA_ACTUAL_DOC',',',	
			 	    'SUCURSAL_ACTUAL',',',	'EMPRESA_ANTERIOR_DOC',',','FECHA_CAMBIO',',',
			 	    'HORA_CAMBIO',',',	'SALARIO_ACTUAL',',','IDSUCURSAL',',','SUCURSALPRINCIPAL')
			
	        INSERT INTO RepNovedades_Concat

		        SELECT DISTINCT CONCAT(NumeroidentificacionAfiliado,',',TipoidentificacionAfiliado,',',EstadoAfiliado, ',',TipoidentificacionEmpresa,',',NumeroidentificacionEmpresa,',',CONVERT(VARCHAR,SucursalEmpleador)
						,',', aant.perNumeroIdentificacion,',', CONVERT(VARCHAR,FechaRadicacion),',',CONVERT(VARCHAR,HoraRadicacion),','+CONVERT(VARCHAR, ValorSalarioMesadaIngresos),',',rn.sueid,',',rn.sucursalprincipal )
                  FROM RepNovedades rn with(nolock)
			 LEFT JOIN ARactual ar with(nolock) ON ar.roaafiliado= rn.roaafiliado 
			 LEFT JOIN ARanterior aant with(nolock) ON aant.roaafiliado= rn.roaafiliado 
			     WHERE rn.solNumeroRadicacion NOT IN (SELECT numeroradicacion FROM CtrlCorbanco WHERE Fechaprocesamiento<GETDATE()-1);

			SELECT dato FROM Repnovedades_concat    ORDER BY primary_key ASC;
 END TRY  

BEGIN CATCH
		SELECT  ERROR_NUMBER() AS ErrorNumber  ,ERROR_SEVERITY() AS ErrorSeverity  
				,ERROR_STATE() AS ErrorState      ,ERROR_PROCEDURE() AS ErrorProcedure  
				,ERROR_LINE() AS ErrorLine      ,ERROR_MESSAGE() AS ErrorMessage;  
 
END CATCH
END