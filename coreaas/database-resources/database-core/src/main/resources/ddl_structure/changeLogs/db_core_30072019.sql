--liquibase formatted sql

--changeset mamonroy:01
--comment:
UPDATE DestinatarioComunicado
SET dcoEtiquetaPlantilla = 'NTF_INT_AFL_DEP'
WHERE dcoProceso = 'AFILIACION_DEPENDIENTE_WEB'
AND dcoEtiquetaPlantilla = 'NTF_INT_AFL';

UPDATE DestinatarioComunicado
SET dcoEtiquetaPlantilla = 'NTF_INT_AFL_DEP'
WHERE dcoProceso = 'AFILIACION_PERSONAS_PRESENCIAL'
AND dcoEtiquetaPlantilla = 'NTF_INT_AFL';