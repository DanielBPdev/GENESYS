ALTER TABLE ParametrizacionGaps
ALTER COLUMN prgDescripcion varchar(300);


if not exists (select * from ParametrizacionGaps where prgNombre='TRASLADO_EMPRESAS_CCF')
BEGIN
INSERT INTO ParametrizacionGaps( prgProceso,prgNombre,prgDescripcion,prgUsuarui,prgFechaModificacion,prgVersionLiberacion,prgTipoDatos,prgEstado,GLPI)values
('TRASLADO EMPRESAS CCF','TRASLADO_EMPRESAS_CCF','Permite parametrizar la vista de Traslado de Empresas CCF',
'Edwin Toro',NULL,'1.3.13','TEXTO','INACTIVO','70598')
END