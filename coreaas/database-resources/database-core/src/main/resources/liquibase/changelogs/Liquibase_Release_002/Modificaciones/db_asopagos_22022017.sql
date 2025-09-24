--liquibase formatted sql


--changeset  atoro:01
--comment:Se modifica tabla SolicitudNovedad donde se agrega nueva columna llamada snoObservaciones
ALTER TABLE SolicitudNovedad add snoObservaciones VARCHAR(200)