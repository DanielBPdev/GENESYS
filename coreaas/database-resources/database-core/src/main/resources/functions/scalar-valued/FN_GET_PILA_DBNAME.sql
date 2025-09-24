--liquibase formatted sql

--changeset abaquero:01 runOnChange:true runAlways:true
--Creación del Function [dbo].[FN_GET_PILA_DBNAME]

/****** Object:  UserDefinedFunction [dbo].[FN_GET_PILA_DBNAME]   ******/
IF (OBJECT_ID('FN_GET_PILA_DBNAME') IS NOT NULL)
	DROP FUNCTION [dbo].[FN_GET_PILA_DBNAME]
GO

-- =============================================
-- Author:		Alfonso Baquero Echeverry
-- Create date: 2018/05/31
-- Description:	Obtiene el nombre de la base de datos de core
-- =============================================
CREATE FUNCTION FN_GET_PILA_DBNAME
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
		SET @DBNAME = 'pila'
	END
	ELSE
	BEGIN
		SELECT @DBNAME = 'pila_' + Data
   		FROM dbo.Split(@sUSERNAME, '_')
   		WHERE Id  = 2
  	END
END
ELSE
BEGIN 
	SET @DBNAME = 'pila'
END

RETURN @DBNAME
END
;