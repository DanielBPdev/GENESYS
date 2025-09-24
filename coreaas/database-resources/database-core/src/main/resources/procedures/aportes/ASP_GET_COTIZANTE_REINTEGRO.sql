-- =============================================
-- Author: Robinson Castillo
-- Create Date: 2021-01-21
-- Description: Procedimiento almacenado, encargado de validar si un cotizante es reintegrable o no, ,para el proceso de novedades
-- Este proceso debe automatizarse para que actualice la tabla final. 
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[ASP_GET_COTIZANTE_REINTEGRO]
AS
BEGIN
SET NOCOUNT ON
select 
case when roaId = max(roaId) over (partition by perTipoIdentificacion,perNumeroIdentificacion) then perNumeroIdentificacion else null end as perNumeroIdentificacion, 
case when roaId = max(roaId) over (partition by perTipoIdentificacion,perNumeroIdentificacion) then perTipoIdentificacion else null end as perTipoIdentificacion,
case when roaId = max(roaId) over (partition by perTipoIdentificacion,perNumeroIdentificacion) then roaEstadoAfiliado else null end as roaEstadoAfiliado,
case when roaId = max(roaId) over (partition by perTipoIdentificacion,perNumeroIdentificacion) then roaOportunidadPago else null end as roaOportunidadPago,
case when roaId = max(roaId) over (partition by perTipoIdentificacion,perNumeroIdentificacion) then roaFechaRetiro else null end as roaFechaRetiro,
(select convert(int,left(prmValor, len(prmValor)-1)) from parametro where prmNombre = 'TIEMPO_REINTEGRO_AFILIADO') as tiempoReinAfi
into #act
from persona as p
inner join Afiliado as a on p.perId = a.afiPersona
inner join RolAfiliado as r on a.afiId = r.roaAfiliado
where roaEstadoAfiliado = 'ACTIVO' and roaFechaRetiro is null


select 
 case when roaFechaRetiro = max(roaFechaRetiro) over (partition by perTipoIdentificacion,perNumeroIdentificacion) then perNumeroIdentificacion else null end as perNumeroIdentificacion
,case when roaFechaRetiro = max(roaFechaRetiro) over (partition by perTipoIdentificacion,perNumeroIdentificacion) then perTipoIdentificacion else null end as perTipoIdentificacion
,case when roaFechaRetiro = max(roaFechaRetiro) over (partition by perTipoIdentificacion,perNumeroIdentificacion) then roaEstadoAfiliado else null end as roaEstadoAfiliado
,case when roaFechaRetiro = max(roaFechaRetiro) over (partition by perTipoIdentificacion,perNumeroIdentificacion) then roaOportunidadPago else null end as roaOportunidadPago
,case when roaFechaRetiro = max(roaFechaRetiro) over (partition by perTipoIdentificacion,perNumeroIdentificacion) then roaFechaRetiro else null end as roaFechaRetiro
,(select convert(int,left(prmValor, len(prmValor)-1)) from parametro where prmNombre = 'TIEMPO_REINTEGRO_AFILIADO') as tiempoReinAfi
into #inact
from persona as p with (nolock)
inner join Afiliado as a with (nolock) on p.perId = a.afiPersona
inner join RolAfiliado as r with (nolock) on a.afiId = r.roaAfiliado
where roaEstadoAfiliado = 'INACTIVO' and roaFechaRetiro is not null and (isnull(roaMotivoDesafiliacion, '') not in ('FALLECIMIENTO', 'AFILIACION_ANULADA'))


--- DEPURACION DE FALLECIDOS

;with fallecido as (
	select p.perNumeroIdentificacion
	from persona as p
	inner join PersonaDetalle as pd on p.perId = pd.pedPersona
	where pd.pedFallecido = 1
)
delete a
from #inact as a
inner join fallecido as b on a.perNumeroIdentificacion = b.perNumeroIdentificacion

---	FINALIZA DEPURACION DE FALLECIDOS

select *
into #inactDep
from (
select perNumeroIdentificacion from #inact
except
select perNumeroIdentificacion from #act) as t

delete b from #inact as b where not exists (select 1 from #inactDep as a where a.perNumeroIdentificacion = b.perNumeroIdentificacion)

if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA = 'dbo' and TABLE_NAME = 'cotReintegro')
	begin
		create table cotReintegro (id bigInt identity (1,1),perNumeroIdentificacion varchar (30),perTipoIdentificacion varchar(30), roaEstadoAfiliado varchar (20), roaOportunidadPago varchar(30), roaFechaRetiro date, tiempoReinAfi smallInt)
	
		merge cotReintegro as d 
		using (
		select perNumeroIdentificacion,perTipoIdentificacion, roaEstadoAfiliado, isnull(roaOportunidadPago,'MES_VENCIDO') as roaOportunidadPago, roaFechaRetiro, tiempoReinAfi from #act
		union 
		select perNumeroIdentificacion,perTipoIdentificacion, roaEstadoAfiliado, isnull(roaOportunidadPago,'MES_VENCIDO') as roaOportunidadPago, roaFechaRetiro, tiempoReinAfi from #inact
		) as o on d.perTipoIdentificacion = o.perTipoIdentificacion and d.perNumeroIdentificacion = o.perNumeroIdentificacion
		when matched then update set d.roaEstadoAfiliado = o.roaEstadoAfiliado, d.roaOportunidadPago = o.roaOportunidadPago,  d.roaFechaRetiro = o.roaFechaRetiro, d.tiempoReinAfi = o.tiempoReinAfi
		when not matched then insert (perNumeroIdentificacion,perTipoIdentificacion, roaEstadoAfiliado, roaOportunidadPago, roaFechaRetiro, tiempoReinAfi)
								values (o.perNumeroIdentificacion,o.perTipoIdentificacion, o.roaEstadoAfiliado, o.roaOportunidadPago, o.roaFechaRetiro, o.tiempoReinAfi)
		when not matched by source then delete;
	end
else 
	begin
		merge cotReintegro as d 
		using (
		select perNumeroIdentificacion,perTipoIdentificacion, roaEstadoAfiliado, isnull(roaOportunidadPago,'MES_VENCIDO') as roaOportunidadPago, roaFechaRetiro, tiempoReinAfi from #act
		union 
		select perNumeroIdentificacion,perTipoIdentificacion, roaEstadoAfiliado, isnull(roaOportunidadPago,'MES_VENCIDO') as roaOportunidadPago, roaFechaRetiro, tiempoReinAfi from #inact
		) as o on d.perTipoIdentificacion = o.perTipoIdentificacion and d.perNumeroIdentificacion = o.perNumeroIdentificacion
		when matched then update set d.roaEstadoAfiliado = o.roaEstadoAfiliado, d.roaOportunidadPago = o.roaOportunidadPago,  d.roaFechaRetiro = o.roaFechaRetiro, d.tiempoReinAfi = o.tiempoReinAfi
		when not matched then insert (perNumeroIdentificacion,perTipoIdentificacion, roaEstadoAfiliado, roaOportunidadPago, roaFechaRetiro, tiempoReinAfi)
								values (o.perNumeroIdentificacion,o.perTipoIdentificacion, o.roaEstadoAfiliado, o.roaOportunidadPago, o.roaFechaRetiro, o.tiempoReinAfi)
		when not matched by source then delete;
	end

drop table #act
drop table #inact
drop table #inactDep

--============= Proceso para los fallecidos. 

if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA = 'dbo' and TABLE_NAME = 'CotpersonaFallecido')
	begin
		create table dbo.CotpersonaFallecido (perNumeroIdentificacion varchar(20), perTipoIdentificacion varchar(30), pedFallecido bit, fechaRegistro datetime)
		insert dbo.CotpersonaFallecido (perNumeroIdentificacion, perTipoIdentificacion, pedFallecido, fechaRegistro)
		select p.perNumeroIdentificacion, p.perTipoIdentificacion, pd.pedFallecido, (select dateadd(hh, -5, CURRENT_TIMESTAMP)) as fechaRegistro
		from dbo.persona as p with (nolock)
		inner join dbo.PersonaDetalle as pd with (nolock) on p.perId = pd.pedPersona
		where pedFallecido = 1
	end
else
	begin
		
		select p.perNumeroIdentificacion, p.perTipoIdentificacion
		into #insert
		from dbo.persona as p with (nolock)
		inner join dbo.PersonaDetalle as pd with (nolock) on p.perId = pd.pedPersona
		where pedFallecido = 1
		except
		select perNumeroIdentificacion, perTipoIdentificacion
		from dbo.CotpersonaFallecido with (nolock)

		insert dbo.CotpersonaFallecido (perNumeroIdentificacion, perTipoIdentificacion, pedFallecido, fechaRegistro)
		select perNumeroIdentificacion, perTipoIdentificacion, 1 as pedFallecido, (select dateadd(hh, -5, CURRENT_TIMESTAMP)) as fechaRegistro
		from #insert

		drop table #insert

	end

END