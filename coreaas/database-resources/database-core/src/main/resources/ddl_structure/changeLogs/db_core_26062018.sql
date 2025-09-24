--liquibase formatted sql

--changeset alquintero:01
--comment: Insercion tabla ParametrizacionEjecucionProgramada
INSERT ParametrizacionEjecucionProgramada (pepProceso, pepHoras, pepMinutos, pepSegundos, pepFrecuencia, pepEstado) VALUES('EJECUTAR_INSERCION_DETALLESPROGRAMADOS_A_DETALLESSUBSIDIOS',00,00,00,'DIARIO','ACTIVO');

--changeset clmarin:02
--comment: Ajustes campos solResultadoProceso y solResultadoProceso por cambio de checks
ALTER TABLE Solicitud ALTER COLUMN solResultadoProceso VARCHAR(30); 
ALTER TABLE aud.Solicitud_aud ALTER COLUMN solResultadoProceso VARCHAR(30);
UPDATE SolicitudCierreRecaudo SET sciEstadoSolicitud = 'CERRADA' WHERE sciEstadoSolicitud = 'APROBADO_REGISTROS_CONCILIADOS';
UPDATE Solicitud SET solResultadoProceso = 'APROBADO_REGISTROS_CONCILIADOS' WHERE solResultadoProceso = 'CERRADA';

