--14: Se crea sp USP_PG_ConfirmarAbonosMedioPagoBancosArchivo
/****** Object:  StoredProcedure [dbo].[USP_PG_ConfirmarAbonosMedioPagoBancosArchivo]    Script Date: 16/01/2023 11:59:50 ******/
-- =============================================
-- Author:		Eprocess
-- Create date: 2020/08/06
-- Description:	SP para registrar la confirmación de Abonos Bancarios Exitosos y No Exitosos - ARCHIVO
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_PG_ConfirmarAbonosMedioPagoBancosArchivo]
	@sUsuario VARCHAR(255),
	@abonosNoExitosos VARCHAR(MAX),
	@abonosExitosos VARCHAR(MAX)
with execute as owner
AS
BEGIN
BEGIN TRY
	DECLARE @iNumeroRegistros BIGINT,
			@iRevision BIGINT;


	DECLARE @TempCuentaAdminPagosIns table(casId bigint,casFechaHoraCreacionRegistro datetime,casUsuarioCreacionRegistro varchar(200),casTipoTransaccionSubsidio varchar(40),casEstadoTransaccionSubsidio varchar(25),casEstadoLiquidacionSubsidio varchar(25),casOrigenTransaccion varchar(30),casMedioDePagoTransaccion varchar(13),casNumeroTarjetaAdmonSubsidio varchar(50),casCodigoBanco varchar(8),casNombreBanco varchar(255),casTipoCuentaAdmonSubsidio varchar(30),casNumeroCuentaAdmonSubsidio varchar(30),casTipoIdentificacionTitularCuentaAdmonSubsidio varchar(20),casNumeroIdentificacionTitularCuentaAdmonSubsidio varchar(20),casNombreTitularCuentaAdmonSubsidio varchar(200),casFechaHoraTransaccion datetime,casUsuarioTransaccion varchar(200),
	casValorOriginalTransaccion numeric(19,5),casValorRealTransaccion numeric(19,5),casIdTransaccionOriginal bigint,casIdRemisionDatosTerceroPagador varchar(200),casIdTransaccionTerceroPagador varchar(200),casNombreTerceroPagado varchar(200),casIdCuentaAdmonSubsidioRelacionado bigint,casFechaHoraUltimaModificacion datetime,casUsuarioUltimaModificacion varchar(200),casAdministradorSubsidio bigint,casSitioDePago bigint,casSitioDeCobro bigint,casMedioDePago bigint,casSolicitudLiquidacionSubsidio bigint,casCondicionPersonaAdmin bigint, casEmpleador bigint, casAfiliadoPrincipal bigint, casBeneficiarioDetalle bigint, casGrupoFamiliar bigint, casIdCuentaOriginal bigint, casIdPuntoDeCobro varchar(200));

	--se crea tabla para los casId con abono exitoso
	CREATE TABLE #abonosNoExito(casId BIGINT);	
	--se crea tabla para los casId con abono no exitoso
	CREATE TABLE #abonosExitosos(casId BIGINT);	

 	WHILE LEN(@abonosNoExitosos) > 0
    BEGIN
        INSERT INTO #abonosNoExito(casId)
        SELECT left(@abonosNoExitosos, charindex(',', @abonosNoExitosos+',') -1) AS id;
        SET @abonosNoExitosos = stuff(@abonosNoExitosos, 1, charindex(',', @abonosNoExitosos + ','), '');
    END

	 	WHILE LEN(@abonosExitosos) > 0
    BEGIN
        INSERT INTO #abonosExitosos(casId)
        SELECT left(@abonosExitosos, charindex(',', @abonosExitosos+',') -1) AS id;
        SET @abonosExitosos = stuff(@abonosExitosos, 1, charindex(',', @abonosExitosos + ','), '');
    END

    INSERT INTO TransaccionesFallidasSubsidio (tfsCuentaAdministradorSubsidio, tfsFechaHoraRegistro, tfsCanal, tfsEstadoResolucion, tfsTipoTransaccionPagoSubsidio)
    SELECT casId, dbo.getLocalDate(), 'WEB', 'PENDIENTE', 'ABONO_NO_EXITOSO'
    FROM #abonosNoExito

	UPDATE CuentaAdministradorSubsidio 
	SET casEstadoTransaccionSubsidio = 'COBRADO'
	OUTPUT INSERTED.casId,INSERTED.casFechaHoraCreacionRegistro,INSERTED.casUsuarioCreacionRegistro,INSERTED.casTipoTransaccionSubsidio,INSERTED.casEstadoTransaccionSubsidio,INSERTED.casEstadoLiquidacionSubsidio,
	INSERTED.casOrigenTransaccion,INSERTED.casMedioDePagoTransaccion,INSERTED.casNumeroTarjetaAdmonSubsidio,INSERTED.casCodigoBanco, INSERTED.casNombreBanco,INSERTED.casTipoCuentaAdmonSubsidio,
	INSERTED.casNumeroCuentaAdmonSubsidio,INSERTED.casTipoIdentificacionTitularCuentaAdmonSubsidio,INSERTED.casNumeroIdentificacionTitularCuentaAdmonSubsidio,
	INSERTED.casNombreTitularCuentaAdmonSubsidio,INSERTED.casFechaHoraTransaccion,INSERTED.casUsuarioTransaccion,INSERTED.casValorOriginalTransaccion,
	INSERTED.casValorRealTransaccion,INSERTED.casIdTransaccionOriginal,INSERTED.casIdRemisionDatosTerceroPagador,INSERTED.casIdTransaccionTerceroPagador,
	INSERTED.casNombreTerceroPagado,INSERTED.casIdCuentaAdmonSubsidioRelacionado,INSERTED.casFechaHoraUltimaModificacion,INSERTED.casUsuarioUltimaModificacion,
	INSERTED.casAdministradorSubsidio,INSERTED.casSitioDePago,INSERTED.casSitioDeCobro,INSERTED.casMedioDePago,INSERTED.casSolicitudLiquidacionSubsidio,
	INSERTED.casCondicionPersonaAdmin, INSERTED.casEmpleador, INSERTED.casAfiliadoPrincipal, INSERTED.casBeneficiarioDetalle, INSERTED.casGrupoFamiliar, INSERTED.casIdCuentaOriginal, INSERTED.casIdPuntoDeCobro
	INTO @TempCuentaAdminPagosIns
	FROM CuentaAdministradorSubsidio
		WHERE casTipoTransaccionSubsidio = 'ABONO'
		AND casEstadoTransaccionSubsidio = 'ENVIADO'
		AND casMedioDePagoTransaccion = 'TRANSFERENCIA'
		AND casId  IN (SELECT casId from #abonosExitosos)
	

	SELECT @iNumeroRegistros = COUNT(*) FROM @TempCuentaAdminPagosIns

	IF @iNumeroRegistros > 0
	BEGIN
		EXEC [dbo].[USP_UTIL_GET_CrearRevisionActualizar] 'com.asopagos.entidades.subsidiomonetario.pagos.CuentaAdministradorSubsidio',@iNumeroRegistros,'',@sUsuario,@iRevision OUTPUT

		INSERT INTO aud.CuentaAdministradorSubsidio_aud (casId,casFechaHoraCreacionRegistro,casUsuarioCreacionRegistro,casTipoTransaccionSubsidio,casEstadoTransaccionSubsidio,casEstadoLiquidacionSubsidio,casOrigenTransaccion,casMedioDePagoTransaccion,casNumeroTarjetaAdmonSubsidio,casCodigoBanco, casNombreBanco,casTipoCuentaAdmonSubsidio,casNumeroCuentaAdmonSubsidio,casTipoIdentificacionTitularCuentaAdmonSubsidio,casNumeroIdentificacionTitularCuentaAdmonSubsidio,casNombreTitularCuentaAdmonSubsidio,casFechaHoraTransaccion,casUsuarioTransaccion,casValorOriginalTransaccion,casValorRealTransaccion,casIdTransaccionOriginal,casIdRemisionDatosTerceroPagador,casIdTransaccionTerceroPagador,casNombreTerceroPagado,casIdCuentaAdmonSubsidioRelacionado,casFechaHoraUltimaModificacion,casUsuarioUltimaModificacion,casAdministradorSubsidio,casSitioDePago,casSitioDeCobro,casMedioDePago,casSolicitudLiquidacionSubsidio,casCondicionPersonaAdmin,casEmpleador,casAfiliadoPrincipal,casBeneficiarioDetalle,casGrupoFamiliar,casIdCuentaOriginal,casIdPuntoDeCobro,REV,REVTYPE)
		SELECT casId,casFechaHoraCreacionRegistro,casUsuarioCreacionRegistro,casTipoTransaccionSubsidio,casEstadoTransaccionSubsidio,casEstadoLiquidacionSubsidio,casOrigenTransaccion,casMedioDePagoTransaccion,casNumeroTarjetaAdmonSubsidio,casCodigoBanco,casNombreBanco,casTipoCuentaAdmonSubsidio,casNumeroCuentaAdmonSubsidio,casTipoIdentificacionTitularCuentaAdmonSubsidio,casNumeroIdentificacionTitularCuentaAdmonSubsidio,casNombreTitularCuentaAdmonSubsidio,casFechaHoraTransaccion,casUsuarioTransaccion,casValorOriginalTransaccion,casValorRealTransaccion,casIdTransaccionOriginal,casIdRemisionDatosTerceroPagador,casIdTransaccionTerceroPagador,casNombreTerceroPagado,casIdCuentaAdmonSubsidioRelacionado,casFechaHoraUltimaModificacion,casUsuarioUltimaModificacion,casAdministradorSubsidio,casSitioDePago,casSitioDeCobro,casMedioDePago,casSolicitudLiquidacionSubsidio,casCondicionPersonaAdmin,casEmpleador,casAfiliadoPrincipal,casBeneficiarioDetalle,casGrupoFamiliar,casIdCuentaOriginal,casIdPuntoDeCobro,@iRevision,1
		FROM @TempCuentaAdminPagosIns
	END

END TRY
BEGIN CATCH
	INSERT RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
	VALUES (dbo.GetLocalDate(),'[USP_PG_ConfirmarAbonosMedioPagoBancos]' ,ERROR_MESSAGE());
END CATCH
	print 'Finaliza USP_PG_ConfirmarAbonosMedioPagoBancos'
END ;
