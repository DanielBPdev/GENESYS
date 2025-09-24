--liquibase formatted sql

--changeset dsuesca:01
--comment: Se actualizan valores de fecha de vencimiento por cc
UPDATE dbo.PilaNormatividadFechaVencimiento
SET pfvDiaVencimiento = pfvDiaVencimiento + 1
WHERE pfvUltimoDigitoId IN ('00-07','08-14','15-21')
AND pfvPeriodoInicial = '2018-09';