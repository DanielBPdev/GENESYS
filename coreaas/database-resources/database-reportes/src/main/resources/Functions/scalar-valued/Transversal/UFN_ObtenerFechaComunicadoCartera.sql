--liquibase formatted sql

--changeset fvasquez:01 runOnChange:true runAlways:true
--Creación del Function [dbo].[UFN_ObtenerFechaComunicadoCartera] 

/****** Object:  UserDefinedFunction [dbo].[UFN_ObtenerFechaComunicadoCartera]   ******/
IF (OBJECT_ID('UFN_ObtenerFechaComunicadoCartera') IS NOT NULL)
	DROP FUNCTION [dbo].[UFN_ObtenerFechaComunicadoCartera]
GO

-- =============================================
-- Author:		Ferney Alonso Vásquez Benavides
-- Create date: 2018/02/21
-- Description:	Función que obtiene la fecha de envío o entrega de un comunicado asociado a una gestión de cobro 
-- HU164
-- =============================================
CREATE FUNCTION [dbo].[UFN_ObtenerFechaComunicadoCartera](
	@tipoAccionCobro VARCHAR(4), 	-- Tipo de acción de cobro
	@tipoFecha VARCHAR(20),  		-- Indica si la fecha a consultar es de envío o de entrega. Posibles valores: FECHA_ENVIO | FECHA_ENTREGA
	@idCartera BIGINT				-- Identificador del registro en cartera
)  
RETURNS DATETIME  
AS  
BEGIN
	DECLARE @numeroOperacion BIGINT
	
	DECLARE @fecha DATE
	
	DECLARE @resultado VARCHAR(20)
	
	
	SELECT @numeroOperacion = cag.cagNumeroOperacion
	FROM CarteraAgrupadora cag
	JOIN Cartera car ON car.carId = cag.cagCartera
	WHERE car.carId = @idCartera
	
	SET @resultado = 'ENVIADO'
	
	IF @tipoFecha = 'FECHA_ENTREGA'
	BEGIN
		SET @resultado = 'EXITOSO'
	END
	
	SELECT @fecha = bca.bcaFecha
	FROM BitacoraCartera bca
	WHERE bca.bcaActividad = @tipoAccionCobro
		AND bca.bcaNumeroOperacion = @numeroOperacion
		AND bca.bcaResultado = @resultado
	
	RETURN @fecha
END