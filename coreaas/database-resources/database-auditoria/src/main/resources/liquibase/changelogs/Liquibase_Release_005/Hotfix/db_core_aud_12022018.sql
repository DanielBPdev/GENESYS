--liquibase formatted sql

--changeset fvasquez:01
--comment:Se adiciona campo en la tabla MedioEfectivo_aud
ALTER TABLE MedioEfectivo_aud ADD mefSedeCajaCompensacion BIGINT NULL; 