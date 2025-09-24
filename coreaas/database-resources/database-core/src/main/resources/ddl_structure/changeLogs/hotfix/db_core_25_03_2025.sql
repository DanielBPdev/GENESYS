if not exists(SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'ControlCertificadosMasivos' AND COLUMN_NAME = 'ccmNombreCargue')
	begin
		alter table ControlCertificadosMasivos add ccmNombreCargue varchar(36) null;
	end

if not exists(SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'ControlCertificadosMasivos_aud'  AND COLUMN_NAME = 'ccmNombreCargue' and TABLE_SCHEMA =  'aud')
	begin
		alter table aud.ControlCertificadosMasivos_aud add ccmNombreCargue varchar(36) null;
	end