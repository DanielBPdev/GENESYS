--liquibase formatted sql

--changeset arocha:00
--comment: Se eliminan procedimientos almacenados y se adicionan campos en las tablas staging.Aportante y staging.Cotizante
IF ( Object_id( 'USP_GetAporteEmpresasPlanillaSSIS' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_GetAporteEmpresasPlanillaSSIS];
IF ( Object_id( 'USP_GetAportantesPlanillaSSIS' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_GetAportantesPlanillaSSIS];
IF ( Object_id( 'USP_GetCotizantesPlanillaSSIS' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_GetCotizantesPlanillaSSIS];
IF ( Object_id( 'USP_ValidateHU480V3_T1T2T3' ) IS NOT NULL ) DROP PROCEDURE [dbo].[USP_ValidateHU480V3_T1T2T3];

--changeset arocha:01
--comment: Se eliminan procedimientos almacenados y se adicionan campos en las tablas staging.Aportante y staging.Cotizante
ALTER TABLE staging.Aportante ADD apoPeriodoAporte VARCHAR(7);
ALTER TABLE staging.Cotizante ADD cotPeriodoAporte VARCHAR(7);

