--liquibase formatted sql

--changeset arocha:01 runOnChange:true
--Creación del Function [dbo].[GetWaterMarkedRow]


IF (OBJECT_ID('GetWaterMarkedRow') IS NOT NULL)
	DROP FUNCTION [dbo].[GetWaterMarkedRow]
GO

CREATE FUNCTION dbo.GetWaterMarkedRow
(
	@sTabla VARCHAR(100),
	@iId BIGINT,
	@dFechaHistorico DATETIME = NULL
)
RETURNS NVARCHAR(MAX)
AS 

BEGIN
	DECLARE @sJsonValueFull NVARCHAR(MAX)
	DECLARE @sJsonValueReturn NVARCHAR(MAX)

	SELECT @sJsonValueFull = COALESCE(@sJsonValueFull,'') + ISNULL(wmr.wmrJsonNewValue,'')
	FROM dbo.WaterMarkedRows wmr
	WHERE wmr.wmrTable = @sTabla
	AND wmr.wmrKeyRowValue = @iId

	SET @sJsonValueFull = REPLACE (@sJsonValueFull,'][',',')

	IF @dFechaHistorico IS NOT NULL
	BEGIN
		SELECT @sJsonValueReturn = j.[value] 
		FROM OPENJSON (@sJsonValueFull) j
		WHERE j.[key] IN (
			SELECT MAX(j1.[key])
			FROM OPENJSON (@sJsonValueFull) j1
			WHERE CAST(JSON_VALUE(j1.[value], '$.revTime') AS DATETIME) <= @dFechaHistorico
		)
	END
	ELSE
	BEGIN
		SET @sJsonValueReturn = @sJsonValueFull
	END
	
	RETURN @sJsonValueReturn
END