--liquibase formatted sql

--changeset mamonroy:01
--comment:
ALTER TABLE PostulacionFovis_aud ADD pofMatriculaInmobiliariaInmueble VARCHAR(50);
ALTER TABLE PostulacionFovis_aud ADD pofFechaRegistroEscritura DATE;
ALTER TABLE PostulacionFovis_aud ADD pofLoteUrbanizado BIT;
ALTER TABLE PostulacionFovis_aud ADD pofPoseedorOcupanteVivienda VARCHAR(50);
ALTER TABLE PostulacionFovis_aud ADD pofNumeroEscritura VARCHAR(20);
ALTER TABLE PostulacionFovis_aud ADD pofFechaEscritura DATE;
ALTER TABLE PostulacionFovis_aud ADD pofUbicacionIgualProyecto BIT;
ALTER TABLE PostulacionFovis_aud ADD pofUbicacionVivienda BIGINT;

ALTER TABLE ProyectoSolucionVivienda_aud DROP COLUMN psvMatriculaInmobiliariaInmueble;
ALTER TABLE ProyectoSolucionVivienda_aud DROP COLUMN psvFechaRegistroEscritura;
ALTER TABLE ProyectoSolucionVivienda_aud DROP COLUMN psvLoteUrbanizado;
ALTER TABLE ProyectoSolucionVivienda_aud DROP COLUMN psvPoseedorOcupanteVivienda;
ALTER TABLE ProyectoSolucionVivienda_aud DROP COLUMN psvNumeroEscritura;
ALTER TABLE ProyectoSolucionVivienda_aud DROP COLUMN psvFechaEscritura;
ALTER TABLE ProyectoSolucionVivienda_aud DROP COLUMN psvUbicacionIgualProyecto;
ALTER TABLE ProyectoSolucionVivienda_aud DROP COLUMN psvUbicacionVivienda;