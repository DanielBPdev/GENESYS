--liquibase formatted sql

--changeset abaquero:01
--comment: Se adiciona campo de referencia a la tabla RegistroDetalladoNovedad para control de creación de TemNovedad
ALTER TABLE dbo.TemNovedad ADD tenRegistroDetalladoNovedad bigint