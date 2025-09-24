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
-- Update date: 2023/01/02 NICOLAS JARAMILLO
-- Description:	Función que obtiene la fecha de envío o entrega de un comunicado asociado a una gestión de cobro
-- HU164
-- =============================================

ALTER FUNCTION [dbo].[UFN_ObtenerFechaComunicadoCartera](
	@tipoAccionCobro VARCHAR(4),
	@tipoFecha VARCHAR(20),
	@idCartera BIGINT
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

		IF @tipoAccionCobro = 'E2'
			BEGIN
				SET @resultado = 'PUBLICADO'
			END
		IF @tipoAccionCobro = 'CC1'
			BEGIN
				DECLARE @actividad varchar(50)
				SELECT @actividad = acdComunicado
				FROM AccionCobro1D

				IF @actividad = 'FIRMEZA_TITULO_EJECUTIVO'
					BEGIN
						SELECT @fecha = bca.bcaFecha
						FROM BitacoraCartera bca
						WHERE bca.bcaActividad = @actividad
						  AND bca.bcaNumeroOperacion = @numeroOperacion
						  AND bca.bcaResultado = 'OTRO'

						RETURN @fecha
					END

				IF @actividad = 'GENERAR_LIQUIDACION'
					BEGIN
						SELECT @fecha = bca.bcaFecha
						FROM BitacoraCartera bca
						WHERE bca.bcaActividad = @actividad
						  AND bca.bcaNumeroOperacion = @numeroOperacion
						  AND bca.bcaResultado = 'ENVIADO'

						RETURN @fecha
					END
			END

		IF @tipoAccionCobro = 'DD2'
			BEGIN
				DECLARE @actividadF2 varchar(100)
				SELECT @actividadF2 = comunicado
				FROM AccionCobro2F

				IF @actividadF2 = 'FIRMEZA_TITULO_EJECUTIVO'
					BEGIN
						SELECT @fecha = bca.bcaFecha
						FROM BitacoraCartera bca
						WHERE bca.bcaActividad = @actividadF2
						  AND bca.bcaNumeroOperacion = @numeroOperacion
						  AND bca.bcaResultado = 'OTRO'

						RETURN @fecha
					END

				IF @actividadF2 = 'GENERAR_LIQUIDACION'
					BEGIN
						SELECT @fecha = bca.bcaFecha
						FROM BitacoraCartera bca
						WHERE bca.bcaActividad = @actividadF2
						  AND bca.bcaNumeroOperacion = @numeroOperacion
						  AND bca.bcaResultado = 'GENERADO_PERSONALMENTE'

						RETURN @fecha
					END
			END

		IF @tipoFecha = 'FECHA_ENTREGA' AND @tipoAccionCobro = 'C2'
			BEGIN
				SELECT @fecha = bca.bcaFecha
				FROM BitacoraCartera bca
				WHERE bca.bcaActividad = @tipoAccionCobro
				  AND bca.bcaNumeroOperacion = @numeroOperacion
				  AND bca.bcaResultado IN ('EXITOSO', 'NO_EXITOSO')
				RETURN @fecha
			END

		SELECT @fecha = bca.bcaFecha
		FROM BitacoraCartera bca
		WHERE bca.bcaActividad = @tipoAccionCobro
		  AND bca.bcaNumeroOperacion = @numeroOperacion
		  AND bca.bcaResultado = @resultado

		RETURN @fecha
	END