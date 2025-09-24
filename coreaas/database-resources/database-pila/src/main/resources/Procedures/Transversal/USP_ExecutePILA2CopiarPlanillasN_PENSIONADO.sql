-- =============================================
-- Author: Robinson Castillo
-- Create Date: 2023-09-27
-- Description: Este sp filtra las planillas N pensionado, para pasarlas por el flujo normal, creando un nuevo registro a partir de la diferencia entre el registro C y el A
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_ExecutePILA2CopiarPlanillasN_PENSIONADO]
(
	@pipId bigInt,
    @IdTransaccion2 bigInt,
	@bSimulado bit,
	@sUsuarioProceso varchar(50)
)
AS
BEGIN

	SET XACT_ABORT ON
	SET NOCOUNT ON
	SET QUOTED_IDENTIFIER ON

		drop table if exists #planillaNBuscarOriginal_sp
		drop table if exists #TiposId
		drop table if exists #planillaNBuscarCotizanteOriginal
		drop table if exists #planillaOriginalCotizante
		drop table if exists #cotCoincide
		drop table if exists #pilaArchivoI2RegistroFinalC
		drop table if exists #cotCoincide2
		drop table if exists #pilaArchivoI2RegistroFinalCInsertar
		drop table if exists #cotCoincide3
		drop table if exists #cotCoincide4
		drop table if exists #pilaArchivoI2RegistroFinalC1
		drop table if exists #PilaArchivoIRegistro2_final
		drop table if exists #tblFInalInsert
		-- =============================================
		--==== Se agrega tabla para sacar el regitro final del registroC. 
		-- =============================================

		; with coti as (
		select ROW_NUMBER() over (order by ip2.ip2TipoIdPensionado, ip2.ip2IdPensionado) as id,  ip2.*
		from dbo.PilaIndicePlanilla as p with (nolock)
		inner join dbo.PilaArchivoIPRegistro1 as ip1 with (nolock) on p.pipId = ip1.ip1IndicePlanilla
		inner join dbo.PilaArchivoIPRegistro2 as ip2 with (nolock) on ip2.ip2IndicePlanilla = p.pipId
		where p.pipId = @pipId
		and ip2.ip2Correcion = 'A'
		union all
		select ROW_NUMBER() over (order by ip2.ip2TipoIdPensionado, ip2.ip2IdPensionado) as id,   ip2.*
		from dbo.PilaIndicePlanilla as p with (nolock)
		inner join dbo.PilaArchivoIPRegistro1 as ip1 with (nolock) on p.pipId = ip1.ip1IndicePlanilla
		inner join dbo.PilaArchivoIPRegistro2 as ip2 with (nolock) on ip2.ip2IndicePlanilla = p.pipId
		where p.pipId = @pipId
		and ip2.ip2Correcion = 'C'),
		coti2 as (
		select row_number() over (partition by ip2TipoIdPensionado, ip2IdPensionado order by id, ip2Correcion) as id2, 
		*
		from coti
		order by id, ip2TipoIdPensionado, ip2IdPensionado, ip2Correcion
		offset 0 rows fetch next 1000000 rows only),
		coti3 as (
		select
		case when last_value(id2) over (partition by id order by id) = 2 then 'X' else null end as aplicarDiff,
		case when id2 = max(id2) over (partition by id) then id2 else null end ultimoRegistro,
		case when id2 = min(id2) over (partition by id) then id2 else null end primerRegistro,
		*
		from coti2
		order by id, id2, ip2TipoIdPensionado, ip2IdPensionado, ip2Correcion
		offset 0 rows fetch next 1000000 rows only),
		cot4 as (
		select *, 
		case when ultimoRegistro = 2 then LAG(ip2Tarifa) over (partition by id order by id2) else null end as ip2Tarifa_ant,
		case when ultimoRegistro = 2 then LAG(ip2ValorAporte) over (partition by id order by id2) else null end as ip2AporteObligatorio_ant
		from coti3
		where aplicarDiff = 'X')
		select ip2IndicePlanilla,ip2Secuencia,ip2TipoIdPensionado,ip2IdPensionado,ip2PrimerApellido,ip2SegundoApellido,ip2PrimerNombre,ip2SegundoNombre,ip2CodDepartamento,ip2CodMunicipio,ip2Tarifa
		,(ip2ValorAporte - ip2AporteObligatorio_ant) as ip2ValorAporte,ip2ValorMesada,ip2DiasCotizados,ip2NovING,ip2NovRET,ip2NovVSP,ip2NovSUS,ip2FechaIngreso,ip2FechaRetiro,ip2FechaInicioVSP,ip2FechaInicioSuspension,ip2FechaFinSuspension,ip2Id,ip2Correcion
		into #tblFInalInsert
		from cot4
		where ultimoRegistro = 2 and aplicarDiff = 'X'
		

		declare @entra bit = (select case when (ip3ValorTotalAporte = valorAporte) then 1 else 0 end
								from dbo.PilaArchivoIPRegistro3 as a with (nolock)
								inner join (select max(ip2IndicePlanilla) as pipIdPlanilla2, sum(ip2ValorAporte) as valorAporte from #tblFInalInsert) as b on a.ip3IndicePlanilla = b.pipIdPlanilla2)

				
		declare @RegistroGeneralId as table (regId bigInt)
		DECLARE @redDateTimeUpdate DATETIME = dbo.getLocalDate()
		declare @registrosDetallados table (redId bigInt, redIdRegistro2pila bigInt)
		
		select *
		into #TiposId
		from (
		VALUES
		('RC','REGISTRO_CIVIL'),
		('TI','TARJETA_IDENTIDAD'),
		('CC','CEDULA_CIUDADANIA'),
		('CE','CEDULA_EXTRANJERIA'),
		('PA','PASAPORTE'),
		('CD','CARNE_DIPLOMATICO'),
		('NI','NIT'),
		('SC','SALVOCONDUCTO'),
		('PE','PERM_ESP_PERMANENCIA'),
		('PT','PERM_PROT_TEMPORAL')) as t (TipoIdCorto, TipoIdLargo)
			
			select @entra

		begin try

			if (@entra = 1)

			begin
				
				BEGIN TRAN 
					-- Registro general Pensionado
					--INSERT INTO [staging].[RegistroGeneral] (
					 insert into staging.RegistroGenCtrl(
						regTransaccion,regEsAportePensionados,regNombreAportante,regTipoIdentificacionAportante,regNumeroIdentificacionAportante,regPeriodoAporte
						,regCodSucursal,regNomSucursal,regDireccion,regCodCiudad,regCodDepartamento,regTelefono,regFax,regEmail,regFechaMatricula,regNaturalezaJuridica
						,regCantPensionados,regOperadorInformacion,regNumeroCuenta,regValTotalApoObligatorio,regValorIntMora
						,regOUTEstadoArchivo,regFechaActualizacion,regRegistroControl,regNumPlanilla,regEsSimulado,regEstadoEvaluacion,regDigVerAportante
						,regOUTMotivoProcesoManual, regNumPlanillaAsociada, regDiasMora, regFechaPagoAporte, regFormaPresentacion, regCantidadEmpleados
						,regCantidadAfiliados, regTipoPersona, regFechaRecaudo,regCodigoEntidadFinanciera
						,regOUTEnProceso, regCantidadReg2,regDateTimeInsert,regDateTimeUpdate)
					--OUTPUT INSERTED.regId INTO @RegistroGeneralId
					SELECT @IdTransaccion2, 1 AS regEsAportePensionados,ip1NombrePagador,ti.TipoIdLargo,ip1IdPagador,ip1PeriodoAporte
						,ip1CodSucursal,ip1NomSucursal,ap1Direccion,ap1CodCiudad,ap1CodDepartamento,ap1Telefono,ap1Fax,ap1Email,NULL AS ap1FechaMatricula,ap1NaturalezaJuridica
						,ip1CantPensionados,ip1CodOperador,pf5NumeroCuenta,ip3ValorTotalAporte,ip3ValorMora
						,pip.pipEstadoArchivo,ip1FechaActualizacion,ip1IndicePlanilla,ip1NumPlanilla,@bSimulado
						,CASE 
							WHEN @bSimulado = 1 AND ISNULL(pip.pipMotivoProcesoManual, '') = 'ARCHIVO_CORRECCION' 
							THEN 'CORREGIDO' ELSE 'VIGENTE'
						END
						,ip1DigVerPagador
						,pip.pipMotivoProcesoManual,NULL,ip1DiasMora, ip1FechaPago,ip1FormaPresentacion,ip1CantPensionados,ip1CantPensionados,ap1TipoPersona
						,CONVERT(DATE, CAST(pf1.pf1FechaRecaudo AS Varchar(8)), 112),pf1.pf1CodigoEntidadFinanciera
						,1, ip1CantidadReg2,dbo.getLocalDate(),dbo.getLocalDate()
					FROM PilaArchivoIPRegistro1 ip1 
					INNER JOIN PilaIndicePlanilla pip ON ip1.ip1IndicePlanilla = pip.pipId
					INNER JOIN PilaIndicePlanilla pipA ON pip.pipIdPlanilla = pipA.pipIdPlanilla
					INNER JOIN PilaArchivoAPRegistro1 ap1 ON ap1.ap1IndicePlanilla = pipA.pipId
					INNER JOIN PilaArchivoIPRegistro3 ip3 ON ip1.ip1IndicePlanilla = ip3.ip3IndicePlanilla
					INNER JOIN #TiposId ti ON ti.TipoIdCorto = ip1TipoIdPagador
					LEFT JOIN (
								SELECT MAX(pf6Id) pf6Id,pf6NumeroPlanilla,pf6PeriodoPago,CAST(pf6CodOperadorInformacion AS SMALLINT) pf6CodOperadorInformacion
								FROM dbo.PilaArchivoFRegistro6 
								WHERE pf6EstadoConciliacion = 'REGISTRO_6_CONCILIADO'
								GROUP BY pf6IndicePlanillaOF, pf6CodOperadorInformacion,pf6NumeroPlanilla,pf6PeriodoPago
								) pf6 ON pf6.pf6CodOperadorInformacion = ip1.ip1CodOperador 
									AND CAST(pf6.pf6NumeroPlanilla AS BIGINT) = CAST(ip1.ip1NumPlanilla AS BIGINT) 
									AND pf6.pf6PeriodoPago = CONVERT(VARCHAR(6),CONVERT(DATETIME,ip1.ip1PeriodoAporte+'-01'),112)
					LEFT JOIN dbo.PilaArchivoFRegistro6 pf6_1 ON pf6.pf6Id = pf6_1.pf6Id
					LEFT JOIN dbo.PilaArchivoFRegistro5 pf5 ON pf6_1.pf6PilaArchivoFRegistro5 = pf5.pf5Id
					LEFT JOIN dbo.PilaArchivoFRegistro1 pf1 ON pf6_1.pf6IndicePlanillaOF = pf1.pf1IndicePlanillaOF
					WHERE ip1IndicePlanilla IN (@pipId)
					AND ip1IndicePlanilla NOT IN (SELECT regRegistroControl FROM [staging].[RegistroGeneral] WHERE regRegistroControl IS NOT NULL);


					INSERT INTO [staging].[RegistroGeneral] (
						regTransaccion,regEsAportePensionados,regNombreAportante,regTipoIdentificacionAportante,regNumeroIdentificacionAportante,regPeriodoAporte
						,regCodSucursal,regNomSucursal,regDireccion,regCodCiudad,regCodDepartamento,regTelefono,regFax,regEmail,regFechaMatricula,regNaturalezaJuridica
						,regCantPensionados,regOperadorInformacion,regNumeroCuenta,regValTotalApoObligatorio,regValorIntMora
						,regOUTEstadoArchivo,regFechaActualizacion,regRegistroControl,regNumPlanilla,regEsSimulado,regEstadoEvaluacion,regDigVerAportante
						,regOUTMotivoProcesoManual, regNumPlanillaAsociada, regDiasMora, regFechaPagoAporte, regFormaPresentacion, regCantidadEmpleados
						,regCantidadAfiliados, regTipoPersona, regFechaRecaudo,regCodigoEntidadFinanciera
						,regOUTEnProceso, regCantidadReg2,regDateTimeInsert,regDateTimeUpdate)
					OUTPUT INSERTED.regId INTO @RegistroGeneralId
					select 
						regTransaccion,regEsAportePensionados,regNombreAportante,regTipoIdentificacionAportante,regNumeroIdentificacionAportante,regPeriodoAporte
						,regCodSucursal,regNomSucursal,regDireccion,regCodCiudad,regCodDepartamento,regTelefono,regFax,regEmail,regFechaMatricula,regNaturalezaJuridica
						,regCantPensionados,regOperadorInformacion,regNumeroCuenta,regValTotalApoObligatorio,regValorIntMora
						,regOUTEstadoArchivo,regFechaActualizacion,regRegistroControl,regNumPlanilla,regEsSimulado,regEstadoEvaluacion,regDigVerAportante
						,regOUTMotivoProcesoManual, regNumPlanillaAsociada, regDiasMora, regFechaPagoAporte, regFormaPresentacion, regCantidadEmpleados
						,regCantidadAfiliados, regTipoPersona, regFechaRecaudo,regCodigoEntidadFinanciera
						,regOUTEnProceso, regCantidadReg2,regDateTimeInsert,regDateTimeUpdate
					from staging.RegistroGenCtrl
					where regRegistroControl = @pipId

					-- Registro detallado Pensionado
					INSERT INTO [staging].[RegistroDetallado] (
						redRegistroGeneral,redTipoIdentificacionCotizante,redNumeroIdentificacionCotizante,redCodDepartamento
						,redCodMunicipio,redPrimerApellido,redSegundoApellido
						,redPrimerNombre,redSegundoNombre,redTarifa,redAporteObligatorio,redSalarioBasico,redDiasCotizados,redNovIngreso
						,redNovRetiro,redNovVSP,redNovSUS,redFechaIngreso,redFechaRetiro,redFechaInicioVSP,redFechaInicioSuspension,redFechaFinSuspension,redRegistroControl,redEstadoEvaluacion,redUsuarioAprobadorAporte
						,redOUTValorMoraCotizante,redIdRegistro2pila,redOUTRegistroActual,redDateTimeUpdate)
					output inserted.redId, inserted.redIdRegistro2pila into @registrosDetallados
					SELECT regId,ti.TipoIdLargo,ip2IdPensionado,ip2CodDepartamento
						,ip2CodDepartamento+ip2CodMunicipio,ip2PrimerApellido,ip2SegundoApellido
						,ip2PrimerNombre,ip2SegundoNombre,ip2Tarifa,ip2ValorAporte,ip2ValorMesada,ip2DiasCotizados,ip2NovING
						,ip2NovRET,ip2NovVSP,ip2NovSUS,ip2FechaIngreso,ip2FechaRetiro,ip2FechaInicioVSP,ip2FechaInicioSuspension,ip2FechaFinSuspension,ip2Secuencia
						,CASE 
							WHEN @bSimulado = 1 AND ISNULL(pip.pipMotivoProcesoManual, '') = 'ARCHIVO_CORRECCION' 
							THEN 'CORREGIDO' ELSE 'VIGENTE'
						END
						,@sUsuarioProceso, 
						CASE WHEN regValTotalApoObligatorio = 0 THEN 0 ELSE
							(ip2ValorAporte * regValorIntMora) / regValTotalApoObligatorio
						END, ip2Id, 1, @redDateTimeUpdate
					FROM #tblFInalInsert 
					INNER JOIN PilaIndicePlanilla pip ON ip2IndicePlanilla = pipId
					INNER JOIN PilaArchivoIPRegistro1 ON ip2IndicePlanilla = ip1IndicePlanilla
					INNER JOIN staging.RegistroGeneral ON regRegistroControl = ip1IndicePlanilla
					INNER JOIN #TiposId ti ON ti.TipoIdCorto = ip2TipoIdPensionado
					WHERE regId IN (SELECT regId FROM @RegistroGeneralId);
				
				
				insert [staging].[RegistroDetalladoPlanillaN] (redId, redRegistroGeneral, redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante, redTipoCotizante, redCodDepartamento, redCodMunicipio, redPrimerApellido, redSegundoApellido, 
				redPrimerNombre, redSegundoNombre, redNovIngreso, redNovRetiro, redNovVSP, redNovVST, redNovSLN, redNovIGE, redNovLMA, redNovVACLR, redNovSUS, redDiasIRL, redDiasCotizados, redSalarioBasico, 
				redValorIBC, redTarifa, redAporteObligatorio, redCorrecciones, redSalarioIntegral, redFechaIngreso, redFechaRetiro, redFechaInicioVSP, redFechaInicioSLN, redFechaFinSLN, redFechaInicioIGE, 
				redFechaFinIGE, redFechaInicioLMA, redFechaFinLMA, redFechaInicioVACLR, redFechaFinVACLR, redFechaInicioVCT, redFechaFinVCT, redFechaInicioIRL, redFechaFinIRL, redFechaInicioSuspension, 
				redFechaFinSuspension, redHorasLaboradas, redRegistroControl, redOUTMarcaValRegistroAporte, redOUTEstadoRegistroAporte, redOUTAnalisisIntegral, redOUTFechaProcesamientoValidRegAporte, 
				redOUTEstadoValidacionV0, redOUTEstadoValidacionV1, redOUTEstadoValidacionV2, redOUTEstadoValidacionV3, redOUTClaseTrabajador, redOUTPorcentajePagoAportes, redOUTEstadoSolicitante, 
				redOUTEsTrabajadorReintegrable, redOUTFechaIngresoCotizante, redOUTFechaUltimaNovedad, redOUTFechaFallecimiento, redUsuarioAprobadorAporte, redNumeroOperacionAprobacion, redEstadoEvaluacion, 
				redEstadoRegistroCorreccion, redOUTCodSucursal, redOUTNomSucursal, redOUTDiasCotizadosPlanillas, redOUTDiasCotizadosBD, redOUTDiasCotizadosNovedades, redOUTTipoAfiliado, redOUTRegistrado, 
				redOUTValorMoraCotizante, redOUTAporteObligatorioMod, redOUTDiasCotizadosMod, redOUTRegistradoAporte, redOUTRegistradoNovedad, redOUTTipoNovedadSituacionPrimaria, redOUTFechaInicioNovedadSituacionPrimaria, 
				redOUTFechaFinNovedadSituacionPrimaria, redOUTRegDetOriginal, redOUTEstadoRegistroRelacionAporte, redOUTEstadoEvaluacionAporte, redOUTFechaRetiroCotizante, redOUTValorIBCMod, redOUTValorMoraCotizanteMod, 
				redFechaInicioVST, redFechaFinVST, redOUTDiasCotizadosNovedadesBD, redOUTGrupoFamiliarReintegrable, redIdRegistro2pila, redOUTEnviadoAFiscalizacionInd, redOUTMotivoFiscalizacionInd, redOUTRegistroActual, 
				redOUTRegInicial, redOUTGrupoAC, redOUTTarifaMod, redDateTimeInsert, redDateTimeUpdate, redOUTPeriodicidad, redUsuarioAccion, redFechaAccion, redRegistroDetalladoAnterior)
				select rd.redId, rd.redRegistroGeneral, rd.redTipoIdentificacionCotizante, rd.redNumeroIdentificacionCotizante, rd.redTipoCotizante, rd.redCodDepartamento, 
				rd.redCodMunicipio, rd.redPrimerApellido, rd.redSegundoApellido, rd.redPrimerNombre, rd.redSegundoNombre, rd.redNovIngreso, rd.redNovRetiro, rd.redNovVSP, 
				rd.redNovVST, rd.redNovSLN, rd.redNovIGE, rd.redNovLMA, rd.redNovVACLR, rd.redNovSUS, rd.redDiasIRL, rd.redDiasCotizados, rd.redSalarioBasico, rd.redValorIBC, 
				rd.redTarifa, rd.redAporteObligatorio, rd.redCorrecciones, rd.redSalarioIntegral, rd.redFechaIngreso, rd.redFechaRetiro, rd.redFechaInicioVSP, rd.redFechaInicioSLN, 
				rd.redFechaFinSLN, rd.redFechaInicioIGE, rd.redFechaFinIGE, rd.redFechaInicioLMA, rd.redFechaFinLMA, rd.redFechaInicioVACLR, rd.redFechaFinVACLR, rd.redFechaInicioVCT, 
				rd.redFechaFinVCT, rd.redFechaInicioIRL, rd.redFechaFinIRL, rd.redFechaInicioSuspension, rd.redFechaFinSuspension, rd.redHorasLaboradas, rd.redRegistroControl, 
				rd.redOUTMarcaValRegistroAporte, rd.redOUTEstadoRegistroAporte, rd.redOUTAnalisisIntegral, rd.redOUTFechaProcesamientoValidRegAporte, rd.redOUTEstadoValidacionV0, 
				rd.redOUTEstadoValidacionV1, rd.redOUTEstadoValidacionV2, rd.redOUTEstadoValidacionV3, rd.redOUTClaseTrabajador, rd.redOUTPorcentajePagoAportes, rd.redOUTEstadoSolicitante, 
				rd.redOUTEsTrabajadorReintegrable, rd.redOUTFechaIngresoCotizante, rd.redOUTFechaUltimaNovedad, rd.redOUTFechaFallecimiento, rd.redUsuarioAprobadorAporte, 
				rd.redNumeroOperacionAprobacion, rd.redEstadoEvaluacion, rd.redEstadoRegistroCorreccion, rd.redOUTCodSucursal, rd.redOUTNomSucursal, rd.redOUTDiasCotizadosPlanillas, 
				rd.redOUTDiasCotizadosBD, rd.redOUTDiasCotizadosNovedades, rd.redOUTTipoAfiliado, rd.redOUTRegistrado, rd.redOUTValorMoraCotizante, rd.redOUTAporteObligatorioMod, 
				rd.redOUTDiasCotizadosMod, rd.redOUTRegistradoAporte, rd.redOUTRegistradoNovedad, rd.redOUTTipoNovedadSituacionPrimaria, rd.redOUTFechaInicioNovedadSituacionPrimaria, 
				rd.redOUTFechaFinNovedadSituacionPrimaria, rd.redOUTRegDetOriginal, rd.redOUTEstadoRegistroRelacionAporte, rd.redOUTEstadoEvaluacionAporte, rd.redOUTFechaRetiroCotizante, 
				rd.redOUTValorIBCMod, rd.redOUTValorMoraCotizanteMod, rd.redFechaInicioVST, rd.redFechaFinVST, rd.redOUTDiasCotizadosNovedadesBD, rd.redOUTGrupoFamiliarReintegrable, 
				rd.redIdRegistro2pila, rd.redOUTEnviadoAFiscalizacionInd, rd.redOUTMotivoFiscalizacionInd, rd.redOUTRegistroActual, rd.redOUTRegInicial, rd.redOUTGrupoAC, rd.redOUTTarifaMod, 
				rd.redDateTimeInsert, rd.redDateTimeUpdate, rd.redOUTPeriodicidad, rd.redUsuarioAccion, rd.redFechaAccion, 
				--==========================================================================
				-3 as registroDetalladoOriginal --=== Se deja este valor para identificar las planillas N sin original. 
				--==========================================================================
				from staging.RegistroDetallado as rd
				inner join @registrosDetallados as rdInsert on rd.redId = rdInsert.redId and rd.redIdRegistro2pila = rdInsert.redIdRegistro2pila

			COMMIT

		end
			
		end try
		begin catch
			if @@TRANCOUNT > 0  rollback transaction;
			SELECT  
			ERROR_NUMBER() AS ErrorNumber  
			,ERROR_SEVERITY() AS ErrorSeverity  
			,ERROR_STATE() AS ErrorState  
			,ERROR_PROCEDURE() AS ErrorProcedure  
			,ERROR_LINE() AS ErrorLine  
			,ERROR_MESSAGE() AS ErrorMessage; 
		
		end catch;

END;