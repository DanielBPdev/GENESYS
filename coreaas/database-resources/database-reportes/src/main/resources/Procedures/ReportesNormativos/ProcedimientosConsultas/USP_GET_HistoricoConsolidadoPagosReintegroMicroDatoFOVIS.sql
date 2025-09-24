/****** Object:  StoredProcedure [dbo].[USP_GET_HistoricoConsolidadoPagosReintegroMicroDatoFOVIS]    Script Date: 2023-08-17 10:24:57 AM ******/
/****** Object:  StoredProcedure [dbo].[USP_GET_HistoricoConsolidadoPagosReintegroMicroDatoFOVIS]    Script Date: 14/04/2023 9:08:21 a. m. ******/
/****** Object:  StoredProcedure [dbo].[USP_GET_HistoricoConsolidadoPagosReintegroMicroDatoFOVIS]    Script Date: 12/04/2023 9:13:17 a. m. ******/
/****** Object:  StoredProcedure [dbo].[USP_GET_HistoricoConsolidadoPagosReintegroMicroDatoFOVIS]    Script Date: 31/03/2023 6:09:12 p. m. ******/
/****** Object:  StoredProcedure [dbo].[USP_GET_HistoricoConsolidadoPagosReintegroMicroDatoFOVIS]    Script Date: 24/03/2023 8:59:20 a. m. ******/
/****** Object:  StoredProcedure [dbo].[USP_GET_HistoricoConsolidadoPagosReintegroMicroDatoFOVIS]    Script Date: 22/03/2023 8:12:26 a. m. ******/
/****** Object:  StoredProcedure [dbo].[USP_GET_HistoricoConsolidadoPagosReintegroMicroDatoFOVIS]    Script Date: 21/03/2023 11:29:37 a. m. ******/
/****** Object:  StoredProcedure [dbo].[USP_GET_HistoricoConsolidadoPagosReintegroMicroDatoFOVIS]    Script Date: 21/03/2023 11:28:09 a. m. ******/
/****** Object:  StoredProcedure [dbo].[USP_GET_HistoricoConsolidadoPagosReintegroMicroDatoFOVIS]    Script Date: 16/03/2023 5:20:34 p. m. ******/
-- =============================================
-- Author:		Fabian López
-- Modificado:  Olga Vega
-- Fecha de Modificación:2023-03-22
-- Create date: 2020/03/06
-- Description:	Datos para reporte HistoricoConsolidadoPagosReintegroMicroDatoFOVIS
---EXEC USP_GET_HistoricoConsolidadoPagosReintegroMicroDatoFOVIS '2023-07-01','2023-08-17', 0
---REPORTE 29
-- =============================================
create or ALTER     PROCEDURE [dbo].[USP_GET_HistoricoConsolidadoPagosReintegroMicroDatoFOVIS]
(
	@fechaInicio DATE,
	@fechaFin DATE,
	@historico BIT = NULL
)
AS
BEGIN TRY
	SET NOCOUNT ON;

	 
 
 
 -------------------*****************************NO ASIGNADOS Y NO DESEMBOLSADOS*******************-----------------------
			
			 BEGIN
			
			DELETE rno.HistoricoConsolidadoPagosReintegroMicroDatoFOVIS 
			-- WHERE hcmFechaInicialReporte = @fechaInicio 
			  -- AND hcmFechaFinalReporte = @fechaFin



	    DROP TABLE IF EXISTS #Comun
		SELECT
			pof.pofId
			,per.perNumeroIdentificacion
			, pof.pofModalidad
			,year(aaf.aafFechaOficio) as [aafFechaOficioAño]---cambio 20230321 de aafFechaOficio por aafFechaActaAsignacionFovis
			,month(aaf.aafFechaOficio) as aafFechaOficioMes
			,case
				when pof.pofModalidad like '%URBAN%' then 'URBANO'
				when  pof.pofModalidad like '%RURAL%' then 'RURAL'
				when cia.ciaNombre like '%URBAN%' then 'URBANO'
				when cia.ciaNombre like '%RURAL%' then 'RURAL'
			end as [Sector], pofJefeHogar, jehAfiliado, per.perid as peridjefe, safFechaAceptacion, aafFinVigencia
		INTO #Comun
		FROM postulacionFovis as pof
			LEFT JOIN SolicitudAsignacion as saf on pof.pofSolicitudAsignacion = saf.safId
			LEFT JOIN ActaAsignacionFovis as aaf on aaf.aafSolicitudAsignacion = saf.safId
			INNER JOIN CicloAsignacion as cia on cia.ciaId = pof.pofCicloAsignacion
			INNER JOIN JefeHogar as jeh on pof.pofJefeHogar = jeh.jehId
			INNER JOIN Afiliado as afi on jeh.jehAfiliado = afi.afiId
			INNER JOIN Persona as per on per.perId = afi.afiPersona
		WHERE pof.pofEstadoHogar <> 'HABIL'


 	 INSERT INTO rno.HistoricoConsolidadoPagosReintegroMicroDatoFOVIS --PRUEBA

			 


 ----******************************************DESEMBOLSOS**************************---------------

  
 SELECT GETDATE() AS Fechahistorico,tipoIdentificacion,numeroIdentificacion,anioVigencia,tipoPlanVivienda, estadoSubsidio, SUM(valorSubsidio) AS valorSubsidio,@fechaInicio, @fechaFin, pofModalidad
   FROM
 	(SELECT DISTINCT
				 
						CASE per.pertipoidentificacion
						WHEN 'CEDULA_CIUDADANIA' THEN '1'
						WHEN 'TARJETA_IDENTIDAD' THEN '2'
						WHEN 'REGISTRO_CIVIL' THEN '3'
						WHEN 'CEDULA_EXTRANJERIA' THEN '4'	
						WHEN 'NUIP' THEN '5'				
						WHEN 'PASAPORTE' THEN '6'
						WHEN 'NIT' THEN '7'
						WHEN 'CARNE_DIPLOMATICO' THEN '8' 
						WHEN 'PERM_ESP_PERMANENCIA' THEN '9' 
						WHEN 'PERM_PROT_TEMPORAL' THEN '15'  
						END AS tipoIdentificacion,
						per.perNumeroIdentificacion AS numeroIdentificacion,
						com.[aafFechaOficioAño] AS anioVigencia,
						CASE 
							WHEN pof.pofModalidad IN ('ADQUISICION_VIVIENDA_NUEVA_RURAL','ADQUISICION_VIVIENDA_NUEVA_URBANA')
							THEN '1' 
							WHEN pof.pofModalidad IN ('ADQUISICION_VIVIENDA_USADA_RURAL','ADQUISICION_VIVIENDA_USADA_URBANA')
							THEN '2'
							WHEN pof.pofModalidad IN ('CONSTRUCCION_SITIO_PROPIO_RURAL','CONSTRUCCION_SITIO_PROPIO_URBANO')
							THEN '3'
							WHEN pof.pofModalidad IN ('MEJORAMIENTO_VIVIENDA_RURAL','MEJORAMIENTO_VIVIENDA_URBANA')
							THEN '4'
							WHEN pof.pofModalidad IN ('MEJORAMIENTO_VIVIENDA_SALUDABLE')
							THEN '7'
						END AS tipoPlanVivienda,
					 '6' AS estadoSubsidio,	
					 lgdMontoDesembolsado  AS valorSubsidio ,solFechaRadicacion,pof.pofModalidad
			 	    FROM PostulacionFOVIS as pof
			INNER JOIN JefeHogar jeh ON (jeh.jehId =pof.pofJefeHogar)
		    INNER JOIN Afiliado afi ON (afi.afiId =jeh.jehAfiliado)
		    INNER JOIN Persona per ON (per.perId = afi.afiPersona)
			INNER JOIN SolicitudLegalizacionDesembolso as sld on sld.sldPostulacionFOVIS = pof.pofId
			INNER JOIN LegalizacionDesembolso as lgd on sld.sldLegalizacionDesembolso = lgd.lgdId
			INNER JOIN Solicitud as sol on sld.sldSolicitudGlobal = sol.solId
			INNER JOIN #Comun as com on com.pofId = pof.pofId
		         WHERE sld.sldEstadoSolicitud in ('LEGALIZACION_Y_DESEMBOLSO_CERRADO')
		           AND lgd.lgdFechaTransferencia BETWEEN @fechaInicio AND @fechaFin
			
			) AS DESEMBOLSOS
	 GROUP BY  tipoIdentificacion,numeroIdentificacion,anioVigencia,estadoSubsidio  ,tipoPlanVivienda,  pofModalidad
	 
UNION ALL
	------****************************************ASIGNADOS****************************************------

				      SELECT GETDATE() AS Fechahistorico,
					     CASE per.pertipoidentificacion
						WHEN 'CEDULA_CIUDADANIA' THEN '1'
						WHEN 'TARJETA_IDENTIDAD' THEN '2'
						WHEN 'REGISTRO_CIVIL' THEN '3'
						WHEN 'CEDULA_EXTRANJERIA' THEN '4'	
						WHEN 'NUIP' THEN '5'				
						WHEN 'PASAPORTE' THEN '6'
						WHEN 'NIT' THEN '7'
						WHEN 'CARNE_DIPLOMATICO' THEN '8' 
						WHEN 'PERM_ESP_PERMANENCIA' THEN '9' 
						WHEN 'PERM_PROT_TEMPORAL' THEN '15'  
						END AS tipoIdentificacion,
						per.perNumeroIdentificacion AS numeroIdentificacion,
						com.[aafFechaOficioAño] AS anioVigencia,
						CASE 
							WHEN pof.pofModalidad IN ('ADQUISICION_VIVIENDA_NUEVA_RURAL','ADQUISICION_VIVIENDA_NUEVA_URBANA')
							THEN '1' 
							WHEN pof.pofModalidad IN ('ADQUISICION_VIVIENDA_USADA_RURAL','ADQUISICION_VIVIENDA_USADA_URBANA')
							THEN '2'
							WHEN pof.pofModalidad IN ('CONSTRUCCION_SITIO_PROPIO_RURAL','CONSTRUCCION_SITIO_PROPIO_URBANO')
							THEN '3'
							WHEN pof.pofModalidad IN ('MEJORAMIENTO_VIVIENDA_RURAL','MEJORAMIENTO_VIVIENDA_URBANA')
							THEN '4'
							WHEN pof.pofModalidad IN ('MEJORAMIENTO_VIVIENDA_SALUDABLE')
							THEN '7'
						END AS tipoPlanVivienda,
         				'1' AS estadoSubsidio,	
						CASE 
							WHEN ISNULL(pof.pofValorAjusteIPCSFV,0)>0 THEN pof.pofValorAjusteIPCSFV
							ELSE  pof.pofValorAsignadoSFV
						END AS valorSubsidio ,@fechaInicio, @fechaFin,pof.pofModalidad
				   FROM PostulacionFOVIS as pof
	 	     INNER JOIN JefeHogar jeh ON (jeh.jehId =pof.pofJefeHogar)
		     INNER JOIN Afiliado afi ON (afi.afiId =jeh.jehAfiliado)
		     INNER JOIN Persona per ON (per.perId = afi.afiPersona)
			 INNER JOIN SolicitudAsignacion as saf on pof.pofSolicitudAsignacion = saf.safId
			 INNER JOIN Solicitud as sol on saf.safSolicitudGlobal = sol.solId
			  LEFT JOIN ActaAsignacionFovis as aaf on aaf.aafSolicitudAsignacion = saf.safId
			 INNER JOIN #Comun as com on com.pofId = pof.pofId
				  WHERE saf.safEstadoSolicitudAsignacion in ('CERRADA')
					AND pofEstadoHogar <> 'HABIL'
					AND aaf.aafFechaOficio BETWEEN @fechaInicio AND @fechaFin
				 


UNION ALL


------****************************************INDEXADOS****************************************------

				      SELECT GETDATE() AS Fechahistorico,
					     CASE per.pertipoidentificacion
						WHEN 'CEDULA_CIUDADANIA' THEN '1'
						WHEN 'TARJETA_IDENTIDAD' THEN '2'
						WHEN 'REGISTRO_CIVIL' THEN '3'
						WHEN 'CEDULA_EXTRANJERIA' THEN '4'	
						WHEN 'NUIP' THEN '5'				
						WHEN 'PASAPORTE' THEN '6'
						WHEN 'NIT' THEN '7'
						WHEN 'CARNE_DIPLOMATICO' THEN '8' 
						WHEN 'PERM_ESP_PERMANENCIA' THEN '9' 
						WHEN 'PERM_PROT_TEMPORAL' THEN '15'  
						END AS tipoIdentificacion,
						per.perNumeroIdentificacion AS numeroIdentificacion,
						com.[aafFechaOficioAño] AS anioVigencia,
						CASE 
							WHEN pof.pofModalidad IN ('ADQUISICION_VIVIENDA_NUEVA_RURAL','ADQUISICION_VIVIENDA_NUEVA_URBANA')
							THEN '1' 
							WHEN pof.pofModalidad IN ('ADQUISICION_VIVIENDA_USADA_RURAL','ADQUISICION_VIVIENDA_USADA_URBANA')
							THEN '2'
							WHEN pof.pofModalidad IN ('CONSTRUCCION_SITIO_PROPIO_RURAL','CONSTRUCCION_SITIO_PROPIO_URBANO')
							THEN '3'
							WHEN pof.pofModalidad IN ('MEJORAMIENTO_VIVIENDA_RURAL','MEJORAMIENTO_VIVIENDA_URBANA')
							THEN '4'
							WHEN pof.pofModalidad IN ('MEJORAMIENTO_VIVIENDA_SALUDABLE')
							THEN '7'
						END AS tipoPlanVivienda,
         				'5' AS estadoSubsidio,	
						(pofValorSFVAjustado-pofValorAsignadoSFV) AS valorSubsidio ,@fechaInicio, @fechaFin, pof.pofModalidad
 
		          FROM PostulacionFOVIS as pof
		    INNER JOIN JefeHogar jeh ON (jeh.jehId =pof.pofJefeHogar)
		    INNER JOIN Afiliado afi ON (afi.afiId =jeh.jehAfiliado)
		    INNER JOIN Persona per ON (per.perId = afi.afiPersona)
			INNER JOIN SolicitudNovedadPersonaFovis as spf on spf.spfPostulacionFovis = pof.pofId
			INNER JOIN SolicitudNovedadFovis as snf on snf.snfId = spf.spfSolicitudNovedadFovis
			INNER JOIN Solicitud as sol on snfSolicitudGlobal = sol.solId
			INNER JOIN #Comun as com on com.pofId = pof.pofId
		WHERE solTipoTransaccion = 'AJUSTE_ACTUALIZACION_VALOR_SFV_133_2018'
		  and snfEstadoSolicitud IN ('NOV_FOVIS_CERRADA','NOV_FOVIS_APROBADA')
		  and pofValorSFVAjustado>0
  		  AND sol.solFechaRadicacion  BETWEEN @fechaInicio AND @fechaFin
	  

UNION ALL
	
	


	------****************************************REINTEGRO****************************************------

				      SELECT GETDATE() AS Fechahistorico,
					     CASE per.pertipoidentificacion
						WHEN 'CEDULA_CIUDADANIA' THEN '1'
						WHEN 'TARJETA_IDENTIDAD' THEN '2'
						WHEN 'REGISTRO_CIVIL' THEN '3'
						WHEN 'CEDULA_EXTRANJERIA' THEN '4'	
						WHEN 'NUIP' THEN '5'				
						WHEN 'PASAPORTE' THEN '6'
						WHEN 'NIT' THEN '7'
						WHEN 'CARNE_DIPLOMATICO' THEN '8' 
						WHEN 'PERM_ESP_PERMANENCIA' THEN '9' 
						WHEN 'PERM_PROT_TEMPORAL' THEN '15'  
						END AS tipoIdentificacion,
						per.perNumeroIdentificacion AS numeroIdentificacion,
						com.[aafFechaOficioAño] AS anioVigencia,
						CASE 
							WHEN pof.pofModalidad IN ('ADQUISICION_VIVIENDA_NUEVA_RURAL','ADQUISICION_VIVIENDA_NUEVA_URBANA')
							THEN '1' 
							WHEN pof.pofModalidad IN ('ADQUISICION_VIVIENDA_USADA_RURAL','ADQUISICION_VIVIENDA_USADA_URBANA')
							THEN '2'
							WHEN pof.pofModalidad IN ('CONSTRUCCION_SITIO_PROPIO_RURAL','CONSTRUCCION_SITIO_PROPIO_URBANO')
							THEN '3'
							WHEN pof.pofModalidad IN ('MEJORAMIENTO_VIVIENDA_RURAL','MEJORAMIENTO_VIVIENDA_URBANA')
							THEN '4'
							WHEN pof.pofModalidad IN ('MEJORAMIENTO_VIVIENDA_SALUDABLE')
							THEN '7'
						END AS tipoPlanVivienda,
         				'4' AS estadoSubsidio,	
						 lgdMontoDesembolsado AS valorSubsidio ,@fechaInicio, @fechaFin, pof.pofModalidad
				  FROM PostulacionFOVIS as pof
			INNER JOIN JefeHogar jeh ON (jeh.jehId =pof.pofJefeHogar)
		    INNER JOIN Afiliado afi ON (afi.afiId =jeh.jehAfiliado)
		    INNER JOIN Persona per ON (per.perId = afi.afiPersona)
			INNER JOIN SolicitudNovedadPersonaFovis as spf on spf.spfPostulacionFovis = pof.pofId
			INNER JOIN SolicitudNovedadFovis as snf on snf.snfId = spf.spfSolicitudNovedadFovis
			INNER JOIN Solicitud as sol on snf.snfSolicitudGlobal = sol.solId
			 LEFT JOIN SolicitudLegalizacionDesembolso as sld on sld.sldPostulacionFOVIS = pof.pofId
			 LEFT JOIN LegalizacionDesembolso as lgd on sld.sldLegalizacionDesembolso = lgd.lgdId
			INNER JOIN #Comun as com on com.pofId = pof.pofId
		         WHERE snf.snfEstadoSolicitud = 'NOV_FOVIS_CERRADA'
			       AND solTipoTransaccion IN ('REEMBOLSO_VOLUNTARIO_SUBSIDIO','RESTITUCION_SUBSIDIO_INCUMPLIMIENTO')
		           AND solFechaRadicacion BETWEEN @fechaInicio AND @fechaFin


 		UNION ALL


		------****************************************RENUNCIA****************************************------

				      SELECT GETDATE() AS Fechahistorico,
					     CASE per.pertipoidentificacion
						WHEN 'CEDULA_CIUDADANIA' THEN '1'
						WHEN 'TARJETA_IDENTIDAD' THEN '2'
						WHEN 'REGISTRO_CIVIL' THEN '3'
						WHEN 'CEDULA_EXTRANJERIA' THEN '4'	
						WHEN 'NUIP' THEN '5'				
						WHEN 'PASAPORTE' THEN '6'
						WHEN 'NIT' THEN '7'
						WHEN 'CARNE_DIPLOMATICO' THEN '8' 
						WHEN 'PERM_ESP_PERMANENCIA' THEN '9' 
						WHEN 'PERM_PROT_TEMPORAL' THEN '15'  
						END AS tipoIdentificacion,
						per.perNumeroIdentificacion AS numeroIdentificacion,
						com.[aafFechaOficioAño] AS anioVigencia,
						CASE 
							WHEN pof.pofModalidad IN ('ADQUISICION_VIVIENDA_NUEVA_RURAL','ADQUISICION_VIVIENDA_NUEVA_URBANA')
							THEN '1' 
							WHEN pof.pofModalidad IN ('ADQUISICION_VIVIENDA_USADA_RURAL','ADQUISICION_VIVIENDA_USADA_URBANA')
							THEN '2'
							WHEN pof.pofModalidad IN ('CONSTRUCCION_SITIO_PROPIO_RURAL','CONSTRUCCION_SITIO_PROPIO_URBANO')
							THEN '3'
							WHEN pof.pofModalidad IN ('MEJORAMIENTO_VIVIENDA_RURAL','MEJORAMIENTO_VIVIENDA_URBANA')
							THEN '4'
							WHEN pof.pofModalidad IN ('MEJORAMIENTO_VIVIENDA_SALUDABLE')
							THEN '7'
						END AS tipoPlanVivienda,
         				'2' AS estadoSubsidio,	
						isnull(pof.pofvalorSFVAjustado, pofValorSFVSolicitado) AS valorSubsidio ,@fechaInicio, @fechaFin, pof.pofModalidad
				from PostulacionFOVIS as pof
			INNER JOIN JefeHogar jeh ON (jeh.jehId =pof.pofJefeHogar)
		    INNER JOIN Afiliado afi ON (afi.afiId =jeh.jehAfiliado)
		    INNER JOIN Persona per ON (per.perId = afi.afiPersona)
			INNER JOIN SolicitudNovedadPersonaFovis as spf on spf.spfPostulacionFovis = pof.pofId
			INNER JOIN SolicitudNovedadFovis as snf on snf.snfId = spf.spfSolicitudNovedadFovis
			INNER JOIN Solicitud as sol on snfSolicitudGlobal = sol.solId
			INNER JOIN #Comun as com on com.pofId = pof.pofId
				 WHERE sol.solTipoTransaccion = 'RENUNCIO_SUBISIDIO_ASIGNADO'
				   AND solFechaRadicacion BETWEEN @fechaInicio AND @fechaFin




	UNION ALL


		------****************************************VENCIMIENTOS****************************************------

				      SELECT GETDATE() AS Fechahistorico,
					     CASE per.pertipoidentificacion
						WHEN 'CEDULA_CIUDADANIA' THEN '1'
						WHEN 'TARJETA_IDENTIDAD' THEN '2'
						WHEN 'REGISTRO_CIVIL' THEN '3'
						WHEN 'CEDULA_EXTRANJERIA' THEN '4'	
						WHEN 'NUIP' THEN '5'				
						WHEN 'PASAPORTE' THEN '6'
						WHEN 'NIT' THEN '7'
						WHEN 'CARNE_DIPLOMATICO' THEN '8' 
						WHEN 'PERM_ESP_PERMANENCIA' THEN '9' 
						WHEN 'PERM_PROT_TEMPORAL' THEN '15'  
						END AS tipoIdentificacion,
						per.perNumeroIdentificacion AS numeroIdentificacion,
						com.[aafFechaOficioAño] AS anioVigencia,
						CASE 
							WHEN pof.pofModalidad IN ('ADQUISICION_VIVIENDA_NUEVA_RURAL','ADQUISICION_VIVIENDA_NUEVA_URBANA')
							THEN '1' 
							WHEN pof.pofModalidad IN ('ADQUISICION_VIVIENDA_USADA_RURAL','ADQUISICION_VIVIENDA_USADA_URBANA')
							THEN '2'
							WHEN pof.pofModalidad IN ('CONSTRUCCION_SITIO_PROPIO_RURAL','CONSTRUCCION_SITIO_PROPIO_URBANO')
							THEN '3'
							WHEN pof.pofModalidad IN ('MEJORAMIENTO_VIVIENDA_RURAL','MEJORAMIENTO_VIVIENDA_URBANA')
							THEN '4'
							WHEN pof.pofModalidad IN ('MEJORAMIENTO_VIVIENDA_SALUDABLE')
							THEN '7'
						END AS tipoPlanVivienda,
         				'3' AS estadoSubsidio,	
						pofValorAsignadoSFV AS valorSubsidio ,@fechaInicio, @fechaFin, pof.pofModalidad
				   FROM PostulacionFOVIS as pof
			INNER JOIN JefeHogar jeh ON (jeh.jehId =pof.pofJefeHogar)
		    INNER JOIN Afiliado afi ON (afi.afiId =jeh.jehAfiliado)
		    INNER JOIN Persona per ON (per.perId = afi.afiPersona)
			INNER JOIN SolicitudNovedadPersonaFovis as spf on spf.spfPostulacionFovis = pof.pofId
			INNER JOIN SolicitudNovedadFovis as snf on snf.snfId = spf.spfSolicitudNovedadFovis
			INNER JOIN Solicitud as sol on snfSolicitudGlobal = sol.solId
			INNER JOIN #Comun as com on com.pofId = pof.pofId
		WHERE  sol.solTipoTransaccion = 'VENCIMIENTO_SUBSIDIOS_ASIGNADOS_AUTOMATICA'
		AND sol.solFechaRadicacion BETWEEN @fechaInicio AND @fechaFin

END	 
	 
	  

	 
	-- ALTER TABLE rno.HistoricoConsolidadoPagosReintegroMicroDatoFOVIS ADD Modalidad NVARCHAR(150)
			 SELECT hcmTipoIdentificacion AS 'Tipo de  Identificación',
					hcmNumeroIdentificacion AS 'Número de  Identificación',
					hcmAnioVigenciaAsignacionSubsidio AS 'Año vigencia  de Asignación del subsidio',
					hcmCodigoTipoPlanVivienda AS 'Código tipo plan de vivienda',
					hcmEstadoSubsidio AS 'Estado del Subsidio',
					hcmValorSubsidios AS 'Valor del Subsidio'
			  FROM  rno.HistoricoConsolidadoPagosReintegroMicroDatoFOVIS 
			 WHERE hcmFechaInicialReporte = @fechaInicio 
			   AND hcmFechaFinalReporte = @fechaFin

			   DROP TABLE  #Comun
END TRY
BEGIN CATCH
	THROW;
END CATCH;