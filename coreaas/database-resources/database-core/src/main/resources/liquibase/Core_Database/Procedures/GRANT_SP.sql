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
--('USP_calculoFechaDiaHabil'),
('servicios'),
('ufn_CantidadConLetra'),
('UFN_ObtenerFechaComunicadoCartera'),
('UFN_SumarDiasFecha')

SET @user = @db +'_aplicacion'

SELECT @sqlGrant = COALESCE(@sqlGrant + '; ','') + 'GRANT EXECUTE ON dbo.' + object + ' TO ' + @user
FROM @ProgrammingObjects

print @sqlGrant

EXEC sp_executesql @sqlGrant