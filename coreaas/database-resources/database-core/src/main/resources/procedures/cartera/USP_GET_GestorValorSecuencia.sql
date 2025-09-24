-- =============================================
-- Author:		Alfonso Baquero E.
-- Create date: 2019/02/21
-- Author:		Francisco Alejandro Hoyos Rojas
-- Modified date: 2020/12/16
-- Description:	Procedimiento almacenado encargado de de la gestión de 
-- toma de valores de secuencia en la BD
-- =============================================
CREATE PROCEDURE dbo.USP_GET_GestorValorSecuencia 
	@iCantidadValores INT, 
	@sNombreSecuencia VARCHAR(50),
	@iPrimerValor BIGINT OUTPUT
AS
IF  @iCantidadValores=0
	BEGIN
	SET  @iCantidadValores=1
	END
BEGIN
	DECLARE @iNuevoValor BIGINT
	DECLARE @range_first_value_output sql_variant;

	EXEC sys.sp_sequence_get_range 
		@sequence_name = @sNombreSecuencia
		, @range_size = @iCantidadValores
		, @sequence_increment = 1
		, @range_first_value = @range_first_value_output OUTPUT;

	SELECT @iPrimerValor = CONVERT(BIGINT,@range_first_value_output);
END;