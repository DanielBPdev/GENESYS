--liquibase formatted sql

--changeset jamartinez:01
--comment: GLPI 76565

if not exists (select * from INFORMATION_SCHEMA.COLUMNS where table_name ='aporteDetalladoRegistroControlN' and TABLE_SCHEMA ='dbo' and COLUMN_NAME = 'regCuentaBancariaRecaudo')
begin 
alter table aporteDetalladoRegistroControlN add regCuentaBancariaRecaudo varchar(20)
end

