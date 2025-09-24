-- =============================================
-- Author:		Claudia Milena Marín Hincapié
-- Create date: 2019/11/15
-- Description:	Procedimiento almacenado que se encarga de actualizar registros en cartera que se encuentran en la línea de cobro 3 
--				por tener aportes NO_OK que posteriormente se modifican a OK desde el proceso de aportes  
-- HU164
-- =============================================
CREATE PROCEDURE [dbo].[USP_ExecuteCARTERAActualizarCarteraOK]
AS

BEGIN TRY
	-- se agrega registro en bitacora de ejecución
	IF EXISTS(SELECT 1 FROM BitacoraEjecucionSp WHERE besNombreStoredProcedure = 'USP_ExecuteCARTERAActualizarCarteraOK')
	BEGIN
		UPDATE BitacoraEjecucionSp
		SET besUltimoInicio = dbo.getLocalDate()
		WHERE besNombreStoredProcedure = 'USP_ExecuteCARTERAActualizarCarteraOK'
	END
	ELSE
	BEGIN
		INSERT INTO BitacoraEjecucionSp (besNombreStoredProcedure, besUltimoInicio)
		VALUES ('USP_ExecuteCARTERAActualizarCarteraOK', dbo.getLocalDate())
	END
	
	DECLARE @Cotizantes TABLE (idCotizante BIGINT, tipoCotizante VARCHAR(24), periodoAporte VARCHAR(7), idAporteDetallado BIGINT)
	DECLARE @CarteraEmpleador TABLE (idCartera BIGINT, idPersona BIGINT, idEmpleador BIGINT, tipoCotizante VARCHAR(24))
	DECLARE @CarteraPersona TABLE (idCartera BIGINT, idPersona BIGINT, tipoCotizante VARCHAR(24))
	DECLARE @Afiliados TABLE (idAfiliado BIGINT)
	DECLARE @aportantesCursor AS CURSOR
	
	DECLARE @idCartera BIGINT
	DECLARE @deudaPresuntaEmpleador NUMERIC(19, 5)
	
	INSERT INTO @Cotizantes (idCotizante, tipoCotizante, periodoAporte, idAporteDetallado)
	SELECT apdPersona, apdTipoCotizante, apgPeriodoAporte, apdId FROM AporteDetallado, AporteGeneral 
	WHERE apdAporteGeneral = apgId AND apdModificadoAportesOK=1
	
	INSERT INTO @CarteraPersona (idCartera, idPersona, tipoCotizante)
	SELECT carId, carPersona, CASE WHEN carTipoSolicitante='INDEPENDIENTE' THEN 'TRABAJADOR_INDEPENDIENTE' ELSE carTipoSolicitante END
	FROM Cartera WHERE carTipoLineaCobro IN ('LC4','LC5')
		AND carPersona IN (SELECT idCotizante FROM @Cotizantes WHERE periodoAporte=carPeriodoDeuda AND carTipoSolicitante=
		(CASE WHEN tipoCotizante='TRABAJADOR_INDEPENDIENTE' THEN 'INDEPENDIENTE' 
		ELSE CASE WHEN tipoCotizante='PENSIONADO' THEN tipoCotizante END END))
	
	UPDATE Cartera
	SET carEstadoOperacion='NO_VIGENTE', carDeudaPresunta=0, carEstadoCartera='AL_DIA'
	WHERE carPersona IN (SELECT idPersona FROM @CarteraPersona WHERE carId=idCartera)
	
	INSERT INTO @CarteraEmpleador (idCartera, idPersona, idEmpleador, tipoCotizante)
	SELECT cadCartera, cadPersona, carPersona, 'TRABAJADOR_DEPENDIENTE' FROM CarteraDependiente, Cartera WHERE carId=cadCartera AND carTipoLineaCobro='LC3'
		AND cadPersona IN (SELECT idCotizante FROM @Cotizantes WHERE tipoCotizante='TRABAJADOR_DEPENDIENTE' AND periodoAporte=carPeriodoDeuda)
	
	UPDATE CarteraDependiente
	SET cadEstadoOperacion='NO_VIGENTE', cadDeudaPresunta=0
	WHERE cadPersona IN (SELECT idPersona FROM @CarteraEmpleador WHERE cadCartera=idCartera)
	
	INSERT INTO @Afiliados(idAfiliado)
	SELECT roaAfiliado FROM RolAfiliado, Afiliado WHERE roaAfiliado=afiId AND (afiPersona IN (SELECT idPersona FROM @CarteraPersona WHERE tipoCotizante=roaTipoAfiliado) 
			OR afiPersona IN (SELECT idPersona FROM @CarteraEmpleador WHERE tipoCotizante=roaTipoAfiliado AND roaEmpleador=idEmpleador))
	
	UPDATE RolAfiliado
	SET roaMotivoFiscalizacion = NULL 
	WHERE roaAfiliado IN (SELECT idAfiliado FROM @Afiliados)
	
	SET @aportantesCursor = CURSOR FAST_FORWARD FOR
	SELECT idCartera FROM @CarteraEmpleador 
	
	OPEN @aportantesCursor	
	FETCH NEXT FROM @aportantesCursor INTO @idCartera 
	
	-- Recorre la lista de empleadores
	WHILE @@FETCH_STATUS = 0
	BEGIN
		SELECT @deudaPresuntaEmpleador = SUM(cadDeudaPresunta) FROM CarteraDependiente WHERE cadCartera=@idCartera 		
		
		IF @deudaPresuntaEmpleador>0
		BEGIN
			UPDATE Cartera
			SET carEstadoOperacion='NO_VIGENTE', carDeudaPresunta=0, carEstadoCartera='AL_DIA'
			WHERE carId=@idCartera
		END
		ELSE
		BEGIN
			UPDATE Cartera
			SET carDeudaPresunta=@deudaPresuntaEmpleador
			WHERE carId=@idCartera
		END
		
		FETCH NEXT FROM @aportantesCursor INTO @idCartera
	END
	
	CLOSE @aportantesCursor
	DEALLOCATE @aportantesCursor
	
	UPDATE AporteDetallado
	SET apdModificadoAportesOK=0  
	WHERE apdId IN (SELECT idAporteDetallado FROM @Cotizantes )
	
	--Auditoria sobre cartera dependiente, cartera, rolAfiliado , APORTEDETALLADO
	
	BEGIN TRY
		-- se marca en bitácora el fin de la ejecución exitosa ylos registros afectados
		UPDATE BitacoraEjecucionSp
		SET besUltimoExito = dbo.getLocalDate()
		WHERE besNombreStoredProcedure = 'USP_ExecuteCARTERAActualizarCarteraOK'
	END TRY
	BEGIN CATCH
	    INSERT INTO CarteraLogError(cleFecha,cleNombreSP, cleaMensaje)
		VALUES (dbo.getLocalDate(),'ssss S:4',ERROR_MESSAGE());
		THROW;
	END CATCH
	
END TRY
BEGIN CATCH
	-- se marca en bitácora el fin de la ejecución fallida
	UPDATE BitacoraEjecucionSp
	SET besUltimoFallo = dbo.getLocalDate() 
	WHERE besNombreStoredProcedure = 'USP_ExecuteCARTERAActualizarCarteraOK'

	DECLARE @ErrorMessage NVARCHAR(4000), 
		@ErrorSeverity INT,
		@ErrorState INT

	SELECT @ErrorMessage = '[dbo].[USP_ExecuteCARTERAActualizarCarteraOK] | ' + ERROR_MESSAGE(),
           @ErrorSeverity = ERROR_SEVERITY(),
           @ErrorState = ERROR_STATE();

    INSERT INTO CarteraLogError(cleFecha,cleNombreSP, cleaMensaje)
	VALUES (dbo.getLocalDate(),'USP_ExecuteCARTERAActualizarCarteraOK',ERROR_MESSAGE())
	
	RAISERROR (@ErrorMessage, -- Message text.
               @ErrorSeverity, -- Severity.
               @ErrorState -- State.
               );
END CATCH