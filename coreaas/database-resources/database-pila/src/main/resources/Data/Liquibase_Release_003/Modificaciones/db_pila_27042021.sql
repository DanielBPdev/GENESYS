--liquibase formatted sql

--changeset dsuesca:01
--comment: 
CREATE NONCLUSTERED INDEX IX_TemAporte on TemAporte (temRegistroGeneral,temMarcaAporteManual,temMarcaAporteSimulado);
CREATE NONCLUSTERED INDEX IX_TemAporte_temIdTransaccion on TemAporte (temIdTransaccion);
CREATE NONCLUSTERED INDEX IX_TemAporteProcesado on TemAporteProcesado (tprAporteGeneral);
