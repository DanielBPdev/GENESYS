--liquibase formatted sql

--changeset mamonroy:01
--comment: 
UPDATE Licencia_aud
SET licTipoLicencia = 'LICENCIA_URBANISMO'
WHERE licTipoLicencia = 'LICENCIA_URBANISNO';

UPDATE LicenciaDetalle_aud
SET lidClasificacionLicencia = 'URBANISMO'
WHERE lidClasificacionLicencia = 'URBANISNO';