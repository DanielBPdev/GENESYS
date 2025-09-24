-- =============================================
-- Author: Robinson Castillo
-- Create Date: 2023-10-17
-- Description: Objeto encargado de crear las devoluciones masivas a nivel general
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[ASP_SimularAportesDevolucionMasivosCore]
( @radicado varchar(30), @idMedioPago bigInt )
AS
BEGIN

    SET NOCOUNT ON
		
		begin transaction

			begin try

				--execute [dbo].[ASP_SimularAportesDevolucionMasivos] N'012023469857'
				drop table if exists #MovimientoAporteTemp
				--=======================================
				--====== Insertamos movimientoAporte
				--=======================================
				
				--declare @radicado varchar(30)= '012023469857'
				
				drop table if exists #MovimientoAporteTemp
				create table #MovimientoAporteTemp ([moaId] [bigint] NOT NULL,[moaTipoAjuste] [varchar](20) NULL,[moaTipoMovimiento] [varchar](23) NULL,[moaEstadoAporte] [varchar](22) NULL,
				[moaValorAporte] [numeric](19, 5) NULL,[moaValorInteres] [numeric](19, 5) NULL,[moaFechaActualizacionEstado] [datetime] NULL,[moaFechaCreacion] [datetime] NULL,[moaAporteDetallado] [bigint] NULL,
				[moaAporteGeneral] [bigint] NOT NULL)
				
				declare @pilaDev as table (apgId bigInt, apdId bigInt, solId bigInt, origen varchar(250))
				insert @pilaDev
				execute sp_execute_remote pilaReferenceData, N'
				select ddet.apgId, ddet.apdId, dgen.idSolicitud
				from masivos.aportesDevolucionDetalle as ddet
				inner join masivos.aportesDevolucionGeneralSimular as dgen on ddet.apgId = dgen.apgId and ddet.apgPeriodoAporte = dgen.apgPeriodoAporte and ddet.numeroRadicado = dgen.numeroRadicado
				where ddet.numeroRadicado = @radicado', N'@radicado varchar(30)', @radicado = @radicado
				
				insert core.dbo.MovimientoAporte (moaTipoAjuste,moaTipoMovimiento,moaEstadoAporte,moaValorAporte,moaValorInteres,moaFechaActualizacionEstado,moaFechaCreacion,moaAporteDetallado,moaAporteGeneral)
				output
				inserted.moaId
				,inserted.moaTipoAjuste
				,inserted.moaTipoMovimiento
				,inserted.moaEstadoAporte
				,inserted.moaValorAporte
				,inserted.moaValorInteres
				,inserted.moaFechaActualizacionEstado
				,inserted.moaFechaCreacion
				,inserted.moaAporteDetallado
				,inserted.moaAporteGeneral
				into #MovimientoAporteTemp
				
				SELECT 'DEVOLUCION' AS moaTipoAjuste,
				'DEVOLUCION_APORTES' AS moaTipoMovimiento,
				'CORREGIDO' AS moaEstadoMovimiento,
				apd.apdAporteObligatorio as moaMontoAportes, 
				apd.apdValorIntMora as moaMontoIntereses,
				dbo.getLocalDate() as moaFechaActualizacionEstado,
				dbo.getLocalDate() AS moaFechaCreacion,
				apd.apdId as moaAporteDetallado,
				apd.apdAporteGeneral as moaAporteGeneral
				from @pilaDev as dev
				inner join dbo.aporteDetallado as apd with (nolock) on apd.apdId = dev.apdId and apd.apdAporteGeneral = dev.apgId

				--=======================================
				---=== Insert Devolucion aporte. 
				--=======================================
				
				declare @pilaDevAporte as table (apgId bigInt, solId bigInt, motivoDev varchar(250), destinatario varchar(70), usuario varchar(70), perTipoIdentificacion varchar(25), perNumeroIdentificacion varchar(25),otroCaja varchar(50), origen varchar(250))
				insert @pilaDevAporte
				execute sp_execute_remote pilaReferenceData, N'
				select dgen.apgId, dgen.idSolicitud, ma.maaMotivoDevolucion, ma.maaDestinatario, ma.maaUsuario, dgen.perTipoIdentificacion, dgen.perNumeroIdentificacion, ma.maaOtroCaja
				from masivos.aportesDevolucionDetalle as ddet
				inner join masivos.aportesDevolucionGeneralSimular as dgen on ddet.apgId = dgen.apgId and ddet.apgPeriodoAporte = dgen.apgPeriodoAporte and ddet.numeroRadicado = dgen.numeroRadicado
				inner join masivos.MasivoArchivo as ma  on ma.maaNumeroRadicacion = ddet.numeroRadicado
				where ddet.numeroRadicado = @radicado
				group by  dgen.apgId, dgen.idSolicitud, ma.maaMotivoDevolucion, ma.maaDestinatario, ma.maaUsuario, dgen.perTipoIdentificacion, dgen.perNumeroIdentificacion, ma.maaOtroCaja',N'@radicado varchar(30)', @radicado = @radicado
				
				insert core.dbo.DevolucionAporte (dapFechaRecepcion, dapMotivoPeticion, dapDestinatarioDevolucion, dapCajaCompensacion, dapOtroDestinatario, dapMontoAportes, dapMontoIntereses
				, dapPeriodoReclamado, dapMedioPago, dapSedeCajaCompensacion, dapDescuentoGestionPagoOI, dapDescuentoGestionFinanciera, dapDescuentoOtro, dapOtraCaja, dapOtroMotivo, apgIdNuevo)
				select 
				dbo.getLocalDate() as dapFechaRecepcion,
				pdev.motivoDev as dapMotivoPeticion,
				pdev.destinatario as dapDestinatarioDevolucion,
				pdev.otroCaja  as dapCajaCompensacion,
				null as dapOtroDestinatario,
				apg.apgValTotalApoObligatorio as dapMontoAportes,
				apg.apgValorIntMora as dapMontoIntereses,
				convert(bigint,DATEDIFF_BIG(ms, '1969-12-31 19:00:00', convert(date,concat(apg.apgPeriodoAporte, N'-01')))) as  dapPeriodoReclamado,
				@idMedioPago as dapMedioPago,
				null as dapSedeCajaCompensacion,
				null as dapDescuentoGestionPagoOI,
				null as dapDescuentoGestionFinanciera,
				null as dapDescuentoOtro,
				null as dapOtraCaja,
				null as dapOtroMotivo,
				apg.apgId as apgIdNuevo
				from @pilaDevAporte as pdev 
				inner join core.dbo.AporteGeneral as apg with (nolock) on apg.apgId = pdev.apgId
				
				--=======================================
				---=== Insert Devolucion aporteDetalle. 
				--=======================================
				declare @dadUsuario varchar(70) = (select top 1 solUsuarioRadicacion from dbo.solicitud with (nolock) where solNumeroRadicacion = @radicado)
				
				INSERT INTO core.dbo.DevolucionAporteDetalle (dadIncluyeAporteObligatorio,dadIncluyeMoraCotizante,dadComentarioHistorico,dadComentarioNovedades,dadComentarioAportes,dadUsuario,dadFechaGestion,dadDevolucionAporte,dadMovimientoAporte)
				SELECT 
				1 as dadIncluyeAporteObligatorio
				,1 as dadIncluyeMoraCotizante
				,'OK' as dadComentarioHistorico
				,'OK' as dadComentarioNovedades
				,'OK' as dadComentarioAportes
				,@dadUsuario as dadUsuario
				,dev.dapFechaRecepcion as dadFechaGestion
				,dev.dapId as dadDevolucionAporte
				,moa.moaId as dadMovimientoAporte 
				FROM #MovimientoAporteTemp as moa
				inner join dbo.DevolucionAporte as dev with (nolock) on moa.moaAporteGeneral = dev.apgIdNuevo
				

				insert dbo.SolicitudDevolucionAporte (sdaEstadoSolicitud,sdaTipoSolicitante,sdaPersona,sdaObservacionAnalista,sdaObservacionSupervisor,sdaResultadoAnalista,sdaResultadoSupervisor,sdaDevolucionAporte,sdaSolicitudGlobal)
				select N'CERRADA' as sdaEstadoSolicitud, apg.apgTipoSolicitante as sdaTipoSolicitante, p.perId as sdaPersona, 
				null as sdaObservacionAnalista, null as sdaObservacionSupervisor, N'APROBADA' as sdaResultadoAnalista, N'APROBADA' as sdaResultadoSupervisor, dev.dapId as sdaDevolucionAporte, a.solId as sdaSolicitudGlobal
				from @pilaDevAporte as a
				inner join dbo.persona as p with (nolock) on p.perTipoIdentificacion = a.perTipoIdentificacion and p.perNumeroIdentificacion = a.perNumeroIdentificacion
				inner join dbo.DevolucionAporte as dev with (nolock) on a.apgId = dev.apgIdNuevo
				inner join dbo.aporteGeneral as apg with (nolock) on a.apgId = apg.apgId
				left join SolicitudDevolucionAporte as sda with (nolock) on sdaDevolucionAporte = dev.dapId
				where sda.sdaid is null

				--=======================================
				---=== Se actualiza el valor del aporteDetalle y aporteGeneral
				--=======================================

				update apd set apd.apdAporteObligatorio = 0, apd.apdValorIntMora = 0
				from #MovimientoAporteTemp as a
				inner join dbo.aporteDetallado as apd on apd.apdId = a.moaAporteDetallado and apd.apdAporteGeneral = a.moaAporteGeneral

				update apg set apg.apgValTotalApoObligatorio = 0, apg.apgValorIntMora = 0
				from #MovimientoAporteTemp as a
				inner join dbo.AporteGeneral as apg on apg.apgId = a.moaAporteGeneral


				execute sp_execute_remote pilaReferenceData, N'
				update ma set ma.maaEstado = ''FINALIZADO''
				from masivos.masivoArchivo ma
				join masivos.masivoGeneralDevolucion mgd on mgd.mgdMasivoArchivo = ma.maaId 
				inner join masivos.aportesDevolucionGeneralSimular as mgds on ma.maaNumeroRadicacion = mgds.numeroRadicado
				where ma.maaNumeroRadicacion = @radicado', N'@radicado varchar(30)', @radicado = @radicado


			end try
			begin catch

				if @@TRANCOUNT > 0
					rollback transaction;


				    SELECT   
        ERROR_NUMBER() AS ErrorNumber  
        ,ERROR_SEVERITY() AS ErrorSeverity  
        ,ERROR_STATE() AS ErrorState  
        ,ERROR_PROCEDURE() AS ErrorProcedure  
        ,ERROR_LINE() AS ErrorLine  
        ,ERROR_MESSAGE() AS ErrorMessage;

				select 'Entro al error'

			end catch

		if @@TRANCOUNT > 0
		commit transaction;

END