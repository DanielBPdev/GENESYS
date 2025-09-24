--liquibase formatted sql

--changeset clmarin:01
--comment:
ALTER TABLE DocumentoSoporte_aud ADD dosBitacoraCartera bigint NULL;

--changeset mamonroy:02
--comment:
ALTER TABLE BeneficiarioDetalle_aud ADD bedTipoUnionConyugal VARCHAR(11);
