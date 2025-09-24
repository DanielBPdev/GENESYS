-- =============================================
-- Author:		Ferney Alonso Vásquez Benavides
-- Create date: 2018/08/10
-- Description:	Procedimiento almacenado que se encarga de consultar el histórico de estados de afiliación de un aportante, respecto a la CCF
-- Req-Consultas - Vista360
-- =============================================
CREATE PROCEDURE [dbo].[USP_ExecuteConsultarHistoricoEstadoAportante]
	@idPersona BIGINT,			--idPersona del aportante
	@tipoAportante VARCHAR(13)			-- Tipo de aportante (EMPLEADOR, INDEPENDIENTE, PENSIONADO)
AS
SET NOCOUNT ON

-- Lista de registros históricos
IF @tipoAportante = 'EMPLEADOR'
BEGIN
	SELECT ISNULL(eec.eecEstadoAfiliacion,'NO_FORMALIZADO_CON_INFORMACION') estado, eec.eecFechaCambioEstado fechaMovimiento
	FROM EstadoAfiliacionEmpleadorCaja eec
	WHERE eec.eecPersona = @idPersona
	ORDER BY eec.eecFechaCambioEstado DESC	
END
ELSE IF @tipoAportante = 'INDEPENDIENTE'
BEGIN
	SELECT ISNULL(eai.eaiEstadoAfiliacion,'NO_FORMALIZADO_CON_INFORMACION') estado, eai.eaiFechaCambioEstado fechaMovimiento
	FROM EstadoAfiliacionPersonaIndependiente eai
	WHERE eai.eaiPersona = @idPersona
	ORDER BY eai.eaiFechaCambioEstado DESC
END
ELSE IF @tipoAportante = 'PENSIONADO'
BEGIN
	SELECT ISNULL(eap.eapEstadoAfiliacion,'NO_FORMALIZADO_CON_INFORMACION') estado, eap.eapFechaCambioEstado fechaMovimiento
	FROM EstadoAfiliacionPersonaPensionado eap
	WHERE eap.eapPersona = @idPersona
	ORDER BY eap.eapFechaCambioEstado DESC
END
ELSE
BEGIN
	SELECT ISNULL(eac.eacEstadoAfiliacion,'NO_FORMALIZADO_CON_INFORMACION') estado, eac.eacFechaCambioEstado fechaMovimiento
	FROM EstadoAfiliacionPersonaCaja eac
	WHERE eac.eacPersona = @idPersona
	ORDER BY eac.eacFechaCambioEstado DESC
END
	

