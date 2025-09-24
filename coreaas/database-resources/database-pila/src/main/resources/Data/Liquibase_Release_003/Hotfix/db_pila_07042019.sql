--liquibase formatted sql

--changeset abaquero:01
--comment: Actualización de listado de tipos de cotizantes independientes para PILA M2
update staging.StagingParametros set stpValorParametro = '3,4,16,34,35,36,51,53,57,59,60,61' where stpNombreParametro = 'SOLICITANTE_INDEPENDIENTE'