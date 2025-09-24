-- =======================================================
-- Create Stored Procedure Template for Azure SQL Database
-- =======================================================

-- =============================================
-- Author:      Gustavo Giraldo
-- Create Date: 2021-07-30
-- Description: Procedimiento almacenado que se encarga de consultar los registros originados por la ejecución del
-- proceso 3.2.3 Asignación de subsidio FOVIS
-- =============================================

CREATE PROCEDURE [odoo].[ConsultaAsignacionFovis](
	@fecha_procesamiento DATE = NULL
)

AS
BEGIN
	IF @fecha_procesamiento IS NULL
	BEGIN
		SET @fecha_procesamiento = CONVERT(DATE, DATEADD(DAY, -1, DATEADD(HH, -5, GETDATE())))
	END

	INSERT INTO [odoo].[asignacion_fovis] (resolucion_asignacion_subsidio, fecha_asignacion_subsidio, tipo_identificacion_trabajador_subsidio_asignado,
		numero_identificacion_trabajador_subsidio_asignado, valor_total_subsidio_vivienda_asignado, tipo_localizacion_subsidio, fecha_contable, campo_tipo_plantilla)
	SELECT DISTINCT
		cast((select top 1 aafNumeroResolucion from ActaAsignacionFovis where aafSolicitudAsignacion=sa.safid order by aaffechaactaasignacionfovis desc)as varchar)[Resolución de Asignación de Subsidio],
		(safFechaAceptacion)[Fecha de Asignación de subsidio],---Validado con Jhon y Miguel
		replace((
		select p.pertipoIdentificacion 
		from postulacionfovis
		inner join jefehogar j on j.jehId=pofJefeHogar
		inner join afiliado a on a.afiId=j.jehAfiliado
		inner join persona p on a.afiPersona=p.perId
		where pf.pofid= postulacionfovis.pofId
		),'_',' ') as [Tipo de Identificación del trabajador del subsidio Asignado],

		(select p.pernumeroIdentificacion 
		from postulacionfovis
		inner join jefehogar j on j.jehId=pofJefeHogar
		inner join afiliado a on a.afiId=j.jehAfiliado
		inner join persona p on a.afiPersona=p.perId
		where pf.pofid= postulacionfovis.pofId) as [No. de Identificación del trabajador del subsidio Asignado],

		--cast(safValorSFVAsignado as varchar) [Valor del subsidio asignado por cada trabajador],--- Ver comentario en el excel

		cast(pofvalorAsignadoSFV as varchar) [Valor total del subsidio de vivienda asignado],---Verificado

		CASE WHEN POFMODALIDAD LIKE '%URBANA%' THEN 'Urbano' WHEN POFMODALIDAD LIKE '%RURAL%' THEN 'Rural' ELSE 'MEJORAMIENTO_VIVIENDA_SALUDABLE' end [Tipo localización subsidio],---Rural-urbano

		(safFechaAceptacion) [Fecha contable],
		'CRE_FV_ASI'[Campo tipo plantilla]

		FROM postulacionfovis pf
		left join jefehogar j on j.jehId=pofJefeHogar
		left join afiliado a on a.afiId=j.jehAfiliado
		LEFT join persona p on a.afiPersona=p.perId
		LEFT JOIN personadetalle pd  WITH(NOLOCK) ON p.perid = pd.pedPersona
		LEFT join ubicacion u on p.perUbicacionPrincipal=u.ubiid
		LEFT join municipio mun on mun.munid=u.ubiMunicipio
		LEFT join departamento dep on dep.depid=mun.munDepartamento 
		INNER join [dbo].[solicitudasignacion] sa WITH(NOLOCK) on sa.safId=pf.pofSolicitudAsignacion
		left join solicitud s on sa.safsolicitudglobal=s.solid
		--
		inner join ActaAsignacionFovis on aafSolicitudAsignacion=sa.safid
		---
		where S.SOLRESULTADOPROCESO='APROBADA'----POR VALIDAR
			and pf.pofresultadoasignacion='ESTADO_ASIGNADO'
			--and solcanalrecepcion='PRESENCIAL'
			AND cast(solfecharadicacion as date) =@fecha_procesamiento
			--select * from [dbo].[ParametrizacionModalidad]
END
