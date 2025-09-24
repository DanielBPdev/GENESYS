--liquibase formatted sql

--changeset atoro:01
--comment:Se modifica campo en las tablas RolAfiliado y Empleador
ALTER TABLE RolAfiliado ALTER COLUMN roaFechaRetiro DATETIME;
ALTER TABLE Empleador ALTER COLUMN empFechaRetiro DATETIME;

--changeset jroa:02
--comment:Eliminacion de check constraints de la tabla InicioTarea
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_InicioTarea_itaProceso')) ALTER TABLE InicioTarea DROP CONSTRAINT CK_InicioTarea_itaProceso;
