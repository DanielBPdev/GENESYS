    -- =======================================================
-- Create Stored Procedure Template for Azure SQL Database
-- =======================================================
-- =============================================
-- Author:      <Author, , Name>
-- Create Date: <Create Date, , >
-- Description: Proedimiento creado que borra persistencias en pila para reprocesar planillas mundo1
-- =============================================
CREATE PROCEDURE [dbo].[ASP_PILA_REPROCESO]
(
    -- Add the parameters for the stored procedure here
   @planillasIds bigint = 0
)
as
begin
	set nocount on;
	--pipId IndicePlanilla
	declare @PilaIndicePlanilla bigint, @idRegistroGeneral bigint, @idTransaccion bigint,  @idReg bigint
	declare @motivoProcesoManual varchar(20)
	declare @planillas table (pipId bigint)
	declare @reiniciarF bit = 0
	declare @numPlanilla varchar(10) = null
	declare @periodo varchar(16)
	declare @codOperador smallint
	declare @borradoTotal bit = 0
	
	
	declare @cursorNumeroPlanilla as cursor 
	set @cursorNumeroPlanilla = cursor fast_forward for 
	select pl.pipId, pl.pipMotivoProcesoManual
	from PilaIndicePlanilla pl
	where pl.pipId in (@planillasIds)
	 
	open @cursorNumeroPlanilla 
	 
	fetch next from @cursorNumeroPlanilla into @PilaIndicePlanilla, @motivoProcesoManual
	 
	while @@fetch_status = 0
	begin
		print @PilaIndicePlanilla
	
		declare @tipoArchivo varchar(75)
		select @tipoArchivo = i.pipTipoArchivo from dbo.PilaIndicePlanilla as i where i.pipId = @PilaIndicePlanilla
	
		select @numPlanilla = pi1.pi1NumPlanilla, @periodo = replace(pi1.pi1PeriodoAporte, '-', ''), @codOperador = pi1.pi1CodOperador
		from PilaArchivoIRegistro1 pi1
		where pi1.pi1IndicePlanilla = @PilaIndicePlanilla
	
		if @numPlanilla IS NULL
		begin
			select @numPlanilla = ip1.ip1NumPlanilla, @periodo = replace(ip1.ip1PeriodoAporte, '-', ''), @codOperador = ip1.ip1CodOperador
			from PilaArchivoIPRegistro1 ip1
			where ip1.ip1IndicePlanilla = @PilaIndicePlanilla
		end
	 
		
		if @borradoTotal = 0 and @motivoProcesoManual is not null
		begin
			update dbo.PilaIndicePlanilla set pipHabilitadoProcesoManual = 1 where pipId = @PilaIndicePlanilla
		end
	 
		select @idRegistroGeneral = rg.regId, @idTransaccion = rg.regTransaccion 
		from staging.RegistroGeneral as rg where rg.regRegistroControl = @PilaIndicePlanilla
	       
		delete from dbo.TemAporteProcesado where tprAporteGeneral = @idRegistroGeneral
		delete from dbo.TemAporte where temIdTransaccion in (
		select staging.RegistroDetallado.redId from staging.RegistroDetallado where staging.RegistroDetallado.redRegistroGeneral = @idRegistroGeneral)
		delete from dbo.TemAportante where tapIdTransaccion in (
		select staging.RegistroDetallado.redId from staging.RegistroDetallado where staging.RegistroDetallado.redRegistroGeneral = @idRegistroGeneral)
		delete from dbo.TemCotizante where tctIdTransaccion in (
		select staging.RegistroDetallado.redId from staging.RegistroDetallado where staging.RegistroDetallado.redRegistroGeneral = @idRegistroGeneral)
		delete from dbo.TemNovedad where tenRegistroDetallado in (
		select staging.RegistroDetallado.redId from staging.RegistroDetallado where staging.RegistroDetallado.redRegistroGeneral = @idRegistroGeneral) 
	 
		delete from staging.RegistroDetalladoNovedad where staging.RegistroDetalladoNovedad.rdnRegistroDetallado IN (
		select staging.RegistroDetallado.redId from staging.RegistroDetallado where staging.RegistroDetallado.redRegistroGeneral = @idRegistroGeneral)
		delete from staging.RegistroDetallado where staging.RegistroDetallado.redRegistroGeneral = @idRegistroGeneral
		delete from staging.RegistroGeneral where staging.RegistroGeneral.regId = @idRegistroGeneral
		delete from  staging.RegistroDetalladoPlanillaN where redRegistroGeneral = @idRegistroGeneral
	
		if @idTransaccion is not null 
		begin
			exec USP_DeleteBloqueStaging @idTransaccion
		end
	 
		update dbo.PilaEstadoBloque 
		set pebEstadoBloque7 = null, 
		pebEstadoBloque8 = null,
		pebEstadoBloque9 = null,
		pebEstadoBloque10 = null,
		pebAccionBloque7 = null,
		pebAccionBloque8 = null,
		pebAccionBloque9 = null,
		pebAccionBloque10 = null,
		pebFechaBloque7 = null,
		pebFechaBloque8 = null,
		pebFechaBloque9 = null,
		pebFechaBloque10 = null
		where pebIndicePlanilla = @PilaIndicePlanilla
	 
		update dbo.PilaIndicePlanilla
		set pipEstadoArchivo = (select pebEstadoBloque6 from dbo.PilaEstadoBloque where pebIndicePlanilla = @PilaIndicePlanilla)
		where pipId = @PilaIndicePlanilla
	
		if @tipoArchivo = 'ARCHIVO_OI_I' or @tipoArchivo = 'ARCHIVO_OI_IR' or @tipoArchivo = 'ARCHIVO_OI_IP' or @tipoArchivo = 'ARCHIVO_OI_IPR'
		begin
		update dbo.PilaIndicePlanilla
		set pipEstadoArchivo = (select pebEstadoBloque6 from dbo.PilaEstadoBloque where pebIndicePlanilla = @PilaIndicePlanilla)
		where pipId = @PilaIndicePlanilla
		end 
		else if @tipoArchivo = 'ARCHIVO_OI_A' or @tipoArchivo = 'ARCHIVO_OI_AR' or @tipoArchivo = 'ARCHIVO_OI_AP' or @tipoArchivo = 'ARCHIVO_OI_APR'
		begin
		update dbo.PilaIndicePlanilla
		set pipEstadoArchivo = (select pebEstadoBloque4 from dbo.PilaEstadoBloque where pebIndicePlanilla = @PilaIndicePlanilla)
		where pipId = @PilaIndicePlanilla
		end 
	
	
	
		if (@tipoArchivo = 'ARCHIVO_OI_I' or @tipoArchivo = 'ARCHIVO_OI_IR')
		and (select pipHabilitadoProcesoManual from PilaIndicePlanilla where pipId = @PilaIndicePlanilla) = 0
		begin
		update dbo.PilaIndicePlanilla
		set pipHabilitadoProcesoManual = 1
		where pipId = @PilaIndicePlanilla
		end
	
		if @reiniciarF = 1
		begin
			declare @idR6 bigint,
			@idOf bigint
	
			select @idR6 = r6.pf6Id, @idOf = pf6IndicePlanillaOF
			from dbo.PilaArchivoFRegistro6 r6
			where CAST(r6.pf6NumeroPlanilla AS BIGINT) = CAST(@numPlanilla AS BIGINT)
			and r6.pf6PeriodoPago = @periodo
			and r6.pf6CodOperadorInformacion = @codOperador
			and r6.pf6EstadoConciliacion != 'REGISTRO_6_ANULADO'
	
			update r6
			set pf6EstadoConciliacion = null
			from dbo.PilaArchivoFRegistro6 r6
			where r6.pf6Id = @idR6
	
			update pio
			SET pio.pioEstadoArchivo = 'ARCHIVO_FINANCIERO_PENDIENTE_CONCILIACION'
			from dbo.PilaIndicePlanillaOF pio
			where pio.pioId = @idOf
		end
		else
		begin
			--se eliminan los historiales de bloque posteriores al B6
			delete from dbo.HistorialEstadoBloque
			where hebIdIndicePlanilla = @PilaIndicePlanilla
			and hebBloque in ('BLOQUE_7_OI', 'BLOQUE_8_OI', 'BLOQUE_9_OI', 'BLOQUE_10_OI')
		end
	
		if @borradoTotal = 0 and @motivoProcesoManual is null
		begin
			--exec USP_ExecutePILA2 @PilaIndicePlanilla , @IdTransaccion = null
			print 'Hola'
		end
	
		fetch next from @cursorNumeroPlanilla into @PilaIndicePlanilla, @motivoProcesoManual
	
	end
end;
