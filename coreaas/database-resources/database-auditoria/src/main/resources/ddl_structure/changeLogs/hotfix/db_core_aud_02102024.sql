if not exists (select * from information_schema.columns where table_schema = 'dbo' and table_name = 'CicloAsignacion_aud' and column_name = 'ciaEstadoCalificacion')
begin
    Alter table CicloAsignacion_aud
    add ciaEstadoCalificacion varchar (30), ciaEjecucionProgramada bigint

    Alter table CalificacionPostulacion_aud
    add cafValorAdicional NUMERIC(12,6), cafEjecutado bit
end