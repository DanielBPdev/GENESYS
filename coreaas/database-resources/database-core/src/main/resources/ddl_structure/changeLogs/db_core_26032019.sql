--liquibase formatted sql

--changeset squintero:01
--comment: 
INSERT Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion)
VALUES ('TAMANO_ARCHIVOS_LISTA_CHEQUEO_EN_MEGABYTES', 3, 0, 'ECM', 'Tamaño máximo que puede tener un archivo cargado a una lista de chequeo');
