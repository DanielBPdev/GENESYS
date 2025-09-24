--liquibase formatted sql

--changeset dsuesca:01
--comment: CC 477
ALTER TABLE DetalleSubsidioAsignado_aud ADD dsaNombreTerceroPagado varchar(200);