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
('USP_GET_EmpresasAportantes'),
('USP_GET_UbicacionContactoAportantes'),
('USP_GET_AsignacionEntregaReintegroFOVIS'),
('USP_REP_CambioCategoriaAfiliado'),
('USP_REP_NuevaCategoriaAfiliado'),
('USP_REP_INS_EstadoAfiliacionEmpleadorCaja'),
('USP_REP_INS_EstadoAfiliacionPersonaCaja'),
('USP_REP_INS_EstadoAfiliacionPersonaEmpresa'),
('USP_REP_INS_EstadoAfiliacionPersonaIndependiente'),
('USP_REP_INS_EstadoAfiliacionPersonaPensionado'),
('USP_SET_CalcularCategoriasAporteFuturo'),
('USP_REP_REPGIASS181'),
('USP_REP_REPGIASS182'),
('USP_REP_REPGIASS183'),
('USP_REP_REPGIASS184'),
('USP_REP_REPGIASS185'),
('USP_REP_REPGIASS186'),
('USP_REP_REPGIASS187'),
('USP_REP_REPGIASS188')

SET @user = @db +'_aplicacion'

SELECT @sqlGrant = COALESCE(@sqlGrant + '; ','') + 'GRANT EXECUTE ON dbo.' + object + ' TO ' + @user
FROM @ProgrammingObjects

print @sqlGrant

EXEC sp_executesql @sqlGrant