--liquibase formatted sql
--changeset keynervides
--comment: 86571
if not exists (select 1 from information_schema.columns where table_schema = 'dbo' and table_name = 'Municipio_aud' and column_name = 'munCodigoDIARI') 
begin
alter table Municipio_aud add munCodigoDIARI varchar(5);
end