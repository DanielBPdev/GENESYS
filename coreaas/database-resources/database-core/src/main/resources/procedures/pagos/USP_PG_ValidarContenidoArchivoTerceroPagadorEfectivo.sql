-- =============================================
-- Author:		Diego Suesca
-- Create date: 2020/01/21
-- Update : 
-- Description:	Procedimiento almacenado que valida archivo de retiro tercero pagador
-- =============================================
CREATE PROCEDURE USP_PG_ValidarContenidoArchivoTerceroPagadorEfectivo
@nIdConvenio bigint,
@nidArchivoTerceroPagadorEfectivo bigint
AS
	UPDATE tat
	SET tat.tatNombreCampo = CASE WHEN adminSub.asuId IS NULL THEN tat.tatTipoIdentificacionAdmin + tat.tatNumeroIdentificacionAdmin 
				WHEN  transaccionConvenio.casIdTransaccionTerceroPagador IS NOT NULL THEN 'Id transacción'
				WHEN dep.depId IS NULL THEN 'Departamento'
				WHEN mun.munId IS NULL THEN 'Municipio'
				WHEN tat.tatValorTransaccion > saldo.saldo THEN 'Valor transacción'
				WHEN tat.tatValorTransaccion < 0 THEN 'Valor transacción'
					end,
		tat.tatResultado = CASE WHEN (adminSub.asuId IS NULL
					OR transaccionConvenio.casIdTransaccionTerceroPagador IS NOT NULL
					OR dep.depId IS NULL
					OR mun.munId IS NULL
					OR tat.tatValorTransaccion > saldo.saldo
					OR tat.tatValorTransaccion < 0) THEN 'NO_EXITOSO'
					ELSE 'EXITOSO'
					end,
		tat.tatMotivo =	CASE WHEN adminSub.asuId IS NULL THEN 'Administrador de subsidio no existe'
				WHEN transaccionConvenio.casIdTransaccionTerceroPagador IS NOT NULL THEN 'Identificador de transacción ya está registrado en el sistema'
				WHEN dep.depId IS NULL THEN 'Código del departamento no está registrado en el sistema'
				WHEN mun.munId IS NULL THEN 'Código del municipio no está registrado en el sistema'
				WHEN tat.tatValorTransaccion > saldo.saldo THEN 'Valor de la transacción es mayor al saldo consultado'
				WHEN tat.tatValorTransaccion < 0 THEN 'Valor de la transacción es menor a cero (0)'
					end,
		tat.tatSitioCobro =	sitioCobro.sipId
	FROM TempArchivoRetiroTerceroPagadorEfectivo tat
	LEFT JOIN (SELECT per.perNumeroIdentificacion,per.perTipoIdentificacion, asu.asuId
				FROM Persona per
				INNER JOIN AdministradorSubsidio asu ON asu.asuPersona = per.perId
			) adminSub ON adminSub.perTipoIdentificacion = tat.tatTipoIdentificacionAdmin AND adminSub.perNumeroIdentificacion = tat.tatNumeroIdentificacionAdmin
	LEFT JOIN (SELECT DISTINCT cas.casIdTransaccionTerceroPagador
				FROM CuentaAdministradorSubsidio cas
				INNER JOIN ConvenioTerceroPagador con ON con.conNombre = cas.casNombreTerceroPagado AND con.conId = @nIdConvenio
			) transaccionConvenio ON transaccionConvenio.casIdTransaccionTerceroPagador = 	tat.tatIdTransaccion
	LEFT JOIN Departamento dep ON dep.depCodigo = tat.tatCodigoDepartamento
	LEFT JOIN Municipio mun ON mun.munCodigo = tat.tatCodigoMunicipio
	LEFT JOIN ( SELECT MAX(sit.sipId) sipId, mun.munCodigo,dep.depCodigo
				 FROM SitioPago AS sit
				 INNER JOIN Infraestructura AS inf on inf.infId = sit.sipInfraestructura
				 INNER JOIN Municipio AS mun on mun.munId = inf.infMunicipio
				 INNER JOIN Departamento dep ON dep.depId = mun.munDepartamento			 			 
				 GROUP BY mun.munCodigo,dep.depCodigo
			) sitioCobro ON sitioCobro.depCodigo = dep.depCodigo AND sitioCobro.munCodigo = mun.munCodigo
	LEFT JOIN (SELECT SUM(cas.casValorRealTransaccion) as saldo,		
				cas.casAdministradorSubsidio as idAdmin
				FROM CuentaAdministradorSubsidio as cas
				INNER JOIN AdministradorSubsidio as adm on cas.casAdministradorSubsidio = adm.asuid 
				WHERE cas.casMedioDePagoTransaccion = 'EFECTIVO'
				AND cas.casEstadoTransaccionSubsidio IN ('APLICADO')
				AND cas.casTipoTransaccionSubsidio IN ('ABONO')
				AND EXISTS (SELECT 1 FROM DetalleSubsidioAsignado WHERE dsaCuentaAdministradorSubsidio = cas.casId)
				GROUP BY cas.casAdministradorSubsidio
			) saldo ON saldo.idAdmin = adminSub.asuId
	WHERE tat.tatIdConvenio = @nIdConvenio
	  AND tat.tatArchivoRetiroTerceroPagadorEfectivo = @nidArchivoTerceroPagadorEfectivo
	  AND ISNULL(tat.tatResultado,'NULL') <> 'NO_EXITOSO'
;