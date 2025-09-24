if not exists(SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'ListasBlancasAportantes_aud')
begin
    CREATE TABLE aud.ListasBlancasAportantes_aud(
        lblId bigint NOT NULL,
        REV bigint NOT NULL,
        REVTYPE smallint NULL,
        lblNumeroIdentificacionPlanilla varchar(16) NULL,
        lblTipoIdentificacionEmpleador varchar(20) NULL,
        lblNumeroIdentificacionEmpleador varchar(16) NULL,
        lblActivo bit NULL
    )

    ALTER TABLE aud.ListasBlancasAportantes_aud  WITH CHECK ADD  CONSTRAINT FK_ListasBlancasAportantes_aud_REV FOREIGN KEY(REV)
    REFERENCES aud.Revision (revId)
end