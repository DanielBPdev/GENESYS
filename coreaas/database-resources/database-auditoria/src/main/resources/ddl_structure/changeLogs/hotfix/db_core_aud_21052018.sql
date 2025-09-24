--liquibase formatted sql

--changeset dsuesca:01
--comment: Ajuste identity tablas de auditoria
ALTER TABLE SolicitudNovedadFovis_aud DROP COLUMN snfId;
ALTER TABLE SolicitudCierreRecaudo_aud DROP COLUMN sciId;
ALTER TABLE EjecucionProgramada_aud DROP COLUMN ejpId;
ALTER TABLE DetalleSubsidioAsignadoProgramado_aud DROP COLUMN dprId;
ALTER TABLE ActoAceptacionProrrogaFovis_aud DROP COLUMN aapId;

ALTER TABLE SolicitudNovedadFovis_aud ADD snfId BIGINT NOT NULL;
ALTER TABLE SolicitudCierreRecaudo_aud ADD sciId BIGINT NOT NULL;
ALTER TABLE EjecucionProgramada_aud ADD ejpId BIGINT NOT NULL;
ALTER TABLE DetalleSubsidioAsignadoProgramado_aud ADD dprId BIGINT NOT NULL;
ALTER TABLE ActoAceptacionProrrogaFovis_aud ADD aapId BIGINT NOT NULL;

