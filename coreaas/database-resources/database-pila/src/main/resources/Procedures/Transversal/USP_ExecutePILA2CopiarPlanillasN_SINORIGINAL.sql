-- =============================================
-- Author: Robinson Castillo
-- Create Date: 2023-09-23
-- Description: Este sp filtra las planillas N sin original, para pasarlas por el flujo normal, pasando solo los registros C sin marca C. 
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_ExecutePILA2CopiarPlanillasN_SINORIGINAL]
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

				
		begin try


			---==================================================
			---====== Se agregan validaciones para el log de planillas N 2023-11-03
			---==================================================
			declare @idPlanilla bigInt = (select pipIdPlanilla from dbo.PilaIndicePlanilla with(nolock) where pipId = @pipId)
			
			declare @extPilaI1 bit = 0, @extPilaI2 bit = 0, @extPilaI3 bit = 0, @extPilaA1 bit = 0, @pipidArchivoA bigInt, @pipidArchivoI bigInt
			
			select @pipidArchivoI = p.pipId
			from dbo.PilaIndicePlanilla as p with (nolock)
			where p.pipIdPlanilla = @idPlanilla
			and p.pipTipoArchivo like 'ARCHIVO_OI_I%'
			and isnull(p.pipEstadoArchivo,'') <> 'ANULADO' and p.pipUsuario not like '%MIGRACION%'
			
			select @pipidArchivoA = p.pipId
			from dbo.PilaIndicePlanilla as p with (nolock)
			where p.pipIdPlanilla = @idPlanilla
			and p.pipTipoArchivo like 'ARCHIVO_OI_A%'
			and isnull(p.pipEstadoArchivo,'') <> 'ANULADO'
			
			select @extPilaI1 = 1
			from dbo.PilaArchivoIRegistro1 with(nolock)
			where pi1IndicePlanilla = @pipidArchivoI
			
			select top 1 @extPilaI2 = 1
			from dbo.PilaArchivoIRegistro2 with(nolock)
			where pi2IndicePlanilla = @pipidArchivoI
			
			select top 1 @extPilaI3 = 1
			from dbo.PilaArchivoIRegistro3 with(nolock)
			where pi3IndicePlanilla = @pipidArchivoI
			
			
			select top 1 @extPilaA1 = 1
			from dbo.PilaArchivoARegistro1 with(nolock)
			where pa1IndicePlanilla = @pipidArchivoA
			

			declare @cantA int, @cantC int, @totalAporteA numeric(19,5), @totalAporteC numeric(19,5), @totalAporte3 numeric(19,5), @mora numeric(19,5)
			select @cantA = count(*), @totalAporteA = sum(pi2.pi2AporteObligatorio)
			from dbo.PilaArchivoIRegistro2 as pi2 with (nolock)
			where pi2.pi2IndicePlanilla = @pipidArchivoI and pi2Correcciones = 'A'
	
			select @cantC = count(*), @totalAporteC = sum(pi2.pi2AporteObligatorio)
			from dbo.PilaArchivoIRegistro2 as pi2 with (nolock)
			where pi2.pi2IndicePlanilla = @pipidArchivoI and pi2Correcciones = 'C'
	
			select @totalAporte3 = pi3ValorTotalAporteObligatorio, @mora = pi3.pi3ValorMora
			from dbo.PilaArchivoIRegistro3 as pi3 with (nolock)
			where pi3.pi3IndicePlanilla = @pipidArchivoI

			
			
			if @extPilaI1 = 0 or  @extPilaI2 = 0 or @extPilaI3 = 0 or @extPilaA1 = 0
			begin
				if @extPilaA1 = 0
					begin
						insert dbo.logPlanillasN (planillaN, planillaOriginal, fechaProceso, Mensaje, fechaPagoAsociado, periodo)
						select @idPlanilla, null, dbo.GetLocalDate() , N'No existe persistencia en archio A -->> Escalar al equipo de db, N sin original', null, null
					end
				else if @extPilaI3 = 0
					begin
						insert dbo.logPlanillasN (planillaN, planillaOriginal, fechaProceso, Mensaje, fechaPagoAsociado, periodo)
						select @idPlanilla, null, dbo.GetLocalDate() , N'No existe persistencia en archio I3 -->> Escalar al equipo de db, N sin original', null, null
					end		
				else if @extPilaI2 = 0
					begin
						insert dbo.logPlanillasN (planillaN, planillaOriginal, fechaProceso, Mensaje, fechaPagoAsociado, periodo)
						select @idPlanilla, null, dbo.GetLocalDate() , N'No existe persistencia en archio I2 -->> Escalar al equipo de db, N sin original', null, null
					end
				else
					begin
						insert dbo.logPlanillasN (planillaN, planillaOriginal, fechaProceso, Mensaje, fechaPagoAsociado, periodo)
						select @idPlanilla, null, dbo.GetLocalDate() , N'No existe persistencia en archio I1 -->> Escalar al equipo de db, N sin original', null, null
					end
			end
			
			---==================================================
			---====== Finaliza validaciones para el log de planillas N 2023-11-03
			--======= Continúa con las demás validaciones. 
			---==================================================
			else if (@cantA = @cantC)
						begin
							if @totalAporte3 = (@totalAporteC - @totalAporteA)
								begin
									;with RegA as (
									select pi2.pi2Id as pi2IdA, ti.TipoIdLargo as tipoIdA, pi2.pi2IdCotizante as numIdCotA, pi2.pi2AporteObligatorio as aporteAnt, pi2.pi2ValorIBC as IBCant, row_number() over (partition by pi2.pi2TipoIdCotizante, pi2.pi2IdCotizante order by pi2.pi2DiasCotizados, pi2.pi2ValorIBC, pi2.pi2SalarioBasico, pi2.pi2AporteObligatorio) as idA
									from dbo.PilaArchivoIRegistro2 as pi2 with (nolock)
									inner join #TiposId as ti on pi2.pi2TipoIdCotizante = ti.TipoIdCorto
									where pi2.pi2IndicePlanilla = @pipidArchivoI
									and pi2.pi2Correcciones = 'A'),
									regC as (
									select pi2.pi2Id as pi2IdC, ti.TipoIdLargo as tipoIdC, pi2.pi2IdCotizante as numIdCotC, pi2.pi2AporteObligatorio as aporteNue, pi2.pi2ValorIBC as IBCnue, row_number() over (partition by pi2.pi2TipoIdCotizante, pi2.pi2IdCotizante order by pi2.pi2DiasCotizados, pi2.pi2ValorIBC, pi2.pi2SalarioBasico, pi2.pi2AporteObligatorio) as idC
									from dbo.PilaArchivoIRegistro2 as pi2 with (nolock)
									inner join #TiposId as ti on pi2.pi2TipoIdCotizante = ti.TipoIdCorto
									where pi2.pi2IndicePlanilla = @pipidArchivoI
									and pi2.pi2Correcciones = 'C'),
									final as (
									select *, case when a.aporteAnt = c.aporteNue then 0 else 1 end as Modificar, case when a.IBCant = c.IBCnue then 0 else 1 end as ModificarIBC
									from RegA as a
									full join regC as c on a.tipoIdA = c.tipoIdC and a.numIdCotA = c.numIdCotC and a.idA = c.idC)
									select *, case when Modificar = 1 then aporteNue - aporteAnt else 0 end as valParaUpdate,
									case when ModificarIBC = 1 then IBCnue - IBCant else 0 end as valParaUpdateIBC
									into #RegistroParaInsert_1
									from final as a
									
									select pi2IndicePlanilla,pi2Secuencia,pi2TipoIdCotizante,pi2IdCotizante,pi2TipoCotizante,pi2SubTipoCotizante,pi2ExtrangeroNoObligado,pi2ColombianoExterior,pi2CodDepartamento,pi2CodMunicipio,pi2PrimerApellido,pi2SegundoApellido,pi2PrimerNombre,pi2SegundoNombre
									,pi2NovIngreso,pi2NovRetiro,pi2NovVSP,pi2NovVST,pi2NovSLN,pi2NovIGE,pi2NovLMA,pi2NovVACLR,pi2DiasIRL,pi2DiasCotizados,pi2SalarioBasico, a.valParaUpdateIBC as pi2ValorIBC,
									pi2Tarifa, a.valParaUpdate as pi2AporteObligatorio
									,pi2Correcciones,pi2SalarioIntegral,pi2FechaIngreso,pi2FechaRetiro,pi2FechaInicioVSP,pi2FechaInicioSLN,pi2FechaFinSLN,pi2FechaInicioIGE,pi2FechaFinIGE,pi2FechaInicioLMA,pi2FechaFinLMA,pi2FechaInicioVACLR,pi2FechaFinVACLR,pi2FechaInicioVCT
									,pi2FechaFinVCT,pi2FechaInicioIRL,pi2FechaFinIRL,pi2HorasLaboradas,pi2Id
									into #tblFInalInsert
									from #RegistroParaInsert_1 as a
									inner join dbo.PilaArchivoIRegistro2 as pi2 with (nolock) on a.pi2IdC = pi2.pi2Id

										BEGIN TRAN 
											-- Registro general Dependiente/Independiente
											--INSERT INTO [staging].[RegistroGeneral] (
											insert into staging.RegistroGenCtrl(
												regTransaccion,regEsAportePensionados,regNombreAportante,regTipoIdentificacionAportante,regNumeroIdentificacionAportante,
												regDigVerAportante,regPeriodoAporte,regTipoPlanilla,regClaseAportante,regCodSucursal,regNomSucursal,regDireccion,regCodCiudad,
												regCodDepartamento,regTelefono,regFax,regEmail,regFechaMatricula,regNaturalezaJuridica,regModalidadPlanilla,regValTotalApoObligatorio,
												regValorIntMora,regFechaRecaudo,regCodigoEntidadFinanciera,regOperadorInformacion,regNumeroCuenta,regOUTEstadoArchivo,
												regFechaActualizacion,regRegistroControl, regRegistroFControl,regNumPlanilla,regEsSimulado,regEstadoEvaluacion,regOUTMotivoProcesoManual,
												regNumPlanillaAsociada, regDiasMora, regFechaPagoAporte, regFormaPresentacion, regCantidadEmpleados,regCantidadAfiliados, 
												regTipoPersona, regOUTEnProceso, regCantidadReg2, regFechaPagoPlanillaAsociada,regDateTimeInsert,regDateTimeUpdate)
											--OUTPUT INSERTED.regId INTO @RegistroGeneralId
											SELECT @IdTransaccion2, 0 AS regEsAportePensionados,pi1RazonSocial,ti.TipoIdLargo,pi1IdAportante,pi1DigVerAportante
												,pi1PeriodoAporte,pi1TipoPlanilla,pi1ClaseAportante,pi1CodSucursal,pi1NomSucursal,pa1Direccion,pa1CodCiudad
												,pa1CodDepartamento,pa1Telefono,pa1Fax,pa1Email,pa1FechaMatricula,pa1NaturalezaJuridica,pi1ModalidadPlanilla
												,pi3ValorTotalAporteObligatorio,pi3ValorMora,CONVERT(DATE, CAST(pf1.pf1FechaRecaudo AS Varchar(8)), 112)
												,pf1CodigoEntidadFinanciera,pi1CodOperador,pf5NumeroCuenta,pip.pipEstadoArchivo,pi1FechaActualizacion
												,pi1IndicePlanilla,pf1IndicePlanillaOF,pi1NumPlanilla,@bSimulado
												,CASE 
													WHEN @bSimulado = 1 AND ISNULL(pip.pipMotivoProcesoManual, '') = 'ARCHIVO_CORRECCION' 
													THEN 'CORREGIDO' ELSE 'VIGENTE'
												END
												,NULL,pi1NumPlanillaAsociada,pi1DiasMora,pi1FechaPago,pi1Presentacion,pi1CantidadEmpleados,pi1CantidadAfiliados
												,pi1TipoPersona, 1, pi1CantidadReg2, pi1FechaPagoAsociado,dbo.getLocalDate(),dbo.getLocalDate()
											FROM PilaArchivoIRegistro1 pi1 
											INNER JOIN #TiposId ti ON ti.TipoIdCorto = pi1.pi1TipoDocAportante
											INNER JOIN PilaIndicePlanilla pip ON pi1.pi1IndicePlanilla = pip.pipId
											INNER JOIN PilaIndicePlanilla pipA ON (
												pip.pipIdPlanilla = pipA.pipIdPlanilla 
												AND ISNULL(pipA.pipEstadoArchivo, '') <> 'ANULADO'
												AND pip.pipTipoArchivo != pipA.pipTipoArchivo
												AND pip.pipCodigoOperadorInformacion = pipA.pipCodigoOperadorInformacion
											)
											INNER JOIN PilaArchivoARegistro1 pa1 ON pa1.pa1IndicePlanilla = pipA.pipId
											INNER JOIN PilaArchivoIRegistro3 pi3 ON pi1.pi1IndicePlanilla = pi3.pi3IndicePlanilla
											LEFT JOIN (
														SELECT MAX(pf6Id) pf6Id, pf6NumeroPlanilla, pf6PeriodoPago, CAST(pf6CodOperadorInformacion AS SMALLINT) pf6CodOperadorInformacion
														FROM dbo.PilaArchivoFRegistro6
														WHERE pf6EstadoConciliacion = 'REGISTRO_6_CONCILIADO'
														GROUP BY pf6IndicePlanillaOF, pf6CodOperadorInformacion,pf6NumeroPlanilla,pf6PeriodoPago
														) pf6 ON pf6.pf6CodOperadorInformacion = pi1.pi1CodOperador 
															AND CAST(pf6.pf6NumeroPlanilla AS BIGINT) = CAST(pi1.pi1NumPlanilla AS BIGINT) 
															AND pf6.pf6PeriodoPago = CONVERT(VARCHAR(6),CONVERT(DATETIME,pi1.pi1PeriodoAporte+'-01'),112)
											LEFT JOIN dbo.PilaArchivoFRegistro6 pf6_1 ON pf6.pf6Id = pf6_1.pf6Id
											LEFT JOIN dbo.PilaArchivoFRegistro5 pf5 ON pf6_1.pf6PilaArchivoFRegistro5 = pf5.pf5Id
											LEFT JOIN dbo.PilaArchivoFRegistro1 pf1 ON pf6_1.pf6IndicePlanillaOF = pf1.pf1IndicePlanillaOF
											WHERE pi1IndicePlanilla IN (@pipId)
											AND pi1IndicePlanilla NOT IN (SELECT regRegistroControl FROM [staging].[RegistroGeneral] with (nolock) WHERE regRegistroControl IS NOT NULL)
											

											INSERT INTO [staging].[RegistroGeneral] (
												regTransaccion,regEsAportePensionados,regNombreAportante,regTipoIdentificacionAportante,regNumeroIdentificacionAportante,
												regDigVerAportante,regPeriodoAporte,regTipoPlanilla,regClaseAportante,regCodSucursal,regNomSucursal,regDireccion,regCodCiudad,
												regCodDepartamento,regTelefono,regFax,regEmail,regFechaMatricula,regNaturalezaJuridica,regModalidadPlanilla,regValTotalApoObligatorio,
												regValorIntMora,regFechaRecaudo,regCodigoEntidadFinanciera,regOperadorInformacion,regNumeroCuenta,regOUTEstadoArchivo,
												regFechaActualizacion,regRegistroControl, regRegistroFControl,regNumPlanilla,regEsSimulado,regEstadoEvaluacion,regOUTMotivoProcesoManual,
												regNumPlanillaAsociada, regDiasMora, regFechaPagoAporte, regFormaPresentacion, regCantidadEmpleados,regCantidadAfiliados, 
												regTipoPersona, regOUTEnProceso, regCantidadReg2, regFechaPagoPlanillaAsociada,regDateTimeInsert,regDateTimeUpdate)
											OUTPUT INSERTED.regId INTO @RegistroGeneralId
											select 
												regTransaccion,regEsAportePensionados,regNombreAportante,regTipoIdentificacionAportante,regNumeroIdentificacionAportante,
												regDigVerAportante,regPeriodoAporte,regTipoPlanilla,regClaseAportante,regCodSucursal,regNomSucursal,regDireccion,regCodCiudad,
												regCodDepartamento,regTelefono,regFax,regEmail,regFechaMatricula,regNaturalezaJuridica,regModalidadPlanilla,regValTotalApoObligatorio,
												regValorIntMora,regFechaRecaudo,regCodigoEntidadFinanciera,regOperadorInformacion,regNumeroCuenta,regOUTEstadoArchivo,
												regFechaActualizacion,regRegistroControl, regRegistroFControl,regNumPlanilla,regEsSimulado,regEstadoEvaluacion,regOUTMotivoProcesoManual,
												regNumPlanillaAsociada, regDiasMora, regFechaPagoAporte, regFormaPresentacion, regCantidadEmpleados,regCantidadAfiliados, 
												regTipoPersona, regOUTEnProceso, regCantidadReg2, regFechaPagoPlanillaAsociada,regDateTimeInsert,regDateTimeUpdate
											from staging.RegistroGenCtrl
											where regRegistroControl = @pipId


											begin 
											--==========================================================================
												---**** CONTROL PARA EVITAR LOS DUPLICADOS EN EL REGISTROGENERAL
											--==========================================================================
											;with controlDuplicados as (
											select r.*, a.regId as regId2, DENSE_RANK() over (order by r.regId) as total
											from @RegistroGeneralId as a
											inner join staging.RegistroGeneral as r on r.regId = a.regId
											)
											delete from staging.RegistroGeneral where regId in (select controlDuplicados.regId from controlDuplicados where total > 1)
											--==========================================================================
												---**** TERMINAR CONTROL PARA EVITAR LOS DUPLICADOS EN EL REGISTROGENERAL
											--==========================================================================
											end
											
											-- Registro detallado Dependiente/Independiente
											INSERT INTO [staging].[RegistroDetallado] (
												redRegistroGeneral,redTipoIdentificacionCotizante,redNumeroIdentificacionCotizante,redTipoCotizante
												,redCodDepartamento,redCodMunicipio,redPrimerApellido,redSegundoApellido,redPrimerNombre,redSegundoNombre,redNovIngreso,redNovRetiro,redNovVSP
												,redNovVST,redNovSLN,redNovIGE,redNovLMA,redNovVACLR,redDiasIRL,redDiasCotizados,redSalarioBasico,redValorIBC,redTarifa,redAporteObligatorio
												,redCorrecciones,redSalarioIntegral,redFechaIngreso,redFechaRetiro,redFechaInicioVSP,redFechaInicioSLN,redFechaFinSLN,redFechaInicioIGE
												,redFechaFinIGE,redFechaInicioLMA,redFechaFinLMA,redFechaInicioVACLR,redFechaFinVACLR,redFechaInicioVCT,redFechaFinVCT,redFechaInicioIRL
												,redFechaFinIRL,redHorasLaboradas,redRegistroControl,redEstadoEvaluacion,redUsuarioAprobadorAporte,redOUTValorMoraCotizante,redIdRegistro2pila
												,redOUTRegistroActual,redDateTimeUpdate,redUsuarioAccion)
											output inserted.redId, inserted.redIdRegistro2pila into @registrosDetallados
											SELECT regId,ti.TipoIdLargo,pi2IdCotizante,pi2TipoCotizante,pi2CodDepartamento
												,pi2CodDepartamento+pi2CodMunicipio,pi2PrimerApellido,pi2SegundoApellido,pi2PrimerNombre,pi2SegundoNombre,pi2NovIngreso,pi2NovRetiro,pi2NovVSP,pi2NovVST
												,pi2NovSLN,pi2NovIGE,pi2NovLMA,pi2NovVACLR,pi2DiasIRL,pi2DiasCotizados,pi2SalarioBasico,pi2ValorIBC,pi2Tarifa,pi2AporteObligatorio
												,'S' as pi2Correcciones,pi2SalarioIntegral,pi2FechaIngreso,pi2FechaRetiro,pi2FechaInicioVSP,pi2FechaInicioSLN,pi2FechaFinSLN,pi2FechaInicioIGE
												,pi2FechaFinIGE,pi2FechaInicioLMA,pi2FechaFinLMA,pi2FechaInicioVACLR,pi2FechaFinVACLR,pi2FechaInicioVCT,pi2FechaFinVCT,pi2FechaInicioIRL
												,pi2FechaFinIRL,pi2HorasLaboradas,pi2Secuencia
												,CASE 
													WHEN @bSimulado = 1 AND ISNULL(pip.pipMotivoProcesoManual, '') = 'ARCHIVO_CORRECCION' 
													THEN 'CORREGIDO' ELSE 'VIGENTE'
												END
												,@sUsuarioProceso
												,CASE WHEN regValTotalApoObligatorio = 0 THEN 0 ELSE
													(pi2AporteObligatorio * regValorIntMora) / regValTotalApoObligatorio
												END, pi2Id, 1, @redDateTimeUpdate,'@asopagos_TI'
											FROM 
											--==========================================================================
												---**** CONTROL PARA EVITAR DUPLICIDAD QUE SE PUEDE DAR EN EL REGISTRO 2
											--==========================================================================
											#tblFInalInsert as PilaArchivoIRegistro2
											--==========================================================================
											---**** FINALIZA CONTROL PARA EVITAR DUPLICIDAD QUE SE PUEDE DAR EN EL REGISTRO 2
											--==========================================================================
											INNER JOIN PilaIndicePlanilla pip ON pi2IndicePlanilla = pipId
											INNER JOIN PilaArchivoIRegistro1 ON PilaArchivoIRegistro2.pi2IndicePlanilla = PilaArchivoIRegistro1.pi1IndicePlanilla 		
											INNER JOIN staging.RegistroGeneral ON regRegistroControl = pi1IndicePlanilla
											INNER JOIN #TiposId ti ON ti.TipoIdCorto = pi2TipoIdCotizante
											WHERE regId IN (SELECT min(regId) FROM @RegistroGeneralId)
											
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
											-2 as registroDetalladoOriginal --=== Se deja este valor para identificar las planillas N sin original. 
											--==========================================================================
											from staging.RegistroDetallado as rd
											inner join @registrosDetallados as rdInsert on rd.redId = rdInsert.redId and rd.redIdRegistro2pila = rdInsert.redIdRegistro2pila

										COMMIT TRAN

								end
							else 
								begin
									insert dbo.logPlanillasN (planillaN, planillaOriginal, fechaProceso, Mensaje, fechaPagoAsociado, periodo)
									select @idPlanilla, null, dbo.GetLocalDate() , N'Hay diferencia en el total del registro 3 y el valor de la planilla N sin original', null, null				
								end
						end
					else
						begin
--insert dbo.logPlanillasN (planillaN, planillaOriginal, fechaProceso, Mensaje, fechaPagoAsociado, periodo)
							--select @idPlanilla, null, dbo.GetLocalDate() , N'Cantidad de registros entre el A y el C no es el mismo, para planilla N sin original, escalar a DB Asopagos.', null, null
								--drop table #RegistroParaInsert_2

								---- validacion si los registros son mas A o C o viceversa sbayona
								;with RegA as (
									select max(pi2.pi2Id) as pi2IdA,max(ti.TipoIdLargo) as tipoIdA, pi2.pi2IdCotizante as numIdCotA, sum(pi2.pi2AporteObligatorio) as aporteAnt, 
									sum(pi2.pi2ValorIBC) as IBCant,sum(pi2.pi2diascotizados)as diascotizadosA,max(pi2.pi2SalarioBasico) as salariobA, 
									max(pi2HorasLaboradas)as horasA
									from dbo.PilaArchivoIRegistro2 as pi2
									inner join #TiposId as ti on pi2.pi2TipoIdCotizante = ti.TipoIdCorto
									where pi2.pi2IndicePlanilla = @pipidArchivoI
									and pi2.pi2Correcciones = 'A'
									group by pi2.pi2IdCotizante
									),
									regC as (
									select max(pi2.pi2Id) as pi2IdC, max(ti.TipoIdLargo) as tipoIdC, pi2.pi2IdCotizante as numIdCotC,sum(pi2.pi2AporteObligatorio) as aporteNue,
									sum(pi2.pi2ValorIBC) as IBCnue,sum(pi2.pi2diascotizados)as diascotizadosC,max(pi2.pi2SalarioBasico) as salariobC, 
									max(pi2HorasLaboradas)as horasC
									from dbo.PilaArchivoIRegistro2 as pi2
									inner join #TiposId as ti on pi2.pi2TipoIdCotizante = ti.TipoIdCorto
									where pi2.pi2IndicePlanilla = @pipidArchivoI
									and pi2.pi2Correcciones = 'C'
									group by pi2.pi2IdCotizante
									),
									final as (
									select *, case when a.aporteAnt = c.aporteNue then 0 else 1 end as Modificar
									from RegA as a
									full join regC as c on a.tipoIdA = c.tipoIdC and a.numIdCotA = c.numIdCotC)
									select *,  aporteNue - aporteAnt  as valParaUpdate,IBCnue - IBCant  as valParaUpdateIBC,
									sum(case when Modificar = 1 then aporteNue - aporteAnt else 0 end) over (partition by Modificar) as EstaValDebeSerIgualAlMensaje2--, @regId as registroGeneral
									into #regValidar1
									from final

							
									select val.pi2IdA, val.pi2IdC, rd.redId as registroDetalladoOriginal, --rd.redRegistroGeneral, marcacion para N sin original con + A que C
									val.Modificar, val.valParaUpdate, val.valParaUpdateIBC,diascotizadosC,salariobC
									into #regValidar2Final
									from #regValidar1 as val
									inner join dbo.PilaArchivoIRegistro2 as pi2 on val.pi2IdA = pi2.pi2Id
									left join staging.RegistroDetallado as rd on val.tipoIdA = rd.redTipoIdentificacionCotizante and val.numIdCotA = rd.redNumeroIdentificacionCotizante --and val.registroGeneral = rd.redRegistroGeneral --and val.aporteAnt = rd.redAporteObligatorio 
									and isnull(upper(rtrim(ltrim(rd.redNovIngreso))),'') = isnull(upper(rtrim(ltrim(pi2.pi2NovIngreso))),'') and 
									isnull(upper(rtrim(ltrim(rd.redNovRetiro))),'') = isnull(upper(rtrim(ltrim(pi2.pi2NovRetiro))),'') and isnull(upper(rtrim(ltrim(rd.redNovVSP))),'') = isnull(upper(rtrim(ltrim(pi2.pi2NovVSP))),'') and isnull(upper(rtrim(ltrim(rd.redNovVST))),'') = isnull(upper(rtrim(ltrim(pi2.pi2NovVST))),'') and 
									isnull(upper(rtrim(ltrim(rd.redNovSLN))),'') = isnull(upper(rtrim(ltrim(pi2.pi2NovSLN))),'') and isnull(upper(rtrim(ltrim(rd.redNovIGE))),'') = isnull(upper(rtrim(ltrim(pi2.pi2NovIGE))),'') and isnull(upper(rtrim(ltrim(rd.redNovLMA))),'') = isnull(upper(rtrim(ltrim(pi2.pi2NovLMA))),'') and 
									isnull(upper(rtrim(ltrim(rd.redNovVACLR))),'') = isnull(upper(rtrim(ltrim(pi2.pi2NovVACLR))),'') and convert(smallint,isnull(upper(rtrim(ltrim(rd.redDiasIRL))),'0')) = convert(smallint,isnull(upper(rtrim(ltrim(pi2.pi2DiasIRL))),'0')) 
									and isnull(upper(rtrim(ltrim(rd.redDiasCotizados))),'') = isnull(upper(rtrim(ltrim(pi2.pi2DiasCotizados))),'') and rd.redSalarioBasico = pi2.pi2SalarioBasico and rd.redValorIBC = pi2.pi2ValorIBC and rd.redTarifa = pi2.pi2Tarifa and rd.redAporteObligatorio = pi2.pi2AporteObligatorio 
									and isnull(upper(rtrim(ltrim(rd.redFechaIngreso))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaIngreso))),'') and isnull(upper(rtrim(ltrim(rd.redFechaRetiro))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaRetiro))),'') and 
									isnull(upper(rtrim(ltrim(rd.redFechaInicioVSP))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaInicioVSP))),'') and isnull(upper(rtrim(ltrim(rd.redFechaInicioSLN))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaInicioSLN))),'') and 
									isnull(upper(rtrim(ltrim(rd.redFechaFinSLN))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaFinSLN))),'') and isnull(upper(rtrim(ltrim(rd.redFechaInicioIGE))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaInicioIGE))),'') and 
									isnull(upper(rtrim(ltrim(rd.redFechaFinIGE))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaFinIGE))),'') and isnull(upper(rtrim(ltrim(rd.redFechaInicioLMA))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaInicioLMA))),'') and 
									isnull(upper(rtrim(ltrim(rd.redFechaFinLMA))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaFinLMA))),'') and isnull(upper(rtrim(ltrim(rd.redFechaInicioVACLR))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaInicioVACLR))),'') and 
									isnull(upper(rtrim(ltrim(rd.redFechaFinVACLR))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaFinVACLR))),'') and isnull(upper(rtrim(ltrim(rd.redFechaInicioVCT))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaInicioVCT))),'') and 
									isnull(upper(rtrim(ltrim(rd.redFechaFinVCT))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaFinVCT))),'') and isnull(upper(rtrim(ltrim(rd.redFechaInicioIRL))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaInicioIRL))),'') and 
									isnull(upper(rtrim(ltrim(rd.redFechaFinIRL))),'') = isnull(upper(rtrim(ltrim(pi2.pi2FechaFinIRL))),'')

										select pi2IndicePlanilla,pi2Secuencia,pi2TipoIdCotizante,pi2IdCotizante,pi2TipoCotizante,pi2SubTipoCotizante,pi2ExtrangeroNoObligado,
										pi2ColombianoExterior,pi2CodDepartamento,pi2CodMunicipio,pi2PrimerApellido,pi2SegundoApellido,pi2PrimerNombre,pi2SegundoNombre,
										pi2NovIngreso,pi2NovRetiro,pi2NovVSP,pi2NovVST,pi2NovSLN, pi2NovIGE,pi2NovLMA,pi2NovVACLR,pi2DiasIRL,case when diascotizadosC>30 then 30 else diascotizadosC end as pi2DiasCotizados,
										salariobC as pi2SalarioBasico,pi2ValorIBC,pi2Tarifa,pi2AporteObligatorio,pi2Correcciones,pi2SalarioIntegral,pi2FechaIngreso,pi2FechaRetiro,
										pi2FechaInicioVSP,pi2FechaInicioSLN,pi2FechaFinSLN,pi2FechaInicioIGE,pi2FechaFinIGE,pi2FechaInicioLMA,pi2FechaFinLMA,pi2FechaInicioVACLR,
										pi2FechaFinVACLR,pi2FechaInicioVCT,pi2FechaFinVCT,pi2FechaInicioIRL,pi2FechaFinIRL,pi2HorasLaboradas,pi2Id
                                        ,val2.registroDetalladoOriginal, val2.Modificar, val2.valParaUpdate, val2.valParaUpdateIBC
											into #PilaArchivoIRegistroC_1
											from #regValidar2Final as val2
											inner join dbo.PilaArchivoIRegistro2 as pi2 on val2.pi2IdC = pi2.pi2Id
	                        
							   BEGIN TRAN

										insert into staging.RegistroGenCtrl(
														regTransaccion,regEsAportePensionados,regNombreAportante,regTipoIdentificacionAportante,regNumeroIdentificacionAportante,
														regDigVerAportante,regPeriodoAporte,regTipoPlanilla,regClaseAportante,regCodSucursal,regNomSucursal,regDireccion,regCodCiudad,
														regCodDepartamento,regTelefono,regFax,regEmail,regFechaMatricula,regNaturalezaJuridica,regModalidadPlanilla,regValTotalApoObligatorio,
														regValorIntMora,regFechaRecaudo,regCodigoEntidadFinanciera,regOperadorInformacion,regNumeroCuenta,regOUTEstadoArchivo,
														regFechaActualizacion,regRegistroControl, regRegistroFControl,regNumPlanilla,regEsSimulado,regEstadoEvaluacion,regOUTMotivoProcesoManual,
														regNumPlanillaAsociada, regDiasMora, regFechaPagoAporte, regFormaPresentacion, regCantidadEmpleados,regCantidadAfiliados, 
														regTipoPersona, regOUTEnProceso, regCantidadReg2, regFechaPagoPlanillaAsociada,regDateTimeInsert,regDateTimeUpdate)
													--OUTPUT INSERTED.regId INTO @RegistroGeneralId
													SELECT @IdTransaccion2, 0 AS regEsAportePensionados,pi1RazonSocial,ti.TipoIdLargo,pi1IdAportante,pi1DigVerAportante
														,pi1PeriodoAporte,pi1TipoPlanilla,pi1ClaseAportante,pi1CodSucursal,pi1NomSucursal,pa1Direccion,pa1CodCiudad
														,pa1CodDepartamento,pa1Telefono,pa1Fax,pa1Email,pa1FechaMatricula,pa1NaturalezaJuridica,pi1ModalidadPlanilla
														,pi3ValorTotalAporteObligatorio,pi3ValorMora,CONVERT(DATE, CAST(pf1.pf1FechaRecaudo AS Varchar(8)), 112)
														,pf1CodigoEntidadFinanciera,pi1CodOperador,pf5NumeroCuenta,pip.pipEstadoArchivo,pi1FechaActualizacion
														,pi1IndicePlanilla,pf1IndicePlanillaOF,pi1NumPlanilla,@bSimulado
														,CASE 
															WHEN @bSimulado = 1 AND ISNULL(pip.pipMotivoProcesoManual, '') = 'ARCHIVO_CORRECCION' 
															THEN 'CORREGIDO' ELSE 'VIGENTE'
														END
														,pip.pipMotivoProcesoManual,pi1NumPlanillaAsociada,pi1DiasMora,pi1FechaPago,pi1Presentacion,pi1CantidadEmpleados,pi1CantidadAfiliados
														,pi1TipoPersona, 1, pi1CantidadReg2, pi1FechaPagoAsociado,dbo.getLocalDate(),dbo.getLocalDate()
													FROM PilaArchivoIRegistro1 pi1 
													INNER JOIN #TiposId ti ON ti.TipoIdCorto = pi1.pi1TipoDocAportante
													INNER JOIN PilaIndicePlanilla pip ON pi1.pi1IndicePlanilla = pip.pipId
													INNER JOIN PilaIndicePlanilla pipA ON (
														pip.pipIdPlanilla = pipA.pipIdPlanilla 
														AND ISNULL(pipA.pipEstadoArchivo, '') <> 'ANULADO'
														AND pip.pipTipoArchivo != pipA.pipTipoArchivo
														AND pip.pipCodigoOperadorInformacion = pipA.pipCodigoOperadorInformacion
													)
													INNER JOIN PilaArchivoARegistro1 pa1 ON pa1.pa1IndicePlanilla = pipA.pipId
													INNER JOIN PilaArchivoIRegistro3 pi3 ON pi1.pi1IndicePlanilla = pi3.pi3IndicePlanilla
													LEFT JOIN (
																SELECT MAX(pf6Id) pf6Id, pf6NumeroPlanilla, pf6PeriodoPago, CAST(pf6CodOperadorInformacion AS SMALLINT) pf6CodOperadorInformacion
																FROM dbo.PilaArchivoFRegistro6
																WHERE pf6EstadoConciliacion = 'REGISTRO_6_CONCILIADO'
																GROUP BY pf6IndicePlanillaOF, pf6CodOperadorInformacion,pf6NumeroPlanilla,pf6PeriodoPago
																) pf6 ON pf6.pf6CodOperadorInformacion = pi1.pi1CodOperador 
																	AND CAST(pf6.pf6NumeroPlanilla AS BIGINT) = CAST(pi1.pi1NumPlanilla AS BIGINT) 
																	AND pf6.pf6PeriodoPago = CONVERT(VARCHAR(6),CONVERT(DATETIME,pi1.pi1PeriodoAporte+'-01'),112)
													LEFT JOIN dbo.PilaArchivoFRegistro6 pf6_1 ON pf6.pf6Id = pf6_1.pf6Id
													LEFT JOIN dbo.PilaArchivoFRegistro5 pf5 ON pf6_1.pf6PilaArchivoFRegistro5 = pf5.pf5Id
													LEFT JOIN dbo.PilaArchivoFRegistro1 pf1 ON pf6_1.pf6IndicePlanillaOF = pf1.pf1IndicePlanillaOF
													WHERE pi1IndicePlanilla IN (@pipId)
													AND pi1IndicePlanilla NOT IN (SELECT regRegistroControl FROM [staging].[RegistroGeneral] with (nolock) WHERE regRegistroControl IS NOT NULL)

													INSERT INTO [staging].[RegistroGeneral] (
														regTransaccion,regEsAportePensionados,regNombreAportante,regTipoIdentificacionAportante,regNumeroIdentificacionAportante,
														regDigVerAportante,regPeriodoAporte,regTipoPlanilla,regClaseAportante,regCodSucursal,regNomSucursal,regDireccion,regCodCiudad,
														regCodDepartamento,regTelefono,regFax,regEmail,regFechaMatricula,regNaturalezaJuridica,regModalidadPlanilla,regValTotalApoObligatorio,
														regValorIntMora,regFechaRecaudo,regCodigoEntidadFinanciera,regOperadorInformacion,regNumeroCuenta,regOUTEstadoArchivo,
														regFechaActualizacion,regRegistroControl, regRegistroFControl,regNumPlanilla,regEsSimulado,regEstadoEvaluacion,regOUTMotivoProcesoManual,
														regNumPlanillaAsociada, regDiasMora, regFechaPagoAporte, regFormaPresentacion, regCantidadEmpleados,regCantidadAfiliados, 
														regTipoPersona, regOUTEnProceso, regCantidadReg2, regFechaPagoPlanillaAsociada,regDateTimeInsert,regDateTimeUpdate)
													OUTPUT INSERTED.regId INTO @RegistroGeneralId
													select 
														regTransaccion,regEsAportePensionados,regNombreAportante,regTipoIdentificacionAportante,regNumeroIdentificacionAportante,
														regDigVerAportante,regPeriodoAporte,regTipoPlanilla,regClaseAportante,regCodSucursal,regNomSucursal,regDireccion,regCodCiudad,
														regCodDepartamento,regTelefono,regFax,regEmail,regFechaMatricula,regNaturalezaJuridica,regModalidadPlanilla,regValTotalApoObligatorio,
														regValorIntMora,regFechaRecaudo,regCodigoEntidadFinanciera,regOperadorInformacion,regNumeroCuenta,regOUTEstadoArchivo,
														regFechaActualizacion,regRegistroControl, regRegistroFControl,regNumPlanilla,regEsSimulado,regEstadoEvaluacion,regOUTMotivoProcesoManual,
														regNumPlanillaAsociada, regDiasMora, regFechaPagoAporte, regFormaPresentacion, regCantidadEmpleados,regCantidadAfiliados, 
														regTipoPersona, regOUTEnProceso, regCantidadReg2, regFechaPagoPlanillaAsociada,regDateTimeInsert,regDateTimeUpdate
													from staging.RegistroGenCtrl
													where regRegistroControl = @pipId


													begin 
														---////////*****************************
														---**** CONTROL PARA EVITAR LOS DUPLICADOS EN EL REGISTROGENERAL
													;with controlDuplicados as (
													select r.*, a.regId as regId2, DENSE_RANK() over (order by r.regId) as total
													from @RegistroGeneralId as a
													inner join staging.RegistroGeneral as r on r.regId = a.regId
													)
													delete from staging.RegistroGeneral where regId in (select controlDuplicados.regId from controlDuplicados where total > 1)
														---////////*****************************
														---**** TERMINAR CONTROL PARA EVITAR LOS DUPLICADOS EN EL REGISTROGENERAL
													end
	
													-- Registro detallado Dependiente/Independiente
													INSERT INTO [staging].[RegistroDetallado] (
														redRegistroGeneral,redTipoIdentificacionCotizante,redNumeroIdentificacionCotizante,redTipoCotizante
														,redCodDepartamento,redCodMunicipio,redPrimerApellido,redSegundoApellido,redPrimerNombre,redSegundoNombre,redNovIngreso,redNovRetiro,redNovVSP
														,redNovVST,redNovSLN,redNovIGE,redNovLMA,redNovVACLR,redDiasIRL,redDiasCotizados,redSalarioBasico,redValorIBC,redTarifa,redAporteObligatorio
														,redCorrecciones,redSalarioIntegral,redFechaIngreso,redFechaRetiro,redFechaInicioVSP,redFechaInicioSLN,redFechaFinSLN,redFechaInicioIGE
														,redFechaFinIGE,redFechaInicioLMA,redFechaFinLMA,redFechaInicioVACLR,redFechaFinVACLR,redFechaInicioVCT,redFechaFinVCT,redFechaInicioIRL
														,redFechaFinIRL,redHorasLaboradas,redRegistroControl,redEstadoEvaluacion,redUsuarioAprobadorAporte,redOUTValorMoraCotizante,redIdRegistro2pila
														,redOUTRegistroActual,redDateTimeUpdate,redUsuarioAccion)
													output inserted.redId, inserted.redIdRegistro2pila into @registrosDetallados
													SELECT regId,ti.TipoIdLargo,pi2.pi2IdCotizante,pi2.pi2TipoCotizante,pi2CodDepartamento
														,pi2CodDepartamento+pi2CodMunicipio,pi2.pi2PrimerApellido,pi2.pi2SegundoApellido,pi2.pi2PrimerNombre,pi2.pi2SegundoNombre,pi2.pi2NovIngreso,pi2.pi2NovRetiro,
														pi2.pi2NovVSP,pi2.pi2NovVST,pi2.pi2NovSLN,pi2.pi2NovIGE,pi2.pi2NovLMA,pi2.pi2NovVACLR,pi2.pi2DiasIRL,pi2.pi2DiasCotizados,pi2.pi2SalarioBasico,
														--pi2.pi2ValorIBC, 
														pi2.valParaUpdateIBC, --== Se deja como ibc la diferencia, cuando hay cambio en el valor del aporte -- 2024-04-15
														pi2.pi2Tarifa,
														--pi2.pi2AporteObligatorio--
														pi2.valParaUpdate --== Se deja como aporte la diferencia para update -- 2024-04-15
														,'S',pi2.pi2SalarioIntegral,pi2.pi2FechaIngreso,pi2.pi2FechaRetiro,pi2.pi2FechaInicioVSP,
														pi2.pi2FechaInicioSLN,pi2.pi2FechaFinSLN,pi2.pi2FechaInicioIGE,pi2.pi2FechaFinIGE,pi2.pi2FechaInicioLMA,pi2.pi2FechaFinLMA,pi2.pi2FechaInicioVACLR,
														pi2.pi2FechaFinVACLR,pi2.pi2FechaInicioVCT,pi2.pi2FechaFinVCT,pi2.pi2FechaInicioIRL,pi2.pi2FechaFinIRL,pi2.pi2HorasLaboradas,pi2.pi2Secuencia
														,CASE 
															WHEN @bSimulado = 1 AND ISNULL(pip.pipMotivoProcesoManual, '') = 'ARCHIVO_CORRECCION' 
															THEN 'CORREGIDO' ELSE 'VIGENTE'
														END
														,@sUsuarioProceso
														,CASE WHEN regValTotalApoObligatorio = 0 THEN 0 ELSE
															(pi2.valParaUpdate * regValorIntMora) / regValTotalApoObligatorio
														END, pi2.pi2Id, 1, @redDateTimeUpdate,'@asopagos_TI'
													from #PilaArchivoIRegistroC_1 as pi2
													INNER JOIN PilaIndicePlanilla pip ON pi2IndicePlanilla = pipId
													INNER JOIN PilaArchivoIRegistro1 ON pi2IndicePlanilla = PilaArchivoIRegistro1.pi1IndicePlanilla 		
													INNER JOIN staging.RegistroGeneral ON regRegistroControl = pi1IndicePlanilla
													INNER JOIN #TiposId ti ON ti.TipoIdCorto = pi2.pi2TipoIdCotizante
													WHERE regId IN (SELECT min(regId) FROM @RegistroGeneralId)
	
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
													redOUTRegInicial, redOUTGrupoAC, redOUTTarifaMod, redDateTimeInsert, redDateTimeUpdate, redOUTPeriodicidad, redUsuarioAccion, redFechaAccion, redRegistroDetalladoAnterior, actualizarApd)
													select rd.redId, rd.redRegistroGeneral, rd.redTipoIdentificacionCotizante, rd.redNumeroIdentificacionCotizante, rd.redTipoCotizante, rd.redCodDepartamento, 
													rd.redCodMunicipio, rd.redPrimerApellido, rd.redSegundoApellido, rd.redPrimerNombre, rd.redSegundoNombre, rd.redNovIngreso, rd.redNovRetiro, rd.redNovVSP, 
													rd.redNovVST, rd.redNovSLN, rd.redNovIGE, rd.redNovLMA, rd.redNovVACLR, rd.redNovSUS, rd.redDiasIRL, rd.redDiasCotizados, rd.redSalarioBasico, 
													c.valParaUpdateIBC, --rd.redValorIBC, --== Se dejan las diferencias en el detalle del control N, para ajustarlo en core ya que es el valor que se calcula, para las N de N, 2024-04-15
													rd.redTarifa, 
													c.valParaUpdate, --rd.redAporteObligatorio, --== Se dejan las diferencias en el detalle del control N, para ajustarlo en core ya que es el valor que se calcula, para las N de N, 2024-04-15 
													rd.redCorrecciones, rd.redSalarioIntegral, rd.redFechaIngreso, rd.redFechaRetiro, rd.redFechaInicioVSP, rd.redFechaInicioSLN, 
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
													rd.redDateTimeInsert, rd.redDateTimeUpdate, rd.redOUTPeriodicidad, rd.redUsuarioAccion, rd.redFechaAccion, --c.registroDetalladoOriginal,
                                                    -2 as registroDetalladoOriginal --=== Se deja este valor para identificar las planillas N sin original. 													
													,case when (c.valParaUpdate + rd.redOUTValorMoraCotizante) > 0 then 1 else 0 end as actualizar
													from staging.RegistroDetallado as rd
													inner join @registrosDetallados as rdInsert on rd.redId = rdInsert.redId and rd.redIdRegistro2pila = rdInsert.redIdRegistro2pila
													inner join #PilaArchivoIRegistroC_1 as c on c.pi2Id = rd.redIdRegistro2pila

											COMMIT TRAN
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