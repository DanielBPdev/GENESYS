-- =============================================
-- Author:		Francisco Alejandro Hoyos Rojas
-- Create date: 2021/02/26
-- Description:	Procedimiento almacenado encargado de 
-- obtener el resumen de la lista de subsidos a anular
-- por prescripción o por venimiento HU-223 y HU-224
-- =============================================
CREATE PROCEDURE [dbo].[USP_SM_GET_ListadoAbonosPorVencimientoYPrescripcionResumen]
	@fechaActual DATE,					--- Desde el servicio que se invoca el SP se envia la fecha actual del sistema
	@dias INT,							--- Dias parametrizados por la CCF para anular un subsidio monetario
	@listaMediosDePago VARCHAR(54),		--- Medios de pagos por los cuales se realizara la anulación
	@offset  INT,						--- Indica la posición desde donde se empezarán a retornar registros
	@orderBy VARCHAR(500),				--- Indica el ordenamiento de la consulta base
	@limit INT,							--- Indica cuantos registros se deben traer
	@filtro INT,						--- Indica el criterio por el cual se va a filtrar LIQUIDACION_ASOCIADA 1, PERIODO_LIQUIDADO 2, TIPO_CUOTA 3, TIPO_LIQUIDACION 4, MEDIO_PAGO 5, SITO_DE_PAGO 6
	@primeraPeticion BIT,
	@totalRegistros BIGINT OUT
AS
 	SET NOCOUNT ON;

BEGIN

	DECLARE @sqlTemporal NVARCHAR(MAX);

	IF @filtro < 6
	BEGIN
		DECLARE @sqlBase1 NVARCHAR(MAX);
		---	Esta es la consulta base que se usa cuando se filtra por:
		---	LIQUIDACION_ASOCIADA
		--- PERIODO_LIQUIDADO
		--- TIPO_CUOTA
		--- TIPO_LIQUIDACION
		--- MEDIO_PAGO
		SET @sqlBase1 = 'SELECT 
						CASE WHEN @filtro = 1 THEN dsa.dsaSolicitudLiquidacionSubsidio  END AS solicitudLiquidacionSubsidio,
						CASE WHEN @filtro = 2 THEN dsa.dsaPeriodoLiquidado END AS periodoLiquidado,
						CASE WHEN @filtro = 3 THEN dsa.dsaTipoCuotaSubsidio  END AS tipoCuotaSubsidio,
						CASE WHEN @filtro = 4 THEN dsa.dsaTipoliquidacionSubsidio END AS tipoLiquidacion,
						CASE WHEN @filtro = 5 THEN cas.casMedioDePagoTransaccion END  AS medioDePago,
						SUM(dsa.dsaValorTotal) AS valorTotal
					FROM CuentaAdministradorSubsidio as cas 
					INNER JOIN DetalleSubsidioAsignado as dsa on cas.casId = dsa.dsaCuentaAdministradorSubsidio  
					WHERE  (SELECT DATEDIFF(DAY, cas.casFechaHoraTransaccion, @fechaActual)) > @dias
						AND  cas.casTipoTransaccionSubsidio = ''ABONO''
						AND  cas.casEstadoTransaccionSubsidio = ''APLICADO'' 
						AND  cas.casMedioDePagoTransaccion IN (SELECT value FROM STRING_SPLIT(@listaMediosDePago,'','')) 
					GROUP BY CASE WHEN @filtro = 1 THEN dsa.dsaSolicitudLiquidacionSubsidio END,
								CASE WHEN @filtro = 2 THEN dsa.dsaPeriodoLiquidado END,
								CASE WHEN @filtro = 3 THEN dsa.dsaTipoCuotaSubsidio END,
								CASE WHEN @filtro = 4 THEN dsa.dsaTipoliquidacionSubsidio END,
								CASE WHEN @filtro = 5 THEN cas.casMedioDePagoTransaccion END';

		--- Se asigna el valor a totalRegistros. Esta consulta solo se realiza la primera vez que se llama el SP para retornar el número de registros ya que es paginada.
		IF @primeraPeticion = 1
		BEGIN
			SET @sqlTemporal = 'SELECT @totalRegistros = COUNT(*) FROM ( ' + @sqlBase1 +' ) AS registros';
			EXEC sp_executesql @sqlTemporal,
			N'@filtro INT, @fechaActual DATE, @dias INT, @listaMediosDePago VARCHAR(54), @totalRegistros BIGINT OUTPUT',
			@filtro = @filtro, @fechaActual = @fechaActual, @dias = @dias, @listaMediosDePago = @listaMediosDePago, @totalRegistros = @totalRegistros OUTPUT;
		END

		IF  @orderBy = ''
		BEGIN
			SET @orderBy = 'CASE WHEN @filtro = 1 THEN solicitudLiquidacionSubsidio END, 
							CASE WHEN @filtro = 2 THEN periodoLiquidado END, 
							CASE WHEN @filtro = 3 THEN tipoCuotaSubsidio END, 
							CASE WHEN @filtro = 4 THEN tipoCuotaSubsidio END, 
							CASE WHEN @filtro = 5 THEN medioDePago END';
		END
		
		SET @sqlTemporal = 'SELECT * FROM ( '+ @sqlBase1 + ' ) AS registros ORDER BY ' + @orderBy +' OFFSET @offset ROWS FETCH NEXT @limit ROWS ONLY'
		EXEC sp_executesql @sqlTemporal,
		N'@filtro INT, @fechaActual DATE, @dias INT, @listaMediosDePago VARCHAR(54), @offset INT, @limit INT',
		@filtro = @filtro, @fechaActual = @fechaActual, @dias = @dias, @listaMediosDePago = @listaMediosDePago, @offset = @offset, @limit = @limit;

	END
	ELSE
	BEGIN
		DECLARE @sqlBase2 NVARCHAR(MAX);
		---	Esta es la consulta base que se usa cuando se filtra por:
		--- SITIO_DE_PAGO
		SET @sqlBase2 = 'SELECT 
							dep.depNombre AS nombreDepartamento,
							mun.munNombre AS nombreMunicipio,
							SUM(dsa.dsaValorTotal) AS valorTotal
						 FROM CuentaAdministradorSubsidio as cas 
						 INNER JOIN DetalleSubsidioAsignado as dsa on cas.casId = dsa.dsaCuentaAdministradorSubsidio  
						 INNER JOIN SolicitudLiquidacionSubsidio as sls on sls.slsId = dsa.dsaSolicitudLiquidacionSubsidio   
						 LEFT JOIN SitioPago as sop on sop.sipId = cas.casSitioDePago  
						 LEFT JOIN Infraestructura as inf on inf.infId = sop.sipInfraestructura  
						 LEFT JOIN Municipio as mun on mun.munId = inf.infMunicipio 
						 LEFT JOIN Departamento as dep on dep.depId = mun.munDepartamento  
						 WHERE (SELECT DATEDIFF(DAY, cas.casFechaHoraTransaccion, @fechaActual)) > @dias
							AND cas.casTipoTransaccionSubsidio = ''ABONO''
							AND cas.casEstadoTransaccionSubsidio = ''APLICADO''
							AND cas.casMedioDePagoTransaccion IN (SELECT value FROM STRING_SPLIT(@listaMediosDePago,'','')) ';
							
		--- Se asigna el valor a totalRegistros. Esta consulta solo se realiza la primera vez que se llama el SP para retornar el número de registros ya que es paginada.
		IF @primeraPeticion = 1
		BEGIN
			SET @sqlTemporal = 'SELECT @totalRegistros = COUNT(*) FROM ( ' + @sqlBase2 + ' GROUP BY dep.depNombre, mun.munNombre ) AS registros';
			EXEC sp_executesql @sqlTemporal,
			N'@filtro INT, @fechaActual DATE, @dias INT, @listaMediosDePago VARCHAR(54), @totalRegistros BIGINT OUTPUT',
			@filtro = @filtro, @fechaActual = @fechaActual, @dias = @dias, @listaMediosDePago = @listaMediosDePago, @totalRegistros = @totalRegistros OUTPUT;
		END

		IF  @orderBy = ''
		BEGIN
			SET @orderBy = 'nombreDepartamento, nombreMunicipio';
		END

		SET @sqlTemporal = 'SELECT * FROM ( '+ @sqlBase2 + ' GROUP BY dep.depNombre, mun.munNombre ) AS registros ORDER BY ' + @orderBy +' OFFSET @offset ROWS FETCH NEXT @limit ROWS ONLY'
		EXEC sp_executesql @sqlTemporal,
		N'@filtro INT, @fechaActual DATE, @dias INT, @listaMediosDePago VARCHAR(54), @offset INT, @limit INT',
		@filtro = @filtro, @fechaActual = @fechaActual, @dias = @dias, @listaMediosDePago = @listaMediosDePago, @offset = @offset, @limit = @limit;
	END
END;