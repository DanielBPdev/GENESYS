if not exists (select 1 from information_schema.columns where table_schema = 'aud' and table_name = 'PersonaDetalle_aud'  and column_name  = 'pedResguardo')
begin
    ALTER TABLE aud.PersonaDetalle_aud ADD pedResguardo bigint
end
if not exists (select 1 from information_schema.columns where table_schema = 'aud' and table_name = 'PersonaDetalle_aud'  and column_name  = 'pedPuebloIndigena')
begin
    ALTER TABLE aud.PersonaDetalle_aud ADD pedPuebloIndigena bigint
end
if not exists (select 1 from information_schema.columns where table_schema = 'dbo' and table_name = 'PersonaDetalle'  and column_name  = 'pedResguardo')
begin
    ALTER TABLE PersonaDetalle ADD pedResguardo bigint
end
if not exists (select 1 from information_schema.columns where table_schema = 'dbo' and table_name = 'PersonaDetalle'  and column_name  = 'pedPuebloIndigena')
begin
    ALTER TABLE PersonaDetalle ADD pedPuebloIndigena bigint
end
if not exists (select 1 from information_schema.columns where table_schema = 'dbo' and table_name = 'puebloIndigena')
begin
CREATE TABLE puebloIndigena(
	puiId bigint IDENTITY(1,1) NOT NULL,
	puiDescripcion varchar(300) NOT NULL
)
end
if not exists (select 1 from information_schema.columns where table_schema = 'dbo' and table_name = 'resguardo')
begin
CREATE TABLE resguardo(
	resId bigint IDENTITY(1,1) NOT NULL,
	resDescripcion varchar(300) NOT NULL
)
end
