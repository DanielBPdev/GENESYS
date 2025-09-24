-- =======================================================
-- Create Stored Procedure Template for Azure SQL Database
-- =======================================================

-- =============================================
-- Author:      Gustavo Giraldo
-- Create Date: 2021-06-27
-- Description: Procedimiento almacenado que se encarga 
-- de consultar la liquidación y pago específico subsidio monetario
-- =============================================

CREATE PROCEDURE [odoo].[ConsultaLiquidacionPagoEspecificoSubsidioMonetario] (
	@fecha_procesamiento DATE = NULL
)

AS
BEGIN
	IF @fecha_procesamiento IS NULL
	BEGIN
		SET @fecha_procesamiento = CONVERT(DATE, DATEADD(DAY, -1, DATEADD(HH, -5, GETDATE())))
	END
	--11.Consulta liquidación y pago específico Subsidio monetario
	--➔ CRE_LS_PG_TP : Liquidación de subsidio pagos realizados por terceros
	--pagadores
	--➔ CRE_LS_PG_TR : Liquidación de subsidio pagos realizados por tarjeta
	--➔ CRE_LS_PG_TB : Liquidación de subsidio pagos realizados por
	--transferencia bancaria
	--➔ CRE_LS_PG_DJ : Liquidación de subsidio pagos realizados por depósitos
	--judiciales.

	--➔ CRE_LS_PG_NO_DJ: Liquidación de subsidio pagos realizados por
	--depósitos NO judiciales.
	--➔ CRE_LS_PG_CR_YA: Liquidación de subsidio pagos realizados por
	--CREDIYA, archivo de pignoración
	----###############
	--[Fecha de procesamiento de liquidación]
	--[Fecha de liquidación oficial]
	--[Fecha dispersión de la liquidación]
	--[id del pago] 
	--[Periodo regular / retroactivo de liquidación]
	--[Periodo asociado a Fecha de procesamiento de liquidación]
	--[Tipo de Identificación del Administrador de subsidio]
	--[No. de Identificación del Administrador de subsidio]
	--[Valor de cuota monetaria liquidada]
	--[Código o identificación del tercero pagador]
	--[Modalidad de Pago del Administrador de subsidio.]
	--[Valor total de cuotas monetarias liquidadas por Modalidad de Pago.]---No se pueden agrupar, esto debe hacer parte de otro reporte...
	--[Fecha contable]
	--[Campo tipo plantilla]

	INSERT INTO [odoo].[liquidacion_pago_especifico_subsidio_monetario] (fecha_procesamiento_liquidacion, fecha_liquidacion_oficial, fecha_dispersion_liquidacion, id_pago,
		periodo_regular_retroactivo_liquidacion, periodo_asociado_a_fecha_procesamiento_liquidacion, tipo_identificacion_administrador_subsidio, identificacion_administrador_subsidio,
		codigo_identificacion_tercero_pagador, modalidad_pago_administrador_subsidio, fecha_contable, campo_tipo_plantilla, codigo_entidad_descuento, nombre_entidad_descuento,
		tipo_liquidacion, valor_asignado, valor_con_descuento, valor_real_para_pago, tipo_identificacion_afiliado_principal, identificacion_afiliado_principal, cobro_judicial, grupo_inembargable)

	--11.Consulta liquidación y pago específico Subsidio monetario
	SELECT * FROM ( 
		SELECT 
			cast (slsFechaDispersion as date) [Fecha de procesamiento de liquidación], --casFechaHoraCreacionRegistro---slsFechaCorteAporte---slsFechaEjecucionProgramada--slsFechaEvaluacionPrimerNivel---slsFechaEvaluacionSegundoNivel-- slsFechaDispersion
			cast (slsFechaDispersion as date) [Fecha de liquidación oficial], --casFechaHoraCreacionRegistro---slsFechaCorteAporte---slsFechaEjecucionProgramada--slsFechaEvaluacionPrimerNivel---slsFechaEvaluacionSegundoNivel-- slsFechaDispersion
			slsFechaDispersion [Fecha dispersión de la liquidación],
			casid as [id del pago],
			case when format (slsFechaDispersion, 'yyyy-MM')= format (casfechahoracreacionregistro, 'yyyy-MM') then 'Regular' else 'Retroactivo' end [Periodo regular / retroactivo de liquidación],---Que contra que? un ejemplo
			format (slsFechaDispersion, 'yyyy-MM') as [Periodo asociado a Fecha de procesamiento de liquidación],
			replace(padm.pertipoidentificacion,'_',' ') [Tipo de Identificación del Administrador de subsidio],
			padm.pernumeroidentificacion [No. de Identificación del Administrador de subsidio],
			null [Código o identificación del tercero pagador],---En la reunión la clara aclara que este campo debe identificar si el subsidio viene por Acertemos, o Jueguemos, sin embargo en GENESYS aún hay un control de cambio en curso para diferenciar estos dos medios de pago, Deisy llevara la duda a ASOPAGOS
			casMedioDePagoTransaccion [Modalidad de Pago del Administrador de subsidio.],
			--null as[Valor total de cuotas monetarias liquidadas por Modalidad de Pago.],--Se acordó que este campo no iría
			slsFechaDispersion as [Fecha contable],
			case WHEN casMedioDePagoTransaccion ='TARJETA' THEN 'CRE_LS_PG_TR'
				WHEN casMedioDePagoTransaccion='EFECTIVO' THEN  'CRE_LS_PG_TP'
				WHEN casMedioDePagoTransaccion='TRANSFERENCIA'  and g.grfinembargable='1' THEN 'CRE_LS_PG_DJ'
				WHEN casMedioDePagoTransaccion='TRANSFERENCIA'  and g.grfinembargable='0' THEN 'CRE_LS_PG_TB'
				WHEN casMedioDePagoTransaccion='CONSIGNACION' THEN 'CRE_LS_PG_TB'
				WHEN casMedioDePagoTransaccion='CHEQUE' THEN 'CRE_LS_PG_TB'
			--when aedsp.ardId is not null then 'CRE_LS_PG_EN_DE' --Comentariado Hasta que exista el campo para el join
			end AS [Campo tipo plantilla],
			null [Codigo Entidad de descuento],
			null [Nombre Entidad de descuento],
			--ed.endCodigo as [Codigo Entidad de descuento],---confirmado
			--ed.endNombre as [Nombre Entidad de descuento],--- confirmado
			sls.SLStipoliquidacion[Tipo de Liquidación],
			dsaValorSubsidioMonetario [valor asignado],
			dsaValorDescuento [valor con descuento],
			dsaValorTotal[valor real para pago],
			replace(pafp.pertipoidentificacion,'_',' ') [Tipo identificacion Afiliado principal (trabajador)],
			pafp.pernumeroidentificacion [Numero identificacion Afiliado principal (trabajador)],
			NULL [Cobro Judicial],--(select top 1 metcobrojudicial from [dbo].[MedioTransferencia] met WHERE met.METNUMEROCUENTA=casx.casNumeroCuentaAdmonSubsidio)
			case when g.grfinembargable=1 then 'SI' ELSE 'NO' END AS [Grupo Inembargable]

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
			where S.solresuLtadoproceso='DISPERSADA'--CANCELADA
			--AND --(
			--sls.SLStipoliquidacion not in ('MASIVA')-- SUBSUDIO_DE_DEFUNCION--RECONOCIMIENTO_DE_SUBSIDIOS--ESPECIFICO
			--or sls.slstipoliquidacionespecifica is not null)---- En la condición de Masiva ya se encuentra la condición
			---AND CASX.CASORIGENTRANSACCION='LIQUIDACION_SUBSIDIO_MONETARIO'-----Para excluir retiros y retiros parciales
			and cast(slsFechaDispersion as DATE) =@fecha_procesamiento		
	) as x
		where cast(x.[Fecha Contable] as DATE) =@fecha_procesamiento		
				---
				--select distinct CASORIGENTRANSACCION from CuentaAdministradorSubsidio and 

				--sE DEBE VERIFICAR LA LÓGICA DE LAS NOVEDADES POR QUE NO ES POSIBLE HACER LOS JOIN 

				--select * from CuentaAdministradorSubsidio where CASmediodepagotransaccion='TRANSFERENCIA'
				--AND CASNUMEROCUENTAADMONSUBSIDIO='00117184111'
				
		--		Select * from CuentaAdministradorSubsidio 
		--		select metcobrojudicial from [dbo].[MedioTransferencia] met WHERE met.METNUMEROCUENTA=casx.casNumeroCuentaAdmonSubsidio--'00117184111'
		--		where CASmediodepagotransaccion='TRANSFERENCIA'
		--		--AND CASNUMEROCUENTAADMONSUBSIDIO='00117184111'
		--		from 
				
		--		--SELECT * FROM [dbo].[MedioConsignacion]
		--		--select distinct slstipoliquidacionespecifica from solicitudliquidacionsubsidio
		----select *
		----from SolicitudNovedad sno
		----INNER JOIN Solicitud s WITH(NOLOCK) ON sno.snoSolicitudGlobal = s.solid

		----    --left JOIN ArchivoEntidadDescuentoSubsidioPignorado as aedsp with (nolock) on aedsp.ardNumeroRadicacion = s.solNumeroRadicacion
		----		--left join entidaddescuento ed on ed.endId=aedsp.ardEntidadDescuento
		----		inner join CuentaAdministradorSubsidio casx on Sls.Slsid=casx.casSolicitudLiquidacionSubsidio
		----		inner join grupofamiliar g on g.grfid=casx.casGrupoFamiliar
		----		left join afiliado ax on ax.afiId=casx.casAfiliadoPrincipal
		----		left join persona pafp on pafp.perid=ax.afiPersona
		----		inner join administradorsubsidio adm on casx.casadministradorsubsidio= adm.asuId
		----		inner join persona padm on padm.perid=adm.asuPersona

		----where s.soltipotransaccion like'%FALLECI%'---COMO SE DISTINGUEN LAS NOVEDADES? O YA ESTÁN EN SLS?



		----2021/07/06 RTA FUNCIONAL: En Genesis se manejan tipos de liquidaciones (masivas y específicas) -Periodo regular es dentro del mismo periodo de liquidacion. Los retroactivos son diferentes al periodo regular (incluye novedades- )

		----Especifico por reconocimiento, por ajuste y por fallecimiento (Trabajador dependientes y Beneficiario), complementario- 1429, cuota agraria, Ajuste valor cuota -final de enero) y Por discapacidad, PQR (reconocimiento). Adicionales a otra liquidación existente

		----2021/07/06 RTA CAJA: Según las aclaraciones funcionales, se deben incluir todas lasliquidaciones y las novedades que se mencionaron a nivel funcional. Liquidaciones de Certificados escolares, Fallecimientos (retroactivas).

		----Genesys maneja liquidacion inmediata para las específicas (adicionales a las masivas hacen parte de las específicas). Según la caja esto se puede subsanar con la inclusión del último campo (tipo de liquidación)


		--		----select distinct castipotransaccionsubsidio from CuentaAdministradorSubsidio where casSolicitudLiquidacionSubsidio in(186)
		--		--select *--distinct slstipoliquidacion,slstipoliquidacionespecifica 
		--		--from SolicitudLiquidacionSubsidio

		--		--sELECT * FROM ArchivoEntidadDescuentoSubsidioPignorado
END
