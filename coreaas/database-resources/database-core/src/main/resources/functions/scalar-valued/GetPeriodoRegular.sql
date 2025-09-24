--liquibase formatted sql

--changeset dsuesca:01 runOnChange:true
--Creación del Function [dbo].[GetPeriodoRegular]


IF (OBJECT_ID('GetPeriodoRegular') IS NOT NULL)
	DROP FUNCTION [dbo].[GetPeriodoRegular]
GO

CREATE FUNCTION [dbo].[GetPeriodoRegular]
(
	@sApdTipoCotizante VARCHAR(100),
	@dApgFechaRecaudo DATE
)
RETURNS DATE
AS
BEGIN
	DECLARE @periodoRegular DATE

	IF @sApdTipoCotizante IN ('TRABAJADOR_INDEPENDIENTE', 'PENSIONADO')
	BEGIN
		SET @periodoRegular = DATEFROMPARTS(YEAR(@dApgFechaRecaudo),MONTH(@dApgFechaRecaudo),1)
	END
	ELSE
	BEGIN
		SET @periodoRegular = DATEADD(MONTH,-1,@dApgFechaRecaudo)
		SET @periodoRegular = DATEFROMPARTS(YEAR(@periodoRegular),MONTH(@periodoRegular),1)
	END

	RETURN @periodoRegular
END
;