--liquibase formatted sql

--changeset jusanchez:01
--comment: Actualizacion en la tabla Parametro
UPDATE Parametro SET prmValor='He leído y acepto los términos y condiciones de uso' WHERE prmNombre='ACEPTA_DERECHOS_ACCESO';