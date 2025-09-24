--liquibase formatted sql

--changeset arocha:01 runOnChange:true
--Creación del Function [dbo].[GetLocalDate]
IF (OBJECT_ID('GetLocalDate') IS NOT NULL)
	DROP FUNCTION [dbo].[GetLocalDate]
GO
-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Description:	
-- =============================================
CREATE FUNCTION [dbo].[GetLocalDate]
()
RETURNS DATETIME
AS
BEGIN
	DECLARE @LocalTime DATETIME
	SELECT @LocalTime = CONVERT(DATETIMEOFFSET, getutcdate()) AT TIME ZONE 'SA Pacific Standard Time'
	RETURN @LocalTime
END
GO