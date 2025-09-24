create or alter proc USP_Calculo_Novedades_Fovis_Pendientes (@idCicloAsignacion bigint)
as
begin
set nocount on;
	declare @FechaCalificacion datetime

	--identificar fecha ultima calificaci�n
	select @FechaCalificacion = max(cafFechaCalificacion)
	from CalificacionPostulacion
	where cafCicloAsignacion = @idCicloAsignacion

	--identificar fecha de cierre de novedad
	drop table if exists #FechaCierreNovedadFovis_buffer
	create table #FechaCierreNovedadFovis_buffer (snfId bigint primary key,FechaCierre datetime)
	insert #FechaCierreNovedadFovis_buffer
	select distinct snf.snfId, null FechaCierre
	from postulacionfovis pof
	inner join SolicitudNovedadPersonaFovis spf ON spf.spfPostulacionFovis = pof.pofId
	inner join SolicitudNovedadFovis snf ON spf.spfSolicitudNovedadFovis = snf.snfId
	where pofCicloAsignacion = @idCicloAsignacion
	and snfEstadoSolicitud = 'NOV_FOVIS_CERRADA'

	update t set FechaCierre = dateadd(second,r.revTimeStamp/1000, '19691231 19:00')
	from #FechaCierreNovedadFovis_buffer t
	join aud.SolicitudNovedadFovis_aud snf on t.snfId = snf.snfId and snfEstadoSolicitud = 'NOV_FOVIS_CERRADA'
	join aud.Revision r on snf.REV = r.revId

	drop table if exists #FechaCierreNovedadFovis_aud
	create table #FechaCierreNovedadFovis_aud (snfId bigint primary key,FechaCierre datetime, origen varchar(250))

	if (select top(1) 1 from #FechaCierreNovedadFovis_buffer where FechaCierre is null) = 1
	begin 

		insert #FechaCierreNovedadFovis_aud
		exec sp_execute_remote coreaudreferencedata,N'drop table if exists #FechaCierreNovedadFovis
		create table #FechaCierreNovedadFovis (snfId bigint primary key,FechaCierre datetime)
		insert #FechaCierreNovedadFovis
		select distinct snf.snfId, null FechaCierre
		from postulacionfovis_aud pof
		inner join SolicitudNovedadPersonaFovis_aud spf ON spf.spfPostulacionFovis = pof.pofId
		inner join SolicitudNovedadFovis_aud snf ON spf.spfSolicitudNovedadFovis = snf.snfId
		where pofCicloAsignacion = @idCicloAsignacion
		and snfEstadoSolicitud = ''NOV_FOVIS_CERRADA'';

		select t.snfId, max(dateadd(second,r.revTimeStamp/1000,''19691231 19:00'')) as FechaCierre
		from #FechaCierreNovedadFovis t
		join SolicitudNovedadFovis_aud snf on t.snfId = snf.snfId and snfEstadoSolicitud = ''NOV_FOVIS_CERRADA''
		join Revision r on snf.REV = r.revId
		group by t.snfId;
		',N'@idCicloAsignacion bigint',@idCicloAsignacion=@idCicloAsignacion
		
		update t set FechaCierre = dateadd(second,r.revTimeStamp/1000, '19691231 19:00')
		from #FechaCierreNovedadFovis_aud t
		join aud.SolicitudNovedadFovis_aud snf on t.snfId = snf.snfId and snfEstadoSolicitud = 'NOV_FOVIS_CERRADA'
		join aud.Revision r on snf.REV = r.revId

	end 

	drop table if exists #FechaCierreNovedadFovis
	create table #FechaCierreNovedadFovis (snfId bigint,FechaCierre datetime)
	insert #FechaCierreNovedadFovis
	select snfId,FechaCierre 
	from #FechaCierreNovedadFovis_aud
	union
	select snfId,FechaCierre 
	from #FechaCierreNovedadFovis_buffer
	order by 2 desc
	
	-----====================
	select distinct pof.*
	from postulacionfovis pof
	inner join SolicitudNovedadPersonaFovis spf ON spf.spfPostulacionFovis = pof.pofId
	inner join SolicitudNovedadFovis snf ON spf.spfSolicitudNovedadFovis = snf.snfId
	inner join Solicitud solnf ON snf.snfSolicitudGlobal = solnf.solId
	left join #FechaCierreNovedadFovis t on snf.snfId = t.snfId
	where pof.pofId in (select pof.pofId
								from PostulacionFovis pof
								inner join CicloAsignacion cia on (pof.pofCicloAsignacion = cia.ciaId)
								inner join SolicitudPostulacion spo on (pof.pofId = spo.spoPostulacionFovis)
								inner join SolicitudGestionCruce sgc on (spo.spoId = sgc.sgcSolicitudPostulacion and sgc.sgcEstadoCruceHogar not in ('CRUCE_RATIFICADO_PENDIENTE_VERIFICACION', 'CRUCES_RATIFICADOS') and sgc.sgcEstado = 'CERRADA')
								left join SolicitudGestionCruce sgcr on (spo.spoId = sgcr.sgcSolicitudPostulacion and (sgcr.sgcEstadoCruceHogar in ('CRUCE_RATIFICADO_PENDIENTE_VERIFICACION', 'CRUCES_RATIFICADOS') and sgcr.sgcEstado = 'CERRADA' or sgcr.sgcEstadoCruceHogar is null))
								left join Solicitud sol ON solId = sgcr.sgcSolicitudGlobal
								where pofEstadoHogar IN ('HABIL', 'HABIL_SEGUNDO_ANIO')
								and cia.ciaId = @idCicloAsignacion
								and (sgcr.sgcId is null or (sol.solInstanciaProceso is not null and sgcr.sgcId is not null)))
	and fechacierre > @FechaCalificacion
	and snfEstadoSolicitud = 'NOV_FOVIS_CERRADA'
	and solnf.solFechaRadicacion >= dateadd(hour,-48,dbo.GetLocalDate())

end

