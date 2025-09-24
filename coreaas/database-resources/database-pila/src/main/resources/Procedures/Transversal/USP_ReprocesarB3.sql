-- =============================================
-- Author: Keyner Vides
-- Create date: 2024-07-04
-- Description:	Proceso encargado de reprecesar planillas incoonsistentes en B3
-- =============================================
create or alter procedure [dbo].[USP_ReprocesarB3]
as
set nocount on;
begin
	set xact_abort on;
	set nocount on;
	set quoted_identifier on;

    begin try
		begin transaction;
			-- Identificamos las planillas
			drop table if exists #Planilla
			create table #Planilla (id int identity(0,1), pipid bigint, pipCodigoOperadorInformacion varchar(2),pipIdPlanilla bigint)
			insert into #Planilla
			select top 5 pipid,pipCodigoOperadorInformacion,pipIdPlanilla
			from dbo.PilaIndicePlanilla pip
			inner join dbo.PilaEstadoBloque peb on pip.pipid = peb.pebIndicePlanilla
			where isnull(pip.pipUsuario, '') != 'MIGRACION@ASOPAGOS.COM'
			and peb.pebEstadoBloque8 is null and peb.pebEstadoBloque9 is null and peb.pebEstadoBloque10 is null
			and isnull(pipEstadoArchivo,'') not in ('RECAUDO_NOTIFICADO')
			and (pip.pipTipoArchivo like 'ARCHIVO_OI_A%' and pebEstadoBloque3 = 'PAREJA_DE_ARCHIVOS_INCONSISTENTES' and pebEstadoBloque4 is null)
				--and not (pip.pipTipoArchivo like 'ARCHIVO_OI_I%' and pebEstadoBloque4 = 'PERSISTENCIA_ARCHIVO_COMPLETADA'))
			and (pip.pipTipoCargaArchivo != 'AUTOMATICA_WEB' or (pip.pipTipoCargaArchivo = 'AUTOMATICA_WEB' and isnull(pip.pipEstadoArchivo,'') != 'DESCARGADO'))
			and pipId not in (select pipid from LogProcesarPlanillaB3 where procesado > 2)
			order by pipidplanilla, pipid

			insert into #Planilla
			select pip.pipid,pip.pipCodigoOperadorInformacion,pip.pipIdPlanilla
			from PilaIndicePlanilla pip
			inner join PilaEstadoBloque peb on peb.pebIndicePlanilla = pip.pipId
			left join #Planilla p on pip.pipid = p.pipid and pip.pipCodigoOperadorInformacion = p.pipCodigoOperadorInformacion and pip.pipIdPlanilla = p.pipIdPlanilla
			where isnull(pip.pipUsuario, '') != 'MIGRACION@ASOPAGOS.COM'
			and peb.pebEstadoBloque0 != 'CARGADO_REPROCESO'
			and pip.pipTipoArchivo like 'ARCHIVO_OI_A%' and pebEstadoBloque2 in ('ESTRUCTURA_VALIDADA','ESTRUCTURA_VALIDADA_CON_INCONSISTENCIA')
			and peb.pebEstadoBloque3 is null and peb.pebEstadoBloque8 is null and peb.pebEstadoBloque9 is null and peb.pebEstadoBloque10 is null
			and isnull(pip.pipEstadoArchivo,'') not in ('RECAUDO_NOTIFICADO')
			and pebFechaBloque2 < dateadd(minute,2,pebFechaBloque2)
			and concat(pip.pipCodigoOperadorInformacion,pip.pipIdPlanilla) not in (select concat(pip2.pipCodigoOperadorInformacion,pip2.pipIdPlanilla)
			from PilaIndicePlanilla pip2
			inner join PilaEstadoBloque peb2 on peb2.pebIndicePlanilla = pip2.pipId
			where peb2.pebEstadoBloque0 = 'CARGADO_REPROCESO'
			and pip2.pipTipoArchivo like 'ARCHIVO_OI_AR%')
			and pip.pipId not in (select pipid from LogProcesarPlanillaB3 where procesado > 2)
			and p.pipid is null

			if (select count(*) from #Planilla) > 0
				begin
					-- ingresamos al log
					insert LogProcesarPlanillaB3(pipid,procesado)
					select p.pipid,0
					from #Planilla p
					left join LogProcesarPlanillaB3 l on p.pipid = l.pipid
					where l.pipid is null

					-- Buscamos pareja 
					drop table if exists #pipid
					create table #pipid (id int identity(0,1),pipid bigint )
					insert into #pipid
					select pip.pipid
					from #Planilla p
					inner join dbo.PilaIndicePlanilla pip on p.pipCodigoOperadorInformacion = pip.pipCodigoOperadorInformacion and p.pipIdPlanilla = pip.pipIdPlanilla
					order by pip.pipIdPlanilla, pip.pipid


						update peb set pebEstadoBloque6  = null
						from dbo.PilaIndicePlanilla pip
						inner join dbo.PilaEstadoBloque peb on pip.pipid = peb.pebIndicePlanilla
						where pip.pipid in (select pipid from #pipid)

						update pip set pip.pipEstadoArchivo = 'CARGADO'
						from dbo.PilaIndicePlanilla pip
						inner join dbo.PilaEstadoBloque peb on pip.pipid = peb.pebIndicePlanilla
						where pip.pipid in (select pipid from #pipid)

						declare @pipid varchar(max)
						select  @pipid = concat(string_agg(pipid,','),'|')
						from #Planilla;
						print(@pipid);
						exec  [dbo].[USP_ReprocesarPlanillaM1] @pipid
						update t set procesado = procesado+1
						from LogProcesarPlanillaB3 t
						where pipid in (select pipid from #Planilla);	

						insert into staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
						values (dbo.getLocalDate(),'USP_ReprocesarB3 | @fechaCorte=' + cast(getdate() as varchar(30)),'Fin');
			end
				else
					begin
						begin tran
							insert into staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
							values (dbo.getLocalDate(),'USP_ReprocesarB3 | @fechaCorte=' + CAST(getdate() AS VARCHAR(30)),'PilaProceso Inactivo');
						commit;
					end;
		commit transaction;
    end try
    begin catch
		rollback transaction;
        insert into staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
        values (dbo.getLocalDate(),'USP_ReprocesarB3 | @fechaCorte=' + cast(getdate() as varchar(30)) ,error_message());
        throw
    end catch;
end;