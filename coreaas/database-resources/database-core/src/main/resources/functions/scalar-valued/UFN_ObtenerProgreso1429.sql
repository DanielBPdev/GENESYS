--liquibase formatted sql

--changeset dsuesca:01 runOnChange:true runAlways:true
--Creación del Function [dbo].[UFN_ObtenerProgreso1429] 

/****** Object:  UserDefinedFunction [dbo].[UFN_ObtenerProgreso1429]   ******/
IF (OBJECT_ID('UFN_ObtenerProgreso1429') IS NOT NULL)
	DROP FUNCTION [dbo].[UFN_ObtenerProgreso1429]
GO

-- =============================================
-- Author:		Diego Alfredo Suesca Rodríguez
-- Create date: 2018/08/17
-- Description:	Función que calcula el progreso del beneficio 1492
-- HU164
-- =============================================
CREATE FUNCTION UFN_ObtenerProgreso1429(
	@bemFechaVinculacion DATETIME, 
	@depNombre VARCHAR(100)
)  
RETURNS SMALLINT  
AS  
BEGIN
	DECLARE @progreso SMALLINT
	IF @bemFechaVinculacion IS NOT NULL AND @depNombre IS NOT NULL
	BEGIN
		IF @depNombre = 'AMAZONAS' OR @depNombre = 'GUAINÍA' OR @depNombre = 'VAUPÉS'
		BEGIN
			SELECT @progreso = CASE WHEN dbo.GetLocalDate() BETWEEN @bemFechaVinculacion AND DATEADD (YEAR ,8, @bemFechaVinculacion) THEN '1'
									WHEN dbo.GetLocalDate() BETWEEN DATEADD (YEAR ,8, @bemFechaVinculacion) AND DATEADD (YEAR ,9, @bemFechaVinculacion) THEN '3'
									WHEN dbo.GetLocalDate() BETWEEN DATEADD (YEAR ,9, @bemFechaVinculacion) AND DATEADD (YEAR ,10, @bemFechaVinculacion) THEN '4'
									WHEN dbo.GetLocalDate() > DATEADD (YEAR ,10, @bemFechaVinculacion) THEN '5'
								END
		END
		ELSE
		BEGIN
			SELECT @progreso = CASE WHEN dbo.GetLocalDate() BETWEEN @bemFechaVinculacion AND DATEADD (YEAR ,2, @bemFechaVinculacion) THEN '1'
									WHEN dbo.GetLocalDate() BETWEEN DATEADD (YEAR ,2, @bemFechaVinculacion) AND DATEADD (YEAR ,3, @bemFechaVinculacion) THEN '2'
									WHEN dbo.GetLocalDate() BETWEEN DATEADD (YEAR ,3, @bemFechaVinculacion) AND DATEADD (YEAR ,4, @bemFechaVinculacion) THEN '3'
									WHEN dbo.GetLocalDate() BETWEEN DATEADD (YEAR ,4, @bemFechaVinculacion) AND DATEADD (YEAR ,5, @bemFechaVinculacion) THEN '4'
									WHEN dbo.GetLocalDate() > DATEADD (YEAR ,5, @bemFechaVinculacion) THEN '5'
								END
		END
	END	 		
	RETURN @progreso
END
;
