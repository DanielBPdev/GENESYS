-- =============================================
-- Author:		Miguel Angel Osorio
-- Create date: 2018/01/10
-- Update : 2018/02/22
-- Description:	Procedimiento almacenado que obtiene los valores del archivo que se cargan de los retiros del tercero pagador y llama el proceso almacenado USP_VALIDAR_CAMPOS_ARCHIVO_RETIRO_CUENTA para conciliar los campos del archivo con los de la cuenta del administrador de subsidio. 
-- =============================================
CREATE PROCEDURE USP_PG_ValidarCamposArchivoRetiroPagos
@IdDocumentoRetiroTerceroPagador VARCHAR(255),
@NumeroVersionDocumento  VARCHAR(4),
@NombreTerceroPagador VARCHAR(200)
AS

	DECLARE @Respuesta BIGINT 

	--Variable que contienen la información de los campos de los archivos de cada registros
	DECLARE @IdArchivoRetiro BIGINT
	DECLARE @IdRegistroArchivoRetiro BIGINT
	DECLARE @IdCampo BIGINT
	DECLARE @TituloCampo VARCHAR (50)
	DECLARE @ValorCampo VARCHAR (50)
	DECLARE @IdRegistro BIGINT

	DECLARE @IdRegistroCampoAux BIGINT
	
	DECLARE @FechaHoraTransaccion DATETIME
	
	DECLARE @IdTransaccionTerceroCampo VARCHAR (200)
	DECLARE	@TipoIdAdminCampo VARCHAR(20)
	DECLARE @NumeroIdAdminCampo VARCHAR(16)
	DECLARE @ValorRealTransaccionCampo NUMERIC(19,5)
	DECLARE @FechaTransaccionCampo VARCHAR (50)
	DECLARE @HoraTransaccionCampo VARCHAR (50)
	DECLARE @CodigoDepartamentoCampo VARCHAR(2)
	DECLARE @CodigoMunicipioCampo VARCHAR(6)
	
	DECLARE @EstadoPrevioRegistro VARCHAR(35)
	DECLARE @isConciliado BIT
	SET @isConciliado = 0

	--id para saber si hay registros previamente conciliados con ese identificador de tercero pagador
	DECLARE @IdRegistroPrevio BIGINT
	
	DECLARE @RegistroPreviamenteConciliado BIT
	SET @RegistroPreviamenteConciliado = 0
	
	DECLARE @CountUno INT
	DECLARE @CountDos INT

	  
	IF EXISTS (SELECT * FROM ArchivoRetiroTerceroPagador  INNER JOIN RegistroArchivoRetiroTerceroPagador  ON arrId = rarArchivoRetiroTerceroPagador
		       WHERE arrIdentificadorDocumento = @IdDocumentoRetiroTerceroPagador AND rarEstado IS NOT NULL AND rarEstado = 'SIN_CONCILIAR')
	BEGIN 
		SET @isConciliado = 1
		print 'isConciliadoPRIMERO'
		print @isConciliado
	END 
	ELSE
	BEGIN

		 SELECT @CountUno = COUNT(*) FROM ArchivoRetiroTerceroPagador  INNER JOIN RegistroArchivoRetiroTerceroPagador  ON arrId = rarArchivoRetiroTerceroPagador
	     WHERE arrIdentificadorDocumento = @IdDocumentoRetiroTerceroPagador AND rarEstado IS NOT NULL AND rarEstado = 'SIN_CONCILIAR'
		 
	     SELECT @CountDos= COUNT(*) FROM ArchivoRetiroTerceroPagador  INNER JOIN RegistroArchivoRetiroTerceroPagador  ON arrId = rarArchivoRetiroTerceroPagador
	     WHERE arrIdentificadorDocumento = @IdDocumentoRetiroTerceroPagador AND rarEstado IS NOT NULL
	     
	     print 'valoresCOUNT'
		 print @CountUno
		 print @CountDos
		 
	     IF @CountUno = @CountDos
	     BEGIN
			 SET @isConciliado = 1
			 print 'isConciliadosegndo'
			 print @isConciliado
		 END  
		
	END 
	
	DECLARE @ConsultarDatosArchivo AS CURSOR 

    IF @isConciliado = 1 --los registros del archivo no han sido conciliados previamente
	BEGIN
		
		print 'isConciliado = 1'
		
		SET @ConsultarDatosArchivo = CURSOR FAST_FORWARD FOR
		SELECT 
			arr.arrId,
			rar.rarId,
			rar.rarIdTransaccionTerceroPagador,
		    rar.rarTipoIdentificacionAdminSubsidio,
			rar.rarNumeroIdentificacionAdminSubsidio,
			rar.rarValorRealTransaccion,
			rar.rarFechaTransaccion,
			rar.rarHoraTransaccion,
			rar.rarCodigoDepartamento,
			rar.rarCodigoMunicipio
		FROM ArchivoRetiroTerceroPagador arr
		INNER JOIN RegistroArchivoRetiroTerceroPagador rar ON arr.arrId = rar.rarArchivoRetiroTerceroPagador
		WHERE arr.arrIdentificadorDocumento = @IdDocumentoRetiroTerceroPagador
		AND arr.arrVersionDocumento = @NumeroVersionDocumento
		
		OPEN @ConsultarDatosArchivo
		FETCH NEXT FROM @ConsultarDatosArchivo INTO
		@IdArchivoRetiro,
		@IdRegistroArchivoRetiro,
		@IdTransaccionTerceroCampo,
		@TipoIdAdminCampo,
		@NumeroIdAdminCampo,
		@ValorRealTransaccionCampo,
		@FechaTransaccionCampo,
		@HoraTransaccionCampo,
		@CodigoDepartamentoCampo,
		@CodigoMunicipioCampo
		
		WHILE @@FETCH_STATUS = 0
		BEGIN
	
			SET @FechaHoraTransaccion  = CAST(@FechaTransaccionCampo+' '+@HoraTransaccionCampo as DATETIME)
		
			-- se llama el procedicimiento almacenado que sabra si existe ese identificador del tercero pagador para conciliar los datos con la cuenta
			EXEC USP_PG_ValidarCamposArchivoRetiroCuenta @IdArchivoRetiro, @IdRegistroArchivoRetiro,
														  @IdTransaccionTerceroCampo, @TipoIdAdminCampo, 
										  				  @NumeroIdAdminCampo, @ValorRealTransaccionCampo,
										  				  @FechaHoraTransaccion,
										  			      @CodigoDepartamentoCampo, @CodigoMunicipioCampo,
										  			      @NombreTerceroPagador;
	
			
			FETCH NEXT FROM @ConsultarDatosArchivo INTO
			@IdArchivoRetiro,
			@IdRegistroArchivoRetiro,
			@IdTransaccionTerceroCampo,
			@TipoIdAdminCampo,
			@NumeroIdAdminCampo,
			@ValorRealTransaccionCampo,
			@FechaTransaccionCampo,
			@HoraTransaccionCampo,
			@CodigoDepartamentoCampo,
			@CodigoMunicipioCampo
		
		END
		CLOSE @ConsultarDatosArchivo;
		DEALLOCATE @ConsultarDatosArchivo;


	END 
	ELSE
	BEGIN
		
		print 'isConciliado = 0'
		SET @RegistroPreviamenteConciliado = 1
		
		SET @ConsultarDatosArchivo = CURSOR FAST_FORWARD FOR
		SELECT 
			arr.arrId,
			rar.rarId,
			rar.rarIdTransaccionTerceroPagador,
		    rar.rarTipoIdentificacionAdminSubsidio,
			rar.rarNumeroIdentificacionAdminSubsidio,
			rar.rarValorRealTransaccion,
			rar.rarFechaTransaccion,
			rar.rarHoraTransaccion,
			rar.rarCodigoDepartamento,
			rar.rarCodigoMunicipio
		FROM ArchivoRetiroTerceroPagador arr
		INNER JOIN RegistroArchivoRetiroTerceroPagador rar ON arr.arrId = rar.rarArchivoRetiroTerceroPagador
		WHERE arr.arrIdentificadorDocumento = @IdDocumentoRetiroTerceroPagador
		AND arr.arrVersionDocumento = @NumeroVersionDocumento
		
		OPEN @ConsultarDatosArchivo
		FETCH NEXT FROM @ConsultarDatosArchivo INTO
		@IdArchivoRetiro,
		@IdRegistroArchivoRetiro,
		@IdTransaccionTerceroCampo,
		@TipoIdAdminCampo,
		@NumeroIdAdminCampo,
		@ValorRealTransaccionCampo,
		@FechaTransaccionCampo,
		@HoraTransaccionCampo,
		@CodigoDepartamentoCampo,
		@CodigoMunicipioCampo
		
		WHILE @@FETCH_STATUS = 0
		BEGIN
	
			UPDATE RegistroArchivoRetiroTerceroPagador 
			SET rarEstado = 'REGISTRO_PREVIAMENTE_CONCILIADO'
			WHERE  rarId = @IdRegistroArchivoRetiro
			
			FETCH NEXT FROM @ConsultarDatosArchivo INTO
			@IdArchivoRetiro,
			@IdRegistroArchivoRetiro,
			@IdTransaccionTerceroCampo,
			@TipoIdAdminCampo,
			@NumeroIdAdminCampo,
			@ValorRealTransaccionCampo,
			@FechaTransaccionCampo,
			@HoraTransaccionCampo,
			@CodigoDepartamentoCampo,
			@CodigoMunicipioCampo
		
		END
		CLOSE @ConsultarDatosArchivo;
		DEALLOCATE @ConsultarDatosArchivo;
		
	END 
	
	IF @RegistroPreviamenteConciliado = 1
	BEGIN
			
		UPDATE ArchivoRetiroTerceroPagador 
		SET arrEstado = 'PREVIAMENTE_PROCESADO'
		WHERE  arrId = @IdArchivoRetiro	
	END
;