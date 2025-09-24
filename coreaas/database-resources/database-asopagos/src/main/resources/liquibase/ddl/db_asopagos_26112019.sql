--liquibase formatted sql

--changeset squintero:01
--comment:
ALTER TABLE ParametrizacionCondicionesSubsidio ADD pcsBaseIngresos varchar(50);