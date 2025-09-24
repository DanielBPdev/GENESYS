--liquibase formatted sql
--changeset dsuesca:01 runAlways:true runOnChange:true
--comment: Creación de borrado de CKs 2019-07-25T17:01:29Z
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Beneficio_befTipoBeneficio')) ALTER TABLE Beneficio DROP CONSTRAINT CK_Beneficio_befTipoBeneficio;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Infraestructura_infAreaGeografica')) ALTER TABLE Infraestructura DROP CONSTRAINT CK_Infraestructura_infAreaGeografica;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_TipoInfraestructura_tifMedidaCapacidad')) ALTER TABLE TipoInfraestructura DROP CONSTRAINT CK_TipoInfraestructura_tifMedidaCapacidad;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_CajaCompensacion_ccfMetodoGeneracionEtiquetas')) ALTER TABLE CajaCompensacion DROP CONSTRAINT CK_CajaCompensacion_ccfMetodoGeneracionEtiquetas;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Requisito_reqEstado')) ALTER TABLE Requisito DROP CONSTRAINT CK_Requisito_reqEstado;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RequisitoCajaClasificacion_rtsClasificacion')) ALTER TABLE RequisitoCajaClasificacion DROP CONSTRAINT CK_RequisitoCajaClasificacion_rtsClasificacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RequisitoCajaClasificacion_rtsTipoTransaccion')) ALTER TABLE RequisitoCajaClasificacion DROP CONSTRAINT CK_RequisitoCajaClasificacion_rtsTipoTransaccion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RequisitoCajaClasificacion_rtsEstado')) ALTER TABLE RequisitoCajaClasificacion DROP CONSTRAINT CK_RequisitoCajaClasificacion_rtsEstado;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RequisitoCajaClasificacion_rtsTipoRequisito')) ALTER TABLE RequisitoCajaClasificacion DROP CONSTRAINT CK_RequisitoCajaClasificacion_rtsTipoRequisito;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_TipoDocumentoRequisito_tdrTipoDocumento')) ALTER TABLE TipoDocumentoRequisito DROP CONSTRAINT CK_TipoDocumentoRequisito_tdrTipoDocumento;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_GradoAcademico_graNivelEducativo')) ALTER TABLE GradoAcademico DROP CONSTRAINT CK_GradoAcademico_graNivelEducativo;
