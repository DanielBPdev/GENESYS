
if not exists (select 1 from INFORMATION_SCHEMA.COLUMNS where TABLE_SCHEMA = 'dbo'
and TABLE_NAME = 'AccionCobro2C_aud' and COLUMN_NAME = 'aocDiasGeneracionActa')
begin
alter table AccionCobro2C_aud
add aocDiasGeneracionActa bigint
end