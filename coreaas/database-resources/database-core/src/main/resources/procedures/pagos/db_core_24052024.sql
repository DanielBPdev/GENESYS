--liquibase formatted sql

--changeset keynervides
--comment: consultar.saldo.cualquierMedioDePago

CREATE OR ALTER VIEW dbo.Vista_SaldoAdministrador AS
SELECT SUM(cas.casValorRealTransaccion) as saldo,
       cas.casAdministradorSubsidio as idAdmin,
	   per.perRazonSocial nombreAdmin,
	   casMedioDePagoTransaccion,
	   per.perTipoIdentificacion, per.perNumeroIdentificacion 
FROM CuentaAdministradorSubsidio as cas with(nolock)
INNER JOIN AdministradorSubsidio as adm with(nolock) ON cas.casAdministradorSubsidio = adm.asuid
INNER JOIN Persona as per with(nolock) on adm.asupersona = per.perid
WHERE cas.casEstadoTransaccionSubsidio = 'APLICADO'
		AND cas.casTipoTransaccionSubsidio IN ('ABONO','AJUSTE_TRANSACCION_RETIRO_INCOMPLETA','RETIRO')
GROUP BY cas.casAdministradorSubsidio,casMedioDePagoTransaccion, per.perTipoIdentificacion, per.perNumeroIdentificacion ,per.perRazonSocial;