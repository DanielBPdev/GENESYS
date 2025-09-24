--liquibase formatted sql

--changeset mamonroy:01
--comment: CC Novedades - Fecha de inicio de condición de invalidez
ALTER TABLE CondicionInvalidez_aud ADD coiFechaInicioInvalidez DATE;