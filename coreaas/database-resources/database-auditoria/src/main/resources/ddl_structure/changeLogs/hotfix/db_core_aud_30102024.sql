if not exists (select * from information_schema.columns where table_schema = 'dbo' and table_name = 'SolicitudgestionCruce_aud' and column_name = 'sgcFechaValidacionCruce')
begin
    ALTER TABLE SolicitudgestionCruce_aud
    ADD sgcFechaValidacionCruce DATETIME
end