-- =============================================
-- Author:		Juan Diego Ocampo Q
-- Create date: 2020-11-23
-- Description:	Reinicia el procesamiento de la planillas seleccionadas a partir de una fecha anterior en N días a la fecha actual, 
--              valor de los dias se determina con parametro en la tabla Parametro  de core.
-- Ajuste 2023-07-10 para evitar el borrado de los datos y errores en los datos, ajuste realizado por Robinson Castillo. 
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_ReiniciarPlanillas]
as
SET NOCOUNT ON

	begin transaction;

		begin try

		if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'procesarIdsConciliar')
			begin
				CREATE TABLE [dbo].[procesarIdsConciliar] ([pipId] [bigint] NULL, pipIdPlanilla bigInt, pipCodigoOperadorInformacion varchar(2), pipTipoArchivo varchar(70), total tinyInt, procesado bit, fecha datetime)
			end

			--========================================
			--========================================

			select p.pipId, p.pipIdPlanilla, p.pipCodigoOperadorInformacion, pipTipoArchivo, convert(date, pipFechaRecibo) as fecha1, pipFechaRecibo, p.pipEstadoArchivo
			into #ArchivoIACT
			from dbo.PilaIndicePlanilla as p with (nolock)
			inner join dbo.PilaEstadoBloque as pb with (nolock) on p.pipId = pb.pebIndicePlanilla
			where p.pipUsuario <> 'MIGRACION@ASOPAGOS.COM'
			and p.pipTipoArchivo like 'ARCHIVO_OI_I%' and pb.pebEstadoBloque6 = 'PENDIENTE_CONCILIACION' and p.pipEstadoArchivo = 'PENDIENTE_CONCILIACION'
			and (pebEstadoBloque7 is null and pebEstadoBloque8 is null and pebEstadoBloque9 is null and pebEstadoBloque10 is null)
			and p.pipFechaRecibo <= dateadd(hour, -1, dbo.getLocalDate())
			and p.pipEstadoArchivo is not null
			
			select p.pipId, p.pipIdPlanilla, p.pipCodigoOperadorInformacion, p.pipTipoArchivo, convert(date, p.pipFechaRecibo) as fecha1, p.pipFechaRecibo, p.pipEstadoArchivo
			into #ArchivoAACT
			from dbo.PilaIndicePlanilla as p with (nolock)
			inner join dbo.PilaEstadoBloque as pb with (nolock) on p.pipId = pb.pebIndicePlanilla
			where p.pipUsuario <> 'MIGRACION@ASOPAGOS.COM'
			and p.pipTipoArchivo like 'ARCHIVO_OI_A%' and p.pipEstadoArchivo = 'VALIDACIONES_FINALIZADAS' 
			and pb.pebEstadoBloque4 = 'VALIDACIONES_FINALIZADAS'


			--========================================
			--========================================

			;with plprocesoA as (
			select * from #ArchivoIACT
			union all 
			select * from #ArchivoAACT
			),
			plproceso2A as (select *, count(*) over (partition by pipIdPlanilla, pipCodigoOperadorInformacion) as total3
			from plprocesoA)
			select a.pipId, a.pipIdPlanilla, a.pipCodigoOperadorInformacion, a.pipTipoArchivo, a.total3, 0 as procesado, dbo.GetLocalDate() as fecha, row_number() over (order by fecha1, a.pipIdPlanilla, a.pipTipoArchivo) as id, 
			null as existeI1, null as existeI2, null as existeI3, null as existeA1 
			into #planillasRecaudoConciliado
			from plproceso2A as a
			--left join dbo.procesarIdsM1 as b with (nolock) on a.pipId = b.pipId
			where total3 = 2 --and b.pipId is null
			order by fecha1, pipIdPlanilla, pipTipoArchivo
			
			
			create clustered index IXNC_Id_pipTipoArchivo ON #planillasRecaudoConciliado ([id],[pipTipoArchivo])

			declare @contA int = 1 
			declare @totalA int = (select count(*) from #planillasRecaudoConciliado)
			
			while @contA <= @totalA
				begin 
					declare @pipidArchivoI_1 bigInt
					declare @pipidArchivoA_1 bigInt
					declare @extPilaI1_1 bit = 0
					declare @extPilaI2_1 bit = 0
					declare @extPilaI3_1 bit = 0
					declare @extPilaA1_1 bit = 0
			
					select @pipidArchivoI_1 = (select pipId from #planillasRecaudoConciliado where id = @contA and pipTipoArchivo like 'ARCHIVO_OI_I%')
					select @pipidArchivoA_1 = (select pipId from #planillasRecaudoConciliado where id = @contA and pipTipoArchivo like 'ARCHIVO_OI_A%')
			
					if @pipidArchivoI_1 is not null
						begin
								select top 1 @extPilaI1_1 = 1
								from dbo.PilaArchivoIRegistro1 with(nolock)
								where exists (select @pipidArchivoI_1)
								--where pi1IndicePlanilla = @pipidArchivoI_1
				
								select top 1 @extPilaI2_1 = 1
								from dbo.PilaArchivoIRegistro2 with(nolock)
								where pi2IndicePlanilla = @pipidArchivoI_1
								
								select top 1 @extPilaI3_1 = 1
								from dbo.PilaArchivoIRegistro3 with(nolock)
								where pi3IndicePlanilla = @pipidArchivoI_1
			
							update #planillasRecaudoConciliado set existeI1 = @extPilaI1_1, existeI2 = @extPilaI2_1, existeI3 = @extPilaI3_1 where id = @contA and pipTipoArchivo like 'ARCHIVO_OI_I%'
						end
			
					if @pipidArchivoA_1 is not null
						begin
								select top 1 @extPilaA1_1 = 1
								from dbo.PilaArchivoARegistro1 with(nolock)
								where pa1IndicePlanilla = @pipidArchivoA_1
			
							update #planillasRecaudoConciliado set existeA1 = @extPilaA1_1 where id = @contA and pipTipoArchivo like 'ARCHIVO_OI_A%'
						end
					
					
			
					set @contA += 1
			
				end
			
			--create nonclustered index IXNC_pa1IndicePlanilla on dbo.PilaArchivoARegistro1 (pa1IndicePlanilla)
			
			select *, case when pipTipoArchivo like 'ARCHIVO_OI_I%' and existeI1 = 1 and existeI2 = 1 and existeI3 = 1 then 1 else 0 end as ValidadorI,
			case when pipTipoArchivo like 'ARCHIVO_OI_A%' and existeA1 = 1 then 1 else 0 end as ValidadorA
			into #planillasRecaudoConciliado2
			from #planillasRecaudoConciliado
			
			
			;with validadorA as (
			select *, case when ValidadorA = 1 and (lead(ValidadorI) over (partition by pipIdPlanilla, pipCodigoOperadorInformacion order by pipTipoArchivo)) = 1 then 1 else null end as validadorFinal
			from #planillasRecaudoConciliado2),
			validador2A as (select a.pipId, a.pipIdPlanilla, a.pipCodigoOperadorInformacion, a.pipTipoArchivo
			from #planillasRecaudoConciliado2 as a
			inner join validadorA as b on a.pipIdPlanilla = b.pipIdPlanilla and a.pipCodigoOperadorInformacion = b.pipCodigoOperadorInformacion
			group by a.pipId, a.pipIdPlanilla, a.pipCodigoOperadorInformacion, a.pipTipoArchivo),
			validador3A as (
			select a.pipId, a.pipIdPlanilla, a.pipCodigoOperadorInformacion, a.pipTipoArchivo, 0 as procesado, dbo.GetLocalDate() as fecha, count(*) over (partition by p.pipIdPlanilla, p.pipCodigoOperadorInformacion) as total3, convert(date, p.pipFechaRecibo) as fecha1
			from dbo.PilaIndicePlanilla as p with (nolock)
			inner join dbo.PilaEstadoBloque as pb with (nolock) on p.pipId = pb.pebIndicePlanilla
			inner join validador2A as a on p.pipId = a.pipId
			order by p.pipIdPlanilla, p.piptipoArchivo
			offset 0 rows fetch next 1000000 rows only)
			select a.pipId, a.pipIdPlanilla, a.pipCodigoOperadorInformacion, a.pipTipoArchivo, a.total3, a.procesado, a.fecha1
			into #planillasPendientes
			from validador3A as a
			left join dbo.procesarIdsM1 as b with (nolock) on a.pipId = b.pipId
			where total3 = 2
			order by fecha1, a.pipIdPlanilla, a.pipTipoArchivo

			--==== Se insertan los registros faltantes. 
			insert dbo.procesarIdsConciliar
			select a.*
			from #planillasPendientes as a
			left join dbo.procesarIdsConciliar as b with (nolock) on a.pipId = b.pipId
			where b.pipId is null

			create clustered index IXNC_pipId on #planillasPendientes (pipId)

			--=== Se actualizan los que no se lograrón pasar. 
			/*
			update b set b.procesado = 0
			from dbo.procesarIdsConciliar as b
			where exists (select 1 from #planillasPendientes as a where a.pipId = b.pipId)
			*/
			--=== Se agrega limpiado del log, de las planillas notificadas. 

			select p.pipIdPlanilla, p.pipCodigoOperadorInformacion
			into #limpReg
			from dbo.PilaIndicePlanilla as p with (nolock)
			inner join dbo.PilaEstadoBloque as pb with (nolock) on p.pipId = pb.pebIndicePlanilla
			inner join staging.RegistroGeneral as r with (nolock) on p.pipId = r.regRegistroControl
			where pb.pebEstadoBloque7 is not null

			delete a
			from dbo.procesarIdsConciliar as a with (nolock)
			where exists (select * from #limpReg as p where p.pipIdPlanilla = a.pipIdPlanilla and p.pipCodigoOperadorInformacion = a.pipCodigoOperadorInformacion)
			option (recompile)


			;with planillaConciliada as (
			select p.pipId, p.pipIdPlanilla, p.pipCodigoOperadorInformacion, pipTipoArchivo, convert(date, pipFechaRecibo) as fecha1, pipFechaRecibo, p.pipEstadoArchivo
			from dbo.PilaIndicePlanilla as p with (nolock)
			inner join dbo.PilaEstadoBloque as pb with (nolock) on p.pipId = pb.pebIndicePlanilla
			where p.pipUsuario <> 'MIGRACION@ASOPAGOS.COM'
			and p.pipTipoArchivo like 'ARCHIVO_OI_I%' and pb.pebEstadoBloque6 in ('RECAUDO_CONCILIADO','RECAUDO_VALOR_CERO_CONCILIADO') and p.pipEstadoArchivo in ('RECAUDO_CONCILIADO', 'RECAUDO_VALOR_CERO_CONCILIADO')
			and (pebEstadoBloque7 is null and pebEstadoBloque8 is null and pebEstadoBloque9 is null and pebEstadoBloque10 is null)
			and not exists (select 1 from staging.RegistroGeneral as r with (nolock) where r.regRegistroControl = p.pipId)),
			pipConciliado as (select p.pipId
			from dbo.PilaIndicePlanilla as p
			inner join planillaConciliada as pc on p.pipIdPlanilla = pc.pipIdPlanilla and p.pipCodigoOperadorInformacion = pc.pipCodigoOperadorInformacion)
			delete a
			from dbo.procesarIdsConciliar as a with (nolock)
			where exists (select * from pipConciliado as p where a.pipId = p.pipId)
			option (recompile)


			delete a
			from dbo.procesarIdsConciliar as a with (nolock)
			inner join dbo.PilaIndicePlanilla as p with (nolock) on a.pipId = p.pipId 
			where isnull(p.pipEstadoArchivo,'') = 'ANULADO'
			option (recompile)


			update a set procesado = 0
			from dbo.procesarIdsConciliar as a with (nolock)
			where fecha <= dateadd(hh, -1, dbo.GetLocalDate())
			

			declare @tblIds as table (pipId bigInt)
			insert @tblIds
			select top 60 pipId
			from dbo.procesarIdsConciliar with (nolock)
			where fecha <= dateadd(mi, -5, dbo.GetLocalDate())
			and procesado = 0
			order by fecha, pipIdPlanilla, pipTipoArchivo

			update dbo.procesarIdsConciliar set fecha = dbo.GetLocalDate(), procesado = 1 where pipId in (select * from @tblIds as a) --=== Se actualizan los registros a procesados, pasan a la cola de espera
		
			select *
			from @tblIds

			--create clustered index IXC_id on dbo.procesarIdsConciliar (pipId) with (online = on)
			--CREATE NONCLUSTERED INDEX [IXNC_pa1IndicePlanilla] ON [dbo].[PilaArchivoARegistro1] ([pa1IndicePlanilla]) with (online = on)

		end try
		begin catch
			if @@TRANCOUNT > 0
				rollback transaction
		end catch

	if @@TRANCOUNT > 0
		commit transaction