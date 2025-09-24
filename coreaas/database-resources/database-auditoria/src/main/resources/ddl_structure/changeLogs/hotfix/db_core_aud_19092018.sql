--liquibase formatted sql

--changeset dsuesca:01
--comment: Se agrega campo dprFechaProgramadaPago
ALTER TABLE DetalleSubsidioAsignadoProgramado_aud ADD dprFechaProgramadaPago DATE;