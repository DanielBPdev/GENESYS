--liquibase formatted sql

--changeset abaquero:01
--comment: Actualizacion tabla ValidatorDefinition
UPDATE ValidatorDefinition SET state=0 WHERE id=2110195;

--changeset arocha:02
--comment: Insercion tabla Parametro 
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES('TIEMPO_MINIMO_CONSECUTIVO_SERVICIOS_CAJA','15552000000',0,'EJECUCION_TIMER','Número de períodos mínimos consecutivos como afiliado trabajador dependiente'),('TIEMPO_ADICIONAL_SERVICIOS_CAJA','5184000000',0,'EJECUCION_TIMER','Tiempo adicional de servicios en la caja');