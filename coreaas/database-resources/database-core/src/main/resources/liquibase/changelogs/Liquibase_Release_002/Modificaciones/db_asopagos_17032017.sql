--liquibase formatted sql

--changeset clmarin:01
--comment: Se borran las VariableComunicado
DELETE FROM PlantillaComunicado where pcoNombre = 'Desistimiento novedad de retiro de aportante';

--changeset clmarin:02
--comment: Se borran todas las VariableComunicado
DELETE FROM variablecomunicado WHERE EXISTS (SELECT * FROM plantillaComunicado pc WHERE pc.pcoid= variablecomunicado.vcoPlantillaComunicado AND pc.pcoetiqueta ='NTF_NVD_EMP');
DELETE FROM variablecomunicado WHERE EXISTS (SELECT * FROM plantillaComunicado pc WHERE pc.pcoid= variablecomunicado.vcoPlantillaComunicado AND pc.pcoetiqueta ='NTF_RAD_NVD_EMP');
DELETE FROM variablecomunicado WHERE EXISTS (SELECT * FROM plantillaComunicado pc WHERE pc.pcoid= variablecomunicado.vcoPlantillaComunicado AND pc.pcoetiqueta ='RCHZ_NVD_EMP_PROD_NSUBLE');
DELETE FROM variablecomunicado WHERE EXISTS (SELECT * FROM plantillaComunicado pc WHERE pc.pcoid= variablecomunicado.vcoPlantillaComunicado AND pc.pcoetiqueta ='CNFR_RET_APRT');

--changeset halzate:03
--comment: Se borra registro VariableComunicado
DELETE FROM VariableComunicado WHERE vcoPlantillaComunicado = (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Carta de bienvenida para empleador');

--changeset halzate:04
--comment: Actualizacion en VariableComunicado
UPDATE VariableComunicado SET vcoTipoVariableComunicado = 'VARIABLE'
WHERE vcoPlantillaComunicado = (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Carta de bienvenida para empleador')
AND vcoNombreConstante IS NULL;

UPDATE VariableComunicado SET vcoTipoVariableComunicado = 'CONSTANTE'
WHERE vcoPlantillaComunicado = (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Carta de bienvenida para empleador')
AND vcoNombreConstante IS NOT NULL;

UPDATE VariableComunicado SET vcoTipoVariableComunicado = 'USUARIO'
WHERE vcoPlantillaComunicado = (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Carta de bienvenida para empleador')
AND (vcoClave = '${responsableCcf}' OR vcoClave = '${responsableAfiliacionCcf}')

--changeset clmarin:05
--comment: Se borran todas las VariableComunicado
DELETE FROM variablecomunicado WHERE EXISTS (SELECT * FROM plantillaComunicado pc WHERE pc.pcoid= variablecomunicado.vcoPlantillaComunicado AND pc.pcoetiqueta ='NTF_NVD_EMP');
DELETE FROM variablecomunicado WHERE EXISTS (SELECT * FROM plantillaComunicado pc WHERE pc.pcoid= variablecomunicado.vcoPlantillaComunicado AND pc.pcoetiqueta ='NTF_RAD_NVD_EMP');
DELETE FROM variablecomunicado WHERE EXISTS (SELECT * FROM plantillaComunicado pc WHERE pc.pcoid= variablecomunicado.vcoPlantillaComunicado AND pc.pcoetiqueta ='RCHZ_NVD_EMP_PROD_NSUBLE');
DELETE FROM variablecomunicado WHERE EXISTS (SELECT * FROM plantillaComunicado pc WHERE pc.pcoid= variablecomunicado.vcoPlantillaComunicado AND pc.pcoetiqueta ='CNFR_RET_APRT');
DELETE FROM variablecomunicado WHERE EXISTS (SELECT * FROM plantillaComunicado pc WHERE pc.pcoid= variablecomunicado.vcoPlantillaComunicado AND pc.pcoetiqueta ='DSTMTO_NVD_RET_APRT');

delete from plantillacomunicado where pcoetiqueta= 'DSTMTO_NVD_RET_APRT';



