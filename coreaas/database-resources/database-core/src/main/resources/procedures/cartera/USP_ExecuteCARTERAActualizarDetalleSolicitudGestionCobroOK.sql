-- =============================================
-- Author:		Jhon Freddy Rico Bermúdez
-- Create date: 2020/08/11
-- Description:	Procedimiento almacenado que se encarga de actualizar masivamente los registros de DetalleSolicitudGestionCobro
-- =============================================
CREATE PROCEDURE [dbo].[USP_ExecuteCARTERAActualizarDetalleSolicitudGestionCobroOK]
	@filtros VARCHAR(MAX)	
AS

BEGIN TRY

	-- se agrega registro en bitacora de ejecuci�n
	IF EXISTS(SELECT 1 FROM BitacoraEjecucionSp WHERE besNombreStoredProcedure = 'USP_ExecuteCARTERAActualizarDetalleSolicitudGestionCobroOK')
	BEGIN
		UPDATE BitacoraEjecucionSp
		SET besUltimoInicio = dbo.getLocalDate()
		WHERE besNombreStoredProcedure = 'USP_ExecuteCARTERAActualizarDetalleSolicitudGestionCobroOK'
	END
	ELSE
	BEGIN
		INSERT INTO BitacoraEjecucionSp (besNombreStoredProcedure, besUltimoInicio)
		VALUES ('USP_ExecuteCARTERAActualizarDetalleSolicitudGestionCobroOK', dbo.getLocalDate())
	END

	DECLARE @iCantidadRegistrosDetalle BIGINT
	DECLARE @iRevision BIGINT

	--Se consulta la cantidad de registros que serán actualizados
	SELECT @iCantidadRegistrosDetalle = COUNT(dsg.dsgId)
	FROM DetalleSolicitudGestionCobro dsg INNER JOIN
	(
		SELECT *
		FROM OPENJSON (@filtros)
		WITH (   
		  cartera bigint '$.idCartera',
		  id      bigint '$.idDetalleSolicitudGestionCobro'
		 )
	) tab ON dsg.dsgId = tab.id and dsg.dsgCartera = tab.cartera
	
	--Se realiza la actualización de los registros de DetalleSolicitudGestionCobro
	UPDATE 
		DetalleSolicitudGestionCobro
	SET
		dsgEnviarPrimeraRemision = tab.enviarPrimeraRemision,
		dsgEnviarSegundaRemision = tab.enviarSegundaRemision,
		dsgEstado = tab.estado,
		dsgFechaPrimeraRemision = tab.fechaPrimeraRemision,
		dsgFechaSegundaRemision = tab.fechaSegundaRemision,
		dsgCartera = tab.cartera,
		dsgObservacionPrimeraEntrega  = tab.observacionPrimeraEntrega,
		dsgObservacionPrimeraRemision = tab.observacionPrimeraRemision,
		dsgObservacionSegundaEntrega  = tab.observacionSegundaEntrega,
		dsgObservacionSegundaRemision = tab.observacionSegundaRemision,
		dsgSolicitudPrimeraRemision = tab.solicitudPrimeraRemision,
		dsgSolicitudSegundaRemision = tab.solicitudSegundaRemision,
		dsgResultadoPrimeraEntrega  = tab.resultadoPrimeraEntrega,
		dsgResultadoSegundaEntrega  = tab.resultadoSegundaEntrega,
		dsgDocumentoPrimeraRemision = tab.documentoPrimeraRemision,
		dsgDocumentoSegundaRemision = tab.documentoSegundaRemision
	FROM
	     DetalleSolicitudGestionCobro
	INNER JOIN     
	(
	    SELECT *
		FROM OPENJSON (@filtros)
		WITH (   
		  enviarPrimeraRemision bit         '$.enviarPrimeraRemision',
		  enviarSegundaRemision bit         '$.enviarSegundaRemision',
		  estado                varchar(52) '$.estadoSolicitud',
		  fechaPrimeraRemision  datetime    '$.fechaPrimeraEntregaString',
		  fechaSegundaRemision  datetime    '$.fechaSegundaEntregaString',
		  cartera               bigint      '$.idCartera',
		  observacionPrimeraEntrega  varchar(255) '$.observacionPrimeraEntrega',
		  observacionPrimeraRemision varchar(255) '$.observacionPrimeraRemision',
		  observacionSegundaEntrega  varchar(255) '$.observacionSegundaEntrega',
		  observacionSegundaRemision varchar(255) '$.observacionSegundaRemision',
		  solicitudPrimeraRemision bigint '$.idPrimeraSolicitudRemision',
		  solicitudSegundaRemision bigint '$.idSegundaSolicitudRemision',
		  resultadoPrimeraEntrega  varchar(18) '$.resultadoPrimeraEntrega',
		  resultadoSegundaEntrega  varchar(18) '$.resultadoSegundaEntrega',
		  documentoPrimeraRemision bigint '$.documentoPrimeraRemision',
		  documentoSegundaRemision bigint '$.documentoSegundaRemision',
		  id bigint '$.idDetalleSolicitudGestionCobro'
		 ) 
	) tab
	ON dsgId = tab.id

	-- Se crea el histórico de DetalleSolicitudGestionCobro
	EXEC USP_UTIL_GET_CrearRevision 'com.asopagos.entidades.ccf.cartera.DetalleSolicitudGestionCobro', @iCantidadRegistrosDetalle, '', 'USUARIO_SISTEMA', @iRevision OUTPUT

	INSERT INTO aud.DetalleSolicitudGestionCobro_aud (
		dsgId,
		REV,
		REVTYPE,
		dsgEnviarPrimeraRemision,
		dsgEnviarSegundaRemision,
		dsgEstado,
		dsgFechaPrimeraRemision,
		dsgFechaSegundaRemision,
		dsgCartera,
		dsgObservacionPrimeraEntrega,
		dsgObservacionPrimeraRemision,
		dsgObservacionSegundaEntrega,
		dsgObservacionSegundaRemision,
		dsgSolicitudPrimeraRemision,
		dsgSolicitudSegundaRemision,
		dsgResultadoPrimeraEntrega,
		dsgResultadoSegundaEntrega,
		dsgDocumentoPrimeraRemision,
		dsgDocumentoSegundaRemision
	)
	SELECT
		dsgId,
		@iRevision,
		1,
		dsgEnviarPrimeraRemision,
		dsgEnviarSegundaRemision,
		dsgEstado,
		dsgFechaPrimeraRemision,
		dsgFechaSegundaRemision,
		dsgCartera,
		dsgObservacionPrimeraEntrega,
		dsgObservacionPrimeraRemision,
		dsgObservacionSegundaEntrega,
		dsgObservacionSegundaRemision,
		dsgSolicitudPrimeraRemision,
		dsgSolicitudSegundaRemision,
		dsgResultadoPrimeraEntrega,
		dsgResultadoSegundaEntrega,
		dsgDocumentoPrimeraRemision,
		dsgDocumentoSegundaRemision
	FROM DetalleSolicitudGestionCobro dsg INNER JOIN
	(
		SELECT *
		FROM OPENJSON (@filtros)
		WITH (   
		  cartera bigint '$.idCartera',
		  id      bigint '$.idDetalleSolicitudGestionCobro'
		 )
	) tab ON dsg.dsgId = tab.id and dsg.dsgCartera = tab.cartera
	

	BEGIN TRY
		-- se marca en bitácora el fin de la ejecución exitosa ylos registros afectados
		UPDATE BitacoraEjecucionSp
		SET besUltimoExito = dbo.getLocalDate()
		WHERE besNombreStoredProcedure = 'USP_ExecuteCARTERAActualizarDetalleSolicitudGestionCobroOK'
	END TRY
	BEGIN CATCH
	    INSERT INTO CarteraLogError(cleFecha,cleNombreSP, cleaMensaje)
		VALUES (dbo.getLocalDate(),'ssss S:4',ERROR_MESSAGE());
		THROW;
	END CATCH
	
	
END TRY
BEGIN CATCH
	-- se marca en bitácora el fin de la ejecución fallida
	UPDATE BitacoraEjecucionSp
	SET besUltimoFallo = dbo.getLocalDate() 
	WHERE besNombreStoredProcedure = 'USP_ExecuteCARTERAActualizarDetalleSolicitudGestionCobroOK'

	DECLARE @ErrorMessage NVARCHAR(4000), 
		@ErrorSeverity INT,
		@ErrorState INT

	SELECT @ErrorMessage = '[dbo].[USP_ExecuteCARTERAActualizarDetalleSolicitudGestionCobroOK] | ' + ERROR_MESSAGE(),
           @ErrorSeverity = ERROR_SEVERITY(),
           @ErrorState = ERROR_STATE();

    INSERT INTO CarteraLogError(cleFecha,cleNombreSP, cleaMensaje)
	VALUES (dbo.getLocalDate(),'USP_ExecuteCARTERAActualizarDetalleSolicitudGestionCobroOK',ERROR_MESSAGE())
	
	RAISERROR (@ErrorMessage, -- Message text.
               @ErrorSeverity, -- Severity.
               @ErrorState -- State.
               );
END CATCH