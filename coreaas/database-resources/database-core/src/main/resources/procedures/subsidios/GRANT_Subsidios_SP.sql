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
('USP_SM_GET_CondicionesTrabajadorFallecido_trabajador'),
('USP_SM_GET_CondicionesBeneficiarioFallecido_beneficiario'),
('USP_SM_GET_CotizanteConSubsidioPeriodo'),
('USP_SM_GET_InsertCuentaAdministradorSubsidio'),
('USP_SM_GET_InsertDetalleSubsidioAsignado'),
('USP_SM_GET_InsertDescuentosSubsidioAsignado'),
('USP_SM_GET_InsertDetalleSubsidioAsignadoProgramado'),
('USP_SM_GET_InsertPeriodo'),
('USP_SM_GET_InsertTablasMigracion'),
('USP_SM_UTIL_EliminarDispersion'),
('USP_SM_GET_LiquidacionesFallecimientoProgramado'),
('USP_UTIL_GET_CrearRevisionBloqueoCuotaMonetaria'),
('USP_UTIL_GET_CrearRevisionDesbloqueoCuotaMonetaria'),
('USP_SM_GET_InsertCuentaAdministradorSubsidioProgramada'),
('USP_SM_GET_ConsultarDispersionMontoLiquidado'),
('USP_SM_GET_ListadoAbonosPorVencimientoYPrescripcion'),
('USP_SM_GET_ListadoAbonosPorVencimientoYPrescripcionResumen'),
('USP_SM_GET_ListadoArchivoTransDetaSubsidio'),
('Subsidio_Especie_Liq_Manual'),
('USP_Calculo_Novedades_Fovis_Pendientes'),
('USP_Consultar_Postulaciones_Para_Legalizacion'),
('SP_ActualizarEstadisticasGenesys')


SET @user = @db +'_aplicacion'

SELECT @sqlGrant = COALESCE(@sqlGrant + '; ','') + 'GRANT EXECUTE ON dbo.' + object + ' TO ' + @user
FROM @ProgrammingObjects

print @sqlGrant

EXEC sp_executesql @sqlGrant