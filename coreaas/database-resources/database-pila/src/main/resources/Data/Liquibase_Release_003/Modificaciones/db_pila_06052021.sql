--liquibase formatted sql

--changeset dsuesca:01
--comment: Campo para referenciar que la novedad se creo por aportes OK, mantis 0266566
ALTER TABLE staging.RegistroDetalladoNovedad ADD rdnIngresoPorAportesOK BIT;
