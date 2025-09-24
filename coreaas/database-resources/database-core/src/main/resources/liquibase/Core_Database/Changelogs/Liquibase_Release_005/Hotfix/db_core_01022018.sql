--liquibase formatted sql

--changeset jusanchez:01 context:integracion 
--comment:Se agregan parametros para Archivos ECM para el ambiente de integracion
INSERT INTO Parametro(prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion)VALUES('ARCHIVO_PLANTILLA_EXCEL_CARGUE_MASIVO_NOVEDADES_449', '7b245399-c8e4-4b33-9a0a-a4ce558aec4d', 0, 'VALOR_GLOBAL_NEGOCIO', 'Identificador de la plantilla de Excel para el cargue múltiple del proceso de novedades masivas 1.3.5');

--changeset jusanchez:02 context:pruebas 
--comment:Se agregan parametros para Archivos ECM para el ambiente de pruebas
INSERT INTO Parametro(prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion)VALUES('ARCHIVO_PLANTILLA_EXCEL_CARGUE_MASIVO_NOVEDADES_449', '3c4a394f-e11a-4ee9-a2b7-36cd54ebaf2e', 0, 'VALOR_GLOBAL_NEGOCIO', 'Identificador de la plantilla de Excel para el cargue múltiple del proceso de novedades masivas 1.3.5');

--changeset jusanchez:03 context:integracion-asopagos
--comment:Se agregan parametros para Archivos ECM para el ambiente de espejo
INSERT INTO Parametro(prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion)VALUES('ARCHIVO_PLANTILLA_EXCEL_CARGUE_MASIVO_NOVEDADES_449', 'f7f30028-3d4a-4382-b773-77c09bc4015c', 0, 'VALOR_GLOBAL_NEGOCIO', 'Identificador de la plantilla de Excel para el cargue múltiple del proceso de novedades masivas 1.3.5');

--changeset jusanchez:04 context:asopagos_confa 
--comment:Se agregan parametros para Archivos ECM para el ambiente de asopagos confa
INSERT INTO Parametro(prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion)VALUES('ARCHIVO_PLANTILLA_EXCEL_CARGUE_MASIVO_NOVEDADES_449', '4e45a99a-850d-433d-8180-1528e44ced09', 0, 'VALOR_GLOBAL_NEGOCIO', 'Identificador de la plantilla de Excel para el cargue múltiple del proceso de novedades masivas 1.3.5');

--changeset jusanchez:05 context:asopagos_funcional 
--comment:Se agregan parametros para Archivos ECM para el ambiente de asopagos funcional
INSERT INTO Parametro(prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion)VALUES('ARCHIVO_PLANTILLA_EXCEL_CARGUE_MASIVO_NOVEDADES_449', '07719f58-b671-48bb-b278-34b09e91f6ce', 0, 'VALOR_GLOBAL_NEGOCIO', 'Identificador de la plantilla de Excel para el cargue múltiple del proceso de novedades masivas 1.3.5');