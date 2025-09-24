if not exists (select * from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME = 'condicionInvalidez_aud' and TABLE_SCHEMA = 'dbo' and COLUMN_NAME = 'coiConyugeCuidador')
begin
    alter table condicionInvalidez_aud
    add coiConyugeCuidador bit
end
if not exists (select * from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME = 'condicionInvalidez_aud' and TABLE_SCHEMA = 'dbo' and COLUMN_NAME = 'coiFechaInicioconyugeCuidador')
begin
    alter table condicionInvalidez_aud
    add coiFechaInicioconyugeCuidador date
end
if not exists (select * from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME = 'condicionInvalidez_aud' and TABLE_SCHEMA = 'dbo' and COLUMN_NAME = 'coiFechaFinconyugeCuidador')
begin
    alter table condicionInvalidez_aud
    add coiFechaFinconyugeCuidador date
end
if not exists (select * from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME = 'condicionInvalidez_aud' and TABLE_SCHEMA = 'dbo' and COLUMN_NAME = 'coiIdConyugeCuidador')
begin
    alter table condicionInvalidez_aud
    add coiIdConyugeCuidador bigint
end