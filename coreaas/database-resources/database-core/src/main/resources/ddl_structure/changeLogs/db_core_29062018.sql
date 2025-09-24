--liquibase formatted sql

--changeset alquintero:01
--comment: Insercion tabla ParametrizacionEjecucionProgramada
ALTER TABLE SolicitudCierreRecaudo add sciAportesConciliacion TEXT;
ALTER TABLE AporteGeneral add apgConciliado BIT;
ALTER TABLE aud.SolicitudCierreRecaudo_aud add sciAportesConciliacion TEXT;
ALTER TABLE aud.AporteGeneral_aud add apgConciliado BIT;