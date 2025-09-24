--liquibase formatted sql

--changeset atoro:01
--comment:Se modifica campo en las tablas RolAfiliado_aud y Empleador_aud
ALTER TABLE RolAfiliado_aud ALTER COLUMN roaFechaRetiro DATETIME;
ALTER TABLE Empleador_aud ALTER COLUMN empFechaRetiro DATETIME;
