--liquibase formatted sql

--changeset fvasquez:01
--comment: Adición de campos acrCartera y agrCartera
ALTER TABLE ActividadCartera_aud ALTER column acrCicloAportante BIGINT NULL;
ALTER TABLE AgendaCartera_aud ALTER column agrCicloAportante BIGINT NULL;
ALTER TABLE ActividadCartera_aud add acrCartera BIGINT NULL;
ALTER TABLE AgendaCartera_aud add agrCartera BIGINT NULL;

--changeset mamonroy:01
--comment: Adición de campos JEHINGRESOMENSUAL
ALTER TABLE jefehogar_aud add jehIngresoMensual NUMERIC(19,2);

--changeset fvasquez:02
--comment: 
alter table AccionCobro2F2G_aud add aofDiasRegistro BIGINT NULL;
alter table AccionCobro2F2G_aud add aofDiasParametrizados BIGINT NULL;

--changeset clmarin:03
--comment: 
EXEC sp_rename 'SolicitudAporte_aud.soaAporteGeneral', 'soaRegistroGeneral', 'COLUMN'; 