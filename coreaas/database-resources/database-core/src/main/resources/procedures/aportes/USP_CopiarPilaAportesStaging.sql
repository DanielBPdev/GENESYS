-- =============================================
-- Update:      Juan Diego Ocampos Q
-- Create date: 2020/09/15
-- Description:	Procedimiento almacenado encargado de poblar la tabla de Cotizante
-- =============================================
CREATE PROCEDURE [dbo].[USP_CopiarPilaAportesStaging]
	@IdTransaccion Bigint
AS
BEGIN
SET NOCOUNT ON;

    INSERT INTO pila.Aporte (appPeriodoAporte,appPeriodoAporteYYYYMM,appTipoIdentificacionAportante,appNumeroIdentificacionAportante,appTipoIdentificacionCotizante,appNumeroIdentificacionCotizante,appTipoPlanilla,appTipoCotizante,appTransaccion,appShardName)
    EXEC sp_execute_remote PilaReferenceData,
		N'EXEC USP_GetAportesSSIS @IdTransaccion',
	  	N'@IdTransaccion bigint',
 		@IdTransaccion=@IdTransaccion;
END;