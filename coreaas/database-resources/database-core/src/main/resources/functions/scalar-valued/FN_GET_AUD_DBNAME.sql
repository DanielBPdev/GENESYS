--liquibase formatted sql

--changeset abaquero:01 runOnChange:true runAlways:true
--Creación del Function [dbo].[FN_GET_AUD_DBNAME]

/****** Object:  UserDefinedFunction [dbo].[FN_GET_AUD_DBNAME]   ******/
IF (OBJECT_ID('FN_GET_AUD_DBNAME') IS NOT NULL)
	DROP FUNCTION [dbo].[FN_GET_AUD_DBNAME]
GO


-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2017/10/25
-- Description:	Obtiene el nombre de la base de datos de auditoria
-- =============================================
CREATE FUNCTION FN_GET_AUD_DBNAME
(
	@sUSERNAME VARCHAR(255)
)
RETURNS VARCHAR(255)
AS
BEGIN
DECLARE @DBNAME AS VARCHAR(255)

IF @sUSERNAME LIKE 'core%'
BEGIN
	IF @sUSERNAME LIKE '%_aplicacion'
	BEGIN
		SET @DBNAME = 'core_aud'
	END
	ELSE
	BEGIN
		SELECT @DBNAME = 'core_' + Data + '_aud'
		FROM dbo.Split(@sUSERNAME, '_')
		WHERE Id  = 2
	END
END
ELSE
BEGIN 
	SET @DBNAME = 'core_aud'
END
RETURN @DBNAME
END
;