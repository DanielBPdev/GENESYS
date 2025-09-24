--liquibase formatted sql

--changeset mosanchez:01
--comment: Se modifica campo en la tabla ParametrizacionSubsidioLiquidacion_aud
EXEC sp_rename 'dbo.ParametrizacionLiquidacionSubsidio_aud.pcsSMLMV', 'plsSMLMV', 'COLUMN';

--changeset rlopez:02
--comment: Se adicionan campos a la tabla SolicitudLiquidacionSubsidio_aud
ALTER TABLE SolicitudLiquidacionSubsidio_aud ADD slsFechaEvaluacionPrimerNivel DATETIME NULL;
ALTER TABLE SolicitudLiquidacionSubsidio_aud ADD slsFechaEvaluacionSegundoNivel DATETIME NULL;

--changeset jocampo:03
--comment:Se modifica tamaño de campo en la tabla SolicitudLiquidacionSubsidio_aud
ALTER TABLE SolicitudLiquidacionSubsidio_aud ALTER COLUMN slsTipoLiquidacion VARCHAR(33) NOT NULL;

--changeset ecastano:04
--comment:Se adiciona campo en la tabla CicloAsignacion_aud
ALTER TABLE CicloAsignacion_aud ADD ciaValorDisponible NUMERIC(19,5) NULL;

--changeset jocampo:05
--comment: Se eliminan tablas SubsidioMonetarioValorPignorado_aud y ArchivoEntidadDescuentoSubsidioPignorado_aud
ALTER TABLE SubsidioMonetarioValorPignorado_aud DROP CONSTRAINT FK_SubsidioMonetarioValorPignorado_aud_REV;
DROP TABLE SubsidioMonetarioValorPignorado_aud;
ALTER TABLE ArchivoEntidadDescuentoSubsidioPignorado_aud DROP CONSTRAINT FK_ArchivoEntidadDescuentoSubsidioPignorado_aud_REV;
DROP TABLE ArchivoEntidadDescuentoSubsidioPignorado_aud;

--changeset abaquero:06 
--comment: Adicion tablas de modelo de subsidios para liquidacion especifica
ALTER TABLE dbo.SolicitudLiquidacionSubsidio_aud ADD slsCodigoReclamo varchar(50);
ALTER TABLE dbo.SolicitudLiquidacionSubsidio_aud ADD slsComentarioReclamo varchar(250);