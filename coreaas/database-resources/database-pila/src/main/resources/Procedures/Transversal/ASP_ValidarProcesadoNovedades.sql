-- =============================================
-- Author: Robinson Castillo
-- Create Date: 2022-08-26
-- Description: Procedimiento para validar si la planilla procesa novedades y notificarla si las tiene. 
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[ASP_ValidarProcesadoNovedades] (@registroGeneral bigInt, @validar bit output)
AS
BEGIN

    SET XACT_ABORT ON
	SET NOCOUNT ON
	SET QUOTED_IDENTIFIER ON  

	begin try

				;with borradoNovReintegroDupli as (
				select rdn.*, row_number() over (partition by rdn.rdnRegistroDetallado, rdn.rdnTipoNovedad order by rdn.rdnId) as id, 
				case when rd.redNovIngreso is null and rdn.rdnTipotransaccion = 'NOVEDAD_REINTEGRO' then 1 else 0 end as borrar
				from staging.RegistroGeneral as r with (nolock)
				inner join staging.RegistroDetallado as rd with (nolock) on r.regId = rd.redRegistroGeneral
				inner join staging.RegistroDetalladoNovedad as rdn with (nolock) on rd.redId = rdn.rdnRegistroDetallado
				where  r.regId = @registroGeneral
				and isnull(rdn.rdnMensajeNovedad,'') <> 'Novedad por cumplimiento de aportes'
				)
				select rdnId
				into #novReitegro
				from borradoNovReintegroDupli
				where borrar = 1
				
				delete from staging.RegistroDetalladoNovedad where rdnId in (select rdnId from #novReitegro)
				delete from dbo.TemNovedad_val_proc where tenRegistroDetalladoNovedad in (select rdnId from #novReitegro)
				delete from dbo.TemNovedad where tenRegistroDetalladoNovedad in (select rdnId from #novReitegro)


				;with borradoDuplicados as (
				select *, row_number() over (partition by tenRegistroGeneral, tenRegistroDetallado, tenRegistroDetalladoNovedad order by tenId) as revision
				from dbo.TemNovedad_val_proc with (nolock)
				where tenRegistroGeneral = @registroGeneral)
				delete from borradoDuplicados where revision > 1;

				;with borradoDuplicados as (
				select *, row_number() over (partition by tenRegistroGeneral, tenRegistroDetallado, tenRegistroDetalladoNovedad order by tenId) as revision
				from dbo.TemNovedad with (nolock)
				where tenRegistroGeneral = @registroGeneral)
				delete from borradoDuplicados where revision > 1;



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
				where exists (select a.redId, a.rdnId from @novPila as a where a.redId = spi.spiRegistroDetallado and a.rdnId = spi.spiIdRegistroDetalladoNovedad)', N'@r bigInt', @r = @registroGeneral
				
				select t.tenId
				into #liberarProcesados
				from dbo.TemNovedad as t
				where exists (select * from @novCore as a where t.tenRegistroDetallado = a.redId and t.tenRegistroDetalladoNovedad = a.rdnId)


				declare @ctr_1 bit = 0;
				while (@ctr_1 = 0)
					begin
				--==== Borrado novedad existente en core. 
						delete top (1000) ten
						from dbo.TemNovedad as ten with (nolock)
						where ten.tenId in (select tenId from #liberarProcesados)
						if @@ROWCOUNT < 1000 set @ctr_1 = 1;

					end;

				declare @ctr_2 bit = 0;
				while (@ctr_2 = 0)
					begin

						delete top (1000) ten
						from dbo.TemNovedad_val_proc as ten with (nolock)
						where ten.tenId in (select tenId from #liberarProcesados)

						if @@ROWCOUNT < 1000 set @ctr_2 = 1;

					end;

				drop table if exists #novFinal
				drop table if exists #novPila
				drop table if exists #novSucursal
				drop table if exists #tblTemp
				drop table if exists #tblTemp2
			--declare @idRegistroGeneral bigInt = 1101124		
			
			select rd.redId as rdnRegistroDetallado, rdn.rdnId
			into #tblTemp
			from staging.RegistroDetallado as rd with (nolock)
			left join staging.RegistroDetalladoNovedad as rdn with (nolock) on rd.redId = rdn.rdnRegistroDetallado
			where rd.redRegistroGeneral = @registroGeneral;


				--=== Ajuste 2023-06-06
				declare @regOUTEstadoEmpleador varchar(70) = null
				select @regOUTEstadoEmpleador = regOUTEstadoEmpleador
				from staging.RegistroGeneral with (nolock)
				where regId = @registroGeneral;
			
				--=== validacionNovedades. 
			if (
						select SUM(cantidadNovedades)
						from (
						select sum(cast(case when redNovIngreso is null then 0 else 1 end as int) +
						cast(case when redNovRetiro is null then 0 else 1 end as int)  +
						cast(case when redNovVSP is null  then 0 else 1 end as int) +
						cast(case when redNovVST is null  then 0 else 1 end as int) +
						cast(case when redNovSLN is null  then 0 else 1 end as int) +
						cast(case when redNovIGE is null  then 0 else 1 end as int)  +
						cast(case when redNovLMA is null  then 0 else 1 end as int) +
						cast(case when redNovVACLR is null  then 0 else 1 end as int) + 
						cast(case when redNovSUS is null  then 0 else 1 end as int) +
						cast(case when (case when isnumeric(redDiasIRL) = 1 then iif(convert(int, redDiasIRL) > 0, 'SI', 'NO') when isnumeric(redDiasIRL) = 0 then iif(redDiasIRL is null, 'NO', 'SI') end) = 'NO' then 0 else 1 end as int)) as cantidadNovedades--, redRegistroGeneral
						from staging.RegistroDetallado with (nolock)
						where redRegistroGeneral = @registroGeneral
						group by redRegistroGeneral
						union 
						select  count(*)
						from dbo.TemNovedad as ten with (nolock)
						inner join (select r.regId, rd.redId
									from staging.RegistroGeneral as r with (nolock)
									inner join staging.RegistroDetallado as rd with (nolock) on r.regId = rd.redRegistroGeneral
									where r.regId = @registroGeneral) as a on ten.tenRegistroGeneral = a.regId and ten.tenRegistroDetallado = a.redId and ten.tenRegistroDetalladoNovedad = -1
						WHERE ten.tenRegistroGeneral = @registroGeneral) as t) > 0 and isnull(@regOUTEstadoEmpleador,'NO_FORMALIZADO_CON_INFORMACION') not in ('NO_FORMALIZADO_CON_INFORMACION','NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES') --=== Se agrega validación para no tener en cuenta a los aportantes no afiliados. 
					
					begin
						
						if (select sum(total)
							from (select COUNT(*) as total
							from staging.registroDetallado as rd with (nolock)
							inner join staging.RegistroDetalladoNovedad as rdn with (nolock) on rd.redId = rdn.rdnRegistroDetallado
							where rd.redRegistroGeneral = @registroGeneral
							union
							select COUNT(*)
							from dbo.TemNovedad as ten with (nolock)
							inner join staging.RegistroDetallado as rd with (nolock) on ten.tenRegistroDetallado = rd.redId and ten.tenRegistroDetalladoNovedad = -1
							where rd.redRegistroGeneral = @registroGeneral) as t) > 0
						begin			
						
							select rdn.rdnRegistroDetallado, rdn.rdnId
							into #novPila
							from dbo.TemNovedad as n with (nolock)
							inner join staging.RegistroDetalladoNovedad as rdn with (nolock) on n.tenRegistroDetalladoNovedad = rdn.rdnId and n.tenRegistroDetallado = rdn.rdnRegistroDetallado
							where tenRegistroGeneral = @registroGeneral;
							
							select rd.redId, ten.tenRegistroDetalladoNovedad
							into #novSucursal
							from dbo.TemNovedad as ten with (nolock)
							inner join staging.RegistroDetallado as rd with (nolock) on ten.tenRegistroDetallado = rd.redId and ten.tenRegistroDetalladoNovedad = -1
							where rd.redRegistroGeneral = @registroGeneral;
							
							select *
							into #novFinal
							from (
							select *
							from #novPila
							union 
							select *
							from #novSucursal) as novs
			
							declare @totlaCore int 
			
							select @totlaCore = COUNT(*)
							from #novFinal as a
							left join core.SolicitudNovedadPila as spi on a.rdnRegistroDetallado = spi.spiRegistroDetallado and a.rdnId = spi.spiIdRegistroDetalladoNovedad
							where spi.spiIdRegistroDetalladoNovedad is null;
							
							if @totlaCore > 0
								begin
									set @validar = 0
									return @validar
								end
			
							if @totlaCore <= 0
								begin
									--=== Borrado de la tabla temporal
									declare @ctr_3 bit = 0;
									while (@ctr_3 = 0)
										begin
											delete top (1000) ten
											from dbo.TemNovedad as ten with (nolock)
											where ten.tenRegistroGeneral = @registroGeneral;
											if @@ROWCOUNT < 1000 set @ctr_3 = 1;
										end

									declare @ctr_4 bit = 0;
									while (@ctr_4 = 0)
										begin
											delete top (1000) ten
											from dbo.TemNovedad_val_proc as ten with (nolock)
											where ten.tenRegistroGeneral = @registroGeneral;
											if @@ROWCOUNT < 1000 set @ctr_4 = 1;
										end

									set @validar = 1
									return @validar;

								end
			
						end
					else
						begin


						declare @ctr_5 bit = 0;
						while (@ctr_5 = 0)
							begin
								delete top (1000) ten
								from dbo.TemNovedad_val_proc as ten with (nolock)
								where ten.tenRegistroGeneral = @registroGeneral;
								if @@ROWCOUNT < 1000 set @ctr_5 = 1;
							end

						declare @ctr_6 bit = 0;
						while (@ctr_6 = 0)
							begin
								delete top (1000) ten
								from dbo.TemNovedad as ten with (nolock)
								where ten.tenRegistroGeneral = @registroGeneral;
								if @@ROWCOUNT < 1000 set @ctr_6 = 1;
							end

							set @validar = 1
							return @validar;

						end
					end
			else
				begin

					declare @ctr_7 bit = 0;
					while (@ctr_7 = 0)
						begin
							delete top (1000) ten
							from dbo.TemNovedad_val_proc as ten with (nolock)
							where ten.tenRegistroGeneral = @registroGeneral;

							if @@ROWCOUNT < 1000 set @ctr_7 = 1;
						end

					declare @ctr_8 bit = 0;
					while (@ctr_8 = 0)
						begin

							delete top (1000) ten
							from dbo.TemNovedad as ten with (nolock)
							where ten.tenRegistroGeneral = @registroGeneral;

							if @@ROWCOUNT < 1000 set @ctr_8 = 1;
						end

					set @validar = 1
					return @validar;

				end


				drop table if exists #novFinal
				drop table if exists #novPila
				drop table if exists #novSucursal
				drop table if exists #tblTemp
				drop table if exists #tblTemp2

	end try
	begin catch

		 set @validar = 0 --=== Se retorna falso en caaso de hacer rollback. 

		 select 'Se generó error al procesar el registroGeneral regId = ' + CONVERT(varchar(50), @registroGeneral)
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

END