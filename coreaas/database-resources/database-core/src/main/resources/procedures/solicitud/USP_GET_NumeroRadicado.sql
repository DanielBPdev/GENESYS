-- =============================================
-- Author:		Juan Diego Ocampo Q
-- Create date: 2019/05/03
-- Author:		Francisco Alejandro Hoyos Rojas
-- Modified date: 2020/12/16
-- Description:	Procedimiento almacenado encargado 
-- calcular el número de radicado según cantidad solicitada
-- =============================================
CREATE PROCEDURE [dbo].[USP_GET_NumeroRadicado]
	@iCantidad INT,
	@iPrimerValor BIGINT OUTPUT,
	@sAnio VARCHAR(4) OUTPUT
AS
SET NOCOUNT ON;
BEGIN
	BEGIN TRAN T1;
		DECLARE @iNuevoValor BIGINT;
		DECLARE @range_first_value_output sql_variant;
		
		SET @sAnio = YEAR(dbo.GetLocalDate());
		
		IF (NOT EXISTS(
				SELECT * 
				FROM NumeroRadicado
				WHERE nraAnio = @sAnio
			)
		)
		BEGIN
			INSERT INTO NumeroRadicado ( nraAnio ) VALUES ( @sAnio );
			ALTER SEQUENCE Sec_NumeroRadicado RESTART WITH 0;
		END 
		
		EXEC sys.sp_sequence_get_range 
		@sequence_name = N'Sec_NumeroRadicado'
		, @range_size = @iCantidad
		, @sequence_increment = 1
		, @range_first_value = @range_first_value_output OUTPUT;

		SELECT  @iPrimerValor = CONVERT(BIGINT,@range_first_value_output);
		
	COMMIT TRAN T1;
	
	/*
	CREATE TABLE NumeroRadicado (
		nraId BIGINT IDENTITY,
		nraAnio VARCHAR(4)
	)

	CREATE SEQUENCE Sec_NumeroRadicado START WITH 0 INCREMENT BY 1
	
	
	validar servicio
	obtenerStickerCorrespondenciaFisica 
	
	*/
END;