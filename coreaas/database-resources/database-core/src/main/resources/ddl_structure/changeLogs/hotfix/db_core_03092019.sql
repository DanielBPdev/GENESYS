--liquibase formatted sql

--changeset dsuesca:01
--comment:
DELETE hbe
FROM HistoricoBeneficiario hbe
WHERE hbe.hbeTipoIdentificacionBeneficiario = 'TARJETA_IDENTIDAD'
  AND hbe.hbeNumeroIdentificacionBeneficiario = '1003206730'
  AND hbe.hbeId IN (12727,12728,12729,12732)