--liquibase formatted sql

--changeset jocampo:01
--comment:
ALTER TABLE PersonaLiquidacionEspecifica ADD pleEmpresa BIGINT;
ALTER TABLE PersonaLiquidacionEspecifica ALTER COLUMN pleEmpleador BIGINT NULL;