
/* Object:  StoredProcedure [dbo].[USP_REP_CalcularIngresosFovis]    Script Date: 16/10/2024 9:07:23 a. m. */


-- =============================================
-- Author: Alexander Camelo
-- Create date: 
-- Description: SP que calcula los ingresos por hogar, como parametros es el tipo y identificacion del jefe hogar, idpostulacion . 
-- Ajuste: 2022-10-31 Se realiza ajuste, para recalcular las categorias. 
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_REP_CalcularIngresosFovis]
@tipoIdentificacion Varchar(50),
@numeroIdentificacion VARCHAR(50),
@idPostulacion BIGINT
--@apdRegistroDetallado BIGINT
--    @perId BIGINT
with execute as owner
AS
 SET NOCOUNT ON;   
--DECLARE @tipoIdentificacion Varchar(50)='CEDULA_CIUDADANIA';
--DECLARE @numeroIdentificacion Varchar(50)='75075800';
--select p.* from jefeHogar j 
--inner join Afiliado a on a.afiId=j.jehAfiliado
--inner join persona pe on pe.perId=a.afiPersona
--inner join PostulacionFOVIS p on p.pofJefeHogar=j.jehId
--where pe.perNumeroIdentificacion=@numeroIdentificacion
--and pe.perTipoIdentificacion=@tipoIdentificacion

--DECLARE @idPostulacion BIGINT =3292;
	BEGIN
		DECLARE @apdRegistroDetallado BIGINT;

		DECLARE @afiId BIGINT
		DECLARE @emplId BIGINT 
		DECLARE @empleadorId BIGINT 
		DECLARE @estadoAfiliado VARCHAR(30);
		DECLARE @idRolAfiliado BIGINT;
		DECLARE @clasificacion VARCHAR(60);
		declare @apdTipoCotizante varchar(30);
		declare @tipoAportante varchar(50);
		declare @perId bigInt;
		declare @apdId bigInt;
		declare @periodo date;
		declare @periRegular varchar(7);
		declare @periRegularMenos1 varchar(7);
		declare @fechaCreacionAporte date;
		declare @mensajeExcepxion varchar(350);
		select @perId=p.perId from Persona p where p.perNumeroIdentificacion=@numeroIdentificacion and p.perTipoIdentificacion=@tipoIdentificacion
		set @periRegular = convert(varchar(7),(select dateadd(day, 1, dateadd(month, -2, eomonth(dbo.GetLocalDate())))));
		set @periRegularMenos1 = convert(varchar(7),(select dateadd(day, 1, dateadd(month, -3, eomonth(dbo.GetLocalDate())))));
		--validacion # 1 validacion que el jefe del hogar o el remplazante esten es estado del hogar activo en en la postulacion a evaluar
		IF EXISTS(select 1 from Afiliado a
		inner join JefeHogar j on j.jehAfiliado=a.afiId
		where a.afiPersona=@perId
		AND j.jehEstadoHogar='ACTIVO') OR EXISTS(select 1 from IntegranteHogar i where i.inhPostulacionFovis=@idPostulacion AND i.inhIntegranteReemplazaJefeHogar=1 AND i.inhEstadoHogar='ACTIVO')
		BEGIN
		drop table if exists #listadoPersonas;
		drop table if exists #tablaDatos;
		create table #listadoPersonas (perId BIGINT);
		insert #listadoPersonas (perId)
		select p.perId from (
				select a.afiPersona as perId
				from Afiliado as a with (nolock)
				inner join JefeHogar as j with (nolock) on j.jehAfiliado=a.afiId
				where a.afiPersona=@perId
				union all
				select i.inhPersona  as perId
				from IntegranteHogar i
				inner join jefeHogar j on i.inhJefeHogar=j.jehId
				inner join Afiliado a on a.afiId=j.jehAfiliado
				and i.inhEstadoHogar='ACTIVO' and i.inhPostulacionFovis=@idPostulacion
				where a.afiPersona=@perId
				) as p group by p.perId 


		create table #tablaDatos (perId BIGINT,apdRegistroDetallado BIGINT,apdFechaCreacion date,apdId BIGINT,tipoCotizante varchar(50),tipoAportante varchar(50),	periodo varchar(7),emplId BIGINT,empleadorId BIGINT);
		insert #tablaDatos (perId ,apdRegistroDetallado  ,apdId ,tipoCotizante ,	periodo,emplId)
		select   p.perId,max(apd.apdRegistroDetallado),max(apd.apdId),  apd.apdTipoCotizante,  max(apg.apgPeriodoAporte),apg.apgEmpresa 
		from aporteDetallado as apd with (nolock) 
		inner join Persona p on p.perId=apd.apdPersona
		inner join AporteGeneral as apg with (nolock) on apd.apdAporteGeneral = apg.apgId 
		where p.perId in (select l.perId from  #listadoPersonas l)
		and (apg.apgPeriodoAporte<=@periRegular and apg.apgPeriodoAporte>=@periRegularMenos1)
		GROUP BY  p.perId,  apd.apdTipoCotizante, apg.apgTipoSolicitante, apg.apgEmpresa 
		--ORDER BY apg.apgPeriodoAporte desc


		   drop table if exists #aporteFormulario
			drop table if exists #aportecat2
			drop table if exists #aporteCatFinal
			drop table if exists #TempCategoriasFinal
			drop table if exists #tablaFinal
			drop table if exists #aportecat3
			drop table if exists #aportecat4
			drop table if exists #empInActivos
			drop table if exists #empActivos
			drop table if exists #indPenActivos
			drop table if exists #nuevoBen
			---------
						--=== Se agrega control, para tener en cuenta, cuando solo llega un aporte y no formaliza afiliación, ese aporte, para el seguiente periodo, no se tiene en cuenta. 

			create table #aportecat3 ([perTipoIdentificacion] nvarchar(30), [perNumeroIdentificacion] nvarchar(20),[salario] numeric(19,2),  [apdTipoCotizante] nvarchar(30), periodo nvarchar(10), empresa bigInt, apgId bigInt,perIdPersona bigInt, apdId bigInt)
			insert #aportecat3 
			select distinct 
			case when apg.apgPeriodoAporte = max(apg.apgPeriodoAporte) over  (partition by apd.apdPersona, apg.apgEmpresa)  then p.perTipoIdentificacion else null end as [perTipoIdentificacion]
			,case when apg.apgPeriodoAporte = max(apg.apgPeriodoAporte) over (partition by apd.apdPersona, apg.apgEmpresa)  then p.perNumeroIdentificacion else null end as [perNumeroIdentificacion]
			,case when apg.apgPeriodoAporte = max(apg.apgPeriodoAporte) over (partition by apd.apdPersona, apg.apgEmpresa)  then apd.apdSalarioBasico else null end as [salario]
			,case when apg.apgPeriodoAporte = max(apg.apgPeriodoAporte) over (partition by apd.apdPersona, apg.apgEmpresa)  then apd.apdTipoCotizante else null end as [apdTipoCotizante]
			,case when apg.apgPeriodoAporte = max(apg.apgPeriodoAporte) over (partition by apd.apdPersona, apg.apgEmpresa)  then apg.apgPeriodoAporte else null end as periodo
			,case when apg.apgPeriodoAporte = max(apg.apgPeriodoAporte) over (partition by apd.apdPersona, apg.apgEmpresa)  then apg.apgEmpresa else null end as empresa
			,case when apg.apgPeriodoAporte = max(apg.apgPeriodoAporte) over (partition by apd.apdPersona, apg.apgEmpresa)  then apg.apgId else null end as apgId
			,case when apg.apgPeriodoAporte = max(apg.apgPeriodoAporte) over (partition by apd.apdPersona, apg.apgEmpresa)  then apd.apdPersona else null end as perIdPersona
			,case when apg.apgPeriodoAporte = max(apg.apgPeriodoAporte) over (partition by apd.apdPersona, apg.apgEmpresa)  then apd.apdId else null end as apdId
			from AporteGeneral as apg with (nolock) 
			inner join AporteDetallado as apd with (nolock) on apg.apgId = apd.apdAporteGeneral
			inner join persona as p with (nolock) on p.perId = apd.apdPersona 
		    inner join #tablaDatos t on t.apdId=apd.apdId
			where  apdSalarioBasico is not null and apdEstadoAporteAjuste != 'ANULADO'
			--and p.perId in (select l.perId from  #listadoPersonas l)
			--and (apg.apgPeriodoAporte=@periRegular or apg.apgPeriodoAporte= @periRegularMenos1)--== Ajuste para solo validar los periodos regulares o retroactvos 2023-05-29
		
			--borra las Personas que encontro ya que tienen aportes en el regular o regular -1
			delete #listadoPersonas  where #listadoPersonas.perId in(select perIdPersona  from #aportecat3 )

				create table #aporteFormulario (perTipoIdentificacion nvarchar(30), perNumeroIdentificacion nvarchar(20), salario numeric (19,2), [tipoCotizante] nvarchar (50), roaEmpleador bigInt,perIdPersona bigInt)
				insert #aporteFormulario (perTipoIdentificacion, perNumeroIdentificacion, salario, tipoCotizante, roaEmpleador,perIdPersona)
				select p.perTipoIdentificacion, p.perNumeroIdentificacion,  sum(r.roaValorSalarioMesadaIngresos) as [salario], r.roaTipoAfiliado, r.roaEmpleador,p.perId
				from persona as p with (nolock)
				inner join afiliado as a with (nolock) on p.perId = a.afiPersona
				inner join RolAfiliado as r with (nolock) on a.afiId = r.roaAfiliado
				where r.roaEstadoAfiliado = 'ACTIVO' and a.afiPersona in (select l.perId from  #listadoPersonas l)
				AND r.roaFechaAfiliacion >= try_convert(date,(concat(@periRegularMenos1, N'-01')) )
				--and r.roaEmpleador != @empleadorId
				and not exists (select 1 
				from AporteDetallado as apd1 with (nolock)
				inner join AporteGeneral as apg1 with (nolock) on apd1.apdAporteGeneral = apg1.apgId
				inner join Empresa as e1 with (nolock) on apg1.apgEmpresa = e1.empId
				inner join Empleador as em1 with (nolock) on e1.empId = em1.empEmpresa
				inner join #tablaDatos t on t.apdId=apd1.apdId
				where --apg1.apgPeriodoAporte >= convert(varchar(7),isnull(r.roaFechaIngreso,r.roaFechaAfiliacion)) and apd1.apdTipoCotizante = r.roaTipoAfiliado
				 em1.empId = r.roaEmpleador and apd1.apdPersona = p.perId
				union --== Se agrega validación independiente o pensionado. 
				select 1 
				from AporteDetallado as apd1 with (nolock)
				inner join AporteGeneral as apg1 with (nolock) on apd1.apdAporteGeneral = apg1.apgId
				inner join persona as p1 on p1.perId = apg1.apgPersona
				inner join Afiliado as a1 on p1.perId = a1.afiPersona
				inner join #tablaDatos t on t.apdId=apd1.apdId
				--where apg1.apgPeriodoAporte >= convert(varchar(7),isnull(r.roaFechaIngreso,r.roaFechaAfiliacion)) and apd1.apdTipoCotizante = r.roaTipoAfiliado
				--and apg1.apgPersona = p.perId
				) --== Se agrega validación, para tener en cuenta si tiene reintegro, calcule contra el formulario y no con los aportes pasados. 
				group by p.perTipoIdentificacion, p.perNumeroIdentificacion,  r.roaTipoAfiliado, r.roaEmpleador,p.perId
							--=== Este deja el salario mayor que es el que prima. 
			;with salarioMayor as (select *, DENSE_RANK() over (partition by apdTipoCotizante,periodo,empresa,perIdPersona order by salario desc, apdId desc) as id
									from #aportecat3) delete from salarioMayor where id > 1
		--borra las Personas que encontro ya que tienen registro de salario en formaulario en el regular o regular -1
			delete #listadoPersonas  where #listadoPersonas.perId in(select perIdPersona  from #aporteFormulario )


			--se consultan las personas restantes que no tienen aportes ni fechas de afiliaon en el rango pero tienen novedad de cambio de salario en el rango 
			insert #aporteFormulario (perTipoIdentificacion, perNumeroIdentificacion, salario, tipoCotizante, roaEmpleador,perIdPersona)
			select p.perTipoIdentificacion, p.perNumeroIdentificacion,  sum(r.roaValorSalarioMesadaIngresos) as [salario], r.roaTipoAfiliado, r.roaEmpleador,p.perId
				from persona as p with (nolock)
				inner join afiliado as a with (nolock) on p.perId = a.afiPersona
				inner join RolAfiliado as r with (nolock) on a.afiId = r.roaAfiliado
				inner join SolicitudNovedadPersona sp on sp.snpRolAfiliado=r.roaId
				inner join SolicitudNovedad sn on sn.snoId=sp.snpSolicitudNovedad
				inner join Solicitud s on s.solId=sn.snoSolicitudGlobal
				where r.roaEstadoAfiliado = 'ACTIVO' and a.afiPersona in (select l.perId from  #listadoPersonas l)
				AND s.solTipoTransaccion in ('ACTUALIZAR_VALOR_TIPO_SALARIO_CONTRATO_TRABAJADOR_DEPENDIENTE_PRESENCIAL','ACTUALIZAR_VALOR_TIPO_SALARIO_CONTRATO_TRABAJADOR_DEPENDIENTE_DEPWEB')
				AND s.solFechaRadicacion >= try_convert(date,(concat(@periRegularMenos1, N'-01')))  and s.solFechaRadicacion <= try_convert(date,(concat(@periodo, N'-01')))
				group by p.perTipoIdentificacion, p.perNumeroIdentificacion,  r.roaTipoAfiliado, r.roaEmpleador,p.perId

			--==== Nuevo Ajuste, para depurar el cálculo. 
			--select e.empId as empId, convert(varchar(7),dateadd(month,-1,r.roaFechaIngreso)) as periodoAct --=== Ajuste al calculo para evaluar periodo regular. 
			--into #empActivos
			--from Persona as p with (nolock)
			--inner join Afiliado as a with (nolock) on p.perId = a.afiPersona
			--inner join RolAfiliado as r with (nolock) on r.roaAfiliado = a.afiId
			--inner join empleador as em with (nolock) on r.roaEmpleador = em.empId
			--inner join Empresa as e with (nolock) on em.empEmpresa = e.empId
			--where p.perId in (select l.perId from  #listadoPersonas l)
			--and r.roaEstadoAfiliado = 'ACTIVO'

			select e.empId as empId, p.perId
			into #empInActivos
			from Persona as p with (nolock)
			inner join Afiliado as a with (nolock) on p.perId = a.afiPersona
			inner join RolAfiliado as r with (nolock) on r.roaAfiliado = a.afiId
			inner join empleador as em with (nolock) on r.roaEmpleador = em.empId
			inner join Empresa as e with (nolock) on em.empEmpresa = e.empId
			where p.perId in (select l.perId from  #listadoPersonas l)
			and r.roaEstadoAfiliado = 'INACTIVO'	
				--======= Borrar el registro de empresa inactivo de los aportes 2024-10-09
			delete a
			from #aportecat3 as a
			inner join #empInActivos as b on a.empresa = b.empId and a.perIdPersona = b.perId

			;with ValidarReintegroFecha  as (select p.perId, perNumeroIdentificacion, perTipoIdentificacion, r.roaEmpleador, r.roaEstadoAfiliado, r.roaValorSalarioMesadaIngresos, e.empId as empIdApg,
			case when r.roaEstadoAfiliado = 'INACTIVO' then convert(varchar(7), r.roaFechaRetiro,126) else null end as FecReti,
			convert(varchar(7), dateadd(month,-1,r.roaFechaIngreso),126) as fecIng
			from RolAfiliado as r with (nolock) 
			inner join Afiliado as a with (nolock) on r.roaAfiliado = a.afiId
			inner join Persona as p with (nolock) on p.perId = a.afiPersona
			inner join Empleador as em with (nolock) on em.empId = r.roaEmpleador
			inner join Empresa as e with (nolock) on e.empId = em.empEmpresa
			where p.perId in (select l.perId from  #listadoPersonas l))
			update b set salario = case when b.periodo >= a.fecIng then b.salario else a.roaValorSalarioMesadaIngresos  end --=== Ajuste al calculo para evaluar periodo regular. 
			from ValidarReintegroFecha as a
			inner join #aportecat3 as b on a.perId = b.perIdPersona and a.empIdApg = b.empresa

			create table #aportecat2 ([perTipoIdentificacion] nvarchar(30), [perNumeroIdentificacion] nvarchar(20),[salario] numeric(19,2),  [apdTipoCotizante] nvarchar(30), periodo nvarchar(10), empresa bigInt, apgId bigInt,perId BIGINT)
			insert #aportecat2
			select perTipoIdentificacion,perNumeroIdentificacion,salario,apdTipoCotizante,periodo,empresa,apgId,perIdPersona from #aportecat3


			create table #aporteCatFinal ([perTipoIdentificacion] nvarchar (30), [perNumeroIdentificacion] nvarchar(30), [salario] numeric (19,2), [apdTipoCotizante] nvarchar(50),empresa bigInt,perid BIGINT)
			insert #aporteCatFinal (perTipoIdentificacion, perNumeroIdentificacion, salario, apdTipoCotizante,empresa,perid)
			select distinct 
			final.perTipoIdentificacion, final.perNumeroIdentificacion, case when (count(*) over (partition by perNumeroIdentificacion,apdTipoCotizante)) > 1 then (sum(final.salario) over (partition by perNumeroIdentificacion,apdTipoCotizante,empresa)) else final.salario end as [salario], final.apdTipoCotizante
			,final.empresa,final.perId
			from (
			select perTipoIdentificacion, perNumeroIdentificacion, apdTipoCotizante, periodo, empresa, 
			max(salario) as salario,perId
			from #aportecat2
			group by perTipoIdentificacion, perNumeroIdentificacion, apdTipoCotizante, periodo, empresa,perId
			) as final

			update abc set abc.empresa = empl.empId
			from #aporteCatFinal as abc 
			inner join empleador as empl on abc.empresa = empl.empEmpresa

			-----------------------------------------------------
			-----COMPARACION NUEVA AFILIACION & APORTES
			-----------------------------------------------------
			create table #tablaFinal (perTipoIdentificacion nvarchar (30), perNumeroIdentificacion nvarchar(30),salario numeric (19,2), apdTipoCotizante nvarchar(50),perId BIGINT)
			insert #tablaFinal (perTipoIdentificacion,perNumeroIdentificacion,salario, apdTipoCotizante,perid)
			select perTipoIdentificacion,perNumeroIdentificacion,sum(salario) as salario, apdTipoCotizante,perid
			from (
			select distinct perTipoIdentificacion,perNumeroIdentificacion,max(salario) as salario, apdTipoCotizante,empresa,perid
			from (
			select a.perTipoIdentificacion,a.perNumeroIdentificacion,a.salario,a.apdTipoCotizante,a.empresa,a.perid from #aporteCatFinal a
			union all
			select   f.perTipoIdentificacion,f.perNumeroIdentificacion, f.salario,f.tipoCotizante,f.roaEmpleador,f.perIdPersona AS perid from #aporteFormulario f) as t
			group by t.perTipoIdentificacion, t.perNumeroIdentificacion, apdTipoCotizante, empresa,perid) as t1
			group by t1.perTipoIdentificacion, t1.perNumeroIdentificacion, t1.apdTipoCotizante,perid


			--Insert a modo de auditoria
			INSERT INTO CalculoIngresosFovis (cifPersona,cifSumaSalario,cifFechaCalculo,cifIdPostulacion) 
			select t.perId,t.salario,dbo.GetLocalDate(),@idPostulacion from #tablaFinal t

		select sum(c.salario) as sumatoriaHogar 
		from #tablaFinal  c



		/*	select sum(b.cifSumaSalario) as sumatoriaHogar,b.fechaCalculo ,b.cifIdPostulacion from (
		select   c.cifPersona,c.cifSumaSalario,max(cifFechaCalculo) as fechaCalculo,cifIdPostulacion
		from CalculoIngresosFovis c
		where cifIdPostulacion=3292
		GROUP BY  c.cifPersona,c.cifSumaSalario,cifIdPostulacion) as b
		GROUP BY  b.cifIdPostulacion,b.fechaCalculo*/
			-------------------------
			-----FIN COMPARACION NUEVA AFILIACION & APORTES
			--------------

	END ELSE
	BEGIN
	SET @mensajeExcepxion ='El jefe Hogar no esta activo en el hogar en la postualción evaluada y ninguno de sus integrantes se encuentra Activo en el hogar e la postulación evaluada'
	--select @mensajeExcepxion
	END

END
