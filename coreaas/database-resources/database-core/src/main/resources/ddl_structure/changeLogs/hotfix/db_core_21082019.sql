--liquibase formatted sql

--changeset jocorrea:01
--comment:
UPDATE sld SET sld.sldJsonPostulacion = JSON_MODIFY(pof.pofInfoAsignacion,'$.postulacion.informacionPostulacion', NULL)
FROM SolicitudLegalizacionDesembolso sld
JOIN PostulacionFovis pof ON (sld.sldPostulacionFovis = pof.pofid)
