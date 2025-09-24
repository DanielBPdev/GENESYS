IF not exists
(
    SELECT *
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_NAME = 'BandejaTransacciones' and TABLE_SCHEMA = 'dbo'
)
BEGIN
    create table BandejaTransacciones
    (
        bdtId                          bigint identity
            primary key,
        bdtTipoIdentificacionPersona   nvarchar(255) collate Latin1_General_CI_AI,
        bdtNumeroIdentificacionPersona nvarchar(255) collate Latin1_General_CI_AI,
        bdtEstado                      nvarchar(255) collate Latin1_General_CI_AI,
        bdtProceso                     nvarchar(255) collate Latin1_General_CI_AI,
        bdtSolicitud                   bigint
            constraint FK_bandeja_solicitud
                references dbo.Solicitud(solId),
        bdtMedioDePagoDestino          bigint,
        bdtMedioDePagoOrigen           bigint,
        bdtProcesoAnibol               bigint,
        bdtfechaInicio                 datetime,
        bdtfechaFin                    datetime
    )
END


IF not exists
(
    SELECT *
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE COLUMN_NAME = 'rsaIdMedioDePagoDestino' AND TABLE_NAME = 'RegistroSolicitudAnibol' and TABLE_SCHEMA = 'dbo'
)
BEGIN
    ALTER TABLE RegistroSolicitudAnibol ADD
    rsaIdMedioDePagoDestino bigint CONSTRAINT FK_RegistroSolicitudAnibol_MedioDePago REFERENCES dbo.MedioDePago(mdpId),
    rsaParametrosTraslado varchar(max) COLLATE Latin1_General_CI_AI;
END