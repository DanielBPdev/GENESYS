-- =============================================
-- Author:		Juan Diego Ocampo Q
-- Create date: 2018/01/29
-- Description:	Rutina para asignar permisos a objetos de programacion
-- de acceso publico desde los servicios de Genesys
-- =============================================

DECLARE @sqlGrant NVARCHAR(MAX)
DECLARE @user VARCHAR(50)
DECLARE @db VARCHAR(50) = 'core'

DECLARE @ProgrammingObjects AS TABLE (object VARCHAR(200))

INSERT INTO @ProgrammingObjects (object)
VALUES
('USP_AUD_DELETE_BufferAuditoria'),
('USP_AUD_UTIL_TableList'),
('USP_UTIL_GET_HistoricoAsignacionFOVIS'),
('USP_UTIL_GET_CrearRevision'),
('USP_ExecuteConsultarHistoricoContactoPersona'),
('USP_UTIL_GET_CrearRevisionActualizar'),
('USP_UTIL_CURSOR_AVI_INC'),
('USP_UTIL_CURSOR_AVI_INC_PER'),
('USP_UTIL_CURSOR_CAR_EMP_EXP'),
('USP_UTIL_CURSOR_CAR_PER_EXP'),
('USP_UTIL_CURSOR_CIT_NTF_PER'),
('USP_UTIL_CURSOR_LIQ_APO_MOR'),
('USP_UTIL_CURSOR_NTF_AVI'),
('USP_UTIL_CURSOR_PRI_AVI_COB_PRS'),
('USP_UTIL_CURSOR_SEG_AVI_COB_PRS'),
('USP_UTIL_CURSOR_SUS_NTF_NO_PAG__NTF_NO_REC_APO'),
('USP_UTIL_PregenerarComunicadosConsolidadoCartera'),
('ASP_ConsultarTrabajadoresNoAfiliadosEmpleador'),
('SP_DesistirSolicitudesAfiliacion'),
('SP_ConsultarInfoPersona'),
('SP_ConsultarInfoBeneficiario'),
('ASP_JsonPersona'),
('ASP_JsonValidacionesCargueMultipleWeb'),
('ASP_JsonCruceAportes'),
('SP_HistoricoAfiliacionPersona'),
('SP_CalcularTiempoDesistirSolicitud')


SET @user = @db +'_aplicacion'

SELECT @sqlGrant = COALESCE(@sqlGrant + '; ','') + 'GRANT EXECUTE ON dbo.' + object + ' TO ' + @user
FROM @ProgrammingObjects

print @sqlGrant

EXEC sp_executesql @sqlGrant