IF (OBJECT_ID('GET_CALCULO_DIAS_ENVIO_COMUNICADO_H2C2_F1C6') IS NOT NULL)
	DROP FUNCTION [dbo].[GET_CALCULO_DIAS_ENVIO_COMUNICADO_H2C2_F1C6]
GO

CREATE FUNCTION [dbo].[GET_CALCULO_DIAS_ENVIO_COMUNICADO_H2C2_F1C6]
(
    @numeroId varchar(20), @diasParam smallInt,@metodo varchar(50),@acciondeCobro varchar(20)
)
RETURNS smallInt
AS
BEGIN

declare @tablaFest table (fecha date, nombreDia varchar(50))
;with cal (fecha) as
(select convert(date,'2000-01-01') as fechaHola
union all
select DATEADD(dd, 1, fecha)
from cal
where fecha <= '2030-01-01'
)
insert @tablaFest
select *, DATENAME(dw,fecha) as nombreDia
from cal
where DATENAME(dw,fecha) not in ('Saturday', 'Sunday')
order by fecha
option(maxrecursion 32767)


declare @sinFestivos table (id int identity(1,1), fechaNoFestiva date)
insert @sinFestivos (fechaNoFestiva)
select *
from (
select fecha
from @tablaFest
except 
select pifFecha
from DiasFestivos
) as t
order by fecha


--create clustered index id on @sinFestivos (id)

--declare @numeroId varchar(20) = '14876323';
declare @autoriza smallInt;
declare @carTipoSolicitante varchar(50);
declare @carId bigInt;
declare @carTipoAccionCobro varchar(10);
declare @tipoIdentificacion varchar(30);
declare @numeroIdentificacion varchar(20);
declare @carFechaAsignacionAccion date;


SELECT @autoriza = 1
,@carTipoSolicitante = c.carTipoSolicitante
,@carId = c.carId
,@carTipoAccionCobro = carTipoAccionCobro
,@tipoIdentificacion = p.perTipoIdentificacion
,@numeroIdentificacion = p.perNumeroIdentificacion
,@carFechaAsignacionAccion = convert(date,c.carFechaAsignacionAccion)
FROM Cartera c
inner join Persona p on p.perId=c.carPersona
where c.carTipoAccionCobro=@acciondeCobro and c.carTipoLineaCobro='C6' and c.carMetodo=@metodo
and p.perNumeroIdentificacion = @numeroId


declare @fechaAsignacion date = @carFechaAsignacionAccion
declare @idInicio int = (select min(id) as idInicio from @sinFestivos where fechaNoFestiva >= @fechaAsignacion)
declare @idInFin int = (select max(id) as idInicio from @sinFestivos where fechaNoFestiva >= @fechaAsignacion)

declare @tablaFinal table (autoriza smallInt,carTipoSolicitante varchar(50),carId bigInt,carTipoAccionCobro varchar(10),tipoIdentificacion varchar(50), numeroIdentificacion varchar(20),carFechaAsignacionAccion date, fechaNoFestiva date)

;with registrosAplicables as (
select @idInicio as ini
union all
select ini + @diasParam
from registrosAplicables
where ini <= @idInFin
), registrosAplicables2 as (
select *
from registrosAplicables as a
inner join @sinFestivos as b on a.ini = b.id
)
insert @tablaFinal
select @autoriza as autoriza, @carTipoSolicitante as carTipoSolicitante,@carId as carId,@carTipoAccionCobro as carTipoAccionCobro,@tipoIdentificacion as tipoIdentificacion,@numeroIdentificacion as numeroIdentificacion,@carFechaAsignacionAccion as carFechaAsignacionAccion, fechaNoFestiva
from registrosAplicables2
option(maxrecursion 32767)


declare @ahora date = dbo.getLocaldate()
declare @retorno smallInt = null


if exists (select * from @tablaFinal where fechaNoFestiva = @ahora)
	begin
		set @retorno = 1
	end
else
	begin
		set @retorno = 0
	end
	
return @retorno

END