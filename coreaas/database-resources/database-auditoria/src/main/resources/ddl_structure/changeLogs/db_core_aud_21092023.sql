if not exists (select 1 from information_schema.columns where table_schema = 'dbo' and table_name = 'PersonaDetalle_aud'  and column_name  = 'pedPuebloIndigena')
begin
    ALTER TABLE PersonaDetalle_aud ADD pedPuebloIndigena bigint

end
if not exists (select 1 from information_schema.columns where table_schema = 'dbo' and table_name = 'PersonaDetalle_aud'  and column_name  = 'pedResguardo')
begin
    ALTER TABLE PersonaDetalle_aud ADD pedResguardo bigint

end