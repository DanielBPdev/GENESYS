-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2017/06/16
-- Description:	Procedimiento almacenado ejecutado desde SSIS
-- para obtener los aportes del área de trabajo de PILA
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_GetAportesSSIS]
(
	@IdTransaccion BigInt
)
AS
BEGIN
SET NOCOUNT ON;
	
		declare @regId bigInt = -3
		set @regId = (select regId from staging.RegistroGeneral where regTransaccion = @IdTransaccion)

	DECLARE @TablaIndependientes AS TABLE (tipoCotizante VARCHAR(2))
	DECLARE @TablaClasificacion AS TABLE (regId BIGINT, esPensionado BIT, esIndependiente BIT)
	
	INSERT INTO @TablaIndependientes
	SELECT Data FROM dbo.Split( (
		SELECT stpValorParametro FROM staging.StagingParametros 
		WHERE stpNombreParametro = 'SOLICITANTE_INDEPENDIENTE') ,',');
	
	INSERT INTO @TablaClasificacion (regId, esPensionado, esIndependiente)
	SELECT reg.regId, reg.regEsAportePensionados, CASE WHEN ind.tipoCotizante IS NOT NULL THEN 1 ELSE 0 END esIndependiente
	FROM staging.RegistroGeneral reg with (nolock)
	INNER JOIN staging.RegistroDetallado red with (nolock) ON red.redRegistroGeneral = reg.regId
	LEFT JOIN @TablaIndependientes ind ON red.redTipoCotizante = ind.tipoCotizante
	WHERE --reg.regTransaccion = @IdTransaccion
	reg.regId = @regId
	GROUP BY reg.regId, reg.regEsAportePensionados, CASE WHEN ind.tipoCotizante IS NOT NULL THEN 1 ELSE 0 END;

	WITH cteRegistroTipoPlanilla
	AS
	(
		SELECT regId
		,regTipoIdentificacionAportante
		,regNumeroIdentificacionAportante
		,CONVERT(DATE,regPeriodoAporte+'-01',120) PeriodoAporte,
		regPeriodoAporte PeriodoAporteYYYMM,
		CASE	WHEN reg.regEsAportePensionados = 1 THEN 'PENSIONADO' 
				ELSE (
					CASE 
						WHEN (EXISTS (SELECT TOP 1 1 FROM @TablaClasificacion cla WHERE cla.regId = reg.regId AND cla.esIndependiente = 1)) 
							AND (EXISTS (SELECT TOP 1 1 FROM @TablaClasificacion cla WHERE cla.regId = reg.regId AND cla.esIndependiente = 0)) 
							THEN 'MIXTA' 
						WHEN (EXISTS (SELECT TOP 1 1 FROM @TablaClasificacion cla WHERE cla.regId = reg.regId AND cla.esIndependiente = 1)) 
							AND (NOT EXISTS (SELECT TOP 1 1 FROM @TablaClasificacion cla WHERE cla.regId = reg.regId AND cla.esIndependiente = 0)) 
							THEN 'INDEPENDIENTE'
						ELSE 'DEPENDIENTE' END ) END TipoPlanilla
		FROM staging.RegistroGeneral reg with (nolock)
		--WHERE reg.regTransaccion = @IdTransaccion
		where reg.regId = @regId
	)

	SELECT DISTINCT PeriodoAporte, PeriodoAporteYYYMM, regTipoIdentificacionAportante TipoIdentificacionAportante, regNumeroIdentificacionAportante NumeroIdentificacionAportante, 
	ISNULL(redTipoIdentificacionCotizante,'') TipoIdentificacionCotizante, ISNULL(redNumeroIdentificacionCotizante,'') NumeroIdentificacionCotizante,
	TipoPlanilla, 
	CASE TipoPlanilla WHEN 'PENSIONADO' THEN 'PENSIONADO' 
					  WHEN 'DEPENDIENTE' THEN (CASE WHEN redTipoCotizante IN (SELECT tipoCotizante FROM @TablaIndependientes) THEN 'TRABAJADOR_INDEPENDIENTE' ELSE 'TRABAJADOR_DEPENDIENTE' END) 
					  WHEN 'MIXTA' THEN (CASE WHEN redTipoCotizante IN (SELECT tipoCotizante FROM @TablaIndependientes) THEN 'TRABAJADOR_INDEPENDIENTE' ELSE 'TRABAJADOR_DEPENDIENTE' END) 
					  WHEN 'INDEPENDIENTE' THEN 'TRABAJADOR_INDEPENDIENTE'
					  END TipoCotizante,
	@IdTransaccion IdTransaccion
	FROM cteRegistroTipoPlanilla reg
	LEFT JOIN staging.RegistroDetallado red with (nolock) ON reg.regId = red.redRegistroGeneral
	ORDER BY PeriodoAporte, TipoIdentificacionAportante, NumeroIdentificacionAportante, TipoIdentificacionCotizante, NumeroIdentificacionCotizante;

END;