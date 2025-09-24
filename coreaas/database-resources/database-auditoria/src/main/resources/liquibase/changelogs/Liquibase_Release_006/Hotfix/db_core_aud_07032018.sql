--liquibase formatted sql

--changeset jocorrea:01
--comment:Adicion de campo en la tabla RegistroNovedadFutura_aud
ALTER TABLE RegistroNovedadFutura_aud ADD rnfProcesada BIT NULL;