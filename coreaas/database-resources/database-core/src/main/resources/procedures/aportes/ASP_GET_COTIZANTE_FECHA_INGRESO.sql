-- =============================================
-- Author: Robinson Castillo
-- Create Date: 2021-01-21
-- Description: Procedimiento almacenado, encargado de traer la última fecha de ingreso con respecto a un empleador de un cotizante en estado activo, este proceso es para las novedades de pila. 
-- Debe automatizarce para que actualice los datos. 
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[ASP_GET_COTIZANTE_FECHA_INGRESO]
AS
BEGIN

if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA = 'dbo' and TABLE_NAME = 'cotFechaIngreso')
	begin
		create table cotFechaIngreso (id bigInt identity (1,1), perNumeroIdentificacionCot varchar(30), perTipoIdentificacionCot varchar(30), roaEstadoAfiliado varchar (20), perNumeroIdentificacionAport varchar(30), roaFechaIngreso date, roaFechaRetiro date)
	
		merge cotFechaIngreso as d 
		using (select p.perNumeroIdentificacion as perNumeroIdentificacionCot, p.perTipoIdentificacion as perTipoIdentificacionCot,
				r.roaEstadoAfiliado, pe.perNumeroIdentificacion as perNumeroIdentificacionAport, isnull(r.roaFechaIngreso,r.roaFechaAfiliacion) as roaFechaIngreso, r.roaFechaRetiro
				from persona as p
				inner join Afiliado as a on p.perId = a.afiPersona
				inner join RolAfiliado as r on a.afiId = r.roaAfiliado
				inner join Empleador as em on em.empId = r.roaEmpleador
				inner join empresa as e on e.empId = em.empEmpresa
				inner join Persona as pe on pe.perId = e.empPersona) as o on d.perTipoIdentificacionCot = o.perTipoIdentificacionCot and d.perNumeroIdentificacionCot = o.perNumeroIdentificacionCot and d.perNumeroIdentificacionAport = o.perNumeroIdentificacionAport
		when matched then update set d.roaEstadoAfiliado = o.roaEstadoAfiliado, d.roaFechaIngreso = o.roaFechaIngreso, d.roaFechaRetiro = o.roaFechaRetiro
		when not matched then insert (perNumeroIdentificacionCot, perTipoIdentificacionCot, roaEstadoAfiliado, perNumeroIdentificacionAport, roaFechaIngreso, roaFechaRetiro)
							values (o.perNumeroIdentificacionCot, o.perTipoIdentificacionCot, o.roaEstadoAfiliado, o.perNumeroIdentificacionAport, o.roaFechaIngreso, o.roaFechaRetiro)
		when not matched by source then delete;
	end
else 
	begin
		truncate table cotFechaIngreso
		DBCC CHECKIDENT ('dbo.cotFechaIngreso', RESEED, 1)

		merge cotFechaIngreso as d 
		using (select p.perNumeroIdentificacion as perNumeroIdentificacionCot, p.perTipoIdentificacion as perTipoIdentificacionCot,
		r.roaEstadoAfiliado, pe.perNumeroIdentificacion as perNumeroIdentificacionAport, isnull(r.roaFechaIngreso,r.roaFechaAfiliacion) as roaFechaIngreso, r.roaFechaRetiro
				from persona as p
				inner join Afiliado as a on p.perId = a.afiPersona
				inner join RolAfiliado as r on a.afiId = r.roaAfiliado
				inner join Empleador as em on em.empId = r.roaEmpleador
				inner join empresa as e on e.empId = em.empEmpresa
				inner join Persona as pe on pe.perId = e.empPersona
				) as o on d.perTipoIdentificacionCot = o.perTipoIdentificacionCot and d.perNumeroIdentificacionCot = o.perNumeroIdentificacionCot and d.perNumeroIdentificacionAport = o.perNumeroIdentificacionAport
		when matched then update set d.roaEstadoAfiliado = o.roaEstadoAfiliado, d.roaFechaIngreso = o.roaFechaIngreso, d.roaFechaRetiro = o.roaFechaRetiro
		when not matched then insert (perNumeroIdentificacionCot, perTipoIdentificacionCot, roaEstadoAfiliado, perNumeroIdentificacionAport, roaFechaIngreso, roaFechaRetiro)
							values (o.perNumeroIdentificacionCot, o.perTipoIdentificacionCot, o.roaEstadoAfiliado, o.perNumeroIdentificacionAport, o.roaFechaIngreso, o.roaFechaRetiro)
		when not matched by source then delete;

		;with controlDuplicados as (
		select *, DENSE_RANK() over (partition by perTipoIdentificacionCot, perNumeroIdentificacionCot, perNumeroIdentificacionAport order by id) as registro
		from cotFechaIngreso)
		delete from controlDuplicados where registro > 1

	end

END