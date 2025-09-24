-- =============================================
-- Author:		Alfonso Baquero Echeverry
-- Create date: 2018/05/02
-- Description:	Procedimiento almacenado encargado aplicar la marca de novedad
-- futura en el registro general
-- =============================================
CREATE PROCEDURE [dbo].[USP_AsignarMarcaNovedadFutura]
	@IdRegistroGeneral BIGINT,
	@FechaReferenciaNovedadFutura DATE = NULL
	
AS
BEGIN
SET NOCOUNT ON;	
	--print 'Inicia USP_AsignarMarcaNovedadFutura'
	
	IF @FechaReferenciaNovedadFutura IS NULL
	BEGIN
		SET @FechaReferenciaNovedadFutura = dbo.GetLocalDate()
	END

	DECLARE @periodosRegulares AS TABLE (
		idRD BIGINT,
		periodoRegular VARCHAR(7)
	)

	INSERT INTO @periodosRegulares (idRD, periodoRegular)
	SELECT redId, 
		CASE 
			WHEN ISNULL(redOUTPeriodicidad, 'MES_VENCIDO') = 'MES_VENCIDO' 
			THEN CONVERT(VARCHAR(7), DATEADD(MONTH, -1, @FechaReferenciaNovedadFutura), 121)
			ELSE CONVERT(VARCHAR(7), @FechaReferenciaNovedadFutura, 121)
		END periodo
	FROM staging.RegistroDetallado with(nolock)
	WHERE redRegistroGeneral = @IdRegistroGeneral

	BEGIN TRAN
	UPDATE reg
	SET reg.regNovedadFutura = CASE WHEN regPeriodoAporte > periodoRegular THEN 1 ELSE 0 END,
		reg.regOUTNovedadFuturaProcesada = CASE WHEN regPeriodoAporte >= periodoRegular THEN 0 ELSE NULL END,
		reg.regDateTimeUpdate = dbo.getLocalDate()
	FROM staging.RegistroGeneral reg with(nolock)
	INNER JOIN staging.RegistroDetallado red with(nolock) ON red.redRegistroGeneral = reg.regId
	INNER JOIN @periodosRegulares pr ON red.redId = pr.idRD
	WHERE reg.regId = @IdRegistroGeneral
	AND (
		red.redNovIGE IS NOT NULL
		OR red.redNovIngreso IS NOT NULL
		OR red.redNovLMA IS NOT NULL
		OR red.redNovRetiro IS NOT NULL
		OR red.redNovSLN IS NOT NULL
		OR red.redNovSUS IS NOT NULL
		OR red.redNovVACLR IS NOT NULL
		OR red.redNovVSP IS NOT NULL
		OR red.redNovVST IS NOT NULL
		OR ISNULL(red.redDiasIRL, 0) > 0
	);
	COMMIT;

	--print 'Finaliza USP_AsignarMarcaNovedadFutura'
END;