--liquibase formatted sql

--changeset abaquero:01
--comment:Se adiciona campo en regOUTNovedadFuturaProcesada la tabla RegistroGeneral
ALTER TABLE staging.RegistroGeneral ADD regOUTNovedadFuturaProcesada BIT;