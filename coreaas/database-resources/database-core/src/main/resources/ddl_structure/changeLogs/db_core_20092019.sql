--liquibase formatted sql

--changeset mamonroy:01
--comment:
UPDATE vco
SET vco.vcoTipoVariableComunicado = 'CONSTANTE', VCO.vcoNombreConstante = 'CIUDAD_CCF'
FROM VariableComunicado Vco
JOIN PlantillaComunicado pco ON pco.pcoId = vco.vcoPlantillaComunicado
WHERE vco.vcoClave = '${ciudadSolicitud}';