--liquibase formatted sql

--changeset jocorrea:01
--comment: 
UPDATE VariableComunicado
SET vcoDescripcion = 'Ciudad de empresa'
WHERE vcoTipoVariableComunicado = 'VARIABLE'
AND vcoNombre = 'Ciudad'
AND vcoPlantillaComunicado IN (SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'CAR_EMP_EXP');

