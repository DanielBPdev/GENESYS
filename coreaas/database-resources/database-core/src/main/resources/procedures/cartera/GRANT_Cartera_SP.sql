-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/01/04
-- Description:	Rutina para asignar permisos a objetos de programacion
-- de acceso publico desde los servicios de Genesys
-- =============================================

DECLARE @sqlGrant NVARCHAR(MAX)
DECLARE @user VARCHAR(50)
DECLARE @db VARCHAR(50) = 'core'

DECLARE @ProgrammingObjects AS TABLE (object VARCHAR(200))

INSERT INTO @ProgrammingObjects (object)
VALUES
('USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores'),
('USP_ExecuteCARTERACalcularDeudaPresuntaPensionados'),
('USP_ExecuteCARTERACalcularDeudaPresuntaIndependientes'),
('USP_ExecuteCARTERAAsignarAccionCobro'),
('USP_ExecuteCARTERACrearCartera'),
('USP_ExecuteCARTERAActualizarCartera'),
('USP_GET_GestorValorSecuencia'),
('USP_ExecuteCARTERAConsultarAportantesGestionCobro'),
('USP_ExecuteCARTERAConsultarAportantesGestionCobroPersonas'),
('USP_ExecuteCARTERAConsultarAportantesDesafiliacion'),
('USP_ExecuteCARTERAConsultarGestionPreventiva'),
('USP_ExecuteCARTERAConsultarGestionFiscalizacion'),
('USP_ExecuteCARTERAActualizarCarteraOK'),
('USP_ExecuteCARTERAConsultarCarteraAportante'),
('USP_ExecuteCARTERAActualizarDetalleSolicitudGestionCobroOK'),
('USP_GET_ConsultarDeudaPresunta'),
('SP_CambioDiadeVencimientoTemp'),
('USP_REP_ConsultarPersonasFirmezaTitulo'),
('USP_Get_Consultapanelcartera'),
('USP_Get_Consultapanelcartera_Conteo'),
('USP_ExecuteCARTERAActualizaDeudaPresuntaEmpleadores'),
('USP_REP_ConsultarCarteraPrescrita'),
('USP_ExecuteCARTERACalculoDeudaParcial')


SET @user = @db +'_aplicacion'

SELECT @sqlGrant = COALESCE(@sqlGrant + '; ','') + 'GRANT EXECUTE ON dbo.' + object + ' TO ' + @user
FROM @ProgrammingObjects

print @sqlGrant

EXEC sp_executesql @sqlGrant