-- ====================================================================================================================================================================================
-- Author:		Jhon Freddy Rico Berm�dez
-- Create date: 2020/08/23
-- Description:	Procedimiento almacenado que se encarga de obtener la informaci�n de las planillas de consolidado de cartera previamente resueltas y guardadas
-- ====================================================================================================================================================================================
CREATE PROCEDURE [dbo].[USP_UTIL_PregenerarComunicadosConsolidadoCartera] 
	@idSolicitud BIGINT,
	@etiqueta varchar(50)
		
AS

SET NOCOUNT ON

DECLARE @solicitud bigint
DECLARE @cartera   bigint
DECLARE @tipoIdentificacion varchar(20)
DECLARE @numeroIdentificacion varchar(16)
DECLARE @tipoAccionCobro varchar(4)

DECLARE @idPlantilla bigint
DECLARE @asuntoPlantilla varchar(150) 
DECLARE @cuerpoPlantilla varchar(8000)
DECLARE @encabezadoPlantilla varchar(500)
DECLARE @mensajePlantilla varchar(8000)
DECLARE @nombrePlantilla varchar(150) 
DECLARE @piePlantilla varchar(500)
DECLARE @etiquetaPlantilla varchar(150) 

DECLARE @asuntoReemplazo varchar(150) 
DECLARE @cuerpoReemplazo varchar(8000)
DECLARE @encabezadoReemplazo varchar(500)
DECLARE @mensajeReemplazo varchar(8000)
DECLARE @pieReemplazo varchar(500)

DECLARE @llave varchar(50)
DECLARE @valor varchar(500) 
DECLARE @tipovariable varchar(100) 

-- Cursor que contiene los datos de las solicitudes y sus empleadores asociados
DECLARE @consultarSolicitudesCursor AS CURSOR 

-- Cursor que contiene las llaves y valores de reemplazo
DECLARE @consultarLlaveValorCursor AS CURSOR 

DECLARE @DatosPlantilla AS TABLE(
	pcrAsunto varchar(500),
	pcrCuerpo varchar(8000),
	pcrEncabezado varchar(1000),
	pcrMensaje varchar(8000),
	pcrNombre varchar(150),
	pcrPie varchar(2000),
	pcrEtiqueta varchar(150),
	pcrSolicitud bigint,
	pcrTipoIdentificacion varchar(20),
	pcrNumeroIdentificacion varchar(16),
	pcrId bigint,
	pcrIdCartera bigint
)

BEGIN TRY

	-- Obtiene los elementos de la planilla
	SELECT @idPlantilla = pcoId, @asuntoPlantilla = pcoAsunto, @cuerpoPlantilla = pcoCuerpo, @encabezadoPlantilla = pcoEncabezado, 
	       @mensajePlantilla = pcoMensaje, @nombrePlantilla = pcoNombre, @piePlantilla = pcoPie, @etiquetaPlantilla = pcoEtiqueta 
	FROM PlantillaComunicado plantCom 
	WHERE plantCom.pcoEtiqueta = @etiqueta

	PRINT 'ASUNTO PLANTILLA: ' + @asuntoPlantilla
	PRINT 'CUERPO PLANTILLA: ' + @cuerpoPlantilla
	PRINT 'ENCABEZADO PLANTILLA: ' + @encabezadoPlantilla
	PRINT 'MENSAJE PLANTILLA: ' + @mensajePlantilla
	PRINT 'PIE PLANTILLA: ' + @piePlantilla	
	
	-- Establece cursor para la consulta de los registros de detalle asociados a la solicitud enviada que a�n no tenga plantillas resueltas
	SET @consultarSolicitudesCursor = CURSOR FAST_FORWARD FOR
	SELECT sgcf.sgfSolicitud, car.carId, per.perTipoIdentificacion,	per.perNumeroIdentificacion, sgcf.sgfTipoAccionCobro
	FROM Cartera car, Persona per, DetalleSolicitudGestionCobro dsgc, SolicitudGestionCobroFisico sgcf, Solicitud s2 
	WHERE NOT EXISTS (
		select 1 from PlantillaComunicadoResuelta pcr where sgcf.sgfSolicitud = pcr.pcrSolicitud 
	)
	AND s2.solId = sgcf.sgfSolicitud 
	AND sgcf.sgfId = dsgc.dsgSolicitudPrimeraRemision 
	AND car.carId = dsgc.dsgCartera 
	AND car.carPersona = per.perId 
	AND dsgc.dsgEnviarPrimeraRemision = 1
	AND s2.solId = @idSolicitud
	
	-- Abre cursor para recorrer los detalles de la solicitud
	OPEN @consultarSolicitudesCursor
	FETCH NEXT FROM @consultarSolicitudesCursor INTO 
		@solicitud, @cartera, @tipoIdentificacion, @numeroIdentificacion, @tipoAccionCobro
	
	-- Itera por los detalles de la solicitud
	WHILE @@FETCH_STATUS = 0
	BEGIN
		
		SET @asuntoReemplazo     = @asuntoPlantilla
		SET @cuerpoReemplazo     = @cuerpoPlantilla
		SET @encabezadoReemplazo = @encabezadoPlantilla
		SET @mensajeReemplazo    = @mensajePlantilla
		SET @pieReemplazo        = @piePlantilla
		
		IF @etiqueta = 'AVI_INC'
		BEGIN
			--AVI_INC (Aviso de incumplimiento)
			PRINT 'SE TRATA DE AVISO DE INCUMPLIMIENTO'
			EXEC dbo.USP_UTIL_CURSOR_AVI_INC @idPlantilla, @tipoAccionCobro, @tipoIdentificacion, @numeroIdentificacion, @consultarLlaveValorCursor OUTPUT
		END
		
		IF @etiqueta = 'LIQ_APO_MOR'
		BEGIN
			--LIQ_APO_MOR (Liquidci�n de aportes en mora)
			PRINT 'SE TRATA DE LIQUIDACION DE APORTES DE MORA'
			EXEC dbo.USP_UTIL_CURSOR_LIQ_APO_MOR @idPlantilla, @cartera, @consultarLlaveValorCursor OUTPUT
		END
		
		IF @etiqueta = 'CIT_NTF_PER'
		BEGIN
			--CIT_NTF_PER (Citaci�n de notificaci�n personal)
			PRINT 'SE TRATA DE CITACION DE NOTIFICACION PERSONAL'
			EXEC dbo.USP_UTIL_CURSOR_CIT_NTF_PER @idPlantilla, @tipoAccionCobro, @tipoIdentificacion, @numeroIdentificacion, @consultarLlaveValorCursor OUTPUT
		END
		
		IF @etiqueta = 'SUS_NTF_NO_PAG' OR @etiqueta = 'NTF_NO_REC_APO'
		BEGIN
			-- SUS_NTF_NO_PAG (Suspensi�n autom�tica de servicios por mora y notificaci�n de no recaudo)
			-- NTF_NO_REC_APO (Notificaci�n de no recaudo de aportes)
			PRINT 'SE TRATA DE SUSPENSION AUTOMATICA DE SERVICIOS O NOTIFICACION DE NO RECAUDO DE APORTES'
			EXEC dbo.USP_UTIL_CURSOR_SUS_NTF_NO_PAG__NTF_NO_REC_APO @idPlantilla, @tipoAccionCobro, @tipoIdentificacion, @numeroIdentificacion, @consultarLlaveValorCursor OUTPUT
		END
		
		IF @etiqueta = 'PRI_AVI_COB_PRS'
		BEGIN
			-- PRI_AVI_COB_PRS (Primer aviso cobro persuasivo)
			PRINT 'SE TRATA DE PRIMER AVISO DE COBRO PERSUASIVO'
			EXEC dbo.USP_UTIL_CURSOR_PRI_AVI_COB_PRS @idPlantilla, @tipoAccionCobro, @tipoIdentificacion, @numeroIdentificacion, @consultarLlaveValorCursor OUTPUT
		END
		
		IF @etiqueta = 'NTF_AVI'
		BEGIN
			-- NTF_AVI (Notificaci�n por aviso)
			PRINT 'SE TRATA DE NOTIFICACION POR AVISO'
			EXEC dbo.USP_UTIL_CURSOR_NTF_AVI @idPlantilla, @tipoAccionCobro, @tipoIdentificacion, @numeroIdentificacion, @consultarLlaveValorCursor OUTPUT
		END
		
		IF @etiqueta = 'SEG_AVI_COB_PRS'
		BEGIN
			-- SEG_AVI_COB_PRS (Segundo aviso cobro persuasivo)
			PRINT 'SE TRATA DE SEGUNDO AVISO DE COBRO PERSUASIVO'
			EXEC dbo.USP_UTIL_CURSOR_SEG_AVI_COB_PRS @idPlantilla, @tipoAccionCobro, @tipoIdentificacion, @numeroIdentificacion, @consultarLlaveValorCursor OUTPUT
		END
		
		IF @etiqueta = 'CAR_EMP_EXP'
		BEGIN
			-- CAR_EMP_EXP (Carta a empresa expulsada)
			PRINT 'SE TRATA DE PRIMER CARTA A EMPRESA EXPULSADA'
			EXEC dbo.USP_UTIL_CURSOR_CAR_EMP_EXP @idPlantilla, @tipoAccionCobro, @tipoIdentificacion, @numeroIdentificacion, @consultarLlaveValorCursor OUTPUT
		END
		
		IF @etiqueta = 'CAR_PER_EXP'
		BEGIN
			-- CAR_PER_EXP (Carta a persona expulsada)
			PRINT 'SE TRATA DE PRIMER CARTA A EMPRESA EXPULSADA'
			EXEC dbo.USP_UTIL_CURSOR_CAR_PER_EXP @idPlantilla, @tipoAccionCobro, @tipoIdentificacion, @numeroIdentificacion, @consultarLlaveValorCursor OUTPUT
		END
		
		IF @etiqueta = 'AVI_INC_PER' or @etiqueta = 'NOTI_IN_RE_APORTE'
		BEGIN
			--AVI_INC_PER(Aviso de incumplimiento Persona)
			PRINT 'SE TRATA DE AVISO DE INCUMPLIMIENTO PERSONA'
			EXEC dbo.USP_UTIL_CURSOR_AVI_INC_PER @idPlantilla, @tipoAccionCobro, @tipoIdentificacion, @numeroIdentificacion, @consultarLlaveValorCursor OUTPUT
		END
		
		-- Abre el cursor de datos de empleadores para recorrerlos y realizar los reemplazos
		FETCH NEXT FROM @consultarLlaveValorCursor INTO @llave, @valor, @tipovariable
		
		WHILE @@FETCH_STATUS = 0
		BEGIN
			-- Hace los reemplazos de las CONSTANTES y VARIABLES, evitando los
			-- de tipo REPORTE_VARIABLE y las im�genes (logo)
			-- los cuales ser�n resueltos en c�digo java
			IF @tipovariable <> 'REPORTE_VARIABLE' AND @llave not like '%logo%'
			BEGIN
				SET @valor = REPLACE(@valor, '&', '&amp;')
				SET @asuntoReemplazo     = REPLACE(@asuntoReemplazo,     @llave, ISNULL(@valor,''))
				SET @cuerpoReemplazo     = REPLACE(@cuerpoReemplazo,     @llave, ISNULL(@valor,''))
				SET @encabezadoReemplazo = REPLACE(@encabezadoReemplazo, @llave, ISNULL(@valor,''))
				SET @mensajeReemplazo    = REPLACE(@mensajeReemplazo,    @llave, ISNULL(@valor,''))
				SET @pieReemplazo        = REPLACE(@pieReemplazo,        @llave, ISNULL(@valor,''))
			END
		
			PRINT 'ENCABEZADO REEMPLAZO: ' + @encabezadoReemplazo
		
			FETCH NEXT FROM @consultarLlaveValorCursor INTO @llave, @valor, @tipovariable;
		END
		
		INSERT INTO @DatosPlantilla (
			pcrAsunto,
			pcrCuerpo,
			pcrEncabezado,
			pcrMensaje,
			pcrNombre,
			pcrPie,
			pcrEtiqueta,
			pcrSolicitud,
			pcrTipoIdentificacion,
			pcrNumeroIdentificacion,
			pcrId,
			pcrIdCartera
		) VALUES (
			@asuntoReemplazo,
			@cuerpoReemplazo,
			@encabezadoReemplazo,
			@mensajeReemplazo,
			@nombrePlantilla,
			@pieReemplazo,
			@etiqueta,
			@solicitud,
			@tipoIdentificacion, 
			@numeroIdentificacion,
			@idPlantilla,
			@cartera
		)
		
		CLOSE @consultarLlaveValorCursor;
		DEALLOCATE @consultarLlaveValorCursor;
		FETCH NEXT FROM @consultarSolicitudesCursor INTO @solicitud, @cartera, @tipoIdentificacion, @numeroIdentificacion, @tipoAccionCobro
	END
	CLOSE @consultarSolicitudesCursor
	DEALLOCATE @consultarSolicitudesCursor
	
	INSERT INTO dbo.PlantillaComunicadoResuelta (
		pcrAsunto,
		pcrCuerpo,
		pcrEncabezado,
		pcrMensaje,
		pcrNombre,
		pcrPie,
		pcrEtiqueta,
		pcrSolicitud,
		pcrTipoIdentificacion,
		pcrNumeroIdentificacion,
		pcrId,
		pcrIdCartera
	) SELECT * FROM @DatosPlantilla
	
END TRY
BEGIN CATCH
	DECLARE @ErrorMessage NVARCHAR(4000), 
		@ErrorSeverity INT,
		@ErrorState INT

	SELECT @ErrorMessage = '[dbo].[USP_PregenerarComunicadosCartera] | ' + ERROR_MESSAGE(),
           @ErrorSeverity = ERROR_SEVERITY(),
           @ErrorState = ERROR_STATE()

    INSERT INTO CarteraLogError(cleFecha,cleNombreSP, cleaMensaje)
	VALUES (dbo.getLocalDate(),'USP_PregenerarComunicadosCartera',ERROR_MESSAGE())
	
	RAISERROR (@ErrorMessage, -- Message text.
               @ErrorSeverity, -- Severity.
               @ErrorState -- State.
               )
END CATCH