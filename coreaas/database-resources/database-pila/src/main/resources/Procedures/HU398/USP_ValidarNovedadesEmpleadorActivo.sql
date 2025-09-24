-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2017/06/16
-- Description:	Procedimiento almacenado encargado  de recorrer los cotizantes
-- de un empleador para el proceso de novedades
-- Update: Andres Julian Rocha Cruz
-- Update date: 2021/02/17
-- Se elimina cursor y se envia logica de transformacion de datos a function
-- Fn_RegistrarRelacionarNovedadesReferenciales cruzando datos a traves de CROSS APPLY
-- Cambios realizados por Robinson Castillo - 2022-01-21
-- Se realiza cambio a la logica para que las novedades se apliquen de forma correcta, teniendo en cuenta el registro anterior,
-- Esto aplica para novedades retroactivas y regulares. 
-- Cambios realizados por Robinson Castillo - 2022-02-03
-- Cambios realizados por Robinson Castillo - 2022-06-21
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_ValidarNovedadesEmpleadorActivo]
(
	@IdRegistroGeneral BIGINT,
	@EsSimulado BIT = 0,
	@EsRegistroManual BIT = 0
)
AS
BEGIN
SET NOCOUNT ON;	

			drop table if exists #tmpRegistroDetallado
			drop table if exists #inicial
			drop table if exists #resultado_otro
			drop table if exists #resultado 

			declare @localDate datetime = dbo.getLocalDate(); 
			INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
			VALUES (@localDate, 'INICIA USP_ValidarNovedadesEmpleadorActivo  @registroGeneral=' + CAST(@IdRegistroGeneral AS VARCHAR(20)) + N' Inicia proceso para novedades', 'Sin error');

			;with borradoNovReintegroDupli as (
			select rdn.*, row_number() over (partition by rdn.rdnRegistroDetallado, rdn.rdnTipoNovedad order by rdn.rdnId) as id, 
			case when rd.redNovIngreso is null and rdn.rdnTipotransaccion = 'NOVEDAD_REINTEGRO' then 1 else 0 end as borrar
			from staging.RegistroGeneral as r with (nolock)
			inner join staging.RegistroDetallado as rd with (nolock) on r.regId = rd.redRegistroGeneral
			inner join staging.RegistroDetalladoNovedad as rdn with (nolock) on rd.redId = rdn.rdnRegistroDetallado
			where  r.regId = @IdRegistroGeneral
			)
			select rdnId
			into #novReitegro
			from borradoNovReintegroDupli
			where borrar = 1
			
			delete from staging.RegistroDetalladoNovedad where rdnId in (select rdnId from #novReitegro)
			delete from dbo.TemNovedad_val_proc where tenRegistroDetalladoNovedad in (select rdnId from #novReitegro)
			delete from dbo.TemNovedad where tenRegistroDetalladoNovedad in (select rdnId from #novReitegro)

-- ================================================
-- === Se inicia proceso para validación del GLPI 55870 - Aplicación de novedades por PILA para SM
	declare @NovIGE_LMA_IRL table (prmValor varchar(12), origen varchar(250))
	insert @NovIGE_LMA_IRL
	execute sp_execute_remote coreReferenceData, N'select prmValor from parametro with(nolock) where prmNombre = ''APLICAR_NOVEDADES_PILA_SUBSIDIO'''
	declare @Var_NovIGE_LMA_IRL varchar(12) 
	set @Var_NovIGE_LMA_IRL = (select prmValor from @NovIGE_LMA_IRL)
-- ================================================

	SELECT 
		regTipoIdentificacionAportante,
		regNumeroIdentificacionAportante,
		redTipoIdentificacionCotizante,
		redNumeroIdentificacionCotizante,
		regOUTSMMLV,
		redOUTEstadoSolicitante,
		redOUTFechaIngresoCotizante,
		regPeriodoAporte,
		regTransaccion,
		redId,
		----------------------------
		redPrimerNombre,
		redSegundoNombre,
		redPrimerApellido,
		redSegundoApellido,
		redCodDepartamento,
		redCodMunicipio,
		-----------------------------------------------------------------
		ISNULL(redOUTEsTrabajadorReintegrable, 0) redOUTEsTrabajadorReintegrable,
		redOUTTipoNovedadSituacionPrimaria,
		redOUTFechaInicioNovedadSituacionPrimaria,
		redOUTFechaFinNovedadSituacionPrimaria
	INTO #tmpRegistroDetallado
	FROM staging.RegistroDetallado red with (nolock)
	INNER JOIN staging.RegistroGeneral reg with (nolock) ON red.redRegistroGeneral = reg.regId
	WHERE red.redRegistroGeneral = @IdRegistroGeneral
	AND (
		redNovIngreso IS NOT NULL OR
		redNovRetiro IS NOT NULL OR
		redNovVSP IS NOT NULL OR
		redNovVST IS NOT NULL OR
		redNovSLN IS NOT NULL OR
		redNovIGE IS NOT NULL OR
		redNovLMA IS NOT NULL OR
		redNovVACLR IS NOT NULL OR
		redDiasIRL IS NOT NULL 
		)
	AND ISNULL(red.redCorrecciones,'') <> 'A'
	AND red.redOUTTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'

	--===============================================================
	--============= Ajuste para tener en cuenta la población fallecida. 
	--===============================================================
	create table #CotpersonaFallecido (perNumeroIdentificacion varchar(20), perTipoIdentificacion varchar(30), pedFallecido bit, fechaRegistro datetime, origen varchar(250))
	insert #CotpersonaFallecido
	execute sp_execute_remote coreReferenceData, N'select * from dbo.CotpersonaFallecido'


create table #inicial (rdnId bigint, redId bigInt, redOUTEstadoSolicitante varchar (50), redTipoIdentificacionCotizante varchar(50),redNumeroIdentificacionCotizante varchar(30), rdnTipoTransaccion varchar(100), rdnTipoNovedad varchar(20), rdnAccionNovedad varchar (30), 
rdnFechaInicioNovedad date, rdnFechaInicioNovedad2 date, rdnFechaFinNovedad date, regPeriodoAporte varchar(7), fechaPeriodo varchar(10), redDiasCotizados smallInt, cotEsTrabajadorReintegrable smallInt, TipoPago varchar (20), cotFechaIngreso date, 
cotFechaRetiro date, cotEstadoAfiliado varchar(50), Identificador smallInt, CantNovCot smallInt, procesado smallInt, novING_RET smallInt, ingRetMismaFecha smallInt, fechaIngRetMismaFecha date, situacionPrimaria varchar (50),
EstadoCotizanteFinal varchar (60), rdnAccionNovedadFinal varchar (100), mensaje varchar(250), marcaReintegroOtro smallInt,rdnMensajeCumpleSuc varchar(200))

insert #inicial 
select rdn.rdnId, rd.redId, isnull(redOUTEstadoSolicitante,'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES') as redOUTEstadoSolicitante, redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante,rdn.rdnTipoTransaccion, rdn.rdnTipoNovedad, rdn.rdnAccionNovedad
,case when rdnTipoNovedad = 'NOVEDAD_ING' and rdn.rdnFechaInicioNovedad is null
	  then dateadd(dd, 1,eomonth(convert(date,r.regPeriodoAporte +'-01'),-1))
	  when rdnTipoNovedad = 'NOVEDAD_RET' and rdn.rdnFechaInicioNovedad is null
	  then eomonth(convert(Date,r.regPeriodoAporte +'-01'))
	  else rdnFechaInicioNovedad end as rdnFechaInicioNovedad
	  -------------****************************-------------
	  -- TODO: ESTE CAMPO SE CREA PARA PODER CONTROLAR LAS NOVEDADES QUE VIENEN SIN FECHA.
	  -------------****************************-------------
,case when rdnTipoNovedad = 'NOVEDAD_ING' and rdn.rdnFechaInicioNovedad is null
	  then dateadd(dd, 1,eomonth(convert(date,r.regPeriodoAporte +'-01'),-1))
	  when rdnTipoNovedad = 'NOVEDAD_RET' and rdn.rdnFechaInicioNovedad is null
	  then isnull(rdn.rdnFechaFinNovedad, eomonth(convert(Date,r.regPeriodoAporte +'-01')))
	  when rdnTipoNovedad in ('NOVEDAD_VST', 'NOVEDAD_VSP','NOVEDAD_SLN','NOVEDAD_VAC_LR') and rdn.rdnFechaInicioNovedad is null
	  then eomonth(convert(Date,r.regPeriodoAporte +'-01'))
	  when rdnTipoNovedad not in ('NOVEDAD_ING', 'NOVEDAD_RET','NOVEDAD_VST') and rdn.rdnFechaInicioNovedad is null
	  then case when right(r.regPeriodoAporte,2) = '02'
			then case when rd.redDiasCotizados > 28
				then convert(Date,r.regPeriodoAporte + N'-' + convert(varchar(2),28))
				else convert(Date,r.regPeriodoAporte + N'-' + convert(varchar(2),rd.redDiasCotizados))
				end
			end
	  else rdnFechaInicioNovedad end as rdnFechaInicioNovedad2
	  -------------****************************-------------
	  -- TODO: FINALIZA CAMPO SE CREA PARA PODER CONTROLAR LAS NOVEDADES QUE VIENEN SIN FECHA. 
	  -------------****************************-------------
,rdn.rdnFechaFinNovedad, r.regPeriodoAporte, concat(r.regPeriodoAporte, N'-01') as fechaPeriodo, rd.redDiasCotizados
--, isnull(cotr.aplicarReintegro,0) as cotEsTrabajadorReintegrable
, case when cotr.perNumeroIdentificacion is null then 0
		when fall.pedFallecido = 1 then 0
		when cotr.roaEstadoAfiliado = 'ACTIVO' then 1
		when cotr.roaEstadoAfiliado = 'INACTIVO' and DATEDIFF(day, convert(date,cotr.roaFechaRetiro), convert(date,DATEADD(MONTH, DATEDIFF(MONTH, 0,convert(Date,r.regPeriodoAporte +'-01')), 0))) <= cotr.tiempoReinAfi then 1
		else 0 end as cotEsTrabajadorReintegrable
,CASE 
	WHEN (cotr.roaOportunidadPago IS NULL OR cotr.roaOportunidadPago = 'MES_VENCIDO' or cotr.roaOportunidadPago = 'MES_ACTUAL') THEN
		CASE 
			WHEN (DATEADD(DAY,1,EOMONTH(DATEADD(MONTH, -1, (select dbo.GetLocalDate())),-1)) > CONVERT(DATE,r.regPeriodoAporte+'-01',120)) THEN 'PERIODO_RETROACTIVO'
			WHEN (DATEADD(DAY,1,EOMONTH(DATEADD(MONTH, -1, (select dbo.GetLocalDate())),-1)) = CONVERT(DATE,r.regPeriodoAporte+'-01',120)) THEN 'PERIODO_REGULAR'
			WHEN (DATEADD(DAY,1,EOMONTH(DATEADD(MONTH, -1, (select dbo.GetLocalDate())),-1)) < CONVERT(DATE,r.regPeriodoAporte+'-01',120)) THEN 'PERIODO_FUTURO'
		END
	WHEN (cotr.roaOportunidadPago = 'ANTICIPADO') THEN
		CASE 
			WHEN (DATEADD(DAY,1,EOMONTH(DATEADD(MONTH, 1, (select dbo.GetLocalDate())),-1)) > CONVERT(DATE,r.regPeriodoAporte+'-01',120)) THEN 'PERIODO_RETROACTIVO'
			WHEN (DATEADD(DAY,1,EOMONTH(DATEADD(MONTH, 1, (select dbo.GetLocalDate())),-1)) = CONVERT(DATE,r.regPeriodoAporte+'-01',120)) THEN 'PERIODO_REGULAR'
			WHEN (DATEADD(DAY,1,EOMONTH(DATEADD(MONTH, 1, (select dbo.GetLocalDate())),-1)) < CONVERT(DATE,r.regPeriodoAporte+'-01',120)) THEN 'PERIODO_FUTURO'
		END
	END as TipoPago
, c.roaFechaIngreso as cotFechaIngreso
, c.roaFechaRetiro as cotFechaRetiro
, c.roaEstadoAfiliado as cotEstadoAfiliado
, 0 as Identificador
, dense_rank() over (partition by redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante, rdnTipoNovedad order by rdnTipoNovedad) as CantNovCot
, 0 as procesado
, 0 as novING_RET
, 0 as ingRetMismaFecha
, dateadd(dd, 1,eomonth(convert(date,r.regPeriodoAporte +'-01'),-1)) as fechaIngRetMismaFecha
,'SIN_NOVEDAD' as situacionPrimaria
,null as EstadoCotizanteFinal
,null as rdnAccionNovedadFinal
,null as mensaje
,null as marcaReintegroOtro
,rdn.rdnMensajeNovedad
from staging.RegistroGeneral as r with (nolock)
inner join staging.RegistroDetallado as rd with (nolock) on r.regId = rd.redRegistroGeneral
inner join staging.RegistroDetalladoNovedad as rdn with (nolock) on rd.redId = rdn.rdnRegistroDetallado
left join dbo.cotReintegro as cotr on rd.redTipoIdentificacionCotizante = cotr.perTipoIdentificacion and rd.redNumeroIdentificacionCotizante = cotr.perNumeroIdentificacion
left join dbo.cotFechaIngreso as c on rd.redTipoIdentificacionCotizante = c.perTipoIdentificacionCot and c.perNumeroIdentificacionCot = rd.redNumeroIdentificacionCotizante and c.perNumeroIdentificacionAport = r.regNumeroIdentificacionAportante
left join #CotpersonaFallecido as fall on rd.redTipoIdentificacionCotizante = fall.perTipoIdentificacion and rd.redNumeroIdentificacionCotizante = fall.perNumeroIdentificacion
where redRegistroGeneral = @IdRegistroGeneral and ISNULL(rd.redCorrecciones,'') <> 'A' and rd.redOUTTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
--and isnull(rdn.rdnMensajeNovedad,'') <> 'Novedad por cumplimiento de aportes'
order by rdnFechaInicioNovedad2


	;with cumpleAporte as (
	select *, sum(case when rdnMensajeCumpleSuc = N'Novedad por cumplimiento de aportes' then 1 else null end) over (partition by redNumeroIdentificacionCotizante) as total
	from #inicial)
	update a set a.redOUTEstadoSolicitante = N'ACTIVO', rdnMensajeCumpleSuc = 'novedad sucursal'
	from cumpleAporte as a
	where total = 1

	update #inicial set EstadoCotizanteFinal = 'ACTIVO', rdnAccionNovedadFinal = 'APLICAR_NOVEDAD', mensaje = N'Novedad por cumplimiento de aportes', procesado = 1
	where rdnTipoNovedad = 'NOVEDAD_ING'
	and rdnMensajeCumpleSuc = 'novedad sucursal'

	-- ACTUALIZAR REGISTRO REINTEGRABLE. 
	update cotr set cotr.aplicarReintegro = a.cotEsTrabajadorReintegrable
	from #inicial as a
	inner join dbo.cotReintegro as cotr on a.redNumeroIdentificacionCotizante = cotr.perNumeroIdentificacion 
	-- FINALIZA ACTUALIZAR REGISTRO REINTEGRABLE. 


--==== Se abre el proceso, para periodo troactivo.
declare @tipoPago varchar(25) = (select distinct TipoPago from #inicial)

if @tipoPago = 'PERIODO_RETROACTIVO'
	begin
		delete from #inicial where rdnMensajeCumpleSuc = 'novedad sucursal'
		execute [dbo].[USP_ValidarNovedadesEmpleadorActivoRetroactivos]
	end

else	
	begin --- Este es el begin donde se procesan las novedades que no son retroactivas. 

--===TODO: 
--=== Proceso para aplicar reintegro por otras empresas.
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
		where idRelacionarProPrimerValor in ('NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES', 'NO_FORMALIZADO_CON_INFORMACION') or idRelacionarProPrimerValor is null
	
--=========================== INICIA PROCESO PARA CONTROL DE NOVEDADES VST, CUANDO VIENEN VARIAS POR EL MISMO COTIZANTE. 
;with controlVST as (
select *, case when rdnTipoNovedad = 'NOVEDAD_VST' then row_number() over (partition by redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante order by rdnTipoNovedad) else 0 end as contadorVST
from #inicial
where rdnTipoNovedad = 'NOVEDAD_VST')
update controlVST set EstadoCotizanteFinal = redOUTEstadoSolicitante, rdnAccionNovedadFinal = 'RELACIONAR_NOVEDAD', mensaje = '- No se logró registrar la novedad de variación transitoria del salario', procesado = 1
where contadorVST > 1
---- FINALIZA PROCESO PARA CONTROL DE NOVEDADES VST, CUANDO VIENEN VARIAS POR EL MISMO COTIZANTE. 


---- TRATAMIENTO PARA NOVEDADES INGRESO Y RETIRO EN LA MISMA FECHA SE LE DA PRIORIDAD A LA NOVEDAD DE RETIRO. 
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
----FINALIZA TRATAMIENTO PARA NOVEDADES INGRESO Y RETIRO EN LA MISMA FECHA SE LE DA PRIORIDAD A LA NOVEDAD DE RETIRO. 
-----***********
-----***********

;with marcarIngRet as 
(select *, case when rdnTipoNovedad in ('NOVEDAD_ING', 'NOVEDAD_RET') then 1 else 0  end novIngresoRetiro
		, sum(CantNovCot) over (partition by redNumeroIdentificacionCotizante) as cantNov
		, case when rdnTipoNovedad = 'NOVEDAD_ING' then 'ANOVEDAD_ING'
			   when rdnTipoNovedad = 'NOVEDAD_RET' then 'ZNOVEDAD_RET'
			   else rdnTipoNovedad end ordenNov
	from #inicial where TipoPago = 'PERIODO_REGULAR' and procesado = 0 and marcaReintegroOtro is null),
actIdentificador as (
select *, row_number() over (partition by redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante order by rdnFechaInicioNovedad2, ordenNov) as identificador2
from marcarIngRet
)
update a set novING_RET = a.novIngresoRetiro, CantNovCot = a.cantNov, Identificador = Identificador2
from marcarIngRet as a 
inner join actIdentificador as b on a.redNumeroIdentificacionCotizante = b.redNumeroIdentificacionCotizante and a.rdnTipoNovedad = b.rdnTipoNovedad and a.redId = b.redId and a.rdnId = b.rdnId

--- ACTUALIZACION DE LA SITUACIÓN PRIMARIA CONTROLANDO LAS FECHAS, CUANDO SON IGUALES. 
;with sitPrimaria as 
(select *, isnull(lag(rdnTipoNovedad) over (partition by redNumeroIdentificacionCotizante order by identificador, rdnFechaInicioNovedad2),'SIN_NOVEDAD') situacionPrimaria2 
	from #inicial where TipoPago = 'PERIODO_REGULAR' and procesado = 0 and marcaReintegroOtro is null)
update sitPrimaria set situacionPrimaria = situacionPrimaria2

--=========================== INICIA VALIDACIÓN PARA LAS NOVEDADES IRL, IGE Y LMA PARA EL GLPI 55870 SEGÚN PARAMETRO
		if @Var_NovIGE_LMA_IRL = ('FALSE')
			begin
				update #inicial set EstadoCotizanteFinal = redOUTEstadoSolicitante, rdnAccionNovedadFinal = 'RELACIONAR_NOVEDAD', mensaje = '- La novedad no fue procesada', procesado = 1
				from #inicial
				where rdnTipoNovedad in ('NOVEDAD_LMA', 'NOVEDAD_IGE', 'NOVEDAD_IRP')
			end
--=========================== FINALIZA VALIDACIÓN PARA LAS NOVEDADES IRL, IGE Y LMA PARA EL GLPI 55870 SEGÚN PARAMETRO


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
			then case when rdnTipoNovedad = 'NOVEDAD_ING' and redOUTEstadoSolicitante <> 'ACTIVO' and situacionPrimaria = 'SIN_NOVEDAD' and cotEsTrabajadorReintegrable = 1 and rdnFechaInicioNovedad > cotFechaRetiro then 'ACTIVO'
				  when rdnTipoNovedad = 'NOVEDAD_ING' and redOUTEstadoSolicitante <> 'ACTIVO' and situacionPrimaria = 'SIN_NOVEDAD' and cotEsTrabajadorReintegrable = 1 and rdnFechaInicioNovedad <= cotFechaRetiro then redOUTEstadoSolicitante
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
			then case when rdnTipoNovedad = 'NOVEDAD_ING' and redOUTEstadoSolicitante <> 'ACTIVO' and situacionPrimaria = 'SIN_NOVEDAD' and cotEsTrabajadorReintegrable = 1 and rdnFechaInicioNovedad > cotFechaRetiro then 'APLICAR_NOVEDAD'
				  when rdnTipoNovedad = 'NOVEDAD_ING' and redOUTEstadoSolicitante <> 'ACTIVO' and situacionPrimaria = 'SIN_NOVEDAD' and cotEsTrabajadorReintegrable = 1 and rdnFechaInicioNovedad <= cotFechaRetiro then 'RELACIONAR_NOVEDAD'
				  when rdnTipoNovedad = 'NOVEDAD_ING' and redOUTEstadoSolicitante <> 'ACTIVO' and situacionPrimaria = 'SIN_NOVEDAD' and cotEsTrabajadorReintegrable = 0 then 'RELACIONAR_NOVEDAD'
				  when rdnTipoNovedad = 'NOVEDAD_ING' and redOUTEstadoSolicitante = 'ACTIVO' then 'RELACIONAR_NOVEDAD'
				  when rdnTipoNovedad = 'NOVEDAD_RET' and redOUTEstadoSolicitante = 'ACTIVO' and situacionPrimaria = 'SIN_NOVEDAD' and rdnFechaInicioNovedad > cotFechaIngreso  then 'APLICAR_NOVEDAD'
				  when rdnTipoNovedad = 'NOVEDAD_RET' and redOUTEstadoSolicitante = 'ACTIVO' and situacionPrimaria = 'SIN_NOVEDAD' and rdnFechaInicioNovedad <= cotFechaIngreso  then 'RELACIONAR_NOVEDAD'
				  when rdnTipoNovedad = 'NOVEDAD_RET' and redOUTEstadoSolicitante <> 'ACTIVO' then 'RELACIONAR_NOVEDAD'
				  end
			else case when redOUTEstadoSolicitante <> 'ACTIVO' -- aca evaluamos los demás registros que no son ingresos y retiros 
				then 'RELACIONAR_NOVEDAD'
				else 'APLICAR_NOVEDAD'
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
			then case when rdnTipoNovedad = 'NOVEDAD_ING' and redOUTEstadoSolicitante <> 'ACTIVO' and situacionPrimaria = 'SIN_NOVEDAD' and cotEsTrabajadorReintegrable = 1 and rdnFechaInicioNovedad > cotFechaRetiro then '- La novedad de ingreso fue procesada'
				  when rdnTipoNovedad = 'NOVEDAD_ING' and redOUTEstadoSolicitante <> 'ACTIVO' and situacionPrimaria = 'SIN_NOVEDAD' and cotEsTrabajadorReintegrable = 1 and rdnFechaInicioNovedad <= cotFechaRetiro then '- No se logró registrar la novedad de ingreso'
				  when rdnTipoNovedad = 'NOVEDAD_ING' and redOUTEstadoSolicitante <> 'ACTIVO' and situacionPrimaria = 'SIN_NOVEDAD' and cotEsTrabajadorReintegrable = 0 then '- No se logró registrar la novedad de ingreso'
				  when rdnTipoNovedad = 'NOVEDAD_ING' and redOUTEstadoSolicitante = 'ACTIVO' then '- No es posible registrar novedad de ingreso, debido a que el trabajador se encuentra ACTIVO por este empleador en nuestra base de datos'
				  when rdnTipoNovedad = 'NOVEDAD_RET' and redOUTEstadoSolicitante = 'ACTIVO' and situacionPrimaria = 'SIN_NOVEDAD' and rdnFechaInicioNovedad > cotFechaIngreso  then '- Se procesó la novedad, el trabajador ha sido retirado de la caja'
				  when rdnTipoNovedad = 'NOVEDAD_RET' and redOUTEstadoSolicitante = 'ACTIVO' and situacionPrimaria = 'SIN_NOVEDAD' and rdnFechaInicioNovedad <= cotFechaIngreso  then '- No se logró registrar la novedad de retiro'
				  when rdnTipoNovedad = 'NOVEDAD_RET' and redOUTEstadoSolicitante <> 'ACTIVO' then '- No se logró registrar la novedad de retiro'
				  end
			else case when redOUTEstadoSolicitante <> 'ACTIVO' -- aca evaluamos los demás registros que no son ingresos y retiros 
				then (select renMensaje from referenciaNovedad where renSituacionPrimaria = 'SIN_NOVEDAD' and renEstadoCotizante = redOUTEstadoSolicitante and renTipoNovedad = rdnTipoNovedad)
				else (select renMensaje from referenciaNovedad where renSituacionPrimaria = 'SIN_NOVEDAD' and renEstadoCotizante = 'ACTIVO' and renTipoNovedad = rdnTipoNovedad)
			 end
		end
	end,
procesado = 1
from #inicial
where Identificador = 1 and procesado = 0 and TipoPago = 'PERIODO_REGULAR' and marcaReintegroOtro is null


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
@ingRetMismaFechaTabla smallInt

set @finalNovedades = cursor fast_forward for
select redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante, rdnTipoNovedad, rdnFechaInicioNovedad2, rdnFechaFinNovedad, situacionPrimaria, Identificador, novING_RET, cotEsTrabajadorReintegrable, fechaIngRetMismaFecha, ingRetMismaFecha
from #inicial
where procesado = 0 and TipoPago = 'PERIODO_REGULAR'
order by redNumeroIdentificacionCotizante, Identificador, rdnFechaInicioNovedad2

open @finalNovedades 
fetch next from @finalNovedades into @redTipoIdentificacionCotizante, @redNumeroIdentificacionCotizante, @rdnTipoNovedad, @rdnFechaInicioNovedad, @rdnFechaFinNovedad, @situacionPrimaria, @Identificador, @novING_RET, @cotEsTrabajadorReintegrable, @fechaIngRetMismaFechaTabla, @ingRetMismaFechaTabla

while @@FETCH_STATUS = 0
	begin
		-- VARIABLES NECESARIAS DEL REGISTRO ANTERIOR

		declare @EstadoCotizanteFinal varchar (60)
		set @EstadoCotizanteFinal = (select EstadoCotizanteFinal from #inicial where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador - 1 and procesado = 1)
		declare @fechaIni date 
		set @fechaIni = (select rdnFechaInicioNovedad from #inicial where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador - 1 and procesado = 1)
		declare @fechaFin date 
		set @fechaFin = (select rdnFechaFinNovedad from #inicial where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador - 1 and procesado = 1)
		declare @ingRetMismaFecha smallInt
		set @ingRetMismaFecha = (select ingRetMismaFecha from #inicial where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador - 1 and procesado = 1)
		declare @fechaIngRetMismaFecha date
		set @fechaIngRetMismaFecha = (select fechaIngRetMismaFecha from #inicial where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador - 1 and procesado = 1)
		declare @cotFechaIngreso2 date
		set @cotFechaIngreso2 = (select cotFechaIngreso from #inicial where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador - 1 and procesado = 1)
		declare @fechaFin2 date
		set @fechaFin2 = (select rdnFechaInicioNovedad2 from #inicial where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador and procesado = 0)

		-- INICIAN LAS VALIDACIONES
		if @novING_RET = 0 and @ingRetMismaFecha = 1
			begin
				update #inicial set EstadoCotizanteFinal = @EstadoCotizanteFinal, rdnAccionNovedadFinal = 'RELACIONAR_NOVEDAD',	
				mensaje = (select renMensaje from referenciaNovedad with (nolock) where renSituacionPrimaria = iif(@situacionPrimaria = 'NOVEDAD_ING', 'SIN_NOVEDAD', @situacionPrimaria) and renEstadoCotizante = @EstadoCotizanteFinal and renTipoNovedad = @rdnTipoNovedad), 
				procesado = 1, cotFechaIngreso = @cotFechaIngreso2, cotFechaRetiro = @fechaFin
				where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador
			end
		else if @novING_RET = 1 and @ingRetMismaFechaTabla = 1
			begin
				if @rdnTipoNovedad = 'NOVEDAD_RET' and @EstadoCotizanteFinal <> 'ACTIVO'
					begin
						update #inicial set EstadoCotizanteFinal = @EstadoCotizanteFinal, rdnAccionNovedadFinal = 'RELACIONAR_NOVEDAD',
						mensaje = '- No se logró registrar la novedad de retiro',
						procesado = 1, cotFechaIngreso = @cotFechaIngreso2, cotFechaRetiro = @fechaFin
						where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador
					end
				else if @rdnTipoNovedad = 'NOVEDAD_RET' and @EstadoCotizanteFinal = 'ACTIVO'
					begin
						update #inicial set EstadoCotizanteFinal = 'INACTIVO', rdnAccionNovedadFinal = 'APLICAR_NOVEDAD',
						mensaje = '- Se procesó la novedad, el trabajador ha sido retirado de la caja',
						procesado = 1, cotFechaIngreso = @cotFechaIngreso2, cotFechaRetiro = @fechaFin
						where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador
					end
				else if @rdnTipoNovedad = 'NOVEDAD_ING' and @EstadoCotizanteFinal = 'ACTIVO'
					begin
						update #inicial set EstadoCotizanteFinal = @EstadoCotizanteFinal, rdnAccionNovedadFinal = 'RELACIONAR_NOVEDAD',
						mensaje = '- No se logró registrar la novedad de ingreso',
						procesado = 1, cotFechaIngreso = @cotFechaIngreso2, cotFechaRetiro = @fechaFin
						where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador
					end
				else if @rdnTipoNovedad = 'NOVEDAD_ING' and @EstadoCotizanteFinal <> 'ACTIVO'
					begin
						update #inicial set EstadoCotizanteFinal = 'ACTIVO', rdnAccionNovedadFinal = 'APLICAR_NOVEDAD',
						mensaje = '- La novedad de ingreso fue procesada',
						procesado = 1, cotFechaIngreso = @cotFechaIngreso2, cotFechaRetiro = @fechaFin
						where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador
					end
			end
			
		else if @EstadoCotizanteFinal <> 'ACTIVO' and @rdnTipoNovedad = 'NOVEDAD_ING' and @cotEsTrabajadorReintegrable = 1
			begin
				update #inicial set EstadoCotizanteFinal = 'ACTIVO', rdnAccionNovedadFinal = 'APLICAR_NOVEDAD',	mensaje = '- La novedad de ingreso fue procesada', procesado = 1, cotFechaIngreso = @cotFechaIngreso2, cotFechaRetiro = @fechaFin
				where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador
			end
		else if @EstadoCotizanteFinal <> 'ACTIVO' and @rdnTipoNovedad = 'NOVEDAD_ING' and @cotEsTrabajadorReintegrable = 0
			begin
				update #inicial set EstadoCotizanteFinal = @EstadoCotizanteFinal, rdnAccionNovedadFinal = 'RELACIONAR_NOVEDAD',	mensaje = '- No se logró registrar la novedad de ingreso', procesado = 1, cotFechaIngreso = @cotFechaIngreso2, cotFechaRetiro = @fechaFin
				where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador
			end
		else if @EstadoCotizanteFinal <> 'ACTIVO' and @novING_RET = 0
			begin
				--print 'no aplicadas las novedades'
				update #inicial set EstadoCotizanteFinal = @EstadoCotizanteFinal, rdnAccionNovedadFinal = 'RELACIONAR_NOVEDAD',	
				mensaje = (select renMensaje from referenciaNovedad with (nolock) where renSituacionPrimaria = iif(@situacionPrimaria = 'NOVEDAD_ING', 'SIN_NOVEDAD', @situacionPrimaria) and renEstadoCotizante = @EstadoCotizanteFinal and renTipoNovedad = @rdnTipoNovedad), 
				procesado = 1, cotFechaIngreso = @cotFechaIngreso2, cotFechaRetiro = @fechaFin
				where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador
			end
		else if @EstadoCotizanteFinal = 'ACTIVO' and @rdnTipoNovedad = 'NOVEDAD_ING'
			begin
				update #inicial set EstadoCotizanteFinal = @EstadoCotizanteFinal, rdnAccionNovedadFinal = 'RELACIONAR_NOVEDAD',
				mensaje = '- No es posible registrar novedad de ingreso, debido a que el trabajador se encuentra ACTIVO por este empleador en nuestra base de datos',
				procesado = 1, cotFechaIngreso = @cotFechaIngreso2, cotFechaRetiro = @fechaFin
				where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador
			end
		else if @EstadoCotizanteFinal = 'ACTIVO' and @novING_RET = 0
			begin
			--print 'entra aca.' + N' ' + convert(varchar,@Identificador) + N' ' + @redNumeroIdentificacionCotizante + N' ' + @situacionPrimaria + N' ' + @rdnTipoNovedad + N' ' +  @EstadoCotizanteFinal
				update #inicial set EstadoCotizanteFinal = @EstadoCotizanteFinal, rdnAccionNovedadFinal = 'APLICAR_NOVEDAD',
				mensaje = (select renMensaje from referenciaNovedad with (nolock) where renSituacionPrimaria = iif(@situacionPrimaria = 'NOVEDAD_ING', 'SIN_NOVEDAD', @situacionPrimaria) and renEstadoCotizante = @EstadoCotizanteFinal and renTipoNovedad = @rdnTipoNovedad),
				procesado = 1, cotFechaIngreso = @cotFechaIngreso2, cotFechaRetiro = @fechaFin
				where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador
			end
		else if @EstadoCotizanteFinal = 'ACTIVO' and @rdnTipoNovedad = 'NOVEDAD_RET' and @fechaFin2 < @cotFechaIngreso2
			begin
				update #inicial set EstadoCotizanteFinal = @EstadoCotizanteFinal, rdnAccionNovedadFinal = 'RELACIONAR_NOVEDAD',
				mensaje = '- No se logró registrar la novedad de retiro',
				procesado = 1, cotFechaIngreso = @cotFechaIngreso2, cotFechaRetiro = @fechaFin
				where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador
			end

		else if @EstadoCotizanteFinal = 'ACTIVO' and @rdnTipoNovedad = 'NOVEDAD_RET' and @fechaFin2 >= @cotFechaIngreso2
			begin
				update #inicial set EstadoCotizanteFinal = 'INACTIVO', rdnAccionNovedadFinal = 'APLICAR_NOVEDAD',
				mensaje = '- Se procesó la novedad, el trabajador ha sido retirado de la caja',
				procesado = 1, cotFechaIngreso = @cotFechaIngreso2, cotFechaRetiro = @fechaFin2
				where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador
			end

		else if @EstadoCotizanteFinal <> 'ACTIVO' and @rdnTipoNovedad = 'NOVEDAD_RET'
			begin
				update #inicial set EstadoCotizanteFinal = @EstadoCotizanteFinal, rdnAccionNovedadFinal = 'RELACIONAR_NOVEDAD',
				mensaje = '- No se logró registrar la novedad de retiro',
				procesado = 1, cotFechaIngreso = @cotFechaIngreso2, cotFechaRetiro = @fechaFin
				where redTipoIdentificacionCotizante = @redTipoIdentificacionCotizante and redNumeroIdentificacionCotizante = @redNumeroIdentificacionCotizante and Identificador = @Identificador
			end
			
		fetch next from @finalNovedades into @redTipoIdentificacionCotizante, @redNumeroIdentificacionCotizante, @rdnTipoNovedad, @rdnFechaInicioNovedad, @rdnFechaFinNovedad, @situacionPrimaria, @Identificador, @novING_RET, @cotEsTrabajadorReintegrable, @fechaIngRetMismaFechaTabla, @ingRetMismaFechaTabla  -- Continuamos con el siguiente registro
	end


	close @finalNovedades;
	deallocate @finalNovedades;


end; --- Este es el end donde se procesan las novedades que no son retroactivas.


	BEGIN TRAN

		UPDATE red
		SET 
		--SELECT red.*, 
			redOUTEsTrabajadorReintegrable = 1, redOUTGrupoFamiliarReintegrable = 1		
		FROM staging.RegistroDetallado red with (nolock)
		INNER JOIN #inicial tmpred on red.redId = tmpred.redId
		WHERE EXISTS (
			SELECT 1
			FROM #inicial tmpnov		
			WHERE	tmpnov.redId = red.redId
				AND tmpnov.novING_RET = 1
		)

		update #inicial set rdnTipoTransaccion = case when rdnTipoNovedad = 'NOVEDAD_ING' then 'NOVEDAD_REINTEGRO' else rdnTipoTransaccion end
		----====== Se agrega cambio para dejar fechas en el inicio y fin de la novedad. 
			update #inicial set rdnFechaInicioNovedad = eomonth(fechaPeriodo), rdnFechaFinNovedad = case when rdnTipoNovedad = 'NOVEDAD_VSP' then null else eomonth(fechaPeriodo) end 
			from #inicial where rdnTipoNovedad not in ('NOVEDAD_ING','NOVEDAD_RET')
			and (rdnFechaInicioNovedad is null and rdnFechaFinNovedad is null)
		----====== Finaliza proceso agrega cambio para dejar fechas en el inicio y fin de la novedad. 


		declare @cant int = 0
		set @cant = (select COUNT(*)
							FROM staging.RegistroDetalladoNovedad rdn with (nolock)
							INNER JOIN #inicial tmpnov ON rdn.rdnId = tmpnov.rdnId
							WHERE  rdn.rdnFechaInicioNovedad is null and rdn.rdnTipoNovedad = 'NOVEDAD_ING')
		if @cant > 0 
			
			begin

				update top (@cant) rdn 
				set rdnFechaInicioNovedad = tmpnov.rdnFechaInicioNovedad2, rdnFechaFinNovedad = null
				FROM staging.RegistroDetalladoNovedad rdn with (nolock)
				INNER JOIN #inicial tmpnov ON rdn.rdnId = tmpnov.rdnId
				WHERE  rdn.rdnFechaInicioNovedad is null and rdn.rdnTipoNovedad = 'NOVEDAD_ING'

			end

		declare @cant1 int = 0
		set @cant1 = (select COUNT(*)
						FROM staging.RegistroDetalladoNovedad rdn with (nolock)
						INNER JOIN #inicial tmpnov ON rdn.rdnId = tmpnov.rdnId
						WHERE  rdn.rdnFechaFinNovedad is null and rdn.rdnTipoNovedad = 'NOVEDAD_RET')

			if @cant1 > 0
				begin

				update top (@cant1) rdn 
				set rdnFechaFinNovedad = tmpnov.rdnFechaInicioNovedad2, rdnFechaInicioNovedad = null
				FROM staging.RegistroDetalladoNovedad rdn with (nolock)
				INNER JOIN #inicial tmpnov ON rdn.rdnId = tmpnov.rdnId
				WHERE  rdn.rdnFechaFinNovedad is null and rdn.rdnTipoNovedad = 'NOVEDAD_RET'

				end


			declare @cant2 int = 0
			set @cant2 = (select COUNT(*)
						FROM staging.RegistroDetalladoNovedad rdn with (nolock)
						INNER JOIN #inicial tmpnov ON rdn.rdnId = tmpnov.rdnId
						WHERE (tmpnov.rdnTipoNovedad IS NOT NULL and rdn.rdnAccionNovedad <> tmpnov.rdnAccionNovedadFinal))

			
			if @cant2 > 0 
				begin 

					UPDATE top (@cant2) rdn
					SET 
						rdnAccionNovedad = tmpnov.rdnAccionNovedadFinal,
						rdnMensajeNovedad = tmpnov.mensaje, 
						/*
						rdnFechaInicioNovedad = tmpnov.rdnFechaInicioNovedad, 
						rdnFechaFinNovedad = tmpnov.rdnFechaFinNovedad,
						*/
						rdnFechaInicioNovedad = case when rdn.rdnTipoNovedad <> 'NOVEDAD_RET' then tmpnov.rdnFechaInicioNovedad else rdn.rdnFechaInicioNovedad end, 
						rdnFechaFinNovedad = case when rdn.rdnTipoNovedad = 'NOVEDAD_RET' then tmpnov.rdnFechaInicioNovedad else tmpnov.rdnFechaFinNovedad end,
						rdnDateTimeUpdate =  dbo.getLocalDate()
						,rdnTipotransaccion = tmpnov.rdnTipoTransaccion
					FROM staging.RegistroDetalladoNovedad rdn with (nolock)
					INNER JOIN #inicial tmpnov ON rdn.rdnId = tmpnov.rdnId
					WHERE (tmpnov.rdnTipoNovedad IS NOT NULL and rdn.rdnAccionNovedad <> tmpnov.rdnAccionNovedadFinal)
					--and isnull(rdn.rdnMensajeNovedad,'') <> 'Novedad por cumplimiento de aportes'

				end 

		delete from #inicial where rdnTipoNovedad = 'NOVEDAD_SUC' and isnull(rdnAccionNovedadFinal,'') <> N'APLICAR_NOVEDAD'


		INSERT INTO #Novedades (tenIdTransaccion,tenMarcaNovedadSimulado,tenMarcaNovedadManual,tenRegistroGeneral,tenRegistroDetallado,tenTipoIdAportante,tenNumeroIdAportante
								,tenTipoIdCotizante,tenNumeroIdCotizante,tenTipoTransaccion, tenEsIngreso, tenEsRetiro, tenFechaInicioNovedad
								,tenFechaFinNovedad,tenAccionNovedad,tenMensajeNovedad,tenPrimerNombre,tenSegundoNombre,tenPrimerApellido
								,tenSegundoApellido,tenCodigoDepartamento,tenCodigoMunicipio,tenRegistroDetalladoNovedad,tenEnProceso)
		SELECT nov.redId,@EsSimulado,@EsRegistroManual,@IdRegistroGeneral,nov.redId,regTipoIdentificacionAportante,regNumeroIdentificacionAportante
				,red.redTipoIdentificacionCotizante,red.redNumeroIdentificacionCotizante
				,case when nov.rdnTipoTransaccion = N'CAMBIO_SUCURSAL_TRABAJADOR_DEPENDIENTE' then N'CAMBIO_SUCURSA_TRABAJADOR_DEPENDIENTE_DEPWEB' else nov.rdnTipoTransaccion end as rdnTipoTransaccion
				,CASE WHEN nov.rdnTipoNovedad = 'NOVEDAD_ING' THEN 1 ELSE 0 END
				,CASE WHEN nov.rdnTipoNovedad = 'NOVEDAD_RET' THEN 1 ELSE 0 END
				,nov.rdnFechaInicioNovedad,nov.rdnFechaFinNovedad,nov.rdnAccionNovedadFinal,mensaje
				,redPrimerNombre,redSegundoNombre,redPrimerApellido,redSegundoApellido,
				redCodDepartamento,RIGHT(redCodMunicipio, 3),nov.rdnId,0
		FROM #tmpRegistroDetallado red
		INNER JOIN #inicial nov ON red.redId = nov.redId
		WHERE nov.rdnTipoNovedad IS NOT NULL


		while 1 = 1
			begin
				
				delete top (50) rdn 
				from staging.RegistroDetalladoNovedad rdn with (nolock)
				inner join #inicial tmpnov ON rdn.rdnId = tmpnov.rdnId
				where rdn.rdnTipotransaccion = N'CAMBIO_SUCURSAL_TRABAJADOR_DEPENDIENTE' and rdn.rdnAccionNovedad <> N'APLICAR_NOVEDAD'

				if @@ROWCOUNT = 0 break;

			end
		
	COMMIT TRAN

	drop table #inicial
    drop table #tmpRegistroDetallado
	drop table if exists #resultado
	drop table if exists #resultado_otro

END;