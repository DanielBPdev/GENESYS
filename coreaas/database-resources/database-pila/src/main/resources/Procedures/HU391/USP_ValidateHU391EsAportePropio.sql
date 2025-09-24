-- =============================================
-- Author:		Alfonso Baquero Echeverry
-- Create date: 2018/01/19
-- Description:	Procedimiento almacenado que evalua sí un archivo
-- PILA es un aporte propio
-- =============================================
CREATE PROCEDURE [dbo].[USP_ValidateHU391EsAportePropio] (
	@iIdIndicePlanilla BIGINT,
	@bEsAportePropio BIT = 0 OUTPUT
)
AS
BEGIN
SET NOCOUNT ON;
	SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED

	DECLARE @tipoArchivo VARCHAR(75)
	DECLARE @iCantidadRegistros2 INT
	DECLARE @iIdAportante VARCHAR(16)

	CREATE TABLE #cotizantes (idCotizante VARCHAR(16))
      
	SELECT @tipoArchivo = i.pipTipoArchivo FROM dbo.PilaIndicePlanilla AS i WHERE i.pipId = @iIdIndicePlanilla

	IF @tipoArchivo = 'ARCHIVO_OI_I' OR @tipoArchivo = 'ARCHIVO_OI_IR'
	BEGIN
			SELECT @iIdAportante = i1.pi1IdAportante FROM dbo.PilaArchivoIRegistro1 AS i1 WHERE i1.pi1IndicePlanilla = @iIdIndicePlanilla
			SELECT @iCantidadRegistros2 = COUNT(*) FROM dbo.PilaArchivoIRegistro2 AS i2 WHERE i2.pi2IndicePlanilla = @iIdIndicePlanilla

			INSERT INTO #cotizantes (idCotizante)
			SELECT i2.pi2IdCotizante
			FROM dbo.PilaArchivoIRegistro2 AS i2 
			WHERE i2.pi2IndicePlanilla = @iIdIndicePlanilla
	END 
	ELSE IF @tipoArchivo = 'ARCHIVO_OI_IP' OR @tipoArchivo = 'ARCHIVO_OI_IPR'
	BEGIN
			SELECT @iIdAportante = ip1.ip1IdPagador FROM dbo.PilaArchivoIPRegistro1 AS ip1 WHERE ip1.ip1IndicePlanilla = @iIdIndicePlanilla
			SELECT @iCantidadRegistros2 = COUNT(*) FROM dbo.PilaArchivoIPRegistro2 AS ip2 WHERE ip2.ip2IndicePlanilla = @iIdIndicePlanilla
        
			INSERT INTO #cotizantes (idCotizante)
			SELECT ip2.ip2IdPensionado
			FROM dbo.PilaArchivoIPRegistro2 AS ip2 
			WHERE ip2.ip2IndicePlanilla = @iIdIndicePlanilla
	END

	IF @iCantidadRegistros2 = 1 AND EXISTS (
		SELECT TOP 1 idCotizante 
		FROM #cotizantes
		WHERE idCotizante = @iIdAportante
	)
	BEGIN
		SET @bEsAportePropio = 1
	END
	ELSE
	BEGIN
		SET @bEsAportePropio = 0
	END

	DROP TABLE #cotizantes;
END;