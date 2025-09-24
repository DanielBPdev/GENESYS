--liquibase formatted sql

--changeset mosanchez:01
--comment: Se modifica el dato roaPorcentajePagoAportes que tiene valor 2 a 0.02
UPDATE RolAfiliado SET roaPorcentajePagoAportes = 0.02 WHERE roaPorcentajePagoAportes =  2.00;

--changeset mosanchez:02
--comment: Se modifica el valor del campo roaPorcentajePagoAportes
ALTER TABLE dbo.rolafiliado ALTER COLUMN roaPorcentajePagoAportes numeric(5,5) NULL;