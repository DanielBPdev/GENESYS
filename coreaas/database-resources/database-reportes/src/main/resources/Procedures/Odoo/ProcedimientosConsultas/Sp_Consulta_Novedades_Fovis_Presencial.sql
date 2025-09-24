-- =======================================================
-- Create Stored Procedure Template for Azure SQL Database
-- =======================================================

-- =============================================
-- Author:      Gustavo Giraldo
-- Create Date: 2021-07-31
-- Description: Procedimiento almacenado que se encarga de consultar los registros originados por la ejecución del
-- proceso 3.2.5 Novedades FOVIS Presencial, clasificados por Renuncias y Restituciones y reintegros
-- =============================================

CREATE PROCEDURE [odoo].[ConsultaNovedadesFovisPresencial](
	@fecha_procesamiento DATE = NULL
)

AS
BEGIN
	IF @fecha_procesamiento IS NULL
	BEGIN
		SET @fecha_procesamiento = CONVERT(DATE, DATEADD(DAY, -1, DATEADD(HH, -5, GETDATE())))
	END

	INSERT INTO [odoo].[novedades_fovis_presencial] (fecha_procesamiento, numero_solicitud, tipo_identificacion_trabajador, numero_identificacion_trabajador, valor_subsidio,
		tipo_movimiento, numero_resolucion, tipo_localizacion_subsidio, fecha_consignacion_bancaria_fecha_transferencia, motivo_reintegro, fecha_contable, campo_tipo_plantilla)
	SELECT  
		cast(solfecharadicacion as date) [Fecha de Procesamiento], 
		solnumeroradicacion [Número de Solicitud] ,
		replace((
			select p.pertipoIdentificacion 
			from postulacionfovis
			inner join jefehogar j on j.jehId=pofJefeHogar
			inner join afiliado a on a.afiId=j.jehAfiliado
			inner join persona p on a.afiPersona=p.perId
			where pf.pofid= postulacionfovis.pofId
		),'_',' ') as [Tipo de Identificación del trabajador],
		(select p.pernumeroIdentificacion 
			from postulacionfovis
			inner join jefehogar j on j.jehId=pofJefeHogar
			inner join afiliado a on a.afiId=j.jehAfiliado
			inner join persona p on a.afiPersona=p.perId
			where pf.pofid= postulacionfovis.pofId) as [No. de Identificación del trabajador],

		pofValorAsignadoSFV [Valor del subsidio],---Verificado
		case when s.soltipotransaccion like 'RENUNCIO%' then 'Renuncia' when s.soltipotransaccion like 'RESTITUCION%' then 'Restitución' ELSE 'Reintegro' end [Tipo de movimiento],
		cast((select top 1 aafNumeroResolucion from ActaAsignacionFovis where aafSolicitudAsignacion=sa.safid order by aaffechaactaasignacionfovis desc)as varchar) [Número de Resolución] ,
		CASE WHEN POFMODALIDAD LIKE '%URBANA%' THEN 'Urbano' WHEN POFMODALIDAD LIKE '%RURAL%' THEN 'Rural' ELSE 'MEJORAMIENTO_VIVIENDA_SALUDABLE' end [Tipo localización subsidio],
		lg.lgdFechaTransferencia [Fecha de consignación bancaria o fecha de transferencia],
		pofMotivoRestitucion [Motivo del reintegro], ----pofMotivoDesistimiento--pofMotivoRechazo--pofMotivoHabilitacion
		cast(solfecharadicacion as date) [Fecha contable],
		case when s.soltipotransaccion like 'RENUNCIO%' then 'CRE_FV_REN' when s.soltipotransaccion like 'RESTITUCION%' then 'CRE_FV_RES' ELSE 'CRE_FV_REI' end [Campo tipo plantilla]

		FROM 
		[dbo].[SolicitudNovedadPersonaFovis] snpf
		inner join persona p on p.perid =[spfPersona]
		inner join [SolicitudNovedadFovis] snf on snfId=[spfSolicitudNovedadFovis]
		inner join solicitud s on snfSolicitudGlobal=s.solid
		inner join postulacionfovis pf on snpf.spfPostulacionFovis=pf.pofId
		INNER join [dbo].[solicitudasignacion] sa WITH(NOLOCK) on sa.safId=pf.pofSolicitudAsignacion
		--
		left join [dbo].[solicitudlegalizaciondesembolso] sld WITH(NOLOCK) on sld.[sldPostulacionFOVIS]=pf.pofid
		left join legalizaciondesembolso lg WITH(NOLOCK) on sld.[sldLegalizacionDesembolso]=lg.lgdid

		LEFT JOIN personadetalle pd  WITH(NOLOCK) ON p.perid = pd.pedPersona
		LEFT join ubicacion u on p.perUbicacionPrincipal=u.ubiid
		LEFT join municipio mun on mun.munid=u.ubiMunicipio
		LEFT join departamento dep on dep.depid=mun.munDepartamento 
		--

		where 
			---(s.soltipotransaccion in('RENUNCIO_SUBISIDIO_ASIGNADO','RESTITUCION_SUBSIDIO_INCUMPLIMIENTO') or (s.soltipotransaccion like 'REINTEGRO%'))
			S.SOLRESULTADOPROCESO='APROBADA'--'CERRADA'
			and solcanalrecepcion='PRESENCIAL'
			and cast(solfecharadicacion as date)= @fecha_procesamiento
			and (pofEstadoHogar like'%RENUNC%' or pofEstadoHogar like'%RESTI%' OR pofEstadoHogar like'%REINT%')
		----
		---La condición sería en la tabla de postulacionfovis---Revisar los constraint
		---
		--select *
		--from --[SolicitudNovedadFovis] snf on snfId=[spfSolicitudNovedadFovis]
		--solicitud s ---on snfSolicitudGlobal=s.solid
		--where (s.soltipotransaccion in('RENUNCIO_SUBISIDIO_ASIGNADO','RESTITUCION_SUBSIDIO_INCUMPLIMIENTO') or (s.soltipotransaccion like 'REINTEGRO%'))

		--select * from postulacionfovis
		--inner join postulacionfovis pf on snpf.spfPostulacionFovis=pf.pofId
		-----reembolso voluntario del subsidio?
END
