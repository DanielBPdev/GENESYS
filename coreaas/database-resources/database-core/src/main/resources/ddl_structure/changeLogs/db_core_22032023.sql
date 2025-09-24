		ALTER TABLE proveedor DROP CONSTRAINT [CK_proveedor_provTipoIdentificacionTitular] 
		ALTER TABLE proveedor WITH CHECK ADD  CONSTRAINT [CK_proveedor_provTipoIdentificacionTitular] CHECK  (([provTipoIdentificacionTitular]='PERM_ESP_PERMANENCIA' OR [provTipoIdentificacionTitular]='SALVOCONDUCTO' OR [provTipoIdentificacionTitular]='NIT' OR [provTipoIdentificacionTitular]='CARNE_DIPLOMATICO' OR [provTipoIdentificacionTitular]='PASAPORTE' OR [provTipoIdentificacionTitular]='CEDULA_EXTRANJERIA' OR [provTipoIdentificacionTitular]='CEDULA_CIUDADANIA' OR [provTipoIdentificacionTitular]='TARJETA_IDENTIDAD' OR [provTipoIdentificacionTitular]='REGISTRO_CIVIL' OR [provTipoIdentificacionTitular]='PERM_PROT_TEMPORAL'))

		IF EXISTS ( SELECT * FROM FieldLoadCatalog WHERE id = 32133157)
		BEGIN
			delete from FieldLoadCatalog
			where id= 32133157
			and description = 'Fecha de afiliacion del trabajador o cabeza de familia'
		END

		IF EXISTS ( SELECT * FROM FieldDefinitionLoad WHERE id = 32133157)
		BEGIN
			delete from FieldDefinitionLoad
			where id = 32133157
			and label = 'Fecha de afiliacion del trabajador o cabeza de familia'
		END

		IF EXISTS ( SELECT * FROM FieldLoadCatalog WHERE id = 32133158)
		BEGIN
			update FieldLoadCatalog
			set fieldOrder = '7'
			where id = '32133158'
		END