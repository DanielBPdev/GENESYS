--changeset ggiraldo:08
--comment: Se agrega formato XML al reporte 23

-- Cuota Monetaria - Numero de Personas a Cargo
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx,csv,xml'
WHERE prnId = 9;
