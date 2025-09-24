--liquibase formatted sql

--changeset mamonroy:01
--comment: Actualizacion tabla MediosPagoCCF
UPDATE MediosPagoCCF
SET mpcAplicaFOVIS = NULL
WHERE mpcMedioPago = 'EFECTIVO'

--changeset jocampo:01
--comment: Se crea indice
CREATE NONCLUSTERED INDEX IDX_Persona_perRazonSocial ON Persona (perRazonSocial);