--liquibase formatted sql

--changeset hhernandez:01
--comment: Se hace la insercion en la tabla ParametrizacionEjecucionProgramada 
INSERT INTO ParametrizacionEjecucionProgramada (pepProceso, pepHoras, pepMinutos, pepSegundos, pepFrecuencia) values ('PILA_CARGA_DESCARGA_AUTOMATICA_ARCHIVOS_OI', '00','10','00','DIARIO');

--changeset jusanchez:02
--comment: Se hace la insercion en la tabla Parametro
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmEstado) VALUES ('CODIGO_FINANCIERO', '1111', 0, 'VALOR_GLOBAL_NEGOCIO', 1);
