ALTER TABLE ParametrizacionGaps
ALTER COLUMN prgDescripcion varchar(300);

if not exists (select * from INFORMATION_SCHEMA.COLUMNS where table_schema = 'dbo' and table_name = 'ParametrizacionGaps' and column_name = 'GLPI')
BEGIN
ALTER TABLE ParametrizacionGaps ADD GLPI varchar(10);
END

if not exists (select * from ParametrizacionGaps where prgNombre='REGISTRO_APORTES_CAMBIO_CAJA_MULTIAFILIACION')
BEGIN
INSERT INTO ParametrizacionGaps( prgProceso,prgNombre,prgDescripcion,prgUsuarui,prgFechaModificacion,prgVersionLiberacion,prgTipoDatos,prgEstado,GLPI)values
('APORTES','REGISTRO_APORTES_CAMBIO_CAJA_MULTIAFILIACION','Permite registrar o relacionar el aporte cuando esta retirado por cambio de caja y multiafiliación, si se elige la opción si se va a relacionar los aportes para los ue esten retirados por motivo de cambio de caja y multiafiliación  y si tiene marcado no se registran',
'Alexander Camelo',NULL,'1.3.11','TEXTO','INACTIVO','64096')
END