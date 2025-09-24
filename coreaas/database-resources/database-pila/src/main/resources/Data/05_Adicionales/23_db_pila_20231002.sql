
--liquibase formatted sql

--changeset arocha:01 


if not exists (select 1 from information_schema.columns where table_schema = 'dbo' and table_name = 'PilaArchivoIPRegistro2' and column_name = 'ip2Correcion')
alter table PilaArchivoIPRegistro2 add ip2Correcion varchar (1)
