--comment: Agrega campo dsgObservaciones a tabl DocumentosSeguimientoGestion_aud
IF NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name='DocumentosSeguimientoGestion_aud' AND column_name='dsgObservaciones')
ALTER TABLE DocumentosSeguimientoGestion_aud ADD dsgObservaciones varchar(500);