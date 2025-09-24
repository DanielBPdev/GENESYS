--liquibase formatted sql

--changeset cwaldo:01
--comment: Se agrega campo cvsTipoProceso a tabla ConjuntoValidacionSubsidio
ALTER TABLE ConjuntoValidacionSubsidio add cvsTipoProceso VARCHAR(50);

--changeset cwaldo:02
--comment: Update a tabla ConjuntoValidacionSubsidio
UPDATE ConjuntoValidacionSubsidio SET cvsTipoProceso='RECONOCIMIENTO' WHERE cvsId<29;
UPDATE ConjuntoValidacionSubsidio SET cvsTipoProceso='FALLECIMIENTO_BENEFICIARIO' WHERE cvsId<50 AND cvsId>28;
UPDATE ConjuntoValidacionSubsidio SET cvsTipoProceso='BENEFICIARIO_TRABAJADOR' WHERE cvsId>49;