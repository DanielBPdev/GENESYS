-- =======================================================
-- Create Stored Procedure Template for Azure SQL Database
-- =======================================================

-- =============================================
-- Author:      Gustavo Giraldo
-- Create Date: 2021-06-07
-- Description: Procedimiento almacenado que se encarga
-- de consultar las afiliaciones de las personas y llenar la tabla afiliaciones_empresas
-- =============================================

CREATE PROCEDURE [odoo].[ConsultaAfiliacionesPersonas] (
	@fecha_procesamiento DATE = NULL
)

AS
BEGIN
	IF @fecha_procesamiento IS NULL
	BEGIN
		SET @fecha_procesamiento = CONVERT(DATE, DATEADD(DAY, -1, DATEADD(HH, -5, GETDATE())))
	END

	INSERT INTO [odoo].[afiliaciones_personas] (tipo_aportante, fecha_recaudo_pila, id_aporte, tipo_aporte, identificacion_aportante, tipo_identificacion_aportante,
		valor_total_aporte, valor_aporte_sin_intereses, valor_intereses_aporte, numero_planilla, estado_aportante_respecto_ccf, fecha_afiliacion_aportante, estado_aporte, fecha_estado_aporte, fecha_conciliacion, fecha_contable, tipo_plantilla)
	SELECT DISTINCT
		isnull(substring(apg.apgTipoSolicitante,1,1),'') as [Tipo Aportante]
		,ISNULL(apg.apgFechaRecaudo,'') as [Fecha de recaudo PILA]
		,cast(apg.apgId as varchar (max)) as [id del aporte] --[No. Operación recaudo]
		,apg.apgModalidadRecaudoAporte [tipo de aporte]
		,cast(isnull(p.perNumeroIdentificacion,'')as varchar(max)) as [No. de identificación del Aportante]
		,isnull(p.perTipoIdentificacion,'') as [Tipo de identificación del Aportante]
		,apg.apgValTotalApoObligatorio+apg.apgValorIntMora as [Valor total del aporte]
		,apg.apgValTotalApoObligatorio as [Valor de aporte sin intereses]
		,apg.apgValorIntMora as [Valor de interés del aporte]
		,(select rg.regNumPlanilla from AporteGeneral2 as apgx inner join RegistroGeneral2 as rg on apgx.apgRegistroGeneral = rg.regId where apgx.apgId = apg.apgId) as [Nº Planilla]
		,( select top 1 case when rolaf.roaEstadoAfiliado is null then isnull(replace(apg.apgEstadoAportante,'_',' '),'')
			else rolaf.roaEstadoAfiliado end 
			from RolAfiliado as rolaf (nolock) where rolaf.roaAfiliado = af.afiId
			order by roaFechaIngreso desc	 
		) as [Estado del Aportante respecto a la CCF]
		,cast(( select top 1  roaFechaIngreso
			from RolAfiliado as rolaf (nolock) where rolaf.roaAfiliado = af.afiId
			order by roaFechaIngreso desc	 
		) as date) as [Fecha de Afiliación del aportante]
		,apg.apgEstadoRegistroAporteAportante as [Estado del aporte]
		,apdFechaMovimiento[Fecha de estado de aporte]
		,(select pebfechabloque6 
			from AporteGeneral2 as apgx 
			inner join RegistroGeneral2 as rg on apgx.apgRegistroGeneral = rg.regId 
			inner join pilaindiceplanilla pi on pi.pipidplanilla=rg.regNumPlanilla
			inner join pilaestadobloque bq on pi.pipId=pebIndicePlanilla
			where apgx.apgId = apg.apgId
			and pebestadobloque6='RECAUDO_CONCILIADO'--'REGISTRO_F_CONCILIADO'
		)[Fecha de conciliación]
		,apdFechaMovimiento[Fecha contable]
		,'ACT_AP_ACREE_A_P7' [Campo tipo plantilla]

		from AporteGeneral2 as apg (nolock) -- 4301 registros
		left join AporteDetallado2 apd on apd.apdaportegeneral=apg.apgid
		left join Persona as p (nolock) on apg.apgPersona = p.perId
		left join Afiliado as af (nolock) on af.afiPersona = p.perId
		left join Ubicacion as u (nolock) on p.perUbicacionPrincipal = u.ubiId
		left join Municipio as m (nolock) on u.ubiMunicipio = m.munId
		left join Departamento as d (nolock) on m.munDepartamento = d.depId
		where apg.apgTipoSolicitante <> 'EMPLEADOR' 
		and apg.apgEstadoRegistroAporteAportante='REGISTRADO'
		AND apdFechaMovimiento IS NOT NULL	
		and cast(apdFechaMovimiento as date)=@fecha_procesamiento--'2021-02-10'

END
