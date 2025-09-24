--liquibase formatted sql

--changeset borozco:01
--comment: Se adiciona campo en la tabla DesafiliacionAportante_aud 
EXEC sp_RENAME 'DesafiliacionAportante_aud.apdTipoLineaCobro' , 'deaTipoLineaCobro', 'COLUMN';

--changeset squintero:02
--comment: Se elimina campo en la tabla ParametrizacionLiquidacionSubsidio_aud
ALTER TABLE ParametrizacionLiquidacionSubsidio_aud DROP COLUMN plsCodigoCajaCompensacion;