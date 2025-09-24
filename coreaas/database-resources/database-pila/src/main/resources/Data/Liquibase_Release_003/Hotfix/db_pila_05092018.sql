--liquibase formatted sql

--changeset abaquero:01
--comment: Se agrega el campo con la fecha de pago de la planilla asociada en staging.RegistroGeneral
ALTER TABLE staging.RegistroGeneral ADD regFechaPagoPlanillaAsociada date