CREATE PROCEDURE USP_UTIL_GET_HistoricoAsignacionFOVIS 
	@numeroRadicacion VARCHAR(255),
	@idPostulacion BIGINT,
	@opcion BIGINT
with execute as owner
AS

BEGIN
	SET NOCOUNT ON;
	DECLARE @sql NVARCHAR(4000);		
	

	CREATE TABLE #CalificacionPostulacion(pofId BIGINT, 
		pofCicloAsignacion BIGINT, 
		pofSolicitudAsignacion BIGINT, 
		pofResultadoAsignacion VARCHAR(50),
		pofPuntaje NUMERIC(5,2),
		pofFechaCalificacion DATETIME,
		pofPrioridadAsignacion VARCHAR(11),
		pofValorAsignadoSFV NUMERIC(19,5),
		pofIdentificardorDocumentoActaAsignacion VARCHAR(255),
		pofModalidad VARCHAR(50),
		pofValorSFVSolicitado NUMERIC(19,5),
		pofJefeHogar BIGINT,
		pofInfoAsignacion VARCHAR(MAX),
		s1 VARCHAR(500))

	SET @sql = 'SELECT DISTINCT pof.pofId, pof.pofCicloAsignacion, 
			pof.pofSolicitudAsignacion, 
			pof.pofResultadoAsignacion, 
			pof.pofPuntaje,
			pof.pofFechaCalificacion, 
			pof.pofPrioridadAsignacion, 
			pof.pofValorAsignadoSFV,
			pof.pofIdentificardorDocumentoActaAsignacion,
			pof.pofModalidad,
			pof.pofValorSFVSolicitado,
			pof.pofJefeHogar,
			pof.pofInfoAsignacion
		FROM (
			SELECT pof.pofId, pof.pofCicloAsignacion, pof.pofSolicitudAsignacion, MAX(pof.REV) AS REV
			FROM PostulacionFOVIS_aud pof
			WHERE pof.pofSolicitudAsignacion IS NOT NULL
			GROUP BY pof.pofId, pof.pofCicloAsignacion, pof.pofSolicitudAsignacion
		) AS pofRev
		JOIN PostulacionFOVIS_aud pof ON (pofRev.pofId = pof.pofId AND pofRev.REV = pof.REV)'
	
	
	INSERT INTO #CalificacionPostulacion (pofId, pofCicloAsignacion, pofSolicitudAsignacion, 
		pofResultadoAsignacion, pofPuntaje, pofFechaCalificacion, pofPrioridadAsignacion,
		pofValorAsignadoSFV, pofIdentificardorDocumentoActaAsignacion, pofModalidad, pofValorSFVSolicitado,
		pofJefeHogar, pofInfoAsignacion, s1)
	EXEC sp_execute_remote CoreAudReferenceData,
	@sql

	-- Opcion 0 Obtener Historico por NumeroRadicacion de postulacion
	IF @opcion = 0
		BEGIN
			SELECT pof.pofId,
			cia.ciaNombre, 
			sol.solNumeroRadicacion,
			per.perTipoIdentificacion, 
			per.perNumeroIdentificacion,
			CONCAT(per.perPrimerNombre, ' ',
			    CASE WHEN per.perSegundoNombre IS NOT NULL THEN CONCAT(per.perSegundoNombre, ' ') ELSE '' END,
			    per.perPrimerApellido, ' ',
			    CASE WHEN per.perSegundoApellido IS NOT NULL THEN per.perSegundoApellido ELSE '' END) AS nombreCompleto,
			pof.pofModalidad,
			pof.pofPuntaje,
			pof.pofValorSFVSolicitado,
			pof.pofIdentificardorDocumentoActaAsignacion,
			saf.safUsuario,               
			pof.pofResultadoAsignacion,
			aaf.aafFechaActaAsignacionFovis,
			saf.safFechaAceptacion,
			pof.pofPrioridadAsignacion,
			pof.pofInfoAsignacion
			FROM SolicitudAsignacion saf
			JOIN Solicitud sol ON (saf.safSolicitudGlobal = sol.solId)
			JOIN ActaAsignacionFovis aaf ON (aaf.aafSolicitudAsignacion = saf.safId)
			JOIN #CalificacionPostulacion pof ON (saf.safId = pof.pofSolicitudAsignacion  AND saf.safCicloAsignacion = pof.pofCicloAsignacion)
			JOIN CicloAsignacion cia ON (pof.pofCicloAsignacion = cia.ciaId)
			JOIN SolicitudPostulacion spo ON (spo.spoPostulacionFovis = pof.pofId)
			JOIN Solicitud solpof ON (spo.spoSolicitudGlobal = solpof.solId)
			JOIN JefeHogar jeh ON (pof.pofJefeHogar = jeh.jehId)
			JOIN Afiliado afi ON (jeh.jehAfiliado = afi.afiId)
			JOIN Persona per ON (afi.afiPersona = per.perId)
			WHERE sol.solResultadoProceso NOT IN ('CANCELADA', 'DESISTIDA')
			AND solpof.solNumeroRadicacion = @numeroRadicacion
		END
	-- Opcion 1 Obtener cantidad asignaciones de una postulacion por identificador de postulacion
	ELSE IF @opcion = 1
		BEGIN
			SELECT COUNT(pof.pofId) AS cantidadAsignaciones
			FROM SolicitudAsignacion saf
			JOIN Solicitud sol ON (saf.safSolicitudGlobal = sol.solId)
			JOIN ActaAsignacionFovis aaf ON (aaf.aafSolicitudAsignacion = saf.safId)
			JOIN #CalificacionPostulacion pof ON (saf.safId = pof.pofSolicitudAsignacion  AND saf.safCicloAsignacion = pof.pofCicloAsignacion)
			WHERE sol.solResultadoProceso = 'APROBADA'
			AND saf.safEstadoSolicitudAsignacion = 'CERRADA'
			AND pof.pofId = @idPostulacion
			GROUP BY pof.pofId
		END
END
;
