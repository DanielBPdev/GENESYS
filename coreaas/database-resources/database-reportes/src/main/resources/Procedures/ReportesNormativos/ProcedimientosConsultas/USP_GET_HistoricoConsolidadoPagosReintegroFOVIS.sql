/****** Object:  StoredProcedure [dbo].[USP_GET_HistoricoConsolidadoPagosReintegroFOVIS]    Script Date: 14/04/2023 9:09:05 a. m. ******/
/****** Object:  StoredProcedure [dbo].[USP_GET_HistoricoConsolidadoPagosReintegroFOVIS]    Script Date: 24/03/2023 8:52:10 a. m. ******/
/****** Object:  StoredProcedure [dbo].[USP_GET_HistoricoConsolidadoPagosReintegroFOVIS]    Script Date: 22/03/2023 4:09:39 p. m. ******/
-- =============================================
-- Author:		Fabian López
-- Modificado: Olga Vega 2023-03-22
-- Create date: 2020/03/06
-- Description:	Datos para reporte HistoricoConsolidadoPagosReintegroFOVIS
---reporte normativo 28
---EXEC [USP_GET_HistoricoConsolidadoPagosReintegroFOVIS] '2023-04-01','2023-04-14'
-- =============================================
create or ALTER     PROCEDURE [dbo].[USP_GET_HistoricoConsolidadoPagosReintegroFOVIS]
(
	@fechaInicio DATE,
	@fechaFin DATE,
	@historico BIT = NULL
)
AS
BEGIN TRY
	SET NOCOUNT ON;
	SET ANSI_NULLS ON
 
SET QUOTED_IDENTIFIER ON
 
 
	--IF @historico = 1
	BEGIN

		DELETE rno.HistoricoConsolidadoPagosReintegroFOVIS 
		--  WHERE hprFechaInicialReporte = @fechaInicio AND
		--		hprFechaFinalReporte = @fechaFin

		 INSERT rno.HistoricoConsolidadoPagosReintegroFOVIS (
				hprFechaHistorico,
				hprAnioVigenciaAsignacionSubsidio,
				hprCodigoTipoPlanVivienda,
				hprEstadoSubsidio,
				hprCantidadSubsidios,
				hprValorSubsidios,
				hprFechaInicialReporte,
				hprFechaFinalReporte)
		
				SELECT @fechaFin,
				hcmAnioVigenciaAsignacionSubsidio,
				CASE WHEN hcmModalidad = 'ADQUISICION_VIVIENDA_NUEVA_URBANA' THEN 1
				WHEN hcmModalidad = 'CONSTRUCCION_SITIO_PROPIO_URBANO' THEN 2
				WHEN hcmModalidad = 'MEJORAMIENTO_VIVIENDA_URBANA' THEN 3
				WHEN hcmModalidad = 'ADQUISICION_VIVIENDA_USADA_URBANA' THEN 4
				WHEN hcmModalidad = 'MEJORAMIENTO_VIVIENDA_SALUDABLE' THEN 5
				WHEN hcmModalidad = 'CONSTRUCCION_SITIO_PROPIO_RURAL' THEN 6
				WHEN hcmModalidad = 'ADQUISICION_VIVIENDA_NUEVA_RURAL' THEN 7
				WHEN hcmModalidad = 'MEJORAMIENTO_VIVIENDA_RURAL' THEN 8 END
			--	9 Arrendamiento 
				AS hcmCodigoTipoPlanVivienda,
				CASE hcmEstadoSubsidio
				WHEN 2 THEN 4
				WHEN 8 THEN 4
				WHEN 3 THEN 5
				WHEN 4 THEN 6
				WHEN 5 THEN 7
				WHEN 6 THEN 8
				WHEN 7 THEN 4
				ELSE 1
			    END AS hcmEstadoSubsidio,
				COUNT(1) AS cantidadSubsidios,
				CAST(SUM(hcmValorSubsidios)AS BIGINT) AS valorSubsidios,
				@fechaInicio,
				@fechaFin
		    FROM rno.HistoricoConsolidadoPagosReintegroMicroDatoFOVIS WITH(NOLOCK)
		   WHERE hcmFechaInicialReporte = @fechaInicio 
			 AND hcmFechaFinalReporte = @fechaFin
		GROUP BY hcmAnioVigenciaAsignacionSubsidio,
				 hcmCodigoTipoPlanVivienda,
				 hcmEstadoSubsidio,hcmModalidad



				 SELECT hprAnioVigenciaAsignacionSubsidio AS 'Año vigencia de Asignación del subsidio'  ,
						hprCodigoTipoPlanVivienda AS 'Código tipo plan de vivienda',
						hprEstadoSubsidio AS 'Estado del Subsidio',
						hprCantidadSubsidios AS 'Cantidad subsidios', 
						hprValorSubsidios AS 'Valor subsidios'
				   FROM rno.HistoricoConsolidadoPagosReintegroFOVIS WITH(NOLOCK)
	END
 
END TRY
BEGIN CATCH
	THROW;
END CATCH
;


 