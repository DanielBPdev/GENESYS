--liquibase formatted sql

--changeset dsuesca:01
--comment:
ALTER TABLE staging.RegistroDetallado ALTER COLUMN redPrimerApellido VARCHAR(50);
ALTER TABLE staging.RegistroDetallado ALTER COLUMN redSegundoApellido VARCHAR(50);
ALTER TABLE staging.RegistroDetallado ALTER COLUMN redPrimerNombre VARCHAR(50);
ALTER TABLE staging.RegistroDetallado ALTER COLUMN redSegundoNombre VARCHAR(50);
