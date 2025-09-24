--liquibase formatted sql

--changeset jusanchez:01
--comment: Insercion en la tabla Parametro
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro) VALUES ('ACEPTA_DERECHOS_ACCESO','ACEPTA_DERECHOS_ACCESO',1,'VALOR_GLOBAL_NEGOCIO');