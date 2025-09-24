--liquibase formatted sql

--changeset flopez:01
--comment: CC Estado Civil
ALTER TABLE BeneficiarioDetalle_aud DROP COLUMN bedTipoUnionConyugal;
