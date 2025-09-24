/****** Object:  StoredProcedure [dbo].[USP_GET_EjecucionFOVIS]    Script Date: 2023-08-02 8:23:31 AM ******/
/****** Object:  StoredProcedure [dbo].[USP_GET_EjecucionFOVIS]    Script Date: 2023-07-26 9:09:36 AM ******/
/****** Object:  StoredProcedure [dbo].[USP_GET_EjecucionFOVIS]    Script Date: 2023-07-19 8:45:17 AM ******/
/****** Object:  StoredProcedure [dbo].[USP_GET_EjecucionFOVIS]    Script Date: 02/06/2023 12:26:27 p. m. ******/
/****** Object:  StoredProcedure [dbo].[USP_GET_EjecucionFOVIS]    Script Date: 30/05/2023 10:16:45 a. m. ******/
/****** Object:  StoredProcedure [dbo].[USP_GET_EjecucionFOVIS]    Script Date: 29/05/2023 1:46:44 p. m. ******/
---EXEC [USP_GET_EjecucionFOVIS] '2023-07-01','2023-07-26'

 
CREATE OR ALTER  PROCEDURE [dbo].[USP_GET_EjecucionFOVIS] 
(
	@FechaInicial DATE,
	@FechaFinal DATE
) AS 
 BEGIN


 SET ANSI_NULLS ON
 SET QUOTED_IDENTIFIER ON
 SET  NOCOUNT ON 


				DROP TABLE IF exists #Comun
				SELECT
					pof.pofId
					,per.perNumeroIdentificacion
					,pof.pofModalidad
					,aaf.aafFechaOficio as FechaOficio
					,CASE
						WHEN pof.pofModalidad like '%URBAN%' THEN 'URBANO'
						WHEN pof.pofModalidad like '%RURAL%' THEN 'RURAL'
						WHEN cia.ciaNombre like '%URBAN%' THEN 'URBANO'
						WHEN cia.ciaNombre like '%RURAL%' THEN 'RURAL'
					END as [Sector]
				 INTO #Comun
				 FROM postulacionFovis as pof
		   INNER JOIN SolicitudAsignacion as saf on pof.pofSolicitudAsignacion = saf.safId
		   INNER JOIN ActaAsignacionFovis as aaf on aaf.aafSolicitudAsignacion = saf.safId
		   INNER JOIN CicloAsignacion as cia on cia.ciaId = pof.pofCicloAsignacion
		   INNER JOIN JefeHogar as jeh on pof.pofJefeHogar = jeh.jehId
		   INNER JOIN Afiliado as afi on jeh.jehAfiliado = afi.afiId
		   INNER JOIN Persona as per on per.perId = afi.afiPersona
				WHERE pof.pofEstadoHogar <> 'HABIL'
				  AND aaf.aafFechaOficio BETWEEN  @FechaInicial AND @fechafinal

 



 				DROP TABLE IF exists #Comunre
				SELECT distinct
					pof.pofId
					,per.perNumeroIdentificacion
					,pof.pofModalidad
					,convert(date,solFechaRadicacion) as FechaOficio
					,CASE
						WHEN pof.pofModalidad like '%URBAN%' THEN 'URBANO'
						WHEN pof.pofModalidad like '%RURAL%' THEN 'RURAL'
						WHEN cia.ciaNombre like '%URBAN%' THEN 'URBANO'
						WHEN cia.ciaNombre like '%RURAL%' THEN 'RURAL'
					END as [Sector]
				 INTO #Comunre
				  FROM PostulacionFOVIS as pof
			INNER JOIN CicloAsignacion as cia on cia.ciaId = pof.pofCicloAsignacion
		    INNER JOIN JefeHogar as jeh on pof.pofJefeHogar = jeh.jehId
		    INNER JOIN Afiliado as afi on jeh.jehAfiliado = afi.afiId
		    INNER JOIN Persona as per on per.perId = afi.afiPersona
		    INNER JOIN SolicitudNovedadPersonaFovis as spf on spf.spfPostulacionFovis = pof.pofId
		    INNER JOIN SolicitudNovedadFovis as snf on snf.snfId = spf.spfSolicitudNovedadFovis
		    INNER JOIN Solicitud as sol on snf.snfSolicitudGlobal = sol.solId
		     LEFT JOIN SolicitudLegalizacionDesembolso as sld on sld.sldPostulacionFOVIS = pof.pofId
		     LEFT JOIN LegalizacionDesembolso as lgd on sld.sldLegalizacionDesembolso = lgd.lgdId
				WHERE pof.pofEstadoHogar <> 'HABIL'
				  AND convert(date,sol.solFechaRadicacion) BETWEEN  @FechaInicial AND @fechafinal

				---  select '#Comunre',* from #Comunre

				DROP TABLE IF exists #Asignaciones
				SELECT distinct
					com.perNumeroIdentificacion as [IdHogar]
					,com.pofModalidad as [Modalidad]
					,com.FechaOficio as [FechaOficio] 
					,pof.pofValorAsignadoSFV as [Valor Movimiento]
					,aaf.aafFechaOficio as [Fecha Movimiento]
					,'ASIGNACION' as [Movimiento]
					,com.Sector as [Sector]
				INTO #Asignaciones
				FROM PostulacionFOVIS as pof
		INNER JOIN SolicitudAsignacion as saf on pof.pofSolicitudAsignacion = saf.safId
		INNER JOIN Solicitud as sol on saf.safSolicitudGlobal = sol.solId
		left join ActaAsignacionFovis as aaf on aaf.aafSolicitudAsignacion = saf.safId
		INNER JOIN #Comun as com on com.pofId = pof.pofId
				WHERE saf.safEstadoSolicitudAsignacion IN ('CERRADA')
				  AND pofEstadoHogar <> 'HABIL'
		

	 


				DROP TABLE IF exists #Reintegros
				SELECT DISTINCT
					com.perNumeroIdentificacion
					,com.pofModalidad
					,com.FechaOficio 
					,lgd.lgdMontoDesembolsado
					,sol.solFechaRadicacion
					,'REINTEGRO' as movimiento
					,com.Sector
				INTO #Reintegros
				FROM PostulacionFOVIS as pof
		  INNER JOIN SolicitudNovedadPersonaFovis as spf on spf.spfPostulacionFovis = pof.pofId
		  INNER JOIN SolicitudNovedadFovis as snf on snf.snfId = spf.spfSolicitudNovedadFovis
		  INNER JOIN Solicitud as sol on snf.snfSolicitudGlobal = sol.solId
		   LEFT JOIN SolicitudLegalizacionDesembolso as sld on sld.sldPostulacionFOVIS = pof.pofId
		   LEFT JOIN LegalizacionDesembolso as lgd on sld.sldLegalizacionDesembolso = lgd.lgdId
		  INNER JOIN #Comunre as com on com.pofId = pof.pofId
				WHERE snf.snfEstadoSolicitud = 'NOV_FOVIS_CERRADA'
					and solTipoTransaccion in 
					('REEMBOLSO_VOLUNTARIO_SUBSIDIO'
					,'RESTITUCION_SUBSIDIO_INCUMPLIMIENTO')

			UNION
				SELECT DISTINCT
					 com.perNumeroIdentificacion
					,com.pofModalidad
					,com.FechaOficio		 
					,ISNULL(pofValorSFVAjustado,pof.pofValorAsignadoSFV) as pofValorAsignadoSFV
					,sol.solFechaRadicacion
					,'REINTEGRO' as movimiento-----RENUNCIAS
					,com.Sector
				FROM PostulacionFOVIS as pof
		  INNER JOIN SolicitudNovedadPersonaFovis as spf on spf.spfPostulacionFovis = pof.pofId
		  INNER JOIN SolicitudNovedadFovis as snf on snf.snfId = spf.spfSolicitudNovedadFovis
		  INNER JOIN Solicitud as sol on snfSolicitudGlobal = sol.solId
		  INNER JOIN #Comunre as com on com.pofId = pof.pofId
			   WHERE sol.solTipoTransaccion = 'RENUNCIO_SUBISIDIO_ASIGNADO'


		UNION
				SELECT DISTINCT
					com.perNumeroIdentificacion
					,com.pofModalidad
					,com.FechaOficio
					,pof.pofValorAsignadoSFV
					,sol.solFechaRadicacion
					,'REINTEGRO' as movimiento---VENCIMIENTO
					,com.Sector
		 
				FROM PostulacionFOVIS as pof
					INNER JOIN SolicitudNovedadPersonaFovis as spf on spf.spfPostulacionFovis = pof.pofId
					INNER JOIN SolicitudNovedadFovis as snf on snf.snfId = spf.spfSolicitudNovedadFovis
					INNER JOIN Solicitud as sol on snfSolicitudGlobal = sol.solId
					INNER JOIN #Comunre as com on com.pofId = pof.pofId
				WHERE  sol.solTipoTransaccion = 'VENCIMIENTO_SUBSIDIOS_ASIGNADOS_AUTOMATICA'

		 --	select '#Reintegros',* from #Reintegros

				DROP TABLE IF EXISTS #Indexados
				SELECT distinct
					com.perNumeroIdentificacion
					,com.pofModalidad
					,com.FechaOficio
					,ISNULL((ISNULL(pof.pofValorSFVAjustado,0) -ISNULL(pof.pofValorAsignadoSFV,0)),0) as pofValorAsignadoSFV
					,sol.solFechaRadicacion
					,'INDEXADO' as movimiento
					,com.Sector
				into #Indexados
				FROM PostulacionFOVIS as pof
		  INNER JOIN SolicitudNovedadPersonaFovis as spf on spf.spfPostulacionFovis = pof.pofId
		  INNER JOIN SolicitudNovedadFovis as snf on snf.snfId = spf.spfSolicitudNovedadFovis
		  INNER JOIN Solicitud as sol on snfSolicitudGlobal = sol.solId
		  INNER JOIN #Comunre as com on com.pofId = pof.pofId
				WHERE solTipoTransaccion = 'AJUSTE_ACTUALIZACION_VALOR_SFV_133_2018'
				  AND snfEstadoSolicitud IN ('NOV_FOVIS_CERRADA','NOV_FOVIS_APROBADA')
				  AND pofValorSFVAjustado>0

			--	  	SELECT '#Indexados',* FROM #Indexados

				DROP TABLE IF EXISTS #Final
				SELECT *
				INTO #Final
				FROM #Asignaciones AS a		 
				UNION
				SELECT * FROM #Reintegros	 
				UNION
				SELECT * FROM #Indexados
	
	-- select * from #Final
	----cantidades
				SELECT CASE Sector
				       WHEN 'URBANO' THEN '1'
				       WHEN 'RURAL' THEN '2' END AS [Componente de vivienda] ,
					   CASE movimiento WHEN 'ASIGNACION' THEN '19'
					   WHEN 'REINTEGRO' THEN '23'
					   WHEN 'INDEXADO' THEN '22' END AS
					   [Código del concepto del subsidio familiar de vivienda],
					   CONVERT(VARCHAR,COUNT(DISTINCT [IdHogar]),1) AS [Valor o cantidad del Concepto] 
                 
			       FROM #Final
				  GROUP BY Sector,movimiento
			--	  WHERE [FechaOficio] BETWEEN  @fechainicio AND @fechafinal
			 	UNION
				--plata
				SELECT CASE Sector
				        WHEN 'URBANO' THEN '1'
				        WHEN 'RURAL' THEN '2' END AS [Componente de vivienda] ,
					   CASE movimiento WHEN 'ASIGNACION' THEN '15'
					   WHEN 'REINTEGRO' THEN '12'
					   WHEN 'INDEXADO' THEN '18' END AS
					   [Código del concepto del subsidio familiar de vivienda],
					   REPLACE(CONVERT(VARCHAR,ROUND( SUM([Valor Movimiento]),0),1),'.00000','')  AS [Valor o cantidad del Concepto] 
                  FROM #Final
				   GROUP BY Sector, movimiento
      
 END