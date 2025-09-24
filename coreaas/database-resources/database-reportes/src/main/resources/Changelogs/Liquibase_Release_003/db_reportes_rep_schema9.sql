--changeset mperilla:09
--comment: Se cambia la periodicidad a Mensual reporte 09

-- Desagregado de Cartera 
UPDATE ParametrizacionReportesNormativos
SET prnNFrecuencia = 'Mensual'
WHERE prnNumero = 9;

--comment: Se elimina registro que hace referencia a el reporte de Consolidado 08
-- Consolidado de Cartera 
DELETE FROM ParametrizacionReportesNormativos 
WHERE prnNumero = 8;