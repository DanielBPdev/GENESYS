--liquibase formatted sql

--changeset fhoyos:01
--comment: 
UPDATE cad
SET cad.cadDeudaReal = 0
FROM CarteraDependiente_aud cad
WHERE cad.cadDeudaReal IS NULL;

ALTER TABLE CarteraDependiente_aud ALTER COLUMN [cadDeudaReal] NUMERIC(19,5) NOT NULL;