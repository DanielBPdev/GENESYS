--liquibase formatted sql

--changeset jocorrea:01
--comment: Se agrega campo 
ALTER TABLE PostulacionFovis_aud ADD pofInfoAsignacion NVARCHAR(MAX);

--changeset jocorrea:02
--comment: Creacion campos
ALTER TABLE EjecucionProcesoAsincrono_aud ADD epsTotalProceso smallint;
ALTER TABLE EjecucionProcesoAsincrono_aud ADD epsAvanceProceso smallint;