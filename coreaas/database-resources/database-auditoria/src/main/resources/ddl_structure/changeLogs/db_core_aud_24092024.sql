if not exists(SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'ListasBlancasAportantes_aud')
begin
    CREATE TABLE ListasBlancasAportantes_aud(
        lblId bigint NOT NULL,
        REV bigint NOT NULL,
        REVTYPE smallint NULL,
        lblNumeroIdentificacionPlanilla varchar(16) NULL,
        lblTipoIdentificacionEmpleador varchar(20) NULL,
        lblNumeroIdentificacionEmpleador varchar(16) NULL,
        lblActivo bit NULL
    )

end