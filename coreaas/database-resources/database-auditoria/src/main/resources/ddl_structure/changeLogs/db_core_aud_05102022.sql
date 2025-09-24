--liquibase formatted sql

--changeset mamonroy:01
--comment:GLPI 57808 - eprocess
ALTER TABLE solicitudaporte_aud ADD soaCuentaBancariaRecaudo int null;

ALTER TABLE aporteGeneral_aud ADD apgCuentaBancariaRecaudo int null;
