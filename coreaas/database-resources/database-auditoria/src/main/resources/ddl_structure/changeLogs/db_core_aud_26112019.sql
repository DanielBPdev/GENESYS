--liquibase formatted sql

--changeset squintero:01
--comment: CC IBC
ALTER TABLE ParametrizacionCondicionesSubsidio_aud ADD pcsBaseIngresos varchar(50);