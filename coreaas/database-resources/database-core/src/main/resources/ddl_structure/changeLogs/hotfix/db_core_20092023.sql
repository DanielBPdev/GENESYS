if not exists (select 1 from information_schema.columns where table_schema = 'aud' and table_name = 'CondicionEspecialPersona_aud'  and column_name  = 'cepPostulacionFovis')
begin
    ALTER TABLE aud.CondicionEspecialPersona_aud ADD cepPostulacionFovis bigint
end