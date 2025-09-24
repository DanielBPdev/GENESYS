--liquibase formatted sql
--changeset mosanchez:01 runAlways:true runOnChange:true
--comment: Creaci�n de CKs 2017-12-20T20:53:18Z
ALTER TABLE Beneficio ADD CONSTRAINT CK_Beneficio_befTipoBeneficio check (befTipoBeneficio in ('LEY_590','LEY_1429'));

ALTER TABLE CajaCompensacion ADD CONSTRAINT CK_CajaCompensacion_ccfMetodoGeneracionEtiquetas check (ccfMetodoGeneracionEtiquetas in ('PREIMPRESA','GENERADA'));

ALTER TABLE GradoAcademico ADD CONSTRAINT CK_GradoAcademico_graNivelEducativo check (graNivelEducativo in ('NINGUNO','SUPERIOR','EDUCACION_PARA_TRABAJO_Y_DESARROLLO_HUMANO','MEDIA_COMPLETA','MEDIA_INCOMPLETA','BASICA_SECUNDARIA_COMPLETA','SUPERIOR','BASICA_SECUNDARIA_INCOMPLETA','BASICA_PRIMARIA_COMPLETA','BASICA_PRIMARIA_INCOMPLETA','PREESCOLAR'));

ALTER TABLE Infraestructura ADD CONSTRAINT CK_Infraestructura_infAreaGeografica check (infAreaGeografica in ('RURAL','URBANA'));

ALTER TABLE Requisito ADD CONSTRAINT CK_Requisito_reqEstado check (reqEstado in ('INHABILITADO','HABILITADO'));

ALTER TABLE TipoInfraestructura ADD CONSTRAINT CK_TipoInfraestructura_tifMedidaCapacidad check (tifMedidaCapacidad in ('NO_APLICA','METROS_CUADRADOS','NUMERO_MAXIMO_CUPOS','NUMERO_MAXIMO_PERSONAS'));

ALTER TABLE TipoDocumentoSoporteFovis ADD CONSTRAINT CK_TipoDocumentoSoporteFovis_tdsEntidad check (tdsEntidad in ('OFERENTE','PSV'));
