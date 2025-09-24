-- =======================================================
-- Create Stored Procedure Template for Azure SQL Database
-- =======================================================

-- =============================================
-- Author:      Gustavo Giraldo
-- Create Date: 2021-08-10
-- Description: Procedimiento almacenado que se encarga de consultar 
-- los registros de aportes actualizados por la ejecución
-- del proceso (Prescripción de aportes) que cambian de estado RELACIONADO a
-- REGISTRADO, (Proceso mediante el cual aportes de Aportantes sin afiliación
-- vigente que figuran en estado RELACIONADO, cumplen con un tiempo
-- determinado (Normativamente) y cambian a estado REGISTRADO)
-- =============================================

CREATE PROCEDURE [odoo].[ConsultaPrescripcionAportes](
	@fecha_procesamiento DATE = NULL
)

AS
BEGIN
	IF @fecha_procesamiento IS NULL
	BEGIN
		SET @fecha_procesamiento = CONVERT(DATE, DATEADD(DAY, -1, DATEADD(HH, -5, GETDATE())))
	END

	INSERT INTO [odoo].[prescripcion_aportes] (fecha_procesamiento_prescripcion, fecha_recaudo_pila, tipo_identificacion_aportante,
		numero_identificacion_aportante, numero_solicitud_prescripcion, id_aporte, tipo_aporte, valor_total_aporte, valor_aporte_sin_intereses,
		valor_interes_aporte, estado_aporte, fecha_estado_aporte, estado_aportante_respecto_ccf, estado_vigencia_aporte,
		fecha_conciliacion, fecha_contable, campo_tipo_plantilla)
    --9 Consulta Prescripcion de aportes
	
	SELECT * FROM ( 
		SELECT 
			apgFechaReconocimiento [Fecha de procesamiento de prescripción.],
			apgFechaRecaudo [Fecha de recaudo PILA],
			case when apg.apgTipoSolicitante <> 'EMPLEADOR' then (select p.pertipoIdentificacion from AporteGeneral2 as apgx inner join Persona as p on apgx.apgPersona = p.perId where apgx.apgId = apg.apgId) 
				else (select P.pertipoIdentificacion from Empresa as emp inner join Persona as p on emp.empPersona = p.perId where emp.empId = apg.apgEmpresa) end  [Tipo de identificación del aportante],
			--
			case when apg.apgTipoSolicitante <> 'EMPLEADOR' then (select p.perNumeroIdentificacion from AporteGeneral2 as apgx inner join Persona as p on apgx.apgPersona = p.perId where apgx.apgId = apg.apgId) 
				else (select P.perNumeroIdentificacion from Empresa as emp inner join Persona as p on emp.empPersona = p.perId where emp.empId = apg.apgEmpresa) end  [Número de identificación del aportante],
			ma.moaid [Número de Solicitud de Prescripción],----No se encontró campo numero de operación
			cast(apg.apgId as varchar (max)) [id del aporte] ,
			apg.apgModalidadRecaudoAporte [tipo de aporte],
			apg.apgValTotalApoObligatorio+apg.apgValorIntMora [Valor total del aporte],
			apg.apgValTotalApoObligatorio[Valor de aporte sin intereses],
			apg.apgValorIntMora [Valor de interés del aporte],
			apg.apgestadoregistroaporteaportante [Estado de aporte],
			moaFechaActualizacionEstado[Fecha de estado de aporte],
			(select case when rolaf.roaEstadoAfiliado is null then isnull(replace(apg.apgEstadoAportante,'_',' '),'')
						else rolaf.roaEstadoAfiliado end 
				from Persona as p (nolock) 
				inner join Afiliado as af (nolock) on af.afiPersona = p.perId
				inner join RolAfiliado as rolaf (nolock) on rolaf.roaAfiliado = af.afiId	 
				where apg.apgPersona = p.perId
						)as[Estado del Aportante respecto a la CCF],----Preguntar si esta si es la lógica
			apg.apgestadoaporteaportante [Estado de vigencia del aporte],
			(select cast(pebfechabloque6 as date)
				from AporteGeneral2 as apgx 
				inner join RegistroGeneral2 as rg on apgx.apgRegistroGeneral = rg.regId 
				inner join pilaindiceplanilla pi on pi.pipidplanilla=rg.regNumPlanilla
				inner join pilaestadobloque bq on pi.pipId=pebIndicePlanilla
				where apgx.apgId = apg.apgId
				and pebestadobloque6='REGISTRO_F_CONCILIADO'
			)[Fecha de conciliación],
			moaFechaActualizacionEstado [Fecha contable],----Preguntar si si es la fecha dde movimiento
			'ACT_AP_ACREE_A_PR'[Campo tipo plantilla]


			FROM MovimientoAporte ma
			inner join AporteGeneral2 apg on ma.moaAporteGeneral = apg.apgId
			inner join AporteDetallado2 as apd on ma.moaAporteDetallado = apd.apdId

			WHERE (apgestadoregistroaporteaportante='REGISTRADO'
				and apgformareconocimientoaporte='RECONOCIMIENTO_RETROACTIVO_MANUAL')
				OR apgestadoregistroaporteaportante='OTROS_INGRESOS'
				and moaFechaActualizacionEstado=@fecha_procesamiento
	)
	as x
	where cast(x.[Fecha Contable] as DATE) =@fecha_procesamiento
END
