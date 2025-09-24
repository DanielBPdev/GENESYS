create or alter procedure [dbo].[OP_RegistroDetalladoPlanillaN_Rendimiento] 
as

declare @fechaAtras varchar(30) = convert(varchar(30),DATEADD(hh,-24, current_timestamp), 121)
declare @query nvarchar(max)

	

	--Inicio Merge tabla RegistroDetalladoPlanillaN

	
	declare @RegistroDetalladoPlanillaN1 table (redId	bigint not null,redRegistroGeneral	bigint not null,redRegistroDetalladoAnterior	bigint null,ultimaFechaModificacion	datetime null
	,operacion int not null,comando int null,secuencia binary(10), origen varchar (250) null)

		set @query = N' 
		select redId, redRegistroGeneral, redRegistroDetalladoAnterior, tran_end_time, __$operation, __$command_id, __$seqval as secuencia
		from cdc.staging_RegistroDetalladoPlanillaN_CT as a
			inner join cdc.lsn_time_mapping as t on a.__$start_lsn = t.start_lsn 
		where a.__$operation <> 3 and t.tran_end_time >= ' + char(39) + @fechaAtras + char(39) 


		insert @RegistroDetalladoPlanillaN1
		execute sp_execute_remote PilaReferenceData, @query

		drop table if exists #RegistroDetalladoPlanillaN1

		;with a as (
			select *,
			case when ultimaFechaModificacion = max(ultimaFechaModificacion) over (partition by redId) then 1 else null end as act
			,row_number() over (partition by redId order by ultimaFechaModificacion desc, operacion desc, secuencia desc, comando desc ) as actId
			from @RegistroDetalladoPlanillaN1)
		select *
		into #RegistroDetalladoPlanillaN1
		from a
		--where act = 1 and actId = 1  SE QUITA FILTRO PARA REALIZAR ACTUALIZACIONES DE ACUERDO AL COMANDO MAS RECIENTE.

		update red 
		set red.redId = a.redId, red.redRegistroGeneral = a.redRegistroGeneral, red.redRegistroDetalladoAnterior = a.redRegistroDetalladoAnterior, red.ultimaFechaModificacion = a.ultimaFechaModificacion
		output inserted.redId, inserted.redRegistroGeneral, inserted.redRegistroDetalladoAnterior, inserted.ultimaFechaModificacion, 4 as operacion, null as origen into dbo.RegistroDetalladoPlanillaN_log
		from #RegistroDetalladoPlanillaN1 as a
			inner join dbo.RegistroDetalladoPlanillaN as red on a.redId = red.redId
		where (a.ultimaFechaModificacion > red.ultimaFechaModificacion or red.ultimaFechaModificacion is null)
			and a.operacion = 4  and a.actId = (select MIN(actId) from #RegistroDetalladoPlanillaN1)



		drop table if exists #redIds
		create table #redIds (redId bigInt)
		insert #redIds
		select redId from #RegistroDetalladoPlanillaN1
		except 
		select redId from dbo.RegistroDetalladoPlanillaN

		insert dbo.RegistroDetalladoPlanillaN (redId,redRegistroGeneral,redRegistroDetalladoAnterior,ultimaFechaModificacion)
		output inserted.redId, inserted.redRegistroGeneral, inserted.redRegistroDetalladoAnterior
		,inserted.ultimaFechaModificacion, 2 as operacion, null as origen into dbo.RegistroDetalladoPlanillaN_log
		select redId,redRegistroGeneral,redRegistroDetalladoAnterior,ultimaFechaModificacion
		from #RegistroDetalladoPlanillaN1
		where redId in (select redId from #redIds)
			and actId = 1

		delete from dbo.RegistroDetalladoPlanillaN
		output deleted.redId, deleted.redRegistroGeneral, deleted.redRegistroDetalladoAnterior
		,deleted.ultimaFechaModificacion, 1 as operacion, null as origen into dbo.RegistroDetalladoPlanillaN_log
		where redId in (select redId from #RegistroDetalladoPlanillaN1 where operacion = 1 and actId = 1)


	