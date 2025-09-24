-- =============================================
-- Author: Robinson Castillo 
-- Create Date: 2024-03-12
-- Description: Proceso encargado de procesar pasar los Id para procesamiento de B9. 
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_GET_ConsultarPlanillasLiberacionBloque9] (@estadoPlanilla varchar(30))

as
SET NOCOUNT ON

	begin transaction;

		begin try

			drop table if exists #yaNotificaron
			drop table if exists #Planillas

			if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'procesarIdsB9')
				begin
					create table dbo.procesarIdsB9 (pipId bigint, pipIdPlanilla bigInt, pipCodigoOperadorInformacion varchar(2), fecha datetime)
				end

			if @estadoPlanilla = 'PROCESADO_SIN_NOVEDAD'
				begin
					set @estadoPlanilla = 'PROCESADO_SIN_NOVEDADES'
				end
			else if @estadoPlanilla = 'PROCESADO_NOVEDADES'
				begin
					set @estadoPlanilla = 'PROCESADO_NOVEDADES'
				end
			else
				begin
					set @estadoPlanilla = ''
				end


			
			select p.pipId, p.pipIdPlanilla, p.pipCodigoOperadorInformacion, dateadd(hh, -5, getdate()) as fecha
			into #Planillas
			from dbo.PilaIndicePlanilla as p with (nolock)
			inner join dbo.PilaEstadoBloque as pb with (nolock) on p.pipId = pb.pebIndicePlanilla
			inner join staging.RegistroGeneral as r with (nolock) on p.pipId = r.regRegistroControl
			where pb.pebEstadoBloque10 is null
			and pb.pebEstadoBloque9 is not null
			and pb.pebEstadoBloque8 is not null
			and isnull(pb.pebEstadoBloque9,'') = @estadoPlanilla
			and not exists (select * from procesarIdsB9 with (nolock) where pipId = p.pipId)

			insert dbo.procesarIdsB9 (pipId, pipIdPlanilla, pipCodigoOperadorInformacion, fecha)
			select pipId, pipIdPlanilla, pipCodigoOperadorInformacion, fecha
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

			delete from dbo.procesarIdsB9 where pipId in (select pipId from #yaNotificaron)

			declare @cant tinyInt = 0

			if @estadoPlanilla = 'PROCESADO_SIN_NOVEDADES'
				begin
					set @cant = 20;
				end
			else
				begin
					set @cant = 12;
				end

			declare @tblPipId as table (pipId bigInt)

			insert @tblPipId
			select top (@cant) pipId
			from dbo.procesarIdsB9 with (nolock)
			where fecha <= dateadd(mi, -5, dbo.GetLocalDate())
			order by fecha, pipIdPlanilla

			update dbo.procesarIdsB9 set fecha = dbo.GetLocalDate() where pipId in (select pipId from @tblPipId)
			
			select pipId
			from @tblPipId

		end try
		begin catch
			if @@TRANCOUNT > 0
				rollback transaction
		end catch

	if @@TRANCOUNT > 0
		commit transaction