/****** Object:  StoredProcedure [dbo].[USP_Calculo_Novedades_Fovis_Pendientes]    Script Date: 19/12/2024 9:30:47 a. m. ******/
CREATE OR ALTER   proc [dbo].[USP_Calculo_Novedades_Fovis_Pendientes] (@idCicloAsignacion bigint)
as
begin
set nocount on;

	declare @FechaCalificacion datetime

	--identificar fecha ultima calificación
	select @FechaCalificacion = max(cafFechaCalificacion)
	from CalificacionPostulacion
	where cafCicloAsignacion = @idCicloAsignacion

	--identificar fecha de cierre de novedad
	drop table if exists #FechaCierreNovedadFovis
	create table #FechaCierreNovedadFovis (snfId bigint primary key,FechaCierre datetime)
	insert #FechaCierreNovedadFovis
	select snf.snfId, null FechaCierre
	from postulacionfovis pof
	inner join SolicitudNovedadPersonaFovis spf ON spf.spfPostulacionFovis = pof.pofId
	inner join SolicitudNovedadFovis snf ON spf.spfSolicitudNovedadFovis = snf.snfId
	where pofCicloAsignacion = @idCicloAsignacion
	and snfEstadoSolicitud = 'NOV_FOVIS_CERRADA'

	update t set FechaCierre = dateadd(second,r.revTimeStamp/1000, '19691231 19:00')
	from #FechaCierreNovedadFovis t
	join aud.SolicitudNovedadFovis_aud snf on t.snfId = snf.snfId and snfEstadoSolicitud = 'NOV_FOVIS_CERRADA'
	join aud.Revision r on snf.REV = r.revId

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
end