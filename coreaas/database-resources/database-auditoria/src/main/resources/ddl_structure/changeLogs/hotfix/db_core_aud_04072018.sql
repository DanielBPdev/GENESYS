--liquibase formatted sql

--changeset fvasquez:01
--comment: Se agrega campo bcaNumeroOperacion
ALTER TABLE BitacoraCartera_aud add bcaNumeroOperacion varchar(12) NULL;
