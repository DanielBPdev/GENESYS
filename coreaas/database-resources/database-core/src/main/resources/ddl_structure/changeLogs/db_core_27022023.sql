INSERT INTO Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion, prmTipoDato)
VALUES ('REG_PASSWORD_CERTIFICADO', 'A5E9?SCmNk\-8U*', NULL, 'VALOR_GLOBAL_TECNICO','Valor que contiene la clave de acceso del certificado como documento', 'TEXT'),
('REG_BUCKET_CERTIFICADO', 'ccf41', NULL, 'VALOR_GLOBAL_TECNICO','Valor que contiene el Bucket de google donde esta el certificado', 'TEXT'), 
('REG_FILENAME_CERTIFICADO', 'certificado.pfx', NULL, 'VALOR_GLOBAL_TECNICO','Valor que contiene el nombre del certificado en el Bucket', 'TEXT'),
('REG_URI_TOKENSERV', 'https://ani.asocajas.org.co:25341/api/autenticacion', NULL, 'VALOR_GLOBAL_TECNICO','Valor que contiene la URL para el llamado del Token de Asocajas-Registraduria', 'TEXT'),
('REG_USER_TOKENSERV', 'carlos.chica@comfenalcoantioquia.com', NULL, 'VALOR_GLOBAL_TECNICO','Valor que contiene el usuario autorizado para el proceso Asocajas-Registraduria', 'TEXT'),
('REG_PASS_TOKENSERV', 'Comfe20.', NULL, 'VALOR_GLOBAL_TECNICO','Valor que contiene la clave de acceso del usuario autorizado para el proceso Asocajas-Registraduria', 'TEXT'),
('REG_URL_API_NUIP', 'https://ani.asocajas.org.co:25341/api/sirc-individual-nuip', NULL, 'VALOR_GLOBAL_TECNICO','Valor que contiene la api para consultar datos de la registraduria Nuip', 'TEXT'),
('REG_URL_API_RC', 'https://ani.asocajas.org.co:25341/api/sirc-individual-serial', NULL, 'VALOR_GLOBAL_TECNICO','Valor que contiene la api para consultar datos de la registraduria Serial', 'TEXT'),
('REG_URL_API_CEDULA', 'https://ani.asocajas.org.co:25341/api/validar-cedulas', NULL, 'VALOR_GLOBAL_TECNICO','Valor que contiene la api para consultar datos de la registraduria Serial', 'TEXT');



CREATE TABLE [dbo].[DatosRegistraduriaNacional](
	[drnId] [bigint] IDENTITY(1,1) NOT NULL,
	[drnNuipANI] [varchar](20) NULL,
	[drnPrimerApellidoCED] [varchar](20) NULL,
	[drnSegundoApellidoCED] [varchar](20) NULL,
	[drnPrimerNombreCED] [varchar](20) NULL,
	[drnSegundoNombreCED] [varchar](20) NULL,
	[drnFechaNacimientoCED] [date] NULL,
	[drnGeneroCED] [varchar](20) NULL,
	[drnFechaFallecimientoCED] [date] NULL,
	[drnNuipRNCE] [varchar](20) NULL,
	[drnPrimerApellidoREG] [varchar](20) NULL,
	[drnSegundoApellidoREG] [varchar](20) NULL,
	[drnPrimerNombreREG] [varchar](20) NULL,
	[drnSegundoNombreREG] [varchar](20) NULL,
	[drnFechaNacimientoREG] [date] NULL,
	[drnGeneroREG] [varchar](20) NULL,
	[drnDepartamentoNacimiento] [varchar](20) NULL,
	[drnMunicipioNacimiento] [varchar](20) NULL,
	[drnDepartamentoInscripcion] [varchar](20) NULL,
	[drnTipoIdentificacionMadre] [varchar](20) NULL,
	[drnNumeroIdentificacionMadre] [varchar](20) NULL,
	[drnPrimerApellidoMadre] [varchar](20) NULL,
	[drnSegundoApellidoMadre] [varchar](20) NULL,
	[drnPrimerNombreMadre] [varchar](20) NULL,
	[drnSegundoNombreMadre] [varchar](20) NULL,
	[drnTipoIdentificacionPadre] [varchar](20) NULL,
	[drnNumeroIdentificacionPadre] [varchar](20) NULL,
	[drnPrimerApellidoPadre] [varchar](20) NULL,
	[drnSegundoApellidoPadre] [varchar](20) NULL,
	[drnPrimerNombrePadre] [varchar](20) NULL,
	[drnSegundoNombrePadre] [varchar](20) NULL,
	[drnFechaConsulta] [date] NULL,
	[drnNumeroIdentificacion] [varchar](20) NULL,
	[drnTipoIdentificacion] [varchar](20) NULL,
	[drnParticula] [varchar](20) NULL,
	[drnMunicipioExpedicion] [varchar](20) NULL,
	[drnDepartamentoExpedicion] [varchar](20) NULL,
	[drnFechaExpedicion] [varchar](20) NULL,
	[drnEstadoCED] [varchar](20) NULL,
	[drnNumeroResolucion] [varchar](20) NULL,
	[drnAnoResolucion] [varchar](20) NULL,
	[drnInformante] [varchar](20) NULL,
	[drnSerialDefuncion] [varchar](20) NULL,
	[drnLugarNovedad] [varchar](20) NULL,
	[drnLugarPreparacion] [varchar](20) NULL,
	[drnGrupoSanguineo] [varchar](20) NULL,
	[drnEstatura] [varchar](20) NULL,
	[drnCorrecionNacimiento] [date] NULL,
	[drnOficinaInscripcion] [varchar](100) NULL,
	[drnFechaInscripcion] [varchar](20) NULL,
	[drnEstadoREG] [varchar](20) NULL,
	[drnSerial] [varchar](20) NULL,
	[drnLugarNacimiento] [varchar](20) NULL,
	[drnNacionalidadMadre] [varchar](20) NULL,
	[drnPaisNacimiento] [varchar](20) NULL)

ALTER TABLE [dbo].[DatosRegistraduriaNacional]  WITH CHECK ADD  CONSTRAINT [CK_datosregistradurianacional_drnTipoIdentificacion] CHECK  (([drnTipoIdentificacion]='PERM_ESP_PERMANENCIA' OR [drnTipoIdentificacion]='SALVOCONDUCTO' OR [drnTipoIdentificacion]='NIT' OR [drnTipoIdentificacion]='CARNE_DIPLOMATICO' OR [drnTipoIdentificacion]='PASAPORTE' OR [drnTipoIdentificacion]='CEDULA_EXTRANJERIA' OR [drnTipoIdentificacion]='CEDULA_CIUDADANIA' OR [drnTipoIdentificacion]='TARJETA_IDENTIDAD' OR [drnTipoIdentificacion]='REGISTRO_CIVIL'))


ALTER TABLE [dbo].[DatosRegistraduriaNacional] CHECK CONSTRAINT [CK_datosregistradurianacional_drnTipoIdentificacion]



CREATE TABLE DatosRegistraduriaNacionalAud(
	dnaId BIGINT IDENTITY(1,1) NOT NULL,
	dnaFechaInicial VARCHAR(50),
	dnaFechaFinal VARCHAR(50),
	dnaTipoIdentificacion VARCHAR(20),
	dnaNumeroIdentificacion VARCHAR(20),
	dnaEstadoDocumento NUMERIC(19, 5),
	dnaConsulta VARCHAR(2000),
	dnaWebService VARCHAR(200),
	dnaRespuesta VARCHAR(200) ,
	dnaMarca VARCHAR(200) ,
	dnaUsuario VARCHAR(20),
);

ALTER TABLE [dbo].DatosRegistraduriaNacionalAud  WITH CHECK ADD  CONSTRAINT [CK_datosregistradurianacionalaud_dnaTipoIdentificacion] CHECK  (([dnaTipoIdentificacion]='CEDULA_CIUDADANIA' OR [dnaTipoIdentificacion]='TARJETA_IDENTIDAD' OR [dnaTipoIdentificacion]='REGISTRO_CIVIL'))

CREATE TABLE ParametrizacionGaps(
prgId smallint IDENTITY(1,1) NOT NULL,
prgProceso varchar(60),
prgNombre varchar(150),
prgDescripcion varchar(255),
prgUsuarui varchar(100),
prgFechaModificacion varchar(100),
prgVersionLiberacion varchar(50),
prgTipoDatos varchar(50),
prgEstado varchar(10)

)