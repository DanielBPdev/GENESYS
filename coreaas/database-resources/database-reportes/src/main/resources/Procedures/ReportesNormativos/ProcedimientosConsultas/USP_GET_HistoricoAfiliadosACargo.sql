-- =============================================
-- Author:		Diego Suesca
-- Create date: 2019/03/22
-- Author:		Francisco Alejandro Hoyos Rojas
-- Modified date: 2020/12/23
-- Description:	Inserta datos para reporte HistoricoAfiliadosACargo
-- =============================================
CREATE PROCEDURE USP_GET_HistoricoAfiliadosACargo
(
	@fechaInicio DATE,
	@fechaFin DATE,
	@historico BIT = NULL
)
AS
BEGIN TRY
	SET NOCOUNT ON;

	DECLARE @validacion smallint = 1;
	DECLARE @posicion  int = 0;
	
	IF @historico = 1
	BEGIN
		WHILE @validacion = 1
		BEGIN
			INSERT rno.HistoricoAfiliadosACargo(	 
				  hacFechaHistorico,
				  hacTipoIdentificacionEmpresa,
				  hacNumeroIdentificacionEmpresa,
				  hacTipoIdentificacionAfiliado,
				  hacNumeroIdentificacionAfiliado,
				  hacTipoIdentificacionPersonaACargo,
				  hacNumeroIdentificacionPersonaACargo,
				  hacPrimerNombrePersonaACargo,
				  hacSegundoNombrePersonaACargo,
				  hacPrimerApellidoPersonaACargo ,
				  hacSegundoApellidoPersonaACargo,
				  hacFechaNacimientoPersonaACargo,
				  hacGeneroPersonaACargo,
				  hacBenParentesco,
				  hacMunicipioResidencia,
				  hacAreaGeografica,
				  hacCondicionDiscapacidad,
				  hacTipoCuotaMonetariaPagadoPersonaCargo,
				  hacValorTotal,
				  hacNumeroCuotasPagadas,
				  hacNumeroPeriodosPagados,
				  hacFechaInicialReporte,
				  hacFechaFinalReporte
			)
			SELECT
				Reporte.fechaHistorico,
				Reporte.TipoIdentificacionEmpresa,
				Reporte.NumeroIdentificacionEmpresa,
				Reporte.TipoIdentificacionAfiliado,
				Reporte.NumeroIdentificacionAfiliado,
				Reporte.TipoIdentificacionPersonaACargo,
				Reporte.NumeroIdentificacionPersonaACargo,
				Reporte.PrimerNombrePersonaACargo,
				Reporte.SegundoNombrePersonaACargo,
				Reporte.PrimerApellidoPersonaACargo,
				Reporte.SegundoApellidoPersonaACargo,
				Reporte.FechaNacimientoPersonaACargo,
				Reporte.GeneroPersonaACargo,
				Reporte.benParentesco,
				Reporte.muniResidencia,
				Reporte.sectorUbicacion,
				Reporte.CondicionDiscapacidad,
				Reporte.tipoCuotaMonetariaPagadoPersonaCargo,
				Reporte.valortotal,
				Reporte.numeroCuotasPagadas,
				Reporte.numeroPeriodosPagados,
				Reporte.fechaInicio,
				Reporte.fechaFin
			FROM
			(
			SELECT
				@fechaFin AS fechaHistorico,
				CASE pere.perTipoIdentificacion
					WHEN 'CEDULA_CIUDADANIA' THEN '1'
					WHEN 'TARJETA_IDENTIDAD' THEN '2'
					WHEN 'REGISTRO_CIVIL' THEN '3'
					WHEN 'CEDULA_EXTRANJERIA' THEN '4'		          
					WHEN 'PASAPORTE' THEN '6'
					WHEN 'NIT' THEN '7'
					WHEN 'CARNE_DIPLOMATICO' THEN '8'
					WHEN 'PERM_ESP_PERMANENCIA' THEN '9'
				END AS TipoIdentificacionEmpresa, 
				pere.perNumeroIdentificacion as NumeroIdentificacionEmpresa,
				CASE pera.perTipoIdentificacion
					WHEN 'CEDULA_CIUDADANIA' THEN '1'
					WHEN 'TARJETA_IDENTIDAD' THEN '2'
					WHEN 'REGISTRO_CIVIL' THEN '3'
					WHEN 'CEDULA_EXTRANJERIA' THEN '4'		           
					WHEN 'PASAPORTE' THEN '6'
					WHEN 'NIT' THEN '7'
					WHEN 'CARNE_DIPLOMATICO' THEN '8'
					WHEN 'PERM_ESP_PERMANENCIA' THEN '9'
				END AS TipoIdentificacionAfiliado,
				pera.perNumeroIdentificacion as NumeroIdentificacionAfiliado,
				CASE perc.perTipoIdentificacion
					WHEN 'CEDULA_CIUDADANIA' THEN '1'
					WHEN 'TARJETA_IDENTIDAD' THEN '2'
					WHEN 'REGISTRO_CIVIL' THEN '3'
					WHEN 'CEDULA_EXTRANJERIA' THEN '4'		           
					WHEN 'PASAPORTE' THEN '6'
					WHEN 'NIT' THEN '7'
					WHEN 'CARNE_DIPLOMATICO' THEN '8'
					WHEN 'PERM_ESP_PERMANENCIA' THEN '9'
				END AS TipoIdentificacionPersonaACargo,
				perc.perNumeroIdentificacion as NumeroIdentificacionPersonaACargo,
				substring(perc.perPrimerNombre,1,30) as PrimerNombrePersonaACargo,
				substring(perc.perSegundoNombre,1,30) as SegundoNombrePersonaACargo,
				substring(perc.perPrimerApellido,1,30) as PrimerApellidoPersonaACargo,
				substring(perc.perSegundoApellido,1,30) as SegundoApellidoPersonaACargo,
				CONVERT(VARCHAR, ped.pedFechaNacimiento,112) as FechaNacimientoPersonaACargo,
				CASE ped.pedGenero
					WHEN 'MASCULINO' THEN 1
					WHEN 'FEMENINO' THEN 2				
					WHEN 'INDEFINIDO' THEN 4
				END AS GeneroPersonaACargo,
				CASE
				WHEN ben.benTipoBeneficiario = 'HIJO_BIOLOGICO' THEN 1
				WHEN ben.benTipoBeneficiario = 'MADRE' OR
						ben.benTipoBeneficiario = 'PADRE' THEN 2
				WHEN ben.benTipoBeneficiario = 'HERMANO_HUERFANO_DE_PADRES' THEN 3
				WHEN ben.benTipoBeneficiario = 'HIJASTRO' THEN 4
				WHEN ben.benTipoBeneficiario = 'CONYUGE' THEN 5
				WHEN ben.benTipoBeneficiario = 'BENEFICIARIO_EN_CUSTODIA' OR 
						ben.benTipoBeneficiario = 'HIJO_ADOPTIVO' THEN 6
				END AS benParentesco,
				muben.munCodigo as muniResidencia,
				CASE ubgrf.ubiSectorUbicacion WHEN 'RURAL' THEN '1' 
				WHEN 'URBANO' THEN '2' END AS sectorUbicacion,
				CASE
					WHEN coi.coiInvalidez = 1 THEN 1
					ELSE 2
				END AS CondicionDiscapacidad,
				CASE
					WHEN detalle.dsaTipoCuotaSubsidio = 'REGULAR'THEN 1
					WHEN detalle.dsaTipoCuotaSubsidio = 'DISCAPACIDAD' THEN 2
					WHEN detalle.dsaTipoCuotaSubsidio = 'AGRICOLA' THEN 3
					WHEN detalle.dsaTipoCuotaSubsidio = 'DISCAPACIDAD_AGRICOLA' THEN 4
					WHEN detalle.dsaTipoCuotaSubsidio = 'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO'
						OR detalle.dsaTipoCuotaSubsidio = 'AUXILIO_EXTRAORDINARIO_MUERTE_AFILIADO' THEN 5
					WHEN ISNULL(detalle.numeroCuotasPagadas,0) = 0 THEN 6
					WHEN detalle.dsaTipoCuotaSubsidio = 'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO_AGRICOLA'
						OR detalle.dsaTipoCuotaSubsidio = 'AUXILIO_EXTRAORDINARIO_MUERTE_AFILIADO_AGRICOLA' THEN 7
					WHEN detalle.dsaTipoCuotaSubsidio = 'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO_DISCAPACITADO' THEN 8
					WHEN detalle.dsaTipoCuotaSubsidio = 'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO_DISCAPACITADO_AGRICOLA'
						OR detalle.dsaTipoCuotaSubsidio = 'AUXILIO_EXTRAORDINARIO_MUERTE_AFILIADO_AGRICOLA_BENEFICIARIO_DISCAPACITADO' THEN 9
					ELSE 6
				END AS tipoCuotaMonetariaPagadoPersonaCargo,
				CAST(isnull(detalle.valortotal,0) as bigint) valortotal,
				ISNULL(detalle.numeroCuotasPagadas,0) AS numeroCuotasPagadas,
				ISNULL(detalle.numeroPeriodosPagados,0) AS numeroPeriodosPagados,				
				@fechaInicio as fechaInicio,
				@fechaFin as fechaFin
				FROM persona pere
				INNER JOIN empresa emp ON pere.perId = emp.empPersona
				INNER JOIN empleador empl ON empl.empEmpresa = emp.empId
				INNER JOIN rolAfiliado roa ON roa.roaEmpleador = empl.empId
				INNER JOIN afiliado afi ON roa.roaAfiliado =  afi.afiId 
				INNER JOIN persona pera ON afi.afiPersona = pera.perId
				INNER JOIN grupofamiliar grf ON grf.grfAfiliado = afi.afiId
				INNER JOIN beneficiario ben ON ben.benGrupoFamiliar = grf.grfId
				INNER JOIN BeneficiarioDetalle bed ON bed.bedId = ben.benBeneficiarioDetalle
				INNER JOIN persona perc ON ben.benPersona = perc.perId
				INNER JOIN personadetalle ped ON ped.pedPersona = perc.perId
				LEFT JOIN condicioninvalidez coi ON coi.coiPersona = perc.perId
				LEFT JOIN Ubicacion ubiben ON ubiben.ubiId = perc.perUbicacionPrincipal 
				LEFT JOIN Ubicacion ubgrf ON ubgrf.ubiId = grf.grfUbicacion 
				LEFT JOIN Municipio muben ON muben.munId =ubiben.ubiMunicipio 
				LEFT JOIN
				(
					SELECT det.dsaAfiliadoPrincipal AS afiliado, 
					det.dsaBeneficiarioDetalle,
					det.dsaTipoCuotaSubsidio,
					det.dsaEmpleador,
					COUNT(distinct det.dsaPeriodoLiquidado) AS numeroPeriodosPagados,
					SUM(det.dsaValorSubsidioMonetario) valorTotal,
					SUM(
					CASE
						WHEN det.dsaTipoCuotaSubsidio
								IN ('REGULAR','AGRICOLA','AUXILIO_EXTRAORDINARIO_MUERTE_AFILIADO', 'AUXILIO_EXTRAORDINARIO_MUERTE_AFILIADO_AGRICOLA',
								'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO','AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO_AGRICOLA')
						THEN 1
						WHEN det.dsaTipoCuotaSubsidio
							IN('DISCAPACIDAD','DISCAPACIDAD_AGRICOLA','AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO_DISCAPACITADO',
							'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO_DISCAPACITADO','AUXILIO_EXTRAORDINARIO_MUERTE_AFILIADO_AGRICOLA_BENEFICIARIO_DISCAPACITADO') 
						THEN 2
					END ) AS numeroCuotasPagadas
					FROM dbo.DetalleSubsidioAsignado det
					WHERE det.dsaEstado IN ('DERECHO_ASIGNADO')
						AND det.dsaOrigenRegistroSubsidio = 'LIQUIDACION_SUBSIDIO_MONETARIO'
						AND  det.dsaFechaHoraCreacion BETWEEN @fechaInicio  AND DATEADD(DAY,1,@fechaFin)
					GROUP BY det.dsaEmpleador, det.dsaAfiliadoPrincipal,det.dsaBeneficiarioDetalle,det.dsaTipoCuotaSubsidio
				) detalle ON detalle.afiliado = afi.afiId AND detalle.dsaBeneficiarioDetalle = bed.bedId AND empl.empId = detalle.dsaEmpleador
				WHERE detalle.dsaTipoCuotaSubsidio IS NOT NULL
					OR roa.roaFechaRetiro BETWEEN @fechaInicio AND @fechaFin
					OR roa.roaEstadoAfiliado = 'ACTIVO'
				GROUP BY 
				pere.perTipoIdentificacion, pere.perNumeroIdentificacion,pera.perTipoIdentificacion,
				pera.perNumeroIdentificacion, perc.perTipoIdentificacion, perc.perNumeroIdentificacion,
				perc.perPrimerNombre,perc.perSegundoNombre, perc.perPrimerApellido, perc.perSegundoApellido,
				ped.pedFechaNacimiento, ped.pedGenero,	ben.benTipoBeneficiario,coi.coiInvalidez,
				detalle.dsaTipoCuotaSubsidio,detalle.numeroCuotasPagadas, detalle.numeroPeriodosPagados,detalle.valortotal,
				muben.munCodigo, ubgrf.ubiSectorUbicacion
				UNION -- PENDIENTE Pensionados y Independientes arreglar

				SELECT
					@fechaFin,
					CASE pera.perTipoIdentificacion
						WHEN 'CEDULA_CIUDADANIA' THEN '1'
						WHEN 'TARJETA_IDENTIDAD' THEN '2'
						WHEN 'REGISTRO_CIVIL' THEN '3'
						WHEN 'CEDULA_EXTRANJERIA' THEN '4'		           
						WHEN 'PASAPORTE' THEN '6'
						WHEN 'NIT' THEN '7'
						WHEN 'CARNE_DIPLOMATICO' THEN '8'
						WHEN 'PERM_ESP_PERMANENCIA' THEN '9'
					END AS TipoIdentificacionEmpresa,
					pera.perNumeroIdentificacion as NumeroIdentificacionEmpresa,
					CASE pera.perTipoIdentificacion
						WHEN 'CEDULA_CIUDADANIA' THEN '1'
						WHEN 'TARJETA_IDENTIDAD' THEN '2'
						WHEN 'REGISTRO_CIVIL' THEN '3'
						WHEN 'CEDULA_EXTRANJERIA' THEN '4'		           
						WHEN 'PASAPORTE' THEN '6'
						WHEN 'NIT' THEN '7'
						WHEN 'CARNE_DIPLOMATICO' THEN '8'
						WHEN 'PERM_ESP_PERMANENCIA' THEN '9'
					END AS TipoIdentificacionAfiliado,
					pera.perNumeroIdentificacion as NumeroIdentificacionAfiliado,
					CASE perc.perTipoIdentificacion
						WHEN 'CEDULA_CIUDADANIA' THEN '1'
						WHEN 'TARJETA_IDENTIDAD' THEN '2'
						WHEN 'REGISTRO_CIVIL' THEN '3'
						WHEN 'CEDULA_EXTRANJERIA' THEN '4'		           
						WHEN 'PASAPORTE' THEN '6'
						WHEN 'NIT' THEN '7'
						WHEN 'CARNE_DIPLOMATICO' THEN '8'
						WHEN 'PERM_ESP_PERMANENCIA' THEN '9'
					END AS TipoIdentificacionPersonaACargo,
					perc.perNumeroIdentificacion as NumeroIdentificacionPersonaACargo,
					substring(perc.perPrimerNombre,1,30) as PrimerNombrePersonaACargo,
					substring(perc.perSegundoNombre,1,30) as SegundoNombrePersonaACargo,
					substring(perc.perPrimerApellido,1,30) as PrimerApellidoPersonaACargo,
					substring(perc.perSegundoApellido,1,30) as SegundoApellidoPersonaACargo,
					CONVERT(VARCHAR, ped.pedFechaNacimiento,112) as FechaNacimientoPersonaACargo,
					CASE ped.pedGenero
						WHEN 'MASCULINO' THEN 1
						WHEN 'FEMENINO' THEN 2				
						WHEN 'INDEFINIDO' THEN 4
					END AS GeneroPersonaACargo,
					CASE
					WHEN ben.benTipoBeneficiario = 'HIJO_BIOLOGICO' THEN 1
					WHEN ben.benTipoBeneficiario = 'MADRE' OR
							ben.benTipoBeneficiario = 'PADRE' THEN 2
					WHEN ben.benTipoBeneficiario = 'HERMANO_HUERFANO_DE_PADRES' THEN 3
					WHEN ben.benTipoBeneficiario = 'HIJASTRO' THEN 4
					WHEN ben.benTipoBeneficiario = 'CONYUGE' THEN 5
					WHEN ben.benTipoBeneficiario = 'BENEFICIARIO_EN_CUSTODIA' OR 
							ben.benTipoBeneficiario = 'HIJO_ADOPTIVO' THEN 6
					END AS benParentesco,
					muben.munCodigo as muniResidencia,
					CASE ubgrf.ubiSectorUbicacion WHEN 'RURAL' THEN '1' 
					WHEN 'URBANO' THEN '2' END AS sectorUbicacion,
					CASE
						WHEN coi.coiInvalidez = 1 THEN 1
						ELSE 2
					END AS CondicionDiscapacidad,
					6 tipoCuotaMonetariaPagadoPersonaCargo,
					0 valortotal,
					0 numeroCuotasPagadas,
					0 numeroPeriodosPagados,
					@fechaInicio as fechaInicio,
					@fechaFin as fechaFin
			FROM persona pera			
			INNER JOIN afiliado afi ON pera.perId = afi.afiPersona
			INNER JOIN rolAfiliado roa ON roa.roaAfiliado = afi.afiId AND roa.roaTipoAfiliado <> 'TRABAJADOR_DEPENDIENTE'				
			INNER JOIN grupofamiliar grf ON grf.grfAfiliado = afi.afiId
			INNER JOIN beneficiario ben ON ben.benGrupoFamiliar = grf.grfId
			INNER JOIN persona perc ON ben.benPersona = perc.perId
			INNER JOIN personadetalle ped ON ped.pedPersona = perc.perId
			LEFT JOIN condicioninvalidez coi ON coi.coiPersona = perc.perId
			LEFT JOIN Ubicacion ubiben ON ubiben.ubiId = perc.perUbicacionPrincipal 
			LEFT JOIN Ubicacion ubgrf ON ubgrf.ubiId = grf.grfUbicacion 
			LEFT JOIN Municipio muben ON muben.munId =ubiben.ubiMunicipio 
			WHERE roa.roaFechaRetiro BETWEEN @fechaInicio AND @fechaFin
				OR roa.roaEstadoAfiliado = 'ACTIVO'
			GROUP BY
			pera.perTipoIdentificacion,pera.perNumeroIdentificacion, perc.perTipoIdentificacion, perc.perNumeroIdentificacion,
			perc.perPrimerNombre,perc.perSegundoNombre, perc.perPrimerApellido, perc.perSegundoApellido,
			ped.pedFechaNacimiento, ped.pedGenero,	ben.benTipoBeneficiario,coi.coiInvalidez, muben.munCodigo, ubgrf.ubiSectorUbicacion
			) AS Reporte
			ORDER BY Reporte.NumeroIdentificacionAfiliado, Reporte.NumeroIdentificacionPersonaACargo OFFSET @posicion ROWS FETCH NEXT 100000 ROWS ONLY;

			IF @@ROWCOUNT = 0
			BEGIN
				SET	@validacion = 0;
			END;

			SET @posicion = @posicion + 100000;
	
		END
	END
END TRY
BEGIN CATCH
	THROW;
END CATCH
;
