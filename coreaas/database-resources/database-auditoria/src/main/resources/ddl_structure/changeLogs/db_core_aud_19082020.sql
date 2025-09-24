--liquibase formatted sql

--changeset amarin:01
--comment:
ALTER TABLE ParametrizacionCondicionesSubsidio_aud ADD pcsSumarSalarioConyugeBenPadre BIT;
ALTER TABLE ParametrizacionCondicionesSubsidio_aud ADD pcsSumarSalarioConyugeBenHijo BIT;
ALTER TABLE ParametrizacionCondicionesSubsidio_aud ADD pcsSumarSalarioConyugeBenHermanoHuerfano BIT;