-- =============================================
-- Author:		Robinson Andrés Arboleda
-- Create date: 2018/03/06
-- Description:	Procedimiento almacenado para verificar si se cumplen las condiciones de 
-- trabajador fallecido
-- =============================================
CREATE PROCEDURE [dbo].[USP_SM_GET_CondicionesTrabajadorFallecido_trabajador]
	@iIdPersona BIGINT,
	@iTipo SMALLINT, -- 1: Validaciones HU-317-503,  2: Validaciones HU-317-506
	@bLiquidacionEnProceso BIT OUTPUT, -- Aplica en ambos casos
	-- Variables de salida HU-317-503			
	@bEstadoActivoEnCaja BIT OUTPUT,
	@bDiaAnteriorFechaDefuncionActivo BIT OUTPUT,
	@bRangoFallecimientoSolitudValido BIT OUTPUT, 
	@bMotivoDesafiliaCanalPresencial BIT OUTPUT,
	-- Variables de salida HU-317-506
	@bAfiliadoPrincipalActivo BIT OUTPUT,
	@bTieneBeneficiarioDistintoConyuge BIT OUTPUT,
	@bTrabajadorActivoAlFallecerBeneficiario BIT OUTPUT,
	@bMasDeUnBeneficiarioFallecido BIT OUTPUT
AS
BEGIN
	DECLARE	@iRangoDiasRegistroSubsidioFallecimiento INT,
			@dFechaPeriodo DATE = CAST(dbo.GetLocalDate() AS DATE)

	print 'Inicia [USP_SM_GET_CondicionesTrabajadorFallecido_trabajador]'

	-- Buscar liquidaciones distintas a cerradas o pendiente pago por aportes
	SELECT @bLiquidacionEnProceso = CASE WHEN  
	EXISTS(
		SELECT sls.slsId
		FROM dbo.SolicitudLiquidacionSubsidio sls
		WHERE sls.slsEstadoLiquidacion NOT IN ('CERRADA')
	) THEN 1 ELSE 0 END

	print 'Liquidacion en proceso: ' + Convert(varchar(50), @bLiquidacionEnProceso); 

	-- Validaciones de trabajador mencionadas en HU-317-503 (Fallecimiento Trabajador)
	IF @iTipo = 1
		BEGIN
			-- CASO 1 (Liquidación no procedente):  Validar que el trabajador tenga “Estado con respecto a la caja” igual a “Activo”
			-- Si al menos 1 de los roles afiliados de un afiliado es ACTIVO se considera al afiliado ACTIVO
			SELECT @bEstadoActivoEnCaja = CASE WHEN   
			EXISTS(
				SELECT roa.roaEstadoAfiliado, roa.roaId 
				FROM dbo.Persona per, dbo.Afiliado afi, dbo.RolAfiliado roa 
				WHERE afi.afiPersona = per.perId  -- Persona  -> Afiliado
				AND afi.afiId = roa.roaAfiliado -- Afiliado -> RolAfiliado
				AND per.perId =  @iIdPersona
				AND roa.roaEstadoAfiliado = 'ACTIVO'		
				AND roa.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
			) THEN 1 ELSE 0 END;

			print 'C1: Es activo en la caja: ' + Convert(varchar(50), @bEstadoActivoEnCaja); 

			-- CASO 2 (Liquidación no procedente): Validar que el trabajador al menos al día anterior a la “Fecha de defunción” tenga 
			-- “Estado de afiliación como dependiente” igual a “Activo”
			SELECT @bDiaAnteriorFechaDefuncionActivo = CASE WHEN 
			EXISTS(
				SELECT pde.pedFechaFallecido, roa.roaFechaRetiro, 
				DATEDIFF(day, pde.pedFechaFallecido, roa.roaFechaRetiro) AS diffDias, roa.roaId, 
				roa.roaEstadoAfiliado  
				FROM dbo.Persona per, dbo.PersonaDetalle pde, dbo.Afiliado afi, dbo.RolAfiliado roa
				WHERE afi.afiPersona = per.perId       -- Persona  -> Afiliado
				AND per.perId = pde.pedPersona         -- Persona  -> PersonaDetalle
				AND afi.afiId = roa.roaAfiliado        -- Afiliado -> RolAfiliado
				AND per.perId = @iIdPersona
				AND roa.roaEstadoAfiliado = 'INACTIVO'
				AND roa.roaMotivoDesafiliacion = 'FALLECIMIENTO'
				AND roa.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
				AND DATEDIFF(day, pde.pedFechaFallecido, roa.roaFechaRetiro) IS NOT NULL
				AND DATEDIFF(day, pde.pedFechaFallecido, roa.roaFechaRetiro) > 0
			) THEN 1 ELSE 0 END

			print 'C2: Al menos el dia anterior al fallecimiento estaba activo: ' + Convert(varchar(50), @bDiaAnteriorFechaDefuncionActivo); 

			-- Tomar el numero de dias parametrizados para registro de subsidio monetario

			SELECT @iRangoDiasRegistroSubsidioFallecimiento = pcs.pcsDiasNovedadFallecimiento 
			FROM dbo.ParametrizacionCondicionesSubsidio pcs
			WHERE @dFechaPeriodo BETWEEN pcs.pcsPeriodoInicio AND pcs.pcsPeriodoFin

			print '# dias para registro de subsidio: ' + Convert(varchar(50), @iRangoDiasRegistroSubsidioFallecimiento); 

			-- CASO 3 (Liquidación no procedente): Validar que el rango de días entre la “Fecha de defunción” y la
			-- “Fecha de registro” de la novedad, sea menor o igual a los días parametrizados por la caja para poder 
			-- registrar una solicitud de subsidio por fallecimiento (parámetro “Días máximos para registrar novedad 
			-- de fallecimiento” en la “HU-311-431 Parametrizar condiciones anuales de subsidio monetario”)
			SELECT @bRangoFallecimientoSolitudValido = CASE WHEN 
			EXISTS(
					SELECT pde.pedFechaFallecido, CAST(sol.solFechaCreacion AS DATE), 
					DATEDIFF(day, pde.pedFechaFallecido, CAST(sol.solFechaCreacion AS DATE))
					FROM dbo.Persona per, dbo.Afiliado afi, dbo.PersonaDetalle pde,
					dbo.SolicitudNovedadPersona snp,  dbo.SolicitudNovedad sn, dbo.Solicitud sol, dbo.ParametrizacionNovedad nov
					WHERE afi.afiPersona = per.perId       -- Persona  -> Afiliado
					AND per.perId = pde.pedPersona         -- Persona  -> PersonaDetalle
					AND per.perId = snp.snpPersona         -- Persona  -> SolicitudNovedadPersona
					AND snp.snpSolicitudNovedad = sn.snoId -- SolicitudNovedadPersona -> SolicitudNovedad
					AND sn.snoSolicitudGlobal = sol.solId  -- SolicitudNovedad -> Solicitud
					AND sn.snoNovedad = nov.novId		   -- SolicitudNovedad -> Parametrizacion Novedad
					AND per.perId = @iIdPersona
					AND pde.pedFallecido = 1
					AND sol.solTipoTransaccion IN ('REPORTE_FALLECIMIENTO_TRABAJADOR_DEPENDIENTE_PRESENCIAL')
					AND sol.solResultadoProceso IN ('APROBADA')
					AND DATEDIFF(day, pde.pedFechaFallecido, CAST(sol.solFechaCreacion AS DATE)) BETWEEN 1 AND @iRangoDiasRegistroSubsidioFallecimiento
			) THEN 0 ELSE 1 END;

			print 'C3: Rango valido entre la fecha de defunción y la fecha de registro de solicitud: ' + Convert(varchar(50), @bRangoFallecimientoSolitudValido); 

			-- CASO 4 (Liquidación no procedente): Validar que el último “Motivo de desafiliación” sea “Fallecimiento” 
			-- y que el “Canal” de registro de la novedad sea “Presencial”:
			SELECT @bMotivoDesafiliaCanalPresencial = CASE WHEN 
			EXISTS( 
				SELECT roa.roaId 
				FROM dbo.Persona per, dbo.Afiliado afi, dbo.RolAfiliado roa, dbo.PersonaDetalle pde,
				dbo.SolicitudNovedadPersona snp,  dbo.SolicitudNovedad sn, dbo.Solicitud sol, dbo.ParametrizacionNovedad nov
				WHERE afi.afiPersona = per.perId       -- Persona  -> Afiliado
				AND per.perId = pde.pedPersona         -- Persona  -> PersonaDetalle
				AND per.perId = snp.snpPersona         -- Persona  -> SolicitudNovedadPersona
				AND snp.snpSolicitudNovedad = sn.snoId -- SolicitudNovedadPersona -> SolicitudNovedad
				AND afi.afiId = roa.roaAfiliado        -- Afiliado -> RolAfiliado
				AND sn.snoSolicitudGlobal = sol.solId  -- SolicitudNovedad -> Solicitud
				AND sn.snoNovedad = nov.novId		   -- SolicitudNovedad -> Parametrizacion Novedad
				AND roa.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
				AND roa.roaMotivoDesafiliacion = 'FALLECIMIENTO'
				AND sol.solCanalRecepcion IN ('PRESENCIAL')
				AND sol.solTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE')
				AND sol.solResultadoProceso IN ('APROBADA')

				AND roa.roaId = 
				(
					SELECT MAX(roa.roaId) 
					FROM dbo.Persona per, dbo.Afiliado afi, dbo.RolAfiliado roa
					WHERE afi.afiPersona = per.perId       -- Persona  -> Afiliado	
					AND afi.afiId = roa.roaAfiliado        -- Afiliado -> RolAfiliado
					AND per.perId =  @iIdPersona
				)
			) THEN 1 ELSE 0 END

			print 'C4: Ultimo motivo desafiliacion es fallecimiento y el canal es presencial : ' + Convert(varchar(50), @bMotivoDesafiliaCanalPresencial); 
		END
	-- Validaciones de trabajador mencionadas en HU-317-506 (Fallecimiento Beneficiario)
	ELSE IF @iTipo = 2
		BEGIN
			-- C1 (Liquidación no procedente - Afiliado): Validar que el trabajador tenga “Estado del afiliado principal como trabajador 
			-- dependiente” igual a “Activo” (al momento de la consulta). Si al menos 1 de los roles afiliados de un afiliado es 
			-- ACTIVO se considera al afiliado ACTIVO
			SELECT @bAfiliadoPrincipalActivo = CASE WHEN   
				/*NOT*/ EXISTS(
					SELECT roa.roaEstadoAfiliado, roa.roaId 
					FROM dbo.Persona per, dbo.Afiliado afi, dbo.RolAfiliado roa 
					WHERE afi.afiPersona = per.perId  -- Persona  -> Afiliado
					AND afi.afiId = roa.roaAfiliado -- Afiliado -> RolAfiliado
					AND per.perId =  @iIdPersona
					AND roa.roaEstadoAfiliado = 'ACTIVO'		
					AND roa.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
				) THEN 1 ELSE 0 END;

			-- C2 (Liquidación no procedente - Afiliado): Validar que el afiliado tenga al menos un beneficiario distinto a “Cónyuge”
			SELECT @bTieneBeneficiarioDistintoConyuge = CASE WHEN 
				/*NOT*/ EXISTS(
					SELECT DISTINCT (perAfi.perId) AS idAfiliado, ben.benId, ben.benEstadoBeneficiarioAfiliado, ben.benTipoBeneficiario
					FROM dbo.Beneficiario ben
					INNER JOIN dbo.Afiliado afi ON ben.benAfiliado = afi.afiId
					INNER JOIN dbo.RolAfiliado roa ON afi.afiId = roa.roaAfiliado
					INNER JOIN dbo.Persona per ON per.perId = ben.benPersona
					INNER JOIN dbo.Persona perAfi ON perAfi.perId = afi.afiPersona
					INNER JOIN dbo.PersonaDetalle pde ON perAfi.perId = pde.pedPersona
					WHERE roa.roaTipoAfiliado IN ('TRABAJADOR_DEPENDIENTE') 
					AND perAfi.perId = @iIdPersona
					AND ben.benTipoBeneficiario NOT IN ('CONYUGE')
				)THEN 1 ELSE 0 END;

			-- C3 (Liquidación no procedente - Afiliado): Validar que en al menos un día del periodo en el que fallece el beneficiario, 
			-- el trabajador tenga “Estado de afiliación como dependiente” igual a “Activo”
			SELECT @bTrabajadorActivoAlFallecerBeneficiario = 0   

			-- C4 (Liquidación no procedente – Más de un beneficiario fallecido)
			SELECT @bMasDeUnBeneficiarioFallecido = CASE WHEN 
				/*NOT*/ EXISTS(
					SELECT COUNT(ped.pedFechaFallecido) 
					FROM dbo.PersonaDetalle ped 
					WHERE ped.pedId IN (
						SELECT DISTINCT (pdeBen.pedId) AS idAfiliado
						FROM dbo.Beneficiario ben
						INNER JOIN dbo.Afiliado afi ON ben.benAfiliado = afi.afiId
						INNER JOIN dbo.RolAfiliado roa ON afi.afiId = roa.roaAfiliado
						INNER JOIN dbo.Persona per ON per.perId = ben.benPersona
						INNER JOIN dbo.Persona perAfi ON perAfi.perId = afi.afiPersona
						INNER JOIN dbo.PersonaDetalle pde ON perAfi.perId = pde.pedPersona
						INNER JOIN dbo.PersonaDetalle pdeBen ON (per.perId = pdeBen.pedPersona)
						WHERE roa.roaTipoAfiliado IN ('TRABAJADOR_DEPENDIENTE') 
						AND perAfi.perId = @iIdPersona
						AND pdeBen.pedFallecido = 1
					)
					AND ped.pedFallecido = 1
					GROUP BY ped.pedFechaFallecido
					HAVING COUNT(ped.pedFechaFallecido) > 1
				)THEN 0 ELSE 1 END;


		END

END;
