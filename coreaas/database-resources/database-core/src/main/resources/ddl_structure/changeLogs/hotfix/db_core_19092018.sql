--liquibase formatted sql

--changeset dsuesca:01
--comment: Se agrega campo dprFechaProgramadaPago
ALTER TABLE DetalleSubsidioAsignadoProgramado ADD dprFechaProgramadaPago DATE;
ALTER TABLE aud.DetalleSubsidioAsignadoProgramado_aud ADD dprFechaProgramadaPago DATE;