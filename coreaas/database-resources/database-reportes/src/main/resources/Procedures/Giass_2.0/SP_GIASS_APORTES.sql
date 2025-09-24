-- =============================================
-- Author:      <Sergio, Bayona>
-- Create Date: <14/10/2024>
-- Description: <SP para el reporte de aportes GIASS 2.0>
-- =============================================
ALTER     PROCEDURE [dbo].[SP_GIASS_APORTES]
AS
BEGIN

---creacion tablas tipoAportante
DROP TABLE IF EXISTS #PilaArchivoIRegistro1
create table #PilaArchivoIRegistro1 (pi1IndicePlanilla bigint,pi1TipoAportante int,pi1NumPlanillaAsociada varchar(100),shard varchar(max))
insert into #PilaArchivoIRegistro1
execute sp_execute_remote PilaReferenceData,N'select distinct pi1IndicePlanilla,pi1TipoAportante,pi1NumPlanillaAsociada from dbo.PilaArchivoIRegistro1'
create clustered index index_pila1 on #PilaArchivoIRegistro1(pi1IndicePlanilla)

---creacion tablas tipoCotizante
--drop table if exists #PilaArchivoIRegistro2
--create table #PilaArchivoIRegistro2 (pi2IndicePlanilla bigint,pi2TipoCotizante int,shard varchar(max))
--insert into #PilaArchivoIRegistro2
--execute sp_execute_remote PilaReferenceData,N'select distinct pi2IndicePlanilla,pi2TipoCotizante from dbo.PilaArchivoIRegistro2' 
--create clustered index index_pila2 on #PilaArchivoIRegistro2(pi2IndicePlanilla)


-- creación tabla temporal con los registros detallados de Pila
--drop table if exists #RegistroDetalladoPila
--create table #RegistroDetalladoPila(redId bigInt,redRegistroGeneral bigInt,redDiasCotizados int,redSalarioBasico numeric(19,2),redValorIBC numeric(19,2),redTarifa numeric(19,2),redAporteObligatorio numeric(19,2),shard varchar(max))
--insert into #RegistroDetalladoPila
--execute sp_execute_remote PilaReferenceData,N'select redId,redRegistroGeneral,redDiasCotizados,redSalarioBasico,redValorIBC,redTarifa,redAporteObligatorio from staging.RegistroDetallado' 


-- creación de la tabla temporal con los registros del movimiento de aportes
--drop table if exists #MovimientoAporte
--select *
--into #MovimientoAporte
--from MovimientoAporte

--ok  select top 10 * from #MovimientoAporte

--drop table if exists #reporte_aportes
--select  distinct  (SELECT cnsValor FROM Constante WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO') as [ccf_id],
--case when p.perTipoIdentificacion='CEDULA_CIUDADANIA' then '1' 
--     when p.perTipoIdentificacion='TARJETA_IDENTIDAD' then '2'  
--     when p.perTipoIdentificacion='REGISTRO_CIVIL' then '3' 
--     when p.perTipoIdentificacion='CEDULA_EXTRANJERIA' then '4'
--     when p.perTipoIdentificacion='PASAPORTE' then '6'
--     when p.perTipoIdentificacion='NIT' then '7' 
--     when p.perTipoIdentificacion='CARNE_DIPLOMATICO' then '8' 
--     when p.perTipoIdentificacion='PERM_ESP_PERMANENCIA' then '9'
--     when p.perTipoIdentificacion='PERM_PROT_TEMPORAL' then '15'
--	 else p.perTipoIdentificacion
--     end as [tipo_identificacion_empleador_id],
--	 p.perNumeroIdentificacion as [numero_identificacion_empleador],
--	 '1' as [novedad_id],
--	 regFechaPagoAporte as [fecha_pago],
--     substring(ag.apgPeriodoAporte,1,4) as [anio_periodo],
--     substring(ag.apgPeriodoAporte,6,2) as [mes_periodo],
--	 case when p2.perTipoIdentificacion='CEDULA_CIUDADANIA' then '1' 
--	 	 when p2.perTipoIdentificacion='TARJETA_IDENTIDAD' then '2'  
--	 	 when p2.perTipoIdentificacion='REGISTRO_CIVIL' then '3' 
--	 	 when p2.perTipoIdentificacion='CEDULA_EXTRANJERIA' then '4'
--	 	 when p2.perTipoIdentificacion='PASAPORTE' then '6'
--	 	 when p2.perTipoIdentificacion='NIT' then '7' 
--	 	 when p2.perTipoIdentificacion='CARNE_DIPLOMATICO' then '8' 
--	 	 when p2.perTipoIdentificacion='PERM_ESP_PERMANENCIA' then '9'
--	 	 when p2.perTipoIdentificacion='PERM_PROT_TEMPORAL' then '15'
--	 	 end as [tipo_identificacion_trabajador_id],
--		 p2.perNumeroIdentificacion [numero_identificacion_trabajador],
---- --pi2.pi2TipoCotizante tipo_cotizante_id,
--		 pi1.pi1TipoAportante [aporte_id],
--		ISNULL((select top 1 rp.redDiasCotizados from #RegistroDetalladoPila rp where ad.apdRegistroDetallado = rp.redId order by rp.redId desc),0) as [dias_cotizados],
--		round(cast(ad.apdSalarioBasico as int),0)  salario_base_cotizacion,
--		ISNULL((select top 1 cast(rp.redAporteObligatorio as numeric(19,0)) from #RegistroDetalladoPila rp where ad.apdRegistroDetallado = rp.redId order by rp.redId desc),0) as [valor_aporte],
--		(select top 1 CAST(CAST(ISNULL(ad.apdTarifa,0) as int)as numeric(12,2)) )  porcentaje_aporte,
--		ISNULL((select top 1 cast(rp.redValorIBC as numeric(19,0)) from #RegistroDetalladoPila rp where ad.apdRegistroDetallado = rp.redId order by rp.redId desc),0)  ingreso_base_cotizacion,
--		'' es_pago_inexactitud,
--		case when ag.apgestadoAportante='NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES' and ad.apdEstadoCotizante='NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES' then 1 else 0 end as es_pago_no_afiliado,
--		--case when scaId is not null then 1 else 0 end as es_correccion,
--		case when ( (pi1.pi1NumPlanillaAsociada is not null)) then 1 else 0 end as es_correccion
--		,case 
--		    when getdate() < '2024-07-31' then 2
--			when getdate() > '2024-07-31' then 1 end [tipo_cargue] --ok
--		,cast(getdate() as date)'fecha_novedad'
--into #reporte_aportes
--from AporteGeneral2 ag WITH (NOLOCK)
--join aporteDetallado2 ad WITH (NOLOCK) on ag.apgId=ad.apdAporteGeneral 
--join Empresa emp on emp.empId=ag.apgEmpresa
--join persona p on p.perId=emp.empPersona
--join RegistroGeneral2 rg WITH (NOLOCK) on rg.regId=ag.apgRegistroGeneral
--join persona p2 on p2.perId=ad.apdPersona and p2.perTipoIdentificacion not in('SALVOCONDUCTO')
----join pila.RegistroDetallado rd on rd.redRegistroGeneral=rg.regId
--join #PilaArchivoIRegistro1 pi1 WITH (NOLOCK) on pi1.pi1IndicePlanilla=rg.regRegistroControl
--join #PilaArchivoIRegistro2 pi2 WITH (NOLOCK) on pi2.pi2IndicePlanilla=rg.regRegistroControl
------left join SolicitudCorreccionAporte sca on sca.scaAporteGeneral=ag.apgId
--left join #MovimientoAporte ma WITH (NOLOCK) on ma.moaAporteGeneral=ag.apgId
--where p.perTipoIdentificacion != 'SALVOCONDUCTO'
--order by 1 desc


--## llenado de la temporal #reporte_aportes
drop table if exists #reporte_aportes
select distinct
	 (SELECT cnsValor FROM Constante WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO') as [ccf_id],
	 case when pe.perTipoIdentificacion='CEDULA_CIUDADANIA' then '1' 
		when pe.perTipoIdentificacion='TARJETA_IDENTIDAD' then '2'  
		when pe.perTipoIdentificacion='REGISTRO_CIVIL' then '3' 
		when pe.perTipoIdentificacion='CEDULA_EXTRANJERIA' then '4'
		when pe.perTipoIdentificacion='PASAPORTE' then '6'
		when pe.perTipoIdentificacion='NIT' then '7' 
		when pe.perTipoIdentificacion='CARNE_DIPLOMATICO' then '8' 
		when pe.perTipoIdentificacion='PERM_ESP_PERMANENCIA' then '9'
		when pe.perTipoIdentificacion='PERM_PROT_TEMPORAL' then '15'
		end as [tipo_identificacion_empleador_id],
	 pe.perNumeroIdentificacion as [numero_identificacion_empleador],
	 '1' as [novedad_id],
	 rg.regFechaPagoAporte as [fecha_pago],
	 substring(apg.apgPeriodoAporte,1,4) as [anio_periodo],
	 substring(apg.apgPeriodoAporte,6,2) as [mes_periodo],
	 case when pa.perTipoIdentificacion='CEDULA_CIUDADANIA' then '1' 
	 	when pa.perTipoIdentificacion='TARJETA_IDENTIDAD' then '2'  
	 	when pa.perTipoIdentificacion='REGISTRO_CIVIL' then '3' 
	 	when pa.perTipoIdentificacion='CEDULA_EXTRANJERIA' then '4'
	 	when pa.perTipoIdentificacion='PASAPORTE' then '6'
	 	when pa.perTipoIdentificacion='NIT' then '7' 
	 	when pa.perTipoIdentificacion='CARNE_DIPLOMATICO' then '8' 
	 	when pa.perTipoIdentificacion='PERM_ESP_PERMANENCIA' then '9'
	 	when pa.perTipoIdentificacion='PERM_PROT_TEMPORAL' then '15'
	 	end as [tipo_identificacion_trabajador_id],
		pa.perNumeroIdentificacion [numero_identificacion_trabajador],
		pi1.pi1TipoAportante [tipo_cotizante_id],
		pi1.pi1TipoAportante [aporte_id],
		isnull(apd.apdDiasCotizados,0) as [dias_cotizados],
		cast(apd.apdSalarioBasico as numeric(19,0))  [salario_base_cotizacion],
		cast(isnull(apd.apdAporteObligatorio,0) as numeric(19,0))  as [valor_aporte],
		cast(cast(apd.apdTarifa as numeric(9,2)) as varchar(5))  [porcentaje_aporte],
		cast(isnull(apd.apdValorIBC,0) as numeric(19,0)) [ingreso_base_cotizacion],
		'' [es_pago_inexactitud],
		case when apg.apgestadoAportante='NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES' and apd.apdEstadoCotizante='NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES' then 1 else 0 end as [es_pago_no_afiliado],
		case when ( (pi1.pi1NumPlanillaAsociada is not null)) then 1 else 0 end as [es_correccion],
		case when getdate() < '2024-07-31' then 2
		     when getdate() > '2024-07-31' then 1 end [tipo_cargue] 
		,cast(getdate() as date)'fecha_novedad'
into #reporte_aportes
from Persona pe WITH (NOLOCK)
inner join Empresa epr  WITH (NOLOCK) on (pe.perId = epr.empPersona) and (pe.perTipoIdentificacion != 'SALVOCONDUCTO') 
inner join Empleador epl WITH (NOLOCK) on (epr.empId = epl.empEmpresa)
inner join AporteGeneral2 apg WITH (NOLOCK) on (apg.apgEmpresa = epr.empId)
inner join AporteDetallado2 apd WITH (NOLOCK) on (apd.apdAporteGeneral = apg.apgId)
inner join RegistroGeneral2 rg WITH (NOLOCK) on (rg.regId=apg.apgRegistroGeneral)
inner join Persona pa on (pa.perId = apd.apdPersona) and (pa.perTipoIdentificacion != 'SALVOCONDUCTO')
inner join #PilaArchivoIRegistro1 pi1 WITH (NOLOCK) on (pi1.pi1IndicePlanilla = rg.regRegistroControl)

insert into aportes_giass
select distinct 
 ccf_id,tipo_identificacion_empleador_id,numero_identificacion_empleador,novedad_id,fecha_pago,anio_periodo,mes_periodo,tipo_identificacion_trabajador_id,numero_identificacion_trabajador
,tipo_cotizante_id,aporte_id,dias_cotizados,salario_base_cotizacion,valor_aporte,porcentaje_aporte,ingreso_base_cotizacion,es_pago_inexactitud,es_pago_no_afiliado
,es_correccion,tipo_cargue,fecha_novedad
FROM #reporte_aportes
except
select  
 ccf_id,tipo_identificacion_empleador_id,numero_identificacion_empleador,novedad_id,fecha_pago,anio_periodo,mes_periodo,tipo_identificacion_trabajador_id,numero_identificacion_trabajador
,tipo_cotizante_id,aporte_id,dias_cotizados,salario_base_cotizacion,valor_aporte,porcentaje_aporte,ingreso_base_cotizacion,es_pago_inexactitud,es_pago_no_afiliado
,es_correccion,tipo_cargue,fecha_novedad
from aportes_giass


SELECT DISTINCT
 ccf_id,tipo_identificacion_empleador_id,numero_identificacion_empleador,novedad_id,fecha_pago,anio_periodo,mes_periodo,tipo_identificacion_trabajador_id,numero_identificacion_trabajador
,tipo_cotizante_id,aporte_id,dias_cotizados,salario_base_cotizacion,cast([valor_aporte] as varchar) [valor_aporte],cast([porcentaje_aporte] as varchar) [porcentaje_aporte],ingreso_base_cotizacion,es_pago_inexactitud,es_pago_no_afiliado
,es_correccion,tipo_cargue,fecha_novedad
from aportes_giass


END

