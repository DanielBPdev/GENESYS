CREATE
OR ALTER PROCEDURE [dbo].[USP_GET_ConsultarTrasladoDeSaldos](
    @numeroDocumento varchar(12),
    @tipoDocumento varchar(25),
    @mediosDePago varchar(32),
    @numeroTarjeta varchar(16)
) AS BEGIN TRY
SET
    NOCOUNT ON;

IF @numeroTarjeta = ''
OR @numeroTarjeta = ' '
SET
    @numeroTarjeta = NULL;

drop table if exists #Persona
create table #Persona(perid bigint) 
insert
    #Persona(perid)
select
    perid
from
    Persona
where
    perNumeroIdentificacion = @numeroDocumento
    and perTipoIdentificacion = @tipoDocumento create clustered index inx_perid on [dbo].[#Persona] ([perid]) drop table if exists #Pagos
SELECT
    distinct ma.mdpId AS medioDePagoId,
    COALESCE(mt.mtrNumeroTarjeta, sp.sipNombre) AS numeroOubicacion,
    ma.mdpTipo AS tipoMedioPago,
    asuId AS idAdmin,
    casId AS casid INTO #Pagos
FROM
    MedioDePago ma with(nolock)
    LEFT JOIN MedioTarjeta mt with(nolock) ON mt.mdpId = ma.mdpId
    LEFT JOIN MedioEfectivo me with(nolock) ON me.mdpId = ma.mdpId
    LEFT JOIN sitioPago sp with(nolock) ON me.mefSitioPago = sp.sipid
    JOIN CuentaAdministradorSubsidio with(nolock) ON casMedioDePago = ma.mdpId
    JOIN DetalleSubsidioAsignado with(nolock) ON casId = dsaCuentaAdministradorSubsidio
    JOIN AdministradorSubsidio with(nolock) ON asuId = casAdministradorSubsidio
    JOIN #Persona with(nolock) ON perId = asuPersona
WHERE
    ma.mdpTipo IN (
        SELECT
            value
        FROM
            STRING_SPLIT(@mediosDePago, ',')
    )
    AND casTipoTransaccionSubsidio = 'ABONO'
    AND casEstadoTransaccionSubsidio = 'APLICADO'
    AND dsaValorTotal > 0
    AND dsaGrupoFamiliar is not null
    AND (
        1 = (
            case
                when @numeroTarjeta is null then 1
                else 0
            end
        )
        OR (mt.mtrNumeroTarjeta = @numeroTarjeta)
    ) drop table if exists #Beneficiario
select
    benId,
    benBeneficiarioDetalle,
    benGrupoFamiliar,
    benEstadoBeneficiarioAfiliado,
    benTipoBeneficiario,
    benPersona into #Beneficiario
from
    Beneficiario
    join AdminSubsidioGrupo on benGrupoFamiliar = asgGrupoFamiliar
    join AdministradorSubsidio on asgAdministradorSubsidio = asuId
    join #Persona on asuPersona = perid 
    create nonclustered index inx_benGrupoFamiliar ON [dbo].[#Beneficiario] ([benGrupoFamiliar])
SELECT
    CONVERT(
        VARCHAR(MAX),
        (
            SELECT
                medioDePagoId AS id,
                MIN(numeroOubicacion) AS numeroOubicacion,
                MIN(tipoMedioPago) AS tipo,
                MIN(idAdmin) AS idAdmin,
                'TRUE' AS estadoMedioDePago,
                (
                    select
                        distinct d1.dsaAfiliadoPrincipal AS afiliadoPrincipalId,
                        p.perNumeroIdentificacion AS numeroIdentificacion,
                        p.perTipoIdentificacion AS tipoIdentificacion,
                        CONCAT(p.perPrimerNombre, ' ', p.perPrimerApellido) as nombre,
                        (
                            SELECT
                                distinct grfId AS id,
                                grfNumero AS numeroGrupo,
                                'FALSE' AS seleccionado,
                                (
                                    SELECT
                                        distinct p.perNumeroIdentificacion AS numeroIdentificacion,
                                        p.perTipoIdentificacion AS tipoIdentificacion,
                                        CONCAT(p.perPrimerNombre, ' ', p.perPrimerApellido) AS nombre,
                                        b.benEstadoBeneficiarioAfiliado AS estado,
                                        b.benTipoBeneficiario AS parentesco,
                                        (
                                            SELECT
                                                SUM(dsaValorTotal)
                                            FROM
                                                DetalleSubsidioAsignado dsa WITH (NOLOCK)
                                                JOIN CuentaAdministradorSubsidio cas WITH (NOLOCK) ON dsa.dsaCuentaAdministradorSubsidio = cas.casId
                                            WHERE
                                                dsa.dsaBeneficiarioDetalle = b.benBeneficiarioDetalle
                                                AND dsa.dsaGrupoFamiliar = b.benGrupoFamiliar
                                                AND dsa.dsaAfiliadoPrincipal = d1.dsaAfiliadoPrincipal
                                                AND dsa.dsaAdministradorSubsidio = idAdmin
                                                AND cas.casTipoTransaccionSubsidio = 'ABONO'
                                                AND cas.casEstadoTransaccionSubsidio = 'APLICADO'
                                                AND cas.casMedioDePago = medioDePagoId
                                                AND dsa.dsaValorTotal > 0
                                        ) AS saldo
                                    FROM
                                        Persona p with(nolock)
                                        JOIN #Beneficiario b with(nolock) ON b.benPersona = p.perId
                                        JOIN DetalleSubsidioAsignado with(nolock) ON dsaBeneficiarioDetalle = b.benBeneficiarioDetalle
                                        and benGrupoFamiliar = grfId
                                        JOIN CuentaAdministradorSubsidio with(nolock) ON dsaCuentaAdministradorSubsidio = casId
                                    WHERE
                                        dsaAfiliadoPrincipal = d1.dsaAfiliadoPrincipal
                                        AND dsaAdministradorSubsidio = idAdmin
                                        AND casTipoTransaccionSubsidio = 'ABONO'
                                        AND casEstadoTransaccionSubsidio = 'APLICADO'
                                        AND casMedioDePago = medioDePagoId
                                        AND dsaValorTotal > 0 FOR JSON PATH
                                ) AS beneficiarios
                            FROM
                                GrupoFamiliar with(nolock)
                                JOIN AdminSubsidioGrupo with(nolock) ON asgGrupoFamiliar = grfId
                            WHERE
                                grfAfiliado = d1.dsaAfiliadoPrincipal FOR JSON PATH
                        ) AS grupoFamiliar
                    from
                        grupoFamiliar with(nolock)
                        join DetalleSubsidioAsignado d1 with(nolock) on d1.dsaGrupoFamiliar = grfId
                        join CuentaAdministradorSubsidio on casid = dsaCuentaAdministradorSubsidio
                        join AdministradorSubsidio with(nolock) on asuId = casAdministradorSubsidio
                        join afiliado afi with(nolock) on afiId = d1.dsaAfiliadoPrincipal
                        JOIN Persona p with(nolock) ON p.perId = afi.afiPersona
                    where
                        medioDePagoId = casMedioDePago
                        AND dsaAfiliadoPrincipal IS NOT NULL
                        AND asuId = idAdmin
                        AND dsaValorTotal > 0
                        AND casTipoTransaccionSubsidio = 'ABONO'
                        AND casEstadoTransaccionSubsidio = 'APLICADO' FOR JSON PATH
                ) AS afiliados
            FROM
                #Pagos AS subquery
            GROUP BY
                medioDePagoId,
                idAdmin FOR JSON PATH,
                ROOT('MediosDePago')
        )
    ) AS jsonResult
END TRY BEGIN CATCH THROW;

END CATCH;