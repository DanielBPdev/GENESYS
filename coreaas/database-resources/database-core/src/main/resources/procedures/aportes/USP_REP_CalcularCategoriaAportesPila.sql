
-- =============================================
-- Author: 
-- Create date: 
-- Description: SP que calcula las categorias cuando vienen por aportes. 
-- Ajuste: 2022-06-24 Se realiza ajuste, para recalcular las categorias. 
-- =============================================
CREATE OR ALTER  PROCEDURE [dbo].[USP_REP_CalcularCategoriaAportesPila]
@apdRegistroDetallado BIGINT
    --@apdPersona BIGINT
with execute as owner
AS
		BEGIN

		DECLARE @afiId BIGINT
		DECLARE @emplId BIGINT 
		DECLARE @empleadorId BIGINT 
		DECLARE @estadoAfiliado VARCHAR(30);
		DECLARE @idRolAfiliado BIGINT;
		DECLARE @clasificacion VARCHAR(60);

		declare @apdTipoCotizante varchar(30);
		declare @tipoAportante varchar(50);
		declare @apdPersona bigInt;
		declare @apdId bigInt;
		declare @periodo date;
		declare @periRegular varchar(7);
		declare @fechaCreacionAporteTime datetime;
		declare @fechaCreacionAporte date;
		declare @marcaPeriodo VARCHAR(60);

		set @periRegular = convert(varchar(7),(select dateadd(day, 1, dateadd(month, -1, eomonth(dbo.GetLocalDate())))));

		select @fechaCreacionAporte=apd.apdFechaCreacion,@apdId = apd.apdId, @apdPersona = apd.apdPersona, @apdTipoCotizante = apd.apdTipoCotizante, @tipoAportante =apg.apgTipoSolicitante, @periodo = try_convert(date,(concat(apg.apgPeriodoAporte, N'-01'))),@marcaPeriodo = apd.apdMarcaPeriodo
		from aporteDetallado as apd with (nolock) 
		inner join AporteGeneral as apg with (nolock) on apd.apdAporteGeneral = apg.apgId 
		where apd.apdRegistroDetallado = @apdRegistroDetallado

		set @fechaCreacionAporteTime= case when @marcaPeriodo = 'PERIODO_FUTURO' then dbo.GetLocalDate() else (select CONVERT(DATETIME,  convert(varchar(50),convert(date,@fechaCreacionAporte))+' '+convert(varchar(12),convert(time,dbo.getlocaldate())))) end
		select @estadoAfiliado  = r.roaEstadoAfiliado,@idRolAfiliado=r.roaId, @afiId = a.afiId
		from RolAfiliado r with (nolock)
		inner join Afiliado a with (nolock) on a.afiId=r.roaAfiliado
		where a.afiPersona=@apdPersona and r.roaTipoAfiliado = @apdTipoCotizante
		AND r.roaEstadoAfiliado IS NOT NULL


		if(@apdTipoCotizante='TRABAJADOR_DEPENDIENTE')
		begin
		 set @clasificacion='TRABAJADOR_DEPENDIENTE'
		 end else
		 begin

			select @clasificacion =s.solClasificacion 
			from Solicitud s with (nolock)
			INNER JOIN  SolicitudAfiliacionPersona sap with (nolock) on sap.sapSolicitudGlobal=s.solId
			INNER JOIN RolAfiliado roa with (nolock) ON sap.sapRolAfiliado = roa.roaId
			WHERE sapEstadoSolicitud <> 'PRE_RADICADA'
			and roa.roaId=@idRolAfiliado 
		    and s.solClasificacion is not null
		end

	  SELECT @emplId = empl.empId,@empleadorId=empl.empId
        FROM Empresa emp with (nolock) 
            INNER JOIN Empleador empl with (nolock)  ON empl.empEmpresa = emp.empId
            INNER JOIN AporteGeneral apg  with (nolock)   ON emp.empId = apg.apgEmpresa
            INNER JOIN AporteDetallado apd   with (nolock)    ON apd.apdAporteGeneral = apg.apgId
        WHERE apd.apdId = @apdId 
        SET NOCOUNT ON;   
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

				create table #aporteFormulario (perTipoIdentificacion nvarchar(30), perNumeroIdentificacion nvarchar(20), salario numeric (19,2), [tipoCotizante] nvarchar (50), roaEmpleador bigInt)
				insert #aporteFormulario (perTipoIdentificacion, perNumeroIdentificacion, salario, tipoCotizante, roaEmpleador)
				select p.perTipoIdentificacion, p.perNumeroIdentificacion,  sum(r.roaValorSalarioMesadaIngresos) as [salario], r.roaTipoAfiliado, r.roaEmpleador
				from persona as p with (nolock)
				inner join afiliado as a with (nolock) on p.perId = a.afiPersona
				inner join RolAfiliado as r with (nolock) on a.afiId = r.roaAfiliado
				where r.roaEstadoAfiliado = 'ACTIVO' and a.afiPersona=@apdPersona --and r.roaEmpleador != @empleadorId
				and not exists (select 1  from SolicitudAfiliacionPersona sp where sp.sapRolAfiliado=r.roaId and sp.sapEstadoSolicitud  IN ('RADICADA','ASIGNADA_AL_BACK'))
				and not exists (select 1 
				from AporteDetallado as apd1 with (nolock)
				inner join AporteGeneral as apg1 with (nolock) on apd1.apdAporteGeneral = apg1.apgId
				inner join Empresa as e1 with (nolock) on apg1.apgEmpresa = e1.empId
				inner join Empleador as em1 with (nolock) on e1.empId = em1.empEmpresa
				where apg1.apgPeriodoAporte >= convert(varchar(7),isnull(r.roaFechaIngreso,r.roaFechaAfiliacion)) and apd1.apdTipoCotizante = r.roaTipoAfiliado
				and em1.empId = r.roaEmpleador and apd1.apdPersona = p.perId
				union --== Se agrega validación independiente o pensionado. 
				select 1 
				from AporteDetallado as apd1 with (nolock)
				inner join AporteGeneral as apg1 with (nolock) on apd1.apdAporteGeneral = apg1.apgId
				inner join persona as p1 on p1.perId = apg1.apgPersona
				inner join Afiliado as a1 on p1.perId = a1.afiPersona
				where apg1.apgPeriodoAporte >= convert(varchar(7),isnull(r.roaFechaIngreso,r.roaFechaAfiliacion)) and apd1.apdTipoCotizante = r.roaTipoAfiliado
				and apg1.apgPersona = p.perId
				) --== Se agrega validación, para tener en cuenta si tiene reintegro, calcule contra el formulario y no con los aportes pasados. 
				group by p.perTipoIdentificacion, p.perNumeroIdentificacion,  r.roaTipoAfiliado, r.roaEmpleador

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
			left join persona as p with (nolock) on p.perId = apd.apdPersona 
			where apd.apdPersona = @apdPersona  AND apdSalarioBasico is not null and apdEstadoAporteAjuste != 'ANULADO'
			and apg.apgPeriodoAporte <= @periRegular --== Ajuste para solo validar los periodos regulares o retroactvos 2023-05-29
			
			--=== Este deja el salario mayor que es el que prima. 
			;with salarioMayor as (select *, DENSE_RANK() over (partition by apdTipoCotizante,periodo,empresa,perIdPersona order by salario desc, apdId desc) as id
									from #aportecat3) delete from salarioMayor where id > 1


			--==== Nuevo Ajuste, para depurar el cálculo. 
			select e.empId as empId, convert(varchar(7),dateadd(month,-1,r.roaFechaIngreso)) as periodoAct --=== Ajuste al calculo para evaluar periodo regular. 
			into #empActivos
			from Persona as p with (nolock)
			inner join Afiliado as a with (nolock) on p.perId = a.afiPersona
			inner join RolAfiliado as r with (nolock) on r.roaAfiliado = a.afiId
			inner join empleador as em with (nolock) on r.roaEmpleador = em.empId
			inner join Empresa as e with (nolock) on em.empEmpresa = e.empId
			where p.perId = @apdPersona
			and r.roaEstadoAfiliado = 'ACTIVO'
			and not exists (select 1  from SolicitudAfiliacionPersona sp where sp.sapRolAfiliado=r.roaId and sp.sapEstadoSolicitud 
			                IN ('RADICADA','ASIGNADA_AL_BACK'))

			select e.empId as empId
			into #empInActivos
			from Persona as p with (nolock)
			inner join Afiliado as a with (nolock) on p.perId = a.afiPersona
			inner join RolAfiliado as r with (nolock) on r.roaAfiliado = a.afiId
			inner join empleador as em with (nolock) on r.roaEmpleador = em.empId
			inner join Empresa as e with (nolock) on em.empEmpresa = e.empId
			where p.perId = @apdPersona
			and r.roaEstadoAfiliado = 'INACTIVO'

			--======= Borrar el registro de empresa inactivo de los aportes 2024-10-09
			delete a
			from #aportecat3 as a
			inner join #empInActivos as b on a.empresa = b.empId


			;with ValidarReintegroFecha  as (select p.perId, perNumeroIdentificacion, perTipoIdentificacion, r.roaEmpleador, r.roaEstadoAfiliado, r.roaValorSalarioMesadaIngresos, e.empId as empIdApg,
			case when r.roaEstadoAfiliado = 'INACTIVO' then convert(varchar(7), r.roaFechaRetiro,126) else null end as FecReti,
			convert(varchar(7), dateadd(month,-1,r.roaFechaIngreso),126) as fecIng
			from RolAfiliado as r with (nolock) 
			inner join Afiliado as a with (nolock) on r.roaAfiliado = a.afiId
			inner join Persona as p with (nolock) on p.perId = a.afiPersona
			inner join Empleador as em with (nolock) on em.empId = r.roaEmpleador
			inner join Empresa as e with (nolock) on e.empId = em.empEmpresa
			where p.perId = @apdPersona)
			update b set salario = case when b.periodo >= a.fecIng then b.salario else a.roaValorSalarioMesadaIngresos  end --=== Ajuste al calculo para evaluar periodo regular. 
			from ValidarReintegroFecha as a
			inner join #aportecat3 as b on a.perId = b.perIdPersona and a.empIdApg = b.empresa
				
			

			select perId, convert(varchar(7),isnull(r.roaFechaIngreso,r.roaFechaAfiliacion)) as periodoAct, r.roaTipoAfiliado
			into #indPenActivos
			from RolAfiliado r with (nolock)
			inner join Afiliado a with (nolock) on a.afiId=r.roaAfiliado
			inner join persona as p with (nolock) on p.perId = a.afiPersona
			where a.afiPersona=@apdPersona and r.roaTipoAfiliado in ('PENSIONADO', 'TRABAJADOR_INDEPENDIENTE') and r.roaEstadoAfiliado =  'ACTIVO'

			declare @novIng table (registroDetallado bigInt, origen varchar(250))
			insert @novIng
			execute sp_execute_remote pilaReferenceData, N'select rdnRegistroDetallado from staging.RegistroDetalladoNovedad with (nolock) where rdnRegistroDetallado = @apdRegistroDetallado and rdnTipoNovedad = ''NOVEDAD_ING'' and rdnAccionNovedad = ''APLICAR_NOVEDAD''',
														N'@apdRegistroDetallado bigint', @apdRegistroDetallado = @apdRegistroDetallado

			declare @perRegular varchar(7) = (select convert(varchar(7),EOMONTH(dbo.GetLocalDate(),-1)))
			;with depurar as (select *,
			case when apdTipoCotizante = 'TRABAJADOR_DEPENDIENTE'
				 then 
					case when empresa = (select empId from #empActivos where empId = empresa) and periodo >= (select periodoAct from #empActivos where empId = empresa) then 'Empresa Activa'
						else 
						case when empresa = (select * from #empInActivos where empId = empresa) and periodo < @perRegular
								then '0'--'No formalizado retirado' --== Se deja en cero, para que no evalue con la empresa inactiva. 2023-08-24
								else 
									case when empresa = (select empId from #empActivos where empId = empresa) and periodo < (select periodoAct from #empActivos where empId = empresa) then '0'
										else 
											case when periodo = @perRegular and (select count(*) from @novIng) > 0 then 'aplicar reintegro'
											else 
												case when (select count(*) from #empActivos) > 0 and periodo = @perRegular  then 'aplicar aporte porque está activo con otras empresas'
													else null
													end
											end
										end
						end
					end
				else --=== Entra a mirar si es idependiente y pensionado. 
						case when perIdPersona in (select perId from #indPenActivos where perId = perIdPersona and apdTipoCotizante = roaTipoAfiliado) and periodo >= (select periodoAct from #indPenActivos where perId = perIdPersona and apdTipoCotizante = roaTipoAfiliado) then 'Indepen o pensio activo'
							else 
								case when perIdPersona in (select perId from #indPenActivos where perId = perIdPersona and apdTipoCotizante = roaTipoAfiliado) and periodo < (select periodoAct from #indPenActivos where perId = perIdPersona and apdTipoCotizante = roaTipoAfiliado) then '0'
									else 
											case when periodo = @perRegular then '0'
											else null
											end
								end
							end
				end as borrar 
			from #aportecat3)
			delete from depurar where borrar is null or borrar = '0'
			--==== Finaliza Nuevo Ajuste, para depurar el cálculo. 

			create table #aportecat2 ([perTipoIdentificacion] nvarchar(30), [perNumeroIdentificacion] nvarchar(20),[salario] numeric(19,2),  [apdTipoCotizante] nvarchar(30), periodo nvarchar(10), empresa bigInt, apgId bigInt)
			insert #aportecat2
			select perTipoIdentificacion,perNumeroIdentificacion,salario,apdTipoCotizante,periodo,empresa,apgId from #aportecat3


			create table #aporteCatFinal ([perTipoIdentificacion] nvarchar (30), [perNumeroIdentificacion] nvarchar(30), [salario] numeric (19,2), [apdTipoCotizante] nvarchar(50),empresa bigInt)
			insert #aporteCatFinal (perTipoIdentificacion, perNumeroIdentificacion, salario, apdTipoCotizante,empresa)
			select distinct 
			final.perTipoIdentificacion, final.perNumeroIdentificacion, case when (count(*) over (partition by perNumeroIdentificacion,apdTipoCotizante)) > 1 then (sum(final.salario) over (partition by perNumeroIdentificacion,apdTipoCotizante,empresa)) else final.salario end as [salario], final.apdTipoCotizante
			,final.empresa
			from (
			select perTipoIdentificacion, perNumeroIdentificacion, apdTipoCotizante, periodo, empresa, 
			max(salario) as salario
			from #aportecat2
			group by perTipoIdentificacion, perNumeroIdentificacion, apdTipoCotizante, periodo, empresa
			) as final

			update abc set abc.empresa = empl.empId
			from #aporteCatFinal as abc 
			inner join empleador as empl on abc.empresa = empl.empEmpresa

			-----------------------------------------------------
			-----COMPARACION NUEVA AFILIACION & APORTES
			-----------------------------------------------------
			create table #tablaFinal (perTipoIdentificacion nvarchar (30), perNumeroIdentificacion nvarchar(30),salario numeric (19,2), apdTipoCotizante nvarchar(50))
			insert #tablaFinal (perTipoIdentificacion,perNumeroIdentificacion,salario, apdTipoCotizante)
			select perTipoIdentificacion,perNumeroIdentificacion,sum(salario) as salario, apdTipoCotizante
			from (
			select distinct perTipoIdentificacion,perNumeroIdentificacion,max(salario) as salario, apdTipoCotizante,empresa
			from (
			select * from #aporteCatFinal
			union all
			select * from #aporteFormulario) as t
			group by t.perTipoIdentificacion, t.perNumeroIdentificacion, apdTipoCotizante, empresa) as t1
			group by t1.perTipoIdentificacion, t1.perNumeroIdentificacion, t1.apdTipoCotizante
			-------------------------
			-----FIN COMPARACION NUEVA AFILIACION & APORTES
			--------------

			declare @actMulTi varchar (20) = (select min(roaEstadoAfiliado) from dbo.RolAfiliado where roaAfiliado = @afiId group by roaAfiliado) --=== Se realiza ajuste para ver si tiene afiliación, pero debe estar por lo menos activo una vez 2023-05-08
			if @afiId is not null and isnull(@actMulTi,'') = 'ACTIVO'  --- INICIA PROCESO,CUANDO SI TIENE AFILIACIÓN6
				begin
			  create table #TempCategoriasFinal (ctaAfiliado bigint,ctaTipoAfiliado varchar(30),ctaClasificacion varchar(48),ctaTotalIngresoMesada numeric (19,5),ctaCategoria varchar(50),ctaEstadoAfiliacion varchar(8),ctaFechaFinServicioSinAfiliacion datetime,ctaMotivoCambioCategoria varchar(50),ctaFechaCambioCategoria datetime,ctaTarifaUVT varchar (50));
			  INSERT #TempCategoriasFinal (ctaAfiliado,ctaTipoAfiliado,ctaClasificacion,ctaTotalIngresoMesada,ctaCategoria,ctaTarifaUVT,ctaEstadoAfiliacion,ctaFechaFinServicioSinAfiliacion,ctaMotivoCambioCategoria,ctaFechaCambioCategoria)
			SELECT @afiId, apdTipoCotizante, @clasificacion, salario, 
			 (CASE apdTipoCotizante 
             WHEN 'TRABAJADOR_DEPENDIENTE' then (case when salario <= (select prmValor * 2 from Parametro where prmNombre = 'SMMLV') then 'A'
					  when salario > (select prmValor * 2 from Parametro where prmNombre = 'SMMLV') and salario <= (select prmValor * 4 from Parametro where prmNombre = 'SMMLV') then 'B'
					  when salario > (select prmValor * 4 from Parametro where prmNombre = 'SMMLV')  then 'C'
					  else '' end) 
			 WHEN 'TRABAJADOR_INDEPENDIENTE' then 
				 (select case when @estadoAfiliado = 'INACTIVO' then 'SIN_CATEGORIA'
				 else 'B'
				 -- comentado GLPI 61674 else  (case when salario <= (select prmValor * 4 from Parametro where prmNombre = 'SMMLV') then 'B' else 'C' end )
				 end)
			 WHEN 'PENSIONADO' then 
			    (select case when @estadoAfiliado = 'INACTIVO' then 'SIN_CATEGORIA' ELSE 
				( case when @clasificacion in ('MAS_1_5_SM_0_6_POR_CIENTO','MAS_1_5_SM_2_POR_CIENTO') and salario > (select prmValor*2 from Parametro where prmNombre = 'SMMLV')   and salario <= (select prmValor*4 from Parametro where prmNombre = 'SMMLV')
				then 'B' 
				when @clasificacion in ('MAS_1_5_SM_0_6_POR_CIENTO','MAS_1_5_SM_2_POR_CIENTO') and salario > (select prmValor*4 from Parametro where prmNombre = 'SMMLV')  
				then 'C' ELSE 'A' END)
			END)
					  end) as Categoria,

					  -----Tarifa UVT
					  			(CASE apdTipoCotizante 
						 WHEN 'TRABAJADOR_DEPENDIENTE' then (case when  (CAST(salario as int ) /  (select p.valoruvt from ParametrizacionValorUVT p where YEAR(getDATE()) = YEAR(p.anio)) < 53) then 'A'
								  when (CAST(salario as int ) /  (select p.valoruvt from ParametrizacionValorUVT p where YEAR(getDATE()) = YEAR(p.anio)) BETWEEN 53 and 106)  then 'B'
								  when (CAST(salario as int ) /  (select p.valoruvt from ParametrizacionValorUVT p where YEAR(getDATE()) = YEAR(p.anio)) > 106)  then 'C'
								  else null end) 
						 WHEN 'TRABAJADOR_INDEPENDIENTE' then 
							 (select case when @estadoAfiliado = 'INACTIVO' then 'SIN_CATEGORIA'
							 else 'B'
							 -- comentado GLPI 61674 else  (case when salario <= (select prmValor * 4 from Parametro where prmNombre = 'SMMLV') then 'B' else 'C' end )
							 end)
						 WHEN 'PENSIONADO' then 
							(select case when @estadoAfiliado = 'INACTIVO' then 'SIN_CATEGORIA' ELSE 
							( case when @clasificacion in ('MAS_1_5_SM_0_6_POR_CIENTO','MAS_1_5_SM_2_POR_CIENTO') 
							AND (CAST(salario as int ) /  (select p.valoruvt from ParametrizacionValorUVT p where YEAR(getDATE()) = YEAR(p.anio)) BETWEEN 53 and 106) 
							then 'B' 
							when @clasificacion in ('MAS_1_5_SM_0_6_POR_CIENTO','MAS_1_5_SM_2_POR_CIENTO') 
							AND (CAST(salario as int ) /  (select p.valoruvt from ParametrizacionValorUVT p where YEAR(getDATE()) = YEAR(p.anio)) > 106) 
							then 'C' ELSE 'A' END)
						END)
					  end) as tarifaUVT
					  ----Fin Tarifa UVT
				  
					  , @estadoAfiliado, null,  (CASE apdTipoCotizante 
                            WHEN 'TRABAJADOR_DEPENDIENTE' THEN 
                                (CASE WHEN @empleadorId IS NOT NULL THEN 'APORTE_RECIBIDO_AFILIADO_CAJA' ELSE 'APORTE_RECIBIDO_NO_AFILIADO_CAJA' END)
                            ELSE 'APORTE_RECIBIDO' END
                        ) as motivoCambioCategoria, @fechaCreacionAporteTime
        FROM #tablaFinal     where #tablaFinal.salario is not null and #tablaFinal.perNumeroIdentificacion is not null and apdTipoCotizante = @apdTipoCotizante


			if @periodo < (select dateadd(day, 1, dateadd(month, -1, eomonth(dbo.GetLocalDate())))) --=== Control para no persistir categorias con aportes futuros inicia
				begin
		--=== Se realiza ajuste para insertar los registros nuevos a las categorías. 
			create table #nuevoBen (ctbCategoriaAfiliado bigInt, benAfiliado bigInt)
			;with cat as (
	select  case when ctaFechaCambioCategoria = max(ctaFechaCambioCategoria) over (partition by ctaAfiliado, ctaTipoAfiliado,ctaClasificacion) then ctaId else null end as ctaId
			,case when ctaFechaCambioCategoria = max(ctaFechaCambioCategoria) over (partition by ctaAfiliado, ctaTipoAfiliado,ctaClasificacion) then ctaAfiliado else null end as ctaAfiliado
			,case when ctaFechaCambioCategoria = max(ctaFechaCambioCategoria) over (partition by ctaAfiliado, ctaTipoAfiliado,ctaClasificacion) then ctaCategoria else null end as ctaCategoria
			,case when ctaFechaCambioCategoria = max(ctaFechaCambioCategoria) over (partition by ctaAfiliado, ctaTipoAfiliado,ctaClasificacion) then ctaTipoAfiliado else null end as ctaTipoAfiliado
			,case when ctaFechaCambioCategoria = max(ctaFechaCambioCategoria) over (partition by ctaAfiliado, ctaTipoAfiliado,ctaClasificacion) then ctaClasificacion else null end as ctaClasificacion
			,case when ctaFechaCambioCategoria = max(ctaFechaCambioCategoria) over (partition by ctaAfiliado, ctaTipoAfiliado,ctaClasificacion) then ctaTotalIngresoMesada else null end as ctaTotalIngresoMesada
			,case when ctaFechaCambioCategoria = max(ctaFechaCambioCategoria) over (partition by ctaAfiliado, ctaTipoAfiliado,ctaClasificacion) then ctaEstadoAfiliacion else null end as ctaEstadoAfiliacion
			,case when ctaFechaCambioCategoria = max(ctaFechaCambioCategoria) over (partition by ctaAfiliado, ctaTipoAfiliado,ctaClasificacion) then ctaMotivoCambioCategoria else null end as ctaMotivoCambioCategoria
			from CategoriaAfiliado with (nolock) where ctaAfiliado  = @afiId)
			INSERT INTO CategoriaAfiliado (ctaAfiliado,ctaTipoAfiliado,ctaClasificacion,ctaTotalIngresoMesada,ctaCategoria,ctaEstadoAfiliacion,ctaFechaFinServicioSinAfiliacion,ctaMotivoCambioCategoria,ctaFechaCambioCategoria,ctaTarifaUVT)
			--output inserted.ctaId, inserted.ctaAfiliado into #nuevoBen
			select a.ctaAfiliado,a.ctaTipoAfiliado,a.ctaClasificacion,a.ctaTotalIngresoMesada,a.ctaCategoria,a.ctaEstadoAfiliacion,a.ctaFechaFinServicioSinAfiliacion,a.ctaMotivoCambioCategoria,a.ctaFechaCambioCategoria,a.ctaTarifaUVT
			from #TempCategoriasFinal as a
			left join cat  as b on a.ctaAfiliado = b.ctaAfiliado and a.ctaAfiliado = b.ctaAfiliado and a.ctaClasificacion = b.ctaClasificacion and a.ctaCategoria = b.ctaCategoria and a.ctaMotivoCambioCategoria = b.ctaMotivoCambioCategoria
			where b.ctaAfiliado is null and a.ctaEstadoAfiliacion is not null

			 EXEC USP_REP_CalcularCategoriaBeneficiarioInsert NULL, @afiId
			--=== Se inserta el registro en categoría beneficiario en el caso de que aplique. 
			/*if (select count(*) from #nuevoBen) > 0
			begin
				insert CategoriaBeneficiario (ctbBeneficiarioDetalle,ctbTipoBeneficiario,ctbCategoriaAfiliado)
				select ben.benBeneficiarioDetalle, ben.benTipoBeneficiario, n.ctbCategoriaAfiliado
				from #nuevoBen as n
				inner join Beneficiario as ben with (nolock) on n.benAfiliado = ben.benAfiliado
			end*/
		end --=== Control para no persistir categorias con aportes futuros inicia
		--=== Finaliza ajuste para insertar los registros nuevos a las categorías. 
	end   --- FINALIZA EL PROCESO CUANDO SI TIENE AFILIACIÓN. 
END