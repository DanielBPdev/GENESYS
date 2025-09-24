--liquibase formatted sql
--changeset silopez:01
--comment: creación de columna pfvFechaPagoInicio pfvFechaPagoFin y actualizacion de registros

ALTER TABLE PilaNormatividadFechaVencimiento
ADD pfvFechaPagoInicio DATETIME;

ALTER TABLE PilaNormatividadFechaVencimiento
ADD pfvFechaPagoFin DATETIME;

UPDATE PilaNormatividadFechaVencimiento
SET pfvPeriodoFinal = '2020-03' WHERE pfvPeriodoInicial = '2018-09' AND pfvUltimoDigitoId = '00-07';
UPDATE PilaNormatividadFechaVencimiento
SET pfvPeriodoFinal = '2020-03' WHERE pfvPeriodoInicial = '2018-09' AND pfvUltimoDigitoId = '08-14';
UPDATE PilaNormatividadFechaVencimiento
SET pfvPeriodoFinal = '2020-03' WHERE pfvPeriodoInicial = '2018-09' AND pfvUltimoDigitoId = '15-21';
UPDATE PilaNormatividadFechaVencimiento
SET pfvPeriodoFinal = '2020-03' WHERE pfvPeriodoInicial = '2018-09' AND pfvUltimoDigitoId = '22-28';
UPDATE PilaNormatividadFechaVencimiento
SET pfvPeriodoFinal = '2020-03' WHERE pfvPeriodoInicial = '2018-09' AND pfvUltimoDigitoId = '29-35';

UPDATE PilaNormatividadFechaVencimiento
SET pfvPeriodoFinal = '2020-02' WHERE pfvPeriodoInicial = '2018-09' AND pfvUltimoDigitoId = '36-42';
UPDATE PilaNormatividadFechaVencimiento
SET pfvPeriodoFinal = '2020-02' WHERE pfvPeriodoInicial = '2018-09' AND pfvUltimoDigitoId = '43-49';
UPDATE PilaNormatividadFechaVencimiento
SET pfvPeriodoFinal = '2020-02' WHERE pfvPeriodoInicial = '2018-09' AND pfvUltimoDigitoId = '50-56';
UPDATE PilaNormatividadFechaVencimiento
SET pfvPeriodoFinal = '2020-02' WHERE pfvPeriodoInicial = '2018-09' AND pfvUltimoDigitoId = '57-63';
UPDATE PilaNormatividadFechaVencimiento
SET pfvPeriodoFinal = '2020-02' WHERE pfvPeriodoInicial = '2018-09' AND pfvUltimoDigitoId = '64-69';
UPDATE PilaNormatividadFechaVencimiento
SET pfvPeriodoFinal = '2020-02' WHERE pfvPeriodoInicial = '2018-09' AND pfvUltimoDigitoId = '70-75';
UPDATE PilaNormatividadFechaVencimiento
SET pfvPeriodoFinal = '2020-02' WHERE pfvPeriodoInicial = '2018-09' AND pfvUltimoDigitoId = '76-81';
UPDATE PilaNormatividadFechaVencimiento
SET pfvPeriodoFinal = '2020-02' WHERE pfvPeriodoInicial = '2018-09' AND pfvUltimoDigitoId = '82-87';
UPDATE PilaNormatividadFechaVencimiento
SET pfvPeriodoFinal = '2020-02' WHERE pfvPeriodoInicial = '2018-09' AND pfvUltimoDigitoId = '88-93';
UPDATE PilaNormatividadFechaVencimiento
SET pfvPeriodoFinal = '2020-02' WHERE pfvPeriodoInicial = '2018-09' AND pfvUltimoDigitoId = '94-99';


INSERT INTO PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId,
	pfvOportunidad, pfvFechaPagoInicio, pfvFechaPagoFin)
VALUES ('2020-04', '2020-08', '00-07', 'MES_VENCIDO', '2020-04-13 00:00:00', '2020-09-30 00:00:00');
INSERT INTO PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId,
	pfvOportunidad, pfvFechaPagoInicio, pfvFechaPagoFin)
VALUES ('2020-04', '2020-08', '08-14', 'MES_VENCIDO', '2020-04-13 00:00:00', '2020-09-30 00:00:00');
INSERT INTO PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId,
	pfvOportunidad, pfvFechaPagoInicio, pfvFechaPagoFin)
VALUES ('2020-04', '2020-08', '15-21', 'MES_VENCIDO', '2020-04-13 00:00:00', '2020-09-30 00:00:00');
INSERT INTO PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId,
	pfvOportunidad, pfvFechaPagoInicio, pfvFechaPagoFin)
VALUES ('2020-04', '2020-08', '22-28', 'MES_VENCIDO', '2020-04-13 00:00:00', '2020-09-30 00:00:00');
INSERT INTO PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId,
	pfvOportunidad, pfvFechaPagoInicio, pfvFechaPagoFin)
VALUES ('2020-04', '2020-08', '29-35', 'MES_VENCIDO', '2020-04-13 00:00:00', '2020-09-30 00:00:00');

INSERT INTO PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId,
	pfvOportunidad, pfvFechaPagoInicio, pfvFechaPagoFin)
VALUES ('2020-03', '2020-08', '36-42', 'MES_VENCIDO', '2020-04-13 00:00:00', '2020-09-30 00:00:00');
INSERT INTO PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId,
	pfvOportunidad, pfvFechaPagoInicio, pfvFechaPagoFin)
VALUES ('2020-03', '2020-08', '43-49', 'MES_VENCIDO', '2020-04-13 00:00:00', '2020-09-30 00:00:00');
INSERT INTO PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId,
	pfvOportunidad, pfvFechaPagoInicio, pfvFechaPagoFin)
VALUES ('2020-03', '2020-08', '50-56', 'MES_VENCIDO', '2020-04-13 00:00:00', '2020-09-30 00:00:00');
INSERT INTO PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId,
	pfvOportunidad, pfvFechaPagoInicio, pfvFechaPagoFin)
VALUES ('2020-03', '2020-08', '57-63', 'MES_VENCIDO', '2020-04-13 00:00:00', '2020-09-30 00:00:00');
INSERT INTO PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId,
	pfvOportunidad, pfvFechaPagoInicio, pfvFechaPagoFin)
VALUES ('2020-03', '2020-08', '64-69', 'MES_VENCIDO', '2020-04-13 00:00:00', '2020-09-30 00:00:00');
INSERT INTO PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId,
	pfvOportunidad, pfvFechaPagoInicio, pfvFechaPagoFin)
VALUES ('2020-03', '2020-08', '70-75', 'MES_VENCIDO', '2020-04-13 00:00:00', '2020-09-30 00:00:00');
INSERT INTO PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId,
	pfvOportunidad, pfvFechaPagoInicio, pfvFechaPagoFin)
VALUES ('2020-03', '2020-08', '76-81', 'MES_VENCIDO', '2020-04-13 00:00:00', '2020-09-30 00:00:00');
INSERT INTO PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId,
	pfvOportunidad, pfvFechaPagoInicio, pfvFechaPagoFin)
VALUES ('2020-03', '2020-08', '82-87', 'MES_VENCIDO', '2020-04-13 00:00:00', '2020-09-30 00:00:00');
INSERT INTO PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId,
	pfvOportunidad, pfvFechaPagoInicio, pfvFechaPagoFin)
VALUES ('2020-03', '2020-08', '88-93', 'MES_VENCIDO', '2020-04-13 00:00:00', '2020-09-30 00:00:00');
INSERT INTO PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId,
	pfvOportunidad, pfvFechaPagoInicio, pfvFechaPagoFin)
VALUES ('2020-03', '2020-08', '94-99', 'MES_VENCIDO', '2020-04-13 00:00:00', '2020-09-30 00:00:00');


INSERT INTO PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvUltimoDigitoId, 
pfvDiaVencimiento, pfvTipoFecha, pfvOportunidad, pfvFechaPagoInicio)
VALUES ('2020-04', '00-07', 2, 'HABIL', 'MES_VENCIDO', '2020-10-01 00:00:00');
INSERT INTO PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvUltimoDigitoId, 
pfvDiaVencimiento, pfvTipoFecha, pfvOportunidad, pfvFechaPagoInicio)
VALUES ('2020-04', '08-14', 3, 'HABIL', 'MES_VENCIDO', '2020-10-01 00:00:00');
INSERT INTO PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvUltimoDigitoId, 
pfvDiaVencimiento, pfvTipoFecha, pfvOportunidad, pfvFechaPagoInicio)
VALUES ('2020-04', '15-21', 4, 'HABIL', 'MES_VENCIDO', '2020-10-01 00:00:00');
INSERT INTO PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvUltimoDigitoId, 
pfvDiaVencimiento, pfvTipoFecha, pfvOportunidad, pfvFechaPagoInicio)
VALUES ('2020-04', '22-28', 5, 'HABIL', 'MES_VENCIDO', '2020-10-01 00:00:00');
INSERT INTO PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvUltimoDigitoId, 
pfvDiaVencimiento, pfvTipoFecha, pfvOportunidad, pfvFechaPagoInicio)
VALUES ('2020-04', '29-35', 6, 'HABIL', 'MES_VENCIDO', '2020-10-01 00:00:00');

INSERT INTO PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvUltimoDigitoId, 
pfvDiaVencimiento, pfvTipoFecha, pfvOportunidad, pfvFechaPagoInicio)
VALUES ('2020-03', '36-42', 7, 'HABIL', 'MES_VENCIDO', '2020-10-01 00:00:00');
INSERT INTO PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvUltimoDigitoId, 
pfvDiaVencimiento, pfvTipoFecha, pfvOportunidad, pfvFechaPagoInicio)
VALUES ('2020-03', '43-49', 8, 'HABIL', 'MES_VENCIDO', '2020-10-01 00:00:00');
INSERT INTO PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvUltimoDigitoId, 
pfvDiaVencimiento, pfvTipoFecha, pfvOportunidad, pfvFechaPagoInicio)
VALUES ('2020-03', '50-56', 9, 'HABIL', 'MES_VENCIDO', '2020-10-01 00:00:00');
INSERT INTO PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvUltimoDigitoId, 
pfvDiaVencimiento, pfvTipoFecha, pfvOportunidad, pfvFechaPagoInicio)
VALUES ('2020-03', '57-63', 10, 'HABIL', 'MES_VENCIDO', '2020-10-01 00:00:00');
INSERT INTO PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvUltimoDigitoId, 
pfvDiaVencimiento, pfvTipoFecha, pfvOportunidad, pfvFechaPagoInicio)
VALUES ('2020-03', '64-69', 11, 'HABIL', 'MES_VENCIDO', '2020-10-01 00:00:00');
INSERT INTO PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvUltimoDigitoId, 
pfvDiaVencimiento, pfvTipoFecha, pfvOportunidad, pfvFechaPagoInicio)
VALUES ('2020-03', '70-75', 12, 'HABIL', 'MES_VENCIDO', '2020-10-01 00:00:00');
INSERT INTO PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvUltimoDigitoId, 
pfvDiaVencimiento, pfvTipoFecha, pfvOportunidad, pfvFechaPagoInicio)
VALUES ('2020-03', '76-81', 13, 'HABIL', 'MES_VENCIDO', '2020-10-01 00:00:00');
INSERT INTO PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvUltimoDigitoId, 
pfvDiaVencimiento, pfvTipoFecha, pfvOportunidad, pfvFechaPagoInicio)
VALUES ('2020-03', '82-87', 14, 'HABIL', 'MES_VENCIDO', '2020-10-01 00:00:00');
INSERT INTO PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvUltimoDigitoId, 
pfvDiaVencimiento, pfvTipoFecha, pfvOportunidad, pfvFechaPagoInicio)
VALUES ('2020-03', '88-93', 15, 'HABIL', 'MES_VENCIDO', '2020-10-01 00:00:00');
INSERT INTO PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvUltimoDigitoId, 
pfvDiaVencimiento, pfvTipoFecha, pfvOportunidad, pfvFechaPagoInicio)
VALUES ('2020-03', '94-99', 16, 'HABIL', 'MES_VENCIDO', '2020-10-01 00:00:00');
