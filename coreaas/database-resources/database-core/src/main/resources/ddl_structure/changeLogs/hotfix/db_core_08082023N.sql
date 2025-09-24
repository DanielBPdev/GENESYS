
if exists (select 1 from information_schema.columns where table_schema = 'dbo' and table_name = 'MedioTransferencia'  and column_name  = 'metCuentaOficinaJudicial')
print ('Existen')
else
begin
 ALTER TABLE MedioTransferencia ADD metCuentaOficinaJudicial VARCHAR(50) NULL;
 ALTER TABLE MedioTransferencia ADD metNumeroOficioJudicial VARCHAR(50) NULL;
 ALTER TABLE MedioTransferencia ADD metNumeroRadicadoJuzgado VARCHAR(50) NULL;
 ALTER TABLE MedioTransferencia ADD metCodigoOficinaJuzgado VARCHAR(50) NULL;

end

if exists ( select 1 from information_schema.columns where table_schema = 'aud' and table_name = 'MedioTransferencia_aud'  and column_name  = 'metCuentaOficinaJudicial')
print ('Existen')
else
begin
    ALTER TABLE aud.MedioTransferencia_aud ADD metCuentaOficinaJudicial VARCHAR(50) NULL;
    ALTER TABLE aud.MedioTransferencia_aud ADD metNumeroOficioJudicial VARCHAR(50) NULL;
    ALTER TABLE aud.MedioTransferencia_aud ADD metNumeroRadicadoJuzgado VARCHAR(50) NULL;
    ALTER TABLE aud.MedioTransferencia_aud ADD metCodigoOficinaJuzgado VARCHAR(50) NULL;
 end