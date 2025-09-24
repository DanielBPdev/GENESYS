-- =============================================
-- Author:		Fabian López
-- Create date: 2020/09/09
-- Description:	Consulta la dispersión y monto liquidado para un medio de pago
-- =============================================
create PROCEDURE [dbo].[USP_SM_GET_ConsultarDispersionMontoLiquidado]
(
	@estadoLiquidacion VARCHAR(50),
	@medioDePago varchar(50),
	@numeroRadicado varchar(50)
)
AS
SET NOCOUNT ON;
BEGIN 
	
	DROP TABLE IF EXISTS #cuentas;
	CREATE TABLE #cuentas (id BIGINT, idAfiliado BIGINT, monto NUMERIC(19,5), dsaCondicionPersonaAfiliado BIGINT);		
	CREATE CLUSTERED INDEX idxCta ON #cuentas (id);
	
	INSERT #cuentas (id, idAfiliado, monto,  dsaCondicionPersonaAfiliado)
	SELECT cas.casId AS identificadorCuentaAdministrador,
			dsa.dsaAfiliadoPrincipal AS identificadorAfiliadoPrincipal,
			COALESCE(SUM(dsa.dsaValorOriginalAbonado), 0) AS monto,
			dsa.dsaCondicionPersonaAfiliado AS condicionAfiliado
			FROM DetalleSubsidioAsignado dsa
			INNER JOIN CuentaAdministradorSubsidio cas ON dsa.dsaCuentaAdministradorSubsidio = cas.casId
			INNER JOIN SolicitudLiquidacionSubsidio sls ON dsa.dsaSolicitudLiquidacionSubsidio = sls.slsId
			INNER JOIN Solicitud sol ON sls.slsSolicitudGlobal = sol.solId
			WHERE cas.casMedioDePagoTransaccion = @medioDePago
			AND cas.casEstadoLiquidacionSubsidio = @estadoLiquidacion
			AND sol.solNumeroRadicacion = @numeroRadicado
			GROUP BY cas.casId, dsa.dsaAfiliadoPrincipal, dsa.dsaCondicionPersonaAfiliado
	

	
	-- Cuando el administrador del subsidio tiene más de un afiliado asociado muestra los campos de
	-- tipoIdentificacionTrabajador, numeroIdentificacionTrabajador y razonSocialAfi vacíos
	-- si sólo tiene un afiliado asociado se muestran los datos de estos campos
	SELECT adminTodo.tipoIdentificacionAdministrador,
			adminTodo.numeroIdentificacionAdministrador,
			adminTodo.razonSocialAdmin,
			adminTodo.numeroTarjeta,
			adminTodo.tipoIdentificacionTrabajador,
			adminTodo.numeroIdentificacionTrabajador,
			adminTodo.razonSocialAfi,
			adminTodo.montoAfiliado
	FROM
	(SELECT cta.id,
			AdministradorDeSubsidio.perTipoIdentificacion AS tipoIdentificacionAdministrador,
			AdministradorDeSubsidio.perNumeroIdentificacion AS numeroIdentificacionAdministrador,
			AdministradorDeSubsidio.perRazonSocial AS razonSocialAdmin,
			cas.casNumeroTarjetaAdmonSubsidio AS numeroTarjeta,
			Trabajador.perTipoIdentificacion AS tipoIdentificacionTrabajador,
			Trabajador.perNumeroIdentificacion AS numeroIdentificacionTrabajador,
			Trabajador.perRazonSocial AS razonSocialAfi,
			COALESCE(SUM(cta.monto), 0) AS montoAfiliado
	FROM #cuentas cta
	INNER JOIN CuentaAdministradorSubsidio cas ON cas.casId = cta.id
	INNER JOIN AdministradorSubsidio adm ON cas.casAdministradorSubsidio = adm.asuId
	INNER JOIN Persona AS AdministradorDeSubsidio ON adm.asuPersona = AdministradorDeSubsidio.perId
	INNER JOIN Afiliado afi ON cta.idAfiliado = afi.afiId
	INNER JOIN Persona AS Trabajador ON afi.afiPersona = Trabajador.perId
	GROUP BY cta.id,AdministradorDeSubsidio.perTipoIdentificacion, AdministradorDeSubsidio.perNumeroIdentificacion, AdministradorDeSubsidio.perRazonSocial
	,cas.casNumeroTarjetaAdmonSubsidio,Trabajador.perTipoIdentificacion,Trabajador.perNumeroIdentificacion, Trabajador.perRazonSocial
	) AS adminTodo
	INNER JOIN
	(SELECT cta.id, adm.asuId,COUNT(adm.asuId) as cantidadAdmin
		FROM #cuentas cta
		INNER JOIN CuentaAdministradorSubsidio cas ON cas.casId = cta.id
		INNER JOIN AdministradorSubsidio adm ON cas.casAdministradorSubsidio = adm.asuId
		INNER JOIN Persona AS AdministradorDeSubsidio ON adm.asuPersona = AdministradorDeSubsidio.perId
		INNER JOIN Afiliado afi ON cta.idAfiliado = afi.afiId
		INNER JOIN Persona AS Trabajador ON afi.afiPersona = Trabajador.perId
		GROUP BY cta.id, adm.asuId
		HAVING COUNT(adm.asuId) = 1
	) AS adminUnAfiliado ON adminUnAfiliado.id = adminTodo.id
	UNION ALL
	SELECT adminTodo.tipoIdentificacionAdministrador,
			adminTodo.numeroIdentificacionAdministrador,
			adminTodo.razonSocialAdmin,
			adminTodo.numeroTarjeta,
			'' AS tipoIdentificacionTrabajador,
			'' AS numeroIdentificacionTrabajador,
			'' AS razonSocialAfi,
			adminTodo.montoAfiliado
	FROM
	(SELECT cta.id,
			AdministradorDeSubsidio.perTipoIdentificacion AS tipoIdentificacionAdministrador,
			AdministradorDeSubsidio.perNumeroIdentificacion AS numeroIdentificacionAdministrador,
			AdministradorDeSubsidio.perRazonSocial AS razonSocialAdmin,
			cas.casNumeroTarjetaAdmonSubsidio AS numeroTarjeta,
			COALESCE(SUM(cta.monto), 0) AS montoAfiliado
	FROM #cuentas cta
	INNER JOIN CuentaAdministradorSubsidio cas ON cas.casId = cta.id
	INNER JOIN AdministradorSubsidio adm ON cas.casAdministradorSubsidio = adm.asuId
	INNER JOIN Persona AS AdministradorDeSubsidio ON adm.asuPersona = AdministradorDeSubsidio.perId
	INNER JOIN Afiliado afi ON cta.idAfiliado = afi.afiId
	INNER JOIN Persona AS Trabajador ON afi.afiPersona = Trabajador.perId
	GROUP BY cta.id,AdministradorDeSubsidio.perTipoIdentificacion, AdministradorDeSubsidio.perNumeroIdentificacion, AdministradorDeSubsidio.perRazonSocial
	,cas.casNumeroTarjetaAdmonSubsidio
	) AS adminTodo
	INNER JOIN
	(SELECT cta.id, adm.asuId,COUNT(adm.asuId) as cantidadAdmin
	FROM #cuentas cta
	INNER JOIN CuentaAdministradorSubsidio cas ON cas.casId = cta.id
	INNER JOIN AdministradorSubsidio adm ON cas.casAdministradorSubsidio = adm.asuId
	INNER JOIN Persona AS AdministradorDeSubsidio ON adm.asuPersona = AdministradorDeSubsidio.perId
	INNER JOIN Afiliado afi ON cta.idAfiliado = afi.afiId
	INNER JOIN Persona AS Trabajador ON afi.afiPersona = Trabajador.perId
	GROUP BY cta.id, adm.asuId
	HAVING COUNT(adm.asuId) > 1
	) AS adminUnAfiliado ON adminUnAfiliado.id = adminTodo.id
		
			
END	 
;