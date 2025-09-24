-- =============================================
-- Author:		Fabian López
-- Create date: 2020/09/04
-- Description:	SP para consultar las Transacciones de Subsidios
-- =============================================
create PROCEDURE [dbo].[USP_PG_GET_ConsultaTransaccionesSubsidio]
(
	@estadoTransaccion VARCHAR(100),
	@medioDePago varchar(100),
	@tipoTransaccion varchar(100),
	@fechaInicio date,
	@fechaFin date,
	@idsAminSubsidio varchar(MAX),
	@offsetParametro int
)
AS
SET NOCOUNT ON;
BEGIN 
	
	DECLARE @filtrarAdmin INT
	DECLARE @cantidadRegistros INT
	SET @cantidadRegistros= 900000
	
	DROP TABLE IF EXISTS #administradoresSubsidios
	CREATE TABLE #administradoresSubsidios(idAdminSubsidio BIGINT);	
	CREATE TABLE #cuentas (id BIGINT, casSitioDePago BIGINT, casSitioDeCobro BIGINT, rpaPersonaAutorizada BIGINT, personaAdmin BIGINT, sipNombre VARCHAR(100), munNombre VARCHAR(50), depNombre VARCHAR(100),razonSocialAdmin VARCHAR(250),razonSocialPerAut VARCHAR(250),tipoIdAdmin VARCHAR(20),numIdAdmin VARCHAR(16));		
	
	INSERT RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
	VALUES (dbo.GetLocalDate(),'[USP_PG_GET_ConsultaTransaccionesSubsidio] @idSolicitudLiquidacion:'
							+ CAST(@tipoTransaccion AS VARCHAR(50))+ CAST(@fechaInicio AS VARCHAR(50))+ CAST(@fechaFin AS VARCHAR(50)),'ERROR_MESSAGE()');

	SET @filtrarAdmin = 0
	IF (LEN(@idsAminSubsidio) > 0)
	BEGIN
		SET @filtrarAdmin = 1
	 	WHILE LEN(@idsAminSubsidio) > 0
	    BEGIN
	        INSERT INTO #administradoresSubsidios(idAdminSubsidio)
	        SELECT left(@idsAminSubsidio, charindex(',', @idsAminSubsidio+',') -1) AS id;
	        SET @idsAminSubsidio = stuff(@idsAminSubsidio, 1, charindex(',', @idsAminSubsidio + ','), '');
	    END
	END 
	
	INSERT #cuentas(id, casSitioDePago, casSitioDeCobro,  rpaPersonaAutorizada, personaAdmin, sipNombre, munNombre, depNombre,razonSocialAdmin,razonSocialPerAut,tipoIdAdmin,numIdAdmin)
	SELECT cas.casId, cas.casSitioDePago, cas.casSitioDeCobro, retPer.rpaPersonaAutorizada, admin.asuPersona, sp.sipNombre, mun.munNombre, dep.depNombre,adminPer.perRazonSocial, perAut.perRazonSocial,adminPer.perTipoIdentificacion,adminPer.perNumeroIdentificacion 
		FROM CuentaAdministradorSubsidio cas
			LEFT JOIN dbo.RetiroPersonaAutorizadaCobroSubsidio AS retPer ON cas.casId = retPer.rpaCuentaAdministradorSubsidio
			INNER JOIN dbo.AdministradorSubsidio AS admin ON admin.asuId = cas.casAdministradorSubsidio
			LEFT JOIN SitioPago sp ON sp.sipId = cas.casSitioDePago 
			LEFT JOIN SitioPago sc ON sc.sipId = cas.casSitioDeCobro
			LEFT JOIN dbo.Infraestructura inf ON sc.sipInfraestructura = inf.infId
			LEFT JOIN dbo.Municipio mun ON mun.munId = inf.infMunicipio
			LEFT JOIN dbo.Departamento dep ON dep.depId = mun.munDepartamento
			INNER JOIN dbo.Persona AS adminPer ON adminPer.perId = admin.asuPersona 
			LEFT JOIN dbo.Persona AS perAut ON perAut.perId = retPer.rpaPersonaAutorizada
			WHERE (convert(date,cas.casFechaHoraTransaccion) BETWEEN @fechaInicio AND @fechaFin)
			AND (@estadoTransaccion = ''  OR cas.casEstadoTransaccionSubsidio = @estadoTransaccion)
			AND (@medioDePago = ''  OR cas.casMedioDePagoTransaccion = @medioDePago)
			AND (@tipoTransaccion = '' OR cas.casTipoTransaccionSubsidio = @tipoTransaccion) 
			AND (@filtrarAdmin = 0 OR cas.casAdministradorSubsidio IN (SELECT idAdminSubsidio FROM #administradoresSubsidios))
			order by cas.casId 
			offset @offsetParametro rows fetch next @cantidadRegistros rows only;
			
	SELECT cta.id,
			cas.casFechaHoraCreacionRegistro ,
			cas.casUsuarioCreacionRegistro ,
			cas.casTipoTransaccionSubsidio ,
			cas.casEstadoTransaccionSubsidio ,
			cas.casOrigenTransaccion ,
			cas.casMedioDePagoTransaccion ,
			cas.casNumeroTarjetaAdmonSubsidio ,
			cas.casCodigoBanco ,
			cas.casNombreBanco ,
			cas.casTipoCuentaAdmonSubsidio ,
			cas.casNumeroCuentaAdmonSubsidio ,
			cas.casTipoIdentificacionTitularCuentaAdmonSubsidio ,
			cas.casNumeroIdentificacionTitularCuentaAdmonSubsidio ,
			cas.casNombreTitularCuentaAdmonSubsidio ,
			cas.casFechaHoraTransaccion ,
			cas.casUsuarioTransaccion ,
			cas.casValorOriginalTransaccion ,
			cas.casValorRealTransaccion ,
			cas.casIdTransaccionOriginal ,
			cas.casIdRemisionDatosTerceroPagador ,
			cas.casIdTransaccionTerceroPagador ,
			cas.casNombreTerceroPagado ,
			cas.casIdCuentaAdmonSubsidioRelacionado ,
			cas.casFechaHoraUltimaModificacion ,
			cas.casUsuarioUltimaModificacion ,
			cta.sipNombre, 
			cta.depNombre,
			cta.munNombre,
			cta.razonSocialAdmin AS razonSocialAdmin,
			cta.razonSocialPerAut AS razonSocialAut,
			cta.tipoIdAdmin,
			cta.numIdAdmin,
			cas.casIdPuntoDeCobro 
		FROM CuentaAdministradorSubsidio cas
			INNER JOIN #cuentas cta  ON cas.casId = cta.id
			
			
END	 
;