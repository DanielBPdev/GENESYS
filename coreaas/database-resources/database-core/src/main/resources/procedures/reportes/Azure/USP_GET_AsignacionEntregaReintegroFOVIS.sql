-- =============================================
-- Author:		Jose Arley Correa
-- Create date: 2018/10/30
-- Description:	SP que lista los datos del reporte Asignación, Pago Y Reintegro De Subsidios De Viviendas – FOVIS 
-- =============================================
CREATE PROCEDURE USP_GET_AsignacionEntregaReintegroFOVIS
	@dFechaInicial DATE,
	@dFechaFin DATE,
	@bCount BIT
with execute as owner
AS

BEGIN
	SET NOCOUNT ON;
	DECLARE @sql NVARCHAR(4000);	
	
	CREATE TABLE #Postulacion(pofId BIGINT, pofEstadoHogar VARCHAR(100), pofSolicitudAsignacion BIGINT, s1 VARCHAR(500))
		
	SET @sql = '
			SELECT DISTINCT pof.pofId , pof.pofEstadoHogar, pof.pofSolicitudAsignacion
			FROM PostulacionFovis_aud pof
			JOIN Revision rev ON (pof.REV = rev.revId)
			WHERE pofEstadoHogar IN (''ASIGNADO_SIN_PRORROGA'',
			''ASIGNADO_CON_PRIMERA_PRORROGA'', 
			''ASIGNADO_CON_SEGUNDA_PRORROGA'', 
			''PENDIENTE_APROBACION_PRORROGA'',
			''SUBSIDIO_LEGALIZADO'', 
			''SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO'',
			''SUBSIDIO_DESEMBOLSADO'', 
			''RENUNCIO_A_SUBSIDIO_ASIGNADO'', 
			''VENCIMIENTO_POR_ASIGNACION_SIN_PRORROGA'', 
			''VENCIMIENTO_POR_ASIGNACION_SIN_SEGUNDA_PRORROGA'', 
			''VENCIMIENTO_POR_ASIGNACION_CON_SEGUNDA_PRORROGA_CADUCADA'', 
			''RESTITUIDO_SIN_SANCION'', 
			''RESTITUIDO_CON_SANCION'', 
			''SUBSIDIO_REEMBOLSADO'')
			AND  DATEADD(SECOND, rev.revTimeStamp / 1000, ''19700101'') AT TIME ZONE ''UTC'' AT TIME ZONE ''SA Pacific Standard Time'' BETWEEN @dFechaInicial AND @dFechaFin
			'	
	
	INSERT INTO #Postulacion (pofId, pofEstadoHogar, pofSolicitudAsignacion, s1)
	EXEC sp_execute_remote CoreAudReferenceData,
	@sql,
	N'@dFechaInicial DATE, @dFechaFin DATE',
	@dFechaInicial = @dFechaInicial, @dFechaFin = @dFechaFin

IF @bCount = 0
	BEGIN
		SELECT cons.fuenteFinanciamiento,
		cons.tipoPlanVivienda,
		cons.codigoMunicipio,
		cons.genero,
		cons.rangoEdad,
		cons.nivelIngreso,
		cons.componente,
		cons.estadoSubsidio,
		cons.anioVigencia,
		COUNT(1) AS cantidadSubsidios,
		CAST(SUM(cons.valorSubsidio)AS BIGINT) AS valorSubsidios
		FROM (SELECT 
			CASE
				WHEN ISNULL(codFinan2.valor,0) > ISNULL(codFinan4.valor,0) 
					AND ISNULL(codFinan2.valor,0) > ISNULL(codFinan5.valor,0)
				THEN codFinan2.codigo
				WHEN ISNULL(codFinan4.valor,0) > ISNULL(codFinan5.valor,0) 
					AND ISNULL(codFinan4.valor,0) > ISNULL(codFinan2.valor,0)
				THEN codFinan4.codigo
				ELSE codFinan5.codigo
			END AS fuenteFinanciamiento,
			CASE pof.pofModalidad
				WHEN 'ADQUISICION_VIVIENDA_NUEVA_URBANA'
				THEN 1 
				WHEN 'CONSTRUCCION_SITIO_PROPIO_URBANO'
				THEN 2
				WHEN 'MEJORAMIENTO_VIVIENDA_URBANA'
				THEN 3
				WHEN 'ADQUISICION_VIVIENDA_USADA_URBANA'
				THEN 4
				WHEN 'MEJORAMIENTO_VIVIENDA_SALUDABLE'
				THEN 5
				WHEN 'CONSTRUCCION_SITIO_PROPIO_RURAL'
				THEN 6
				WHEN 'ADQUISICION_VIVIENDA_NUEVA_RURAL'
				THEN 7
				WHEN 'MEJORAMIENTO_VIVIENDA_RURAL'
				THEN 8
			END AS tipoPlanVivienda,
			munCodigo AS codigoMunicipio,
			CASE ped.pedGenero
				WHEN 'FEMENINO'
				THEN 2
				WHEN 'MASCULINO'
				THEN 1
				WHEN 'INDEFINIDO'
				THEN 4
			END AS genero,
			CASE 
				WHEN DATEDIFF(YEAR, ped.pedFechaNacimiento, dbo.GetLocalDate()) BETWEEN 16 AND 18
				THEN 6
				WHEN DATEDIFF(YEAR, ped.pedFechaNacimiento, dbo.GetLocalDate()) BETWEEN 19 AND 23
				THEN 7
				WHEN DATEDIFF(YEAR, ped.pedFechaNacimiento, dbo.GetLocalDate()) BETWEEN 24 AND 45
				THEN 8
				WHEN DATEDIFF(YEAR, ped.pedFechaNacimiento, dbo.GetLocalDate()) BETWEEN 46 AND 60
				THEN 9
				WHEN DATEDIFF(YEAR, ped.pedFechaNacimiento, dbo.GetLocalDate()) > 60
				THEN 10
				ELSE
				1
			END AS rangoEdad,
			CASE
				WHEN YEAR(cia.ciaFechaInicio) < 2018
				THEN CASE
						WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) IS NULL
						THEN 1
						WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 0 AND 1
						THEN 2
						WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 1 AND 1.5
						THEN 3
						WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 1.5 AND 2
						THEN 4
						WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 2 AND 2.25
						THEN 5
						WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 2.25 AND 2.5
						THEN 6
						WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 2.5 AND 2.75
						THEN 7
						WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 2.75 AND 3
						THEN 8
						WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 3 AND 3.5
						THEN 9
						WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 3.5 AND 4
						THEN 10
					END
				WHEN pof.pofModalidad IN ('ADQUISICION_VIVIENDA_USADA_URBANA', 'ADQUISICION_VIVIENDA_USADA_RURAL','MEJORAMIENTO_VIVIENDA_RURAL','MEJORAMIENTO_VIVIENDA_URBANA','MEJORAMIENTO_VIVIENDA_SALUDABLE')
				THEN  CASE
						WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) IS NULL
						THEN 1
						WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 0 AND 1
						THEN 2
						WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 1 AND 1.5
						THEN 3
						WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 1.5 AND 2
						THEN 4
						WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 2 AND 2.25
						THEN 5
						WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 2.25 AND 2.5
						THEN 6
						WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 2.5 AND 2.75
						THEN 7
						WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 2.75 AND 3
						THEN 8
						WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 3 AND 3.5
						THEN 9
						WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 3.5 AND 4
						THEN 10
					END
				ELSE CASE
						WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 0 AND 1.6
						THEN 11
						WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 1.6 AND 2
						THEN 12
						WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 2 AND 3
						THEN 13
						WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 3 AND 4
						THEN 14
					END
			END AS nivelIngreso,
			CASE 
				WHEN pof.pofCondicionHogar IN ('HOGAR_OBJETO_REUBICACION_ZONA_ALTO_RIESGO')
				THEN 3
				WHEN  pof.pofModalidad IN('ADQUISICION_VIVIENDA_NUEVA_URBANA','ADQUISICION_VIVIENDA_USADA_URBANA','CONSTRUCCION_SITIO_PROPIO_URBANO','MEJORAMIENTO_VIVIENDA_URBANA','MEJORAMIENTO_VIVIENDA_SALUDABLE')
				THEN 1
				WHEN  pof.pofModalidad IN('ADQUISICION_VIVIENDA_NUEVA_RURAL','ADQUISICION_VIVIENDA_USADA_RURAL','CONSTRUCCION_SITIO_PROPIO_RURAL','MEJORAMIENTO_VIVIENDA_RURAL')
				THEN 2
			END AS componente,
			YEAR(cia.ciaFechaInicio) AS anioVigencia,
			CASE 
				WHEN pof.pofValorAjusteIPCSFV IS NOT NULL
				THEN pof.pofValorAjusteIPCSFV
				ELSE pof.pofValorAsignadoSFV
			END AS valorSubsidio,
			CASE estado.valor 
				WHEN 2 THEN 7
				WHEN 3 THEN 2
				WHEN 4 THEN 3
				WHEN 5 THEN 4
				WHEN 6 THEN 5
				WHEN 7 THEN 6
				ELSE 1
			END AS estadoSubsidio
			FROM PostulacionFOVIS pof
			JOIN Parametro prm ON (prm.prmNombre = 'SMMLV')
			JOIN CicloAsignacion cia ON (pof.pofCicloAsignacion = cia.ciaId)
			JOIN JefeHogar jeh ON (pof.pofJefeHogar = jeh.jehId)
			JOIN Afiliado afi ON (jeh.jehAfiliado = afi.afiId)
			JOIN Persona per ON (afi.afiPersona = per.perId)
			LEFT JOIN PersonaDetalle ped ON (per.perId = ped.pedPersona)
			LEFT JOIN Ubicacion ubi ON (per.perUbicacionPrincipal = ubi.ubiId)
			LEFT JOIN Municipio mun ON (ubi.ubiMunicipio = mun.munId)
			LEFT JOIN (SELECT fuente5.id, fuente5.codigo, SUM(fuente5.valor) as valor 
				FROM (SELECT ahp.ahpPostulacionFOVIS AS id, '5' AS codigo, ahp.ahpValor AS valor
					FROM AhorroPrevio ahp
					WHERE ahp.ahpValor > 0
					AND ahp.ahpNombreAhorro NOT IN ('VALOR_LOTE_OPV', 'VALOR_LOTE_POR_SUBSIDIO_MUNICIPAL_O_DEPARTAMENTAL')
					UNION
					SELECT rec.recPostulacionFOVIS AS id, '5' AS codigo, rec.recValor AS valor
					FROM RecursoComplementario rec
					WHERE rec.recValor > 0
					AND rec.recNombre NOT IN ('CREDITO_APROBADO', 'EVALUACION_CREDITICIA', 'APORTES_ENTE_TERRITORIAL', 'DONACION_OTRAS_ENTIDADES')
				) AS fuente5
				GROUP BY fuente5.id, fuente5.codigo
			) AS codFinan5 ON (codFinan5.id = pof.pofId)
			LEFT JOIN (SELECT fuente4.id, fuente4.codigo, SUM(fuente4.valor) as valor
				FROM (SELECT ahp.ahpPostulacionFOVIS AS id, '4' AS codigo, ahp.ahpValor AS valor
					FROM AhorroPrevio ahp
					WHERE ahp.ahpValor > 0
					AND ahp.ahpNombreAhorro IN ('VALOR_LOTE_OPV', 'VALOR_LOTE_POR_SUBSIDIO_MUNICIPAL_O_DEPARTAMENTAL')
					UNION
					SELECT rec.recPostulacionFOVIS AS id, '4' AS codigo, rec.recValor AS valor
					FROM RecursoComplementario rec
					WHERE rec.recValor > 0
					AND rec.recNombre  IN ('APORTES_ENTE_TERRITORIAL', 'DONACION_OTRAS_ENTIDADES')
				) AS fuente4
				GROUP BY fuente4.id, fuente4.codigo
			) AS codFinan4 ON (codFinan4.id = pof.pofId)
			LEFT JOIN (SELECT fuente2.id, fuente2.codigo, SUM(fuente2.valor) as valor
				FROM (SELECT rec.recPostulacionFOVIS AS id, '2' AS codigo, rec.recValor AS valor
					FROM RecursoComplementario rec
					WHERE rec.recValor > 0
					AND rec.recNombre IN ('CREDITO_APROBADO', 'EVALUACION_CREDITICIA')
				)AS fuente2
				GROUP BY fuente2.id, fuente2.codigo
			) AS codFinan2 ON (codFinan2.id = pof.pofId)
			JOIN (SELECT ingresoHogar.jehId AS jefeHogar, SUM(ingresoHogar.ingreso) AS ingresos
				FROM (SELECT jeh.jehId, CASE WHEN jeh.jehIngresoMensual IS NULL THEN ing.ingresoAfiliado
						ELSE jeh.jehIngresoMensual END AS ingreso
					FROM JefeHogar jeh
					JOIN (SELECT afi.afiId, SUM(roa.roaValorSalarioMesadaIngresos) AS ingresoAfiliado
						FROM Afiliado afi 
						JOIN RolAfiliado roa ON (afi.afiId = roa.roaAfiliado)
						WHERE roa.roaEstadoAfiliado = 'ACTIVO'
						GROUP BY afi.afiId) AS ing ON  (jeh.jehAfiliado = ing.afiId)
						UNION
						SELECT inh.inhJefeHogar, SUM(CASE WHEN inh.inhSalarioMensual IS NULL AND ingAfi.ingresoAfiliado IS NOT NULL
							THEN ingAfi.ingresoAfiliado
							WHEN inh.inhSalarioMensual IS NULL AND ingAfi.ingresoAfiliado IS NULL
							THEN ingBen.ingresoBeneficiario
							ELSE inh.inhSalarioMensual END) AS ingresoIntegrante
						FROM IntegranteHogar inh
						LEFT JOIN (SELECT per.perId, SUM(bed.bedSalarioMensual) AS ingresoBeneficiario
							FROM Beneficiario ben
							JOIN Persona per ON (ben.benPersona = per.perId)
							JOIN PersonaDetalle ped ON (ped.pedPersona = per.perId)
							JOIN BeneficiarioDetalle bed ON (bed.bedPersonaDetalle = ped.pedPersona)
							GROUP BY per.perId) AS ingBen ON (inh.inhPersona = ingBen.perId)
						LEFT JOIN (SELECT per.perId, SUM(roa.roaValorSalarioMesadaIngresos) AS ingresoAfiliado
							FROM Persona per
							JOIN Afiliado afi ON (per.perId = afi.afiPersona)
							JOIN RolAfiliado roa ON (afi.afiId = roa.roaAfiliado)
							WHERE roa.roaEstadoAfiliado = 'ACTIVO'
							GROUP BY per.perId) AS ingAfi ON (inh.inhPersona = ingAfi.perId)
						WHERE inh.inhEstadoHogar = 'ACTIVO'
						GROUP BY inh.inhJefeHogar ) AS ingresoHogar 
				GROUP BY ingresoHogar.jehId
			) AS salario ON (salario.jefeHogar = jeh.jehId)
			JOIN ( SELECT pof.pofId,
					MAX (CASE
						WHEN pof.pofEstadoHogar IN ('SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO','SUBSIDIO_DESEMBOLSADO')
							AND leg.legFechaOperacion BETWEEN aaf.aafFechaActaAsignacionFovis AND aaf.aafFinVigencia
						THEN '3'
						WHEN pof.pofEstadoHogar IN ('SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO','SUBSIDIO_DESEMBOLSADO')
							AND leg.legFechaOperacion > aaf.aafFinVigencia
						THEN '4'
						WHEN nov.novTipoTransaccion = 'RENUNCIO_SUBISIDIO_ASIGNADO'
							AND pof.pofEstadoHogar IN ('RENUNCIO_A_SUBSIDIO_ASIGNADO')
						THEN '5'
						WHEN pof.pofEstadoHogar IN ('VENCIMIENTO_POR_ASIGNACION_SIN_PRORROGA', 'VENCIMIENTO_POR_ASIGNACION_SIN_SEGUNDA_PRORROGA', 'VENCIMIENTO_POR_ASIGNACION_CON_SEGUNDA_PRORROGA_CADUCADA')
						THEN '6'
						WHEN nov.novTipoTransaccion IN ('RESTITUCION_SUBSIDIO_INCUMPLIMIENTO', 'REEMBOLSO_VOLUNTARIO_SUBSIDIO')
							AND pof.pofEstadoHogar IN ('RESTITUIDO_SIN_SANCION', 'RESTITUIDO_CON_SANCION', 'SUBSIDIO_REEMBOLSADO')
						THEN '7'
						WHEN nov.novTipoTransaccion = 'AJUSTE_ACTUALIZACION_VALOR_SFV'
							AND pofEstadoHogar IN ('ASIGNADO_SIN_PRORROGA','ASIGNADO_CON_PRIMERA_PRORROGA', 'ASIGNADO_CON_SEGUNDA_PRORROGA')
						THEN '2'
						WHEN pofEstadoHogar IN ('ASIGNADO_SIN_PRORROGA','ASIGNADO_CON_PRIMERA_PRORROGA', 'ASIGNADO_CON_SEGUNDA_PRORROGA', 'PENDIENTE_APROBACION_PRORROGA','SUBSIDIO_LEGALIZADO')
						THEN '1'
					END) AS valor
				FROM #Postulacion pof
				JOIN SolicitudAsignacion saf ON (pof.pofSolicitudAsignacion = saf.safId)
				JOIN ActaAsignacionFovis aaf ON (saf.safId = aaf.aafSolicitudAsignacion AND aaf.aafIdentificadorDocumentoConsolidado IS NOT NULL)
				LEFT JOIN (
					SELECT sld.sldPostulacionFOVIS AS legPostulacionFovis, lgd.lgdFormaPago AS legFormaPago, MAX(sld.sldFechaOperacion) AS legFechaOperacion, SUM(lgd.lgdValorDesembolsar) AS legValorDesembolsar
					FROM SolicitudLegalizacionDesembolso sld
					JOIN LegalizacionDesembolso lgd ON (sld.sldLegalizacionDesembolso = lgd.lgdId)
					GROUP BY sld.sldPostulacionFOVIS, lgd.lgdFormaPago
				) AS leg ON (pof.pofId = leg.legPostulacionFovis)
				LEFT JOIN (
					SELECT spf.spfPostulacionFovis AS novPostulacionFovis, sol.solTipoTransaccion AS novTipoTransaccion, MAX(sol.solFechaRadicacion) AS novFechaRadicacion
					FROM SolicitudNovedadPersonaFovis spf 
					JOIN SolicitudNovedadFovis snf ON (snf.snfId = spf.spfSolicitudNovedadFovis)
					JOIN Solicitud sol ON ( snf.snfSolicitudGlobal = sol.solId)
					WHERE sol.solTipoTransaccion IN ('AJUSTE_ACTUALIZACION_VALOR_SFV', 'RENUNCIO_SUBISIDIO_ASIGNADO', 'RESTITUCION_SUBSIDIO_INCUMPLIMIENTO', 'REEMBOLSO_VOLUNTARIO_SUBSIDIO', 'PRORROGA_FOVIS', 'VENCIMIENTO_SUBSIDIOS_ASIGNADOS_AUTOMATICA')
					AND sol.solResultadoProceso = 'APROBADA'
					AND snf.snfEstadoSolicitud = 'NOV_FOVIS_CERRADA'
					AND sol.solFechaRadicacion BETWEEN @dFechaInicial AND @dFechaFin
					GROUP BY spf.spfPostulacionFovis, sol.solTipoTransaccion
				) AS nov ON (pof.pofId = nov.novPostulacionFovis)
				GROUP BY pof.pofId
			) AS estado ON (estado.pofId = pof.pofId)
		) AS cons
		GROUP BY cons.fuenteFinanciamiento,
			cons.tipoPlanVivienda,
			cons.codigoMunicipio,
			cons.genero,
			cons.rangoEdad,
			cons.nivelIngreso,
			cons.componente,
			cons.estadoSubsidio,
			cons.anioVigencia
	END
ELSE
	BEGIN
		SELECT COUNT(1)
		FROM (SELECT COUNT(1) AS cantidadSubsidios 
			FROM (SELECT 
				CASE
					WHEN ISNULL(codFinan2.valor,0) > ISNULL(codFinan4.valor,0) 
						AND ISNULL(codFinan2.valor,0) > ISNULL(codFinan5.valor,0)
					THEN codFinan2.codigo
					WHEN ISNULL(codFinan4.valor,0) > ISNULL(codFinan5.valor,0) 
						AND ISNULL(codFinan4.valor,0) > ISNULL(codFinan2.valor,0)
					THEN codFinan4.codigo
					ELSE codFinan5.codigo
				END AS fuenteFinanciamiento,
				CASE pof.pofModalidad
					WHEN 'ADQUISICION_VIVIENDA_NUEVA_URBANA'
					THEN 1 
					WHEN 'CONSTRUCCION_SITIO_PROPIO_URBANO'
					THEN 2
					WHEN 'MEJORAMIENTO_VIVIENDA_URBANA'
					THEN 3
					WHEN 'ADQUISICION_VIVIENDA_USADA_URBANA'
					THEN 4
					WHEN 'MEJORAMIENTO_VIVIENDA_SALUDABLE'
					THEN 5
					WHEN 'CONSTRUCCION_SITIO_PROPIO_RURAL'
					THEN 6
					WHEN 'ADQUISICION_VIVIENDA_NUEVA_RURAL'
					THEN 7
					WHEN 'MEJORAMIENTO_VIVIENDA_RURAL'
					THEN 8
				END AS tipoPlanVivienda,
				munCodigo AS codigoMunicipio,
				CASE ped.pedGenero
					WHEN 'FEMENINO'
					THEN 2
					WHEN 'MASCULINO'
					THEN 1
					WHEN 'INDEFINIDO'
					THEN 4
				END AS genero,
				CASE 
					WHEN DATEDIFF(YEAR, ped.pedFechaNacimiento, dbo.GetLocalDate()) BETWEEN 16 AND 18
					THEN 6
					WHEN DATEDIFF(YEAR, ped.pedFechaNacimiento, dbo.GetLocalDate()) BETWEEN 19 AND 23
					THEN 7
					WHEN DATEDIFF(YEAR, ped.pedFechaNacimiento, dbo.GetLocalDate()) BETWEEN 24 AND 45
					THEN 8
					WHEN DATEDIFF(YEAR, ped.pedFechaNacimiento, dbo.GetLocalDate()) BETWEEN 46 AND 60
					THEN 9
					WHEN DATEDIFF(YEAR, ped.pedFechaNacimiento, dbo.GetLocalDate()) > 60
					THEN 10
					ELSE
					1
				END AS rangoEdad,
				CASE
					WHEN YEAR(cia.ciaFechaInicio) < 2018
					THEN CASE
							WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) IS NULL
							THEN 1
							WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 0 AND 1
							THEN 2
							WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 1 AND 1.5
							THEN 3
							WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 1.5 AND 2
							THEN 4
							WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 2 AND 2.25
							THEN 5
							WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 2.25 AND 2.5
							THEN 6
							WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 2.5 AND 2.75
							THEN 7
							WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 2.75 AND 3
							THEN 8
							WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 3 AND 3.5
							THEN 9
							WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 3.5 AND 4
							THEN 10
						END
					WHEN pof.pofModalidad IN ('ADQUISICION_VIVIENDA_USADA_URBANA', 'ADQUISICION_VIVIENDA_USADA_RURAL','MEJORAMIENTO_VIVIENDA_RURAL','MEJORAMIENTO_VIVIENDA_URBANA','MEJORAMIENTO_VIVIENDA_SALUDABLE')
					THEN  CASE
							WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) IS NULL
							THEN 1
							WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 0 AND 1
							THEN 2
							WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 1 AND 1.5
							THEN 3
							WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 1.5 AND 2
							THEN 4
							WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 2 AND 2.25
							THEN 5
							WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 2.25 AND 2.5
							THEN 6
							WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 2.5 AND 2.75
							THEN 7
							WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 2.75 AND 3
							THEN 8
							WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 3 AND 3.5
							THEN 9
							WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 3.5 AND 4
							THEN 10
						END
					ELSE CASE
							WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 0 AND 1.6
							THEN 11
							WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 1.6 AND 2
							THEN 12
							WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 2 AND 3
							THEN 13
							WHEN CAST(ROUND((salario.ingresos / prm.prmValor),2) AS NUMERIC(36,2)) BETWEEN 3 AND 4
							THEN 14
						END
				END AS nivelIngreso,
				CASE 
					WHEN pof.pofCondicionHogar IN ('HOGAR_OBJETO_REUBICACION_ZONA_ALTO_RIESGO')
					THEN 3
					WHEN  pof.pofModalidad IN('ADQUISICION_VIVIENDA_NUEVA_URBANA','ADQUISICION_VIVIENDA_USADA_URBANA','CONSTRUCCION_SITIO_PROPIO_URBANO','MEJORAMIENTO_VIVIENDA_URBANA','MEJORAMIENTO_VIVIENDA_SALUDABLE')
					THEN 1
					WHEN  pof.pofModalidad IN('ADQUISICION_VIVIENDA_NUEVA_RURAL','ADQUISICION_VIVIENDA_USADA_RURAL','CONSTRUCCION_SITIO_PROPIO_RURAL','MEJORAMIENTO_VIVIENDA_RURAL')
					THEN 2
				END AS componente,
				YEAR(cia.ciaFechaInicio) AS anioVigencia,
				CASE 
					WHEN pof.pofValorAjusteIPCSFV IS NOT NULL
					THEN pof.pofValorAjusteIPCSFV
					ELSE pof.pofValorAsignadoSFV
				END AS valorSubsidio,
				CASE estado.valor 
					WHEN 2 THEN 7
					WHEN 3 THEN 2
					WHEN 4 THEN 3
					WHEN 5 THEN 4
					WHEN 6 THEN 5
					WHEN 7 THEN 6
					ELSE 1
				END AS estadoSubsidio
				FROM PostulacionFOVIS pof
				JOIN Parametro prm ON (prm.prmNombre = 'SMMLV')
				JOIN CicloAsignacion cia ON (pof.pofCicloAsignacion = cia.ciaId)
				JOIN JefeHogar jeh ON (pof.pofJefeHogar = jeh.jehId)
				JOIN Afiliado afi ON (jeh.jehAfiliado = afi.afiId)
				JOIN Persona per ON (afi.afiPersona = per.perId)
				LEFT JOIN PersonaDetalle ped ON (per.perId = ped.pedPersona)
				LEFT JOIN Ubicacion ubi ON (per.perUbicacionPrincipal = ubi.ubiId)
				LEFT JOIN Municipio mun ON (ubi.ubiMunicipio = mun.munId)
				LEFT JOIN (SELECT fuente5.id, fuente5.codigo, SUM(fuente5.valor) as valor 
					FROM (SELECT ahp.ahpPostulacionFOVIS AS id, '5' AS codigo, ahp.ahpValor AS valor
						FROM AhorroPrevio ahp
						WHERE ahp.ahpValor > 0
						AND ahp.ahpNombreAhorro NOT IN ('VALOR_LOTE_OPV', 'VALOR_LOTE_POR_SUBSIDIO_MUNICIPAL_O_DEPARTAMENTAL')
						UNION
						SELECT rec.recPostulacionFOVIS AS id, '5' AS codigo, rec.recValor AS valor
						FROM RecursoComplementario rec
						WHERE rec.recValor > 0
						AND rec.recNombre NOT IN ('CREDITO_APROBADO', 'EVALUACION_CREDITICIA', 'APORTES_ENTE_TERRITORIAL', 'DONACION_OTRAS_ENTIDADES')
					) AS fuente5
					GROUP BY fuente5.id, fuente5.codigo
				) AS codFinan5 ON (codFinan5.id = pof.pofId)
				LEFT JOIN (SELECT fuente4.id, fuente4.codigo, SUM(fuente4.valor) as valor
					FROM (SELECT ahp.ahpPostulacionFOVIS AS id, '4' AS codigo, ahp.ahpValor AS valor
						FROM AhorroPrevio ahp
						WHERE ahp.ahpValor > 0
						AND ahp.ahpNombreAhorro IN ('VALOR_LOTE_OPV', 'VALOR_LOTE_POR_SUBSIDIO_MUNICIPAL_O_DEPARTAMENTAL')
						UNION
						SELECT rec.recPostulacionFOVIS AS id, '4' AS codigo, rec.recValor AS valor
						FROM RecursoComplementario rec
						WHERE rec.recValor > 0
						AND rec.recNombre  IN ('APORTES_ENTE_TERRITORIAL', 'DONACION_OTRAS_ENTIDADES')
					) AS fuente4
					GROUP BY fuente4.id, fuente4.codigo
				) AS codFinan4 ON (codFinan4.id = pof.pofId)
				LEFT JOIN (SELECT fuente2.id, fuente2.codigo, SUM(fuente2.valor) as valor
					FROM (SELECT rec.recPostulacionFOVIS AS id, '2' AS codigo, rec.recValor AS valor
						FROM RecursoComplementario rec
						WHERE rec.recValor > 0
						AND rec.recNombre IN ('CREDITO_APROBADO', 'EVALUACION_CREDITICIA')
					)AS fuente2
					GROUP BY fuente2.id, fuente2.codigo
				) AS codFinan2 ON (codFinan2.id = pof.pofId)
				JOIN (SELECT ingresoHogar.jehId AS jefeHogar, SUM(ingresoHogar.ingreso) AS ingresos
					FROM (SELECT jeh.jehId, CASE WHEN jeh.jehIngresoMensual IS NULL THEN ing.ingresoAfiliado
							ELSE jeh.jehIngresoMensual END AS ingreso
						FROM JefeHogar jeh
						JOIN (SELECT afi.afiId, SUM(roa.roaValorSalarioMesadaIngresos) AS ingresoAfiliado
							FROM Afiliado afi 
							JOIN RolAfiliado roa ON (afi.afiId = roa.roaAfiliado)
							WHERE roa.roaEstadoAfiliado = 'ACTIVO'
							GROUP BY afi.afiId) AS ing ON  (jeh.jehAfiliado = ing.afiId)
							UNION
							SELECT inh.inhJefeHogar, SUM(CASE WHEN inh.inhSalarioMensual IS NULL AND ingAfi.ingresoAfiliado IS NOT NULL
								THEN ingAfi.ingresoAfiliado
								WHEN inh.inhSalarioMensual IS NULL AND ingAfi.ingresoAfiliado IS NULL
								THEN ingBen.ingresoBeneficiario
								ELSE inh.inhSalarioMensual END) AS ingresoIntegrante
							FROM IntegranteHogar inh
							LEFT JOIN (SELECT per.perId, SUM(bed.bedSalarioMensual) AS ingresoBeneficiario
								FROM Beneficiario ben
								JOIN Persona per ON (ben.benPersona = per.perId)
								JOIN PersonaDetalle ped ON (ped.pedPersona = per.perId)
								JOIN BeneficiarioDetalle bed ON (bed.bedPersonaDetalle = ped.pedPersona)
								GROUP BY per.perId) AS ingBen ON (inh.inhPersona = ingBen.perId)
							LEFT JOIN (SELECT per.perId, SUM(roa.roaValorSalarioMesadaIngresos) AS ingresoAfiliado
								FROM Persona per
								JOIN Afiliado afi ON (per.perId = afi.afiPersona)
								JOIN RolAfiliado roa ON (afi.afiId = roa.roaAfiliado)
								WHERE roa.roaEstadoAfiliado = 'ACTIVO'
								GROUP BY per.perId) AS ingAfi ON (inh.inhPersona = ingAfi.perId)
							WHERE inh.inhEstadoHogar = 'ACTIVO'
							GROUP BY inh.inhJefeHogar ) AS ingresoHogar 
					GROUP BY ingresoHogar.jehId
				) AS salario ON (salario.jefeHogar = jeh.jehId)
				JOIN ( SELECT pof.pofId,
						MAX (CASE
							WHEN pof.pofEstadoHogar IN ('SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO','SUBSIDIO_DESEMBOLSADO')
								AND leg.legFechaOperacion BETWEEN aaf.aafFechaActaAsignacionFovis AND aaf.aafFinVigencia
							THEN '3'
							WHEN pof.pofEstadoHogar IN ('SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO','SUBSIDIO_DESEMBOLSADO')
								AND leg.legFechaOperacion > aaf.aafFinVigencia
							THEN '4'
							WHEN nov.novTipoTransaccion = 'RENUNCIO_SUBISIDIO_ASIGNADO'
								AND pof.pofEstadoHogar IN ('RENUNCIO_A_SUBSIDIO_ASIGNADO')
							THEN '5'
							WHEN pof.pofEstadoHogar IN ('VENCIMIENTO_POR_ASIGNACION_SIN_PRORROGA', 'VENCIMIENTO_POR_ASIGNACION_SIN_SEGUNDA_PRORROGA', 'VENCIMIENTO_POR_ASIGNACION_CON_SEGUNDA_PRORROGA_CADUCADA')
							THEN '6'
							WHEN nov.novTipoTransaccion IN ('RESTITUCION_SUBSIDIO_INCUMPLIMIENTO', 'REEMBOLSO_VOLUNTARIO_SUBSIDIO')
								AND pof.pofEstadoHogar IN ('RESTITUIDO_SIN_SANCION', 'RESTITUIDO_CON_SANCION', 'SUBSIDIO_REEMBOLSADO')
							THEN '7'
							WHEN nov.novTipoTransaccion = 'AJUSTE_ACTUALIZACION_VALOR_SFV'
								AND pofEstadoHogar IN ('ASIGNADO_SIN_PRORROGA','ASIGNADO_CON_PRIMERA_PRORROGA', 'ASIGNADO_CON_SEGUNDA_PRORROGA')
							THEN '2'
							WHEN pofEstadoHogar IN ('ASIGNADO_SIN_PRORROGA','ASIGNADO_CON_PRIMERA_PRORROGA', 'ASIGNADO_CON_SEGUNDA_PRORROGA', 'PENDIENTE_APROBACION_PRORROGA','SUBSIDIO_LEGALIZADO')
							THEN '1'
						END) AS valor
					FROM #Postulacion pof
					JOIN SolicitudAsignacion saf ON (pof.pofSolicitudAsignacion = saf.safId)
					JOIN ActaAsignacionFovis aaf ON (saf.safId = aaf.aafSolicitudAsignacion AND aaf.aafIdentificadorDocumentoConsolidado IS NOT NULL)
					LEFT JOIN (
						SELECT sld.sldPostulacionFOVIS AS legPostulacionFovis, lgd.lgdFormaPago AS legFormaPago, MAX(sld.sldFechaOperacion) AS legFechaOperacion, SUM(lgd.lgdValorDesembolsar) AS legValorDesembolsar
						FROM SolicitudLegalizacionDesembolso sld
						JOIN LegalizacionDesembolso lgd ON (sld.sldLegalizacionDesembolso = lgd.lgdId)
						GROUP BY sld.sldPostulacionFOVIS, lgd.lgdFormaPago
					) AS leg ON (pof.pofId = leg.legPostulacionFovis)
					LEFT JOIN (
						SELECT spf.spfPostulacionFovis AS novPostulacionFovis, sol.solTipoTransaccion AS novTipoTransaccion, MAX(sol.solFechaRadicacion) AS novFechaRadicacion
						FROM SolicitudNovedadPersonaFovis spf 
						JOIN SolicitudNovedadFovis snf ON (snf.snfId = spf.spfSolicitudNovedadFovis)
						JOIN Solicitud sol ON ( snf.snfSolicitudGlobal = sol.solId)
						WHERE sol.solTipoTransaccion IN ('AJUSTE_ACTUALIZACION_VALOR_SFV', 'RENUNCIO_SUBISIDIO_ASIGNADO', 'RESTITUCION_SUBSIDIO_INCUMPLIMIENTO', 'REEMBOLSO_VOLUNTARIO_SUBSIDIO', 'PRORROGA_FOVIS', 'VENCIMIENTO_SUBSIDIOS_ASIGNADOS_AUTOMATICA')
						AND sol.solResultadoProceso = 'APROBADA'
						AND snf.snfEstadoSolicitud = 'NOV_FOVIS_CERRADA'
						AND sol.solFechaRadicacion BETWEEN @dFechaInicial AND @dFechaFin
						GROUP BY spf.spfPostulacionFovis, sol.solTipoTransaccion
					) AS nov ON (pof.pofId = nov.novPostulacionFovis)
					GROUP BY pof.pofId
				) AS estado ON (estado.pofId = pof.pofId)
			) AS cons
			GROUP BY cons.fuenteFinanciamiento,
				cons.tipoPlanVivienda,
				cons.codigoMunicipio,
				cons.genero,
				cons.rangoEdad,
				cons.nivelIngreso,
				cons.componente,
				cons.estadoSubsidio,
				cons.anioVigencia
		) AS cuenta
	END
END;
