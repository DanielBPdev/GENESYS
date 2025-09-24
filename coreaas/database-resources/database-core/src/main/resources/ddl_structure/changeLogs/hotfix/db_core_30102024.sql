DELETE FROM ValidacionProceso
WHERE vapValidacion = 'VALIDACION_INGRESOS_TOTALES_PERMITIDOS' and vapBloque = '323-045-1'

if not exists (select * from information_schema.columns where table_schema = 'dbo' and table_name = 'SolicitudgestionCruce' and column_name = 'sgcFechaValidacionCruce')
begin
    ALTER TABLE SolicitudgestionCruce
    ADD sgcFechaValidacionCruce DATETIME

    ALTER TABLE aud.SolicitudgestionCruce_aud
    ADD sgcFechaValidacionCruce DATETIME
end