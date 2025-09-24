-- =============================================
-- Author: Robinson Castillo 
-- Create Date: 2024-03-12
-- Description: Proceso encargado de procesar pasar los Id para procesamiento de B9. 
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_GET_ConsultarPlanillasLiberacionBloque9_V2]
as
SET NOCOUNT ON

	SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
	begin transaction;

		begin try
			drop table if exists #yaNotificaron
			drop table if exists #Planillas

			if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'procesarIdsB9')
				begin
					create table dbo.procesarIdsB9 (pipId bigint, pipIdPlanilla bigInt, pipCodigoOperadorInformacion varchar(2), fecha datetime, pebEstadoBloque9 varchar(50))
				end

			select p.pipId, p.pipIdPlanilla, p.pipCodigoOperadorInformacion, dateadd(hh, -5, getdate()) as fecha, pb.pebEstadoBloque9
			into #Planillas
			from dbo.PilaIndicePlanilla as p with (nolock)
			inner join dbo.PilaEstadoBloque as pb with (nolock) on p.pipId = pb.pebIndicePlanilla
			inner join staging.RegistroGeneral as r with (nolock) on p.pipId = r.regRegistroControl
			where pb.pebEstadoBloque10 is null
			and pb.pebEstadoBloque9 is not null
			and pb.pebEstadoBloque8 is not null
			and isnull(pb.pebEstadoBloque9,'') = 'PROCESADO_NOVEDADES'
			and not exists (select * from procesarIdsB9 with (nolock) where pipId = p.pipId)

			insert #Planillas
			select p.pipId, p.pipIdPlanilla, p.pipCodigoOperadorInformacion, dateadd(hh, -5, getdate()) as fecha, pb.pebEstadoBloque9
			from dbo.PilaIndicePlanilla as p with (nolock)
			inner join dbo.PilaEstadoBloque as pb with (nolock) on p.pipId = pb.pebIndicePlanilla
			inner join staging.RegistroGeneral as r with (nolock) on p.pipId = r.regRegistroControl
			where pb.pebEstadoBloque10 is null
			and pb.pebEstadoBloque9 is not null
			and pb.pebEstadoBloque8 is not null
			and isnull(pb.pebEstadoBloque9,'') = 'PROCESADO_SIN_NOVEDADES'
			and not exists (select * from procesarIdsB9 with (nolock) where pipId = p.pipId)

			--alter table dbo.procesarIdsB9 add pebEstadoBloque9 varchar(50)

			insert dbo.procesarIdsB9 (pipId, pipIdPlanilla, pipCodigoOperadorInformacion, fecha, pebEstadoBloque9)
			select pipId, pipIdPlanilla, pipCodigoOperadorInformacion, fecha, pebEstadoBloque9
			from #Planillas

			select pipId 
			into #yaNotificaron
			from dbo.procesarIdsB9
			intersect 
			select pipId
			from dbo.PilaIndicePlanilla as p with (nolock)
			inner join dbo.PilaEstadoBloque as pb with (nolock) on p.pipId = pb.pebIndicePlanilla
			inner join staging.RegistroGeneral as r with (nolock) on p.pipId = r.regRegistroControl
			where pb.pebEstadoBloque10 is not null
			and pb.pebEstadoBloque9 is not null
			and pb.pebEstadoBloque8 is not null

			declare @cont1 int = (select count(*) from #yaNotificaron)
			while 1 = 1
				begin
					delete top (@cont1) from dbo.procesarIdsB9 where pipId in (select pipId from #yaNotificaron)
					if @@ROWCOUNT = 0 break;
				end


			drop table if exists #nov
			drop table if exists #sinNov
			
			declare @tabla as table (id smallInt identity(1,1) primary key, PROCESADO_NOVEDADES bigInt, PROCESADO_SIN_NOVEDADES bigInt)


			select pipId, row_number() over (order by fecha, pipIdPlanilla) as id
			into #nov
			from dbo.procesarIdsB9
			where pebEstadoBloque9 = 'PROCESADO_NOVEDADES' and fecha <= dateadd(mi, -3, dbo.GetLocalDate())
			order by fecha, pipIdPlanilla
			offset 0 rows fetch next 20 rows only
			
			select pipId, row_number() over (order by fecha, pipIdPlanilla) as id
			into #sinNov
			from dbo.procesarIdsB9
			where pebEstadoBloque9 = 'PROCESADO_SIN_NOVEDADES' and fecha <= dateadd(mi, -3, dbo.GetLocalDate())
			order by fecha, pipIdPlanilla
			offset 0 rows fetch next 20 rows only
			
			insert @tabla (PROCESADO_NOVEDADES)
			select pipId from #nov
			
			update a set PROCESADO_SIN_NOVEDADES = b.pipId
			from @tabla as a
			inner join #sinNov as b on a.id = b.id 
			
			
			declare @fecha datetime = dbo.getLocalDate()
			update dbo.procesarIdsB9 set fecha = @fecha where pipId in (select pipId from #nov)
			update dbo.procesarIdsB9 set fecha = @fecha where pipId in (select pipId from #sinNov)
			
			select PROCESADO_NOVEDADES, PROCESADO_SIN_NOVEDADES
			from @tabla


		commit transaction;

		end try
		begin catch

			if @@TRANCOUNT > 0
			rollback transaction

		end catch