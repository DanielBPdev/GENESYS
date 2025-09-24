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
('USP_PG_ValidarCamposArchivoRetiroCuenta'),
('USP_PG_ValidarCamposArchivoRetiroPagos'),
('USP_SM_AlmacenarDetallesAsignadosProgramadosADetallesSubsidiosAsignados'),
('USP_PG_ValidarContenidoArchivoTerceroPagadorEfectivo'),
('USP_PG_InsertRestuladosValidacionCargaManualRetiroTerceroPag'),
('USP_PG_ResultadosDispersionAdministradorMedioPago'),
('USP_PG_ConfirmarAbonosMedioPagoBancos'),
('USP_PG_DispersarPagosEstadoEnviado'),
('USP_PG_DispersarPagosEstadoAplicado'),
('USP_PG_GET_ConsultaDetallesSubsidio'),
('USP_PG_GET_ConsultaTransaccionesSubsidio'),
('USP_PG_GET_ConsultaTransDetallesSubsidio'),
('USP_PG_ModificarCuentaYDetallePorReverso'),
('sp_consultar_liquidaciones_especificas')



SET @user = @db +'_aplicacion'

SELECT @sqlGrant = COALESCE(@sqlGrant + '; ','') + 'GRANT EXECUTE ON dbo.' + object + ' TO ' + @user
FROM @ProgrammingObjects

print @sqlGrant

EXEC sp_executesql @sqlGrant