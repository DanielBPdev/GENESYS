-- =============================================
-- Author: Robinson Castillo	
-- Create Date: 2022-03-11
-- Description: Para imprimir el antes y después en la pestaña novedades de vista 360.
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[ASP_NovedadesRegistradasPersonaIntING]
	@tipoIdentificacion varchar (50),
	@numeroIdentificacion varchar (50)
as
SET NOCOUNT ON;
IF OBJECT_ID('tempdb..#EstAfiPersona') IS NOT NULL
	begin
		drop table #EstAfiPersona
	end

IF OBJECT_ID('tempdb..#solPer') IS NOT NULL
	begin
		drop table #solPer
	end

IF OBJECT_ID('tempdb..#novPila') IS NOT NULL
	begin
		drop table #novPila
	end

IF OBJECT_ID('tempdb..#ResultFinal') IS NOT NULL
	begin
		drop table #ResultFinal
	end

select *
into #EstAfiPersona
from (
select p.perNumeroIdentificacion, p.perId, r.roaId, r.roaEmpleador, convert(date,eape.eaeFechaCambioEstado) as eaeFechaCambioEstado, case when eape.eaeEstadoAfiliacion is null then r.roaEstadoAfiliado else eape.eaeEstadoAfiliacion end as eaeEstadoAfiliacion
,case when convert(date,LAG(eape.eaeFechaCambioEstado) over (partition by p.perId, r.roaId, r.roaEmpleador order by eape.eaeFechaCambioEstado)) is null then eape.eaeFechaCambioEstado else convert(date,LAG(eape.eaeFechaCambioEstado) over (partition by p.perId, r.roaId, r.roaEmpleador order by eape.eaeFechaCambioEstado)) end as fechaAntes
,case when (LAG(eape.eaeEstadoAfiliacion) over (partition by p.perId, r.roaId, r.roaEmpleador order by eape.eaeFechaCambioEstado)) is null then eape.eaeEstadoAfiliacion else LAG(eape.eaeEstadoAfiliacion) over (partition by p.perId, r.roaId, r.roaEmpleador order by eape.eaeFechaCambioEstado) end as EstadoAfiAntes
,convert(date,LEAD(eape.eaeFechaCambioEstado) over (partition by p.perId, r.roaId, r.roaEmpleador order by eape.eaeFechaCambioEstado)) as fechaDespues
,LEAD(eape.eaeEstadoAfiliacion) over (partition by p.perId, r.roaId, r.roaEmpleador order by eape.eaeFechaCambioEstado) as EstadoAfiDespues
,ROW_NUMBER() over (partition by p.perId, r.roaId, r.roaEmpleador order by eape.eaeFechaCambioEstado) as orden
,r.roaEstadoAfiliado
,r.roaTipoAfiliado
,r.roaFechaRetiro
from Persona as p with (nolock)
inner join Afiliado as a with (nolock) on p.perId = a.afiPersona
inner join RolAfiliado as r with (nolock) on a.afiId = r.roaAfiliado
left join EstadoAfiliacionPersonaEmpresa as eape with (nolock) on p.perId = eape.eaePersona and r.roaEmpleador = eape.eaeEmpleador
where p.perTipoIdentificacion = @tipoIdentificacion
and p.perNumeroIdentificacion = @numeroIdentificacion
) as tblEstaAfiPersona

declare @dias smallInt = (select convert(int,left(prmValor, len(prmValor)-1)) from parametro where prmNombre = 'TIEMPO_REINTEGRO_AFILIADO')

select *
into #solPer
from Solicitud as s with (nolock)
inner join SolicitudNovedad as sn with (nolock) on s.solId = sn.snoSolicitudGlobal
inner join SolicitudNovedadPersona as snp with (nolock) on sn.snoId = snp.snpSolicitudNovedad
inner join ParametrizacionNovedad as nov with (nolock) on sn.snoNovedad = nov.novId
inner join (select *
			from persona as p with (nolock)
			inner join afiliado as a with (nolock) on p.perId = a.afiPersona
			inner join RolAfiliado as r with (nolock) on r.roaAfiliado = a.afiId
			where p.perTipoIdentificacion = @tipoIdentificacion
			and p.perNumeroIdentificacion = @numeroIdentificacion) as p on p.perId = snp.snpPersona
left join SolicitudNovedadPila as spi on spi.spiSolicitudNovedad = sn.snoId
where snp.snpBeneficiario is null
AND s.solTipoTransaccion NOT LIKE 'ACTIVAR_BENEFICIARIOS_MULTIPLES_%'
--and s.solTipoTransaccion not in ('NOVEDAD_REINTEGRO')

declare @novPila nvarchar(max) = null
select @novPila = @novPila + convert(varchar(20),spiRegistroDetallado) + N',' from (select distinct spiRegistroDetallado from #solPer) as t

create table #novPila (rdnRegistroDetallado bigInt, rdnTipotransaccion varchar(200), rdnFechaInicioNovedad date, origen varchar(200))

if @novPila is not null
	begin
		select @novPila = left(@novPila, len(@novPila) -1)
		declare @queryPila nvarchar(max) = ''
		select @queryPila = N'select rdnRegistroDetallado, rdnTipotransaccion, rdnFechaInicioNovedad from pila.staging.registroDetalladoNovedad with(nolock) where rdnRegistroDetallado in (' + @novPila + ')'
		insert #novPila
		execute sp_execute_remote pilaReferenceData, @queryPila
	end

;with novCom as (select novTipoTransaccion
				from ParametrizacionNovedad
				where novTipoTransaccion not in (
				'CAMBIO_CLASE_TRABAJADOR_TRABAJADOR_DEPENDIENTE_PRESENCIAL'
				,'CAMBIO_CLASE_TRABAJADOR_TRABAJADOR_DEPENDIENTE_DEPWEB'
				,'ACTUALIZAR_VALOR_TIPO_SALARIO_CONTRATO_TRABAJADOR_DEPENDIENTE_PRESENCIAL'
				,'ACTUALIZAR_VALOR_TIPO_SALARIO_CONTRATO_TRABAJADOR_DEPENDIENTE_DEPWEB'
				,'CAMBIO_SUCURSA_TRABAJADOR_DEPENDIENTE_DEPWEB'
				,'ACTUALIZACION_VACACIONES_TRABAJADOR_DEPENDIENTE'
				,'RETIRO_TRABAJADOR_DEPENDIENTE'
				,'SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_DEPENDIENTE_PRESENCIAL'
				,'SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_DEPENDIENTE_DEPWEB'
				,'VACACIONES_LICENCIA_REMUNERADA_VAC_DEPENDIENTE_PRESENCIAL'
				,'VACACIONES_LICENCIA_REMUNERADA_VAC_DEPENDIENTE_DEPWEB'
				,'VARIACION_TRANSITORIA_SALARIO_VST_DEPENDIENTE_PRESENCIAL'
				,'VARIACION_TRANSITORIA_SALARIO_VST_DEPENDIENTE_DEPWEB'
				,'VARIACION_PERMANENTE_SALARIO_VSP_DEPENDIENTE_PRESENCIAL'
				,'VARIACION_PERMANENTE_SALARIO_VSP_DEPENDIENTE_DEPWEB'
				,'REGISTRO_SUBSANACION_EXPULSION_DEPENDIENTE'))

select distinct 
s.novTipoTransaccion
, s.solFechaRadicacion
,case when s.solResultadoProceso = 'APROBADA' THEN 'Aplicada' ELSE 'Guardada' END estadoNovedad
, nop.nopFechaInicio
, case when s.novTipoTransaccion = 'RETIRO_TRABAJADOR_DEPENDIENTE' AND s.solCanalRecepcion IN ('PILA', 'CARTERA','APORTE_MANUAL') then (select convert(date,max(roaFechaRetiro)) from #EstAfiPersona where roaEmpleador = empl.empId) else nop.nopFechaFin end as nopFechaFin
, s.solNumeroRadicacion
, CASE WHEN s.solCanalRecepcion = 'WEB' AND s.novTipoTransaccion LIKE'%_DEPWEB%' THEN 'Web Empresas'
		WHEN s.solCanalRecepcion = 'WEB' THEN 'Web Personas'
		ELSE s.solCanalRecepcion END solCanalRecepcion
, pemp.perRazonSocial empleador
, case  when s.novTipoTransaccion not like 'RETIRO%' and pil.rdnFechaInicioNovedad >= isnull(s.roaFechaAfiliacion,s.roaFechaIngreso)
		and pil.rdnFechaInicioNovedad < s.roaFechaRetiro
		and s.novTipoTransaccion = pil.rdnTipotransaccion then 'ACTIVO'
		when s.snpRolAfiliado is null and s.novTipoTransaccion in (select * from novCom) then (select top 1 roaEstadoAfiliado from VW_EstadoAfiliacionPersonaCaja where perTipoIdentificacion = @tipoIdentificacion and perNumeroIdentificacion = @numeroIdentificacion) 
		when s.snpRolAfiliado is null and s.novTipoTransaccion not in (select * from novCom) then 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES'
		when s.solResultadoProceso = 'APROBADA' and  s.novTipoTransaccion = 'NOVEDAD_REINTEGRO' then 'INACTIVO'
		when s.solResultadoProceso = 'APROBADA' and s.novTipoTransaccion not like 'RETIRO%' then 'ACTIVO'
	when (select distinct estAfi.perId from #EstAfiPersona as estAfi where s.perId = estAfi.perId and estAfi.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE') is not null 
			then (select top 1 t.eaeEstadoAfiliacion 
					from (select estAfi.perId, estAfi.orden, estAfi.EstadoAfiAntes, estAfi.eaeEstadoAfiliacion, estAfi.eaeFechaCambioEstado from #EstAfiPersona as estAfi where s.perId = estAfi.perId and estAfi.roaEmpleador = empl.empId and convert(date,s.solFechaRadicacion) > estAfi.eaeFechaCambioEstado) as t order by t.orden desc) 
		else 
			case when (select distinct estAfi.perId from #EstAfiPersona as estAfi where s.perId = estAfi.perId and estAfi.roaTipoAfiliado <> 'TRABAJADOR_DEPENDIENTE') is not null 
				then (select distinct estAfi.eaeEstadoAfiliacion from #EstAfiPersona as estAfi where s.perId = estAfi.perId and estAfi.roaTipoAfiliado <> 'TRABAJADOR_DEPENDIENTE') 
			else 
				case when (select distinct estAfi.perId from #EstAfiPersona as estAfi where s.perId = estAfi.perId) is null then 'NO_FORMALIZADO_SIN_AFILIACION'
					else ''
					end
				end 
			end 
			as estadoAntes
, case
	when s.novTipoTransaccion not like 'RETIRO%' and pil.rdnFechaInicioNovedad >= isnull(s.roaFechaAfiliacion,s.roaFechaIngreso) and pil.rdnFechaInicioNovedad < s.roaFechaRetiro and s.novTipoTransaccion = pil.rdnTipotransaccion then 'ACTIVO' 
	when S.snpRolAfiliado is null and S.novTipoTransaccion in (select * from novCom) then (select top 1 roaEstadoAfiliado from VW_EstadoAfiliacionPersonaCaja where perTipoIdentificacion = @tipoIdentificacion and perNumeroIdentificacion = @numeroIdentificacion) 
	when S.snpRolAfiliado is null and S.novTipoTransaccion not in (select * from novCom) then 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES'
	when s.solResultadoProceso = 'APROBADA' and s.novTipoTransaccion not like 'RETIRO%' then 'ACTIVO'
	when s.solResultadoProceso = 'APROBADA' and s.novTipoTransaccion like 'RETIRO%' then 'INACTIVO'
	when (select distinct estAfi.perId from #EstAfiPersona as estAfi where S.perId = estAfi.perId and estAfi.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE') is not null  then (select top 1 case when t.roaEstadoAfiliado = 'ACTIVO' then t.eaeEstadoAfiliacion
				else 
					case when (datediff(day, t.eaeFechaCambioEstado, s.solFechaRadicacion)) > @dias and S.novTipoTransaccion not like 'RETIRO%' then 'NO_FORMALIZADO_RETIRADO_CON_APORTES' else t.eaeEstadoAfiliacion end 
					end
				from (select estAfi.perId, estAfi.orden, estAfi.eaeEstadoAfiliacion, estAfi.eaeFechaCambioEstado, estAfi.roaEstadoAfiliado from #EstAfiPersona as estAfi where S.perId = estAfi.perId and estAfi.roaEmpleador = empl.empId and convert(date,s.solFechaRadicacion) >= estAfi.eaeFechaCambioEstado) as t order by t.orden desc)
	else 
		case when (select distinct estAfi.perId from #EstAfiPersona as estAfi where S.perId = estAfi.perId and estAfi.roaTipoAfiliado <> 'TRABAJADOR_DEPENDIENTE') is not null 
			then (select distinct estAfi.eaeEstadoAfiliacion from #EstAfiPersona as estAfi where S.perId = estAfi.perId and estAfi.roaTipoAfiliado <> 'TRABAJADOR_DEPENDIENTE') 
		else case when (select distinct estAfi.perId from #EstAfiPersona as estAfi where S.perId = estAfi.perId) is null and empl.empId is not null then 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES'
				else ''
			end
		end
	end as estadoDespues
, s.solId
, case when s.novTipoTransaccion LIKE '%_DEPENDIENTE%' or s.novTipoTransaccion like '%_INDEPENDIENTE%' or s.novTipoTransaccion like '%_PENSIONADO%' then 'Afiliación' else 'Persona' end nivelNovedad
, empl.empId as idEmpleador
, s.perId
, empr.empId as idEmpresa,
	isnull(roaEstadoAfiliado,'INACTIVO') roaEstadoAfiliado 
into #ResultFinal
from #solPer as s
left join NovedadDetalle nop with (nolock) ON nop.nopSolicitudNovedad = s.snoId
LEFT JOIN Empresa empr with (nolock) ON empr.empId = 
(CASE WHEN s.snpRolAfiliado is null
	THEN (SELECT ag.apgEmpresa 
			FROM Empresa er with (nolock)
			JOIN AporteGeneral ag with (nolock) ON er.empId = ag.apgEmpresa 
			JOIN AporteDetallado ad with (nolock) ON ad.apdAporteGeneral=ag.apgId 
			JOIN SolicitudNovedadPila snp2 with (nolock) ON ad.apdRegistroDetallado=snp2.spiRegistroDetallado and snp2.spiSolicitudNovedad=s.snoId AND s.solCanalRecepcion IN ('PILA', 'APORTE_MANUAL'))
	ELSE (SELECT er2.empId 
			FROM RolAfiliado roa with (nolock)
			JOIN Empleador el with (nolock) ON el.empId=roa.roaEmpleador 
			JOIN Empresa er2 with (nolock) ON er2.empId = el.empEmpresa
WHERE roa.roaId = s.snpRolAfiliado) END)
LEFT JOIN Empleador empl with (nolock) ON (empl.empEmpresa = empr.empId)
LEFT JOIN Persona pemp with (nolock) ON (pemp.perId = empr.empPersona)
LEFT JOIN #novPila as pil on s.spiRegistroDetallado = pil.rdnRegistroDetallado and pil.rdnTipotransaccion = s.solTipoTransaccion
order by s.solFechaRadicacion;

with resul as (
select *, row_number() over (partition by estadoNovedad, solNumeroRadicacion, solCanalRecepcion order by solFechaRadicacion, estadoAntes, estadoDespues desc) as idOrden
from #ResultFinal)
select novTipoTransaccion,solFechaRadicacion,estadoNovedad,nopFechaInicio,nopFechaFin,solNumeroRadicacion,solCanalRecepcion,empleador
,isnull(estadoAntes,roaEstadoAfiliado) estadoAntes
,isnull(iif(estadoNovedad='Aplicada',estadoDespues,estadoAntes),isnull(estadoAntes,roaEstadoAfiliado)) estadoDespues
,solId,nivelNovedad
,idEmpleador,perId,idEmpresa
from resul
where idOrden = 1
order by solFechaRadicacion desc;
return;