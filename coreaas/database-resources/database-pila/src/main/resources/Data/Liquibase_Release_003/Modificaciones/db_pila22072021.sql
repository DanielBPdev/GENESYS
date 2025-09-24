--liquibase formatted sql

--changeset pparra:01
--comment: cambio campo bloque 4 'Persistencia archivo completada',
alter table PilaErrorValidacionLog alter column pevNombreCampo varchar (200)