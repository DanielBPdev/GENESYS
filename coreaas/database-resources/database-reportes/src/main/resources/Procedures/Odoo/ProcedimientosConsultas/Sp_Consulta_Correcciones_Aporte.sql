--8. Consulta correcciones sobre Aportes
--ACT_AP_COR : Corrección de aportes
--Tipo Aportante
--Fecha de recaudo PILA
--No. de identificación del aportante
--Tipo de identificación del aportante
--Valor total del aporte
--Valor de aporte sin intereses
--Valor de interés del aporte
--Número de planilla
--id del aporte
--tipo identificación del aporte
--Estado de aporte
--Estado de vigencia del aporte
--Fecha de estado de aporte 
--Fecha de conciliación 
--Fecha contable
--Campo tipo plantilla

CREATE PROCEDURE [odoo].[ConsultaCorreccionesAportes](
	@fecha_procesamiento DATE = NULL
)

AS
BEGIN
	IF @fecha_procesamiento IS NULL
	BEGIN
		SET @fecha_procesamiento = CONVERT(DATE, DATEADD(DAY, -1, DATEADD(HH, -5, GETDATE())))
	END
	
	INSERT INTO [odoo].[correcciones_aportes] (tipo_aportante, fecha_recaudo_PILA, num_identificacion_aportante, tipo_identificacion_aportante, valor_total_aporte, valor_aporte_sin_intereses,
		valor_interes_aporte, numero_planilla, id_aporte, tipo_identificacion_aporte, estado_aporte, estado_vigencia_aporte, fecha_estado_aporte, fecha_conciliacion, fecha_contable, campo_tipo_plantilla)

	SELECT * FROM ( 
		SELECT
			SUBSTRING(apg.apgTipoSolicitante,1,1) [Tipo Aportante],
			apg.apgFechaRecaudo [Fecha de Recaudo PILA],
			case when apg.apgTipoSolicitante <> 'EMPLEADOR' then (select p.perNumeroIdentificacion from AporteGeneral as apgx inner join Persona as p on apgx.apgPersona = p.perId where apgx.apgId = apg.apgId) 
				else (select P.perNumeroIdentificacion from Empresa as emp inner join Persona as p on emp.empPersona = p.perId where emp.empId = apg.apgEmpresa) end  [No. de identificación del Aportante],
			case when apg.apgTipoSolicitante <> 'EMPLEADOR' then (select p.pertipoIdentificacion from AporteGeneral as apgx inner join Persona as p on apgx.apgPersona = p.perId where apgx.apgId = apg.apgId) 
				else (select P.pertipoIdentificacion from Empresa as emp inner join Persona as p on emp.empPersona = p.perId where emp.empId = apg.apgEmpresa) end  [Tipo de identificación del aportante],
			apg.apgValTotalApoObligatorio+apg.apgValorIntMora as [Valor total del aporte],
					apg.apgValTotalApoObligatorio as [Valor de aporte sin intereses],
					apg.apgValorIntMora as [Valor de interés del aporte],
			(select rg.regNumPlanilla from AporteGeneral as apgx inner join RegistroGeneral as rg on apgx.apgRegistroGeneral = rg.regId where apgx.apgId = apg.apgId) as [Nº Planilla],
			cast(apg.apgId as varchar (max)) as [id del aporte], --[No. Operación recaudo]
			apg.apgModalidadRecaudoAporte [tipo identificación del aporte],---POR CAMBIAR nOMBRE A TIPO APORTE? 
			apg.apgestadoregistroaporteaportante as [Estado de aporte],---Pendiente por verificar moaEstadoAporte
			apg.apgestadoaporteaportante as [Estado de vigencia del aporte],---
			moaFechaActualizacionEstado [Fecha de estado de aporte],
			(select cast(pebfechabloque6 as date)
				from AporteGeneral as apgx 
				inner join RegistroGeneral as rg on apgx.apgRegistroGeneral = rg.regId 
				inner join pilaindiceplanilla pi on pi.pipidplanilla=rg.regNumPlanilla
				inner join pilaestadobloque bq on pi.pipId=pebIndicePlanilla
				where apgx.apgId = apg.apgId
				and pebestadobloque6='REGISTRO_F_CONCILIADO'
			)[Fecha de conciliación],
			ma.moaFechaCreacion [Fecha contable],-- por validar
			'ACT_AP_COR'[Campo tipo plantilla]

			FROM MovimientoAporte as ma
			Left join AporteGeneral as apg on ma.moaAporteGeneral = apg.apgId
			left join AporteDetallado as apd on ma.moaAporteDetallado = apd.apdId
			left join SolicitudCorreccionAporte as sca on sca.scaAporteGeneral = apg.apgId
			left join Correccion as c on sca.scaId = c.corSolicitudCorreccionAporte
			inner join AporteGeneral as apgc on apgc.apgId = c.corAporteGeneral
			where ma.moaTipoAjuste = 'CORRECCION_A_LA_BAJA'
			and cast(ma.moaFechaCreacion as DATE) =@fecha_procesamiento	
		) as x
			where cast(x.[Fecha Contable] as DATE) =@fecha_procesamiento
		--Preguntar como se consulltarían los otros 2 registros (aporte Original, La anulación y la corrección y como se relacionan)
END
