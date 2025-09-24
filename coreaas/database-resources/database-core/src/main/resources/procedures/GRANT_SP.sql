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
('FN_GET_AUD_DBNAME'),
('FN_GET_PILA_DBNAME'),
('GetLocalDate'),
('ufn_CantidadConLetra'),
('UFN_ObtenerFechaComunicadoCartera'),
('UFN_GenerarNumeroOperacionCartera'),
('UFN_SumarDiasFecha'),
('UFN_GET_Porcentaje1429'),
('UFN_GET_ConsultarDeudaPresuntaEmpleadores'),
('UFN_GET_ConsultarDeudaPresuntaIndependientes'),
('UFN_GET_ConsultarDeudaPresuntaPensionados')

SET @user = @db +'_aplicacion'

SELECT @sqlGrant = COALESCE(@sqlGrant + '; ','') + 'GRANT EXECUTE ON dbo.' + object + ' TO ' + @user
FROM @ProgrammingObjects

print @sqlGrant

EXEC sp_executesql @sqlGrant