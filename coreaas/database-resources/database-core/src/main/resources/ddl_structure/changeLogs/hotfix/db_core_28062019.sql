--liquibase formatted sql

--changeset mamonroy:01
--comment: Parámetro envió correos y actualización de check
IF NOT EXISTS (SELECT * FROM Parametro WHERE prmNombre ='ENVIO_CORREOS_ACTIVO') INSERT INTO Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion, prmTipoDato) VALUES ('ENVIO_CORREOS_ACTIVO','TRUE',0,'VALOR_GLOBAL_TECNICO','Indica si el envío de correos se encuentra actvo','BOOLEAN');