-- =============================================
-- Author: Robinson Castillo
-- Create date: 2022-07-09
-- Description:	Procedimiento almacenado para gestion de las planillas N
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_CopiarAportesDesdeTemporalPilaPlanillasN]
	@idRegistroGeneralN BIGINT
AS
BEGIN
	SET NOCOUNT ON;
	set xact_abort on;
	begin try

		drop table if exists #PlanillaNBuscarAnterior
		drop table if exists #PlanillaNBuscaAporteGeneralAnt
		drop table if exists #aporteDetalladoParaUpdate
		drop table if exists #aporteDetalladoParaUpdateFinal
		drop table if exists #AporteDetalladoTemporalN
		drop table if exists #MovimientoAporteTemporalN
		drop table if exists #MovimientoAporteTemporalNUpdate
		drop table if exists #AporteGeneralTtemporalN


			INSERT RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
			VALUES (dbo.GetLocalDate(),'[USP_CopiarAportesDesdeTemporalPilaPlanillasN] INICIA' ,@idRegistroGeneralN);

			DECLARE @iRevision BIGINT;
			DECLARE @localDate DATETIME = dbo.getLocalDate()
			--declare @idRegistroGeneralN bigInt = 1116477

			create table #PlanillaNBuscarAnterior (redIdNuevo bigInt, redDiasCotizados smallInt, redValorIBC numeric(19,5), redTarifa numeric(19,5), redAporteObligatorio numeric(19,5), redRegistroDetalladoAnterior bigInt
			,redNumeroIdentificacionCotizante varchar(20), redOUTValorMoraCotizante numeric(19,5), redSalarioBasico numeric(19,5), planillaN bigInt, regFechaPagoAporte date,planillaAntes bigint, actualizarApd bit, regCuentaBancariaRecaudo varchar(20),origen varchar(200))
			insert #PlanillaNBuscarAnterior
			execute sp_execute_remote pilaReferenceData, N'select redId, redDiasCotizados, redValorIBC, redTarifa, redAporteObligatorio, redRegistroDetalladoAnterior, redNumeroIdentificacionCotizante, redOUTValorMoraCotizante, redSalarioBasico, 
															r.regNumPlanilla as planillaN, regFechaPagoAporte, r.regNumPlanillaAsociada as planillaAntes, rd_1.actualizarApd,r.regNumeroCuenta
															from dbo.PilaIndicePlanilla as p with (nolock)
															inner join staging.RegistroGeneral as r with (nolock) on p.pipId = r.regRegistroControl
															inner join staging.registroDetalladoPlanillaN as rd_1 with (nolock) on r.regId = rd_1.redRegistroGeneral
															where rd_1.redRegistroGeneral = @idRegistroGeneralN
															and rd_1.actualizarApd = 1'
														,N'@idRegistroGeneralN bigint', @idRegistroGeneralN = @idRegistroGeneralN


		--===================================================================================================================
		--==== Busqueda del registro N de N, para encontrar el original.
		--===================================================================================================================

		
			alter table #PlanillaNBuscarAnterior add id bigInt, procesado int, redRegistroDetalladoAnterior2 bigInt

			;with ajustarOrden as (
			select *, row_number() over (order by redIdNuevo) as Id2
			from #PlanillaNBuscarAnterior)
			update ajustarOrden set id = Id2

			declare @contNN int = 1
			declare @finN int = (select count(*) from #PlanillaNBuscarAnterior)

			while @contNN <= @finN
				begin
					
					declare @redRegistroDetalladoAnterior bigInt = (select redRegistroDetalladoAnterior from #PlanillaNBuscarAnterior where id = @contNN)

					if not exists (select * from dbo.AporteDetallado where apdRegistroDetallado = @redRegistroDetalladoAnterior)
						begin

							declare @registroOriginal as table (redId bigInt, ori varchar(250))
							insert @registroOriginal
						
							execute sp_execute_remote pilaReferenceData, N'
								declare @redId bigInt = @redRegistroDetalladoAnterior
								while 1 = 1
									begin
										declare @redIdAnt bigInt
										if exists (select * from staging.RegistroDetalladoPlanillaN where redId = @redId)
											begin
												set @redIdAnt = (select redRegistroDetalladoAnterior from staging.RegistroDetalladoPlanillaN where redId = @redId)
												set @redId = @redIdAnt
											end
										else 
											begin
												select @redId
												break;
											end
									end
							', N'@redRegistroDetalladoAnterior bigInt', @redRegistroDetalladoAnterior = @redRegistroDetalladoAnterior

							declare @redOri bigInt = (select redId from @registroOriginal)

							while 1 = 1 
								begin
									if exists (select * from dbo.AporteDetallado where apdRegistroDetallado = @redOri)
										begin
											set @redOri = (select apdRegistroDetallado from dbo.AporteDetallado where apdRegistroDetallado = @redOri)
											break;
										end
									else
										begin
											set @redOri = @redOri
											break;
										end
								end
							
							update #PlanillaNBuscarAnterior set redRegistroDetalladoAnterior2 = @redOri, procesado = 1  where id = @contNN

							delete from @registroOriginal

						end


					set @contNN += 1
				end

			--============= Ajuste para el registro detallado anterior. 2024-04-04
			update a set redRegistroDetalladoAnterior2 =  (case when redRegistroDetalladoAnterior2 is null then redRegistroDetalladoAnterior else redRegistroDetalladoAnterior2 end)
			from #PlanillaNBuscarAnterior as a

			--Variable Guarda Fecha Pago--
			-- DECLARE @fechaPagoN DATE

			select redNumeroIdentificacionCotizante
			,apd.apdId
			,apd.apdAporteGeneral
			,nuevo.redDiasCotizados, apd.apdDiasCotizados
			,case when nuevo.redDiasCotizados <> apd.apdDiasCotizados then nuevo.redDiasCotizados else 0 end as diasCotDif
			,nuevo.redValorIBC, apd.apdValorIBC
			,case when nuevo.actualizarApd = 1 and nuevo.redValorIBC > 0 then nuevo.redValorIBC else 0 end as diferenciaIBC
			,nuevo.redTarifa, apd.apdTarifa
			,case when nuevo.redTarifa <> apd.apdTarifa then nuevo.redTarifa  else 0 end as diferenciaTarifa
			,nuevo.redAporteObligatorio, apd.apdAporteObligatorio
			,case when nuevo.actualizarApd = 1 and nuevo.redAporteObligatorio > 0 then nuevo.redAporteObligatorio else 0 end as diferenciaAporteCot
			,nuevo.redOUTValorMoraCotizante, apd.apdValorIntMora
			,case when nuevo.actualizarApd = 1 and nuevo.redOUTValorMoraCotizante <> 0 then nuevo.redOUTValorMoraCotizante else 0 end as diferenciaMora
			,((case when nuevo.redValorIBC <> apd.apdValorIBC then 1 else 0 end) + (case when nuevo.redTarifa <> apd.apdTarifa then 1 else 0 end) + (case when nuevo.redAporteObligatorio <> apd.apdAporteObligatorio then 1 else 0 end) + 
												(case when nuevo.redDiasCotizados <> apd.apdDiasCotizados then 1 else 0 end) + (case when nuevo.redOUTValorMoraCotizante <> apd.apdValorIntMora then 1 else 0 end)) as ActualizarAporteDetalldo
			,nuevo.redSalarioBasico as redSalarioBasico
			, nuevo.planillaAntes, nuevo.planillaN, nuevo.regFechaPagoAporte as fechaPagoN
			into #PlanillaNBuscaAporteGeneralAnt
			from #PlanillaNBuscarAnterior as nuevo
			inner join AporteDetallado as apd with (nolock) on nuevo.redRegistroDetalladoAnterior2 = apd.apdRegistroDetallado

			-- Asignar valor a @fechaPagoN
			-- SELECT @fechaPagoN = nuevo.regFechaPagoAporte
			-- FROM #PlanillaNBuscarAnterior AS nuevo

			declare @noti as table (pipId varchar(50), origen varchar(250))
			insert @noti
			execute sp_execute_remote pilaReferenceData, 
			N' select p.pipId
			from dbo.pilaIndicePlanilla as p
			inner join dbo.pilaEstadoBloque as pb on p.pipId = pb.pebIndicePlanilla
			inner join staging.registroGeneral as r on p.pipId = r.regRegistroControl
			where r.regId = @idRegistroGeneralN
			', N'@idRegistroGeneralN bigInt', @idRegistroGeneralN = @idRegistroGeneralN
			

	--== Se valida si hay datos para actualizar el aporteDetallado
			if (select sum(ActualizarAporteDetalldo) from #PlanillaNBuscaAporteGeneralAnt group by planillaN) > 0 and not exists (select * from dbo.aporteDetalladoRegistroControlN where pipId = (select pipId from @noti))
				
				begin

					begin transaction

						CREATE TABLE #AporteDetalladoTemporalN ([apdId] [bigint] NOT NULL,[apdAporteGeneral] [bigint] NULL,[apdDiasCotizados] [smallint] NULL,[apdHorasLaboradas] [smallint] NULL,[apdSalarioBasico] [numeric](19, 5) NULL,[apdValorIBC] [numeric](19, 5) NULL,
						[apdValorIntMora] [numeric](19, 5) NULL,[apdTarifa] [numeric](5, 5) NULL,[apdAporteObligatorio] [numeric](19, 5) NULL,[apdValorSaldoAporte] [numeric](19, 5) NULL,[apdCorrecciones] [varchar](400) NULL,[apdEstadoAporteRecaudo] [varchar](50) NULL,
						[apdEstadoAporteAjuste] [varchar](50) NULL,[apdEstadoRegistroAporte] [varchar](50) NULL,[apdSalarioIntegral] [bit] NULL,[apdMunicipioLaboral] [varchar](5) NULL,[apdDepartamentoLaboral] [smallint] NULL,[apdRegistroDetallado] [bigint] NOT NULL,
						[apdTipoCotizante] [varchar](100) NULL,[apdEstadoCotizante] [varchar](60) NULL,[apdEstadoAporteCotizante] [varchar](50) NULL,[apdEstadoRegistroAporteCotizante] [varchar](40) NULL,[apdPersona] [bigint] NULL,[apdUsuarioAprobadorAporte] [varchar](50) NOT NULL,
						[apdEstadoRegistroAporteArchivo] [varchar](60) NOT NULL,[apdCodSucursal] [varchar](10) NULL,[apdNomSucursal] [varchar](100) NULL,[apdFechaMovimiento] [date] NULL,[apdFechaCreacion] [date] NULL,[apdFormaReconocimientoAporte] [varchar](75) NULL,
						[apdMarcaPeriodo] [varchar](19) NULL,[apdModalidadRecaudoAporte] [varchar](40) NULL,[apdMarcaCalculoCategoria] [bit] NULL,[apdModificadoAportesOK] [bit] NULL,[apdRegistroDetalladoUltimo] bigint)

						--== Entramos al update del aporteDetallado en el caso de que aplique actualización. 		
						select	apd.apdAporteObligatorio, case when act.diferenciaAporteCot > 0 then act.redAporteObligatorio else 0 end as update_apdAporteObligatorio,
							apd.apdValorIBC, case when act.diferenciaIBC > 0 then act.redValorIBC else 0 end as update_apdValorIBC,
							apd.apdDiasCotizados, case when act.diasCotDif > 0 then act.redDiasCotizados else 0 end as update_apdDiasCotizados,
							apd.apdTarifa, case when act.diferenciaTarifa > 0 then act.redTarifa else apd.apdTarifa end as update_apdTarifa,
							apd.apdValorIntMora, case when act.diferenciaMora > 0 then act.redOUTValorMoraCotizante else 0 end as update_apdValorIntMora,
							apd.apdSalarioBasico, case when apd.apdSalarioBasico <> act.redSalarioBasico then act.redSalarioBasico else apd.apdSalarioBasico end as update_apdSalarioBasico,
							apd.apdId
						into #aporteDetalladoParaUpdate
						from dbo.AporteDetallado as apd with (nolock)
						inner join #PlanillaNBuscaAporteGeneralAnt as act on apd.apdId = act.apdId
		
						select *,
						(case when update_apdAporteObligatorio > 0 then 1 else 0 end + 
						case when update_apdValorIBC > 0 then 1 else 0 end +
						case when apdDiasCotizados <> update_apdDiasCotizados then 1 else 0 end +
						case when apdTarifa <> update_apdTarifa then 1 else 0 end +
						case when update_apdValorIntMora > 0 then 1 else 0 end +
						case when apdSalarioBasico <> update_apdSalarioBasico then 1 else 0 end) as act
						into #aporteDetalladoParaUpdateFinal
						from #aporteDetalladoParaUpdate

						
						update apd set 
						apd.apdAporteObligatorio = case when (case when b.update_apdAporteObligatorio <> 0 then 1 else 0 end) > 0 then (apd.apdAporteObligatorio + b.update_apdAporteObligatorio) else apd.apdAporteObligatorio end,
						apd.apdValorIntMora = case when (case when b.update_apdValorIntMora <> 0 then 1 else 0 end) > 0 then (apd.apdValorIntMora + b.update_apdValorIntMora) else apd.apdValorIntMora end,
						apd.apdValorIBC = case when (case when b.update_apdValorIBC <> 0 then 1 else 0 end) > 0 then (apd.apdValorIBC + b.update_apdValorIBC) else apd.apdValorIBC end,
						apd.apdDiasCotizados = case when (case when apd.apdDiasCotizados <> update_apdDiasCotizados then 1 else 0 end) > 0 
												--then 
												--	case when (apd.apdDiasCotizados + b.update_apdDiasCotizados) > 30 
												--		then 30
												--		else (apd.apdDiasCotizados + b.update_apdDiasCotizados) end 
												--else --apd.apdDiasCotizados 
												--update_apdDiasCotizados
												--end,
												then 
												case when b.update_apdDiasCotizados > 30
												then 30
												else update_apdDiasCotizados end
												end,
						apd.apdTarifa = case when (case when update_apdTarifa <> 0 then 1 else 0 end) > 0 then b.update_apdTarifa else apd.apdTarifa end,
						apd.apdSalarioBasico = case when (case when apd.apdSalarioBasico <> update_apdSalarioBasico then 1 else 0 end) > 0 then b.update_apdSalarioBasico else apd.apdSalarioBasico end
						output
						INSERTED.apdId,INSERTED.apdAporteGeneral,INSERTED.apdDiasCotizados,INSERTED.apdHorasLaboradas,INSERTED.apdSalarioBasico,INSERTED.apdValorIBC,INSERTED.apdValorIntMora,INSERTED.apdTarifa,INSERTED.apdAporteObligatorio,
						INSERTED.apdValorSaldoAporte,INSERTED.apdCorrecciones,INSERTED.apdEstadoAporteRecaudo,INSERTED.apdEstadoAporteAjuste,INSERTED.apdEstadoRegistroAporte,INSERTED.apdSalarioIntegral,INSERTED.apdMunicipioLaboral,INSERTED.apdDepartamentoLaboral,
						INSERTED.apdRegistroDetallado,INSERTED.apdTipoCotizante,INSERTED.apdEstadoCotizante,INSERTED.apdEstadoAporteCotizante,INSERTED.apdEstadoRegistroAporteCotizante,INSERTED.apdPersona,INSERTED.apdUsuarioAprobadorAporte,INSERTED.apdEstadoRegistroAporteArchivo,
						INSERTED.apdCodSucursal,INSERTED.apdNomSucursal,INSERTED.apdFechaMovimiento,INSERTED.apdFechaCreacion,INSERTED.apdFormaReconocimientoAporte,INSERTED.apdMarcaPeriodo,INSERTED.apdModalidadRecaudoAporte,INSERTED.apdMarcaCalculoCategoria,
						INSERTED.apdModificadoAportesOK,INSERTED.apdRegistroDetalladoUltimo INTO #AporteDetalladoTemporalN
						from dbo.AporteDetallado as apd with (nolock)
						inner join #aporteDetalladoParaUpdateFinal as b on apd.apdId = b.apdId
						where b.act > 0

						--== Creamos la auditoria para el update. 

						--=========================================
						--=== Se crea auditoria para el aporteDetallado
						--=========================================
						EXEC USP_UTIL_GET_CrearRevisionActualizar  'com.asopagos.entidades.ccf.aportes.AporteDetallado', NULL, '', 'USUARIO_SISTEMA', @iRevision OUTPUT

						INSERT INTO aud.AporteDetallado_aud(apdId,apdAporteGeneral,apdDiasCotizados,apdHorasLaboradas,apdSalarioBasico,apdValorIBC,apdValorIntMora,apdTarifa,apdAporteObligatorio,apdValorSaldoAporte,apdCorrecciones,apdEstadoAporteRecaudo,apdEstadoAporteAjuste,
						apdEstadoRegistroAporte,apdSalarioIntegral,apdMunicipioLaboral,apdDepartamentoLaboral,apdRegistroDetallado,apdTipoCotizante,apdEstadoCotizante,apdEstadoAporteCotizante,apdEstadoRegistroAporteCotizante,apdPersona,apdUsuarioAprobadorAporte,apdEstadoRegistroAporteArchivo,
						apdCodSucursal,apdNomSucursal,apdFechaMovimiento,apdFechaCreacion,apdFormaReconocimientoAporte,apdMarcaPeriodo,apdModalidadRecaudoAporte,apdMarcaCalculoCategoria,apdModificadoAportesOK,apdRegistroDetalladoUltimo,REV, REVTYPE)
						SELECT apdId,apdAporteGeneral,apdDiasCotizados,apdHorasLaboradas,apdSalarioBasico,apdValorIBC,apdValorIntMora,apdTarifa,apdAporteObligatorio,apdValorSaldoAporte,apdCorrecciones,apdEstadoAporteRecaudo,apdEstadoAporteAjuste,apdEstadoRegistroAporte,apdSalarioIntegral,
						apdMunicipioLaboral,apdDepartamentoLaboral,apdRegistroDetallado,apdTipoCotizante,apdEstadoCotizante,apdEstadoAporteCotizante,apdEstadoRegistroAporteCotizante,apdPersona,apdUsuarioAprobadorAporte,apdEstadoRegistroAporteArchivo,apdCodSucursal,apdNomSucursal,
						apdFechaMovimiento,apdFechaCreacion,apdFormaReconocimientoAporte,apdMarcaPeriodo,apdModalidadRecaudoAporte,apdMarcaCalculoCategoria,apdModificadoAportesOK,apdRegistroDetalladoUltimo,@iRevision, 1 FROM #AporteDetalladoTemporalN

						--=========================================
						--=== MovimientoAporte. 
						--=========================================
						CREATE TABLE #MovimientoAporteTemporalN ([moaId] [bigint] NOT NULL,[moaTipoAjuste] [varchar](20) NULL,[moaTipoMovimiento] [varchar](23) NULL,[moaEstadoAporte] [varchar](22) NULL,[moaValorAporte] [numeric](19, 5) NULL,[moaValorInteres] [numeric](19, 5) NULL,
						[moaFechaActualizacionEstado] [datetime] NULL,[moaFechaCreacion] [datetime] NULL,[moaAporteDetallado] [bigint] NULL,[moaAporteGeneral] [bigint] NOT NULL)

						INSERT INTO MovimientoAporte (moaTipoAjuste,moaTipoMovimiento,moaEstadoAporte,moaValorAporte,moaValorInteres,moaFechaActualizacionEstado,moaFechaCreacion,moaAporteDetallado,moaAporteGeneral)
						OUTPUT INSERTED.moaId,INSERTED.moaTipoAjuste,INSERTED.moaTipoMovimiento,INSERTED.moaEstadoAporte,INSERTED.moaValorAporte,INSERTED.moaValorInteres,INSERTED.moaFechaActualizacionEstado,INSERTED.moaFechaCreacion,
						INSERTED.moaAporteDetallado,INSERTED.moaAporteGeneral INTO #MovimientoAporteTemporalN
						SELECT 'CORRECCION_A_LA_ALTA','CORRECCION_APORTES','VIGENTE',a.apdAporteObligatorio,a.apdValorIntMora,
						--@localDate,
						DATETIMEFROMPARTS(YEAR(@localDate), MONTH(@localDate), day(@localDate), DATEPART(hour, @localDate), 00, 00, 00),
						@localDate,a.apdId,a.apdAporteGeneral 
						FROM #AporteDetalladoTemporalN as a
						--inner join #PlanillaNBuscaAporteGeneralAnt  as b on a.apdId = b.apdId

						INSERT INTO aud.MovimientoAporte_aud(moaId,moaTipoAjuste,moaTipoMovimiento,moaEstadoAporte,moaValorAporte,moaValorInteres,moaFechaActualizacionEstado,moaFechaCreacion,moaAporteDetallado,moaAporteGeneral,REV, REVTYPE)
						SELECT moaId,moaTipoAjuste,moaTipoMovimiento,moaEstadoAporte,moaValorAporte,moaValorInteres,moaFechaActualizacionEstado,moaFechaCreacion,moaAporteDetallado,moaAporteGeneral,@iRevision, 0 FROM #MovimientoAporteTemporalN where moaTipoAjuste = 'CORRECCION_A_LA_ALTA'

						--=========================================
						--=== Se realiza update al registro detallado anterior. 
						--=========================================
						CREATE TABLE #MovimientoAporteTemporalNUpdate ([moaId] [bigint] NOT NULL,[moaTipoAjuste] [varchar](20) NULL,[moaTipoMovimiento] [varchar](23) NULL,[moaEstadoAporte] [varchar](22) NULL,[moaValorAporte] [numeric](19, 5) NULL,[moaValorInteres] [numeric](19, 5) NULL,
						[moaFechaActualizacionEstado] [datetime] NULL,[moaFechaCreacion] [datetime] NULL,[moaAporteDetallado] [bigint] NULL,[moaAporteGeneral] [bigint] NOT NULL)

						update moa set moa.moaEstadoAporte = 'CORREGIDO'
						OUTPUT INSERTED.moaId,INSERTED.moaTipoAjuste,INSERTED.moaTipoMovimiento,INSERTED.moaEstadoAporte,INSERTED.moaValorAporte,INSERTED.moaValorInteres,INSERTED.moaFechaActualizacionEstado,INSERTED.moaFechaCreacion,
						INSERTED.moaAporteDetallado,INSERTED.moaAporteGeneral INTO #MovimientoAporteTemporalNUpdate
						from MovimientoAporte as moa with (nolock)
						inner join #MovimientoAporteTemporalN as n on moa.moaAporteDetallado = n.moaAporteDetallado
						where moa.moaTipoAjuste is null and moa.moaTipoMovimiento = 'RECAUDO_PILA_AUTOMATICO' and moa.moaEstadoAporte = 'VIGENTE'

						INSERT INTO aud.MovimientoAporte_aud(moaId,moaTipoAjuste,moaTipoMovimiento,moaEstadoAporte,moaValorAporte,moaValorInteres,moaFechaActualizacionEstado,moaFechaCreacion,moaAporteDetallado,moaAporteGeneral,REV, REVTYPE)
						SELECT moaId,moaTipoAjuste,moaTipoMovimiento,moaEstadoAporte,moaValorAporte,moaValorInteres,moaFechaActualizacionEstado,moaFechaCreacion,moaAporteDetallado,moaAporteGeneral,@iRevision, 1 FROM #MovimientoAporteTemporalNUpdate where moaEstadoAporte = 'CORREGIDO'

							--== Se valida si hay actualización en el aporteGeneral
							if (select sum(diferenciaAporteCot) from #PlanillaNBuscaAporteGeneralAnt group by planillaN) > 0 or (select sum(diferenciaMora) from #PlanillaNBuscaAporteGeneralAnt group by planillaN) > 0
								begin

									CREATE TABLE #AporteGeneralTtemporalN ([apgId] [bigint] NOT NULL,[apgPeriodoAporte] [varchar](7) NULL,[apgValTotalApoObligatorio] [numeric](19, 5) NULL,[apgValorIntMora] [numeric](19, 5) NULL,
									[apgFechaRecaudo] [date] NULL,[apgFechaProcesamiento] [datetime] NULL,[apgCodEntidadFinanciera] [smallint] NULL,[apgOperadorInformacion] [bigint] NULL,[apgModalidadPlanilla] [varchar](40) NULL,
									[apgModalidadRecaudoAporte] [varchar](40) NULL,[apgApoConDetalle] [bit] NULL,[apgNumeroCuenta] [varchar](17) NULL,[apgRegistroGeneral] [bigint] NOT NULL,[apgPersona] [bigint] NULL,
									[apgEmpresa] [bigint] NULL,[apgSucursalEmpresa] [bigint] NULL,[apgEstadoAportante] [varchar](50) NULL,[apgEstadoAporteAportante] [varchar](40) NULL,[apgEstadoRegistroAporteAportante] [varchar](30) NULL,
									[apgPagadorPorTerceros] [bit] NULL,[apgTipoSolicitante] [varchar](13) NULL,[apgOrigenAporte] [varchar](26) NULL,[apgCajaCompensacion] [int] NULL,[apgEmailAportante] [varchar](255) NULL,
									[apgEmpresaTramitadoraAporte] [bigint] NULL,[apgFechaReconocimiento] [datetime] NULL,[apgFormaReconocimientoAporte] [varchar](75) NULL,[apgMarcaPeriodo] [varchar](19) NULL,[apgMarcaActualizacionCartera] [bit] NULL,
									[apgConciliado] [bit] NULL,[apgNumeroPlanillaManual] [varchar](10) NULL,[apgEnProcesoReconocimiento] [bit] NULL,[apgRegistroGeneralUltimo] bigint)

									update apg set apg.apgValTotalApoObligatorio = apd.apdAporteObligatorio, apg.apgValorIntMora = apd.apdValorIntMora --, apg.apgFechaRecaudo = (SELECT nuevo.regFechaPagoAporte FROM #PlanillaNBuscarAnterior AS nuevo WHERE nuevo.moaId = dbo.aporteDetalladoRegistroControlN.moaId AND nuevo.regFechaPagoAporte IN (@fechaPagoN))	-->
									OUTPUT INSERTED.apgId,INSERTED.apgPeriodoAporte,INSERTED.apgValTotalApoObligatorio,INSERTED.apgValorIntMora,INSERTED.apgFechaRecaudo,INSERTED.apgFechaProcesamiento,INSERTED.apgCodEntidadFinanciera,INSERTED.apgOperadorInformacion
									,INSERTED.apgModalidadPlanilla,INSERTED.apgModalidadRecaudoAporte,INSERTED.apgApoConDetalle,INSERTED.apgNumeroCuenta,INSERTED.apgRegistroGeneral,INSERTED.apgPersona,INSERTED.apgEmpresa,INSERTED.apgSucursalEmpresa,INSERTED.apgEstadoAportante
									,INSERTED.apgEstadoAporteAportante,INSERTED.apgEstadoRegistroAporteAportante,INSERTED.apgPagadorPorTerceros,INSERTED.apgTipoSolicitante,INSERTED.apgOrigenAporte,INSERTED.apgCajaCompensacion,INSERTED.apgEmailAportante,INSERTED.apgEmpresaTramitadoraAporte
									,INSERTED.apgFechaReconocimiento,INSERTED.apgFormaReconocimientoAporte,INSERTED.apgMarcaPeriodo,INSERTED.apgMarcaActualizacionCartera,INSERTED.apgConciliado,INSERTED.apgNumeroPlanillaManual,INSERTED.apgEnProcesoReconocimiento
									,INSERTED.apgRegistroGeneralUltimo INTO #AporteGeneralTtemporalN
									from AporteGeneral as apg with (nolock)
									inner join (select sum(apd.apdAporteObligatorio) as apdAporteObligatorio, sum(apd.apdValorIntMora) as apdValorIntMora, apd.apdAporteGeneral
												from dbo.AporteDetallado as apd with(nolock)
												where apd.apdAporteGeneral in (select apdAporteGeneral from #PlanillaNBuscaAporteGeneralAnt group by apdAporteGeneral)
												group by apd.apdAporteGeneral) as apd on apg.apgId = apd.apdAporteGeneral


									--== Se crea la auditoria para el aporteGeneral
									INSERT INTO aud.AporteGeneral_aud(apgId,apgPeriodoAporte,apgValTotalApoObligatorio,apgValorIntMora,apgFechaRecaudo,apgFechaProcesamiento,apgCodEntidadFinanciera,apgOperadorInformacion,apgModalidadPlanilla,apgModalidadRecaudoAporte,apgApoConDetalle
									,apgNumeroCuenta,apgRegistroGeneral,apgPersona,apgEmpresa,apgSucursalEmpresa,apgEstadoAportante,apgEstadoAporteAportante,apgEstadoRegistroAporteAportante,apgPagadorPorTerceros,apgTipoSolicitante,apgOrigenAporte,apgCajaCompensacion,apgEmailAportante
									,apgEmpresaTramitadoraAporte,apgFechaReconocimiento,apgFormaReconocimientoAporte,apgMarcaPeriodo,apgMarcaActualizacionCartera,apgConciliado,apgNumeroPlanillaManual,apgEnProcesoReconocimiento
									,apgRegistroGeneralUltimo,REV, REVTYPE)
									SELECT apgid,apgPeriodoAporte,apgValTotalApoObligatorio,apgValorIntMora,apgFechaRecaudo,apgFechaProcesamiento,apgCodEntidadFinanciera,apgOperadorInformacion,apgModalidadPlanilla,apgModalidadRecaudoAporte,apgApoConDetalle
									,apgNumeroCuenta,apgRegistroGeneral,apgPersona,apgEmpresa,apgSucursalEmpresa,apgEstadoAportante,apgEstadoAporteAportante,apgEstadoRegistroAporteAportante,apgPagadorPorTerceros,apgTipoSolicitante,apgOrigenAporte,apgCajaCompensacion
									,apgEmailAportante,apgEmpresaTramitadoraAporte,apgFechaReconocimiento,apgFormaReconocimientoAporte,apgMarcaPeriodo,apgMarcaActualizacionCartera,apgConciliado,apgNumeroPlanillaManual,apgEnProcesoReconocimiento
									,apgRegistroGeneralUltimo,@iRevision, 1 FROM #AporteGeneralTtemporalN

								end

							--== Se llena tabla de control para notificación de plailla, después de que hace las validaciones. 
							--alter table dbo.aporteDetalladoRegistroControlN add moaId bigInt

							begin
								declare @pipIdInsert bigInt;
								declare @pipId table (pipId bigint, origen varchar(200))
								insert @pipId
								execute sp_execute_remote pilaReferenceData, N'select regRegistroControl from staging.registroGeneral where regId = @idRegistroGeneralN', N'@idRegistroGeneralN bigInt', @idRegistroGeneralN = @idRegistroGeneralN
								set @pipIdInsert = (select pipId from @pipId)
								
								/*
								insert aporteDetalladoRegistroControlN (redNumeroIdentificacionCotizante,apdId,apdAporteGeneral,redDiasCotizados,apdDiasCotizados,diasCotDif,redValorIBC,apdValorIBC
										,diferenciaIBC,redTarifa,apdTarifa,diferenciaTarifa,redAporteObligatorio,apdAporteObligatorio,diferenciaAporteCot,ActualizarAporteDetalldo, registroGeneralNuevo, pipId, diferenciaMora, fechaProcesamiento, planillaN, 
										planillaAntes, regFechaPagoAporte,moaId)
									select o.redNumeroIdentificacionCotizante,o.apdId,o.apdAporteGeneral,o.redDiasCotizados,o.apdDiasCotizados,o.diasCotDif,o.redValorIBC,o.apdValorIBC
										,o.diferenciaIBC,o.redTarifa,o.apdTarifa,o.diferenciaTarifa,o.redAporteObligatorio,o.apdAporteObligatorio,o.diferenciaAporteCot,o.ActualizarAporteDetalldo, @idRegistroGeneralN, @pipIdInsert, o.diferenciaMora, @localDate, o.planillaN, 
										o.planillaAntes, o.fechaPagoN, c.moaId
									from dbo.aporteDetalladoRegistroControlN as d with (nolock)
									right join #PlanillaNBuscaAporteGeneralAnt as o on d.apdId = o.apdId and o.apdAporteGeneral = d.apdAporteGeneral and d.registroGeneralNuevo = @idRegistroGeneralN and d.regFechaPagoAporte = o.fechaPagoN and o.planillaN = d.planillaN and o.planillaAntes = d.planillaAntes
									inner join #aporteDetalladoParaUpdateFinal as b on b.apdId = o.apdId
									inner join #MovimientoAporteTemporalN as c on b.apdId = c.moaAporteDetallado and o.apdAporteGeneral = c.moaAporteGeneral
									where (d.apdId is null and d.apdAporteGeneral is null and d.registroGeneralNuevo is null)
									and b.act > 0

								*/
								----creacion llenado de variable cuenta bancaria
								
								declare @cuenta bigint = (select top 1 regCuentaBancariaRecaudo from #PlanillaNBuscarAnterior)
                                declare @regCuentaBancariaRecaudo int
								set @regCuentaBancariaRecaudo = (select ID
                                                                 from dbo.cuentas_Bancarias
                                                                 where replace(NUMERO_CUENTA, '-','') = @cuenta
                                                                 and TIPO_RECAUDO = N'PILA')
		                                 
		                           insert dbo.aporteDetalladoRegistroControlN (redNumeroIdentificacionCotizante,apdId,apdAporteGeneral,redDiasCotizados,apdDiasCotizados,diasCotDif,redValorIBC,apdValorIBC
										,diferenciaIBC,redTarifa,apdTarifa,diferenciaTarifa,redAporteObligatorio,apdAporteObligatorio,diferenciaAporteCot,ActualizarAporteDetalldo, registroGeneralNuevo, pipId, diferenciaMora, fechaProcesamiento, planillaN, 
										planillaAntes, regFechaPagoAporte,moaId,regCuentaBancariaRecaudo)
										
									select o.redNumeroIdentificacionCotizante,o.apdId,o.apdAporteGeneral,o.redDiasCotizados,o.apdDiasCotizados,o.diasCotDif,o.redValorIBC,o.apdValorIBC
										,o.diferenciaIBC,o.redTarifa,o.apdTarifa,o.diferenciaTarifa,o.redAporteObligatorio,o.apdAporteObligatorio,o.diferenciaAporteCot,o.ActualizarAporteDetalldo, @idRegistroGeneralN, @pipIdInsert, o.diferenciaMora, @localDate, o.planillaN, 
										o.planillaAntes, o.fechaPagoN, c.moaId,@regCuentaBancariaRecaudo
									from #PlanillaNBuscaAporteGeneralAnt as o 
									inner join #aporteDetalladoParaUpdateFinal as b on b.apdId = o.apdId
									inner join #MovimientoAporteTemporalN as c on b.apdId = c.moaAporteDetallado and o.apdAporteGeneral = c.moaAporteGeneral
									where b.act > 0
									and not exists (select 1 from dbo.aporteDetalladoRegistroControlN as d with (nolock) where  d.apdId = o.apdId and o.apdAporteGeneral = d.apdAporteGeneral 
									and d.registroGeneralNuevo = @idRegistroGeneralN and d.regFechaPagoAporte = o.fechaPagoN and o.planillaN = d.planillaN and o.planillaAntes = d.planillaAntes)
                         
                        	---------------validaciones V1,V2 Y V3 sbayona
                           declare @planillaOrignal bigint=(select distinct planillaAntes from #PlanillaNBuscarAnterior)

							drop table if exists #DatosOriginales
							create table #DatosOriginales (regId bigint,shard varchar(max))
							insert into #DatosOriginales
							exec sp_execute_remote PilaReferenceData,N'select distinct regId
							from staging.registroGeneral rg 
						    where rg.regNumplanilla = @planillaOrignal and regRegistroControlManual is null
			                ', N'@planillaOrignal bigInt', @planillaOrignal = @planillaOrignal
							
						   declare @regIdOriginal bigint=(select distinct regId from #DatosOriginales)
						
						    drop table if exists #DatosN
							create table #DatosN (redOUTEstadoValidacionV0 varchar(10),redOUTEstadoValidacionV1 varchar(10),redOUTEstadoValidacionV2 varchar(10),redOUTEstadoValidacionV3 varchar(10),redNumeroIdentificacionCotizante varchar(60),shard varchar(max))
							insert into #DatosN
							exec sp_execute_remote PilaReferenceData,N'select distinct redOUTEstadoValidacionV0,redOUTEstadoValidacionV1,redOUTEstadoValidacionV2,redOUTEstadoValidacionV3,redNumeroIdentificacionCotizante 
							from staging.registroGeneral rg 
							inner join staging.registroDetallado rd on rg.regId=rd.redRegistroGeneral
                            where rg.regId = @idRegistroGeneralN and redOUTEstadoValidacionV0=''CUMPLE'' and redOUTEstadoValidacionV1=''OK'' and redOUTEstadoValidacionV2=''OK'' and redOUTEstadoValidacionV3=''OK''
			                ', N'@idRegistroGeneralN bigInt', @idRegistroGeneralN = @idRegistroGeneralN
							
						if exists (select * from #DatosN)
						begin 
						   -------------------creacion auditoria
						CREATE TABLE #AporteDetalladoTemporalN3 ([apdId] [bigint] NOT NULL,[apdAporteGeneral] [bigint] NULL,[apdDiasCotizados] [smallint] NULL,[apdHorasLaboradas] [smallint] NULL,[apdSalarioBasico] [numeric](19, 5) NULL,[apdValorIBC] [numeric](19, 5) NULL,
						[apdValorIntMora] [numeric](19, 5) NULL,[apdTarifa] [numeric](5, 5) NULL,[apdAporteObligatorio] [numeric](19, 5) NULL,[apdValorSaldoAporte] [numeric](19, 5) NULL,[apdCorrecciones] [varchar](400) NULL,[apdEstadoAporteRecaudo] [varchar](50) NULL,
						[apdEstadoAporteAjuste] [varchar](50) NULL,[apdEstadoRegistroAporte] [varchar](50) NULL,[apdSalarioIntegral] [bit] NULL,[apdMunicipioLaboral] [varchar](5) NULL,[apdDepartamentoLaboral] [smallint] NULL,[apdRegistroDetallado] [bigint] NOT NULL,
						[apdTipoCotizante] [varchar](100) NULL,[apdEstadoCotizante] [varchar](60) NULL,[apdEstadoAporteCotizante] [varchar](50) NULL,[apdEstadoRegistroAporteCotizante] [varchar](40) NULL,[apdPersona] [bigint] NULL,[apdUsuarioAprobadorAporte] [varchar](50) NOT NULL,
						[apdEstadoRegistroAporteArchivo] [varchar](60) NOT NULL,[apdCodSucursal] [varchar](10) NULL,[apdNomSucursal] [varchar](100) NULL,[apdFechaMovimiento] [date] NULL,[apdFechaCreacion] [date] NULL,[apdFormaReconocimientoAporte] [varchar](75) NULL,
						[apdMarcaPeriodo] [varchar](19) NULL,[apdModalidadRecaudoAporte] [varchar](40) NULL,[apdMarcaCalculoCategoria] [bit] NULL,[apdModificadoAportesOK] [bit] NULL,[apdRegistroDetalladoUltimo] bigint)

						   ---select ad.* 
						   update ad set apdEstadoRegistroAporteArchivo=(select distinct redOUTEstadoValidacionV1 from #DatosN)
						   output
						   	INSERTED.apdId,INSERTED.apdAporteGeneral,INSERTED.apdDiasCotizados,INSERTED.apdHorasLaboradas,INSERTED.apdSalarioBasico,INSERTED.apdValorIBC,INSERTED.apdValorIntMora,INSERTED.apdTarifa,INSERTED.apdAporteObligatorio,
							INSERTED.apdValorSaldoAporte,INSERTED.apdCorrecciones,INSERTED.apdEstadoAporteRecaudo,INSERTED.apdEstadoAporteAjuste,INSERTED.apdEstadoRegistroAporte,INSERTED.apdSalarioIntegral,INSERTED.apdMunicipioLaboral,INSERTED.apdDepartamentoLaboral,
							INSERTED.apdRegistroDetallado,INSERTED.apdTipoCotizante,INSERTED.apdEstadoCotizante,INSERTED.apdEstadoAporteCotizante,INSERTED.apdEstadoRegistroAporteCotizante,INSERTED.apdPersona,INSERTED.apdUsuarioAprobadorAporte,INSERTED.apdEstadoRegistroAporteArchivo,
							INSERTED.apdCodSucursal,INSERTED.apdNomSucursal,INSERTED.apdFechaMovimiento,INSERTED.apdFechaCreacion,INSERTED.apdFormaReconocimientoAporte,INSERTED.apdMarcaPeriodo,INSERTED.apdModalidadRecaudoAporte,INSERTED.apdMarcaCalculoCategoria,
							INSERTED.apdModificadoAportesOK,INSERTED.apdRegistroDetalladoUltimo INTO #AporteDetalladoTemporalN3
						   from dbo.AporteGeneral ag
						   inner join dbo.AporteDetallado ad on ag.apgId=ad.apdAporteGeneral
						   inner join dbo.Persona p on p.perId=ad.apdPersona
						   where ag.apgRegistroGeneral in (select regId from #DatosOriginales) and p.perNumeroIdentificacion in (select distinct redNumeroIdentificacionCotizante from #DatosN)

						   EXEC USP_UTIL_GET_CrearRevisionActualizar  'com.asopagos.entidades.ccf.aportes.AporteDetallado', NULL, '', 'USUARIO_SISTEMA', @iRevision OUTPUT

							INSERT INTO aud.AporteDetallado_aud(apdId,apdAporteGeneral,apdDiasCotizados,apdHorasLaboradas,apdSalarioBasico,apdValorIBC,apdValorIntMora,apdTarifa,apdAporteObligatorio,apdValorSaldoAporte,apdCorrecciones,apdEstadoAporteRecaudo,apdEstadoAporteAjuste,
							apdEstadoRegistroAporte,apdSalarioIntegral,apdMunicipioLaboral,apdDepartamentoLaboral,apdRegistroDetallado,apdTipoCotizante,apdEstadoCotizante,apdEstadoAporteCotizante,apdEstadoRegistroAporteCotizante,apdPersona,apdUsuarioAprobadorAporte,apdEstadoRegistroAporteArchivo,
							apdCodSucursal,apdNomSucursal,apdFechaMovimiento,apdFechaCreacion,apdFormaReconocimientoAporte,apdMarcaPeriodo,apdModalidadRecaudoAporte,apdMarcaCalculoCategoria,apdModificadoAportesOK,apdRegistroDetalladoUltimo,REV, REVTYPE)
							SELECT apdId,apdAporteGeneral,apdDiasCotizados,apdHorasLaboradas,apdSalarioBasico,apdValorIBC,apdValorIntMora,apdTarifa,apdAporteObligatorio,apdValorSaldoAporte,apdCorrecciones,apdEstadoAporteRecaudo,apdEstadoAporteAjuste,apdEstadoRegistroAporte,apdSalarioIntegral,
							apdMunicipioLaboral,apdDepartamentoLaboral,apdRegistroDetallado,apdTipoCotizante,apdEstadoCotizante,apdEstadoAporteCotizante,apdEstadoRegistroAporteCotizante,apdPersona,apdUsuarioAprobadorAporte,apdEstadoRegistroAporteArchivo,apdCodSucursal,apdNomSucursal,
							apdFechaMovimiento,apdFechaCreacion,apdFormaReconocimientoAporte,apdMarcaPeriodo,apdModalidadRecaudoAporte,apdMarcaCalculoCategoria,apdModificadoAportesOK,apdRegistroDetalladoUltimo,@iRevision, 1 FROM #AporteDetalladoTemporalN3
						   
						   ---------------actualizacion pila registroOriginal por cotizante
						    declare @redNumeroIdentificacionCotizante varchar(max)=(select STRING_AGG(redNumeroIdentificacionCotizante, ',') from #DatosN)
                          
                           exec sp_execute_remote PilaReferenceData,N'update rd set redOUTEstadoValidacionV1=''OK'' from staging.registroGeneral rg
						   inner join staging.registroDetallado rd on rg.regId=rd.redRegistroGeneral 
						   where rg.regId=@regIdOriginal and redNumeroIdentificacionCotizante in (@redNumeroIdentificacionCotizante)
						    ', N'@regIdOriginal bigInt,@redNumeroIdentificacionCotizante varchar(max)', @regIdOriginal = @regIdOriginal,@redNumeroIdentificacionCotizante=@redNumeroIdentificacionCotizante
						   
						   end
                           ---------------fin V1
                        


                           ----- validacion para que actualice los dias en AporteDetallado

						CREATE TABLE #AporteDetalladoTemporalN2 ([apdId] [bigint] NOT NULL,[apdAporteGeneral] [bigint] NULL,[apdDiasCotizados] [smallint] NULL,[apdHorasLaboradas] [smallint] NULL,[apdSalarioBasico] [numeric](19, 5) NULL,[apdValorIBC] [numeric](19, 5) NULL,
						[apdValorIntMora] [numeric](19, 5) NULL,[apdTarifa] [numeric](5, 5) NULL,[apdAporteObligatorio] [numeric](19, 5) NULL,[apdValorSaldoAporte] [numeric](19, 5) NULL,[apdCorrecciones] [varchar](400) NULL,[apdEstadoAporteRecaudo] [varchar](50) NULL,
						[apdEstadoAporteAjuste] [varchar](50) NULL,[apdEstadoRegistroAporte] [varchar](50) NULL,[apdSalarioIntegral] [bit] NULL,[apdMunicipioLaboral] [varchar](5) NULL,[apdDepartamentoLaboral] [smallint] NULL,[apdRegistroDetallado] [bigint] NOT NULL,
						[apdTipoCotizante] [varchar](100) NULL,[apdEstadoCotizante] [varchar](60) NULL,[apdEstadoAporteCotizante] [varchar](50) NULL,[apdEstadoRegistroAporteCotizante] [varchar](40) NULL,[apdPersona] [bigint] NULL,[apdUsuarioAprobadorAporte] [varchar](50) NOT NULL,
						[apdEstadoRegistroAporteArchivo] [varchar](60) NOT NULL,[apdCodSucursal] [varchar](10) NULL,[apdNomSucursal] [varchar](100) NULL,[apdFechaMovimiento] [date] NULL,[apdFechaCreacion] [date] NULL,[apdFormaReconocimientoAporte] [varchar](75) NULL,
						[apdMarcaPeriodo] [varchar](19) NULL,[apdModalidadRecaudoAporte] [varchar](40) NULL,[apdMarcaCalculoCategoria] [bit] NULL,[apdModificadoAportesOK] [bit] NULL,[apdRegistroDetalladoUltimo] bigint)
							update ad set ad.apdDiasCotizados=rdn.redDiasCotizados
							output
							INSERTED.apdId,INSERTED.apdAporteGeneral,INSERTED.apdDiasCotizados,INSERTED.apdHorasLaboradas,INSERTED.apdSalarioBasico,INSERTED.apdValorIBC,INSERTED.apdValorIntMora,INSERTED.apdTarifa,INSERTED.apdAporteObligatorio,
							INSERTED.apdValorSaldoAporte,INSERTED.apdCorrecciones,INSERTED.apdEstadoAporteRecaudo,INSERTED.apdEstadoAporteAjuste,INSERTED.apdEstadoRegistroAporte,INSERTED.apdSalarioIntegral,INSERTED.apdMunicipioLaboral,INSERTED.apdDepartamentoLaboral,
							INSERTED.apdRegistroDetallado,INSERTED.apdTipoCotizante,INSERTED.apdEstadoCotizante,INSERTED.apdEstadoAporteCotizante,INSERTED.apdEstadoRegistroAporteCotizante,INSERTED.apdPersona,INSERTED.apdUsuarioAprobadorAporte,INSERTED.apdEstadoRegistroAporteArchivo,
							INSERTED.apdCodSucursal,INSERTED.apdNomSucursal,INSERTED.apdFechaMovimiento,INSERTED.apdFechaCreacion,INSERTED.apdFormaReconocimientoAporte,INSERTED.apdMarcaPeriodo,INSERTED.apdModalidadRecaudoAporte,INSERTED.apdMarcaCalculoCategoria,
							INSERTED.apdModificadoAportesOK,INSERTED.apdRegistroDetalladoUltimo INTO #AporteDetalladoTemporalN2
							from dbo.AporteDetallado ad 
							inner join dbo.aporteDetalladoRegistroControlN rdn on ad.apdId=rdn.apdId
							where rdn.registroGeneralNuevo=@idRegistroGeneralN

							EXEC USP_UTIL_GET_CrearRevisionActualizar  'com.asopagos.entidades.ccf.aportes.AporteDetallado', NULL, '', 'USUARIO_SISTEMA', @iRevision OUTPUT

							INSERT INTO aud.AporteDetallado_aud(apdId,apdAporteGeneral,apdDiasCotizados,apdHorasLaboradas,apdSalarioBasico,apdValorIBC,apdValorIntMora,apdTarifa,apdAporteObligatorio,apdValorSaldoAporte,apdCorrecciones,apdEstadoAporteRecaudo,apdEstadoAporteAjuste,
							apdEstadoRegistroAporte,apdSalarioIntegral,apdMunicipioLaboral,apdDepartamentoLaboral,apdRegistroDetallado,apdTipoCotizante,apdEstadoCotizante,apdEstadoAporteCotizante,apdEstadoRegistroAporteCotizante,apdPersona,apdUsuarioAprobadorAporte,apdEstadoRegistroAporteArchivo,
							apdCodSucursal,apdNomSucursal,apdFechaMovimiento,apdFechaCreacion,apdFormaReconocimientoAporte,apdMarcaPeriodo,apdModalidadRecaudoAporte,apdMarcaCalculoCategoria,apdModificadoAportesOK,apdRegistroDetalladoUltimo,REV, REVTYPE)
							SELECT apdId,apdAporteGeneral,apdDiasCotizados,apdHorasLaboradas,apdSalarioBasico,apdValorIBC,apdValorIntMora,apdTarifa,apdAporteObligatorio,apdValorSaldoAporte,apdCorrecciones,apdEstadoAporteRecaudo,apdEstadoAporteAjuste,apdEstadoRegistroAporte,apdSalarioIntegral,
							apdMunicipioLaboral,apdDepartamentoLaboral,apdRegistroDetallado,apdTipoCotizante,apdEstadoCotizante,apdEstadoAporteCotizante,apdEstadoRegistroAporteCotizante,apdPersona,apdUsuarioAprobadorAporte,apdEstadoRegistroAporteArchivo,apdCodSucursal,apdNomSucursal,
							apdFechaMovimiento,apdFechaCreacion,apdFormaReconocimientoAporte,apdMarcaPeriodo,apdModalidadRecaudoAporte,apdMarcaCalculoCategoria,apdModificadoAportesOK,apdRegistroDetalladoUltimo,@iRevision, 1 FROM #AporteDetalladoTemporalN2


							end
						
						if (select count(*) from #PlanillaNBuscarAnterior) = (select count(*) from dbo.aporteDetalladoRegistroControlN with (nolock) where registroGeneralNuevo = @idRegistroGeneralN)
							begin
								commit transaction
							end
						else
							begin
								insert dbo.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
								values (dbo.GetLocalDate(),'[USP_CopiarAportesDesdeTemporalPilaPlanillasN] -->> ' + convert(varchar(50), @idRegistroGeneralN) ,N' Se hace rollback de la transacción porque no afecto todos los aportes aporteNuevo -->>>  ' + convert(varchar(50), @idRegistroGeneralN));
								rollback transaction
							end
					end

				--== Se realiza el borrado de las tablas temporales. 
				begin
							EXEC sp_execute_remote PilaReferenceData,
							N'EXEC USP_BorrarDatosTemporalesAportes @idRegistroGeneralN',
				  			N'@idRegistroGeneralN bigint',
 							@idRegistroGeneralN = @idRegistroGeneralN;
				end
	-- ----------------------------------------------------------------------------------------------
		-- ------- INICIA PROCESO CONTROL CATEGORÍAS agregado  11/08/2022
		-- ----------------------------------------------------------------------------------------------
	--IF OBJECT_ID('tempdb.dbo.#AporteDetalladoTemporal', 'U') IS NOT NULL
			if OBJECT_ID('tempdb..#AporteDetalladoTemporalN') IS NOT NULL
			begin
				
				;with salariomax as (--select case when apd.apdSalarioBasico = max(apd.apdSalarioBasico) over (partition by apd.apdPersona, apg.apgEmpresa, apd.apdTipoCotizante) then apd.apdId else null end as apdId
					select apd.apdRegistroDetallado
				from AporteGeneral as apg with (nolock)
				inner join #AporteDetalladoTemporalN as apd on apg.apgId = apd.apdAporteGeneral
				--where apg.apgRegistroGeneral = @idRegistroGeneralN 
				where apg.apgMarcaPeriodo <> 'PERIODO_FUTURO' and apg.apgEstadoRegistroAporteAportante <> 'RELACIONADO'
				and apd.apdSalarioBasico > 0
				)
				select apdRegistroDetallado, ROW_NUMBER() over (order by apdRegistroDetallado) as id
				into #categorias
				from salariomax
				where apdRegistroDetallado is not null

					declare @cont smallInt = 1
					while @cont <= (select count(*) from #categorias)
					begin
					
						declare @idDetallado bigInt = (select apdRegistroDetallado from #categorias where id = @cont)
							execute [dbo].[USP_REP_CalcularCategoriaAportesPila] @idDetallado
							set @cont += 1 
					end
				end
		-- ----------------------------------------------------------------------------------------------
		-- ------- FINALIZA	 PROCESO CONTROL CATEGORÍAS
		-- ----------------------------------------------------------------------------------------------

		begin
					EXEC sp_execute_remote PilaReferenceData,
					N'EXEC USP_BorrarDatosTemporalesAportes @idRegistroGeneralN',
		  			N'@idRegistroGeneralN bigint',
 					@idRegistroGeneralN = @idRegistroGeneralN;
		end

	/*
Estos son los campos que se modifican dentro de una planilla N
Tipo y subtipo de cotizante. 
Novedades. 
Días cotizados. 
Tarifa. 
Aporte. 
ibc
*/

	END TRY
		BEGIN CATCH

			IF (XACT_STATE()) = -1  
				BEGIN  
				    	INSERT RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
						VALUES (dbo.GetLocalDate(),'[USP_CopiarAportesDesdeTemporalPilaPlanillasN] -->> ' + convert(varchar(50), @idRegistroGeneralN) ,ERROR_MESSAGE());
				    ROLLBACK TRANSACTION;  
				END;  
  
			IF (XACT_STATE()) = 1  
			BEGIN  
			    COMMIT TRANSACTION;     
			END;  

	END CATCH

END;