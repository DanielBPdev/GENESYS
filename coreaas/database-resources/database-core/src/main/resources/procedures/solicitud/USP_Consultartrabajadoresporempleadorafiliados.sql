create or alter procedure [dbo].[USP_Consultartrabajadoresporempleadorafiliados] (@idEmpleador bigint,@offset int,@limit int, @orderBy VARCHAR(500))
as 
set nocount on
begin
drop table if exists #t
create table #t (perTipoIdentificacion varchar(20),perNumeroIdentificacion varchar(16),perRazonSocial varchar(250),roaEstadoAfiliado varchar(200),nombreComercial varchar(250)
,perPrimerNombre varchar(50),perSegundoNombre varchar(50),perPrimerApellido varchar(50),perSegundoApellido varchar(50),perId bigint,idEmpresa bigint,fechaIngreso date,fechaRetiro datetime)

drop table if exists #AporteEmpresa
create table #AporteEmpresa (apdPersona bigint,empId bigint,periodo date)
insert #AporteEmpresa
select apd.apdPersona, em.empId,convert(date,concat(max(apg.apgPeriodoAporte), '-01')) as periodo
from dbo.AporteGeneral as apg with(nolock)
inner join dbo.aporteDetallado as apd with(nolock) on apg.apgId = apd.apdAporteGeneral
inner join dbo.empresa as e with(nolock) on e.empId = apg.apgEmpresa
inner join dbo.empleador as em with(nolock) on e.empId = em.empEmpresa
where  em.empId = @idEmpleador
group by apd.apdPersona, em.empId
create index inx on #AporteEmpresa (apdPersona,empId)

;with ex as (select apdPersona, empId
			from #AporteEmpresa
			group by apdPersona, empId
			except
			select a.afiPersona, r.roaEmpleador
			from dbo.Afiliado as a with(nolock)
			inner join dbo.RolAfiliado as r with(nolock) on a.afiId = r.roaAfiliado
			where r.roaEmpleador = @idEmpleador),
			fech as (select apdPersona, max(periodo) as periodo
			from #AporteEmpresa
			group by apdPersona)
			insert into #t(perTipoIdentificacion,perNumeroIdentificacion,perRazonSocial,roaEstadoAfiliado,nombreComercial,perPrimerNombre,perSegundoNombre,perPrimerApellido,perSegundoApellido,perId,idEmpresa,fechaIngreso,fechaRetiro)
			select per.perTipoIdentificacion as perTipoIdentificacion,per.perNumeroIdentificacion as perNumeroIdentificacion,per.perRazonSocial as perRazonSocial,
					case when isnull(roa.roaEstadoAfiliado,'') = 'ACTIVO' then 'ACTIVO'
						when isnull(roa.roaEstadoAfiliado,'') = 'INACTIVO' and convert(date,roa.roaFechaRetiro) < fech.periodo then 'NO_FORMALIZADO_RETIRADO_CON_APORTES'
						else isnull(roa.roaEstadoAfiliado,'INACTIVO') end as roaEstadoAfiliado,
					emp.empNombreComercial AS nombreComercial,per.perPrimerNombre as perPrimerNombre,per.perSegundoNombre as perSegundoNombre,
					per.perPrimerApellido as perPrimerApellido,per.perSegundoApellido as perSegundoApellido,per.perId as perId,emp.empId as idEmpresa,roa.roaFechaAfiliacion AS fechaIngreso,roa.roaFechaRetiro AS fechaRetiro
				from dbo.RolAfiliado roa with(nolock)
				inner join dbo.Afiliado afi with(nolock) on (roa.roaAfiliado = afi.afiId)
				inner join dbo.Empleador empl with(nolock) on (roa.roaEmpleador = empl.empId)
				inner join dbo.Empresa emp with(nolock) on (empl.empEmpresa = emp.empId)
				inner join dbo.Persona per with(nolock) on (afi.afiPersona = per.perId)
				left join fech on per.perId = fech.apdPersona
				where empl.empId = @idEmpleador and not exists (select 1 from ex where ex.apdPersona = per.perId)
	
;with afi as (select afi.afiPersona
			from dbo.RolAfiliado roa
			inner join dbo.Afiliado afi on (roa.roaAfiliado = afi.afiId)
			where roa.roaEmpleador = @idEmpleador)
insert into #t(perTipoIdentificacion,perNumeroIdentificacion,perRazonSocial,roaEstadoAfiliado,nombreComercial,perPrimerNombre,perSegundoNombre,perPrimerApellido,perSegundoApellido,perId,idEmpresa,fechaIngreso,fechaRetiro)
SELECT per.perTipoIdentificacion as perTipoIdentificacion,per.perNumeroIdentificacion as perNumeroIdentificacion,per.perRazonSocial as perRazonSocial,
'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES' as roaEstadoAfiliado,'' as nombreComercial,per.perPrimerNombre as perPrimerNombre,per.perSegundoNombre as perSegundoNombre,per.perPrimerApellido as perPrimerApellido,
per.perSegundoApellido as perSegundoApellido,per.perId as perId,per.perId as idEmpresa,dbo.GetLocalDate() as fechaIngreso,dbo.GetLocalDate() fechaRetiro
from dbo.AporteGeneral as apg with(nolock)
inner join dbo.AporteDetallado as apd with(nolock) on apg.apgId = apd.apdAporteGeneral
inner join dbo.Persona as per with(nolock) on per.perId = apd.apdPersona
left join afi on afi.afiPersona = apd.apdPersona
where apg.apgEmpresa = (select empEmpresa from dbo.Empleador with(nolock) where empId = @idEmpleador) and afi.afiPersona is null
group by per.perPrimerNombre,per.perSegundoNombre,per.perPrimerApellido,per.perSegundoApellido,per.perId,per.perTipoIdentificacion,per.perNumeroIdentificacion,per.perRazonSocial;

declare @cant varchar(10) = (select count(*) from #t)

DECLARE @sql NVARCHAR(MAX);

IF @orderBy = ''
BEGIN
    SET @orderBy = 'perId';
END

SET @sql = N'
SELECT 
    perTipoIdentificacion AS tipoIdentificacion,
    perNumeroIdentificacion AS numeroIdentificacion,
    perRazonSocial AS nombre,
    roaEstadoAfiliado AS estadoAfiliadoEnum,
    nombreComercial,
    perPrimerNombre,
    perSegundoNombre,
    perPrimerApellido,
    perSegundoApellido,
    perId,
    idEmpresa,
    fechaIngreso,
    fechaRetiro,
    @cant CantidadTrabajadores
FROM #t
ORDER BY ' +
    CASE 
        WHEN @orderBy = 'numeroIdentificacion' THEN 'CAST(perNumeroIdentificacion AS BIGINT)'
        WHEN @orderBy = '-numeroIdentificacion' THEN 'CAST(perNumeroIdentificacion AS BIGINT) DESC'
        ELSE 
            CASE 
                WHEN LEFT(@orderBy, 1) = '-' THEN STUFF(@orderBy, 1, 1, '') + ' DESC'
                ELSE @orderBy + ' ASC'
            END
    END + '
OFFSET @offset ROWS
FETCH NEXT @limit ROWS ONLY';

EXEC sp_executesql @sql, N'@offset INT, @limit INT, @cant VARCHAR(10)', @offset = @offset, @limit = @limit, @cant = @cant;

end;
