/****** Object:  StoredProcedure [dbo].[USP_SM_AlmacenarDetallesAsignadosProgramadosADetallesSubsidiosAsignados]    Script Date: 11/11/2022 10:05:57 a. m. ******/
-- ============================================= 
-- Author:		OLGA VEGA
-- Create date: 2018/06/18
-- Update :     2018/10/02
-- Description:	Procedimiento almacenado que convalida inserta los registros de la tabla de DetalleSubsidioAsignadoProgramado a la tabla DetalleSubsidioAsignado
-- =============================================
CREATE PROCEDURE [dbo].[USP_SM_AlmacenarDetallesAsignadosProgramadosADetallesSubsidiosAsignados]
AS 

BEGIN
		
	--variables DetalleSubsidioAsignadoProgramado
	DECLARE @dprSolicitudLiquidacionSubsidio bigint	 
	DECLARE @dprCuentaAdministradorSubsidio bigint
	DECLARE @numeroRadicado VARCHAR(20)
	DECLARE @medioDePagoTransaccion VARCHAR(14)
	DECLARE @idmedioDePago bigint
	DECLARE @AdministradorSubsidio bigint
	-- varible auxiliar para saber el id de la cuenta que tiene cada detalle programado
	DECLARE @idCuentaAdmonSubsidio bigint
	-- varible auxiliar para saber el id de la solicitud de liquidacion que tiene cada detalle programado
	DECLARE @idSolicitudLiquidacion bigint
	-- varible auxiliar que tiene el id de la nueva cuenta que se crea al momento de tener los valores de los detalles programados
	DECLARE @idNuevaCuentaAdmonSubsidio bigint
	-- variable que contiene el total de los valores totales de cada detalle perteneciente a una cuenta de administrador de subsidio
	DECLARE @sumaValoresTotalesDetallesProgramados numeric(19,5)
	
 
	DROP TABLE IF EXISTS #TablaSolicitudSumatoria
	-- tabla que contiene las llaves de la tabla
	CREATE TABLE #TablaSolicitudSumatoria (
		idSolicitudDetalleProgramado bigint,
		numeroRadicado varchar(20),
		sumatoriaDetalles  numeric(19,5),
		CuentaAdministradorSubsidio  bigint,  
		medioDePagoTransaccion varchar(13),
		IdMedioDePago bigint,
		AdministradorSubsidio bigint
	)
	
	--DROP TABLE IF EXISTS #TablaIdNuevaCuenta
	-- tabla que contiene las llaves de la tabl
	CREATE TABLE #TablaIdNuevaCuenta (
		idSolicitudDetalleProgramado bigint,
		idNuevaCuentaAdmonSubsidio bigint,
		idCuentaAdministradorSubsidio bigint
	)
	
	SET @idCuentaAdmonSubsidio = NULL
			 
	-- Se llena la tabla de sumatoria de valores por solicitud de liquidación
	INSERT INTO #TablaSolicitudSumatoria (
		idSolicitudDetalleProgramado, 
		numeroRadicado, 
		sumatoriaDetalles,  
		CuentaAdministradorSubsidio,  
		medioDePagoTransaccion, 
		IdMedioDePago,  
		AdministradorSubsidio)			
	SELECT 
		dpr.dprSolicitudLiquidacionSubsidio, 
		sol.solNumeroRadicacion, 
		SUM(dpr.dprValorTotal), 
		dprCuentaAdministradorSubsidioProgramada, 
		capMedioDePagoTransaccion, 
		capMedioDePago, 
		capAdministradorSubsidio
	FROM dbo.DetalleSubsidioAsignadoProgramado dpr with(nolock)
	JOIN SolicitudLiquidacionSubsidio sls with(nolock) ON sls.slsId = dpr.dprSolicitudLiquidacionSubsidio
	JOIN Solicitud sol with(nolock) ON sol.solId = sls.slsSolicitudGlobal 
	INNER JOIN CuentaAdministradorSubsidioProgramada with(nolock) ON dprCuentaAdministradorSubsidioProgramada = capid 
	WHERE  dpr.dprEstado <> 'PROGRAMADO_APLICADO'
		AND dpr.dprFechaProgramadaPago = CAST(CONVERT(VARCHAR(10), dbo.GetLocalDate(),121) AS DATE)
	GROUP BY 
		dpr.dprSolicitudLiquidacionSubsidio, 
		sol.solNumeroRadicacion, 
		dpr.dprCuentaAdministradorSubsidioProgramada, 
		capMedioDePagoTransaccion, 
		capMedioDePago, 
		capAdministradorSubsidio

	DECLARE @item_category_counter INT 

	--Tabla que va a contener las solicitudes que se deben dispersar, agrupadas por administrador y medio de pago
	DECLARE @solicitudesprogramadas TABLE (
		primary_key INT IDENTITY(1,1) NOT NULL, 
		idSolicitudDetalleProgramado bigint,
		numeroRadicado varchar(20),
		sumatoriaDetalles  numeric(19,5),
		CuentaAdministradorSubsidio  bigint ,
		medioDePagoTransaccion VARCHAR(14),
		IdMedioDePago bigint ,
		Administradorsubsidio bigint 
	)

	--Se llena la variable tipo tabla @solicitudesprogramadas desde la tabla temporal #TablaSolicitudSumatoria
	INSERT INTO @solicitudesprogramadas				
	SELECT idSolicitudDetalleProgramado ,
		numeroRadicado,
		sumatoriaDetalles,
		CuentaAdministradorSubsidio,
		medioDePagoTransaccion,
		IdMedioDePago,
		AdministradorSubsidio
	FROM #TablaSolicitudSumatoria


	-------------- Inicia ciclo donde se evalúan se crean abonos agrupadores a partir de los derechos asignados que cumplen con la fecha de programación

	DECLARE @loop_counter INT
	SET @loop_counter = (SELECT COUNT(*) FROM @solicitudesprogramadas);
	SET @item_category_counter = 1;

	WHILE @loop_counter > 0 AND @item_category_counter <= @loop_counter
	BEGIN  	
		SELECT 
			@dprSolicitudLiquidacionSubsidio = idSolicitudDetalleProgramado,
			@numeroRadicado = numeroRadicado,
			@sumaValoresTotalesDetallesProgramados = sumatoriaDetalles,
			@dprCuentaAdministradorSubsidio = CuentaAdministradorSubsidio,
			@medioDePagoTransaccion = medioDePagoTransaccion,
			@idmedioDePago = idmedioDePago,
			@AdministradorSubsidio = Administradorsubsidio
		FROM @solicitudesprogramadas
		WHERE primary_key = @item_category_counter

	 

		----inserta abonos a la Cuenta de administrador de subsidio

		INSERT INTO dbo.CuentaAdministradorSubsidio (
			casFechaHoraCreacionRegistro, 
			casUsuarioCreacionRegistro, 
			casTipoTransaccionSubsidio, 
			casEstadoTransaccionSubsidio, 
			casEstadoLiquidacionSubsidio,
			casOrigenTransaccion, 
			casMedioDePagoTransaccion, 
			casNumeroTarjetaAdmonSubsidio, 
			casCodigoBanco, 
			casNombreBanco, 
			casTipoCuentaAdmonSubsidio, 
			casNumeroCuentaAdmonSubsidio, 
			casTipoIdentificacionTitularCuentaAdmonSubsidio, 
			casNumeroIdentificacionTitularCuentaAdmonSubsidio, 
			casNombreTitularCuentaAdmonSubsidio,
			casFechaHoraTransaccion, 
			casUsuarioTransaccion, 
			casValorOriginalTransaccion, 
			casValorRealTransaccion, 
			casIdTransaccionOriginal, 
			casIdRemisionDatosTerceroPagador, 
			casIdTransaccionTerceroPagador, 
			casNombreTerceroPagado, 
			casIdCuentaAdmonSubsidioRelacionado, 
			casFechaHoraUltimaModificacion, 
			casUsuarioUltimaModificacion, 
			casAdministradorSubsidio, 
			casSitioDePago, 
			casSitioDeCobro, 
			casMedioDePago, 
			casSolicitudLiquidacionSubsidio, 
			casCondicionPersonaAdmin
		)		  
		SELECT 
			dbo.GetLocalDate(),  
			csp.capUsuarioCreacionRegistro, 
			csp.capTipoTransaccionSubsidio,
			csp.capEstadoTransaccionSubsidio,  
			csp.capEstadoLiquidacionSubsidio,
			csp.capOrigenTransaccion, 
			csp.capMedioDePagoTransaccion, 
			csp.capNumeroTarjetaAdmonSubsidio, 
			csp.capCodigoBanco, 
			csp.capNombreBanco, 
			csp.capTipoCuentaAdmonSubsidio, 
			csp.capNumeroCuentaAdmonSubsidio, 
			csp.capTipoIdentificacionTitularCuentaAdmonSubsidio, 
			csp.capNumeroIdentificacionTitularCuentaAdmonSubsidio, 
			csp.capNombreTitularCuentaAdmonSubsidio,
			dbo.GetLocalDate(),  
			csp.capUsuarioCreacionRegistro,
			@sumaValoresTotalesDetallesProgramados,  
			@sumaValoresTotalesDetallesProgramados,
			csp.capIdTransaccionOriginal, 
			csp.capIdRemisionDatosTerceroPagador,
			csp.capIdTransaccionTerceroPagador, 
			csp.capNombreTerceroPagado,
			csp.capIdCuentaAdmonSubsidioRelacionado, 
			dbo.GetLocalDate(),
			csp.capUsuarioUltimaModificacion, 
			csp.capAdministradorSubsidio,
			csp.capSitioDePago, 
			csp.capSitioDeCobro, 
			csp.capMedioDePago,
			csp.capSolicitudLiquidacionSubsidio, 
			csp.capCondicionPersonaAdmin
		FROM dbo.CuentaAdministradorSubsidioProgramada csp with(nolock) 
		WHERE csp.capId = @dprCuentaAdministradorSubsidio
			AND csp.capMedioDePago = @idmedioDePago
			AND csp.capAdministradorSubsidio = @AdministradorSubsidio


		--Se almacena el último id de la cuenta, con el objetivo de asociar el abono con los derechos
		SELECT @idNuevaCuentaAdmonSubsidio = MAX(casId) FROM dbo.CuentaAdministradorSubsidio

		--Se cambia el estado del abono a "Aplicado" cuando el medio de pago es "Efectivo"
		IF @medioDePagoTransaccion = 'EFECTIVO' AND @sumaValoresTotalesDetallesProgramados <> 0
		BEGIN
			UPDATE dbo.CuentaAdministradorSubsidio 
			SET dbo.CuentaAdministradorSubsidio.casEstadoTransaccionSubsidio = 'APLICADO'
			WHERE dbo.CuentaAdministradorSubsidio.casId = @idNuevaCuentaAdmonSubsidio
		END 

		--Se cambia el estado del abono a "Enviado" cuando el medio de pago es "Transferencia"
		IF @medioDePagoTransaccion = 'TRANSFERENCIA' AND @sumaValoresTotalesDetallesProgramados <> 0
		BEGIN
			UPDATE dbo.CuentaAdministradorSubsidio 
			SET dbo.CuentaAdministradorSubsidio.casEstadoTransaccionSubsidio = 'ENVIADO'
		WHERE dbo.CuentaAdministradorSubsidio.casId = @idNuevaCuentaAdmonSubsidio
		END 


		----Inserta derechos a Detalle de subsidio asignado

		INSERT INTO dbo.DetalleSubsidioAsignado (
			dsaUsuarioCreador, 
			dsaFechaHoraCreacion, 
			dsaEstado, 
			dsaMotivoAnulacion, 
			dsaDetalleAnulacion, 
			dsaOrigenRegistroSubsidio, 
			dsaTipoliquidacionSubsidio, 
			dsaTipoCuotaSubsidio, 
			dsaValorSubsidioMonetario, 
			dsaValorDescuento, 
			dsaValorOriginalAbonado, 
			dsaValorTotal, 
			dsaFechaTransaccionRetiro, 
			dsaUsuarioTransaccionRetiro, 
			dsaFechaTransaccionAnulacion, 
			dsaUsuarioTransaccionAnulacion, 
			dsaFechaHoraUltimaModificacion, 
			dsaUsuarioUltimaModificacion, 
			dsaSolicitudLiquidacionSubsidio, 
			dsaEmpleador, 
			dsaAfiliadoPrincipal, 
			dsaGrupoFamiliar, 
			dsaAdministradorSubsidio, 
			dsaIdRegistroOriginalRelacionado,
			dsaCuentaAdministradorSubsidio, 
			dsaBeneficiarioDetalle, 
			dsaPeriodoLiquidado, 
			dsaResultadoValidacionLiquidacion, 
			dsaCondicionPersonaBeneficiario,
			dsaCondicionPersonaAfiliado, 
			dsaCondicionPersonaEmpleador
		)
		SELECT 
			dprUsuarioCreador, 
			dbo.GetLocalDate(), 
			dprEstado, 
			dprMotivoAnulacion, 
			dprDetalleAnulacion, 
			dprOrigenRegistroSubsidio,
			dprTipoliquidacionSubsidio, 
			dprTipoCuotaSubsidio, 
			dprValorSubsidioMonetario, 
			dprValorDescuento, 
			dprValorOriginalAbonado, 
			dprValorTotal, 
			dprFechaTransaccionRetiro, 
			dprUsuarioTransaccionRetiro, 
			dprFechaTransaccionAnulacion, 
			dprUsuarioTransaccionAnulacion, 
			dbo.GetLocalDate(), 
			dprUsuarioUltimaModificacion, 
			dprSolicitudLiquidacionSubsidio, 
			dprEmpleador, 
			dprAfiliadoPrincipal, 
			dprGrupoFamiliar, 
			dprAdministradorSubsidio, 
			dprIdRegistroOriginalRelacionado, 
			@idNuevaCuentaAdmonSubsidio, 
			dprBeneficiarioDetalle, 
			dprPeriodoLiquidado, 
			dprResultadoValidacionLiquidacion, 
			dprCondicionPersonaBeneficiario,	
			dprCondicionPersonaAfiliado, 
			dprCondicionPersonaEmpleador
		FROM dbo.DetalleSubsidioAsignadoProgramado WITH(NOLOCK)
		INNER JOIN CuentaAdministradorSubsidioProgramada ON capid = dprCuentaAdministradorSubsidioProgramada
		WHERE dprCuentaAdministradorSubsidioProgramada = @dprCuentaAdministradorSubsidio
			AND capMedioDePago = @idmedioDePago
			AND capAdministradorSubsidio = @AdministradorSubsidio
			AND dprEstado <> 'PROGRAMADO_APLICADO'
			AND dprFechaProgramadaPago = CAST(CONVERT(VARCHAR(10), dbo.GetLocalDate(),121) AS DATE)
					 	
			     
		UPDATE DET SET DET.dprEstado = 'PROGRAMADO_APLICADO' 
		FROM DetalleSubsidioAsignadoProgramado DET
		INNER JOIN CuentaAdministradorSubsidioProgramada ON capid = dprCuentaAdministradorSubsidioProgramada				   
		WHERE DET.dprCuentaAdministradorSubsidioProgramada = @dprCuentaAdministradorSubsidio
			AND capMedioDePago = @idmedioDePago
			AND capAdministradorSubsidio = @AdministradorSubsidio
			AND dprEstado <> 'PROGRAMADO_APLICADO'
			AND dprFechaProgramadaPago = CAST(CONVERT(VARCHAR(10), dbo.GetLocalDate(),121) AS DATE)
			 
		SET @numeroRadicado = NULL 
		SET @sumaValoresTotalesDetallesProgramados = 0  
		SET @dprCuentaAdministradorSubsidio = 0 
		SET @idNuevaCuentaAdmonSubsidio = 0
		SET @AdministradorSubsidio = 0
				
		SET @item_category_counter = @item_category_counter + 1
	END 
END