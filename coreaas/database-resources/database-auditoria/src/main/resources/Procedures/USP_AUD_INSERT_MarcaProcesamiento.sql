-- =============================================
-- Author:		Andres Rocha
-- Create date: 2018/05/23
-- Description:	
-- =============================================
CREATE PROCEDURE [dbo].[USP_AUD_INSERT_MarcaProcesamiento]
AS
	print 'Inicia USP_AUD_INSERT_MarcaProcesamiento'	
BEGIN TRY	
	 INSERT MarcaProcesamiento (mapFechaUltimaEjecucionUTC) VALUES (GETUTCDATE());
END TRY
BEGIN CATCH
	THROW
END CATCH	
	print 'Finaliza USP_AUD_INSERT_MarcaProcesamiento'
;