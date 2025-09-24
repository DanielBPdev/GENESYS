if exists (select 1 from information_schema.columns where table_schema = 'dbo' and table_name = 'MedioTransferencia_aud'  and column_name  = 'metCuentaOficinaJudicial')
print ('Existen')
else
begin
 ALTER TABLE MedioTransferencia_aud ADD metCuentaOficinaJudicial VARCHAR(50) NULL;
 ALTER TABLE MedioTransferencia_aud ADD metNumeroOficioJudicial VARCHAR(50) NULL;
 ALTER TABLE MedioTransferencia_aud ADD metNumeroRadicadoJuzgado VARCHAR(50) NULL;
 ALTER TABLE MedioTransferencia_aud ADD metCodigoOficinaJuzgado VARCHAR(50) NULL;

end