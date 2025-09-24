--liquibase formatted sql

--changeset abaquero:01
--comment: Adición de campo de control de proceso reconocimiento de aportes
alter table AporteGeneral add apgEnProcesoReconocimiento bit
alter table aud.AporteGeneral_aud add apgEnProcesoReconocimiento bit