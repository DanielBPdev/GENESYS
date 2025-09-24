-- =============================================
-- Author:		Miguel Angel Osorio
-- Create date: 2018/01/10
-- Update : 2018/02/22
-- Description:	Procedimiento almacenado que obtiene los valores del archivo que se cargan de los retiros del tercero pagador y llama el proceso almacenado USP_VALIDAR_CAMPOS_ARCHIVO_RETIRO_CUENTA para conciliar los campos del archivo con los de la cuenta del administrador de subsidio. 
-- =============================================
CREATE PROCEDURE USP_PG_ResultadosDispersionAdministradorMedioPago(
	@sNumeroRadicado VARCHAR(23),
	@sMedio VARCHAR(40)
	)
AS

SELECT 
    TotalesAcumulados.condicionAdministrador,
    TotalesAcumulados.condicionBeneficiario,
    PersonaAdministrador.perTipoIdentificacion AS tipoIdentificacionAdministrador,
    PersonaAdministrador.perNumeroIdentificacion AS numeroIdentificacionAdministrador,
    PersonaAdministrador.perRazonSocial AS nombreAdministrador,
    PersonaBeneficiario.perTipoIdentificacion AS tipoIdentificacionBeneficiario,
    PersonaBeneficiario.perNumeroIdentificacion AS numeroIdentificacionBeneficiario,
    PersonaBeneficiario.perRazonSocial AS nombreBeneficiario,
    Beneficiario.benTipoBeneficiario,
    TotalesAcumulados.totalDerecho,
    TotalesAcumulados.totalDescuentos,
    TotalesAcumulados.totalDispersar,
    TotalesDispersados.totalDispersado AS totalDispersadoAdministrador,
    TotalesProgramados.totalProgramado AS totalProgramadoAdministrador,
    CASE
        WHEN 'TRANSFERENCIA' IN (@sMedio) THEN
            (SELECT MedioTransferencia.metCobroJudicial
             FROM MedioTransferencia
             WHERE TotalesAcumulados.idMedioDePago = MedioTransferencia.mdpId)
        ELSE -1
    END AS pagoJudicial,
    TotalesAcumulados.totalDescuentosEntidad AS totalDescuentosEntidad
FROM (
    SELECT 
        TotalesFallecimiento.condicionAdministrador,
        TotalesFallecimiento.condicionBeneficiario,
        TotalesFallecimiento.beneficiarioDetalle,
        TotalesFallecimiento.administradorSubsidio,
        TotalesFallecimiento.afiliadoPrincipal,
        SUM(TotalesFallecimiento.valorSubsidioMonetario) AS totalDerecho,
        SUM(TotalesFallecimiento.valorDescuento) AS totalDescuentos,
        SUM(TotalesFallecimiento.valorOriginalAbonado) AS totalDispersar,
        TotalesFallecimiento.idMedioDePago,
        COALESCE(SUM(TotalesFallecimiento.totalDescuentosEntidad), 0) AS totalDescuentosEntidad
    FROM (
        SELECT 
            DetalleSubsidioAsignado.dsaBeneficiarioDetalle AS beneficiarioDetalle,
            DetalleSubsidioAsignado.dsaAdministradorSubsidio AS administradorSubsidio,
            DetalleSubsidioAsignado.dsaAfiliadoPrincipal AS afiliadoPrincipal,
            CuentaAdministradorSubsidio.casCondicionPersonaAdmin AS condicionAdministrador,
            DetalleSubsidioAsignado.dsaCondicionPersonaBeneficiario AS condicionBeneficiario,
            DetalleSubsidioAsignado.dsaValorSubsidioMonetario AS valorSubsidioMonetario,
            DetalleSubsidioAsignado.dsaValorOriginalAbonado AS valorOriginalAbonado,
            DetalleSubsidioAsignado.dsaValorDescuento AS valorDescuento,
            CuentaAdministradorSubsidio.casMedioDePago AS idMedioDePago,
            COALESCE(SUM(DescuentosSubsidioAsignado.desMontoDescontado), 0) AS totalDescuentosEntidad
        FROM DetalleSubsidioAsignado
        INNER JOIN SolicitudLiquidacionSubsidio ON DetalleSubsidioAsignado.dsaSolicitudLiquidacionSubsidio = SolicitudLiquidacionSubsidio.slsId
        INNER JOIN Solicitud ON SolicitudLiquidacionSubsidio.slsSolicitudGlobal = Solicitud.solId
        INNER JOIN CuentaAdministradorSubsidio ON DetalleSubsidioAsignado.dsaCuentaAdministradorSubsidio = CuentaAdministradorSubsidio.casId
		LEFT JOIN DescuentosSubsidioAsignado ON DetalleSubsidioAsignado.dsaId = DescuentosSubsidioAsignado.desDetalleSubsidioAsignado
        WHERE CuentaAdministradorSubsidio.casMedioDePagoTransaccion IN (@sMedio)
        AND CuentaAdministradorSubsidio.casEstadoLiquidacionSubsidio = 'GENERADO'
        AND DetalleSubsidioAsignado.dsaEstado <> 'PROGRAMADO_APLICADO'
        AND Solicitud.solNumeroRadicacion = @sNumeroRadicado
        GROUP BY 
            DetalleSubsidioAsignado.dsaBeneficiarioDetalle,
            DetalleSubsidioAsignado.dsaAdministradorSubsidio,
            DetalleSubsidioAsignado.dsaAfiliadoPrincipal,
            CuentaAdministradorSubsidio.casCondicionPersonaAdmin,
            DetalleSubsidioAsignado.dsaCondicionPersonaBeneficiario,
            DetalleSubsidioAsignado.dsaValorSubsidioMonetario,
            DetalleSubsidioAsignado.dsaValorOriginalAbonado,
            DetalleSubsidioAsignado.dsaValorDescuento,
            CuentaAdministradorSubsidio.casMedioDePago

        UNION ALL

        SELECT 
            DetalleSubsidioAsignadoProgramado.dprBeneficiarioDetalle AS beneficiarioDetalle,
            DetalleSubsidioAsignadoProgramado.dprAdministradorSubsidio AS administradorSubsidio,
            DetalleSubsidioAsignadoProgramado.dprAfiliadoPrincipal AS afiliadoPrincipal,
            CuentaAdministradorSubsidioProgramada.capCondicionPersonaAdmin AS condicionAdministrador,
            DetalleSubsidioAsignadoProgramado.dprCondicionPersonaBeneficiario AS condicionBeneficiario,
            DetalleSubsidioAsignadoProgramado.dprValorSubsidioMonetario AS valorSubsidioMonetario,
            DetalleSubsidioAsignadoProgramado.dprValorOriginalAbonado AS valorOriginalAbonado,
            DetalleSubsidioAsignadoProgramado.dprValorDescuento AS valorDescuento,
            CuentaAdministradorSubsidioProgramada.capMedioDePago AS idMedioDePago,
            COALESCE(SUM(DescuentosSubsidioAsignado.desMontoDescontado), 0) AS totalDescuentosEntidad
        FROM DetalleSubsidioAsignadoProgramado
        INNER JOIN SolicitudLiquidacionSubsidio ON DetalleSubsidioAsignadoProgramado.dprSolicitudLiquidacionSubsidio = SolicitudLiquidacionSubsidio.slsId
        INNER JOIN Solicitud ON SolicitudLiquidacionSubsidio.slsSolicitudGlobal = Solicitud.solId
        INNER JOIN CuentaAdministradorSubsidioProgramada ON DetalleSubsidioAsignadoProgramado.dprCuentaAdministradorSubsidioProgramada = CuentaAdministradorSubsidioProgramada.capId
        LEFT JOIN DescuentosSubsidioAsignado ON DetalleSubsidioAsignadoProgramado.dprId = DescuentosSubsidioAsignado.desDetalleSubsidioAsignado
		WHERE CuentaAdministradorSubsidioProgramada.capMedioDePagoTransaccion IN (@sMedio)
        AND CuentaAdministradorSubsidioProgramada.capEstadoLiquidacionSubsidio = 'GENERADO'
        AND DetalleSubsidioAsignadoProgramado.dprEstado <> 'PROGRAMADO_APLICADO'
        AND Solicitud.solNumeroRadicacion = @sNumeroRadicado
        GROUP BY 
            DetalleSubsidioAsignadoProgramado.dprBeneficiarioDetalle,
            DetalleSubsidioAsignadoProgramado.dprAdministradorSubsidio,
            DetalleSubsidioAsignadoProgramado.dprAfiliadoPrincipal,
            CuentaAdministradorSubsidioProgramada.capCondicionPersonaAdmin,
            DetalleSubsidioAsignadoProgramado.dprCondicionPersonaBeneficiario,
            DetalleSubsidioAsignadoProgramado.dprValorSubsidioMonetario,
            DetalleSubsidioAsignadoProgramado.dprValorOriginalAbonado,
            DetalleSubsidioAsignadoProgramado.dprValorDescuento,
            CuentaAdministradorSubsidioProgramada.capMedioDePago
    ) AS TotalesFallecimiento
    GROUP BY 
        TotalesFallecimiento.beneficiarioDetalle,
        TotalesFallecimiento.administradorSubsidio,
        TotalesFallecimiento.afiliadoPrincipal,
        TotalesFallecimiento.condicionAdministrador,
        TotalesFallecimiento.condicionBeneficiario,
        TotalesFallecimiento.idMedioDePago,
        TotalesFallecimiento.totalDescuentosEntidad
) AS TotalesAcumulados
INNER JOIN AdministradorSubsidio ON TotalesAcumulados.administradorSubsidio = AdministradorSubsidio.asuId
INNER JOIN Persona AS PersonaAdministrador ON AdministradorSubsidio.asuPersona = PersonaAdministrador.perId
INNER JOIN BeneficiarioDetalle ON TotalesAcumulados.beneficiarioDetalle = BeneficiarioDetalle.bedId
INNER JOIN PersonaDetalle ON BeneficiarioDetalle.bedPersonaDetalle = PersonaDetalle.pedId
INNER JOIN Persona AS PersonaBeneficiario ON PersonaDetalle.pedPersona = PersonaBeneficiario.perId
INNER JOIN Beneficiario ON (PersonaBeneficiario.perId = Beneficiario.benPersona AND TotalesAcumulados.afiliadoPrincipal = Beneficiario.benAfiliado)
LEFT JOIN (
    SELECT 
        CuentaAdministradorSubsidio.casCondicionPersonaAdmin AS condicionAdministrador,
        SUM(DetalleSubsidioAsignado.dsaValorOriginalAbonado) AS totalDispersado
    FROM DetalleSubsidioAsignado
    INNER JOIN SolicitudLiquidacionSubsidio ON DetalleSubsidioAsignado.dsaSolicitudLiquidacionSubsidio = SolicitudLiquidacionSubsidio.slsId
    INNER JOIN Solicitud ON SolicitudLiquidacionSubsidio.slsSolicitudGlobal = Solicitud.solId
    INNER JOIN CuentaAdministradorSubsidio ON DetalleSubsidioAsignado.dsaCuentaAdministradorSubsidio = CuentaAdministradorSubsidio.casId
    LEFT JOIN DescuentosSubsidioAsignado ON DetalleSubsidioAsignado.dsaId = DescuentosSubsidioAsignado.desDetalleSubsidioAsignado
    WHERE CuentaAdministradorSubsidio.casMedioDePagoTransaccion IN (@sMedio)
    AND CuentaAdministradorSubsidio.casEstadoLiquidacionSubsidio = 'GENERADO'
    AND DetalleSubsidioAsignado.dsaEstado <> 'PROGRAMADO_APLICADO'
    AND Solicitud.solNumeroRadicacion = @sNumeroRadicado
    GROUP BY CuentaAdministradorSubsidio.casCondicionPersonaAdmin
) AS TotalesDispersados ON TotalesAcumulados.condicionAdministrador = TotalesDispersados.condicionAdministrador
LEFT JOIN (
    SELECT 
        CuentaAdministradorSubsidioProgramada.capCondicionPersonaAdmin AS condicionAdministrador,
        SUM(DetalleSubsidioAsignadoProgramado.dprValorOriginalAbonado) AS totalProgramado
    FROM DetalleSubsidioAsignadoProgramado
    INNER JOIN SolicitudLiquidacionSubsidio ON DetalleSubsidioAsignadoProgramado.dprSolicitudLiquidacionSubsidio = SolicitudLiquidacionSubsidio.slsId
    INNER JOIN Solicitud ON SolicitudLiquidacionSubsidio.slsSolicitudGlobal = Solicitud.solId
    INNER JOIN CuentaAdministradorSubsidioProgramada ON DetalleSubsidioAsignadoProgramado.dprCuentaAdministradorSubsidioProgramada = CuentaAdministradorSubsidioProgramada.capId
    WHERE CuentaAdministradorSubsidioProgramada.capMedioDePagoTransaccion IN (@sMedio)
    AND CuentaAdministradorSubsidioProgramada.capEstadoLiquidacionSubsidio = 'GENERADO'
    AND DetalleSubsidioAsignadoProgramado.dprEstado <> 'PROGRAMADO_APLICADO'
    AND Solicitud.solNumeroRadicacion = @sNumeroRadicado
    GROUP BY CuentaAdministradorSubsidioProgramada.capCondicionPersonaAdmin
) AS TotalesProgramados ON TotalesAcumulados.condicionAdministrador = TotalesProgramados.condicionAdministrador
OPTION(RECOMPILE)
;