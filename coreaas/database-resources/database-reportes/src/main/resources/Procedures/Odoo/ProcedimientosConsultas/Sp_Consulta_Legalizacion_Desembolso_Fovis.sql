-- =======================================================
-- Create Stored Procedure Template for Azure SQL Database
-- =======================================================

-- =============================================
-- Author:      Gustavo Giraldo
-- Create Date: 2021-07-31
-- Description: Procedimiento almacenado que se encarga de consultar los registros originados por la ejecución del
-- proceso 3.2.4 Legalización y Desembolso FOVIS
-- =============================================

CREATE PROCEDURE [odoo].[ConsultaLegalizacionDesembolsoFovis](
	@fecha_procesamiento DATE = NULL
)

AS
BEGIN
	IF @fecha_procesamiento IS NULL
	BEGIN
		SET @fecha_procesamiento = CONVERT(DATE, DATEADD(DAY, -1, DATEADD(HH, -5, GETDATE())))
	END

	INSERT INTO [odoo].[legalizacion_desembolso_fovis] (fecha_desembolso_subsidio_asignado, nombre_proyecto, numero_solicitud, tipo_identificacion_trabajador_subsidio_asignado,
		numero_identificacion_trabajador_subsidio_asignado, nombre_completo_trabajador_subsidio_asignado, tipo_identificacion_tercero_proyecto, numero_identificacion_tercero_proyecto,
		valor_subsidio_asignado_por_tercero, tipo_localizacion_subsidio, fecha_contable, campo_tipo_plantilla)
	SELECT DISTINCT
		sldfechaoperacion  [Fecha de desembolso del Subsidio Asignado],---lgdFechaTransferencia
		-- Si tiene json en dato temporal solicitud entonces busca el proyecto solucion vivienda ahi
		CASE
			WHEN dtSol.dtsJsonPayload IS NOT NULL 
				THEN JSON_VALUE(CAST(dtSol.dtsJsonPayload AS NVARCHAR(MAX)), '$.proyectoSolucionViviendaLegalizacion.nombreProyecto')
			ELSE psvNombreProyecto
		END [Nombre del proyecto],

		solnumeroradicacion [Número de solicitud] ,
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

		(select concat(p.perPrimerNombre,' ',p.persegundoNombre,' ',p.perPrimerapellido,' ',p.persegundoapellido)
		from postulacionfovis
		inner join jefehogar j on j.jehId=pofJefeHogar
		inner join afiliado a on a.afiId=j.jehAfiliado
		inner join persona p on a.afiPersona=p.perId
		where pf.pofid= postulacionfovis.pofId

		) as  [Nombre completo del trabajador con Subsidio Asignado],
		--------
		-------------------------------------------------------------------------------------------Arreglar
		-- Si tiene json en dato temporal solicitud entonces busca el oferente ahí
		CASE
			WHEN dtSol.dtsJsonPayload IS NOT NULL 
				THEN JSON_VALUE(CAST(dtSol.dtsJsonPayload AS NVARCHAR(MAX)), '$.oferenteLegalizacion.oferente.empresa.tipoIdentificacion')
			ELSE (select pertipoidentificacion from persona where perid=ofepersona )
		END [Tipo de Identificación del tercero del proyecto],

		CASE
			WHEN dtSol.dtsJsonPayload IS NOT NULL 
				THEN JSON_VALUE(CAST(dtSol.dtsJsonPayload AS NVARCHAR(MAX)), '$.oferenteLegalizacion.oferente.empresa.numeroIdentificacion')
			ELSE (select perNumeroIdentificacion from persona where perid=ofepersona )
		END [No. de Identificación del tercero del proyecto],
		---
		lgdmontodesembolsado [Valor del subsidio asignado por tercero],
		CASE WHEN POFMODALIDAD LIKE '%URBANA%' THEN 'Urbano' else 'Rural' end [Tipo localización subsidio],
		sldfechaoperacion [Fecha contable],----Preguntar---
		'CRE_FV_DES'[Campo tipo plantilla]

		FROM postulacionfovis pf
		left join jefehogar j on j.jehId=pofJefeHogar
		left join afiliado a on a.afiId=j.jehAfiliado
		LEFT join persona p on a.afiPersona=p.perId
		LEFT JOIN personadetalle pd  WITH(NOLOCK) ON p.perid = pd.pedPersona
		LEFT join ubicacion u on p.perUbicacionPrincipal=u.ubiid
		LEFT join municipio mun on mun.munid=u.ubiMunicipio
		LEFT join departamento dep on dep.depid=mun.munDepartamento 
		---No hay datos en solicitudlegalizacion desembolso ni en core ni en reportes
		INNER join [dbo].[solicitudlegalizaciondesembolso] sa WITH(NOLOCK) on sa.[sldPostulacionFOVIS]=pf.pofid
		left join legalizaciondesembolso WITH(NOLOCK) on sa.[sldLegalizacionDesembolso]=lgdid
		left join solicitud s on sa.sldsolicitudglobal=s.solid
		left join ProyectoSolucionVivienda psv on pf.pofProyectoSolucionVivienda=psv.psvId
		left join oferente o on psv.psvOferente = o.ofeId
		LEFT JOIN DatoTemporalSolicitud dtSol ON sa.sldSolicitudGlobal = dtSol.dtsSolicitud
		where S.SOLRESULTADOPROCESO='APROBADA'--'CERRADA'
			and sa.sldestadosolicitud='LEGALIZACION_Y_DESEMBOLSO_CERRADO'
			AND cast(solfecharadicacion as date) =@fecha_procesamiento

		--select * from [dbo].[solicitudlegalizaciondesembolso] sa WITH(NOLOCK) on sa.[sldPostulacionFOVIS]=pf.pofid
		--select * from legalizaciondesembolso
		--where lgdmontodesembolsado<>lgdvalordesembolsar    
END
