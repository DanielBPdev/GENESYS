-- =============================================
-- Author:		Robinson Andrés Arboleda
-- Create date: 2018/03/06
-- Description:	Procedimiento almacenado para verificar si se cumplen las condiciones de 
-- beneficiario fallecido
-- =============================================
CREATE PROCEDURE [dbo].[USP_SM_GET_CondicionesBeneficiarioFallecido_beneficiario]
	@iIdBeneficiario BIGINT,
	@bEstadoDistintoDeActivo BIT OUTPUT,	
	@bRangoValidoDiasParametrizacion BIT OUTPUT,
	@bMotivoFallecimientoCanalPresencial BIT OUTPUT
AS
BEGIN
	DECLARE	@iRangoDiasRegistroSubsidioFallecimiento INT,
			@dFechaPeriodo DATE = CAST(dbo.GetLocalDate() AS DATE)

	-- C5 (Liquidación no procedente – Beneficiario): Validar que el beneficiario fallecido, tenga “Estado de 
	-- afiliación con respecto al afiliado principal” distinto a “Activo” (al momento de la consulta).
	SELECT @bEstadoDistintoDeActivo = CASE WHEN
	EXISTS(
		SELECT ben.benId, per.perId, ben.benMotivoDesafiliacion
		FROM dbo.Beneficiario ben
		INNER JOIN dbo.Persona per ON (ben.benPersona = per.perId)
		INNER JOIN dbo.PersonaDetalle ped ON (per.perId = ped.pedPersona)
		WHERE ben.benId = @iIdBeneficiario
		--AND ben.benMotivoDesafiliacion IN ('FALLECIMIENTO')
		AND ben.benEstadoBeneficiarioAfiliado NOT IN ('ACTIVO')
		--AND ped.pedFallecido = 1
		--AND ped.pedFechaFallecido IS NOT NULL		
	)THEN 0 ELSE 1 END

	-- C7 (Liquidación no procedente - Beneficiario): Validar que el rango de días entre la “Fecha de defunción” 
	-- y la “Fecha de registro” de la novedad, sea menor o igual a los días parametrizados por la caja para poder 
	-- registrar una solicitud de subsidio por fallecimiento
	-- TODO pendiente control de cambios
	SELECT @bRangoValidoDiasParametrizacion = 0 --(Core)

	-- C8 (Liquidación no procedente - Beneficiario): Validar que el último “Motivo de desafiliación” sea “Fallecimiento” 
	-- y que el “Canal” de registro de la novedad sea “Presencial”:
	--SELECT @bMotivoFallecimientoCanalPresencial = 0 --(Core)
	SELECT @bMotivoFallecimientoCanalPresencial = CASE WHEN 
	EXISTS(  
		SELECT 1
		FROM dbo.Beneficiario ben		
		INNER JOIN dbo.SolicitudNovedadPersona snp ON (snp.snpBeneficiario = ben.benId)
		INNER JOIN dbo.SolicitudNovedad sn ON (snp.snpSolicitudNovedad = sn.snoId)
		INNER JOIN dbo.Solicitud sol ON (sn.snoSolicitudGlobal = sol.solId) 
		WHERE ben.benId = @iIdBeneficiario
		AND sol.solCanalRecepcion IN ('PRESENCIAL')
		AND sol.solResultadoProceso IN ('APROBADA')
		AND sol.solTipoTransaccion IN ('REPORTE_FALLECIMIENTO_PERSONAS_DEPWEB','REPORTE_FALLECIMIENTO_PERSONAS','REPORTE_FALLECIMIENTO_PERSONAS_WEB')
	) THEN 0 ELSE 1 END

END ; 