--liquibase formatted sql

--changeset fvasquez:01 runOnChange:true runAlways:true
--Creación del Function [dbo].[UFN_GenerarNumeroOperacionCartera] 

/****** Object:  UserDefinedFunction [dbo].[UFN_GenerarNumeroOperacionCartera]   ******/
IF (OBJECT_ID('UFN_GenerarNumeroOperacionCartera') IS NOT NULL)
	DROP FUNCTION [dbo].[UFN_GenerarNumeroOperacionCartera]
GO

-- =============================================
-- Author:		Ferney Alonso Vásquez Benavides
-- Create date: 2018/11/01
-- Description:	Función que genera un número de operación para un aportante registrado en cartera. Si el número de operación existe, lo retorna; sino, genera y retorna un nuevo número de operación
-- HU-164
-- =============================================
CREATE FUNCTION [dbo].[UFN_GenerarNumeroOperacionCartera](	
	@idCartera BIGINT		-- Identificador del registro en cartera
)  
RETURNS BIGINT  
AS  
BEGIN
	DECLARE @idPersona BIGINT
	DECLARE @tipoAportante VARCHAR(13)
	DECLARE @lineaCobro VARCHAR(3)
	DECLARE @periodo VARCHAR(7)
	DECLARE @numeroOperacion BIGINT
	
	IF @idCartera IS NOT NULL
	BEGIN
		SELECT @idPersona = carPersona, @tipoAportante = carTipoSolicitante, @lineaCobro = carTipoLineaCobro, @periodo = CONVERT(VARCHAR(7), carPeriodoDeuda, 120)
		FROM Cartera
		WHERE carId = @idCartera	
				
		SELECT @numeroOperacion = cag.cagNumeroOperacion 
		FROM CarteraAgrupadora cag
		JOIN Cartera car ON car.carId = cag.cagCartera
		WHERE car.carPersona = @idPersona
			AND car.carTipoSolicitante = @tipoAportante
			AND car.carTipoLineaCobro = @lineaCobro
			AND CONVERT(VARCHAR(7), carPeriodoDeuda, 120) = @periodo
			
		IF @numeroOperacion IS NULL -- No existe un número de operación asociado al registro en cartera
		BEGIN
			SELECT @numeroOperacion = cag.cagNumeroOperacion 
			FROM CarteraAgrupadora cag
			JOIN Cartera car ON car.carId = cag.cagCartera
			WHERE car.carPersona = @idPersona
				AND car.carTipoSolicitante = @tipoAportante
				AND car.carTipoLineaCobro = @lineaCobro
				AND car.carEstadoOperacion = 'VIGENTE'
				
			IF @numeroOperacion IS NULL -- No existe el número de operación para el aportante en la línea de cobro
			BEGIN
				SELECT @numeroOperacion = 1+ISNULL(MAX(cagNumeroOperacion),0) 
				FROM CarteraAgrupadora
			END
		END		
	END	
	
	RETURN @numeroOperacion
END