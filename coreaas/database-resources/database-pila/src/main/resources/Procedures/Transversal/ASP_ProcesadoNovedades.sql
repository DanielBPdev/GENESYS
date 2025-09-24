-- =============================================
-- Author: Robinson Castillo
-- Create Date: 2023-09-05
-- Description: Procedimiento encargado de validar las novedades a procesar. 
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[ASP_ProcesadoNovedades] ( @idRegistroGeneral bigInt, @resul bit output)
AS
BEGIN
    SET XACT_ABORT ON;
	SET NOCOUNT ON;
	SET QUOTED_IDENTIFIER ON ;

	begin try
		--begin transaction


			;with borradoNovReintegroDupli as (
			select rdn.*, row_number() over (partition by rdn.rdnRegistroDetallado, rdn.rdnTipoNovedad order by rdn.rdnId) as id, 
			case when rd.redNovIngreso is null and rdn.rdnTipotransaccion = 'NOVEDAD_REINTEGRO' then 1 else 0 end as borrar
			from staging.RegistroGeneral as r with (nolock)
			inner join staging.RegistroDetallado as rd with (nolock) on r.regId = rd.redRegistroGeneral
			inner join staging.RegistroDetalladoNovedad as rdn with (nolock) on rd.redId = rdn.rdnRegistroDetallado
			where  r.regId = @idRegistroGeneral
			and isnull(rdn.rdnMensajeNovedad,'') <> 'Novedad por cumplimiento de aportes'
			)
			select rdnId
			into #novReitegro
			from borradoNovReintegroDupli
			where borrar = 1
			
			delete from staging.RegistroDetalladoNovedad where rdnId in (select rdnId from #novReitegro)
			delete from dbo.TemNovedad_val_proc where tenRegistroDetalladoNovedad in (select rdnId from #novReitegro)
			delete from dbo.TemNovedad where tenRegistroDetalladoNovedad in (select rdnId from #novReitegro)

			--==== Borrado duplicados 2024-01-17
			;with borradoDuplicados as (
			select *, row_number() over (partition by tenRegistroGeneral, tenRegistroDetallado, tenRegistroDetalladoNovedad order by tenId) as revision
			from dbo.TemNovedad_val_proc
			where tenRegistroGeneral = @idRegistroGeneral)
			delete from borradoDuplicados where revision > 1;

			;with borradoDuplicados as (
			select *, row_number() over (partition by tenRegistroGeneral, tenRegistroDetallado, tenRegistroDetalladoNovedad order by tenId) as revision
			from dbo.TemNovedad
			where tenRegistroGeneral = @idRegistroGeneral)
			delete from borradoDuplicados where revision > 1;

			--==== Borrado novedad existente en core. 
			/*
					CREATE NONCLUSTERED INDEX [IXNC_tenRegistroGeneral] ON [dbo].[TemNovedad] ([tenRegistroGeneral]) with (online = on)
					CREATE CLUSTERED INDEX [IXNC_tenRegistroGeneral_val_proc] ON [dbo].[TemNovedad_val_proc] ([tenRegistroGeneral]) with (online = on)
			*/

			declare @novCore as table (redId bigint, rdnId bigInt, origen varchar(250))
			insert @novCore
			execute sp_execute_remote coreReferenceData, N'
			declare @novPila as table (redId bigint, rdnId bigInt, origen varchar(250), unique clustered (redId,rdnId))
			insert @novPila
			execute sp_execute_remote pilaReferenceData, 
			N''select tenRegistroDetallado, tenRegistroDetalladoNovedad
				from dbo.TemNovedad 
				where tenRegistroGeneral = @r'', N''@r bigint'', @r = @r
			
			select spi.spiRegistroDetallado, spi.spiIdRegistroDetalladoNovedad
			from dbo.solicitudNovedadPila as spi 
			where exists (select a.redId, a.rdnId from @novPila as a where a.redId = spi.spiRegistroDetallado and a.rdnId = spi.spiIdRegistroDetalladoNovedad)', N'@r bigInt', @r = @idRegistroGeneral
			
			select t.tenId
			into #liberarProcesados
			from dbo.TemNovedad as t
			where exists (select * from @novCore as a where t.tenRegistroDetallado = a.redId and t.tenRegistroDetalladoNovedad = a.rdnId)


			declare @ctr bit = 0;
			while (@ctr = 0)
			begin
				delete top (1000) ten
				from dbo.TemNovedad as ten with (nolock)
				--inner join core.SolicitudNovedadPila as spi on ten.tenRegistroDetallado = spi.spiRegistroDetallado and ten.tenRegistroDetalladoNovedad = spi.spiIdRegistroDetalladoNovedad
				where ten.tenId in (select tenId from #liberarProcesados)
				if @@rowcount < 1000 set @ctr = 1
			end


			declare @ctr1 bit = 0;
			while (@ctr1 = 0)
			begin
				delete top (1000) ten
				from dbo.TemNovedad_val_proc as ten with (nolock)
				--inner join core.SolicitudNovedadPila as spi on ten.tenRegistroDetallado = spi.spiRegistroDetallado and ten.tenRegistroDetalladoNovedad = spi.spiIdRegistroDetalladoNovedad
				where ten.tenId in (select tenId from #liberarProcesados)

				if @@rowcount < 1000 set @ctr1 = 1
			end
			/* Se modifican estos dos objetos, para recibir una tabla de control*/
			-- [USP_ExecuteRegistrarRelacionarNovedadesRegistro]
			-- [USP_ExecuteRegistrarRelacionarNovedadesRegistro_N]
			-- [USP_ExecuteRegistrarRelacionarAportesRegistro] -- Aca se setean novedades con menos 1, solo debe existir un registro por registroDetallado. 


			drop table if exists #tblTemp
			drop table if exists #tblTemp2
			drop table if exists #tblTempBorrado
			drop table if exists #NovedadNoExistenteEnTemp
			--declare @idRegistroGeneral bigInt = 1101124		

			--=== Se agrega validación para limpiar los registros que no están en el registro detallado. 2023-10-26

			select rd.redRegistroGeneral, rdn.rdnRegistroDetallado, rdn.rdnId
			into #NovedadNoExistenteEnTemp
			from staging.RegistroDetallado as rd with (nolock)
			inner join staging.RegistroDetalladoNovedad as rdn with (nolock) on rd.redId = rdn.rdnRegistroDetallado
			where rd.redRegistroGeneral = @idRegistroGeneral;


			delete from d
			from #NovedadNoExistenteEnTemp as a with (nolock)
			right join dbo.TemNovedad_val_proc as d with (nolock) on a.rdnId = d.tenRegistroDetalladoNovedad and a.rdnRegistroDetallado = d.tenRegistroDetallado and a.redRegistroGeneral = d.tenRegistroGeneral
			where (d.tenRegistroGeneral = @idRegistroGeneral and d.tenRegistroDetalladoNovedad <> -1 and a.rdnId is null);
			
			--=== Finaliza 2023-10-26

			SELECT 
			CASE WHEN ten.tenMarcaNovedadManual = 0 THEN 'PILA' ELSE 'APORTE_MANUAL' END canal, 
			ten.*
			into #tblTemp
			FROM TemNovedad as ten with (nolock)
			inner join (select rd.redRegistroGeneral, rdn.rdnRegistroDetallado, rdn.rdnId
								from staging.RegistroDetallado as rd with (nolock)
								inner join staging.RegistroDetalladoNovedad as rdn with (nolock) on rd.redId = rdn.rdnRegistroDetallado
								where rd.redRegistroGeneral = @idRegistroGeneral) as a on ten.tenRegistroGeneral = a.redRegistroGeneral and ten.tenRegistroDetallado = a.rdnRegistroDetallado and ten.tenRegistroDetalladoNovedad = a.rdnId
			WHERE ten.tenRegistroGeneral = @idRegistroGeneral;
			--order by tenNumeroIdCotizante, tenFechaInicioNovedad, tenTipoTransaccion

			--====== Se agrega novedades por cambio de sucursal que quedan en el detalle con -1 
			select
			CASE WHEN ten.tenMarcaNovedadManual = 0 THEN 'PILA' ELSE 'APORTE_MANUAL' END canal, 
			ten.*
			into #tblTemp2
			from dbo.TemNovedad as ten with (nolock)
			inner join staging.RegistroDetallado as rd with (nolock) on ten.tenRegistroGeneral = rd.redRegistroGeneral and ten.tenRegistroDetallado = rd.redId and ten.tenRegistroDetalladoNovedad = -1
			WHERE ten.tenRegistroGeneral = @idRegistroGeneral;


			--========= Se agrega validación para borrar la novedad existente en core. 

			merge dbo.TemNovedad_val_proc with (serializable) as d
			using (select ten.canal, case when (ten.tenRegistroDetallado = spi.spiRegistroDetallado and ten.tenRegistroDetalladoNovedad = spi.spiIdRegistroDetalladoNovedad) then 1 
										when (ten.tenRegistroDetallado = spi.spiRegistroDetallado and spi.spiIdRegistroDetalladoNovedad = -1) then 1 
										else 0 end as novedadexistenteCore,
					ten.tenId,ten.tenIdTransaccion,ten.tenMarcaNovedadSimulado,ten.tenMarcaNovedadManual,ten.tenRegistroGeneral
					,ten.tenRegistroDetallado,ten.tenTipoIdAportante,ten.tenNumeroIdAportante,ten.tenTipoIdCotizante,ten.tenNumeroIdCotizante,ten.tenTipoTransaccion,ten.tenEsIngreso,ten.tenEsRetiro,ten.tenFechaInicioNovedad,ten.tenFechaFinNovedad
					,ten.tenAccionNovedad,ten.tenMensajeNovedad,ten.tenTipoCotizante,ten.tenPrimerApellido,ten.tenSegundoApellido,ten.tenPrimerNombre,ten.tenSegundoNombre,ten.tenCodigoDepartamento,ten.tenCodigoMunicipio,ten.tenModalidadRecaudoAporte
					,ten.tenValor,ten.tenEsEmpleadorReintegrable,ten.tenEsTrabajadorReintegrable,ten.tenRegistroDetalladoNovedad,ten.tenEnProceso
					from (
						select * from #tblTemp
						union
						select * from #tblTemp2) as ten
					left join core.SolicitudNovedadPila as spi on ten.tenRegistroDetallado = spi.spiRegistroDetallado and ten.tenRegistroDetalladoNovedad = spi.spiIdRegistroDetalladoNovedad 
					) as o on d.tenId = o.tenId and d.tenIdTransaccion = o.tenIdTransaccion and o.novedadexistenteCore = 0
			when matched then update set d.tenEnProceso = o.tenEnProceso --=== Se agrega la actualización del tenEnProceso, para mover el registro.  
			when not matched by target then insert (canal,novedadexistenteCore,tenId,tenIdTransaccion,tenMarcaNovedadSimulado,tenMarcaNovedadManual,tenRegistroGeneral,tenRegistroDetallado,tenTipoIdAportante,tenNumeroIdAportante,tenTipoIdCotizante,tenNumeroIdCotizante,tenTipoTransaccion
			,tenEsIngreso,tenEsRetiro,tenFechaInicioNovedad,tenFechaFinNovedad,tenAccionNovedad,tenMensajeNovedad,tenTipoCotizante,tenPrimerApellido,tenSegundoApellido,tenPrimerNombre,tenSegundoNombre,tenCodigoDepartamento,tenCodigoMunicipio,tenModalidadRecaudoAporte
			,tenValor,tenEsEmpleadorReintegrable,tenEsTrabajadorReintegrable,tenRegistroDetalladoNovedad,tenEnProceso)
			values (o.canal,o.novedadexistenteCore,o.tenId,o.tenIdTransaccion,o.tenMarcaNovedadSimulado,o.tenMarcaNovedadManual,o.tenRegistroGeneral,o.tenRegistroDetallado,o.tenTipoIdAportante,o.tenNumeroIdAportante
			,o.tenTipoIdCotizante,o.tenNumeroIdCotizante,o.tenTipoTransaccion,o.tenEsIngreso,o.tenEsRetiro,o.tenFechaInicioNovedad,o.tenFechaFinNovedad,o.tenAccionNovedad,o.tenMensajeNovedad,o.tenTipoCotizante,o.tenPrimerApellido,o.tenSegundoApellido
			,o.tenPrimerNombre,o.tenSegundoNombre,o.tenCodigoDepartamento,o.tenCodigoMunicipio,o.tenModalidadRecaudoAporte,o.tenValor,o.tenEsEmpleadorReintegrable,o.tenEsTrabajadorReintegrable,o.tenRegistroDetalladoNovedad,o.tenEnProceso);


			declare @ctr2 bit = 0;
			while (@ctr2 = 0)
			begin

				delete top (1000) from d
				from #NovedadNoExistenteEnTemp as a
				right join dbo.TemNovedad_val_proc as d on a.rdnId = d.tenRegistroDetalladoNovedad and a.rdnRegistroDetallado = d.tenRegistroDetallado and a.redRegistroGeneral = d.tenRegistroGeneral
				where (d.tenRegistroGeneral = @idRegistroGeneral and d.tenRegistroDetalladoNovedad <> -1 and a.rdnId is null);

				if @@ROWCOUNT < 1000 set @ctr2 = 1

			end


			drop table if exists #NovedadNoExistenteEnTemp
			drop table if exists #tblTemp
			drop table if exists #tblTemp2
			drop table if exists #tblTempBorrado

		--commit transaction;

		set @resul = 1 --=== Se devuelve verdadero para saber que tiene las novedades ok

	end try
	begin catch

		 set @resul = 0 --=== Se retorna falso en caaso de hacer rollback. 

		 select 'Se generó error al procesar el registroGeneral regId = ' + CONVERT(varchar(50), @idRegistroGeneral)
		 SELECT  
         ERROR_NUMBER() AS ErrorNumber  
         ,ERROR_SEVERITY() AS ErrorSeverity  
         ,ERROR_STATE() AS ErrorState  
         ,ERROR_PROCEDURE() AS ErrorProcedure  
         ,ERROR_LINE() AS ErrorLine  
         ,ERROR_MESSAGE() AS ErrorMessage; 

		if (XACT_STATE()) = -1  
		begin 
		    rollback transaction;
		end;  

		if (XACT_STATE()) = 1  
		begin
		    commit transaction;     
		end; 
	end catch;
END;