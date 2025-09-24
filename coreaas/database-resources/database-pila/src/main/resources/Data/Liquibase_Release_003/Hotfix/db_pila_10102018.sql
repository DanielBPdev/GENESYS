--liquibase formatted sql

--changeset abaquero:01
--comment: Se actualizan casos incorrectos de normatividad de fecha de vencimiento por CC de Ajustes4
UPDATE dbo.PilaNormatividadFechaVencimiento
SET pfvPeriodoInicial = '2007-06'
WHERE pfvPeriodoInicial = '2007-05' 
AND pfvPeriodoFinal = '2017-01'