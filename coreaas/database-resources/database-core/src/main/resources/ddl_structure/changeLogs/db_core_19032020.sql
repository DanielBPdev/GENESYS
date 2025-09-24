--liquibase formatted sql

--changeset mamonroy:01
--comment:
DELETE FROM DATABASECHANGELOG WHERE FileName = 'ddl_structure/changeLogs/db_core_19032020.sql';

--changeset flopez:02
--comment: CC Estado Civil
UPDATE PersonaDetalle SET pedEstadoCivil= 'CASADO' WHERE pedEstadoCivil in ('CASADO_UNION_LIBRE');

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_BeneficiarioDetalle_bedTipoUnionConyugal')) ALTER TABLE BeneficiarioDetalle DROP CONSTRAINT CK_BeneficiarioDetalle_bedTipoUnionConyugal;
IF EXISTS (SELECT 1 FROM sys.columns  WHERE Name = N'bedTipoUnionConyugal'AND Object_ID = Object_ID(N'dbo.BeneficiarioDetalle')) ALTER TABLE BeneficiarioDetalle DROP COLUMN bedTipoUnionConyugal;
IF EXISTS (SELECT 1 FROM sys.columns  WHERE Name = N'bedTipoUnionConyugal'AND Object_ID = Object_ID(N'aud.BeneficiarioDetalle_aud')) ALTER TABLE aud.BeneficiarioDetalle_aud DROP COLUMN bedTipoUnionConyugal;

IF NOT EXISTS (SELECT novId FROM ParametrizacionNovedad WHERE novTipoTransaccion = 'ACTUALIZACION_TIPO_UNION_CONYUGE_PRESENCIAL') INSERT INTO ParametrizacionNovedad
(novTipoTransaccion, novPuntoResolucion, novRutaCualificada, novTipoNovedad, novProceso, novAplicaTodosRoles)
VALUES('ACTUALIZACION_TIPO_UNION_CONYUGE_PRESENCIAL', 'BACK', 'com.asopagos.novedades.convertidores.persona.ActualizarBeneficiarioNovedadPersona', 'BENEFICIARIO', 'NOVEDADES_PERSONAS_PRESENCIAL', NULL);

IF NOT EXISTS (SELECT novId FROM ParametrizacionNovedad WHERE novTipoTransaccion = 'ACTUALIZACION_TIPO_UNION_CONYUGE_WEB') INSERT INTO ParametrizacionNovedad
(novTipoTransaccion, novPuntoResolucion, novRutaCualificada, novTipoNovedad, novProceso, novAplicaTodosRoles)
VALUES('ACTUALIZACION_TIPO_UNION_CONYUGE_WEB', 'BACK', 'com.asopagos.novedades.convertidores.persona.ActualizarBeneficiarioNovedadPersona', 'BENEFICIARIO', 'NOVEDADES_PERSONAS_WEB', NULL);

DELETE FROM ValidacionProceso WHERE vapBloque LIKE '%ACTUALIZACION_TIPO_UNION_CONYUGE%';