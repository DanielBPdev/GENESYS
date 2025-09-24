--liquibase formatted sql

--changeset jocampo:01
--comment: Se agrega campo bcaNumeroOperacion
ALTER TABLE Cartera_aud ADD carDeudaPresuntaUnitaria  numeric(19,5);