--liquibase formatted sql

--changeset juagonzalez:01
--comment: Insercion en la tabla Parametro
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmEstado) VALUES ('TIEMPO_INACTIVAR_CUENTA','2592000000',0,'EJECUCION_TIMER',1);