/****** Object:  UserDefinedFunction [dbo].[UFN_ObtenerProgreso590]    Script Date: 12/05/2023 11:06:50 a. m. ******/
/****** Object:  UserDefinedFunction [dbo].[UFN_ObtenerProgreso590]    Script Date: 27/04/2023 2:28:35 p. m. ******/
 

CREATE OR ALTER   FUNCTION [dbo].[UFN_ObtenerProgreso590](
	@bemFechaVinculacion DATETIME  
	 
)  
RETURNS SMALLINT  
AS  
BEGIN
	DECLARE @progreso SMALLINT
	 
		BEGIN
			SELECT @progreso = CASE WHEN dbo.GetLocalDate() 
			                        BETWEEN @bemFechaVinculacion AND DATEADD (YEAR ,2, @bemFechaVinculacion) THEN '2'
									WHEN dbo.GetLocalDate() 
									BETWEEN DATEADD (YEAR ,2, @bemFechaVinculacion) AND DATEADD (YEAR ,3, @bemFechaVinculacion) THEN '3'
									WHEN dbo.GetLocalDate() BETWEEN DATEADD (YEAR ,3, @bemFechaVinculacion) AND DATEADD (YEAR ,4, @bemFechaVinculacion) THEN '4'
									WHEN dbo.GetLocalDate() BETWEEN DATEADD (YEAR ,4, @bemFechaVinculacion) AND DATEADD (YEAR ,5, @bemFechaVinculacion) THEN '5'
									WHEN dbo.GetLocalDate() > DATEADD (YEAR ,5, @bemFechaVinculacion) THEN '5'
								END
		END
  		
	RETURN @progreso
END