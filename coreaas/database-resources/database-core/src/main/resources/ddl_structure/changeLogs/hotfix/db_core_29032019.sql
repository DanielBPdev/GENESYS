--liquibase formatted sql

--changeset mamonroy:01
--comment: 
DELETE variablecomunicado FROM plantillaComunicado
JOIN variablecomunicado ON pcoId = vcoPlantillaComunicado
WHERE pcoEtiqueta = 'ACT_ASIG_FOVIS'
AND vcoClave = '${modalidad}';