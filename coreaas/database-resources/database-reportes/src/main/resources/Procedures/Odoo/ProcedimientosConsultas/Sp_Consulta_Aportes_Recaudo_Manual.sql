/* Object:  StoredProcedure [odoo].[consultasTerceros]    Script Date: 26/06/2021 11:06:38 a. m. */

--6. Consulta de Aportes recaudo manual
--CRE_AP_MN_AC: Registros de aportes acreedores por consignación
--CRE_AP_MN_PC:Registros de aportes pagos por convenio"			
---#############################################
--Tipo Aportante
--Fecha de recaudo del aporte Manual
--Fecha de transferencia bancaria / fecha consignación
--id del aporte 
--No. de identificación del aportante
--Tipo de identificación del aportante
--Valor total del aporte
--Valor de aporte sin intereses
--Valor de interés del aporte.
--Estado de aporte
--Fecha de estado de aporte
--Número de Convenio
--Número de cuota de Convenio
--Código de transacción o Modalidad de pago
--Fecha contable
--Campo tipo plantilla

CREATE PROCEDURE [odoo].[ConsultaAportesRecaudoManual] (
	@fecha_procesamiento DATE = NULL
)

AS
BEGIN
	IF @fecha_procesamiento IS NULL
	BEGIN
		SET @fecha_procesamiento = CONVERT(DATE, DATEADD(DAY, -1, DATEADD(HH, -5, GETDATE())))
	END
	
	INSERT INTO [odoo].[aportes_recaudo_manual] (tipo_aportante, fecha_recaudo_aporte_manual, fecha_transferencia_bancaria, id_aporte, num_identificacion_aportante, tipo_identificacion_aportante,
		valor_total_aporte, valor_aporte_intereses, valor_interes_aporte, estado_aporte, fecha_estado_aporte, numero_convenio, numero_cuota_convenio, codigo_transaccion, fecha_contable, campo_tipo_plantilla)
	
	SELECT * FROM ( 
		SELECT DISTINCT
			isnull(substring(apg.apgTipoSolicitante,1,1),'') as [Tipo Aportante],  
			ISNULL(apg.apgFechaRecaudo,'') as [Fecha de recaudo del aporte Manual],
			apdFechaMovimiento [Fecha de transferencia bancaria / fecha consignación], --preguntar, no se tiene en los actuales
			cast(apg.apgId as varchar (max)) as [id del aporte], --[No. Operación recaudo]
			cast(isnull(p.perNumeroIdentificacion,'')as varchar(max)) as [No. de identificación del Aportante]
			,isnull(p.perTipoIdentificacion,'') as [Tipo de identificación del Aportante]
			,apg.apgValTotalApoObligatorio+apg.apgValorIntMora as [Valor total del aporte]
			,apg.apgValTotalApoObligatorio as [Valor de aporte sin intereses]
			,apg.apgValorIntMora as [Valor de interés del aporte]
			,apg.apgEstadoAporteAportante as [Estado de aporte],
			apdFechaMovimiento as [Fecha de estado de aporte],-----
			null [Número de Convenio],--copid
			null [Número de cuota de Convenio],--copCuotasPorPagar 
			/*(select top 1 case when mdpTipo='CHEQUE' THEN 'CH'
				when mdpTipo IN('CONSIGNACION','TARJETA') THEN 'CG' ELSE 'NC' END from mediopagopersona mpp 
				inner join medioDepago mz on mpp.mppmediopago=mz.mdpId
				where mpp.mpppersona=p.perid)*/
			apg.apgModalidadRecaudoAporte as [Código de transacción o Modalidad de pago],
			apg.apgFechaRecaudo [Fecha contable],
			'CRE_AP_MN_AC' [Campo tipo plantilla]--Como se va a calcular?

		FROM AporteGeneral2 as apg (nolock) -- 4301 registros
			left join aportedetallado apd on apd.apdaportegeneral=apg.apgid
			left join Persona as p (nolock) on apg.apgPersona = p.perId
			--left join conveniopago con on con.copPersona=p.perid-- select * from conveniopagodependiente--ConvenioTerceroPagador
			left join Afiliado as af (nolock) on af.afiPersona = p.perId
			left join RolAfiliado as rolaf (nolock) on rolaf.roaAfiliado = af.afiId

			where apg.apgTipoSolicitante <> 'EMPLEADOR' 
			and apg.apgModalidadRecaudoAporte= 'MANUAL' 
			AND apdFechaMovimiento IS NOT NULL 
			and cast(apg.apgfechaprocesamiento as date)=@fecha_procesamiento--'2021-02-10'

		UNION ALL

		SELECT DISTINCT
			isnull(substring(apg.apgTipoSolicitante,1,1),'') as [Tipo Aportante],  
			ISNULL(apg.apgFechaRecaudo,'') as [Fecha de recaudo del aporte Manual],
			apdFechaMovimiento [Fecha de transferencia bancaria / fecha consignación], --preguntar, no se tiene en los actuales
			cast(apg.apgId as varchar (max)) as [id del aporte], --[No. Operación recaudo]
			cast(isnull(p.perNumeroIdentificacion,'')as varchar(max)) as [No. de identificación del Aportante]
			,isnull(p.perTipoIdentificacion,'') as [Tipo de identificación del Aportante]
			,apg.apgValTotalApoObligatorio+apg.apgValorIntMora as [Valor total del aporte]
			,apg.apgValTotalApoObligatorio as [Valor de aporte sin intereses]
			,apg.apgValorIntMora as [Valor de interés del aporte]
			,apg.apgEstadoAporteAportante as [Estado de aporte],
			apdFechaMovimiento as [Fecha de estado de aporte],
			null[Número de Convenio],--copid
			null [Número de cuota de Convenio],--copCuotasPorPagar 
			/*(select top 1 case when mdpTipo='CHEQUE' THEN 'CH'
			when mdpTipo IN('CONSIGNACION','TARJETA') THEN 'CG' ELSE 'NC' END from mediopagopersona mpp 
				inner join medioDepago mz on mpp.mppmediopago=mz.mdpId
				where mpp.mpppersona=p.perid)*/
			apg.apgModalidadRecaudoAporte as [Código de transacción o Modalidad de pago],--------Se debe verificar con LINA
			apg.apgFechaRecaudo[Fecha contable],
			'CRE_AP_MN_AC' [Campo tipo plantilla]

			FROM AporteGeneral2 as apg (nolock)
			left join aportedetallado apd on apd.apdaportegeneral=apg.apgid
			left join Empresa as emp  (nolock) on apg.apgEmpresa = emp.empId
			left join Empleador as empl (nolock) on empl.empEmpresa = emp.empId
			left join Persona as p (nolock) on emp.empPersona = p.perId
			--left join conveniopago con on con.copPersona=p.perid-- select * from conveniopagodependiente--ConvenioTerceroPagador--left join conveniopagodependiente con on con.cpdPersona=p.perid-- select * from conveniopagodependiente--ConvenioTerceroPagador
			left join CodigoCIIU as c (nolock) on emp.empCodigoCIIU = c.ciiId
			where apg.apgTipoSolicitante = 'EMPLEADOR' 
			and apg.apgModalidadRecaudoAporte= 'MANUAL' 
			AND apdFechaMovimiento IS NOT NULL 
			and cast(apg.apgfechaprocesamiento as date)=@fecha_procesamiento--'2021-02-10'
		) as x
				where cast(x.[Fecha Contable] as DATE) =@fecha_procesamiento
			--  select * from registrogeneral
END
