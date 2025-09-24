--liquibase formatted sql

--changeset abaquero:01
--comment: Adición de campos de control de creación y actualización para reportes 

ALTER TABLE PilaIndicePlanilla ADD pipDateTimeInsert DATETIME;
ALTER TABLE PilaEstadoBloque ADD pebDateTimeInsert DATETIME;
ALTER TABLE PilaErrorValidacionLog ADD pevDateTimeInsert DATETIME;
ALTER TABLE Staging.RegistroGeneral ADD regDateTimeInsert DATETIME;
ALTER TABLE staging.RegistroDetallado ADD redDateTimeInsert DATETIME;
ALTER TABLE staging.RegistroDetalladoNovedad ADD rdnDateTimeInsert DATETIME;
ALTER TABLE PilaIndicePlanilla ADD pipDateTimeUpdate DATETIME;
ALTER TABLE PilaEstadoBloque ADD pebDateTimeUpdate DATETIME;
ALTER TABLE PilaErrorValidacionLog ADD pevDateTimeUpdate DATETIME;
ALTER TABLE Staging.RegistroGeneral ADD regDateTimeUpdate DATETIME;
ALTER TABLE staging.RegistroDetallado ADD redDateTimeUpdate DATETIME;
ALTER TABLE staging.RegistroDetalladoNovedad ADD rdnDateTimeUpdate DATETIME;