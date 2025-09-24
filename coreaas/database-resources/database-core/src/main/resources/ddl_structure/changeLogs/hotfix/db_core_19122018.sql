--liquibase formatted sql

--changeset clmarin:01
--comment: Actualizacion tabla PlantillaComunicado
UPDATE PlantillaComunicado
SET pcoAsunto='Aprobación Cierre aportes - Analista contable',  pcoNombre='Aprobación Cierre aportes - Analista contable'
WHERE pcoEtiqueta='APR_ANL_CON';
