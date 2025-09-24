--liquibase formatted sql

--changeset dsuesca:01
--comment:
ALTER TABLE DetalleSubsidioAsignado ADD dsaNombreTerceroPagado varchar(200);
ALTER TABLE aud.DetalleSubsidioAsignado_aud ADD dsaNombreTerceroPagado varchar(200);