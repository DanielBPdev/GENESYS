--liquibase formatted sql

--changeset dsuesca:01 runOnChange:true runAlways:true
--Creación del Function [dbo].[UFN_ObtenerProgreso590] 

/****** Object:  UserDefinedFunction [dbo].[UFN_ObtenerProgreso590]   ******/
IF (OBJECT_ID('UFN_ObtenerProgreso590') IS NOT NULL)
	DROP FUNCTION [dbo].[UFN_ObtenerProgreso590]
GO

-- =============================================
-- Author:		Diego Alfredo Suesca Rodríguez
-- Create date: 2018/08/17
-- Description:	Función que calcula el progreso del beneficio 590
-- HU164
-- =============================================
CREATE FUNCTION UFN_ObtenerProgreso590(
	@bemFechaVinculacion DATETIME
)
RETURNS varchar(1)
AS
BEGIN
	DECLARE @progreso varchar(1)	
	SELECT @progreso
	= CASE WHEN dbo.GetLocalDate() BETWEEN @bemFechaVinculacion AND DATEADD (YEAR ,1, @bemFechaVinculacion) THEN '2'
			WHEN dbo.GetLocalDate() BETWEEN DATEADD (YEAR ,1, @bemFechaVinculacion) AND DATEADD (YEAR ,2, @bemFechaVinculacion) THEN '3'
			WHEN dbo.GetLocalDate() BETWEEN DATEADD (YEAR ,2, @bemFechaVinculacion) AND DATEADD (YEAR ,3, @bemFechaVinculacion) THEN '4'
			ELSE '6'
		END
	RETURN @progreso
END
;
