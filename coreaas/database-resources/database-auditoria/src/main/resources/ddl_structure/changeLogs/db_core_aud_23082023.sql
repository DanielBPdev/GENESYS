--Se realiza alter a la tabla CondicionEspecialPersona_aud para el glpi 66317

if exists (select 1 from information_schema.columns where table_schema = 'dbo' and table_name = 'CondicionEspecialPersona_aud'  and column_name  = 'cepPostulacionFovis')
print ('Existen')
else
begin
    ALTER TABLE CondicionEspecialPersona_aud ADD cepPostulacionFovis bigint
end



