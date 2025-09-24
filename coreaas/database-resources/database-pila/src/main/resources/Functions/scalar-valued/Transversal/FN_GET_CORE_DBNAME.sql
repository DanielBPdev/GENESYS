--liquibase formatted sql

--changeset arocha:01 runOnChange:true
--Creación del Function [dbo].[FN_GET_CORE_DBNAME]
IF (OBJECT_ID('FN_GET_CORE_DBNAME') IS NOT NULL)
	DROP FUNCTION [dbo].[FN_GET_CORE_DBNAME]
GO
-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2017/11/14
-- Description:	Obtiene el nombre de la base de datos de core
-- =============================================
CREATE FUNCTION FN_GET_CORE_DBNAME
(
	@sUSERNAME VARCHAR(255)
)
RETURNS VARCHAR(255)
AS
BEGIN
DECLARE @DBNAME AS VARCHAR(255)

IF @sUSERNAME LIKE 'pila%'
BEGIN
	IF @sUSERNAME LIKE '%_aplicacion'
	BEGIN
		SET @DBNAME = 'core'
	END
	ELSE
	BEGIN
		SELECT @DBNAME = 'core_' + Data
   		FROM dbo.Split(@sUSERNAME, '_')
   		WHERE Id  = 2
  	END
END
ELSE
BEGIN 
	SET @DBNAME = 'core'
END

RETURN @DBNAME
END
;