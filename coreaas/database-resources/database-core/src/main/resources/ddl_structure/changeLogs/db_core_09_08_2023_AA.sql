if exists (select 1 from information_schema.columns where table_schema = 'dbo' and table_name = 'MedioTransferencia'  and column_name  = 'metCuentaOficinaJudicial')
print ('Existen')
else
begin
 ALTER TABLE MedioTransferencia alter column metCuentaOficinaJudicial VARCHAR(50) NULL;
 ALTER TABLE MedioTransferencia alter column metNumeroOficioJudicial VARCHAR(50) NULL;
 ALTER TABLE MedioTransferencia alter column metNumeroRadicadoJuzgado VARCHAR(50) NULL;
 ALTER TABLE MedioTransferencia alter column metCodigoOficinaJuzgado VARCHAR(50) NULL;
end

if exists ( select 1 from information_schema.columns where table_schema = 'aud' and table_name = 'MedioTransferencia_aud'  and column_name  = 'metCuentaOficinaJudicial')
print ('Existen')
else
begin
 ALTER TABLE aud.MedioTransferencia_aud alter column metCuentaOficinaJudicial VARCHAR(50) NULL;
 ALTER TABLE aud.MedioTransferencia_aud alter column metNumeroOficioJudicial VARCHAR(50) NULL;
 ALTER TABLE aud.MedioTransferencia_aud alter column metNumeroRadicadoJuzgado VARCHAR(50) NULL;
 ALTER TABLE aud.MedioTransferencia_aud alter column metCodigoOficinaJuzgado VARCHAR(50) NULL;
end
