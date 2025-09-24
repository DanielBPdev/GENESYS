--liquibase formatted sql

--changeset squintero:01
--comment:
INSERT INTO parametrizacionEjecucionProgramada (pepProceso, pepHoras, pepMinutos, pepSegundos, pepDiaSemana, pepDiaMes, pepMes, pepAnio, pepFechaInicio, pepFechaFin, pepFrecuencia, pepEstado)
VALUES ('CAMBIO_CATEGORIA_POR_APORTES_FUTUROS', '00', '00', '00', null, '01', null, null, null, null, 'MENSUAL', 'ACTIVO');

--changeset mamonroy:02
--comment:
UPDATE vco
SET vco.vcoTipoVariableComunicado = 'CONSTANTE',vcoNombreConstante = 'CIUDAD_CCF'
FROM PlantillaComunicado pco
JOIN VariableComunicado vco ON pco.pcoId = vco.vcoPlantillaComunicado
WHERE pco.pcoEtiqueta = 'REC_PLZ_LMT_PAG'
AND vco.vcoClave = '${ciudadSolicitud}';

--changeset squintero:03
--comment:
update parametrizacionEjecucionProgramada set pepSegundos = substring(pepSegundos, 1, (len(pepSegundos) - 3)) where pepFrecuencia = 'INTERVALO'
AND pepSegundos is not null;