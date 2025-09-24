-- =============================================
-- Author:		Miguel Angel Osorio
-- Create date: 2018/01/10
-- Update : 2018/02/22
-- Description:	Procedimiento almacenado que convalida los registros de la cuenta de administrador de subsidio con los datos del archivo de retiro del tercero pagador. 
-- =============================================
CREATE PROCEDURE USP_PG_ValidarCamposArchivoRetiroCuenta
@IdArchivoRetiro BIGINT,
@IdRegistroArchivoRetiro BIGINT,
@IdTransaccionTerceroCampo VARCHAR (200),
@TipoIdAdminCampo VARCHAR(20),
@NumeroIdAdminCampo VARCHAR(16),
@ValorRealTransaccionCampo NUMERIC(19,5),
@FechaHoraTransaccionCampo DATETIME,
@CodigoDepartamentoCampo VARCHAR(2),
@CodigoMunicipioCampo VARCHAR(6),
@NombreTerceroPagado varchar(200)
AS

	--Variables que contienen la información de la cuenta de administrador de subsidio
	DECLARE @IdCuentaAdminSubsidio BIGINT
	DECLARE @IdTransaccionTerceroPagadorCuenta VARCHAR(200)
	DECLARE @TipoIdAdminSubsidioCuenta VARCHAR(20)
    DECLARE @NumeroIdAdminSubsidioCuenta VARCHAR(16)
	DECLARE @ValorRealTransaccionCuenta NUMERIC(19,5)
	DECLARE @FechaHoraTransaccionCuenta DATETIME
	DECLARE @CodigoDepartamentoCuenta VARCHAR(2)
	DECLARE @CodigoMunicipioCuenta VARCHAR(6)
	
	DECLARE @IdTerceroPagadorCoincidiente BIT
	SET @IdTerceroPagadorCoincidiente = 0  
		
	DECLARE @EstadoArchivo VARCHAR(30)	
	
	DECLARE @RespuestaEstadoArchivo VARCHAR(30) 
	SET @RespuestaEstadoArchivo = 'PROCESADO'
	
	DECLARE @IdCuentaAdmin BIGINT
	
	DECLARE @ConsultarDatosCuenta AS CURSOR 
	
	SET @ConsultarDatosCuenta = CURSOR FAST_FORWARD FOR
	SELECT 
	    cas.casId,
		cas.casIdTransaccionTerceroPagador,
		per.perTipoIdentificacion,
		per.perNumeroIdentificacion,		
		cas.casValorRealTransaccion,
		cas.casFechaHoraTransaccion,
		dep.depCodigo,
		mun.munCodigo		
	FROM CuentaAdministradorSubsidio cas
	INNER JOIN AdministradorSubsidio adm ON cas.casAdministradorSubsidio = adm.asuId
	INNER JOIN Persona per ON adm.asuPersona = per.perId
	INNER JOIN SitioPago sip ON sip.sipId = cas.casSitioDeCobro 
	INNER JOIN Infraestructura inf ON inf.infId = sip.sipInfraestructura
	INNER JOIN Municipio mun ON mun.munId = inf.infMunicipio
	INNER JOIN Departamento	dep ON dep.depId = mun.munDepartamento
	WHERE cas.casIdTransaccionTerceroPagador = @IdTransaccionTerceroCampo
	AND cas.casNombreTerceroPagado = @NombreTerceroPagado

	
	OPEN @ConsultarDatosCuenta
	FETCH NEXT FROM @ConsultarDatosCuenta INTO
	@IdCuentaAdminSubsidio,
	@IdTransaccionTerceroPagadorCuenta,
	@TipoIdAdminSubsidioCuenta,	
	@NumeroIdAdminSubsidioCuenta,	
	@ValorRealTransaccionCuenta,
	@FechaHoraTransaccionCuenta,
	@CodigoDepartamentoCuenta,
	@CodigoMunicipioCuenta 
	
	WHILE @@FETCH_STATUS = 0
	BEGIN
				
		IF @IdTransaccionTerceroPagadorCuenta = @IdTransaccionTerceroCampo
		BEGIN	
				
				SET @IdTerceroPagadorCoincidiente = 1
				SET @IdCuentaAdmin = @IdCuentaAdminSubsidio
				
				--Se compara si los tipos de identificación del administrador del subsidio no coincide
				IF @TipoIdAdminSubsidioCuenta <> @TipoIdAdminCampo
				BEGIN
					INSERT INTO CampoArchivoRetiroTerceroPagador (carDescripcionCampo,carValorCampoArchivo,carValorCampoCuentaAdminSubsidio,carInconsistencia,carRegistroArchivoRetiroTerceroPagador) 
					VALUES('TIPO_IDENTIFICACION_ADMININISTRADOR_SUBSIDIO',@TipoIdAdminCampo,@TipoIdAdminSubsidioCuenta,'TIPO_IDENTIFICACION_ADMININISTRADOR_SUBSIDIO',@IdRegistroArchivoRetiro)
					
					SET @RespuestaEstadoArchivo ='PROCESADO_CON_INCONSISTENCIA'
					
				END
				
				
				--Se compara si los números de identificación del administrador de subsidio no coinciden
				IF @NumeroIdAdminSubsidioCuenta <> @NumeroIdAdminCampo
				BEGIN
				
					INSERT INTO CampoArchivoRetiroTerceroPagador (carDescripcionCampo,carValorCampoArchivo,carValorCampoCuentaAdminSubsidio,carInconsistencia,carRegistroArchivoRetiroTerceroPagador) 
					VALUES('NUMERO_IDENTIFICACION_ADMININSTRADOR_SUBSIDIO',@NumeroIdAdminCampo,@NumeroIdAdminSubsidioCuenta,'NUMERO_IDENTIFICACION_ADMININSTRADOR_SUBSIDIO',@IdRegistroArchivoRetiro)
					
					SET @RespuestaEstadoArchivo ='PROCESADO_CON_INCONSISTENCIA'
				
				END
				
				--Se compara si los valores reales no coinciden
				IF  ABS(@ValorRealTransaccionCuenta) <> @ValorRealTransaccionCampo
				BEGIN
					INSERT INTO CampoArchivoRetiroTerceroPagador (carDescripcionCampo,carValorCampoArchivo,carValorCampoCuentaAdminSubsidio,carInconsistencia,carRegistroArchivoRetiroTerceroPagador) 
					VALUES('VALOR_REAL_TRANSACCION',@ValorRealTransaccionCampo,@ValorRealTransaccionCuenta,'VALOR_REAL_TRANSACCION',@IdRegistroArchivoRetiro)
					
					SET @RespuestaEstadoArchivo ='PROCESADO_CON_INCONSISTENCIA'
					
				
				END
				
				--Se compara si la fecha y hora de transacción no coinciden
				IF @FechaHoraTransaccionCuenta <> @FechaHoraTransaccionCampo
				BEGIN
		
					DECLARE @FechaCuenta DATE
					DECLARE @FechaCampo DATE
					
					SET @FechaCuenta = CAST (@FechaHoraTransaccionCuenta as date)
					SET @FechaCampo = CAST (@FechaHoraTransaccionCampo as date)
					
					--Se compara si la fecha no coincide
					IF @FechaCuenta <> @FechaCampo
					BEGIN
						INSERT INTO CampoArchivoRetiroTerceroPagador (carDescripcionCampo,carValorCampoArchivo,carValorCampoCuentaAdminSubsidio,carInconsistencia,carRegistroArchivoRetiroTerceroPagador) 
						VALUES('FECHA_TRANSACCION',@FechaCampo,@FechaCuenta,'FECHA_TRANSACCION',@IdRegistroArchivoRetiro)
						
						SET @RespuestaEstadoArchivo ='PROCESADO_CON_INCONSISTENCIA'
												
					END 
					
					DECLARE @HoraCuenta VARCHAR(8)
					DECLARE @HoraCampo VARCHAR(8)
					 
					SET @HoraCuenta = CONVERT(VARCHAR(8),@FechaHoraTransaccionCuenta,108)
					SET @HoraCampo = CONVERT(VARCHAR(8),@FechaHoraTransaccionCampo,108)
					
					--Se compara si la hora no coincide
					IF @HoraCuenta  <> @HoraCampo 
					BEGIN
						INSERT INTO CampoArchivoRetiroTerceroPagador (carDescripcionCampo,carValorCampoArchivo,carValorCampoCuentaAdminSubsidio,carInconsistencia,carRegistroArchivoRetiroTerceroPagador) 
						VALUES('HORA_TRANSACCION',@HoraCampo,@HoraCuenta,'HORA_TRANSACCION',@IdRegistroArchivoRetiro)
						
						SET @RespuestaEstadoArchivo ='PROCESADO_CON_INCONSISTENCIA'
						
					END 
				END
				
				--Se compara si el código del departamento no coinciden
			    IF @CodigoDepartamentoCuenta <> @CodigoDepartamentoCampo
				BEGIN
					INSERT INTO CampoArchivoRetiroTerceroPagador (carDescripcionCampo,carValorCampoArchivo,carValorCampoCuentaAdminSubsidio,carInconsistencia,carRegistroArchivoRetiroTerceroPagador) 
					VALUES('DEPARTAMENTO',@CodigoDepartamentoCampo,@CodigoDepartamentoCuenta,'DEPARTAMENTO',@IdRegistroArchivoRetiro)
						
					SET @RespuestaEstadoArchivo ='PROCESADO_CON_INCONSISTENCIA'
					
				END
				
				--Se compara si el código del municipio no coinciden
				IF @CodigoMunicipioCuenta <> @CodigoMunicipioCampo
				BEGIN
					INSERT INTO CampoArchivoRetiroTerceroPagador (carDescripcionCampo,carValorCampoArchivo,carValorCampoCuentaAdminSubsidio,carInconsistencia,carRegistroArchivoRetiroTerceroPagador) 
					VALUES('MUNICIPIO',@CodigoMunicipioCampo,@CodigoMunicipioCuenta,'MUNICIPIO',@IdRegistroArchivoRetiro)
						
					SET @RespuestaEstadoArchivo ='PROCESADO_CON_INCONSISTENCIA'
				
				END
				
			BREAK;
				
		END 
		
		FETCH NEXT FROM @ConsultarDatosCuenta INTO
		@IdCuentaAdminSubsidio,
		@IdTransaccionTerceroPagadorCuenta,
		@TipoIdAdminSubsidioCuenta,
		@NumeroIdAdminSubsidioCuenta,
		@ValorRealTransaccionCuenta,
		@FechaHoraTransaccionCuenta,
		@CodigoDepartamentoCuenta,
		@CodigoMunicipioCuenta 
	
	END
	CLOSE @ConsultarDatosCuenta;
	DEALLOCATE @ConsultarDatosCuenta;
	
	IF @IdTerceroPagadorCoincidiente = 0
	BEGIN
		
		IF @IdTransaccionTerceroPagadorCuenta IS NULL
		BEGIN
			INSERT INTO CampoArchivoRetiroTerceroPagador (carDescripcionCampo,carValorCampoArchivo,carValorCampoCuentaAdminSubsidio,carInconsistencia,carRegistroArchivoRetiroTerceroPagador) 
			VALUES('IDENTIFICADOR_TRANSACCION_TERCERO_PAGADOR',@IdTransaccionTerceroCampo,'No coincide el identificador de tercero pagador','IDENTIFICADOR_TRANSACCION_TERCERO_PAGADOR',@IdRegistroArchivoRetiro)
			
			SET @RespuestaEstadoArchivo ='PROCESADO_CON_INCONSISTENCIA'
		END	
		

	END
		
	SELECT @EstadoArchivo = arrEstado FROM ArchivoRetiroTerceroPagador  WHERE arrId = @IdArchivoRetiro

	print 'estado archivo'
	print @EstadoArchivo
	
	IF @EstadoArchivo IS NULL 
	BEGIN
		
		UPDATE ArchivoRetiroTerceroPagador 
		SET arrEstado = @RespuestaEstadoArchivo
		WHERE  arrId = @IdArchivoRetiro
		
		SET @EstadoArchivo = @RespuestaEstadoArchivo
	END 
	
	--se actualiza el estado del registro que contiene los campos por conciliado o sin conciliar
	IF @RespuestaEstadoArchivo ='PROCESADO_CON_INCONSISTENCIA'
	BEGIN
		
		UPDATE RegistroArchivoRetiroTerceroPagador 
		SET rarEstado = 'SIN_CONCILIAR'
		WHERE  rarId = @IdRegistroArchivoRetiro      
		
	END 
	ELSE
	BEGIN 
	
		UPDATE RegistroArchivoRetiroTerceroPagador 
		SET rarEstado = 'CONCILIADO',
			rarCuentaAdministradorSubsidio = @IdCuentaAdmin
		WHERE  rarId = @IdRegistroArchivoRetiro   
	
	END 
	print 'Estado del archivo'	
	print @RespuestaEstadoArchivo 
;