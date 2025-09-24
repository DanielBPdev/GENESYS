--liquibase formatted sql
--changeset mosanchez:01 runAlways:true runOnChange:true
--comment: Creaci�n de borrado de CKs 2017-12-20T20:53:18Z
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Beneficio_befTipoBeneficio')) ALTER TABLE Beneficio DROP CONSTRAINT CK_Beneficio_befTipoBeneficio;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CajaCompensacion_ccfMetodoGeneracionEtiquetas')) ALTER TABLE CajaCompensacion DROP CONSTRAINT CK_CajaCompensacion_ccfMetodoGeneracionEtiquetas;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_GradoAcademico_graNivelEducativo')) ALTER TABLE GradoAcademico DROP CONSTRAINT CK_GradoAcademico_graNivelEducativo;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Infraestructura_infAreaGeografica')) ALTER TABLE Infraestructura DROP CONSTRAINT CK_Infraestructura_infAreaGeografica;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Requisito_reqEstado')) ALTER TABLE Requisito DROP CONSTRAINT CK_Requisito_reqEstado;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_TipoInfraestructura_tifMedidaCapacidad')) ALTER TABLE TipoInfraestructura DROP CONSTRAINT CK_TipoInfraestructura_tifMedidaCapacidad;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_TipoDocumentoSoporteFovis_tdsEntidad')) ALTER TABLE TipoDocumentoSoporteFovis DROP CONSTRAINT CK_TipoDocumentoSoporteFovis_tdsEntidad;
