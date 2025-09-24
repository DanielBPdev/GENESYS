-- =============================================
-- Author:		Juan David Alberto Quintero
-- Create date: 2022/11/03
-- Description:	Procedimiento almacenado encargado de 
-- obtener los detalles de los archivos de subsidio monetario
-- por prescripción de GLPI 57373
-- =============================================
CREATE PROCEDURE [dbo].[USP_SM_GET_ListadoArchivoTransDetaSubsidio]
	@offset  INT,						--- Indica la posición desde donde se empezarán a retornar registros
	@orderBy VARCHAR(500),				--- Indica el ordenamiento de la consulta base
	@limit INT,							--- Indica cuantos registros se deben traer
	@totalRegistros BIGINT OUT

AS
BEGIN
	DECLARE @sqlTemp NVARCHAR(MAX);
	IF  @orderBy = ''
	BEGIN
		SET @orderBy = 'atdsFechaGeneracion';
	END

	SET @sqlTemp = N'SELECT * FROM ArchivoTransDetaSubsidio ORDER BY ' + @orderBy + N' OFFSET @offset ROWS FETCH NEXT @limit ROWS ONLY';
	EXEC sp_executesql @sqlTemp,
	N'@offset INT, @limit INT',
	@offset = @offset,
	@limit = @limit;

	SET @totalRegistros = (SELECT COUNT(atdsId) FROM ArchivoTransDetaSubsidio);

END;