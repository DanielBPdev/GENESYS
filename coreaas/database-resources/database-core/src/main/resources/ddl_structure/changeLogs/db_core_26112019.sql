--liquibase formatted sql

--changeset squintero:01
--comment: Conttrol de cambios IBC
ALTER TABLE ParametrizacionCondicionesSubsidio ADD pcsBaseIngresos varchar(50);
ALTER TABLE aud.ParametrizacionCondicionesSubsidio_aud ADD pcsBaseIngresos varchar(50);
