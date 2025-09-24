--liquibase formatted sql

--changeset abaquero:01
--comment:Se adiciona campo en la tabla TemCotizante
ALTER TABLE TemNovedad ADD tenEsEmpleadorReintegrable BIT;
ALTER TABLE TemNovedad ADD tenEsTrabajadorReintegrable BIT;