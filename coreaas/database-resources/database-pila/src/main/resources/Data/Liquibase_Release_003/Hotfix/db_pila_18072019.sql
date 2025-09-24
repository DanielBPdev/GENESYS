--liquibase formatted sql

--changeset dsuesca:01
--comment:Datos por default a registros sin fecha
 UPDATE staging.RegistroGeneral SET regDateTimeUpdate = '1970-01-01T00:00:00' WHERE regDateTimeUpdate IS NULL;
 UPDATE staging.RegistroDetallado SET redDateTimeUpdate = '1970-01-01T00:00:00' WHERE redDateTimeUpdate IS NULL;
 UPDATE staging.RegistroDetalladoNovedad SET rdnDateTimeUpdate = '1970-01-01T00:00:00' WHERE rdnDateTimeUpdate IS NULL;
 UPDATE PilaErrorValidacionLog SET pevDateTimeUpdate = '1970-01-01T00:00:00' WHERE pevDateTimeUpdate IS NULL;
 UPDATE PilaEstadoBloque SET pebDateTimeUpdate = '1970-01-01T00:00:00' WHERE pebDateTimeUpdate IS NULL;
 UPDATE PilaIndicePlanilla SET pipDateTimeUpdate = '1970-01-01T00:00:00' WHERE pipDateTimeUpdate IS NULL;

--changeset dsuesca:02
--comment: Se crea tabla de control de procesos
 CREATE TABLE MarcaProcesamiento (
	mapFechaUltimaEjecucionUTC datetime NULL
);