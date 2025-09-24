-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2017/11/29
-- Description:	Procedimiento almacenado encargado  de eliminar una liquidaci�n dada
-- restaurando las marcas "EnColaProceso" en el modelo de Staging
-- =============================================
CREATE PROCEDURE [dbo].[USP_SM_UTIL_EliminarDispersion]
(
	@sNumeroRadicado VARCHAR(23)
)
AS
BEGIN TRY
	BEGIN TRAN
	
	SELECT dsaId, dsaCuentaAdministradorSubsidio
	INTO #dsaIds
	FROM DetalleSubsidioAsignado dsa
	JOIN SolicitudLiquidacionSubsidio sls on dsa.dsaSolicitudLiquidacionSubsidio = slsId
	JOIN Solicitud sol ON sol.solId = sls.slsSolicitudGlobal
	WHERE sol.solNumeroRadicacion=@sNumeroRadicado

	create clustered index ix_dsaIds on #dsaIds (dsaId)


	DELETE des 
	FROM DescuentosSubsidioAsignado des
	JOIN #dsaIds d ON d.dsaId = des.desDetalleSubsidioAsignado

	ALTER TABLE dbo.DetalleSubsidioAsignado NOCHECK CONSTRAINT ALL;
	DELETE dsa 
	FROM DetalleSubsidioAsignado dsa
	JOIN #dsaIds d ON d.dsaId = dsa.dsaId		
	ALTER TABLE dbo.DetalleSubsidioAsignado CHECK CONSTRAINT ALL;

	DELETE dss
	FROM DetalleSolicitudAnulacionSubsidioCobrado dss
	JOIN #dsaIds d ON d.dsaCuentaAdministradorSubsidio = dss.dssCuentaAdministradorSubisdio		

	DELETE rac
	FROM RegistroArchivoConsumosAnibol rac
	JOIN #dsaIds d ON d.dsaCuentaAdministradorSubsidio = rac.racCuentaAdministradorSubsidio		
	
	DELETE rpa
	FROM RetiroPersonaAutorizadaCobroSubsidio rpa
	JOIN #dsaIds d ON d.dsaCuentaAdministradorSubsidio = rpa.rpaCuentaAdministradorSubsidio		
	
	DELETE tfs
	FROM TransaccionesFallidasSubsidio tfs
	JOIN #dsaIds d ON d.dsaCuentaAdministradorSubsidio = tfs.tfsCuentaAdministradorSubsidio	

	DELETE cas 
	FROM CuentaAdministradorSubsidio cas
	JOIN (SELECT DISTINCT dsaCuentaAdministradorSubsidio 
			FROM #dsaIds
		 ) dsaId ON dsaId.dsaCuentaAdministradorSubsidio = cas.casId	

	/*
	subsidio

	UPDATE dmp
	set dmp.dmpEnviadoAPagos=0
	FROM dbo.DispersionMedioDePagoAdmin dmp
	WHERE dmp.dmpNumeroRadicado =@sNumeroRadicado

	update rvl 
	set rvl.rvlEnviadoAPagos = 1
	FROM ResultadoValidacionLiquidacion rvl 
	WHERE rvl.rvlNumeroRadicado ='142020542064'

	UPDATE dlq 
	SET dlq.dlqEnviadoAPagos = 1
	FROM dbo.DescuentoLiquidacion dlq
	join ResultadoValidacionLiquidacion rvl on dlq.dlqResultadoValidacionLiquidacion = rvl.rvlId 
	WHERE rvl.rvlNumeroRadicado ='142020542064'
	*/

	COMMIT;
END TRY
BEGIN CATCH
	ROLLBACK;
	BEGIN TRY
		-- Se agrega trazabilidad del error
		INSERT INTO RegistroLog (relFecha, relErrorMessage, relParametrosEjecucion) 
		VALUES(dbo.GetLocalDate(), ERROR_MESSAGE(), 
   			'[dbo].[USP_SM_UTIL_EliminarLiquidacion]'+
   			' |@sNumeroRadicado = '+ @sNumeroRadicado);
   	END TRY
   	BEGIN CATCH
   		----print 'ERROR INSERT RegistroLog: '+ERROR_MESSAGE();
		THROW;
	END CATCH
END CATCH	
;