-- =============================================
-- Author:		Alfonso Baquero Echeverry
-- Create date: 2018/05/08
-- Description:	Procedimiento almacenado para verificar sí un cotizante ha causado
-- subsidio monetario en un período específico
-- =============================================
CREATE PROCEDURE [dbo].[USP_SM_GET_InsertPeriodo]	
AS
BEGIN
	DECLARE @periodoActual DATE = DATEFROMPARTS(YEAR(dbo.getlocaldate()),MONTH(dbo.getlocaldate()),1)

	IF NOT EXISTS (SELECT 1 FROM dbo.Periodo WHERE priPeriodo = @periodoActual)
	BEGIN
		INSERT dbo.Periodo VALUES (@periodoActual)
	END
END ; 