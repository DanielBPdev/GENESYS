-- =============================================
-- Author:		Ferney Alonso Vásquez Benavides
-- Create date: 2018/08/09
-- Description:	Procedimiento almacenado que se encarga de consultar los últimos 3 teléfonos (fijo y celular) y correos electónicos registrados para una persona
-- Req-Consultas - Vista360
-- =============================================
CREATE PROCEDURE [dbo].[USP_ExecuteConsultarHistoricoContactoPersona]
	@tipoIdentificacion VARCHAR(20),	-- Tipo de identificación del afiliado
	@numeroIdentificacion VARCHAR(16)  	-- Número de identificación del afiliado
AS
SET NOCOUNT ON

DECLARE @contador INT
DECLARE @contaFijo INT
DECLARE @contaCelular INT
DECLARE @contaCorreo INT

DECLARE @idPersona BIGINT

DECLARE @fijo VARCHAR(100)
DECLARE @celular VARCHAR(100)
DECLARE @correo VARCHAR(100)
DECLARE @fijoAnterior VARCHAR(100)
DECLARE @celularAnterior VARCHAR(100)
DECLARE @correoAnterior VARCHAR(100)

DECLARE @historicoCursor AS CURSOR

DECLARE @tablaHistorico TABLE(
	id INT,
	telefonoFijo VARCHAR(100),
	telefonoCelular VARCHAR(100),
	correo VARCHAR(100))
		
		
-- Consulta persona
SELECT @idPersona = per.perId
FROM Persona per
WHERE per.perTipoIdentificacion = @tipoIdentificacion
	AND per.perNumeroIdentificacion = @numeroIdentificacion
	
-- Datos iniciales
SET @contador = 0
SET @contaFijo = 0
SET @contaCelular = 0
SET @contaCorreo = 0
SET @fijoAnterior = ''
SET @celularAnterior = ''
SET @correoAnterior = ''
	
-- Inserción datos dummy
WHILE @contador < 3
BEGIN
	SET @contador = @contador + 1

	INSERT INTO @tablaHistorico(id,telefonoFijo,telefonoCelular,correo)
	VALUES(@contador,NULL,NULL,NULL)	
END
	
-- Histórico ubicación
SET @historicoCursor = CURSOR FAST_FORWARD FOR
	SELECT ISNULL(hub.hubTelefonoFijo,''), ISNULL(hub.hubTelefonoCelular,''), ISNULL(hub.hubEmail,'')
	FROM dbo.HistoricoUbicacion hub
	WHERE hub.hubPersona = @idPersona
	ORDER BY hub.hubId DESC

OPEN @historicoCursor
FETCH NEXT FROM @historicoCursor INTO @fijo, @celular, @correo

-- Recorre el histórico de ubicación 
WHILE @@FETCH_STATUS = 0
BEGIN	
	IF @fijo <> @fijoAnterior  AND '' <> LTRIM(RTRIM(@fijo)) 
	BEGIN		
		SET @contaFijo = @contaFijo + 1
		
		UPDATE @tablaHistorico 
		SET telefonoFijo = @fijo
		WHERE id = @contaFijo
	END
	
	IF @celular <> @celularAnterior AND '' <> LTRIM(RTRIM(@celular)) 
	BEGIN
		SET @contaCelular = @contaCelular + 1
		
		UPDATE @tablaHistorico 
		SET telefonocelular = @celular
		WHERE id = @contaCelular
	END
	
	IF @correo <> @correoAnterior AND '' <> LTRIM(RTRIM(@correo)) 
	BEGIN
		SET @contaCorreo = @contaCorreo + 1
		
		UPDATE @tablaHistorico 
		SET correo = @correo
		WHERE id = @contaCorreo
	END	
	
	SET @fijoAnterior = @fijo
	SET @celularAnterior = @celular
	SET @correoAnterior = @correo
	
	FETCH NEXT FROM @historicoCursor INTO @fijo, @celular, @correo
END

CLOSE @historicoCursor
DEALLOCATE @historicoCursor

-- Resultado
SELECT telefonoFijo,telefonoCelular,correo
FROM @tablaHistorico
	
