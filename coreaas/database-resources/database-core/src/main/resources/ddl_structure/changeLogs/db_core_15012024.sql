if not exists (select * from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME = 'condicionInvalidez' and TABLE_SCHEMA = 'dbo' and COLUMN_NAME = 'coiConyugeCuidador')
begin
    alter table condicionInvalidez
    add coiConyugeCuidador bit
end
if not exists (select * from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME = 'CondicionInvalidez' and TABLE_SCHEMA = 'dbo' and COLUMN_NAME = 'coiFechaInicioconyugeCuidador')
begin
    alter table CondicionInvalidez
    add coiFechaInicioconyugeCuidador date
end
if not exists (select * from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME = 'condicionInvalidez' and TABLE_SCHEMA = 'dbo' and COLUMN_NAME = 'coiFechaFinconyugeCuidador')
begin
    alter table condicionInvalidez
    add coiFechaFinconyugeCuidador date
end
if not exists (select * from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME = 'condicionInvalidez' and TABLE_SCHEMA = 'dbo' and COLUMN_NAME = 'coiIdConyugeCuidador')
begin
    alter table condicionInvalidez
    add coiIdConyugeCuidador bigint
end

--aud
if not exists (select * from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME = 'condicionInvalidez_aud' and TABLE_SCHEMA = 'aud' and COLUMN_NAME = 'coiConyugeCuidador')
begin
    alter table aud.condicionInvalidez_aud
    add coiConyugeCuidador bit
end
if not exists (select * from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME = 'condicionInvalidez_aud' and TABLE_SCHEMA = 'aud' and COLUMN_NAME = 'coiFechaInicioconyugeCuidador')
begin
    alter table aud.condicionInvalidez_aud
    add coiFechaInicioconyugeCuidador date
end
if not exists (select * from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME = 'condicionInvalidez_aud' and TABLE_SCHEMA = 'aud' and COLUMN_NAME = 'coiFechaFinconyugeCuidador')
begin
    alter table aud.condicionInvalidez_aud
    add coiFechaFinconyugeCuidador date
end
if not exists (select * from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME = 'condicionInvalidez_aud' and TABLE_SCHEMA = 'aud' and COLUMN_NAME = 'coiIdConyugeCuidador')
begin
    alter table aud.condicionInvalidez_aud
    add coiIdConyugeCuidador bigint
end

