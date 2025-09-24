-- =============================================
-- Author: Robinson Castillo Capera
-- Create Date: 2022-06-24
-- Description: SP encargado de Calculas las categorias, cuando es una nueva afiliacion. 
-- =============================================
create or alter procedure [dbo].[USP_REP_CalcularCategoriaNuevaAfiliacion]

      @afiId BIGINT, 
	  @empleadorId BIGINT,
	  @apdPersona bigInt,
	  @estadoAfiliado VARCHAR(30),
	  @idRolAfiliado bigInt

as
    BEGIN

	 DECLARE @clasificacion VARCHAR(60);
	 declare @tipoAfiliadoNuevaAfi varchar(30);
	 declare @solTipoTransaccion varchar(70);

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
			drop table if exists #nuevoBen1
			drop table if exists #TempCategoriasFinal2
			
			
			;with solo as (select 
			case when s.solId = max(s.solId) over (partition by sap.sapRolAfiliado) then s.solClasificacion else null end as solClasificacion
			,case when s.solId = max(s.solId) over (partition by sap.sapRolAfiliado) then roa.roaTipoAfiliado else null end as roaTipoAfiliado
			,case when s.solId = max(s.solId) over (partition by sap.sapRolAfiliado) then (case when solTipoTransaccion = 'NOVEDAD_REINTEGRO' then replace(solTipoTransaccion,'NOVEDAD_REINTEGRO','REINTEGRO')
					else
						case when s.solTipoTransaccion = 'AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION' then replace(s.solTipoTransaccion,'AFILIACION_PERSONAS_WEB_DEPENDIENTE_','')
						when s.solTipoTransaccion = 'AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO' then replace(s.solTipoTransaccion,'AFILIACION_PERSONAS_WEB_DEPENDIENTE_','')
								when s.solTipoTransaccion = 'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION' then replace(s.solTipoTransaccion,'AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_','')
								else replace(s.solTipoTransaccion,'AFILIACION_PERSONAS_PRESENCIAL_','') end
							end)
					else null end as solTipoTransaccion
			from Solicitud s with (nolock)
			INNER JOIN  SolicitudAfiliacionPersona sap with (nolock) on sap.sapSolicitudGlobal=s.solId
			INNER JOIN RolAfiliado roa with (nolock) ON sap.sapRolAfiliado = roa.roaId
			WHERE sapEstadoSolicitud = 'CERRADA' and roa.roaId= @idRolAfiliado /*and  s.solClasificacion is not null ORDER BY sap.sapId desc*/)
			select @clasificacion = isnull(solClasificacion,(CASE WHEN roaTipoAfiliado='TRABAJADOR_DEPENDIENTE' THEN 'TRABAJADOR_DEPENDIENTE' ELSE NULL END)), @tipoAfiliadoNuevaAfi = roaTipoAfiliado, @solTipoTransaccion = solTipoTransaccion
			from solo



				declare @periodoLimiteAporte varchar(7) =( select CONVERT(VARCHAR(7), DATEADD(mm, -1, dbo.GetLocalDate()), 20) ) --evitar calculo de aportes futuros
						---------
				create table #aporteFormulario (perTipoIdentificacion nvarchar(30), perNumeroIdentificacion nvarchar(20), salario numeric (19,2), [tipoCotizante] nvarchar (50), roaEmpleador bigInt, NuevaAfi varchar(30))
				insert #aporteFormulario (perTipoIdentificacion, perNumeroIdentificacion, salario, tipoCotizante, roaEmpleador, NuevaAfi)
				select p.perTipoIdentificacion, p.perNumeroIdentificacion,  sum(r.roaValorSalarioMesadaIngresos) as [salario], r.roaTipoAfiliado, r.roaEmpleador
				,case when r.roaId = @idRolAfiliado then 'NUEVA_AFILIACION' else null end as NuevaAfi
				from persona as p
				inner join afiliado as a on p.perId = a.afiPersona
				inner join RolAfiliado as r on a.afiId = r.roaAfiliado
				where r.roaEstadoAfiliado = 'ACTIVO' and a.afiPersona=@apdPersona --and r.roaEmpleador != @empleadorId
				and not exists (select 1 
				from AporteDetallado as apd1 with (nolock)
				inner join AporteGeneral as apg1 with (nolock) on apd1.apdAporteGeneral = apg1.apgId
				inner join Empresa as e1 with (nolock) on apg1.apgEmpresa = e1.empId
				inner join Empleador as em1 with (nolock) on e1.empId = em1.empEmpresa
				where apg1.apgPeriodoAporte >= convert(varchar(7),isnull(r.roaFechaIngreso,r.roaFechaAfiliacion)) and apd1.apdTipoCotizante = r.roaTipoAfiliado
				and apg1.apgPeriodoAporte<=@periodoLimiteAporte
				and em1.empId = r.roaEmpleador and apd1.apdPersona = p.perId
				union
				select 1 
				from AporteDetallado as apd1 with (nolock)
				inner join AporteGeneral as apg1 with (nolock) on apd1.apdAporteGeneral = apg1.apgId
				inner join persona as p1 on p1.perId = apg1.apgPersona
				inner join Afiliado as a1 on p1.perId = a1.afiPersona
				where apg1.apgPeriodoAporte >= convert(varchar(7),isnull(r.roaFechaIngreso,r.roaFechaAfiliacion)) and apd1.apdTipoCotizante = r.roaTipoAfiliado
				and apg1.apgPeriodoAporte<=@periodoLimiteAporte
				and apg1.apgPersona = p.perId
				) --== Se agrega validación, para tener en cuenta si tiene reintegro, calcule contra el formulario y no con los aportes pasados. 
				group by p.perTipoIdentificacion, p.perNumeroIdentificacion,  r.roaTipoAfiliado, r.roaEmpleador,r.roaId
			--=== Se agrega control, para tener en cuenta, cuando solo llega un aporte y no formaliza afiliación, ese aporte, para el seguiente periodo, no se tiene en cuenta. 


			create table #aportecat3 ([perTipoIdentificacion] nvarchar(30), [perNumeroIdentificacion] nvarchar(20),[salario] numeric(19,2),  [apdTipoCotizante] nvarchar(30), periodo nvarchar(10), empresa bigInt, apgId bigInt,perIdPersona bigInt)
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
			from AporteGeneral as apg with (nolock) 
			inner join AporteDetallado as apd with (nolock) on apg.apgId = apd.apdAporteGeneral
			left join persona as p with (nolock) on p.perId = apd.apdPersona 
			where apd.apdPersona = @apdPersona  AND apdSalarioBasico is not null and apdEstadoAporteAjuste !='ANULADO'
			and apg.apgPeriodoAporte<=@periodoLimiteAporte
			
			--==== Nuevo Ajuste, para depurar el cálculo. 
			select e.empId as empId, convert(varchar(7),r.roaFechaIngreso) as periodoAct
			into #empActivos
			from Persona as p with (nolock)
			inner join Afiliado as a with (nolock) on p.perId = a.afiPersona
			inner join RolAfiliado as r with (nolock) on r.roaAfiliado = a.afiId
			inner join empleador as em with (nolock) on r.roaEmpleador = em.empId
			inner join Empresa as e with (nolock) on em.empEmpresa = e.empId
			where p.perId = @apdPersona
			and r.roaEstadoAfiliado = 'ACTIVO'

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
			convert(varchar(7), r.roaFechaIngreso,126) as fecIng
			from RolAfiliado as r with (nolock) 
			inner join Afiliado as a with (nolock) on r.roaAfiliado = a.afiId
			inner join Persona as p with (nolock) on p.perId = a.afiPersona
			inner join Empleador as em with (nolock) on em.empId = r.roaEmpleador
			inner join Empresa as e with (nolock) on e.empId = em.empEmpresa
			where p.perId = @apdPersona)
			update b set salario = case when a.fecIng > b.periodo then a.roaValorSalarioMesadaIngresos else b.salario end
			from ValidarReintegroFecha as a
			inner join #aportecat3 as b on a.perId = b.perIdPersona and a.empIdApg = b.empresa

			select perId, convert(varchar(7),isnull(r.roaFechaIngreso,r.roaFechaAfiliacion)) as periodoAct, r.roaTipoAfiliado
			into #indPenActivos
			from RolAfiliado r with (nolock)
			inner join Afiliado a with (nolock) on a.afiId=r.roaAfiliado
			inner join persona as p with (nolock) on p.perId = a.afiPersona
			where a.afiPersona=@apdPersona and r.roaTipoAfiliado in ('PENSIONADO', 'TRABAJADOR_INDEPENDIENTE') and r.roaEstadoAfiliado =  'ACTIVO'


			declare @perRegular varchar(7) = (select convert(varchar(7),EOMONTH(dbo.GetLocalDate(),-1)))
			;with depurar as (select *,
			case when apdTipoCotizante = 'TRABAJADOR_DEPENDIENTE'
				 then 
					case when empresa = (select empId from #empActivos where empId = empresa) and periodo >= (select periodoAct from #empActivos where empId = empresa) then 'Empresa Activa'
						else 
						case when empresa = (select * from #empInActivos where empId = empresa) and periodo = @perRegular
								then '0'--'No formalizado retirado' --== Se deja en cero, para que no evalue con la empresa inactiva. 2023-08-24
								else 
									case when empresa = (select empId from #empActivos where empId = empresa) and periodo < (select periodoAct from #empActivos where empId = empresa) then null
										else 
											case when periodo = @perRegular and (select count(*) from #empActivos) > 0 then 'aplicar aporte porque está activo con otras empresas'
											else 
													case when (select count(*) from #empActivos) > 0 and periodo = @perRegular then 'aplicar aporte porque está activo con otras empresas'
													else null
													end
											end
										end
						end
					end
				else --=== Entra a mirar si es idependiente y pensionado. 
						case when perIdPersona in (select perId from #indPenActivos where perId = perIdPersona and apdTipoCotizante = roaTipoAfiliado) and periodo >= (select periodoAct from #indPenActivos where perId = perIdPersona and apdTipoCotizante = roaTipoAfiliado) then 'Indepen o pensio activo'
							else 
								case when perIdPersona in (select perId from #indPenActivos where perId = perIdPersona and apdTipoCotizante = roaTipoAfiliado) and periodo < (select periodoAct from #indPenActivos where perId = perIdPersona and apdTipoCotizante = roaTipoAfiliado) then null
									else 
											case when periodo = @perRegular then '0'
											else null
											end
								end
							end
				end as borrar 
			from #aportecat3)
			delete from depurar where borrar is null  or borrar = '0'
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

			-----------------------------------------------------
			-----COMPARACION NUEVA AFILIACION & APORTES
			-----------------------------------------------------
			create table #tablaFinal (perTipoIdentificacion nvarchar (30), perNumeroIdentificacion nvarchar(30),salario numeric (19,2), apdTipoCotizante nvarchar(50),NuevaAfi varchar(30))
			insert #tablaFinal (perTipoIdentificacion,perNumeroIdentificacion,salario, apdTipoCotizante,NuevaAfi)
			select distinct perTipoIdentificacion,perNumeroIdentificacion,sum(salario), apdTipoCotizante,max(NuevaAfi) as NuevaAfi
			from (
			select perTipoIdentificacion,perNumeroIdentificacion,salario,apdTipoCotizante,null as NuevaAfi from #aporteCatFinal
			union all
			select perTipoIdentificacion,perNumeroIdentificacion,salario,tipoCotizante as apdTipoCotizante, NuevaAfi from #aporteFormulario) as t
			group by t.perTipoIdentificacion, t.perNumeroIdentificacion, apdTipoCotizante--,NuevaAfi
			-------------------------
			-----FIN COMPARACION NUEVA AFILIACION & APORTES
			--------------
	       declare @TempCantidadVecesActivo int=0;
			IF @estadoAfiliado='INACTIVO' and @clasificacion='TRABAJADOR_DEPENDIENTE'
			BEGIN
			select @TempCantidadVecesActivo=count(*) from RolAfiliado r 
			where r.roaAfiliado=@afiId AND r.roaTipoAfiliado='TRABAJADOR_DEPENDIENTE' and r.roaEstadoAfiliado='ACTIVO'

				IF @TempCantidadVecesActivo > 0
				BEGIN
				set @estadoAfiliado='ACTIVO'
				END
			END 
			
			if @afiId is not null  and @estadoAfiliado = 'ACTIVO'--- INICIA PROCESO,CUANDO SI TIENE AFILIACIÓN
				begin

			select @TempCantidadVecesActivo=count(*) from RolAfiliado r 
			where r.roaAfiliado=@afiId AND r.roaTipoAfiliado='TRABAJADOR_DEPENDIENTE' and r.roaEstadoAfiliado='ACTIVO'

			  create table #TempCategoriasFinal2 (ctaAfiliado bigint,ctaTipoAfiliado varchar(30),ctaClasificacion varchar(48),ctaTotalIngresoMesada numeric (19,5),ctaCategoria varchar(50),ctaEstadoAfiliacion varchar(8),ctaFechaFinServicioSinAfiliacion datetime,ctaMotivoCambioCategoria varchar(100),ctaFechaCambioCategoria datetime, ctaTarifaUVT varchar (50));
			  INSERT #TempCategoriasFinal2 (ctaAfiliado,ctaTipoAfiliado,ctaClasificacion,ctaTotalIngresoMesada,ctaCategoria,ctaTarifaUVT,ctaEstadoAfiliacion,ctaFechaFinServicioSinAfiliacion,ctaMotivoCambioCategoria,ctaFechaCambioCategoria)
			SELECT @afiId, apdTipoCotizante, @clasificacion, salario, 
			 (CASE apdTipoCotizante 
             WHEN 'TRABAJADOR_DEPENDIENTE' then (case when salario <= (select prmValor * 2 from Parametro where prmNombre = 'SMMLV') and @estadoAfiliado = 'ACTIVO' then 'A'
					  when (salario > (select prmValor * 2 from Parametro where prmNombre = 'SMMLV') and salario <= (select prmValor * 4 from Parametro where prmNombre = 'SMMLV')) and @estadoAfiliado = 'ACTIVO' then 'B'
					  when salario > (select prmValor * 4 from Parametro where prmNombre = 'SMMLV') and @estadoAfiliado = 'ACTIVO'  then 'C'
					  else 'SIN_CATEGORIA' end) 
			 WHEN 'TRABAJADOR_INDEPENDIENTE' then 
				 (select case when @estadoAfiliado = 'INACTIVO' AND @TempCantidadVecesActivo <= 1  then 'SIN_CATEGORIA'
				  else 'B'
				 --COMENTADO glpi 61674 else  (case when salario <= (select prmValor * 4 from Parametro where prmNombre = 'SMMLV') then 'B' else 'C' end )
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

					  , @estadoAfiliado, null
					  , case when @estadoAfiliado = 'ACTIVO'
					  then 
						case when apdTipoCotizante = 'TRABAJADOR_DEPENDIENTE'
								 then
									case when NuevaAfi = 'NUEVA_AFILIACION' then @solTipoTransaccion
										else 
											case when @empleadorId is not null then 'APORTE_RECIBIDO_AFILIADO_CAJA' 
											     else 'APORTE_RECIBIDO_NO_AFILIADO_CAJA' 
												 end
										end
								 else
									case when NuevaAfi = 'NUEVA_AFILIACION' then 'NUEVA_AFILIACION'
										 else 'APORTE_RECIBIDO'
										 end
								end 
						else 'RETIRO'
						end	as motivoCambioCategoria
						,dbo.GetLocalDate()
        FROM #tablaFinal     where #tablaFinal.salario is not null and #tablaFinal.perNumeroIdentificacion is not null and apdTipoCotizante = @tipoAfiliadoNuevaAfi

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
			from CategoriaAfiliado with (nolock) where ctaAfiliado = @afiId)
			INSERT INTO CategoriaAfiliado (ctaAfiliado,ctaTipoAfiliado,ctaClasificacion,ctaTotalIngresoMesada,ctaCategoria,ctaEstadoAfiliacion,ctaFechaFinServicioSinAfiliacion,ctaMotivoCambioCategoria,ctaFechaCambioCategoria,ctaTarifaUVT)
			--output inserted.ctaId, inserted.ctaAfiliado into #nuevoBen
			select a.ctaAfiliado,a.ctaTipoAfiliado,a.ctaClasificacion,a.ctaTotalIngresoMesada,a.ctaCategoria,a.ctaEstadoAfiliacion,a.ctaFechaFinServicioSinAfiliacion,a.ctaMotivoCambioCategoria,a.ctaFechaCambioCategoria,a.ctaTarifaUVT
			from #TempCategoriasFinal2 as a
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
		--=== Finaliza ajuste para insertar los registros nuevos a las categorías. 
	end   --- FINALIZA EL PROCESO CUANDO SI TIENE AFILIACIÓN. 
	
	
	--========================================================
	--=================== Se agrega validación para los retiros. 
	--========================================================

	else if @afiId is not null  and @estadoAfiliado = 'INACTIVO' 
	begin
	declare @tipoAfiliadoRetiro varchar(30);
	declare @salarioRetiro numeric(19,5);
	select @tipoAfiliadoRetiro = roaTipoAfiliado from RolAfiliado with (nolock) where roaId = @idRolAfiliado
	select @salarioRetiro = roaValorSalarioMesadaIngresos from RolAfiliado with (nolock) where roaId = @idRolAfiliado
				create table #TempCategoriasFinal (ctaAfiliado bigint,ctaTipoAfiliado varchar(30),ctaClasificacion varchar(48),ctaTotalIngresoMesada numeric (19,5),ctaCategoria varchar(50),ctaEstadoAfiliacion varchar(8),ctaFechaFinServicioSinAfiliacion datetime,ctaMotivoCambioCategoria varchar(50),ctaFechaCambioCategoria datetime,ctaTarifaUVT varchar(50));
			    INSERT #TempCategoriasFinal (ctaAfiliado,ctaTipoAfiliado,ctaClasificacion,ctaTotalIngresoMesada,ctaCategoria,ctaEstadoAfiliacion,ctaFechaFinServicioSinAfiliacion,ctaMotivoCambioCategoria,ctaFechaCambioCategoria,ctaTarifaUVT)
									select @afiId, @tipoAfiliadoRetiro,@clasificacion, @salarioRetiro,'SIN_CATEGORIA','INACTIVO',NULL,'RETIRO',dbo.getLocaldate(),'SIN_TARIFA'

		--=== Se realiza ajuste para insertar los registros nuevos a las categorías. 
			create table #nuevoBen1 (ctbCategoriaAfiliado bigInt, benAfiliado bigInt)
			;with cat as (
			select  case when ctaFechaCambioCategoria = max(ctaFechaCambioCategoria) over (partition by ctaAfiliado, ctaTipoAfiliado,ctaClasificacion) then ctaId else null end as ctaId
			,case when ctaFechaCambioCategoria = max(ctaFechaCambioCategoria) over (partition by ctaAfiliado, ctaTipoAfiliado,ctaClasificacion) then ctaAfiliado else null end as ctaAfiliado
			,case when ctaFechaCambioCategoria = max(ctaFechaCambioCategoria) over (partition by ctaAfiliado, ctaTipoAfiliado,ctaClasificacion) then ctaCategoria else null end as ctaCategoria
			,case when ctaFechaCambioCategoria = max(ctaFechaCambioCategoria) over (partition by ctaAfiliado, ctaTipoAfiliado,ctaClasificacion) then ctaTipoAfiliado else null end as ctaTipoAfiliado
			,case when ctaFechaCambioCategoria = max(ctaFechaCambioCategoria) over (partition by ctaAfiliado, ctaTipoAfiliado,ctaClasificacion) then ctaClasificacion else null end as ctaClasificacion
			,case when ctaFechaCambioCategoria = max(ctaFechaCambioCategoria) over (partition by ctaAfiliado, ctaTipoAfiliado,ctaClasificacion) then ctaTotalIngresoMesada else null end as ctaTotalIngresoMesada
			,case when ctaFechaCambioCategoria = max(ctaFechaCambioCategoria) over (partition by ctaAfiliado, ctaTipoAfiliado,ctaClasificacion) then ctaEstadoAfiliacion else null end as ctaEstadoAfiliacion
			,case when ctaFechaCambioCategoria = max(ctaFechaCambioCategoria) over (partition by ctaAfiliado, ctaTipoAfiliado,ctaClasificacion) then ctaMotivoCambioCategoria else null end as ctaMotivoCambioCategoria
			from CategoriaAfiliado with (nolock) where ctaAfiliado = @afiId)
			INSERT INTO CategoriaAfiliado (ctaAfiliado,ctaTipoAfiliado,ctaClasificacion,ctaTotalIngresoMesada,ctaCategoria,ctaEstadoAfiliacion,ctaFechaFinServicioSinAfiliacion,ctaMotivoCambioCategoria,ctaFechaCambioCategoria,ctaTarifaUVT)
			--output inserted.ctaId, inserted.ctaAfiliado into #nuevoBen1
			select a.ctaAfiliado,a.ctaTipoAfiliado,a.ctaClasificacion,a.ctaTotalIngresoMesada,a.ctaCategoria,a.ctaEstadoAfiliacion,a.ctaFechaFinServicioSinAfiliacion,a.ctaMotivoCambioCategoria,a.ctaFechaCambioCategoria,a.ctaTarifaUVT
			from #TempCategoriasFinal as a
			left join cat  as b on a.ctaAfiliado = b.ctaAfiliado and a.ctaAfiliado = b.ctaAfiliado and a.ctaClasificacion = b.ctaClasificacion and a.ctaCategoria = b.ctaCategoria and a.ctaMotivoCambioCategoria = b.ctaMotivoCambioCategoria
			where b.ctaAfiliado is null

			 EXEC USP_REP_CalcularCategoriaBeneficiarioInsert NULL, @afiId
			--=== Se inserta el registro en categoría beneficiario en el caso de que aplique. 
			/*if (select count(*) from #nuevoBen1) > 0
			begin
				insert CategoriaBeneficiario (ctbBeneficiarioDetalle,ctbTipoBeneficiario,ctbCategoriaAfiliado)
				select ben.benBeneficiarioDetalle, ben.benTipoBeneficiario, n.ctbCategoriaAfiliado
				from #nuevoBen1 as n
				inner join Beneficiario as ben with (nolock) on n.benAfiliado = ben.benAfiliado
			end*/
	end
END