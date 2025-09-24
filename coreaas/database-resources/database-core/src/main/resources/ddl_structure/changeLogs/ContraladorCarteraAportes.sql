if not exists (select * from sys.objects as o where o.name ='ContraladorCarteraAportes')
begin
CREATE TABLE dbo.ContraladorCarteraAportes(
	ccaId BIGINT NOT NULL IDENTITY(1,1),
	ccaTipoIdentificacion VARCHAR (50) NULL,
	ccaNumeroIdentificacion VARCHAR (50) NULL,
	ccaPeriodoAporte VARCHAR (20) NULL,
	ccaTipoSolicitante VARCHAR (50) NULL,
	ccaFechaRegistro DATE NULL,
	ccaPasoCartera BIT NULL,
	ccaPlanilla BIGINT NULL,
	ccaError VARCHAR (100) NULL ,
CONSTRAINT PK_ContraladorCarteraAportes_ccaId PRIMARY KEY (ccaId)
);
end

ALTER TABLE ContraladorCarteraAportes ADD CONSTRAINT CK_ContraladorCarteraAportes_ccaTipoIdentificacion check (ccaTipoIdentificacion in ('REGISTRO_CIVIL','TARJETA_IDENTIDAD','CEDULA_CIUDADANIA','CEDULA_EXTRANJERIA','PASAPORTE','CARNE_DIPLOMATICO','NIT','SALVOCONDUCTO','PERM_ESP_PERMANENCIA','PERM_PROT_TEMPORAL'));

ALTER TABLE ContraladorCarteraAportes ADD CONSTRAINT CK_ContraladorCarteraAportes_ccaTipoSolicitante check (ccaTipoSolicitante in ('EMPLEADOR','INDEPENDIENTE','PENSIONADO'));