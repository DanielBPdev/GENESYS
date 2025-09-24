--liquibase formatted sql

--changeset dsuesca:01
--comment: 
ALTER TABLE DetalleSubsidioAsignado_aud ADD dsaDetalleSolicitudAnulacionSubsidioCobrado BIGINT;