/****** Object:  StoredProcedure [dbo].[reporteAfiliadosYAsignados_ASIGNADOS]    Script Date: 28/02/2023 4:49:31 p. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteAfiliadosYAsignados_ASIGNADOS]    Script Date: 26/01/2023 10:44:56 a. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteAfiliadosYAsignados_ASIGNADOS]    Script Date: 24/01/2023 10:05:48 a. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteAfiliadosYAsignados_ASIGNADOS]    Script Date: 12/10/2022 10:46:09 a. m. ******/
-- =============================================
-- Author: Miguel Angel Perilla
-- Last Update Date: 12 Julio 2022
-- Description: Procedimiento almacenado para obtener el resultado que hace referencia al reporte 17B.
-- Reporte 17B
---EXEC ReporteAfiliadosYAsignados_ASIGNADOS '2023-01-01','2023-02-28'
-- =============================================

CREATE OR ALTER PROCEDURE [dbo].[reporteAfiliadosYAsignados_ASIGNADOS](
	@FECHA_INICIAL DATETIME,
	@FECHA_FINAL DATETIME
)

AS
BEGIN
SET NOCOUNT ON
----------------------------------------------------------------------------------------------
--/---------------------------------------**********---------------------------------------\--
--                    REPORTE DE AFILIADOS Y ASIGNADOS - ASIGNADOS  -  N° 17B.
--\---------------------------------------**********---------------------------------------/--
----------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------
/****-- JSON PARA OBTENER EL ESTADO CIVIL DEL BENEFICIARIO EN EL MOMENTO DE LA POSTULACION --****/
--------------------------------------------------------------------------------------------------
DECLARE @jsonPostulacion nvarchar(max);

IF OBJECT_ID('tempdb.dbo.#JSON_ESTADO_CIVIL_TEMP', 'U') IS NOT NULL
  DROP TABLE #JSON_ESTADO_CIVIL_TEMP;

IF OBJECT_ID('tempdb.dbo.#ESTADO_CIVIL_TEMP', 'U') IS NOT NULL
  DROP TABLE #ESTADO_CIVIL_TEMP;

CREATE TABLE #ESTADO_CIVIL_TEMP(
	ID_POSTULACION BIGINT,
	ESTADO_CIVIL VARCHAR(255)
)


SELECT 
	pofId AS ID_POSTULACION,
cast(pofJsonPostulacion as nvarchar(max)) AS JSON_POSTULACION

INTO #JSON_ESTADO_CIVIL_TEMP
FROM PostulacionFOVIS 
WHERE pofJsonPostulacion IS NOT NULL

DECLARE 
	@longPostulacionesJSON INT, 
	@CONTADOR INT

SET @CONTADOR = 1
SET @longPostulacionesJSON = (SELECT COUNT(*) FROM #JSON_ESTADO_CIVIL_TEMP )

WHILE (@CONTADOR <= @longPostulacionesJSON)	
	BEGIN
		SELECT TOP(@CONTADOR)
			@jsonPostulacion = cast(JSON_POSTULACION as nvarchar(max))
		FROM
			#JSON_ESTADO_CIVIL_TEMP
		ORDER BY ID_POSTULACION 

		INSERT INTO #ESTADO_CIVIL_TEMP
		
		SELECT
			DATA_JSON_POSTULACION.ID_POSTULACION,
			DATA_JSON_POSTULACION.ESTADO_CIVIL
		FROM 
		OPENJSON( @jsonPostulacion )
		WITH (
			ID_POSTULACION VARCHAR(500) '$.postulacion.idPostulacion',
			ESTADO_CIVIL VARCHAR(500) '$.postulacion.jefeHogar.estadoCivil'
		)AS DATA_JSON_POSTULACION
		GROUP BY
			DATA_JSON_POSTULACION.ID_POSTULACION,
			DATA_JSON_POSTULACION.ESTADO_CIVIL

		SET @CONTADOR = @CONTADOR + 1
		PRINT @CONTADOR
	END
----------------------------------------------------------------------------------------
/****-- FINAL JSON ESTADO CIVIL DEL BENEFICIARIO EN EL MOMENTO DE LA POSTULACION --****/
----------------------------------------------------------------------------------------
---------------------------------------------------
------ JEFES DE HOGAR
---------------------------------------------------DROP TABLE #TEMP_AfiliadosTitulares SELECT * FROM #TEMP_AfiliadosTitulares

SELECT

----------NIT Entidad
	REPLACE ( PARAMETRO.prmValor, '-', '' ) AS [NIT Entidad],

---------ID HOGAR
	SOLICITUD_GLOBAL_POSTULACION.solNumeroRadicacion AS [ID de Hogar] ,

----------Documento Beneficiario
	PERSONA.perNumeroIdentificacion AS [Documento beneficiario],

----------Apellidos del Beneficiario
	CONCAT( PERSONA.perPrimerApellido, ' ', PERSONA.perSegundoApellido ) AS [Apellidos Beneficiario], 

----------Nombres del Beneficiario
	CONCAT( PERSONA.perPrimerNombre, ' ', PERSONA.perSegundoNombre ) AS [Nombres Beneficiario],

----------Nombre Entidad
	PARAMETRO_NOMBRE.prmValor AS [Nombre Entidad],

----------Fecha de asignación
	FORMAT( SOLICITUD_ASIGNACION.safFechaAceptacion, 'yyyy/MM/dd' ) AS [Fecha Asignación],

----------Valor asignación
	REPLACE( CASE
		  WHEN ISNULL(POSTULACION.pofValorSFVAjustado,0) = 0
		  THEN POSTULACION.pofValorAsignadoSFV 
		  ELSE POSTULACION.pofValorSFVAjustado 
		  END ,'.00000','')
	 AS [Valor Asignación],

----------Tipo de vivienda
	CASE
		WHEN POSTULACION.pofModalidad IN (
			'CONSTRUCCION_SITIO_PROPIO_RURAL',
			'ADQUISICION_VIVIENDA_NUEVA_RURAL',
			'MEJORAMIENTO_VIVIENDA_RURAL'
		)
		THEN 5
		ELSE 6 
	END AS [Tipo de Vivienda],

	--select top 10 *  from ActaAsignacionFovis
	--select top 10* from PostulacionFovis inner join ActaAsignacionFovis on aafSolicitudAsignacion = pofSolicitudAsignacion

----------Número resolución
	--POSTULACION.pofIdentificardorDocumentoActaAsignacion AS [Numero de Resolución],
	CASE
		WHEN CAST(ACTA_FOVIS.aafNumeroOficio AS BIGINT) IS NULL
		THEN CAST(ACTA_FOVIS.aafNumeroResolucion AS BIGINT) 
		ELSE CAST(ACTA_FOVIS.aafNumeroOficio AS BIGINT)
	END AS [Numero de Resolución],

----------Campo en blanco
	NULL AS [Campo en Blanco],

----------Tipo de documento
	CASE
		WHEN PERSONA.perTipoIdentificacion = 'CEDULA_CIUDADANIA'
		THEN '1'
		WHEN PERSONA.perTipoIdentificacion = 'CEDULA_EXTRANJERIA'
		THEN '2'
		WHEN PERSONA.perTipoIdentificacion = 'TARJETA_IDENTIDAD'
		THEN '7'
		--ELSE PERSONA.perTipoIdentificacion
	END	AS [Tipo Documento],

----------Estado Civil
	/*CASE
		WHEN JsonPostulacion.ESTADO_CIVIL IS NOT NULL
		THEN 
			CASE
				WHEN JsonPostulacion.ESTADO_CIVIL = 'SOLTERO'
				THEN '1'
				WHEN JsonPostulacion.ESTADO_CIVIL IN ( 'UNION_LIBRE', 'CASADO' )
				THEN '2'
				WHEN JsonPostulacion.ESTADO_CIVIL IN ( 'SEPARADO', 'VIUDO', '' )
				THEN '3'
				ELSE JsonPostulacion.ESTADO_CIVIL
			END
		ELSE*/
			CASE 
				WHEN PERSONA_DET.pedEstadoCivil = 'SOLTERO'
				THEN '1'
				WHEN PERSONA_DET.pedEstadoCivil IN ( 'UNION_LIBRE', 'CASADO' )
				THEN '2'
				WHEN PERSONA_DET.pedEstadoCivil IN ( 'SEPARADO', 'VIUDO', 'DIVORCIADO' )
				THEN '3'
				ELSE PERSONA_DET.pedEstadoCivil
			--END
	END AS [Estado Civil],

----------ID Parentesco
	'1' AS [ID Parentesco],

----------Modalidad Vivienda
	CASE 

		WHEN POSTULACION.pofModalidad IN ('ADQUISICION_VIVIENDA_NUEVA_URBANA', 'ADQUISICION_VIVIENDA_NUEVA_RURAL', 'ADQUISICION_VIVIENDA_USADA_URBANA', 'ADQUISICION_VIVIENDA_USADA_RURAL')
		THEN '1'

		WHEN POSTULACION.pofModalidad IN ('CONSTRUCCION_SITIO_PROPIO_URBANO', 'CONSTRUCCION_SITIO_PROPIO_RURAL')
		THEN '2'

		WHEN POSTULACION.pofModalidad IN ('MEJORAMIENTO_VIVIENDA_URBANA', 'MEJORAMIENTO_VIVIENDA_RURAL', 'MEJORAMIENTO_VIVIENDA_SALUDABLE')
		THEN '3'

		ELSE '4'

	END AS [Modalidad Vivienda],

----------Perdió Vivienda
	CASE 
		WHEN POSTULACION.pofEstadoHogar IN ( 'RESTITUIDO_SIN_SANCION'  )
		AND POSTULACION.pofMotivoRestitucion IN ( 'VIVIENDA_SUBSIDIADA_OBJETO_REMATE_JUDICIAL' ) 
		THEN '1'
		ELSE '0'
	END AS [Perdió Vivienda],

----------Estado Legalizacion
	CASE 
		WHEN LEGALIZACION.sldEstadoSolicitud IN ('LEGALIZACION_Y_DESEMBOLSO_CERRADO')
		THEN 'Si'
		ELSE 'No'
	END AS [Estado Legalización],

----------Fecha Legalizacion
	
	CASE WHEN LEGALIZACION.sldEstadoSolicitud IN ('LEGALIZACION_Y_DESEMBOLSO_CERRADO')
	THEN FORMAT( LEGALIZACION.sldFechaOperacion, 'yyyy/MM/dd' ) 
	ELSE '' END 
	AS [Fecha Legalización],

	POSTULACION.pofId,

	POSTULACION.pofEstadoHogar
	
INTO #TEMP_AfiliadosTitulares
--select *
FROM 
	PostulacionFOVIS AS POSTULACION
	
	
	INNER JOIN ActaAsignacionFovis AS ACTA_FOVIS ON aafSolicitudAsignacion = POSTULACION.pofSolicitudAsignacion

	LEFT JOIN SolicitudAsignacion AS SOLICITUD_ASIGNACION 
		ON SOLICITUD_ASIGNACION.safId = POSTULACION.pofSolicitudAsignacion  
	INNER JOIN Solicitud AS SOLICITUD_GLOBAL_ASIGNACION 
		ON SOLICITUD_GLOBAL_ASIGNACION.solId = SOLICITUD_ASIGNACION.safSolicitudGlobal

	LEFT JOIN SolicitudLegalizacionDesembolso AS LEGALIZACION 
		ON LEGALIZACION.sldPostulacionFOVIS = POSTULACION.pofId

	LEFT JOIN SolicitudPostulacion AS SOLICITUD_POSTULACION 
		ON SOLICITUD_POSTULACION.spoPostulacionFOVIS = POSTULACION.pofId
	INNER JOIN Solicitud AS SOLICITUD_GLOBAL_POSTULACION 
		ON SOLICITUD_GLOBAL_POSTULACION.solId = SOLICITUD_POSTULACION.spoSolicitudGlobal

	LEFT JOIN JefeHogar AS JEFE ON JEFE.jehId = POSTULACION.pofJefeHogar
	LEFT JOIN Afiliado AS AFILIADO ON AFILIADO.afiId = JEFE.jehAfiliado
	LEFT JOIN Persona AS PERSONA 
		ON PERSONA.perId = AFILIADO.afiPersona 
		AND PERSONA.perTipoIdentificacion IN ('TARJETA_IDENTIDAD', 'CEDULA_CIUDADANIA', 'CEDULA_EXTRANJERIA')
	INNER JOIN PersonaDetalle AS PERSONA_DET ON PERSONA_DET.pedPersona = PERSONA.perId

	LEFT JOIN SolicitudNovedadPersona AS NOVEDAD ON NOVEDAD.snpPersona = AFILIADO.afiPersona
	LEFT JOIN SolicitudNovedad AS SOL_NOVEDAD ON NOVEDAD.snpSolicitudNovedad = SOL_NOVEDAD.snoId
	LEFT JOIN Solicitud AS SOLICITUD_GLOBAL_NOVEDAD ON SOLICITUD_GLOBAL_NOVEDAD.solId = SOL_NOVEDAD.snoSolicitudGlobal
	LEFT JOIN parametrizacionNovedad AS PARAMETRIZACION_NOV ON SOL_NOVEDAD.snoNovedad = PARAMETRIZACION_NOV.novId	

	LEFT JOIN IntegranteHogar AS INTEGRANTE ON INTEGRANTE.inhJefeHogar = jefe.jehId
	--INNER JOIN Persona AS PERSONA_INTEGRANTE ON INTEGRANTE.inhJefeHogar = jefe.jehId

	LEFT JOIN Parametro AS PARAMETRO ON PARAMETRO.prmNombre IN ('NUMERO_ID_CCF')
	LEFT JOIN Parametro AS PARAMETRO_NOMBRE ON PARAMETRO_NOMBRE.prmNombre IN ('NOMBRE_CCF')

	LEFT JOIN #ESTADO_CIVIL_TEMP AS JsonPostulacion ON JsonPostulacion.ID_POSTULACION = POSTULACION.pofId
	--where PERSONA.perNumeroidentificacion in ('39289135'  )
WHERE  --PERSONA.perNumeroidentificacion in ('39289135') and 
	POSTULACION.pofEstadoHogar IN(
		'SUBSIDIO_DESEMBOLSADO',
		'ASIGNADO_SIN_PRORROGA', 
		'ASIGNADO_CON_PRIMERA_PRORROGA', 
		'ASIGNADO_CON_SEGUNDA_PRORROGA',
		'RESTITUIDO_CON_SANCION'---MENOR A 10 AÑOS
	) 
	OR (POSTULACION.pofEstadoHogar = 'RESTITUIDO_SIN_SANCION'
		AND pofMotivoRestitucion IN ( 'VIVIENDA_SUBSIDIADA_OBJETO_REMATE_JUDICIAL' ) )
	
	AND SOLICITUD_GLOBAL_POSTULACION.solFechaRadicacion <= @FECHA_FINAL
	AND DATEDIFF(YEAR, PERSONA_DET.pedFechaNacimiento, SOLICITUD_ASIGNACION.safFechaAceptacion) >= 18
	
GROUP BY
	PARAMETRO.prmValor,
	PERSONA.perNumeroIdentificacion,
	PERSONA.perPrimerApellido, 
	PERSONA.perSegundoApellido,
	PERSONA.perPrimerNombre, 
	PERSONA.perSegundoNombre,
	PARAMETRO_NOMBRE.prmValor,
	SOLICITUD_ASIGNACION.safFechaAceptacion,
	POSTULACION.pofValorAsignadoSFV,
	POSTULACION.pofIdentificardorDocumentoActaAsignacion,
	POSTULACION.pofId,
	PERSONA.perTipoIdentificacion,
	PERSONA_DET.pedEstadoCivil,
	JsonPostulacion.ESTADO_CIVIL,
	POSTULACION.pofModalidad,
	POSTULACION.pofEstadoHogar,
	SOLICITUD_GLOBAL_POSTULACION.solNumeroRadicacion,
	LEGALIZACION.sldEstadoSolicitud,
	LEGALIZACION.sldFechaOperacion,
	INTEGRANTE.inhTipoIntegrante,
	POSTULACION.pofHogarPerdioSubsidioNoPago,
	POSTULACION.pofMotivoRestitucion,
	PARAMETRIZACION_NOV.novTipoTransaccion,
	ACTA_FOVIS.aafNumeroOficio,
	ACTA_FOVIS.aafNumeroResolucion
	--SOLICITUD_GLOBAL_POSTULACION.solFechaRadicacion
	,POSTULACION.pofEstadoHogar,
	postulacion.pofValorSFVAjustado


	--select '#TEMP_AfiliadosTitulares',* from #TEMP_AfiliadosTitulares
        
	SELECT DISTINCT * FROM (			 

------- INTEGRANTES HOGAR DE LAS POSTULACION
	SELECT

----------NIT Entidad
	REPLACE ( PARAMETRO.prmValor, '-', '' ) AS [NIT Entidad],

---------ID HOGAR
	convert(numeric,POFact.[ID de Hogar]) AS [ID de Hogar],

----------Documento Beneficiario
	PERSONA.perNumeroIdentificacion AS [Documento beneficiario],

----------Apellidos del Beneficiario
	CONCAT( PERSONA.perPrimerApellido, ' ', PERSONA.perSegundoApellido ) AS [Apellidos Beneficiario], 

----------Nombres del Beneficiario
	CONCAT( PERSONA.perPrimerNombre, ' ', PERSONA.perSegundoNombre ) AS [Nombres Beneficiario],

----------Nombre Entidad
	PARAMETRO_NOMBRE.prmValor AS [Nombre Entidad],

----------Fecha de asignación
	POFact.[Fecha Asignación] AS [Fecha Asignación],

----------Valor asignación
	--POFact.[Valor Asignación] AS [Valor Asignación],
	0 AS [Valor Asignación],
----------Tipo de vivienda
	POFact.[Tipo de Vivienda] AS [Tipo de Vivienda],

----------Número resolución
	POFact.[Numero de Resolución] AS [Numero de Resolución],

----------Campo en blanco
	NULL AS [Campo en Blanco],

----------Tipo de documento
	CASE
		WHEN PERSONA.perTipoIdentificacion = 'CEDULA_CIUDADANIA'
		THEN '1'
		WHEN PERSONA.perTipoIdentificacion = 'CEDULA_EXTRANJERIA'
		THEN '2'
		WHEN PERSONA.perTipoIdentificacion = 'TARJETA_IDENTIDAD'
		THEN '7'

		--ELSE PERSONA.perTipoIdentificacion
	
	END	AS [Tipo Documento],

----------Estado Civil
	 
			CASE 
				WHEN PERSONA_DET.pedEstadoCivil = 'SOLTERO'
				THEN '1'
				WHEN PERSONA_DET.pedEstadoCivil IN ( 'UNION_LIBRE', 'CASADO' )
				THEN '2'
				WHEN PERSONA_DET.pedEstadoCivil IN ( 'SEPARADO', 'VIUDO', 'DIVORCIADO' )
				THEN '3'
				ELSE PERSONA_DET.pedEstadoCivil
			--END
	END AS [Estado Civil],

----------ID Parentesco
	CASE 
		WHEN INTEGRANTE.inhTipoIntegrante IN( 'CONYUGE', 'CONYUGE_HOGAR' ) 
		THEN '2'
		WHEN INTEGRANTE.inhTipoIntegrante IN( 'HIJO_BIOLOGICO', 'HIJO_BIOLOGICO_HOGAR' ) 
		THEN '3'
		WHEN INTEGRANTE.inhTipoIntegrante IN( 'HERMANO', 'HERMANO_HOGAR' ) 
		THEN '4'
		WHEN INTEGRANTE.inhTipoIntegrante IN( 'PADRE_HOGAR', 'MADRE_HOGAR', 'PADRE_MADRE_ADOPTANTE_HOGAR', 'MADRE', 'PADRE' ) 
		THEN '5'
		WHEN INTEGRANTE.inhTipoIntegrante IN( 'ABUELO', 'ABUELO_HOGAR', 'ABUELA', 'ABUELA_HOGAR', 'NIETO', 'NIETO_HOGAR', 'NIETA', 'NIETA_HOGAR' ) 
		THEN '6'
		WHEN INTEGRANTE.inhTipoIntegrante IN( 'BISNIETO','BISNIETO_HOGAR', 'BISNIETA', 'BISNIETA_HOGAR', 'BISABUELO','BISABUELO_HOGAR', 'BISABUELA', 'BISABUELA_HOGAR', 'SOBRINO', 'SOBRINA', 'SOBRINO_HOGAR', 'SOBRINA_HOGAR', 'TIO', 'TIO_HOGAR', 'TIA', 'TIA_HOGAR' ) 
		THEN '7'
		WHEN INTEGRANTE.inhTipoIntegrante IN( 'SUEGRO', 'SUEGRA', 'SUEGRO_HOGAR', 'SUEGRA_HOGAR', 'CUÑADO', 'CUÑADA', 'CUÑADO_HOGAR', 'CUÑADA_HOGAR' ) 
		THEN '8'
		WHEN INTEGRANTE.inhTipoIntegrante IN( 'HIJO_ADOPTIVO', 'HIJO_ADOPTIVO_HOGAR', 'PADRE_ADOPTIVO', 'PADRE_ADOPTIVO_HOGAR' ) 
		THEN '9'
		WHEN INTEGRANTE.inhTipoIntegrante IN( 'NUERA', 'NUERA_HOGAR', 'YERNO', 'YERNO_HOGAR' ) 
		THEN '10'

		ELSE '11'

	END AS [ID Parentesco],

----------Modalidad Vivienda
	POFact.[Modalidad Vivienda] AS [Modalidad Vivienda],

----------Perdió Vivienda
	POFact.[Perdió Vivienda] AS [Perdió Vivienda],

----------Estado Legalizacion
	POFact.[Estado Legalización] AS [Estado Legalización],

----------Fecha Legalizacion
 CASE WHEN POFact.[Fecha Legalización] =  MAX(POFact.[Fecha Legalización])
						OVER (PARTITION BY   POFact.[ID de Hogar],POFact.[Documento beneficiario]) 
						THEN POFact.[Fecha Legalización] ELSE NULL END AS  [Fecha Legalización]
	 

FROM 
	#TEMP_AfiliadosTitulares POFact

	LEFT JOIN IntegranteHogar AS INTEGRANTE ON INTEGRANTE.inhPostulacionFovis = POFact.pofId

	INNER JOIN Persona AS PERSONA 
		ON INTEGRANTE.inhPersona = PERSONA.perId
		AND PERSONA.perTipoIdentificacion IN ( 'CEDULA_CIUDADANIA', 'CEDULA_EXTRANJERIA')
	INNER JOIN PersonaDetalle AS PERSONA_DET ON PERSONA_DET.pedPersona = PERSONA.perId

	LEFT JOIN Parametro AS PARAMETRO ON PARAMETRO.prmNombre IN ('NUMERO_ID_CCF')
	LEFT JOIN Parametro AS PARAMETRO_NOMBRE ON PARAMETRO_NOMBRE.prmNombre IN ('NOMBRE_CCF')

	LEFT JOIN #ESTADO_CIVIL_TEMP AS JsonPostulacion ON JsonPostulacion.ID_POSTULACION = POFact.pofId

WHERE
	DATEDIFF(YEAR, PERSONA_DET.pedFechaNacimiento, POFact.[Fecha Asignación]) >= 18
 

UNION

SELECT 
----------NIT Entidad
    POFact.[NIT Entidad],

---------ID HOGAR
    POFact.[ID de Hogar] ,

----------Documento Beneficiario
    POFact.[Documento beneficiario],

----------Apellidos del Beneficiario
    POFact.[Apellidos Beneficiario], 

----------Nombres del Beneficiario
    POFact.[Nombres Beneficiario],

----------Nombre Entidad
    POFact.[Nombre Entidad],

----------Fecha de asignación
    POFact.[Fecha Asignación],

----------Valor asignación
    POFact.[Valor Asignación],

----------Tipo de vivienda
    POFact.[Tipo de Vivienda],

----------Número resolución
    POFact.[Numero de Resolución],

----------Campo en blanco
    POFact.[Campo en Blanco],

----------Tipo de documento
    POFact.[Tipo Documento],

----------Estado Civil
    POFact.[Estado Civil],

----------ID Parentesco
    POFact.[ID Parentesco],

----------Modalidad Vivienda
    POFact.[Modalidad Vivienda],

----------Perdió Vivienda
    POFact.[Perdió Vivienda],

----------Estado Legalizacion
    POFact.[Estado Legalización],

----------Fecha Legalizacion
    CASE WHEN POFact.[Fecha Legalización] =  MAX(POFact.[Fecha Legalización])
						OVER (PARTITION BY   POFact.[ID de Hogar],POFact.[Documento beneficiario]) 
						THEN POFact.[Fecha Legalización] ELSE NULL END AS  [Fecha Legalización]

FROM #TEMP_AfiliadosTitulares AS POFact

) X WHERE X.[Fecha Legalización] IS NOT NULL
 
 --- select * from rno.HistoricoAfiliadosBeneficiariosFOVIS
END