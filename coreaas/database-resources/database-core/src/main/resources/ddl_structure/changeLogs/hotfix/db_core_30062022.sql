INSERT INTO ParametrizacionEjecucionProgramada
( pepProceso
, pepHoras
, pepMinutos
, pepSegundos
, pepDiaSemana
, pepDiaMes
, pepMes
, pepAnio
, pepFechaInicio
, pepFechaFin
, pepFrecuencia
, pepEstado)
VALUES ('CARTERA_1702_FIRMEZA_TITULO', 17, 53, 00, null, null, null, null, null, null, 'DIARIO', 'INACTIVO');


INSERT
INTO Constante
( cnsNombre
, cnsValor
, cnsDescripcion
, cnsTipoDato)
VALUES
    ('DIAS_FIRMEZA_TITULO', 10, 'Identifica el numero de dias en el cual e debe enviar nofiticacion de firmeza de titulo', 'NUMBER');