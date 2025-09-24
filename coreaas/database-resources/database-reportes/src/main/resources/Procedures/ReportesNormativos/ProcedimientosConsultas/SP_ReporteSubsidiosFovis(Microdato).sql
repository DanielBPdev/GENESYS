-- =============================================
-- Author:      Miguel Angel Perilla
-- Last Update Date: 12 Julio 2022
-- Description: Procedimiento almacenado para obtener el resultado que hace referencia al reporte 16.
-- Reporte 16
-- =============================================
CREATE PROCEDURE [dbo].[SP_ReporteSubsidiosFovis]( @FECHA_INICIAL DATE, @FECHA_FINAL DATE )
AS

BEGIN 
SET NOCOUNT ON

--------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------
--/-----------------------------------------**********-----------------------------------------\--
-- REPORTE DE ASIGNACION, PAGO Y REINTEGRO DE SUBSIDIOS DE VIVIENDA – FOVIS MICRODATO -  N° 16.
--\-----------------------------------------**********-----------------------------------------/--
---------------------------------------------------------------------
---------------------------------------------------------------------
---------------------------------------------------------------------
---------------------------------------------------------------------

	DECLARE @fechaInicioRev BIGINT, @fechaFinRev BIGINT
	SET @fechaInicioRev = DATEDIFF_BIG (ms, '1969-12-31 19:00:00', @FECHA_INICIAL)
	SET @fechaFinRev = DATEDIFF_BIG (ms, '1969-12-31 19:00:00', @FECHA_FINAL)

	DECLARE @sql NVARCHAR(4000)
	SET @sql = 
	'
		SELECT 
			POFAUD.pofId,
			POFAUD.pofEstadoHogar,
			MAX( DATEADD(SECOND, revTimeStamp / 1000, ''19700101'') AT TIME ZONE ''UTC'' AT TIME ZONE ''SA Pacific Standard Time'' ) AS pofFechaCambio
		FROM 
			PostulacionFOVIS_aud POFAUD
			INNER JOIN Revision ON revId = REV 
		WHERE 
			revTimeStamp BETWEEN @fechaInicioRev AND @fechaFinRev AND
			POFAUD.pofEstadoHogar IN (
				''ASIGNADO_CON_SEGUNDA_PRORROGA'', 
				''ASIGNADO_CON_PRIMERA_PRORROGA'', 
				''ASIGNADO_SIN_PRORROGA'',
				''PENDIENTE_APROBACION_PRORROGA'', 
				''SUBSIDIO_LEGALIZADO'', 
				''RENUNCIO_A_SUBSIDIO_ASIGNADO'', 
				''VENCIMIENTO_POR_ASIGNACION_CON_SEGUNDA_PRORROGA_CADUCADA'',
				''VENCIMIENTO_POR_ASIGNACION_SIN_SEGUNDA_PRORROGA'', 
				''VENCIMIENTO_POR_ASIGNACION_SIN_PRORROGA'', 
				''RESTITUIDO_CON_SANCION'',
				''RESTITUIDO_SIN_SANCION'', 
				''SUBSIDIO_REEMBOLSADO'', 
				''SUBSIDIO_DESEMBOLSADO'', 
				''SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO''
			)
		GROUP BY 
			POFAUD.pofId,
			POFAUD.pofEstadoHogar
		ORDER BY 
			MAX( revTimeStamp )
	'

	IF OBJECT_ID('tempdb.dbo.#EstadosPostulacion', 'U') IS NOT NULL
		DROP TABLE #EstadosPostulacion; 

	CREATE TABLE #EstadosPostulacion(
		pofId VARCHAR(500), 
		pofEstadoHogar VARCHAR(500),
		pofFechaCambio VARCHAR(500),
		shard VARCHAR(500)
	)
 	
	INSERT INTO #EstadosPostulacion (
		pofId, 
		pofEstadoHogar, 
		pofFechaCambio, 
		shard
	)

	EXEC sp_execute_remote N'CoreAudReferenceData',
		@sql,
		N'@fechaInicioRev bigInt, @fechaFinRev bigInt',
		@fechaInicioRev = @fechaInicioRev, 
		@fechaFinRev = @fechaFinRev

---------------------------------------------------------------------
---------------------------------------------------------------------
---------------------------------------------------------------------
--------------------------------------------
--------------------    --------------------
--------------------------------------------
-------		
		----------------------
		----------  ----------
		----------------------
		----------------------
		----------  ----------
		----------------------
		---------------------------------------------------------------------
		----------------------TABLA TEMPORAL CON LA INFO DEL REPORTE
		---------------------------------------------------------------------
		IF OBJECT_ID('tempdb.dbo.#TABLE_DATA_SUBSIDIO_TEMP', 'U') IS NOT NULL
		  DROP TABLE #TABLE_DATA_SUBSIDIO_TEMP;

		SELECT
			---------- Tipo de Identificacion del Aportante ----------
			CASE consolidado.tipoIdentificacionJefe
				WHEN 'CEDULA_CIUDADANIA' THEN '1'
				WHEN 'TARJETA_IDENTIDAD' THEN '2'
				WHEN 'REGISTRO_CIVIL' THEN '3'
				WHEN 'CEDULA_EXTRANJERIA' THEN '4'		          
				WHEN 'PASAPORTE' THEN '6'
				WHEN 'NIT' THEN '7'
				WHEN 'CARNE_DIPLOMATICO' THEN '8'
				WHEN 'PERM_ESP_PERMANENCIA' THEN '9'

				ELSE consolidado.tipoIdentificacionJefe

			END AS [tipoIdentificacionJefe],	
			---------- Numero de Identificacion Aportante  ----------
			consolidado.numeroIdentificacion,		
			---------- Componente Hogar Objeto. ----------
			consolidado.componenteHogar,		
			----------  Tipo Identificacion Integrante del Hogar. ----------
			CASE
				WHEN consolidado.componenteHogar = '2'
				THEN 
					CASE consolidado.tipoIdIntegrante
						WHEN 'CEDULA_CIUDADANIA' THEN '1'
						WHEN 'TARJETA_IDENTIDAD' THEN '2'
						WHEN 'REGISTRO_CIVIL' THEN '3'
						WHEN 'CEDULA_EXTRANJERIA' THEN '4'		          
						WHEN 'PASAPORTE' THEN '6'
						WHEN 'NIT' THEN '7'
						WHEN 'CARNE_DIPLOMATICO' THEN '8' 
						WHEN 'PERM_ESP_PERMANENCIA' THEN '9'

						ELSE consolidado.tipoIdIntegrante

					END 
				ELSE
					CASE consolidado.tipoIdentificacionJefe
						WHEN 'CEDULA_CIUDADANIA' THEN '1'
						WHEN 'TARJETA_IDENTIDAD' THEN '2'
						WHEN 'REGISTRO_CIVIL' THEN '3'
						WHEN 'CEDULA_EXTRANJERIA' THEN '4'		          
						WHEN 'PASAPORTE' THEN '6'
						WHEN 'NIT' THEN '7'
						WHEN 'CARNE_DIPLOMATICO' THEN '8' 
						WHEN 'PERM_ESP_PERMANENCIA' THEN '9'

						ELSE consolidado.tipoIdentificacionJefe

					END
			END as [tipoIdIntegrante],	
			---------- Numero de Identificacion Integrante del Hogar. ----------
			CASE
				WHEN consolidado.componenteHogar = '2'
				THEN consolidado.numIdIntegrante
				ELSE 
					consolidado.numeroIdentificacion
			END as numIdIntegrante,	
			---------- Titular Subsidio ----------
			consolidado.afiliadoACaja,		
			---------- Primer Nombre de la persona reportada. ----------
			consolidado.primerNombre,	
			---------- Segundo Nombre de la persona reportada. ----------
			consolidado.segundoNombre,	
			---------- Primer Apellido de la persona reportada. ----------
			consolidado.primerApellido,	
			---------- Segundo Apellido de la persona reportada. ----------
			consolidado.segundoApellido,		
			---------- Parentezco con el Titular. ----------	
			consolidado.parentezcoIntegrante,	
			---------- Ingresos Integrante. ----------
			consolidado.ingresosIntegrante,		
			---------- Nivel de Ingreso. ----------
			consolidado.nivelIngreso,	
			---------- Componente. ----------
			consolidado.componente,	
			---------- A;o asignacion. ----------
			consolidado.anioVigencia,	
			---------- Estado Subsidio. ----------
			CASE
				WHEN consolidado.TIPOINTEGRANTE = 'PRINCIPAL'
				THEN consolidado.estadoSubsidio
				ELSE '9'
			END estadoSubsidio,	
			---------- Valor Subsidio. ----------
			CAST( MAX(consolidado.valorSubsidio) AS BIGINT ) AS [valorSubsidio],
			--CAST( SUM(consolidado.valorSubsidio) AS BIGINT ) AS [valorSubsidio],
			----------Codigo Tipo Plan de Vivienda----------
			consolidado.tipoPlanVivienda,
			CASE
				WHEN consolidado.TIPOINTEGRANTE NOT IN ( 'PRINCIPAL' )
				THEN consolidado.pofEstadoHogar
				ELSE '9'
			END pofEstadoHogar,
			pofId
			--pofJsonPostulacion
			

		INTO #TABLE_DATA_SUBSIDIO_TEMP	
		FROM (
	

--------------------------------------------------------------------------------------------------
-- INICIO CONSOLIDADO
			
		SELECT 
			PERJEFE.perTipoIdentificacion AS tipoIdentificacionJefe,
			PERJEFE.perNumeroIdentificacion AS numeroIdentificacion,

			CASE 
				WHEN (
					SELECT DISTINCT inh.inhJefeHogar
					FROM IntegranteHogar inh 
					WHERE 
						inh.inhEstadoHogar = 'ACTIVO' 
						AND inh.inhJefeHogar = JEHJEFE.jehId
						AND inh.inhPostulacionFovis = pof.pofId
					GROUP BY inh.inhJefeHogar
				) IS NULL 
				THEN 1 
				ELSE 2 
			END AS componenteHogar,
	
			PERINTE.perTipoIdentificacion AS tipoIdIntegrante,
			
			PERINTE.perNumeroIdentificacion AS numIdIntegrante,

			CASE 
				WHEN PERINTE.perNumeroIdentificacion = PERJEFE.perNumeroIdentificacion --OR integrante.numDocumentoIntegrante IS NULL
					THEN 1 
				ELSE 2 
			END AS afiliadoACaja,
			
			/*CASE 
				WHEN integrante.perPrimerNombre IS NULL THEN per.perPrimerNombre 
				ELSE integrante.perPrimerNombre
			END */ 
			PERINTE.perPrimerNombre AS primerNombre,
			
			/*CASE 
				WHEN integrante.perSegundoNombre IS NULL THEN per.perSegundoNombre
				ELSE integrante.perSegundoNombre
			END */
			PERINTE.perSegundoNombre AS segundoNombre,
			
			/*CASE 
				WHEN integrante.perPrimerApellido IS NULL THEN per.perPrimerApellido
				ELSE integrante.perPrimerApellido
			END*/
			PERINTE.perPrimerApellido AS primerApellido,
			
			/*CASE 
				WHEN integrante.perSegundoApellido IS NULL THEN per.perSegundoApellido
				ELSE integrante.perSegundoApellido
			END */
			PERINTE.perSegundoApellido AS segundoApellido,
			
			CASE 
				WHEN INTEGRANTES.TIPOINTEGRANTE IN ('HIJO_BIOLOGICO_HOGAR', 'HIJO_BIOLOGICO') THEN '1 '
				WHEN INTEGRANTES.TIPOINTEGRANTE IN (
					'PADRE_HOGAR',
					'MADRE_HOGAR',
					'PADRE_MADRE_ADOPTANTE_HOGAR', 
					'MADRE', 
					'PADRE') THEN '2'
				WHEN INTEGRANTES.TIPOINTEGRANTE = 'HERMANO_HOGAR' THEN '3'
				WHEN INTEGRANTES.TIPOINTEGRANTE = 'HIJASTRO_HOGAR' THEN '4'
				WHEN INTEGRANTES.TIPOINTEGRANTE IN ('CONYUGE', 'CONYUGE_HOGAR') THEN '5'
				WHEN INTEGRANTES.TIPOINTEGRANTE IN ('ABUELO', 'ABUELO_HOGAR', 'ABUELA', 'ABUELA_HOGAR') THEN '7'
				WHEN INTEGRANTES.TIPOINTEGRANTE IN ('NIETO', 'NIETO_HOGAR', 'NIETA', 'NIETA_HOGAR') THEN '8'
				WHEN INTEGRANTES.TIPOINTEGRANTE IN ('TIO', 'TIO_HOGAR', 'TIA', 'TIA_HOGAR') THEN '9'
				WHEN INTEGRANTES.TIPOINTEGRANTE IN ('SOBRINO', 'SOBRINA', 'SOBRINO_HOGAR', 'SOBRINA_HOGAR') THEN '10'
				WHEN INTEGRANTES.TIPOINTEGRANTE IN ('BISABUELO','BISABUELO_HOGAR', 'BISABUELA', 'BISABUELA_HOGAR') THEN '11'
				WHEN INTEGRANTES.TIPOINTEGRANTE IN ('BISNIETO','BISNIETO_HOGAR', 'BISNIETA', 'BISNIETA_HOGAR') THEN '12'
				WHEN INTEGRANTES.TIPOINTEGRANTE IN ('SUEGRO', 'SUEGRA', 'SUEGRO_HOGAR', 'SUEGRA_HOGAR') THEN '13'
				WHEN INTEGRANTES.TIPOINTEGRANTE IN ('CUÑADO', 'CUÑADA', 'CUÑADO_HOGAR', 'CUÑADA_HOGAR') THEN '14'
				WHEN INTEGRANTES.TIPOINTEGRANTE IN ('NUERA', 'NUERA_HOGAR') THEN '15'
				WHEN INTEGRANTES.TIPOINTEGRANTE IN ('YERNO', 'YERNO_HOGAR') THEN '16'
				WHEN INTEGRANTES.TIPOINTEGRANTE IN ('PADRE_ADOPTIVO') THEN '17'
				WHEN INTEGRANTES.TIPOINTEGRANTE IN ('HIJO_ADOPTIVO_HOGAR') THEN '18'
				WHEN INTEGRANTES.TIPOINTEGRANTE IN ('PRINCIPAL') THEN '19'
				ELSE  '6'
			END AS parentezcoIntegrante,
			

			/*CASE 
				WHEN integrante.jefeHogar IS NULL AND salario.ingresos IS NOT NULL
				THEN salario.ingresos 
				WHEN integrante.jefeHogar IS NOT NULL AND integrante.ingresosIntegrante IS NOT NULL
				THEN integrante.ingresosIntegrante 
				WHEN integrante.jefeHogar IS NULL
				THEN 0
				ELSE 0
			END*/
			CASE
				
				WHEN INTEGRANTES.TIPOINTEGRANTE IN ('PRINCIPAL')
				THEN 0
				ELSE ISNULL( INTEGRANTES.SALARIOINTEGRANTE, 0 ) 
			END AS ingresosIntegrante,

			
			CASE
				WHEN ROUND(CONVERT(FLOAT,salario.ingresos)/prm.prmValor,2) IS NULL OR INTEGRANTES.TIPOINTEGRANTE IN ('PRINCIPAL') --integrante.jefeHogar IS NULL
				THEN 1

				WHEN ROUND(CONVERT(FLOAT,salario.ingresos)/prm.prmValor,2) BETWEEN 0 AND 1
				THEN 2

				WHEN ROUND(CONVERT(FLOAT,salario.ingresos)/prm.prmValor,2) BETWEEN 1 AND 1.5
				THEN 3

				WHEN ROUND(CONVERT(FLOAT,salario.ingresos)/prm.prmValor,2) BETWEEN 1.5 AND 2
				THEN 4

				WHEN ROUND(CONVERT(FLOAT,salario.ingresos)/prm.prmValor,2) BETWEEN 2 AND 2.25
				THEN 5

				WHEN ROUND(CONVERT(FLOAT,salario.ingresos)/prm.prmValor,2) BETWEEN 2.25 AND 2.5
				THEN 6

				WHEN ROUND(CONVERT(FLOAT,salario.ingresos)/prm.prmValor,2) BETWEEN 2.5 AND 2.75
				THEN 7

				WHEN ROUND(CONVERT(FLOAT,salario.ingresos)/prm.prmValor,2) BETWEEN 2.75 AND 3
				THEN 8

				WHEN ROUND(CONVERT(FLOAT,salario.ingresos)/prm.prmValor,2) BETWEEN 3 AND 3.5
				THEN 9
					
				WHEN ROUND(CONVERT(FLOAT,salario.ingresos)/prm.prmValor,2) BETWEEN 3.5 AND 4
				THEN 10

				WHEN ROUND(CONVERT(FLOAT,salario.ingresos)/prm.prmValor,2) BETWEEN 0.0 AND 1.6
				THEN 11
					
				WHEN ROUND(CONVERT(FLOAT,salario.ingresos)/prm.prmValor,2) BETWEEN 1.6 AND 2
				THEN 12
					
				WHEN ROUND(CONVERT(FLOAT,salario.ingresos)/prm.prmValor,2) BETWEEN 2 AND 3
				THEN 13
					
				WHEN ROUND(CONVERT(FLOAT,salario.ingresos)/prm.prmValor,2) BETWEEN 3 AND 4
				THEN 14
					
				WHEN ROUND(CONVERT(FLOAT,salario.ingresos)/prm.prmValor,2) BETWEEN 0.0 AND 2
				THEN 15
					
				WHEN ROUND(CONVERT(FLOAT,salario.ingresos)/prm.prmValor,2) BETWEEN 2 AND 4
				THEN 16

				ELSE
					CASE 
						WHEN ROUND(CONVERT(FLOAT,salario.ingresos)/prm.prmValor,2) > 4
						THEN 10
					END

			END AS nivelIngreso,
		
			CASE 
				WHEN INTEGRANTES.TIPOINTEGRANTE IN ('PRINCIPAL')
				THEN
					CASE
						WHEN  pof.pofModalidad IN(
							'ADQUISICION_VIVIENDA_NUEVA_URBANA',
							'ADQUISICION_VIVIENDA_USADA_URBANA',
							'CONSTRUCCION_SITIO_PROPIO_URBANO',
							'MEJORAMIENTO_VIVIENDA_URBANA',
							'MEJORAMIENTO_VIVIENDA_SALUDABLE'
						) THEN 1

						WHEN  pof.pofModalidad IN(
							'ADQUISICION_VIVIENDA_NUEVA_RURAL',
							'ADQUISICION_VIVIENDA_USADA_RURAL',
							'CONSTRUCCION_SITIO_PROPIO_RURAL',
							'MEJORAMIENTO_VIVIENDA_RURAL'
						)THEN 2

						--WHEN integrante.numIdIntegrante = per.perNumeroIdentificacion
					END
				ELSE 4
			END AS componente,
	
			--ISNULL(YEAR(SAF.safFechaAceptacion), YEAR(@FECHA_INICIAL)) AS anioVigencia,
			ISNULL(YEAR(SAF.safFechaAceptacion), YEAR(@FECHA_INICIAL)) AS anioVigencia,
			--pof.pofestadoHogar,
			CASE 
				WHEN INTEGRANTES.TIPOINTEGRANTE IN ('PRINCIPAL')
				THEN (
					SELECT 

						MAX ( 
							CASE 
								----- asignacion ------
								WHEN 
									solcitudGlobalPostulacion.solTipoTransaccion = 'ASIGNACION_SUBSIDIO_FOVIS' OR
									POF2.pofEstadoHogar IN(
										'ASIGNADO_CON_SEGUNDA_PRORROGA', 
										'ASIGNADO_CON_PRIMERA_PRORROGA', 
										'ASIGNADO_SIN_PRORROGA',
										'RENUNCIO_A_SUBSIDIO_ASIGNADO',
										'SUBSIDIO_DESEMBOLSADO'
									) 
								
								THEN
									CASE 
										WHEN (pof2.pofValorAjusteIPCSFV IS NOT NULL AND pof2.pofValorAjusteIPCSFV <> 0)
											THEN pof2.pofValorAjusteIPCSFV
										ELSE pof2.pofValorAsignadoSFV
									END 

								----- reintegro ------
								WHEN 
									solcitudGlobalPostulacion.solTipoTransaccion IN(
										'NOVEDAD_REINTEGRO',
										'REEMBOLSO_VOLUNTARIO_SUBSIDIO',
										'DEVOLUCION_APORTES'
									)  OR
									POF2.pofEstadoHogar IN (
										'RESTITUIDO_CON_SANCION',
										'RESTITUIDO_SIN_SANCION'
									)
								THEN 
									CASE 
										WHEN (detalleNovedadFovis.dnfValorDiferenciaAjuste IS NOT NULL AND detalleNovedadFovis.dnfValorDiferenciaAjuste <> 0)
										THEN detalleNovedadFovis.dnfValorDiferenciaAjuste
										ELSE 
											CASE 
												WHEN detalleNovedadFovis.dnfValorTotalDiferencia IS NULL
												THEN pofValorSFVSolicitado
												ELSE detalleNovedadFovis.dnfValorTotalDiferencia
											END
									END
								ELSE 
									CASE 
										WHEN pof2.pofValorAsignadoSFV = 0
										THEN POF2.pofValorSFVSolicitado
										ELSE pof2.pofValorAsignadoSFV
									END
								------ DESEMBOLSOS ------
								--ELSE SUM(legalizacionDesembolso.lgdValorDesembolsar)

							END ) AS valorSubsidio
					
					
					FROM 

						PostulacionFOVIS pof2
					
						----------PARA OBTENER DESEMBOLSOS ----------
						LEFT JOIN SolicitudLegalizacionDesembolso solicitudLegalizacion 
							ON solicitudLegalizacion.sldPostulacionFOVIS = pof2.pofId

						--LEFT JOIN LegalizacionDesembolso legalizacionDesembolso 
							--ON legalizacionDesembolso.lgdId = solicitudLegalizacion.sldLegalizacionDesembolso
						
						----------PARA OBTENER REINTEGROS---------
						LEFT JOIN SolicitudNovedadPersonaFovis solicitudPersonaFOVIS
							ON solicitudPersonaFOVIS.spfPostulacionFovis = pof2.pofId

						LEFT JOIN SolicitudNovedadFovis solicitudFOVIS
							ON solicitudFOVIS.snfId = solicitudPersonaFOVIS.spfSolicitudNovedadFovis

						LEFT JOIN Solicitud solcitudGlobalPostulacion 
							ON solcitudGlobalPostulacion.solId = solicitudFOVIS.snfSolicitudGlobal

						----------VALOR REINTEGRO----------
						LEFT JOIN DetalleNovedadFovis AS detalleNovedadFovis
							ON detalleNovedadFovis.dnfSolicitudNovedad = solicitudFOVIS.snfSolicitudGlobal
						----------
						
					WHERE
						--pof2.pofId = 1939  --
						pof2.pofId = pof.pofId /*AND
						--solicitudLegalizacion.sldPostulacionFovis = pof2.pofId*/
					GROUP BY 
						--solcitudGlobalPostulacion.solTipoTransaccion,
						detalleNovedadFovis.dnfValorDiferenciaAjuste,
						detalleNovedadFovis.dnfValorTotalDiferencia,
						pof2.pofEstadoHogar
					
				)
				ELSE 0
			END AS valorSubsidio, 

			/*CASE 
				WHEN INTEGRANTES.TIPOINTEGRANTE IN ('PRINCIPAL')
				THEN*/
					CASE estado.valor 
						WHEN 2 THEN 5
						WHEN 3 THEN 6
						WHEN 4 THEN 6
						WHEN 5 THEN 2
						WHEN 6 THEN 3
						WHEN 7 THEN 4
						ELSE 1
					--END
				--ELSE 9
			END  AS estadoSubsidio,

			--estado.valor,

			CASE
				WHEN INTEGRANTES.TIPOINTEGRANTE IN ('PRINCIPAL')
				THEN
					CASE
						WHEN pof.pofModalidad IN (
							'ADQUISICION_VIVIENDA_NUEVA_URBANA', 					
							'ADQUISICION_VIVIENDA_NUEVA_RURAL'
						)THEN 1

						WHEN pof.pofModalidad IN (
							'ADQUISICION_VIVIENDA_USADA_URBANA', 					
							'ADQUISICION_VIVIENDA_USADA_RURAL'
						)THEN 2

						WHEN pof.pofModalidad IN (
							'CONSTRUCCION_SITIO_PROPIO_URBANO', 					
							'CONSTRUCCION_SITIO_PROPIO_RURAL'
						)THEN 3

						WHEN pof.pofModalidad IN (
							'MEJORAMIENTO_VIVIENDA_URBANA', 					
							'MEJORAMIENTO_VIVIENDA_RURAL'
						)THEN 4

						WHEN pof.pofModalidad IN (
							'MEJORAMIENTO_VIVIENDA_SALUDABLE'
						)THEN 7
					END
				ELSE 8
			END AS tipoPlanVivienda,

		pof.pofId,
		POFEST.pofEstadoHogar,
		INTEGRANTES.TIPOINTEGRANTE,
		SAF.safFechaAceptacion
		
		--------------------------------------
		FROM -- FROM PRINCIPAL DE LA CONSULTA
		-------------------------------------
			PostulacionFOVIS POF
			LEFT JOIN #EstadosPostulacion POFEST ON POFEST.pofId = POF.pofId
		
			LEFT JOIN Parametro PRM ON (PRM.prmNombre = 'SMMLV')
			LEFT JOIN CicloAsignacion CIA 
				ON (POF.pofCicloAsignacion = CIA.ciaId)
				AND CIA.ciaFechaFin BETWEEN @FECHA_INICIAL AND @FECHA_FINAL 
			LEFT JOIN SolicitudAsignacion SAF ON SAF.safId = pofSolicitudAsignacion
				--AND  > @FECHA_INICIO
			INNER JOIN SolicitudPostulacion SPO 
				ON SPO.spoPostulacionFOVIS = POF.pofId
			
			--INNER JOIN SolicitudNovedadPersonaFovis AS SPF ON SPF.spfPostulacionFovis = POF.pofId
			--LEFT JOIN SolicitudNovedadFovis AS SNF ON SNF.snfId = SPF.spfSolicitudNovedadFovis
				
			INNER JOIN Solicitud SOL 
				ON SPO.spoSolicitudGlobal = SOL.solId
				--OR SNF.snfSolicitudGlobal = SOL.solId
		
			
				
			INNER JOIN JefeHogar JEHJEFE ON (POF.pofJefeHogar = JEHJEFE.jehId)
			INNER JOIN Afiliado AFIJEFE ON (JEHJEFE.jehAfiliado = AFIJEFE.afiId)
			INNER JOIN Persona PERJEFE ON (AFIJEFE.afiPersona = PERJEFE.perId)
			
			-----------------------------------------------------------------------
			INNER JOIN ( 
				SELECT 
					INH.inhPostulacionFovis POFID,
					INH.inhTipoIntegrante TIPOINTEGRANTE,
					INH.inhPersona IDPERSONA,
					INH.inhSalarioMensual SALARIOINTEGRANTE
				FROM 
					IntegranteHogar INH
						

				----------
				UNION 
				----------

				SELECT 
					POFJEH.pofId,
					'PRINCIPAL',
					AFIJEH.afiPersona,
					JEH.jehIngresoMensual

				FROM 
					PostulacionFOVIS POFJEH
					INNER JOIN JefeHogar JEH ON JEH.jehId = POFJEH.pofJefeHogar
					INNER JOIN Afiliado AFIJEH ON AFIJEH.afiId = JEH.jehAfiliado
			)AS INTEGRANTES ON INTEGRANTES.POFID = POF.pofId
			-----------------------------------------------------------------------

			INNER JOIN Persona PERINTE ON PERINTE.perId = INTEGRANTES.IDPERSONA

			-----------------------------------------------------------------------
			----------INICIO SALARIO
			LEFT JOIN (
				SELECT 
					ingresoHogar.jehId AS jefeHogar, 
					SUM(ingresoHogar.ingreso) AS ingresos
				FROM (
	
					SELECT 
						jeh.jehId, 
						CASE 
							WHEN jeh.jehIngresoMensual IS NULL THEN ing.ingresoAfiliado
							ELSE jeh.jehIngresoMensual 
						END AS ingreso
					FROM 
						JefeHogar jeh
						JOIN (
							SELECT 
								afi.afiId, 
								SUM(roa.roaValorSalarioMesadaIngresos) AS ingresoAfiliado
							FROM 
								Afiliado afi 
								JOIN RolAfiliado roa ON (afi.afiId = roa.roaAfiliado)
							WHERE 
								roa.roaEstadoAfiliado = 'ACTIVO'
						GROUP BY 
							afi.afiId
						) AS ing ON  (jeh.jehAfiliado = ing.afiId)
	
					/*UNION
						
					SELECT 
						inh.inhJefeHogar jehId, 
						SUM(
							CASE 
								WHEN inh.inhSalarioMensual IS NULL AND ingAfi.ingresoAfiliado IS NOT NULL
								THEN ingAfi.ingresoAfiliado
	
								WHEN inh.inhSalarioMensual IS NULL AND ingAfi.ingresoAfiliado IS NULL
								THEN ingBen.ingresoBeneficiario
	
								ELSE inh.inhSalarioMensual 
							END
						) AS ingreso --ingresoIntegrante
					FROM 
						IntegranteHogar inh
	
						LEFT JOIN (
							SELECT 
								per.perId, 
								SUM(bed.bedSalarioMensual) AS ingresoBeneficiario
							FROM 
								Beneficiario ben
								JOIN Persona per ON (ben.benPersona = per.perId)
								JOIN PersonaDetalle ped ON (ped.pedPersona = per.perId)
								JOIN BeneficiarioDetalle bed ON (bed.bedPersonaDetalle = ped.pedPersona)
							GROUP BY per.perId
						) AS ingBen ON (inh.inhPersona = ingBen.perId)
	
						LEFT JOIN (
							SELECT 
								per.perId, 
								SUM(roa.roaValorSalarioMesadaIngresos) AS ingresoAfiliado
							FROM 
								Persona per
								JOIN Afiliado afi ON (per.perId = afi.afiPersona)
								JOIN RolAfiliado roa ON (afi.afiId = roa.roaAfiliado)
							WHERE 
								roa.roaEstadoAfiliado = 'ACTIVO'
							GROUP BY 
								per.perId
						) AS ingAfi ON (inh.inhPersona = ingAfi.perId)
	
					WHERE inh.inhEstadoHogar = 'ACTIVO'
					GROUP BY inh.inhJefeHogar */
	
				) AS ingresoHogar 
				GROUP BY ingresoHogar.jehId
	
			) AS salario ON (salario.jefeHogar = POF.pofJefeHogar)
			----------FIN SALARIO
			-----------------------------------------------------------------------

			------------Se obtiene informacion del estado-------
			LEFT JOIN ( 
				SELECT 

					pof.pofId As idPostulacion,
						
					MAX(
						CASE
							WHEN pofest.pofEstadoHogar IN ('SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO','SUBSIDIO_DESEMBOLSADO', 'SUBSIDIO_DESEMBOLSADO', 'SUBSIDIO_LEGALIZADO')
								AND leg.legFechaOperacion BETWEEN aaf.aafFechaActaAsignacionFovis AND aaf.aafFinVigencia
							THEN '3'

							WHEN pofest.pofEstadoHogar IN ('SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO', 'SUBSIDIO_DESEMBOLSADO', 'SUBSIDIO_DESEMBOLSADO')
								AND leg.legFechaOperacion > aaf.aafFinVigencia
							THEN '4'
								
							WHEN novedad.novTipoTransaccion = 'RENUNCIO_SUBISIDIO_ASIGNADO'
								OR pofest.pofEstadoHogar IN ('RENUNCIO_A_SUBSIDIO_ASIGNADO')
							THEN '5'

							WHEN pofest.pofEstadoHogar IN ('VENCIMIENTO_POR_ASIGNACION_SIN_PRORROGA', 'VENCIMIENTO_POR_ASIGNACION_SIN_SEGUNDA_PRORROGA', 'VENCIMIENTO_POR_ASIGNACION_CON_SEGUNDA_PRORROGA_CADUCADA')
							THEN '6'

							WHEN novedad.novTipoTransaccion IN ('RESTITUCION_SUBSIDIO_INCUMPLIMIENTO', 'REEMBOLSO_VOLUNTARIO_SUBSIDIO') AND 
								pofest.pofEstadoHogar IN ('SUBSIDIO_REEMBOLSADO')
							THEN '7'

							WHEN pofest.pofEstadoHogar IN ('RESTITUIDO_SIN_SANCION', 'RESTITUIDO_CON_SANCION', 'SUBSIDIO_REEMBOLSADO')
							THEN '7'

							WHEN novedad.novTipoTransaccion = 'AJUSTE_ACTUALIZACION_VALOR_SFV'
								AND pofest.pofEstadoHogar IN ('ASIGNADO_SIN_PRORROGA','ASIGNADO_CON_PRIMERA_PRORROGA', 'ASIGNADO_CON_SEGUNDA_PRORROGA')
							THEN '2'

							WHEN pofest.pofEstadoHogar IN ('ASIGNADO_SIN_PRORROGA','ASIGNADO_CON_PRIMERA_PRORROGA', 'ASIGNADO_CON_SEGUNDA_PRORROGA', 'PENDIENTE_APROBACION_PRORROGA','SUBSIDIO_LEGALIZADO')
							THEN '1'
						END
					) AS valor
				
				FROM 
					PostulacionFOVIS pof
					inner join #EstadosPostulacion pofest on pofest.pofid = pof.pofId
					INNER JOIN SolicitudAsignacion saf ON (pof.pofSolicitudAsignacion = saf.safId) 
					INNER JOIN ActaAsignacionFovis aaf ON (saf.safId = aaf.aafSolicitudAsignacion AND aaf.aafIdentificadorDocumentoConsolidado IS NOT NULL)
						
					LEFT JOIN (
						SELECT 
							sld.sldPostulacionFOVIS AS legPostulacionFovis, 
							lgd.lgdFormaPago AS legFormaPago, 
							MAX(sld.sldFechaOperacion) AS legFechaOperacion, 
							SUM(lgd.lgdValorDesembolsar) AS legValorDesembolsar
						FROM 
							SolicitudLegalizacionDesembolso sld
							JOIN LegalizacionDesembolso lgd ON (sld.sldLegalizacionDesembolso = lgd.lgdId)
						GROUP BY 
							sld.sldPostulacionFOVIS, 
							lgd.lgdFormaPago
					) AS leg ON (pof.pofId = leg.legPostulacionFovis)
					------------FINAL Se obtiene informacion del estado-------

					LEFT JOIN (
						SELECT 
							spf.spfPostulacionFovis AS novPostulacionFovis, 
							sol.solTipoTransaccion AS novTipoTransaccion, 
							MAX(sol.solFechaRadicacion) AS novFechaRadicacion
						FROM 
							SolicitudNovedadPersonaFovis spf 
							JOIN SolicitudNovedadFovis snf ON (snf.snfId = spf.spfSolicitudNovedadFovis)
							JOIN Solicitud sol ON ( snf.snfSolicitudGlobal = sol.solId)
						WHERE 
							sol.solTipoTransaccion IN (
								'NOVEDAD_REINTEGRO',
								'REEMBOLSO_VOLUNTARIO_SUBSIDIO',
								'DEVOLUCION_APORTES'
							)
							AND sol.solResultadoProceso = 'APROBADA'
							AND snf.snfEstadoSolicitud = 'NOV_FOVIS_CERRADA'
							AND sol.solFechaRadicacion BETWEEN @FECHA_INICIAL AND @FECHA_FINAL
						GROUP BY 
							spf.spfPostulacionFovis, 
							sol.solTipoTransaccion
					) AS novedad ON (pof.pofId = novedad.novPostulacionFovis)

				WHERE 
					pofest.pofEstadoHogar IN (
						'ASIGNADO_CON_SEGUNDA_PRORROGA', 
						'ASIGNADO_CON_PRIMERA_PRORROGA', 
						'ASIGNADO_SIN_PRORROGA',
						'PENDIENTE_APROBACION_PRORROGA', 
						'SUBSIDIO_LEGALIZADO', 
						'RENUNCIO_A_SUBSIDIO_ASIGNADO', 
						'VENCIMIENTO_POR_ASIGNACION_CON_SEGUNDA_PRORROGA_CADUCADA',
						'VENCIMIENTO_POR_ASIGNACION_SIN_SEGUNDA_PRORROGA', 
						'VENCIMIENTO_POR_ASIGNACION_SIN_PRORROGA', 
						'RESTITUIDO_CON_SANCION',
						'RESTITUIDO_SIN_SANCION', 
						'SUBSIDIO_REEMBOLSADO', 
						'SUBSIDIO_DESEMBOLSADO', 
						'SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO'
					)

				GROUP BY 
					pof.pofId, 
					pofest.pofEstadoHogar

			) AS estado ON (estado.idPostulacion = pof.pofId) 

		WHERE 
		-- PERJEFE.perNumeroIdentificacion in ('24332574','15958633') and
			( 
				POF.pofMotivoRestitucion NOT IN ('VIVIENDA_SUBSIDIADA_OBJETO_REMATE_JUDICIAL') OR 
				POF.pofMotivoRestitucion IS NULL 
			) AND
			POFEST.pofEstadoHogar IS NOT NULL AND
			POF.pofEstadoHogar IN (
				'ASIGNADO_CON_SEGUNDA_PRORROGA', 
				'ASIGNADO_CON_PRIMERA_PRORROGA', 
				'ASIGNADO_SIN_PRORROGA',
				'PENDIENTE_APROBACION_PRORROGA', 
				'SUBSIDIO_LEGALIZADO', 
				'RENUNCIO_A_SUBSIDIO_ASIGNADO', 
				'VENCIMIENTO_POR_ASIGNACION_CON_SEGUNDA_PRORROGA_CADUCADA',
				'VENCIMIENTO_POR_ASIGNACION_SIN_SEGUNDA_PRORROGA', 
				'VENCIMIENTO_POR_ASIGNACION_SIN_PRORROGA', 
				'RESTITUIDO_CON_SANCION',
				'RESTITUIDO_SIN_SANCION', 
				'SUBSIDIO_REEMBOLSADO', 
				'SUBSIDIO_DESEMBOLSADO', 
				'SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO'
			)	
--------------------------------------------------------------------------------------------------
-- FIN CONSOLIDADO

		
			) as consolidado

			group by
				consolidado.nivelIngreso,
				consolidado.componente,
				consolidado.estadoSubsidio,
				consolidado.anioVigencia,
				consolidado.tipoIdentificacionJefe,
				consolidado.numeroIdentificacion,
				consolidado.componenteHogar,
				consolidado.tipoIdIntegrante,
				consolidado.numIdIntegrante,
				consolidado.afiliadoACaja,
				consolidado.primerNombre,
				consolidado.segundoNombre,
				consolidado.primerApellido,
				consolidado.segundoApellido,
				consolidado.parentezcoIntegrante,
				consolidado.ingresosIntegrante,
				consolidado.tipoPlanVivienda,
				consolidado.pofId,
				consolidado.pofEstadoHogar,
				--consolidado.pofJsonPostulacion,
				consolidado.tipoIdentificacionJefe,
				consolidado.TIPOINTEGRANTE
		--------------------------------------------------------------------------------
		----------------------FINAL TABLA CON LA INFO DEL REPORTE
		--------------------------------------------------------------------------------
		---------------------------------------------------------------------
		----------------------TABLA TEMPORAL CON LOS JSON PARA OBTENER FUENTE 
		----------------------DE FINANCIAMIENTO
		DECLARE @jsonDesvinculados nvarchar(max);         

		IF OBJECT_ID('tempdb.dbo.#TABLE_TEMP_JSON', 'U') IS NOT NULL
		  DROP TABLE #TABLE_TEMP_JSON;

		IF OBJECT_ID('tempdb.dbo.#FINANCIAMIENTO_JSON_TEMP', 'U') IS NOT NULL
		  DROP TABLE #FINANCIAMIENTO_JSON_TEMP;

		IF OBJECT_ID('tempdb.dbo.#FINANCIAMIENTO_JSON_TEMP_II', 'U') IS NOT NULL
		  DROP TABLE #FINANCIAMIENTO_JSON_TEMP_II;

		--declare @FINANCIAMIENTO_JSON_TEMP as table(
		CREATE TABLE #FINANCIAMIENTO_JSON_TEMP(
			ID_POSTULACION BIGINT,
			AHORRO VARCHAR(255),
			PAGOS_PERIODICOS VARCHAR(255),
			CUOTAS VARCHAR(255),
			CUOTA_INICIAL VARCHAR(255),
			CESANTIAS_INMOVILIZADAS VARCHAR(255),
			AHORRO_OTRAS_MODALIDADES VARCHAR(255),
			APORTES_ENTE_TERRITORIAL VARCHAR(255),
			APORTES_SOLIDARIOS VARCHAR(255),
			CESANTIAS_NO_INMOVILIZADAS VARCHAR(255),
			CREDITO_APROBADO VARCHAR(255),
			DONACION_OTRAS_ENTIDADES VARCHAR(255),
			OTROS_RECURSOS VARCHAR(255),
			EVALUACION_CREDITICIA VARCHAR(255)
		)

		SELECT 
			pofId AS ID_POSTULACION,
			cast(pofJsonPostulacion as nvarchar(max)) AS JSON_POSTULACION
		INTO #TABLE_TEMP_JSON
		FROM PostulacionFOVIS 
		WHERE pofJsonPostulacion IS NOT NULL

		DECLARE 
			@longPostulacionesJSON INT, 
			@CONTADOR INT

		SET @CONTADOR = 1
		SET @longPostulacionesJSON = (SELECT COUNT(*) FROM #TABLE_TEMP_JSON )
		
		--print @longPostulacionesJSON

		WHILE (@CONTADOR <= @longPostulacionesJSON)	
			BEGIN
				SELECT TOP(@CONTADOR)
					@jsonDesvinculados = cast(JSON_POSTULACION as nvarchar(max))
				FROM
					#TABLE_TEMP_JSON
				ORDER BY ID_POSTULACION DESC 
				-- select * from #FINANCIAMIENTO_JSON_TEMP
				
				INSERT INTO #FINANCIAMIENTO_JSON_TEMP
				--
				SELECT
					FUENTE_FINANCIAMIENTO.ID_POSTULACION,
					CASE WHEN FUENTE_FINANCIAMIENTO.AHORRO > 0 THEN 'AHORRO' ELSE NULL END AS AHORRO,
					CASE WHEN FUENTE_FINANCIAMIENTO.PAGOS_PERIODICOS > 0 THEN 'PAGOS_PERIODICOS' ELSE NULL END  AS PAGOS_PERIODICOS,
					CASE WHEN FUENTE_FINANCIAMIENTO.CUOTAS > 0 THEN 'CUOTAS' ELSE NULL END  AS CUOTAS,
					CASE WHEN FUENTE_FINANCIAMIENTO.CUOTA_INICIAL > 0 THEN 'CUOTA_INICIAL' ELSE NULL END AS CUOTA_INICIAL,
					CASE WHEN FUENTE_FINANCIAMIENTO.CESANTIAS_INMOVILIZADAS > 0 THEN 'CESANTIAS_INMOVILIZADAS' ELSE NULL END AS CESANTIAS_INMOVILIZADAS,
					CASE WHEN FUENTE_FINANCIAMIENTO.AHORRO_OTRAS_MODALIDADES > 0 THEN 'AHORRO_OTRAS_MODALIDADES' ELSE NULL END AS AHORRO_OTRAS_MODALIDADES,
					CASE WHEN FUENTE_FINANCIAMIENTO.APORTES_ENTE_TERRITORIAL > 0 THEN 'APORTES_ENTE_TERRITORIAL' ELSE NULL END  AS APORTES_ENTE_TERRITORIAL,
					CASE WHEN FUENTE_FINANCIAMIENTO.APORTES_SOLIDARIOS > 0 THEN 'APORTES_SOLIDARIOS' ELSE NULL END AS APORTES_SOLIDARIOS,
					CASE WHEN FUENTE_FINANCIAMIENTO.CESANTIAS_NO_INMOVILIZADAS > 0 THEN 'CESANTIAS_NO_INMOVILIZADAS' ELSE NULL END AS CESANTIAS_NO_INMOVILIZADAS,
					CASE WHEN FUENTE_FINANCIAMIENTO.CREDITO_APROBADO > 0 THEN 'CREDITO_APROBADO' ELSE NULL END AS CREDITO_APROBADO,
					CASE WHEN FUENTE_FINANCIAMIENTO.DONACION_OTRAS_ENTIDADES > 0 THEN 'DONACION_OTRAS_ENTIDADES' ELSE NULL END AS DONACION_OTRAS_ENTIDADES,
					CASE WHEN FUENTE_FINANCIAMIENTO.OTROS_RECURSOS > 0 THEN 'OTROS_RECURSOS' ELSE NULL END AS OTROS_RECURSOS,
					CASE WHEN FUENTE_FINANCIAMIENTO.EVALUACION_CREDITICIA > 0 THEN 'EVALUACION_CREDITICIA' ELSE NULL END AS EVALUACION_CREDITICIA
					--END AS CLASIFICACION_FINANCIAMIENTO
				--INTO #FINANCIAMIENTO_JSON_TEMP
				FROM 
				OPENJSON( @jsonDesvinculados )
				WITH (
					ID_POSTULACION BIGINT '$.postulacion.idPostulacion',
					AHORRO VARCHAR '$.postulacion.ahorroProgramadoContractual.valor',
					PAGOS_PERIODICOS VARCHAR '$.postulacion.aportesPeriodicos.valor',
					CUOTAS VARCHAR '$.postulacion.cuotasPagadas.valor',
					CUOTA_INICIAL VARCHAR '$.postulacion.cuotaInicial',
					EVALUACION_CREDITICIA VARCHAR '$.postulacion.evaluacionCrediticia.valor',
					CESANTIAS_INMOVILIZADAS VARCHAR '$.postulacion.cesantiasInmovilizadas.valor',
					AHORRO_OTRAS_MODALIDADES VARCHAR '$.postulacion.ahorroOtrasModalidades.valor',
					APORTES_ENTE_TERRITORIAL VARCHAR '$.postulacion.aportesEnteTerritorial.valor',
					APORTES_SOLIDARIOS VARCHAR '$.postulacion.aportesSolidarios.valor',
					CESANTIAS_NO_INMOVILIZADAS VARCHAR '$.postulacion.cesantiasNoInmovilizadas.valor',
					CREDITO_APROBADO VARCHAR '$.postulacion.creditoAprobado.valor',
					DONACION_OTRAS_ENTIDADES VARCHAR '$.postulacion.donacionOtrasEntidades.valor',
					OTROS_RECURSOS VARCHAR '$.postulacion.otrosRecursos.valor'
				)AS FUENTE_FINANCIAMIENTO
				GROUP BY
					FUENTE_FINANCIAMIENTO.ID_POSTULACION,
					FUENTE_FINANCIAMIENTO.AHORRO,
					FUENTE_FINANCIAMIENTO.PAGOS_PERIODICOS,
					FUENTE_FINANCIAMIENTO.CUOTA_INICIAL,
					FUENTE_FINANCIAMIENTO.CUOTAS,
					FUENTE_FINANCIAMIENTO.CESANTIAS_INMOVILIZADAS,
					FUENTE_FINANCIAMIENTO.AHORRO_OTRAS_MODALIDADES,
					FUENTE_FINANCIAMIENTO.APORTES_ENTE_TERRITORIAL,
					FUENTE_FINANCIAMIENTO.APORTES_SOLIDARIOS,
					FUENTE_FINANCIAMIENTO.CESANTIAS_NO_INMOVILIZADAS,
					FUENTE_FINANCIAMIENTO.CREDITO_APROBADO,
					FUENTE_FINANCIAMIENTO.DONACION_OTRAS_ENTIDADES,
					FUENTE_FINANCIAMIENTO.OTROS_RECURSOS,
					FUENTE_FINANCIAMIENTO.EVALUACION_CREDITICIA

				SET @CONTADOR = @CONTADOR + 1
				PRINT @CONTADOR
				--select * from #FINANCIAMIENTO_JSON_TEMP
			END

		SELECT 
			ID_POSTULACION,
			CLASIFICACION
		INTO #FINANCIAMIENTO_JSON_TEMP_II
		FROM  
			#FINANCIAMIENTO_JSON_TEMP
		UNPIVOT  
		   (Orders FOR Clasificacion IN   
			  (	
				[AHORRO],
				--[PAGOS_PERIODICOS],
				[CUOTAS],
				[CUOTA_INICIAL],
				[CESANTIAS_INMOVILIZADAS],
				[AHORRO_OTRAS_MODALIDADES],
				[APORTES_ENTE_TERRITORIAL],
				[APORTES_SOLIDARIOS],
				[CESANTIAS_NO_INMOVILIZADAS],
				[CREDITO_APROBADO],
				[DONACION_OTRAS_ENTIDADES],
				[OTROS_RECURSOS],
				[EVALUACION_CREDITICIA]
		
			  )  
			)AS unpvt; 

		-----------------------------------------------------------------------------------------------------------------
		/*-------------------------------------------------------------------------------------------------------------*/
		-----------------------------------------------------------------------------------------------------------------
		SELECT 
			---------- Tipo de Identificacion del Aportante ----------
			consolidado_end.tipoIdentificacionJefe AS [Tipo de Identificación],
	
			---------- Numero de Identificacion Aportante  ----------
			consolidado_end.numeroIdentificacion AS [Número de Identificación],
		
			---------- Componente Hogar Objeto. ----------
			consolidado_end.componenteHogar AS [Componente del hogar objeto],
		
			----------  Tipo Identificacion Integrante del Hogar. ----------
			consolidado_end.tipoIdIntegrante as [Tipo de Identificación de los integrantes del hogar],
	
			---------- Numero de Identificacion Integrante del Hogar. ----------
			consolidado_end.numIdIntegrante AS [Número de Identificación de los integrantes del hogar],
	
			---------- Titular Subsidio ----------
			consolidado_end.afiliadoACaja AS [Titular del subsidio],
			
			---------- Primer Nombre de la persona reportada. ----------
			CASE 
				WHEN consolidado_end.primerNombre IS NULL
				THEN 'NO APLICA' 
				ELSE consolidado_end.primerNombre 
			END AS [Primer Nombre],
	
			---------- Segundo Nombre de la persona reportada. ----------
			CASE 
				WHEN consolidado_end.segundoNombre IS NULL
				THEN 'NO APLICA' 
				ELSE consolidado_end.segundoNombre
			END AS [Segundo Nombre],
	
			---------- Primer Apellido de la persona reportada. ----------
			CASE 
				WHEN consolidado_end.primerApellido IS NULL
				THEN 'NO APLICA' 
				ELSE consolidado_end.primerApellido
			END AS [Primer Apellido],
	
			---------- Segundo Apellido de la persona reportada. ----------
			CASE 
				WHEN consolidado_end.segundoApellido IS NULL
				THEN 'NO APLICA' 
				ELSE consolidado_end.segundoApellido
			END AS [Segundo Apellido],
		
			
			---------- Parentezco con el Titular. ----------	
			consolidado_end.parentezcoIntegrante AS [Parentesco de la persona con el titular],
	
			---------- Ingresos Integrante. ----------
			consolidado_end.ingresosIntegrante AS [Ingreso del integrante del hogar],
		
			---------- Nivel de Ingreso. ----------
			consolidado_end.nivelIngreso AS [Nivel de ingreso],
	
			---------- Componente. ----------
			consolidado_end.componente AS [Componente],
	
			---------- A;o asignacion. ----------
			consolidado_end.anioVigencia AS [Año de asignación],
	
			---------- Estado Subsidio. ----------
			consolidado_end.estadoSubsidio AS [Estado del subsidio],
	
			---------- Valor Subsidio. ----------
			consolidado_end.valorSubsidio AS [Valor del subsidio],

			----------Codigo Tipo Plan de Vivienda----------
			consolidado_end.tipoPlanVivienda AS [Código tipo plan de vivienda],

			----------Fuente de Financiamiento----------
			consolidado_end.fuentefinanciamiento AS [Fuente de financiamiento]

			--consolidado_end.pofEstadoHogar
			
		FROM(

			SELECT 
				---------- Tipo de Identificacion del Aportante ----------
				consolidado.tipoIdentificacionJefe,
	
				---------- Numero de Identificacion Aportante  ----------
				consolidado.numeroIdentificacion,
		
				---------- Componente Hogar Objeto. ----------
				consolidado.componenteHogar,
		
				----------  Tipo Identificacion Integrante del Hogar. ----------
				consolidado.tipoIdIntegrante,
	
				---------- Numero de Identificacion Integrante del Hogar. ----------
				consolidado.numIdIntegrante,
	
				---------- Titular Subsidio ----------
				consolidado.afiliadoACaja,
		
				---------- Primer Nombre de la persona reportada. ----------
				consolidado.primerNombre,
	
				---------- Segundo Nombre de la persona reportada. ----------
				consolidado.segundoNombre,
	
				---------- Primer Apellido de la persona reportada. ----------
				consolidado.primerApellido,
	
				---------- Segundo Apellido de la persona reportada. ----------
				consolidado.segundoApellido,
		
				---------- Parentezco con el Titular. ----------	
				consolidado.parentezcoIntegrante,
	
				---------- Ingresos Integrante. ----------
				consolidado.ingresosIntegrante,
		
				---------- Nivel de Ingreso. ----------
				consolidado.nivelIngreso,
	
				---------- Componente. ----------
				consolidado.componente,
	
				---------- A;o asignacion. ----------
				consolidado.anioVigencia,
	
				---------- Estado Subsidio. ----------
				consolidado.estadoSubsidio,
	
				---------- Valor Subsidio. ----------
				consolidado.valorSubsidio,

				----------Codigo Tipo Plan de Vivienda----------
				consolidado.tipoPlanVivienda,

				----------Fuente de Financiamiento----------
				CASE 
					WHEN JSONVAR.ID_POSTULACION IS NULL AND consolidado.parentezcoIntegrante = '19'
					THEN '7'
					--WHEN consolidado.parentezcoIntegrante != '19' --AND consolidado.parentezcoIntegrante != null
					--THEN '8'
					ELSE
						CASE 
							WHEN JSONVAR.CLASIFICACION IN ('APORTES_ENTE_TERRITORIAL') THEN '1'
							WHEN JSONVAR.CLASIFICACION IN ('CREDITO_APROBADO', 'EVALUACION_CREDITICIA') THEN '2'
							--WHEN JSONVAR.CLASIFICACION_FINANCIAMIENTO IN ('') THEN '3'
							WHEN JSONVAR.CLASIFICACION IN ('OTROS_RECURSOS') THEN '4'
							WHEN JSONVAR.CLASIFICACION IN ('AHORRO', 'AHORRO_OTRAS_MODALIDADES', 'CESANTIAS_NO_INMOVILIZADAS', 'CUOTA_INICIAL', 'CESANTIAS_INMOVILIZADAS','CUOTAS','PAGOS_PERIODICOS')
							THEN '5'
							--WHEN JSONVAR.CLASIFICACION_FINANCIAMIENTO IN ('') THEN '6'
							ELSE '7'
						END
				END AS [fuentefinanciamiento], 
				consolidado.pofEstadoHogar
			FROM 
				#TABLE_DATA_SUBSIDIO_TEMP AS consolidado
				LEFT JOIN #FINANCIAMIENTO_JSON_TEMP_II JSONVAR ON JSONVAR.ID_POSTULACION = consolidado.pofId
			WHERE 
				consolidado.pofEstadoHogar not in ('RECHAZADO')
			group by
					consolidado.nivelIngreso,
					consolidado.componente,
					consolidado.estadoSubsidio,
					consolidado.anioVigencia,
					consolidado.tipoIdentificacionJefe,
					consolidado.numeroIdentificacion,
					consolidado.componenteHogar,
					consolidado.tipoIdIntegrante,
					consolidado.numIdIntegrante,
					consolidado.afiliadoACaja,
					consolidado.primerNombre,
					consolidado.segundoNombre,
					consolidado.primerApellido,
					consolidado.segundoApellido,
					consolidado.parentezcoIntegrante,
					consolidado.ingresosIntegrante,
					consolidado.tipoPlanVivienda,
					consolidado.pofId,
					consolidado.valorSubsidio,
					--consolidado.pofJsonPostulacion,
					consolidado.tipoIdentificacionJefe,
					consolidado.pofEstadoHogar,
					JSONVAR.ID_POSTULACION,
					JSONVAR.CLASIFICACION
			)AS consolidado_end

		GROUP BY
			consolidado_end.nivelIngreso,
			consolidado_end.componente,
			consolidado_end.estadoSubsidio,
			consolidado_end.anioVigencia,
			consolidado_end.tipoIdentificacionJefe,
			consolidado_end.numeroIdentificacion,
			consolidado_end.componenteHogar,
			consolidado_end.tipoIdIntegrante,
			consolidado_end.numIdIntegrante,
			consolidado_end.afiliadoACaja,
			consolidado_end.primerNombre,
			consolidado_end.segundoNombre,
			consolidado_end.primerApellido,
			consolidado_end.segundoApellido,
			consolidado_end.parentezcoIntegrante,
			consolidado_end.ingresosIntegrante,
			consolidado_end.tipoPlanVivienda,
			consolidado_end.valorSubsidio,
			consolidado_end.tipoIdentificacionJefe,
			consolidado_end.fuentefinanciamiento,
			consolidado_end.pofEstadoHogar

END

