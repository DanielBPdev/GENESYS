--liquibase formatted sql

--changeset dsuesca:01
--comment:
UPDATE Beneficio SET befFechaVigenciaInicio = '2000-07-10' WHERE befTipoBeneficio = 'LEY_590';