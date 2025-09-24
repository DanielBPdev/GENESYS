--liquibase formatted sql

--changeset fvasquez:01
--comment: Se agrega campo bcaNumeroOperacion
ALTER TABLE SolicitudNovedadPila_aud ADD spiOriginadoEnAporteManual bit;