-- =============================================
-- Author:		Ferney Alonso Vásquez Benavides
-- Create date: 2018/08/10
-- Description:	Procedimiento almacenado que se encarga de consultar el histórico de afiliaciones de una persona, como dependiente, independiente o pensionado
-- Req-Consultas - Vista360
-- =============================================
CREATE PROCEDURE [dbo].[USP_ExecuteConsultarHistoricoAfiliacionPersona]
	@tipoIdentificacion VARCHAR(20),			-- Tipo de identificación del afiliado
	@numeroIdentificacion VARCHAR(16), 			-- Número de identificación del afiliado
	@tipoAfiliado VARCHAR(50),					-- Tipo de afiliado (TRABAJADOR_DEPENDIENTE, TRABAJADOR_INDEPENDIENTE, PENSIONADO)
	@tipoIdentificacionEmpleador VARCHAR(20),	-- Tipo de identificación del empleador, cuando @tipoAfiliado='TRABAJADOR_DEPENDIENTE'
	@numeroIdentificacionEmpleador VARCHAR(16)	-- Número de identificación del empleador, cuando @tipoAfiliado='TRABAJADOR_DEPENDIENTE'
AS
SET NOCOUNT ON

DECLARE @idPersona BIGINT
DECLARE @idEmpleador BIGINT

DECLARE @numeroRadicacion VARCHAR(12)
DECLARE @canal VARCHAR(50)
DECLARE @estadoActual VARCHAR(50)
DECLARE @estadoAnterior VARCHAR(50)
DECLARE @motivo VARCHAR(100)

DECLARE @sql NVARCHAR(1000)

DECLARE @fechaCambioEstado DATETIME
DECLARE @fechaIngreso DATETIME
DECLARE @fechaRetiro DATETIME

DECLARE @tablaHistorico TABLE(
	fechaIngreso DATETIME, 
	fechaRetiro DATETIME, 
	canal VARCHAR(50), 
	numeroRadicacion VARCHAR(12),
	fechaMovimiento DATETIME,
	estado VARCHAR(50))
	
DECLARE @historicoCursor AS CURSOR
	

-- Consulta persona
SELECT @idPersona = per.perId
FROM dbo.Persona per 
WHERE per.perTipoIdentificacion = @tipoIdentificacion
	AND per.perNumeroIdentificacion = @numeroIdentificacion
	
-- Instancia cursor dependiendo del tipo de persona
IF @tipoAfiliado = 'TRABAJADOR_INDEPENDIENTE'
BEGIN
	SET @historicoCursor = CURSOR FAST_FORWARD FOR	
		SELECT ISNULL(eai.eaiEstadoAfiliacion,'NO_FORMALIZADO_CON_INFORMACION') estado, eai.eaiFechaCambioEstado fecha
		FROM dbo.EstadoAfiliacionPersonaIndependiente eai
		WHERE eai.eaiPersona = @idPersona
		ORDER BY eai.eaiFechaCambioEstado DESC
END
ELSE 
BEGIN
	IF @tipoAfiliado = 'PENSIONADO'
	BEGIN
		SET @historicoCursor = CURSOR FAST_FORWARD FOR	
			SELECT ISNULL(eap.eapEstadoAfiliacion,'NO_FORMALIZADO_CON_INFORMACION') estado, eap.eapFechaCambioEstado fecha
			FROM dbo.EstadoAfiliacionPersonaPensionado eap
			WHERE eap.eapPersona = @idPersona
			ORDER BY eap.eapFechaCambioEstado DESC
	END
	ELSE 
	BEGIN
		IF @tipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
		BEGIN
			SELECT @idEmpleador = empl.empId
			FROM dbo.Empleador empl 
			JOIN dbo.Empresa emp ON empl.empEmpresa = emp.empId
			JOIN dbo.Persona per ON per.perId = emp.empPersona
			WHERE per.perTipoIdentificacion = @tipoIdentificacionEmpleador
				AND per.perNumeroIdentificacion = @numeroIdentificacionEmpleador
		
			SET @historicoCursor = CURSOR FAST_FORWARD FOR	
				SELECT ISNULL(eae.eaeEstadoAfiliacion,'NO_FORMALIZADO_CON_INFORMACION') estado, eae.eaeFechaCambioEstado fecha
				FROM dbo.EstadoAfiliacionPersonaEmpresa eae
				WHERE eae.eaePersona = @idPersona
					AND eae.eaeEmpleador = @idEmpleador
				ORDER BY eae.eaeFechaCambioEstado DESC
		END
	END
END
	
OPEN @historicoCursor	
FETCH NEXT FROM @historicoCursor INTO @estadoActual, @fechaCambioEstado

-- Recorre el histórico de afiliaciones 
WHILE @@FETCH_STATUS = 0
BEGIN	
	-- Consulta la información de la solicitud de afiliación						
	SELECT @numeroRadicacion = sol.solNumeroRadicacion, @canal = sol.solCanalRecepcion 
	FROM dbo.SolicitudAfiliacionPersona sap 
	JOIN dbo.Solicitud sol ON sap.sapSolicitudGlobal = sol.solId 
	JOIN dbo.RolAfiliado roa ON sap.sapRolAfiliado = roa.roaId 
	JOIN dbo.Afiliado afi ON afi.afiId = roa.roaAfiliado 
	WHERE sol.solNumeroRadicacion IS NOT NULL 
	AND roa.roaTipoAfiliado = @tipoAfiliado
	AND afi.afiPersona = @idPersona

	INSERT INTO @tablaHistorico(fechaIngreso, fechaRetiro, canal, numeroRadicacion, fechaMovimiento, estado)
	VALUES(@fechaIngreso, @fechaRetiro, @canal, @numeroRadicacion, @fechaCambioEstado, @estadoActual)
	
	SET @fechaIngreso = NULL
	SET @fechaRetiro = NULL		
	
	FETCH NEXT FROM @historicoCursor INTO @estadoActual, @fechaCambioEstado
END

CLOSE @historicoCursor
DEALLOCATE @historicoCursor	

-- Respuesta
SELECT fechaIngreso, fechaRetiro, canal, numeroRadicacion, fechaMovimiento, estado
FROM @tablaHistorico

