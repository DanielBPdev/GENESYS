--liquibase formatted sql

--changeset squintero:01
--comment:
ALTER TABLE ParametrizacionCondicionesSubsidio_aud ADD pcsBaseIngresos varchar(50);