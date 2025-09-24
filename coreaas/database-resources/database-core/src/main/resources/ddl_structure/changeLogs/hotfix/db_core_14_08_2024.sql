IF EXISTS (SELECT COUNT(*)
FROM information_schema.tables
WHERE table_schema = 'dbo'
AND table_name = 'RegistroOperacionTransaccionSubsidio')
ALTER TABLE RegistroOperacionTransaccionSubsidio
ADD rotUrl VARCHAR(500) NULL, rotTiempo varchar(20) NULL;