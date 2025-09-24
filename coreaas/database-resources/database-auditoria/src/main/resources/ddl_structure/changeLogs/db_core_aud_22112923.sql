--comment: Agrega campo carPrescribir a tabl cartera_aud
IF NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name='cartera_aud' AND column_name='carPrescribir')
ALTER TABLE cartera_aud ADD carPrescribir varchar(32);