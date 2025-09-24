--liquibase formatted sql

--changeset jocampo:01
--comment: 
ALTER TABLE TemCotizante ALTER COLUMN tctNomSucursal varchar(100)
