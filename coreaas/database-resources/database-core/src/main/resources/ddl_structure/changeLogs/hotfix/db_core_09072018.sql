--liquibase formatted sql

--changeset dsuesca:01
--comment: Arreglo de datos de auditoria por control de cambios jocorrea
UPDATE aud
SET aud.snoNovedad = NULL,
	aud.snoSolicitudGlobal = NULL
FROM core_aud.dbo.SolicitudNovedad_aud aud

UPDATE aud
SET aud.snoNovedad = nov.snoNovedad,
	aud.snoCargaMultiple = nov.snoCargaMultiple,
	aud.snoEstadoSolicitud = nov.snoEstadoSolicitud,
	aud.snoObservaciones = nov.snoObservaciones,
	aud.snoSolicitudGlobal = nov.snoSolicitudGlobal	
FROM core_aud.dbo.SolicitudNovedad_aud aud
INNER JOIN SolicitudNovedad nov ON (nov.snoId = aud.snoId)
WHERE aud.REV = (SELECT MAX(REV) FROM core_aud.dbo.SolicitudNovedad_aud
			WHERE snoId = nov.snoId)

UPDATE aud
SET aud.solAnulada = nov.solAnulada,
aud.solCajaCorrespondencia = nov.solCajaCorrespondencia,
aud.solCanalRecepcion = nov.solCanalRecepcion,
aud.solCargaAfiliacionMultipleEmpleador = nov.solCargaAfiliacionMultipleEmpleador,
aud.solCiudadUsuarioRadicacion = nov.solCiudadUsuarioRadicacion,
aud.solClasificacion = nov.solClasificacion,
aud.solDestinatario = nov.solDestinatario,
aud.solDiferenciasCargueActualizacion = nov.solDiferenciasCargueActualizacion,
aud.solEstadoDocumentacion = nov.solEstadoDocumentacion,
aud.solFechaCreacion = nov.solFechaCreacion,
aud.solFechaRadicacion = nov.solFechaRadicacion,
aud.solInstanciaProceso = nov.solInstanciaProceso,
aud.solMetodoEnvio = nov.solMetodoEnvio,
aud.solNumeroRadicacion = nov.solNumeroRadicacion,
aud.solObservacion = nov.solObservacion,
aud.solResultadoProceso = nov.solResultadoProceso,
aud.solSedeDestinatario = nov.solSedeDestinatario,
aud.solTipoRadicacion = nov.solTipoRadicacion,
aud.solTipoTransaccion = nov.solTipoTransaccion,
aud.solUsuarioRadicacion = nov.solUsuarioRadicacion
FROM core_aud.dbo.Solicitud_aud aud
INNER JOIN Solicitud nov ON (nov.solId = aud.solId)