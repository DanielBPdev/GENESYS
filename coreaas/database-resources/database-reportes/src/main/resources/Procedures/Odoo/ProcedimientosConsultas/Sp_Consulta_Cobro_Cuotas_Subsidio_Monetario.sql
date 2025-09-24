-- =======================================================
-- Create Stored Procedure Template for Azure SQL Database
-- =======================================================

-- =============================================
-- Author:      Gustavo Giraldo
-- Create Date: 2021-07-28
-- Description: Procedimiento almacenado que se encarga de consultar los registros de liquidación originados por la
-- ejecución del proceso de Cobro de Cuotas Monetarias de Subsidio Familiar, con
-- clasificación de cobros de acuerdo al medio de Pago de Administradores de Subsidio.
-- =============================================

CREATE PROCEDURE [odoo].[ConsultaCobroCuotasSubsidioMonetario] (
	@fecha_procesamiento DATE = NULL
)

AS
BEGIN
	IF @fecha_procesamiento IS NULL
	BEGIN
		SET @fecha_procesamiento = CONVERT(DATE, DATEADD(DAY, -1, DATEADD(HH, -5, GETDATE())))
	END

	INSERT INTO [odoo].[cobro_cuotas_subsidio_monetario] (fecha_liquidacion_oficial, fecha_cobro, fecha_dispersion_liquidacion, periodo_asociado_fecha_cobro, identificacion_abono,
		codigo_identificacion_tercero_pagador, modalidad_pago_administrador_subsidio, numero_solicitud_cobro, tipo_identificacion_administrador_subsidio,
		numero_identificacion_administrador_subsidio, valor_cobrado, suma_valores_cobrados_ct_monetaria_discriminada_por_dia, fecha_contable, campo_tipo_plantilla)
	--12 Consulta cobro de cuotas Subsidio monetario
	SELECT * FROM ( 
		SELECT
			cast (slsFechaDispersion as date) [Fecha de liquidación oficial], --casFechaHoraCreacionRegistro---slsFechaCorteAporte---slsFechaEjecucionProgramada--slsFechaEvaluacionPrimerNivel---slsFechaEvaluacionSegundoNivel-- slsFechaDispersion
			casFechaHoraTransaccion [Fecha de cobro],
			slsFechaDispersion [Fecha dispersión de la liquidación],
			dsa.dsaperiodoliquidado [Periodo asociado a Fecha de Cobro],
			casid as [identificación del abono],
			null [Código o identificación del tercero pagador],---RTA CAJA 2021/06/30: En la reunión la clara aclara que este campo debe identificar si el subsidio viene por Acertemos, o Jueguemos, sin embargo en GENESYS aún hay un control de cambio en curso para diferenciar estos dos medios de pago, Deisy llevara la duda a ASOPAGOS
			casmediodepagotransaccion [Modalidad de Pago del Administrador de Subsidio.],
			--
			s.solnumeroradicacion[Número de solicitud de Cobro],
			---
			replace(padm.pertipoidentificacion,'_',' ') [Tipo de Identificación del Administrador de subsidio],
			padm.pernumeroidentificacion [No. de Identificación del Administrador de subsidio],
			casValorOriginalTransaccion [Valor cobrado],-- Verificar cuales de los que siguen serían los valores
			casValorRealTransaccion [Suma todos los valores cobrados CT monetaria discriminada por día],
			slsFechaDispersion as [Fecha contable],
			case WHEN casMedioDePagoTransaccion ='TARJETA' THEN 'CRE_LS_CP_TR'
				WHEN casMedioDePagoTransaccion='EFECTIVO' THEN  'CRE_LS_CP_TP'
				WHEN casMedioDePagoTransaccion='TRANSFERENCIA'  and g.grfinembargable='1' THEN 'CRE_LS_CP_DJ'
				WHEN casMedioDePagoTransaccion='TRANSFERENCIA'  and g.grfinembargable='0' THEN 'CRE_LS_CP_TB'
				WHEN casMedioDePagoTransaccion='CONSIGNACION' THEN 'CRE_LS_CP_TB'
				WHEN casMedioDePagoTransaccion='CHEQUE' THEN 'CRE_LS_CP_TB'
				--when aedsp.ardId is not null then 'CRE_LS_CP_CR_YA' --Comentariado Hasta que exista el campo para el join
			end AS [Campo tipo plantilla]--,
			--,ed.endNombre as [Nombre Entidad de descuento]--- confirmado
			--,ed.endCodigo as [Codigo Entidad de descuento]---confirmado
			--replace(pafp.pertipoidentificacion,'_',' ') [Tipo identificacion Afiliado principal (trabajador)],
			--pafp.pernumeroidentificacion [Numero identificacion Afiliado principal (trabajador)],
			--NULL [Cobro Judicial],---metCobroJudicial (MEDIOTRANSFERENCIA)
			--case when g.grfinembargable=1 then 'SI' ELSE 'NO' END AS [Grupo Inembargable]

			--select * 
			FROM SolicitudLiquidacionSubsidio sls WITH(NOLOCK)
				INNER JOIN Solicitud s WITH(NOLOCK) ON sls.slsSolicitudGlobal = s.solid
				inner join detallesubsidioasignado dsa on dsa.dsasolicitudliquidacionsubsidio=sls.slsId
				--left JOIN ArchivoEntidadDescuentoSubsidioPignorado as aedsp with (nolock) on aedsp.ardNumeroRadicacion = slS.solNumeroRadicacion
				--left join entidaddescuento ed on ed.endId=aedsp.ardEntidadDescuento
				inner join CuentaAdministradorSubsidio casx on casx.casId = dsa.dsaCuentaAdministradorSubsidio
				inner join grupofamiliar g on g.grfid=casx.casGrupoFamiliar
				left join afiliado ax on ax.afiId=casx.casAfiliadoPrincipal
				left join persona pafp on pafp.perid=ax.afiPersona
				inner join administradorsubsidio adm on casx.casadministradorsubsidio= adm.asuId
				inner join persona padm on padm.perid=adm.asuPersona
				where 
				--AND CASX.CASORIGENTRANSACCION='LIQUIDACION_SUBSIDIO_MONETARIO'
				S.solresuLtadoproceso='DISPERSADA'

				and cast(slsFechaDispersion as DATE) =@fecha_procesamiento	
		) as x
		where cast(x.[Fecha Contable] as DATE) =@fecha_procesamiento	

		--select * from detallesubsidioasignado
END
