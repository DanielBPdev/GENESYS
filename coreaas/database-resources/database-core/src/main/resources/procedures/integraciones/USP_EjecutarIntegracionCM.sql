

-- ====================================================================================================
-- Author: VILLAMARIN JULIAN
-- Create date: marzo 11 DE 2022
-- Description: Ejecuta los procedimientos para la ejecucion de cada una de las integraciones de
-- clientes y contables.
-- =====================================================================================================
CREATE OR ALTER     PROCEDURE [sap].[USP_EjecutarIntegracionCM] AS
----Ajuest para liquibase
Select 1


BEGIN


SET NOCOUNT ON;
BEGIN TRANSACTION

----- insercion en tabla de log para ejecucion del procedimiento. 
	DECLARE @idlog INT, @fechahorainicio DATETIME, @fechahorafinal DATETIME
	DECLARE @casidtran bigint
	INSERT INTO sap.IC_LogEjecucion ([Integracion],[fechahorainicio],[fechahorafinal],[RegistrosEnviados]) VALUES ('CM',GETDATE() -'05:00:00' ,GETDATE() -'05:00:00', '' )
	SET  @idlog = SCOPE_IDENTITY()
	SELECT @fechahorainicio = GETDATE()-'05:00:00'

	IF (SELECT COUNT(*) FROM  sap.ejecucion_CM WITH (NOLOCK)) = 0
	BEGIN
		
			INSERT sap.ejecucion_CM
			SELECT 1
			

		DECLARE	 @perNumeroIdentificacion VARCHAR(20), @perTipoIdentificacion VARCHAR(20), @solNumeroRadicacion VARCHAR(30), @casid bigint

		CREATE TABLE #registros (Nregistros int)
	

----------------------------------------------------------------C02----------------------------------------------------------------------------
  
		SELECT DISTINCT TOP 50 cas.casIdTransaccionOriginal  ,per.perTipoIdentificacion,per.perNumeroIdentificacion
		INTO #tmp_filaLiquidacion_02
		FROM dbo.CuentaAdministradorSubsidio cas  with (nolock) 
		INNER JOIN core.dbo.DetalleSubsidioAsignado dsa with (nolock) ON (cas.casid = dsa.dsaCuentaAdministradorSubsidio)
		LEFT JOIN dbo.AdministradorSubsidio ads with (nolock)  ON (ads.asuId = cas.casAdministradorSubsidio)
		LEFT JOIN dbo.Persona per with (nolock)  ON (ads.asuPersona = per.perId)		
		LEFT JOIN sap.ContablesCtrl ctr with (nolock)  ON   ctr.fechaIngreso >='2024-04-01' AND ctr.solNumeroRadicacion=convert(varchar,cas.casIdTransaccionOriginal) 
		AND ctr.perNumeroIdentificacion = per.perNumeroIdentificacion AND ctr.perTipoIdentificacion = per.perTipoIdentificacion
		AND CONCAT (cas.casIdTransaccionOriginal, 'C02' ) = ctr.observaciones
		WHERE (casTipoTransaccionSubsidio = 'ANULACION' AND casEstadoTransaccionSubsidio = 'GENERADO' and casMedioDePagoTransaccion <> 'TRANSFERENCIA' )
		AND cas.casFechaHoraCreacionRegistro >= '2024-04-17'
		AND ctr.solNumeroRadicacion is null AND dsa.dsaEstado IN ('ANULADO_REEMPLAZADO', 'ANULADO')
	
	DECLARE @countC2 INT, @count2C2 INT 	
	SET @countC2 = 0;
	SET @count2C2 = (SELECT COUNT(*) FROM  #tmp_filaLiquidacion_02)
	WHILE (@countC2 < @count2C2)
	BEGIN 
		BEGIN TRANSACTION

		SELECT TOP 1 @casidtran = casIdTransaccionOriginal, @perTipoIdentificacion = perTipoIdentificacion, @perNumeroIdentificacion = perNumeroIdentificacion
			FROM #tmp_filaLiquidacion_02  
					

			EXEC [sap].[USP_InterfazCM_C02] @casidtran,@perTipoIdentificacion, @perNumeroIdentificacion ;

			DELETE FROM #tmp_filaLiquidacion_02 WHERE casIdTransaccionOriginal = @casidtran AND perTipoIdentificacion = @perTipoIdentificacion 
			AND perNumeroIdentificacion = @perNumeroIdentificacion

			SET @countC2=@countC2 + 1
			PRINT @countC2
		COMMIT TRANSACTION
	END 
	

-------------------------------------------------------------------Fin C02--------------------------------------------------------------


--------------------------------------------------------------------C01-----------------------------------------------------------------------
	drop table if exists #solicitud
	select s.solid, s.solNumeroRadicacion
	into #solicitud
	from dbo.Solicitud s left join sap.ContablesCtrl ct on s.solNumeroRadicacion = ct.solNumeroRadicacion 
	where s.solResultadoProceso='DISPERSADA' and s.solObservacion IS NULL and ct.solNumeroRadicacion IS NULL 
	 

	   
	SELECT DISTINCT TOP 100  sol.solNumeroRadicacion,per.perTipoIdentificacion,per.perNumeroIdentificacion, casId 
	INTO #tmp_filaLiquidacion_01
	FROM dbo.CuentaAdministradorSubsidio cas  with (nolock) 
		INNER JOIN dbo.DetalleSubsidioAsignado dsa with (nolock) ON dsa.dsaEstado = 'DERECHO_ASIGNADO' AND (cas.casid = dsa.dsaCuentaAdministradorSubsidio)
		LEFT JOIN dbo.DescuentosSubsidioAsignado as dcsa with (nolock) ON (dsa.dsaId = dcsa.desDetalleSubsidioAsignado)
		LEFT JOIN dbo.EntidadDescuento as entd with (nolock) ON (entd.endId = dcsa.desEntidadDescuento)
		INNER JOIN dbo.SolicitudLiquidacionSubsidio sls with (nolock)  ON (sls.slsid = cas.casSolicitudLiquidacionSubsidio)
		INNER JOIN #solicitud sol with (nolock)  ON (sol.solId = slsSolicitudGlobal)
		INNER JOIN dbo.AdministradorSubsidio ads with (nolock)  ON (ads.asuId = dsa.dsaAdministradorSubsidio)
		INNER JOIN dbo.Persona per with (nolock)  ON (ads.asuPersona = per.perId)
		LEFT JOIN  sap.ContablesCtrl ctr with (nolock)  ON    ctr.solNumeroRadicacion=sol.solNumeroRadicacion 
		AND ctr.perNumeroIdentificacion = per.perNumeroIdentificacion AND ctr.perTipoIdentificacion = per.perTipoIdentificacion
		AND CONCAT (cas.casId, 'C01' ) = ctr.observaciones
		WHERE  ctr.solNumeroRadicacion IS NULL  AND
		casFechaHoraCreacionRegistro >= '2024-04-17' and casEstadoTransaccionSubsidio in ('APLICADO', 'ENVIADO')
			
	UNION
	SELECT DISTINCT TOP 50  sol.solNumeroRadicacion,per.perTipoIdentificacion,per.perNumeroIdentificacion, casId
	FROM dbo.CuentaAdministradorSubsidio cas  with (nolock) 
		INNER JOIN dbo.DetalleSubsidioAsignado dsa with (nolock) ON dsa.dsaEstado = 'DERECHO_ASIGNADO' AND (cas.casid = dsa.dsaCuentaAdministradorSubsidio)
		LEFT JOIN dbo.DescuentosSubsidioAsignado as dcsa with (nolock) ON (dsa.dsaId = dcsa.desDetalleSubsidioAsignado)
		LEFT JOIN dbo.EntidadDescuento as entd with (nolock) ON (dcsa.desEntidadDescuento = entd.endId)
		INNER JOIN dbo.SolicitudLiquidacionSubsidio sls with (nolock)  ON (cas.casSolicitudLiquidacionSubsidio=sls.slsid)
		INNER JOIN #solicitud sol with (nolock)  ON (sol.solId = slsSolicitudGlobal)
		INNER JOIN dbo.AdministradorSubsidio ads with (nolock)  ON (dsa.dsaAdministradorSubsidio = ads.asuId)
		INNER JOIN dbo.Persona per with (nolock)  ON (ads.asuPersona = per.perId)
		LEFT JOIN  sap.ContablesCtrl ctr with (nolock)  ON   sol.solNumeroRadicacion =  ctr.solNumeroRadicacion
		AND per.perNumeroIdentificacion= ctr.perNumeroIdentificacion AND per.perTipoIdentificacion = ctr.perTipoIdentificacion 
		AND CONCAT (cas.casId, 'C01' ) = ctr.observaciones
		WHERE  ctr.solNumeroRadicacion IS NULL  AND
		casFechaHoraCreacionRegistro >= '2024-04-17' and casEstadoTransaccionSubsidio ='COBRADO' and dsaValorTotal = 0 and dsaValorDescuento > 0

	--- Cambio por GAP 0030,GLPI 78414, detecta los abonos aplicados por cambio de medio de pago, No deben integrar, estos casos se envian por movimiento C18
		delete #tmp_filaLiquidacion_01 where casId in(
		select distinct ca.casId 
		from #tmp_filaLiquidacion_01 t
		inner join dbo.CuentaAdministradorSubsidio ca on ca.casId = t.casId
		inner join dbo.CuentaAdministradorSubsidio co on ca.casIdTransaccionOriginal = co.casId
		inner join dbo.DetalleSubsidioAsignado do on co.casId = do.dsaCuentaAdministradorSubsidio
		where ca.casOrigenTransaccion ='ANULACION' and do.dsaMotivoAnulacion='CAMBIO_MEDIO_DE_PAGO')

		
		DECLARE @count INT, @count2 INT 	
		SET @count = 0;
		SET @count2 = (SELECT COUNT(*) FROM  #tmp_filaLiquidacion_01)
		WHILE (@count < @count2)
		BEGIN 
			BEGIN TRANSACTION

			SELECT TOP 1 @solNumeroRadicacion = solNumeroRadicacion, @perTipoIdentificacion = perTipoIdentificacion, @perNumeroIdentificacion = perNumeroIdentificacion,
			@casid = casId
				FROM #tmp_filaLiquidacion_01  
						
				EXEC [sap].[USP_InterfazCM_C01] @solNumeroRadicacion,@perTipoIdentificacion, @perNumeroIdentificacion, @casid ;

				DELETE FROM #tmp_filaLiquidacion_01 WHERE solNumeroRadicacion = @solNumeroRadicacion AND perTipoIdentificacion = @perTipoIdentificacion 
				AND perNumeroIdentificacion = @perNumeroIdentificacion AND casId = @casid

				SET @count=@count + 1
				PRINT @count
			COMMIT TRANSACTION
		END 
		
	

---------------------------------------------------------------FIN C01-----------------------------------------------------------------------------------


---------------------------------------------------------------------C05----------------------------------------------------------------------

		SELECT DISTINCT TOP 50 casId,per.perTipoIdentificacion,per.perNumeroIdentificacion
		INTO #tmp_filaLiquidacion_05
		FROM CuentaAdministradorSubsidio cas  with (nolock) 
		Inner JOIN DetalleSubsidioAsignado dsa with (nolock) ON (cas.casid = dsa.dsaCuentaAdministradorSubsidio)
		INNER JOIN AdministradorSubsidio ads with (nolock)  ON (ads.asuId = dsa.dsaAdministradorSubsidio)
		INNER JOIN Persona per with (nolock)  ON (ads.asuPersona = per.perId)
		LEFT JOIN sap.ContablesCtrl ctr with (nolock)  ON  (convert(varchar,cas.casId) = ctr.solNumeroRadicacion) and per.perNumeroIdentificacion = ctr.perNumeroIdentificacion
		and per.perTipoIdentificacion = ctr.perTipoIdentificacion AND  CONCAT (cas.casId, 'C05' ) = ctr.observaciones	
		WHERE cas.casTipoTransaccionSubsidio = 'ABONO'
		AND cas.casEstadoTransaccionSubsidio = 'COBRADO'
		AND cas.casValorRealTransaccion > 0
		AND cas.casMedioDePagoTransaccion = 'TRANSFERENCIA'
		AND cas.casFechaHoraCreacionRegistro >= '2024-04-08' -- se debe dejar con fecha de la creacion del abono porque no actulaiza la ultima modificacion
		AND ctr.solNumeroRadicacion IS NULL 
		

	DECLARE @count05 INT, @count2C05 INT 	
	SET @count05 = 0;
	SET @count2C05 = (SELECT COUNT(*) FROM  #tmp_filaLiquidacion_05)
	WHILE (@count05 < @count2C05)
	BEGIN 
		BEGIN TRANSACTION

		SELECT TOP 1 @solNumeroRadicacion = casId, @perTipoIdentificacion = perTipoIdentificacion, @perNumeroIdentificacion = perNumeroIdentificacion
				FROM #tmp_filaLiquidacion_05  
					
			EXEC [sap].[USP_InterfazCM_C05] @solNumeroRadicacion,@perTipoIdentificacion, @perNumeroIdentificacion;

			DELETE FROM #tmp_filaLiquidacion_05 WHERE casId = @solNumeroRadicacion AND perTipoIdentificacion = @perTipoIdentificacion 
			AND perNumeroIdentificacion = @perNumeroIdentificacion 

			SET @count05=@count05 + 1
			PRINT @count05	
		COMMIT TRANSACTION
	END 





---------------------------------------------FIN C05---------------------------------------------------------

---------------------------------------------------------C07-------------------------------------------------------------

		SELECT DISTINCT TOP 50 casIdCuentaAdmonSubsidioRelacionado,per.perTipoIdentificacion,per.perNumeroIdentificacion
		INTO #tmp_filaLiquidacion_07
		FROM CuentaAdministradorSubsidio cas  with (nolock) 
		Inner JOIN DetalleSubsidioAsignado dsa with (nolock) ON (cas.casid = dsa.dsaCuentaAdministradorSubsidio)
		LEFT JOIN DescuentosSubsidioAsignado as dcsa with (nolock) ON (dsa.dsaId = dcsa.desDetalleSubsidioAsignado)
		LEFT JOIN EntidadDescuento as entd with (nolock) ON (entd.endId = dcsa.desEntidadDescuento)
		INNER JOIN SolicitudLiquidacionSubsidio sls with (nolock)  ON (sls.slsid = cas.casSolicitudLiquidacionSubsidio)
		INNER JOIN Solicitud sol with (nolock)  ON (sol.solId = slsSolicitudGlobal)
		INNER JOIN AdministradorSubsidio ads with (nolock)  ON (ads.asuId = dsa.dsaAdministradorSubsidio)
		INNER JOIN Persona per with (nolock)  ON (ads.asuPersona = per.perId)
		LEFT JOIN sap.ContablesCtrl ctr with (nolock)  ON (CONVERT(varchar, cas.casIdCuentaAdmonSubsidioRelacionado) = ctr.solNumeroRadicacion) AND per.perNumeroIdentificacion = ctr.perNumeroIdentificacion
		AND  per.perTipoIdentificacion = ctr.perTipoIdentificacion AND  CONCAT (cas.casIdCuentaAdmonSubsidioRelacionado, 'C07' ) = ctr.observaciones
		WHERE cas.casTipoTransaccionSubsidio = 'ABONO'
		AND cas.casEstadoTransaccionSubsidio = 'COBRADO'
		AND cas.casValorRealTransaccion > 0
		AND cas.casMedioDePagoTransaccion = 'TARJETA'
		AND cas.casFechaHoraUltimaModificacion >= '2024-05-17'
		AND ctr.solNumeroRadicacion IS NULL 
			
		
	DECLARE @count07 INT, @count2C07 INT 	
	SET @count07 = 0;
	SET @count2C07 = (SELECT COUNT(*) FROM  #tmp_filaLiquidacion_07)
	WHILE (@count07 < @count2C07)
	BEGIN 
		BEGIN TRANSACTION

		SELECT TOP 1 @solNumeroRadicacion = casIdCuentaAdmonSubsidioRelacionado, @perTipoIdentificacion = perTipoIdentificacion, @perNumeroIdentificacion = perNumeroIdentificacion
				FROM #tmp_filaLiquidacion_07  
					
			EXEC [sap].[USP_InterfazCM_C07] @solNumeroRadicacion,@perTipoIdentificacion, @perNumeroIdentificacion;


			DELETE FROM #tmp_filaLiquidacion_07 WHERE casIdCuentaAdmonSubsidioRelacionado = @solNumeroRadicacion AND perTipoIdentificacion = @perTipoIdentificacion 
			AND perNumeroIdentificacion = @perNumeroIdentificacion 

			SET @count07=@count07 + 1
			PRINT @count07		
		COMMIT TRANSACTION
	END 

	

--------------------------------------------------FIN C07---------------------------------------------------------------------------

--------------------------------------------------------C14-------------------------------------------------------------------------
/*
	SELECT  DISTINCT TOP 50 cas.casId,per.perTipoIdentificacion,per.perNumeroIdentificacion
		INTO #tmp_filaLiquidacion_14
		FROM CuentaAdministradorSubsidio cas  with (nolock) 
		Inner JOIN DetalleSubsidioAsignado dsa with (nolock) ON (cas.casid = dsa.dsaCuentaAdministradorSubsidio)
		INNER JOIN DescuentosSubsidioAsignado as dcsa with (nolock) ON (dsa.dsaId = dcsa.desDetalleSubsidioAsignado)
		INNER JOIN EntidadDescuento as entd with (nolock) ON (entd.endId = dcsa.desEntidadDescuento)
		INNER JOIN AdministradorSubsidio ads with (nolock)  ON (ads.asuId = dsa.dsaAdministradorSubsidio)
		INNER JOIN Persona per with (nolock)  ON (ads.asuPersona = per.perId)
		LEFT JOIN sap.ContablesCtrl ctr with (nolock)  ON  (CONVERT(varchar, cas.casid)= ctr.solNumeroRadicacion)
		AND per.perNumeroIdentificacion = ctr.perNumeroIdentificacion AND  per.perTipoIdentificacion = ctr.perTipoIdentificacion
		AND  CONCAT (cas.casId, 'C14' ) = ctr.observaciones		
		WHERE cas.casTipoTransaccionSubsidio = 'ABONO'
		AND cas.casEstadoTransaccionSubsidio = 'COBRADO'
		AND desMontoDescontado > 0
		AND entd.endCodigo = '3'
		AND cas.casFechaHoraCreacionRegistro > '2023-07-14'
		AND ctr.solNumeroRadicacion IS NULL
		ORDER BY casId ASC
		
	
	DECLARE @count14 INT, @count2C14 INT 	
	SET @count14 = 0;
	SET @count2C14 = (SELECT COUNT(*) FROM  #tmp_filaLiquidacion_14)
	WHILE (@count14 < @count2C14)
	BEGIN 
		BEGIN TRANSACTION

		SELECT TOP 1 @solNumeroRadicacion = casId, @perTipoIdentificacion = perTipoIdentificacion, @perNumeroIdentificacion = perNumeroIdentificacion
				FROM #tmp_filaLiquidacion_14  
					
			EXEC [sap].[USP_InterfazCM_C14] @solNumeroRadicacion,@perTipoIdentificacion, @perNumeroIdentificacion;


			DELETE FROM #tmp_filaLiquidacion_14 WHERE casId = @solNumeroRadicacion AND perTipoIdentificacion = @perTipoIdentificacion 
			AND perNumeroIdentificacion = @perNumeroIdentificacion 

			SET @count14=@count14 + 1
			PRINT @count14			
		COMMIT TRANSACTION
	END 
*/
---------------------------------------------FIN C14---------------------------------------------------------
		
----------------------------------------C16--------------------------------------------------------------------


		SELECT DISTINCT TOP 50 sol.solNumeroRadicacion, per.perTipoIdentificacion,per.perNumeroIdentificacion
		INTO #tmp_filaLiquidacion_16
		FROM CuentaAdministradorSubsidio cas  with (nolock) 
		INNER JOIN DetalleSubsidioAsignado dsa with (nolock) ON (cas.casid = dsa.dsaCuentaAdministradorSubsidio)
		LEFT JOIN DescuentosSubsidioAsignado as dcsa with (nolock) ON (dsa.dsaId = dcsa.desDetalleSubsidioAsignado)
		LEFT JOIN EntidadDescuento as entd with (nolock) ON (entd.endId = dcsa.desEntidadDescuento)
		INNER JOIN SolicitudLiquidacionSubsidio sls with (nolock)  ON (sls.slsid = cas.casSolicitudLiquidacionSubsidio)
		INNER JOIN Solicitud sol with (nolock)  ON (sol.solId = slsSolicitudGlobal)
		INNER JOIN AdministradorSubsidio ads with (nolock)  ON (ads.asuId = dsa.dsaAdministradorSubsidio)
		INNER JOIN Persona per with (nolock)  ON (ads.asuPersona = per.perId)
		LEFT JOIN sap.ContablesCtrl ctr with (nolock)  ON  (ctr.fechaIngreso >='2024-04-01' and sol.solNumeroRadicacion= ctr.solNumeroRadicacion)
		AND per.perNumeroIdentificacion = ctr.perNumeroIdentificacion AND  per.perTipoIdentificacion = ctr.perTipoIdentificacion
		AND  CONCAT (cas.casId, 'C16' ) = ctr.observaciones				

		WHERE dsa.dsaEstado = 'ANULADO' 
		AND dsa.dsaValorTotal > 0
		AND dsaMotivoAnulacion = 'PRESCRIPCION'
		AND cas.casFechaHoraUltimaModificacion >= '2024-05-17'
		AND ctr.solNumeroRadicacion	IS NULL


		DECLARE @count16 INT, @count2C16 INT 	
		SET @count16 = 0;
		SET @count2C16 = (SELECT COUNT(*) FROM  #tmp_filaLiquidacion_16)
		WHILE (@count16 < @count2C16)
		BEGIN 
			BEGIN TRANSACTION

			SELECT TOP 1 @solNumeroRadicacion = solNumeroRadicacion, @perTipoIdentificacion = perTipoIdentificacion, @perNumeroIdentificacion = perNumeroIdentificacion
					FROM #tmp_filaLiquidacion_16  
					
				EXEC [sap].[USP_InterfazCM_C16] @solNumeroRadicacion,@perTipoIdentificacion, @perNumeroIdentificacion;


				DELETE FROM #tmp_filaLiquidacion_16 WHERE solNumeroRadicacion = @solNumeroRadicacion AND perTipoIdentificacion = @perTipoIdentificacion 
				AND perNumeroIdentificacion = @perNumeroIdentificacion 

				SET @count16=@count16 + 1
				PRINT @count16			
			COMMIT TRANSACTION
		END 

------------------------------------------------------------Fin C16-------------------------------------------------------------------------


---------------------------------------------------------------C18------------------------------------------------------------------------

		SELECT DISTINCT TOP 50 casId,per.perTipoIdentificacion,per.perNumeroIdentificacion
			INTO #tmp_filaLiquidacion_18
			FROM CuentaAdministradorSubsidio cas  with (nolock) 
			Inner JOIN DetalleSubsidioAsignado dsa with (nolock) ON (cas.casIdTransaccionOriginal = dsa.dsaCuentaAdministradorSubsidio)
			LEFT JOIN DescuentosSubsidioAsignado as dcsa with (nolock) ON (dsa.dsaId = dcsa.desDetalleSubsidioAsignado)
			LEFT JOIN EntidadDescuento as entd with (nolock) ON (entd.endId = dcsa.desEntidadDescuento)
			INNER JOIN SolicitudLiquidacionSubsidio sls with (nolock)  ON (sls.slsid = cas.casSolicitudLiquidacionSubsidio)
			INNER JOIN Solicitud sol with (nolock)  ON (sol.solId = slsSolicitudGlobal)
			INNER JOIN AdministradorSubsidio ads with (nolock)  ON (ads.asuId = dsa.dsaAdministradorSubsidio)
			INNER JOIN Persona per with (nolock)  ON (ads.asuPersona = per.perId)
			LEFT JOIN sap.ContablesCtrl ctr with (nolock)  ON  (CONVERT(varchar, cas.casid)= ctr.solNumeroRadicacion)
			AND per.perNumeroIdentificacion = ctr.perNumeroIdentificacion AND  per.perTipoIdentificacion = ctr.perTipoIdentificacion
			AND  CONCAT (cas.casId, 'C18' ) = ctr.observaciones		
			WHERE cas.casTipoTransaccionSubsidio = 'ANULACION'
			AND cas.casEstadoTransaccionSubsidio = 'GENERADO'
			AND cas.casValorRealTransaccion <> 0
			AND dsa.dsaMotivoAnulacion = 'CAMBIO_MEDIO_DE_PAGO'
			AND cas.casFechaHoraCreacionRegistro >= '2024-05-14'
			AND ctr.solNumeroRadicacion IS NULL
	
	
		DECLARE @count18 INT, @count2C18 INT 	
		SET @count18 = 0;
		SET @count2C18 = (SELECT COUNT(*) FROM  #tmp_filaLiquidacion_18)
		WHILE (@count18 < @count2C18)
		BEGIN 
			BEGIN TRANSACTION

			SELECT TOP 1 @solNumeroRadicacion = casId, @perTipoIdentificacion = perTipoIdentificacion, @perNumeroIdentificacion = perNumeroIdentificacion
					FROM #tmp_filaLiquidacion_18  
					
				EXEC [sap].[USP_InterfazCM_C18] @solNumeroRadicacion,@perTipoIdentificacion, @perNumeroIdentificacion;

		

				DELETE FROM #tmp_filaLiquidacion_18 WHERE casId = @solNumeroRadicacion AND perTipoIdentificacion = @perTipoIdentificacion 
				AND perNumeroIdentificacion = @perNumeroIdentificacion 

				SET @count18=@count18 + 1
				PRINT @count18			
			COMMIT TRANSACTION
		END 

--------------------------------------------------FIN C18---------------------------------------------------------------------------
---------------------------------------------------------C19-------------------------------------------------------------

	SELECT DISTINCT TOP 50 cas.casIdCuentaAdmonSubsidioRelacionado,per.perTipoIdentificacion,per.perNumeroIdentificacion
		INTO #tmp_filaLiquidacion_19
		FROM CuentaAdministradorSubsidio cas  with (nolock) 
		Inner JOIN DetalleSubsidioAsignado dsa with (nolock) ON (cas.casid = dsa.dsaCuentaAdministradorSubsidio)
		LEFT JOIN DescuentosSubsidioAsignado as dcsa with (nolock) ON (dsa.dsaId = dcsa.desDetalleSubsidioAsignado)
		LEFT JOIN EntidadDescuento as entd with (nolock) ON (entd.endId = dcsa.desEntidadDescuento)
		INNER JOIN SolicitudLiquidacionSubsidio sls with (nolock)  ON (sls.slsid = cas.casSolicitudLiquidacionSubsidio)
		INNER JOIN Solicitud sol with (nolock)  ON (sol.solId = slsSolicitudGlobal)
		INNER JOIN AdministradorSubsidio ads with (nolock)  ON (ads.asuId = dsa.dsaAdministradorSubsidio)
		INNER JOIN Persona per with (nolock)  ON (ads.asuPersona = per.perId)
		LEFT JOIN  sap.ContablesCtrl ctr with (nolock)  ON ctr.fechaIngreso >='2024-04-01' and  ctr.solNumeroRadicacion= convert(varchar,cas.casIdCuentaAdmonSubsidioRelacionado)
		AND ctr.perNumeroIdentificacion = per.perNumeroIdentificacion AND ctr.perTipoIdentificacion = per.perTipoIdentificacion
		AND CONCAT (cas.casIdCuentaAdmonSubsidioRelacionado, 'C19' ) = ctr.observaciones

		WHERE cas.casTipoTransaccionSubsidio = 'ABONO'
		AND cas.casEstadoTransaccionSubsidio = 'COBRADO'
		AND cas.casValorRealTransaccion > 0
		AND cas.casMedioDePagoTransaccion = 'EFECTIVO'
		AND dsa.dsaFechaTransaccionRetiro >= '2024-05-30'
		AND ctr.solNumeroRadicacion IS NULL


	DECLARE @countC19 INT, @count2C19 INT 	
	SET @countC19 = 0;
	SET @count2C19 = (SELECT COUNT(*) FROM  #tmp_filaLiquidacion_19)
	WHILE (@countC19 < @count2C19)
	BEGIN 
		BEGIN TRANSACTION

		SELECT TOP 1 @solNumeroRadicacion = casIdCuentaAdmonSubsidioRelacionado, @perTipoIdentificacion = perTipoIdentificacion, @perNumeroIdentificacion = perNumeroIdentificacion
			FROM #tmp_filaLiquidacion_19  
					

			EXEC [sap].[USP_InterfazCM_C19] @solNumeroRadicacion,@perTipoIdentificacion, @perNumeroIdentificacion ;

			DELETE FROM #tmp_filaLiquidacion_19 WHERE casIdCuentaAdmonSubsidioRelacionado = @solNumeroRadicacion AND perTipoIdentificacion = @perTipoIdentificacion 
			AND perNumeroIdentificacion = @perNumeroIdentificacion

			SET @countC19=@countC19 + 1
			PRINT @countC19
		COMMIT TRANSACTION
	END 





	
--------------------------------------------------FIN C19---------------------------------------------------------------------------

------------------------------------------------------------C20-------------------------------------------------------------

		SELECT DISTINCT TOP 50 casId,per.perTipoIdentificacion,per.perNumeroIdentificacion
			INTO #tmp_filaLiquidacion_20
			FROM CuentaAdministradorSubsidio cas  with (nolock) 
			INNER JOIN DetalleSubsidioAsignado dsa with (nolock) ON (cas.casid = dsa.dsaCuentaAdministradorSubsidio)
			LEFT JOIN DescuentosSubsidioAsignado as dcsa with (nolock) ON (dsa.dsaId = dcsa.desDetalleSubsidioAsignado)
			LEFT JOIN EntidadDescuento as entd with (nolock) ON (entd.endId = dcsa.desEntidadDescuento)
			INNER JOIN SolicitudLiquidacionSubsidio sls with (nolock)  ON (sls.slsid = cas.casSolicitudLiquidacionSubsidio)
			INNER JOIN Solicitud sol with (nolock)  ON (sol.solId = slsSolicitudGlobal)
			INNER JOIN AdministradorSubsidio ads with (nolock)  ON (ads.asuId = dsa.dsaAdministradorSubsidio)
			INNER JOIN Persona per with (nolock)  ON (ads.asuPersona = per.perId)
			INNER JOIN sap.MaestraTiposIdentificacion mti with (nolock)  ON (mti.TipoIdGenesys = per.perTipoIdentificacion)
			INNER JOIN sap.MaestraFormasPago mfp with (nolock) ON (cas.casMedioDePagoTransaccion = mfp.nombre)
			INNER JOIN MedioDePago mp with (nolock) on mdpid = casmediodepago
			INNER JOIN MedioTransferencia mt with (nolock) on mp.mdpid = mt.mdpid
			LEFT JOIN sap.ContablesCtrl ctr with (nolock)  ON ctr.fechaIngreso >='2024-04-01' and  (CONVERT(varchar, cas.casid)= ctr.solNumeroRadicacion)
			AND per.perNumeroIdentificacion = ctr.perNumeroIdentificacion AND  per.perTipoIdentificacion = ctr.perTipoIdentificacion
			AND  CONCAT (cas.casId, 'C20' ) = ctr.observaciones	
		
			WHERE cas.casTipoTransaccionSubsidio = 'ABONO'
			AND cas.casEstadoTransaccionSubsidio = 'COBRADO'
			AND cas.casValorRealTransaccion > 0
			AND cas.casMedioDePagoTransaccion = 'TRANSFERENCIA'
			AND cas.casFechaHoraCreacionRegistro >= '2024-05-17'
			AND mt.metCobroJudicial =1
			AND ctr.solNumeroRadicacion IS NULL			
				
		DECLARE @countC20 INT, @count2C20 INT 	
		SET @countC20 = 0;
		SET @count2C20 = (SELECT COUNT(*) FROM  #tmp_filaLiquidacion_20)
		WHILE (@countC20 < @count2C20)
		BEGIN 
			BEGIN TRANSACTION

			SELECT TOP 1 @solNumeroRadicacion = casId, @perTipoIdentificacion = perTipoIdentificacion, @perNumeroIdentificacion = perNumeroIdentificacion
				FROM #tmp_filaLiquidacion_20  
					

				EXEC [sap].[USP_InterfazCM_C19] @solNumeroRadicacion,@perTipoIdentificacion, @perNumeroIdentificacion ;

				DELETE FROM #tmp_filaLiquidacion_20 WHERE casId = @solNumeroRadicacion AND perTipoIdentificacion = @perTipoIdentificacion 
				AND perNumeroIdentificacion = @perNumeroIdentificacion

				SET @countC20=@countC20 + 1
				PRINT @countC20	
			COMMIT TRANSACTION
		END 
		
--------------------------------------------------FIN C20---------------------------------------------------------------------------


	SELECT @fechahorafinal = GETDATE() -'05:00:00'


	INSERT INTO #registros
		 SELECT COUNT(*) FROM sap.IC_CM_Det WHERE fechaEjecucion >=@fechahorainicio AND FechaEjecucion <=@fechahorafinal	

	INSERT INTO #registros
		 SELECT COUNT(*) FROM sap.IC_CM_Enc WHERE fecIng >= convert (date,@fechahorainicio) AND fecIng <= convert (date,@fechahorafinal)
		 and horaIng >= convert (varchar,@fechahorainicio, 108)  and horaIng <= convert (varchar,@fechahorafinal, 108)

		 UPDATE sap.IC_LogEjecucion SET RegistrosEnviados =(SELECT SUM(Nregistros) FROM #registros)
			WHERE id = @idlog

	
		UPDATE sap.IC_LogEjecucion set fechahorafinal = GETDATE() -'05:00:00'  where id = @idlog

		SELECT 'ok ejec',GETDATE()  
		DELETE FROM  sap.ejecucion_CM
		END
	ELSE BEGIN
		SELECT 'El proceso ya se encuentra en ejecucion'
	END


	
	COMMIT TRANSACTION

END