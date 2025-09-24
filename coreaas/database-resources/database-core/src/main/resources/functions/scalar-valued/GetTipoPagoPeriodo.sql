--liquibase formatted sql

--changeset dsuesca:01 runOnChange:true
--Creación del Function [dbo].[GetTipoPagoPeriodo]


IF (OBJECT_ID('GetTipoPagoPeriodo') IS NOT NULL)
	DROP FUNCTION [dbo].[GetTipoPagoPeriodo]
GO

CREATE FUNCTION [dbo].[GetTipoPagoPeriodo]
(
	@sApdTipoCotizante VARCHAR(100),
	@dApgFechaRecaudo DATE,
	@sApgPeriodoAporte VARCHAR(7)
)
RETURNS VARCHAR(19)
AS
BEGIN
	DECLARE @periodoAporte DATE
	DECLARE @STipoPagoPeriodo VARCHAR(19)

	SET @periodoAporte = CAST(@sApgPeriodoAporte + '-01' AS DATE)

	IF dbo.GetPeriodoRegular(@sApdTipoCotizante,@dApgFechaRecaudo) = @periodoAporte
	BEGIN
		SET @STipoPagoPeriodo = 'PERIODO_REGULAR'
	END

	IF dbo.GetPeriodoRegular(@sApdTipoCotizante,@dApgFechaRecaudo) < @periodoAporte
	BEGIN
		SET @STipoPagoPeriodo = 'PERIODO_RETROACTIVO'
	END

	IF dbo.GetPeriodoRegular(@sApdTipoCotizante,@dApgFechaRecaudo) > @periodoAporte
	BEGIN
		SET @STipoPagoPeriodo = 'PERIODO_FUTURO'
	END

	RETURN @STipoPagoPeriodo
END
;