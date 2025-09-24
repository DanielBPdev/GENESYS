
-- =============================================
-- Author:		Diego Suesca
-- Create date: 2018/05/29
-- Description:	
-- =============================================
CREATE PROCEDURE [dbo].[USP_SM_GET_InsertDetalleSubsidioAsignadoProgramado]
	@idSolicitudLiquidacion bigint,
	@sUsuario varchar(255)
with execute as owner
AS
BEGIN
BEGIN TRY
	print 'Inicia USP_SM_GET_InsertDetalleSubsidioAsignadoProgramado'	

	DECLARE @iNumeroRegistros BIGINT,
			@iRevision BIGINT
	
	DROP TABLE IF EXISTS #TempDetalleSubPagosPro
	
	CREATE TABLE #TempDetalleSubPagosPro (tmdDispersionMedioDePagoAdmin bigint,tmdMedioDePago bigint,tmdUsuarioCreador varchar(200),tmdFechaHoraCreacion datetime,tmdEstado varchar(20),tmdMotivoAnulacion varchar(40),tmdDetalleAnulacion varchar(250),tmdOrigenRegistroSubsidio varchar(30),tmdTipoliquidacionSubsidio varchar(60),tmdTipoCuotaSubsidio varchar(80),tmdValorSubsidioMonetario numeric(19,5),tmdValorDescuento numeric(19,5),tmdValorOriginalAbonado numeric(19,5),tmdValorTotal numeric(19,5),tmdFechaTransaccionRetiro date,tmdUsuarioTransaccionRetiro varchar(200),tmdFechaTransaccionAnulacion date,tmdUsuarioTransaccionAnulacion varchar(200),tmdFechaHoraUltimaModificacion datetime,tmdUsuarioUltimaModificacion varchar(200),tmdSolicitudLiquidacionSubsidio bigint,tmdEmpleador bigint,tmdAfiliadoPrincipal bigint,tmdGrupoFamiliar bigint,tmdBeneficiarioDetalle bigint,tmdAdministradorSubsidio bigint,tmdRegistroOriginalRelacionado bigint,tmdCuentaAdministradorSubsidio bigint,tmdPeriodoLiquidado date,tmdResultadoValidacionLiquidacion bigint,tmdCbeCondicionPersona bigint,tmdCtrCondicionPersona bigint,tmdCemCondicionPersona bigint,tmdEnviadoAPagos bit,tmdFechaProgramadaPago date,tmdMedioDePagoTransaccion varchar(13),s1 varchar(500));
	DECLARE @TempDetalleSubPagosIns TABLE(dprId bigint,dprUsuarioCreador varchar(200),dprFechaHoraCreacion datetime,dprEstado varchar(20),dprMotivoAnulacion varchar(40),dprDetalleAnulacion varchar(250),dprOrigenRegistroSubsidio varchar(30),dprTipoliquidacionSubsidio varchar(60),dprTipoCuotaSubsidio varchar(80),dprValorSubsidioMonetario numeric(19,5),dprValorDescuento numeric(19,5),dprValorOriginalAbonado numeric(19,5),dprValorTotal numeric(19,5),dprFechaTransaccionRetiro date,dprUsuarioTransaccionRetiro varchar(200),dprFechaTransaccionAnulacion date,dprUsuarioTransaccionAnulacion varchar(200),dprFechaHoraUltimaModificacion datetime,dprUsuarioUltimaModificacion varchar(200),dprSolicitudLiquidacionSubsidio bigint,dprEmpleador bigint,dprAfiliadoPrincipal bigint,dprGrupoFamiliar bigint,dprAdministradorSubsidio bigint,dprIdRegistroOriginalRelacionado bigint,dprCuentaAdministradorSubsidio bigint,dprBeneficiarioDetalle bigint,dprPeriodoLiquidado date,dprResultadoValidacionLiquidacion bigint,dprCondicionPersonaBeneficiario bigint,dprCondicionPersonaAfiliado bigint,dprCondicionPersonaEmpleador bigint,dprFechaProgramadaPago DATE)
	
	INSERT #TempDetalleSubPagosPro (tmdUsuarioCreador,tmdFechaHoraCreacion,tmdEstado,tmdMotivoAnulacion,tmdDetalleAnulacion,tmdOrigenRegistroSubsidio,tmdTipoliquidacionSubsidio,tmdTipoCuotaSubsidio,tmdValorSubsidioMonetario,tmdValorDescuento,tmdValorOriginalAbonado,tmdValorTotal,tmdFechaTransaccionRetiro,tmdUsuarioTransaccionRetiro,tmdFechaTransaccionAnulacion,tmdUsuarioTransaccionAnulacion,tmdFechaHoraUltimaModificacion,tmdUsuarioUltimaModificacion,tmdSolicitudLiquidacionSubsidio,tmdEmpleador,tmdAfiliadoPrincipal,tmdGrupoFamiliar,tmdBeneficiarioDetalle,tmdAdministradorSubsidio,tmdRegistroOriginalRelacionado,tmdCuentaAdministradorSubsidio,tmdPeriodoLiquidado,tmdResultadoValidacionLiquidacion,tmdCbeCondicionPersona,tmdCtrCondicionPersona,tmdCemCondicionPersona,tmdMedioDePago,tmdFechaProgramadaPago,tmdMedioDePagoTransaccion,s1)
		EXEC sp_execute_remote SubsidioReferenceData, 
		N'SELECT detalle.tmdUsuarioCreador,detalle.tmdFechaHoraCreacion,detalle.tmdEstado,detalle.tmdMotivoAnulacion,detalle.tmdDetalleAnulacion,detalle.tmdOrigenRegistroSubsidio,detalle.tmdTipoliquidacionSubsidio,detalle.tmdTipoCuotaSubsidio,detalle.tmdValorSubsidioMonetario,detalle.tmdValorDescuento,detalle.tmdValorOriginalAbonado,detalle.tmdValorTotal,detalle.tmdFechaTransaccionRetiro,detalle.tmdUsuarioTransaccionRetiro,detalle.tmdFechaTransaccionAnulacion,detalle.tmdUsuarioTransaccionAnulacion,detalle.tmdFechaHoraUltimaModificacion,detalle.tmdUsuarioUltimaModificacion,detalle.tmdSolicitudLiquidacionSubsidio,detalle.tmdEmpleador,detalle.tmdAfiliadoPrincipal,detalle.tmdGrupoFamiliar,detalle.tmdBeneficiarioDetalle,detalle.tmdAdministradorSubsidio,detalle.tmdRegistroOriginalRelacionado,0,detalle.tmdPeriodoLiquidado,detalle.tmdResultadoValidacionLiquidacion,detalle.tmdCbeCondicionPersona,detalle.tmdCtrCondicionPersona,detalle.tmdCemCondicionPersona,detalle.tmdMedioDePago,detalle.tmdFechaProgramadaPago,detalle.tmdMedioDePagoTransaccion
				  FROM TempDetalleSubPagos detalle ORDER BY detalle.tmdBeneficiarioDetalle, detalle.tmdPeriodoLiquidado ASC	
				';
			
	print 'DETALLES PROGRAMADOS A GUARDAR'
	declare @detallesSubPagosPro BIGINT
	SET  @detallesSubPagosPro = (SELECT COUNT(*) FROM #TempDetalleSubPagosPro)
	print @detallesSubPagosPro
		  
		  
	INSERT DetalleSubsidioAsignadoProgramado (dprUsuarioCreador,dprFechaHoraCreacion,dprEstado,dprMotivoAnulacion,dprDetalleAnulacion,dprOrigenRegistroSubsidio,dprTipoliquidacionSubsidio,dprTipoCuotaSubsidio,dprValorSubsidioMonetario,dprValorDescuento,dprValorOriginalAbonado,dprValorTotal,dprFechaTransaccionRetiro,dprUsuarioTransaccionRetiro,dprFechaTransaccionAnulacion,dprUsuarioTransaccionAnulacion,dprFechaHoraUltimaModificacion,dprUsuarioUltimaModificacion,dprSolicitudLiquidacionSubsidio,dprEmpleador,dprAfiliadoPrincipal,dprGrupoFamiliar,dprAdministradorSubsidio,dprIdRegistroOriginalRelacionado,dprCuentaAdministradorSubsidioProgramada,dprBeneficiarioDetalle,dprPeriodoLiquidado,dprResultadoValidacionLiquidacion,dprCondicionPersonaBeneficiario,dprCondicionPersonaAfiliado,dprCondicionPersonaEmpleador,dprFechaProgramadaPago)
	OUTPUT INSERTED.dprId,INSERTED.dprUsuarioCreador,INSERTED.dprFechaHoraCreacion,INSERTED.dprEstado,INSERTED.dprMotivoAnulacion,INSERTED.dprDetalleAnulacion,INSERTED.dprOrigenRegistroSubsidio,INSERTED.dprTipoliquidacionSubsidio,INSERTED.dprTipoCuotaSubsidio,INSERTED.dprValorSubsidioMonetario,INSERTED.dprValorDescuento,INSERTED.dprValorOriginalAbonado,INSERTED.dprValorTotal,INSERTED.dprFechaTransaccionRetiro,INSERTED.dprUsuarioTransaccionRetiro,INSERTED.dprFechaTransaccionAnulacion,INSERTED.dprUsuarioTransaccionAnulacion,INSERTED.dprFechaHoraUltimaModificacion,INSERTED.dprUsuarioUltimaModificacion,INSERTED.dprSolicitudLiquidacionSubsidio,INSERTED.dprEmpleador,INSERTED.dprAfiliadoPrincipal,INSERTED.dprGrupoFamiliar,INSERTED.dprAdministradorSubsidio,INSERTED.dprIdRegistroOriginalRelacionado,INSERTED.dprCuentaAdministradorSubsidioProgramada,INSERTED.dprBeneficiarioDetalle,INSERTED.dprPeriodoLiquidado,INSERTED.dprResultadoValidacionLiquidacion,INSERTED.dprCondicionPersonaBeneficiario,INSERTED.dprCondicionPersonaAfiliado,INSERTED.dprCondicionPersonaEmpleador,INSERTED.dprFechaProgramadaPago
	INTO @TempDetalleSubPagosIns
	--(dprUsuarioCreador,dprFechaHoraCreacion,dprEstado,dprMotivoAnulacion,dprDetalleAnulacion,dprOrigenRegistroSubsidio,dprTipoliquidacionSubsidio,dprTipoCuotaSubsidio,dprValorSubsidioMonetario,dprValorDescuento,dprValorOriginalAbonado,dprValorTotal,dprFechaTransaccionRetiro,dprUsuarioTransaccionRetiro,dprFechaTransaccionAnulacion,dprUsuarioTransaccionAnulacion,dprFechaHoraUltimaModificacion,dprUsuarioUltimaModificacion,dprSolicitudLiquidacionSubsidio,dprEmpleador,dprAfiliadoPrincipal,dprGrupoFamiliar,dprBeneficiarioDetalle,dprAdministradorSubsidio,dprIdRegistroOriginalRelacionado,dprCuentaAdministradorSubsidioProgramada,dprPeriodoLiquidado,dprResultadoValidacionLiquidacion,dprCondicionPersonaBeneficiario,dprCondicionPersonaAfiliado,dprCondicionPersonaEmpleador,dprFechaProgramadaPago)
	SELECT detalle.tmdUsuarioCreador,detalle.tmdFechaHoraCreacion,detalle.tmdEstado,detalle.tmdMotivoAnulacion,detalle.tmdDetalleAnulacion,detalle.tmdOrigenRegistroSubsidio,detalle.tmdTipoliquidacionSubsidio,detalle.tmdTipoCuotaSubsidio,detalle.tmdValorSubsidioMonetario,detalle.tmdValorDescuento,detalle.tmdValorOriginalAbonado,detalle.tmdValorTotal,detalle.tmdFechaTransaccionRetiro,detalle.tmdUsuarioTransaccionRetiro,detalle.tmdFechaTransaccionAnulacion,detalle.tmdUsuarioTransaccionAnulacion,detalle.tmdFechaHoraUltimaModificacion,detalle.tmdUsuarioUltimaModificacion,detalle.tmdSolicitudLiquidacionSubsidio,detalle.tmdEmpleador,detalle.tmdAfiliadoPrincipal,detalle.tmdGrupoFamiliar,detalle.tmdAdministradorSubsidio,detalle.tmdRegistroOriginalRelacionado,cuenta.capId,detalle.tmdBeneficiarioDetalle,detalle.tmdPeriodoLiquidado,detalle.tmdResultadoValidacionLiquidacion,detalle.tmdCbeCondicionPersona,detalle.tmdCtrCondicionPersona,detalle.tmdCemCondicionPersona,detalle.tmdFechaProgramadaPago
	  FROM #TempDetalleSubPagosPro detalle
	  INNER JOIN CuentaAdministradorSubsidioProgramada cuenta ON (detalle.tmdAdministradorSubsidio = cuenta.capAdministradorSubsidio
													 AND detalle.tmdMedioDePagoTransaccion  = cuenta.capMedioDePagoTransaccion---cambio 20230826
													AND cuenta.capSolicitudLiquidacionSubsidio = @idSolicitudLiquidacion)	

	  print 'DETALLES PROGRAMADOS GUARDADOS'
	declare @detallesSubPagosProG BIGINT
			SET  @detallesSubPagosProG = (SELECT COUNT(*) FROM DetalleSubsidioAsignadoProgramado WHERE dprSolicitudLiquidacionSubsidio = @idSolicitudLiquidacion)
		  print @detallesSubPagosProG
	  
	  
	SELECT @iNumeroRegistros = COUNT(*) FROM #TempDetalleSubPagosPro
	print @iNumeroRegistros
	IF @iNumeroRegistros > 0
	BEGIN
		EXEC [dbo].[USP_UTIL_GET_CrearRevision] 'com.asopagos.entidades.subsidiomonetario.pagos.DetalleSubsidioAsignadoProgramado',@iNumeroRegistros,'',@sUsuario,@iRevision OUTPUT

		INSERT aud.DetalleSubsidioAsignadoProgramado_aud (dprId,dprUsuarioCreador,dprFechaHoraCreacion,dprEstado,dprMotivoAnulacion,dprDetalleAnulacion,dprOrigenRegistroSubsidio,dprTipoliquidacionSubsidio,dprTipoCuotaSubsidio,dprValorSubsidioMonetario,dprValorDescuento,dprValorOriginalAbonado,dprValorTotal,dprFechaTransaccionRetiro,dprUsuarioTransaccionRetiro,dprFechaTransaccionAnulacion,dprUsuarioTransaccionAnulacion,dprFechaHoraUltimaModificacion,dprUsuarioUltimaModificacion,dprSolicitudLiquidacionSubsidio,dprEmpleador,dprAfiliadoPrincipal,dprGrupoFamiliar,dprBeneficiarioDetalle,dprAdministradorSubsidio,dprIdRegistroOriginalRelacionado,dprCuentaAdministradorSubsidioProgramada,dprPeriodoLiquidado,dprResultadoValidacionLiquidacion,dprCondicionPersonaBeneficiario,dprCondicionPersonaAfiliado,dprCondicionPersonaEmpleador,dprFechaProgramadaPago,REV,REVTYPE)
		SELECT detalle.dprId,detalle.dprUsuarioCreador,detalle.dprFechaHoraCreacion,detalle.dprEstado,detalle.dprMotivoAnulacion,detalle.dprDetalleAnulacion,detalle.dprOrigenRegistroSubsidio,detalle.dprTipoliquidacionSubsidio,detalle.dprTipoCuotaSubsidio,detalle.dprValorSubsidioMonetario,detalle.dprValorDescuento,detalle.dprValorOriginalAbonado,detalle.dprValorTotal,detalle.dprFechaTransaccionRetiro,detalle.dprUsuarioTransaccionRetiro,detalle.dprFechaTransaccionAnulacion,detalle.dprUsuarioTransaccionAnulacion,detalle.dprFechaHoraUltimaModificacion,detalle.dprUsuarioUltimaModificacion,detalle.dprSolicitudLiquidacionSubsidio,detalle.dprEmpleador,detalle.dprAfiliadoPrincipal,detalle.dprGrupoFamiliar,detalle.dprBeneficiarioDetalle,detalle.dprAdministradorSubsidio,detalle.dprIdRegistroOriginalRelacionado,detalle.dprCuentaAdministradorSubsidio,detalle.dprPeriodoLiquidado,detalle.dprResultadoValidacionLiquidacion,detalle.dprCondicionPersonaBeneficiario,detalle.dprCondicionPersonaAfiliado,detalle.dprCondicionPersonaEmpleador,detalle.dprFechaProgramadaPago,@iRevision,0
		  FROM @TempDetalleSubPagosIns detalle
		INNER JOIN #TempDetalleSubPagosPro tdetalle ON tdetalle.tmdResultadoValidacionLiquidacion = detalle.dprResultadoValidacionLiquidacion
		INNER JOIN CuentaAdministradorSubsidioProgramada cuenta ON (detalle.dprAdministradorSubsidio = cuenta.capAdministradorSubsidio
														AND tdetalle.tmdMedioDePagoTransaccion = cuenta.capMedioDePagoTransaccion---cambio 20230826
														AND cuenta.capSolicitudLiquidacionSubsidio = @idSolicitudLiquidacion)	
	END

	UPDATE cap
	SET cap.capEmpleador = dsa.dprEmpleador,
		cap.capAfiliadoPrincipal = dsa.dprAfiliadoPrincipal,
		cap.capBeneficiarioDetalle = dsa.dprBeneficiarioDetalle,
		cap.capGrupoFamiliar = dsa.dprGrupoFamiliar
	FROM CuentaAdministradorSubsidioProgramada cap
	INNER JOIN DetalleSubsidioAsignadoProgramado dsa ON dsa.dprCuentaAdministradorSubsidioProgramada = cap.capId
	WHERE dsa.dprSolicitudLiquidacionSubsidio = @idSolicitudLiquidacion

	UPDATE cap
	SET cap.capEmpleador = dsa.dprEmpleador,
		cap.capAfiliadoPrincipal = dsa.dprAfiliadoPrincipal,
		cap.capBeneficiarioDetalle = dsa.dprBeneficiarioDetalle,
		cap.capGrupoFamiliar = dsa.dprGrupoFamiliar
	FROM aud.CuentaAdministradorSubsidioProgramada_aud cap
	INNER JOIN aud.DetalleSubsidioAsignadoProgramado_aud dsa ON dsa.dprCuentaAdministradorSubsidioProgramada = cap.capId
	WHERE dsa.dprSolicitudLiquidacionSubsidio = @idSolicitudLiquidacion
END TRY
BEGIN CATCH
	INSERT RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
	VALUES (dbo.GetLocalDate(),'[USP_SM_GET_InsertDetalleSubsidioAsignadoProgramado] @idSolicitudLiquidacion:'
							+ CAST(@idSolicitudLiquidacion AS VARCHAR(50)),ERROR_MESSAGE());
END CATCH
	print 'Finaliza USP_SM_GET_InsertDetalleSubsidioAsignadoProgramado'
END ;
