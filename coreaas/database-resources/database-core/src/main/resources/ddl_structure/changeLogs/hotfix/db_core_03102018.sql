--liquibase formatted sql

--changeset abaquero:01
--comment: Adición de parámetros para los límites de tamaño de archivos PILA
INSERT INTO dbo.Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion)
VALUES ('TAMANO_ARCHIVOS_PILA_EN_MEGABYTES','5',0,'VALOR_GLOBAL_NEGOCIO','TAMAÑO MÁXIMO QUE PUEDE TENER UN ARCHIVO QUE ES CARGADO EN PILA')
INSERT INTO dbo.Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion)
VALUES ('TAMANO_ARCHIVOS_PILA_GRUPAL_EN_MEGABYTES','10',0,'VALOR_GLOBAL_NEGOCIO','TAMAÑO MÁXIMO QUE PUEDE TENER UN GRUPO DE ARCHIVOS QUE SON CARGADOS EN PILA')

--changeset dsuesca:02
--comment: Se cambia tipo de dato de columna AporteDetallado_aud
ALTER TABLE aud.AporteDetallado_aud ALTER COLUMN apdMunicipioLaboral VARCHAR(5);