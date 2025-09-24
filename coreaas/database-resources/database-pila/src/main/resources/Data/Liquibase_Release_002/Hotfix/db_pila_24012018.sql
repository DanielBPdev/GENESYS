--liquibase formatted sql

--changeset hhernandez:01
--comment:Se modifica columna pscPlantillaFinanciera de la tabla PilaSolicitudCambioNumIdentAportante para solucionar el mantis 0232000
ALTER TABLE PilaSolicitudCambioNumIdentAportante ALTER COLUMN pscIdPlanillaFinanciera BIGINT NULL;