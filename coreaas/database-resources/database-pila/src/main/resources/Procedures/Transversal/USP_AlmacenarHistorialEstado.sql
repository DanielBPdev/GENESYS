-- =============================================
-- Author:		Alfonso Baquero Echeverry
-- Create date: 2018/10/03
-- Update:      Juan Diego Ocampos Q
-- Update date: 2020/09/15
-- Description:	Procedimiento almacenado encargado de registrar una entrada 
-- de hist�rico de estado de validaci�n en PILA
-- =============================================
CREATE PROCEDURE [dbo].[USP_AlmacenarHistorialEstado]
(
	@iIdTransaccion BIGINT,
	@sBloque VARCHAR(11),
	@iIdIndicePlanilla BIGINT = NULL
)
AS
BEGIN
	SET NOCOUNT ON;

	BEGIN TRAN
		INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
		VALUES (dbo.getLocalDate(), 
			';@iIdTransaccion=' + ISNULL(CAST(@iIdTransaccion AS VARCHAR(20)), 'NULL') + 
			';@sBloque=' + ISNULL(@sBloque, 'NULL') + 
			';@iIdIndicePlanilla=' + ISNULL(CAST(@iIdIndicePlanilla AS VARCHAR(20)), 'NULL') , 
			'INICIO USP_AlmacenarHistorialEstado');
	COMMIT;

	if(@iIdIndicePlanilla IS NULL)
	BEGIN
		IF @sBloque = 'BLOQUE_7_OI'
		BEGIN
		BEGIN TRAN 
			INSERT INTO HistorialEstadoBloque (hebIdIndicePlanilla, hebEstado, hebAccion, hebFechaEstado, hebBloque)
			SELECT peb.pebIndicePlanilla, peb.pebEstadoBloque7, peb.pebAccionBloque7, peb.pebFechaBloque7, @sBloque 
			FROM dbo.PilaEstadoBloque peb WITH (NOLOCK)
			INNER JOIN staging.RegistroGeneral reg WITH (NOLOCK) ON reg.regRegistroControl = peb.pebIndicePlanilla
			WHERE reg.regTransaccion = @iIdTransaccion AND peb.pebEstadoBloque7 IS NOT NULL;
		COMMIT;
		END

		ELSE IF @sBloque = 'BLOQUE_8_OI'
		BEGIN
		BEGIN TRAN 
			INSERT INTO HistorialEstadoBloque (hebIdIndicePlanilla, hebEstado, hebAccion, hebFechaEstado, hebBloque)
			SELECT peb.pebIndicePlanilla, peb.pebEstadoBloque8, peb.pebAccionBloque8, peb.pebFechaBloque8, @sBloque 
			FROM dbo.PilaEstadoBloque peb WITH (NOLOCK)
			INNER JOIN staging.RegistroGeneral reg WITH (NOLOCK) ON reg.regRegistroControl = peb.pebIndicePlanilla
			WHERE reg.regTransaccion = @iIdTransaccion AND peb.pebEstadoBloque8 IS NOT NULL;
		COMMIT;
		END

		ELSE IF @sBloque = 'BLOQUE_9_OI'
		BEGIN
		BEGIN TRAN 
			INSERT INTO HistorialEstadoBloque (hebIdIndicePlanilla, hebEstado, hebAccion, hebFechaEstado, hebBloque)
			SELECT peb.pebIndicePlanilla, peb.pebEstadoBloque9, peb.pebAccionBloque9, peb.pebFechaBloque9, @sBloque 
			FROM dbo.PilaEstadoBloque peb WITH (NOLOCK)
			INNER JOIN staging.RegistroGeneral reg WITH (NOLOCK) ON reg.regRegistroControl = peb.pebIndicePlanilla
			WHERE reg.regTransaccion = @iIdTransaccion AND peb.pebEstadoBloque9 IS NOT NULL;
		COMMIT;
		END

		ELSE IF @sBloque = 'BLOQUE_10_OI'
		BEGIN
		BEGIN TRAN 
			INSERT INTO HistorialEstadoBloque (hebIdIndicePlanilla, hebEstado, hebAccion, hebFechaEstado, hebBloque)
			SELECT peb.pebIndicePlanilla, peb.pebEstadoBloque10, peb.pebAccionBloque10, peb.pebFechaBloque10, @sBloque 
			FROM dbo.PilaEstadoBloque peb WITH (NOLOCK)
			INNER JOIN staging.RegistroGeneral reg WITH (NOLOCK) ON reg.regRegistroControl = peb.pebIndicePlanilla
			WHERE reg.regTransaccion = @iIdTransaccion AND peb.pebEstadoBloque10 IS NOT NULL;
		COMMIT;
		END;
	END;

	if(@iIdIndicePlanilla IS NOT NULL)
	BEGIN
		IF @sBloque = 'BLOQUE_7_OI'
		BEGIN
		BEGIN TRAN 
			INSERT INTO HistorialEstadoBloque (hebIdIndicePlanilla, hebEstado, hebAccion, hebFechaEstado, hebBloque)
			SELECT peb.pebIndicePlanilla, peb.pebEstadoBloque7, peb.pebAccionBloque7, peb.pebFechaBloque7, @sBloque 
			FROM dbo.PilaEstadoBloque peb WITH (NOLOCK)
			INNER JOIN staging.RegistroGeneral reg WITH (NOLOCK) ON reg.regRegistroControl = peb.pebIndicePlanilla
			WHERE reg.regRegistroControl = @iIdIndicePlanilla AND peb.pebEstadoBloque7 IS NOT NULL;
		COMMIT;
		END

		ELSE IF @sBloque = 'BLOQUE_8_OI'
		BEGIN
		BEGIN TRAN 
			INSERT INTO HistorialEstadoBloque (hebIdIndicePlanilla, hebEstado, hebAccion, hebFechaEstado, hebBloque)
			SELECT peb.pebIndicePlanilla, peb.pebEstadoBloque8, peb.pebAccionBloque8, peb.pebFechaBloque8, @sBloque 
			FROM dbo.PilaEstadoBloque peb WITH (NOLOCK)
			INNER JOIN staging.RegistroGeneral reg WITH (NOLOCK) ON reg.regRegistroControl = peb.pebIndicePlanilla
			WHERE reg.regRegistroControl = @iIdIndicePlanilla AND peb.pebEstadoBloque8 IS NOT NULL;
		COMMIT;
		END

		ELSE IF @sBloque = 'BLOQUE_9_OI'
		BEGIN
		BEGIN TRAN 
			INSERT INTO HistorialEstadoBloque (hebIdIndicePlanilla, hebEstado, hebAccion, hebFechaEstado, hebBloque)
			SELECT peb.pebIndicePlanilla, peb.pebEstadoBloque9, peb.pebAccionBloque9, peb.pebFechaBloque9, @sBloque 
			FROM dbo.PilaEstadoBloque peb WITH (NOLOCK)
			INNER JOIN staging.RegistroGeneral reg WITH (NOLOCK) ON reg.regRegistroControl = peb.pebIndicePlanilla
			WHERE reg.regRegistroControl = @iIdIndicePlanilla AND peb.pebEstadoBloque9 IS NOT NULL;
		COMMIT;
		END

		ELSE IF @sBloque = 'BLOQUE_10_OI'
		BEGIN
		BEGIN TRAN 
			INSERT INTO HistorialEstadoBloque (hebIdIndicePlanilla, hebEstado, hebAccion, hebFechaEstado, hebBloque)
			SELECT peb.pebIndicePlanilla, peb.pebEstadoBloque10, peb.pebAccionBloque10, peb.pebFechaBloque10, @sBloque 
			FROM dbo.PilaEstadoBloque peb WITH (NOLOCK)
			INNER JOIN staging.RegistroGeneral reg WITH (NOLOCK) ON reg.regRegistroControl = peb.pebIndicePlanilla
			WHERE reg.regRegistroControl = @iIdIndicePlanilla AND peb.pebEstadoBloque10 IS NOT NULL;
		COMMIT;
		END;
	END;


	BEGIN TRAN
			INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
			VALUES (dbo.getLocalDate(), 
				';@iIdTransaccion=' + ISNULL(CAST(@iIdTransaccion AS VARCHAR(20)), 'NULL') + 
				';@sBloque=' + ISNULL(@sBloque, 'NULL') + 
				';@iIdIndicePlanilla=' + ISNULL(CAST(@iIdIndicePlanilla AS VARCHAR(20)), 'NULL') , 
				'FIN USP_AlmacenarHistorialEstado');
	COMMIT;
END;