if not exists (SELECT *
FROM INFORMATION_SCHEMA.COLUMNS
WHERE COLUMN_NAME = 'metCuentaOficinaJudicial' AND TABLE_NAME = 'mediotransferencia' and TABLE_SCHEMA = 'dbo')
BEGIN
alter table mediotransferencia
add metCuentaOficinaJudicial varchar(50), metNumeroOficioJudicial varchar(50), metNumeroRadicadoJuzgado varchar(50), metCodigoOficinaJuzgado varchar(50)
END