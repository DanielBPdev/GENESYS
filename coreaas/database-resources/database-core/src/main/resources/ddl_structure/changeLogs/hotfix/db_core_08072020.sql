--liquibase formatted sql

--changeset mamonroy:01
--comment: 
UPDATE Licencia
SET licTipoLicencia = 'LICENCIA_URBANISMO'
WHERE licTipoLicencia = 'LICENCIA_URBANISNO';

UPDATE LicenciaDetalle
SET lidClasificacionLicencia = 'URBANISMO'
WHERE lidClasificacionLicencia = 'URBANISNO';

UPDATE aud.Licencia_aud
SET licTipoLicencia = 'LICENCIA_URBANISMO'
WHERE licTipoLicencia = 'LICENCIA_URBANISNO';

UPDATE aud.LicenciaDetalle_aud
SET lidClasificacionLicencia = 'URBANISMO'
WHERE lidClasificacionLicencia = 'URBANISNO';