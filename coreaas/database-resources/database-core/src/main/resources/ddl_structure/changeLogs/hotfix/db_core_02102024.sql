
if not exists (select * from information_schema.columns where table_schema = 'dbo' and table_name = 'CicloAsignacion' and column_name = 'ciaEstadoCalificacion')
begin
	alter table CicloAsignacion
	add ciaEstadoCalificacion varchar (30), ciaEjecucionProgramada bigint
	
	Alter table aud.CicloAsignacion_aud
	add ciaEstadoCalificacion varchar (30), ciaEjecucionProgramada bigint
	
	-------------------------------------
	
	Alter table aud.CalificacionPostulacion_aud
	add cafValorAdicional NUMERIC(12,6), cafEjecutado bit
	
	Alter table CalificacionPostulacion
	add cafValorAdicional NUMERIC(12,6), cafEjecutado bit
	
	------------------------------------
	
	update parametro
	set prmNombre = 'PUNTAJE_REUBICACION_ALTO_RIESGO_NO_MITIGABLE'
	where prmNombre = 'PUNTAJE_FOVIS_ADICION_200_PUNTOS'
	
	------------------------------------
end
