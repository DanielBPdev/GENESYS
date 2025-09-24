--liquibase formatted sql

--changeset abaquero:01
--comment: Se agregan campos a tabla staging.RegistroGeneral
ALTER TABLE staging.RegistroGeneral ADD regDiasMora smallint;
ALTER TABLE staging.RegistroGeneral ADD regFechaPagoAporte date;
ALTER TABLE staging.RegistroGeneral ADD regFormaPresentacion varchar(1);
ALTER TABLE staging.RegistroGeneral ADD regCantidadEmpleados int;
ALTER TABLE staging.RegistroGeneral ADD regCantidadAfiliados int;
ALTER TABLE staging.RegistroGeneral ADD regTipoPersona varchar(1);