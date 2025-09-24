--liquibase formatted sql

--changeset abaquero:01 
--comment:  creación del campos PilaIndicePlanilla
alter table PilaIndicePlanilla add pipIdentificadorDocumento varchar(255);
alter table PilaIndicePlanilla add pipVersionDocumento smallint;

alter table PilaIndicePlanillaOF add pioFechaRecibo datetime;
alter table PilaIndicePlanillaOF add pioIdentificadorDocumento varchar(255);
alter table PilaIndicePlanillaOF add pioVersionDocumento smallint;