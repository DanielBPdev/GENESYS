--5. Consulta de Aportes, recaudos PILA
--CRE_AP_P6 : Registros de aportes en estado registrado por proceso -PILA.
--CRE_AP_ACREE: Registros de aportes en estado relacionado por proceso -PILA.
--CRE_AP_PPC: Registros de aportes en estado pendientes por conciliar.
--ACT_AP_PPC_A_P6: Modificacion de registros de aportes en estado pendientes por conciliar a REGISTRADO por proceso PILA.
--ACT_AP_PPC_A_ACREE: Modificación de registros de aportes en estado pendientes por conciliar a acreedor estado (RELACIONADO) por proceso PILA.

---#############################################
--Tipo Aportante
--Fecha de recaudo PILA.
----Fecha de transferencia bancaria / fecha consignación. Nuevo
--id del aporte
----Quitar tipo de aporte
----cambiar los dos siguientes
--No. de identificación del aportante
--Tipo de identificación del aportante
--Valor total del aporte
--Valor de aporte sin intereses
--Valor de interés del aporte
--Número de planilla
--Estado de aporte
----Estado de vigencia del aporte ?¡
--Estado de conciliación --apg concilado que necesitan 
--Fecha de estado de aporte
--Fecha de conciliación-- Asociada a los bloques de pila
--Fecha P. Conciliar.---Asociada a los bloques de pila
--Código de transacción o Modalidad de pago 
--Fecha contable
--Campo tipo plantilla
--"RTA CAJA 17/06/2021: CRE_AP_P6 : Registros de aportes en estado registrado por proceso -PILA. - FECHA PROCESAMIENTO DE APORTE GENERAL
--➔ CRE_AP_ACREE: Registros de aportes en estado relacionado por proceso -PILA. - FECHA PROCESAMIENTO DE APORTE GENERAL
--➔ CRE_AP_PPC: Registros de aportes en estado pendientes por conciliar. - FECHA EN QUE LLEGA A  BLOQUE 6 
--➔ ACT_AP_PPC_A_P6: Modificacion de registros de aportes en estado pendientes por conciliar a REGISTRADO por proceso PILA. - FECHA EN QUE LLEGA A  BLOQUE 6
--➔ ACT_AP_PPC_A_ACREE: Modificación de registros de aportes en estado pendientes por conciliar a acreedor estado (RELACIONADO) por proceso PILA. - FECHA  EN QUE LLEGA A BLOQUE 6 

--Nota: Estas fechas se definieron con el apoyo del funcional Asopagos en la Sesión."

CREATE PROCEDURE [odoo].[ConsultaAportesRecaudosPila] (
	@fecha_procesamiento DATE = NULL
)

AS
BEGIN
	IF @fecha_procesamiento IS NULL
	BEGIN
		SET @fecha_procesamiento = CONVERT(DATE, DATEADD(DAY, -1, DATEADD(HH, -5, GETDATE())))
	END
	
	INSERT INTO [odoo].[aportes_recaudos_pila] (tipo_aportante, fecha_recaudo_PILA, fecha_transferencia_bancaria_consignacion, id_aporte, tipo_identificacion_aporte,
		num_identificacion_aportante, tipo_identificacion_aportante, valor_total_aporte, valor_aporte_sin_intereses, valor_interes_aporte,
		numero_planilla, estado_aporte, estado_vigencia_aporte, estado_conciliacion, fecha_estado_aporte,
		fecha_conciliacion, fecha_p_conciliar, codigo_transaccion_modalidad_pago, fecha_contable, campo_tipo_plantilla)

	SELECT DISTINCT * 
	FROM ( 
		SELECT DISTINCT
			isnull(substring(apg.apgTipoSolicitante,1,1),'') as [Tipo Aportante]  
			,ISNULL(apg.apgFechaRecaudo,'') as [Fecha de recaudo PILA]
			,null [Fecha de transferencia bancaria / fecha consignación] --RTA CAJA 21/06/2021: No se tiene,así que no será enviado para los recaudos de PILA
			,cast(apg.apgId as varchar (max)) as [id del aporte] --[No. Operación recaudo]
			,apg.apgModalidadRecaudoAporte as [tipo identificación del aporte]
			,cast(isnull(p.perNumeroIdentificacion,'')as varchar(max)) as [No. de identificación del Aportante]
			,isnull(p.perTipoIdentificacion,'') as [Tipo de identificación del Aportante]
			,apg.apgValTotalApoObligatorio+apg.apgValorIntMora as [Valor total del aporte]
			,apg.apgValTotalApoObligatorio as [Valor de aporte sin intereses]
			,apg.apgValorIntMora as [Valor de interés del aporte]
			,(select rg.regNumPlanilla from AporteGeneral2 as apgx inner join RegistroGeneral2 as rg on apgx.apgRegistroGeneral = rg.regId where apgx.apgId = apg.apgId) as [Nº Planilla]
			,apg.apgestadoregistroaporteaportante as [Estado del aporte],

			apg.apgEstadoAporteAportante[Estado de vigencia del aporte],

			(select TOP 1 pebEstadoBloque6 
				from AporteGeneral2 as apgx 
				inner join RegistroGeneral2 as rg on apgx.apgRegistroGeneral = rg.regId 
				inner join pilaindiceplanilla pi on pi.pipidplanilla=rg.regNumPlanilla
				inner join pilaestadobloque bq on pi.pipId=pebIndicePlanilla
				where apgx.apgId = apg.apgId
			) [Estado de conciliación],

			apdFechaMovimiento [Fecha de estado de aporte]
			,(select cast(pebfechabloque6 as date)
				from AporteGeneral2 as apgx 
				inner join RegistroGeneral2 as rg on apgx.apgRegistroGeneral = rg.regId 
				inner join pilaindiceplanilla pi on pi.pipidplanilla=rg.regNumPlanilla
				inner join pilaestadobloque bq on pi.pipId=pebIndicePlanilla
				where apgx.apgId = apg.apgId
				and pebestadobloque6='REGISTRO_F_CONCILIADO'
			) [Fecha de conciliación]

			,(select cast(pebfechabloque6 as date)
				from AporteGeneral2 as apgx 
				inner join RegistroGeneral2 as rg on apgx.apgRegistroGeneral = rg.regId 
				inner join pilaindiceplanilla pi on pi.pipidplanilla=rg.regNumPlanilla
				inner join pilaestadobloque bq on pi.pipId=pebIndicePlanilla
				where apgx.apgId = apg.apgId
				and pebestadobloque6='PENDIENTE_CONCILIACION'
			) [Fecha P. Conciliar.],

			apgModalidadRecaudoAporte [Código de transacción o Modalidad de pago],

			CASE WHEN apg.apgEstadoRegistroAporteAportante IN ('REGISTRADO','RELACIONADO') 
				AND (select CAST( cast(pebfechabloque6 as date) AS VARCHAR(10)) 
						from AporteGeneral2 as apgx 
						inner join RegistroGeneral2 as rg on apgx.apgRegistroGeneral = rg.regId 
						inner join pilaindiceplanilla pi on pi.pipidplanilla=rg.regNumPlanilla
						inner join pilaestadobloque bq on pi.pipId=pebIndicePlanilla
						where apgx.apgId = apg.apgId
						and pebestadobloque6 ='PENDIENTE_CONCILIACION'
					) IS NULL    THEN CAST(CAST(apgFechaProcesamiento AS DATE) AS VARCHAR(10)) 
			ELSE 
				(select CAST(cast(pebfechabloque6 as date) AS VARCHAR(10)) 
					from AporteGeneral2 as apgx 
					inner join RegistroGeneral2 as rg on apgx.apgRegistroGeneral = rg.regId 
					inner join pilaindiceplanilla pi on pi.pipidplanilla=rg.regNumPlanilla
					inner join pilaestadobloque bq on pi.pipId=pebIndicePlanilla
					where apgx.apgId = apg.apgId
				and pebestadobloque6 IN ('PENDIENTE_CONCILIACION','REGISTRO_F_CONCILIADO')
				)
			END AS [Fecha contable],

			CASE 
				when apg.apgEstadoRegistroAporteAportante='REGISTRADO'  and 
					(select CAST(cast(pebfechabloque6  as date)AS VARCHAR(10)) 
						from AporteGeneral2 as apgx 
						inner join RegistroGeneral2 as rg on apgx.apgRegistroGeneral = rg.regId 
						inner join pilaindiceplanilla pi on pi.pipidplanilla=rg.regNumPlanilla
						inner join pilaestadobloque bq on pi.pipId=pebIndicePlanilla
						where apgx.apgId = apg.apgId
						and pebestadobloque6 ='PENDIENTE_CONCILIACION'
					) IS NULL  THEN 'CRE_AP_P6'
				when apg.apgEstadoRegistroAporteAportante='RELACIONADO' 
					and 
						(select CAST(cast(pebfechabloque6 as date) AS VARCHAR(10)) 
							from AporteGeneral2 as apgx 
							inner join RegistroGeneral2 as rg on apgx.apgRegistroGeneral = rg.regId 
							inner join pilaindiceplanilla pi on pi.pipidplanilla=rg.regNumPlanilla
							inner join pilaestadobloque bq on pi.pipId=pebIndicePlanilla
							where apgx.apgId = apg.apgId
							and pebestadobloque6 ='PENDIENTE_CONCILIACION'
						) IS NULL THEN 'CRE_AP_ACREE'
				WHEN 
					(select CAST(cast(pebfechabloque6 as date)AS VARCHAR(10)) 
						from AporteGeneral2 as apgx 
						inner join RegistroGeneral2 as rg on apgx.apgRegistroGeneral = rg.regId 
						inner join pilaindiceplanilla pi on pi.pipidplanilla=rg.regNumPlanilla
						inner join pilaestadobloque bq on pi.pipId=pebIndicePlanilla
						where apgx.apgId = apg.apgId
						and pebestadobloque6 ='PENDIENTE_CONCILIACION'
					) IS NOT NULL THEN 'CRE_AP_PPC'
				WHEN
					(select CAST(cast(pebfechabloque6 as date) AS VARCHAR(10)) 
						from AporteGeneral2 as apgx 
						inner join RegistroGeneral2 as rg on apgx.apgRegistroGeneral = rg.regId 
						inner join pilaindiceplanilla pi on pi.pipidplanilla=rg.regNumPlanilla
						inner join pilaestadobloque bq on pi.pipId=pebIndicePlanilla
						where apgx.apgId = apg.apgId
						and pebestadobloque6 ='PENDIENTE_CONCILIACION'
					) IS NOT NULL  AND apg.apgEstadoRegistroAporteAportante='REGISTRADO' THEN 'ACT_AP_PPC_A_P6'
				WHEN (select CAST( cast(pebfechabloque6 as date) AS VARCHAR(10)) 
						from AporteGeneral2 as apgx 
						inner join RegistroGeneral2 as rg on apgx.apgRegistroGeneral = rg.regId 
						inner join pilaindiceplanilla pi on pi.pipidplanilla=rg.regNumPlanilla
						inner join pilaestadobloque bq on pi.pipId=pebIndicePlanilla
						where apgx.apgId = apg.apgId
						and pebestadobloque6 ='PENDIENTE_CONCILIACION'
					) IS NOT NULL AND apg.apgEstadoRegistroAporteAportante='RELACIONADO' THEN 'ACT_AP_PPC_A_ACREE'
			END [Campo tipo plantilla]

			FROM AporteGeneral2 as apg (nolock) -- 4301 registros
			left join aportedetallado apd on apd.apdaportegeneral=apg.apgid
			left join Persona as p (nolock) on apg.apgPersona = p.perId
			left join Afiliado as af (nolock) on af.afiPersona = p.perId
			left join RolAfiliado as rolaf (nolock) on rolaf.roaAfiliado = af.afiId
			left join Ubicacion as u (nolock) on p.perUbicacionPrincipal = u.ubiId
			left join Municipio as m (nolock) on u.ubiMunicipio = m.munId
			left join Departamento as d (nolock) on m.munDepartamento = d.depId
			where apg.apgTipoSolicitante <> 'EMPLEADOR' 
				and apg.apgModalidadRecaudoAporte='PILA'
				and cast(apg.apgfechaprocesamiento as date) = @fecha_procesamiento--='2021-02-10' 
	
		UNION ALL

		SELECT DISTINCT
			isnull(substring(apg.apgTipoSolicitante,1,1),'') as [Tipo Aportante]  
			,ISNULL(apg.apgFechaRecaudo,'') as [Fecha de recaudo PILA]
			,null [Fecha de transferencia bancaria / fecha consignación] --preguntar, no se tiene en los actuales
			,cast(apg.apgId as varchar (max)) as [id del aporte] --[No. Operación recaudo]
			,apg.apgModalidadRecaudoAporte as [tipo identificación del aporte]
			,cast(isnull(p.perNumeroIdentificacion,'')as varchar(max)) as [No. de identificación del Aportante]
			,isnull(p.perTipoIdentificacion,'') as [Tipo de identificación del Aportante]
			,apg.apgValTotalApoObligatorio+apg.apgValorIntMora as [Valor total del aporte]
			,apg.apgValTotalApoObligatorio as [Valor de aporte sin intereses]
			,apg.apgValorIntMora as [Valor de interés del aporte]
			,(select rg.regNumPlanilla from AporteGeneral2 as apgx inner join RegistroGeneral2 as rg on apgx.apgRegistroGeneral = rg.regId where apgx.apgId = apg.apgId) as [Nº Planilla]
			,apg.apgestadoregistroaporteaportante as [Estado del aporte],
			apg.apgEstadoAporteAportante[Estado de vigencia del aporte],
			(select TOP 1 pebEstadoBloque6 
				from AporteGeneral2 as apgx 
				inner join RegistroGeneral2 as rg on apgx.apgRegistroGeneral = rg.regId 
				inner join pilaindiceplanilla pi on pi.pipidplanilla=rg.regNumPlanilla
				inner join pilaestadobloque bq on pi.pipId=pebIndicePlanilla
				where apgx.apgId = apg.apgId
			) [Estado de conciliación],
			apd.apdfechamovimiento[Fecha de estado de aporte]
			,(select cast(pebfechabloque6 as date) 
				from AporteGeneral2 as apgx 
				inner join RegistroGeneral2 as rg on apgx.apgRegistroGeneral = rg.regId 
				inner join pilaindiceplanilla pi on pi.pipidplanilla=rg.regNumPlanilla
				inner join pilaestadobloque bq on pi.pipId=pebIndicePlanilla
				where apgx.apgId = apg.apgId
				and pebestadobloque6='REGISTRO_F_CONCILIADO'
			)[Fecha de conciliación]

			,(select cast(pebfechabloque6 as date) 
				from AporteGeneral2 as apgx 
				inner join RegistroGeneral2 as rg on apgx.apgRegistroGeneral = rg.regId 
				inner join pilaindiceplanilla pi on pi.pipidplanilla=rg.regNumPlanilla
				inner join pilaestadobloque bq on pi.pipId=pebIndicePlanilla
				where apgx.apgId = apg.apgId
				and pebestadobloque6='PENDIENTE_CONCILIACION'
			)[Fecha P. Conciliar.],

			apgModalidadRecaudoAporte [Código de transacción o Modalidad de pago],

			case WHEN apg.apgEstadoRegistroAporteAportante IN ('REGISTRADO','RELACIONADO') 
				AND (select CAST( cast(pebfechabloque6 as date) AS VARCHAR(10)) 
					from AporteGeneral2 as apgx 
					inner join RegistroGeneral2 as rg on apgx.apgRegistroGeneral = rg.regId 
					inner join pilaindiceplanilla pi on pi.pipidplanilla=rg.regNumPlanilla
					inner join pilaestadobloque bq on pi.pipId=pebIndicePlanilla
					where apgx.apgId = apg.apgId
					and pebestadobloque6 ='PENDIENTE_CONCILIACION'
					) IS NULL    THEN CAST(CAST(apgFechaProcesamiento AS DATE) AS VARCHAR(10)) 
			ELSE 
				(select CAST( cast(pebfechabloque6 as date) AS VARCHAR(10)) 
				from AporteGeneral2 as apgx 
				inner join RegistroGeneral2 as rg on apgx.apgRegistroGeneral = rg.regId 
				inner join pilaindiceplanilla pi on pi.pipidplanilla=rg.regNumPlanilla
				inner join pilaestadobloque bq on pi.pipId=pebIndicePlanilla
				where apgx.apgId = apg.apgId
				and pebestadobloque6 IN ('PENDIENTE_CONCILIACION','REGISTRO_F_CONCILIADO')
				)
			END AS [Fecha contable],


			case 
				when apg.apgEstadoRegistroAporteAportante='REGISTRADO'  and 
					(select CAST( cast(pebfechabloque6 as date) AS VARCHAR(10)) 
					from AporteGeneral2 as apgx 
					inner join RegistroGeneral2 as rg on apgx.apgRegistroGeneral = rg.regId 
					inner join pilaindiceplanilla pi on pi.pipidplanilla=rg.regNumPlanilla
					inner join pilaestadobloque bq on pi.pipId=pebIndicePlanilla
					where apgx.apgId = apg.apgId
					and pebestadobloque6 ='PENDIENTE_CONCILIACION'
					) IS NULL  THEN 'CRE_AP_P6'
				when apg.apgEstadoRegistroAporteAportante='RELACIONADO' 
					and 
						(select CAST( cast(pebfechabloque6 as date) AS VARCHAR(10)) 
						from AporteGeneral2 as apgx 
						inner join RegistroGeneral2 as rg on apgx.apgRegistroGeneral = rg.regId 
						inner join pilaindiceplanilla pi on pi.pipidplanilla=rg.regNumPlanilla
						inner join pilaestadobloque bq on pi.pipId=pebIndicePlanilla
						where apgx.apgId = apg.apgId
						and pebestadobloque6 ='PENDIENTE_CONCILIACION'
						) IS NULL THEN 'CRE_AP_ACREE'
				WHEN 
					(select CAST(cast(pebfechabloque6 as date) AS VARCHAR(10)) 
					from AporteGeneral2 as apgx 
					inner join RegistroGeneral2 as rg on apgx.apgRegistroGeneral = rg.regId 
					inner join pilaindiceplanilla pi on pi.pipidplanilla=rg.regNumPlanilla
					inner join pilaestadobloque bq on pi.pipId=pebIndicePlanilla
					where apgx.apgId = apg.apgId
					and pebestadobloque6 ='PENDIENTE_CONCILIACION'
					) IS NOT NULL THEN 'CRE_AP_PPC'

				WHEN
					(select CAST( cast(pebfechabloque6 as date) AS VARCHAR(10)) 
					from AporteGeneral2 as apgx 
					inner join RegistroGeneral2 as rg on apgx.apgRegistroGeneral = rg.regId 
					inner join pilaindiceplanilla pi on pi.pipidplanilla=rg.regNumPlanilla
					inner join pilaestadobloque bq on pi.pipId=pebIndicePlanilla
					where apgx.apgId = apg.apgId
					and pebestadobloque6 ='PENDIENTE_CONCILIACION'
					) IS NOT NULL  AND apg.apgEstadoRegistroAporteAportante='REGISTRADO' THEN 'ACT_AP_PPC_A_P6'

				WHEN (select CAST( cast(pebfechabloque6 as date) AS VARCHAR(10)) 
					from AporteGeneral2 as apgx 
					inner join RegistroGeneral2 as rg on apgx.apgRegistroGeneral = rg.regId 
					inner join pilaindiceplanilla pi on pi.pipidplanilla=rg.regNumPlanilla
					inner join pilaestadobloque bq on pi.pipId=pebIndicePlanilla
					where apgx.apgId = apg.apgId
					and pebestadobloque6 ='PENDIENTE_CONCILIACION'
					) IS NOT NULL  AND apg.apgEstadoRegistroAporteAportante='RELACIONADO' THEN 'ACT_AP_PPC_A_ACREE'
			END [Campo tipo plantilla]

			FROM AporteGeneral2 as apg (nolock)
			left join aportedetallado apd on apd.apdaportegeneral=apg.apgid
			left join Empresa as emp  (nolock) on apg.apgEmpresa = emp.empId
			left join Empleador as empl (nolock) on empl.empEmpresa = emp.empId
			left join Persona as p (nolock) on emp.empPersona = p.perId
			left join CodigoCIIU as c (nolock) on emp.empCodigoCIIU = c.ciiId
			LEFT JOIN Ubicacionempresa ue (NOLOCK) ON emp.empid = ue.ubeempresa and ue.ubetipoubicacion='UBICACION_PRINCIPAL'
			left join ubicacion u2 on ue.ubeUbicacion=u2.ubiid
			LEFT JOIN Municipio me (NOLOCK) ON u2.ubiMunicipio =me.munId
			LEFT JOIN departamento de  (NOLOCK) ON me.mundepartamento = de.depid
			where apg.apgTipoSolicitante = 'EMPLEADOR' 
				and apg.apgModalidadRecaudoAporte='PILA'	   
				and cast(apg.apgfechaprocesamiento as date) = @fecha_procesamiento--='2021-02-10' 
				
		) as x
	where cast(x.[Fecha Contable] as DATE) = @fecha_procesamiento
END
