--liquibase formatted sql

--changeset jocampo:01
--comment:
CREATE TABLE ListasBlancasAportantes (
    lblId bigint IDENTITY(1,1) NOT NULL,
    lblNumeroIdentificacionPlanilla varchar(16) NOT NULL,
    lblTipoIdentificacionEmpleador varchar(20) NOT NULL,
    lblNumeroIdentificacionEmpleador varchar(16) NOT NULL,
    lblActivo bit NOT NULL
)