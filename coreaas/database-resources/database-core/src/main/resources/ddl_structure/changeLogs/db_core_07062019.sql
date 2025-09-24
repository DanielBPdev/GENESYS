--liquibase formatted sql

--changeset jocorrea:01
--comment: 
INSERT ParametrizacionNovedad(novTipoTransaccion, novPuntoResolucion, novRutaCualificada, novTipoNovedad, novProceso) 
VALUES ('DESAFILIACION_AUTOMATICA_POR_MORA','SISTEMA_AUTOMATICO','com.asopagos.novedades.convertidores.empleador.DesafiliacionEmpleadorMoraAportes','AUTOMATICA','NOVEDADES_EMPRESAS_PRESENCIAL');