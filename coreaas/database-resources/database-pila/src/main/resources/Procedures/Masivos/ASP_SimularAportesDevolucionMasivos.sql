-- =============================================
-- Author: Robinson Castillo
-- Create Date: 2023-10-22
-- Description: Proceso encargado de descartar y simular la devolución masiva de aportes. 
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[ASP_SimularAportesDevolucionMasivos]
(
	 @numeroRadicado varchar (25)
)
AS
BEGIN

    SET NOCOUNT ON

		drop table if exists #devValidar
		drop table if exists #devProcesarTotal
		drop table if exists #devProcesar
		drop table if exists #devNoProcesarPorAporteManual
		drop table if exists #devNoProcesarPorPlanilla
		drop table if exists #devNoProcesarPorSubsidio
		drop table if exists #registrosGeneralesDev
		drop table if exists #consultarAportesCore

		/*
		drop table masivos.aportesDevolucionGeneralSimular
		create table masivos.aportesDevolucionGeneralSimular
		(iden int identity (1,1), id int, idSolicitud bigInt,apgId bigInt, apgFechaRecaudo date, apgModalidadPlanilla varchar(50), apgApoConDetalle varchar(2), pipIdPlanilla bigInt, 
		estadoArchivo varchar(30), tipoArchivoDetalle varchar(3), regTipoPlanilla varchar(2), apgPeriodoAporte varchar(7), tieneModificaciones varchar(2), apgValTotalApoObligatorio numeric(19,5), 
		apgValorIntMora numeric(19,5), totalAporte numeric(19,5), perTipoIdentificacion varchar(25), perNumeroIdentificacion varchar(25), perRazonSocial varchar(70), perPrimerNombre varchar(50), 
		perPrimerApellido varchar(50), numeroRadicado varchar(25), apgEstadoAporteAportante varchar(40), apgModalidadRecaudoAporte varchar(30), soaEstadoSolicitud varchar(40),radicadoSimulado varchar(30), solIdRadicadoSimulado bigInt)
		*/
		select *
		into #devValidar
		from masivos.aportesDevolucion
		where numeroRadicado = @numeroRadicado
		and isnull(apgEstadoAporteAportante,'') = 'VIGENTE'
		and estadoArchivo in ('RECAUDO_NOTIFICADO','PROCESADO_SIN_NOVEDADES','PROCESADO_NOVEDADES')

		select *
		into #devNoProcesarPorPlanilla
		from masivos.aportesDevolucion
		where numeroRadicado = @numeroRadicado
		and isnull(apgEstadoAporteAportante,'') != 'VIGENTE'
		or estadoArchivo not in ('RECAUDO_NOTIFICADO','PROCESADO_SIN_NOVEDADES','PROCESADO_NOVEDADES')
		
		;with devProc as (
				select *, case when apgModalidadRecaudoAporte = 'MANUAL' and soaEstadoSolicitud = 'CERRADA' then 1
							else
								case when apgModalidadRecaudoAporte = 'PILA' then 1
									else 0
								end
							end as procesar
				from #devValidar)
			select *
			into #devProcesarTotal
			from devProc

			select *
			into #devProcesar
			from #devProcesarTotal
			where procesar = 1

			select *
			into #devNoProcesarPorAporteManual
			from #devProcesarTotal
			where procesar = 0
		
			--=================================================
			--== Ajuste para mejorar el rendimiento. 
			--=================================================

			create table #consultarAportesCore (apgId bigInt, apdId bigInt, tipoSolicitante varchar(30), tipoCotizante varchar(75), aporteObligatorio numeric(19,5), valorIntereses numeric(19,5), valorTotalAporte numeric(19,5), origen varchar(250))

			declare @tblConsultarAportes as table (id int identity(1,1), apgId bigInt)
			insert @tblConsultarAportes (apgId)
			select t.apgId 
			from (
			select apgId
			from #devProcesar
			union all 
			select apgId
			from #devNoProcesarPorAporteManual) as t

			declare @cont int = 1
			declare @total int = (select count(*) from @tblConsultarAportes)

			while @cont <= @total
				begin
					
					declare @ids varchar(max) = ''
					select @ids = @ids + convert(varchar(20),apgId) + N',' from (select apgId from @tblConsultarAportes where id >= @cont and id < @cont + 1000) as t
					select @ids = left(@ids, len(@ids) -1)

					declare @query nvarchar(max) = N'select
														apg.apgId,
														apd.apdId,
														apg.apgTipoSolicitante as tipoSolicitante,
														apd.apdTipoCotizante as tipoCotizante,
														apd.apdAporteObligatorio as aporteObligatorio,
														apd.apdValorIntMora as valorIntereses,
														apd.apdAporteObligatorio + apd.apdValorIntMora as valorTotalAporte
														from dbo.aporteGeneral as apg with (nolock)
														inner join dbo.aporteDetallado as apd with (nolock) on apg.apgId = apd.apdAporteGeneral
														where apgId in (' + @ids + N')'

					insert #consultarAportesCore
					execute sp_execute_remote coreReferenceData, @query

					set @cont += 1000

				end

		begin transaction T1

		begin try

			declare @tieneSub bit = (select maaValSubsidio from masivos.MasivoArchivo as ma where maaNumeroRadicacion = @numeroRadicado)
			declare @idSolicitud bigInt = (select maaSolicitud from masivos.MasivoArchivo where maaNumeroRadicacion = @numeroRadicado)
		
			if @tieneSub = 0
				begin

				select ROW_NUMBER() over (order by apgId) - 1 as id, @idSolicitud idSolicitud, apgId, apgFechaRecaudo, pipIdPlanilla, apgPeriodoAporte, apgValTotalApoObligatorio, apgValorIntMora, perTipoIdentificacion, perNumeroIdentificacion, 
				perRazonSocial, perPrimerNombre, perPrimerApellido, numeroRadicado, apgEstadoAporteAportante, apgModalidadRecaudoAporte, soaEstadoSolicitud, apgModalidadPlanilla, apgApoConDetalle, estadoArchivo, tipoArchivoDetalle, regTipoPlanilla, tieneModificaciones, totalAporte
				into #devNoProcesarPorSubsidio
				from #devProcesar a
				where exists (select 1 from masivos.aportesDevolucionDetalle as b where tieneSub = 1 and a.apgId = b.apgId and a.apgPeriodoAporte = b.apgPeriodoAporte and a.numeroRadicado = b.numeroRadicado)
				group by apgId, apgFechaRecaudo, pipIdPlanilla, apgPeriodoAporte, apgValTotalApoObligatorio, apgValorIntMora, perTipoIdentificacion, perNumeroIdentificacion, perRazonSocial, perPrimerNombre, perPrimerApellido, numeroRadicado, apgEstadoAporteAportante, 
				apgModalidadRecaudoAporte, soaEstadoSolicitud, apgModalidadPlanilla, apgApoConDetalle, estadoArchivo, tipoArchivoDetalle, regTipoPlanilla, tieneModificaciones, totalAporte

				;with idsGenerales as (
				select ROW_NUMBER() over (order by apgId) - 1 as id, @idSolicitud idSolicitud, apgId, apgFechaRecaudo, pipIdPlanilla, apgPeriodoAporte, apgValTotalApoObligatorio, apgValorIntMora, perTipoIdentificacion, perNumeroIdentificacion, 
				perRazonSocial, perPrimerNombre, perPrimerApellido, numeroRadicado, apgEstadoAporteAportante, apgModalidadRecaudoAporte, soaEstadoSolicitud, apgModalidadPlanilla, apgApoConDetalle, estadoArchivo, tipoArchivoDetalle, regTipoPlanilla, tieneModificaciones, totalAporte
				from #devProcesar as a
				where not exists (select 1 from masivos.aportesDevolucionDetalle as b where tieneSub = 1 and a.apgId = b.apgId and a.apgPeriodoAporte = b.apgPeriodoAporte and a.numeroRadicado = b.numeroRadicado)
				group by apgId, apgFechaRecaudo, pipIdPlanilla, apgPeriodoAporte, apgValTotalApoObligatorio, apgValorIntMora, perTipoIdentificacion, perNumeroIdentificacion, perRazonSocial, perPrimerNombre, perPrimerApellido, numeroRadicado, apgEstadoAporteAportante, 
				apgModalidadRecaudoAporte, soaEstadoSolicitud, apgModalidadPlanilla, apgApoConDetalle, estadoArchivo, tipoArchivoDetalle, regTipoPlanilla, tieneModificaciones, totalAporte
				order by a.apgId
				offset 0 rows fetch next 1000000 rows only
				)
				insert masivos.aportesDevolucionGeneralSimular (
				id
				,idSolicitud
				,apgId
				,apgFechaRecaudo
				,apgModalidadPlanilla
				,apgApoConDetalle
				,pipIdPlanilla
				,estadoArchivo
				,tipoArchivoDetalle
				,regTipoPlanilla
				,apgPeriodoAporte
				,tieneModificaciones
				,apgValTotalApoObligatorio
				,apgValorIntMora
				,totalAporte
				,perTipoIdentificacion
				,perNumeroIdentificacion
				,perRazonSocial
				,perPrimerNombre
				,perPrimerApellido
				,numeroRadicado
				,apgEstadoAporteAportante
				,apgModalidadRecaudoAporte
				,soaEstadoSolicitud
				,radicadoSimulado
				)
				select 
				id
				,idSolicitud
				,apgId
				,apgFechaRecaudo
				,apgModalidadPlanilla
				,apgApoConDetalle
				,pipIdPlanilla
				,estadoArchivo
				,tipoArchivoDetalle
				,regTipoPlanilla
				,apgPeriodoAporte
				,tieneModificaciones
				,apgValTotalApoObligatorio
				,apgValorIntMora
				,totalAporte
				,perTipoIdentificacion
				,perNumeroIdentificacion
				,perRazonSocial
				,perPrimerNombre
				,perPrimerApellido
				,numeroRadicado
				,apgEstadoAporteAportante
				,apgModalidadRecaudoAporte
				,soaEstadoSolicitud
				,case when id = 0 then numeroRadicado
							else CONCAT(numeroRadicado, N'_', id)
							end radicadoSimulado
				from idsGenerales

				end
			else

				begin
				;with idsGenerales as (
				select ROW_NUMBER() over (order by apgId) - 1 as id, @idSolicitud idSolicitud, *
				from #devProcesar as a
				where exists (select 1 from masivos.aportesDevolucionDetalle as b where a.apgId = b.apgId and a.apgPeriodoAporte = b.apgPeriodoAporte and a.numeroRadicado = b.numeroRadicado)
				order by a.apgId
				offset 0 rows fetch next 1000000 rows only
				)
				insert masivos.aportesDevolucionGeneralSimular (
				id
				,idSolicitud
				,apgId
				,apgFechaRecaudo
				,apgModalidadPlanilla
				,apgApoConDetalle
				,pipIdPlanilla
				,estadoArchivo
				,tipoArchivoDetalle
				,regTipoPlanilla
				,apgPeriodoAporte
				,tieneModificaciones
				,apgValTotalApoObligatorio
				,apgValorIntMora
				,totalAporte
				,perTipoIdentificacion
				,perNumeroIdentificacion
				,perRazonSocial
				,perPrimerNombre
				,perPrimerApellido
				,numeroRadicado
				,apgEstadoAporteAportante
				,apgModalidadRecaudoAporte
				,soaEstadoSolicitud
				,radicadoSimulado
				)
				select 
				id
				,idSolicitud
				,apgId
				,apgFechaRecaudo
				,apgModalidadPlanilla
				,apgApoConDetalle
				,pipIdPlanilla
				,estadoArchivo
				,tipoArchivoDetalle
				,regTipoPlanilla
				,apgPeriodoAporte
				,tieneModificaciones
				,apgValTotalApoObligatorio
				,apgValorIntMora
				,totalAporte
				,perTipoIdentificacion
				,perNumeroIdentificacion
				,perRazonSocial
				,perPrimerNombre
				,perPrimerApellido
				,numeroRadicado
				,apgEstadoAporteAportante
				,apgModalidadRecaudoAporte
				,soaEstadoSolicitud
				,case when id = 0 then numeroRadicado
							else CONCAT(numeroRadicado, N'_', id)
							end radicadoSimulado
				from idsGenerales

			end

			---=== Se inserta en la tabla de errores de devoluciones las causales de rechazo para cada una de las condiciones evaluadas

			DECLARE @fechaHoraCarga date;
			DECLARE @nombreArchivo varchar(300);

			select top 1 @fechaHoraCarga = maaFechaProcesamiento, @nombreArchivo = maaNombreOriginalArchivo
			from masivos.masivoArchivo ma with (nolock)
			inner join masivos.masivoGeneralDevolucion mgd with (nolock) on mgd.mgdMasivoArchivo = ma.maaId 
			inner join masivos.aportesDevolucionGeneralSimular as mgds with (nolock) on ma.maaNumeroRadicacion = mgds.numeroRadicado
			where ma.maaNumeroRadicacion = @numeroRadicado


			if object_id('tempdb..#devNoProcesarPorPlanilla') is not null
				begin
						insert into masivos.MasivosDvGenerarRechazos2(numeroRegistro, fechaHoraDeCarga, nombreArchivo, tipoIdentificacionAportante,
						numeroIdentificacionAportante, razonSocial, periodoPago, tipoAportante, tipoIdentificacionCotizante, numeroIdentificacionCotizante,
						tipoCotizante, aporteObligatorio, valorIntereses, totalAporte, error, numeroRadicado)
							select ROW_NUMBER() OVER(order by res.numeroIdentificacion) as numeroRegistro,
							@fechaHoraCarga as fechaDeCarga,
							@nombreArchivo as nombreArchivo,
							res.tipoIdentificacion as tipoIdentificacion,
							res.numeroIdentificacion as numeroIdentificacion,
							res.razonSocial as razonSocial,
							res.periodoAporte as periodoPago,
							res.tipoSolicitante as tipoAportante,
							res.tipoIdentificacionCotizante as tipoIdentificacionCotizante,
							res.numeroIdentificacionCotizante as numeroIdentificacionCotizante,
							res.tipoCotizante as tipoCotizante,
							res.aporteObligatorio as aporteObligatorio,
							res.valorIntereses as valorIntereses,
							res.valorTotalAporte as valorTotalAporte,
							res.error as error,
							@numeroRadicado as numeroRadicado
						from
						 (select
							a.perTipoIdentificacion as tipoIdentificacion,
							a.perNumeroIdentificacion as numeroIdentificacion,
							a.perRazonSocial as razonSocial,
							a.apgPeriodoAporte as periodoAporte,
							ap.tipoSolicitante,
							b.perTipoIdentificacion as tipoIdentificacionCotizante,
							b.perNumeroIdentificacion as numeroIdentificacionCotizante,
							ap.tipoCotizante,
							ap.aporteObligatorio,
							ap.valorIntereses,
							ap.valorTotalAporte,
							'El aporte no es vigente' as error,
							@numeroRadicado as numeroRadicado
						from #devNoProcesarPorPlanilla a
						inner join masivos.aportesDevolucionDetalle as b on a.apgId = b.apgId and a.apgPeriodoAporte = b.apgPeriodoAporte and a.numeroRadicado = b.numeroRadicado
						inner join #consultarAportesCore as ap on a.apgId = ap.apgId and b.apdId = ap.apdId) as res

				end

			--if exists (select count(*) from #devNoProcesarPorSubsidio)
			if object_id('tempdb..#devNoProcesarPorSubsidio') is not null
				begin
				
						insert into masivos.MasivosDvGenerarRechazos2(numeroRegistro, fechaHoraDeCarga, nombreArchivo, tipoIdentificacionAportante,
						numeroIdentificacionAportante, razonSocial, periodoPago, tipoAportante, tipoIdentificacionCotizante, numeroIdentificacionCotizante,
						tipoCotizante, aporteObligatorio, valorIntereses, totalAporte, error, numeroRadicado)
						select
						ROW_NUMBER() OVER(order by a.perNumeroIdentificacion) as numeroRegistro,
						@fechaHoraCarga as fechaDeCarga,
						@nombreArchivo as nombreArchivo,
						a.perTipoIdentificacion as tipoIdentificacion,
						a.perNumeroIdentificacion as numeroIdentificacion,
						a.perRazonSocial as razonSocial,
						a.apgPeriodoAporte as periodoAporte,
						ap.tipoSolicitante,
						b.perTipoIdentificacion as tipoIdentificacionCotizante,
						b.perNumeroIdentificacion as numeroIdentificacionCotizante,
						ap.tipoCotizante,
						ap.aporteObligatorio,
						ap.valorIntereses,
						ap.valorTotalAporte,
						'El aporte ya fue reclamado en subsidio' as error,
						@numeroRadicado as numeroRadicado
						from #devNoProcesarPorSubsidio a
						inner join masivos.aportesDevolucionDetalle as b on a.apgId = b.apgId and a.apgPeriodoAporte = b.apgPeriodoAporte and a.numeroRadicado = b.numeroRadicado
						inner join #consultarAportesCore as ap on a.apgId = ap.apgId and b.apdId = ap.apdId
					
				end

			--if (select count(*) from #devNoProcesarPorAporteManual) > 0
			if object_id('tempdb..#devNoProcesarPorAporteManual') is not null
				begin
						insert into masivos.MasivosDvGenerarRechazos2(numeroRegistro, fechaHoraDeCarga, nombreArchivo, tipoIdentificacionAportante,
						numeroIdentificacionAportante, razonSocial, periodoPago, tipoAportante, tipoIdentificacionCotizante, numeroIdentificacionCotizante,
						tipoCotizante, aporteObligatorio, valorIntereses, totalAporte, error, numeroRadicado)
						select
						ROW_NUMBER() OVER(order by a.perNumeroIdentificacion) as numeroRegistro,
						@fechaHoraCarga as fechaDeCarga,
						@nombreArchivo as nombreArchivo,
						a.perTipoIdentificacion as tipoIdentificacion,
						a.perNumeroIdentificacion as numeroIdentificacion,
						a.perRazonSocial as razonSocial,
						a.apgPeriodoAporte as periodoAporte,
						ap.tipoSolicitante,
						b.perTipoIdentificacion as tipoIdentificacionCotizante,
						b.perNumeroIdentificacion as numeroIdentificacionCotizante,
						ap.tipoCotizante,
						ap.aporteObligatorio,
						ap.valorIntereses,
						ap.valorTotalAporte,
						'La solicitud del aporte manual no ha sido finalizada' as error,
						@numeroRadicado as numeroRadicado
						from #devNoProcesarPorAporteManual a
						inner join masivos.aportesDevolucionDetalle as b on a.apgId = b.apgId and a.apgPeriodoAporte = b.apgPeriodoAporte and a.numeroRadicado = b.numeroRadicado
						inner join #consultarAportesCore as ap on a.apgId = ap.apgId and b.apdId = ap.apdId

				end

			
			---=== Se actualiza estado a procesado cuando finaliza la devolución. 

			update ma set ma.maaEstado = 'SIMULADO'
			from masivos.masivoArchivo ma with (nolock)
			inner join masivos.masivoGeneralDevolucion mgd with (nolock) on mgd.mgdMasivoArchivo = ma.maaId 
			inner join masivos.aportesDevolucionGeneralSimular as mgds with (nolock) on ma.maaNumeroRadicacion = mgds.numeroRadicado
			where ma.maaNumeroRadicacion = @numeroRadicado

			end try
			begin catch
				SELECT ERROR_MESSAGE() AS ErrorMessage;  
				if @@TRANCOUNT > 0
				rollback transaction T1;
			end catch

			if @@TRANCOUNT > 0
			commit transaction T1

END