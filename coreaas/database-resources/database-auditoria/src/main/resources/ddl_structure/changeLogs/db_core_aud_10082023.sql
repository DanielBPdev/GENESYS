--Se realiza alter a la tabla PostulacionAsignacion_aud de acuerdo al gap 65394



if exists (select 1 from information_schema.columns where table_schema = 'dbo' and table_name = 'PostulacionAsignacion_aud'  and column_name  = 'pasRecursoPrioridad')
print ('Existen')
else
begin
    alter table PostulacionAsignacion_aud add pasRecursoPrioridad varchar(50) 
end



