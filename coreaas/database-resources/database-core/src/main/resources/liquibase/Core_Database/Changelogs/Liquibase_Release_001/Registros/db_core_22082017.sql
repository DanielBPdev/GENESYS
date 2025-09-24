--liquibase formatted sql

--changeset jusanchez:01
--comment: Insercion de registro en la tabla parametro
INSERT Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion) VALUES ('NUMERO_REGISTROS_LECTURA_AFILIACION_MULTIPLE', '50', 0, 'FILE_DEFINITION', ' Parámetro que contiene el número de registros de lectura de afiliación de personas dependientes');
