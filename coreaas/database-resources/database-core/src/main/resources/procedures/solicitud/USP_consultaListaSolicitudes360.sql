-- =============================================
-- Author:		Francisco Alejandro Hoyos Rojas
-- Create date: 2020/12/01
-- Description:	Procedimiento almacenado encargado de 
-- obtener la lista de solicitudes para la vista 360
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_consultaListaSolicitudes360]
	@tipoIdentificacion VARCHAR(20),	--Tipo de identificación de la persona
	@numeroIdentificacion VARCHAR(16),	--Número de identificación de la persona
	@tiposAportante	VARCHAR(34),	    --Lista con los de tipos aportantes (EMPLEADOR, INDEPENDIENTE, PENSIONADO)
	@numeroRadicado VARCHAR(20),		--Número de radicado de una solicitud
	@tipoSolicitud	VARCHAR(71),		--Lista con los tipos de solicitudes (AFILIACION, CORRECCION_APORTES, DEVOLUCION_APORTES, NOVEDAD, PAGOS_MANUALES)
	@estadoSolicitud VARCHAR(479),		--Lista con los estados de solicitud (APROBADA, ASIGNADA_AL_BACK, CANCELADA, CERRADA, DESISTIDA, EN_ANALISIS_ESPECIALIZADO, ESCALADA,INSTANCIADA, NO_CONFORME_EN_GESTION, NO_CONFORME_GESTIONADA, NO_CONFORME_SUBSANABLE, PENDIENTE_DE_LIBERAR_POR_DOCS_FISICOS, PENDIENTE_ENVIO_AL_BACK, PRE_RADICADA, RADICADA, RECHAZADA, REGISTRO_INTENTO_AFILIACION, ESPERANDO_CONFIRMACION_RETIRO, GESTIONADA_POR_ESPECIALISTA, BLOQUEADA, EN_ANALISIS, EN_EVALUACION_SUPERVISOR, EVALUADA_POR_SUPERVISOR, FALTA_INFORMACION, GESTIONAR_PAGO,NOTIFICADA, PAGO_PROCESADO)
	@fechaRadicado	VARCHAR(10),		--Fecha de radicación de las solicitudes	
	@fechaInicio	VARCHAR(10),		--Fecha inicial  
	@fechaFin		VARCHAR(10)			--Fecha Fin
	
AS
	SET NOCOUNT ON;
	-- Tabla tipos de aportantes
	DECLARE @tipoAportanteTB TABLE (tipoAportante VARCHAR(13))
	IF @tiposAportante <> ''
		BEGIN
			INSERT INTO @tipoAportanteTB (tipoAportante)
			SELECT Data FROM dbo.Split(@tiposAportante,',') 
		END
	
	-- Tabla tipos de solicitudes
	DECLARE @tipoSolicitudTB TABLE (tipoSolicitud VARCHAR(19))
	INSERT INTO @tipoSolicitudTB (tipoSolicitud)
	SELECT Data FROM dbo.Split(@tipoSolicitud,',') 
	
	-- Tabla tipos de estados solicitud
	DECLARE @estadoSolicitudTB TABLE (estadoSolicitud VARCHAR(37))
	INSERT INTO @estadoSolicitudTB (estadoSolicitud)
	SELECT Data FROM dbo.Split(@estadoSolicitud,',') 
	
	--Fecha radicado fin se utiliza para comparar con la fecha de radicado ingresada
	DECLARE @fechaRadicadoFin DATETIME2

	--Convertir fechas a datetime2
	IF @fechaRadicado != '0'
		BEGIN
			SET @fechaRadicado = CONVERT(DATETIME2,@fechaRadicado,120)
			--Fecha radicado fin se utiliza para comparar con la fecha de radicado ingresada
			SET @fechaRadicadoFin = DATEADD(DAY,1,@fechaRadicado)
		END
	ELSE
		BEGIN
		  DECLARE @hayFechaRadicado INT = 0
		  SET @fechaRadicado = CONVERT(DATETIME2,@fechaInicio,120)
			--Fecha radicado fin se utiliza para comparar con la fecha de radicado ingresada
		  SET @fechaRadicadoFin = CONVERT(DATETIME2,@fechaFin,120)
		END 

	SET @fechaInicio = CONVERT(DATETIME2,@fechaInicio,120)
	SET @fechaFin = CONVERT(DATETIME2,DATEADD(DAY, 1, (CONVERT(DATETIME2,@fechaFin))),120)
	SET @fechaRadicadoFin = DATEADD(DAY,1,@fechaRadicado)
	

	-- Número de registros en la tabla tipoAportanteTB
	DECLARE @hayTipoAportante INT
	SET @hayTipoAportante = 0
	IF @tiposAportante <> ''
		BEGIN
			SET @hayTipoAportante = (SELECT COUNT(tipoAportante) FROM @tipoAportanteTB)
		END

	-- Número de registros en la tabla tipoAportanteTB
	DECLARE @hayEstadoSolicitud INT
	SET @hayEstadoSolicitud = (SELECT COUNT(estadoSolicitud) FROM @estadoSolicitudTB)

	-- Tabla con las solicitudes encontradas
	DECLARE @SolicitudTB TABLE(numeroRadicado VARCHAR(20), fechaRadicacion DATETIME2, tipoSolicitud VARCHAR(19), estado VARCHAR(37) , idSolicitud BIGINT, idSolicitudAfiliacion BIGINT, canalRecepcion VARCHAR(21))
    BEGIN
	IF EXISTS(Select tipoSolicitud FROM @tipoSolicitudTB WHERE tipoSolicitud='PAGOS_MANUALES')
	   BEGIN
			INSERT INTO @SolicitudTB(numeroRadicado,fechaRadicacion,tipoSolicitud,estado,idSolicitud,idSolicitudAfiliacion,canalRecepcion)
				SELECT sol.solNumeroRadicacion numeroRadicado, sol.solFechaRadicacion fechaRadicacion, 'PAGOS_MANUALES' tipoSolicitud, ISNULL(sol.solResultadoProceso, soa.soaEstadoSolicitud) estado, sol.solId idSolicitud, NULL idSolicitudAfiliacion, sol.solCanalRecepcion canalRecepcion 
				FROM SolicitudAporte soa
				JOIN Solicitud sol ON soa.soaSolicitudGlobal = sol.solId
				WHERE soa.soaTipoIdentificacion = @tipoIdentificacion
				AND soa.soaNumeroIdentificacion = @numeroIdentificacion
				AND (@hayTipoAportante = 0 OR soa.soaTipoSolicitante IN (SELECT tipoAportante FROM @tipoAportanteTB))
				AND (@numeroRadicado = '' OR sol.solNumeroRadicacion = @numeroRadicado)	
				AND (@hayEstadoSolicitud = 0 OR soa.soaEstadoSolicitud IN (SELECT estadoSolicitud FROM @estadoSolicitudTB) OR sol.solResultadoProceso IN (SELECT estadoSolicitud FROM @estadoSolicitudTB))
				AND (@hayFechaRadicado = 0 OR sol.solFechaRadicacion >= @fechaRadicado AND sol.solFechaRadicacion < @fechaRadicadoFin )
				AND  sol.solFechaRadicacion >= @fechaInicio AND sol.solFechaRadicacion < @fechaFin 
		END
	IF EXISTS(Select tipoSolicitud FROM @tipoSolicitudTB WHERE tipoSolicitud='DEVOLUCION_APORTES')
	   BEGIN
			INSERT INTO @SolicitudTB(numeroRadicado,fechaRadicacion,tipoSolicitud,estado,idSolicitud,idSolicitudAfiliacion,canalRecepcion)
				SELECT sol.solNumeroRadicacion numeroRadicado, sol.solFechaRadicacion fechaRadicacion, 'DEVOLUCION_APORTES' tipoSolicitud, ISNULL(sol.solResultadoProceso, sda.sdaEstadoSolicitud) estado, sol.solId idSolicitud, NULL idSolicitudAfiliacion, sol.solCanalRecepcion canalRecepcion 
				FROM SolicitudDevolucionAporte sda
				JOIN Solicitud sol ON sda.sdaSolicitudGlobal = sol.solId
				JOIN Persona per ON per.perId = sda.sdaPersona
				WHERE per.perTipoIdentificacion = @tipoIdentificacion
				AND per.perNumeroIdentificacion = @numeroIdentificacion
				AND (@hayTipoAportante = 0 OR sda.sdaTipoSolicitante IN (SELECT tipoAportante FROM @tipoAportanteTB))
				AND (@numeroRadicado = '' OR sol.solNumeroRadicacion = @numeroRadicado)	
				AND (@hayEstadoSolicitud = 0 OR sda.sdaEstadoSolicitud IN (SELECT estadoSolicitud FROM @estadoSolicitudTB) OR sol.solResultadoProceso IN (SELECT estadoSolicitud FROM @estadoSolicitudTB))
				AND (@hayFechaRadicado = 0 OR sol.solFechaRadicacion >= @fechaRadicado AND sol.solFechaRadicacion < @fechaRadicadoFin)
				AND sol.solFechaRadicacion >= @fechaInicio AND sol.solFechaRadicacion <@fechaFin
		END
	IF EXISTS(Select tipoSolicitud FROM @tipoSolicitudTB WHERE tipoSolicitud='CORRECCION_APORTES')
	   BEGIN
			INSERT INTO @SolicitudTB(numeroRadicado,fechaRadicacion,tipoSolicitud,estado,idSolicitud,idSolicitudAfiliacion,canalRecepcion)
				SELECT sol.solNumeroRadicacion numeroRadicado, sol.solFechaRadicacion fechaRadicacion, 'CORRECCION_APORTES' tipoSolicitud, ISNULL(sol.solResultadoProceso, sca.scaEstadoSolicitud) estado, sol.solId idSolicitud, NULL idSolicitudAfiliacion, sol.solCanalRecepcion canalRecepcion 
				FROM SolicitudCorreccionAporte sca
				JOIN Solicitud sol ON sca.scaSolicitudGlobal = sol.solId
				JOIN Persona per ON per.perId = sca.scaPersona
				WHERE per.perTipoIdentificacion = @tipoIdentificacion
				AND per.perNumeroIdentificacion = @numeroIdentificacion
				AND (@hayTipoAportante = 0 OR sca.scaTipoSolicitante IN (SELECT tipoAportante FROM @tipoAportanteTB))
				AND (@numeroRadicado = '' OR sol.solNumeroRadicacion = @numeroRadicado)	
				AND (@hayEstadoSolicitud = 0 OR sca.scaEstadoSolicitud IN (SELECT estadoSolicitud FROM @estadoSolicitudTB) OR sol.solResultadoProceso IN (SELECT estadoSolicitud FROM @estadoSolicitudTB))
				AND (@hayFechaRadicado = 0 OR sol.solFechaRadicacion >= @fechaRadicado AND sol.solFechaRadicacion < @fechaRadicadoFin)
				AND sol.solFechaRadicacion >= @fechaInicio AND sol.solFechaRadicacion < @fechaFin
		END
	IF EXISTS(Select tipoSolicitud FROM @tipoSolicitudTB WHERE tipoSolicitud='NOVEDAD')
	   BEGIN
			INSERT INTO @SolicitudTB(numeroRadicado,fechaRadicacion,tipoSolicitud,estado,idSolicitud,idSolicitudAfiliacion,canalRecepcion)
				SELECT sol.solNumeroRadicacion numeroRadicado, sol.solFechaRadicacion fechaRadicacion, 'NOVEDAD' tipoSolicitud, ISNULL(sol.solResultadoProceso, sno.snoEstadoSolicitud) estado, sol.solId idSolicitud, NULL idSolicitudAfiliacion, sol.solCanalRecepcion canalRecepcion 
				FROM SolicitudNovedad sno
				JOIN Solicitud sol ON sno.snoSolicitudGlobal = sol.solId
				JOIN SolicitudNovedadPersona snp ON snp.snpSolicitudNovedad = sno.snoId
				JOIN Persona per ON per.perId = snp.snpPersona
				LEFT JOIN IntentoNovedad ino ON sol.solId = ino.inoSolicitud 
				WHERE per.perTipoIdentificacion = @tipoIdentificacion
				AND per.perNumeroIdentificacion = @numeroIdentificacion
				AND (@numeroRadicado = '' OR sol.solNumeroRadicacion = @numeroRadicado)	
				AND (@hayEstadoSolicitud = 0 OR sno.snoEstadoSolicitud IN (SELECT estadoSolicitud FROM @estadoSolicitudTB) OR sol.solResultadoProceso IN (SELECT estadoSolicitud FROM @estadoSolicitudTB))
				AND (@hayFechaRadicado = 0 OR sol.solFechaRadicacion >= @fechaRadicado AND sol.solFechaRadicacion < @fechaRadicadoFin)
				AND sol.solFechaRadicacion >= @fechaInicio AND sol.solFechaRadicacion < @fechaFin
				AND ino.inoSolicitud IS NULL
			IF EXISTS(Select tipoAportante FROM @tipoAportanteTB WHERE tipoAportante='EMPLEADOR')
				BEGIN
				INSERT INTO @SolicitudTB(numeroRadicado,fechaRadicacion,tipoSolicitud,estado,idSolicitud,idSolicitudAfiliacion,canalRecepcion)
					SELECT sol.solNumeroRadicacion numeroRadicado, sol.solFechaRadicacion fechaRadicacion, 'NOVEDAD' tipoSolicitud, ISNULL(sol.solResultadoProceso, sno.snoEstadoSolicitud) estado, sol.solId idSolicitud, NULL idSolicitudAfiliacion, sol.solCanalRecepcion canalRecepcion 
					FROM SolicitudNovedad sno
					JOIN Solicitud sol ON sno.snoSolicitudGlobal = sol.solId
					JOIN SolicitudNovedadEmpleador sne ON sne.sneIdSolicitudNovedad = sno.snoId
					JOIN Empleador empl ON empl.empId = sne.sneIdEmpleador
					JOIN Empresa emp ON empl.empEmpresa = emp.empId
					JOIN Persona per ON per.perId = emp.empPersona
					WHERE per.perTipoIdentificacion = @tipoIdentificacion
					AND per.perNumeroIdentificacion = @numeroIdentificacion
					AND (@numeroRadicado = '' OR sol.solNumeroRadicacion = @numeroRadicado)	
					AND (@hayEstadoSolicitud = 0 OR sno.snoEstadoSolicitud IN (SELECT estadoSolicitud FROM @estadoSolicitudTB) OR sol.solResultadoProceso IN (SELECT estadoSolicitud FROM @estadoSolicitudTB))
					AND (@hayFechaRadicado = 0 OR sol.solFechaRadicacion >= @fechaRadicado AND sol.solFechaRadicacion < @fechaRadicadoFin)
					AND sol.solFechaRadicacion >= @fechaInicio AND sol.solFechaRadicacion < @fechaFin
				END
		END
	IF EXISTS(Select tipoSolicitud FROM @tipoSolicitudTB WHERE tipoSolicitud='INTENTO_AFILIACION')
	   BEGIN
			-- Intento Afiliacion Persona
			INSERT INTO @SolicitudTB(numeroRadicado,fechaRadicacion,tipoSolicitud,estado,idSolicitud,idSolicitudAfiliacion,canalRecepcion)
				SELECT sol.solNumeroRadicacion numeroRadicado, sol.solFechaRadicacion fechaRadicacion, 'INTENTO_AFILIACION' tipoSolicitud, ISNULL(sol.solResultadoProceso, sap.sapEstadoSolicitud) estado, sol.solId idSolicitud, NULL idSolicitudAfiliacion, sol.solCanalRecepcion canalRecepcion 
				FROM IntentoAfiliacion iaf
				JOIN Solicitud sol ON iaf.iafSolicitud = sol.solId
				JOIN SolicitudAfiliacionPersona sap ON sap.sapSolicitudGlobal = sol.solId
				JOIN RolAfiliado roa ON roa.roaId = sap.sapRolAfiliado
				JOIN Afiliado afi ON afi.afiId = roa.roaAfiliado
				JOIN Persona per ON per.perId = afi.afiPersona
				WHERE per.perTipoIdentificacion = @tipoIdentificacion
				AND per.perNumeroIdentificacion = @numeroIdentificacion
				AND (@numeroRadicado = '' OR sol.solNumeroRadicacion = @numeroRadicado)	
				AND (@hayEstadoSolicitud = 0 OR sap.sapEstadoSolicitud IN (SELECT estadoSolicitud FROM @estadoSolicitudTB) OR sol.solResultadoProceso IN (SELECT estadoSolicitud FROM @estadoSolicitudTB))
				AND (@hayFechaRadicado = 0 OR sol.solFechaRadicacion >= @fechaRadicado AND sol.solFechaRadicacion < @fechaRadicadoFin)
				AND sol.solFechaRadicacion >= @fechaInicio AND sol.solFechaRadicacion < @fechaFin
			-- Intento Afiliacion Empleador
			INSERT INTO @SolicitudTB(numeroRadicado,fechaRadicacion,tipoSolicitud,estado,idSolicitud,idSolicitudAfiliacion,canalRecepcion)
				SELECT sol.solNumeroRadicacion numeroRadicado, sol.solFechaRadicacion fechaRadicacion, 'INTENTO_AFILIACION' tipoSolicitud, ISNULL(sol.solResultadoProceso, sae.saeEstadoSolicitud) estado, sol.solId idSolicitud, NULL idSolicitudAfiliacion, sol.solCanalRecepcion canalRecepcion 
				FROM IntentoAfiliacion iaf
				JOIN Solicitud sol ON iaf.iafSolicitud = sol.solId
				JOIN SolicitudAfiliaciEmpleador sae ON sae.saeSolicitudGlobal = sol.solId
				JOIN Empleador empl ON empl.empId = sae.saeEmpleador
				JOIN Empresa emp ON empl.empEmpresa = emp.empId
				JOIN Persona per ON per.perId = emp.empPersona
				WHERE per.perTipoIdentificacion = @tipoIdentificacion
				AND per.perNumeroIdentificacion = @numeroIdentificacion
				AND (@numeroRadicado = '' OR sol.solNumeroRadicacion = @numeroRadicado)	
				AND (@hayEstadoSolicitud = 0 OR sae.saeEstadoSolicitud IN (SELECT estadoSolicitud FROM @estadoSolicitudTB) OR sol.solResultadoProceso IN (SELECT estadoSolicitud FROM @estadoSolicitudTB))
				AND (@hayFechaRadicado = 0 OR sol.solFechaRadicacion >= @fechaRadicado AND sol.solFechaRadicacion < @fechaRadicadoFin)
				AND sol.solFechaRadicacion >= @fechaInicio AND sol.solFechaRadicacion < @fechaFin
		END

	IF EXISTS(Select tipoSolicitud FROM @tipoSolicitudTB WHERE tipoSolicitud='AFILIACION') 
		
	   BEGIN
		if @tiposAportante <> 'EMPLEADOR'
		BEGIN
			INSERT INTO @SolicitudTB(numeroRadicado,fechaRadicacion,tipoSolicitud,estado,idSolicitud,idSolicitudAfiliacion,canalRecepcion)
				SELECT sol.solNumeroRadicacion numeroRadicado, sol.solFechaRadicacion fechaRadicacion, 'AFILIACION' tipoSolicitud, ISNULL(sol.solResultadoProceso, sap.sapEstadoSolicitud) estado, sol.solId idSolicitud, sap.sapId idSolicitudAfiliacion, sol.solCanalRecepcion canalRecepcion 
				FROM Solicitud sol 
				JOIN SolicitudAfiliacionPersona sap ON sap.sapSolicitudGlobal = sol.solId
				JOIN RolAfiliado roa ON roa.roaId = sap.sapRolAfiliado
				JOIN Afiliado afi ON afi.afiId = roa.roaAfiliado
				JOIN Persona per ON per.perId = afi.afiPersona
				--se comenta bajo el glpi 91620
				--LEFT JOIN IntentoAfiliacion iaf ON iaf.iafSolicitud = sol.solId
				WHERE per.perTipoIdentificacion = @tipoIdentificacion
				AND per.perNumeroIdentificacion = @numeroIdentificacion
				--se comenta bajo el glpi 91620
				--AND iaf.iafId IS NULL
				AND (@numeroRadicado = '' OR sol.solNumeroRadicacion = @numeroRadicado)	
				AND (@hayEstadoSolicitud = 0 OR sap.sapEstadoSolicitud IN (SELECT estadoSolicitud FROM @estadoSolicitudTB) OR sol.solResultadoProceso IN (SELECT estadoSolicitud FROM @estadoSolicitudTB))
				AND (@hayFechaRadicado = 0 OR sol.solFechaRadicacion >= @fechaRadicado AND sol.solFechaRadicacion < @fechaRadicadoFin)
				AND sol.solFechaRadicacion >= @fechaInicio AND sol.solFechaRadicacion < @fechaFin

		END
		ELSE
		BEGIN
				INSERT INTO @SolicitudTB(numeroRadicado,fechaRadicacion,tipoSolicitud,estado,idSolicitud,idSolicitudAfiliacion,canalRecepcion)
					SELECT sol.solNumeroRadicacion numeroRadicado, sol.solFechaRadicacion fechaRadicacion, 'AFILIACION' tipoSolicitud, ISNULL(sol.solResultadoProceso, sae.saeEstadoSolicitud) estado, sol.solId idSolicitud, sae.saeId idSolicitudAfiliacion, sol.solCanalRecepcion canalRecepcion 
					FROM Solicitud sol 
					JOIN SolicitudAfiliaciEmpleador sae ON sae.saeSolicitudGlobal = sol.solId
					JOIN Empleador empl ON empl.empId = sae.saeEmpleador
					JOIN Empresa emp ON empl.empEmpresa = emp.empId
					JOIN Persona per ON per.perId = emp.empPersona
					LEFT JOIN IntentoAfiliacion iaf ON iaf.iafSolicitud = sol.solId
					WHERE per.perTipoIdentificacion = @tipoIdentificacion
					AND per.perNumeroIdentificacion = @numeroIdentificacion
					AND iaf.iafId IS NULL
					AND (@numeroRadicado = '' OR sol.solNumeroRadicacion = @numeroRadicado)	
					AND (@hayEstadoSolicitud = 0 OR sae.saeEstadoSolicitud IN (SELECT estadoSolicitud FROM @estadoSolicitudTB) OR sol.solResultadoProceso IN (SELECT estadoSolicitud FROM @estadoSolicitudTB))
					AND (@hayFechaRadicado = 0 OR sol.solFechaRadicacion >= @fechaRadicado AND sol.solFechaRadicacion < @fechaRadicadoFin)
					AND sol.solFechaRadicacion >= @fechaInicio AND sol.solFechaRadicacion < @fechaFin
				end
		end

			SELECT numeroRadicado, fechaRadicacion, tipoSolicitud, estado, idSolicitud, idSolicitudAfiliacion, canalRecepcion FROM @SolicitudTB GROUP BY numeroRadicado, fechaRadicacion, tipoSolicitud, estado, idSolicitud, idSolicitudAfiliacion, canalRecepcion ORDER BY fechaRadicacion DESC

END;