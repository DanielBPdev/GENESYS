-- =============================================
-- Author:		Diego Suesca
-- Create date: 2018/05/29
-- Description:	
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_SM_GET_InsertDetalleSubsidioAsignado]
	@idSolicitudLiquidacion bigint,
	@sUsuario varchar(255),
	@primerRegistro bigint = NULL, 
	@ultimoRegistro bigint = NULL
with execute as owner
AS
BEGIN
BEGIN TRY
	print 'Inicia USP_SM_GET_InsertDetalleSubsidioAsignado'	

	DECLARE @iNumeroRegistros BIGINT,
			@iRevision BIGINT;

	CREATE TABLE #TempDetalleSubPagos (tmdDispersionMedioDePagoAdmin bigint,tmdMedioDePago bigint,tmdUsuarioCreador varchar(200),tmdFechaHoraCreacion datetime,tmdEstado varchar(20),tmdMotivoAnulacion varchar(40),tmdDetalleAnulacion varchar(250),tmdOrigenRegistroSubsidio varchar(30),tmdTipoliquidacionSubsidio varchar(60),tmdTipoCuotaSubsidio varchar(80),tmdValorSubsidioMonetario numeric(19,5),tmdValorDescuento numeric(19,5),tmdValorOriginalAbonado numeric(19,5),tmdValorTotal numeric(19,5),tmdFechaTransaccionRetiro date,tmdUsuarioTransaccionRetiro varchar(200),tmdFechaTransaccionAnulacion date,tmdUsuarioTransaccionAnulacion varchar(200),tmdFechaHoraUltimaModificacion datetime,tmdUsuarioUltimaModificacion varchar(200),tmdSolicitudLiquidacionSubsidio bigint,tmdEmpleador bigint,tmdAfiliadoPrincipal bigint,tmdGrupoFamiliar bigint,tmdBeneficiarioDetalle bigint,tmdAdministradorSubsidio bigint,tmdRegistroOriginalRelacionado bigint,tmdCuentaAdministradorSubsidio bigint,tmdPeriodoLiquidado date,tmdResultadoValidacionLiquidacion bigint,tmdCbeCondicionPersona bigint,tmdCtrCondicionPersona bigint,tmdCemCondicionPersona bigint,tmdEnviadoAPagos bit,tmdMedioDePagoTransaccion varchar(13), tmdNumeroCuenta varchar(30),TmdTipoCuenta varchar(100),s1 varchar(500));---cambio 20230826
	DECLARE @TempDetalleSubPagosIns TABLE(dsaId bigint,dsaUsuarioCreador varchar(200),dsaFechaHoraCreacion datetime,dsaEstado varchar(20),dsaMotivoAnulacion varchar(40),dsaDetalleAnulacion varchar(250),dsaOrigenRegistroSubsidio varchar(30),dsaTipoliquidacionSubsidio varchar(60),dsaTipoCuotaSubsidio varchar(80),dsaValorSubsidioMonetario numeric(19,5),dsaValorDescuento numeric(19,5),dsaValorOriginalAbonado numeric(19,5),dsaValorTotal numeric(19,5),dsaFechaTransaccionRetiro date,dsaUsuarioTransaccionRetiro varchar(200),dsaFechaTransaccionAnulacion date,dsaUsuarioTransaccionAnulacion varchar(200),dsaFechaHoraUltimaModificacion datetime,dsaUsuarioUltimaModificacion varchar(200),dsaSolicitudLiquidacionSubsidio bigint,dsaEmpleador bigint,dsaAfiliadoPrincipal bigint,dsaGrupoFamiliar bigint,dsaAdministradorSubsidio bigint,dsaIdRegistroOriginalRelacionado bigint,dsaCuentaAdministradorSubsidio bigint,dsaBeneficiarioDetalle bigint,dsaPeriodoLiquidado date,dsaResultadoValidacionLiquidacion bigint,dsaCondicionPersonaBeneficiario bigint,dsaCondicionPersonaAfiliado bigint,dsaCondicionPersonaEmpleador bigint)

	IF @primerRegistro IS  NOT NULL
	BEGIN
		INSERT #TempDetalleSubPagos (tmdUsuarioCreador,tmdFechaHoraCreacion,tmdEstado,tmdMotivoAnulacion,tmdDetalleAnulacion,tmdOrigenRegistroSubsidio,tmdTipoliquidacionSubsidio,tmdTipoCuotaSubsidio,tmdValorSubsidioMonetario,tmdValorDescuento,tmdValorOriginalAbonado,tmdValorTotal,tmdFechaTransaccionRetiro,tmdUsuarioTransaccionRetiro,tmdFechaTransaccionAnulacion,tmdUsuarioTransaccionAnulacion,tmdFechaHoraUltimaModificacion,tmdUsuarioUltimaModificacion,tmdSolicitudLiquidacionSubsidio,tmdEmpleador,tmdAfiliadoPrincipal,tmdGrupoFamiliar,tmdBeneficiarioDetalle,tmdAdministradorSubsidio,tmdRegistroOriginalRelacionado,tmdCuentaAdministradorSubsidio,tmdPeriodoLiquidado,tmdResultadoValidacionLiquidacion,tmdCbeCondicionPersona,tmdCtrCondicionPersona,tmdCemCondicionPersona,tmdMedioDePago, tmdMedioDePagoTransaccion,tmdNumeroCuenta,TmdTipoCuenta,s1)---cambio 20230826
		EXEC sp_execute_remote SubsidioReferenceData, 
		N'SELECT detalle.tmdUsuarioCreador,detalle.tmdFechaHoraCreacion,detalle.tmdEstado,detalle.tmdMotivoAnulacion,detalle.tmdDetalleAnulacion,detalle.tmdOrigenRegistroSubsidio,detalle.tmdTipoliquidacionSubsidio,detalle.tmdTipoCuotaSubsidio,detalle.tmdValorSubsidioMonetario,detalle.tmdValorDescuento,detalle.tmdValorOriginalAbonado,detalle.tmdValorTotal,detalle.tmdFechaTransaccionRetiro,detalle.tmdUsuarioTransaccionRetiro,detalle.tmdFechaTransaccionAnulacion,detalle.tmdUsuarioTransaccionAnulacion,detalle.tmdFechaHoraUltimaModificacion,detalle.tmdUsuarioUltimaModificacion,detalle.tmdSolicitudLiquidacionSubsidio,detalle.tmdEmpleador,detalle.tmdAfiliadoPrincipal,detalle.tmdGrupoFamiliar,detalle.tmdBeneficiarioDetalle,detalle.tmdAdministradorSubsidio,detalle.tmdRegistroOriginalRelacionado,0,detalle.tmdPeriodoLiquidado,detalle.tmdResultadoValidacionLiquidacion,detalle.tmdCbeCondicionPersona,detalle.tmdCtrCondicionPersona,detalle.tmdCemCondicionPersona,detalle.tmdMedioDePago, detalle.tmdMedioDePagoTransaccion, detalle.tmdNumeroCuenta,detalle.TmdTipoCuenta
				  FROM TempDetalleSubPagos detalle			
				WHERE detalle.tmdDispersionMedioDePagoAdmin BETWEEN @primerRegistro AND @ultimoRegistro',
		  	N'@primerRegistro bigint, @ultimoRegistro bigint',
	 		@primerRegistro = @primerRegistro, @ultimoRegistro = @ultimoRegistro;
	END 
	ELSE
	BEGIN
		INSERT #TempDetalleSubPagos (tmdUsuarioCreador,tmdFechaHoraCreacion,tmdEstado,tmdMotivoAnulacion,tmdDetalleAnulacion,tmdOrigenRegistroSubsidio,tmdTipoliquidacionSubsidio,tmdTipoCuotaSubsidio,tmdValorSubsidioMonetario,tmdValorDescuento,tmdValorOriginalAbonado,tmdValorTotal,tmdFechaTransaccionRetiro,tmdUsuarioTransaccionRetiro,tmdFechaTransaccionAnulacion,tmdUsuarioTransaccionAnulacion,tmdFechaHoraUltimaModificacion,tmdUsuarioUltimaModificacion,tmdSolicitudLiquidacionSubsidio,tmdEmpleador,tmdAfiliadoPrincipal,tmdGrupoFamiliar,tmdBeneficiarioDetalle,tmdAdministradorSubsidio,tmdRegistroOriginalRelacionado,tmdCuentaAdministradorSubsidio,tmdPeriodoLiquidado,tmdResultadoValidacionLiquidacion,tmdCbeCondicionPersona,tmdCtrCondicionPersona,tmdCemCondicionPersona,tmdMedioDePago,tmdMedioDePagoTransaccion,tmdNumeroCuenta,TmdTipoCuenta,s1)
		EXEC sp_execute_remote SubsidioReferenceData, 
		N'SELECT detalle.tmdUsuarioCreador,detalle.tmdFechaHoraCreacion,detalle.tmdEstado,detalle.tmdMotivoAnulacion,detalle.tmdDetalleAnulacion,detalle.tmdOrigenRegistroSubsidio,detalle.tmdTipoliquidacionSubsidio,detalle.tmdTipoCuotaSubsidio,detalle.tmdValorSubsidioMonetario,detalle.tmdValorDescuento,detalle.tmdValorOriginalAbonado,detalle.tmdValorTotal,detalle.tmdFechaTransaccionRetiro,detalle.tmdUsuarioTransaccionRetiro,detalle.tmdFechaTransaccionAnulacion,detalle.tmdUsuarioTransaccionAnulacion,detalle.tmdFechaHoraUltimaModificacion,detalle.tmdUsuarioUltimaModificacion,detalle.tmdSolicitudLiquidacionSubsidio,detalle.tmdEmpleador,detalle.tmdAfiliadoPrincipal,detalle.tmdGrupoFamiliar,detalle.tmdBeneficiarioDetalle,detalle.tmdAdministradorSubsidio,detalle.tmdRegistroOriginalRelacionado,0,detalle.tmdPeriodoLiquidado,detalle.tmdResultadoValidacionLiquidacion,detalle.tmdCbeCondicionPersona,detalle.tmdCtrCondicionPersona,detalle.tmdCemCondicionPersona,detalle.tmdMedioDePago, detalle.tmdMedioDePagoTransaccion, detalle.tmdNumeroCuenta, detalle.TmdTipoCuenta
				  FROM TempDetalleSubPagos detalle';
	END	

 	INSERT DetalleSubsidioAsignado (dsaUsuarioCreador,dsaFechaHoraCreacion,dsaEstado,dsaMotivoAnulacion,dsaDetalleAnulacion,dsaOrigenRegistroSubsidio,dsaTipoliquidacionSubsidio,dsaTipoCuotaSubsidio,dsaValorSubsidioMonetario,dsaValorDescuento,dsaValorOriginalAbonado,dsaValorTotal,dsaFechaTransaccionRetiro,dsaUsuarioTransaccionRetiro,dsaFechaTransaccionAnulacion,dsaUsuarioTransaccionAnulacion,dsaFechaHoraUltimaModificacion,dsaUsuarioUltimaModificacion,dsaSolicitudLiquidacionSubsidio,dsaEmpleador,dsaAfiliadoPrincipal,dsaGrupoFamiliar,dsaAdministradorSubsidio,dsaIdRegistroOriginalRelacionado,dsaCuentaAdministradorSubsidio,dsaBeneficiarioDetalle,dsaPeriodoLiquidado,dsaResultadoValidacionLiquidacion,dsaCondicionPersonaBeneficiario,dsaCondicionPersonaAfiliado,dsaCondicionPersonaEmpleador)
 	OUTPUT INSERTED.dsaId,INSERTED.dsaUsuarioCreador,INSERTED.dsaFechaHoraCreacion,INSERTED.dsaEstado,INSERTED.dsaMotivoAnulacion,INSERTED.dsaDetalleAnulacion,INSERTED.dsaOrigenRegistroSubsidio,INSERTED.dsaTipoliquidacionSubsidio,INSERTED.dsaTipoCuotaSubsidio,INSERTED.dsaValorSubsidioMonetario,INSERTED.dsaValorDescuento,INSERTED.dsaValorOriginalAbonado,INSERTED.dsaValorTotal,INSERTED.dsaFechaTransaccionRetiro,INSERTED.dsaUsuarioTransaccionRetiro,INSERTED.dsaFechaTransaccionAnulacion,INSERTED.dsaUsuarioTransaccionAnulacion,INSERTED.dsaFechaHoraUltimaModificacion,INSERTED.dsaUsuarioUltimaModificacion,INSERTED.dsaSolicitudLiquidacionSubsidio,INSERTED.dsaEmpleador,INSERTED.dsaAfiliadoPrincipal,INSERTED.dsaGrupoFamiliar,INSERTED.dsaAdministradorSubsidio,INSERTED.dsaIdRegistroOriginalRelacionado,INSERTED.dsaCuentaAdministradorSubsidio,INSERTED.dsaBeneficiarioDetalle,INSERTED.dsaPeriodoLiquidado,INSERTED.dsaResultadoValidacionLiquidacion,INSERTED.dsaCondicionPersonaBeneficiario,INSERTED.dsaCondicionPersonaAfiliado,INSERTED.dsaCondicionPersonaEmpleador
 	INTO @TempDetalleSubPagosIns
 	--(dsaUsuarioCreador,dsaFechaHoraCreacion,dsaEstado,dsaMotivoAnulacion,dsaDetalleAnulacion,dsaOrigenRegistroSubsidio,dsaTipoliquidacionSubsidio,dsaTipoCuotaSubsidio,dsaValorSubsidioMonetario,dsaValorDescuento,dsaValorOriginalAbonado,dsaValorTotal,dsaFechaTransaccionRetiro,dsaUsuarioTransaccionRetiro,dsaFechaTransaccionAnulacion,dsaUsuarioTransaccionAnulacion,dsaFechaHoraUltimaModificacion,dsaUsuarioUltimaModificacion,dsaSolicitudLiquidacionSubsidio,dsaEmpleador,dsaAfiliadoPrincipal,dsaGrupoFamiliar,dsaBeneficiarioDetalle,dsaAdministradorSubsidio,dsaIdRegistroOriginalRelacionado,dsaCuentaAdministradorSubsidio,dsaPeriodoLiquidado,dsaResultadoValidacionLiquidacion,dsaCondicionPersonaBeneficiario,dsaCondicionPersonaAfiliado,dsaCondicionPersonaEmpleador)
	SELECT detalle.tmdUsuarioCreador,detalle.tmdFechaHoraCreacion,detalle.tmdEstado,detalle.tmdMotivoAnulacion,detalle.tmdDetalleAnulacion,detalle.tmdOrigenRegistroSubsidio,detalle.tmdTipoliquidacionSubsidio,detalle.tmdTipoCuotaSubsidio,detalle.tmdValorSubsidioMonetario,detalle.tmdValorDescuento,detalle.tmdValorOriginalAbonado,detalle.tmdValorTotal,detalle.tmdFechaTransaccionRetiro,detalle.tmdUsuarioTransaccionRetiro,detalle.tmdFechaTransaccionAnulacion,detalle.tmdUsuarioTransaccionAnulacion,detalle.tmdFechaHoraUltimaModificacion,detalle.tmdUsuarioUltimaModificacion,detalle.tmdSolicitudLiquidacionSubsidio,detalle.tmdEmpleador,detalle.tmdAfiliadoPrincipal,detalle.tmdGrupoFamiliar,detalle.tmdAdministradorSubsidio,detalle.tmdRegistroOriginalRelacionado,cuenta.casId,	detalle.tmdBeneficiarioDetalle,	detalle.tmdPeriodoLiquidado,detalle.tmdResultadoValidacionLiquidacion,detalle.tmdCbeCondicionPersona,detalle.tmdCtrCondicionPersona,detalle.tmdCemCondicionPersona
	  FROM #TempDetalleSubPagos detalle
	INNER JOIN CuentaAdministradorSubsidio cuenta ON (detalle.tmdAdministradorSubsidio = cuenta.casAdministradorSubsidio
												     AND detalle.tmdMedioDePagoTransaccion  = cuenta.casMedioDePagoTransaccion---cambio 20230826
													 AND isnull(detalle.tmdNumeroCuenta,'-1') = isnull(cuenta.casNumeroCuentaAdmonSubsidio,'-1')
													 AND isnull(cuenta.casTipoCuentaAdmonSubsidio,'-1')=isnull(detalle.TmdTipoCuenta,'-1') 
													AND cuenta.casSolicitudLiquidacionSubsidio = @idSolicitudLiquidacion)	
    group by  detalle.tmdUsuarioCreador,detalle.tmdFechaHoraCreacion,detalle.tmdEstado,detalle.tmdMotivoAnulacion,detalle.tmdDetalleAnulacion,detalle.tmdOrigenRegistroSubsidio,detalle.tmdTipoliquidacionSubsidio,detalle.tmdTipoCuotaSubsidio,detalle.tmdValorSubsidioMonetario,detalle.tmdValorDescuento,detalle.tmdValorOriginalAbonado,detalle.tmdValorTotal,detalle.tmdFechaTransaccionRetiro,detalle.tmdUsuarioTransaccionRetiro,detalle.tmdFechaTransaccionAnulacion,detalle.tmdUsuarioTransaccionAnulacion,detalle.tmdFechaHoraUltimaModificacion,detalle.tmdUsuarioUltimaModificacion,detalle.tmdSolicitudLiquidacionSubsidio,detalle.tmdEmpleador,detalle.tmdAfiliadoPrincipal,detalle.tmdGrupoFamiliar,detalle.tmdAdministradorSubsidio,detalle.tmdRegistroOriginalRelacionado,cuenta.casId,	detalle.tmdBeneficiarioDetalle,	detalle.tmdPeriodoLiquidado,detalle.tmdResultadoValidacionLiquidacion,detalle.tmdCbeCondicionPersona,detalle.tmdCtrCondicionPersona,detalle.tmdCemCondicionPersona

	SELECT @iNumeroRegistros = COUNT(*) FROM #TempDetalleSubPagos

	IF @iNumeroRegistros > 0
	BEGIN
		EXEC [dbo].[USP_UTIL_GET_CrearRevision] 'com.asopagos.entidades.subsidiomonetario.pagos.DetalleSubsidioAsignado',@iNumeroRegistros,'',@sUsuario,@iRevision OUTPUT	

		INSERT aud.DetalleSubsidioAsignado_aud (dsaId,dsaUsuarioCreador,dsaFechaHoraCreacion,dsaEstado,dsaMotivoAnulacion,dsaDetalleAnulacion,dsaOrigenRegistroSubsidio,dsaTipoliquidacionSubsidio,dsaTipoCuotaSubsidio,dsaValorSubsidioMonetario,dsaValorDescuento,dsaValorOriginalAbonado,dsaValorTotal,dsaFechaTransaccionRetiro,dsaUsuarioTransaccionRetiro,dsaFechaTransaccionAnulacion,dsaUsuarioTransaccionAnulacion,dsaFechaHoraUltimaModificacion,dsaUsuarioUltimaModificacion,dsaSolicitudLiquidacionSubsidio,dsaEmpleador,dsaAfiliadoPrincipal,dsaGrupoFamiliar,dsaBeneficiarioDetalle,dsaAdministradorSubsidio,dsaIdRegistroOriginalRelacionado,dsaCuentaAdministradorSubsidio,dsaPeriodoLiquidado,dsaResultadoValidacionLiquidacion,dsaCondicionPersonaBeneficiario,dsaCondicionPersonaAfiliado,dsaCondicionPersonaEmpleador,REV,REVTYPE)
		SELECT detalle.dsaId,detalle.dsaUsuarioCreador,detalle.dsaFechaHoraCreacion,detalle.dsaEstado,detalle.dsaMotivoAnulacion,detalle.dsaDetalleAnulacion,detalle.dsaOrigenRegistroSubsidio,detalle.dsaTipoliquidacionSubsidio,detalle.dsaTipoCuotaSubsidio,detalle.dsaValorSubsidioMonetario,detalle.dsaValorDescuento,detalle.dsaValorOriginalAbonado,detalle.dsaValorTotal,detalle.dsaFechaTransaccionRetiro,detalle.dsaUsuarioTransaccionRetiro,detalle.dsaFechaTransaccionAnulacion,detalle.dsaUsuarioTransaccionAnulacion,detalle.dsaFechaHoraUltimaModificacion,detalle.dsaUsuarioUltimaModificacion,detalle.dsaSolicitudLiquidacionSubsidio,detalle.dsaEmpleador,detalle.dsaAfiliadoPrincipal,detalle.dsaGrupoFamiliar,detalle.dsaBeneficiarioDetalle,detalle.dsaAdministradorSubsidio,detalle.dsaIdRegistroOriginalRelacionado,detalle.dsaCuentaAdministradorSubsidio,detalle.dsaPeriodoLiquidado,detalle.dsaResultadoValidacionLiquidacion,detalle.dsaCondicionPersonaBeneficiario,detalle.dsaCondicionPersonaAfiliado,detalle.dsaCondicionPersonaEmpleador,@iRevision,0
		   FROM @TempDetalleSubPagosIns detalle
		-- INNER JOIN #TempDetalleSubPagos tdetalle ON tdetalle.tmdResultadoValidacionLiquidacion = detalle.dsaResultadoValidacionLiquidacion
		-- INNER JOIN CuentaAdministradorSubsidio cuenta ON (detalle.dsaAdministradorSubsidio = cuenta.casAdministradorSubsidio
		-- 												 AND tdetalle.tmdMedioDePagoTransaccion = cuenta.casMedioDePagoTransaccion----cambio 20230826
		-- 												 AND isnull(tdetalle.tmdNumeroCuenta,'-1') = isnull(cuenta.casNumeroCuentaAdmonSubsidio,'-1')
		-- 												  AND isnull(cuenta.casTipoCuentaAdmonSubsidio,'-1')=isnull(tdetalle.TmdTipoCuenta,'-1') 
		-- 												AND cuenta.casSolicitudLiquidacionSubsidio = @idSolicitudLiquidacion)	
	END

	UPDATE cas
	SET cas.casEmpleador = dsa.dsaEmpleador,
		cas.casAfiliadoPrincipal = dsa.dsaAfiliadoPrincipal,
		cas.casBeneficiarioDetalle = dsa.dsaBeneficiarioDetalle,
		cas.casGrupoFamiliar = dsa.dsaGrupoFamiliar
	FROM CuentaAdministradorSubsidio cas
	INNER JOIN DetalleSubsidioAsignado dsa ON dsa.dsaCuentaAdministradorSubsidio = cas.casId
	WHERE dsa.dsaSolicitudLiquidacionSubsidio = @idSolicitudLiquidacion

	UPDATE cas
	SET cas.casEmpleador = dsa.dsaEmpleador,
		cas.casAfiliadoPrincipal = dsa.dsaAfiliadoPrincipal,
		cas.casBeneficiarioDetalle = dsa.dsaBeneficiarioDetalle,
		cas.casGrupoFamiliar = dsa.dsaGrupoFamiliar
	FROM aud.CuentaAdministradorSubsidio_aud cas
	INNER JOIN aud.DetalleSubsidioAsignado_aud dsa ON dsa.dsaCuentaAdministradorSubsidio = cas.casId
	WHERE dsa.dsaSolicitudLiquidacionSubsidio = @idSolicitudLiquidacion

	EXEC sp_execute_remote SubsidioReferenceData, 
	N'UPDATE rvl SET rvl.rvlEnviadoAPagos = 1
	FROM dbo.ResultadoValidacionLiquidacion rvl
	INNER JOIN TempDetalleSubPagos detalle ON (detalle.tmdResultadoValidacionLiquidacion = rvl.rvlId)'
 	
END TRY
BEGIN CATCH
	INSERT RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
	VALUES (dbo.GetLocalDate(),'[USP_SM_GET_InsertDetalleSubsidioAsignado] @primerRegistro:'
							+ CAST(@primerRegistro AS VARCHAR(20))
							+ '@ultimoRegistro: ' +CAST(@ultimoRegistro AS VARCHAR(20)) ,ERROR_MESSAGE());
END CATCH
	print 'Finaliza USP_SM_GET_InsertCuentaAdministradorSubsidios'
END ;