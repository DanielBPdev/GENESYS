--liquibase formatted sql

--changeset borozco:01
--comment: Se modifica campo en la tabla DesafiliacionAportante
EXEC sp_RENAME 'DesafiliacionAportante.apdTipoLineaCobro' , 'deaTipoLineaCobro', 'COLUMN';

--changeset squintero:02
--comment: Se elimina campo en la tabla ParametrizacionLiquidacionSubsidio
ALTER TABLE ParametrizacionLiquidacionSubsidio DROP COLUMN plsCodigoCajaCompensacion;
