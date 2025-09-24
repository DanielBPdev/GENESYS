--liquibase formatted sql

--changeset fvasquez:01
--comment: 
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_BitacoraCartera_bcaActividad')) ALTER TABLE CargueMultiple DROP CONSTRAINT CK_BitacoraCartera_bcaActividad;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_BitacoraCartera_bcaResultado')) ALTER TABLE CargueMultiple DROP CONSTRAINT CK_BitacoraCartera_bcaResultado;