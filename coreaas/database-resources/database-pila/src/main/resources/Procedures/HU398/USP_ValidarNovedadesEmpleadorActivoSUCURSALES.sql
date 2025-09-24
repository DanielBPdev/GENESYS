-- =============================================
-- Author: Robinson Castillo
-- Create Date: 2022-12-22
-- Description: Se encarga de quitar duplicados en la generación de las novedades por cambio de sucursal y aplicar o no aplicarla dependiendo del caso. 
-- se incluye despues del objeto USP_ValidarNovedadesEmpleadorActivo ya que las novedades de cambio de sucursal se hacen antes de la validación de novedades. 
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_ValidarNovedadesEmpleadorActivoSUCURSALES]
(@IdTransaccion bigInt)
AS
BEGIN

	SET NOCOUNT ON;
	SET QUOTED_IDENTIFIER ON;

		begin try
	
				declare @localDate datetime = dbo.getLocalDate(); 
				declare @regId2 bigInt = (select regId from staging.RegistroGeneral with (nolock) where regTransaccion = @IdTransaccion and isnull(regOUTEstadoArchivo,'') <> 'RECAUDO_NOTIFICADO')

				INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
				VALUES (@localDate, 'USP_ValidarNovedadesEmpleadorActivoSUCURSALES  @registroGeneral=' + CAST(@regId2 AS VARCHAR(20)) + N' @IdTransaccion = ' + CAST(@IdTransaccion AS VARCHAR(20)) + ' Inicia proceso para recalcular las novedades de sucursales', 'Inicia proceso');

			drop table if exists #nov2

			;with nov1 as (select tn.*,
			case when rd.redOUTEstadoSolicitante = 'ACTIVO' then eomonth(convert(date, concat(r.regPeriodoAporte, N'-01')))
			else 
				case when rdn.rdnTipoNovedad = 'NOVEDAD_ING' and rdn.rdnAccionNovedad = 'APLICAR_NOVEDAD' then isnull(rdnFechaInicioNovedad, eomonth(convert(date, concat(r.regPeriodoAporte, N'-01'))))
					else null end
			end as actFecha,
			case when rd.redOUTEstadoSolicitante = 'ACTIVO' then 'APLICAR_NOVEDAD' 
				else 
					case when rdn.rdnTipoNovedad = 'NOVEDAD_ING' and rdn.rdnAccionNovedad = 'APLICAR_NOVEDAD' then 'APLICAR_NOVEDAD' 
						else 'RELACIONAR_NOVEDAD' end
				end as 'AplicarNovSucu'
			from staging.RegistroGeneral as r with (nolock) 
			inner join staging.RegistroDetallado as rd with (nolock) on r.regId = rd.redRegistroGeneral
			left join staging.RegistroDetalladoNovedad as rdn with (nolock) on rd.redId = rdn.rdnRegistroDetallado
			inner join dbo.TemNovedad as tn with (nolock) on rd.redId = tn.tenRegistroDetallado and r.regId = tn.tenRegistroGeneral
			--where r.regTransaccion = @IdTransaccion)
			where r.regId = @regId2)
			select *, 
			row_number() over (partition by nov1.tenTipoIdCotizante, nov1.tenNumeroIdCotizante order by nov1.tenRegistroDetallado) as identificador
			into #nov2
			from nov1
			where nov1.tenTipoTransaccion = 'CAMBIO_SUCURSA_TRABAJADOR_DEPENDIENTE_DEPWEB'
			
			delete a
			from dbo.TemNovedad as a with (nolock)
			inner join #nov2 as b on a.tenId = b.tenId
			where b.identificador > 1
			
			delete from #nov2 where identificador > 1
			
			update a set a.tenFechaInicioNovedad = b.actFecha, a.tenAccionNovedad = b.AplicarNovSucu
			from dbo.TemNovedad as a with (nolock)
			inner join #nov2 as b on a.tenId = b.tenId

			drop table if exists #nov2


			if not exists (select 1
							from staging.registroDetallado as rd with (nolock)
							inner join staging.registroDetalladoNovedad as rdn with (nolock) on rd.redId = rdn.rdnRegistroDetallado
							where rd.redRegistroGeneral = @regId2)
					begin
						delete from dbo.TemNovedad where tenRegistroDetalladoNovedad = -1 and tenRegistroGeneral = @regId2
					end
			else if exists (select 1 
							from staging.registroDetallado as rd with (nolock)
							inner join staging.registroDetalladoNovedad as rdn with (nolock) on rd.redId = rdn.rdnRegistroDetallado
							where rd.redRegistroGeneral = @regId2
							and rdn.rdnTipoNovedad not in ('NOVEDAD_ING','NOVEDAD_RET')
							and isnull(redOUTEstadoSolicitante,'') in ('', 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES','NO_FORMALIZADO_CON_INFORMACION', 'NO_FORMALIZADO_RETIRADO_CON_APORTES', 'INACTIVO'))
						begin
							;with redId as (select redId 
											from staging.registroDetallado as rd with (nolock)
											inner join staging.registroDetalladoNovedad as rdn with (nolock) on rd.redId = rdn.rdnRegistroDetallado
											where rd.redRegistroGeneral = @regId2
											and rdn.rdnTipoNovedad not in ('NOVEDAD_ING','NOVEDAD_RET')
											and isnull(redOUTEstadoSolicitante,'') in ('', 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES','NO_FORMALIZADO_CON_INFORMACION', 'NO_FORMALIZADO_RETIRADO_CON_APORTES', 'INACTIVO'))
			
							delete from dbo.TemNovedad where tenRegistroDetalladoNovedad = -1 and tenRegistroDetallado in (select redId from redId)
						end
			else if exists (select 1
					from staging.registroDetallado as rd
					inner join staging.registroDetalladoNovedad as rdn on rd.redId = rdn.rdnRegistroDetallado
					where rd.redRegistroGeneral = @regId2
					and rdn.rdnTipoNovedad in ('NOVEDAD_ING','NOVEDAD_RET'))
					begin
						;with nov as (select rd.redRegistroGeneral, rd.redNumeroIdentificacionCotizante, rdnRegistroDetallado, rdnAccionNovedad, case when rdnTipoNovedad = 'NOVEDAD_ING' then rdnFechaInicioNovedad else rdnFechaFinNovedad end as fechaNov, rdnTipoNovedad
						from staging.registroDetallado as rd 
						inner join staging.registroDetalladoNovedad as rdn on rd.redId = rdn.rdnRegistroDetallado
						where rd.redRegistroGeneral = @regId2
						and rdn.rdnTipoNovedad in ('NOVEDAD_ING','NOVEDAD_RET')),
						nov2 as (select *, row_number() over (partition by redNumeroIdentificacionCotizante order by fechaNov desc) as id
								from nov),
						nov3 as (select *
								from nov2
								where id = 1
								and rdnAccionNovedad = 'APLICAR_NOVEDAD')
						delete a
						from dbo.TemNovedad as a
						inner join nov3 on a.tenRegistroDetallado = nov3.rdnRegistroDetallado
						where a.tenRegistroDetalladoNovedad = -1
					end

		end try
		begin catch
			
			DECLARE @ErrorMessage NVARCHAR(4000);
			SELECT @ErrorMessage = ERROR_MESSAGE();

			INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
			--VALUES (@localDate, '@registroGeneral=' + CAST(@IdTransaccion AS VARCHAR(20)) + ' Revisar el error.', @ErrorMessage);
			VALUES (@localDate, 'FIN USP_ValidarNovedadesEmpleadorActivoSUCURSALES @registroGeneral=' + CAST(@regId2 AS VARCHAR(20)) + N' @IdTransaccion = ' + CAST(@IdTransaccion AS VARCHAR(20)) + ' Se presento error. ', @ErrorMessage);

		end catch
END;