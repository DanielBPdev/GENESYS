--liquibase formatted sql

--changeset alquintero:01
--comment: Actualizaciones tablas AporteGeneral y datotemporalsolicitud por cambio de valor enumerado
UPDATE AporteGeneral SET apgModalidadPlanilla = 'ELECTRONICA' WHERE apgModalidadPlanilla = 'UNICA';
UPDATE datotemporalsolicitud SET dtsJsonPayload = REPLACE ( CAST(dtsJsonPayload AS nvarchar(max)) , '"modalidadPlanilla":"UNICA"' , '"modalidadPlanilla":"ELECTRONICA"' ) WHERE dtsJsonPayload LIKE '%"modalidadPlanilla":"UNICA"%';
--changeset clmarin:01
--comment: Ajustes campos solResultadoProceso y solResultadoProceso por cambio de checks
ALTER TABLE Solicitud ALTER COLUMN solResultadoProceso VARCHAR(30); 
ALTER TABLE aud.Solicitud_aud ALTER COLUMN solResultadoProceso VARCHAR(30);

--changeset clmarin:02
--comment: Ajustes por campo de check
UPDATE SolicitudCierreRecaudo SET sciEstadoSolicitud = 'CERRADA' WHERE sciEstadoSolicitud = 'APROBADO_REGISTROS_CONCILIADOS';
UPDATE Solicitud SET solResultadoProceso = 'APROBADO_REGISTROS_CONCILIADOS' WHERE solResultadoProceso = 'CERRADA';
