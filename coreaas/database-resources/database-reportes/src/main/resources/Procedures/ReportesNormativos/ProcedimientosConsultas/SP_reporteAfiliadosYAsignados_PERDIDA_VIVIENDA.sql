/****** Object:  StoredProcedure [dbo].[reporteAfiliadosYAsignados_PERDIDA_VIVIENDA]    Script Date: 2023-11-28 7:58:15 AM ******/
 
/****** Object:  StoredProcedure [dbo].[reporteAfiliadosYAsignados_PERDIDA_VIVIENDA]    Script Date: 2023-11-07 7:30:13 AM ******/
/****** Object:  StoredProcedure [dbo].[reporteAfiliadosYAsignados_PERDIDA_VIVIENDA]    Script Date: 2023-07-19 10:24:06 AM ******/
----reporte 17 c
 
create or ALTER     PROCEDURE [dbo].[reporteAfiliadosYAsignados_PERDIDA_VIVIENDA](
	@FECHA_INICIAL DATETIME,
	@FECHA_FINAL DATETIME
)
---exec reporteAfiliadosYAsignados_PERDIDA_VIVIENDA '2023-11-01','2023-11-28'
AS
BEGIN
--SET ANSI_WARNINGS OFF
SET NOCOUNT ON

SET ANSI_NULLS ON
 
SET QUOTED_IDENTIFIER ON
 
----------Afiliados y Asignados - Perdida de Vivienda

	SELECT 
		----------NIT ENTIDAD
		REPLACE(PARAMETRO.prmValor,'-','') AS [NIT ENTIDAD],

		----------ID HOGAR
		SOLICITUD_GLOBAL_POF.solNumeroRadicacion AS [ID HOGAR],
	
		----------DOCUMENTO BENEFICIARIO
		CASE 
			WHEN PERSONA_INTEG.perId <> NULL
			THEN PERSONA_INTEG.perNumeroIdentificacion 
			ELSE PERSONA_AFI.perNumeroIdentificacion
		END AS [DOCUMENTO BENEFICIARIO],
	
		----------FECHA ASIGNACIÓN
	--	MAX( FORMAT( SOL_ASIGNACION_POF.safFechaAceptacion ,'yyyy/MM/dd') ) 
	'' AS [FECHA ASIGNACIÓN],
	
		----------TIPO DE DOCUMENTO
		CASE
			(
				CASE 
					WHEN PERSONA_INTEG.perId <> NULL
					THEN PERSONA_INTEG.perTipoIdentificacion
					ELSE PERSONA_AFI.perTipoIdentificacion
				END
			)
			WHEN 'CEDULA_CIUDADANIA'
			THEN '1'
			WHEN 'CEDULA_EXTRANJERIA'
			THEN '2'
			WHEN 'TARJETA_IDENTIDAD'
			THEN '7'
		END AS [TIPO DE DOCUMENTO]
	
	FROM 
		--Solicitud AS SOLICITUD
		--INNER JOIN SolicitudNovedadFovis AS SOL_NOVEDAD ON SOLICITUD.solId = SOL_NOVEDAD.snfSolicitudGlobal  
		--INNER JOIN SolicitudNovedadPersonaFovis AS SOL_NOVEDAD_PERSONA ON SOL_NOVEDAD.snfId = SOL_NOVEDAD_PERSONA.spfSolicitudNovedadFovis

	--	INNER JOIN 
	PostulacionFOVIS AS POSTULACION --ON POSTULACION.pofId = SOL_NOVEDAD_PERSONA.spfPostulacionFovis
		INNER JOIN SolicitudAsignacion AS SOL_ASIGNACION_POF ON SOL_ASIGNACION_POF.safCicloAsignacion = POSTULACION.pofCicloAsignacion

		INNER JOIN SolicitudPostulacion AS SOL_POF ON SOL_POF.spoPostulacionFOVIS = POSTULACION.pofId
		INNER JOIN Solicitud AS SOLICITUD_GLOBAL_POF ON SOLICITUD_GLOBAL_POF.solId = SOL_POF.spoSolicitudGlobal

		INNER JOIN JefeHogar AS JEFHOG ON JEFHOG.jehId = POSTULACION.pofJefeHogar
		INNER JOIN Afiliado AS AFILIADO ON AFILIADO.afiId = JEFHOG.jehAfiliado
		INNER JOIN Persona AS PERSONA_AFI ON PERSONA_AFI.perId = AFILIADO.afiPersona

		LEFT JOIN IntegranteHogar AS INTEGRANTE ON INTEGRANTE.inhJefeHogar = JEFHOG.jehId

		LEFT JOIN Persona AS PERSONA_INTEG 
			ON INTEGRANTE.inhPersona = PERSONA_INTEG.perId
			AND PERSONA_INTEG.perTipoIdentificacion IN ( 'CEDULA_CIUDADANIA', 'CEDULA_EXTRANJERIA')

		LEFT JOIN Parametro AS PARAMETRO ON PARAMETRO.prmNombre IN ('NUMERO_ID_CCF')
		LEFT JOIN Parametro AS PARAMETRO_NOMBRE ON PARAMETRO_NOMBRE.prmNombre IN ('NOMBRE_CCF')

	WHERE 
		--SOLICITUD.solNumeroRadicacion IN ( '012022218013', '012022218022', '012022218021', '012022218020', '012022218057', '012022218069' ) AND 
	--	POSTULACION.pofEstadoHogar IN ( 'RESTITUIDO_SIN_SANCION', 'RESTITUIDO_CON_SANCION' )
		--AND POSTULACION.pofMotivoRestitucion IN ( 'VIVIENDA_SUBSIDIADA_OBJETO_REMATE_JUDICIAL' ) 
		---AND PERSONA_AFI.perNumeroIdentificacion ='70856212'
		pofEstadoHogar IN  ( 'SUBSIDIO_DESEMBOLSADO' ,'ASIGNADO_CON_PRIMERA_PRORROGA',
								'ASIGNADO_CON_SEGUNDA_PRORROGA',
								'ASIGNADO_SIN_PRORROGA' )
		AND pofHogarPerdioSubsidioNoPago = 1
	GROUP BY 
		PARAMETRO.prmValor,
		SOLICITUD_GLOBAL_POF.solNumeroRadicacion,
		PERSONA_AFI.perNumeroIdentificacion,
		PERSONA_AFI.perTipoIdentificacion	

END