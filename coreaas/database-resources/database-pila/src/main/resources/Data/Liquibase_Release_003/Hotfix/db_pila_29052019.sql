--liquibase formatted sql

--changeset abaquero:01
--comment: Actualización de reglas de normatividad de fechas de vencimiento existentes
update PilaNormatividadFechaVencimiento set pfvPeriodoFinal = '2018-08' where pfvPeriodoFinal is null

--changeset abaquero:02
--comment: Se agrega campo de oportunidad fija en la parametrización de la normatividad de fecha de vencimiento
alter table PilaNormatividadFechaVencimiento add pfvOportunidad varchar(20)

--changeset abaquero:03
--comment: Adición de nuevas reglas de validación de fecha de vencimiento para mes vencidoinsert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacionAportante, pfvDiaVencimiento, pfvTipoFecha, pfvOportunidad) values ('2018-09', NULL, '00-07', NULL, 1, 'HABIL', 'MES_VENCIDO');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacionAportante, pfvDiaVencimiento, pfvTipoFecha, pfvOportunidad) values ('2018-09', NULL, '00-07', NULL, 1, 'HABIL', 'MES_VENCIDO');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacionAportante, pfvDiaVencimiento, pfvTipoFecha, pfvOportunidad) values ('2018-09', NULL, '08-14', NULL, 2, 'HABIL', 'MES_VENCIDO');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacionAportante, pfvDiaVencimiento, pfvTipoFecha, pfvOportunidad) values ('2018-09', NULL, '15-21', NULL, 3, 'HABIL', 'MES_VENCIDO');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacionAportante, pfvDiaVencimiento, pfvTipoFecha, pfvOportunidad) values ('2018-09', NULL, '22-28', NULL, 5, 'HABIL', 'MES_VENCIDO');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacionAportante, pfvDiaVencimiento, pfvTipoFecha, pfvOportunidad) values ('2018-09', NULL, '29-35', NULL, 6, 'HABIL', 'MES_VENCIDO');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacionAportante, pfvDiaVencimiento, pfvTipoFecha, pfvOportunidad) values ('2018-09', NULL, '36-42', NULL, 7, 'HABIL', 'MES_VENCIDO');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacionAportante, pfvDiaVencimiento, pfvTipoFecha, pfvOportunidad) values ('2018-09', NULL, '43-49', NULL, 8, 'HABIL', 'MES_VENCIDO');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacionAportante, pfvDiaVencimiento, pfvTipoFecha, pfvOportunidad) values ('2018-09', NULL, '50-56', NULL, 9, 'HABIL', 'MES_VENCIDO');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacionAportante, pfvDiaVencimiento, pfvTipoFecha, pfvOportunidad) values ('2018-09', NULL, '57-63', NULL, 10, 'HABIL', 'MES_VENCIDO');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacionAportante, pfvDiaVencimiento, pfvTipoFecha, pfvOportunidad) values ('2018-09', NULL, '64-69', NULL, 11, 'HABIL', 'MES_VENCIDO');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacionAportante, pfvDiaVencimiento, pfvTipoFecha, pfvOportunidad) values ('2018-09', NULL, '70-75', NULL, 12, 'HABIL', 'MES_VENCIDO');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacionAportante, pfvDiaVencimiento, pfvTipoFecha, pfvOportunidad) values ('2018-09', NULL, '76-81', NULL, 13, 'HABIL', 'MES_VENCIDO');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacionAportante, pfvDiaVencimiento, pfvTipoFecha, pfvOportunidad) values ('2018-09', NULL, '82-87', NULL, 14, 'HABIL', 'MES_VENCIDO');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacionAportante, pfvDiaVencimiento, pfvTipoFecha, pfvOportunidad) values ('2018-09', NULL, '88-93', NULL, 15, 'HABIL', 'MES_VENCIDO');
insert into PilaNormatividadFechaVencimiento (pfvPeriodoInicial, pfvPeriodoFinal, pfvUltimoDigitoId, pfvClasificacionAportante, pfvDiaVencimiento, pfvTipoFecha, pfvOportunidad) values ('2018-09', NULL, '94-99', NULL, 16, 'HABIL', 'MES_VENCIDO');