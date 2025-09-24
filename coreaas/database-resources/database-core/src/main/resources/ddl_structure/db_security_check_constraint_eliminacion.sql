--liquibase formatted sql
--changeset abaquero:01 runAlways:true runOnChange:true
--comment: Creación de borrado de CKs 2018-12-26 13:20
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Pregunta_preEstado')) ALTER TABLE Pregunta DROP CONSTRAINT CK_Pregunta_preEstado;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ReferenciaToken_retTipoIdentificacion')) ALTER TABLE ReferenciaToken DROP CONSTRAINT CK_ReferenciaToken_retTipoIdentificacion;

