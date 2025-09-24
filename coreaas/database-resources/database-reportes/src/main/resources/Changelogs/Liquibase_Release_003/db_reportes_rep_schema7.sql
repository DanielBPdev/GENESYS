--changeset ggiraldo:07
--comment: Actualización en la parametrización de los reportes normativos para quitarles el formato csv y se agrega xml para el reporte 24

-- Cuota Monetaria - Numero total
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx,csv,xml'
WHERE prnId = 10;

-- Reporte Maestro de Afiliados
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx'
WHERE prnId = 1;

-- Novedades de Afiliados y Subsidios
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx'
WHERE prnId = 2;

-- Novedades de Estado de la Afiliacion y al dia del aportante
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx'
WHERE prnId = 3;

-- Reporte Afiliados y Beneficiarios - Afiliados
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx'
WHERE prnId = 6;

-- Devoluciones UGPP
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx'
WHERE prnId = 7;

-- Pagos Fuera de Pila UGPP
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx'
WHERE prnId = 8;

-- Registro Unico de Empleadores
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx'
WHERE prnId = 11;

-- Reporte Semestral de trabajadores sector Agropecuario
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx'
WHERE prnId = 12;

-- Reporte consolidado de cartera
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx'
WHERE prnId = 21;

-- Reporte desagregado de cartera
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx'
WHERE prnId = 22;

-- Aviso de incumplimiento
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx'
WHERE prnId = 23;

-- Archivo Maestro de Subsidios
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx'
WHERE prnId = 24;

-- Reporte Trabajadores Sector Agropecuario
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx'
WHERE prnId = 25;

-- Reporte de Inconsistencias
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx'
WHERE prnId = 26;

-- Reporte Afiliados y Beneficiarios - Asignados
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx'
WHERE prnId = 29;

-- Reporte Afiliados y Beneficiarios - Perdida de Vivienda
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx'
WHERE prnId = 30;

-- Asignados Concurrencia
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx'
WHERE prnId = 31;

-- Novedades Concurrencia
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx'
WHERE prnId = 32;