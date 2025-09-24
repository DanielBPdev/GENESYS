--liquibase formatted sql

--changeset jzambrano:01
--comment:Se adiciona campo en la tabla Solicitud_aud
ALTER TABLE Solicitud_aud ADD solAnulada BIT NULL;

--changeset borozco:02
--comment:Se adicionan campos en la tabla ActividadCartera_aud
ALTER TABLE ActividadCartera_aud ADD acrFechaCompromiso DATETIME;

--changeset borozco:03
--comment:Se modifica tipo de dato de columna acrFechaCompromiso
ALTER TABLE ActividadCartera_aud ALTER COLUMN acrFechaCompromiso DATE;