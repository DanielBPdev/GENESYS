--liquibase formatted sql

--changeset clmarin:01
--comment: Se agregan campos tabla SolicitudCierreRecaudo_aud y AporteGeneral_aud
ALTER TABLE SolicitudCierreRecaudo_aud add sciAportesConciliacion TEXT;
ALTER TABLE AporteGeneral_aud add apgConciliado BIT;