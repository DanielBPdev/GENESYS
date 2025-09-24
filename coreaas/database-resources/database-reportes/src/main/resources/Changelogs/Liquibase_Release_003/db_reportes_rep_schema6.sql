--changeset ggiraldo:06
--comment: Actualización en la parametrización de todos los reportes normativos para agregar el formato csv

-- Reporte Maestro de Afiliados
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx,csv'
WHERE prnId = 1;

-- Novedades de Afiliados y Subsidios
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx,csv'
WHERE prnId = 2;

-- Novedades de Estado de la Afiliacion y al dia del aportante
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx,csv'
WHERE prnId = 3;

-- Asignación, Pago Y Reintegro De Subsidios De Viviendas – FOVIS
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'xml,txt,xlsx,csv'
WHERE prnId = 4;

-- Asignacion, pago y reintegro de Subsidios de vivienda FOVIS microdato
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx,csv'
WHERE prnId = 5;

-- Reporte Afiliados y Beneficiarios - Afiliados
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx,csv'
WHERE prnId = 6;

-- Devoluciones UGPP
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx,csv'
WHERE prnId = 7;

-- Pagos Fuera de Pila UGPP
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx,csv'
WHERE prnId = 8;

-- Cuota Monetaria - Numero de Personas a Cargo
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx,csv'
WHERE prnId = 9;

-- Cuota Monetaria - Numero total 
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx,csv'
WHERE prnId = 10;

-- Registro Unico de Empleadores
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx,csv'
WHERE prnId = 11;

-- Reporte Semestral de trabajadores sector Agropecuario
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx,csv'
WHERE prnId = 12;

-- Postulaciones Y Asignaciones – FOVIS
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx,xml,csv'
WHERE prnId = 13;

-- Consolidado histórico asignaciones, pagos y reingresos
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx,xml,csv'
WHERE prnId = 15;

-- CONSOLIDADO HISTORICO ASIGNACIONES, PAGOS Y REINTEGROS (ANUAL)(Microdato) Circular 0007 del 2019
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx,xml,csv'
WHERE prnId = 16;

-- Reporte Empresas y aportantes
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx,xml,csv'
WHERE prnId = 17;

-- Afiliados
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx,xml,csv'
WHERE prnId = 18;

-- Afiliados a cargo
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx,xml,csv'
WHERE prnId = 19;

-- Empresas en Mora
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx,xml,csv'
WHERE prnId = 20;

-- Reporte consolidado de cartera
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx,csv'
WHERE prnId = 21;

-- Reporte desagregado de cartera
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx,csv'
WHERE prnId = 22;

-- Aviso de incumplimiento
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx,csv'
WHERE prnId = 23;

-- Archivo Maestro de Subsidios
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx,csv'
WHERE prnId = 24;

-- Reporte Trabajadores Sector Agropecuario
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx,csv'
WHERE prnId = 25;

-- Reporte de Inconsistencias
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx,csv'
WHERE prnId = 26;

-- Reporte Afiliados y Beneficiarios - Asignados
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx,csv'
WHERE prnId = 29;

-- Reporte Afiliados y Beneficiarios - Perdida de Vivienda
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx,csv'
WHERE prnId = 30;

-- Asignados Concurrencia
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx,csv'
WHERE prnId = 31;

-- Novedades Concurrencia
UPDATE ParametrizacionReportesNormativos
SET prnFormatos = 'txt,xlsx,csv'
WHERE prnId = 32;

