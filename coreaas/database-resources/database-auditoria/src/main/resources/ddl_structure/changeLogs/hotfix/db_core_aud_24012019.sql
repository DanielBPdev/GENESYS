--liquibase formatted sql

--changeset jocorrea:01
--comment: 
ALTER TABLE PostulacionFovis_aud ADD pofValorAvaluoCatastral NUMERIC(19,5) NULL;
ALTER TABLE ProyectoSolucionVivienda_aud DROP COLUMN psvAvaluoCatastral;