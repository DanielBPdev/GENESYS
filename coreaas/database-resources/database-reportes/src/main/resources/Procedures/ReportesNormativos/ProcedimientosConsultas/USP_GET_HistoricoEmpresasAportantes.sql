-- =============================================
-- Author:		Diego Suesca
-- Create date: 2019/03/14
-- Description:	Inserta datos para reporte HistoricoDesagregadoCarteraAportante
-- =============================================
CREATE PROCEDURE USP_GET_HistoricoEmpresasAportantes
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
		INSERT rno.HistoricoEmpresasAportantes(hepFechaHistorico ,
											  heptipoIdentificaicon ,
											  hepperNumeroIdentificacion ,
											  hepNombre ,
											  hepmunCodigo ,
											  hepubiDireccionFisica ,
											  hepestadoVinculacion ,
											  heptipoDeAportante ,
											  heptipoDeSector ,
											  hepactEconomicaPpl ,
											  hepsituacion1429 ,
											  hepprogresividad1429 ,
											  hepsituacion590,
											  hepprogresividad590,
											  hepaporteTotalMensual ,
											  hepinteresesMora ,
											  hepvalorReintegros,
											  hepFechaInicialReporte,
											  hepFechaFinalReporte)	
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
	            END as tipoIdentificaicon,
	        per.perNumeroIdentificacion,
	        SUBSTRING(per.perRazonSocial,1,200) as Nombre,  
	        munE.munCodigo munCodigo,
	        LEFT(ubiE.ubiDireccionFisica,100) as ubiDireccionFisica,
	        CASE 
	            WHEN empl.empMarcaExpulsion IS NOT NULL AND empl.empFechaRetiro IS NOT NULL THEN '2'
	            WHEN empl.empFechaRetiro IS NOT NULL THEN '4'
	            WHEN empl.empEstadoEmpleador = 'ACTIVO' THEN '1' END estadoVinculacion,
	        '1' tipoDeAportante,
	        CASE WHEN sol.solClasificacion = 'PERSONA_NATURAL' OR sol.solClasificacion = 'EMPLEADOR_DE_SERVICIO_DOMESTICO' THEN '4'
	        	WHEN emp.empNaturalezaJuridica = 'PUBLICA' THEN '1'
	            WHEN emp.empNaturalezaJuridica = 'MIXTA' THEN '3'
	            WHEN emp.empNaturalezaJuridica = 'PRIVADA' THEN '2'
				 END tipoDeSector,
	            ISNULL(cii.ciiCodigo,'0000') actEconomicaPpl,
	        CASE WHEN (bem1429.bemBeneficioActivo=1)
	                    THEN 1
	                    ELSE 2 END situacion1429,
	        dbo.UFN_ObtenerProgreso1429(bem1429.bemFechaVinculacion,depE.depNombre) AS progresividad1429,
	         CASE WHEN (bem590.bemBeneficioActivo=1)
	                    THEN 1
	                    ELSE 2 END situacion590,
	        dbo.UFN_ObtenerProgreso590(bem1429.bemFechaVinculacion) AS progresividad590,
	       	CAST(SUM(CASE WHEN apd.apdId IS NOT NULL
	       				THEN CASE WHEN apd.apdEstadoAporteCotizante IN ('VIGENTE','CORREGIDO')
	       						THEN apd.apdAporteObligatorio 
	       				     END
	       			ELSE CASE WHEN apg.apgEstadoAporteAportante IN ('VIGENTE','CORREGIDO')
	       					THEN apg.apgValTotalApoObligatorio 
	       				 END
	       			END	) AS BIGINT) AS aporteTotalMensual,
	        CAST(SUM(ISNULL(apd.apdValorIntMora,0)) AS BIGINT) as interesesMora,	        
	        dev.sumReint valorReintegros,
	        @fechaInicio,
			@fechaFin
		FROM Persona per 
		INNER JOIN Empresa emp ON per.perId = emp.empPersona
		INNER JOIN Empleador empl ON empl.empEmpresa = emp.empId
		LEFT JOIN UbicacionEmpresa ube ON ube.ubeEmpresa = emp.empId AND ube.ubeTipoUbicacion='UBICACION_PRINCIPAL'
		LEFT JOIN Ubicacion ubiE ON ube.ubeUbicacion = ubiE.ubiId
		LEFT JOIN Municipio munE ON munE.munId = ubiE.ubiMunicipio
		LEFT JOIN Departamento depE ON depE.depId = munE.munDepartamento
		LEFT JOIN codigoCIIU cii on cii.ciiId = emp.empCodigoCIIU
		LEFT JOIN BeneficioEmpleador bem1429 ON bem1429.bemEmpleador = empl.empId AND bem1429.bemBeneficio = 1
		LEFT JOIN BeneficioEmpleador bem590 ON bem590.bemEmpleador = empl.empId AND bem590.bemBeneficio = 2
		LEFT JOIN AporteGeneral apg ON apg.apgPeriodoAporte BETWEEN CAST(YEAR(@fechaInicio) AS VARCHAR) + '-' +  REPLICATE('0', 2 - DATALENGTH(CAST(MONTH(@fechaInicio) AS VARCHAR))) + CAST(MONTH(@fechaInicio) AS VARCHAR)
		                              AND CAST(YEAR(@fechaFin) AS VARCHAR) + '-' +  REPLICATE('0', 2 - DATALENGTH(CAST(MONTH(@fechaFin) AS VARCHAR))) + CAST(MONTH(@fechaFin) AS VARCHAR)
									AND  (apg.apgEmpresa = emp.empId)
		LEFT JOIN AporteDetallado apd ON apd.apdAporteGeneral = apg.apgId
		LEFT JOIN (SELECT MAX(sol.solId) solId,empl.empId 
					FROM Empleador empl
					INNER JOIN SolicitudAfiliaciEmpleador sae ON sae.saeEmpleador = empl.empId
					INNER JOIN Solicitud sol ON sol.solId = sae.saeSolicitudGlobal
					GROUP BY empl.empId ) ultiSol ON ultiSol.empId = empl.empId		
		LEFT JOIN Solicitud sol ON sol.solId = ultiSol.solId		
		LEFT JOIN (SELECT SUM(moaValorAporte) sumReint, empl.empId
					 FROM Empleador empl 
					 INNER JOIN Empresa emp ON emp.empId = empl.empEmpresa
					 INNER JOIN AporteGeneral apg ON apg.apgEmpresa = emp.empId
					 INNER JOIN MovimientoAporte moa ON moa.moaAporteGeneral = apg.apgId
					 WHERE moaFechaCreacion BETWEEN @fechaInicio and @fechaFin
					   AND moaTipoMovimiento = 'DEVOLUCION_APORTES'
					   GROUP BY empl.empId) dev ON dev.empId = empl.empId		
		WHERE empl.empEstadoEmpleador = 'ACTIVO'
		  OR exists (SELECT 1 FROM DetalleSubsidioAsignado
		  			WHERE dsaEmpleador =  empl.empId
		  			  AND dsaFechaHoraCreacion BETWEEN @fechaInicio AND DATEADD(DAY,1,@fechaFin)
		  			  AND dsaEstado IN ('DERECHO_ASIGNADO')
					  AND dsaOrigenRegistroSubsidio = 'LIQUIDACION_SUBSIDIO_MONETARIO')
		  OR apg.apgId IS NOT NULL
		GROUP BY apg.apgPeriodoAporte,
		per.perRazonSocial,
		per.perTipoIdentificacion,
		per.perNumeroIdentificacion,
		per.perPrimerNombre,
		per.perSegundoNombre,
		per.perPrimerApellido,
		per.perSegundoApellido,		
		munE.munCodigo,		
		ubiE.ubiDireccionFisica,
		apg.apgTipoSolicitante,
		emp.empNaturalezaJuridica,
		cii.ciiCodigo,
		emp.empFechaConstitucion,
		bem1429.bemFechaDesvinculacion,
		bem1429.bemFechaVinculacion,
		empl.empMarcaExpulsion,
		empl.empFechaRetiro,
		bem590.bemFechaDesvinculacion,
		bem590.bemFechaVinculacion,		
		empl.empId,
		empl.empEstadoEmpleador,
		depE.depNombre,
		sol.solClasificacion,
		bem1429.bemBeneficioActivo,
		bem590.bemBeneficioActivo,
		dev.sumReint
	UNION ALL
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
	            END as tipoIdentificaicon,
	        per.perNumeroIdentificacion,
	        LEFT(RTrim(Coalesce(per.perPrimerNombre + ' ','') 
	                        + Coalesce(per.perSegundoNombre + ' ', '')
	                        + Coalesce(per.perPrimerApellido + ' ', '')
	                        + Coalesce(per.perSegundoApellido, ''))
	            ,200) as Nombre,  
	        mun.munCodigo munCodigo,
	        LEFT(ubi.ubiDireccionFisica,100) as ubiDireccionFisica,
	        CASE WHEN roa.roaEstadoAfiliado = 'ACTIVO' THEN '1'
	        	WHEN roa.roaMotivoDesafiliacion IN ('RETIRO_POR_MORA_APORTES','MAL_USO_DE_SERVICIOS_CCF','ENTREGA_DE_INFORMACION_FRAUDULENTA_CCF') THEN '2'
	        	ELSE '4' END estadoVinculacion,
	       CASE WHEN apg.apgTipoSolicitante IS NOT NULL THEN
		        CASE apg.apgTipoSolicitante
		            WHEN 'EMPLEADOR' THEN '1'
		            WHEN 'PENSIONADO' THEN '2'
		            WHEN 'INDEPENDIENTE' THEN '3'
		            WHEN 'FACULTATIVO' THEN '4'                             -- PENDIENTE
		        END
				WHEN roa.roaTipoAfiliado IS NOT NULL THEN
				CASE roa.roaTipoAfiliado
					WHEN 'PENSIONADO' THEN '2'
					WHEN 'INDEPENDIENTE' THEN '3'
				END
				ELSE '5'
		    END tipoDeAportante,
	        '4' tipoDeSector,
	        '0000' actEconomicaPpl,
	        2 situacion1429,
	        '6' progresividad1429,
	        2 situacion590,
	        '6' progresividad590,
	       	CAST(SUM(CASE WHEN apd.apdId IS NOT NULL
	       				THEN CASE WHEN apd.apdEstadoAporteCotizante IN ('VIGENTE','CORREGIDO')
	       					THEN apd.apdAporteObligatorio END
	       			ELSE CASE WHEN apg.apgEstadoAporteAportante IN ('VIGENTE','CORREGIDO')
	       					THEN apg.apgValTotalApoObligatorio END
	       				END	) AS BIGINT) AS aporteTotalMensual,
	        CAST(SUM(ISNULL(apd.apdValorIntMora,0)) AS BIGINT) as interesesMora,
	        dev.sumReint valorReintegros,
	        @fechaInicio,
			@fechaFin
		FROM Persona per
		INNER JOIN Afiliado afi ON per.perId = afi.afiPersona
		INNER JOIN RolAfiliado roa ON roa.roaAfiliado = afi.afiId AND roa.roaTipoAfiliado <> 'TRABAJADOR_DEPENDIENTE'
		LEFT JOIN Ubicacion ubi ON ubi.ubiId = per.perUbicacionPrincipal
		LEFT JOIN Municipio mun ON mun.munId = ubi.ubiMunicipio
		LEFT JOIN Departamento dep ON dep.depId = mun.munDepartamento
		LEFT JOIN AporteDetallado apd ON apd.apdPersona = per.perId
		LEFT JOIN AporteGeneral apg ON apg.apgPeriodoAporte BETWEEN CAST(YEAR(@fechaInicio) AS VARCHAR) + '-' +  REPLICATE('0', 2 - DATALENGTH(CAST(DAY(@fechaInicio) AS VARCHAR))) + CAST(MONTH(@fechaInicio) AS VARCHAR)
		                              AND CAST(YEAR(@fechaFin) AS VARCHAR) + '-' +  REPLICATE('0', 2 - DATALENGTH(CAST(DAY(@fechaFin) AS VARCHAR))) + CAST(MONTH(@fechaFin) AS VARCHAR)
									AND apd.apdAporteGeneral = apg.apgId
		LEFT JOIN (SELECT SUM(moaValorAporte) sumReint, per.perId
					 FROM Persona per
					 INNER JOIN AporteGeneral apg ON apg.apgPersona = per.perId
					 INNER JOIN MovimientoAporte moa ON moa.moaAporteGeneral = apg.apgId
					 WHERE moaFechaCreacion BETWEEN @fechaInicio and @fechaFin
					   AND moaTipoMovimiento = 'DEVOLUCION_APORTES'
					   GROUP BY per.perId) dev ON dev.perId = per.perId
		WHERE roa.roaEstadoAfiliado = 'ACTIVO'
		  OR apg.apgId IS NOT NULL
		GROUP BY apg.apgPeriodoAporte,
		per.perRazonSocial,
		per.perTipoIdentificacion,
		per.perNumeroIdentificacion,
		per.perPrimerNombre,
		per.perSegundoNombre,
		per.perPrimerApellido,
		per.perSegundoApellido,
		mun.munCodigo,
		ubi.ubiDireccionFisica,
		apg.apgTipoSolicitante,
		dep.depNombre,
		roa.roaTipoAfiliado,
		roa.roaEstadoAfiliado,
		roa.roaMotivoDesafiliacion,
		dev.sumReint
	END
END TRY
BEGIN CATCH
	THROW;
END CATCH
;
