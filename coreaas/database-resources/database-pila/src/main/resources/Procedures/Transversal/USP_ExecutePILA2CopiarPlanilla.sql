-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2017/06/16
-- Update:      Juan Diego Ocampos Q
-- Update date: 2020/09/15
-- Description:	Procedimiento almacenado encargado marcar la planilla como "_MANUAL" (HU-410) 
--              de crear el registro correspondiente en [staging].[Transaccion] y de crear
--              los datos en las tabla se registro general y detallado HU395, HU396 y HU480
-- Note:        Los aportes manuales que se crean por pantalla tienen la responsabilidad de 
--              crear el registro general y detallado pues no tienen una planilla asociada.
-- Nota:   Se agrega ajuste para control de duplicados en las planillas, no se dejan lo with no lock, para garantizar las transacciones. 
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_ExecutePILA2CopiarPlanilla]
	@iIdIndicePlanilla BIGINT,
    @sFase VARCHAR(100) = 'SIN_PARAMETRO',
	@bSimulado BIT = 0,
	@sUsuarioProceso VARCHAR(50) = 'SISTEMA',
	@bBorrarBloqueStaging BIT = 0, -- SIN USO
	@iIdentificadorPaquete BIGINT = 0, -- SIN USO
	@IdTransaccion BIGINT OUTPUT
AS
BEGIN
SET NOCOUNT ON;	
DECLARE @ErrorMessage NVARCHAR(4000);
DECLARE @ErrorSeverity INT;
DECLARE @ErrorState INT;

--SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
BEGIN TRY

	BEGIN TRAN
		INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
		VALUES (dbo.GetLocalDate(), 
				'USP_ExecutePILA2CopiarPlanilla |' +
				'@iIdIndicePlanilla = ' + ISNULL(CAST(@iIdIndicePlanilla AS VARCHAR), 'NULL') + 
				', @sFase = ' + ISNULL(CAST(@sFase AS VARCHAR), 'NULL') + 
				', @bSimulado = ' + ISNULL(CAST(@bSimulado AS VARCHAR(1)), 'NULL') + 
				', @sUsuarioProceso = ' + ISNULL(CAST(@sUsuarioProceso AS VARCHAR), 'NULL') + 
				', @IdTransaccion = ' + ISNULL(CAST(@IdTransaccion AS VARCHAR), 'NULL'),
				'USP_ExecutePILA2CopiarPlanilla INICIO');
	COMMIT;

	IF ISNULL(@iIdIndicePlanilla, 0) = 0
	BEGIN		
		BEGIN TRAN
			INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
			VALUES (dbo.GetLocalDate(), 
					'USP_ExecutePILA2CopiarPlanilla |' +
					'@iIdIndicePlanilla = ' + ISNULL(CAST(@iIdIndicePlanilla AS VARCHAR), 'NULL') + 
					', @sFase = ' + ISNULL(CAST(@sFase AS VARCHAR), 'NULL') + 
					', @bSimulado = ' + ISNULL(CAST(@bSimulado AS VARCHAR(1)), 'NULL') + 
					', @sUsuarioProceso = ' + ISNULL(CAST(@sUsuarioProceso AS VARCHAR), 'NULL') + 
					', @IdTransaccion = ' + ISNULL(CAST(@IdTransaccion AS VARCHAR), 'NULL'),
					'Invocación con @iIdIndicePlanilla null o 0');
        COMMIT;
	END
	ELSE
	BEGIN
		DECLARE @IndicesPlanilla AS TABLE (IdIndicePlanilla Bigint, EsPensionado BIT)
		DECLARE @RegistroGeneralId AS TABLE (regId BIGINT)

		DECLARE @redDateTimeUpdate DATETIME = dbo.getLocalDate()
		DECLARE @localDate DATETIME = dbo.getLocalDate()

		DECLARE @TablaTiposIdentificacion AS TABLE (TipoIdCorto VARCHAR(2), TipoIdLargo VARCHAR(40))
		DECLARE @CantidadDatosStaging INT

		SET @IdTransaccion = 0

		INSERT INTO @TablaTiposIdentificacion
		VALUES
		('RC','REGISTRO_CIVIL'						),
		('TI','TARJETA_IDENTIDAD'					),
		('CC','CEDULA_CIUDADANIA'					),
		('CE','CEDULA_EXTRANJERIA'					),
		('PA','PASAPORTE'							),
		('CD','CARNE_DIPLOMATICO'					),
		('NI','NIT'									),
		('SC','SALVOCONDUCTO'						),
		('PE','PERM_ESP_PERMANENCIA'				),
		('PT','PERM_PROT_TEMPORAL'					);

		-- Solo se debe utilizar el registro correspondiente al archivo I/IR/IP/IPR la planilla para el procesamiento de mundo 2
		INSERT INTO @IndicesPlanilla 
		SELECT pipId, CASE WHEN [pipTipoArchivo]='ARCHIVO_OI_IPR' OR [pipTipoArchivo]='ARCHIVO_OI_IP'THEN 1 ELSE 0 END
		FROM PilaIndicePlanilla
		WHERE pipId = @iIdIndicePlanilla;


				--==== Se agrega control para las planillas N, solo se van a pasar los registros C.
				drop table if exists #planillaNBuscarOriginal
				select distinct pi1.pi1NumPlanillaAsociada, pi1.pi1FechaPagoAsociado,pi1.pi1IdAportante, tia.TipoIdLargo
				into #planillaNBuscarOriginal
				from PilaIndicePlanilla as p with (nolock)
				inner join PilaArchivoIRegistro1 as pi1 with (nolock) on p.pipId = pi1.pi1IndicePlanilla
				inner join @TablaTiposIdentificacion as tia on tia.TipoIdCorto = pi1.pi1TipoDocAportante
				inner join PilaArchivoIRegistro2 as pi2 with (nolock) on pi2.pi2IndicePlanilla = p.pipId
				inner join @TablaTiposIdentificacion as tic on tic.TipoIdCorto = pi2.pi2TipoIdCotizante
				where p.pipId = @iIdIndicePlanilla and pi1.pi1TipoPlanilla = 'N' and pi1.pi1NumPlanillaAsociada is not null and isnull(pi2.pi2Correcciones,'') in ('A', 'C') and isnull(p.pipEstadoArchivo, '') not in ('ANULADO','RECAUDO_NOTIFICADO')

				--=== Se agrega validación para las planillas N sin original, para que pasen por otr flujo. 
				select top 1 p.pipIdPlanilla, p.pipId
				into #planillaNBuscarOriginalNA
				from dbo.PilaIndicePlanilla as p with (nolock)
				inner join dbo.PilaArchivoIRegistro1 as pi1 with (nolock) on p.pipId = pi1.pi1IndicePlanilla
				inner join dbo.PilaArchivoIRegistro2 as pi2 with (nolock) on pi2.pi2IndicePlanilla = p.pipId
				where p.pipIdPlanilla in (	select pipIdPlanilla
											from #planillaNBuscarOriginal
											where pi1NumPlanillaAsociada in (	select pi1NumPlanillaAsociada
																				from #planillaNBuscarOriginal
																				except
																				select p.pipIdPlanilla 
																				from dbo.PilaIndicePlanilla as p with (nolock)))
				and pi1.pi1TipoPlanilla = 'N' and pi1.pi1NumPlanillaAsociada is not null and isnull(pi2.pi2Correcciones,'') in ('A', 'C') 


				    --------validacion para pasar las planillas con N sin original sbayona
				    declare  @registroGeneralConteo smallint

                    select @registroGeneralConteo=isnull(count(r.regId),0)
					from dbo.PilaIndicePlanilla as p with (nolock)
					inner join staging.RegistroGeneral as r on p.pipId = r.regRegistroControl
					where p.pipIdPlanilla in (select distinct pi1NumPlanillaAsociada from #planillaNBuscarOriginal) 



				if exists (select * from #planillaNBuscarOriginalNA)
					begin
						delete from  #planillaNBuscarOriginal
					end


		IF NOT EXISTS (
			SELECT 1
			FROM [staging].[RegistroGeneral] reg
			JOIN @IndicesPlanilla tmp ON reg.regRegistroControl = tmp.IdIndicePlanilla 
		)
		BEGIN
			
			declare @tablaTran table (idTran bigInt)
			INSERT INTO [staging].[Transaccion] (traFechaTransaccion) 
			output inserted.traId into @tablaTran
			VALUES (@localDate)
			SET @IdTransaccion = (select idTran from @tablaTran)

			--print '	VerificarDependienteIndependiente'
			IF EXISTS (	
				SELECT TOP 1 1
				FROM @IndicesPlanilla
				WHERE EsPensionado = 0
			)
			BEGIN --== Incia proceso para independientes o pensionados. 
						
						
				--==== Se agrega control para las planillas N, solo se van a pasar los registros C.
				if exists (select * from #planillaNBuscarOriginal) and @registroGeneralConteo=1
					begin
					/*
						insert pruebaParaPlanillasN (mensaje, pipId, fecha) 
						select 'ENTRA A LAS N', @iIdIndicePlanilla,dbo.GetLocalDate()
					*/
					declare @IdTransaccion2 bigInt 
					set @IdTransaccion2 = @IdTransaccion
					execute USP_ExecutePILA2CopiarPlanillasN @iIdIndicePlanilla, @IdTransaccion2, @bSimulado, @sUsuarioProceso

					end

				--===== Planillas N sin Original
				else if  exists (select * from #planillaNBuscarOriginalNA) or (@registroGeneralConteo > 1)
					begin

						--select 'N/A'
						
						declare @IdTransaccion2_1 bigInt 
						set @IdTransaccion2_1 = @IdTransaccion
						execute USP_ExecutePILA2CopiarPlanillasN_SINORIGINAL @iIdIndicePlanilla, @IdTransaccion2_1, @bSimulado, @sUsuarioProceso
						
					end

				else
					begin

							BEGIN TRAN 
								-- Registro general Dependiente/Independiente
								-- Se cambia el insert para controlar evitar duplicados en el registroGeneral por planillas. 

								

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
								SELECT @IdTransaccion, 0 AS regEsAportePensionados,pi1RazonSocial,ti.TipoIdLargo,pi1IdAportante,pi1DigVerAportante
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
								INNER JOIN @TablaTiposIdentificacion ti ON ti.TipoIdCorto = pi1.pi1TipoDocAportante
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
								WHERE pi1IndicePlanilla IN (SELECT IdIndicePlanilla FROM @IndicesPlanilla)
								AND pi1IndicePlanilla NOT IN (SELECT regRegistroControl FROM [staging].[RegistroGeneral] WHERE regRegistroControl IS NOT NULL)

								--==== Se hace el insert en el registroGeneral final. 
								
								INSERT INTO [staging].[RegistroGeneral](
									regTransaccion,regEsAportePensionados,regNombreAportante,regTipoIdentificacionAportante,regNumeroIdentificacionAportante,
									regDigVerAportante,regPeriodoAporte,regTipoPlanilla,regClaseAportante,regCodSucursal,regNomSucursal,regDireccion,regCodCiudad,
									regCodDepartamento,regTelefono,regFax,regEmail,regFechaMatricula,regNaturalezaJuridica,regModalidadPlanilla,regValTotalApoObligatorio,
									regValorIntMora,regFechaRecaudo,regCodigoEntidadFinanciera,regOperadorInformacion,regNumeroCuenta,regOUTEstadoArchivo,
									regFechaActualizacion,regRegistroControl, regRegistroFControl,regNumPlanilla,regEsSimulado,regEstadoEvaluacion,regOUTMotivoProcesoManual,
									regNumPlanillaAsociada, regDiasMora, regFechaPagoAporte, regFormaPresentacion, regCantidadEmpleados,regCantidadAfiliados, 
									regTipoPersona, regOUTEnProceso, regCantidadReg2, regFechaPagoPlanillaAsociada,regDateTimeInsert,regDateTimeUpdate)
									OUTPUT INSERTED.regId INTO @RegistroGeneralId
								select regTransaccion,regEsAportePensionados,regNombreAportante,regTipoIdentificacionAportante,regNumeroIdentificacionAportante,
									regDigVerAportante,regPeriodoAporte,regTipoPlanilla,regClaseAportante,regCodSucursal,regNomSucursal,regDireccion,regCodCiudad,
									regCodDepartamento,regTelefono,regFax,regEmail,regFechaMatricula,regNaturalezaJuridica,regModalidadPlanilla,regValTotalApoObligatorio,
									regValorIntMora,regFechaRecaudo,regCodigoEntidadFinanciera,regOperadorInformacion,regNumeroCuenta,regOUTEstadoArchivo,
									regFechaActualizacion,regRegistroControl, regRegistroFControl,regNumPlanilla,regEsSimulado,regEstadoEvaluacion,regOUTMotivoProcesoManual,
									regNumPlanillaAsociada, regDiasMora, regFechaPagoAporte, regFormaPresentacion, regCantidadEmpleados,regCantidadAfiliados, 
									regTipoPersona, regOUTEnProceso, regCantidadReg2, regFechaPagoPlanillaAsociada,regDateTimeInsert,regDateTimeUpdate
									from staging.RegistroGenCtrl
									where regRegistroControl = @iIdIndicePlanilla

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
									,redOUTRegistroActual,redDateTimeUpdate)
								SELECT regId,ti.TipoIdLargo,pi2IdCotizante,pi2TipoCotizante,pi2CodDepartamento
									,pi2CodDepartamento+pi2CodMunicipio,pi2PrimerApellido,pi2SegundoApellido,pi2PrimerNombre,pi2SegundoNombre,pi2NovIngreso,pi2NovRetiro,pi2NovVSP,pi2NovVST
									,pi2NovSLN,pi2NovIGE,pi2NovLMA,pi2NovVACLR,pi2DiasIRL,pi2DiasCotizados,pi2SalarioBasico,pi2ValorIBC,pi2Tarifa,pi2AporteObligatorio
									,pi2Correcciones,pi2SalarioIntegral,pi2FechaIngreso,pi2FechaRetiro,pi2FechaInicioVSP,pi2FechaInicioSLN,pi2FechaFinSLN,pi2FechaInicioIGE
									,pi2FechaFinIGE,pi2FechaInicioLMA,pi2FechaFinLMA,pi2FechaInicioVACLR,pi2FechaFinVACLR,pi2FechaInicioVCT,pi2FechaFinVCT,pi2FechaInicioIRL
									,pi2FechaFinIRL,pi2HorasLaboradas,pi2Secuencia
									,CASE 
										WHEN @bSimulado = 1 AND ISNULL(pip.pipMotivoProcesoManual, '') = 'ARCHIVO_CORRECCION' 
										THEN 'CORREGIDO' ELSE 'VIGENTE'
									END
									,@sUsuarioProceso
									,CASE WHEN regValTotalApoObligatorio = 0 THEN 0 ELSE
										(pi2AporteObligatorio * regValorIntMora) / regValTotalApoObligatorio
									END, pi2Id, 1, @redDateTimeUpdate
								FROM 
									---////////*****************************
									---**** CONTROL PARA EVITAR DUPLICIDAD QUE SE PUEDE DAR EN EL REGISTRO 2
									(select pi2IndicePlanilla ,pi2Secuencia,pi2TipoIdCotizante,pi2IdCotizante,pi2TipoCotizante,pi2SubTipoCotizante,pi2ExtrangeroNoObligado,pi2ColombianoExterior,pi2CodDepartamento,pi2CodMunicipio,pi2PrimerApellido,pi2SegundoApellido,pi2PrimerNombre,pi2SegundoNombre,pi2NovIngreso
									,pi2NovRetiro,pi2NovVSP,pi2NovVST,pi2NovSLN,pi2NovIGE,pi2NovLMA,pi2NovVACLR,pi2DiasIRL,pi2DiasCotizados,pi2SalarioBasico,pi2ValorIBC,pi2Tarifa,pi2AporteObligatorio,pi2Correcciones,pi2SalarioIntegral,pi2FechaIngreso,pi2FechaRetiro,pi2FechaInicioVSP,pi2FechaInicioSLN,pi2FechaFinSLN
									,pi2FechaInicioIGE,pi2FechaFinIGE,pi2FechaInicioLMA,pi2FechaFinLMA,pi2FechaInicioVACLR,pi2FechaFinVACLR,pi2FechaInicioVCT,pi2FechaFinVCT,pi2FechaInicioIRL,pi2FechaFinIRL,pi2HorasLaboradas, min(pi2Id) as pi2Id
									from PilaArchivoIRegistro2 
									where pi2IndicePlanilla in (select IdIndicePlanilla from @IndicesPlanilla)
									group by pi2IndicePlanilla ,pi2Secuencia,pi2TipoIdCotizante,pi2IdCotizante,pi2TipoCotizante,pi2SubTipoCotizante,pi2ExtrangeroNoObligado,pi2ColombianoExterior,pi2CodDepartamento,pi2CodMunicipio,pi2PrimerApellido,pi2SegundoApellido,pi2PrimerNombre,pi2SegundoNombre,pi2NovIngreso
									,pi2NovRetiro,pi2NovVSP,pi2NovVST,pi2NovSLN,pi2NovIGE,pi2NovLMA,pi2NovVACLR,pi2DiasIRL,pi2DiasCotizados,pi2SalarioBasico,pi2ValorIBC,pi2Tarifa,pi2AporteObligatorio,pi2Correcciones,pi2SalarioIntegral,pi2FechaIngreso,pi2FechaRetiro,pi2FechaInicioVSP,pi2FechaInicioSLN,pi2FechaFinSLN
									,pi2FechaInicioIGE,pi2FechaFinIGE,pi2FechaInicioLMA,pi2FechaFinLMA,pi2FechaInicioVACLR,pi2FechaFinVACLR,pi2FechaInicioVCT,pi2FechaFinVCT,pi2FechaInicioIRL,pi2FechaFinIRL,pi2HorasLaboradas) as PilaArchivoIRegistro2
									---**** FINALIZA CONTROL PARA EVITAR DUPLICIDAD QUE SE PUEDE DAR EN EL REGISTRO 2
									---////////*****************************
								INNER JOIN PilaIndicePlanilla pip ON pi2IndicePlanilla = pipId
								INNER JOIN PilaArchivoIRegistro1 ON PilaArchivoIRegistro2.pi2IndicePlanilla = PilaArchivoIRegistro1.pi1IndicePlanilla 		
								INNER JOIN staging.RegistroGeneral ON regRegistroControl = pi1IndicePlanilla
								INNER JOIN @TablaTiposIdentificacion ti ON ti.TipoIdCorto = pi2TipoIdCotizante
								WHERE regId IN (SELECT min(regId) FROM @RegistroGeneralId)
								AND ((regOUTMotivoProcesoManual IS NULL) --OR regOUTMotivoProcesoManual = 'ARCHIVO_CORRECCION')
									OR (regOUTMotivoProcesoManual IS NOT NULL AND ISNULL(pi2Correcciones, '') not in ('C','A')));
							COMMIT;
					end
			END --== Finaliza proceso para independientes o pensionados. 

			--print '	VerificarPensionado'
			IF EXISTS (	
				SELECT TOP 1 1
				FROM @IndicesPlanilla
				WHERE EsPensionado = 1
			)
			BEGIN
			

			if exists (select top 1 1
						from dbo.PilaArchivoIPRegistro2 with (nolock)
						where ip2IndicePlanilla = @iIdIndicePlanilla
						and isnull(ip2Correcion, '') in ('A', 'C'))
			begin

				declare @IdTransaccion2_11 bigInt 
				set @IdTransaccion2_11 = @IdTransaccion
				execute [dbo].[USP_ExecutePILA2CopiarPlanillasN_PENSIONADO] @iIdIndicePlanilla, @IdTransaccion2_11, @bSimulado, @sUsuarioProceso
			end 

			else
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
					SELECT @IdTransaccion, 1 AS regEsAportePensionados,ip1NombrePagador,ti.TipoIdLargo,ip1IdPagador,ip1PeriodoAporte
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
					INNER JOIN @TablaTiposIdentificacion ti ON ti.TipoIdCorto = ip1TipoIdPagador
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
					WHERE ip1IndicePlanilla IN (SELECT IdIndicePlanilla FROM @IndicesPlanilla)
					AND ip1IndicePlanilla NOT IN (SELECT regRegistroControl FROM [staging].[RegistroGeneral] WHERE regRegistroControl IS NOT NULL)
					and not exists (select top 1 1 from dbo.PilaArchivoIPRegistro2 where ip2IndicePlanilla = @iIdIndicePlanilla and isnull(ip2Correcion, '') in ('A', 'C'))


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
					where regRegistroControl = @iIdIndicePlanilla


					-- Registro detallado Pensionado
					INSERT INTO [staging].[RegistroDetallado] (
						redRegistroGeneral,redTipoIdentificacionCotizante,redNumeroIdentificacionCotizante,redCodDepartamento
						,redCodMunicipio,redPrimerApellido,redSegundoApellido
						,redPrimerNombre,redSegundoNombre,redTarifa,redAporteObligatorio,redSalarioBasico,redDiasCotizados,redNovIngreso
						,redNovRetiro,redNovVSP,redNovSUS,redFechaIngreso,redFechaRetiro,redFechaInicioVSP,redFechaInicioSuspension,redFechaFinSuspension,redRegistroControl,redEstadoEvaluacion,redUsuarioAprobadorAporte
						,redOUTValorMoraCotizante,redIdRegistro2pila,redOUTRegistroActual,redDateTimeUpdate)
					SELECT regId,ti.TipoIdLargo,ip2IdPensionado,ip2CodDepartamento
						,ip2CodMunicipio,ip2PrimerApellido,ip2SegundoApellido
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
					from (select ip2IndicePlanilla, ip2IdPensionado,ip2CodDepartamento ,(ip2CodDepartamento+ip2CodMunicipio) as ip2CodMunicipio,ip2PrimerApellido,ip2SegundoApellido
							,ip2PrimerNombre,ip2SegundoNombre,ip2Tarifa,ip2ValorAporte,ip2ValorMesada,ip2DiasCotizados,ip2NovING,ip2NovRET,ip2NovVSP, ip2TipoIdPensionado
							,ip2NovSUS,ip2FechaIngreso,ip2FechaRetiro,ip2FechaInicioVSP,ip2FechaInicioSuspension,ip2FechaFinSuspension,ip2Secuencia, min(ip2Id) as ip2Id
							from PilaArchivoIPRegistro2 with (nolock)
							where isnull(ip2Correcion,'') = '' --== Se agrega control para evitar procesado de estos registros. 
							group by ip2IndicePlanilla, ip2IdPensionado,ip2CodDepartamento ,ip2CodDepartamento+ip2CodMunicipio,ip2PrimerApellido,ip2SegundoApellido
							,ip2PrimerNombre,ip2SegundoNombre,ip2Tarifa,ip2ValorAporte,ip2ValorMesada,ip2DiasCotizados,ip2NovING,ip2NovRET,ip2NovVSP, ip2TipoIdPensionado
							,ip2NovSUS,ip2FechaIngreso,ip2FechaRetiro,ip2FechaInicioVSP,ip2FechaInicioSuspension,ip2FechaFinSuspension,ip2Secuencia) as PilaArchivoIPRegistro2 --==== Ajuste para duplicados en registro2 Pensionados. 
					INNER JOIN PilaIndicePlanilla pip ON ip2IndicePlanilla = pipId
					INNER JOIN PilaArchivoIPRegistro1 ON ip2IndicePlanilla = ip1IndicePlanilla
					INNER JOIN staging.RegistroGeneral ON regRegistroControl = ip1IndicePlanilla
					INNER JOIN @TablaTiposIdentificacion ti ON ti.TipoIdCorto = ip2TipoIdPensionado
					WHERE regId IN (SELECT regId FROM @RegistroGeneralId)
				COMMIT
			end

			END
		END
		ELSE IF @bSimulado = 1 and not exists (select * from #planillaNBuscarOriginal) --==== Se agrega validación, para que no actualice la transacción.
		BEGIN 
			DECLARE @regId BIGINT;
			
			SELECT @regId = reg.regId
			FROM [staging].[RegistroGeneral] reg 
			JOIN @IndicesPlanilla tmp ON reg.regRegistroControl = tmp.IdIndicePlanilla;

			BEGIN TRAN
			--	INSERT INTO [staging].[Transaccion] (traFechaTransaccion) VALUES (@localDate);
			--	SET @IdTransaccion = @@IDENTITY;

				UPDATE reg
					 --regTransaccion = @IdTransaccion
					set  regEsSimulado = @bSimulado
					,regOUTEnProceso = 1,
					regDateTimeUpdate = @localDate
				FROM [staging].[RegistroGeneral] reg 
				WHERE reg.regId = @regId;
			COMMIT;
		END;
       
	   			------------validacion planilla tipo O sbayona
        drop table if exists #registrosO 
	    select distinct regId,regTipoPlanilla,regValTotalApoObligatorio,regValorIntMora 
		into #registrosO 
		from staging.RegistroGeneral 
		where regValTotalApoObligatorio<=0 and regTipoPlanilla='O' and regId in (SELECT regId FROM @RegistroGeneralId)

		if exists (select * from #registrosO)
		begin
		update rd set redOUTValorMoraCotizante=(select cast(r.regValorIntMora as bigint) / count(*) over (partition by rd.redRegistroGeneral) as regValorIntMora 
			from staging.RegistroGeneral r
			inner join staging.RegistroDetallado rd on r.regId=rd.redRegistroGeneral
			where regId in (SELECT regId FROM @RegistroGeneralId))
		from staging.registroDetallado rd
		inner join staging.registroGeneral rg on rd.redRegistroGeneral=rg.regId
		inner join #registrosO r2 on r2.regId=rg.regId
		where rg.regId in (SELECT regId FROM @RegistroGeneralId)
	    end

	END

	BEGIN TRAN
		INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
		VALUES (dbo.GetLocalDate(), 
				'USP_ExecutePILA2CopiarPlanilla |' +
				'@iIdIndicePlanilla = ' + ISNULL(CAST(@iIdIndicePlanilla AS VARCHAR), 'NULL') + 
				', @sFase = ' + ISNULL(CAST(@sFase AS VARCHAR), 'NULL') + 
				', @bSimulado = ' + ISNULL(CAST(@bSimulado AS VARCHAR(1)), 'NULL') + 
				', @sUsuarioProceso = ' + ISNULL(CAST(@sUsuarioProceso AS VARCHAR), 'NULL') + 
				', @IdTransaccion = ' + ISNULL(CAST(@IdTransaccion AS VARCHAR), 'NULL'),
				'USP_ExecutePILA2CopiarPlanilla FIN');

		drop table if exists #planillaNBuscarOriginal

	COMMIT;
END TRY
BEGIN CATCH
	-- se hace rollback de los registros que se hayan creado
	IF XACT_STATE() <> 0 AND @@TRANCOUNT > 0
	BEGIN
		ROLLBACK
	END
	
	SELECT @ErrorMessage = '[dbo].[USP_ExecutePILA2CopiarPlanilla]|' + ERROR_MESSAGE(),
		@ErrorSeverity = ERROR_SEVERITY(),
		@ErrorState = ERROR_STATE();

	BEGIN TRANSACTION log2
		INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
		VALUES (dbo.GetLocalDate(), 
					'USP_ExecutePILA2CopiarPlanilla |' +
					'@iIdIndicePlanilla = ' + ISNULL(CAST(@iIdIndicePlanilla AS VARCHAR), 'NULL') + 
					', @sFase = ' + ISNULL(CAST(@sFase AS VARCHAR), 'NULL') + 
					', @bSimulado = ' + ISNULL(CAST(@bSimulado AS VARCHAR(1)), 'NULL') + 
					', @sUsuarioProceso = ' + ISNULL(CAST(@sUsuarioProceso AS VARCHAR), 'NULL') + 
					', @IdTransaccion = ' + ISNULL(CAST(@IdTransaccion AS VARCHAR), 'NULL'),
					@ErrorMessage);
	COMMIT TRANSACTION log2

	RAISERROR (@ErrorMessage, -- Message text.
				@ErrorSeverity, -- Severity.
				@ErrorState -- State.
				);      
END CATCH;
END;