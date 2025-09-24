DECLARE @DBNAME VARCHAR(255),
		@sql NVARCHAR(4000),
		@fechaInicio DATE = '20190601',
		@fechaFin DATE = '20190901'
DROP TABLE #FechasNovedadesSolicituNovedad


CREATE TABLE #FechasNovedadesSolicituNovedad(snoId BIGINT, fechaRegistroNovedadCerrada DATE, sl VARCHAR(500))

SET @sql = '
SELECT sno.snoId, CAST(CONVERT(VARCHAR(10), dateadd(second, revTimeStamp / 1000, ''19700101'') AT TIME ZONE ''UTC'' AT TIME ZONE ''SA Pacific Standard Time'' ) AS DATE) AS fechaRegistroNovedadCerrada
FROM SolicitudNovedad_aud sno 
INNER JOIN Revision rev ON (sno.REV = rev.revId)
INNER JOIN(
	SELECT sno1.snoId, sno1.REV  
	FROM  SolicitudNovedad_aud sno1 
	WHERE sno1.snoEstadoSolicitud = ''APROBADA''
	GROUP BY sno1.snoId,sno1.REV 
) aux ON aux.snoId = sno.snoId AND aux.REV < sno.REV
WHERE sno.snoEstadoSolicitud = ''CERRADA''
GROUP BY sno.snoId, revTimeStamp'

INSERT INTO #FechasNovedadesSolicituNovedad(snoId, fechaRegistroNovedadCerrada, sl)
EXEC sp_execute_remote CoreAudReferenceData, @sql	

SELECT
			2 AS tipoRegistro,
			(SELECT cnsValor FROM dbo.Constante WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO') AS codigoCCF,
			CASE perAfiCore.perTipoIdentificacion 
			    WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			    WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			    WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			    WHEN 'PASAPORTE' THEN 'PA'
			    WHEN 'CARNE_DIPLOMATICO' THEN 'CD' 
			END AS tipoIdAfiliado,
			perAfiCore.perNumeroIdentificacion AS numeroIdAfiliado,
			CASE perDetAfiCore.pedGenero
				WHEN 'MASCULINO' THEN 'M'
				ELSE 'F'
			END AS codigoGeneroAfiliado,
			perDetAfiCore.pedFechaNacimiento AS fechaNacimientoAfiliado,
			SUBSTRING(perAfiCore.perPrimerApellido,0,60) AS primerApellidoAfiliado,
			SUBSTRING(perAfiCore.perSegundoApellido,0,60) AS segundoApellidoAfiliado,
			SUBSTRING(perAfiCore.perPrimerNombre,0,60) AS primerNombreAfiliado,
			SUBSTRING(perAfiCore.perSegundoNombre,0,60) AS segundoNombreAfiliado,
			'C09' AS codigoNovedad,
			NULL AS VARIABLE1,
			NULL AS VARIABLE2,
			NULL AS VARIABLE3,
			NULL AS VARIABLE4,
			NULL AS VARIABLE5,
			NULL AS VARIABLE6,
			NULL AS VARIABLE7,
			NULL AS VARIABLE8,
			NULL AS VARIABLE9,
			NULL AS VARIABLE10,
			NULL AS VARIABLE11,
			NULL AS VARIABLE12,
			NULL AS VARIABLE13,
			NULL AS VARIABLE14,
			NULL AS VARIABLE15,
			NULL AS VARIABLE16,
			NULL AS VARIABLE17,
			NULL AS VARIABLE18,
			NULL AS VARIABLE19,
			NULL AS VARIABLE20,
			NULL AS VARIABLE21,
			NULL AS VARIABLE22,
			NULL AS VARIABLE23,
			NULL AS VARIABLE24,
			NULL AS VARIABLE25,
			NULL AS VARIABLE26,
			NULL AS VARIABLE27,
			sls.slsFechaInicio AS fechaAsignacionSubsidio, -- VARIABLE 28
			LEFT(CAST(detalle.dsaValorSubsidioMonetario  AS VARCHAR), CHARINDEX('.', CAST(detalle.dsaValorSubsidioMonetario  AS VARCHAR)) - 1)
			AS valorSubsidio, -- VARIABLE 29
			1 AS codigoTipoSubsidio, -- VARIABLE 30
			CASE WHEN cuenta.casEstadoTransaccionSubsidio IN ('GENERADO','ENVIADO','APLICADO') THEN 1
				WHEN cuenta.casEstadoTransaccionSubsidio = 'RETENIDO' THEN 2
				WHEN cuenta.casEstadoTransaccionSubsidio = 'COBRADO' THEN 3
				WHEN cuenta.casEstadoTransaccionSubsidio = 'ANULADO' THEN 5
			END AS estadoSubsidio, -- VARIABLE 31 				--codigoNovedad = 'C09'
			(SELECT prmValor FROM dbo.Parametro WHERE prmNombre = 'CAJA_COMPENSACION_DEPTO_ID') AS departamentoSubsidio, -- VARIABLE 32
			(SELECT prmValor FROM dbo.Parametro WHERE prmNombre = 'CAJA_COMPENSACION_MUNI_ID') AS municipioSubsidio,   -- VARIABLE 33
			NULL AS fechaEntregaUltimoSubsidio, -- VARIABLE 34
			--- ================= FIN NOVEDAD 'C09' =====================
			--===================================================
			--================== INICIO NOVEDAD C04 ===========================
			NULL fechaDesvinculacionAportante, -- VARIABLE 35
			NULL fechaRetiroAfiliado, -- VARIABLE 36
			NULL fechaFallecimiento, -- VARIABLE 37
			--============= FIN NOVEDAD C04  ===========
			--- ================= INICIO NOVEDAD 'C09' ====================----
			CASE perBenCore.perTipoIdentificacion
				WHEN 'REGISTRO_CIVIL' THEN 'RC'
				WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
				WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
				WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
				WHEN 'PASAPORTE' THEN 'PA'
				WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
			END AS tipoIdbeneficiario, -- VARIABLE 38
			perBenCore.perNumeroIdentificacion AS numeroIdBeneficiario, -- VARIABLE 39
			NULL AS VARIABLE40,
			NULL AS VARIABLE41,
			NULL AS VARIABLE42,
			NULL AS VARIABLE43,
			NULL AS VARIABLE44,
			NULL AS VARIABLE45,
			NULL AS VARIABLE46,
			NULL AS VARIABLE47,
			NULL AS VARIABLE48,
			NULL AS VARIABLE49,
			NULL AS VARIABLE50,
			NULL AS VARIABLE51,
			NULL AS VARIABLE52,
			NULL AS VARIABLE53,
			NULL AS VARIABLE54,
			detalle.dsaId AS identificadorUnicoSubsidio, -- VARIABLE 55
			NULL AS nuevoTipoIdMiembrePoblacionCubierta,  --VARIABLE 56
			NULL AS nuevoNumeroIdMiembroPoblacionCubierta, -- VARIABLE 57
			NULL AS causaRetiro
			FROM dbo.Afiliado AS afiCore
			INNER JOIN dbo.RolAfiliado AS rolAfiCore ON rolAfiCore.roaAfiliado = afiCore.afiId
			INNER JOIN dbo.Persona AS perAfiCore ON afiCore.afiPersona = perAfiCore.perId
			INNER JOIN dbo.PersonaDetalle AS perDetAfiCore ON perAfiCore.perId = perDetAfiCore.pedPersona
			-- Novedades Persona
			LEFT JOIN dbo.DetalleSubsidioAsignado AS detalle ON detalle.dsaAfiliadoPrincipal = afiCore.afiId
			LEFT JOIN dbo.CuentaAdministradorSubsidio AS cuenta ON detalle.dsaCuentaAdministradorSubsidio = cuenta.casId
			LEFT JOIN dbo.SolicitudLiquidacionSubsidio AS sls ON sls.slsId = detalle.dsaSolicitudLiquidacionSubsidio
			LEFT JOIN dbo.Solicitud AS solSubsidio ON sls.slsSolicitudGlobal = solSubsidio.solId
			-- Fechas de las novedades subsidio
			LEFT JOIN dbo.SolicitudNovedad AS sno ON solSubsidio.solId = sno.snoSolicitudGlobal
			LEFT JOIN dbo.ParametrizacionNovedad ON sno.snoNovedad = dbo.ParametrizacionNovedad.novId
			LEFT JOIN #FechasNovedadesSolicituNovedad AS novedadesAprobadas ON novedadesAprobadas.snoId = sno.snoId
			LEFT JOIN (
						SELECT CASE WHEN afiRol.roaEmpleador IS NOT NULL THEN NULL ELSE afi.afiPersona END apgPersona, 
								empleadorAporte.empEmpresa apgEmpresa,
							   	afiRol.roaFechaAfiliacion,
							   	afiRol.roaFechaRetiro,
							   	afiRol.roaId,
							   	empleadorAporte.empMotivoDesafiliacion,
							   	empleadorAporte.empFechaCambioEstadoAfiliacion
						FROM dbo.RolAfiliado AS afiRol
						LEFT JOIN dbo.Empleador AS empleadorAporte ON empleadorAporte.empId = afiRol.roaEmpleador
						LEFT JOIN dbo.Afiliado afi ON afi.afiId = afiRol.roaAfiliado
			) AS aporteGenAfiCore ON aporteGenAfiCore.roaId = rolAfiCore.roaId
			INNER JOIN dbo.BeneficiarioDetalle AS benDetCore ON benDetCore.bedId = detalle.dsaBeneficiarioDetalle
			INNER JOIN dbo.Beneficiario AS benCore ON afiCore.afiId = benCore.benAfiliado AND benDetCore.bedId = benCore.benBeneficiarioDetalle
			INNER JOIN dbo.Persona AS perBenCore ON perBenCore.perId = benCore.benPersona
			WHERE (sls.slsTipoLiquidacion IN ('AJUSTE_DE_CUOTA') OR sls.slsTipoLiquidacionEspecifica IN ('RECONOCER_DISCAPACIDAD', 'RECONOCER_CUOTA_AGRARIA', 'VALOR_CUOTA', 'COMPLEMENTAR_CUOTA'))
  			  AND detalle.dsaFechaHoraCreacion BETWEEN @fechaInicio AND @fechaFin
  			  AND detalle.dsaOrigenRegistroSubsidio IN('LIQUIDACION_SUBSIDIO_MONETARIO')