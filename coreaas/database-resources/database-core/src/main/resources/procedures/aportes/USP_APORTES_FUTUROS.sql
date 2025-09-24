-- =============================================
-- Author:      EPROCESS - MAURICIO HERNANDEZ
-- Create Date: 13/04/2
-- Description: GLPI 50417 APORTES FUTUROS
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[SP_APORTES_FUTUROS]
AS
BEGIN

		declare @periodo varchar(7) = (select convert(varchar(7),eomonth(dbo.GetLocalDate(),-1)));
		
		select apgId, row_number() over (order by apgId) as id
		into #apgIds
		from dbo.AporteGeneral with(nolock)
		where 
		apgMarcaPeriodo = 'PERIODO_FUTURO' 
		and apgEstadoRegistroAporteAportante != 'REGISTRADO'
		and apgPeriodoAporte = @periodo
		and isnull(apgEstadoAportante,'') <> 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES'
		
		
		declare @cont int = 1
		declare @total int = (select count(*) from #apgIds)
		
		while @cont <= @total
			begin
		
				declare @apgId bigInt = (select apgId from #apgIds where id = @cont)
				update dbo.AporteGeneral set apgEnProcesoReconocimiento = 1 where apgId = @apgId;
				
				update AporteDetallado set 
					apdEstadoRegistroAporteCotizante = 'REGISTRADO',
					apdFormaReconocimientoAporte = 'RECONOCIMIENTO_RETROACTIVO_AUTOMATICO',
					apdFechaMovimiento = dbo.GetLocalDate()
				where apdAporteGeneral = @apgId
				
				update dbo.AporteGeneral set 
					apgFechaReconocimiento = dbo.GetLocalDate(),
					apgEstadoRegistroAporteAportante = 'REGISTRADO',
					apgFormaReconocimientoAporte = 'RECONOCIMIENTO_RETROACTIVO_AUTOMATICO'
				where apgId = @apgId
				
				update dbo.AporteGeneral set apgEnProcesoReconocimiento = 0 where apgId = @apgId;
		
				set @cont += 1
		
			end
		
		
		select apgId, row_number() over (order by apgId) as id, apgTipoSolicitante, apgEstadoAportante, apgEmpresa, case when em.empId is null then 'NO' else 'SI' end as apropiacion
		into #apgIds2
		from dbo.AporteGeneral as apg with(nolock)
		inner join dbo.Empresa as e with(nolock) on apg.apgEmpresa = e.empId
		left join dbo.Empleador as em with(nolock) on e.empId = em.empEmpresa
		where apgMarcaPeriodo = 'PERIODO_FUTURO' 
		and apgEstadoRegistroAporteAportante != 'REGISTRADO'
		and apgPeriodoAporte = @periodo
		and isnull(apgEstadoAportante,'') = 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES'
		and apgTipoSolicitante = 'EMPLEADOR'
		
		select *, row_number() over (order by apgId) as id2
		into #apgIds2_1
		from #apgIds2 
		where apropiacion = 'SI'
		
		declare @cont2 int = 1
		declare @total2 int = (select count(*) from #apgIds2_1)
		
		if @total2 > 0
			begin
				while @cont2 <= @total2
					begin
					
							declare @apgId2 bigInt = (select apgId from #apgIds2_1 where id2 = @cont2)
							update dbo.AporteGeneral set apgEnProcesoReconocimiento = 1 where apgId = @apgId2;
							
							update dbo.AporteDetallado set 
								apdEstadoRegistroAporteCotizante = 'REGISTRADO',
								apdFormaReconocimientoAporte = 'RECONOCIMIENTO_RETROACTIVO_AUTOMATICO',
								apdFechaMovimiento = dbo.GetLocalDate()
							where apdAporteGeneral = @apgId2
							
							update dbo.AporteGeneral set 
								apgFechaReconocimiento = dbo.GetLocalDate(),
								apgEstadoRegistroAporteAportante = 'REGISTRADO',
								apgFormaReconocimientoAporte = 'RECONOCIMIENTO_RETROACTIVO_AUTOMATICO'
							where apgId = @apgId2
							
							update dbo.AporteGeneral set apgEnProcesoReconocimiento = 0 where apgId = @apgId2;
		
							set @cont2 += 1
		
					end
			end
		
		
		
		select apgId, row_number() over (order by apgId) as id, apgTipoSolicitante, apgEstadoAportante, apgPersona, case when afi.afiPersona is null then 'NO' else 'SI' end as apropiacion
		into #apgIds3
		from dbo.AporteGeneral as apg with (nolock)
		inner join dbo.Persona as p with (nolock) on apg.apgPersona = p.perId
		left join (select a.afiPersona, case when r.roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE' then 'INDEPENDIENTE' else r.roaTipoAfiliado end as roaTipoAfiliado
					from dbo.Afiliado as a with (nolock)
					inner join dbo.RolAfiliado as r with (nolock) on a.afiId = r.roaAfiliado
					where isnull(r.roaTipoAfiliado,'') in ('TRABAJADOR_INDEPENDIENTE', 'PENSIONADO')) as afi on p.perId = afi.afiPersona and apg.apgTipoSolicitante = afi.roaTipoAfiliado
		where apgMarcaPeriodo = 'PERIODO_FUTURO' 
		and apgEstadoRegistroAporteAportante != 'REGISTRADO'
		and apgPeriodoAporte = @periodo
		and isnull(apgEstadoAportante,'') = 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES'
		and apgTipoSolicitante <> 'EMPLEADOR'
		
		select *, row_number() over (order by apgId) as id2
		into #apgIds3_1
		from #apgIds3 
		where apropiacion = 'SI'
		
		
		declare @cont3 int = 1
		declare @total3 int = (select count(*) from #apgIds3_1)
		
		if @total3 > 0
			begin
				while @cont3 <= @total3
					begin
		
							declare @apgId3 bigInt = (select apgId from #apgIds3_1 where id2 = @cont3)
							update dbo.AporteGeneral set apgEnProcesoReconocimiento = 1 where apgId = @apgId3;
							
							update dbo.AporteDetallado set 
								apdEstadoRegistroAporteCotizante = 'REGISTRADO',
								apdFormaReconocimientoAporte = 'RECONOCIMIENTO_RETROACTIVO_AUTOMATICO',
								apdFechaMovimiento = dbo.GetLocalDate()
							where apdAporteGeneral = @apgId3
							
							update dbo.AporteGeneral set 
								apgFechaReconocimiento = dbo.GetLocalDate(),
								apgEstadoRegistroAporteAportante = 'REGISTRADO',
								apgFormaReconocimientoAporte = 'RECONOCIMIENTO_RETROACTIVO_AUTOMATICO'
							where apgId = @apgId3
							
							update dbo.AporteGeneral set apgEnProcesoReconocimiento = 0 where apgId = @apgId3;
		
							set @cont3 += 1
					end
			end

END;