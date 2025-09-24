
--changeset kvides
--comment: Creacion table LogProcesarPlanillaB3
if not exists (select 1 from information_schema.tables where table_type = 'BASE TABLE' and table_name = 'LogProcesarPlanillaB3' and table_schema = 'dbo')
begin
	create table LogProcesarPlanillaB3(id bigint identity(1,1),pipid bigint, procesado bigint)
end;