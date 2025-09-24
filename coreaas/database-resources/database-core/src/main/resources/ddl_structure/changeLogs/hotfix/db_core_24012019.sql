--liquibase formatted sql

--changeset jocorrea:01
--comment: 
ALTER TABLE PostulacionFovis ADD pofValorAvaluoCatastral NUMERIC(19,5) NULL;

--changeset jocorrea:02
--comment: 

UPDATE PostulacionFovis
SET pofValorAvaluoCatastral = (SELECT psvAvaluoCatastral FROM ProyectoSolucionVivienda WHERE psvId = pofProyectoSolucionVivienda)
WHERE pofModalidad IN ('MEJORAMIENTO_VIVIENDA_RURAL', 'MEJORAMIENTO_VIVIENDA_URBANA', 'MEJORAMIENTO_VIVIENDA_SALUDABLE');

ALTER TABLE ProyectoSolucionVivienda DROP COLUMN psvAvaluoCatastral;

--changeset jocorrea:03
--comment: 

ALTER TABLE aud.PostulacionFovis_aud ADD pofValorAvaluoCatastral NUMERIC(19,5) NULL;