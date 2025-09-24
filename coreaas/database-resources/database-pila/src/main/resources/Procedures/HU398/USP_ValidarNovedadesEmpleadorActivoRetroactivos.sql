-- =============================================
-- Author: Robinson Castillo
-- Create date: 2022/09/29
-- Description: Procedimiento para las novedades retroactivas de un empleador para el proceso de novedades
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_ValidarNovedadesEmpleadorActivoRetroactivos]
AS
BEGIN
SET NOCOUNT ON;
--===========================
		--=========================== SOLUCIÓN AL PERIODO RETROACTIVO
		;with marcarIngRetRetroAct as 
		(select *, case when rdnTipoNovedad in ('NOVEDAD_ING', 'NOVEDAD_RET') then 1 else 0  end novIngresoRetiro
				, sum(CantNovCot) over (partition by redNumeroIdentificacionCotizante) as cantNov
				, case when rdnTipoNovedad = 'NOVEDAD_ING' then 'ANOVEDAD_ING'
					   when rdnTipoNovedad = 'NOVEDAD_RET' then 'ZNOVEDAD_RET'
					   else rdnTipoNovedad end ordenNov
			from #inicial),
		actIdentificadorRetroAct as (
		select *, row_number() over (partition by redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante order by rdnFechaInicioNovedad2, ordenNov) as identificador2
		from marcarIngRetRetroAct
		)
		update a set novING_RET = a.novIngresoRetiro, CantNovCot = a.cantNov, Identificador = Identificador2
		from marcarIngRetRetroAct as a 
		inner join actIdentificadorRetroAct as b on a.redNumeroIdentificacionCotizante = b.redNumeroIdentificacionCotizante and a.rdnTipoNovedad = b.rdnTipoNovedad and a.redId = b.redId and a.rdnId = b.rdnId
		

		--- ACTUALIZACION DE LA SITUACIÓN PRIMARIA CONTROLANDO LAS FECHAS, CUANDO SON IGUALES. 
		;with sitPrimaria as ( select *, isnull(lag(rdnTipoNovedad) over (partition by redNumeroIdentificacionCotizante order by identificador, rdnFechaInicioNovedad2),'SIN_NOVEDAD') situacionPrimaria2 from #inicial where TipoPago = 'PERIODO_RETROACTIVO')
		update sitPrimaria set situacionPrimaria = situacionPrimaria2
		
		if exists (select 1 from #inicial where cotFechaIngreso is null and cotEsTrabajadorReintegrable = 0)
		begin
			update #inicial set EstadoCotizanteFinal = redOUTEstadoSolicitante, rdnAccionNovedadFinal = 'RELACIONAR_NOVEDAD', mensaje = '- La novedad no fue procesada', procesado = 1
			from #inicial
			where cotFechaIngreso is null and cotEsTrabajadorReintegrable = 0
		end
		if exists (select 1 from #inicial where (redOUTEstadoSolicitante in ('NO_FORMALIZADO_CON_INFORMACION', 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES','NO_FORMALIZADO_RETIRADO_CON_APORTES') or redOUTEstadoSolicitante = null) and cotEsTrabajadorReintegrable = 0)
		begin
			update #inicial set EstadoCotizanteFinal = redOUTEstadoSolicitante, rdnAccionNovedadFinal = 'RELACIONAR_NOVEDAD', mensaje = '- La novedad no fue procesada', procesado = 1
			from #inicial
			where (redOUTEstadoSolicitante in ('NO_FORMALIZADO_CON_INFORMACION', 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES','NO_FORMALIZADO_RETIRADO_CON_APORTES') or redOUTEstadoSolicitante = null) and cotEsTrabajadorReintegrable = 0
		end

		update #inicial set marcaReintegroOtro = 1 where (redOUTEstadoSolicitante in ('NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES', 'NO_FORMALIZADO_CON_INFORMACION','NO_FORMALIZADO_RETIRADO_CON_APORTES') or redOUTEstadoSolicitante is null) and cotEsTrabajadorReintegrable = 1


		if exists (select * from #inicial where (redOUTEstadoSolicitante in ('NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES', 'NO_FORMALIZADO_CON_INFORMACION','NO_FORMALIZADO_RETIRADO_CON_APORTES') or redOUTEstadoSolicitante is null) and cotEsTrabajadorReintegrable = 1 and marcaReintegroOtro = 1)
		begin

			;with novIng_otro as (
			select redNumeroIdentificacionCotizante as redNumeroIdentificacionCotizante_a, rdnFechaInicioNovedad as rdnFechaInicioNovedad_a, rdnTipoNovedad as rdnTipoNovedad_a
			from #inicial
			where rdnFechaInicioNovedad is not null and procesado = 0 and rdnTipoNovedad = 'NOVEDAD_ING' and cotEsTrabajadorReintegrable = 1 and marcaReintegroOtro = 1),
			novRet_otro as (
			select redNumeroIdentificacionCotizante as redNumeroIdentificacionCotizante_b, rdnFechaInicioNovedad as rdnFechaInicioNovedad_b, rdnTipoNovedad as rdnTipoNovedad_b
			from #inicial
			where rdnFechaInicioNovedad is not null and procesado = 0 and rdnTipoNovedad = 'NOVEDAD_RET' and cotEsTrabajadorReintegrable = 1 and marcaReintegroOtro = 1)
			select * into #resultado_otro
			from novIng_otro as a
			inner join novRet_otro as b on a.redNumeroIdentificacionCotizante_a = b.redNumeroIdentificacionCotizante_b and a.rdnFechaInicioNovedad_a = b.rdnFechaInicioNovedad_b
			
			update i set ingRetMismaFecha = 1, fechaIngRetMismaFecha = b.rdnFechaInicioNovedad_a
			from #inicial as i
			inner join #resultado_otro as b on i.redNumeroIdentificacionCotizante = b.redNumeroIdentificacionCotizante_a and i.rdnFechaInicioNovedad = b.rdnFechaInicioNovedad_a and i.rdnTipoNovedad = b.rdnTipoNovedad_a
			
			update i set ingRetMismaFecha = 1, fechaIngRetMismaFecha = b.rdnFechaInicioNovedad_b
			from #inicial as i
			inner join #resultado_otro as b on i.redNumeroIdentificacionCotizante = b.redNumeroIdentificacionCotizante_b and i.rdnFechaInicioNovedad = b.rdnFechaInicioNovedad_b and i.rdnTipoNovedad = b.rdnTipoNovedad_b

			;with marcarIngRet_otro as 
			(select *, case when rdnTipoNovedad in ('NOVEDAD_ING', 'NOVEDAD_RET') then 1 else 0  end novIngresoRetiro
					, sum(CantNovCot) over (partition by redNumeroIdentificacionCotizante) as cantNov
					, case when rdnTipoNovedad = 'NOVEDAD_ING' then 'ANOVEDAD_ING'
						   when rdnTipoNovedad = 'NOVEDAD_RET' then 'ZNOVEDAD_RET'
						   else rdnTipoNovedad end ordenNov
				from #inicial where TipoPago = 'PERIODO_REGULAR' and procesado = 0 and cotEsTrabajadorReintegrable = 1 and marcaReintegroOtro = 1),
			actIdentificador_otro as (
			select *, row_number() over (partition by redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante order by rdnFechaInicioNovedad2, ordenNov) as identificador2
			from marcarIngRet_otro
			)
			update a set novING_RET = a.novIngresoRetiro, CantNovCot = a.cantNov, Identificador = Identificador2
			from marcarIngRet_otro as a 
			inner join actIdentificador_otro as b on a.redNumeroIdentificacionCotizante = b.redNumeroIdentificacionCotizante and a.rdnTipoNovedad = b.rdnTipoNovedad and a.redId = b.redId and a.rdnId = b.rdnId
			
			;with sitPrimaria_otro as 
			(select *, isnull(lag(rdnTipoNovedad) over (partition by redNumeroIdentificacionCotizante order by identificador, rdnFechaInicioNovedad2),'SIN_NOVEDAD') situacionPrimaria2 
				from #inicial where TipoPago = 'PERIODO_REGULAR' and procesado = 0 and cotEsTrabajadorReintegrable = 1 and marcaReintegroOtro = 1)
			update sitPrimaria_otro set situacionPrimaria = situacionPrimaria2

			--== Actualizamos el registro de ingreso, si la primera novedad, llega a ser el ingreso. 

			update #inicial set EstadoCotizanteFinal = 'ACTIVO', rdnAccionNovedadFinal = 'APLICAR_NOVEDAD', mensaje = 'La novedad de ingreso por otro empl fue procesada', procesado = 1, redOUTEstadoSolicitante = 'ACTIVO', cotFechaIngreso = rdnFechaInicioNovedad2
			where cotEsTrabajadorReintegrable = 1 and Identificador = 1 and rdnTipoNovedad = 'NOVEDAD_ING' and procesado = 0 and cotEsTrabajadorReintegrable = 1 and marcaReintegroOtro = 1

			--== Se actualiza el registro, cuando la primera novedad no es ingreso. 

			update #inicial set EstadoCotizanteFinal = redOUTEstadoSolicitante, rdnAccionNovedadFinal = 'NO_APLICADA', mensaje = '- No se logró registrar la novedad', procesado = 1, redOUTEstadoSolicitante = redOUTEstadoSolicitante, cotFechaIngreso = rdnFechaInicioNovedad2
			where cotEsTrabajadorReintegrable = 1 and Identificador = 1 and rdnTipoNovedad <> 'NOVEDAD_ING' and procesado = 0 and cotEsTrabajadorReintegrable = 1 and marcaReintegroOtro = 1


end
--=== Finaliza proceso para aplicar reintegro por otras empresas.


		--=== Se relacionan las novedades que no aplican reintegro. 
			;with relacionarNoReint as (select *, 
			FIRST_VALUE(redOUTEstadoSolicitante) over (partition by redTipoIdentificacionCotizante,redNumeroIdentificacionCotizante order by Identificador) as idRelacionarProPrimerValor
			from #inicial)
			update relacionarNoReint set EstadoCotizanteFinal = redOUTEstadoSolicitante, rdnAccionNovedadFinal = 'RELACIONAR_NOVEDAD', mensaje = '- La novedad no fue procesada', procesado = 1
			where idRelacionarProPrimerValor in ('NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES', 'NO_FORMALIZADO_CON_INFORMACION','NO_FORMALIZADO_RETIRADO_CON_APORTES') or idRelacionarProPrimerValor is null


		--TODO:
		--===== TRATAMIENTO PARA NOVEDADES INGRESO Y RETIRO EN LA MISMA FECHA SE LE DA PRIORIDAD A LA NOVEDAD DE RETIRO. 
		;with novIng as (
		select redNumeroIdentificacionCotizante as redNumeroIdentificacionCotizante_a, rdnFechaInicioNovedad as rdnFechaInicioNovedad_a, rdnTipoNovedad as rdnTipoNovedad_a
		from #inicial
		where rdnFechaInicioNovedad is not null and procesado = 0 and rdnTipoNovedad = 'NOVEDAD_ING' and marcaReintegroOtro is null),
		novRet as (
		select redNumeroIdentificacionCotizante as redNumeroIdentificacionCotizante_b, rdnFechaInicioNovedad as rdnFechaInicioNovedad_b, rdnTipoNovedad as rdnTipoNovedad_b
		from #inicial
		where rdnFechaInicioNovedad is not null and procesado = 0 and rdnTipoNovedad = 'NOVEDAD_RET' and marcaReintegroOtro is null)
		select * into #resultado
		from novIng as a
		inner join novRet as b on a.redNumeroIdentificacionCotizante_a = b.redNumeroIdentificacionCotizante_b and a.rdnFechaInicioNovedad_a = b.rdnFechaInicioNovedad_b
		
		update i set ingRetMismaFecha = 1, fechaIngRetMismaFecha = b.rdnFechaInicioNovedad_a
		from #inicial as i
		inner join #resultado as b on i.redNumeroIdentificacionCotizante = b.redNumeroIdentificacionCotizante_a and i.rdnFechaInicioNovedad = b.rdnFechaInicioNovedad_a and i.rdnTipoNovedad = b.rdnTipoNovedad_a
		
		update i set ingRetMismaFecha = 1, fechaIngRetMismaFecha = b.rdnFechaInicioNovedad_b
		from #inicial as i
		inner join #resultado as b on i.redNumeroIdentificacionCotizante = b.redNumeroIdentificacionCotizante_b and i.rdnFechaInicioNovedad = b.rdnFechaInicioNovedad_b and i.rdnTipoNovedad = b.rdnTipoNovedad_b
		--===== FINALIZA TRATAMIENTO PARA NOVEDADES INGRESO Y RETIRO EN LA MISMA FECHA SE LE DA PRIORIDAD A LA NOVEDAD DE RETIRO. 
		
			
		;with marcarIngRet as 
		(select *, case when rdnTipoNovedad in ('NOVEDAD_ING', 'NOVEDAD_RET') then 1 else 0  end novIngresoRetiro
				, sum(CantNovCot) over (partition by redNumeroIdentificacionCotizante) as cantNov
				, case when rdnTipoNovedad = 'NOVEDAD_ING' then 'ANOVEDAD_ING'
					   when rdnTipoNovedad = 'NOVEDAD_RET' then 'ZNOVEDAD_RET'
					   else rdnTipoNovedad end ordenNov
			from #inicial where procesado = 0 and marcaReintegroOtro is null),
		actIdentificador as (
		select *, row_number() over (partition by redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante order by rdnFechaInicioNovedad2, ordenNov) as identificador2
		from marcarIngRet
		where procesado = 0
		)
		update a set novING_RET = a.novIngresoRetiro, CantNovCot = a.cantNov, Identificador = Identificador2
		from marcarIngRet as a 
		inner join actIdentificador as b on a.redNumeroIdentificacionCotizante = b.redNumeroIdentificacionCotizante and a.rdnTipoNovedad = b.rdnTipoNovedad and a.redId = b.redId and a.rdnId = b.rdnId
				
		--===== ACTUALIZACION DE LA SITUACIÓN PRIMARIA CONTROLANDO LAS FECHAS, CUANDO SON IGUALES. 
		;with sitPrimaria as 
		(select *, isnull(lag(rdnTipoNovedad) over (partition by redNumeroIdentificacionCotizante order by identificador, rdnFechaInicioNovedad2),'SIN_NOVEDAD') situacionPrimaria2 
			from #inicial where procesado = 0 and marcaReintegroOtro is null)
		update sitPrimaria set situacionPrimaria = situacionPrimaria2
		


		update #inicial set procesado = 1, EstadoCotizanteFinal = redOUTEstadoSolicitante, rdnAccionNovedadFinal = 'RELACIONAR_NOVEDAD', mensaje = '- No se logró registrar la novedad de retiro, la fecha de novedad es menor a la última fecha de ingreso'
		where rdnTipoNovedad = 'NOVEDAD_RET' and rdnFechaInicioNovedad2 < cotFechaIngreso

		--TODO: 
		--============= Evaluamos el estado del cotizante para el periodo de pago, esto solo para las novedades que sean de retiro o ingreso, si es que quedan en estado sin procesar. 
		;with actualizar as (select *,
		case when fechaPeriodo between cotFechaIngreso and	isnull(cotFechaRetiro, dbo.GetLocalDate()) and rdnTipoNovedad <> 'NOVEDAD_RET'
			and rdnFechaInicioNovedad2 <= (min(case when rdnTipoNovedad = 'NOVEDAD_RET' then ISNULL(rdnFechaFinNovedad,rdnFechaInicioNovedad2) else isnull(cotFechaRetiro,eomonth(fechaPeriodo)) end) over (partition by redTipoIdentificacionCotizante,redNumeroIdentificacionCotizante))
			then 'ACTIVO' 
			else 
				case when rdnTipoNovedad = 'NOVEDAD_RET' and redOUTEstadoSolicitante = 'ACTIVO' then 'ACTIVO'
					else  redOUTEstadoSolicitante
						end
					end
			as EstadoCotizanteFinalAct
		from #inicial
		where procesado = 0
		--and Identificador = 1 
		and marcaReintegroOtro is null
		)
		update actualizar set redOUTEstadoSolicitante = EstadoCotizanteFinalAct
		--=============================================================================================================================================================
		--=============================================================================================================================================================
		--=============================================================================================================================================================
		-- ================================================
		-- === Se inicia proceso para validación del GLPI 55870 - Aplicación de novedades por PILA para SM
			declare @NovIGE_LMA_IRL table (prmValor varchar(12), origen varchar(250))
			insert @NovIGE_LMA_IRL
			execute sp_execute_remote coreReferenceData, N'select prmValor from parametro with(nolock) where prmNombre = ''APLICAR_NOVEDADES_PILA_SUBSIDIO'''
			declare @Var_NovIGE_LMA_IRL varchar(12) 
			set @Var_NovIGE_LMA_IRL = (select distinct prmValor from @NovIGE_LMA_IRL)
		-- ================================================
		--=========================== INICIA VALIDACIÓN PARA LAS NOVEDADES IRL, IGE Y LMA PARA EL GLPI 55870 SEGÚN PARAMETRO
			if @Var_NovIGE_LMA_IRL = ('FALSE')
				begin
					update #inicial set EstadoCotizanteFinal = redOUTEstadoSolicitante, rdnAccionNovedadFinal = 'RELACIONAR_NOVEDAD', mensaje = '- La novedad no fue procesada', procesado = 1
					from #inicial
					where rdnTipoNovedad in ('NOVEDAD_LMA', 'NOVEDAD_IGE', 'NOVEDAD_IRP')
				end;
		--=========================== FINALIZA VALIDACIÓN PARA LAS NOVEDADES IRL, IGE Y LMA PARA EL GLPI 55870 SEGÚN PARAMETRO
		--===========================

update #inicial set

 EstadoCotizanteFinal = 
case when novING_RET = 1 and ingRetMismaFecha = 1
		then case when redOUTEstadoSolicitante <> 'ACTIVO' and rdnTipoNovedad = 'NOVEDAD_ING'
			then 'ACTIVO'
		when redOUTEstadoSolicitante <> 'ACTIVO' and rdnTipoNovedad = 'NOVEDAD_RET'
			then redOUTEstadoSolicitante
		else --'INACTIVO'
			case when rdnTipoNovedad = 'NOVEDAD_ING'
				then 'ACTIVO'
				else 'INACTIVO'
				end
		end
	else
		case when novING_RET = 1
			then case when rdnTipoNovedad = 'NOVEDAD_ING' and redOUTEstadoSolicitante <> 'ACTIVO' and situacionPrimaria = 'SIN_NOVEDAD' and cotEsTrabajadorReintegrable = 1 and rdnFechaInicioNovedad2 >= isnull(cotFechaRetiro,rdnFechaInicioNovedad2) then 'ACTIVO'
				  when rdnTipoNovedad = 'NOVEDAD_ING' and redOUTEstadoSolicitante <> 'ACTIVO' and situacionPrimaria = 'SIN_NOVEDAD' and cotEsTrabajadorReintegrable = 1 and rdnFechaInicioNovedad2 <= isnull(cotFechaRetiro,rdnFechaInicioNovedad2) then redOUTEstadoSolicitante
				  when rdnTipoNovedad = 'NOVEDAD_ING' and redOUTEstadoSolicitante <> 'ACTIVO' and situacionPrimaria = 'SIN_NOVEDAD' and cotEsTrabajadorReintegrable = 0 then redOUTEstadoSolicitante
				  when rdnTipoNovedad = 'NOVEDAD_ING' and redOUTEstadoSolicitante = 'ACTIVO' then redOUTEstadoSolicitante
				  when rdnTipoNovedad = 'NOVEDAD_RET' and redOUTEstadoSolicitante = 'ACTIVO' and situacionPrimaria = 'SIN_NOVEDAD' then 'INACTIVO'
				  when rdnTipoNovedad = 'NOVEDAD_RET' and redOUTEstadoSolicitante <> 'ACTIVO' then redOUTEstadoSolicitante
				  end
			else case when redOUTEstadoSolicitante <> 'ACTIVO' -- aca evaluamos los demás registros que no son ingresos y retiros 
				then redOUTEstadoSolicitante
				 else 'ACTIVO'
				end
		end
	end,
rdnAccionNovedadFinal = 
case when novING_RET = 1 and ingRetMismaFecha = 1
	then case when rdnTipoNovedad = 'NOVEDAD_ING' and redOUTEstadoSolicitante <> 'ACTIVO' then 'APLICAR_NOVEDAD'
		when rdnTipoNovedad = 'NOVEDAD_ING' and redOUTEstadoSolicitante = 'ACTIVO' then 'RELACIONAR_NOVEDAD'
		when rdnTipoNovedad = 'NOVEDAD_RET' and redOUTEstadoSolicitante <> 'ACTIVO' then 'RELACIONAR_NOVEDAD'
		when rdnTipoNovedad = 'NOVEDAD_RET' and redOUTEstadoSolicitante = 'ACTIVO' then 'APLICAR_NOVEDAD'
		else 'RELACIONAR_NOVEDAD'
		end
	else 
		case when novING_RET = 1
			then case when rdnTipoNovedad = 'NOVEDAD_ING' and redOUTEstadoSolicitante <> 'ACTIVO' and situacionPrimaria = 'SIN_NOVEDAD' and cotEsTrabajadorReintegrable = 1 and rdnFechaInicioNovedad2 >= isnull(cotFechaRetiro,rdnFechaInicioNovedad2) then 'APLICAR_NOVEDAD'
				  when rdnTipoNovedad = 'NOVEDAD_ING' and redOUTEstadoSolicitante <> 'ACTIVO' and situacionPrimaria = 'SIN_NOVEDAD' and cotEsTrabajadorReintegrable = 1 and rdnFechaInicioNovedad2 <= isnull(cotFechaRetiro,rdnFechaInicioNovedad2) then 'RELACIONAR_NOVEDAD'
				  when rdnTipoNovedad = 'NOVEDAD_ING' and redOUTEstadoSolicitante <> 'ACTIVO' and situacionPrimaria = 'SIN_NOVEDAD' and cotEsTrabajadorReintegrable = 0 then 'RELACIONAR_NOVEDAD'
				  when rdnTipoNovedad = 'NOVEDAD_ING' and redOUTEstadoSolicitante = 'ACTIVO' then 'RELACIONAR_NOVEDAD'
				  when rdnTipoNovedad = 'NOVEDAD_RET' and redOUTEstadoSolicitante = 'ACTIVO' and situacionPrimaria = 'SIN_NOVEDAD' and rdnFechaInicioNovedad2 > cotFechaIngreso  then 'APLICAR_NOVEDAD'
				  when rdnTipoNovedad = 'NOVEDAD_RET' and redOUTEstadoSolicitante = 'ACTIVO' and situacionPrimaria = 'SIN_NOVEDAD' and rdnFechaInicioNovedad2 <= cotFechaIngreso  then 'RELACIONAR_NOVEDAD'
				  when rdnTipoNovedad = 'NOVEDAD_RET' and redOUTEstadoSolicitante <> 'ACTIVO' then 'RELACIONAR_NOVEDAD'
				  end
			else case when redOUTEstadoSolicitante <> 'ACTIVO' -- aca evaluamos los demás registros que no son ingresos y retiros 
				then 'RELACIONAR_NOVEDAD'
				else case when rdnFechaInicioNovedad2 > cotFechaRetiro
					then 'RELACIONAR_NOVEDAD'
					else 'APLICAR_NOVEDAD'
					end
			 end
		end
	end,
mensaje = 
case when novING_RET = 1 and ingRetMismaFecha = 1
	then case when rdnTipoNovedad = 'NOVEDAD_ING' and redOUTEstadoSolicitante <> 'ACTIVO' then '- La novedad de ingreso fue procesada'
		when rdnTipoNovedad = 'NOVEDAD_ING' and redOUTEstadoSolicitante = 'ACTIVO' then '- No se logró registrar la novedad de ingreso'
		when rdnTipoNovedad = 'NOVEDAD_RET' and redOUTEstadoSolicitante <> 'ACTIVO' then '- No se logró registrar la novedad de retiro'
		when rdnTipoNovedad = 'NOVEDAD_RET' and redOUTEstadoSolicitante = 'ACTIVO' then '- Se procesó la novedad, el trabajador ha sido retirado de la caja'
		else '- No se logró registrar la novedad de ingreso'
		end
	else 
		case when novING_RET = 1
			then case when rdnTipoNovedad = 'NOVEDAD_ING' and redOUTEstadoSolicitante <> 'ACTIVO' and situacionPrimaria = 'SIN_NOVEDAD' and cotEsTrabajadorReintegrable = 1 and rdnFechaInicioNovedad2 >= isnull(cotFechaRetiro,rdnFechaInicioNovedad2) then '- La novedad de ingreso fue procesada'
				  when rdnTipoNovedad = 'NOVEDAD_ING' and redOUTEstadoSolicitante <> 'ACTIVO' and situacionPrimaria = 'SIN_NOVEDAD' and cotEsTrabajadorReintegrable = 1 and rdnFechaInicioNovedad2 <= isnull(cotFechaRetiro,rdnFechaInicioNovedad2) then '- No se logró registrar la novedad de ingreso'
				  when rdnTipoNovedad = 'NOVEDAD_ING' and redOUTEstadoSolicitante <> 'ACTIVO' and situacionPrimaria = 'SIN_NOVEDAD' and cotEsTrabajadorReintegrable = 0 then '- No se logró registrar la novedad de ingreso'
				  when rdnTipoNovedad = 'NOVEDAD_ING' and redOUTEstadoSolicitante = 'ACTIVO' then '- No es posible registrar novedad de ingreso, debido a que el trabajador se encuentra ACTIVO por este empleador en nuestra base de datos'
				  when rdnTipoNovedad = 'NOVEDAD_RET' and redOUTEstadoSolicitante = 'ACTIVO' and situacionPrimaria = 'SIN_NOVEDAD' and rdnFechaInicioNovedad2 > cotFechaIngreso  then '- Se procesó la novedad, el trabajador ha sido retirado de la caja'
				  when rdnTipoNovedad = 'NOVEDAD_RET' and redOUTEstadoSolicitante = 'ACTIVO' and situacionPrimaria = 'SIN_NOVEDAD' and rdnFechaInicioNovedad2 <= cotFechaIngreso  then '- No se logró registrar la novedad de retiro'
				  when rdnTipoNovedad = 'NOVEDAD_RET' and redOUTEstadoSolicitante <> 'ACTIVO' then '- No se logró registrar la novedad de retiro'
				  end
			else case when redOUTEstadoSolicitante <> 'ACTIVO' -- aca evaluamos los demás registros que no son ingresos y retiros 
				then (select renMensaje from referenciaNovedad where renSituacionPrimaria = 'SIN_NOVEDAD' and renEstadoCotizante = redOUTEstadoSolicitante and renTipoNovedad = rdnTipoNovedad)
				else case when rdnFechaInicioNovedad2 > cotFechaRetiro
					then '- No se logró registrar la novedad de ingreso'
					else (select renMensaje from referenciaNovedad where renSituacionPrimaria = 'SIN_NOVEDAD' and renEstadoCotizante = 'ACTIVO' and renTipoNovedad = rdnTipoNovedad)
					end
			 end
		end
	end,
procesado = 1
from #inicial
where Identificador = 1 and procesado = 0

--=== Actualizamos las fechas de ingreso y retiro, para evaluar en los flujos. 
	update #inicial set cotFechaIngreso = rdnFechaInicioNovedad2 where Identificador = 1 and procesado = 0 and rdnTipoNovedad = 'NOVEDAD_ING'
	update #inicial set cotFechaRetiro = rdnFechaFinNovedad where Identificador = 1 and procesado = 0 and rdnTipoNovedad = 'NOVEDAD_RET'
	
--=== Se reorganizan los identificadores, en el caso que guarde las novedades complementarias. 
		;with reordenaPrio as (select *, case when Identificador > 1 and procesado = 1 then 2 else 1 end as 'CambioIdentificador'
							  from #inicial),
		nuevoIdentificador as (select *, ROW_NUMBER() over (partition by redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante order by CambioIdentificador, Identificador) as 'NuevoIdentificador'
		from reordenaPrio)
		update b set b.Identificador = a.NuevoIdentificador
		from nuevoIdentificador as a
		inner join #inicial as b on a.redNumeroIdentificacionCotizante = b.redNumeroIdentificacionCotizante and a.Identificador = b.Identificador and a.procesado = b.procesado and a.rdnTipoNovedad = b.rdnTipoNovedad
--=== Finaliza proceso. 


		declare @finalNovedades as cursor
		declare 
		@redTipoIdentificacionCotizante varchar(50),
		@redNumeroIdentificacionCotizante varchar(30),
		@rdnTipoNovedad varchar(20),
		@rdnFechaInicioNovedad date,
		@rdnFechaFinNovedad date,
		@situacionPrimaria varchar (50),
		@Identificador smallInt,
		@novING_RET smallInt,
		@cotEsTrabajadorReintegrable smallInt,
		@fechaIngRetMismaFechaTabla date,
		@ingRetMismaFechaTabla smallInt,
		@cotFechaRetiro date,
		@cotFechaIngreso date
		
		set @finalNovedades = cursor fast_forward for
		select redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante, rdnTipoNovedad, rdnFechaInicioNovedad2, rdnFechaFinNovedad, situacionPrimaria, Identificador, novING_RET, cotEsTrabajadorReintegrable, fechaIngRetMismaFecha, ingRetMismaFecha, cotFechaRetiro, cotFechaIngreso
		from #inicial
		where procesado = 0
		order by redNumeroIdentificacionCotizante, Identificador, rdnFechaInicioNovedad2
		
		open @finalNovedades 
		fetch next from @finalNovedades into @redTipoIdentificacionCotizante, @redNumeroIdentificacionCotizante, @rdnTipoNovedad, @rdnFechaInicioNovedad, @rdnFechaFinNovedad, @situacionPrimaria, @Identificador, @novING_RET, @cotEsTrabajadorReintegrable, @fechaIngRetMismaFechaTabla, @ingRetMismaFechaTabla, @cotFechaRetiro, @cotFechaIngreso
		
		while @@FETCH_STATUS = 0
			begin
				-- VARIABLES NECESARIAS DEL REGISTRO ANTERIOR
		
				declare @EstadoCotizanteFinal varchar (60)
				set @EstadoCotizanteFinal = (select EstadoCotizanteFinal from #inicial where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador - 1 and procesado = 1)
				declare @fechaIni date 
				set @fechaIni = (select rdnFechaInicioNovedad2 from #inicial where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador - 1 and procesado = 1)
				declare @fechaFin date 
				set @fechaFin = (select rdnFechaFinNovedad from #inicial where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador - 1 and procesado = 1)
				declare @ingRetMismaFecha smallInt
				set @ingRetMismaFecha = (select ingRetMismaFecha from #inicial where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador - 1 and procesado = 1)
				declare @fechaIngRetMismaFecha date
				set @fechaIngRetMismaFecha = (select fechaIngRetMismaFecha from #inicial where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador - 1 and procesado = 1)
				declare @situPrimaria varchar(50)
				set @situPrimaria = (select situacionPrimaria from #inicial where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador - 1 and procesado = 1)
				declare @cotFechaIngreso2 date
				set @cotFechaIngreso2 = (select cotFechaIngreso from #inicial where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador - 1 and procesado = 1)
				declare @cotFechaRetiro2 date
				set @cotFechaRetiro2 = (select isnull(cotFechaRetiro,isnull(rdnFechaFinNovedad,rdnFechaInicioNovedad2)) from #inicial where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador - 1 and procesado = 1)
		
				--=== SE AGREGA PERIODO PARA EVALUAR CUANDO LAS FECHAS VIENEN VACIAS. 2023-07-24
				declare @periodo date 
				set @periodo = (select fechaPeriodo from #inicial where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador - 1 and procesado = 1)

				-- INICIAN LAS VALIDACIONES
				if @novING_RET = 0 and @ingRetMismaFecha = 1
					begin
						update #inicial set EstadoCotizanteFinal = @EstadoCotizanteFinal, rdnAccionNovedadFinal = 'RELACIONAR_NOVEDAD',	
						mensaje = (select renMensaje from referenciaNovedad with (nolock) where renSituacionPrimaria = iif(@situacionPrimaria = 'NOVEDAD_ING', 'SIN_NOVEDAD', @situacionPrimaria) and renEstadoCotizante = @EstadoCotizanteFinal and renTipoNovedad = @rdnTipoNovedad), 
						procesado = 1
						where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador
					end
				else if @novING_RET = 1 and @ingRetMismaFechaTabla = 1
					begin
						if @rdnTipoNovedad = 'NOVEDAD_RET' and @EstadoCotizanteFinal <> 'ACTIVO'
							begin
								update #inicial set EstadoCotizanteFinal = @EstadoCotizanteFinal, rdnAccionNovedadFinal = 'RELACIONAR_NOVEDAD',
								mensaje = '- No se logró registrar la novedad de retiro',
								procesado = 1, cotFechaIngreso = @cotFechaIngreso2, cotFechaRetiro = @cotFechaRetiro2
								where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador
							end
						else if @rdnTipoNovedad = 'NOVEDAD_RET' and @EstadoCotizanteFinal = 'ACTIVO'
							begin
								update #inicial set EstadoCotizanteFinal = 'INACTIVO', rdnAccionNovedadFinal = 'APLICAR_NOVEDAD',
								mensaje = '- Se procesó la novedad, el trabajador ha sido retirado de la caja',
								procesado = 1, cotFechaIngreso = @cotFechaIngreso2, cotFechaRetiro = @cotFechaRetiro2
								where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador
							end
						else if @rdnTipoNovedad = 'NOVEDAD_ING' and @EstadoCotizanteFinal = 'ACTIVO'
							begin
								update #inicial set EstadoCotizanteFinal = @EstadoCotizanteFinal, rdnAccionNovedadFinal = 'RELACIONAR_NOVEDAD',
								mensaje = '- No se logró registrar la novedad de ingreso',
								procesado = 1, cotFechaIngreso = @cotFechaIngreso2, cotFechaRetiro = @cotFechaRetiro2
								where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador
							end
						else if @rdnTipoNovedad = 'NOVEDAD_ING' and @EstadoCotizanteFinal <> 'ACTIVO'
							begin
								update #inicial set EstadoCotizanteFinal = 'ACTIVO', rdnAccionNovedadFinal = 'APLICAR_NOVEDAD',
								mensaje = '- La novedad de ingreso fue procesada',
								procesado = 1, cotFechaIngreso = @cotFechaIngreso2, cotFechaRetiro = @cotFechaRetiro2
								where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador
							end
					end
					
				else if @EstadoCotizanteFinal <> 'ACTIVO' and @rdnTipoNovedad = 'NOVEDAD_ING' and @cotEsTrabajadorReintegrable = 1 and @rdnFechaInicioNovedad > @cotFechaRetiro2
					begin
						update #inicial set EstadoCotizanteFinal = 'ACTIVO', rdnAccionNovedadFinal = 'APLICAR_NOVEDAD',	mensaje = '- La novedad de ingreso fue procesada', procesado = 1
						, cotFechaIngreso = @cotFechaIngreso2, cotFechaRetiro = @cotFechaRetiro2
						where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador
					end
				else if @EstadoCotizanteFinal <> 'ACTIVO' and @rdnTipoNovedad = 'NOVEDAD_ING' and @cotEsTrabajadorReintegrable = 0 and @rdnFechaInicioNovedad > @cotFechaRetiro2
					begin
						update #inicial set EstadoCotizanteFinal = @EstadoCotizanteFinal, rdnAccionNovedadFinal = 'RELACIONAR_NOVEDAD',	mensaje = '- No se logró registrar la novedad de ingreso', procesado = 1
						, cotFechaIngreso = @cotFechaIngreso2, cotFechaRetiro = @cotFechaRetiro2
						where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador
					end
				else if @EstadoCotizanteFinal <> 'ACTIVO' and @novING_RET = 0
					begin
						if @rdnFechaInicioNovedad between @cotFechaIngreso2 and @cotFechaRetiro2
							begin
								update #inicial set EstadoCotizanteFinal = 'ACTIVO', rdnAccionNovedadFinal = 'APLICAR_NOVEDAD',
								mensaje = (select renMensaje from referenciaNovedad with (nolock) where renSituacionPrimaria = iif(@situacionPrimaria = 'NOVEDAD_ING', 'SIN_NOVEDAD', @situacionPrimaria) and renEstadoCotizante = @EstadoCotizanteFinal and renTipoNovedad = @rdnTipoNovedad),
								procesado = 1, cotFechaIngreso = @cotFechaIngreso2, cotFechaRetiro = @cotFechaRetiro2
								where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador
							end
						else 
							begin
								--print 'no aplicadas las novedades'
								update #inicial set EstadoCotizanteFinal = @EstadoCotizanteFinal, rdnAccionNovedadFinal = 'RELACIONAR_NOVEDAD',	
								mensaje = (select renMensaje from referenciaNovedad with (nolock) where renSituacionPrimaria = iif(@situacionPrimaria = 'NOVEDAD_ING', 'SIN_NOVEDAD', @situacionPrimaria) and renEstadoCotizante = @EstadoCotizanteFinal and renTipoNovedad = @rdnTipoNovedad), 
								procesado = 1, cotFechaIngreso = @cotFechaIngreso2, cotFechaRetiro = @cotFechaRetiro2
								where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador
							end
					end
				else if @EstadoCotizanteFinal = 'ACTIVO' and @rdnTipoNovedad = 'NOVEDAD_ING'
					begin
						update #inicial set EstadoCotizanteFinal = @EstadoCotizanteFinal, rdnAccionNovedadFinal = 'RELACIONAR_NOVEDAD',
						mensaje = '- No es posible registrar novedad de ingreso, debido a que el trabajador se encuentra ACTIVO por este empleador en nuestra base de datos',
						procesado = 1, cotFechaIngreso = @cotFechaIngreso2, cotFechaRetiro = @cotFechaRetiro2
						where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador
					end
				else if @EstadoCotizanteFinal = 'ACTIVO' and @novING_RET = 0 and isnull(isnull(@rdnFechaInicioNovedad,@fechaIni), @periodo) < isnull(@cotFechaIngreso2, getdate())
					begin
						update #inicial set EstadoCotizanteFinal = @EstadoCotizanteFinal, rdnAccionNovedadFinal = 'RELACIONAR_NOVEDAD',
						mensaje = (select renMensaje from referenciaNovedad with (nolock) where renSituacionPrimaria = iif(@situacionPrimaria = 'NOVEDAD_ING', 'SIN_NOVEDAD', @situacionPrimaria) and renEstadoCotizante = @EstadoCotizanteFinal and renTipoNovedad = @rdnTipoNovedad),
						procesado = 1, cotFechaIngreso = @cotFechaIngreso2 --, cotFechaRetiro = @cotFechaRetiro2
						where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador
					end
				else if @EstadoCotizanteFinal = 'ACTIVO' and @novING_RET = 0 and isnull(isnull(@rdnFechaInicioNovedad,@fechaIni), @periodo) >= isnull(@cotFechaIngreso2,@fechaIni) and @situacionPrimaria <> 'NOVEDAD_RET' --and @rdnFechaInicioNovedad < isnull(@cotFechaRetiro, getdate())
					begin
						update #inicial set EstadoCotizanteFinal = @EstadoCotizanteFinal, rdnAccionNovedadFinal = 'APLICAR_NOVEDAD',
						mensaje = (select renMensaje from referenciaNovedad with (nolock) where renSituacionPrimaria = iif(@situacionPrimaria = 'NOVEDAD_ING', 'SIN_NOVEDAD', @situacionPrimaria) and renEstadoCotizante = @EstadoCotizanteFinal and renTipoNovedad = @rdnTipoNovedad),
						procesado = 1, cotFechaIngreso = @cotFechaIngreso2--, cotFechaRetiro = @cotFechaRetiro2
						where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador
					end
				else if @EstadoCotizanteFinal = 'ACTIVO' and @novING_RET = 0 and isnull(isnull(@rdnFechaInicioNovedad,@fechaIni),@periodo) > isnull(@cotFechaRetiro, convert(date, getdate()))
					begin
						update #inicial set EstadoCotizanteFinal = @EstadoCotizanteFinal, rdnAccionNovedadFinal = 'RELACIONAR_NOVEDAD',
						mensaje = (select renMensaje from referenciaNovedad with (nolock) where renSituacionPrimaria = iif(@situacionPrimaria = 'NOVEDAD_ING', 'SIN_NOVEDAD', @situacionPrimaria) and renEstadoCotizante = @EstadoCotizanteFinal and renTipoNovedad = @rdnTipoNovedad),
						procesado = 1, cotFechaIngreso = @cotFechaIngreso2--, cotFechaRetiro = @cotFechaRetiro2
						where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador
					end
				else if @EstadoCotizanteFinal = 'ACTIVO' and @rdnTipoNovedad = 'NOVEDAD_RET' and @situacionPrimaria <> 'NOVEDAD_RET'
					begin
						update #inicial set EstadoCotizanteFinal = 'INACTIVO', rdnAccionNovedadFinal = 'APLICAR_NOVEDAD',
						mensaje = '- Se procesó la novedad, el trabajador ha sido retirado de la caja',
						procesado = 1, cotFechaIngreso = @cotFechaIngreso2, cotFechaRetiro = @cotFechaRetiro2
						where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador
					end
				else if @EstadoCotizanteFinal <> 'ACTIVO' and @rdnTipoNovedad = 'NOVEDAD_ING' and @cotEsTrabajadorReintegrable = 1
					begin
						update #inicial set EstadoCotizanteFinal = 'ACTIVO', rdnAccionNovedadFinal = 'APLICAR_NOVEDAD',	mensaje = '- La novedad de ingreso fue procesada', procesado = 1
						where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador
					end
				else if @EstadoCotizanteFinal <> 'ACTIVO' and @rdnTipoNovedad = 'NOVEDAD_RET'
					begin
						update #inicial set EstadoCotizanteFinal = @EstadoCotizanteFinal, rdnAccionNovedadFinal = 'RELACIONAR_NOVEDAD',
						mensaje = '- No se logró registrar la novedad de retiro',
						procesado = 1, cotFechaIngreso = @cotFechaIngreso2, cotFechaRetiro = @cotFechaRetiro2
						where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador
					end
					
				fetch next from @finalNovedades into @redTipoIdentificacionCotizante, @redNumeroIdentificacionCotizante, @rdnTipoNovedad, @rdnFechaInicioNovedad, @rdnFechaFinNovedad, @situacionPrimaria, @Identificador, @novING_RET, @cotEsTrabajadorReintegrable, @fechaIngRetMismaFechaTabla, @ingRetMismaFechaTabla
				,@cotFechaRetiro, @cotFechaIngreso  -- Continuamos con el siguiente registro


			end
		
	close @finalNovedades;
	deallocate @finalNovedades;	
END;