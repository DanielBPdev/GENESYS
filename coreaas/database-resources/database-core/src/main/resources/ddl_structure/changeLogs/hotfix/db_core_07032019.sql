--liquibase formatted sql

--changeset jocampo:01
--comment: 
ALTER TABLE Cartera ADD carDeudaPresuntaUnitaria  numeric(19,5);
ALTER TABLE aud.Cartera_aud ADD carDeudaPresuntaUnitaria  numeric(19,5);