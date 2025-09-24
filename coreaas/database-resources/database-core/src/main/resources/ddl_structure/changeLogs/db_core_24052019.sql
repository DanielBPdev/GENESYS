--liquibase formatted sql

--changeset mamonroy:01
--comment:
ALTER TABLE PostulacionFovis ADD pofMatriculaInmobiliariaInmueble VARCHAR(50);
ALTER TABLE PostulacionFovis ADD pofFechaRegistroEscritura DATE;
ALTER TABLE PostulacionFovis ADD pofLoteUrbanizado BIT;
ALTER TABLE PostulacionFovis ADD pofPoseedorOcupanteVivienda VARCHAR(50);
ALTER TABLE PostulacionFovis ADD pofNumeroEscritura VARCHAR(20);
ALTER TABLE PostulacionFovis ADD pofFechaEscritura DATE;
ALTER TABLE PostulacionFovis ADD pofUbicacionIgualProyecto BIT;
ALTER TABLE PostulacionFovis ADD pofUbicacionVivienda BIGINT;
ALTER TABLE PostulacionFovis ADD CONSTRAINT FK_PostulacionFovis_pofUbicacionVivienda FOREIGN KEY (pofUbicacionVivienda) REFERENCES Ubicacion(ubiId);

ALTER TABLE aud.PostulacionFovis_aud ADD pofMatriculaInmobiliariaInmueble VARCHAR(50);
ALTER TABLE aud.PostulacionFovis_aud ADD pofFechaRegistroEscritura DATE;
ALTER TABLE aud.PostulacionFovis_aud ADD pofLoteUrbanizado BIT;
ALTER TABLE aud.PostulacionFovis_aud ADD pofPoseedorOcupanteVivienda VARCHAR(50);
ALTER TABLE aud.PostulacionFovis_aud ADD pofNumeroEscritura VARCHAR(20);
ALTER TABLE aud.PostulacionFovis_aud ADD pofFechaEscritura DATE;
ALTER TABLE aud.PostulacionFovis_aud ADD pofUbicacionIgualProyecto BIT;
ALTER TABLE aud.PostulacionFovis_aud ADD pofUbicacionVivienda BIGINT;

--MIGRACION DE DATOS ENTRE TABLAS
UPDATE pof
SET pof.pofMatriculaInmobiliariaInmueble = psv.psvMatriculaInmobiliariaInmueble
FROM PostulacionFovis pof
JOIN ProyectoSolucionVivienda psv ON psv.psvId = pof.pofProyectoSolucionVivienda;

UPDATE pof
SET pof.pofFechaRegistroEscritura = psv.psvFechaRegistroEscritura
FROM PostulacionFovis pof
JOIN ProyectoSolucionVivienda psv ON psv.psvId = pof.pofProyectoSolucionVivienda;

UPDATE pof
SET pof.pofLoteUrbanizado = psv.psvLoteUrbanizado
FROM PostulacionFovis pof
JOIN ProyectoSolucionVivienda psv ON psv.psvId = pof.pofProyectoSolucionVivienda;

UPDATE pof
SET pof.pofPoseedorOcupanteVivienda = psv.psvPoseedorOcupanteVivienda
FROM PostulacionFovis pof
JOIN ProyectoSolucionVivienda psv ON psv.psvId = pof.pofProyectoSolucionVivienda;

UPDATE pof
SET pof.pofNumeroEscritura = psv.psvNumeroEscritura
FROM PostulacionFovis pof
JOIN ProyectoSolucionVivienda psv ON psv.psvId = pof.pofProyectoSolucionVivienda;

UPDATE pof
SET pof.pofFechaEscritura = psv.psvFechaEscritura
FROM PostulacionFovis pof
JOIN ProyectoSolucionVivienda psv ON psv.psvId = pof.pofProyectoSolucionVivienda;

UPDATE pof
SET pof.pofUbicacionIgualProyecto = psv.psvUbicacionIgualProyecto
FROM PostulacionFovis pof
JOIN ProyectoSolucionVivienda psv ON psv.psvId = pof.pofProyectoSolucionVivienda;

UPDATE pof
SET pof.pofUbicacionVivienda = psv.psvUbicacionVivienda
FROM PostulacionFovis pof
JOIN ProyectoSolucionVivienda psv ON psv.psvId = pof.pofProyectoSolucionVivienda;


--ELIMINACION COLUMNAS PROYECTO SOLUCION VIVIENDA
ALTER TABLE ProyectoSolucionVivienda DROP COLUMN psvMatriculaInmobiliariaInmueble;
ALTER TABLE ProyectoSolucionVivienda DROP COLUMN psvFechaRegistroEscritura;
ALTER TABLE ProyectoSolucionVivienda DROP COLUMN psvLoteUrbanizado;
ALTER TABLE ProyectoSolucionVivienda DROP COLUMN psvPoseedorOcupanteVivienda;
ALTER TABLE ProyectoSolucionVivienda DROP COLUMN psvNumeroEscritura;
ALTER TABLE ProyectoSolucionVivienda DROP COLUMN psvFechaEscritura;
ALTER TABLE ProyectoSolucionVivienda DROP COLUMN psvUbicacionIgualProyecto;
ALTER TABLE ProyectoSolucionVivienda DROP CONSTRAINT FK_ProyectoSolucionVivienda_psvUbicacionVivienda;
ALTER TABLE ProyectoSolucionVivienda DROP COLUMN psvUbicacionVivienda;

ALTER TABLE aud.ProyectoSolucionVivienda_aud DROP COLUMN psvMatriculaInmobiliariaInmueble;
ALTER TABLE aud.ProyectoSolucionVivienda_aud DROP COLUMN psvFechaRegistroEscritura;
ALTER TABLE aud.ProyectoSolucionVivienda_aud DROP COLUMN psvLoteUrbanizado;
ALTER TABLE aud.ProyectoSolucionVivienda_aud DROP COLUMN psvPoseedorOcupanteVivienda;
ALTER TABLE aud.ProyectoSolucionVivienda_aud DROP COLUMN psvNumeroEscritura;
ALTER TABLE aud.ProyectoSolucionVivienda_aud DROP COLUMN psvFechaEscritura;
ALTER TABLE aud.ProyectoSolucionVivienda_aud DROP COLUMN psvUbicacionIgualProyecto;
ALTER TABLE aud.ProyectoSolucionVivienda_aud DROP COLUMN psvUbicacionVivienda;

--changeset mamonroy:02
--comment:
UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.matriculaInmobiliariaInmueble',
	JSON_VALUE(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.matriculaInmobiliariaInmueble'));

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.fechaRegistroEscritura',
	JSON_VALUE(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.fechaRegistroEscritura'));

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.loteUrbanizado',
	JSON_VALUE(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.loteUrbanizado'));


UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.poseedorOcupanteVivienda',
	JSON_VALUE(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.poseedorOcupanteVivienda'));

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.numeroEscritura',
	JSON_VALUE(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.numeroEscritura'));

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.fechaEscritura',
	JSON_VALUE(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.fechaEscritura'));

--UBICACION

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.autorizacionEnvioEmail',
	JSON_VALUE(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.autorizacionEnvioEmail'));

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.codigoPostal',
	JSON_VALUE(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.codigoPostal'));

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.descripcionIndicacion',
	JSON_VALUE(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.descripcionIndicacion'));

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.direccionFisica',
	JSON_VALUE(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.direccionFisica'));

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.email',
	JSON_VALUE(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.email'));

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.idMunicipio',
	JSON_VALUE(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.idMunicipio'));

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.idUbicacion',
	JSON_VALUE(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.idUbicacion'));

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.indicativoTelFijo',
	JSON_VALUE(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.indicativoTelFijo'));

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.telefonoCelular',
	JSON_VALUE(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.telefonoCelular'));

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.telefonoFijo',
	JSON_VALUE(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.telefonoFijo'));

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionViviendaMismaProyecto',
	JSON_VALUE(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionViviendaMismaProyecto'));

--DELETE

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.matriculaInmobiliariaInmueble',NULL);

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.fechaRegistroEscritura',NULL);

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.loteUrbanizado',NULL)

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.poseedorOcupanteVivienda',NULL)

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.numeroEscritura',NULL)

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.fechaEscritura',NULL)

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionViviendaMismaProyecto',NULL)

UPDATE postulacionFovis
SET pofInfoAsignacion = JSON_MODIFY(CAST(pofInfoAsignacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda',NULL)

--changeset mamonroy:03
--comment:
UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.matriculaInmobiliariaInmueble',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.matriculaInmobiliariaInmueble'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.fechaRegistroEscritura',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.fechaRegistroEscritura'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;


UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.loteUrbanizado',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.loteUrbanizado'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.poseedorOcupanteVivienda',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.poseedorOcupanteVivienda'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.numeroEscritura',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.numeroEscritura'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.fechaEscritura',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.fechaEscritura'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

--UBICACION

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.ubicacionVivienda.autorizacionEnvioEmail',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.ubicacionVivienda.autorizacionEnvioEmail'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.ubicacionVivienda.codigoPostal',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.ubicacionVivienda.codigoPostal'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.ubicacionVivienda.descripcionIndicacion',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.ubicacionVivienda.descripcionIndicacion'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.ubicacionVivienda.direccionFisica',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.ubicacionVivienda.direccionFisica'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.ubicacionVivienda.email',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.ubicacionVivienda.email'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.ubicacionVivienda.idMunicipio',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.ubicacionVivienda.idMunicipio'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.ubicacionVivienda.idUbicacion',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.ubicacionVivienda.idUbicacion'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.ubicacionVivienda.indicativoTelFijo',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.ubicacionVivienda.indicativoTelFijo'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.ubicacionVivienda.telefonoCelular',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.ubicacionVivienda.telefonoCelular'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.ubicacionVivienda.telefonoFijo',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.ubicacionVivienda.telefonoFijo'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.ubicacionViviendaMismaProyecto',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.ubicacionViviendaMismaProyecto'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

--DELETE

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.matriculaInmobiliariaInmueble',NULL)
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.fechaRegistroEscritura',NULL)
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.loteUrbanizado',NULL)
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.poseedorOcupanteVivienda',NULL)
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.numeroEscritura',NULL)
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.fechaEscritura',NULL)
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.ubicacionVivienda',NULL)
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.proyectoSolucionViviendaLegalizacion.ubicacionViviendaMismaProyecto',NULL)
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

--changeset mamonroy:04
--comment:
UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.matriculaInmobiliariaInmueble',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.matriculaInmobiliariaInmueble'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.fechaRegistroEscritura',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.fechaRegistroEscritura'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;


UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.loteUrbanizado',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.loteUrbanizado'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.poseedorOcupanteVivienda',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.poseedorOcupanteVivienda'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.numeroEscritura',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.numeroEscritura'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.fechaEscritura',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.fechaEscritura'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

--UBICACION

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.ubicacionVivienda.autorizacionEnvioEmail',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.ubicacionVivienda.autorizacionEnvioEmail'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.ubicacionVivienda.codigoPostal',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.ubicacionVivienda.codigoPostal'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.ubicacionVivienda.descripcionIndicacion',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.ubicacionVivienda.descripcionIndicacion'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.ubicacionVivienda.direccionFisica',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.ubicacionVivienda.direccionFisica'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.ubicacionVivienda.email',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.ubicacionVivienda.email'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.ubicacionVivienda.idMunicipio',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.ubicacionVivienda.idMunicipio'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.ubicacionVivienda.idUbicacion',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.ubicacionVivienda.idUbicacion'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.ubicacionVivienda.indicativoTelFijo',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.ubicacionVivienda.indicativoTelFijo'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.ubicacionVivienda.telefonoCelular',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.ubicacionVivienda.telefonoCelular'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.ubicacionVivienda.telefonoFijo',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.ubicacionVivienda.telefonoFijo'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.ubicacionViviendaMismaProyecto',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.ubicacionViviendaMismaProyecto'))
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

--DELETE

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.matriculaInmobiliariaInmueble',NULL)
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.fechaRegistroEscritura',NULL)
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;


UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.loteUrbanizado',NULL)
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.poseedorOcupanteVivienda',NULL)
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.numeroEscritura',NULL)
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.fechaEscritura',NULL)
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

--

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.ubicacionVivienda',NULL)
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacionFovis.postulacion.proyectoSolucionVivienda.ubicacionViviendaMismaProyecto',NULL)
FROM SolicitudLegalizacionDesembolso sld
JOIN Solicitud sol ON sol.solId = sld.sldSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

--changeset mamonroy:05
--comment:
UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.matriculaInmobiliariaInmueble',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.matriculaInmobiliariaInmueble'))
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.fechaRegistroEscritura',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.fechaRegistroEscritura'))
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.loteUrbanizado',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.loteUrbanizado'))
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.poseedorOcupanteVivienda',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.poseedorOcupanteVivienda'))
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.numeroEscritura',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.numeroEscritura'))
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.fechaEscritura',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.fechaEscritura'))
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

--UBICACION

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.ubicacionVivienda.autorizacionEnvioEmail',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.ubicacionVivienda.autorizacionEnvioEmail'))
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.ubicacionVivienda.codigoPostal',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.ubicacionVivienda.codigoPostal'))
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.ubicacionVivienda.descripcionIndicacion',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.ubicacionVivienda.descripcionIndicacion'))
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.ubicacionVivienda.direccionFisica',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.ubicacionVivienda.direccionFisica'))
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.ubicacionVivienda.email',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.ubicacionVivienda.email'))
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.ubicacionVivienda.idMunicipio',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.ubicacionVivienda.idMunicipio'))
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.ubicacionVivienda.idUbicacion',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.ubicacionVivienda.idUbicacion'))
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.ubicacionVivienda.indicativoTelFijo',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.ubicacionVivienda.indicativoTelFijo'))
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.ubicacionVivienda.telefonoCelular',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.ubicacionVivienda.telefonoCelular'))
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.ubicacionVivienda.telefonoFijo',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.ubicacionVivienda.telefonoFijo'))
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.ubicacionViviendaMismaProyecto',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.ubicacionViviendaMismaProyecto'))
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

--DELETE

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.matriculaInmobiliariaInmueble',NULL)
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.fechaRegistroEscritura',NULL)
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.loteUrbanizado',NULL)
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.poseedorOcupanteVivienda',NULL)
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.numeroEscritura',NULL)
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.fechaEscritura',NULL)
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

--

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.ubicacionVivienda',NULL)
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.datosPostulacion.postulacion.proyectoSolucionVivienda.ubicacionViviendaMismaProyecto',NULL)
FROM SolicitudNovedadFovis snf
JOIN Solicitud sol ON sol.solId = snf.snfSolicitudGlobal
JOIN DatoTemporalSolicitud dts ON sol.solId = dts.dtsSolicitud;

--changeset mamonroy:06
--comment:
UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.matriculaInmobiliariaInmueble',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.matriculaInmobiliariaInmueble'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.fechaRegistroEscritura',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.fechaRegistroEscritura'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.loteUrbanizado',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.loteUrbanizado'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.poseedorOcupanteVivienda',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.poseedorOcupanteVivienda'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.numeroEscritura',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.numeroEscritura'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.fechaEscritura',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.fechaEscritura'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

--UBICACION

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.autorizacionEnvioEmail',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.autorizacionEnvioEmail'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.codigoPostal',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.codigoPostal'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.descripcionIndicacion',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.descripcionIndicacion'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.direccionFisica',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.direccionFisica'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.email',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.email'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.idMunicipio',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.idMunicipio'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.idUbicacion',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.idUbicacion'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.indicativoTelFijo',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.indicativoTelFijo'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.telefonoCelular',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.telefonoCelular'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.telefonoFijo',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.telefonoFijo'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.ubicacionViviendaMismaProyecto',
	JSON_VALUE(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionViviendaMismaProyecto'))
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

--DELETE

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.matriculaInmobiliariaInmueble',NULL)
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.fechaRegistroEscritura',NULL)
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;


UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.loteUrbanizado',NULL)
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.poseedorOcupanteVivienda',NULL)
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.numeroEscritura',NULL)
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.fechaEscritura',NULL)
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionViviendaMismaProyecto',NULL)
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

UPDATE dts
SET dts.dtsJsonPayload = JSON_MODIFY(CAST(dtsJsonPayload AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda',NULL)
FROM Solicitud sol
JOIN SolicitudPostulacion spo on sol.solId = spo.spoSolicitudGlobal
JOIN DatoTemporalSolicitud dts on sol.solId = dts.dtsSolicitud;

--changeset mamonroy:07
--comment:
UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.matriculaInmobiliariaInmueble',
	JSON_VALUE(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.matriculaInmobiliariaInmueble'))
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.fechaRegistroEscritura',
	JSON_VALUE(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.fechaRegistroEscritura'))
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.loteUrbanizado',
	JSON_VALUE(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.loteUrbanizado'))
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.poseedorOcupanteVivienda',
	JSON_VALUE(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.poseedorOcupanteVivienda'))
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.numeroEscritura',
	JSON_VALUE(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.numeroEscritura'))
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.fechaEscritura',
	JSON_VALUE(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.fechaEscritura'))
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

--UBICACION

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.autorizacionEnvioEmail',
	JSON_VALUE(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.autorizacionEnvioEmail'))
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.codigoPostal',
	JSON_VALUE(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.codigoPostal'))
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.descripcionIndicacion',
	JSON_VALUE(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.descripcionIndicacion'))
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.direccionFisica',
	JSON_VALUE(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.direccionFisica'))
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.email',
	JSON_VALUE(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.email'))
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.idMunicipio',
	JSON_VALUE(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.idMunicipio'))
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.idUbicacion',
	JSON_VALUE(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.idUbicacion'))
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.indicativoTelFijo',
	JSON_VALUE(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.indicativoTelFijo'))
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.telefonoCelular',
	JSON_VALUE(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.telefonoCelular'))
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionVivienda.telefonoFijo',
	JSON_VALUE(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda.telefonoFijo'))
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.ubicacionViviendaMismaProyecto',
	JSON_VALUE(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionViviendaMismaProyecto'))
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

--DELETE

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.matriculaInmobiliariaInmueble',NULL)
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.fechaRegistroEscritura',NULL)
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.loteUrbanizado',NULL)
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.poseedorOcupanteVivienda',NULL)
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.numeroEscritura',NULL)
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.fechaEscritura',NULL)
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

--

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionVivienda',NULL)
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE sld
SET sld.sldJsonPostulacion = JSON_MODIFY(CAST(sldJsonPostulacion AS NVARCHAR(MAX)),'$.postulacion.proyectoSolucionVivienda.ubicacionViviendaMismaProyecto',NULL)
FROM SolicitudLegalizacionDesembolso sld
WHERE sldJsonPostulacion IS NOT NULL;

UPDATE ParametrizacionNovedad
SET novPuntoResolucion = 'BACK'
WHERE novTipoTransaccion IN ('ACTUALIZACION_TIPO_UNION_CONYUGE_PRESENCIAL','ACTUALIZACION_TIPO_UNION_CONYUGE_WEB');


--changeset mamonroy:08
--comment:
ALTER TABLE ParametrizacionSubsidioAjuste ALTER COLUMN psaValorCuotaAjuste numeric(19,5) NULL;
ALTER TABLE ParametrizacionSubsidioAjuste ALTER COLUMN psaValorCuotaAgrariaAjuste numeric(19,5) NULL;