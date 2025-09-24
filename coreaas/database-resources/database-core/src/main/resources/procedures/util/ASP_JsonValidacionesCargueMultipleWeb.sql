CREATE OR ALTER PROCEDURE [dbo].[ASP_JsonValidacionesCargueMultipleWeb]
(
 @personas varchar(max)
)
AS

SET NOCOUNT ON;
declare @perPed varchar(max)

drop table if exists #Validaciones 
create table #Validaciones (peridEmp bigint, perIdAfi bigint,pertipoIdEmp varchar(20),numIdEmp varchar(16),pertipoId varchar(20),numId varchar(16),fechaNacimiento date 
,VALIDACION_SOLICITUDES_EN_CURSO bit not null default 0,VALIDACION_FECHA_NACIMIENTO_MAYOR_FECHA_AFILIACION bit not null default 0, VALIDACION_PERSONA_COOPERANTE bit not null default 0
,VALIDACION_PERSONA_FALLECIDA bit not null default 0,VALIDACION_PERSONA_NO_MAYOR_15 bit not null default 0,VALIDACION_PERSONA_RETIRADA_CON_SUBSANACION  bit not null default 0)
insert #Validaciones(pertipoIdEmp,numIdEmp,pertipoId,numId,fechaNacimiento)
select *
from openjson(@personas) 
with (pertipoIdEmp varchar(25) '$.tipoIdentificacionEmpleador', numIdEmp varchar(25) '$.numeroIdentificacionEmpleador',pertipoId varchar(25) '$.tipoIdentificacion', numId varchar(25) '$.numeroIdentificacion', fechaNacimiento date '$.fechaNacimiento')
--- extraer perid para cruce de rendimeinto
--empresa
update #Validaciones set peridEmp = perEmp.perId
from #Validaciones
inner join Persona perEmp on pertipoIdEmp = perEmp.perTipoIdentificacion and numIdEmp = perEmp.perNumeroIdentificacion

--afiliado
update #Validaciones set perIdAfi = perAfi.perId
from #Validaciones
inner join Persona perAfi on pertipoId = perAfi.perTipoIdentificacion and numId = perAfi.perNumeroIdentificacion

create index Inx_id on #Validaciones (peridEmp,perIdAfi)

--Validacion solicitudes en curso-- 

update val set validacion_solicitudes_en_curso = 1
from #Validaciones val
inner join Persona per on per.perId = val.perIdAfi
inner join SolicitudNovedadPersona snp on per.perId = snp.snpPersona
inner join SolicitudNovedad sno on sno.snoId = snp.snpSolicitudNovedad
inner join Solicitud sol on sol.solId = sno.snoSolicitudGlobal
where sno.snoEstadoSolicitud not in ('CERRADA','CANCELADA','RECHAZADA','DESISTIDA','APROBADA')
and sol.solCanalRecepcion not in ('PRESENCIAL_INT','ARCHIVO_CERTI_ESCOLAR','PILA')

--revisar si es con la empresa del archivo ********************************************************************************************
update val set validacion_solicitudes_en_curso = 1
from #Validaciones val
inner join Persona per on per.perId = val.perIdAfi
inner join Afiliado afi ON per.perId = afi.afiPersona
inner join RolAfiliado roa ON afi.afiId = roa.roaAfiliado
inner join SolicitudAfiliacionPersona sap ON roa.roaId = sap.sapRolAfiliado
inner join Solicitud sol ON sol.solId = sap.sapSolicitudGlobal
left join IntentoAfiliacion ia ON ia.iafSolicitud = sol.solId 
where ia.iafSolicitud is null
and (sap.sapEstadoSolicitud = 'PRE_RADICADA' or sap.sapEstadoSolicitud not in ('CERRADA','CANCELADA','RECHAZADA','DESISTIDA','REGISTRO_INTENTO_AFILIACION'))

-- VALIDACION_FECHA_NACIMIENTO_MAYOR_FECHA_AFILIACION
update val set validacion_fecha_nacimiento_mayor_fecha_afiliacion = 1
from #Validaciones val
inner join Persona per on per.perId = val.perIdAfi
inner join Afiliado afi on per.perId = afi.afiPersona
inner join RolAfiliado roa on afi.afiId = roa.roaAfiliado
inner join Empleador emp on roa.roaEmpleador = emp.empId
inner join Empresa e on emp.empEmpresa = e.empId
inner join Persona peremp on peremp.perId = val.peridEmp
where roaFechaAfiliacion < fechaNacimiento 

--VALIDACION_PERSONA_COOPERANTE
--empresa
;with ranked_Empleador as (
    select val.peridEmp, 
        first_value(sol.solClasificacion) over (partition by Emp.empPersona
            order by sol.solFechaRadicacion desc
        ) as Clasificacion
    from #Validaciones val
    inner join dbo.Empresa Emp on val.peridEmp = Emp.empPersona
    inner join dbo.Empleador Empor on Emp.empid = Empor.empEmpresa
    inner join dbo.SolicitudAfiliaciEmpleador sae on Empor.empId = sae.saeEmpleador
    inner join dbo.Solicitud sol on sae.saeSolicitudGlobal = sol.solId
	where sol.solResultadoProceso = 'APROBADA'
)

update val set VALIDACION_PERSONA_COOPERANTE = 1
from ranked_Empleador r
inner join #Validaciones val on r.peridEmp = val.peridEmp
where Clasificacion = 'COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO';

----trabajador ************************************ pendiente 
;with ranked_Trabajador as (
    select val.peridEmp, 
        first_value(sol.solClasificacion) over (partition by afi.afiPersona
            order by sol.solFechaRadicacion desc
        ) as Clasificacion
    from #Validaciones val
    inner join dbo.Afiliado afi on val.perIdAfi = afi.afiPersona
	inner join dbo.RolAfiliado roa on afi.afiId = roa.roaAfiliado
    inner join dbo.SolicitudAfiliacionPersona sap on roa.roaId = sap.sapRolAfiliado
	inner join dbo.Solicitud sol on sap.sapSolicitudGlobal = sol.solId
	where sol.solResultadoProceso = 'APROBADA'
)

update val set VALIDACION_PERSONA_COOPERANTE = 1
from ranked_Trabajador r
inner join #Validaciones val on r.peridEmp = val.peridEmp
where Clasificacion = 'COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO';

--VALIDACION_PERSONA_FALLECIDA
update Val set validacion_persona_fallecida = 1
from #Validaciones val
inner join PersonaDetalle ped on val.perIdAfi = ped.pedPersona
where ped.pedFallecido = 1

--VALIDACION_PERSONA_NO_MAYOR_15
update val set validacion_persona_no_mayor_15 = 1
from #Validaciones val
inner join PersonaDetalle ped on val.perIdAfi = ped.pedPersona
where datediff(year,ped.pedFechaNacimiento,dbo.getlocaldate())-
			(case
			when dateadd(yy,datediff(year,ped.pedFechaNacimiento,dbo.getlocaldate()),ped.pedFechaNacimiento) > dbo.getlocaldate() then 1
			else 0
			end) < 15

--VALIDACION_PERSONA_RETIRADA_CON_SUBSANACION
--primera consulta--
drop table if exists #ValSubsanacion
select perIdAfi 
into #ValSubsanacion
from #Validaciones val
inner join Afiliado afi on val.perIdAfi = afi.afiPersona
inner join RolAfiliado roa on afi.afiId = roa .roaAfiliado
where roa.roaEstadoAfiliado = 'INACTIVO'
and roa.roaFechaRetiro is not null
and roa.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
and roa.roaMotivoDesafiliacion in ('ENTREGA_DE_INFORMACION_FRAUDULENTA_CCF','MAL_USO_DE_SERVICIOS_CCF','RETIRO_POR_MORA_APORTES')

update val set validacion_persona_retirada_con_subsanacion = 1
from #ValSubsanacion vals
inner join #Validaciones val on vals.perIdAfi = val.perIdAfi

--excluir registros subsanados
update val set validacion_persona_retirada_con_subsanacion = 0
from #ValSubsanacion vals
inner join #Validaciones val on vals.perIdAfi = val.perIdAfi
inner join SolicitudNovedadPersona snp on vals.perIdAfi = snp.snpPersona
inner join SolicitudNovedad sn ON sn.snoId = snp.snpSolicitudNovedad
inner join Solicitud s ON s.solId = sn.snoSolicitudGlobal
inner join ParametrizacionNovedad pn ON pn.novId = sn.snoNovedad
where pn.novTipoTransaccion in ('REGISTRO_SUBSANACION_EXPULSION_DEPENDIENTE','REGISTRO_SUBSANACION_EXPULSION_PENSIONADO_25ANIOS','REGISTRO_SUBSANACION_EXPULSION_PENSIONADO_MAYOR_1_5SM_0_6','REGISTRO_SUBSANACION_EXPULSION_PENSIONADO_MAYOR_1_5SM_2','REGISTRO_SUBSANACION_EXPULSION_PENSIONADO_MENOR_1_5SM_0','REGISTRO_SUBSANACION_EXPULSION_PENSIONADO_MENOR_1_5SM_0_6','REGISTRO_SUBSANACION_EXPULSION_PENSIONADO_MENOR_1_5SM_2','REGISTRO_SUBSANACION_EXPULSION_PENSIONADO_PENSION_FAMILIAR')
and s.solResultadoProceso = 'APROBADA'

select @perPed = (
select pertipoId as tipoIdentificacion,numId as numeroIdentificacion,
convert(bit,sum(
        convert(smallint,VALIDACION_SOLICITUDES_EN_CURSO) + 
        convert(smallint,VALIDACION_FECHA_NACIMIENTO_MAYOR_FECHA_AFILIACION) + 
        convert(smallint,VALIDACION_PERSONA_COOPERANTE) + 
        convert(smallint,VALIDACION_PERSONA_FALLECIDA) + 
        convert(smallint,VALIDACION_PERSONA_NO_MAYOR_15) 
    ) )as afiliable
from #Validaciones
group by pertipoId,numId
for json path,  include_null_values)
		
select @perPed;