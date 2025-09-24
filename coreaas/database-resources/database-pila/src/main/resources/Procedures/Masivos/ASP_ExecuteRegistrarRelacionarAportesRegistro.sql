-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2017/06/16
-- Description:	Procedimiento almacenado encargado  de ejecutar el registro
-- o relacion de aportes del IdIndicePlanilla dado
-- =============================================
CREATE or ALTER  PROCEDURE [masivos].[ASP_ExecuteRegistrarRelacionarAportesRegistro]
	@IdRegistroGeneral Bigint,
	@EsSimulado BIT = 0,
	@EsProcesoManual BIT = 0
AS
BEGIN
SET NOCOUNT ON;	
	DECLARE @redDateTimeUpdate DATETIME = dbo.getLocalDate()
	
	INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
	VALUES (dbo.getLocalDate(), 
	'@IdRegistroGeneral=' + isnull(CAST(@IdRegistroGeneral AS VARCHAR(20)),'') + 
	';@EsSimulado=' + isnull(CAST(@EsSimulado AS CHAR(1)),'') + 
	';@EsProcesoManual=' + isnull(CAST(@EsProcesoManual AS VARCHAR(20)),''), 
	'INICIO USP_ExecuteRegistrarRelacionarAportesRegistro');
 
	
	DECLARE @cantCotizantesDependientes Smallint
	DECLARE @cantCotizantesIndependientes Smallint
	create table #TablaIndependientes (tipoCotizante VARCHAR(2))
	DECLARE @TipoAporteIndependiente VARCHAR(20) = ''
	DECLARE @Estado VARCHAR(50) = ''
	DECLARE @EstadoAportante VARCHAR(50) = ''
	DECLARE @EstadoCotizante VARCHAR(50) = ''
	DECLARE @TipoSolicitudAportante VARCHAR(20)
	DECLARE @TipoSolicitudAportanteInd VARCHAR(20)
	DECLARE @MarcaCreacionAportante VARCHAR(20)
	DECLARE @MarcaCreacionAportanteInd VARCHAR(20)
	DECLARE @EstadoArchivo VARCHAR(60)
	DECLARE @EsArchivoPensionados BIT
	DECLARE @MarcaRegistroRelacionAporte VARCHAR(50)

	DECLARE @MarcaSucursalPILA BIT = 0
	DECLARE @cumpleSucursal BIT = 1
	
	
	DECLARE @codigoSucursalPILA VARCHAR(10) = NULL
	DECLARE @codigoSucursalPrincipal VARCHAR(10) = NULL
	DECLARE @nombreSucursalPrincipal VARCHAR(100) = NULL
		
	INSERT INTO #TablaIndependientes
	SELECT Data FROM dbo.Split ( (
		SELECT stpValorParametro FROM staging.StagingParametros 
		WHERE stpNombreParametro = 'SOLICITANTE_INDEPENDIENTE') ,',')

	--print 'SEQC Evaluar Condiciones Iniciales'
	--print '	ConsultarTipoArchivo'

	SELECT	@EstadoArchivo = reg.regOUTEstadoArchivo,
			@EsArchivoPensionados = reg.regEsAportePensionados, 
			@EstadoAportante = reg.regOUTEstadoEmpleador
	FROM staging.RegistroGeneral reg with (nolock)
	WHERE reg.regId = @IdRegistroGeneral
	AND reg.regOUTEstadoArchivo = 'PROCESADO_VS_BD'

	--print '@EstadoArchivo'
	--print @EstadoArchivo

	--print '			VerificarEstadoArchivo'
	IF @EstadoArchivo = 'PROCESADO_VS_BD'
	BEGIN
		-- se actualiza el nombre de un aportante que paga por sí mismo en el registro general
		EXEC USP_ActualizarAportanteAportePropio @IdRegistroGeneral

		---- Se crean temporales con los datos requeridos de cotizante y aportante para el aporte
		--DECLARE @temAportanteTemp AS TABLE (
		--	tapIdTransaccion BIGINT, 
		--	tapTipoSolicitud VARCHAR(100)
		--)

		--DECLARE @temCotizanteTemp AS TABLE (
		--	tctIdTransaccion BIGINT, 
		--	tctTipoCotizante VARCHAR(50), 
		--	tctMarcaCreacion VARCHAR(100)
		--)
			
		--print '				Marcar aportantes para fiscalización'

		EXEC dbo.USP_AsignarMarcaProcesoFiscalizacion @IdRegistroGeneral

		--print '				Marcar novedad futura'

		EXEC dbo.USP_AsignarMarcaNovedadFutura @IdRegistroGeneral, NULL

		--print '				VerificarAprobacionDepInde'

		IF NOT EXISTS (			
			SELECT TOP 1 1 
			FROM staging.RegistroDetallado red with (nolock)
			INNER JOIN staging.RegistroGeneral reg with (nolock) ON red.redRegistroGeneral = reg.regId
			WHERE red.redRegistroGeneral = @IdRegistroGeneral
			AND red.redOUTEstadoRegistroAporte IN ('NO_VALIDADO_BD', 'NO_OK')
			AND (@EsSimulado = 0 
				OR (@EsSimulado = 1 AND red.redOUTRegistrado IS NOT NULL)
			)
			AND ISNULL(red.redCorrecciones, '') != 'A'
		) AND @EsArchivoPensionados = 0
		BEGIN
			
			SELECT @codigoSucursalPrincipal = regOUTCodSucursalPrincipal, @nombreSucursalPrincipal = regOUTNomSucursalPrincipal
				,@MarcaSucursalPILA = regOUTMarcaSucursalPILA
				,@codigoSucursalPILA = regCodSucursal
			FROM staging.RegistroGeneral pi1 with (nolock)
			WHERE pi1.regId = @IdRegistroGeneral

			--print '					Evaluar Aportante'
				
			SET @cantCotizantesDependientes = (SELECT COUNT(1) FROM staging.RegistroDetallado WHERE ISNULL(redTipoCotizante,'') NOT IN (SELECT tipoCotizante FROM #TablaIndependientes) AND redRegistroGeneral = @IdRegistroGeneral)
			SET @cantCotizantesIndependientes = (SELECT COUNT(1) FROM staging.RegistroDetallado WHERE ISNULL(redTipoCotizante,'') IN (SELECT tipoCotizante FROM #TablaIndependientes) AND redRegistroGeneral = @IdRegistroGeneral)

			--print '@cantCotizantesDependientes'
			--print @cantCotizantesDependientes

			--print '@cantCotizantesIndependientes'
			--print @cantCotizantesIndependientes

			IF (@cantCotizantesDependientes > 0)
			BEGIN
				-- si se encuentra por lo menos a un cotizante dependiente (diferente a 3, 16, 57 y 59) se valida
				-- al aportante como un empleador

				SELECT @EstadoAportante = CASE 
						WHEN regOUTEstadoEmpleador IS NULL THEN 'CREAR_Y_RELACIONAR_EMPLEADOR'
						WHEN regOUTEstadoEmpleador IN ('NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES','NO_FORMALIZADO_CON_INFORMACION') THEN 'RELACIONAR'
						ELSE 'REGISTRAR'
					END
				FROM staging.RegistroGeneral pi1 with (nolock)
				WHERE pi1.regId = @IdRegistroGeneral

				SET @TipoSolicitudAportante = 'EMPLEADOR'
				IF @EstadoAportante = 'CREAR_Y_RELACIONAR_EMPLEADOR'
				BEGIN
					SET @MarcaCreacionAportante = 'CREADO_DESDE_PILA'
				END

				--print '@EstadoAportante'
				--print @EstadoAportante

				--print '@TipoSolicitudAportante'
				--print @TipoSolicitudAportante

				--print '@MarcaCreacionAportante'
				--print @MarcaCreacionAportante
			END

			IF @cantCotizantesIndependientes > 0
			BEGIN
				
				SELECT @TipoAporteIndependiente = CASE WHEN (
					SELECT COUNT(1) 
					FROM staging.RegistroGeneral i1 with (nolock)
						INNER JOIN staging.RegistroDetallado i2 with (nolock) ON (
							i1.regId = i2.redRegistroGeneral
							AND	i1.regTipoIdentificacionAportante = i2.redTipoIdentificacionCotizante
							AND i1.regNumeroIdentificacionAportante = i2.redNumeroIdentificacionCotizante
						)
					WHERE i1.regId = @IdRegistroGeneral) > 0 AND COUNT(1) = 1 THEN 'APORTE_PROPIO' ELSE 'APORTE_TERCERO' END,
					@EstadoCotizante = i2_ext.redOUTEstadoSolicitante 
				FROM staging.RegistroDetallado i2_ext with (nolock)
				WHERE i2_ext.redRegistroGeneral = @IdRegistroGeneral
				AND ISNULL(i2_ext.redCorrecciones, '') != 'A'
				GROUP BY i2_ext.redOUTEstadoSolicitante

				IF @TipoAporteIndependiente = 'APORTE_PROPIO'
				BEGIN

					SET @Estado = CASE 
						WHEN @EstadoCotizante IS NULL THEN 'CREAR_Y_RELACIONAR_INDEPENDIENTE'
						WHEN @EstadoCotizante IN ('NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES', 'NO_FORMALIZADO_CON_INFORMACION') THEN 'RELACIONAR'
						ELSE 'REGISTRAR'
					END
						
					SET @TipoSolicitudAportanteInd = 'PERSONA'
					IF @Estado = 'CREAR_Y_RELACIONAR_INDEPENDIENTE'
					BEGIN
						SET @MarcaCreacionAportanteInd = 'CREADO_DESDE_PILA'
					END
				END
				ELSE -- 'APORTE_TERCERO'
				BEGIN
						
					SET	@Estado = CASE 
						WHEN @EstadoAportante IS NULL THEN 'CREAR_Y_RELACIONAR_ENTIDAD_PAGADORA_INDEPENDIENTE' ELSE 'REGISTRAR' 
					END

					SET @TipoSolicitudAportanteInd = 'ENTIDAD_PAGADORA'
					IF @Estado = 'CREAR_Y_RELACIONAR_ENTIDAD_PAGADORA_INDEPENDIENTE'
					BEGIN
						SET @MarcaCreacionAportanteInd = 'CREADO_DESDE_PILA'
					END

				END

				--print '@TipoSolicitudAportanteInd'
				--print @TipoSolicitudAportanteInd

				--print '@MarcaCreacionAportanteInd'
				--print @MarcaCreacionAportanteInd
			END

			--print '@TipoSolicitudAportante'
			--print @TipoSolicitudAportante
				
			--Evaluar sucursal solo aplica para el proceso de dependiente
			IF @TipoSolicitudAportante = 'EMPLEADOR'
			BEGIN
				--print '							BLOQUE DE SUCURSALES'

				IF @MarcaSucursalPILA = 1
				BEGIN
					EXEC USP_VerificarCumplimientoSucursal @IdRegistroGeneral, @codigoSucursalPILA, @codigoSucursalPrincipal, @cumpleSucursal OUTPUT

					IF @cumpleSucursal = 0
					BEGIN
						--print 'Pasar a bandeja'

						UPDATE reg
							SET regOUTEstadoArchivo = 'PENDIENTE_POR_REGISTRO_Y_RELACION_DE_APORTES',
							reg.regOUTEnProceso = 0,
							reg.regDateTimeUpdate = dbo.getLocalDate()
						FROM staging.RegistroGeneral reg with (nolock)
						WHERE reg.regId = @IdRegistroGeneral
							
						UPDATE red
						SET
							redDateTimeUpdate = @redDateTimeUpdate,
							redOUTEstadoRegistroAporte='PENDIENTE_POR_REGISTO_RELACION_APORTE'
						FROM staging.RegistroDetallado red with (nolock)
						WHERE red.redRegistroGeneral = @IdRegistroGeneral

						RETURN
					END
					
					IF @cumpleSucursal = 1
					BEGIN
						--print 'Sacar de la bandeja (sí aplica)'

						DECLARE @estadoDet VARCHAR(60) = NULL

						SELECT @estadoDet = redOUTEstadoRegistroAporte
						FROM staging.RegistroDetallado red with (nolock)
						WHERE red.redRegistroGeneral = @IdRegistroGeneral

						IF ISNULL(@estadoDet, '') = 'PENDIENTE_POR_REGISTO_RELACION_APORTE'
						BEGIN
							-- se re calcula la evaluación del cotizante

							SELECT @estadoDet = CASE 
								WHEN (
									redOUTEstadoValidacionV0 = 'CUMPLE' 
									AND redOUTEstadoValidacionV1 = 'OK' 
									AND ISNULL(redOUTEstadoValidacionV2, 'NO_OK') != 'NO_OK' 
									AND redOUTEstadoValidacionV3 = 'OK') 
								THEN 'OK'
								ELSE 'NO_OK_APROBADO' 
							END
							FROM staging.RegistroDetallado red with (nolock)
							WHERE red.redRegistroGeneral = @IdRegistroGeneral

							UPDATE red
								SET redOUTEstadoRegistroAporte = @estadoDet 
							FROM staging.RegistroDetallado red with (nolock)
							WHERE red.redRegistroGeneral = @IdRegistroGeneral
						END
					END
				END
			END

			-- sólo se lleva a cabo la creación de temporales cuando se ha cumplido con la validación de sucursal (cuando aplica)
		--	IF @cumpleSucursal = 1
			BEGIN
				--print '						@TipoSolicitudAportante = ' + @TipoSolicitudAportante
				--print '						Insertar TemAportante'
			
				-- se agregan los aportantes EMPLEADOR
				--INSERT INTO @temAportanteTemp (TapIdTransaccion, tapTipoSolicitud)
				--SELECT DISTINCT redId, 
				--	CASE WHEN red.redOUTTipoAfiliado = 'TRABAJADOR_DEPENDIENTE' THEN 'EMPLEADOR' ELSE 'PERSONA' END
				--FROM staging.RegistroGeneral reg
				--INNER JOIN staging.RegistroDetallado red ON reg.regId = red.redRegistroGeneral
				--WHERE reg.regId = @IdRegistroGeneral

				INSERT INTO dbo.TemAportante (TapIdTransaccion,tapRazonSocial,tapTipoDocAportante,TapIdAportante,tapDigVerAportante,
				tapDireccion,tapCodCiudad,tapCodDepartamento,tapTelefono,tapFax,tapEmail,tapFechaMatricula,tapNaturalezaJuridica,tapMarcaSucursal,
				tapMarcaCreacion,tapTipoSolicitud,tapFechaHoraSolicitud,tapEsEmpleadorReintegrable,tapEnviadoAFiscalizacion,tapMotivoFiscalizacion,
				tapPrimerNombreAportante, tapSegundoNombreAportante, tapPrimerApellidoAportante, tapSegundoApellidoAportante)
				SELECT	redId,
						reg.regNombreAportante,
						reg.regTipoIdentificacionAportante, 
						reg.regNumeroIdentificacionAportante,
						reg.regDigVerAportante,
						regDireccion,regCodCiudad,regCodDepartamento,regTelefono,regFax,regEmail,regFechaMatricula,regNaturalezaJuridica,
						ISNULL(reg.regOUTMarcaSucursalPILA,0), 
						@MarcaCreacionAportante solicitud,						 
						'EMPLEADOR' tipoCreacion, 
						dbo.GetLocalDate () fechaHoraSolicitud,
						ISNULL(reg.regOUTEsEmpleadorReintegrable, 0), 
						reg.regOUTEnviadoAFiscalizacion, 
						reg.regOUTMotivoFiscalizacion, 
						reg.regOUTPrimerNombreAportante, 
						reg.regOUTSegundoNombreAportante, 
						reg.regOUTPrimerApellidoAportante, 
						reg.regOUTSegundoApellidoAportante
				FROM staging.RegistroGeneral reg with (nolock)
				INNER JOIN staging.RegistroDetallado red with (nolock) ON reg.regId = red.redRegistroGeneral
				LEFT JOIN dbo.TemAportante tap with (nolock) ON tap.TapIdTransaccion = red.redId
				WHERE reg.regId = @IdRegistroGeneral
				AND ISNULL(red.redCorrecciones, '') != 'A'
				AND (@EsSimulado = 0 
					OR (@EsSimulado = 1 AND red.redOUTRegistrado IS NOT NULL)
				)
				AND (@EsSimulado = 0 
					OR (@EsSimulado = 1 AND (redOUTAporteObligatorioMod > 0 OR redCorrecciones IS NULL))
				)
				AND tap.tapId IS NULL
				AND red.redOUTTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
				--AND reg.regOUTEstadoEmpleador IS NULL

				-- se agregan los aportantes PERSONA o ENTIDAD PAGADORA

				INSERT INTO dbo.TemAportante (TapIdTransaccion,tapRazonSocial,tapTipoDocAportante,TapIdAportante,tapDigVerAportante,
				tapDireccion,tapCodCiudad,tapCodDepartamento,tapTelefono,tapFax,tapEmail,tapFechaMatricula,tapNaturalezaJuridica,tapMarcaSucursal,
				tapMarcaCreacion,tapTipoSolicitud,tapFechaHoraSolicitud,tapNombreTramitador,tapTipoDocTramitador,tapIdTramitador,
				tapDigVerTramitador,tapEsEmpleadorReintegrable,tapEnviadoAFiscalizacion,tapMotivoFiscalizacion,
				tapPrimerNombreAportante, tapSegundoNombreAportante, tapPrimerApellidoAportante, tapSegundoApellidoAportante)
				SELECT	redId, 
						red.redPrimerNombre + ISNULL(' ' + red.redSegundoNombre + ' ', ' ') + red.redPrimerApellido + ISNULL(' ' + red.redSegundoApellido, '') nombreAportante,
						red.redTipoIdentificacionCotizante,
						red.redNumeroIdentificacionCotizante,
						NULL,
						regDireccion,regCodCiudad,regCodDepartamento,regTelefono,regFax,regEmail,regFechaMatricula,regNaturalezaJuridica,
						ISNULL(reg.regOUTMarcaSucursalPILA,0), 
						@MarcaCreacionAportante solicitud,						 
						'PERSONA' tipoCreacion, 
						dbo.GetLocalDate () fechaHoraSolicitud,
						CASE 
							WHEN (reg.regTipoIdentificacionAportante != red.redTipoIdentificacionCotizante OR reg.regNumeroIdentificacionAportante != red.redNumeroIdentificacionCotizante)
							THEN reg.regNombreAportante ELSE NULL
						END nombreTramitador,
						CASE 
							WHEN (reg.regTipoIdentificacionAportante != red.redTipoIdentificacionCotizante OR reg.regNumeroIdentificacionAportante != red.redNumeroIdentificacionCotizante)
							THEN reg.regTipoIdentificacionAportante ELSE NULL
						END tipoIdTramitador,
						CASE 
							WHEN (reg.regTipoIdentificacionAportante != red.redTipoIdentificacionCotizante OR reg.regNumeroIdentificacionAportante != red.redNumeroIdentificacionCotizante)
							THEN reg.regNumeroIdentificacionAportante ELSE NULL
						END numIdTramitador,
						CASE 
							WHEN (reg.regTipoIdentificacionAportante != red.redTipoIdentificacionCotizante OR reg.regNumeroIdentificacionAportante != red.redNumeroIdentificacionCotizante)
							THEN reg.regDigVerAportante ELSE NULL
						END dvTramitador,
						ISNULL(reg.regOUTEsEmpleadorReintegrable, 0), 
						red.redOUTEnviadoAFiscalizacionInd, 
						red.redOUTMotivoFiscalizacionInd, 
						red.redPrimerNombre, 
						red.redSegundoNombre, 
						red.redPrimerApellido, 
						red.redSegundoApellido 
				FROM staging.RegistroGeneral reg with (nolock)
				INNER JOIN staging.RegistroDetallado red with (nolock) ON reg.regId = red.redRegistroGeneral
				LEFT JOIN dbo.TemAportante tap with (nolock) ON tap.TapIdTransaccion = red.redId
				WHERE reg.regId = @IdRegistroGeneral
				AND ISNULL(red.redCorrecciones, '') != 'A'
				AND (@EsSimulado = 0 
					OR (@EsSimulado = 1 AND red.redOUTRegistrado IS NOT NULL)
				)
				AND (@EsSimulado = 0 
					OR (@EsSimulado = 1 AND (redOUTAporteObligatorioMod > 0 OR redCorrecciones IS NULL))
				)
				AND tap.tapId IS NULL
				AND red.redOUTTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE'
				--AND reg.regOUTEstadoEmpleador IS NULL



				--print '						Insertar TemCotizante'
				--INSERT INTO @temCotizanteTemp (tctIdTransaccion, tctTipoCotizante, tctMarcaCreacion)
				--SELECT DISTINCT redId, redOUTTipoAfiliado, 
				--	CASE WHEN redOUTEstadoSolicitante IS NULL THEN 'CREADO_DESDE_PILA' ELSE NULL END
				--FROM staging.RegistroDetallado red
				--INNER JOIN staging.RegistroGeneral reg ON red.redRegistroGeneral = reg.regId 
				--WHERE ISNULL(red.redTipoCotizante,'') NOT IN (SELECT tipoCotizante FROM @TablaIndependientes)
				--AND reg.regId = @IdRegistroGeneral

				INSERT INTO dbo.TemCotizante (tctIdTransaccion,tctTipoIdCotizante,tctIdCotizante,tctTipoCotizante,tctPrimerApellido,tctSegundoApellido,tctPrimerNombre,tctSegundoNombre,tctCodigoDepartamento,tctCodigoMunicipio,tctTipoIdEmpleador,tctIdEmpleador,
				tctCodSucursal,tctNomSucursal,
				tctCodSucursalPILA,tctNomSucursalPILA,
				tctFechaHoraSolicitud,tctMarcaCreacion,tctTipoSolicitud,tctEsFallecido,tctEsTrabajadorReintegrable,tctGrupoFamiliarReintegrable)
				SELECT redId,redTipoIdentificacionCotizante,redNumeroIdentificacionCotizante,redOUTTipoAfiliado,redPrimerApellido,redSegundoApellido,redPrimerNombre,redSegundoNombre,redCodDepartamento,redCodMunicipio,regTipoIdentificacionAportante,regNumeroIdentificacionAportante,
				ISNULL(redOUTCodSucursal, @codigoSucursalPrincipal) AS codigoSucursal, 
				ISNULL(redOUTNomSucursal, @nombreSucursalPrincipal) AS nombreSucursal, 
				ISNULL(regCodSucursal, @codigoSucursalPrincipal) AS codigoSucursalPILA, 
				ISNULL(regNomSucursal, @nombreSucursalPrincipal) AS nombreSucursalPILA, 
				dbo.GetLocalDate(), CASE WHEN redOUTEstadoSolicitante IS NULL THEN 'CREADO_DESDE_PILA' ELSE NULL END AS MarcaCreacionCotizante,
				redOUTTipoAfiliado AS TipoSolicitudCotizante
				,CASE 
					WHEN red.redOUTFechaFallecimiento < CONVERT(DATE, reg.regPeriodoAporte +'-01', 120) THEN 1
					ELSE 0 END AS EsFallecido
				,ISNULL(redOUTEsTrabajadorReintegrable, 0) esReintegrable
				,ISNULL(redOUTGrupoFamiliarReintegrable, 0) esGrupoReintegrable
				FROM staging.RegistroDetallado red with (nolock)
				INNER JOIN staging.RegistroGeneral reg with (nolock) ON red.redRegistroGeneral = reg.regId
				LEFT JOIN dbo.TemCotizante tct with (nolock) ON tct.tctIdTransaccion = red.redId
				WHERE ISNULL(red.redTipoCotizante,'') NOT IN (SELECT tipoCotizante FROM #TablaIndependientes)
				AND reg.regId = @IdRegistroGeneral
				AND ISNULL(red.redCorrecciones,'') != 'A'
				AND (@EsSimulado = 0 
					OR (@EsSimulado = 1 AND red.redOUTRegistrado IS NOT NULL)
				)
				AND (@EsSimulado = 0 
					OR (@EsSimulado = 1 AND (redOUTAporteObligatorioMod > 0 OR redCorrecciones IS NULL))
				)
				AND tct.tctId IS NULL
				AND redOUTTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
				--AND red.redOUTEstadoSolicitante IS NULL
							

				INSERT INTO dbo.TemCotizante (tctIdTransaccion,tctTipoIdCotizante,tctIdCotizante,tctTipoCotizante,tctPrimerApellido,tctSegundoApellido,tctPrimerNombre,tctSegundoNombre,tctCodigoDepartamento,tctCodigoMunicipio,tctTipoIdEmpleador,tctIdEmpleador,
				tctCodSucursal,tctNomSucursal,
				tctCodSucursalPILA,tctNomSucursalPILA,
				tctFechaHoraSolicitud,tctMarcaCreacion,tctTipoSolicitud,tctEsFallecido,tctEsTrabajadorReintegrable,tctGrupoFamiliarReintegrable)
				SELECT redId,redTipoIdentificacionCotizante,redNumeroIdentificacionCotizante,redOUTTipoAfiliado,redPrimerApellido,redSegundoApellido,redPrimerNombre,redSegundoNombre,redCodDepartamento,redCodMunicipio,regTipoIdentificacionAportante,regNumeroIdentificacionAportante,
				NULL AS CodSucursal, NULL AS NomSucursal,NULL AS CodSucursalPILA, NULL AS NomSucursalPILA,
				dbo.GetLocalDate(), CASE WHEN redOUTEstadoSolicitante IS NULL THEN 'CREADO_DESDE_PILA' ELSE NULL END AS MarcaCreacionCotizante,
				redOUTTipoAfiliado AS TipoSolicitudCotizante
				,CASE 
					WHEN redOUTFechaFallecimiento < CONVERT(DATE, regPeriodoAporte +'-01', 120) THEN 1
					ELSE 0 END AS EsFallecido
				,ISNULL(redOUTEsTrabajadorReintegrable, 0) esReintegrable
				,ISNULL(redOUTGrupoFamiliarReintegrable, 0) esGrupoReintegrable
				FROM staging.RegistroDetallado with (nolock)
				INNER JOIN staging.RegistroGeneral with (nolock) ON redRegistroGeneral = regId
				LEFT JOIN dbo.TemCotizante tct with (nolock) ON tct.tctIdTransaccion = redId
				WHERE redTipoCotizante IN (SELECT tipoCotizante FROM #TablaIndependientes)
				AND regId = @IdRegistroGeneral
				AND ISNULL(redCorrecciones,'') != 'A'
				AND (@EsSimulado = 0 
					OR (@EsSimulado = 1 AND redOUTRegistrado IS NOT NULL)
				)
				AND (@EsSimulado = 0 
					OR (@EsSimulado = 1 AND (redOUTAporteObligatorioMod > 0 OR redCorrecciones IS NULL))
				)
				AND tct.tctId IS NULL
				AND redOUTTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE'
				--AND redOUTEstadoSolicitante IS NULL
			
				--print '                     Estableciendo marca REGISTRO/RELACION del aporte'
			
					(SELECT  
						@MarcaRegistroRelacionAporte =  CASE 
							WHEN regOUTEstadoEmpleador IS NULL THEN 'CREAR_Y_RELACIONAR_EMPLEADOR'
							WHEN regOUTEstadoEmpleador IN ('NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES','NO_FORMALIZADO_CON_INFORMACION') THEN 'RELACIONADO'
							ELSE 'REGISTRADO'
						END
					FROM staging.RegistroGeneral reg
					WHERE reg.regId = @IdRegistroGeneral)
			
				--print '						Insertar TemAporte'

				INSERT INTO dbo.TemAporte
				(temIdTransaccion,temMarcaAporteSimulado,temMarcaAporteManual,temRegistroGeneral,temPeriodoAporte,temValTotalApoObligatorio,temValorIntMoraGeneral,temFechaRecaudo,
				temTipoIdAportante,temNumeroIdAportante,temCodEntidadFinanciera,temOperadorInformacion,temModalidadPlanilla,temModalidadRecaudoAporte,
				temApoConDetalle,temFormaReconocimientoAporte,temNumeroCuenta,
				temRegistroDetallado,temTipoIdCotizante,temNumeroIdCotizante,
				temDiasCotizados,temHorasLaboradas,temSalarioBasico,temMunicipioLaboral,temDepartamentoLaboral,temValorIBC,temTarifa,temSalarioIntegral,
				temAporteObligatorio,temValorSaldoAporte,temValorIntMoraDetalle,temCorrecciones,temFechaProcesamiento,temEstadoAporteRecaudo,temEstadoAporteAjuste,temEstadoRegistroAporte,
				temMarcaValRegistroAporte,temEstadoValRegistroAporte,temUsuarioAprobadorAporte,temPresentaNovedad,temNumeroPlanillaManual,temEnProceso)
				SELECT
					redId AS temIdTransaccion,
					@EsSimulado AS temRegistroAporteSimulado,
					@EsProcesoManual AS temMarcaAporteManual,
					reg.regId AS temRegistroGeneral,
					reg.regPeriodoAporte AS temPeriodoAporte,
					aporteTotalDet.valTotalApoObligatorio AS temValTotalApoObligatorio,
					aporteTotalDet.valorIntMora AS temValorIntMora,
					
					CASE WHEN regFechaPagoAporte IS NULL THEN regFechaRecaudo ELSE regFechaPagoAporte END AS temFechaRecaudo,

					regTipoIdentificacionAportante AS temTipoIdAportante,
					regNumeroIdentificacionAportante AS temNumeroIdAportante,

					regCodigoEntidadFinanciera AS temCodEntidadFinanciera,
					regOperadorInformacion AS temOperadorInformacion, 

					CASE regModalidadPlanilla WHEN 1 THEN 'ELECTRONICA' WHEN 2 THEN 'ASISTIDA' END AS temModalidadPlanilla,
					CASE 
						WHEN @EsSimulado = 1 THEN 'PILA_MANUAL' 
						WHEN @EsProcesoManual = 1 THEN 'MANUAL' 
						ELSE 'PILA' 
					END AS temModalidadRecaudoAporte,

					1 AS temApoConDetalle,
					CASE 
						WHEN temA.tapTipoSolicitud = 'EMPLEADOR' AND @MarcaRegistroRelacionAporte = 'REGISTRADO' THEN 'RECONOCIMIENTO_AUTOMATICO_OPORTUNO'
						WHEN temC.tctTipoCotizante <> 'TRABAJADOR_DEPENDIENTE' AND ISNULL(temC.tctMarcaCreacion,'') <> 'CREADO_DESDE_PILA' AND ISNULL(red.redOUTEstadoSolicitante,'') NOT IN ('NO_FORMALIZADO_CON_INFORMACION', 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES') THEN 'RECONOCIMIENTO_AUTOMATICO_OPORTUNO' 
						ELSE NULL
					END AS temFormaReconocimientoAporte,
					regNumeroCuenta AS temNumeroCuenta,
					
					redId AS temRegistroDetallado,
					red.redTipoIdentificacionCotizante AS temTipoIdCotizante,
					red.redNumeroIdentificacionCotizante AS temNumeroIdCotizante,

					CASE WHEN @esSimulado = 0 OR regOUTMotivoProcesoManual = 'ARCHIVO_ADICION' THEN redDiasCotizados ELSE redOUTDiasCotizadosMod END AS temDiasCotizados,
					redHorasLaboradas AS temHorasLaboradas,
					redSalarioBasico AS temSalarioBasico,
					redCodMunicipio AS temMunicipioLaboral,
					redCodDepartamento AS temDepartamentoLaboral,
					CASE WHEN @esSimulado = 0 OR regOUTMotivoProcesoManual = 'ARCHIVO_ADICION' THEN redValorIBC ELSE redOUTValorIBCMod END AS temValorIBC,
					CASE WHEN @esSimulado = 0 OR regOUTMotivoProcesoManual = 'ARCHIVO_ADICION' THEN redTarifa ELSE redOUTTarifaMod END AS temTarifa,
					CASE redSalarioIntegral WHEN 'S' THEN 1 ELSE 0 END AS temSalarioIntegral,

					CASE WHEN @esSimulado = 0 OR regOUTMotivoProcesoManual = 'ARCHIVO_ADICION' THEN redAporteObligatorio ELSE redOUTAporteObligatorioMod END AS temAporteObligatorio,
					0 AS temValorSaldoAporte,
					CASE WHEN @esSimulado = 0 OR regOUTMotivoProcesoManual = 'ARCHIVO_ADICION' THEN redOUTValorMoraCotizante ELSE redOUTValorMoraCotizanteMod END AS temValorIntMoraDetalle,
					redCorrecciones AS temCorrecciones,
					dbo.GetLocalDate() AS temFechaProcesamiento,
					'VIGENTE' AS temEstadoAporteRecaudo,
					'VIGENTE' AS temEstadoAporteAjuste,
					CASE 
						WHEN temA.tapTipoSolicitud = 'EMPLEADOR' AND @MarcaRegistroRelacionAporte <> 'CREAR_Y_RELACIONAR_EMPLEADOR' THEN @MarcaRegistroRelacionAporte
						WHEN temC.tctTipoCotizante <> 'TRABAJADOR_DEPENDIENTE' AND ISNULL(temC.tctMarcaCreacion,'') <> 'CREADO_DESDE_PILA' AND ISNULL(red.redOUTEstadoSolicitante,'') NOT IN ('', 'NO_FORMALIZADO_CON_INFORMACION', 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES') THEN 'REGISTRADO' 
						ELSE 'RELACIONADO'
					END AS temEstadoRegistroAporte,
					redOUTMarcaValRegistroAporte,
					redOUTEstadoRegistroAporte,
					redUsuarioAprobadorAporte, 
					CASE 
						WHEN ISNULL(regNovedadFutura, 0) != 1
							AND (redNovIngreso IS NOT NULL OR
								redNovRetiro IS NOT NULL OR
								redNovVSP IS NOT NULL OR
								redNovVST IS NOT NULL OR
								redNovSLN IS NOT NULL OR
								redNovIGE IS NOT NULL OR
								redNovLMA IS NOT NULL OR
								redNovVACLR IS NOT NULL OR
								ISNULL(redDiasIRL, 0) != 0 OR
								redNovSUS IS NOT NULL)
						THEN 1 ELSE 0 
					END AS temPresentaNovedad,
					CASE WHEN reg.regRegistroControlManual IS NOT NULL THEN regNumPlanilla ELSE NULL END, 
					0 AS temEnProceso
				FROM staging.RegistroGeneral reg with (nolock)
				INNER JOIN staging.RegistroDetallado red with (nolock) ON reg.regId = red.redRegistroGeneral
				INNER JOIN TemAportante temA with (nolock) ON temA.TapIdTransaccion = red.redId
				--INNER JOIN @temAportanteTemp temA ON temA.TapIdTransaccion = red.redId
				INNER JOIN TemCotizante temC with (nolock) ON temC.tctIdTransaccion = red.redId
				--INNER JOIN @temCotizanteTemp temC ON temC.tctIdTransaccion = red.redId
				LEFT JOIN (
					SELECT reg2.regId, 
						SUM(CASE WHEN @esSimulado = 0 OR reg2.regOUTMotivoProcesoManual = 'ARCHIVO_ADICION' THEN redAporteObligatorio ELSE redOUTAporteObligatorioMod END) valTotalApoObligatorio, 
						SUM(CASE WHEN @esSimulado = 0 OR reg2.regOUTMotivoProcesoManual = 'ARCHIVO_ADICION' THEN redOUTValorMoraCotizante ELSE redOUTValorMoraCotizanteMod END) valorIntMora
					FROM staging.RegistroDetallado red2 
						INNER JOIN staging.RegistroGeneral reg2 ON reg2.regId = red2.redRegistroGeneral
					WHERE red2.redOUTTipoAfiliado != 'TRABAJADOR_INDEPENDIENTE'
					GROUP BY reg2.regId
				) aporteTotalDet ON aporteTotalDet.regId = reg.regId
				LEFT JOIN dbo.TemAporte tem with (nolock) ON tem.temIdTransaccion = red.redId
				WHERE reg.regId = @IdRegistroGeneral
				AND ISNULL(red.redCorrecciones, '') != 'A'
				AND (@EsSimulado = 0 
					OR (@EsSimulado = 1 AND red.redOUTRegistrado IS NOT NULL)
				)
				AND (@EsSimulado = 0 
					OR (@EsSimulado = 1 AND (redOUTAporteObligatorioMod > 0 OR redCorrecciones IS NULL))
				)
				AND tem.temId IS NULL
				AND red.redOUTTipoAfiliado != 'TRABAJADOR_INDEPENDIENTE'

				

				INSERT INTO dbo.TemAporte
				(temIdTransaccion,temMarcaAporteSimulado,temMarcaAporteManual,temRegistroGeneral,temPeriodoAporte,temValTotalApoObligatorio,temValorIntMoraGeneral,temFechaRecaudo,
				temTipoIdAportante,temNumeroIdAportante,temCodEntidadFinanciera,temOperadorInformacion,temModalidadPlanilla,temModalidadRecaudoAporte,
				temApoConDetalle,temFormaReconocimientoAporte,temNumeroCuenta,
				temRegistroDetallado,temTipoIdCotizante,temNumeroIdCotizante,
				temDiasCotizados,temHorasLaboradas,temSalarioBasico,temMunicipioLaboral,temDepartamentoLaboral,temValorIBC,temTarifa,temSalarioIntegral,
				temAporteObligatorio,temValorSaldoAporte,temValorIntMoraDetalle,temCorrecciones,temFechaProcesamiento,temEstadoAporteRecaudo,temEstadoAporteAjuste,temEstadoRegistroAporte,
				temMarcaValRegistroAporte,temEstadoValRegistroAporte,temUsuarioAprobadorAporte,temPresentaNovedad,temNumeroPlanillaManual,temEnProceso)
				SELECT
					redId AS temIdTransaccion,
					@EsSimulado AS temRegistroAporteSimulado,
					@EsProcesoManual AS temMarcaAporteManual,
					reg.regId AS temRegistroGeneral,
					reg.regPeriodoAporte AS temPeriodoAporte,
					CASE 
						WHEN red.redOUTTipoAfiliado != 'TRABAJADOR_INDEPENDIENTE' THEN regValTotalApoObligatorio
						ELSE aporteTotalDet.valTotalApoObligatorio
					END AS temValTotalApoObligatorio,
					CASE 
						WHEN red.redOUTTipoAfiliado != 'TRABAJADOR_INDEPENDIENTE' THEN regValorIntMora
						ELSE aporteTotalDet.valorIntMora
					END AS temValorIntMora,

					CASE WHEN regFechaPagoAporte IS NULL THEN regFechaRecaudo ELSE regFechaPagoAporte END AS temFechaRecaudo,

					CASE 
						WHEN red.redOUTTipoAfiliado != 'TRABAJADOR_INDEPENDIENTE' THEN regTipoIdentificacionAportante
						ELSE red.redTipoIdentificacionCotizante
					END AS temTipoIdAportante,
					CASE 
						WHEN red.redOUTTipoAfiliado != 'TRABAJADOR_INDEPENDIENTE' THEN regNumeroIdentificacionAportante 
						ELSE red.redNumeroIdentificacionCotizante
					END AS temNumeroIdAportante,

					regCodigoEntidadFinanciera AS temCodEntidadFinanciera,
					regOperadorInformacion AS temOperadorInformacion, 

					CASE regModalidadPlanilla WHEN 1 THEN 'ELECTRONICA' WHEN 2 THEN 'ASISTIDA' END AS temModalidadPlanilla,
					CASE 
						WHEN @EsSimulado = 1 THEN 'PILA_MANUAL' 
						WHEN @EsProcesoManual = 1 THEN 'MANUAL' 
						ELSE 'PILA'
					END AS temModalidadRecaudoAporte,

					1 AS temApoConDetalle,
					CASE 
						WHEN temA.tapTipoSolicitud = 'EMPLEADOR' AND @MarcaRegistroRelacionAporte = 'REGISTRADO' THEN 'RECONOCIMIENTO_AUTOMATICO_OPORTUNO'
						WHEN temC.tctTipoCotizante <> 'TRABAJADOR_DEPENDIENTE' AND ISNULL(temC.tctMarcaCreacion,'') <> 'CREADO_DESDE_PILA' AND ISNULL(red.redOUTEstadoSolicitante,'') NOT IN ('NO_FORMALIZADO_CON_INFORMACION', 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES') THEN 'RECONOCIMIENTO_AUTOMATICO_OPORTUNO' 
						ELSE NULL
					END AS temFormaReconocimientoAporte,
					regNumeroCuenta AS temNumeroCuenta,
					
					redId AS temRegistroDetallado,
					red.redTipoIdentificacionCotizante AS temTipoIdCotizante,
					red.redNumeroIdentificacionCotizante AS temNumeroIdCotizante,
					
					CASE WHEN @esSimulado = 0 OR regOUTMotivoProcesoManual = 'ARCHIVO_ADICION' THEN redDiasCotizados ELSE redOUTDiasCotizadosMod END AS temDiasCotizados,
					redHorasLaboradas AS temHorasLaboradas,
					redSalarioBasico AS temSalarioBasico,
					redCodMunicipio AS temMunicipioLaboral,
					redCodDepartamento AS temDepartamentoLaboral,
					CASE WHEN @esSimulado = 0 OR regOUTMotivoProcesoManual = 'ARCHIVO_ADICION' THEN redValorIBC ELSE redOUTValorIBCMod END AS temValorIBC,
					CASE WHEN @esSimulado = 0 OR regOUTMotivoProcesoManual = 'ARCHIVO_ADICION' THEN redTarifa ELSE redOUTTarifaMod END AS temTarifa,
					CASE redSalarioIntegral WHEN 'S' THEN 1 ELSE 0 END AS temSalarioIntegral,
					
					CASE WHEN @esSimulado = 0 OR regOUTMotivoProcesoManual = 'ARCHIVO_ADICION' THEN redAporteObligatorio ELSE redOUTAporteObligatorioMod END AS temAporteObligatorio,
					0 AS temValorSaldoAporte,
					CASE WHEN @esSimulado = 0 OR regOUTMotivoProcesoManual = 'ARCHIVO_ADICION' THEN redOUTValorMoraCotizante ELSE redOUTValorMoraCotizanteMod END AS temValorIntMoraDetalle,
					redCorrecciones AS temCorrecciones,
					dbo.GetLocalDate() AS temFechaProcesamiento,
					'VIGENTE' AS temEstadoAporteRecaudo,
					'VIGENTE' AS temEstadoAporteAjuste,
					CASE 
						WHEN temA.tapTipoSolicitud = 'EMPLEADOR' AND @MarcaRegistroRelacionAporte <> 'CREAR_Y_RELACIONAR_EMPLEADOR' THEN @MarcaRegistroRelacionAporte
						WHEN temC.tctTipoCotizante <> 'TRABAJADOR_DEPENDIENTE' AND ISNULL(temC.tctMarcaCreacion,'') <> 'CREADO_DESDE_PILA' AND ISNULL(red.redOUTEstadoSolicitante,'') NOT IN ('', 'NO_FORMALIZADO_CON_INFORMACION', 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES') THEN 'REGISTRADO' 
						ELSE 'RELACIONADO'
					END AS temEstadoRegistroAporte,
					redOUTMarcaValRegistroAporte,
					redOUTEstadoRegistroAporte,
					redUsuarioAprobadorAporte, 
					CASE 
						WHEN ISNULL(regNovedadFutura, 0) != 1
							AND (redNovIngreso IS NOT NULL OR
								redNovRetiro IS NOT NULL OR
								redNovVSP IS NOT NULL OR
								redNovVST IS NOT NULL OR
								redNovSLN IS NOT NULL OR
								redNovIGE IS NOT NULL OR
								redNovLMA IS NOT NULL OR
								redNovVACLR IS NOT NULL OR
								ISNULL(redDiasIRL, 0) != 0 OR
								redNovSUS IS NOT NULL)
						THEN 1 ELSE 0 
					END AS temPresentaNovedad,
					CASE WHEN reg.regRegistroControlManual IS NOT NULL THEN regNumPlanilla ELSE NULL END, 
					0 AS temEnProceso
				FROM staging.RegistroGeneral reg with (nolock)
				INNER JOIN staging.RegistroDetallado red with (nolock) ON reg.regId = red.redRegistroGeneral
				INNER JOIN TemAportante temA with (nolock) ON temA.TapIdTransaccion = red.redId
				--INNER JOIN @temAportanteTemp temA ON temA.TapIdTransaccion = red.redId
				INNER JOIN TemCotizante temC with (nolock) ON temC.tctIdTransaccion = red.redId
				--INNER JOIN @temCotizanteTemp temC ON temC.tctIdTransaccion = red.redId
				LEFT JOIN (
					SELECT red2.redTipoIdentificacionCotizante, red2.redNumeroIdentificacionCotizante, reg2.regId, 
						SUM(CASE WHEN @esSimulado = 0 OR reg2.regOUTMotivoProcesoManual = 'ARCHIVO_ADICION' THEN redAporteObligatorio ELSE redOUTAporteObligatorioMod END) valTotalApoObligatorio, 
						SUM(CASE WHEN @esSimulado = 0 OR reg2.regOUTMotivoProcesoManual = 'ARCHIVO_ADICION' THEN redOUTValorMoraCotizante ELSE redOUTValorMoraCotizanteMod END) valorIntMora
					FROM staging.RegistroDetallado red2 with (nolock) 
						INNER JOIN staging.RegistroGeneral reg2 with (nolock) ON reg2.regId = red2.redRegistroGeneral
					GROUP BY red2.redTipoIdentificacionCotizante, red2.redNumeroIdentificacionCotizante, reg2.regId
				) aporteTotalDet ON (aporteTotalDet.redTipoIdentificacionCotizante = red.redTipoIdentificacionCotizante
					AND aporteTotalDet.redNumeroIdentificacionCotizante = red.redNumeroIdentificacionCotizante
					AND aporteTotalDet.regId = reg.regId)
				LEFT JOIN dbo.TemAporte tem with (nolock) ON tem.temIdTransaccion = red.redId
				WHERE reg.regId = @IdRegistroGeneral
				AND ISNULL(red.redCorrecciones, '') != 'A'
				AND (@EsSimulado = 0 
					OR (@EsSimulado = 1 AND red.redOUTRegistrado IS NOT NULL)
				)
				AND (@EsSimulado = 0 
					OR (@EsSimulado = 1 AND (redOUTAporteObligatorioMod > 0 OR redCorrecciones IS NULL))
				)
				AND tem.temId IS NULL
				AND red.redOUTTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE'





				-- si la marca de sucursal PILA está activa, se preparan las solicitudes de novedad de cambio de sucursal que apliquen
				IF @MarcaSucursalPILA = 1
				BEGIN
					INSERT INTO TemNovedad (tenIdTransaccion,tenMarcaNovedadSimulado,tenMarcaNovedadManual,tenRegistroGeneral,tenRegistroDetallado,tenTipoIdAportante,tenNumeroIdAportante
											,tenTipoIdCotizante,tenNumeroIdCotizante,tenTipoCotizante,tenModalidadRecaudoAporte,tenTipoTransaccion, tenEsIngreso, tenEsRetiro, tenFechaInicioNovedad,tenFechaFinNovedad
											,tenAccionNovedad,tenMensajeNovedad,tenValor,tenRegistroDetalladoNovedad, tenEnProceso)
					SELECT 
						redId,
						@EsSimulado,
						@EsProcesoManual,
						regId,
						redId,
						regTipoIdentificacionAportante,
						regNumeroIdentificacionAportante,
						redTipoIdentificacionCotizante,
						redNumeroIdentificacionCotizante,
						'TRABAJADOR_DEPENDIENTE',
						CASE WHEN @EsSimulado = 1 THEN 'PILA_MANUAL' ELSE (CASE WHEN @EsProcesoManual = 1 THEN 'MANUAL' ELSE 'PILA' END) END,
						'CAMBIO_SUCURSA_TRABAJADOR_DEPENDIENTE_DEPWEB',
						0 AS tenEsIngreso,
						0 AS tenEsRetiro,
						NULL,
						NULL,
						'APLICAR_NOVEDAD',
						NULL,
						ISNULL(regCodSucursal,regOUTCodSucursalPrincipal), 
						-1,
						0 as tenEnProceso
					FROM staging.RegistroDetallado red with (nolock)
					INNER JOIN staging.RegistroGeneral reg with (nolock) ON red.redRegistroGeneral = reg.regId
					LEFT JOIN dbo.TemNovedad ten with (nolock) ON ten.tenIdTransaccion = red.redId AND ten.tenRegistroDetalladoNovedad = -1
					WHERE reg.regId = @IdRegistroGeneral
					AND redTipoCotizante NOT IN (SELECT tipoCotizante FROM #TablaIndependientes)
					AND regOUTMarcaSucursalPILA = 1
					AND regOUTCodSucursalPrincipal IS NOT NULL
					AND ISNULL(regCodSucursal,regOUTCodSucursalPrincipal) <> isnull(redOUTCodSucursal,'')
					AND ISNULL(regNomSucursal,regOUTNomSucursalPrincipal) <> isnull(redOUTNomSucursal,'')
					AND ISNULL(red.redCorrecciones, '') != 'A'
					AND (@EsSimulado = 0 
						OR (@EsSimulado = 1 AND red.redOUTRegistrado IS NOT NULL)
					)
					AND (@EsSimulado = 0 
						OR (@EsSimulado = 1 AND (redOUTAporteObligatorioMod > 0 OR redCorrecciones IS NULL))
					)
					AND ten.tenId IS NULL
				END
			END -- Se cierra proceso de sucursal. 
		END
			
		--print '				VerificarAprobacionPen'
		IF NOT EXISTS (			
			SELECT TOP 1 1 
			FROM staging.RegistroDetallado red with (nolock) 
			INNER JOIN staging.RegistroGeneral reg with (nolock) ON red.redRegistroGeneral = reg.regId
			WHERE red.redRegistroGeneral = @IdRegistroGeneral
			AND red.redOUTEstadoRegistroAporte IN ('NO_VALIDADO_BD', 'NO_OK')
		) AND @EsArchivoPensionados = 1
		BEGIN
			DECLARE @TipoAportePensionado VARCHAR(20)
			
			SELECT @TipoAportePensionado = CASE WHEN (
				SELECT COUNT(1) 
				FROM staging.RegistroGeneral i1 
					INNER JOIN staging.RegistroDetallado i2 with (nolock) ON (
						i1.regId = i2.redRegistroGeneral
						AND	i1.regTipoIdentificacionAportante = i2.redTipoIdentificacionCotizante
						AND i1.regNumeroIdentificacionAportante = i2.redNumeroIdentificacionCotizante
					)
				WHERE i1.regId = @IdRegistroGeneral) > 0 AND COUNT(1) = 1 THEN 'APORTE_PROPIO' ELSE 'APORTE_TERCERO' END,
				@EstadoCotizante = i2_ext.redOUTEstadoSolicitante 
			FROM staging.RegistroDetallado i2_ext with (nolock)
			WHERE i2_ext.redRegistroGeneral = @IdRegistroGeneral
			GROUP BY i2_ext.redOUTEstadoSolicitante

			IF @TipoAportePensionado = 'APORTE_PROPIO'
			BEGIN
				SET @TipoSolicitudAportante = 'PERSONA'
			END
			ELSE
			BEGIN
				SET @TipoSolicitudAportante = 'ENTIDAD_PAGADORA'
			END
				
			IF @TipoSolicitudAportante = 'PERSONA'
			BEGIN
				INSERT INTO dbo.TemAportante (TapIdTransaccion,tapRazonSocial,tapTipoDocAportante,TapIdAportante,tapDigVerAportante,
				tapDireccion,tapCodCiudad,tapCodDepartamento,tapTelefono,tapFax,tapEmail,tapFechaMatricula,tapNaturalezaJuridica,tapMarcaSucursal,
				tapMarcaCreacion,tapTipoSolicitud,tapFechaHoraSolicitud,tapEsEmpleadorReintegrable,tapEnviadoAFiscalizacion,tapMotivoFiscalizacion,
				tapPrimerNombreAportante, tapSegundoNombreAportante, tapPrimerApellidoAportante, tapSegundoApellidoAportante)
				SELECT	redId,
						reg.regNombreAportante,
						reg.regTipoIdentificacionAportante,
						reg.regNumeroIdentificacionAportante,
						reg.regDigVerAportante,
						regDireccion,regCodCiudad,regCodDepartamento,regTelefono,regFax,regEmail,regFechaMatricula,regNaturalezaJuridica,
						ISNULL(reg.regOUTMarcaSucursalPILA,0), 
						CASE WHEN redOUTEstadoSolicitante IS NULL THEN 'CREADO_DESDE_PILA' ELSE NULL END AS solicitud,						 
						@TipoSolicitudAportante tipoCreacion, 
						dbo.GetLocalDate () fechaHoraSolicitud,
						ISNULL(reg.regOUTEsEmpleadorReintegrable, 0), 
						reg.regOUTEnviadoAFiscalizacion,
						reg.regOUTMotivoFiscalizacion, 
						CASE WHEN @TipoSolicitudAportante != 'ENTIDAD_PAGADORA' THEN reg.regOUTPrimerNombreAportante ELSE red.redPrimerNombre END primerNombre, 
						CASE WHEN @TipoSolicitudAportante != 'ENTIDAD_PAGADORA' THEN reg.regOUTSegundoNombreAportante ELSE red.redSegundoNombre END SegundoNombre, 
						CASE WHEN @TipoSolicitudAportante != 'ENTIDAD_PAGADORA' THEN reg.regOUTPrimerApellidoAportante ELSE red.redPrimerApellido END primerApellido, 
						CASE WHEN @TipoSolicitudAportante != 'ENTIDAD_PAGADORA' THEN reg.regOUTSegundoApellidoAportante ELSE red.redSegundoApellido END segundoApellido 
				FROM staging.RegistroGeneral reg with (nolock)
				INNER JOIN staging.RegistroDetallado red with (nolock) ON reg.regId = red.redRegistroGeneral
				LEFT JOIN dbo.TemAportante tap with (nolock) ON tap.TapIdTransaccion = red.redId
				WHERE reg.regId = @IdRegistroGeneral
				AND tap.tapId IS NULL
				--AND reg.regOUTEstadoEmpleador IS NULL
			END
			ELSE
			BEGIN	
				INSERT INTO dbo.TemAportante (TapIdTransaccion,tapRazonSocial,tapTipoDocAportante,TapIdAportante,tapDigVerAportante,
				tapDireccion,tapCodCiudad,tapCodDepartamento,tapTelefono,tapFax,tapEmail,tapFechaMatricula,tapNaturalezaJuridica,
				tapMarcaCreacion,tapTipoSolicitud,tapFechaHoraSolicitud,tapNombreTramitador,tapTipoDocTramitador,tapIdTramitador,
				tapDigVerTramitador,tapEsEmpleadorReintegrable,tapEnviadoAFiscalizacion,tapMotivoFiscalizacion,
				tapPrimerNombreAportante, tapSegundoNombreAportante, tapPrimerApellidoAportante, tapSegundoApellidoAportante)
					SELECT	redId, 
							red.redPrimerNombre + ISNULL(' ' + red.redSegundoNombre + ' ', ' ') + red.redPrimerApellido + ISNULL(' ' + red.redSegundoApellido, '') nombreAportante,
							red.redTipoIdentificacionCotizante,
							red.redNumeroIdentificacionCotizante,
							NULL, 
							regDireccion,regCodCiudad,regCodDepartamento,regTelefono,regFax,regEmail,regFechaMatricula,regNaturalezaJuridica,
							CASE WHEN regOUTEstadoEmpleador IS NULL THEN 'CREADO_DESDE_PILA' ELSE NULL END AS tapMarcaCreacion,
							@TipoSolicitudAportante AS tapTipoSolicitud, 
							dbo.GetLocalDate () fechaHoraSolicitud,
							reg.regNombreAportante,
							reg.regTipoIdentificacionAportante,
							reg.regNumeroIdentificacionAportante,  
							reg.regDigVerAportante,
							ISNULL(reg.regOUTEsEmpleadorReintegrable, 0), 
							reg.regOUTEnviadoAFiscalizacion,
							reg.regOUTMotivoFiscalizacion, 
							CASE WHEN @TipoSolicitudAportante != 'ENTIDAD_PAGADORA' THEN reg.regOUTPrimerNombreAportante ELSE red.redPrimerNombre END primerNombre, 
							CASE WHEN @TipoSolicitudAportante != 'ENTIDAD_PAGADORA' THEN reg.regOUTSegundoNombreAportante ELSE red.redSegundoNombre END SegundoNombre, 
							CASE WHEN @TipoSolicitudAportante != 'ENTIDAD_PAGADORA' THEN reg.regOUTPrimerApellidoAportante ELSE red.redPrimerApellido END primerApellido, 
							CASE WHEN @TipoSolicitudAportante != 'ENTIDAD_PAGADORA' THEN reg.regOUTSegundoApellidoAportante ELSE red.redSegundoApellido END segundoApellido 
				FROM staging.RegistroGeneral reg with (nolock)
				INNER JOIN staging.RegistroDetallado red with (nolock) ON reg.regId = red.redRegistroGeneral
				LEFT JOIN dbo.TemAportante tap with (nolock) ON tap.TapIdTransaccion = red.redId
				WHERE reg.regId = @IdRegistroGeneral
				AND tap.tapId IS NULL
				--AND reg.regOUTEstadoEmpleador IS NULL
			END
			
			--INSERT INTO @temCotizanteTemp (tctIdTransaccion, tctTipoCotizante, tctMarcaCreacion)
			--SELECT DISTINCT redId, 'PENSIONADO', 
			--	CASE WHEN redOUTEstadoSolicitante IS NULL THEN 'CREADO_DESDE_PILA' ELSE NULL END
			--FROM staging.RegistroDetallado
			--INNER JOIN staging.RegistroGeneral ON staging.RegistroDetallado.redRegistroGeneral = staging.RegistroGeneral.regId
			--WHERE staging.RegistroGeneral.regId = @IdRegistroGeneral

			INSERT INTO dbo.TemCotizante (tctIdTransaccion,tctTipoIdCotizante,tctIdCotizante,tctTipoCotizante,tctPrimerApellido,tctSegundoApellido,tctPrimerNombre,tctSegundoNombre,tctCodigoDepartamento,tctCodigoMunicipio,tctTipoIdEmpleador,tctIdEmpleador,
			tctCodSucursal,tctNomSucursal,
			tctCodSucursalPILA,tctNomSucursalPILA,
			tctFechaHoraSolicitud,tctMarcaCreacion,tctTipoSolicitud,tctEsFallecido,tctEsTrabajadorReintegrable,tctGrupoFamiliarReintegrable)
			SELECT DISTINCT redId,redTipoIdentificacionCotizante,redNumeroIdentificacionCotizante,'PENSIONADO' AS TipoCotizante,redPrimerApellido,redSegundoApellido,redPrimerNombre,redSegundoNombre,redCodDepartamento,redCodMunicipio,regTipoIdentificacionAportante,regNumeroIdentificacionAportante,
			NULL AS CodSucursal, NULL AS NomSucursal,NULL AS CodSucursalPILA, NULL AS NomSucursalPILA,
			dbo.GetLocalDate(), CASE WHEN redOUTEstadoSolicitante IS NULL THEN 'CREADO_DESDE_PILA' ELSE NULL END AS MarcaCreacionCotizante,
			'PENSIONADO' AS TipoSolicitudCotizante,
			CASE 
				WHEN staging.RegistroDetallado.redOUTFechaFallecimiento < CONVERT(DATE, staging.RegistroGeneral.regPeriodoAporte +'-01', 120) THEN 1
				ELSE 0 END AS EsFallecido
			,ISNULL(redOUTEsTrabajadorReintegrable, 0)
			,ISNULL(redOUTGrupoFamiliarReintegrable, 0) 
			FROM staging.RegistroDetallado with (nolock)
			INNER JOIN staging.RegistroGeneral with (nolock) ON staging.RegistroDetallado.redRegistroGeneral = staging.RegistroGeneral.regId
			LEFT JOIN dbo.TemCotizante tct with (nolock) ON tct.tctIdTransaccion = staging.RegistroDetallado.redId
			WHERE staging.RegistroGeneral.regId = @IdRegistroGeneral
			AND tct.tctId IS NULL
			--AND redOUTEstadoSolicitante IS NULL


			INSERT INTO dbo.TemAporte
			(temIdTransaccion,temMarcaAporteSimulado,temMarcaAporteManual,temRegistroGeneral,temPeriodoAporte,temValTotalApoObligatorio,temValorIntMoraGeneral,temFechaRecaudo,
			temTipoIdAportante,temNumeroIdAportante,temCodEntidadFinanciera,temOperadorInformacion,temModalidadPlanilla,temModalidadRecaudoAporte,
			temApoConDetalle,temFormaReconocimientoAporte,temNumeroCuenta,
			temRegistroDetallado,temTipoIdCotizante,temNumeroIdCotizante,
			temDiasCotizados,temHorasLaboradas,temSalarioBasico,temMunicipioLaboral,temDepartamentoLaboral,temValorIBC,temTarifa,temSalarioIntegral,
			temAporteObligatorio,temValorSaldoAporte,temValorIntMoraDetalle,temCorrecciones,temFechaProcesamiento,temEstadoAporteRecaudo,temEstadoAporteAjuste,temEstadoRegistroAporte,
			temMarcaValRegistroAporte,temEstadoValRegistroAporte,temUsuarioAprobadorAporte,temPresentaNovedad,temNumeroPlanillaManual,temEnProceso)
			SELECT DISTINCT
				redId AS temIdTransaccion,
				@EsSimulado AS temRegistroAporteSimulado,
				@EsProcesoManual AS temMarcaAporteManual,
				reg.regId AS temRegistroGeneral,
				reg.regPeriodoAporte AS temPeriodoAporte,
				CASE 
					WHEN @TipoSolicitudAportante IN ('PERSONA', 'EMPLEADOR') THEN regValTotalApoObligatorio
					ELSE aporteTotalDet.valTotalApoObligatorio
				END AS temValTotalApoObligatorio,
				CASE 
					WHEN @TipoSolicitudAportante IN ('PERSONA', 'EMPLEADOR') THEN regValorIntMora
					ELSE aporteTotalDet.valorIntMora
				END AS temValorIntMora,
				CASE WHEN regFechaPagoAporte IS NULL THEN regFechaRecaudo ELSE regFechaPagoAporte END AS temFechaRecaudo,

				CASE 
					WHEN @TipoSolicitudAportante IN ('PERSONA', 'EMPLEADOR') THEN regTipoIdentificacionAportante
					ELSE red.redTipoIdentificacionCotizante
				END AS temTipoIdAportante,
				CASE 
					WHEN @TipoSolicitudAportante IN ('PERSONA', 'EMPLEADOR') THEN regNumeroIdentificacionAportante 
					ELSE red.redNumeroIdentificacionCotizante
				END AS temNumeroIdAportante,

				regCodigoEntidadFinanciera AS temCodEntidadFinanciera,
				regOperadorInformacion AS temOperadorInformacion, 
				CASE regModalidadPlanilla WHEN 1 THEN 'ELECTRONICA' WHEN 2 THEN 'ASISTIDA' END AS temModalidadPlanilla,
				CASE WHEN @EsSimulado = 1 THEN 'PILA_MANUAL' ELSE (CASE WHEN @EsProcesoManual = 1 THEN 'MANUAL' ELSE 'PILA' END) END AS temModalidadRecaudoAporte,

				1 AS temApoConDetalle,
				CASE WHEN temC.tctMarcaCreacion = 'CREADO_DESDE_PILA' OR ISNULL(red.redOUTEstadoSolicitante,'') IN ('NO_FORMALIZADO_CON_INFORMACION', 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES')
					THEN NULL ELSE 'RECONOCIMIENTO_AUTOMATICO_OPORTUNO' END AS temFormaReconocimientoAporte,
				regNumeroCuenta AS temNumeroCuenta,
					
				redId AS temRegistroDetallado,
				red.redTipoIdentificacionCotizante AS temTipoIdCotizante,
				red.redNumeroIdentificacionCotizante AS temNumeroIdCotizante,

				redDiasCotizados AS temDiasCotizados,
				redHorasLaboradas AS temHorasLaboradas,
				redSalarioBasico AS temSalarioBasico,
				redCodMunicipio AS temMunicipioLaboral,
				redCodDepartamento AS temDepartamentoLaboral,
				redValorIBC AS temValorIBC,
				redTarifa AS temTarifa,
				CASE redSalarioIntegral WHEN 'S' THEN 1 ELSE 0 END AS temSalarioIntegral,

				redAporteObligatorio AS temAporteObligatorio,
				0 AS temValorSaldoAporte, 
				redOUTValorMoraCotizante AS temValorIntMoraDetalle,
				redCorrecciones AS temCorrecciones,
				dbo.GetLocalDate() AS temFechaProcesamiento,
				'VIGENTE' AS temEstadoAporteRecaudo,
				'VIGENTE' AS temEstadoAporteAjuste,
				CASE WHEN temC.tctMarcaCreacion = 'CREADO_DESDE_PILA' OR ISNULL(red.redOUTEstadoSolicitante,'') IN ('NO_FORMALIZADO_CON_INFORMACION', 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES')
					THEN 'RELACIONADO' ELSE 'REGISTRADO' END AS temEstadoRegistroAporte,
				redOUTMarcaValRegistroAporte,
				redOUTEstadoRegistroAporte,
				redUsuarioAprobadorAporte, 
				CASE 
					WHEN ISNULL(regNovedadFutura, 0) != 1
						AND (redNovIngreso IS NOT NULL OR
							redNovRetiro IS NOT NULL OR
							redNovVSP IS NOT NULL OR
							redNovVST IS NOT NULL OR
							redNovSLN IS NOT NULL OR
							redNovIGE IS NOT NULL OR
							redNovLMA IS NOT NULL OR
							redNovVACLR IS NOT NULL OR
							ISNULL(redDiasIRL, 0) > 0 OR
							redNovSUS IS NOT NULL)
					THEN 1 ELSE 0 
				END AS temPresentaNovedad,
				CASE WHEN reg.regRegistroControlManual IS NOT NULL THEN regNumPlanilla ELSE NULL END,
				0 AS temEnProceso
			FROM staging.RegistroGeneral reg with (nolock)
			INNER JOIN staging.RegistroDetallado red with (nolock) ON reg.regId = red.redRegistroGeneral
			INNER JOIN TemCotizante temC with (nolock) ON temC.tctIdTransaccion = red.redId
			--INNER JOIN @temCotizanteTemp temC ON temC.tctIdTransaccion = red.redId
			LEFT JOIN (
				SELECT red2.redTipoIdentificacionCotizante, red2.redNumeroIdentificacionCotizante, reg2.regId, 
					SUM(redAporteObligatorio) valTotalApoObligatorio , SUM(redOUTValorMoraCotizante) valorIntMora
				FROM staging.RegistroDetallado red2 
					INNER JOIN staging.RegistroGeneral reg2 ON reg2.regId = red2.redRegistroGeneral
				GROUP BY red2.redTipoIdentificacionCotizante, red2.redNumeroIdentificacionCotizante, reg2.regId
			) aporteTotalDet ON (aporteTotalDet.redTipoIdentificacionCotizante = red.redTipoIdentificacionCotizante
				AND aporteTotalDet.redNumeroIdentificacionCotizante = red.redNumeroIdentificacionCotizante
				AND aporteTotalDet.regId = reg.regId) 
			LEFT JOIN dbo.TemAporte tem with (nolock) ON tem.temIdTransaccion = red.redId
			WHERE reg.regId = @IdRegistroGeneral
			AND tem.temId IS NULL

		END

		IF @EsSimulado = 0
		BEGIN
			UPDATE reg
			SET regOUTEstadoArchivo = 'REGISTRADO_O_RELACIONADO_LOS_APORTES',
				regOUTFinalizadoProcesoManual = CASE WHEN @EsProcesoManual = 1 THEN 1 ELSE regOUTFinalizadoProcesoManual END,
				regDateTimeUpdate = dbo.getLocalDate()
			FROM staging.RegistroGeneral reg with (nolock)
			WHERE reg.regId = @IdRegistroGeneral
		END

		-- la marca de EstadoRegistroAporte, depende del tipo del solicitante

		--print 'valor final de @MarcaRegistroRelacionAporte'
		--print @MarcaRegistroRelacionAporte

		IF @MarcaRegistroRelacionAporte IS NULL OR @MarcaRegistroRelacionAporte = 'CREAR_Y_RELACIONAR_EMPLEADOR'
		BEGIN
			SET @MarcaRegistroRelacionAporte = 'RELACIONADO'
		END

		IF @TipoSolicitudAportante = 'EMPLEADOR'
		BEGIN
			UPDATE red
				SET
				redDateTimeUpdate = @redDateTimeUpdate,
				redOUTEstadoRegistroAporte='PROCESADO_APORTE',
				redOUTEstadoRegistroRelacionAporte=@MarcaRegistroRelacionAporte, 
				redOUTEstadoEvaluacionAporte = (CASE WHEN @EsSimulado = 0 THEN 'VIGENTE' ELSE 'CORREGIDO' END)  
			FROM staging.RegistroDetallado red with (nolock)
			WHERE red.redRegistroGeneral = @IdRegistroGeneral
		END
		ELSE
		BEGIN
			UPDATE red
				SET
				redDateTimeUpdate = @redDateTimeUpdate,
				redOUTEstadoRegistroAporte='PROCESADO_APORTE',
				redOUTEstadoRegistroRelacionAporte=(
					CASE 
						WHEN temC.tctMarcaCreacion = 'CREADO_DESDE_PILA' OR ISNULL(red.redOUTEstadoSolicitante,'') IN ('NO_FORMALIZADO_CON_INFORMACION', 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES')
						THEN 'RELACIONADO' ELSE 'REGISTRADO' 
					END), 
				redOUTEstadoEvaluacionAporte = (CASE WHEN @EsSimulado = 0 THEN 'VIGENTE' ELSE 'CORREGIDO' END)  
			FROM staging.RegistroDetallado red with (nolock)
			INNER JOIN dbo.TemCotizante temC with (nolock) ON temC.tctIdTransaccion = red.redId
			WHERE red.redRegistroGeneral = @IdRegistroGeneral
		END
	END;

	BEGIN
			--TODO: Se agrega este control para evitar error diferencia entre las fechas de recaudo de pila y aporteGeneral en core, se encontró el error en el proceso de revisión reporte 90. 
		if exists (select 1 from staging.RegistroGeneral as rg 
						inner join staging.RegistroDetallado as rd on rg.regId = rd.redRegistroGeneral
						inner join TemAporte as t on rg.regId = t.temRegistroGeneral and rd.redId = t.temRegistroDetallado 
						where rg.regId = @IdRegistroGeneral and t.temFechaRecaudo <> rg.regFechaRecaudo)
			begin
				update t set t.temFechaRecaudo = CASE WHEN rg.regFechaPagoAporte IS NULL THEN rg.regFechaRecaudo ELSE rg.regFechaPagoAporte END
				from staging.RegistroGeneral as rg 
						inner join staging.RegistroDetallado as rd on rg.regId = rd.redRegistroGeneral
						inner join TemAporte as t on rg.regId = t.temRegistroGeneral and rd.redId = t.temRegistroDetallado 
						where rg.regId = @IdRegistroGeneral and t.temFechaRecaudo <> rg.regFechaRecaudo
			end;

	  	  --TODO: Se agrega este control para evitar error diferencia entre los valores de aportes entre core y pila
		if exists (select 1 from staging.RegistroGeneral as rg 
						inner join staging.RegistroDetallado as rd on rg.regId = rd.redRegistroGeneral
						inner join TemAporte as t on rg.regId = t.temRegistroGeneral and rd.redId = t.temRegistroDetallado
						where rg.regId = @IdRegistroGeneral and (t.temAporteObligatorio <> rd.redAporteObligatorio and rg.regTipoPlanilla <> 'N'))
			begin
				update t set t.temAporteObligatorio = rd.redAporteObligatorio
				from staging.RegistroGeneral as rg 
				inner join staging.RegistroDetallado as rd on rg.regId = rd.redRegistroGeneral
				inner join TemAporte as t on rg.regId = t.temRegistroGeneral and rd.redId = t.temRegistroDetallado
				where rg.regId = @IdRegistroGeneral and (t.temAporteObligatorio <> rd.redAporteObligatorio and rg.regTipoPlanilla <> 'N')
			end


			--TODO: Se agrega este control para evitar error diferencia entre los valores de aportes entre core y pila
		if exists (select 1 from staging.RegistroGeneral as rg 
						inner join staging.RegistroDetallado as rd on rg.regId = rd.redRegistroGeneral
						inner join TemAporte as t on rg.regId = t.temRegistroGeneral and rd.redId = t.temRegistroDetallado
						where rg.regId = @IdRegistroGeneral and (t.temValTotalApoObligatorio <> rg.regValTotalApoObligatorio and rg.regTipoPlanilla <> 'N'))
			begin
				update t set t.temValTotalApoObligatorio = rg.regValTotalApoObligatorio
				from staging.RegistroGeneral as rg 
				inner join staging.RegistroDetallado as rd on rg.regId = rd.redRegistroGeneral
				inner join TemAporte as t on rg.regId = t.temRegistroGeneral and rd.redId = t.temRegistroDetallado
				where rg.regId = @IdRegistroGeneral and (t.temValTotalApoObligatorio <> rg.regValTotalApoObligatorio and rg.regTipoPlanilla <> 'N')
			end

	END

END;