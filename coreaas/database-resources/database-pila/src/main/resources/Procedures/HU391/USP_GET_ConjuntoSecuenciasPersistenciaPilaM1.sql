-- =============================================
-- Author:		Alfonso Baquero E.
-- Create date: 2018/11/23
-- Description:	Procedimiento almacenado encargado de consultar un conjunto de 
-- n?meros de secuencia para usar como ID para la persistencia de registros 
-- tipo 2 de I-IP y registro tipo 6 de F
-- =============================================
CREATE PROCEDURE dbo.USP_GET_ConjuntoSecuenciasPersistenciaPilaM1 
	@iCantidadValores INT, 
	@sNombreSecuencia VARCHAR(30),
	@iPrimerValor BIGINT OUTPUT
AS

BEGIN
SET NOCOUNT ON;
	DECLARE @range_first_value sql_variant,   
	   @range_first_value_output sql_variant
	
	EXEC sp_sequence_get_range  
	@sequence_name = @sNombreSecuencia
	, @range_size = @iCantidadValores 
	, @range_first_value = @range_first_value_output OUTPUT ;   
	
	SET @iPrimerValor = CONVERT(BIGINT, @range_first_value_output); 
END;