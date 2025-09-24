
---- ====================================================================================================
---- Author: VILLAMARIN JULIAN
---- Create date: MAYO 10 DE 2021
---- Description: Extrae la informacion de los movimientos contables para la integracion de FOVIS 
---- Modificacion: Yesika Bernal adicion de nuevos movimientos a ejecutar 
---- =====================================================================================================
CREATE OR ALTER PROCEDURE [sap].[USP_EjecutarIntegracionFovis] AS

select 1
BEGIN

SET NOCOUNT ON;
BEGIN TRANSACTION
----- insercion en tabla de log para ejecucion del procedimiento. 
	Declare @idlog int, @fechahorainicio datetime, @fechahorafinal datetime
	Insert Into core.sap.IC_LogEjecucion ([Integracion],[fechahorainicio],[fechahorafinal]) VALUES ('FOVIS',GETDATE() -'05:00:00' ,GETDATE() -'05:00:00' )
	set  @idlog = SCOPE_IDENTITY()
	set @fechahorainicio = dbo.GetLocalDate()

	IF ((SELECT COUNT(*) FROM sap.ejecucion_FOVIS WITH (NOLOCK)) = 0) 
	BEGIN
		INSERT sap.ejecucion_FOVIS
		SELECT 1
		

		DECLARE	@perNumeroIdentificacion VARCHAR(20), @perTipoIdentificacion VARCHAR(20), @solNumeroRadicacion VARCHAR(20), @tipo VARCHAR(10), @observaciones VARCHAR(150), @estado BIT, @fechaIngreso DATETIME
		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			-- Busca las solicitudes de Postulacion
		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		SELECT DISTINCT sol.solNumeroRadicacion, 1 as estado, per.perNumeroIdentificacion, per.perTipoIdentificacion, SAP.GetLocalDate() AS fechaIngreso, 'FOVIS' AS tipo, CONCAT (sol.solTipoTransaccion, ' - ', saf.safEstadoSolicitudAsignacion, ' - ', 
		pof.pofEstadoHogar, ' - ',perPrimerNombre, ' - ', perSegundoNombre, ' - ', perPrimerApellido, ' - ', perSegundoApellido) AS observaciones, solFechaRadicacion
		INTO #tmp_filaPersonas_A
		FROM core.dbo.Solicitud sol 
		INNER JOIN core.dbo.SolicitudAsignacion saf WITH (NOLOCK) ON sol.solId = saf.safSolicitudGlobal 
		INNER JOIN core.dbo.PostulacionFOVIS pof WITH (NOLOCK) ON pof.pofSolicitudAsignacion = saf.safId
		INNER JOIN core.dbo.jefehogar jeh WITH (NOLOCK) ON jeh.jehId = pof.pofJefeHogar
		INNER JOIN core.dbo.Afiliado afi WITH (NOLOCK) ON afi.afiId = jeh.jehAfiliado
		INNER JOIN core.dbo.Persona per WITH (NOLOCK) ON per.perId = afi.afiPersona
		LEFT JOIN sap.ContablesCtrl ctr WITH (NOLOCK) ON  sol.solNumeroRadicacion = ctr.solNumeroRadicacion and per.perNumeroIdentificacion = ctr.perNumeroIdentificacion
		and per.perTipoIdentificacion = ctr.perTipoIdentificacion
		WHERE ctr.solNumeroRadicacion IS NULL 	AND sol.solResultadoProceso = 'APROBADA' AND sol.solFechaRadicacion >= '2024-01-01'	AND sol.solNumeroRadicacion LIKE '03%'
		order by solFechaRadicacion

		-- Toma las solicitudes de FOVIS y ejecuta los movimientos contables de FOVIS.

		DECLARE @COUNT BIGINT, @COUNT2  BIGINT
		SET @COUNT = 0
		SET @COUNT2 = (SELECT COUNT(*) FROM #tmp_filaPersonas_A)
		WHILE (@COUNT < @COUNT2)
		BEGIN
			BEGIN TRANSACTION
			
					SELECT TOP 1 @solNumeroRadicacion = solNumeroRadicacion, @estado = MAX(estado), @perNumeroIdentificacion = perNumeroIdentificacion, 
					@perTipoIdentificacion = perTipoIdentificacion, @fechaIngreso = MAX(fechaIngreso), @tipo = tipo, @observaciones = MAX(observaciones)
					FROM #tmp_filaPersonas_A
					GROUP BY solNumeroRadicacion, perNumeroIdentificacion, perTipoIdentificacion, tipo
				
					-- Llamado de los movimientos contables
					EXEC [sap].[USP_GetICFOVIS_Insert_F05] @solNumeroRadicacion, @estado, @perNumeroIdentificacion, @perTipoIdentificacion, @fechaIngreso, @tipo, @observaciones;

				
					DELETE FROM #tmp_filaPersonas_A WHERE solNumeroRadicacion = @solNumeroRadicacion AND estado = @estado AND
					perNumeroIdentificacion = @perNumeroIdentificacion AND perTipoIdentificacion = @perTipoIdentificacion AND fechaIngreso = @fechaIngreso
					AND tipo = @tipo AND observaciones = @observaciones
		
				set @COUNT = @COUNT + 1 
				PRINT @COUNT
			COMMIT TRANSACTION			
		END
			

--		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
--			-- FIN Busca las solicitudes de Postulacion
--		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


--		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
--			-- Busca las solicitudes de renuncia
--		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++						

		SELECT DISTINCT sol.solNumeroRadicacion, 1 as estado, per.perNumeroIdentificacion, per.perTipoIdentificacion, SAP.GetLocalDate() AS fechaIngreso, 'FOVIS' AS tipo, CONCAT (sol.solTipoTransaccion, ' - ',  
		pof.pofEstadoHogar, ' - ',perPrimerNombre, ' - ', perSegundoNombre, ' - ', perPrimerApellido, ' - ', perSegundoApellido) AS observaciones
		INTO #tmp_filaPersonas_R
		FROM core.dbo.Solicitud sol 
		INNER JOIN core.dbo.SolicitudNovedadFovis snf WITH (NOLOCK) ON sol.solid = snf.snfSolicitudGlobal
		INNER JOIN core.dbo.SolicitudNovedadPersonaFovis spf WITH (NOLOCK) ON snf.snfId = spf.spfSolicitudNovedadFovis
		INNER JOIN core.dbo.Persona per WITH (NOLOCK) ON per.perId = spf.spfPersona
		INNER JOIN core.dbo.PostulacionFOVIS pof WITH (NOLOCK) ON pof.pofId = spf.spfPostulacionFovis
		LEFT JOIN sap.ContablesCtrl ctr WITH (NOLOCK) ON  sol.solNumeroRadicacion = ctr.solNumeroRadicacion and per.perNumeroIdentificacion = ctr.perNumeroIdentificacion
		and per.perTipoIdentificacion = ctr.perTipoIdentificacion
		WHERE sol.solFechaRadicacion >=  '2024-01-01'	
		AND ctr.solNumeroRadicacion IS NULL
		AND POF.pofEstadoHogar IN ('SUBSIDIO_REEMBOLSADO','RESTITUIDO_CON_SANCION','HOGAR_DESISTIO_POSTULACION','VENCIMIENTO_POR_ASIGNACION_CON_SEGUNDA_PRORROGA_CADUCADA','VENCIMIENTO_POR_ASIGNACION_SIN_PRORROGA','RESTITUIDO_SIN_SANCION','RENUNCIO_A_SUBSIDIO_ASIGNADO')
		AND sol.solResultadoProceso = 'APROBADA'
		AND sol.solNumeroRadicacion LIKE '03%'
		--and sol.solNumeroRadicacion = '032022368577'		Revisar como queda el estado del hogar

		-- Toma las solicitudes de FOVIS y ejecuta los movimientos contables de FOVIS.
		DECLARE cursor_documentoId CURSOR FOR 
			SELECT solNumeroRadicacion, MAX(estado) AS estado, perNumeroIdentificacion, perTipoIdentificacion, MAX(fechaIngreso) AS fechaIngreso, tipo, MAX(observaciones) AS observaciones
			FROM #tmp_filaPersonas_R 
			GROUP BY solNumeroRadicacion, perNumeroIdentificacion, perTipoIdentificacion, tipo
				
		-- Listar los numeros de radicado asociados	 
		OPEN cursor_documentoId 
		FETCH NEXT FROM cursor_documentoId INTO @solNumeroRadicacion, @estado, @perNumeroIdentificacion, @perTipoIdentificacion, @fechaIngreso, @tipo, @observaciones
	
		WHILE @@FETCH_STATUS = 0 
		BEGIN
			SELECT @solNumeroRadicacion, @estado, @perNumeroIdentificacion, @perTipoIdentificacion, @fechaIngreso, @tipo, @observaciones
		
			-- Llamado de los movimientos contables de FOVIS	
			EXEC [sap].[USP_GetICFOVIS_Insert_F01] @solNumeroRadicacion, @estado, @perNumeroIdentificacion, @perTipoIdentificacion, @fechaIngreso, @tipo, @observaciones;
			EXEC [sap].[USP_GetICFOVIS_Insert_F13] @solNumeroRadicacion, @estado, @perNumeroIdentificacion, @perTipoIdentificacion, @fechaIngreso, @tipo, @observaciones;			--Pendiente de definicion GAP para activar.
			EXEC [sap].[USP_GetICFOVIS_Insert_F14] @solNumeroRadicacion, @estado, @perNumeroIdentificacion, @perTipoIdentificacion, @fechaIngreso, @tipo, @observaciones;			--Pendiente de definicion GAP para activar.
			EXEC [sap].[USP_GetICFOVIS_Insert_F17_F19_F22] @solNumeroRadicacion, @estado, @perNumeroIdentificacion, @perTipoIdentificacion, @fechaIngreso, @tipo, @observaciones;		--Pendiente de definicion GAP para activar.
			EXEC [sap].[USP_GetICFOVIS_Insert_F18_F19_F22] @solNumeroRadicacion, @estado, @perNumeroIdentificacion, @perTipoIdentificacion, @fechaIngreso, @tipo, @observaciones;		--Pendiente de definicion GAP para activar.
		
			FETCH NEXT FROM cursor_documentoId INTO @solNumeroRadicacion, @estado, @perNumeroIdentificacion, @perTipoIdentificacion, @fechaIngreso, @tipo, @observaciones
		END 
		CLOSE cursor_documentoId 
		DEALLOCATE cursor_documentoId

--		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
--			--fIN Busca las solicitudes de renuncia y novedades del jefe de hogar
--		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	

--		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
--			-- Busca las solicitudes de VENCIMIENTO
--		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++						
--		/*
--		SELECT DISTINCT solfechacreacion, sol.solNumeroRadicacion, 1 as estado, per.perNumeroIdentificacion, per.perTipoIdentificacion, SAP.GetLocalDate() AS fechaIngreso, 'FOVIS' AS tipo, CONCAT (sol.solTipoTransaccion, ' - ',  
--		pof.pofEstadoHogar, ' - ',perPrimerNombre, ' - ', perSegundoNombre, ' - ', perPrimerApellido, ' - ', perSegundoApellido) AS observaciones
--		INTO #tmp_filaPersonas_V
--		FROM persona PER
--		INNER JOIN Afiliado AFI WITH (NOLOCK) ON  PER.perId = AFI.afiPersona
--		INNER JOIN core.dbo.jefehogar JEH WITH (NOLOCK) ON AFI.afiId = JEH.jehAfiliado
--		INNER JOIN core.dbo.PostulacionFOVIS POF WITH (NOLOCK) ON JEH.jehId = POF.pofJefeHogar
--		INNER JOIN SolicitudNovedadPersonaFovis SPF WITH (NOLOCK) ON POF.pofId = SPF.spfPostulacionFovis
--		INNER JOIN SolicitudNovedadFovis SNF WITH (NOLOCK) ON SPF.spfSolicitudNovedadFovis = SNF.snfId
--		INNER JOIN Solicitud SOL WITH (NOLOCK) ON SNF.snfSolicitudGlobal = SOL.solId AND Solfechacreacion >= '2023-10-02'
--		WHERE sol.solResultadoProceso = 'APROBADA'
--		AND NOT EXISTS (SELECT solNumeroRadicacion 
--										FROM sap.ContablesCtrl PCTR WITH (NOLOCK) WHERE PCTR.solNumeroRadicacion=SOL.solNumeroRadicacion AND PCTR.perNumeroIdentificacion = PER.perNumeroIdentificacion AND PCTR.perTipoIdentificacion = PER.perTipoIdentificacion )
--		AND POF.pofEstadoHogar IN ('SUBSIDIO_REEMBOLSADO','RESTITUIDO_CON_SANCION','HOGAR_DESISTIO_POSTULACION','VENCIMIENTO_POR_ASIGNACION_CON_SEGUNDA_PRORROGA_CADUCADA','VENCIMIENTO_POR_ASIGNACION_SIN_PRORROGA','RESTITUIDO_SIN_SANCION','RENUNCIO_A_SUBSIDIO_ASIGNADO')
--		AND solNumeroRadicacion LIKE '03%'
		
	
--		-- Toma las solicitudes de FOVIS y ejecuta los movimientos contables de FOVIS.
--		DECLARE cursor_documentoId CURSOR FOR 
--			SELECT solNumeroRadicacion, MAX(estado) AS estado, perNumeroIdentificacion, perTipoIdentificacion, MAX(fechaIngreso) AS fechaIngreso, tipo, MAX(observaciones) AS observaciones
--			FROM #tmp_filaPersonas_V
--			GROUP BY solNumeroRadicacion, perNumeroIdentificacion, perTipoIdentificacion, tipo
				
--		-- Listar los numeros de radicado asociados	 
--		OPEN cursor_documentoId 
--		FETCH NEXT FROM cursor_documentoId INTO @solNumeroRadicacion, @estado, @perNumeroIdentificacion, @perTipoIdentificacion, @fechaIngreso, @tipo, @observaciones
	
--		WHILE @@FETCH_STATUS = 0 
--		BEGIN
--			SELECT @solNumeroRadicacion, @estado, @perNumeroIdentificacion, @perTipoIdentificacion, @fechaIngreso, @tipo, @observaciones
		
--			-- Llamado de los movimientos contables de FOVIS	
--			--EXEC [sap].[USP_GetICFOVIS_Insert_F03] @solNumeroRadicacion, @estado, @perNumeroIdentificacion, @perTipoIdentificacion, @fechaIngreso, @tipo, @observaciones;			--Pendiente dependiente
		
--			FETCH NEXT FROM cursor_documentoId INTO @solNumeroRadicacion, @estado, @perNumeroIdentificacion, @perTipoIdentificacion, @fechaIngreso, @tipo, @observaciones
--		END 
--		CLOSE cursor_documentoId 
--		DEALLOCATE cursor_documentoId
--		*/
--		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
--		--fIN Busca las solicitudes de VENCIMIENTO
--		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	

--		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
--			-- Busca las solicitudes de novedades del jefe de hogar
--		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++						
--/*
--		SELECT DISTINCT solFechaRadicacion, solResultadoProceso,sol.solNumeroRadicacion, 1 as estado, per.perNumeroIdentificacion, per.perTipoIdentificacion, SAP.GetLocalDate() AS fechaIngreso, 'FOVIS' AS tipo, CONCAT (sol.solTipoTransaccion, ' - ',  
--		pof.pofEstadoHogar, ' - ',perPrimerNombre, ' - ', perSegundoNombre, ' - ', perPrimerApellido, ' - ', perSegundoApellido) AS observaciones
--		FROM core.dbo.Solicitud  sol 
--		INNER JOIN core.dbo.SolicitudNovedadFovis solf ON sol.solId = solf.snfSolicitudGlobal
--		INNER JOIN core.dbo.SolicitudNovedadPersonaFovis snf ON solf.snfId =snf.spfSolicitudNovedadFovis
--		INNER JOIN core.dbo.Persona per ON  snf.spfPersona = per.perId
--		INNER JOIN core.dbo.afiliado afi WITH (NOLOCK)  ON per.perId = afi.afiPersona
--		INNER JOIN core.dbo.jefehogar jeh WITH (NOLOCK)  ON afi.afiId = jeh.jehAfiliado
--		INNER JOIN core.dbo.PostulacionFOVIS pof WITH (NOLOCK)  ON jeh.jehId = pof.pofJefeHogar
--		INNER JOIN core.dbo.IntegranteHogar inh WITH (NOLOCK)   ON jeh.jehid = inh.inhJefeHogar
--		INNER JOIN core.dbo.PersonaDetalle ped WITH (NOLOCK)  ON per.perId = ped.pedPersona
--		LEFT JOIN core.dbo.ProyectoSolucionVivienda psv WITH (NOLOCK)  ON pof.pofProyectoSolucionVivienda = psv.psvId
--		LEFT JOIN core.sap.MaestraIdentificacion mi WITH (NOLOCK) ON per.perTipoIdentificacion = mi.tipoIdGenesys
--		LEFT JOIN core.sap.MaestraSector ms WITH (NOLOCK)  ON pof.pofModalidad = ms.tipoSectorGenesys
--		WHERE --perNumeroIdentificacion = '35696005'
--		sol.solFechaRadicacion >= '2023-03-14'
--		AND solResultadoProceso = 'APROBADA'
--		AND sol.solTipoTransaccion LIKE '%CAMBIO_REEMPLAZANTE_JEFE%'
--		AND ped.pedFallecido = 1
--	    AND solNumeroRadicacion LIKE '03%'
--		AND pofEstadoHogar IN ('ASIGNADO_SIN_PRORROGA','ASIGNADO_CON_PRIMERA_PRORROGA', 'ASIGNADO_CON_SEGUNDA_PRORROGA', 'SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO')
--		AND NOT EXISTS (SELECT solNumeroRadicacion 
--										FROM sap.ContablesCtrl PCTR WITH (NOLOCK) WHERE PCTR.solNumeroRadicacion=SOL.solNumeroRadicacion AND PCTR.perNumeroIdentificacion = PER.perNumeroIdentificacion AND PCTR.perTipoIdentificacion = PER.perTipoIdentificacion )
--	*/	

		SELECT DISTINCT sol.solNumeroRadicacion, 1 as estado, per.perNumeroIdentificacion, per.perTipoIdentificacion, SAP.GetLocalDate() AS fechaIngreso, 'FOVIS' AS tipo, CONCAT (sol.solTipoTransaccion, ' - ',  
		pof.pofEstadoHogar, ' - ',perPrimerNombre, ' - ', perSegundoNombre, ' - ', perPrimerApellido, ' - ', perSegundoApellido) AS observaciones
		INTO #tmp_filaPersonas_F
		FROM core.dbo.Solicitud  sol WITH (NOLOCK)
		INNER JOIN core.dbo.SolicitudNovedadFovis solf WITH (NOLOCK) ON sol.solId = solf.snfSolicitudGlobal
		INNER JOIN core.dbo.SolicitudNovedadPersonaFovis snf WITH (NOLOCK) ON solf.snfId =snf.spfSolicitudNovedadFovis
		INNER JOIN core.dbo.Persona per WITH (NOLOCK) ON  snf.spfPersona = per.perId
		INNER JOIN core.dbo.afiliado afi WITH (NOLOCK)  ON per.perId = afi.afiPersona
		INNER JOIN core.dbo.jefehogar jeh WITH (NOLOCK)  ON afi.afiId = jeh.jehAfiliado
		INNER JOIN core.dbo.PostulacionFOVIS pof WITH (NOLOCK)  ON jeh.jehId = pof.pofJefeHogar
		INNER JOIN core.dbo.IntegranteHogar inh WITH (NOLOCK)   ON jeh.jehid = inh.inhJefeHogar
		INNER JOIN core.dbo.PersonaDetalle ped WITH (NOLOCK)  ON per.perId = ped.pedPersona
		LEFT JOIN sap.ContablesCtrl ctr WITH (NOLOCK) ON  sol.solNumeroRadicacion = ctr.solNumeroRadicacion and per.perNumeroIdentificacion = ctr.perNumeroIdentificacion
		and per.perTipoIdentificacion = ctr.perTipoIdentificacion
		WHERE  sol.solFechaRadicacion >=  '2024-01-01'	
		AND ctr.solNumeroRadicacion IS NULL
		AND solResultadoProceso = 'APROBADA'
		AND sol.solTipoTransaccion LIKE '%CAMBIO_REEMPLAZANTE_JEFE%'
		AND ped.pedFallecido = 1
	    AND sol.solNumeroRadicacion LIKE '03%'
		AND pofEstadoHogar IN ('ASIGNADO_SIN_PRORROGA','ASIGNADO_CON_PRIMERA_PRORROGA', 'ASIGNADO_CON_SEGUNDA_PRORROGA', 'SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO')
		 
	
		-- Toma las solicitudes de FOVIS y ejecuta los movimientos contables de FOVIS.
		DECLARE cursor_documentoId CURSOR FOR 
			SELECT solNumeroRadicacion, MAX(estado) AS estado, perNumeroIdentificacion, perTipoIdentificacion, MAX(fechaIngreso) AS fechaIngreso, tipo, MAX(observaciones) AS observaciones
			FROM #tmp_filaPersonas_F 
			GROUP BY solNumeroRadicacion, perNumeroIdentificacion, perTipoIdentificacion, tipo
				
		-- Listar los numeros de radicado asociados	 
		OPEN cursor_documentoId 
		FETCH NEXT FROM cursor_documentoId INTO @solNumeroRadicacion, @estado, @perNumeroIdentificacion, @perTipoIdentificacion, @fechaIngreso, @tipo, @observaciones
	
		WHILE @@FETCH_STATUS = 0 
		BEGIN
			SELECT @solNumeroRadicacion, @estado, @perNumeroIdentificacion, @perTipoIdentificacion, @fechaIngreso, @tipo, @observaciones
		
			-- Llamado de los movimientos contables de FOVIS	
			EXEC [sap].[USP_GetICFOVIS_Insert_F20] @solNumeroRadicacion, @estado, @perNumeroIdentificacion, @perTipoIdentificacion, @fechaIngreso, @tipo, @observaciones;			--Pendiente por aprobacion Comfenalco
		
			FETCH NEXT FROM cursor_documentoId INTO @solNumeroRadicacion, @estado, @perNumeroIdentificacion, @perTipoIdentificacion, @fechaIngreso, @tipo, @observaciones
		END 
		CLOSE cursor_documentoId 
		DEALLOCATE cursor_documentoId
 
--		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
--			--fIN Busca las solicitudes de renuncia y novedades del jefe de hogar
--		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
--		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
--			-- Busca las solicitudes de ajuste de valor sfv
--		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++						

	 SELECT DISTINCT sol.solNumeroRadicacion, 1 as estado, per.perNumeroIdentificacion, per.perTipoIdentificacion, SAP.GetLocalDate() AS fechaIngreso, 'FOVIS' AS tipo, CONCAT (sol.solTipoTransaccion, ' - ',  
		pof.pofEstadoHogar, ' - ',perPrimerNombre, ' - ', perSegundoNombre, ' - ', perPrimerApellido, ' - ', perSegundoApellido) AS observaciones
		INTO #tmp_filaPersonas_S
		FROM core.dbo.Solicitud sol 
		INNER JOIN core.dbo.SolicitudNovedadFovis snf WITH (NOLOCK) ON sol.solid = snf.snfSolicitudGlobal
		INNER JOIN core.dbo.SolicitudNovedadPersonaFovis spf WITH (NOLOCK) ON snf.snfId = spf.spfSolicitudNovedadFovis
		INNER JOIN core.dbo.Persona per WITH (NOLOCK) ON per.perId = spf.spfPersona
		INNER JOIN core.dbo.PostulacionFOVIS pof WITH (NOLOCK) ON pof.pofId = spf.spfPostulacionFovis
		LEFT JOIN sap.ContablesCtrl ctr WITH (NOLOCK) ON  sol.solNumeroRadicacion = ctr.solNumeroRadicacion and per.perNumeroIdentificacion = ctr.perNumeroIdentificacion
		and per.perTipoIdentificacion = ctr.perTipoIdentificacion
		WHERE  sol.solFechaRadicacion >=  '2024-01-01'	
		AND ctr.solNumeroRadicacion IS NULL
		AND POF.pofEstadoHogar IN ('ASIGNADO_SIN_PRORROGA','ASIGNADO_CON_PRIMERA_PRORROGA', 'ASIGNADO_CON_SEGUNDA_PRORROGA')
		AND sol.solTipoTransaccion IN ('AJUSTE_ACTUALIZACION_VALOR_SFV_133_2018' )
		AND sol.solNumeroRadicacion LIKE '03%'
		AND solResultadoProceso ='APROBADA'

		-- Toma las solicitudes de FOVIS y ejecuta los movimientos contables de FOVIS.
		DECLARE cursor_documentoId CURSOR FOR 
			SELECT solNumeroRadicacion, MAX(estado) AS estado, perNumeroIdentificacion, perTipoIdentificacion, MAX(fechaIngreso) AS fechaIngreso, tipo, MAX(observaciones) AS observaciones
			FROM #tmp_filaPersonas_S
			GROUP BY solNumeroRadicacion, perNumeroIdentificacion, perTipoIdentificacion, tipo
				
		-- Listar los numeros de radicado asociados	 
		OPEN cursor_documentoId 
		FETCH NEXT FROM cursor_documentoId INTO @solNumeroRadicacion, @estado, @perNumeroIdentificacion, @perTipoIdentificacion, @fechaIngreso, @tipo, @observaciones
	
		WHILE @@FETCH_STATUS = 0 
		BEGIN
			SELECT @solNumeroRadicacion, @estado, @perNumeroIdentificacion, @perTipoIdentificacion, @fechaIngreso, @tipo, @observaciones
		
			-- Llamado de los movimientos contables de FOVIS	
			EXEC [sap].[USP_GetICFOVIS_Insert_F21] @solNumeroRadicacion, @estado, @perNumeroIdentificacion, @perTipoIdentificacion, @fechaIngreso, @tipo, @observaciones;			--Pendiente por aprobacion Comfenalco
		
			FETCH NEXT FROM cursor_documentoId INTO @solNumeroRadicacion, @estado, @perNumeroIdentificacion, @perTipoIdentificacion, @fechaIngreso, @tipo, @observaciones
		END 
		CLOSE cursor_documentoId 
		DEALLOCATE cursor_documentoId
 
--		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
--			--fIN Busca las solicitudes de ajuste valor sfv 
--		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	

--		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
--			-- Busca las solicitudes de Pago
--		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
  
		SELECT DISTINCT sol.solNumeroRadicacion, 1 as estado, per.perNumeroIdentificacion, per.perTipoIdentificacion, SAP.GetLocalDate() AS fechaIngreso, 'FOVIS' AS tipo, CONCAT (sol.solTipoTransaccion, ' - ',  
		pof.pofEstadoHogar, ' - ',perPrimerNombre, ' - ', perSegundoNombre, ' - ', perPrimerApellido, ' - ', perSegundoApellido) AS observaciones
		INTO #tmp_filaPersonas_P
		FROM core.dbo.Solicitud sol  
		INNER JOIN core.dbo.SolicitudLegalizacionDesembolso sld WITH (NOLOCK) ON sol.solid = sld.sldSolicitudGlobal
		INNER JOIN core.dbo.PostulacionFOVIS pof WITH (NOLOCK) ON pof.pofId = sld.sldPostulacionFOVIS
		INNER JOIN core.dbo.jefehogar jeh WITH (NOLOCK) ON pof.pofJefeHogar = jeh.jehId
		INNER JOIN core.dbo.afiliado afi WITH (NOLOCK) ON jehAfiliado = afi.afiid
		INNER JOIN core.dbo.Persona per WITH (NOLOCK) ON per.perId = afi.afiPersona
		LEFT JOIN core.dbo.ProyectoSolucionVivienda psv WITH (NOLOCK) ON pof.pofProyectoSolucionVivienda = psv.psvId
		INNER JOIN core.dbo.LegalizacionDesembolso lgd WITH (NOLOCK) ON sld.sldLegalizacionDesembolso = lgd.lgdId
		LEFT JOIN sap.ContablesCtrl ctr WITH (NOLOCK) ON  sol.solNumeroRadicacion = ctr.solNumeroRadicacion and per.perNumeroIdentificacion = ctr.perNumeroIdentificacion
		and per.perTipoIdentificacion = ctr.perTipoIdentificacion
		WHERE  sol.solFechaRadicacion >=  '2024-01-01'	
		AND ctr.solNumeroRadicacion IS NULL
		AND sol.solNumeroRadicacion LIKE '03%'
		AND sldEstadoSolicitud ='DESEMBOLSO_FOVIS_SOLICITADO_A_AREA_FINANCIERA'

		-- Toma las solicitudes de FOVIS y ejecuta los movimientos contables de FOVIS.
		DECLARE cursor_documentoId CURSOR FOR 
			SELECT solNumeroRadicacion, MAX(estado) AS estado, perNumeroIdentificacion, perTipoIdentificacion, MAX(fechaIngreso) AS fechaIngreso, tipo, MAX(observaciones) AS observaciones
			FROM #tmp_filaPersonas_P 
			GROUP BY solNumeroRadicacion, perNumeroIdentificacion, perTipoIdentificacion, tipo
				
		-- Listar los numeros de radicado asociados	 
		OPEN cursor_documentoId 
		FETCH NEXT FROM cursor_documentoId INTO @solNumeroRadicacion, @estado, @perNumeroIdentificacion, @perTipoIdentificacion, @fechaIngreso, @tipo, @observaciones
	
		WHILE @@FETCH_STATUS = 0 
		BEGIN
			SELECT @solNumeroRadicacion, @estado, @perNumeroIdentificacion, @perTipoIdentificacion, @fechaIngreso, @tipo, @observaciones
			
			-- Llamado de los movimientos contables
			EXEC [sap].[USP_GetICFOVIS_Insert_F06] @solNumeroRadicacion, @estado, @perNumeroIdentificacion, @perTipoIdentificacion, @fechaIngreso, @tipo, @observaciones;
			EXEC [sap].[USP_GetICFOVIS_Insert_F07] @solNumeroRadicacion, @estado, @perNumeroIdentificacion, @perTipoIdentificacion, @fechaIngreso, @tipo, @observaciones;
			EXEC [sap].[USP_GetICFOVIS_Insert_F16_F22] @solNumeroRadicacion, @estado, @perNumeroIdentificacion, @perTipoIdentificacion, @fechaIngreso, @tipo, @observaciones;
			EXEC [sap].[USP_GetICFOVIS_Insert_F09_F10] @solNumeroRadicacion, @estado, @perNumeroIdentificacion, @perTipoIdentificacion, @fechaIngreso, @tipo, @observaciones;
			EXEC [sap].[USP_GetICFOVIS_Insert_F11_F12] @solNumeroRadicacion, @estado, @perNumeroIdentificacion, @perTipoIdentificacion, @fechaIngreso, @tipo, @observaciones;
		
			FETCH NEXT FROM cursor_documentoId INTO @solNumeroRadicacion, @estado, @perNumeroIdentificacion, @perTipoIdentificacion, @fechaIngreso, @tipo, @observaciones
		END 
		CLOSE cursor_documentoId 
		DEALLOCATE cursor_documentoId
		
--		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
--			-- FIN Busca las solicitudes de Pago
--		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
	
--		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
--			-- Busca las solicitudes de Pago
--		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
  
		
		DECLARE @json NVARCHAR(max)
						SET @json = (SELECT dtsJsonPayload FROM core.dbo.Solicitud sol WITH (NOLOCK) INNER JOIN core.dbo.DatoTemporalSolicitud ds WITH (NOLOCK) ON sol.solId =ds.dtsSolicitud  WHERE solNumeroRadicacion = @solNumeroRadicacion)

		SELECT  sol.solNumeroRadicacion, 1 as estado, per.pernumeroIdentificacion, per.pertipoIdentificacion, SAP.GetLocalDate() AS fechaIngreso, 'FOVIS' AS tipo, CONCAT (sol.solTipoTransaccion, ' - ',  
				pof.pofEstadoHogar, ' - ',perprimerNombre, ' - ', persegundoNombre, ' - ', perprimerApellido, ' - ', persegundoApellido) AS observaciones	
		INTO #tmp_filaPersonas
		 from (select  * from openjson(@json,'$.legalizacionProveedor')
						with(
								pronumeroIdentificacion varchar(20) '$.proveedor.persona.numeroIdentificacion',
								idPersona varchar(20) '$.proveedor.persona.idPersona'
						
						)) legalizacionProveedor
				
							LEFT JOIN proveedor ON idPersona = provPersona
							,
					(select   * from openjson(@json,'$.datosPostulacionFovis.postulacion')
						with(
						
								idpersona varchar (30) '$.jefeHogar.idPersona'
												
							)) jefehogar 
					
							INNER JOIN core.dbo.persona per WITH (NOLOCK) ON idpersona = per.perId
							INNER JOIN core.dbo.solicitud sol WITH (NOLOCK) ON solnumeroradicacion = @solNumeroRadicacion
							INNER JOIN core.dbo.SolicitudLegalizacionDesembolso sld WITH (NOLOCK) ON sol.solid = sld.sldSolicitudGlobal
							INNER JOIN core.dbo.PostulacionFOVIS pof WITH (NOLOCK) ON pof.pofId = sld.sldPostulacionFOVIS
							INNER JOIN core.dbo.LegalizacionDesembolso lgd WITH (NOLOCK) ON sld.sldLegalizacionDesembolso = lgd.lgdId
							LEFT JOIN sap.ContablesCtrl ctr WITH (NOLOCK) ON  sol.solNumeroRadicacion = ctr.solNumeroRadicacion and per.perNumeroIdentificacion = ctr.perNumeroIdentificacion
							and per.perTipoIdentificacion = ctr.perTipoIdentificacion
				WHERE	YEAR(sol.solFechaRadicacion) >=2023
						AND sol.solNumeroRadicacion = @solNumeroRadicacion
						AND ctr.solNumeroRadicacion IS NULL
						AND pronumeroIdentificacion = '890900842'
						AND sol.solNumeroRadicacion LIKE '03%'

		
		-- Toma las solicitudes de FOVIS y ejecuta los movimientos contables de FOVIS.
		DECLARE cursor_documentoId CURSOR FOR 
			SELECT solNumeroRadicacion, MAX(estado) AS estado, perNumeroIdentificacion, perTipoIdentificacion, MAX(fechaIngreso) AS fechaIngreso, tipo, MAX(observaciones) AS observaciones
			FROM #tmp_filaPersonas
			GROUP BY solNumeroRadicacion, perNumeroIdentificacion, perTipoIdentificacion, tipo
				
		-- Listar los numeros de radicado asociados	 
		OPEN cursor_documentoId 
		FETCH NEXT FROM cursor_documentoId INTO @solNumeroRadicacion, @estado, @perNumeroIdentificacion, @perTipoIdentificacion, @fechaIngreso, @tipo, @observaciones
	
		WHILE @@FETCH_STATUS = 0 
		BEGIN
			SELECT @solNumeroRadicacion, @estado, @perNumeroIdentificacion, @perTipoIdentificacion, @fechaIngreso, @tipo, @observaciones

			EXEC [sap].[USP_GetICFOVIS_Insert_F08] @solNumeroRadicacion, @estado, @perNumeroIdentificacion, @perTipoIdentificacion, @fechaIngreso, @tipo, @observaciones;
			
		FETCH NEXT FROM cursor_documentoId INTO @solNumeroRadicacion, @estado, @perNumeroIdentificacion, @perTipoIdentificacion, @fechaIngreso, @tipo, @observaciones
		END 
		CLOSE cursor_documentoId 
		DEALLOCATE cursor_documentoId

		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			-- FIN Busca las solicitudes de Pago
		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
	
		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			-- Busca las solicitudes que no inician integraciones pero se marcan en la tabla de control
		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	

		INSERT INTO SAP.ContablesCtrl
		SELECT DISTINCT TOP 10 sol.solNumeroRadicacion, 1 as estado, per.perNumeroIdentificacion, per.perTipoIdentificacion, SAP.GetLocalDate() AS fechaIngreso, 'FOVIS' AS tipo, CONCAT (sol.solTipoTransaccion, ' - ',  
		pof.pofEstadoHogar, ' - ',sld.sldEstadoSolicitud, ' - ',perPrimerNombre, ' - ', perSegundoNombre, ' - ', perPrimerApellido, ' - ', perSegundoApellido) AS observaciones
		FROM core.dbo.Solicitud sol  
		INNER JOIN core.dbo.SolicitudLegalizacionDesembolso sld WITH (NOLOCK) ON sol.solid = sld.sldSolicitudGlobal
		INNER JOIN core.dbo.PostulacionFOVIS pof WITH (NOLOCK) ON pof.pofId = sld.sldPostulacionFOVIS
		INNER JOIN core.dbo.jefehogar jeh WITH (NOLOCK) ON pof.pofJefeHogar = jeh.jehId
		INNER JOIN core.dbo.afiliado afi WITH (NOLOCK) ON jehAfiliado = afi.afiid
		INNER JOIN core.dbo.Persona per WITH (NOLOCK) ON per.perId = afi.afiPersona
		LEFT JOIN core.dbo.ProyectoSolucionVivienda psv WITH (NOLOCK) ON pof.pofProyectoSolucionVivienda = psv.psvId
		INNER JOIN core.dbo.LegalizacionDesembolso lgd WITH (NOLOCK) ON sld.sldLegalizacionDesembolso = lgd.lgdId
		LEFT JOIN sap.ContablesCtrl ctr WITH (NOLOCK) ON  sol.solNumeroRadicacion = ctr.solNumeroRadicacion and per.perNumeroIdentificacion = ctr.perNumeroIdentificacion
		and per.perTipoIdentificacion = ctr.perTipoIdentificacion
		WHERE sol.solFechaRadicacion >=  '2024-01-01'	
		AND ctr.solNumeroRadicacion  IS NULL
		AND sld.sldId is NULL
		AND lgd.lgdId is NULL
		AND sol.solNumeroRadicacion LIKE '03%'


			
--			-- No realiza llamado de movimientos pero actualiza la tabla de control en estados no usados en la integracion
		
		
--		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
--			-- FIN Busca las solicitudes que no inician integraciones pero se marcan en la tabla de control
--		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
	set @fechahorafinal = dbo.GetLocalDate()

UPDATE core.sap.IC_LogEjecucion SET fechahorafinal = GETDATE() -'05:00:00' where id = @idlog

UPDATE core.sap.IC_LogEjecucion SET RegistrosEnviados = (select count(*) from sap.IC_FOVIS_Det where fechaEjecucion >= @fechahorainicio and fechaEjecucion <= @fechahorafinal) where id = @idlog 



		DELETE FROM core.sap.ejecucion_FOVIS
	END
	ELSE BEGIN
		SELECT 'El proceso ya se encuentra en ejecucion'
	END
COMMIT TRANSACTION

END