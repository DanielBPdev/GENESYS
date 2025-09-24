--liquibase formatted sql

--changeset dsuesca:01
--comment: 
ALTER TABLE DetalleSubsidioAsignado ADD dsaDetalleSolicitudAnulacionSubsidioCobrado BIGINT;

--changeset dsuesca:02
--comment: 
ALTER TABLE aud.DetalleSubsidioAsignado_aud ADD dsaDetalleSolicitudAnulacionSubsidioCobrado BIGINT;