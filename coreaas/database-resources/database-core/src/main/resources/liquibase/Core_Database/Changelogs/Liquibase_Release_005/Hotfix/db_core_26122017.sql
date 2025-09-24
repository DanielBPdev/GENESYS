--liquibase formatted sql

--changeset abaquero:01
--comment:Se actualiza registro en la tabla Beneficio
UPDATE Beneficio SET befFechaVigenciaFin = '2014-12-31' WHERE befTipoBeneficio = 'LEY_1429';