-- =============================================
-- Author:		Diego Suesca
-- Create date: 2018/05/29
-- Description:	
-- =============================================
CREATE PROCEDURE [dbo].[USP_SM_GET_InsertDescuentosSubsidioAsignado]	
with execute as owner
AS
BEGIN
BEGIN TRY
	print 'Inicia USP_SM_GET_InsertDescuentosSubsidioAsignado'	

	CREATE TABLE #TempDescuentoPagos (tmeValorPignoradoBeneficiario numeric(19,5),  tmeEntidadDescuento bigint,tmeResultadoValidacionLiquidacion bigint,tmeDispersionMedioDePagoAdmin bigint, tmeDescueltoLiquidacion bigint,tmeEnviadoAPagos bit,tmeNombreEntidadDescuento varchar(250), tmeCodigoReferencia varchar(20), tmeFechaCargueDescuento DATETIME, S1 varchar(500))

	INSERT #TempDescuentoPagos (tmeEntidadDescuento,tmeValorPignoradoBeneficiario,tmeNombreEntidadDescuento,tmeResultadoValidacionLiquidacion,tmeCodigoReferencia,tmeFechaCargueDescuento,S1)
	EXEC sp_execute_remote SubsidioReferenceData, 
	N'SELECT des.tmeEntidadDescuento,des.tmeValorPignoradoBeneficiario,des.tmeNombreEntidadDescuento, des.tmeResultadoValidacionLiquidacion,des.tmeCodigoReferencia,des.tmeFechaCargueDescuento
			  FROM TempDescuentoPagos des' 		


 	INSERT DescuentosSubsidioAsignado (desDetalleSubsidioAsignado,desEntidadDescuento,desMontoDescontado,desNombreEntidadDescuento,desCodigoReferencia,desFechaCargueDescuento)
	SELECT detalle.dsaId,des.tmeEntidadDescuento,des.tmeValorPignoradoBeneficiario,des.tmeNombreEntidadDescuento, des.tmeCodigoReferencia, des.tmeFechaCargueDescuento
			  FROM #TempDescuentoPagos des
			INNER JOIN DetalleSubsidioAsignado detalle ON (detalle.dsaResultadoValidacionLiquidacion = des.tmeResultadoValidacionLiquidacion)																				
 	print 'Finaliza USP_SM_GET_InsertDescuentosSubsidioAsignado'	


	EXEC sp_execute_remote SubsidioReferenceData, 
	N'UPDATE dlq SET dlq.dlqEnviadoAPagos = 1
	FROM dbo.DescuentoLiquidacion dlq
	INNER JOIN TempDescuentoPagos des ON (des.tmeDescueltoLiquidacion = dlq.dlqId)' 	
	
END TRY
BEGIN CATCH
	INSERT RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
	VALUES (dbo.GetLocalDate(),'[USP_SM_GET_InsertDescuentosSubsidioAsignado] ' ,ERROR_MESSAGE());
END CATCH
	print 'Finaliza USP_SM_GET_InsertCuentaAdministradorSubsidios'
END ;
