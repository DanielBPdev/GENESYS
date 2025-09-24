-- =============================================
-- Author:		María Cuéllar - Eprocess
-- Create date: 2022/07/10
-- Description:	SP para generar los registros correspondientes a las liquidaciones masivas
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[sp_consultar_abonos_traslados]
@entidadesReceptoras varchar(200),
@fechaTraslado varchar(200),
@idEntidad varchar(200),
@id_tipo_entidad int
AS
BEGIN
if @fechaTraslado = ''
begin 
	set @fechaTraslado = null
end


select   'Traslado de saldos' as tipoLiquidacion, 
CONVERT (DATE,casFechaHoraCreacionRegistro) as fechaTraslado,
sum(casValorRealTransaccion) as total,
COUNT(distinct casid) as totalAbonos,
COUNT(distinct casAdministradorSubsidio) as administradoresSubsidio,
COUNT(distinct Banco.banId) as totalBancos,
STRING_AGG(casId, ',')
FROM CuentaAdministradorSubsidio 
INNER JOIN MedioTransferencia ON CuentaAdministradorSubsidio.casMedioDePago = MedioTransferencia.mdpId
INNER JOIN Banco ON MedioTransferencia.metBanco = Banco.banId
where casOrigenTransaccion = 'TRASLADO_DE_SALDO'

--AND Banco.banCodigoPILA IN (SELECT data FROM dbo.SPLIT(@entidadesReceptoras, ','))
and (CONVERT (DATE, casFechaHoraCreacionRegistro) = @fechaTraslado or @fechaTraslado IS null)
AND CuentaAdministradorSubsidio.casId NOT IN (select RADI_RADI from RADICACIONES_PAGOS_BANCOS where RADI_BANC_ORIG = @idEntidad)
group by CONVERT (DATE,casFechaHoraCreacionRegistro)

	


END