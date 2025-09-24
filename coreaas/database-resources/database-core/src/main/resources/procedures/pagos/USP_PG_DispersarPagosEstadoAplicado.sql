-- =============================================
-- Author:		Andrés Camilo Marín
-- Create date: 2020/09/01
-- Description:	SP para actualizar el estado de la transaccion de los pagos exitosos con ids de abonos exitosos
-- =============================================
CREATE PROCEDURE [dbo].[USP_PG_DispersarPagosEstadoAplicado]
	@sUsuario VARCHAR(255),
	@sNumeroRadicacion VARCHAR(255),
	@sEstadoTransaccion VARCHAR(255),
	@listaMediosPago VARCHAR(MAX),
	@listaAbonosExitosos VARCHAR(MAX)
with execute as owner
AS
BEGIN
BEGIN TRY
	DECLARE @iNumeroRegistros BIGINT,
			@iRevision BIGINT;

    --Tabla para almacenar los datos insertados en el UPDATE
	CREATE TABLE #TempCuentaAdminPagosIns(casId bigint,casFechaHoraCreacionRegistro datetime,casUsuarioCreacionRegistro varchar(200),casTipoTransaccionSubsidio varchar(40),casEstadoTransaccionSubsidio varchar(25),casEstadoLiquidacionSubsidio varchar(25),casOrigenTransaccion varchar(30),casMedioDePagoTransaccion varchar(13),casNumeroTarjetaAdmonSubsidio varchar(50),casCodigoBanco varchar(8),casNombreBanco varchar(255),casTipoCuentaAdmonSubsidio varchar(30),casNumeroCuentaAdmonSubsidio varchar(30),casTipoIdentificacionTitularCuentaAdmonSubsidio varchar(20),casNumeroIdentificacionTitularCuentaAdmonSubsidio varchar(20),casNombreTitularCuentaAdmonSubsidio varchar(200),casFechaHoraTransaccion datetime,casUsuarioTransaccion varchar(200),
	casValorOriginalTransaccion numeric(19,5),casValorRealTransaccion numeric(19,5),casIdTransaccionOriginal bigint,casIdRemisionDatosTerceroPagador varchar(200),casIdTransaccionTerceroPagador varchar(200),casNombreTerceroPagado varchar(200),casIdCuentaAdmonSubsidioRelacionado bigint,casFechaHoraUltimaModificacion datetime,casUsuarioUltimaModificacion varchar(200),casAdministradorSubsidio bigint,casSitioDePago bigint,casSitioDeCobro bigint,casMedioDePago bigint,casSolicitudLiquidacionSubsidio bigint,casCondicionPersonaAdmin bigint, casEmpleador bigint, casAfiliadoPrincipal bigint, casBeneficiarioDetalle bigint, casGrupoFamiliar bigint, casIdCuentaOriginal bigint, casIdPuntoDeCobro varchar(200));

	--Extraer la lista de medios de pago que llega como parametro
	CREATE TABLE #listaMediosPago(casMedioDePagoTransaccion VARCHAR(255));

	WHILE LEN(@listaMediosPago) > 0
    BEGIN
        INSERT INTO #listaMediosPago(casMedioDePagoTransaccion)
        SELECT left(@listaMediosPago, charindex(',', @listaMediosPago+',') -1) AS medioPago;
        SET @listaMediosPago = stuff(@listaMediosPago, 1, charindex(',', @listaMediosPago + ','), '');
    END

    --Extraer la lista de abonos exitosos que llega como parametro
	CREATE TABLE #listaAbonosExitosos(casId BIGINT);

	WHILE LEN(@listaAbonosExitosos) > 0
    BEGIN
        INSERT INTO #listaAbonosExitosos(casId)
        SELECT left(@listaAbonosExitosos, charindex(',', @listaAbonosExitosos+',') -1) AS id;
        SET @listaAbonosExitosos = stuff(@listaAbonosExitosos, 1, charindex(',', @listaAbonosExitosos + ','), '');
    END

    UPDATE cas
    --Mantis 259718 Si el valor es igual a cero se actualiza a estado COBRADO
	SET cas.casEstadoTransaccionSubsidio = CASE WHEN cas.casValorRealTransaccion = 0 THEN 'COBRADO' ELSE @sEstadoTransaccion END
	OUTPUT INSERTED.casId,INSERTED.casFechaHoraCreacionRegistro,INSERTED.casUsuarioCreacionRegistro,INSERTED.casTipoTransaccionSubsidio,INSERTED.casEstadoTransaccionSubsidio,INSERTED.casEstadoLiquidacionSubsidio,
	INSERTED.casOrigenTransaccion,INSERTED.casMedioDePagoTransaccion,INSERTED.casNumeroTarjetaAdmonSubsidio,INSERTED.casCodigoBanco, INSERTED.casNombreBanco,INSERTED.casTipoCuentaAdmonSubsidio,
	INSERTED.casNumeroCuentaAdmonSubsidio,INSERTED.casTipoIdentificacionTitularCuentaAdmonSubsidio,INSERTED.casNumeroIdentificacionTitularCuentaAdmonSubsidio,
	INSERTED.casNombreTitularCuentaAdmonSubsidio,INSERTED.casFechaHoraTransaccion,INSERTED.casUsuarioTransaccion,INSERTED.casValorOriginalTransaccion,
	INSERTED.casValorRealTransaccion,INSERTED.casIdTransaccionOriginal,INSERTED.casIdRemisionDatosTerceroPagador,INSERTED.casIdTransaccionTerceroPagador,
	INSERTED.casNombreTerceroPagado,INSERTED.casIdCuentaAdmonSubsidioRelacionado,INSERTED.casFechaHoraUltimaModificacion,INSERTED.casUsuarioUltimaModificacion,
	INSERTED.casAdministradorSubsidio,INSERTED.casSitioDePago,INSERTED.casSitioDeCobro,INSERTED.casMedioDePago,INSERTED.casSolicitudLiquidacionSubsidio,
	INSERTED.casCondicionPersonaAdmin, INSERTED.casEmpleador, INSERTED.casAfiliadoPrincipal, INSERTED.casBeneficiarioDetalle, INSERTED.casGrupoFamiliar, INSERTED.casIdCuentaOriginal, INSERTED.casIdPuntoDeCobro
	INTO #TempCuentaAdminPagosIns
	FROM CuentaAdministradorSubsidio cas
	JOIN
	(SELECT DISTINCT cas.casId,cas.casFechaHoraCreacionRegistro,cas.casUsuarioCreacionRegistro,cas.casTipoTransaccionSubsidio,cas.casEstadoTransaccionSubsidio,cas.casEstadoLiquidacionSubsidio,
	cas.casOrigenTransaccion,cas.casMedioDePagoTransaccion,cas.casNumeroTarjetaAdmonSubsidio,cas.casCodigoBanco, cas.casNombreBanco,cas.casTipoCuentaAdmonSubsidio,
	cas.casNumeroCuentaAdmonSubsidio,cas.casTipoIdentificacionTitularCuentaAdmonSubsidio,cas.casNumeroIdentificacionTitularCuentaAdmonSubsidio,
	cas.casNombreTitularCuentaAdmonSubsidio,cas.casFechaHoraTransaccion,cas.casUsuarioTransaccion,cas.casValorOriginalTransaccion,
	cas.casValorRealTransaccion,cas.casIdTransaccionOriginal,cas.casIdRemisionDatosTerceroPagador,cas.casIdTransaccionTerceroPagador,
	cas.casNombreTerceroPagado,cas.casIdCuentaAdmonSubsidioRelacionado,cas.casFechaHoraUltimaModificacion,cas.casUsuarioUltimaModificacion,
	cas.casAdministradorSubsidio,cas.casSitioDePago,cas.casSitioDeCobro,cas.casMedioDePago,cas.casSolicitudLiquidacionSubsidio,
	cas.casCondicionPersonaAdmin, cas.casEmpleador, cas.casAfiliadoPrincipal, cas.casBeneficiarioDetalle, cas.casGrupoFamiliar, cas.casIdCuentaOriginal, cas.casIdPuntoDeCobro
	FROM CuentaAdministradorSubsidio cas
	JOIN DetalleSubsidioAsignado dsa ON cas.casId = dsa.dsaCuentaAdministradorSubsidio
	JOIN SolicitudLiquidacionSubsidio sls ON dsa.dsaSolicitudLiquidacionSubsidio = sls.slsId
	JOIN solicitud sol ON sol.solId=sls.slsSolicitudGlobal
	JOIN (SELECT casMedioDePagoTransaccion FROM #listaMediosPago) mediosPago ON mediosPago.casMedioDePagoTransaccion = cas.casMedioDePagoTransaccion
	JOIN (SELECT casId FROM #listaAbonosExitosos) abonosExitosos ON abonosExitosos.casId = cas.casId
	WHERE sol.solNumeroRadicacion = @sNumeroRadicacion
	AND cas.casEstadoLiquidacionSubsidio = 'GENERADO') cuentas ON cuentas.casId = cas.casId;


	SELECT @iNumeroRegistros = COUNT(*) FROM #TempCuentaAdminPagosIns

	IF @iNumeroRegistros > 0
	BEGIN
		EXEC [dbo].[USP_UTIL_GET_CrearRevisionActualizar] 'com.asopagos.entidades.subsidiomonetario.pagos.CuentaAdministradorSubsidio',@iNumeroRegistros,'',@sUsuario,@iRevision OUTPUT

		INSERT INTO aud.CuentaAdministradorSubsidio_aud (casId,casFechaHoraCreacionRegistro,casUsuarioCreacionRegistro,casTipoTransaccionSubsidio,casEstadoTransaccionSubsidio,casEstadoLiquidacionSubsidio,casOrigenTransaccion,casMedioDePagoTransaccion,casNumeroTarjetaAdmonSubsidio,casCodigoBanco, casNombreBanco,casTipoCuentaAdmonSubsidio,casNumeroCuentaAdmonSubsidio,casTipoIdentificacionTitularCuentaAdmonSubsidio,casNumeroIdentificacionTitularCuentaAdmonSubsidio,casNombreTitularCuentaAdmonSubsidio,casFechaHoraTransaccion,casUsuarioTransaccion,casValorOriginalTransaccion,casValorRealTransaccion,casIdTransaccionOriginal,casIdRemisionDatosTerceroPagador,casIdTransaccionTerceroPagador,casNombreTerceroPagado,casIdCuentaAdmonSubsidioRelacionado,casFechaHoraUltimaModificacion,casUsuarioUltimaModificacion,casAdministradorSubsidio,casSitioDePago,casSitioDeCobro,casMedioDePago,casSolicitudLiquidacionSubsidio,casCondicionPersonaAdmin,casEmpleador,casAfiliadoPrincipal,casBeneficiarioDetalle,casGrupoFamiliar,casIdCuentaOriginal,casIdPuntoDeCobro,REV,REVTYPE)
		SELECT casId,casFechaHoraCreacionRegistro,casUsuarioCreacionRegistro,casTipoTransaccionSubsidio,casEstadoTransaccionSubsidio,casEstadoLiquidacionSubsidio,casOrigenTransaccion,casMedioDePagoTransaccion,casNumeroTarjetaAdmonSubsidio,casCodigoBanco,casNombreBanco,casTipoCuentaAdmonSubsidio,casNumeroCuentaAdmonSubsidio,casTipoIdentificacionTitularCuentaAdmonSubsidio,casNumeroIdentificacionTitularCuentaAdmonSubsidio,casNombreTitularCuentaAdmonSubsidio,casFechaHoraTransaccion,casUsuarioTransaccion,casValorOriginalTransaccion,casValorRealTransaccion,casIdTransaccionOriginal,casIdRemisionDatosTerceroPagador,casIdTransaccionTerceroPagador,casNombreTerceroPagado,casIdCuentaAdmonSubsidioRelacionado,casFechaHoraUltimaModificacion,casUsuarioUltimaModificacion,casAdministradorSubsidio,casSitioDePago,casSitioDeCobro,casMedioDePago,casSolicitudLiquidacionSubsidio,casCondicionPersonaAdmin,casEmpleador,casAfiliadoPrincipal,casBeneficiarioDetalle,casGrupoFamiliar,casIdCuentaOriginal,casIdPuntoDeCobro,@iRevision,1
		FROM #TempCuentaAdminPagosIns
	END

	DROP TABLE #listaMediosPago;
	DROP TABLE #TempCuentaAdminPagosIns;
	DROP TABLE #listaAbonosExitosos;

END TRY
BEGIN CATCH
	INSERT RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
	VALUES (dbo.GetLocalDate(),'[USP_PG_DispersarPagosEstadoAplicado]' ,ERROR_MESSAGE());
END CATCH
	print 'Finaliza USP_PG_DispersarPagosEstadoAplicado'
END ;