-- =============================================
-- Author:		Andres Rocha
-- Create date: 2018/05/23
-- Description:	
-- =============================================
CREATE PROCEDURE [dbo].[USP_AUD_DELETE_MarcaProcesamiento]
AS
	print 'Inicia USP_AUD_DELETE_MarcaProcesamiento'	
BEGIN TRY	
	 DELETE FROM MarcaProcesamiento;
END TRY
BEGIN CATCH
	THROW
END CATCH	
	print 'Finaliza USP_AUD_DELETE_MarcaProcesamiento'
;