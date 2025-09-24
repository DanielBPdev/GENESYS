--liquibase formatted sql

--changeset mamonroy:01
--comment: CC Novedades - Fecha de inicio de condición de invalidez
ALTER TABLE CondicionInvalidez ADD coiFechaInicioInvalidez DATE;
ALTER TABLE aud.CondicionInvalidez_aud ADD coiFechaInicioInvalidez DATE;
