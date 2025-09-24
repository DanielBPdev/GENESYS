--liquibase formatted sql

--changeset atoro:01
--comment: Se resuelve incidencia # 0223724
INSERT Parametro (prmNombre,prmValor) VALUES ('ACEPTACION_HIJOS_19_22_CON_ESTUDIO_CERTIFICADO','SI');