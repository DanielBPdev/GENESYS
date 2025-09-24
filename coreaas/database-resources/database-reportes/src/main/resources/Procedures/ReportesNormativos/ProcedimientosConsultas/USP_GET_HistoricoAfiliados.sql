-- =============================================
-- Author:		Diego Suesca
-- Create date: 2019/03/14
-- Description:	Inserta datos para reporte
-- =============================================
create PROCEDURE USP_GET_HistoricoAfiliados
(
	@fechaInicio DATE,
	@fechaFin DATE,
	@historico BIT = NULL
)
AS
BEGIN TRY
	SET NOCOUNT ON;
	IF @historico = 1
	BEGIN
		-- ************** INICIO CALCULO DE CATEGORIAS ****************************************************************************
	    -- se crea tabla temporal para calcular categorías con sp USP_GET_CategoriaAfiliado
	    CREATE TABLE #CategoriaAfiliadoCalculada
	    (afiliado bigint,persona bigint,tipoIdentificacion  varchar(20),numeroIdentificacion varchar(16),tipoCotizante varchar(100),clasificacion varchar(48),salario numeric (19,2),estadoAfiliacion varchar(8),fechaFinServicioSinAfiliacion date,aporteEmpleadorNoAfiliado bit,categoria varchar (50)
	    );         

	    -- personas a calcular categoria
	    INSERT #CategoriaAfiliadoCalculada (persona)
	    SELECT afiPersona
		FROM Afiliado

	    -- ejecucion sp calculo de categorias, quedaran en la tabla #CategoriaAfiliadoCalculada
	    EXEC USP_GET_CategoriaAfiliado

	    -- **************** FIN CALCULO DE CATEGORIAS ***********************************************************************

		INSERT rno.HistoricoAfiliados(
			hraFechaHistorico,
			hraTipoIdentificacionEmpresa,
			hraNumeroIdentificacion,
			hraTipoIdentificacionAfiliado,
			hraNumeroIdentificacionAfiliado,
			hraPerPrimerNombre,
			hraPerSegundoNombre,
			hraPerPrimerApellido,
			hraPerSegundoApellido,
			hraPedFechaNacimiento,
			hraGenero,
			hraOrientacionSexual,
			hraNivelEducativo,
			hraOcupacionProfesional,
			hraFactorVulnerabilidad,
			hraEstadoCivil,
			hraPertenenciaEtnica,
			hraPaisResidencia,
			hraCodigoMunicipio,
			hraAreaGeografica,
			hraSalarioBasico,
			hraTipoAfiliado,
			hraCategoria,
			hraBeneficiarioCuota,
			hraFechaInicialReporte,
			hraFechaFinalReporte)
				SELECT @fechaFin,
		CASE perEmp.perTipoIdentificacion
			WHEN 'CEDULA_CIUDADANIA' THEN '1'
	        WHEN 'TARJETA_IDENTIDAD' THEN '2'
	        WHEN 'REGISTRO_CIVIL' THEN '3'
	        WHEN 'CEDULA_EXTRANJERIA' THEN '4'			        
	        WHEN 'PASAPORTE' THEN '6'
	        WHEN 'NIT' THEN '7'
	        WHEN 'CARNE_DIPLOMATICO' THEN '8'
	        WHEN 'PERM_ESP_PERMANENCIA' THEN '9'		
		END tipoIdentificacionEmpresa,
		perEmp.perNumeroIdentificacion numeroIdentificacion,
		CASE per.perTipoIdentificacion
	        WHEN 'CEDULA_CIUDADANIA' THEN '1'
	        WHEN 'TARJETA_IDENTIDAD' THEN '2'
	        WHEN 'REGISTRO_CIVIL' THEN '3'
	        WHEN 'CEDULA_EXTRANJERIA' THEN '4'			       
	        WHEN 'PASAPORTE' THEN '6'
	        WHEN 'NIT' THEN '7'
	        WHEN 'CARNE_DIPLOMATICO' THEN '8'
	        WHEN 'PERM_ESP_PERMANENCIA' THEN '9'
		END AS tipoIdentificacionAfiliado,
		per.perNumeroIdentificacion AS numeroIdentificacionAfiliado,
		substring(per.perPrimerNombre,1,30),
		substring(per.perSegundoNombre,1,30),
		substring(per.perPrimerApellido,1,30),
		substring(per.perSegundoApellido,1,30),
		CONVERT(VARCHAR,ped.pedFechaNacimiento,112) AS pedFechaNacimiento,
		CASE ped.pedGenero
			WHEN 'MASCULINO' THEN '1'
			WHEN 'FEMENINO' THEN '2'			
			WHEN 'INDEFINIDO' THEN '4'
		END AS genero,
		CASE ped.pedOrientacionSexual WHEN 'HETEROSEXUAL' THEN 1
        WHEN 'HOMOSEXUAL' THEN 2
        WHEN 'BISEXUAL' THEN 3
        WHEN 'INFORMACION_NO_DISPONIBLE' THEN 4 END as orientacionSexual,
		CASE WHEN ped.pedNivelEducativo = 'PREESCOLAR' THEN 1
		WHEN ped.pedNivelEducativo IN ('BASICA_PRIMARIA_COMPLETA','BASICA_PRIMARIA_INCOMPLETA') THEN 2
        WHEN ped.pedNivelEducativo IN ('BASICA_SECUNDARIA_COMPLETA','BASICA_SECUNDARIA_INCOMPLETA') THEN 3
		WHEN ped.pedNivelEducativo IN ('MEDIA_COMPLETA','MEDIA_INCOMPLETA') THEN 4
		WHEN ped.pedNivelEducativo IN ('BASICA_PRIMARIA_COMPLETA_ADULTOS','BASICA_PRIMARIA_INCOMPLETA_ADULTOS') THEN 6
		WHEN ped.pedNivelEducativo IN ('BASICA_SECUNDARIA_COMPLETA_ADULTOS','BASICA_SECUNDARIA_INCOMPLETA_ADULTOS') THEN 7
		WHEN ped.pedNivelEducativo IN ('MEDIA_COMPLETA_ADULTOS','MEDIA_INCOMPLETA_ADULTOS') THEN 8
		WHEN ped.pedNivelEducativo = 'PRIMERA_INFANCIA' THEN 9
		WHEN ped.pedNivelEducativo = 'EDUCACION_PARA_TRABAJO_Y_DESARROLLO_HUMANO' THEN 10
		WHEN ped.pedNivelEducativo = 'SUPERIOR' THEN 11
		WHEN ped.pedNivelEducativo = 'SUPERIOR_PREGRADO' THEN 12
		WHEN ped.pedNivelEducativo = 'SUPERIOR_POSGRADO' THEN 13
		ELSE 14 END as nivelEducativo,
		CASE WHEN ped.pedOcupacionProfesion IS NULL THEN ''
		ELSE CONVERT(VARCHAR,(ped.pedOcupacionProfesion - 1))
		END pedOcupacionProfesion,
		CASE WHEN ped.pedFactorVulnerabilidad = 'DESPLAZADO' THEN 1
		WHEN ped.pedFactorVulnerabilidad = 'VICTIMA_CONFLICTO_NO_DESPLAZADO' THEN 2
		WHEN ped.pedFactorVulnerabilidad = 'DESMOVILIZADO_REINSERTADO' THEN 3
		WHEN ped.pedFactorVulnerabilidad = 'HIJO_DESMOVILIZADO_REINSERTADO' THEN 4
		WHEN ped.pedFactorVulnerabilidad = 'DAMNIFICADO_DESASTRE_NATURAL' THEN 5
		WHEN ped.pedFactorVulnerabilidad = 'CABEZA_FAMILIA' THEN 6
		WHEN ped.pedFactorVulnerabilidad = 'HIJO_MADRES_CABEZA_FAMILIA' THEN 7
		WHEN ped.pedFactorVulnerabilidad = 'EN_CONDICION_DISCAPACIDAD' THEN 8
		WHEN ped.pedFactorVulnerabilidad = 'POBLACION_MIGRANTE' THEN 9
		WHEN ped.pedFactorVulnerabilidad = 'POBLACION_ZONAS_FRONTERA_NACIONALES' THEN 10
		WHEN ped.pedFactorVulnerabilidad = 'EJERCICIO_TRABAJO_SEXUAL' THEN 11
		WHEN ped.pedFactorVulnerabilidad IN ('NO_APLICA','') THEN 12 END as factorVulnerabilidad,
		CASE ped.pedEstadoCivil 
		WHEN 'UNION_LIBRE' THEN 1
		WHEN 'CASADO' THEN 2
		WHEN 'DIVORCIADO' THEN 3
		WHEN 'SEPARADO' THEN 4
		WHEN 'VIUDO' THEN 5
		WHEN 'SOLTERO' THEN 6
        ELSE 7 END AS estadoCivil,
		CASE ped.pedPertenenciaEtnica WHEN 'AFROCOLOMBIANO' THEN 1
        WHEN 'COMUNIDAD_NEGRA' THEN 2
        WHEN 'INDIGENA' THEN 3
        WHEN 'PALENQUERO' THEN 4 
		WHEN 'RAIZAL_ARCHI_SAN_ANDRES_PROVIDENCIA_SANTA_CATALINA' THEN 5
		WHEN 'ROOM_GITANO' THEN 6
		WHEN 'NINGUNO_DE_LOS_ANTERIORES' THEN 7
		WHEN '' THEN 8 END as pertenenciaEtnica,
		pai.paiDescripcion as paisResidencia,
		mun.munCodigo codigoMunicipio,
		CASE ISNULL(ped.pedResideSectorRural,0)
			WHEN 0 THEN '1'
			WHEN 1 THEN '2'
		END AS areaGeografica,
		CASE WHEN aportes.apdSalarioBasico IS NOT NULL
				THEN CAST(aportes.apdSalarioBasico AS BIGINT)
			ELSE
				CASE WHEN roa.roaValorSalarioMesadaIngresos IS NOT NULL
					THEN CAST(roa.roaValorSalarioMesadaIngresos AS BIGINT)
					ELSE CASE WHEN aportes.apdValorIBC IS NOT NULL
						THEN CAST(aportes.apdValorIBC AS BIGINT)
						END
				END
		END salarioBasico,
		CASE WHEN cat.tipoCotizante = 'TRABAJADOR_DEPENDIENTE' AND ISNULL(roa.roaClaseTrabajador,'SINCLASE') NOT IN ('MADRE_COMUNITARIA','SERVICIO_DOMESTICO') THEN '1'
			WHEN roa.roaClaseTrabajador = 'SERVICIO_DOMESTICO' THEN '2'
			WHEN roa.roaClaseTrabajador = 'MADRE_COMUNITARIA' THEN '3'
			WHEN cat.tipoCotizante = 'PENSIONADO' AND (cat.clasificacion = 'MENOS_1_5_SM_0_6_POR_CIENTO'
														OR cat.clasificacion = 'MAS_1_5_SM_0_6_POR_CIENTO') THEN '4'
			WHEN cat.tipoCotizante = 'PENSIONADO' AND (cat.clasificacion = 'MENOS_1_5_SM_2_POR_CIENTO'
														OR cat.clasificacion = 'MAS_1_5_SM_2_POR_CIENTO') THEN '5'
			--WHEN cat.tipoCotizante = 'FACULTATIVO' THEN '6'
			WHEN cat.clasificacion = 'TRABAJADOR_INDEPENDIENTE_0_6_POR_CIENTO' AND ISNULL(roa.roaClaseIndependiente,'SIN_CLASE_INDEPENDIENTE') NOT IN ('TAXISTA') THEN '7'
			WHEN cat.clasificacion = 'TRABAJADOR_INDEPENDIENTE_2_POR_CIENTO' AND ISNULL(roa.roaClaseIndependiente,'SIN_CLASE_INDEPENDIENTE') NOT IN ('TAXISTA') THEN '8'
			WHEN cat.tipoCotizante = 'PENSIONADO' AND cat.clasificacion = 'FIDELIDAD_25_ANIOS' THEN '9'
			--WHEN cat.clasificacion = 'DESAFILIADO_CON_DERECHO_TEMPORAL_A_SUBSIDIO_EN_CUOTA_MONETARIA' THEN '10'
			WHEN cat.tipoCotizante = 'PENSIONADO' AND cat.clasificacion = 'MENOS_1_5_SM_0_POR_CIENTO' THEN '11'
			WHEN roa.roaClaseIndependiente IN ('TAXISTA') THEN '12'
			--WHEN cat.categoria = 'C' THEN '13'
			--WHEN cat.tipoCotizante = 'FIDELIDAD_POR_DESEMPLEO' THEN '14'
		END tipoAfiliado,
		CASE cat.categoria WHEN 'A' THEN '1' WHEN 'B' THEN '2' WHEN 'C' THEN '3' END AS categoria,
		CASE WHEN MAX(dsa.dsaId) IS NOT NULL THEN '1' ELSE '2' END AS beneficiarioCuota,
		@fechaInicio,
		@fechaFin
		FROM Persona per
		INNER JOIN PersonaDetalle ped ON ped.pedPersona = per.perId
		INNER JOIN Afiliado afi ON afi.afiPersona = per.perId
		INNER JOIN RolAfiliado roa ON roa.roaAfiliado = afi.afiId
		INNER JOIN Empleador empl ON empl.empId = roa.roaEmpleador
		INNER JOIN Empresa emp ON emp.empId = empl.empEmpresa
		INNER JOIN Persona perEmp ON emp.empPersona = perEmp.perId		
		LEFT JOIN UbicacionEmpresa ube ON ube.ubeEmpresa = emp.empId AND ube.ubeTipoUbicacion='UBICACION_PRINCIPAL'
		LEFT JOIN Ubicacion ubi ON ube.ubeUbicacion = ubi.ubiId
		LEFT JOIN Municipio mun ON mun.munId = ubi.ubiMunicipio
		LEFT JOIN Departamento dep ON dep.depId = mun.munDepartamento
		LEFT JOIN Pais pai ON pai.paiId =ped.pedPaisResidencia
		LEFT JOIN (SELECT apd.apdPersona,apg.apgEmpresa,
						sum(apd.apdValorIntMora) apdValorIntMora,
						sum(apd.apdAporteObligatorio) apdAporteObligatorio,
						sum(apd.apdSalarioBasico) apdSalarioBasico,
						sum(apd.apdValorIBC) apdValorIBC
					FROM AporteDetallado apd
					LEFT JOIN AporteGeneral apg ON apd.apdAporteGeneral = apg.apgId
					WHERE apg.apgPeriodoAporte BETWEEN CAST(YEAR(@fechaInicio) AS VARCHAR) + '-' +  REPLICATE('0', 2 - DATALENGTH(CAST(MONTH(@fechaInicio) AS VARCHAR))) + CAST(MONTH(@fechaInicio) AS VARCHAR)
	                AND CAST(YEAR(@fechaFin) AS VARCHAR) + '-' +  REPLICATE('0', 2 - DATALENGTH(CAST(MONTH(@fechaFin) AS VARCHAR))) + CAST(MONTH(@fechaFin) AS VARCHAR)
					AND apg.apgEstadoAporteAportante IN ('VIGENTE','CORREGIDO')
					GROUP BY apd.apdPersona,apg.apgEmpresa
					) aportes ON aportes.apdPersona = per.perId AND aportes.apgEmpresa=emp.empId -- pendiente 
		LEFT JOIN (SELECT dsaId,dsaAfiliadoPrincipal,dsaEmpleador
					FROM DetalleSubsidioAsignado
					WHERE dsaFechaHoraCreacion BETWEEN @fechaInicio AND DATEADD(DAY,1,@fechaFin)
					  AND dsaEstado IN ('DERECHO_ASIGNADO')
					  AND dsaOrigenRegistroSubsidio = 'LIQUIDACION_SUBSIDIO_MONETARIO'
				   ) dsa ON dsa.dsaAfiliadoPrincipal = afi.afiId AND dsa.dsaEmpleador = empl.empId
		INNER JOIN #CategoriaAfiliadoCalculada cat ON cat.afiliado = afi.afiId
		--LEFT JOIN vw_EstadoAfiliacionPersonaCaja act ON act.perId = per.perId
		WHERE dsa.dsaId IS NOT NULL
			 OR roa.roaEstadoAfiliado = 'ACTIVO'
			 OR aportes.apdPersona IS NOT NULL
	GROUP BY roa.roaEmpleador
		,perEmp.perTipoIdentificacion
		,roa.roaEmpleador
		,perEmp.perNumeroIdentificacion
		,per.perTipoIdentificacion
		,per.perNumeroIdentificacion
		,per.perPrimerNombre
		,per.perSegundoNombre
		,per.perPrimerApellido
		,per.perSegundoApellido
		,ped.pedFechaNacimiento
		,ped.pedGenero
		,mun.munCodigo
		,ped.pedResideSectorRural
		,aportes.apdSalarioBasico
		,roa.roaValorSalarioMesadaIngresos
		,aportes.apdValorIBC
		,roa.roaTipoAfiliado
		,roa.roaClaseTrabajador
		,cat.tipoCotizante
		,cat.clasificacion
		,roa.roaClaseIndependiente
		,cat.categoria
		,ped.pedOrientacionSexual
		,ped.pedNivelEducativo
		,ped.pedOcupacionProfesion
		,ped.pedFactorVulnerabilidad
		,ped.pedEstadoCivil
		,ped.pedPertenenciaEtnica
		,pai.paiDescripcion
	UNION ALL -- PENDIENTE Ajuste union para solo pensionados independientes
		SELECT @fechaFin,			
		CASE per.perTipoIdentificacion
		        WHEN 'CEDULA_CIUDADANIA' THEN '1'
		        WHEN 'TARJETA_IDENTIDAD' THEN '2'
		        WHEN 'REGISTRO_CIVIL' THEN '3'
		        WHEN 'CEDULA_EXTRANJERIA' THEN '4'			        
		        WHEN 'PASAPORTE' THEN '6'
		        WHEN 'NIT' THEN '7'
		        WHEN 'CARNE_DIPLOMATICO' THEN '8'
		        WHEN 'PERM_ESP_PERMANENCIA' THEN '9'		
		END tipoIdentificacionEmpresa,
		per.perNumeroIdentificacion numeroIdentificacion,
		CASE per.perTipoIdentificacion
			        WHEN 'CEDULA_CIUDADANIA' THEN '1'
			        WHEN 'TARJETA_IDENTIDAD' THEN '2'
			        WHEN 'REGISTRO_CIVIL' THEN '3'
			        WHEN 'CEDULA_EXTRANJERIA' THEN '4'			        
			        WHEN 'PASAPORTE' THEN '6'
			        WHEN 'NIT' THEN '7'
			        WHEN 'CARNE_DIPLOMATICO' THEN '8'
			        WHEN 'PERM_ESP_PERMANENCIA' THEN '9'
		END AS tipoIdentificacionAfiliado,
		per.perNumeroIdentificacion AS numeroIdentificacionAfiliado,
		substring(per.perPrimerNombre,1,30),
		substring(per.perSegundoNombre,1,30),
		substring(per.perPrimerApellido,1,30),
		substring(per.perSegundoApellido,1,30),
		CONVERT(VARCHAR,ped.pedFechaNacimiento,112) AS pedFechaNacimiento,
		CASE ped.pedGenero
			WHEN 'MASCULINO' THEN '1'
			WHEN 'FEMENINO' THEN '2'			
			WHEN 'INDEFINIDO' THEN '4'
		END AS genero,
		CASE ped.pedOrientacionSexual WHEN 'HETEROSEXUAL' THEN 1
        WHEN 'HOMOSEXUAL' THEN 2
        WHEN 'BISEXUAL' THEN 3
        WHEN 'INFORMACION_NO_DISPONIBLE' THEN 4 END as orientacionSexual,
		CASE WHEN ped.pedNivelEducativo = 'PREESCOLAR' THEN 1
		WHEN ped.pedNivelEducativo IN ('BASICA_PRIMARIA_COMPLETA','BASICA_PRIMARIA_INCOMPLETA') THEN 2
        WHEN ped.pedNivelEducativo IN ('BASICA_SECUNDARIA_COMPLETA','BASICA_SECUNDARIA_INCOMPLETA') THEN 3
		WHEN ped.pedNivelEducativo IN ('MEDIA_COMPLETA','MEDIA_INCOMPLETA') THEN 4
		WHEN ped.pedNivelEducativo IN ('BASICA_PRIMARIA_ADULTO_COMPLETA','BASICA_PRIMARIA_ADULTO_INCOMPLETA') THEN 6
		WHEN ped.pedNivelEducativo IN ('BASICA_SECUNDARIA_ADULTO_COMPLETA','BASICA_SECUNDARIA_ADULTO_INCOMPLETA') THEN 7
		WHEN ped.pedNivelEducativo IN ('MEDIA_ADULTO_COMPLETA','MEDIA_ADULTO_INCOMPLETA') THEN 8
		WHEN ped.pedNivelEducativo = 'PRIMERA_INFANCIA' THEN 9
		WHEN ped.pedNivelEducativo = 'EDUCACION_PARA_TRABAJO_Y_DESARROLLO_HUMANO' THEN 10
		WHEN ped.pedNivelEducativo = 'SUPERIOR' THEN 11
		WHEN ped.pedNivelEducativo = 'SUPERIOR_PREGRADO' THEN 12
		WHEN ped.pedNivelEducativo = 'SUPERIOR_POSGRADO' THEN 13
		WHEN ped.pedNivelEducativo = '' THEN 14 END as nivelEducativo,
		CASE WHEN ped.pedOcupacionProfesion IS NULL THEN ''
		ELSE CONVERT(VARCHAR,(ped.pedOcupacionProfesion - 1)) 
		END pedOcupacionProfesion,
		CASE WHEN ped.pedFactorVulnerabilidad = 'DESPLAZADO' THEN 1
		WHEN ped.pedFactorVulnerabilidad = 'VICTIMA_CONFLICTO_NO_DESPLAZADO' THEN 2
		WHEN ped.pedFactorVulnerabilidad = 'DESMOVILIZADO_REINSERTADO' THEN 3
		WHEN ped.pedFactorVulnerabilidad = 'HIJO_DESMOVILIZADO_REINSERTADO' THEN 4
		WHEN ped.pedFactorVulnerabilidad = 'DAMNIFICADO_DESASTRE_NATURAL' THEN 5
		WHEN ped.pedFactorVulnerabilidad = 'CABEZA_FAMILIA' THEN 6
		WHEN ped.pedFactorVulnerabilidad = 'HIJO_MADRES_CABEZA_FAMILIA' THEN 7
		WHEN ped.pedFactorVulnerabilidad = 'EN_CONDICION_DISCAPACIDAD' THEN 8
		WHEN ped.pedFactorVulnerabilidad = 'POBLACION_MIGRANTE' THEN 9
		WHEN ped.pedFactorVulnerabilidad = 'POBLACION_ZONAS_FRONTERA_NACIONALES' THEN 10
		WHEN ped.pedFactorVulnerabilidad = 'EJERCICIO_TRABAJO_SEXUAL' THEN 11
		WHEN ped.pedFactorVulnerabilidad IN ('NO_APLICA','') THEN 12 END as factorVulnerabilidad,
		CASE ped.pedEstadoCivil 
		WHEN 'UNION_LIBRE' THEN 1
		WHEN 'CASADO' THEN 2
		WHEN 'DIVORCIADO' THEN 3
		WHEN 'SEPARADO' THEN 4
		WHEN 'VIUDO' THEN 5
		WHEN 'SOLTERO' THEN 6
        ELSE 7 END AS estadoCivil,
		CASE ped.pedPertenenciaEtnica WHEN 'AFROCOLOMBIANO' THEN 1
        WHEN 'COMUNIDAD_NEGRA' THEN 2
        WHEN 'INDIGENA' THEN 3
        WHEN 'PALENQUERO' THEN 4 
		WHEN 'RAIZAL_ARCHI_SAN_ANDRES_PROVIDENCIA_SANTA_CATALINA' THEN 5
		WHEN 'ROOM_GITANO' THEN 6
		WHEN 'NINGUNO_DE_LOS_ANTERIORES' THEN 7
		WHEN '' THEN 8 END as pertenenciaEtnica,
		pai.paiDescripcion as paisResidencia,
		mun.munCodigo codigoMunicipio,
		CASE ISNULL(ped.pedResideSectorRural,0)
			WHEN 0 THEN '1'
			WHEN 1 THEN '2'
		END AS areaGeografica,
		CASE WHEN aportes.apdSalarioBasico IS NOT NULL
				THEN CAST(aportes.apdSalarioBasico AS BIGINT)
			ELSE
				CASE WHEN roa.roaValorSalarioMesadaIngresos IS NOT NULL
					THEN CAST(roa.roaValorSalarioMesadaIngresos AS BIGINT)
					ELSE CASE WHEN aportes.apdValorIBC IS NOT NULL
						THEN CAST(aportes.apdValorIBC AS BIGINT)
						END
				END
		END salarioBasico,
		CASE WHEN cat.tipoCotizante = 'TRABAJADOR_DEPENDIENTE' AND ISNULL(roa.roaClaseTrabajador,'SINCLASE') NOT IN ('MADRE_COMUNITARIA','SERVICIO_DOMESTICO') THEN '1'
			WHEN roa.roaClaseTrabajador = 'SERVICIO_DOMESTICO' THEN '2'
			WHEN roa.roaClaseTrabajador = 'MADRE_COMUNITARIA' THEN '3'
			WHEN cat.tipoCotizante = 'PENSIONADO' AND (cat.clasificacion = 'MENOS_1_5_SM_0_6_POR_CIENTO'
														OR cat.clasificacion = 'MAS_1_5_SM_0_6_POR_CIENTO') THEN '4'
			WHEN cat.tipoCotizante = 'PENSIONADO' AND (cat.clasificacion = 'MENOS_1_5_SM_2_POR_CIENTO'
														OR cat.clasificacion = 'MAS_1_5_SM_2_POR_CIENTO') THEN '5'
			--WHEN cat.tipoCotizante = 'FACULTATIVO' THEN '6'
			WHEN cat.clasificacion = 'TRABAJADOR_INDEPENDIENTE_0_6_POR_CIENTO' AND ISNULL(roa.roaClaseIndependiente,'SIN_CLASE_INDEPENDIENTE') NOT IN ('TAXISTA') THEN '7'
			WHEN cat.clasificacion = 'TRABAJADOR_INDEPENDIENTE_2_POR_CIENTO' AND ISNULL(roa.roaClaseIndependiente,'SIN_CLASE_INDEPENDIENTE') NOT IN ('TAXISTA') THEN '8'
			WHEN cat.tipoCotizante = 'PENSIONADO' AND cat.clasificacion = 'FIDELIDAD_25_ANIOS' THEN '9'
			--WHEN cat.clasificacion = 'DESAFILIADO_CON_DERECHO_TEMPORAL_A_SUBSIDIO_EN_CUOTA_MONETARIA' THEN '10'
			WHEN cat.tipoCotizante = 'PENSIONADO' AND cat.clasificacion = 'MENOS_1_5_SM_0_POR_CIENTO' THEN '11'
			WHEN roa.roaClaseIndependiente IN ('TAXISTA') THEN '12'
			--WHEN cat.categoria = 'C' THEN '13'
			--WHEN cat.tipoCotizante = 'FIDELIDAD_POR_DESEMPLEO' THEN '14'
		END tipoAfiliado,
		CASE cat.categoria WHEN 'A' THEN '1' WHEN 'B' THEN '2' WHEN 'C' THEN '3' END AS categoria,
		CASE WHEN MAX(dsa.dsaId) IS NOT NULL THEN '1' ELSE '2' END AS beneficiarioCuota,
		@fechaInicio,
		@fechaFin
		FROM Persona per
		INNER JOIN PersonaDetalle ped ON ped.pedPersona = per.perId
		INNER JOIN Afiliado afi ON afi.afiPersona = per.perId
		INNER JOIN RolAfiliado roa ON roa.roaAfiliado = afi.afiId AND roa.roaTipoAfiliado <> 'TRABAJADOR_DEPENDIENTE'	
		LEFT JOIN Ubicacion ubi ON ubi.ubiId = per.perUbicacionPrincipal
		LEFT JOIN Municipio mun ON mun.munId = ubi.ubiMunicipio
		LEFT JOIN Departamento dep ON dep.depId = mun.munDepartamento
		LEFT JOIN Pais pai ON pai.paiId =ped.pedPaisResidencia
		LEFT JOIN (SELECT apd.apdPersona,
						sum(apd.apdValorIntMora) apdValorIntMora,
						sum(apd.apdAporteObligatorio) apdAporteObligatorio,
						sum(apd.apdSalarioBasico) apdSalarioBasico,
						sum(apd.apdValorIBC) apdValorIBC
					FROM AporteDetallado apd
					LEFT JOIN AporteGeneral apg ON apd.apdAporteGeneral = apg.apgId
					WHERE apg.apgPeriodoAporte BETWEEN CAST(YEAR(@fechaInicio) AS VARCHAR) + '-' +  REPLICATE('0', 2 - DATALENGTH(CAST(MONTH(@fechaInicio) AS VARCHAR))) + CAST(MONTH(@fechaInicio) AS VARCHAR)
	                AND CAST(YEAR(@fechaFin) AS VARCHAR) + '-' +  REPLICATE('0', 2 - DATALENGTH(CAST(MONTH(@fechaFin) AS VARCHAR))) + CAST(MONTH(@fechaFin) AS VARCHAR)
					AND apg.apgEstadoAporteAportante IN ('VIGENTE','CORREGIDO')
					GROUP BY apd.apdPersona
					) aportes ON aportes.apdPersona = per.perId
		LEFT JOIN DetalleSubsidioAsignado dsa ON (dsa.dsaAfiliadoPrincipal = afi.afiId
												AND dsa.dsaFechaHoraCreacion BETWEEN @fechaInicio AND DATEADD(DAY,1,@fechaFin))
		INNER JOIN #CategoriaAfiliadoCalculada cat ON (cat.afiliado = afi.afiId)
		--LEFT JOIN vw_EstadoAfiliacionPersonaCaja act ON (act.perId = per.perId)
		WHERE dsa.dsaId IS NOT NULL
			 OR roa.roaEstadoAfiliado = 'ACTIVO'
			 OR aportes.apdPersona IS NOT NULL
	GROUP BY per.perTipoIdentificacion
		,per.perNumeroIdentificacion
		,per.perPrimerNombre
		,per.perSegundoNombre
		,per.perPrimerApellido
		,per.perSegundoApellido
		,ped.pedFechaNacimiento
		,ped.pedGenero
		,mun.munCodigo
		,ped.pedResideSectorRural
		,aportes.apdSalarioBasico
		,roa.roaValorSalarioMesadaIngresos
		,aportes.apdValorIBC
		,roa.roaTipoAfiliado
		,roa.roaClaseTrabajador
		,cat.tipoCotizante
		,cat.clasificacion
		,roa.roaClaseIndependiente
		,cat.categoria
		,ped.pedOrientacionSexual
		,ped.pedNivelEducativo
		,ped.pedOcupacionProfesion
		,ped.pedFactorVulnerabilidad
		,ped.pedEstadoCivil
		,ped.pedPertenenciaEtnica
		,pai.paiDescripcion
	END
END TRY
BEGIN CATCH
	THROW;
END CATCH
;
